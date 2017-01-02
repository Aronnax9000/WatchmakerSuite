unit Pedigree;

interface

uses
	MacOSAll, 
	Globals, 
	HIBoxTypes,
	DevelopDefs,
	RectDrawDefs,
	PedigreeTypes,
	PersonDefs, 
	ModeDefs, 
	ReproduceDef, 
	TriangleUtils, 
	AlbumUtil, 
	ErrorUnit, 
	DrawPicDefs;
	
	procedure InitializePedigreeRegions;
	
	procedure PhylogNew (Biomorph: PersonPtr);
	procedure DrawOutFrom (ThisFull: FullHandle);
	procedure Detach (ThisFull: FullHandle);
	procedure Shoot (ThisFull: FullHandle);
	function MouseInBox (MLoc: HIPoint; VAR ThisFull: FullHandle): Boolean;
	procedure FollowMouse (ThisFull: FullHandle);
	function IsAnAdam (ThisFull: FullHandle): Boolean;
	function Created: FullHandle;
	procedure InitializePedigree;

implementation

	var	
		Region2, DestRegion, SaveRegion: RgnHandle;

		
function Created: FullHandle;
	VAR
		{FIXME is there animation going on here? -ABC}
		{TickValue: LongInt;}
		Mem: Size;
		interim: FullHandle;
		sizeNeeded: LongInt;
	begin
		Mem := CompactMem(SizeOf(Full));
		IF Mem < SizeOf(Full) THEN
			Sysbeep(1);
		SizeNeeded := SizeOf(Full);
		Interim := FullHandle(NewHandle(SizeNeeded));
		IF MemError <> noErr THEN
			ExitToShell;
		Interim^^.Parent := NIL;
		Interim^^.FirstBorn := NIL;
		Interim^^.LastBorn := NIL;
		Interim^^.ElderSib := NIL;
		Interim^^.YoungerSib := NIL;
		Interim^^.Damaged := FALSE;
		Interim^^.next := NIL;
		Interim^^.prec := NIL;
		created := interim
	end;

	procedure InitializePedigreeRegions;
		begin
			Region2 := NewRgn;
			DestRegion := NewRgn;
			SaveRegion := NewRgn;
		end;

	procedure InitializePedigree;
	begin
		InitializePedigreeRegions;
		Rays := 1;
		RootGod := GodHandle(NewHandle(SizeOf(God)));
			
		{This stanza of four lines by Richard Dawkins mentioned in his original documentation.}
		{Please do not disturb, and preserve in ports, as nearly as may be. -- Alan Canon}
		RootGod^^.Adam := NIL;
		RootGod^^.NextGod := NIL;
		RootGod^^.PreviousGod := NIL;
		ThatFull := Created;

		OldSpecialFull := NIL;
		SpecialFull := NIL;
	end;
	


function MouseInBox (MLoc: HIPoint; VAR ThisFull: FullHandle): Boolean;
	VAR
		Victim: FullHandle;
	procedure CheckVictim (ThisFull: FullHandle);
		begin
		(*
			IF PtInRect(MLoc, ThisFull^^.Surround) THEN
				Victim := ThisFull
			ELSE
				begin
					IF ThisFull^^.Next <> NIL THEN
						CheckVictim(ThisFull^^.Next)
				end
		*) {FIXME}
		end; {CheckVictim}
		
	begin
		Victim := NIL;
		IF ThisFull <> NIL THEN
			CheckVictim(ThisFull);
		IF Victim = NIL THEN
			MouseInBox := FALSE
		ELSE
			begin
				ThisFull := Victim;
				MouseInBox := TRUE
			end
	end; {MouseInBox}


procedure HighlightAll (ThisFull: FullHandle);
	begin {Highlights Thisfull and its elder sibs, and all their descendants}
	(*
		IF ThisFull <> NIL THEN
			InvertRect(ThisFull^^.Surround);
		IF ThisFull^^.LastBorn <> NIL THEN
			HighlightAll(ThisFull^^.LastBorn);
		IF ThisFull^^.ElderSib <> NIL THEN
			HighlightAll(ThisFull^^.ElderSib);
	*) {FIXME}
	end; {HighlightAllSibs}


procedure HighlightPedigree (ThisFull: FullHandle);
	begin
	(* FIXME)
		IF ThisFull <> NIL THEN
			begin
				InvertRect(ThisFull^^.Surround);
				IF ThisFull^^.LastBorn <> NIL THEN
					HighlightAll(ThisFull^^.LastBorn);
			end
			*)
	end; {HighlightPedigree}


procedure FindLastGod; {Delivers last God in theGod}
	VAR
		thisGod: GodHandle;
	procedure TryGod (thisGod: GodHandle);
		begin
			GodCounter := GodCounter + 1;
			IF thisGod^^.NextGod = NIL THEN
				theGod := thisGod
			ELSE
				TryGod(thisGod^^.NextGod)
		end; {TryGod}
	begin
		thisGod := RootGod;
		GodCounter := 1;
		IF thisGod^^.NextGod = NIL THEN
			theGod := thisGod
		ELSE
			TryGod(thisGod)
	end; {FindLastGod}


