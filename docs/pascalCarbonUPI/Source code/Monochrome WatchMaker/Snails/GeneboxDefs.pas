unit GeneboxDefs;

interface
	uses BoxesDefs, MacOSAll, PersonDefs, Globals, RectDrawDefs;

	var
		GeneBox: ARRAY[1..16] OF Rect;


	procedure MakeGeneBox (biomorph: person; theWindow: WindowRef);
	procedure ShowGeneBox (j: Integer; biomorph: person);
	procedure ShowChangedGene (an1, an2: person);

implementation

var
	geneBoxWidth: integer;



PROCEDURE DrawNumber (n: LongInt);
	VAR
		s: str255;
	BEGIN
		NumToString(n, s);
		Drawstring(s);
	END;



PROCEDURE DrawReal (r: single);
	VAR
		numerator, remainder, den1, den2: LongInt;
		theString: str255;
	BEGIN
		IF r < 0 THEN
			BEGIN
				DrawString('-');
				r := -r
			END;
		numerator := trunc(r);
		DrawNumber(numerator);
		remainder := round(100 * (r - numerator));
		Drawstring('.');
		IF remainder < 10 THEN
			BEGIN
				den1 := 0;
				den2 := remainder
			END
		ELSE
			BEGIN
				den1 := remainder DIV 10;
				den2 := remainder - 10 * den1
			END;
		DrawNumber(den1);
		DrawNumber(den2);
	END;


PROCEDURE GeneBoxTemplate;
	VAR
		j: Integer;
	BEGIN
		geneBoxWidth := (Prect.right - Prect.left) DIV 5;
		WITH GeneBox[1] DO
			BEGIN
				left := box[1].left;
				right := left + geneBoxWidth;
				top := Prect.Top;
				bottom := top + GenesHeight;
				EraseRect(GeneBox[1]);
				Framerect(GeneBox[1]);
			END;
		FOR j := 2 TO 5 DO
			WITH GeneBox[j] DO
				BEGIN
					top := PRect.top;
					bottom := top + GenesHeight;
					left := GeneBox[j - 1].right;
					right := left + geneBoxWidth;
					EraseRect(GeneBox[j]);
					Framerect(GeneBox[j])
				END;
	END; {GeneBoxTemplate}

PROCEDURE ShowGeneBox (j: Integer; biomorph: person);
	VAR
		theString: str255;
	BEGIN
		WITH biomorph DO
			IF j > 0 THEN
				WITH GeneBox[j] DO
					BEGIN
						EraseInnerRect(GeneBox[j]);
						MoveTo(left - 8 + geneBoxWidth DIV 2, top + 14);
						CASE j OF
							1: 
								BEGIN
									DrawReal(WOpening);
									MoveTo(Left, top + 14);
									Drawstring('   W');
									MoveTo(left + (geneBoxWidth - stringwidth(thestring)) DIV 2, top + 14);
								END;
							2: 
								BEGIN
									DrawReal(DDisplacement);
									MoveTo(Left, top + 14);
									Drawstring('   D');
									MoveTo(left + (geneBoxWidth - stringwidth(thestring)) DIV 2, top + 14);
								END;
							3: 
								BEGIN
									DrawReal(SShape);
									MoveTo(Left, top + 14);
									Drawstring('   S ');
									MoveTo(left + (geneBoxWidth - stringwidth(thestring)) DIV 2, top + 14);
								END;
							4: 
								BEGIN
									DrawReal(TTranslation);
									MoveTo(Left, top + 14);
									Drawstring('   T');
									MoveTo(left + (geneBoxWidth - stringwidth(thestring)) DIV 2, top + 14);
								END;
						END; {cases}
					END; {with}
	END; {ShowGeneBox}


PROCEDURE ShowChangedGene (an1, an2: person);
	BEGIN
		IF OldBox > 0 THEN
			BEGIN
				IF an1.Wopening <> an2.Wopening THEN
					ShowGeneBox(1, an1);
				IF an1.DDisplacement <> an2.DDisplacement THEN
					ShowGeneBox(2, an1);
				IF an1.SShape <> an2.SShape THEN
					ShowGeneBox(3, an1);
				IF an1.TTranslation <> an2.TTranslation THEN
					ShowGeneBox(4, an1);
			END;
	END; {ShowChangedGene}

	procedure MakeGeneBox (biomorph: person; theWindow: WindowRef);
	VAR
		j: Integer;
	BEGIN
		GeneBoxTemplate;
		FOR j := 1 TO 4 DO
			ShowGeneBox(j, biomorph);
	END; {MakeGeneBox}



end.