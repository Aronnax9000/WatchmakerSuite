unit unit_snail_types;

interface

{$IFC UNDEFINED THINK_Pascal}
  uses ToolUtils, Types, Quickdraw;
{$ENDC}
const
	GenesDlogID = 129;
	SafetyValve = 20;
	CarefulThreshold = 8192;
	trickle = 10;
	MutSizestart = 10;
	iconhalfsize = 128;
	ScrollBarWidth = 16;
	TopBoxHeight = 10;
	MaxGene9 = 12;
	MaxAlbum = 100;	{v1.1 was set to 15, but turbo version said 100}
	MaxBoxes = 100; {v1.1 was set to 50}
	StackSize = 100;
	GenesHeight = 20;
	PicSizeMax = 4095;
	WorryMax = 4095;
	TickDelay = 20;
	MutTypeNo = 7;

{*** Menu Constants ***}
	MenuCnt = 8;
	ApplMenu = 1000;
	FileMenu = 1001;
  {v1.1 added these menu item constants. Life is too hard without them}
	LoadAlbumItem = 1;
	LoadFossilsItem = 2;
	SaveBiomorphItem = 3;
	SaveFossilsItem = 4;
	SaveAlbumItem = 5;
	CloseAlbumItem = 6;
	QuitItem = 7;

	EditMenu = 1002;
	UndoItem = 1;
	CutItem = 3;
	Copyitem = 4;
	PasteItem = 5;
	ClearItem = 6;
	HighlightBiomItem = 8;
	AddBiomItem = 9;
	ShowAlbumItem = 10;
	ImportPICTItem = 11;

	OperMenu = 1004;
	BreedItem = 1;
	DriftItem = 2;
	EngineeringItem = 3;
	HopeMonsterItem = 4;
	InitFossRecItem = 5;
	PlayFossilItem = 6;
	RecordFossilItem = 7;
	TriangleItem = 8;
	ArrayItem = 9;

	BoxMenu = 1005;
	MoreRowsItem = 1;
	FewerRowsItem = 2;
	MoreColumnsItem = 3;
	FewerColumnsItem = 4;
	SideViewItem = 5;
	DriftSweepItem = 6;
	TriangleTopItem = 7;
	TriangleLeftItem = 8;
	TriangleRightItem = 9;
	ViewPedigreeItem = 10;

	AnimalsMenu = 1006;
	CustomiseItem = 1;
	SnailItem = 2;
	TurritellaItem = 3;
	BivalveItem = 4;
	AmmoniteItem = 5;
	NautilusItem = 6;
	BrachiopodItem = 7;
	ConeItem = 8;
	WhelkItem = 9;
	ScallopItem = 10;
	EloiseItem = 11;
	GallaghersItem = 12;
	RapaItem = 13;
	LightningItem = 14;
	SundialItem = 15;
	FigItem = 16;
	TunItem = 17;
	RazorShellItem = 18;
	JapaneseWonderItem = 19;

	PedigreeMenu = 1007;
	DisplayPedigreeItem = 1;
	DrawOutItem = 3;
	NoMirrorsItem = 4;
	SingleMirrorItem = 5;
	DoubleMirrorItem = 6;
	MoveItem = 8;
	DetachItem = 9;
	KillItem = 10;

	HelpMenu = 3238;
	HelpCurrentItem = 1;
	HelpMiscItem = 2;

	SpecMenu = 21537;
	ClosePlaybackItem = 1;
	BreedCurrentItem = 2;
	QuitPlaybackItem = 3;

	AM = 1;    { index into MenuList for Apple Menu }
	FM = 2;    { ditto for File Menu                }
	EM = 3;
	OM = 4;    { ditto for Operation Menu             }
	BM = 5;    { ditto for Boxes Menu                }
	MM = 6;    { ditto for Mutations Menu}
	PM = 7;    { ditto for Pedigree Menu}
	HM = 8;	{W for Window Menu}


