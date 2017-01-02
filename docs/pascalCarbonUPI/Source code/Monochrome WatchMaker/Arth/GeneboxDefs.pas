unit GeneboxDefs;

interface
	uses MacOSAll, PersonDefs, Globals, RectDrawDefs, BoxesDefs;

	var
		GeneBox: ARRAY[1..16] OF Rect;


	procedure MakeGeneBox (biomorph: person; theWindow: WindowRef);
	procedure ShowGeneBox (j: Integer; biomorph: person);
	procedure ShowChangedGene (an1, an2: person);

implementation

var
	geneBoxWidth: integer;

	procedure DrawInt (i: Integer);
		procedure Drawi (i: Integer);
			VAR
				l, r: Integer;
			begin
				IF i <= 9 THEN
					drawchar(chr(ord('0') + i))
				ELSE
					begin
						l := i DIV 10;
						r := i - 10 * l;
						drawi(l);
						drawi(r);
					end;
			end; {drawi}

		begin {drawint proper}
			IF i < 0 THEN
				begin
					drawchar('-');
					i := abs(i);
				end
			ELSE
				Drawchar('+');
			drawi(i);
		end; {drawint}


	procedure GeneBoxTemplate(theWindow: WindowRef);
		VAR
			j: Integer;
			boundsRect: Rect;
		begin
			GetWindowPortBounds(theWindow, boundsRect);
			geneBoxWidth := (boundsRect.right - boundsRect.left) DIV 16;
			WITH GeneBox[1] DO
				begin
					left := box[1].left;
					right := left + geneBoxWidth;
					top := boundsRect.Top;
					bottom := top + GenesHeight;
					EraseRect(GeneBox[1]);
					Framerect(GeneBox[1])
				end;
			FOR j := 2 TO 16 DO
				WITH GeneBox[j] DO
					begin
						top := boundsRect.top;
						bottom := top + GenesHeight;
						left := GeneBox[j - 1].right;
						right := left + geneBoxWidth;
						EraseRect(GeneBox[j]);
						Framerect(GeneBox[j])
					end;
		end; {GeneBoxTemplate}

	procedure MakeGeneBox (biomorph: person; theWindow: WindowRef);
		VAR
			j: Integer;
			boundsRect: Rect;
		begin
			GetWindowPortBounds(theWindow, boundsRect);
			GeneBoxTemplate(theWindow);
			FOR j := 1 TO 16 DO
				ShowGeneBox(j, biomorph);
		end; {MakeGeneBox}

	procedure ShowGeneBox (j: Integer; biomorph: person);
		VAR
			thestring: str255;
		begin
			WITH GeneBox[j] DO
				begin
					EraseInnerRect(GeneBox[j]);
					MoveTo(left - 8 + geneBoxWidth DIV 2, top + 14);
					CASE j OF
						1..9: 
							begin {DrawInt(biomorph.gene[j]);}
								Numtostring(biomorph.gene[j], thestring);
								MoveTo(left + (geneBoxWidth - stringwidth(thestring)) DIV 2, top + 14);
								Drawstring(thestring);
								CASE biomorph.dGene[j] OF
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
								CASE biomorph.dGene[10] OF
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
								CASE Biomorph.CompletenessGene OF
									CompletenessTypeSingle: 
										Drawstring(AsymString);
									CompletenessTypeDouble: 
										Drawstring(BilatString)
								end
							end;
						13: 
							begin
								MoveTo(left + 2, Top + 14);
								CASE Biomorph.SpokesGene OF
									NorthOnly: 
										DrawString(SingleString);
									NSouth: 
										DrawString(UpDnString);
									Radial: 
										DrawString(RadialString);
								end
							end;
						14: 
							DrawInt(biomorph.tricklegene);
						15: 
							DrawInt(biomorph.MutSizegene);
						16: 
							Drawint(biomorph.MutProbGene);
					end; {Gene Cases}
				end; {WITH GeneBox}
		end; {ShowGeneBox}


	procedure ShowChangedGene (an1, an2: person);
		VAR
			k: Integer;
		begin
			IF OldBox > 0 THEN
				begin
					FOR k := 1 TO 9 DO
						IF (an1.gene[k] <> an2.gene[k]) OR (an1.dgene[k] <> an2.dgene[k]) THEN
							ShowGeneBox(k, an1);
					IF (an1.dgene[10] <> an2.dgene[10]) THEN
						ShowGeneBox(k, an1);
					IF an1.SegNoGene <> an2.SegNoGene THEN
						ShowGeneBox(10, an1);
					IF (an1.SegDistGene <> an2.SegDistGene) OR (an1.dgene[10] <> an2.dgene[10]) THEN
						ShowGeneBox(11, an1);
					IF an1.CompletenessGene <> an2.CompletenessGene THEN
						ShowGeneBox(12, an1);
					IF an1.SpokesGene <> an2.SpokesGene THEN
						ShowGeneBox(13, an1);
					IF an1.TrickleGene <> an2.TrickleGene THEN
						ShowGeneBox(14, an1);
					IF an1.MutSizeGene <> an2.MutSizeGene THEN
						ShowGeneBox(15, an1);
					IF an1.MutProbGene <> an2.MutProbGene THEN
						ShowGeneBox(16, an1);
				end
		end; {ShowChangedGene}


end.