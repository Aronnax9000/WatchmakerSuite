unit BiomorphsGenebox;

interface

uses MacOSAll;

	procedure GeneBoxTemplate;
	procedure ShowGeneBox (j: INTEGER; biomorph: person);
	procedure ShowChangedGene (an1, an2: person);
	procedure MakeGeneBox (biomorph: person);

implementation

	var
		width: integer;



	procedure drawint (i: integer);
		procedure drawi (i: INTEGER);
			var
				l, r: integer;
		begin
			if i <= 9 then
				drawchar(chr(ord('0') + i))
			else
				begin
					l := i div 10;
					r := i - 10 * l;
					drawi(l);
					drawi(r);
				end;
		end; {drawi}

	begin {drawint proper}
{textSize(9);}
		if i < 0 then
			begin
				drawchar('-');
				i := abs(i);
			end;
{Drawchar('+');}
		drawi(i);
{textsize(12);}
	end; {drawint}


	procedure GeneBoxTemplate;
		var
			j: INTEGER;
	begin
		width := (Prect.right - Prect.left) div GeneCount;
		with GeneBox[1] do
			begin
				left := box[1].left;
				right := left + width + 3;
				top := Prect.Top;
				bottom := top + GenesHeight;
				EraseRect(GeneBox[1]);
				Framerect(GeneBox[1])
			end;
		for j := 2 to 11 do
			with GeneBox[j] do
				begin
					top := PRect.top;
					bottom := top + GenesHeight;
					left := GeneBox[j - 1].right;
					right := left + width + 3;
					EraseRect(GeneBox[j]);
					Framerect(GeneBox[j])
				end;
		for j := 12 to 13 do
			with GeneBox[j] do
				begin
					top := PRect.top;
					bottom := top + GenesHeight;
					left := GeneBox[j - 1].right;
					right := left + width + 7;
					EraseRect(GeneBox[j]);
					Framerect(GeneBox[j])
				end;
		for j := 14 to 26 do
			with GeneBox[j] do
				begin
					top := PRect.top;
					bottom := top + GenesHeight;
					left := GeneBox[j - 1].right;
					right := left + width - 2;
					EraseRect(GeneBox[j]);
					Framerect(GeneBox[j])
				end;
		with GeneBox[GeneCount] do
			begin
				top := PRect.top;
				bottom := top + GenesHeight;
				left := GeneBox[GeneCount - 1].right;
				right := left + width - 2;
				EraseRect(GeneBox[GeneCount]);
				Framerect(GeneBox[GeneCount])
			end;
		HIWindowFlush(BreedWin);

	end; {GeneBoxTemplate}

	procedure ShowGeneBox (j: INTEGER; biomorph: person);
		var
			thestring: str255;
			LittleRect: Rect;
			thePort: GrafPtr;
			tempPattern: Pattern;
	begin
		SetPortWindowPort(BreedWin);
		textSize(9);
{cure system font bug}
		if (j > GeneCount) or (j < 1) then
			begin
				sysbeep(1);
				ExitToShell;
			end;
		with GeneBox[j] do
			begin
				EraseInnerRect(GeneBox[j]);
				MoveTo(left - 7 + width div 2, top + 14);
				case j of
					1..9: 
						begin {DrawInt(biomorph.gene[j]);}
							Numtostring(biomorph.gene[j], thestring);
							MoveTo(left + (width - stringwidth(thestring)) div 2, top + 14);
							Drawstring(thestring);
							case biomorph.dGene[j] of
								Shrink: 
									begin
										MoveTo(left + 2, top + 21);
										DrawChar(chr(165))
									end;
								Same: 
									;
								Swell: 
									begin
										MoveTo(left + 2, top + 7);
										DrawChar(chr(165))
									end;
							end; {dGene cases}
						end; {1..9}
					10: 
						DrawInt(biomorph.SegNoGene);
					11: 
						begin
							DrawInt(biomorph.SegDistGene);
							case biomorph.dGene[10] of
								Shrink: 
									begin
										MoveTo(left + 2, top + 21);
										DrawChar(chr(165))
									end;
								Same: 
									;
								Swell: 
									begin
										MoveTo(left + 2, top + 7);
										DrawChar(chr(165))
									end;
							end; {dGene cases}
						end;
					12: 
						begin
							MoveTo(left + 2, top + 14);
							case Biomorph.CompletenessGene of
								Single: 
									Drawstring('Asym');
								Double: 
									Drawstring('Bilat')
							end
						end;
					13: 
						begin
							MoveTo(left + 2, Top + 14);
							case Biomorph.SpokesGene of
								NorthOnly: 
									DrawString('Single');
								NSouth: 
									DrawString('UpDn');
								Radial: 
									DrawString('Radial')
							end
						end;
					14: 
						DrawInt(biomorph.tricklegene);
					15: 
						DrawInt(biomorph.MutSizegene);
					16: 
						Drawint(biomorph.MutProbGene);
					17..24: 
						begin
							with GeneBox[j] do
								begin
									BackColor(WhiteColor);
									EraseRect(GeneBox[j]);
									FrameRect(GeneBox[j]);
									textfont(kFontIDGeneva);
									MoveTo(left + 2, top + 14);
									drawint(Biomorph.Colorgene[j - 16]);
								end;
						end;
					25: 
						begin
							Pensize(Biomorph.ThicknessGene, Biomorph.ThicknessGene);
							MoveTo(Left + 1 - Biomorph.ThicknessGene + (right - left) div 2, bottom - Biomorph.ThicknessGene);
							LineTo(Left + (right - left) div 2, bottom - (bottom - top) div 2);
							PenSize(1, 1);
							SetRect(LittleRect, left + (right - left) div 3, top + (bottom - top) div 6, right - (right - left) div 3, top + (bottom - top) div 2);
							case Biomorph.LimbShapeGene of
								Stick: 
									begin
										MoveTo(Left + (right - left) div 2, top + (bottom - top) div 2);
										LineTo(Left + 1, Top);
										MoveTo(Left + (right - left) div 2, bottom - (bottom - top) div 2);
										LineTo(Right - 1, Top);
									end;
								Oval: 
									begin
										PenSize(1, 1);
										FrameOval(LittleRect);
										if Biomorph.LimbFillGene = Filled then
											FillOval(LittleRect, GetQDGlobalsBlack(tempPattern)^);
									end;
								Rectangle: 
									begin
										Pensize(1, 1);
										FrameRect(LittleRect);
										if Biomorph.LimbFillGene = Filled then
											FillOval(LittleRect, GetQDGlobalsBlack(tempPattern)^);
									end;
							end; {Limb Cases}
						end;
					26: 
						begin
							with GeneBox[j] do
								begin
									BackColor(WhiteColor);
									EraseRect(GeneBox[j]);
									FrameRect(GeneBox[j]);
									textfont(kFontIDGeneva);
									MoveTo(left + 2, top + 14);
									drawint(Biomorph.BackColorGene);
								end;
						end;
				end; {Gene Cases}
			end; {WITH GeneBox}
		textsize(12);
		HIWindowFlush(BreedWin);

	end; {ShowGeneBox}


	procedure ShowChangedGene (an1, an2: person);
		var
			k: INTEGER;
	begin
		if OldBox > 0 then
			begin
				for k := 1 to 9 do
					if (an1.gene[k] <> an2.gene[k]) or (an1.dgene[k] <> an2.dgene[k]) then
						ShowGeneBox(k, an1);
				for k := 17 to 24 do
					if an1.ColorGene[k - 16] <> an2.ColorGene[k - 16] then
						ShowGeneBox(k, an1);
				if (an1.LimbShapeGene <> an2.LimbShapeGene) or (an1.LimbFillGene <> an2.LimbFillGene) or (an1.ThicknessGene <> an2.ThicknessGene) then
					ShowGeneBox(25, an1);
				if an1.BackColorGene <> an2.BackColorGene then
					ShowGeneBox(26, an1);
				if (an1.dgene[10] <> an2.dgene[10]) then
					ShowGeneBox(k, an1);
				if an1.SegNoGene <> an2.SegNoGene then
					ShowGeneBox(10, an1);
				if (an1.SegDistGene <> an2.SegDistGene) or (an1.dgene[10] <> an2.dgene[10]) then
					ShowGeneBox(11, an1);
				if an1.CompletenessGene <> an2.CompletenessGene then
					ShowGeneBox(12, an1);
				if an1.SpokesGene <> an2.SpokesGene then
					ShowGeneBox(13, an1);
				if an1.TrickleGene <> an2.TrickleGene then
					ShowGeneBox(14, an1);
				if an1.MutSizeGene <> an2.MutSizeGene then
					ShowGeneBox(15, an1);
				if an1.MutProbGene <> an2.MutProbGene then
					ShowGeneBox(16, an1);
			end
	end; {ShowChangedGene}


	procedure MakeGeneBox (biomorph: person);
		var
			j: INTEGER;
	begin
		GeneBoxTemplate;
		for j := 1 to GeneCount do
			ShowGeneBox(j, biomorph);
	end; {MakeGeneBox}

end.
