unit Ted;
{$mode macpas}
interface
	uses
		{$ifc  undefined THINK_PASCAL}
		MacOSAll,
		{$endc}
		MyGlobals, Boxes, Error_Alert;
	const
		YardSize = 10000;
		miniSize = 200;
		scale = 10;
		{$ifc undefined THINK_PASCAL}
		kFree = 0;
		kAnimalTrunk = 1;
		kAnimalJoint = 2;
		kAnimalClaw = 3;
		kSectionTrunk = 4;
		kSectionJoint = 5;
		kSectionClaw = 6;
		kSegmentTrunk = 7;
		kSegmentJoint = 8;
		kSegmentClaw = 9;
		kJoint = 10;
		kClaw = 11;
		{$endc}
		
{2500 would allow 18 Animals with 15 segments each and 4 joints per segment.}
	type

		AtomKind = (Free, AnimalTrunk, AnimalJoint, AnimalClaw, SectionTrunk, SectionJoint, SectionClaw, SegmentTrunk, SegmentJoint, SegmentClaw, Joint, Claw);
		{$ifc undefined THINK_PASCAL}
		{Think Pascal represented enumerations with a short integer (byte), and wrote them}
		{to files as a single byte, with a second nonce byte as padding. This record is used}
		{for reading and writing Atoms to files under Carbon, to preserve the use of the AtomKind}
		{enumeration in the rest of the code. - ABC}

		
		{$endc}
		Atom = record
			Kind: AtomKind;
			Height: single;		{also used for Thickness of a Joint}
			Width: single;		{also used for Length of a Joint}
			Angle: single;		{also used in an AnimalTrunk to store the number of atoms in the animal}
						{also used in SectionTrunk to store the Overlap of segments}
						{also used in SegmentTrunk to store the rank number of the segment}
				
			{where to look in the BoneYard for the next atom. 0 means end of chain}
			{Also used in AnimalTrunk to store Gradient gene, slightly more or less than 100.  Treat as Percentage}
			NextLikeMe: SInt16;
				
			{where to look in the BoneYard for the next atom. 0 means end of chain}
			FirstBelowMe: SInt16;
		end;
			
		AtomPtr = ^Atom;
		AtomHdl = ^AtomPtr;
		
		AtomArray = array[1..Yardsize] of AtomHdl;		{for the real thing, use 2500}
		SmallAtomArray = array[1..miniSize] of AtomHdl;		{Just holds one animal, compactly}
		AnimalStarts = array[0..MaxBoxes] of integer;

		LevelLocs = array[1..10] of integer;		{stores indexes of where we are when travelling through an animal}
		{to copy it.  1 spare, 2 AnimalTrunk, 3 AnimalJoint, 4  SectionTrunk, 5 SectionJoint, 6 SegmentTrunk, }
			{7 SegmentJoint, 8 Joint, 9 Claw, 10 spare}
		KindsData = array[AtomKind] of integer;		{a number for each kind of Atom}
		CumParams = array[1..9] of real;		{where the AnimalTrunk.Width is multiplied by SegmentTrunk.Width}
	var
		BoneYard: AtomArray;		{all atoms live here.  We index it to look at atoms}
		MiniYard: SmallAtomArray;
		RecordTop, RecordBottom, CurrentGenome: integer;		{index of first atom on an Animal}
		BreedersChoice: AnimalStarts;		{indexes of starts of all the Animals on the screen}
		NorthPole, SouthPole, EastPole, WestPole, FreePointer, MiniFree: integer;			{start searching from here for free blocks}
		ParamOffset: KindsData;	{Tells where Height, Width, Angle go in a CumParams.  see Draw}
		AnimalPicture: array[0..MaxBoxes] of PicHandle;
		Midriff, SegmentCounter, SecondSegmentAtomNo: integer;
		naive: boolean;
		GradientFactor: real;
procedure Kill (which: integer);
	
	function CountAtoms (which: integer): integer;
	procedure InitBoneYard;
	procedure TellError (what: string);
	procedure Tandem (target: integer);
	procedure TellStatus (status: OsStatus);
	function Extract (which: integer): integer;
	function Deposit (which: integer): integer;
	function Reproduce (which: integer): integer;
	procedure CountSeg (which: integer);
	function MinimalAnimal: integer;
	function AllocateAtom: Integer;
	function CopyAtom (which: integer): integer;		
