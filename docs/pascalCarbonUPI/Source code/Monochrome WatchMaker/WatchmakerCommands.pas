unit WatchmakerCommands;


interface

	uses
		BoxesMoreFewer,
		ClipboardingDefs,
		HIBoxTypes,
		MacOSAll;

	function WatchCmdLoadAlbum: OSStatus;
	function WatchCmdLoadFossils: OSStatus;
	function WatchCmdSaveAlbum: OSStatus;
	function WatchCmdSaveBiomorph: OSStatus;
	function WatchCmdSaveFossils: OSStatus;
	function WatchCmdCloseAlbum: OSStatus;

	function WatchCmdClosePlayback: OSStatus;
	function WatchCmdBreedCurrent(boxes: HIBoxesPtr): OSStatus;

	function WatchCmdBreed(boxes: HIBoxesPtr): OSStatus;
	function WatchCmdDrift: OSStatus;
	function WatchCmdEngineering: OSStatus;

	function WatchCmdRecordFossil: OSStatus;
	function WatchCmdTriangle: OSStatus;
	function WatchCmdPlayFossil: OSStatus;
	function WatchCmdInitFossRec: OSStatus;
	function WatchCmdHopeMonster(boxes: HIBoxesPtr): OSStatus;
	function WatchCmdDisplayPedigree: OSStatus;
	function WatchCmdPedigreeDrawOut: OSStatus;
	function WatchCmdPedigreeNoMirrors: OSStatus;
	function WatchCmdPedigreeSingleMirror: OSStatus;
	function WatchCmdPedigreeDoubleMirror: OSStatus;
	function WatchCmdPedigreeMove: OSStatus;
	function WatchCmdPedigreeDetach: OSStatus;
	function WatchCmdPedigreeKill: OSStatus;
	function WatchCmdHelpCurrent: OSStatus;
	function WatchCmdHelpMisc: OSStatus;
	function WatchCmdTriangleRight:  OSStatus;
	function WatchCmdTriangleLeft:  OSStatus;
	function WatchCmdTriangleTop:  OSStatus;
	function WatchCmdDriftSweep:  OSStatus;
	function WatchCmdThinnerPen:  OSStatus;
	function WatchCmdThickerPen:  OSStatus;
	function WatchCmdFewerColumns(boxes: HIBoxesPtr):  OSStatus;
	function WatchCmdMoreColumns(boxes: HIBoxesPtr):  OSStatus;
	function WatchCmdFewerRows(boxes: HIBoxesPtr):  OSStatus;
	function WatchCmdMoreRows(boxes: HIBoxesPtr): OSStatus;
	function WatchCmdUndo: OSStatus;
	function WatchCmdCut: OSStatus;
	function WatchCmdCopy: OSStatus;
	function WatchCmdPaste(breedModel: HIBreedModelPtr): OSStatus;
	function WatchCmdClear(breedModel: HIBreedModelPtr): OSStatus;
	function WatchCmdHighlightBiom: OSStatus;
	function WatchCmdAddBiom(breedModel: HIBreedModelPtr): OSStatus;
	function WatchCmdShowAlbum: OSStatus;

