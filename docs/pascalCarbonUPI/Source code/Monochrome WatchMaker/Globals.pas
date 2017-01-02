{***********************************************************}
{*												v1.1 sept 1993																	*}
{*																																		*}
{*		Made a start at cleaning up all these globals, but still a lot to do.  I have removed a	*}
{*		couple of dozen variables which were just wasting memory, and moved others to		*}
{*		local variable status.  For the most part, they need to be made into parameters or		*}
{*		file-scoped variables with unit initializer; this is too much work for now.					*}
{**********************************************************}


unit Globals;

interface

	uses MacOSAll, PersonDefs, PicDefs, MorphGlobals;

CONST
	SafetyValve = 20;
	CarefulThreshold = 8192;
	trickle = 10;
	MutSizestart = 10;
	iconhalfsize = 128;
	ScrollBarWidth = 16;
	TopBoxHeight = 10;
	MaxGene9 = 20; {was 12...exponential growth, so be careful}
	MaxBoxes = 100; {v1.1 was set to 50}
	StackSize = 100;
	GenesHeight = 20;
	TickDelay = 2;
	
			{Menu Item Constants}
		kWatchMenuAppleAbout = 1;

		kWatchMenuFileLoadAlbum = 1;	{v1.1 added these menu  constants. Life is too hard without them}
		kWatchMenuFileLoadFossils = 2;
		kWatchMenuFileSaveBiomorph = 3;
		kWatchMenuFileSaveFossils = 4;
		kWatchMenuFileSaveAlbum = 5;
		kWatchMenuFileCloseAlbum = 6;
		kWatchMenuFileQuit = 7;

		kWatchMenuEditUndo = 1;
		kWatchMenuEditCut = 3;
		kWatchMenuEditCopy = 4;
		kWatchMenuEditPaste = 5;
		kWatchMenuEditClear = 6;
		kWatchMenuEditHighlightBiom = 8;
		kWatchMenuEditAddBiom = 9;
		kWatchMenuEditShowAlbum = 10;

		kWatchMenuOperationBreed = 1;
		kWatchMenuOperationDrift = 2;
		kWatchMenuOperationEngineering = 3;
		kWatchMenuOperationHopeMonster = 4;
		kWatchMenuOperationInitFossRec = 5;
		kWatchMenuOperationPlayFossil = 6;
		kWatchMenuOperationRecordFossil = 7;
		kWatchMenuOperationTriangle = 8;




		kWatchMenuPedigreeDisplayPedigree = 1;
		kWatchMenuPedigreeDrawOut = 3;
		kWatchMenuPedigreeNoMirrors = 4;
		kWatchMenuPedigreeSingleMirror = 5;
		kWatchMenuPedigreeDoubleMirror = 6;
		kWatchMenuPedigreeMove = 8;
		kWatchMenuPedigreeDetach = 9;
		kWatchMenuPedigreeKill = 10;

		kWatchMenuHelpCurrent = 1;
		kWatchMenuHelpMisc = 2;

		kWatchMenuSpecClosePlayback = 1;
		kWatchMenuSpecBreedCurrent = 2;
		kWatchMenuSpecQuitPlayback = 3;

	
	{Common Commands}
	
		
		kWatchCmdAppleAbout = UInt32('apab');
		
		kWatchCmdFileLoadAlbum = UInt32('fila');
		kWatchCmdFileLoadFossils = UInt32('filf');
		kWatchCmdFileSaveBiomorph = UInt32('fisb');
		kWatchCmdFileSaveFossils = UInt32('fisf');
		kWatchCmdFileSaveAlbum = UInt32('fisa');
		kWatchCmdFileCloseAlbum = UInt32('fica');
		kWatchCmdFileQuit = UInt32('fiqt');

		kWatchCmdEditUndo = UInt32('edud');
		kWatchCmdEditCut = UInt32('edct');
		kWatchCmdEditCopy = UInt32('edcp');
		kWatchCmdEditPaste = UInt32('edps');
		kWatchCmdEditClear = UInt32('edcl');
		kWatchCmdEditHighlightBiom = UInt32('edhb');
		kWatchCmdEditAddBiom = UInt32('edab');
		kWatchCmdEditShowAlbum = UInt32('edsa');

		kWatchCmdOperationBreed = UInt32('opbr');
		kWatchCmdOperationDrift = UInt32('opdr');
		kWatchCmdOperationEngineering = UInt32('opeg');
		kWatchCmdOperationHopeMonster = UInt32('ophm');
		kWatchCmdOperationInitFossRec = UInt32('opif');
		kWatchCmdOperationPlayFossil = UInt32('oppf');
		kWatchCmdOperationRecordFossil = UInt32('oprf');
		kWatchCmdOperationTriangle = UInt32('optr');

		kWatchCmdBoxMoreRows = UInt32('bxmr');
		kWatchCmdBoxFewerRows = UInt32('bxfr');
		kWatchCmdBoxMoreColumns = UInt32('bxmc');
		kWatchCmdBoxFewerColumns = UInt32('bxfc');
		kWatchCmdBoxThickerPen = UInt32('bxtk');
		kWatchCmdBoxThinnerPen = UInt32('bxtn');
		kWatchCmdBoxDriftSweep = UInt32('bxds');
		kWatchCmdBoxTriangleTop = UInt32('bxtt');
		kWatchCmdBoxTriangleLeft = UInt32('bxtl');
		kWatchCmdBoxTriangleRight = UInt32('bxtr');
		kWatchCmdBoxHideWindow = UInt32('bxhw');




		kWatchCmdPedigreeDisplayPedigree = UInt32('pddp');
		kWatchCmdPedigreeDrawOut = UInt32('pddo');
		kWatchCmdPedigreeNoMirrors = UInt32('pdnm');
		kWatchCmdPedigreeSingleMirror = UInt32('pdsm');
		kWatchCmdPedigreeDoubleMirror = UInt32('pddm');
		kWatchCmdPedigreeMove = UInt32('pdmv');
		kWatchCmdPedigreeDetach = UInt32('pddt');
		kWatchCmdPedigreeKill = UInt32('pdkl');

		kWatchCmdHelpCurrent = UInt32('hecu');
		kWatchCmdHelpMisc = UInt32('hems');

		kWatchCmdSpecClosePlayback = UInt32('spcp');
		kWatchCmdSpecBreedCurrent = UInt32('spbc');
		kWatchCmdSpecQuitPlayback = UInt32('spqp');	
	

	PascalKind = 32700;  {windowKind of pascal windows}


	MainID = 1000;    { resource ID for MainWindow         }
	AboutID = 1000;    { resource ID for dialog box         }
	Text1ID = 1000;    { resource IDs for 'About...' text   }
	Text2ID = 1001;
	Text3ID = 1002;
	Text4ID = 21950;
	WarnDiscString = 22773;
	SaveAlbumString = 4720;
	SaveFossilString = 11609;
	WarnIconString = 7852;
	TooLargeString = 13603;
	SaveBiomorphString = 3866;

