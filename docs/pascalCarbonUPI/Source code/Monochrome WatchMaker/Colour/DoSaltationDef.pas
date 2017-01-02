unit DoSaltationDef;

interface

	uses 
		BoxesDefs,
		DevelopDefs,
		GeneboxDefs,
		Globals,
		MacOSAll,
		Miscellaneous,
		ModeDefs,
		MorphGlobals,
		PersonDefs,
		RandSwellDef;

	procedure DoSaltation;


implementation

	procedure DoSaltation;
		VAR
			j, maxgene, r: Integer;
			factor: -1..1;
			mainWindowBounds: Rect;
		begin
			DelayedDrawing := FALSE;
			special := MidBox;
			WITH child[special] DO {bomb 5, range check failed, here after killing top Adam}
				begin
					IF Mut[1] THEN
						begin
							SegNoGene := randint(6);
							SegDistGene := randint(20);
						end
					ELSE
						begin
							SegNoGene := 1;
							SegDistGene := 1
						end;
					r := randint(100);
					CompletenessGene := CompletenessTypeDouble;
					IF Mut[3] THEN
						IF r < 50 THEN
							CompletenessGene := CompletenessTypeSingle
						ELSE
							CompletenessGene := CompletenessTypeDouble;
					r := randint(100);
					IF Mut[4] THEN
						begin
							IF r < 33 THEN
								SpokesGene := Radial
							ELSE IF r < 66 THEN
								SpokesGene := NSouth
							ELSE
								SpokesGene := NorthOnly
						end
					ELSE
						SpokesGene := NorthOnly;
					IF Mut[5] THEN
						begin
							TrickleGene := 1 + randint(100) DIV 10;
							IF TrickleGene > 1 THEN
								MutSizeGene := Tricklegene DIV 2
						end;
					FOR j := 1 TO 8 DO
						REPEAT
							gene[j] := MutSizeGene * (randint(19) - 10);
							IF Mut[2] THEN
								dGene[j] := RandSwell(dgene[j])
							ELSE
								dGene[j] := Same;
							CASE dGene[j] OF
								Shrink: 
									factor := 1;
								Same: 
									factor := 0;
								Swell: 
									factor := 1;
							end; {Cases}
							maxgene := gene[j] * SegNoGene * factor;
						UNTIL (maxgene <= 9 * Tricklegene) AND (maxgene >= -9 * Tricklegene);
					REPEAT
						IF Mut[8] THEN
							dGene[9] := RandSwell(dgene[9])
						ELSE
							dGene[9] := Same;
						IF Mut[2] THEN
							dGene[10] := RandSwell(dgene[9])
						ELSE
							dGene[10] := Same;
						CASE dGene[j] OF
							Shrink: 
								factor := 1;
							Same: 
								factor := 0;
							Swell: 
								factor := 1;
						end; {Cases}
						maxgene := SegDistGene * SegNoGene * factor;
					UNTIL (maxgene <= 100) AND (maxgene >= -100);
					REPEAT
						Gene[9] := randint(6)
					UNTIL Gene[9] > 1;
				end;
			EraseRect(GetWindowPortBounds(MainWindow, mainWindowBounds)^);
			Develop(child[special], centre[midBox]);
			MakeGeneBox(child[special], MainWindow);
			SetMode(MainWindow, Randoming);
		end; {DoSaltation}
	
end.