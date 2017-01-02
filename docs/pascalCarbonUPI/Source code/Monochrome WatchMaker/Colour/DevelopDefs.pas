unit DevelopDefs;

interface

	uses 
		 
		DrawPicDefs,
		Globals, 
		MacOSAll,
		Miscellaneous,
		ModeDefs,
		PicDefs,
		PersonDefs;


	procedure Develop (VAR biomorph: person; Here: point);
	procedure Delayvelop (VAR Biomorph: Person; Here: Point);

implementation

procedure Develop (VAR biomorph: person; Here: point);
	VAR
		order, j, seg, Upextent, Downextent, wid, ht, SizeWorry, thick: Integer;
		dx, dy: ARRAY[0..7] OF Integer;
		Running: chromosome;
		OldHere, Centre: Point;
		OddOne: Boolean;
		ExtraDistance, IncDistance: Integer;
	procedure Tree (x, y, lgth, dir: Integer);
		VAR
			xnew, ynew: Integer;
		begin
			IF dir < 0 THEN
				dir := dir + 8;
			IF dir >= 8 THEN
				dir := dir - 8;
			IF biomorph.tricklegene < 1 THEN
				biomorph.tricklegene := 1;
			xnew := x + lgth * dx[dir] DIV biomorph.tricklegene;
			ynew := y + lgth * dy[dir] DIV biomorph.tricklegene;
			WITH margin DO
				begin
					IF x < left THEN
						left := x;
					IF x > right THEN
						right := x;
					IF y > bottom THEN
						bottom := y;
					IF y < top THEN
						top := y;
					IF xnew < left THEN
						left := xnew;
					IF xnew > right THEN
						right := xnew;
					IF ynew > bottom THEN
						bottom := ynew;
					IF ynew < top THEN
						top := ynew;
				end;
   {IF (x<>xnew) OR (y<>ynew) THEN }
			IF biomorph.dGene[9] = shrink THEN
				thick := lgth
			ELSE IF biomorph.dGene[9] = swell THEN
				thick := 1 + biomorph.Gene[9] - lgth
			ELSE
				thick := 1;
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

		IF zeromargin THEN
			begin
				margin.left := Here.h;
				margin.right := Here.h;
				margin.top := Here.v;
				margin.bottom := Here.v;
			end;
		Centre := Here;

		PlugIn(Biomorph.gene);
		ZeroPic(MyPic, Here);
		WITH biomorph DO
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
								Here.v := Here.v + (SegDistGene + IncDistance) DIV Tricklegene;
								IncDistance := IncDistance + ExtraDistance;
								IF biomorph.dGene[9] = shrink THEN
									thick := biomorph.Gene[9]
								ELSE
									thick := 1;
								PicLine(MyPic, OldHere.h, Oldhere.v, Here.h, Here.v, thick);
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
						SizeWorry := biomorph.SegNoGene * TwoToThe(biomorph.gene[9]);
						IF SizeWorry > WorryMax THEN
							biomorph.Gene[9] := biomorph.Gene[9] - 1;
						IF biomorph.gene[9] < 1 THEN
							biomorph.gene[9] := 1;
						tree(Here.h, Here.v, order, 2);
					end;
			end;
		WITH biomorph DO
			WITH margin DO
				begin
					IF Centre.h - left > right - Centre.h THEN
						right := Centre.h + (Centre.h - left)
					ELSE
						left := Centre.h - (right - Centre.h);
					Upextent := Centre.v - top; {can be zero if biomorph goes down}
					Downextent := bottom - Centre.v;
					IF ((SpokesGene = NSouth) OR (SpokesGene = Radial)) OR (TheMode = engineering) THEN 
					{Obscurely necessary to cope with erasing last Rect in Manipulation}
						begin
							IF UpExtent > DownExtent THEN
								bottom := Centre.v + UpExtent
							ELSE
								top := Centre.v - DownExtent
						end;
					IF SpokesGene = Radial THEN
						begin
							wid := right - left;
							ht := bottom - top;
							IF wid > ht THEN
								begin
									top := centre.v - wid DIV 2 - 1;
									bottom := centre.v + wid DIV 2 + 1
								end
							ELSE
								begin
									left := centre.h - ht DIV 2 - 1;
									right := centre.h + ht DIV 2 + 1
								end
						end
				end;
		MyPic.PicPerson := biomorph;
		IF NOT DelayedDrawing THEN
			DrawPic(MyPic, Centre, biomorph)
	end; {develop}

procedure Delayvelop (VAR Biomorph: Person; Here: Point);
	VAR
		margcentre, offset: Integer;
		OffCentre: Point;
	begin
		DelayedDrawing := TRUE;
		Zeromargin := TRUE;
		develop(Biomorph, Here);
		DelayedDrawing := FALSE;
		WITH margin DO
			margcentre := top + (bottom - top) DIV 2;
		offset := margcentre - Here.v;
		WITH Margin DO
			begin
				Top := Top - Offset;
				Bottom := Bottom - Offset
			end;
		WITH OffCentre DO
			begin
				h := Here.h;
				v := Here.v - offset;
			end;
		DrawPic(MyPic, offcentre, Biomorph);
		HiWindowFlush(MainWindow);
	end; {Delayvelop}
end.