function IsAnAdam (ThisFull: FullHandle): Boolean;
{Returns TRUE if ThisFull is an Adam}
	VAR
		Yes: Boolean;
		TryGod: GodHandle;
	procedure AdamError (WhichError: Integer);
		begin
		(* FIXME
			IF thisfull <> NIL THEN
				InvertRect(ThisFull^^.Surround); *)
			Sysbeep(1); {Writeln('Adam Error');}
			Sysbeep(1);
			IF Whicherror = 1 THEN {Writeln('Is Adam but Parent<>NIL') ELSE}
{        Writeln('Not Adam, but Parent=NIL')}
				;
		{	InvertRect(ThisFull^^.Surround) FIXME}
		end; {AdamError}

	procedure CheckAdam (ThisGod: GodHandle);
		begin
			IF ThisGod <> NIL THEN
				begin
					IF ThisGod^^.Adam <> NIL THEN
						begin
							IF ThisGod^^.Adam = ThisFull THEN
								begin
									Yes := TRUE;
									theGod := thisGod
								end
						end;
					IF ThisGod^^.NextGod <> NIL THEN
						CheckAdam(ThisGod^^.NextGod);
				end;
		end; {CheckAdam}

	begin
		Yes := FALSE;
		tryGod := rootGod;
		IF ThisFull <> NIL THEN
			CheckAdam(tryGod);
		{IF Yes AND (ThisFull^^.parent<>NIL) THEN AdamError(1);}
		{IF (NOT Yes) AND (ThisFull^^.Parent=NIL) THEN AdamError(2);}
		IsAnAdam := Yes
	end; {IsAnAdam}


procedure ShowAllAdams (theGod: GodHandle);
	begin
		IF theGod <> NIL THEN
			begin
			(*
				InvertRect(theGod^^.Adam^^.surround); FIXME *)
				IF theGod^^.nextGod <> NIL THEN
					ShowAllAdams(theGod^^.NextGod)
			end
	end;


procedure ShowRelatives (thisFull: FullHandle);
	begin
	(*
		IF thisFull <> NIL THEN
			begin
				IF thisFull^^.parent <> NIL THEN
					FrameRect(ThisFull^^.parent^^.surround);
				IF thisFull^^.ElderSib <> NIL THEN
					FrameRect(ThisFull^^.ElderSib^^.surround);
				IF thisFull^^.YoungerSib <> NIL THEN
					FrameRect(ThisFull^^.YoungerSib^^.surround);
				IF thisFull^^.LastBorn <> NIL THEN
					FrameRect(ThisFull^^.LastBorn^^.surround);
				IF thisFull^^.FirstBorn <> NIL THEN
					FrameRect(ThisFull^^.FirstBorn^^.surround);
			end
			*)
	end;


procedure ShowAllFulls (thisFull: FullHandle);
	begin
		IF thisFull <> NIL THEN
			begin
			(* FIXME
				Framerect(thisFull^^.surround); *)
				ShowRelatives(ThisFull)
			end;
		IF ThisFull^^.Next <> NIL THEN
			ShowAllFulls(ThisFull^^.Next)
	end;


procedure MarkIf (ThisFull: FullHandle);
	begin
		IF IsAnAdam(ThisFull) THEN
			begin
			(* FIXME	FrameInnerRect(ThisFull^^.Surround); *)
			end
	end; {MarkIf}


procedure MarkUp (ThisFull: FullHandle);
	begin
		IF ThisFull <> NIL THEN
			MarkIf(ThisFull);
		IF ThisFull^^.Next <> NIL THEN
			MarkUp(ThisFull^^.Next)
	end; {MarkUp}


procedure ReDevelop (ThisFull: FullHandle);
	VAR
		TempSnap: BitMap;
		{FIXME Animation?}
		{TickValue: LongInt;}
	begin
		IF thisfull <> NIL THEN
			begin
			(* FIXME
				TempSnap.BaseAddr := ThisFull^^.SnapHandle^;
				TempSnap.rowBytes := ThisFull^^.SnapBytes;
				TempSnap.Bounds := ThisFull^^.SnapBounds;
				CopyBits(TempSnap, GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, TempSnap.Bounds, thisFull^^.Surround, srcCopy, NIL);
				MarkIf(ThisFull);
				ThisFull^^.Damaged := FALSE; *)
			end;
	end; {ReDevelop}


procedure CrossOut (ThisFull: FullHandle; Colour: Pattern);
	begin
		IF thisfull <> NIL THEN
			begin
			(* FIXME
				MoveTo(ThisFull^^.Surround.Left, ThisFull^^.Surround.Top);
				PenPat(Colour);
				LineTo(ThisFull^^.Surround.Right, ThisFull^^.Surround.Bottom);
				PenNormal
				*)
			end
			
	end;


procedure SetAllUndamaged (ThisFull: FullHandle);
	var tmpPattern: Pattern;
	begin
		IF ThisFull <> NIL THEN
			begin
				IF ThisFull^^.Damaged THEN
					CrossOut(ThisFull, GetQDGlobalsWhite(tmpPattern)^);
				ThisFull^^.Damaged := FALSE;
			end;
		IF ThisFull^^.Next <> NIL THEN
			SetAllUndamaged(ThisFull^^.Next)
	end; {SetAllUndamaged}


