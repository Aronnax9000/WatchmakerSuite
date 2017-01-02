unit DevelopDefs;

interface

	uses 
		 
		DrawPicDefs,
		Globals, 
		HIBoxTypes,
		MacOSAll,
		Miscellaneous,
		ModeDefs,
		PicDefs,
		PersonDefs;


	procedure Develop (theBox: HiBoxPtr; Here: HIPoint);
	procedure Delayvelop (box: HIBoxPtr; Here: HIPoint);

	
implementation

procedure Develop (theBox: HiBoxPtr; Here: HIPoint);
	VAR
		order, j, seg: integer; 
		Upextent, Downextent: single; 
		wid, ht: single;
		SizeWorry: longint;
		thick: single;
		dx, dy:  ARRAY[0..7] OF single;
		Running: chromosome;
		OldHere, Centre: HIPoint;
		OddOne: Boolean;
		ExtraDistance, IncDistance: single;
		oldValue: CGFloat;
		biomorph: PersonPtr;
		MyPic: MorphPicPtr;
	procedure Tree (x, y: CGFloat; lgth, dir: Integer);
		VAR
			xnew, ynew: CGFloat;
		begin
			IF dir < 0 THEN
				dir := dir + 8;
			IF dir >= 8 THEN
				dir := dir - 8;
			IF biomorph^.tricklegene < 1 THEN
				biomorph^.tricklegene := 1;
			xnew := x + lgth * dx[dir] / biomorph^.tricklegene;
			ynew := y + lgth * dy[dir] / biomorph^.tricklegene;
			WITH theBox^.margin DO
				begin
					{IF x < left THEN}
					IF x < origin.x THEN
						begin
							oldValue := origin.x;
							origin.x := x;
							size.width := size.width + (oldValue - origin.x); {Widen by the excess of the old origin over x}							
						end;
					{IF x > right THEN}
					IF x > origin.x + size.width THEN
						{right := x;}
						size.width := x - origin.x;
					{IF y > bottom THEN}
					IF y > origin.y + size.height THEN
						{bottom := y;}
						size.height := y - origin.y;
					{IF y < top THEN}
					IF y < origin.y THEN
						begin
							{top := y;}
							oldValue := origin.y;
							origin.y := y;
							size.height := size.height + (oldValue - origin.y);
						end;
					{IF xnew < left THEN}
					IF xnew < origin.x THEN
						{left := xnew;}
						origin.x := xnew;
					{IF xnew > right THEN}
					IF xnew > origin.x + size.width THEN
						{right := xnew;}
						size.width := xnew - origin.x;
					{IF ynew > bottom THEN}
					IF ynew > origin.y + size.height THEN
						{bottom := ynew;}
						size.height := ynew - origin.y;
					{IF ynew < top THEN}	
					IF ynew < origin.y THEN
						begin
							{top := ynew;}
							oldValue := origin.y;
							origin.y := ynew;
							size.height := size.height + (oldValue - origin.y);
							
						end;
				end;
			case biomorph^.dGene[9] of
				shrink: thick := lgth;
				swell: thick := 1 + biomorph^.Gene[9] - lgth;
				otherwise {'same'}
					thick := 1
			end;
			PicLine(MyPic, x, y, xnew, ynew, thick * MyPenSize);
			IF (lgth > 1) THEN
				begin
					IF oddone THEN
						begin
							tree(xnew, ynew, lgth - 1, dir + 1);
							IF lgth < order THEN
								tree(xnew, ynew, lgth - 1, dir - 1)
						end
					ELSE
						begin
							tree(xnew, ynew, lgth - 1, dir - 1);
							IF lgth < order THEN
								tree(xnew, ynew, lgth - 1, dir + 1)
						end
				end
		end; {tree}

	procedure PlugIn (Gene: chromosome);
		begin
			order := gene[9];
			dx[3] := gene[1];
			dx[4] := gene[2];
			dx[5] := gene[3];
			dy[2] := gene[4];
			dy[3] := gene[5];
			dy[4] := gene[6];
			dy[5] := gene[7];
			dy[6] := gene[8];
			dx[1] := -dx[3];
			dy[1] := dy[3];
			dx[0] := -dx[4];
			dy[0] := dy[4];
			dx[7] := -dx[5];
			dy[7] := dy[5];
			dx[2] := 0;
			dx[6] := 0;
		end; {PlugIn}


	begin {develop}
		ClipBoarding := FALSE;
		biomorph := theBox^.denizen;
		MyPic := theBox^.MyPic;
		IF zeromargin THEN
			begin
				theBox^.margin.origin.x := Here.x;
				theBox^.margin.size.width := 0;
				theBox^.margin.origin.y := Here.y;
				theBox^.margin.size.height := 0;
			end;
		Centre := Here;

		PlugIn(Biomorph^.gene);
		ZeroPic(MyPic, Here);
		WITH biomorph^ DO
			begin
				IF SegNoGene < 1 THEN
					SegNoGene := 1;
				IF dGene[10] = Swell THEN
					Extradistance := Tricklegene
				ELSE IF dGene[10] = Shrink THEN
					Extradistance := -Tricklegene
				ELSE
					ExtraDistance := 0;
				Running := gene;
				IncDistance := 0;
				FOR seg := 1 TO SegNoGene DO
					begin
						OddOne := odd(seg);
						IF seg > 1 THEN
							begin
								OldHere := Here;
								Here.y := Here.y + (SegDistGene + IncDistance) / Tricklegene;
								IncDistance := IncDistance + ExtraDistance;
								IF biomorph^.dGene[9] = shrink THEN
									thick := biomorph^.Gene[9]
								ELSE
									thick := 1;
								PicLine(MyPic, OldHere.x, Oldhere.y, Here.x, Here.y, thick);
								FOR j := 1 TO 8 DO
									begin
										IF dGene[j] = Swell THEN
											Running[j] := Running[j] + Tricklegene;
										IF dGene[j] = Shrink THEN
											Running[j] := Running[j] - Tricklegene;
									end;
								IF Running[9] < 1 THEN
									Running[9] := 1;
								PlugIn(Running)
							end;
						SizeWorry := biomorph^.SegNoGene * TwoToThe(biomorph^.gene[9]);
						IF SizeWorry > WorryMax THEN
							biomorph^.Gene[9] := biomorph^.Gene[9] - 1;
						IF biomorph^.gene[9] < 1 THEN
							biomorph^.gene[9] := 1;
						tree(Here.x, Here.y, order, 2);
					end;
			end;
		WITH biomorph^ DO
			WITH theBox^.margin DO
				begin
						
					{IF Centre.h - left > right - Centre.h THEN}
					IF Centre.x - origin.x > origin.x + size.width - Centre.x THEN
						{right := Centre.h + (Centre.h - left)}
						size.width := centre.x + (centre.x - origin.x) - origin.x
					ELSE
						begin
							{left := Centre.h - (right - Centre.h); }
							oldValue := origin.x;
							origin.x := centre.x - (size.width + origin.x - centre.x);
							size.width := size.width + (oldValue - origin.x)
						end;
										
					{Upextent := Centre.v - top;} {can be zero if biomorph goes down}
					Upextent := Centre.y - origin.y; {can be zero if biomorph goes down}
					{Downextent := bottom - Centre.v;}
					Downextent := origin.y + size.height - Centre.y;
					
					IF ((SpokesGene = NSouth) OR (SpokesGene = Radial)) OR (TheMode = engineering) THEN 
					{Obscurely necessary to cope with erasing last Rect in Manipulation}
						begin
							IF UpExtent > DownExtent THEN
								{bottom := Centre.v + UpExtent}
								size.height := Centre.y + UpExtent - origin.y
							ELSE
								begin
									{top := Centre.v - DownExtent}
									oldValue := origin.y;
									origin.y := Centre.y - DownExtent;
									size.height := size.height + (oldValue - origin.y)
								end;
						end;
					IF SpokesGene = Radial THEN
						begin
							IF size.width > size.height THEN
								begin
									{top := centre.v - wid DIV 2 - 1;}
									oldValue := origin.y;
									origin.y := centre.y - size.width / 2 - 1;
									size.height := size.height + (oldValue - origin.y);
									{bottom := centre.v + wid DIV 2 + 1}
									size.height := centre.y + size.width / 2 + 1 - origin.y
								end
							ELSE
								begin
									{left := centre.h - ht DIV 2 - 1;}
									oldValue := origin.x;
									origin.x := centre.x - size.height / 2 - 1;
									size.width := size.width + (oldValue - origin.x);
									{right := centre.h + ht DIV 2 + 1}
									size.width := centre.x + size.height / 2 + 1 - origin.x
								end
						end
				end;
		MyPic^.PicPerson := biomorph;
		(*IF NOT DelayedDrawing THEN
			HIViewSetNeedsDisplay(box^.view, true);*)
			{DrawPic(MyPic, Centre, box) FIXME }
	end; {develop}

procedure Delayvelop (box: HIBoxPtr; Here: HIPoint);
	VAR
		margcentre: CGFloat;
		offset: CGFloat;
	begin
		DelayedDrawing := TRUE;
		Zeromargin := TRUE;
		develop(box, Here);
		DelayedDrawing := FALSE;
(*		WITH margin DO
			margcentre := top + (bottom - top) DIV 2; *)
			
		margcentre := box^.margin.origin.y + box^.margin.size.height / 2;
		
		{offset := margcentre - Here.v;}
		offset := margcentre - Here.y;

		(*WITH Margin DO
			begin
				Top := Top - Offset;
				Bottom := Bottom - Offset 
			end;*)
		box^.Margin.origin.y := box^.Margin.origin.y - Offset;
		
		(*WITH OffCentre DO
			begin
				h := Here.h;
				v := Here.v - offset;
			end;		
		*)
		
		box^.OffCentre.x := Here.x;
		box^.OffCentre.y := Here.y - offset;
		HIViewSetNeedsDisplay(box^.view, true);
			
	end; {Delayvelop}
end.