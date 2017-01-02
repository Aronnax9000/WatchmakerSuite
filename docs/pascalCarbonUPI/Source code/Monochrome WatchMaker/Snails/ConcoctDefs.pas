unit ConcoctDefs;

interface

	uses Globals, MacOSAll, TriangleDefs, PersonDefs, Miscellaneous;

	procedure Concoct (r1, r2, r3: single; a, b, c: person; VAR new: person);

implementation


PROCEDURE Concoct (r1, r2, r3: single; a, b, c: person; VAR new: person);
	VAR
		hand, j, weight: Integer;
	FUNCTION Force3 (r: single): Integer;
		VAR
			i: Integer;
		BEGIN
			i := round(r);
			IF i > 2 THEN
				i := 2;
			IF i < 0 THEN
				i := 0;
			Force3 := i
		END; {Force3}
	FUNCTION Force2 (r: single): Integer;
		VAR
			i: Integer;
		BEGIN
			i := round(r);
			IF i > 1 THEN
				i := 1;
			IF i < 0 THEN
				i := 0;
			Force2 := i
		END; {Force2}
	BEGIN
		WITH new DO
			BEGIN
				Wopening := exp(r1 * ln(a.Wopening) + r2 * ln(b.Wopening) + r3 * ln(c.Wopening));
				IF WOpening < 1 THEN
					BEGIN
						sysbeep(1);
						Wopening := 1
					END;
				DDisplacement := r1 * a.DDisplacement + r2 * b.DDisplacement + r3 * c.DDisplacement;
				SShape := r1 * a.SShape + r2 * b.SShape + r3 * c.SShape;
				TTranslation := r1 * a.TTranslation + r2 * b.TTranslation + r3 * c.TTranslation;
				TranslationGradient := r1 * a.TranslationGradient + r2 * b.TranslationGradient + r3 * c.TranslationGradient;
				IF TranslationGradient <= 0.1 THEN
					TranslationGradient := 0.1;
				reach := round(r1 * a.reach + r2 * b.reach + r3 * c.reach);
				Coarsegraininess := round(r1 * a.Coarsegraininess + r2 * b.Coarsegraininess + r3 * c.Coarsegraininess);
				hand := round(r1 * a.Coarsegraininess + r2 * b.Coarsegraininess + r3 * c.Coarsegraininess);
				IF hand < 0 THEN
					handedness := -1
				ELSE
					handedness := 1;

			END
	END; {concoct}
		
end.