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
		ReproduceDef {for direction function}
		;

	procedure DoSaltation;


implementation

FUNCTION Randreal (Max: single): single;
	VAR
		rint: integer;
		r: single;
	BEGIN
		rint := abs(random);
		r := rint / 32767;
		randreal := r * max;
	END; {randreal}


PROCEDURE DoSaltation;
	BEGIN
		DelayedDrawing := FALSE;
		special := MidBox;
		WITH child[special] DO
			BEGIN
				WOpening := exp(randreal(4));
				DDisplacement := randreal(1);
				SShape := randreal(2);
				TTranslation := randreal(4);
				MutProb := 50;
				GeneratingCurve := 0;
				Handedness := Direction;
				TranslationGradient := randreal(1.3);
				CoarseGraininess := randint(4);
				reach := Randint(4);
			END;
		EraseRect(Prect);
		Develop(child[special], centre[midBox]);
{MakeGeneBox(child[MidBox]);}
		TheMode := Randoming;
	END; {DoSaltation}	
end.