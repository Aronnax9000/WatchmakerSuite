unit InitializeAnimalsDefs;

interface

	uses Globals,  MacOsAll, MorphGlobals, PersonDefs;

	procedure InitializeAnimals;

	FUNCTION snail: person;
	FUNCTION Turritella: person;
	FUNCTION Bivalve: person;
	FUNCTION Ammonite: person;
	FUNCTION Nautilus: person;
	FUNCTION Brachiopod: person;
	FUNCTION Cone: person;
	FUNCTION Whelk: person;
	FUNCTION Scallop: person;
	FUNCTION Eloise: person;
	FUNCTION Gallaghers: person;
	FUNCTION Rapa: person;
	FUNCTION Lightning: person;
	FUNCTION Sundial: person;
	FUNCTION Fig: person;
	FUNCTION Tun: person;
	FUNCTION RazorShell: person;
	FUNCTION JapaneseWonder: person;

implementation

	FUNCTION snail: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.66;
					DDisplacement := 0;
					SShape := 1.2;
					TTranslation := 2;
					Coarsegraininess := 4;
					reach := 5;
					GeneratingCurve := 0;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			snail := theShell;
		END;

	FUNCTION Turritella: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.30;
					DDisplacement := 0;
					SShape := 1;
					TTranslation := 8.2;
					Coarsegraininess := 8;
					reach := 10;
					GeneratingCurve := 0;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Turritella := theShell;
		END;

	FUNCTION Bivalve: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1000;
					DDisplacement := 0;
					SShape := 1.2;
					TTranslation := 0.5;
					MutProb := 50;
					Coarsegraininess := 2;
					reach := 1;
					GeneratingCurve := 0;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Bivalve := theShell;
		END;

	FUNCTION Ammonite: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 2;
					DDisplacement := 0;
					SShape := 1;
					TTranslation := 0;
					Coarsegraininess := 8;
					reach := 3;
					GeneratingCurve := 0;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Ammonite := theShell;
		END;

	FUNCTION Nautilus: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 3.4;
					DDisplacement := 0;
					SShape := 1.2;
					TTranslation := 0;
					Coarsegraininess := 8;
					reach := 3;
					GeneratingCurve := 0;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Nautilus := theShell;
		END;

	FUNCTION Brachiopod: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 10000;
					DDisplacement := 0;
					SShape := 1;
					TTranslation := 0;
					Coarsegraininess := 2;
					reach := 3;
					GeneratingCurve := 0;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Brachiopod := theShell;
		END;

	FUNCTION Cone: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.66;
					DDisplacement := 0;
					SShape := 3;
					TTranslation := 3.5;
					Coarsegraininess := 2;
					reach := 3;
					GeneratingCurve := 128;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Cone := theShell;
		END;

	FUNCTION Whelk: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.7;
					DDisplacement := 0;
					SShape := 2;
					TTranslation := 4;
					Coarsegraininess := 2;
					reach := 6;
					GeneratingCurve := 128;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Whelk := theShell;
		END;

	FUNCTION Scallop: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 10000;
					DDisplacement := 0;
					SShape := 1;
					TTranslation := 0;
					Coarsegraininess := 2;
					reach := 3;
					GeneratingCurve := 148;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Scallop := theShell;
		END;

	FUNCTION Eloise: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.4;
					DDisplacement := 0;
					SShape := 1.7;
					TTranslation := 3.5;
					Coarsegraininess := 4;
					reach := 6;
					GeneratingCurve := 146;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Eloise := theShell;
		END;

	FUNCTION Gallaghers: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.66;
					DDisplacement := 0;
					SShape := 1.8;
					TTranslation := 5;
					Coarsegraininess := 4;
					reach := 6;
					GeneratingCurve := 136;
					Handedness := -1;
					TranslationGradient := 1;
				END;
			Gallaghers := theShell;
		END;

	FUNCTION Rapa: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.66;
					DDisplacement := 0;
					SShape := 2;
					TTranslation := 2.2;
					Coarsegraininess := 4;
					reach := 9;
					GeneratingCurve := 132;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Rapa := theShell;
		END;

	FUNCTION Lightning: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.66;
					DDisplacement := 0;
					SShape := 3.5;
					TTranslation := 4;
					Coarsegraininess := 4;
					reach := 6;
					GeneratingCurve := 150;
					Handedness := -1;
					TranslationGradient := 0.9;
				END;
			Lightning := theShell;
		END;

	FUNCTION Sundial: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.384;
					DDisplacement := 0.261;
					SShape := 0.618;
					TTranslation := 1.055;
					Coarsegraininess := 2;
					reach := 10;
					GeneratingCurve := 152;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Sundial := theShell;
		END;

	FUNCTION Fig: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 2;
					DDisplacement := 0;
					SShape := 3;
					TTranslation := 3.5;
					Coarsegraininess := 2;
					reach := 8;
					GeneratingCurve := 134;
					Handedness := 1;
					TranslationGradient := 0.95;
				END;
			Fig := theShell;
		END;

	FUNCTION Tun: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 2;
					DDisplacement := 0;
					SShape := 2;
					TTranslation := 2.8;
					Coarsegraininess := 2;
					reach := 8;
					GeneratingCurve := 134;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			Tun := theShell;
		END;

	FUNCTION RazorShell: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1000;
					DDisplacement := 0;
					SShape := 8;
					TTranslation := 6;
					MutProb := 50;
					Coarsegraininess := 2;
					reach := 1;
					GeneratingCurve := 138;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			RazorShell := theShell;
		END;

	FUNCTION JapaneseWonder: person;
		VAR
			theShell: person;
		BEGIN
			WITH theShell DO
				BEGIN
					WOpening := 1.7;
					DDisplacement := 0;
					SShape := 1.3;
					TTranslation := 4.2;
					MutProb := 50;
					Coarsegraininess := 2;
					reach := 8;
					GeneratingCurve := 130;
					Handedness := 1;
					TranslationGradient := 1;
				END;
			JapaneseWonder := theShell;
		END;



	procedure InitializeAnimals;
		begin
			{set up default triangle anchors}
			Topan := snail;
			Leftan := Turritella;
			Rightan := bivalve;
		end;
end.