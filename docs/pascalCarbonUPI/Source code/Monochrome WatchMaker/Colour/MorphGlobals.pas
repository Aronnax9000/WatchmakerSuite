unit MorphGlobals;

interface

	uses MacOSAll;
	
	const
		NibFileNameSansExtension = 'BW';
	
		MorphsHaveGenebox = true;
		MenuCnt = 9;


		kWatchMenuApple = 1000;   				{Menu resource ID}
		kWatchMenuFile = 1001;   				{Menu resource ID}
		kWatchMenuEdit = 1002;   				{Menu resource ID}
		kWatchMenuOperation = 1004;   			{Menu resource ID}
		kWatchMenuBox = 1005;   				{Menu resource ID}
		kMobiMenuMutation = 1006; {Carbon-based File menu (without Quit)}
		kWatchMenuPedigree = 1007;
		kWatchMenuHelp = 3238;
		kWatchMenuSpec = 21537;

		{Menu List indices}
		
		kWatchMenuIndexApple = 1;    { index into MenuList for Apple Menu }
		kWatchMenuIndexFile = 2;    { ditto for File Menu                }
		kWatchMenuIndexEdit = 3;
		kWatchMenuIndexOperation = 4;    { ditto for Operation Menu             }
		kWatchMenuIndexBox = 5;    { ditto for Boxes Menu                }
		kMobiMenuIndexMutation = 6;    { ditto for Mutations Menu}
		kWatchMenuIndexPedigree = 7;    { ditto for Pedigree Menu}
		kWatchMenuIndexWindow = 8; {W for Window Menu -- Added by ABC}
		kWatchMenuIndexHelp = 9;	{Help menu}
		

		kMobiMenuMutationSegmentation = 1;
		kMobiMenuMutationGradient = 2;
		kMobiMenuMutationAsymmetry = 3;
		kMobiMenuMutationRadialSym = 4;
		kMobiMenuMutationScalingFactor = 5;
		kMobiMenuMutationMutationSize = 6;
		kMobiMenuMutationMutationRate = 7;
		
		kWatchMenuBoxMoreRows = 1;
		kWatchMenuBoxFewerRows = 2;
		kWatchMenuBoxMoreColumns = 3;
		kWatchMenuBoxFewerColumns = 4;
		kWatchMenuBoxThickerPen = 5;
		kWatchMenuBoxThinnerPen = 6;
		kWatchMenuBoxDriftSweep = 7;
		kWatchMenuBoxTriangleTop = 8;
		kWatchMenuBoxTriangleLeft = 9;
		kWatchMenuBoxTriangleRight = 10;
		kWatchMenuBoxHideWindow = 12;{Think this one is gone. -ABC}

		{Menu commands}
	
	
		kMobiCmdMutationSegmentation = UInt32('mut1');
		kMobiCmdMutationGradient = UInt32('mut2');
		kMobiCmdMutationAsymmetry = UInt32('mut3');
		kMobiCmdMutationRadialSym = UInt32('mut4');
		kMobiCmdMutationScalingFactor = UInt32('mut5');
		kMobiCmdMutationMutationSize = UInt32('mut6');
		kMobiCmdMutationMutationRate = UInt32('mut7');
		MutTypeNo = 9;

	var
		MenuList: ARRAY[1..MenuCnt] OF MenuRef;  { holds menu info }

		Mut: ARRAY[1..MutTypeNo] OF Boolean;

	
		theScale: real;
		SideView: Boolean;
		Threshold: integer;
		MugShot: BitMap; 
		Mirrorshot: bitmap;
		RectOfInterest: Rect;
		CurrentGeneratingCurve: Integer;
	
		
	procedure InitializeMorphGlobals;
		
implementation

	procedure InitializeMorphGlobals;
		begin
			
		end;
		

end.