{new STR# IDs for alerts - v1.1}
	kAlertStringsID = 128;
	kFossilsID = 7;
	kAlbumID = 8;
	kBiomorphID = 9;
	kQuittingID = 10;
	kClosingID = 11;
	kResettingID = 12;

	PlusIconString = 4477;
	AsString = 17474;
	AlbumPageID = 5102;
	ClipID = 2000;
	Clip1ID = 2000;
	Clip2ID = 2001;
	Clip3ID = 2002;



	BSize = 512;
	BCount = 256;

	windowEventDrawContentType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowDrawContent);
	windowEventBoundsChangedType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowBoundsChanged);
	windowEventCloseType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowClose);
	windowEventResizeCompletedType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowResizeCompleted);
	windowEventHandleContentClickType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowHandleContentClick);
	windowEventHandleUpdateType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowUpdate);
	windowEventControlHitType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlHit);
	commandEventType: EventTypeSpec = (eventClass: kEventClassCommand; eventKind: kEventCommandProcess);
	eventControlValueFieldChangedType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlValueFieldChanged);
	eventControlClickType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlClick);
	MouseMovedEventType: EventTypeSpec = (eventClass: kEventClassMouse; eventKind: kEventMouseMoved);
	eventControlTrackType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlTrack);
	eventControlDrawType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlDraw);
	eventControlHitTestType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlHitTest);

TYPE

	PointArray = ARRAY[1..4] OF Point;


VAR
	Finished, DotheSave, LoadingFossils: Boolean;
	FossilCounter: LongInt;


  { ******** Menu stuff ***************}
	SpecialBreedMenu: MenuRef;

  { ******** Window stuff  ***************}

	MainWindow: HIWindowRef;        			{ pointer to main window        }
	PlaybackWindow: HIWindowRef;
	frontw: HIWindowRef;        				{ pointer to active window      }
	MyControl: ControlRef;
	MyBitMap: GWorldPtr;
	k, NumberInFile: Integer;
	MyPenSize: CGFloat;
	MidScreen: HIPoint;
	hideInBackGround: Boolean;			{added v1.1 if TRUE hide our windows when switched out. Toggled by menu}
	Quadrant: ARRAY[0..4] OF Rect; {Made global to fold mouse event handling into main event handler. - Alan Canon}
	OldQuadrant: integer; {as above for Quadrant - ABC}
{******** File Stuff **********}
	volRefNum, DefaultVolNum, MaxPages: Integer;  {volRefNum is volume containing fossil file, DefaultVolNum contains app}
	Slides: SInt16;									{forkRefNum of fossil file}
	SizeOfPerson: LongInt;						{Record size for saving to files.}
	LastPutFileName, LastGetFileName: Str255;

	FossilsToSave, FirstTime, ThereAreLines, SweepOn: Boolean;
	NRows, Ncols, NBoxes, NActiveBoxes: integer;
	{MidBox: integer;}
	{height: Integer;}
	AlbumNRows, AlbumNCols: Integer; 
	BreedNRows, BreedNCols, BreedNBoxes: Integer;
	RememberSpecial: integer;
	{margin: HIRect;} 
	DamageRect: Rect;
	PRect, BusinessPart, DummyRect: Rect;
	
	child: ARRAY[1..MaxBoxes] OF person;


	ClipBoarding, Fossilizing, naive, burst, WarningHasBeenGiven: Boolean;
	Midline, CurrentPage: Integer;
	DelayedDrawing: Boolean;
	ZeroMargin: Boolean;
	BrokenOut: Boolean;
	{MyPic: MorphPic;}
	ThisPic: PicHandle;
	DocumentMessage, DocumentCount, Rays, Verdict: Integer;
	NPages, Morph, Page, BoxNo, DriftOne, GodCounter: Integer;
	PBoxNo: ARRAY[1..4] OF Integer;
	MyPicture: PicHandle;

	StoreChild: ARRAY[1..MaxBoxes] OF Person;

	SomethingToRestore, AlreadyTriangling: Boolean;

	firstBiomorph, theBiomorph: Person;
	topan, leftan, rightan, copiedanimal: PersonPtr; 
	target: PersonPtr;
	r1, r2, r3: single;
	a, b, c, mous, LastMouse, NowMouse: HIPoint;



end.