const
	DMutsize = 0.01;
	SMutSize = 0.1;
	TMutSize = 0.1;
	PSize = 1;

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

  {CURSORS > made these contiguous numbers so that }
  {they can be read in a loop and checked - Alun}
	leftCursID = 135;
	leftCursor = 5;
	rightCursID = 136;
	rightCursor = 6;
	UpCursId = 137;
	UpCursor = 7;
	DownCursID = 138;
	DownCursor = 8;
	EqCursID = 139;
	EqCursor = 9;
	InjectCursID = 140;
	InjectCursor = 10;
	QCursID = 141;
	QCursor = 11;
	BlackCursId = 142;
	BlackCursor = 12;
	BoxCursID = 143;
	BoxCursor = 13;
	RandCursID = 144;
	RandCursor = 14;
	BreedCursID = 145;
	BreedCursor = 15;
	HandCursID = 146;
	HandCursor = 16;
	DrawOutCursID = 147;
	DrawOutCursor = 17;
	ScissorCursId = 148;
	ScissorCursor = 18;
	GunCursID = 149;
	GunCursor = 19;
	LensCursID = 150;
	LensCursor = 20;
	BlankCursID = 151;
	BlankCursor = 21;

	BSize = 512;
	BCount = 256;

type

	tSystem = packed record		{****Added for v1.1****}
			hasWNE: BOOLEAN;
			hasColorQD: BOOLEAN;
			hasGestalt: BOOLEAN;
			hasAppleEvents: BOOLEAN;
			hasAliasMgr: BOOLEAN;
			hasFolderMgr: BOOLEAN;
			hasEditionMgr: BOOLEAN;
			hasHelpMgr: BOOLEAN;
			hasScriptMgr: BOOLEAN;
			hasFPU: BOOLEAN;
			scriptsInstalled: Integer;
			systemVersion: Integer;
			sysVRefNum: Integer;
		end;


	CursorList = array[iBeamCursor..BlankCursor] of CursHandle;
	PtrInteger = ^Integer;
	PtrString = ^str255;
	SwellType = (Swell, Same, Shrink);
	HorizPos = (LeftThird, MidThird, RightThird);
	VertPos = (TopRung, MidRung, BottomRung);
	SmallNumber = -1..1;
	chromosome = array[1..9] of Integer;
	Compass = (NorthSouth, EastWest);
	CompletenessType = (Single, Ddouble);
	SpokesType = (NorthOnly, NSouth, Radial);
	person = record
			WOpening, DDisplacement, SShape, TTranslation: real;
			Coarsegraininess, Reach, GeneratingCurve: integer;
			TranslationGradient, DGradient: real;
			Handedness: -1..1;
		end;

	MarchingOrders = record
			Start, By, Till: real;
			Kind: (Wop, Dis, Trans);
		end;

	FullPtr = ^Full;
	FullHandle = ^FullPtr;
	Full = record
			genome: person;
			surround: Rect;
			origin, centre: Point;
			parent: FullHandle;
			firstborn: FullHandle;
			lastborn: FullHandle;
			eldersib: FullHandle;
			youngersib: FullHandle;
			Prec, Next: FullHandle;
			Damaged{,Blackened}
			: Boolean;
			snapHandle: Handle;
			snapBytes: Integer;
			snapBounds: Rect;
		end;
	GodPtr = ^God;
	GodHandle = ^GodPtr;
	God = record
			Adam: FullHandle;
			PreviousGod, NextGod: GodHandle;
		end;
	PartOfBox = (MainPart, MovePart, GoAwayPart, AnyPart);
	Lin = record
			StartPt, EndPt: Point
		end;
	LinPtr = ^Lin;
	LinHandle = ^LinPtr;
	Pic = record
			BasePtr: Ptr;
			MovePtr: LinPtr;
			Origin: Point;
			PicSize: Integer;
			PicPerson: person
		end;
	BaseHandle = ^Ptr;
	PointArray = array[1..4] of Point;
	Menagerie = record
			Member: array[1..MaxAlbum] of person;
			Place: array[1..MaxAlbum] of record
					Page, BoxNo: Integer
				end;
			Size: Integer
		end;
	mode = (preliminary, breeding, albuming, phyloging, killing, moving,
    detaching, randoming, engineering, drifting, highlighting,
    PlayingBack, triangling, sweeping, arraying);

  SnailPreferencesHandle = ^SnailPreferencesPtr;
  SnailPreferencesPtr = ^SnailPreferences;
  SnailPreferences = RECORD
    box: array[1..MaxBoxes] of Rect;
    theMode: Mode;
    CursList: CursorList;
    ZeroMargin: boolean;
    SideView: boolean;
    ClipBoarding: boolean;
    DontDraw: boolean;
    DelayedDrawing: boolean;
    {var types}
    MyPic: Pic;
    theScale: real;
    margin: Rect;
    GeneBox: array[1..16] of Rect;
    PRect: Rect;
    {DrawPic}
    threshold: integer;
  end;


implementation

end.

