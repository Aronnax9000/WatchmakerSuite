unit MorphGlobals;

interface

	uses 
		MacOSAll;
	const

		BypassGeneratingCurves = true;

		NibFileNameSansExtension = 'Snails';
	
	
		MaxResources = 154;
		DMutsize = 0.01;
		PSize = 1;
		SMutSize = 0.1;
		TMutSize = 0.1;
		MutTypeNo = 7;
		
		{*** Menu Constants ***}
		MenuCnt = 9; {Was 8, now 9 because of OS X "Window" menu - ABC}

		kWatchMenuIndexApple = 1;    { index into MenuList for Apple Menu }
		kWatchMenuIndexFile = 2;    { ditto for File Menu                }
		kWatchMenuIndexEdit = 3;
		kWatchMenuIndexOperation = 4;    { ditto for Operation Menu             }
		kWatchMenuIndexBox = 5;    { ditto for Boxes Menu                }
		kSnailMenuIndexAnimal = 6;    { ditto for Mutations Menu}
		kWatchMenuIndexPedigree = 7;    { ditto for Pedigree Menu}
		kWatchMenuIndexWindow = 8; { ditto for Window menu (Created in carbon, not loaded from resource - ABC)}
		kWatchMenuIndexHelp = 9;	{W for Help Menu}

		kWatchMenuApple = 1000;
		kWatchMenuFile = 1001;
		kWatchMenuEdit = 1002;
		kWatchMenuOper = 1004;
		kSnailMenuAnimal = 1006;
		kWatchMenuPedigree = 1007;
		kWatchMenuBox = 1005;
		kWatchMenuHelp = 3238;
		kWatchMenuSpec = 21537;
		
		kSnailMenuAppleAbout = 1;
		
		kSnailMenuFileLoadAlbum = 1;
		kSnailMenuFileLoadFossils = 2;
		kSnailMenuFileSaveBiomorph = 3;
		kSnailMenuFileSaveFossils = 4;
		kSnailMenuFileSaveAlbum = 5;
		kSnailMenuFileCloseAlbum = 6;
		kSnailMenuFileQuit = 7;

		kSnailMenuEditUndo = 1;
		kSnailMenuEditCut = 3;
		kSnailMenuEditCopy = 4;
		kSnailMenuEditPaste = 5;
		kSnailMenuEditClear = 6;
		kSnailMenuEditHighlightBiom = 8;
		kSnailMenuEditAddBiom = 9;
		kSnailMenuEditShowAlbum = 10;
		kSnailMenuEditImportPICT = 11;

		kSnailMenuOperBreed = 1;
		kSnailMenuOperDrift = 2;
		kSnailMenuOperEngineering = 3;
		kSnailMenuOperHopeMonster = 4;
		kSnailMenuOperInitFossRec = 5;
		kSnailMenuOperPlayFossil = 6;
		kSnailMenuOperRecordFossil = 7;
		kSnailMenuOperTriangle = 8;
		kSnailMenuOperArray = 9;

		kWatchMenuBoxMoreRows = 1;
		kWatchMenuBoxFewerRows = 2;
		kWatchMenuBoxMoreColumns = 3;
		kWatchMenuBoxFewerColumns = 4;
		kSnailMenuBoxSideView = 5;
		kWatchMenuBoxDriftSweep = 6;
		kWatchMenuBoxTriangleTop = 7;
		kWatchMenuBoxTriangleLeft = 8;
		kWatchMenuBoxTriangleRight = 9;
		kWatchMenuBoxViewPedigree = 10;

		kSnailMenuAnimalCustomise = 1;
		kSnailMenuAnimalSnail = 2;
		kSnailMenuAnimalTurritella = 3;
		kSnailMenuAnimalBivalve = 4;
		kSnailMenuAnimalAmmonite = 5;
		kSnailMenuAnimalNautilus = 6;
		kSnailMenuAnimalBrachiopod = 7;
		kSnailMenuAnimalCone = 8;
		kSnailMenuAnimalWhelk = 9;
		kSnailMenuAnimalScallop = 10;
		kSnailMenuAnimalEloise = 11;
		kSnailMenuAnimalGallaghers = 12;
		kSnailMenuAnimalRapa = 13;
		kSnailMenuAnimalLightning = 14;
		kSnailMenuAnimalSundial = 15;
		kSnailMenuAnimalFig = 16;
		kSnailMenuAnimalTun = 17;
		kSnailMenuAnimalRazorShell = 18;
		kSnailMenuAnimalJapaneseWonder = 19;

		kSnailCmdBoxSideView = UInt32('bxsv');

		kSnailCmdAnimalCustomise = UInt32('an01');
		kSnailCmdAnimalSnail = UInt32('an02');
		kSnailCmdAnimalTurritella = UInt32('an03');
		kSnailCmdAnimalBivalve = UInt32('an04');
		kSnailCmdAnimalAmmonite = UInt32('an05');
		kSnailCmdAnimalNautilus = UInt32('an06');
		kSnailCmdAnimalBrachiopod = UInt32('an07');
		kSnailCmdAnimalCone = UInt32('an08');
		kSnailCmdAnimalWhelk = UInt32('an09');
		kSnailCmdAnimalScallop = UInt32('an10');
		kSnailCmdAnimalEloise = UInt32('an11');
		kSnailCmdAnimalGallaghers = UInt32('an12');
		kSnailCmdAnimalRapa = UInt32('an13');
		kSnailCmdAnimalLightning = UInt32('an14');
		kSnailCmdAnimalSundial = UInt32('an15');
		kSnailCmdAnimalFig = UInt32('an16');
		kSnailCmdAnimalTun = UInt32('an17');
		kSnailCmdAnimalRazorShell = UInt32('an18');
		kSnailCmdAnimalJapaneseWonder = UInt32('an19');
		
	type
		MarchingOrders = RECORD
			Start, By, Till: single;
			Kind: (Wop, Dis, Trans);
		END;


	var
		Mut: ARRAY[1..MutTypeNo] OF Boolean;
		MenuList: ARRAY[1..MenuCnt] OF MenuRef;  { holds menu info }

		theScale: single;
		SideView: Boolean;
		Threshold: integer;
		MugShot: GWorldPtr; 
		Mirrorshot: GWorldPtr;
		RectOfInterest: Rect;
		CurrentGeneratingCurve: Integer;
		Coarsegraininess: Integer;
		inc: integer;
		OffCentre: point;
		DontDraw: boolean;
		MutProb: single;
		WDetails, DDetails, TDetails: MarchingOrders;

	procedure InitializeMorphGlobals;
		
implementation

	procedure InitializeMorphGlobals;
		var GenCurveRect: Rect;
		var tempPat: Pattern;
		begin
			inc := 16;
			CurrentGeneratingCurve := 0;
			SetRect(GenCurveRect, 0, 0, 511, 511);
			NewGWorld(MugShot, 0, GenCurveRect, nil, nil, 0);
			NewGWorld(Mirrorshot, 0, GenCurveRect, nil, nil, 0);
			WITH WDetails DO
				BEGIN
					start := 1.2;
					by := 1.5;
					till := 10;
				END;
			WITH DDetails DO
				BEGIN
					start := 0;
					by := 0.2;
					till := 0.6;
				END;
			WITH TDetails DO
				BEGIN
					start := 0;
					by := 2;
					till := 8;
				END;
			SideView := true;
			Threshold := 20;
			PenPat(GetQDGlobalsBlack(tempPat)^);
		end;

end.
