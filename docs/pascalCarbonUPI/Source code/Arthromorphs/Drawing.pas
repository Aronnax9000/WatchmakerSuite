unit Drawing;

interface
uses MacOSAll, MyGlobals, Ted, Boxes;

procedure UpdateAnimals;
	procedure evolve (MLoc: point);
procedure FirstGeneration;
	procedure NewMinimal;
	procedure Breed;
implementation

	var NorthPole, SouthPole, EastPole, WestPole: integer;
		

	procedure DrawLine (x, y, endx, endy, thick: integer);
		procedure Dline (x, y, endx, endy, thick: integer);
		begin
{thick := round(thick div thickscale);}
{if thick < 1 then thick := 1;}
			if endy < NorthPole then
				NorthPole := endy;
			if endy > SouthPole then
				SouthPole := endy;
			if endx < WestPole then
				WestPole := endx;
			if endx > EastPole then
				EastPole := endx;
			PenSize(thick, thick);
			MoveTo(x - thick div 2, y - thick div 2);
			LineTo(endX - thick div 2, endY - thick div 2);
			PenSize(1, 1);
		end;
	begin
		if sideways then
			Dline(y, x, endy, endx, thick)
		else
			Dline(x, y, endx, endy, thick);
	end; {Drawline}

	procedure DrawOval (x, y, width, height: integer);
		procedure DOval (x, y, width, height: integer);
			var
				r: rect;
				{$ifc undefined THINK_PASCAL}
				tmpPatPtr: PatternPtr;
				{$endc}
				patternLightGray: Pattern;
		begin
			with r do
				begin
					left := x;
					top := y;
					right := left + width;
					bottom := top + height;
					if top < NorthPole then
						NorthPole := top;
					if bottom > SouthPole then
						SouthPole := bottom;
					if left < WestPole then
						WestPole := left;
					if right > EastPole then
						EastPole := right;
				end;
			if WantColor then
				begin
					rgbbackcolor(rgbGreenColor);
					EraseOval(r);
				end
			else
				{$ifc not undefined THINK_PASCAL}
				patternLightGray := ltgray;
				{$elsec}
				tmpPatPtr := GetQDGlobalsLightGray(patternLightGray);
				{$endc}
				fillOval(r, patternLightGray);
			pensize(2, 2);
			frameOval(r);
			pensize(1, 1);
			backColor(whiteColor);
		end;
	begin
		if sideways then
			DOval(y, x, height, width)
		else
			DOval(x, y, width, height);
	end; {DrawOval}

	procedure DrawSeg (x, y: integer; width, height: real);
	{We must adjust the position before drawing the oval}
		var
			halfW: integer;
	begin
		width := width;
		height := height;
		halfW := round(width / 2);
		DrawOval(x - halfW, y, round(width), round(height));
		forecolor(BlackColor);
	{convert from center of oval to left corner}
	end;{DrawSeg}


	procedure DrawClaw (which: integer; params: CumParams; x, y, xCenter: integer);
	{Draw a claw, note that we don't use which at all.  Param info is already folded in}
		var
			oldX, oldY, leftOldX, leftX, thick: integer;
			ang: real;
	begin
		foreColor(RedColor);
		oldX := x;
		oldY := y;
		ang := params[9] / 2.0;
		{half claw opening, in radians}
		x := round(x + params[8] * sin(ang));		{line end point   width*sine(angle)}
		y := round(y + params[8] * cos(ang));		{line end point}
		thick := 1 + trunc(params[7]);		{1 is minimum thickness}
		DrawLine(oldX, oldY, x, y, thick);		{right side, top of claw}

		leftX := xCenter - (x - xCenter);		{do the left side, top of claw}
		leftOldX := xCenter - (oldX - xCenter);
		DrawLine(leftOldX, oldY, leftX, y, thick);

	{Bottom of the claw}
		y := round(y - 2.0 * params[8] * cos(ang));
		DrawLine(oldX, oldY, x, y, thick);				{right side}
		DrawLine(leftOldX, oldY, leftX, y, thick);	{left side}
		ForeColor(BlackColor);
	end;

	procedure Draw (which: integer; params: CumParams; x, y, xCenter: integer; var ySeg: integer);
	{Starting at the atom 'which', multiply its numbers into the array of params.}
	{At the bottom, draw the part starting at x,y}
	{params accumulates the final Joint width, Claw angle, etc.}
	{params: 1 Seg height, 2 Seg width, 3 (not used), 4 Joint thickness, 5 Joint length, 6 Joint angle,}
	{ 	7 Claw thickness, 8 Claw length, 9 Claw angle between pincers}
	{x,y are current local point, xCenter is the centerline of the animal (left and right Joints need this)}
		var
			myPars: CumParams;
			oldX, oldY, leftOldX, leftX, offset, thick: integer;
			ang, jointscale: real;
	begin
		jointscale := 0.5;
		myPars := params;
	{local copy so next segment builds on section above, not this segment}
		with BoneYard[which]^^ do
			begin
				if kind = AnimalTrunk then
					begin
						GradientFactor := NextLikeMe;
						if gradientFactor > 1000 then
							{$ifc not undefined THINK_PASCAL}
							SysBeep(1);
							{$elsec}
							TellError('Gradient factor exceeds 1000.');
							{$endc}
					end;
				offset := ParamOffset[Kind];		{where in params to begin, see InitBoneYard}
				params[offset] := params[offset] * Height;		{fold in this atom's params}
				params[offset + 1] := params[offset + 1] * Width;
				params[offset + 2] := params[offset + 2] * Angle;	{Must be a real number, even if not used in this Atom}
				if kind = SectionTrunk then
					overlap := angle;{by convention}
				if Kind = SegmentTrunk then
					begin
						if GradientFactor > 1000 then
							{$ifc not undefined THINK_PASCAL}
							SysBeep(1);
							{$elsec}
							TellError('Gradient factor exceeds 1000.');
							{$endc}
						params[2] := params[2] + GradientFactor * angle;
						Params[1] := Params[1] + GradientFactor * angle;
						DrawSeg(x, ySeg, params[2], params[1]);
{Draw the oval in the right place. 2 = Width , 1 = Height }
						oldY := ySeg;  {Save for next segment}
						x := x + round(params[2] / 2.0);{joint starts at the side of the segment}
						y := ySeg + round(params[1] / 2.0);
{joint starts half way down the segment }
					end;
				if Kind = Joint then
					begin
			{both next joint (NextLikeMe) and claw (FirstBelowMe) want x,y at end of this joint}
						oldX := x;
						oldY := y;
						ang := params[6];
						x := round(x + jointscale * params[5] * cos(ang));		{line end point   width*sine(angle)}
						y := round(y + jointscale * params[5] * sin(ang));		{line end point}
						thick := 1 + trunc(params[4]);		{1 is minimum thickness}
						DrawLine(oldX, oldY, x, y, thick);		{right side leg}
						leftX := xCenter - (x - xCenter);		{do the left side leg}
						leftOldX := xCenter - (oldX - xCenter);
						DrawLine(leftOldX, oldY, leftX, y, thick);
						foreColor(blackColor);
					end;
				if kind = Claw then
					DrawClaw(which, params, x, y, xCenter)		{all work is done in here}
				else
 {TED:  why else?  Presumably because claw is the end of the line?}
					begin
						if FirstBelowMe <> 0 then
							Draw(FirstBelowMe, params, x, y, xCenter, ySeg);		{build on my cumulative numbers}
						if Kind = SegmentTrunk then
							begin
								x := xCenter;
								ySeg := round(oldY + overlap * params[1]);{Seg}
		{Jump down by height of this segment to the next segment}
							end;
						if NextLikeMe <> 0 then
							begin
								if (Kind = AnimalJoint) or (Kind = SectionJoint) or (Kind = SegmentJoint) then
									Draw(NextLikeMe, params, x, y, xCenter, ySeg)		{build on me}
								else if kind <> AnimalTrunk then
									Draw(NextLikeMe, myPars, x, y, xCenter, ySeg);		{build on my parent's numbers}
							end;
				{Note that each Joint builds on the length of the SegJoint, not the joint just before it.}
				{This is consistant with the way Sections and Segments work.}
					end;
			end;
	end; {Draw}


	procedure DrawAnimal (BoxNo, x, y: integer);
	{An example of how to call Draw for an animal}
		var
			params: CumParams;
			ii, ySeg: integer;
	begin
		for ii := 1 to 9 do
			params[ii] := 1.0;		{clear it all out}
		ySeg := y;
		Draw(BreedersChoice[BoxNo], params, x, y, x, ySeg);
		{x = xCenter when we start}
	end;

	procedure DrawInBox (BoxNo: integer);
		var
			where: rect;
			centre, start, boxwidth, boxheight: integer;
	begin
		where := Box[BoxNo];
		boxwidth := where.right - where.left;
		boxheight := where.bottom - where.top;
		if sideways then
			begin
				centre := where.top + boxheight div 2;
				start := where.left + boxwidth div 2;
				WestPole := Prect.right;
				EastPole := Prect.left;
				if centring or (BoxNo = MidBox) then
					begin
						hidePen;
						DrawAnimal(BoxNo, centre, start); {return with NorthPole and SouthPole updated}
						ShowPen;
						Midriff := WestPole + (EastPole - WestPole) div 2;
						verticalOffset := Start - Midriff;
					end;
			end
		else
			begin
				start := where.top + boxheight div 2;
				centre := where.left + boxwidth div 2;
				NorthPole := Prect.bottom;
				SouthPole := Prect.top;
				if centring or (BoxNo = MidBox) then
					begin
						hidePen; {Preliminary dummy draw to measure North & South extent of animal}
						DrawAnimal(BoxNo, centre, start); {return with NorthPole and SouthPole updated}
						ShowPen;
						Midriff := NorthPole + (SouthPole - NorthPole) div 2;
						verticalOffset := Start - Midriff;
					end;
			end;
		if AnimalPicture[BoxNo] <> nil then
			KillPicture(AnimalPicture[BoxNo]); {redraw Picture in correct position}
		AnimalPicture[BoxNo] := OpenPicture(Box[BoxNo]);
		showpen;
		ClipRect(Box[BoxNo]);
		DrawAnimal(BoxNo, centre, start + VerticalOffset);
{Midriff := NorthPole - VerticalOffset + (SouthPole - NorthPole) div 2;}
{VerticalOffset := Start - Midriff;}
		hidepen;
		ClipRect(Prect);
		ClosePicture;
	end; {DrawInBox}

	procedure Clear (box: rect);
		var
			r: rect;
	begin
		with box do
			begin
				r.top := top + 1;
				r.bottom := bottom - 1;
				r.left := left + 1;
				r.right := right - 1;
			end;
		eraserect(r);
	end;{clear }

	{Called from UpdateAnimals, FirstGeneration, and BreedingWindow content click event} 
	procedure evolve (MLoc: point);
		var
			j: INTEGER;
			whitePattern: Pattern;
			blackPattern: Pattern;
			{$ifc  undefined THINK_PASCAL}
			tempPatPtr: PatternPtr;
			{$endc}
		procedure GrowChild (j: INTEGER);
			var
				k: LONGINT;
		begin
			Cliprect(Prect);
			PenMode(PatXor);
			MoveTo(Centre[Midbox].h, Centre[Midbox].v);
			LineTo(Centre[j].h, Centre[j].v);
			k := TickCount;
			HiWindowFlush(BreedingWindow);
			repeat
			until TickCount > k + DelayTicks;
			MoveTo(Centre[Midbox].h, Centre[Midbox].v);
			LineTo(Centre[j].h, Centre[j].v);
			HiWindowFlush(BreedingWindow);

			PenMode(PatCopy);
			if (BoneYard[BreedersChoice[j]]^^.kind <> AnimalTrunk) then
				TellError('Breeders Choise is not an animal');
			if j <> MidBox then
				kill(BreedersChoice[j]);
			BreedersChoice[j] := reproduce(BreedersChoice[MidBox]);
			SegmentCounter := 0;
			CountSeg(BreedersChoice[j]);
{ClipRect(Box[j]);}
{if not AbortFlag then}
			DrawInBox(j);
			HiWindowFlush(BreedingWindow);
		end;

	begin
		j := 0;
		special := 0;
		repeat
			j := j + 1;
			if (PtInRect(Mloc, box[j])) then
				special := j;
		until (j = NBoxes);
		if special > 0 then
			begin
				ObscureCursor;
				for j := 1 to NBoxes do
					if j <> special then
						if not resizing then
							EraseRect(box[j]);
				{$ifc not undefined THINK_PASCAL}
				whitePattern := white;
				blackPattern := black;
				{$elsec}
				tempPatPtr := GetQDGlobalsWhite(whitePattern);
				tempPatPtr := GetQDGlobalsBlack(blackPattern);
				{$endc}
				PenPat(whitePattern);
				Framerect(box[special]);
				PenPat(blackPattern);
				
				Slide(box[special], box[MidBox]);
				if special <> MidBox then
					begin
						kill(BreedersChoice[MidBox]);
						BreedersChoice[MidBox] := AllocateAtom;
					end;
				BreedersChoice[MidBox] := CopyAtom(BreedersChoice[special]);
				if not resizing then
					SetUpBoxes;
				ClipRect(Box[MidBox]);
				DrawInBox(MidBox);
				HiWindowFlush(BreedingWindow);

				for j := 1 to MidBox - 1 do
					Growchild(j);
				for j := MidBox + 1 to NBoxes do
					Growchild(j);
				ClipRect(Prect);
				special := MidBox;
			end;
	end; {evolve}

	{The old resizing behaviour was to trigger a new round of breeding (evolve) on every resize,}
	{destroying the arthromorphs on the screen. How rude! The new routine preserves them. It}
	{also recalculates box sizes and redraws boxes on every call, fixing a resize bug endemic}
	{to the original version, which skipped the call to DrawBoxes in certain circumstances. - ABC}
	procedure UpDateAnimals;
		var
			j: integer;
	begin
		setupboxes;
		for j := 1 to NRows * NCols do
			DrawPicture(AnimalPicture[j], box[j]);
		resizing := false;
	end; {UpDateAnimal}

	procedure FirstGeneration;
		var
			ii: integer;
	begin
		for ii := 1 to MidBox - 1 do
			begin
				BreedersChoice[ii] := Reproduce(BreedersChoice[MidBox]);
			end;
		for ii := MidBox + 1 to NRows * NCols do
			begin
				BreedersChoice[ii] := Reproduce(BreedersChoice[MidBox]);
			end;
		Evolve(Centre[MidBox]);
	end; {FirstGeneration}

	procedure Breed;
		var
			ii: integer;
			NeedAnimal: Boolean;
	begin
		NeedAnimal := false;
		ii := BreedersChoice[MidBox];
		if (ii < 1) or (ii > YardSize) then
			NeedAnimal := true
		else if Boneyard[BreedersChoice[MidBox]]^^.kind = free then
			NeedAnimal := true;
		if needAnimal then
			begin
				BreedersChoice[MidBox] := AllocateAtom;
				BreedersChoice[MidBox] := MinimalAnimal;
				FirstGeneration;
				BreedersChoice[MidBox] := MinimalAnimal;
			end; {else the Open Breed_Window in HandleMenus is sufficient to replace the old Arthromorphs}
	end;{Breed}

	procedure NewMinimal;
	begin
		BreedersChoice[MidBox] := 0; {Force Breed to recreate new MinimalAnimal}
		Breed
	end;

	procedure flipWantColor;
	begin
		wantColor := not wantColor;
		DrawinBox(MidBox);
	end; {flipWantColor}



end.