implementation

	uses 
		AlbumDefs,
		AlbumDoClear,
		AlbumPlayback,
		AlbumUtil, 
		BoxesDefs,
		DevelopDefs,
		
		DialogGeomDefs, 
		DoAboutDef,
		DoSaltationDef,
		EngineeringDefs, 
		EvolveDefs, 
		FossilsExistDefs,
		GeneBoxDefs,
		Globals, 
		MainWindowInit,
		MenuGreyAdjustDefs,
		MessageDefs, 
		ModeDefs,
		MorphGlobals,
		Pedigree, 
		Serialization, 
		SerializationUtil, 
		SetItemStateDefs,
		TriangleUtils;

	function WatchCmdLoadAlbum: OSStatus;
		var
			StoreNRows, StoreNCols: Integer;
		begin
			StoreNRows := NRows;
			StoreNCols := NCols;

			NRows := AlbumNRows;
			NCols := AlbumNCols;
			{SetupBoxes(MainWindow); FIXME}
			LoadingFossils := FALSE;
			DoLoad(TRUE);
			NRows := StoreNRows;
			NCols := StoreNCols;
			WatchCmdLoadAlbum := noerr;
		end;

	function WatchCmdLoadFossils: OSStatus;
		var status: OSStatus;
		begin
			IF NOT FossilsExist(slides) THEN
				ResetFossils
			ELSE
				begin
					DotheSave := FALSE;
					DireMessage(kFossilsID, kResettingID, Verdict, true);
					IF Verdict = 1 THEN
						SaveSlides; {Yes}
					{returns with DoTheSave}
					IF (Verdict = 1) AND (DoTheSave) THEN
						ResetFossils;
					IF Verdict = 2 THEN
						ResetFossils {No}
				end;
			IF verdict <> 3 THEN {3=cancel}
				begin
					Fossilizing := FALSE;  {Filetype := 'COLL';}
					status := SetEOF(slides, 0);
					LoadingFossils := TRUE;
					ReadAnimals(ThisMenagerie); {Menagerie is dummy}
				{The next 3 lines are necessary for Spencer's manual but better out}
				(*ClosePlayBack;}
{                        Fossilizing:=TRUE;}
{                        DoBreed*)
				{Reset(slides);}
{                        StartPlayBack;}
				end; {Not cancelled}
			WatchCmdLoadFossils:= status;
		end;{case 2}

	function WatchCmdSaveAlbum: OSStatus;
		begin
				IF theMode = Moving THEN
					DoSave(2)
				ELSE
					DoSave(3);
					WatchCmdSaveAlbum:= noerr
		end;

	function WatchCmdSaveBiomorph: OSStatus;
		begin
			DoSave(1);
			WatchCmdSaveBiomorph := noerr;
		end;

	function WatchCmdSaveFossils: OSStatus;
		begin
			SaveSlides;
			WatchCmdSaveFossils := noerr;
		end;

	function WatchCmdCloseAlbum: OSStatus;
		begin
			DoClose;
			WatchCmdCloseAlbum := noerr;
		end;

	function WatchCmdClosePlayback: OSStatus;
		begin
			ClosePlayBack;
			{FIXME InitializeMainWindow;}
			WatchCmdClosePlayback := noerr;
		end;

	function WatchCmdBreedCurrent(boxes: HIBoxesPtr): OSStatus;
		begin
			FirstBiomorph := TheBiomorph;
			ClosePlayBack;
			Fossilizing := FALSE;
			{FIXME InitializeMainWindow;}
			DoBreed(boxes);
			WatchCmdBreedCurrent := noerr
		end;

	function WatchCmdBreed(boxes: HIBoxesPtr): OSStatus;
		begin
			{FIXME InitializeMainWindow;}
			NRows := BreedNRows;
			NCols := BreedNCols;
			{SetupBoxes(MainWindow);}
			NActiveBoxes := NBoxes;
			DoBreed(boxes);
			WatchCmdBreed := noerr
		end;

	function WatchCmdDrift: OSStatus;
		begin
			{FIXME InitializeMainWindow;}
			EraseRect(Prect);{FIXME}
			SetMode(MainWindow, Drifting);
			DriftOne := 0;
			NActiveBoxes := 0;
			WatchCmdDrift := noerr
		end;

	function WatchCmdEngineering: OSStatus;
		begin
			DoEngineer;
			WatchCmdEngineering := noerr;
		end;


	function WatchCmdHopeMonster(boxes: HIBoxesPtr): OSStatus;
		begin
			DoSaltation(boxes);
			WatchCmdHopeMonster := noerr;
		end;
		
	function WatchCmdInitFossRec: OSStatus;
		begin
			IF NOT FossilsExist(Slides) THEN
				ResetFossils
			ELSE
				begin
					DotheSave := FALSE;
					DireMessage(kFossilsID, kResettingID, Verdict, true);
					IF Verdict = 1 THEN
						SaveSlides; {Yes}
					{returns with DoTheSave}
					IF (Verdict = 1) AND (DoTheSave) THEN
						ResetFossils;
					IF Verdict = 2 THEN
						ResetFossils {No}
				end;
			WatchCmdInitFossRec := noerr
		end;
	function WatchCmdPlayFossil: OSStatus;
		var err: OSStatus;
		begin
			err := ResetDataFork(Slides);		{v1.1  was reset(Slides);}
			{Only play back history made this time}
			StartPlayBack;  {Play Back Fossil Record}
			WatchCmdPlayFossil := err
		end;

	function WatchCmdRecordFossil: OSStatus;
		begin
			Fossilizing := NOT Fossilizing;
			WatchCmdRecordFossil := noerr
		end;
	
	
	function WatchCmdTriangle: OSStatus;
		var theBoxes: HIBoxesPtr;
		context: CGContextRef;
		begin
			SetMode(MainWindow, triangling);
			OldSpecial := nil;
			{FIXME InitializeMainWindow(boxes);}

			MainTriangle(theBoxes, context);
			WatchCmdTriangle := noerr
		end;



	
	{	ClipBoarding := FALSE;
		InitializeMainWindow(boxes);  before all Box Menu commands}

	function WatchCmdMoreRows(boxes: HIBoxesPtr): OSStatus;
		begin
			DoRowMore(boxes);
			BreedNRows := NRows;
			BreedNCols := NCols;
			BreedNBoxes := NBoxes;
			WatchCmdMoreRows := noerr
		end;
		
	function WatchCmdFewerRows(boxes: HIBoxesPtr):  OSStatus;
		begin
			DoRowLess(boxes);
			BreedNRows := NRows;
			BreedNCols := NCols;
			BreedNBoxes := NBoxes;
			WatchCmdFewerRows := noerr
		end;
		
	function WatchCmdMoreColumns(boxes: HIBoxesPtr):  OSStatus;
		begin
			DoColMore(boxes);
			BreedNRows := NRows;
			BreedNCols := NCols;
			BreedNBoxes := NBoxes;
			WatchCmdMoreColumns := noerr
		end;
		
	function WatchCmdFewerColumns(boxes: HIBoxesPtr):  OSStatus;
		begin
			DoColLess(boxes);
			BreedNRows := NRows;
			BreedNCols := NCols;
			BreedNBoxes := NBoxes;
			WatchCmdFewerColumns := noerr
		end;
		
	function WatchCmdThickerPen:  OSStatus;

				begin
					MyPenSize := MyPenSize + 1;
					DoEngineer;
					WatchCmdThickerPen := noerr
				end;
				
	function WatchCmdThinnerPen:  OSStatus;

				begin
					MyPenSize := MyPenSize - 1;
					DoEngineer;
					WatchCmdThinnerPen := noerr
				end;
				
	function WatchCmdDriftSweep:  OSStatus;

				begin
					SweepOn := NOT SweepOn;
					CheckMenuItem(MenuList[kWatchMenuIndexBox], kWatchMenuBoxDriftSweep, SweepOn);
					DriftOne := 0;
					WatchCmdDriftSweep := noerr
				end;
				
	function WatchCmdTriangleTop:  OSStatus;
		var theBoxes: HIBoxesPtr;
			context: CGContextRef;
		begin
			IF theMode = Triangling THEN
				begin
					SimpleMessage(14234, Verdict);
					IF verdict = 1 THEN
						begin
							Topan := Special^.denizen;
							MainTriangle(theBoxes, context);
						end
				end
			ELSE
				Topan := Special^.denizen;
				WatchCmdTriangleTop := noerr
		end;
		
	function WatchCmdTriangleLeft:  OSStatus;
		var theBoxes: HIBoxesPtr;
			context: CGContextRef;
		begin
			IF theMode = Triangling THEN
				begin
					SimpleMessage(14234, Verdict);
					IF verdict = 1 THEN
						begin
							Leftan := Special^.denizen;
							MainTriangle(theBoxes, context);
						end
				end
			ELSE
				Leftan := Special^.denizen;
				WatchCmdTriangleLeft := noerr
		end;
		
	function WatchCmdTriangleRight:  OSStatus;
		var theBoxes: HIBoxesPtr;
			context: CGContextRef;
		begin
			IF theMode = Triangling THEN
				begin
					SimpleMessage(14234, Verdict);
					IF verdict = 1 THEN
						begin
							Rightan := Special^.denizen;
							MainTriangle(theBoxes, context);
						end
				end
			ELSE
				Rightan := Special^.denizen;
			WatchCmdTriangleRight := noerr
		end;

	function WatchCmdDisplayPedigree: OSStatus;
		begin
			{FIXME InitializeMainWindow;}
			PhylogNew(Special^.denizen);
			WatchCmdDisplayPedigree := noerr
		end;

	function WatchCmdPedigreeDrawOut: OSStatus;
		begin
			SetMode(MainWindow, Phyloging);
			WatchCmdPedigreeDrawOut := noerr
		end;

	function WatchCmdPedigreeNoMirrors: OSStatus;
		begin
			Rays := 1;
			CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 4, TRUE);
			CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 5, FALSE);
			CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 6, FALSE);
			WatchCmdPedigreeNoMirrors := noerr;
		end;

	function WatchCmdPedigreeSingleMirror: OSStatus;
				begin
					Rays := 2;
					CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 5, TRUE);
					CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 4, FALSE);
					CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 6, FALSE);
					WatchCmdPedigreeSingleMirror := noerr;
				end;
	function WatchCmdPedigreeDoubleMirror: OSStatus;
				begin
					Rays := 4;
					CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 6, TRUE);
					CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 4, FALSE);
					CheckMenuItem(MenuList[kWatchMenuIndexPedigree], 5, FALSE);
					WatchCmdPedigreeDoubleMirror := noerr
				end;
	function WatchCmdPedigreeMove: OSStatus;
		begin
				SetMode(MainWindow, Moving);
				WatchCmdPedigreeMove := noerr
		end;
	function WatchCmdPedigreeDetach: OSStatus;
		begin
				SetMode(MainWindow, Detaching);
				WatchCmdPedigreeDetach := noerr
		end;
	function WatchCmdPedigreeKill: OSStatus;
		begin
				SetMode(MainWindow, Killing);
				WatchCmdPedigreeKill := noerr
		end;

	function WatchCmdHelpCurrent: OSStatus;
		begin
			CASE theMode OF
			Breeding: 
				HelpMessage(2630);
			albuming: 
				HelpMessage(11506);
			phyloging: 
				HelpMessage(12587);
			killing: 
				HelpMessage(11150);
			moving: 
				HelpMessage(4241);
			detaching: 
				HelpMessage(16204);
			randoming: 
				HelpMessage(26732);
			engineering: 
				HelpMessage(8597);
			drifting: 
				HelpMessage(8947);
			highlighting: 
				HelpMessage(19866);
			PlayingBack: 
				HelpMessage(11238);
			triangling: 
				HelpMessage(17751);
			sweeping: 
				HelpMessage(8947);
				
		end; {Cases}
		WatchCmdHelpCurrent := noerr
	end;
