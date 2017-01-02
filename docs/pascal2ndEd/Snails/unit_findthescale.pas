unit unit_findthescale;

interface
uses
{$IFC UNDEFINED THINK_Pascal}
  Types,
{$ENDC}
  unit_snail_types, unit_globals, unit_error, unit_miscellaneous;

	procedure FindTheScale (prefs: SnailPreferencesHandle; var theScale: real);
	procedure ZeroPic (var ThisPic: Pic; Here: Point);
	procedure PicLine (var ThisPic: Pic; x, y, xnew, ynew: Integer);
  procedure Slide (LiveRect, DestRect: Rect);
	procedure SetUpBoxes(prefs: SnailPreferencesHandle);
	procedure MakeGeneBox (prefs: SnailPreferencesHandle; biomorph: person);
	procedure ShowGeneBox (prefs: SnailPreferencesHandle; j: Integer; biomorph: person);
	procedure ShowChangedGene (prefs: SnailPreferencesHandle; an1, an2: person);
  function margarine (W: real; direction: integer): real;
	procedure Reproduce (prefs: SnailPreferencesHandle; parent: person; var child: person);
	function Direction: Integer;
  procedure DoShowBoxes(prefs: SnailPreferencesHandle);
implementation
{$IFC UNDEFINED THINK_Pascal}
	uses
		Quickdraw, QuickdrawText, Events, SegLoad, Memory, ToolUtils;
{$ENDC}

	function power (w, i: real): real;
	begin
		power := exp(i * ln(w));
	end;


	procedure GeneBoxTemplate(prefs: SnailPreferencesHandle);
		var
			j: Integer;
	begin
    with prefs^^ do
      begin
		    width := (Prect.right - Prect.left) div 5;
		    with GeneBox[1] do
			    begin
				    left := box[1].left;
				    right := left + width;
				    top := Prect.Top;
				    bottom := top + GenesHeight;
				    EraseRect(GeneBox[1]);
				    Framerect(GeneBox[1]);
			    end;
		    for j := 2 to 5 do
			    with GeneBox[j] do
				    begin
					    top := PRect.top;
					    bottom := top + GenesHeight;
					    left := GeneBox[j - 1].right;
					    right := left + width;
					    EraseRect(GeneBox[j]);
					    Framerect(GeneBox[j])
				    end;
      end;
  end; {GeneBoxTemplate}

	procedure ShowGeneBox (prefs: SnailPreferencesHandle; j: Integer; biomorph: person);
		var
			theString: str255;
			n: real;
			l: longint;
	begin
		with biomorph do
      with prefs^^ do
			if j > 0 then
				with GeneBox[j] do
					begin
						EraseInnerRect(GeneBox[j]);
						MoveTo(left - 8 + width div 2, top + 14);
						case j of
							1:
								begin
									DrawReal(WOpening);
									MoveTo(Left, top + 14);
									Drawstring('   W');
									MoveTo(left + (width - stringwidth(thestring)) div 2, top + 14);
								end;
							2:
								begin
									DrawReal(DDisplacement);
									MoveTo(Left, top + 14);
									Drawstring('   D');
									MoveTo(left + (width - stringwidth(thestring)) div 2, top + 14);
								end;
							3:
								begin
									DrawReal(SShape);
									MoveTo(Left, top + 14);
									Drawstring('   S ');
									MoveTo(left + (width - stringwidth(thestring)) div 2, top + 14);
								end;
							4:
								begin
									DrawReal(TTranslation);
									MoveTo(Left, top + 14);
									Drawstring('   T');
									MoveTo(left + (width - stringwidth(thestring)) div 2, top + 14);
								end;
						end; {cases}
					end; {with}
	end; {ShowGeneBox}


	procedure ShowChangedGene (prefs: SnailPreferencesHandle; an1, an2: person);
		var
			k: integer;
	begin
		if OldBox > 0 then
			begin
				if an1.Wopening <> an2.Wopening then
					ShowGeneBox(prefs, 1, an1);
				if an1.DDisplacement <> an2.DDisplacement then
					ShowGeneBox(prefs, 2, an1);
				if an1.SShape <> an2.SShape then
					ShowGeneBox(prefs, 3, an1);
				if an1.TTranslation <> an2.TTranslation then
					ShowGeneBox(prefs, 4, an1);
			end;
	end; {ShowChangedGene}

	procedure MakeGeneBox (prefs: SnailPreferencesHandle; biomorph: person);
		var
			j: Integer;
	begin
		GeneBoxTemplate(prefs);
		for j := 1 to 4 do
			ShowGeneBox(prefs, j, biomorph);
	end; {MakeGeneBox}


	function Direction: Integer;
	begin
		if randint(2) = 2 then
			direction := 1
		else
			direction := -1
	end;
	function Direction9: Integer;
		var
			r: integer;
	begin
		r := randint(5);
		case r of
			5:
				direction9 := 2;
			4:
				direction9 := 1;
			3:
				direction9 := 0;
			2:
				direction9 := -1;
			1:
				direction9 := -2;
			otherwise
				direction9 := 0;
		end; {cases}
	end;


	function margarine (W: real; direction: integer): real;
{we want to change by large amounts when low, small amounts when large}
		var
			m, logged, logchanged, WMutSize: real;
	begin
		WMutSize := 0.1;
		logged := ln(W);
		logchanged := logged + WMutSize * direction;
		if logchanged > 20 then
			logchanged := 20;
		m := exp(logchanged);
		if m < 1 then
			m := 1;
		margarine := m
	end;

	procedure Reproduce (prefs: SnailPreferencesHandle; parent: person; var child: person);
		var
			j: Integer;

	begin
		child := parent;
		with child do
			begin
				begin
					if Randint(100) < MutProb then
						WOpening := Margarine(WOpening, direction);
					if Randint(100) < MutProb then
						begin
							DDisplacement := DDisplacement + direction9 * DMutSize;
							if DDisplacement < 0 then
								DDisplacement := 0;
							if DDisplacement > 1 then
								DDisplacement := 1;
						end;
					if (Randint(100) < MutProb) and prefs^^.SideView then
						begin {Don't let Translation gene drift when you can't see its consequences}
							TTranslation := TTranslation + direction9 * TMutSize;
						end;
					if (Randint(100) < 1) then
						Handedness := -Handedness;
				end;
			end
	end; {reproduce}


	function Sgn (x: Integer): Integer;
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
			xDiscrep, yDiscrep, dh, dv, dx, dy, xmoved, ymoved, xToMove, yToMove, distx, disty: Integer;
			TickValue: LONGINT;
			upregion: RgnHandle;		{moved from globals}

	begin {PenMode(PatXor); FrameRect(LiveRect); PenMode(PatCopy);}
		upregion := NewRgn;		{moved from initialize}
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
			repeat
			until TickValue <> TickCount;
			if (xMoved < xToMove) or (yMoved < yToMove) then
				ScrollRect(SlideRect, dx * dh, dy * dv, upregion);
			xMoved := xMoved + ABS(dh);
			yMoved := yMoved + ABS(dv);
		until (xMoved >= xToMove) and (yMoved >= yToMove);
	end; {Slide}

	procedure SetUpBoxes(prefs: SnailPreferencesHandle);
		var
			j, l, t, row, column, boxwidth: Integer;
			height: Integer;
			SquareBox, inbox: Rect;
	begin
		SquareBox := prefs^^.Prect;
		SquareBox.right := prefs^^.Prect.bottom;
		j := 0;
		NBoxes := NRows * NCols;
		MidBox := NBoxes div 2 + 1;
		NActiveBoxes := NBoxes;
		with prefs^^.PRect do
			begin
{Framerect(Prect);}
				boxwidth := (right - left) div ncols;
				height := (bottom - top - GenesHeight) div nrows;
				for row := 1 to nrows do
					for column := 1 to ncols do
						begin
							j := j + 1;
							l := left + boxwidth * (column - 1);
							t := top + GenesHeight + height * (row - 1);
							setrect(prefs^^.box[j], l, t, l + boxwidth, t + height);
							if (prefs^^.TheMode = breeding) and (j <> MidBox) then
								FrameRect(prefs^^.box[j]);
							with prefs^^.box[j] do
								begin
									Centre[j].h := left + boxwidth div 2;
									CENTRE[j].v := top + height div 2
								end;
						end; {row & column loop}
			end; {WITH Prect}
		if prefs^^.theMode = breeding then
			begin
				PenSize(3, 3);
				FrameRect(prefs^^.box[MidBox]);
				PenSize(Psize, Psize);
			end;
		with BusinessPart do
			begin
				left := prefs^^.box[1].left;
				right := prefs^^.Box[NBoxes].right;
				top := prefs^^.box[1].top;
				bottom := prefs^^.box[Nboxes].bottom
			end;
	end; {setup boxes}

	procedure DoShowBoxes(prefs: SnailPreferencesHandle);
	begin
		SetUpBoxes(prefs);
		BoxesOnly(prefs);
	end;



	procedure PicLine (var ThisPic: Pic; x, y, xnew, ynew: Integer);
	begin
		with ThisPic do
			begin
				if PicSize >= PicSizeMax then
					begin
{Message(GetString(TooLargeString));}
 {used the help dialog! v1.1 changed to alert}
						DisplayError(-147, 'Biomorph too large, or other problem', ' ', StopError);
						ExitToShell
					end
				else
					with MovePtr^ do
						begin
							StartPt.h := x;
							StartPt.v := y;
							EndPt.h := xnew;
							EndPt.v := ynew
						end;
				MovePtr := linptr(size(MovePtr) + 8);  {advance 'array subscript' by number}
{                                    of bytes occupied by one lin}
				PicSize := PicSize + 1
			end
	end; {PicLine}

	procedure ZeroPic (var ThisPic: Pic; Here: Point);
	begin
		with ThisPic do
			begin
				MovePtr := LinPtr(BasePtr);
				PicSize := 0;
				Origin := Here
			end
	end; {ZeroPic}

	procedure FindTheScale (prefs: SnailPreferencesHandle; var theScale: real);
		var
			targetheight, targetwidth, inheight, inwidth: integer;
			heightscale, widthscale: real;
	begin {fix theScale here based upon Margin and box[midbox]}
		with prefs^^.box[midbox] do
			begin
				targetheight := bottom - top;
				targetwidth := right - left;
        with prefs^^ do
          begin
				    inheight := margin.bottom - margin.top;
				    inwidth := margin.right - margin.left;
          end;
        heightscale := targetheight / inheight;
				widthscale := targetwidth / inwidth;
				if heightscale < widthscale then
					theScale := heightscale
				else
					theScale := widthScale;
			end;
		theScale := 0.95 * theScale;
	end;
end.
