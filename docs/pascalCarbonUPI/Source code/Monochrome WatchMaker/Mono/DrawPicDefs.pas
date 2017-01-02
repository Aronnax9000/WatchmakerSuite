unit DrawPicDefs;

interface
	uses Globals, MacOSAll, PersonDefs, PicDefs, HIBoxTypes;
	procedure DrawPic (ThisPic: MorphPicPtr; Place: HIPoint; box: HIBoxPtr; context: CGContextRef);

implementation

procedure DrawPic (ThisPic: MorphPicPtr; Place: HIPoint; box: HIBoxPtr; context: CGContextRef);
 {MorphPic already contains its own origin, meaning the coordinates at which}
{ it was originally drawn. Now draw it at Place}
	TYPE
		PicStyleType = (LF, RF, FF, LUD, RUD, FUD, LSW, RSW, FSW);
	VAR
		Mid2: CGFloat; 
		belly2: CGFloat;
		PicStyle: PicStyleType;
		Biomorph: person;
		MovePtr: LinPtr;

	procedure ActualLine (PicStyle: PicStyleType; Orientation: Compass);
		var
			x0, x1: CGFloat;
			y0, y1: CGFloat;
			VertOffset, HorizOffset: CGFloat;
			tmpThickness: CGFloat;
		begin
			{context := box^.view;}
			WITH MovePtr^ DO
				begin
					tmpThickness := MovePtr^.thickness;
					CGContextSetLineWidth ( context, thickness);
					CGContextBeginPath(context);
					with ThisPic^ do
						IF Orientation = NorthSouth THEN
							begin
								VertOffset := Origin.y - Place.y;
								HorizOffset := Origin.x - Place.x;
								y0 := StartPt.y - VertOffset;
								y1 := EndPt.y - VertOffset;
								x0 := StartPt.x - HorizOffset;
								x1 := EndPt.x - HorizOffset
							end
						ELSE
							begin
								VertOffset := Origin.x - Place.y;
								HorizOffset := Origin.y - Place.x;
								y0 := StartPt.x - VertOffset;
								y1 := EndPt.x - VertOffset;
								x0 := StartPt.y - HorizOffset;
								x1 := EndPt.y - HorizOffset
							end;
					CASE PicStyle OF
						LF: 
							begin
								CGContextMoveToPoint(context, x0, y0);
								CGContextAddLineToPoint(context, x1, y1)
							end;
						RF: 
							begin
								CGContextMoveToPoint(context, Mid2 - x0, y0);
								CGContextAddLineToPoint(context, Mid2 - x1, y1)
							end;
						FF: 
							begin
								CGContextMoveToPoint(context, x0, y0);
								CGContextAddLineToPoint(context, x1, y1);
								CGContextMoveToPoint(context, Mid2 - x0, y0);
								CGContextAddLineToPoint(context, Mid2 - x1, y1)
							end;
						LUD: 
							begin
								CGContextMoveToPoint(context, x0, y0);
								CGContextAddLineToPoint(context, x1, y1);
								CGContextMoveToPoint(context, Mid2 - x0, belly2 - y0);
								CGContextAddLineToPoint(context, Mid2 - x1, belly2 - y1);
							end;
						RUD: 
							begin
								CGContextMoveToPoint(context, Mid2 - x0, y0);
								CGContextAddLineToPoint(context, Mid2 - x1, y1);
								CGContextMoveToPoint(context, x0, belly2 - y0);
								CGContextAddLineToPoint(context, x1, belly2 - y1);
							end;
						FUD: 
							begin
								CGContextMoveToPoint(context, x0, y0);
								CGContextAddLineToPoint(context, x1, y1);
								CGContextMoveToPoint(context, Mid2 - x0, y0);
								CGContextAddLineToPoint(context, Mid2 - x1, y1);
								CGContextMoveToPoint(context, x0, belly2 - y0);
								CGContextAddLineToPoint(context, x1, belly2 - y1);
								CGContextMoveToPoint(context, Mid2 - x0, belly2 - y0);
								CGContextAddLineToPoint(context, Mid2 - x1, belly2 - y1)
							end;
					end; {CASES}
					CGContextStrokePath(context);
				end
		end; {ActualLine}

	begin
		if thisPic^.PicSize = 0 then
			exit;
		CGContextSetStrokeColorWithColor(context, CGColorGetConstantColor(kCGColorBlack));	
		biomorph := box^.denizen^;
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
		CGContextSetLineWidth ( context, MyPenSize);
		Mid2 := 2 * Place.x;
		belly2 := 2 * Place.y;
		WITH ThisPic^ DO
			begin
				MovePtr := First; {reposition at base of grabbed space}
				while MovePtr <> Last do
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
								MovePtr := MovePtr^.next;

						end;
			end;
		CGContextSetLineWidth ( context, 1);
	end; {DrawPic}

end.