(*		IF ClipBoarding THEN
			HelpMessage(7699) {FIXME what is this message for? - ABC}
		ELSE
*)
		function WatchCmdHelpMisc: OSStatus;
			begin
				HelpMessage(21128);
				WatchCmdHelpMisc := noerr
			end;



	function WatchCmdUndo: OSStatus;
		begin
			ClipBoarding := FALSE;
			Sysbeep(1);
			WatchCmdUndo := noerr;
		end;
		
	function WatchCmdCut: OSStatus;
		begin		
			Sysbeep(1);
			WatchCmdCut := noerr;
		end;
		
	function WatchCmdCopy: OSStatus;
		begin
			SendToClipBoard;
			WatchCmdCopy := noerr;
		end;

	function WatchCmdPaste(breedModel: HIBreedModelPtr): OSStatus;
		begin
			ClipBoarding := FALSE;

			DoClear(FALSE, breedModel);	
			WatchCmdPaste := noerr;
		end;
		

	function WatchCmdClear(breedModel: HIBreedModelPtr): OSStatus;
		begin
			ClipBoarding := FALSE;
			DoClear(TRUE, breedModel);
			MenuGreyAdjust;
			WatchCmdClear := noerr;
		end;
		
	function WatchCmdHighlightBiom: OSStatus;
		VAR
			StoreNRows, StoreNCols: Integer;
		begin
			StoreNRows := NRows;
			StoreNCols := NCols;
			ClipBoarding := FALSE;
			DoHighlight;
			NRows := StoreNRows;
			NCols := StoreNCols;
			WatchCmdHighlightBiom := noerr;
		end;

	function WatchCmdAddBiom(breedModel: HIBreedModelPtr): OSStatus;
		VAR
			StoreNRows, StoreNCols: Integer;
		begin
			StoreNRows := NRows;
			StoreNCols := NCols;
			ClipBoarding := FALSE;

			IF TheMode <> Albuming THEN
				StoreBreedingScreen;
			NRows := AlbumNRows;
			NCols := AlbumNCols;
			SetMode(MainWindow, Albuming);
			{SetupBoxes(MainWindow);}

			AddToAlbum(Special^.denizen, breedModel);
			NRows := StoreNRows;
			NCols := StoreNCols;
			WatchCmdAddBiom := noerr;
		end;
		
	function WatchCmdShowAlbum: OSStatus;
		VAR
			StoreNRows, StoreNCols: Integer;
		begin
			StoreNRows := NRows;
			StoreNCols := NCols;
			ClipBoarding := FALSE;
			IF TheMode <> Albuming THEN
				StoreBreedingScreen;
			NRows := AlbumNRows;
			NCols := AlbumNCols;
			SetMode(MainWindow, Albuming);
			{SetupBoxes(MainWindow);}
			{Range check fails on special, gives bomb, when album emptied}
			IF Page > 1 THEN
				Zoom
			ELSE
				begin
					UncurtainPage(CurrentPage);
					TakeCare(CurrentPage);
					IF OldSpecial = nil THEN
						begin
							OldSpecial := Special;
							(*InvertRect(Box[special]) FIXME *)
						end;
				end;

			NRows := StoreNRows;
			NCols := StoreNCols;
			WatchCmdShowAlbum := noerr;
		end;
end.