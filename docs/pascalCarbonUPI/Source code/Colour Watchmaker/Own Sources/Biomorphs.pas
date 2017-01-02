unit Biomorphs;

interface
	uses
{Palettes, }
		PCommonExhibition, Common_Exhibition, Utils_Exhibition, PA_Warning_Alert, BiomorphsBoxes, BiomorphsDevelop, BiomorphsDraw, MacOSAll;


	procedure DrawAllBoxes;
	procedure Evolve (MLoc: point);
	procedure DoBreed;
	procedure BasicTree (var genotype: person);
	procedure RandomForeColour (var theBiomorph: person);
	procedure RandomBackColour (var theBiomorph: person);
	procedure DeliverSaltation (var theBiomorph: Person);
	procedure DoSaltation(theWindow: WindowRef);
	procedure Suzy;
	procedure myDoEvent (theWindow: WindowRef; loc: point);
	procedure myUpdate (theWindow: WindowRef);
	procedure myOpen (theWindow: WindowRef);
	procedure myApplLoop(theWindow: WindowRef);
	procedure DoMouseMoved(theEvent: eventRef);

	var
		oldSpecial: integer;
		dullRgn, midRgn: rgnHandle;
		
implementation



	procedure RimAdjust;
		var
			tickValue: LongInt;
			tempPattern: Pattern;
	begin
		tickValue := TickCount;
		repeat
		until TickCount - TickValue > DelayTicks;
		PenMode(PatXor);
		PenPat(GetQDGlobalsBlack(tempPattern)^);
		FrameRect(Box[special]);
		PenMode(PatCopy);
		HIWindowFlush(BreedWin);
	end; {RimAdjust}

	procedure DrawAllBoxes;
		var
			J: INTEGER;
	begin
		for j := 1 to MidBox do{-1}
			begin
				ClipRect(Box[j]);
				if not AbortFlag then
					begin
						RGBBackcolor(chooseColor(child[j].BackColorGene));
						EraseRect(box[j]);
						FrameRect(box[j]);
						delayvelop(Child[j], Centre[j]);
						BackColor(whiteColor);
					end
			end;
		PenSize(3, 3);
		Framerect(box[MidBox]);
		PenSize(1, 1);
		for j := MidBox + 1 to NBoxes do
			begin
				ClipRect(Box[j]);
				if not AbortFlag then
					begin
						RGBBackcolor(chooseColor(child[j].BackColorGene));
						EraseRect(box[j]);
						FrameRect(box[j]);
						delayvelop(Child[j], Centre[j]);
						BackColor(whiteColor);
					end
			end;
		ClipRect(PRect);
		if theMode = Highlighting then
			RimAdjust;
		HiWindowFlush(BreedWin);
	end; {DrawAllBoxes}


	procedure evolve (MLoc: point);
		var
			j: INTEGER;
			tempPattern: Pattern;

		{This is where the cool "spray lines" are created.}
		procedure GrowChild (j: INTEGER);
			var
				k: LONGINT;
		begin
			Cliprect(Prect);
			PenMode(PatXor);
			Pensize(3, 3);
			MoveTo(Centre[Midbox].h, Centre[Midbox].v);
			LineTo(Centre[j].h, Centre[j].v);
			HiWindowFlush(BreedWin);
			k := TickCount;
			repeat
			until TickCount >= k + DelayTicks;
			MoveTo(Centre[Midbox].h, Centre[Midbox].v);
			LineTo(Centre[j].h, Centre[j].v);
			HiWindowFlush(BreedWin);
			pensize(1, 1);
			PenMode(PatCopy);
			reproduce(child[MidBox], child[j]);
			HiWindowFlush(BreedWin);
			ClipRect(Box[j]);
			if not AbortFlag then
				begin
					RGBBackcolor(chooseColor(child[j].BackColorGene));
					EraseRect(box[j]);
					FrameRect(box[j]);
					delayvelop(Child[j], Centre[j]);
					BackColor(whiteColor);
				end;
			HiWindowFlush(BreedWin);
			
		end;

	begin
		SetCursor(Watch^^);
		GlobalToLocal(Mloc);
		j := 0;
		repeat
			j := j + 1
		until (PtInRect(Mloc, box[j])) or (j > NBoxes);
		if j <= NBoxes then
			special := j
		else
			special := 0;
		if special > 0 then
			begin
				ObscureCursor;
				RGBBackColor(choosecolor(child[midbox].backcolorgene));
				for j := 1 to NBoxes do
					if j <> special then
						EraseRect(box[j]);
				PenPat(GetQDGlobalsWhite(tempPattern)^);
				Framerect(box[special]);
				PenPat(GetQDGlobalsBlack(tempPattern)^);
				Slide(box[special], box[MidBox]);
				
				child[MidBox] := child[special];
				SetUpBoxes(TRUE);
				for j := 1 to MidBox - 1 do
					growchild(j);
				PenSize(3, 3);
				Framerect(box[MidBox]);
				PenSize(1, 1);
				for j := MidBox + 1 to NBoxes do
					growchild(j);
			end;
		ClipRect(Prect);
		special := MidBox;
		SetCursor(Hand^^);
	end; {evolve}


	procedure DoBreed;
		var
			p: point;
			oldMode: mode;
	begin
		oldMode := theMode;
		TheMode := breeding;
		OldBox := special;
		EraseRect(BusinessPart);
		SetUpBoxes(TRUE);
		special := MidBox;
		OldSpecial := 0;
		Child[MidBox] := child[special];
		Special := MidBox;
		RGBBackColor(chooseColor(Child[special].BackColorGene));
		EraseRect(Box[MidBox]);
		HIWindowFlush(BreedWin);
		Delayvelop(Child[Special], Centre[MidBox]);
		HIWindowFlush(BreedWin);
		p := centre[MidBox];
		p.v := box[MidBox].bottom - 1;
		Evolve(p);
		HIWindowFlush(BreedWin);
{showControl(Ctrl_CLICK_HERE_TO_R);}
		ClipRect(PRect);
		ResetClock;
		HIWindowFlush(BreedWin);
	end; {DoBreed}


	procedure makegenes (var genotype: person; a, b, c, d, e, f, g, h, i: integer);
		var
			j: INTEGER;
	begin
		with genotype do
			begin
				for j := 1 to 10 do
					dgene[j] := same;
				SegNoGene := 1;
				SegDistGene := 1;
				CompletenessGene := Double;
				SpokesGene := NorthOnly;
				TrickleGene := Trickle;
				MutSizeGene := Trickle div 2;
				MutProbGene := 10;
				gene[1] := a;
				gene[2] := b;
				gene[3] := c;
				gene[4] := d;
				gene[5] := e;
				gene[6] := f;
				gene[7] := g;
				gene[8] := h;
				gene[9] := i;
			end;
	end; {makegenes}

	procedure chess (var genotype: person);
		var
			j: INTEGER;
	begin
		makegenes(genotype, -trickle, 3 * trickle, -3 * trickle, -3 * trickle, trickle, -2 * trickle, 6 * trickle, -5 * trickle, 7);
		with genotype do
			begin
				for j := 1 to 8 do
					colorgene[j] := rainbow div 2;
				backcolorGene := rainbow div 3;
				LimbShapeGene := Stick;
				LimbFillGene := Filled;
				ThicknessGene := 1;
			end;
	end; {chess}

	procedure BasicTree (var genotype: person);
		var
			j: INTEGER;
	begin
		makegenes(genotype, -trickle, -trickle, -trickle, -trickle, -trickle, 0, trickle, trickle, 6);
		with GENOTYPE do
			begin
				for j := 1 to 8 do
					for j := 1 to 8 do
						colorgene[j] := rainbow div 2;
				backcolorGene := rainbow div 3;
				LimbShapeGene := Stick;
				LimbFillGene := Filled;
				ThicknessGene := 1;
			end;
	end; {root}

	procedure insect (var genotype: person);
		var
			j: INTEGER;

	begin
		makegenes(genotype, trickle, trickle, -4 * trickle, trickle, -trickle, -2 * trickle, 8 * trickle, -4 * trickle, 6);
		with genotype do
			begin
				for j := 1 to 8 do
					for j := 1 to 8 do
						colorgene[j] := rainbow div 2;
				backcolorGene := rainbow div 3;
				LimbShapeGene := Stick;
				LimbFillGene := Filled;
				ThicknessGene := 1;
			end;
	end; {insect}

	procedure RandomForeColour (var theBiomorph: person);
		var
			RainbowSize: LongInt;
			j: integer;
	begin
		RainbowSize := Rainbow;
		for j := 1 to 8 do
			theBiomorph.ColorGene[j] := (randint(RainbowSize) << 16) + randint(RainbowSize);
	end; {RandomForeColour}

	procedure RandomBackColour (var theBiomorph: person);
	begin
		theBiomorph.BackColorGene := randint(Rainbow);
	end; {RandomBackColour}


	procedure DeliverSaltation (var thebiomorph: person);
		var
			j, maxgene, r: INTEGER;
			factor: -1..1;

	begin
		DelayedDrawing := FALSE;
		special := MidBox;
		with theBiomorph do {bomb 5, range check failed, here after killing top Adam}
			begin
				if Mut[1] then
					begin
						SegNoGene := randint(6);
						SegDistGene := randint(20);
					end
				else
					begin
						SegNoGene := 1;
						SegDistGene := 1
					end;
				r := randint(100);
				CompletenessGene := Double;
				if Mut[3] then
					if r < 50 then
						CompletenessGene := Single
					else
						CompletenessGene := Double;
				r := randint(100);
				if Mut[4] then
					begin
						if r < 33 then
							SpokesGene := Radial
						else if r < 66 then
							SpokesGene := NSouth
						else
							SpokesGene := NorthOnly
					end
				else
					SpokesGene := NorthOnly;
				if Mut[5] then
					begin
						TrickleGene := 1 + randint(100) div 10;
						if TrickleGene > 1 then
							MutSizeGene := Tricklegene div 2
					end;
				if Mut[10] then
					RandomForeColour(theBiomorph);
				if Mut[8] then
					LimbShapeGene := RandLimbType;
				if Mut[9] then
					LimbFillGene := RandLimbFill;
				if Mut[11] then
					RandomBackColour(theBiomorph);
				if Mut[12] then
					ThicknessGene := RandInt(3);
				for j := 1 to 8 do
					repeat
						if Mut[13] then
							gene[j] := MutSizeGene * (randint(19) - 10);
						if Mut[2] then
							dGene[j] := RandSwell(dgene[j])
						else
							dGene[j] := Same;
						case dGene[j] of
							Shrink: 
								factor := 1;
							Same: 
								factor := 0;
							Swell: 
								factor := 1;
						end; {Cases}
						maxgene := gene[j] * SegNoGene * factor;
					until (maxgene <= 9 * Tricklegene) and (maxgene >= -9 * Tricklegene);
				repeat
					if Mut[2] then
						dGene[10] := RandSwell(dgene[10])
					else
						dGene[10] := Same;
					case dGene[j] of
						Shrink: 
							factor := 1;
						Same: 
							factor := 0;
						Swell: 
							factor := 1;
					end; {Cases}
					maxgene := SegDistGene * SegNoGene * factor;
				until (maxgene <= 100) and (maxgene >= -100);
				repeat
					Gene[9] := randint(6)
				until Gene[9] > 1;
				dGene[9] := Same;
			end;
		RGBBackColor(chooseColor(theBiomorph.BackColorGene));
	end;
 {DeliverSaltation}

	procedure DoSaltation(theWindow: WindowRef);
	begin
		if special = 0 then
			special := midbox;
		DeliverSaltation(child[special]);
		TheMode := Randoming;
		InValWindowRect(theWindow, box[midbox]);
		ResetClock;
		HiWindowFlush(theWindow);
	end; {DoSaltation}


	procedure Suzy;
	begin
		Message('You are using the mouse incorrectly.  HOLD your finger down on, say, "Operation", then "pull" to the option you want, then let go.  Before trying again you must click OK to remove this message');
	end; {suzy}

	procedure myUpdate (theWindow: WindowRef);
	begin
		if theMode = breeding then
			begin
				DrawAllBoxes;
				ClipRect(Prect);
			end;
		if theMode = randoming then
			begin
				ClipRect(Prect);
{showControl(Ctrl_CLICK_HERE_TO_R);}
{DrawControls(theWindow);}
				SetClip(dullRgn);
				BackColor(WhiteColor);
				EraseRect(BusinessPart);
				RGBBackColor(chooseColor(child[special].backColorGene));
				SetClip(MidRgn);
				EraseRect(box[midbox]);
				PenSize(2, 2);
				FrameRect(box[midbox]);
				Develop(child[special], centre[midBox]);
				BackColor(WhiteColor);
			end;
		ResetClock;
		HiWindowFlush(theWindow);
	end; {myUpdate}

	procedure myOpen (theWindow: WindowRef);
	begin
		GetWindowPortBounds(theWindow, PRect);
		Prect.bottom := Prect.bottom - 20;
		setUpBoxes(true);
		ClipRect(Prect);
		BackColor(whiteColor);
		EraseRect(Prect);
		FrameRect(Prect);
		RectRgn(dullRgn, BusinessPart);
		RectRgn(midRgn, box[MidBox]);
		DiffRgn(dullRgn, midRgn, dullRgn);
		doSaltation(theWindow);
		HiWindowFlush(theWindow);
	end; {myOpen}

	procedure myDoEvent (theWindow: WindowRef; loc: point);
	begin     																{Handle U_DoEvent_}
{***DEAL WITH CHOICES OF BIOMORPHS HERE}
		if theMode = Breeding then
			evolve(loc);
		if theMode = Randoming then
			begin
				globalToLocal(loc);
				if (PtInRect(loc, box[midbox])) then
					begin
						theMode := breeding;
						flushevents(everyEvent, nullEvent);
						evolve(centre[midbox]);
					end
				else if loc.v > box[1].top then
					DoSaltation(theWindow);
			end;
		ResetClock;
		flushevents(everyEvent, nullEvent);
		HiWindowFlush(theWindow);
	end; {myDoEvent}
 																	{end of Handle U_DoEvent_}
	procedure DoMouseMoved(theEvent: eventRef);
	var
		MLoc: point;
		theHIPoint: HIPoint;
	begin
		if theEvent = nil then
			GetMouse(MLoc)
		else
			begin
				GetEventParameter (theEvent, kEventParamWindowMouseLocation, typeHIPoint, nil, sizeof (HIPoint), nil, @theHIPoint);
				HIPointConvert(theHIPoint, kHICoordSpaceScreenPixel, nil, kHICoordSpaceWindow, BreedWin);
				Mloc.h := trunc(theHiPoint.x);
				Mloc.v := trunc(theHiPoint.y)
			end;
			
		if not PtInRect(MLoc, Prect) then
			InitCursor
		else
			begin
				case theMode of
					breeding: 
						if MLoc.v > box[1].top then
							SetCursor(hand^^)
						else
							InitCursor;
					randoming: 
						if PtInRect(MLoc, box[midbox]) then
							SetCursor(Hand^^)
						else if MLoc.v < PRect.top then
							InitCursor
						else if MLoc.v < box[1].top then
							SetCursor(hand^^)
						else
							SetCursor(dice^^);
				end;
			end;
	end;
	
	procedure myApplLoop(theWindow: WindowRef);

	begin
		GetDateTime(theTime);
		if (theMode = Breeding) and YesRevert then
			begin
				if (theTime - TimeOfEvent) > MonsterRevert then
					begin
						BackColor(WhiteColor);
						EraseRect(PRect);
						DoSaltation(theWindow);
					end;
			end;
		if (theMode = Randoming) and YesRhythm then
			begin
				if (theTime - TimeOfEvent) > MonsterInterval then
					DoSaltation(theWindow);
			end;
		DoMouseMoved(nil);

	end;
end.