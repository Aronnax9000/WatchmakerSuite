unit DevelopDefs;

interface

	uses 
		BoxesDefs,
		DrawPicDefs,
		Globals, 
		MacOSAll,
		Miscellaneous,
		ModeDefs,
		MorphGlobals,
		PicDefs,
		PersonDefs;


	PROCEDURE Develop (VAR theShell: person; Where: point);
	procedure Delayvelop (VAR Biomorph: Person; Here: Point);

implementation

PROCEDURE FindTheScale (VAR theScale: single);
	VAR
		targetheight, targetwidth, inheight, inwidth: integer;
		heightscale, widthscale: single;
	BEGIN {fix theScale here based upon Margin and box[midbox]}
		WITH box[midbox] DO
			BEGIN
				targetheight := bottom - top;
				targetwidth := right - left;
				inheight := margin.bottom - margin.top;
				inwidth := margin.right - margin.left;
				heightscale := targetheight / inheight;
				widthscale := targetwidth / inwidth;
				IF heightscale < widthscale THEN
					theScale := heightscale
				ELSE
					theScale := widthScale;
			END;
		theScale := 0.95 * theScale;
	END;

PROCEDURE Develop (VAR theShell: person; Where: point);
	VAR
		centre: point;
		p: single;
		ink, Offset, LeftExtent, RightExtent, Upextent, Downextentheight, width, newheight, newwidth, newtop, newbottom, newleft, newright: integer;

	PROCEDURE DrawTop;
		VAR
			denom, theSize, W, D, S, T, MNX, MNY, RAD, XV1, YV1, XV2, YV2, XU1, YU1, XU2, YU2, X1, Y1, X2, Y2, SX, PI, I, BD, WI: single;
			M, start: INTEGER;
		PROCEDURE GOSUB8000;
			VAR
				XX1, XX2, YY1, YY2: integer;
			BEGIN
				XX1 := round(x1 - MNX);
				XX2 := round(X2 - MNX);
				YY1 := round(Y1 - MNY);
				YY2 := round(Y2 - MNY);
				WITH margin DO
					BEGIN
						IF xx1 < left THEN
							left := xx1;
						IF xx2 > right THEN
							right := xx2;
						IF YY1 < top THEN
							top := YY1;
						IF YY2 > bottom THEN
							bottom := YY2;
					END;
				PicLine(MyPic, XX1, YY1, XX2, YY2, PSize);
			END;
		BEGIN
			WITH theShell DO
				BEGIN
					DGradient := 1;
					W := WOpening;
					D := DDisplacement;
					S := SShape;
					T := TTranslation;
					INC := Coarsegraininess;
					start := reach * 360;
					rad := 100;
				END;
			theSize := 0.74;
			denom := 136 * theSize;
			mny := (-(100 / denom) * where.v);
{the smaller or more negative the number,the lower down the page}
{mxy := 90;}
			mnx := (-(100 / denom) * where.h);

			XV1 := 0;
			Yv1 := 0;
			xU1 := 0;
			YU1 := 0;
			PI := 3.14159;
			m := start;
			REPEAT
				p := (start - (start - m) * (1 - theShell.DGradient)) / start;
				D := theShell.DDisplacement * p;
				i := m / 360;
				BD := 2 * PI * I;
				WI := RAD * EXP(-I * LN(W));
				XV2 := theShell.handedness * (-WI * COS(BD));
				YV2 := WI * SIN(BD);
				XU2 := XV2 * D;
				YU2 := YV2 * D;
				X1 := XV1;
				X2 := XV2;
				Y1 := YV1;
				Y2 := YV2;
				GOSUB8000;
				X1 := X2;
				X2 := XU2;
				Y1 := Y2;
				Y2 := YU2;
				IF m <= 0 THEN
					GOSUB8000;
				X1 := X2;
				X2 := XU1;
				Y1 := Y2;
				Y2 := YU1;
				GOSUB8000;
				XV1 := XV2;
				YV1 := YV2;
				XU1 := XU2;
				YU1 := YU2;
				m := m - inc;
			UNTIL m < 0;
		END;
	PROCEDURE Drawshell;
		VAR
			rad1, rad2, r, rad, Twopi, i, fw, bd, W, D, S, T, gradient, p: single;
{r, rad, mny, mnx,}
			m, inc, Start: integer;
			grunge, temp, f, g, h, k, xc, yc, xr, yr, mnx, mny, siz: single;
			theRect: rect;
			myPicture: PicHandle;
			denom, theSize: single;
		PROCEDURE swap (VAR a, b: longint);
			VAR
				temp: longint;
			BEGIN
				temp := a;
				a := b;
				b := temp
			END; {swap}
		PROCEDURE SetUp;
			BEGIN
				theSize := 0.8;
				denom := 136 * theSize;
				WITH theShell DO
					BEGIN
						W := WOpening;
						D := DDisplacement;
						S := SShape;
						T := TTranslation;
						r := 3;
						rad := 100;
						Twopi := 2 * 3.14159;
						mny := round(-(100 / denom) * where.v * 1.088);
						mnx := round(-(100 / denom) * where.h * 1.088);
						rad1 := 1.088 * (rad + rad * D) / 2;
						rad2 := 1.088 * (rad - rad * D) / 2;
						IF CoarseGraininess < 1 THEN
							Coarsegraininess := 1;
						inc := Coarsegraininess;
						IF reach < 1 THEN
							reach := 1;
						start := reach * 360;
					END; {with theShell}
				centre := where;
				ClipBoarding := FALSE;
				m := start;
			END; {SetUp}

		BEGIN {main procedure Drawshell}
			SetUp;
			WITH theShell DO
				REPEAT
					p := (start - (start - m) * (1 - TranslationGradient)) / start;
					T := TTranslation * p;
					i := m / 360;
					fw := exp(-i * ln(W));
					grunge := fw * cos(TwoPi * i);

					xc := handedness * (rad1 * grunge);
					yc := -rad1 * T * (1 - fw);
					xr := handedness * (rad2 * grunge);
					yr := -rad2 * fw * S;
{the minus signs are to invert the whole snail}

					h := (yc - yr - mny);
					g := (xc - xr - mnx);
					f := (yc + yr - mny);
					k := (xc + xr - mnx);


					WITH theRect DO
						BEGIN
							top := round(f);
							bottom := round(h);
							left := round(g);
							right := round(k);
							IF left <= right THEN
								BEGIN
									IF left < margin.left THEN
										margin.left := left;
									IF right > margin.right THEN
										margin.right := right;
								END
							ELSE
								BEGIN
									IF right < margin.left THEN
										margin.left := right;
									IF left > margin.right THEN
										margin.right := left;
								END;
							IF top < margin.top THEN
								margin.top := top;
							IF bottom > margin.bottom THEN
								margin.bottom := bottom;
						END;
					BEGIN
						IF NOT DontDraw THEN
							WITH theRect DO
								PicLine(MyPic, left, top, right, bottom, PSize);
					END; {DontDraw is set by Pedigree so only Margin is measured, no drawing}
					m := m - inc;
				UNTIL m <= 0;
		END; {DrawShell}

	BEGIN {develop}
		SetCursor(CursList[watchCursor]^^);
		ZeroPic(MyPic, where);
		IF zeromargin THEN
			WITH margin DO
				BEGIN
					left := Where.h;
					right := Where.h;
					top := Where.v;
					bottom := Where.v;
				END;
		IF SideView THEN
			DrawShell
		ELSE
			DRAWTOP;
		FindTheScale(theScale);
		WITH Margin DO
			BEGIN
				height := bottom - top;
				width := right - left;
				newheight := round(height * theScale);
				newwidth := round(width * thescale);
				newtop := top + (height - newheight) DIV 2;
				newbottom := newtop + newheight;
				newleft := left + (width - newwidth) DIV 2;
				newright := newleft + newwidth;
			END;
		setrect(Margin, newleft, newtop, newright, newbottom);
		WITH Margin DO
			BEGIN
				centre.v := top + (bottom - top) DIV 2;
				offset := centre.v - where.v;
				Top := Top - Offset;
				Bottom := Bottom - Offset;
				OffCentre.v := Where.v - Offset;
				centre.h := left + (right - left) DIV 2;
				offset := centre.h - where.h;
				left := left - Offset;
				right := right - Offset;
				OffCentre.h := Where.h - Offset;
			END;
		IF NOT DelayedDrawing THEN
			DrawPic(MyPic, offCentre, theShell);
	END; {Develop}

PROCEDURE Delayvelop (VAR Biomorph: Person; Here: Point);
	BEGIN
		Zeromargin := TRUE;
		DelayedDrawing := false;
		develop(Biomorph, Here);
	END; {Delayvelop}


end.