procedure JuniorIntersection (ThisFull, OtherFull: FullHandle);
{Records whether any intersection between This and Other (or Other's juniors),}
{    in truth value of Other^^.Damaged and This^^.Damaged}
	begin
		IF (thisfull <> NIL) AND (OtherFull <> NIL) THEN
			begin
				IF ThisFull <> OtherFull THEN
					begin (*
						IF SectRect(ThisFull^^.Surround, OtherFull^^.Surround, DummyRect) THEN
							begin
								OtherFull^^.Damaged := TRUE;
								ThisFull^^.Damaged := TRUE
							end FIXME *)
					end;
				IF OtherFull^^.Next <> NIL THEN
					JuniorIntersection(ThisFull, OtherFull^^.Next);
			end
	end; {JuniorIntersection}


procedure Coverer (ThisFull: FullHandle);

	begin
		IF (ThisFull <> NIL) AND (ThisFull^^.Next <> NIL) THEN
			JuniorIntersection(ThisFull, ThisFull^^.Next);
{IF ThisFull^^.Next<>NIL THEN Coverer(ThisFull^^.Next)}
	end; {Coverer}


function IsCovered (ThisFull: FullHandle): Boolean;
{Returns TRUE IF Thisfull is covered by any of its own seniors}
	VAR
		Covered: Boolean;
	procedure SeniorIntersection (ThisFull, OtherFull: FullHandle);
{Records whether any intersection between This and Other (or Other's seniors,}
{    in truth value of Other^^.Damaged and This^^.Damaged}
		begin
			IF (thisfull <> NIL) AND (OtherFull <> NIL) THEN
				begin
					IF ThisFull <> OtherFull THEN
						begin
						(*
							IF SectRect(ThisFull^^.Surround, OtherFull^^.Surround, DummyRect) THEN
								Covered := TRUE FIXME *)
						end;
					IF OtherFull^^.Prec <> NIL THEN
						SeniorIntersection(ThisFull, OtherFull^^.Prec);
				end
		end; {SeniorIntersection}
	begin
		IF ThisFull <> NIL THEN
			begin
				IF ThisFull^^.Prec = NIL THEN
					IsCovered := FALSE
				ELSE
					begin
						Covered := FALSE;
						SeniorIntersection(ThisFull, ThisFull^^.Prec);
						IsCovered := Covered;
					end;
			end
	end; {IsCovered}


function OverEdge (ThisFull: FullHandle): Boolean;
	VAR
		Any: Boolean;
		DestRect: Rect;
	begin
	(* FIXME
		Any := SectRect(Prect, ThisFull^^.Surround, DestRect);
    {Don't use Any, interested in DestRect}
		OverEdge := NOT EqualRect(DestRect, ThisFull^^.Surround) *)
	end; {OverEdge}


procedure ReDrawAll (ThisFull: FullHandle);
	begin
	(* FIXME
		IF ThisFull <> NIL THEN
			begin
				MoveTo(ThisFull^^.Centre.h, ThisFull^^.Centre.v);
				IF ThisFull^^.Parent <> NIL THEN
					LineTo(ThisFull^^.Parent^^.Centre.h, ThisFull^^.Parent^^.Centre.v)
			end;
			*)
		IF ThisFull^^.LastBorn <> NIL THEN
			ReDrawAll(ThisFull^^.LastBorn);
		IF ThisFull^^.ElderSib <> NIL THEN
			ReDrawAll(ThisFull^^.ElderSib);
	end; {ReDrawAll}


procedure ReDrawLines (ThisFull: FullHandle);
{Draws line from each box to its parent, if it has one, treating}
{original ThisFull as Adam}
	begin
		IF ThisFull <> NIL THEN
			begin(* FIXME
				MoveTo(ThisFull^^.Centre.h, ThisFull^^.Centre.v);
				IF ThisFull^^.Parent <> NIL THEN
					LineTo(ThisFull^^.Parent^^.Centre.h, ThisFull^^.Parent^^.Centre.v); *)
				IF ThisFull^^.LastBorn <> NIL THEN
					ReDrawAll(ThisFull^^.LastBorn);
			end
	end; {ReDrawLines}


procedure AllLines (theGod: GodHandle);
	begin
		IF theGod <> NIL THEN
			begin
				IF theGod^^.Adam <> NIL THEN
					ReDrawLines(theGod^^.Adam);
				IF theGod^^.nextGod <> NIL THEN
					AllLines(theGod^^.nextGod)
			end
	end; {AllLines}


procedure Connect (NucleusFull, OrbitFull: FullHandle);
	begin
		IF (nucleusfull <> NIL) AND (orbitfull <> NIL) THEN
			begin
			(* FIXME
				MoveTo(NucleusFull^^.Centre.h, NucleusFull^^.Centre.v);
				ThereAreLines := TRUE;
				LineTo(OrbitFull^^.Centre.h, OrbitFull^^.Centre.v);
				*)
			end
	end;


procedure LocalLines (ThisFull: FullHandle);
	procedure ChildLine (Child: FullHandle);
		begin
			Connect(ThisFull, Child);
			IF Child^^.YoungerSib <> NIL THEN
				ChildLine(Child^^.YoungerSib);
		end; {ChildLine}
	begin
		IF ThisFull^^.Parent <> NIL THEN
			Connect(ThisFull, ThisFull^^.Parent);
		IF ThisFull^^.FirstBorn <> NIL THEN
			ChildLine(ThisFull^^.FirstBorn)
	end; {LocalLines}


procedure Incorporate (ThisFull: FullHandle);
{Incorporates it into clip region so not drawn over in future}
	begin
	(* FIXME
		RectRgn(Region2, ThisFull^^.Surround);
		DiffRgn(DestRegion, Region2, DestRegion); {DestRegion now updated to include new box}
		SetClip(DestRegion)
		*)
	end; {Incorporate}


procedure WithdrawProtection (ThisFull: FullHandle);
	begin
	(* FIXME
		RectRgn(Region2, ThisFull^^.Surround); *)
		UnionRgn(DestRegion, Region2, DestRegion); {DestRegion now updated to include new box}
		SetClip(DestRegion)
	end; {WithdrawProtection}


procedure Protect;
	procedure ProtectAll (ThisFull: FullHandle);
		begin
			IF ThisFull <> NIL THEN
				Incorporate(ThisFull);
			IF ThisFull^^.Next <> NIL THEN
				ProtectAll(ThisFull^^.Next)
		end; {ProtectAll}
	begin
		RectRgn(DestRegion, PRect);
		IF SpecialFull <> NIL THEN
			ProtectAll(SpecialFull);
	end; {Protect}


procedure Repair;
	procedure RepairThis (ThisFull: FullHandle);
		begin
			IF ThisFull <> NIL THEN
				begin
					IF ThisFull^^.Damaged THEN
						begin
							Redevelop(ThisFull);
							Incorporate(ThisFull);
							ThisFull^^.Damaged := FALSE
						end;
					IF ThisFull^^.Next <> NIL THEN
						RepairThis(ThisFull^^.Next)
				end
		end; {RepairThis}
	begin
		RectRgn(DestRegion, Prect);
		RepairThis(SpecialFull)
	end; {Repair}


procedure WeedOut (ThisFull: FullHandle);
	VAR
		OnlyChild: Boolean;
	begin {WeedOut}
		IF ThisFull <> NIL THEN
			IF ThisFull^^.Parent <> NIL THEN
				begin
					OnlyChild := (ThisFull^^.YoungerSib = NIL) AND (ThisFull^^.ElderSib = NIL);
					IF OnlyChild THEN
						begin
							ThisFull^^.Parent^^.LastBorn := NIL;
							ThisFull^^.Parent^^.FirstBorn := NIL
						end
					ELSE
						begin {not only child}
							IF ThisFull^^.YoungerSib = NIL THEN
								ThisFull^^.Parent^^.LastBorn := ThisFull^^.ElderSib
							ELSE
								ThisFull^^.YoungerSib^^.ElderSib := ThisFull^^.ElderSib;
							IF ThisFull^^.ElderSib = NIL THEN
								ThisFull^^.Parent^^.FirstBorn := ThisFull^^.YoungerSib
							ELSE
								ThisFull^^.ElderSib^^.YoungerSib := ThisFull^^.YoungerSib;
						end;
				end;
	end; {WeedOut}


procedure WipeOut (ThisFull: FullHandle);
	begin(* FIXME
		DamageRect := ThisFull^^.Surround; *)
		Coverer(ThisFull);
		IF ThisFull = SpecialFull THEN
			begin
				OldSpecialFull := SpecialFull;
				SpecialFull := ThisFull^^.Next;
				ThisFull^^.Prec := NIL;
				{Corrected by RD Dec 1993 to cure Norton-reported bug, bombing when ancestor Killed}
				ThisFull^^.Next := NIL;
			end
		ELSE
			ThisFull^^.Prec^^.Next := ThisFull^^.Next;
		IF ThisFull^^.Next <> NIL THEN
			ThisFull^^.Next^^.Prec := ThisFull^^.Prec;
		EraseRect(DamageRect);
	end; {WipeOut}


procedure KillAll (ThisFull: FullHandle);
{Kill ThisFull and all its elder sibs, including all their descendants}
	VAR
		NextVictim, SecondVictim: FullHandle;
	begin
		IF ThisFull <> NIL THEN
			begin
				NextVictim := ThisFull^^.LastBorn;
				SecondVictim := ThisFull^^.ElderSib;
				WipeOut(ThisFull);
				IF ThisFull = NIL THEN
					SysBeep(1)
				ELSE
					begin
						DisposeHandle(ThisFull^^.SnapHandle);
						DisposeHandle(Handle(ThisFull));
						ThisFull := NIL
					end;
			end;
		IF NextVictim <> NIL THEN
			KillAll(NextVictim);
		IF SecondVictim <> NIL THEN
			KillAll(SecondVictim);
	end; {KillALL}


procedure Kill (ThisFull: FullHandle);
{Kill this one and all its descendants}
	VAR
		NextVictim: FullHandle;
	begin
		IF ThisFull <> NIL THEN
			begin
				NextVictim := ThisFull^^.LastBorn;
				WipeOut(ThisFull);
				IF ThisFull = NIL THEN
					SysBeep(1)
				ELSE
					begin
						DisposeHandle(ThisFull^^.SnapHandle);
						DisposeHandle(Handle(ThisFull));
						ThisFull := NIL
					end;
				IF NextVictim <> NIL THEN
					KillAll(NextVictim);
			end;
	end; {Kill}


procedure DrawWholeLot (ThisFull: FullHandle);
	begin
		IF THisFull <> NIL THEN
			begin
				Redevelop(ThisFull);
				Incorporate(ThisFull);
				IF ThisFull^^.Next <> NIL THEN
					DrawWholeLot(ThisFull^^.Next)
			end
	end; {DrawWholeLot}


procedure Shoot (ThisFull: FullHandle);
	VAR
		YesAdam: Boolean;
	begin
		FindLastGod;
		YesAdam := IsAnAdam(ThisFull); {leaves theGod as ThisFull's god if any}
		IF NOT YesAdam THEN
			begin
				WeedOut(ThisFull);
				Kill(ThisFull)
			end
		ELSE
			begin {only comes here if trying to kill an Adam}
				IF ThisFull^^.parent <> NIL THEN
					Sysbeep(1);
				IF ThisFull^^.LastBorn <> NIL THEN
					begin
						KillAll(ThisFull^^.LastBorn);
						ThisFull^^.FirstBorn := NIL;
						ThisFull^^.LastBorn := NIL;
					end;
				IF ThisFull <> NIL THEN
					begin
						WipeOut(ThisFull);
						DisposeHandle(ThisFull^^.SnapHandle);
						DisposeHandle(Handle(ThisFull));
						ThisFull := NIL
					end;
				IF GodCounter = 3 THEN
					begin
						SetMode(MainWindow, Preliminary);
						Special := nil
					end;
				IF theGod^^.PreviousGod = NIL THEN
					SysBeep(1)
				ELSE
					theGod^^.PreviousGod^^.NextGod := theGod^^.NextGod;
				IF theGod^^.NextGod <> NIL THEN
					theGod^^.NextGod^^.PreviousGod := theGod^^.PreviousGod;
				theGod^^.nextGod := NIL;
				theGod^^.PreviousGod := NIL;
				TheGod^^.Adam := NIL;
				IF theGod = NIL THEN
					SysBeep(1)
				ELSE
					begin
						DisposeHandle(Handle(theGod));
						theGod := NIL
					end

			end;
		{   Protect;}
		{   EraseRect(Prect);}
		{   AllLines(rootGod);}
		{   ClipRect(Prect);}
		{Repair;}
		EraseRect(Prect);
		RectRgn(DestRegion, PRect);
		DrawWholeLot(SpecialFull);
		AllLines(rootGod);
		ClipRect(PRect);
	end; {shoot}


procedure ShootAll (thisGod: GodHandle);
	begin
		IF thisGod <> NIL THEN
			begin
				IF ThisGod^^.Adam <> NIL THEN
					Shoot(ThisGod^^.Adam);
				IF ThisGod^^.NextGod <> NIL THEN
					ShootAll(ThisGod^^.NextGod)
			end
	end; {ShootAll}


procedure Massacre (ThisFull: FullHandle);
	{Normally called with SpecialFull first}
	begin
		IF ThisFull <> NIL THEN
			Shoot(ThisFull);
		IF ThisFull^^.Next <> NIL THEN
			Massacre(ThisFull^^.Next)
	end; {Massacre}

{Isolates ThisFull from all except its descendants, leaving rest of}
{pedigree hierarchical linked list tidied up and pointing elsewhere.}
{Does not touch linear Specialfull linked list, since this reflects}
{spatial relations on screen, and nonrelatives can cover each other}
procedure Detach (ThisFull: FullHandle);

	VAR
		TempGod: GodHandle;
		tmpPattern: Pattern;
	begin
		IF ThisFull^^.Parent <> NIL THEN
			begin
				PenPat(GetQDGlobalsWhite(tmpPattern)^);
				RectRgn(DestRegion, PRect);
				Incorporate(ThisFull);
				Incorporate(ThisFull^^.Parent);
				Connect(ThisFull, ThisFull^^.Parent);
				PenNormal;
				ClipRect(Prect);
				IF ThisFull^^.Parent^^.LastBorn = ThisFull THEN
					ThisFull^^.Parent^^.LastBorn := ThisFull^^.ElderSib;
				IF ThisFull^^.Parent^^.FirstBorn = ThisFull THEN
					ThisFull^^.Parent^^.FirstBorn := ThisFull^^.YoungerSib;
			end; {of whitening line connecting with ThisFull's parent}
		IF ThisFull^^.YoungerSib <> NIL THEN
			ThisFull^^.YoungerSib^^.ElderSib := ThisFull^^.ElderSib;
		IF ThisFull^^.ElderSib <> NIL THEN
			ThisFull^^.ElderSib^^.YoungerSib := ThisFull^^.YoungerSib;
		ThisFull^^.ElderSib := NIL;
		ThisFull^^.YoungerSib := NIL;
		ThisFull^^.Parent := NIL;
		TempGod := GodHandle(NewHandle(SizeOf(God)));
		IF MemError <> noErr THEN
			ExitToShell;
		TempGod^^.NextGod := NIL;
		FindLastGod;
		TempGod^^.PreviousGod := theGod;
		TempGod^^.Adam := ThisFull;
		theGod^^.nextGod := TempGod;
		theGod := TempGod;
		MarkIf(ThisFull);
	end; {Detach}


procedure FollowMouse (ThisFull: FullHandle);
	VAR
		mous, OldMous: Point;
		Height, Width, HalfHeight, HalfWidth, VertOffset, HorizOffset: Integer;
		TickValue: Longint;
		WasOverEdge: Boolean;
		TempSnap: BitMap;
		tmpPattern: Pattern;

	begin(* FIXME
		SetCursor(CursList[WatchCursor]^^);
		TempSnap.BaseAddr := ThisFull^^.SnapHandle^;
		TempSnap.rowBytes := ThisFull^^.SnapBytes;
		TempSnap.Bounds := ThisFull^^.SnapBounds;
		IF ThisFull^^.Prec <> NIL THEN {inverted logic to avoid empty clause - ABC}
			{If Chosen one is not already in front. No change}
			begin {Must bring chosen one to front, after isolating it}
				ThisFull^^.Prec^^.Next := ThisFull^^.Next;
				IF ThisFull^^.Next <> NIL THEN
					ThisFull^^.Next^^.Prec := ThisFull^^.Prec;
				{Chosen one has now been isolated, still called ThisFull}
				ThisFull^^.Next := SpecialFull; {This brings it to front}
				SpecialFull^^.Prec := ThisFull; {This corrects old specialfull's pointer to Prec}
				OldSpecialFull := SpecialFull;
				SpecialFull := ThisFull; {This gives the new specialfull its proper name}
				SpecialFull^^.Prec := NIL;
			end;
		Coverer(ThisFull); {Records all damage done by ThisFull, now also Specialfull}
		Special^.denizen^ := SpecialFull^^.Genome; {FIXME IllMemRef? -ABC}
		WasOverEdge := OverEdge(ThisFull);
		Width := ThisFull^^.Surround.Right - ThisFull^^.Surround.Left;
		Height := ThisFull^^.Surround.Bottom - ThisFull^^.Surround.Top;
		HalfWidth := Width DIV 2;
		HalfHeight := Height DIV 2;
		DamageRect := ThisFull^^.Surround;
		Protect;

		PenPat(GetQDGlobalsWhite(tmpPattern)^);

		LocalLines(ThisFull);
		GetMouse(mous);
		IF thisfull <> NIL THEN
			begin
				HorizOffset := ThisFull^^.Centre.h - mous.h;
				VertOffset := ThisFull^^.Centre.v - mous.v;
				ThisFull^^.Surround.Left := ThisFull^^.Centre.h - HalfWidth;
				ThisFull^^.Surround.Right := ThisFull^^.Surround.Left + width;
				ThisFull^^.Surround.Top := ThisFull^^.Centre.v - Halfheight;
				ThisFull^^.Surround.Bottom := ThisFull^^.Surround.Top + Height;
				ClipRect(PRect);
				EraseRect(ThisFull^^.Surround);
			end;

		CopyBits(GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, GetPortBitMapForCopyBits(MyBitMap)^, Prect, Prect, srcCopy, NIL);
		CopyBits(TempSnap, GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, TempSnap.Bounds, thisFull^^.Surround, srcCopy, NIL); {show chosen one in front}

		{store background}
		PenMode(PatXor); {White is bad because it deletes other lines}

		PenPat(GetQDGlobalsBlack(tmpPattern)^);
		Protect;
		ThereAreLines := FALSE;
		LocalLines(ThisFull);
		HideCursor;
		REPEAT
			OldMous := mous;
			REPEAT
				GetMouse(mous)
			UNTIL PtInRect(mous, PRect);
			ClipRect(ThisFull^^.Surround);

			{Bring on new one}
			SetClip(DestRegion);
			IF (mous.h <> OldMous.h) OR (mous.v <> oldmous.v) OR (NOT Stilldown) THEN
				IF thisfull <> NIL THEN
					begin
						ThatFull^^ := ThisFull^^;
						ClipRect(Prect);
						TickValue := TickCount;
						IF mous.v > 100 THEN
							REPEAT
							UNTIL TickCount <> TickValue;
						{an empirically suggested device for reducing flicker}
						CopyBits(GetPortBitMapForCopyBits(MyBitMap)^, GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, ThisFull^^.Surround, ThisFull^^.Surround, srcCopy, NIL); {Bring back old}
						ThisFull^^.Centre.h := mous.h + HorizOffset;
						ThisFull^^.Centre.v := mous.v + VertOffset;
						ThisFull^^.Surround.Left := ThisFull^^.Centre.h - HalfWidth;
						ThisFull^^.Surround.Right := ThisFull^^.Surround.Left + width;
						ThisFull^^.Surround.Top := ThisFull^^.Centre.v - Halfheight;
						ThisFull^^.Surround.Bottom := ThisFull^^.Surround.Top + Height;
						IF ThereAreLines THEN
							begin
								SetClip(DestRegion);
            {TickValue:=TickCount;}
{            REPEAT UNTIL TickCount<>TickValue;}
								LocalLines(ThatFull); {delete old lines}
							end;
						ClipRect(ThisFull^^.Surround);
        {TickValue:=TickCount;}
{        REPEAT UNTIL TickCount<>TickValue;}
						CopyBits(TempSnap, GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, TempSnap.Bounds, thisFull^^.Surround, srcCopy, NIL);
                {Bring on new one}
						IF ThereAreLines THEN
							begin
								Protect;
            {TickValue:=TickCount;}
{            REPEAT UNTIL TickCount<>TickValue;}
								LocalLines(ThisFull)
							end
					end
		UNTIL NOT StillDown;
		ShowCursor;
		SetCursor(CursList[HandCursor]^^);
		PenNormal;
		ClipRect(Prect);
		ThisFull^^.Origin.h := mous.h + HorizOffset;
		ThisFull^^.Origin.v := mous.v + VertOffset;
		ThisFull^^.Damaged := TRUE;{WasOverEdge}
		ClipRect(Prect);
		Repair;
		Protect;
		AllLines(rootGod);
		ClipRect(Prect);
		*)
	end; {FollowMouse}


procedure DrawAll;

	procedure DrawSibs (ThisFull: Fullhandle);
		begin
			IF ThisFull <> NIL THEN
				ThisFull^^.Damaged := TRUE;
			IF ThisFull^^.FirstBorn <> NIL THEN
				DrawSibs(ThisFull^^.FirstBorn);
			IF ThisFull^^.YoungerSib <> NIL THEN
				DrawSibs(ThisFull^^.YoungerSib);
		end; {DrawSibs}

	procedure Draw (ThisFull: FullHandle);
		begin
			ThisFull^^.Damaged := TRUE;
			IF ThisFull^^.FirstBorn <> NIL THEN
				begin
					Draw(ThisFull^^.FirstBorn);
					IF ThisFull^^.FirstBorn <> NIL THEN
						DrawSibs(ThisFull^^.FirstBorn^^.YoungerSib)
				end
		end; {Draw}

	begin
		ClipRect(Prect);
		EraseRect(Prect);
		Draw(theGod^^.Adam);
		Protect;
		AllLines(theGod);
		ClipRect(Prect);
	end; {DrawAll}


procedure SpawnOne (ThisFull: FullHandle; Here: HIPoint; VAR current: FullHandle);
	VAR
		TempSnap: BitMap;
		Width, WidthBytes, Height, voffset: Integer;
		SizeNeeded: LongInt;
	begin
	(* FIXME
		SetCursor(Curslist[WatchCursor]^^);
		Current := Created;
		Reproduce(@ThisFull^^.Genome, @Current^^.Genome);
		Current^^.Origin := here;
		ZeroMargin := TRUE;
		DelayedDrawing := TRUE;
		Develop(@Current^^.Genome, here);
		Current^^.Surround := Margin;
		AtLeast(Current^^.Surround);
		WITH Current^^.Surround DO
			begin
				height := bottom - top;
				WidthBytes := (right - left) DIV 8;
				WHILE odd(WidthBytes) DO
					WidthBytes := WidthBytes + 1;
				Width := WidthBytes * 8;
				voffset := 0;
				IF top < PRect.top THEN
					begin
						voffset := Prect.top - top;
						top := Prect.top;
						bottom := top + height;
					end;
				IF bottom > PRect.bottom THEN
					begin
						voffset := Prect.bottom - bottom;
						bottom := Prect.bottom;
						top := bottom - height
					end;
				IF left < PRect.left THEN
					begin
						left := PRect.left;
						right := left + width
					end;
				IF right > Prect.right THEN
					begin
						right := Prect.right;
						left := right - width
					end;
			end;
		EraseRect(Current^^.Surround);
		Framerect(Current^^.Surround);
		WITH Current^^.Surround DO
			begin
				Current^^.Centre.h := Left + (Right - Left) DIV 2;
				Current^^.Centre.v := Top + (Bottom - Top) DIV 2
			end;
		Here.x := current^^.origin.v + voffset;
		Here.y := Current^^.Centre.h;
		DrawPic(MyPic, here, Current^^.Genome);
		WITH Current^^.SnapBounds DO
			begin
				left := 0;
				right := Current^^.Surround.right - Current^^.Surround.left;
				top := 0;
				bottom := height
			end;
		TempSnap.Bounds := Current^^.SnapBounds;
		Current^^.SnapBytes := WidthBytes;
		SizeNeeded := LONGINT(WidthBytes * Height);
		Current^^.SnapHandle := NewHandle(SizeNeeded);
		IF MemError <> noErr THEN
			ExitToShell;
		TempSnap.BaseAddr := Current^^.SnapHandle^;
		TempSnap.rowBytes := Current^^.SnapBytes;
		CopyBits(GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, TempSnap, Current^^.Surround, TempSnap.Bounds, srcCopy, NIL);
		Current^^.Parent := ThisFull;
		Current^^.ElderSib := ThisFull^^.LastBorn;
		IF Current^^.ElderSib <> NIL THEN
			Current^^.ElderSib^^.YoungerSib := Current;
		Current^^.LastBorn := NIL;
		Current^^.YoungerSib := NIL;
		IF ThisFull^^.LastBorn = NIL THEN
			ThisFull^^.FirstBorn := Current;
		ThisFull^^.LastBorn := Current;
		Current^^.Next := SpecialFull; {Puts Currentfull at head of list}
		SpecialFull^^.Prec := Current;  {Updates seniority pointer of previous head}
		OldSpecialFull := SpecialFull;
		SpecialFull := Current;  {Gives new head its proper title}
		SpecialFull^^.Prec := NIL; {Probably unnecessary but good form}
		Child[Special] := Current^^.Genome;
		MarkIf(Current);
		*)
	end; {SpawnOne}


procedure Radiate (From, Goal: Point; Spokes: Integer; VAR Here: PointArray);
	VAR
		dx, dy, j: Integer;
	begin
		dx := Goal.h - From.h;
		dy := Goal.v - From.v;
		Here[1].h := From.h + dx;
		Here[1].v := From.v + dy;
		Here[2].h := From.h - dx;
		Here[2].v := From.v - dy;
		Here[3].h := From.h - dy;
		Here[3].v := From.v + dx;
		Here[4].h := From.h + dy;
		Here[4].v := From.v - dx;
		FOR j := 1 TO Spokes DO
			begin
				MoveTo(From.h, From.v);
				LineTo(Here[j].h, Here[j].v)
			end;
	end; {Radiate}


procedure DrawOutFrom (ThisFull: FullHandle);
	VAR
		mous: Point;
		tickValue: LongInt;
		Current: FullHandle;
		OldMous: Point;
		Here: PointArray;
		j: Integer;
	begin
	(*
		SetCursor(CursList[CrossCursor]^^);
		ClipRect(Prect);
		IF IsCovered(ThisFull) THEN
			Redevelop(ThisFull);
		IF ThisFull^^.Prec = NIL THEN
		{Chosen one is already in front. No change}
		ELSE
			begin {Must bring chosen one to front, after isolating it}
				ThisFull^^.Prec^^.Next := ThisFull^^.Next;
				IF ThisFull^^.Next <> NIL THEN
					ThisFull^^.Next^^.Prec := ThisFull^^.Prec;
				{Chosen one has now been isolated, still called ThisFull}
				ThisFull^^.Next := SpecialFull; {This brings it to front}
				SpecialFull^^.Prec := ThisFull; {This corrects old specialfull's pointer to Prec}
				OldSpecialFull := SpecialFull;
				SpecialFull := ThisFull; {This gives the new specialfull its proper name}
				SpecialFull^^.Prec := NIL;
			end;
		GetClip(SaveRegion);
		RectRgn(DestRegion, PRect);
		Protect;
		PenMode(PatXor);
		OwnCursor(SpecialFull^^.Surround, GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, theCursor);
		SetCursor(theCursor);
		REPEAT
			GetMouse(mous);
			{FIXME Restore original animation? - ABC}
			{TickValue:=TickCount;}
			{REPEAT UNTIL TickValue<>TickCount;}
			{Framerect(ThisFull^^.Surround);}
			{Just for flicker}
		UNTIL (NOT StillDown) OR (NOT PtInRect(mous, ThisFull^^.Surround));
		PenNormal;
		Framerect(ThisFull^^.Surround);
		MarkIf(ThisFull);
		Special^.denizen^ := Thisfull^^.Genome;
		IF StillDown THEN
			begin
				SetClip(DestRegion);
				PenMode(PatXor);
				Radiate(ThisFull^^.Centre, mous, Rays, Here);
				WHILE StillDown DO
					begin
						OldMous := Mous;
						GetMouse(mous);
						IF mous.v < Prect.top THEN
							mous.v := Prect.top;
						IF ((mous.h <> OldMous.h) OR (mous.v <> OldMous.v)) THEN {(NOT PtInRect(mous,ThisFull^^.Surround)) AND}
							begin
								TickValue := TickCount;
								REPEAT
								UNTIL TickValue <> TickCount;
								Radiate(ThisFull^^.Centre, OldMous, Rays, Here);
								TickValue := TickCount;
								REPEAT
								UNTIL TickValue <> TickCount;
								IF NOT PtInRect(mous, ThisFull^^.Surround) THEN
									Radiate(ThisFull^^.Centre, Mous, Rays, Here)
							end;
					end; {WHILE loop}
				{Button just released}
				{SetCursor(CursList[WatchCursor]^^);}
				Radiate(ThisFull^^.Centre, Mous, Rays, Here);
				PenNormal;
				j := Rays;
				ClipRect(Prect);
				IF NOT PtInRect(mous, ThisFull^^.Surround) THEN
					WHILE j >= 1 DO
						begin
							theCursor.data := curslist[randcursor]^^.mask;
							theCursor.data[8] := 128;  {make up dot cursor}
							theCursor.mask := theCursor.data;
							SetCursor(theCursor);
							SpawnOne(Thisfull, Here[j], Current);
							j := j - 1
						end
				ELSE
			end;
		Protect;
		LocalLines(ThisFull);
		{EraseRect(Prect);}
		{AllLines(rootGod);}
		ClipRect(Prect);
		{REPEAT  GetMouse(mous)}
		{    UNTIL NOT PtInRect(mous,Current^^.Surround);}
		{  The purpose of this is to keep}
		{    the dot cursor going until we have left the new box.  But it bombs if you move back}
		{    to the old box before drawing has finished}
		SetCursor(CursList[DrawOutCursor]^^);
		*)
	end; {DrawOutFrom}


procedure PhylogNew (Biomorph: PersonPtr);
	VAR
		tempGod: GodHandle;
		TempSnap: BitMap;
		Width, Height, SizeNeeded: LongInt;
		screenBounds: Rect;
	begin
		EraseRect(Prect);
		TempGod := GodHandle(NewHandle(SizeOf(God)));
		IF MemError <> noErr THEN
			ExitToShell;
		TempGod^^.NextGod := NIL;
		FindLastGod;
		TempGod^^.PreviousGod := theGod;
		theGod^^.nextGod := TempGod;
		theGod := TempGod;
		theGod^^.Adam := Created;
		theGod^^.Adam^^.Genome := Biomorph^;
		GetPortBounds(GetQDGLobalsThePort, screenBounds);
		(* FIXME
		WITH screenBounds DO
			begin
				theGod^^.Adam^^.Origin.h := (Right - Left) DIV 2;
				theGod^^.Adam^^.Origin.v := (Bottom - Top) DIV 2
			end;
		delayvelop(theGod^^.Adam^^.Genome, theGod^^.Adam^^.Origin);
		theGod^^.Adam^^.Surround := Margin;
		AtLeast(theGod^^.Adam^^.Surround);
		FrameRect(theGod^^.Adam^^.Surround);
		FrameInnerRect(theGod^^.Adam^^.Surround);
		WITH theGod^^.Adam^^.Surround DO
			begin
				height := bottom - top;
				Width := (right - left) DIV 8;
				WHILE odd(Width) DO
					begin {Write('Odd Width');}
						Width := Width + 1;
					end
			end;
		WITH theGod^^.Adam^^.SnapBounds DO
			begin
				left := 0;
				right := theGod^^.Adam^^.Surround.right - theGod^^.Adam^^.Surround.left;
				top := 0;
				bottom := height
			end;
		TempSnap.Bounds := theGod^^.Adam^^.SnapBounds;
		theGod^^.Adam^^.SnapBytes := Width;
		SizeNeeded := LONGINT(Width * Height);
		theGod^^.Adam^^.SnapHandle := NewHandle(SizeNeeded);
		IF MemError <> noErr THEN
			ExitToShell;
		TempSnap.BaseAddr := theGod^^.Adam^^.SnapHandle^;
		TempSnap.rowBytes := theGod^^.Adam^^.SnapBytes;
		CopyBits(GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, TempSnap, theGod^^.Adam^^.Surround, TempSnap.Bounds, srcCopy, NIL);
		WITH theGod^^.Adam^^ DO
			begin
				Centre.h := Surround.Left + (Surround.Right - Surround.Left) DIV 2;
				Centre.v := Surround.Top + (Surround.Bottom - Surround.Top) DIV 2
			end;
		*)
		OldSpecialFull := SpecialFull;
		IF SpecialFull <> NIL THEN
			SpecialFull^^.Prec := theGod^^.Adam;
    													{This corrects old specialfull's pointer to Prec}
		theGod^^.Adam^^.next := SpecialFull;
		SpecialFull := theGod^^.Adam;
		SpecialFull^^.Prec := NIL;
		SetMode(MainWindow, Phyloging);								{Changed July 1990}
		EraseRect(Prect);
		RectRgn(DestRegion, PRect);
		DrawWholeLot(SpecialFull);
		AllLines(rootGod);
		ClipRect(PRect);
	end; 				{PhylogNew}

end.