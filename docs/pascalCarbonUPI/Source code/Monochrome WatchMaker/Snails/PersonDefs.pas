unit PersonDefs;

interface

uses MacOSAll;

type
	
	person = RECORD
			WOpening: single; 
			DDisplacement: single; 
			SShape: single; 
			TTranslation: single;
			Coarsegraininess: SInt16; 
			Reach: integer; 
			GeneratingCurve: SInt16;
			TranslationGradient: single; 
			DGradient: single;
			Handedness: -1..1;
		END;

	PersonPtr = ^Person;

	MarchingOrders = RECORD
			Start, By, Till: single;
			Kind: (Wop, Dis, Trans);
		END;
	

implementation


end.