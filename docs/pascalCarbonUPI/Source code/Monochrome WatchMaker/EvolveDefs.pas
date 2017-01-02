unit EvolveDefs;

interface

uses
	AnimationTypes,
	CursorDefs,
	MacOSAll, 
	PersonSerialization, 
	Globals,  
	SlideDefs, 
	BoxesDefs, 
	ModeDefs,
	ErrorUnit, 
	DevelopDefs,
	ReproduceDef,
	HIBoxTypes;

	procedure DoHighlight;
	procedure DoBreed(boxes: HIBoxesPtr);

	procedure Evolve (proudParentBox: HIBoxPtr);


implementation

{ Breed a HIBoxes full of Biomorphs from whatever HIBox is passed in. }
{ The parent biomorph may or may not be on the same breeding field as }
{ the target. If it is on the same breeding field, it is added to the }
{ animation queue first, so that it will be seen to slide to the center}
{ before breeding commences. The biomorph is copied to the MidBox, and}
{ reproduced from there to the rest of the boxes in the field. Each box }
{ is added to the animation queue with an animation state of Start. }
procedure Evolve (proudParentBox: HIBoxPtr);
	VAR
		j: Integer;
		bytesToSave: LongInt;
		tmpPat: Pattern;
		actualCount: ByteCount;
		perSerializer: PersonSerializer;
		theBox: HIBoxPtr;
		boxIndex: HIBoxPtr;
		boxes: HIBoxesPtr;

	procedure GrowChild (boxToGrowChildIn: HIBoxPtr);
		VAR
			
			k: LONGINT;
		begin
{FIXME SPARK LINES}		
(*			
		
			Cliprect(Prect);
			PenMode(PatXor);
			MoveTo(trunc(boxes^.midbox^.MidPoint.x), trunc(boxes^.midbox^.MidPoint.y));
			LineTo(trunc(theBox^.MidPoint.x), trunc(theBox^.MidPoint.y));
			HiWindowFlush(MainWindow);
			*)
			(*
			k := TickCount;
			REPEAT
			UNTIL TickCount >= k + TickDelay; 
			MoveTo(trunc(boxes^.midbox^.MidPoint.x), trunc(boxes^.midbox^.MidPoint.y));
			LineTo(trunc(theBox^.MidPoint.x), trunc(theBox^.MidPoint.y));
			HiWindowFlush(MainWindow);
			PenMode(PatCopy);
			*)
			reproduce(theBox^.parent^.midbox^.denizen, theBox^.denizen);
			{HiWindowFlush(MainWindow);}
			{ClipRect(Box[j]);}
			{delayvelop(theBox, theBox^.MidPoint);}
			{HiWindowFlush(MainWindow);}
		end;

	begin
		(* 
			The cursor is obscured. All boxes in the same collection 
			as the supplied box are erased. The frame around the 
			special box is erased. The special box has it state set to 
		*)
		IF proudParentBox <> nil THEN
			begin
			
				ObscureCursor;
				boxIndex := proudParentBox^.parent^.first;
				while boxIndex <> nil do
					begin
						if boxIndex <> special then
							begin
								boxIndex^.animationState := HIBoxStateErase;
								HIViewSetNeedsDisplay(boxIndex^.view, true);
							end;
						boxIndex := boxIndex^.next;
					end;

				{PenPat(GetQDGlobalsWhite(tmpPat)^);}
				{Framerect(box[special]);} {FIXME}
				{HiWindowFlush(MainWindow);}
				{PenPat(GetQDGlobalsBlack(tmpPat)^);}
				{special^.slidingDest := boxes^.MidBox^.bounds;}
				{HiWindowFlush(MainWindow);}
				
				boxes := proudParentBox^.parent;
				{Careful to copy by value, not by pointer}
				boxes^.midbox^.denizen^ := proudParentBox^.denizen^;
				boxIndex := boxes^.first;
				while boxIndex <> nil do
					begin
						if boxIndex <> boxes^.midbox then
							Growchild(boxIndex);
						boxIndex := boxIndex^.next;
					end;
			end;
{		ClipRect(Prect); }
{		special := boxes^.MidBox; }
		IF fossilizing THEN
			begin
				bytesToSave := SizeOf(PersonSerializer);
				SetPersonSerializerFromPerson(@perSerializer, boxes^.MidBox^.denizen);
				FSWriteFork(slides, fsAtMark, 0, bytesToSave, @perSerializer, @actualCount);
				FossilsToSave := TRUE
			end;{****}
		
	end; {evolve}


procedure DoBreed(boxes: HIBoxesPtr);
	VAR
		p: HIPoint;
		{tmpRect: Rect;}

	begin
		SetMode(MainWindow, breeding);
		OldBox := special;
		{EraseRect(PRect);}{FIXME}
		OldSpecial := nil;
		SetCursor(CursList[watchCursor]^^);
		boxes^.MidBox^.denizen := special^.denizen;
		Special := boxes^.MidBox;
		{MakeGeneBox(Special^.denizen, MainWindow);}  {FIXME}
		Delayvelop(special, special^.midpoint);
		p := boxes^.midbox^.midpoint;
		p.y := boxes^.midbox^.bounds.size.height - 1;
		Evolve(boxes^.MidBox);
		{Delayvelop(special, p);}
		{ClipRect(PRect);}
	end; {DoBreed}

procedure DoHighlight;
	VAR
		Ticking: Boolean;
		TickValue: LongInt;
	begin
		Ticking := FALSE;
		IF (TheMode = Phyloging) OR (TheMode = Moving) OR (TheMode = Detaching) OR (TheMode = Killing) THEN {OR (theMode=Noahing)}
			begin
{				InvertRect(SpecialFull^^.Surround); FIXME}
				TickValue := TickCount;
				Ticking := TRUE;
			end;
		IF (theMode = breeding) OR (TheMode = Drifting) THEN
			begin
				OldSpecial := Special;
				Special^.Highlighted := true; {FIXME trigger redraw}
				SetMode(MainWindow, Highlighting);
				{MakeGeneBox(Special^.denizen, MainWindow); FIXME}
			end;
		IF theMode = Albuming THEN
			OldSpecial := Special;
		IF Ticking THEN
			begin
				REPEAT
				UNTIL TickCount - TickValue >= 30;
			{	InvertRect(SpecialFull^^.Surround); FIXME}
			end
	end; {DoHighlighting}
end.