implementation

	procedure TellStatus;
	begin
		TellError(GetMacOSStatusErrorString(status));
	end;
	procedure TellError (what: string);
	begin
		ParamText(what, '', '', '');
		A_Error_Alert;
	end;

	function randint (Max: Integer): Integer;
		var
			r: integer;
	begin
{delivers integer between 1 and Max;}
		repeat
			r := ABS(Random) mod (Max + 1)
		until r > 0;
		randint := r;
	end;

{Basic handling of Atoms}
	procedure InitBoneYard;		{Call just once at the beginning}
		var
			which: integer;
	begin
		for which := 1 to YardSize do
			BoneYard[which] := AtomHdl(NewHandle(SizeOf(Atom)));
		for which := 1 to MiniSize do
			begin
				MiniYard[which] := AtomHdl(NewHandle(SizeOf(Atom)));
				MiniYard[which]^^.kind := free;
			end;
		FreePointer := 1;
		for which := 1 to YardSize do
			begin
				BoneYard[which]^^.Kind := Free;
				BoneYard[which]^^.NextLikeMe := 0;   {Don't count on this}
			end;
		ParamOffset[AnimalTrunk] := 1;		{where in a CumParams the Width of an AnimalTrunk gets multiplied in}
		ParamOffset[AnimalJoint] := 4;
		ParamOffset[AnimalClaw] := 7;
		ParamOffset[SectionTrunk] := 1;
		ParamOffset[SectionJoint] := 4;
		ParamOffset[SectionClaw] := 7;
		ParamOffset[SegmentTrunk] := 1;
		ParamOffset[SegmentJoint] := 4;
		ParamOffset[SegmentClaw] := 7;
		ParamOffset[Joint] := 4;
		ParamOffset[Claw] := 7;
	end;

	function AllocateAtom: Integer;
		var
			this: Atom;
			oldFreePtr, which: integer;
	begin
		oldFreePtr := FreePointer;
		which := FreePointer;
		repeat
			this := BoneYard[which]^^;
			which := which + 1;		{remember its one bigger}
		until (this.Kind = Free) or (which > YardSize);
		if which > YardSize then
			begin
				which := 1;
				repeat
					this := BoneYard[which]^^;
					which := which + 1;
				until (this.Kind = Free) or (which > oldFreePtr);
				if which = oldFreePtr + 1 then
					TellError('Morphs are too complex');
			end;
		FreePointer := which;
		if which <= 1 then
			TellError('AllocateAtom tried to put out less than 1');
		if which > Yardsize then
			TellError('AllocateAtom tried to put out >Yardsize');
		AllocateAtom := which - 1;		{undo the +1 above}
	end;

	procedure Deallocate (which: integer);
	begin
		BoneYard[which]^^.Kind := Free;		{toss it back}
	end;

{Creating and destroying Animals}
	procedure Kill (which: integer);
	{Destroy this animal.   Mark all of its Atoms as Free again.}
	{Recursively step through the animal}
		var
			this: Atom;
	begin
		this := BoneYard[which]^^;
		if this.FirstBelowMe <> 0 then
			Kill(this.FirstBelowMe);
		if (this.NextLikeMe <> 0) and (this.kind <> AnimalTrunk) then
			Kill(this.NextLikeMe);
		Deallocate(which);		{Free this Atom}
	end; {Kill}

	function CopyAtom (which: integer): integer;
		var
			newPlace: integer;
	begin
	{Duplicate this entire animal.   Return the index of the start of the new animal.}
	{It is a very good idea to Kill the old animal first.  That way, we can reuse its atoms.}
		newPlace := AllocateAtom;		{Grab a new atom}
		BoneYard[NewPlace]^^ := BoneYard[which]^^;
		if BoneYard[which]^^.FirstBelowMe <> 0 then
			BoneYard[NewPlace]^^.FirstBelowMe := CopyAtom(BoneYard[which]^^.FirstBelowMe);
		if (BoneYard[which]^^.NextLikeMe <> 0) and (BoneYard[which]^^.kind <> AnimalTrunk) then
			BoneYard[NewPlace]^^.NextLikeMe := CopyAtom(BoneYard[which]^^.NextLikeMe);
		CopyAtom := newPlace;			{Return the index of the new one}
	end;

	function CopyExceptNext (which: integer): integer;
		var
			newPlace: integer;
	begin
	{Duplicate Subtree starting at the atom which, but don't copy NextLikeMe.  Leave old value there}
	{Copy the things I own, but not the things after me}
		newPlace := AllocateAtom;		{Grab a new atom}
		BoneYard[NewPlace]^^ := BoneYard[which]^^;
		if BoneYard[which]^^.FirstBelowMe <> 0 then
			BoneYard[NewPlace]^^.FirstBelowMe := CopyAtom(BoneYard[which]^^.FirstBelowMe);		{Normal COPY from here on}
		CopyExceptNext := newPlace;			{Return the index of the new one}
	end;


	function FindNth (which, pick: integer; var count: integer): integer;
	{travel over the Animal, counting Atoms and return the Nth}
	begin
		count := count + 1;
		if BoneYard[which]^^.kind = SegmentTrunk then
			SegmentCounter := Segmentcounter + 1;
		if segmentCounter = 2 then
			SecondSegmentAtomNo := count;
		if count >= pick then
			FindNth := which		{We are done!}
		else
			with BoneYard[which]^^ do
				begin
					if FirstBelowMe <> 0 then
						FindNth := FindNth(FirstBelowMe, pick, count);
					if not (count >= pick) then
						if (NextLikeMe <> 0) then
							FindNth := FindNth(NextLikeMe, pick, count);
					if not (count >= pick) then
						FindNth := 0;		{not there yet}
				end;
	end;

	procedure CountSeg (which: integer);
		var
			this: Atom;
	begin
		this := BoneYard[which]^^;
		with this do
			begin
				if kind = SegmentTrunk then
					begin
						SegmentCounter := SegmentCounter + 1;
						BoneYard[which]^^.angle := SegmentCounter;
					end;
				if FirstBelowMe <> 0 then
					CountSeg(FirstBelowMe);
				if (NextLikeMe <> 0) and (kind <> AnimalTrunk) then
					CountSeg(NextLikeMe);
			end
	end;

	function CountAtoms (which: integer): integer;
	{travel over the Animal, counting Atoms}
		var
			count: integer;
	begin
		count := 1;	{count me}
		with BoneYard[which]^^ do
			begin
				if FirstBelowMe <> 0 then
					count := count + CountAtoms(FirstBelowMe);
				if (NextLikeMe <> 0) and (kind <> AnimalTrunk) then
					count := count + CountAtoms(NextLikeMe);
			end;
		CountAtoms := count;	{Me and all below me}
	end;

	function GetFactor: real;		{How much to grow or shrink a Length or Height or Angle}
		var
			choose: integer;
	begin
		case MutationPressure of
			positive: 
				choose := 2 + randint(2);
			zero: 
				choose := randint(4);
			negative: 
				choose := randint(2);
		end; {cases}
		case choose of
			1: 			{Richard, you can play with these factors}
				GetFactor := 0.50;
			2: 
				GetFactor := 0.9;
			3: 
				GetFactor := 1.1;
			4: 
				GetFactor := 1.5;
		end; {cases}
	end;


	function DoDelete (which: integer): boolean;
	{Delete a section of the animal somewhere near the atom which.}
	{Caller must correct the AtomCount of the whole animal.  Return false if failed}
		var
			parent, chain: integer;
	{Must have a hold on the atom above what we delete.  If chosen atom is:  }
	{AnimalTrunk   delete first Sec}
	{	AnimalJoint   delete first Sec}
	{	AnimalClaw	delete first Sec}
	{		SectionTrunk	delete next Sec}
	{			SectionJoint		delete first Seg}
	{			SectionClaw		delete first Seg}
	{				SegmentTrunk		delete next Seg}
	{					SegmentJoint		delete first Joint}
	{					SegmentClaw		delete first Joint}
	{						Joint				delete next Joint}
	{						Joint				delete next Joint}
	{						Joint				delete Claw}
	{							Claw				fail}
	{Also fail if trying to delete last example of a Kind}
	begin
		parent := which;
		DoDelete := false;	{unless we actually succeed in killing one}
		if (BoneYard[Parent]^^.Kind = AnimalTrunk) then
			begin
				parent := BoneYard[Parent]^^.FirstBelowMe;		{AinmalJoint}
			end;
		if (BoneYard[Parent]^^.Kind = AnimalJoint) or (BoneYard[Parent]^^.Kind = SectionJoint) or (BoneYard[Parent]^^.Kind = SegmentJoint) then
			begin
				parent := BoneYard[Parent]^^.FirstBelowMe;		{AinmalClaw is parent}
			end;
		if parent <> 0 then
			with BoneYard[Parent]^^ do
				if (Kind = SectionTrunk) or (Kind = SegmentTrunk) or (Kind = Joint) then
					begin		{Delete NextLikeMe of parent}
						if (NextLikeMe <> 0) then
							begin
								chain := BoneYard[NextLikeMe]^^.NextLikeMe;		{May be 0}
								BoneYard[NextLikeMe]^^.NextLikeMe := 0;		{So Kill won't get the rest of chain}
								Kill(NextLikeMe);		{won't be killing last one, since parent qualifies as one}
								NextLikeMe := chain;
								DoDelete := true;
							end;
					end
				else		{Try to delete FirstBelow}
					if (FirstBelowMe <> 0) then			{we know FirstBelow exists}
						begin
							chain := BoneYard[FirstBelowMe]^^.NextLikeMe;		{Atom after one we will delete}
							BoneYard[FirstBelowMe]^^.NextLikeMe := 0;
							if (chain <> 0) then			{FirstBelow is not only one }
								begin
									Kill(FirstBelowMe);
									FirstBelowMe := chain;
									DoDelete := true;
								end;
						end;
	end; {DoDelete}

	procedure Tandem (target: integer);
		var
			extraclaw: integer;
			targetAtom: Atom;
		{If Dup and target is second or third part of an Animal, Section, or Segment,}
		{Then jump down to the next part of the animal}
	begin
		targetAtom := BoneYard[target]^^;
		if (targetAtom.Kind = AnimalJoint) or (targetAtom.Kind = SectionJoint) or (targetAtom.Kind = SegmentJoint) then
			begin
				target := BoneYard[target]^^.NextLikeMe;   {AinmalClaw}
				targetAtom := BoneYard[target]^^;      {fetch new atom}
			end;
		if (targetAtom.Kind = AnimalClaw) or (targetAtom.Kind = SectionClaw) or (targetAtom.Kind = SegmentClaw) then
			target := BoneYard[target]^^.FirstBelowMe;
{SectionTrunk .. where we want to be }
		with BoneYard[target]^^ do
			begin
				NextLikeMe := CopyExceptNext(target);  {Insert copy of me after me}
					{CopyExceptNext makes sure NextLikeMe of copy now points to old NextLikeMe of target}
					{So brothers are kept, and new subtree is inserted}
				if (Kind = Joint) and (FirstBelowMe <> 0) then		{last joint has claw.  When duplicate, get rid of extra claw}
					begin
						extraClaw := FirstBelowMe;
						FirstBelowMe := 0;
						Kill(extraClaw);
					end;
			end;
		BoneYard[BreedersChoice[MidBox]]^^.Angle := CountAtoms(BreedersChoice[MidBox]); 		{A little wasteful to count entire animal again}
	end; {Tandem}

	function Mutate (which: integer): boolean;
	{Mutate first picks an atom randomly from the Animal.}
	{	From num of atoms, picks one and step down to it}
	{		Flip a coin for what to do: change Height, Width, Angle, Dup part, Delete part, Flip angle}
	{			Test if legal to do it and do it (else return false)}
	{				Delete does not delete the first-and-only of its Kind}
	{Forbid: Angle mod if none, delete last Section, or Seg }
	{		Delete Animal, Dup Animal,   Delete Claw, Dup Claw}
	{Range limits on some modifications??  Only angles can be negative.}
		var
			size, pick, count, target, change, extraclaw, lastSegment, AtomNumber: integer;
			this, targetAtom: Atom;
			OK, MutOK, CouldBe: boolean;
			factor: real;
	begin
		this := BoneYard[which]^^;
		if this.Kind <> AnimalTrunk then
			TellError('Not an animal');
		SecondSegmentAtomNo := 0;
		AtomNumber := CountAtoms(which);
		LastSegment := SegmentCounter;
		size := trunc(this.Angle);		{As a convention, we keep the number of Atoms in this animal in AnimalTrunk's Angle field}
		pick := Randint(size);		{a number from 1 to size.  Index of the atom we will modify}
		count := 0;
		target := FindNth(which, pick, count);  	{find the Nth atom}
		if target = 0 then
			begin
				TellError('Atom count is wrong.  Fatal.  Quitting');		{Aren't pick atoms in this Animal}
				exitToShell
			end;
		targetAtom := BoneYard[target]^^;

	{Decide what to do}
		change := randint(7);		{seven basic operations}
			{ 1 twiddle Height, 2 twiddle Width, 3 twiddle Angle, 4 Duplicate entire subtree, 5 Delete subtree}
			{ 6 reverse an angle , 7 reverse sign of Gradient}
		if (change = 7) and (targetAtom.kind = AnimalTrunk) then
			BoneYard[target]^^.NextLikeMe := -BoneYard[target]^^.NextLikeMe;
		if (change = 4) then
		{If Dup and target is second or third part of an Animal, Section, or Segment,}
		{Then jump down to the next part of the animal}
			begin
				if (targetAtom.Kind = AnimalJoint) or (targetAtom.Kind = SectionJoint) or (targetAtom.Kind = SegmentJoint) then
					begin
						target := BoneYard[target]^^.NextLikeMe;   {AinmalClaw}
						targetAtom := BoneYard[target]^^;      {fetch new atom}
					end;
				if (targetAtom.Kind = AnimalClaw) or (targetAtom.Kind = SectionClaw) or (targetAtom.Kind = SegmentClaw) then
					target := BoneYard[target]^^.FirstBelowMe;
{SectionTrunk .. where we want to be }
			end;

		MutOK := false;
		with BoneYard[target]^^ do
			case kind of
				AnimalTrunk: 
					if AnimalTrunkMut then
						MutOK := true;
				AnimalJoint: 
					if AnimalLegsMut then
						MutOK := true;
				AnimalClaw: 
					if AnimalClawsMut then
						MutOK := true;
				SectionTrunk: 
					if SectionTrunkMut then
						MutOK := true;
				SectionJoint: 
					if SectionLegsMut then
						MutOK := true;
				SectionClaw: 
					if SectionClawsMut then
						MutOK := true;
				SegmentTrunk: 
					if SegmentTrunkMut then
						MutOK := true;
				SegmentJoint: 
					if SegmentLegsMut then
						MutOK := true;
				SegmentClaw: 
					if SegmentClawsMut then
						MutOK := true;
				Joint: 
					if LegsMut then
						MutOK := true;
				Claw: 
					if ClawsMut then
						MutOK := true;
				otherwise
					MutOK := false;
			end; {cases }

		case FocusOfAttention of
			FirstSegmentOnly: 
				if SecondSegmentAtomNo > 0 then
					begin
						if count < SecondSegmentAtomNo then
							begin
								with BoneYard[target]^^ do
									CouldBe := (kind = SegmentTrunk) or (kind = SegmentJoint) or (kind = SegmentClaw) or (kind = joint) or (kind = claw);
								if not CouldBe then
									MutOK := false;
							end
					end
				else
					MutOK := false;
			LastSegmentOnly: 
				if SegmentCounter <> lastSegment then
					MutOk := false;
			AnySegment: 
				;
{No need for action.  MutOK retains its present value}
		end; {cases}


		if MutOK then
			with BoneYard[target]^^ do
				begin
					OK := true;
					if ((change = 4) or (change = 5)) and ((Kind = Claw)) then{(Kind = AnimalTrunk) or}
						OK := false; {Forbid delete or dup of claw}
					if ((change = 3) or (change = 6)) and ((Kind = AnimalTrunk) or (Kind = SegmentTrunk)) then
						OK := false;		{These atoms have no Angle part. SectionTrunk does, because 'angle' is overlap, by convention}
					if OK then
						begin
							if (change = 4) then
								begin
									if DuplicationMut then
										begin
											if kind = AnimalTrunk then
												NextLikeMe := NextLikeMe + 1
											else{Special case, means GradientFactor}
												NextLikeMe := CopyExceptNext(target);  {Insert copy of me after me}
					{CopyExceptNext makes sure NextLikeMe of copy now points to old NextLikeMe of target}
					{So brothers are kept, and new subtree is inserted}
											if (Kind = Joint) and (FirstBelowMe <> 0) then		{last joint has claw.  When duplicate, get rid of extra claw}
												begin
													extraClaw := FirstBelowMe;
													FirstBelowMe := 0;
													Kill(extraClaw);
												end;
											BoneYard[which]^^.Angle := CountAtoms(which); 		{A little wasteful to count entire animal again}
										end
									else
										OK := false;
								end; {change=4}
							if (change < 4) then
								begin
									factor := GetFactor;		{See table above}
									case change of
										1: 
											begin
												if HeightMut then
													Height := Height * factor
												else
													OK := false;
											end;
										2: 
											begin
												if WidthMut then
													Width := Width * factor
												else
													OK := false;
											end;
										3: 
											begin
												if AngleMut then
													begin
														Angle := Angle * factor;
														if (kind = SectionTrunk) then
															begin
																Angle := abs(angle); {forbid backward overlaps}
																if Angle > 1 then
																	Angle := 1; {Otherwise disembodied segments}
															end;
													end
												else
													OK := false;
											end;
									end; {cases}
								end;
							if (change = 5) then
								begin
									if DeletionMut then
										begin
											if kind = AnimalTrunk then
												NextLikeMe := NextLikeMe - 1; {special case. by convention means GradientFactor}
{Delete.  Complex because we need to talk to the atom above where we delete}
											OK := DoDelete(target);  {there is a problem here}
											if OK then
												BoneYard[which]^^.Angle := CountAtoms(which);
		{A little wasteful to count entire animal again}
										end
									else
										OK := false;
								end;
							if (change = 6) and (kind <> SectionTrunk) then
								begin
									if AngleMut then
										Angle := -1.0 * Angle {reverse an angle}
									else
										OK := false;
								end
						end;
				end;
		Mutate := OK and MutOK;
	end;

	function Reproduce (which: integer): integer;
	{Reproduce copies an animal and calls Mutate}
	{Please kill the old animal before calling this.  We may need to use his atoms.}
		var
			counter, new: integer;
			done: boolean;
	begin
		counter := 0;
		new := CopyAtom(which);
		repeat
			counter := counter + 1;
{if counter = 100 then}
{SetCursor(GetCursor(watchCursor)^^);}
			done := Mutate(new);		{If it fails, just try again until we succeed at changing something}
		until done or (counter > 1000);
		if counter > 1000 then
			begin
				TellError('Timed out, perhaps attempting impossible duplication or deletion');
				Reproduce := which;
			end
		else
			Reproduce := new;		{Return it}
{SetCursor(GetCursor(-16000)^^);}
 {Arrow cursor}
	end;

	function NewAtom: integer;
	{Create a new atom with generic values in it}
	{NewAtom has 1.0 in factors and 0 in pointers as a nice default}
		var
			new: integer;
	begin
		new := AllocateAtom;
		with BoneYard[new]^^ do
			begin
				Height := 1.0;
				Width := 1.0;
				Angle := 1.0;
				NextLikeMe := 0;
				FirstBelowMe := 0;
			end;
		NewAtom := new;
	end;
{I still vote for AnimalJoint . Width = 20 and AnimalJoint . Angle = 0.25 in the default animal .}

	function MinimalAnimal: integer;
		var
			aa, bb: integer;
	begin
		aa := NewAtom;
		BoneYard[aa]^^.Kind := Claw;

		bb := NewAtom;
		BoneYard[bb]^^.Kind := Joint;
		BoneYard[bb]^^.width := 5;
		BoneYard[bb]^^.angle := 2;
		BoneYard[bb]^^.FirstBelowMe := aa;

		aa := NewAtom;
		BoneYard[aa]^^.Kind := SegmentClaw;
		BoneYard[aa]^^.FirstBelowMe := bb;

		bb := NewAtom;
		BoneYard[bb]^^.Kind := SegmentJoint;
		BoneYard[bb]^^.NextLikeMe := aa;
		BoneYard[bb]^^.angle := 2;

		aa := NewAtom;
		BoneYard[aa]^^.Kind := SegmentTrunk;
		BoneYard[aa]^^.FirstBelowMe := bb;

		bb := NewAtom;
		BoneYard[bb]^^.Kind := SectionClaw;
		BoneYard[bb]^^.FirstBelowMe := aa;

		aa := NewAtom;
		BoneYard[aa]^^.Kind := SectionJoint;
		BoneYard[aa]^^.NextLikeMe := bb;

		bb := NewAtom;
		BoneYard[bb]^^.Kind := SectionTrunk;
		BoneYard[bb]^^.Angle := 0.5; {Segment overlap, by convention}
		BoneYard[bb]^^.FirstBelowMe := aa;

		aa := NewAtom;
		BoneYard[aa]^^.Kind := AnimalClaw;
		BoneYard[aa]^^.FirstBelowMe := bb;

		bb := NewAtom;
		BoneYard[bb]^^.Kind := AnimalJoint;
		BoneYard[bb]^^.NextLikeMe := aa;
		BoneYard[bb]^^.Width := 5;		{make it visible}
		BoneYard[bb]^^.angle := 5;

		aa := NewAtom;
		BoneYard[aa]^^.Kind := AnimalTrunk;
		BoneYard[aa]^^.FirstBelowMe := bb;
		BoneYard[aa]^^.NextLikeMe := -2; {Gradient, by convention}
		BoneYard[aa]^^.Angle := CountAtoms(aa);
		BoneYard[aa]^^.Height := 20;
		BoneYard[aa]^^.Width := 20;

		MinimalAnimal := aa;
	end;

end.