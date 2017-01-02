unit DrawPicDefs;

interface
	uses Globals, MacOSAll, PersonDefs, PicDefs;
	procedure DrawPic (ThisPic: Pic; Place: Point; VAR Biomorph: person);

implementation

procedure DrawPic (ThisPic: Pic; Place: Point; VAR Biomorph: person);
 {Pic already contains its own origin, meaning the coordinates at which}
{ it was originally drawn. Now draw it at Place}
	TYPE
		PicStyleType = (LF, RF, FF, LUD, RUD, FUD, LSW, RSW, FSW);
	VAR
		j, y0, y1, x0, x1, VertOffset, HorizOffset, Mid2, belly2: Integer;
		PicStyle: PicStyleType;
		LinSize: integer;

	procedure ActualLine (PicStyle: PicStyleType; Orientation: Compass);
		begin
			WITH ThisPic.MovePtr^ DO
				begin
					Pensize(Thickness, Thickness);
					IF Orientation = NorthSouth THEN
						begin
							VertOffset := ThisPic.Origin.v - Place.v;
							HorizOffset := ThisPic.Origin.h - Place.h;
							y0 := StartPt.v - VertOffset;
							y1 := EndPt.v - VertOffset;
							x0 := StartPt.h - HorizOffset;
							x1 := EndPt.h - HorizOffset
						end
					ELSE
						begin
							VertOffset := ThisPic.Origin.h - Place.v;
							HorizOffset := ThisPic.Origin.v - Place.h;
							y0 := StartPt.h - VertOffset;
							y1 := EndPt.h - VertOffset;
							x0 := StartPt.v - HorizOffset;
							x1 := EndPt.v - HorizOffset
						end;
					CASE PicStyle OF
						LF: 
							begin
								MoveTo(x0, y0);
								LineTo(x1, y1)
							end;
						RF: 
							begin
								MoveTo(Mid2 - x0, y0);
								LineTo(Mid2 - x1, y1)
							end;
						FF: 
							begin
								MoveTo(x0, y0);
								LineTo(x1, y1);
								MoveTo(Mid2 - x0, y0);
								LineTo(Mid2 - x1, y1)
							end;
						LUD: 
							begin
								MoveTo(x0, y0);
								LineTo(x1, y1);
								MoveTo(Mid2 - x0, belly2 - y0);
								LineTo(Mid2 - x1, belly2 - y1);
							end;
						RUD: 
							begin
								MoveTo(Mid2 - x0, y0);
								LineTo(Mid2 - x1, y1);
								MoveTo(x0, belly2 - y0);
								LineTo(x1, belly2 - y1);
							end;
						FUD: 
							begin
								MoveTo(x0, y0);
								LineTo(x1, y1);
								MoveTo(Mid2 - x0, y0);
								LineTo(Mid2 - x1, y1);
								MoveTo(x0, belly2 - y0);
								LineTo(x1, belly2 - y1);
								MoveTo(Mid2 - x0, belly2 - y0);
								LineTo(Mid2 - x1, belly2 - y1)
							end;
					end; {CASES}
				end
		end; {ActualLine}

	begin
		LinSize := SizeOf(Lin);
		PicStyle := FF; {To correct initialisation bug, due to call in DoUpdate}
		WITH biomorph DO
			CASE CompletenessGene OF
				CompletenessTypeSingle: 
					CASE SpokesGene OF
						NorthOnly: 
							PicStyle := LF;
						NSouth: 
							PicStyle := LUD;
						Radial: 
							PicStyle := LUD;
					end;
				CompletenessTypeDouble: 
					CASE SpokesGene OF
						NorthOnly: 
							PicStyle := FF;
						NSouth: 
							PicStyle := FUD;
						Radial: 
							PicStyle := FUD;
					end;
			end; {CASES}
		PenSize(MyPenSize, MyPenSize);
		Mid2 := 2 * Place.h;
		belly2 := 2 * Place.v;
		WITH ThisPic DO
			begin
				MovePtr := linptr(BasePtr); {reposition at base of grabbed space}
				FOR j := 1 TO PicSize DO
					WITH Biomorph DO
						begin
							ActualLine(PicStyle, NorthSouth); {sometimes rangecheck error}
							IF SpokesGene = Radial THEN
								begin
									IF CompletenessGene = CompletenessTypeSingle THEN
										ActualLine(RUD, EastWest)
									ELSE
										ActualLine(PicStyle, EastWest)
								end;
							ThisPic.MovePtr := linptr(size(ThisPic.MovePtr) + LinSize);
						end;
			end;
		PenSize(1, 1);
	end; {DrawPic}

end.