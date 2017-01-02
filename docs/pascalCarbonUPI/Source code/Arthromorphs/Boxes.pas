unit Boxes;
interface
	uses
		{$ifc undefined THINK_PASCAL}
		MacOSAll,
		{$endc}
		MyGlobals;
	procedure SetUpBoxes;
	procedure Slide (LiveRect, DestRect: Rect);
	procedure DrawBoxes;

implementation


	function sgn (x: INTEGER): INTEGER;
	begin
		if x < 0 then
			sgn := -1
		else if x > 0 then
			sgn := 1
		else
			sgn := 0
	end; {sgn}


	procedure Slide (LiveRect, DestRect: Rect);
		var
			SlideRect: RECT;
			xDiscrep, yDiscrep, dh, dv, dx, dy, xmoved, ymoved, xToMove, yToMove, distx, disty: INTEGER;
			TickValue: LONGINT;

	begin {PenMode(PatXor); FrameRect(LiveRect); PenMode(PatCopy);}
		xMoved := 0;
		yMoved := 0;
		distx := DestRect.left - LiveRect.left;
		disty := DestRect.bottom - LiveRect.bottom;
		dx := sgn(distx);
		dy := sgn(disty);
		xToMove := ABS(distx);
		yToMove := ABS(disty);
		xMoved := 0;
		yMoved := 0;
		UnionRect(LiveRect, DestRect, SlideRect);
		ObscureCursor;
		repeat
			TickValue := TickCount;
			xDiscrep := xToMove - xMoved;
			if xDiscrep <= 20 then
				dh := xDiscrep
			else
				dh := (xDiscrep) div 2;
			yDiscrep := yToMove - yMoved;
			if Ydiscrep <= 20 then
				dv := yDiscrep
			else
				dv := (yDiscrep) div 2;
			if (xMoved < xToMove) or (yMoved < yToMove) then
				ScrollRect(SlideRect, dx * dh, dy * dv, upregion);
			xMoved := xMoved + ABS(dh);
			yMoved := yMoved + ABS(dv);
			HiWindowFlush(BreedingWindow);
			repeat
			until TickCount > TickValue + DelayTicks;
		until (xMoved >= xToMove) and (yMoved >= yToMove);
	end; {Slide}

	procedure DrawBoxes;
		var
			j: integer;
	begin
		for j := 1 to NBoxes do
			framerect(box[j]);
		PenSize(3, 3);
		FrameRect(box[MidBox]);
		PenSize(1, 1);
	end;

	{The original Pascal source duplicated the effort of DrawBoxes.}
	{The carbon port cleans this up by substituting a call to DrawBoxes.}
	procedure SetUpBoxes;
		var
			j, l, t, row, column, boxwidth, height, midBox: INTEGER;
			{$ifc  undefined THINK_PASCAL}
			portRectPtr: RectPtr;
			{$endc}

	begin
		{$ifc not undefined THINK_PASCAL}
		Prect := BreedingWindow^.PortRect;
		{$elsec}
		portRectPtr := GetPortBounds(GetWindowPort(BreedingWindow), Prect);
		{$endc}
		with Prect do
			begin
				bottom := bottom - 20;
				right := right - 20;
			end;
		EraseRect(Prect);
		j := 0;
		NBoxes := NRows * NCols;
		MidBox := NBoxes div 2 + 1;
		with Prect do
			begin
				boxwidth := (right - left) div ncols;
				height := (bottom - top) div nrows;
				for row := 1 to NRows do
					for column := 1 to NCols do
						begin
							j := j + 1;
							l := left + boxwidth * (column - 1);
							t := top + height * (row - 1);
							setrect(box[j], l, t, l + boxwidth, t + height);
							with box[j] do
								begin
									Centre[j].h := left + boxwidth div 2;
									Centre[j].v := top + height div 2
								end;
						end; {row & column loop}
			end; {WITH Prect}
		with Prect do
			begin
				left := box[1].left;
				right := Box[NBoxes].right;
				top := box[1].top;
				bottom := box[Nboxes].bottom
			end;
		DrawBoxes;
		SetRect(Box[0], 261, 28, 483, 320); {Special box for Engineering window}
		with box[0] do
			begin
				boxwidth := right - left;
				height := bottom - top;
				Centre[0].h := left + boxwidth div 2;
				Centre[0].v := top + height div 2
			end;
	end; {setup boxes}

end.