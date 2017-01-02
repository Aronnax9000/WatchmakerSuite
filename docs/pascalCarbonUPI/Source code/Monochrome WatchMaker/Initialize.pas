unit Initialize;
{These routines are called once only at startup.  Keep this file in a separate segment that can be unloaded}
{At present, initialize proc is *very* long, mostly because of the vast numbers of global vars. We should try}
{to cut this down, splitting the proc up and giving vars file scope or less where possible}

{History}
{v1.1  Added system check}
{		   Added attempts to save temp file in several places (v1.01 failed on locked server volumes) }
{          Removed calls to toolbox inits (done automatically by Think Pascal)}
{          Removed calls to MoreMasters (10 calls "             "       "        "   )}
{          No longer lock down cursors throughout program execution}
{			Improved out of memory warnings (but still some plain ExitToShells in there) }

interface
uses
	AlbumDefs,
	AlbumPlayback, 
	AlbumUtil,
	BoxesDefs, 
	CreateHistoryFileCarbon,
	CursorDefs,
	ErrorUnit, 
	GeneboxDefs,
	Globals, 
	HIBoxDefs,
	HIBoxTypes,
	InitializeMenusDef, 
	InitializeMorphMenusDef,
	MacOSAll, 
	Miscellaneous, 
	ModeDefs, 
	Pedigree, 
	PicDefs,
	SetItemStateDefs, 
	CommandHandlerDef, 
	MainWindowInit, 
	PersonDefs, 
	PlaybackWindowDefs, 
	MorphGlobals;

function InitializeProgram(breedModel: HIBreedModelPtr): OSStatus;

implementation


		

function InitializeProgram(breedModel: HIBreedModelPtr): OSStatus;
{}
{    purpose         initialize everything for the program}
{NB Think Pascal sets up toolbox managers, flushes event queue, and calls MoreMasters 10x}
	VAR
		j: Integer;
		tempRect: Rect;
		status: OSStatus = noerr;
	begin
		InitializeMorphGlobals;
		InitializeGeneboxStrings;

		FossilsToSave := FALSE;
		AlbumEmpty := TRUE;
		slides := -1;
		CreateHistoryFile;					{      "                 }

		Fossilizing := FALSE;
		LoadCursors;		{Changed v1.1}

		SizeOfPerson := SizeOf(Person);	{v1.1 Saves calculating it before every save to file}
		
		NRows := 3;
		NCols := 5;					
		BreedNBoxes := 15;
		MyPenSize := 1;


		
		MaxPages := 4;		{v1.1 was 1, but in turbo was a constant set to 4}
		AlbumNRows := 3;
		AlbumNCols := 5;
		
		BreedNRows := 3;
		BreedNCols := 5;
		FossilCounter := 0;
		SetMode(MainWindow, Preliminary);
		Page := 1;
		BoxNo := 1;
		Morph := 1;
		{Create BreedModel}
		new(breedModel);


		{ set up menus }
		InitializeMenus;


		{ set up window stuff }
		InitializeMainWindow(breedModel);

		{InitializePlayBackWindow(0);}
		GetWindowPortBounds(MainWindow, tempRect);
		status := NewGWorld(MyBitMap, 0, tempRect, nil, nil, 0);
		InitializeQuadrants(MainWindow);
		GetWindowPortBounds(MainWindow, Prect);
		CopyBits(GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, GetPortBitMapForCopyBits(AlbumBitMap[Page])^, PRect, PRect, srcCopy, NIL);
		{ program-specific initialization }
		Finished := False;                 { set program terminator to false   }

		InitializeMutations;
		TextSize(9);
		{TextFace([]);}
		TextFace(normal);
		Album.menagerieSize := 0;
		OldSpecial := nil;
		Special := nil;
		ShowPen;
		SetMode(MainWindow, Preliminary);
		{SetupBoxes(MainWindow);} {Needed only to define MidBox. Doesn't show because not breeding}
		Special := breedModel^.boxes^.MidBox;
		DelayedDrawing := FALSE;

		OldBox := nil;
		FOR j := 1 TO 4 DO
			SetItemState(kWatchMenuIndexBox, j, TRUE);

		FOR j := 1 TO MaxPages DO
			PBoxNo[j] := 0;
		CurrentPage := 1;
		Page := 1;
		LastPutFileName := '';
		LastGetFileName := '';

		Randomize;
		SomethingToRestore := FALSE;

		InitializePedigree;
		WITH MidScreen DO
			begin
				x := PRect.Left + (Prect.right - Prect.left) DIV 2;
				y := PRect.Top + (PRect.Bottom - PRect.Top) DIV 2
			end;
{PrDrvrOpen;}
		SweepOn := FALSE;
		hideInBackGround := FALSE;		{by default, we leave windows alone. Under System 6 this might make disks unaccessable}
		AlbumChanged := FALSE;
		Danger := FALSE;
		Zoomed := FALSE;
		OldMode := Breeding;
		ClipBoarding := FALSE;
		Naive := true; 
		WarningHasBeenGiven := FALSE;
		InitializeProgram := status;
	end; { of proc Initialize }

end.