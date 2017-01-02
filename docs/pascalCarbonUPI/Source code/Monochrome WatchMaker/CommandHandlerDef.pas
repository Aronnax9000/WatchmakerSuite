unit CommandHandlerDef;

{******************************************************************}
{*															v1.1 Sept 1993.								*}
{* 	Added Suspend/Resume events																			*}
{*		Added command key handling																		*}
{*		Split activate/deactivate event handling														*}
{*		New menu item -> hide windows in background														*}
{*		Save screen if hiding windows, otherwise don't - speed up context switching						*}
{*		Enabled desk accessories																		*}
{*		Added bad disk handling																			*}
{*		Split up HandleMenu proc into several procedures - one per menu 								*}
{*		Added named constants for menu items (so ordinary mortals like me can read this!)				*}
{*		All file operations converted to Mac file manager calls											*}
{*		DLOGs for DireMessages changed to Alerts, conforming to Apple's HIG								*}
{*		Removed Repeat/Until loops around save changes alerts											*}
{*		Removed many unused variables, and restricted USE of other units								*}
{*		Added auto positioning of dialogs and alerts under System 6 (urgh). Added auto position to		*}
{*				resources for System 7																	*}
{*		Saving files in same folder as file with same name now removes old file, even if open (not		*}
{*				sure of the wisdom of this, though)														*}
{*																										*}
{--------------------------------------------------------------------------------------------}
{*										Original 1993 TO DO List	
{*									(Done by Alan Canon March 24, 2015)									*}
{*		Check out other dialogs, some of them should be alerts	(done)									*}
{*		Offscreen bitmaps:																				*}
{*			1) Save only rect of dialog or alert														*}
{*			2) Don't save screen before switching out (makes response sluggish - Apple says don't do it)*}
{*			3) Change DoUpdate so that it draws normally if no offscreen saved							*}
{*			4) Remove spurious calls to StoreOffScreen													*}
{*			5) Disentangle activate and update event handling											*}
{*		Restrict window size, and allow users to change size. Option: leave space for disk icons		*}
{*		Add support for a least the required set of Apple Events										*}
{*																										*}
{******************************************************************}

interface

uses
	AlbumPlayback, 
	AlbumUtil,
	BiomorphCommandDef,
	DoAboutDef,
	EngineeringDefs, 
	Globals, 
	HIBoxTypes,
	MacOSAll, 
	MainWindowDefs,
	WatchmakerCommands, 
	MenuGreyAdjustDefs, 
	Miscellaneous, 
	ModeDefs, 
	InitializeMenusDef,
	MorphGlobals,
	Pedigree, 
	PlaybackGeomUtil,
	Serialization, 
	TriangleUtils; 


	function CommandHandler(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;


implementation

	function CommandHandler(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var 
			status: OSStatus = NoErr;
			theCommand: HICommand;
			breedModel: HIBreedModelPtr;
		begin
			breedModel := HIBreedModelPtr(userData);
			GetEventParameter(theEvent, kEventParamDirectObject, typeHICommand, nil, sizeof(HICommand), nil, @theCommand);
			case theCommand.commandID of
				kWatchCmdAppleAbout: DoAbout;
			
				kWatchCmdFileLoadAlbum: WatchCmdLoadAlbum;
				kWatchCmdFileLoadFossils: WatchCmdLoadFossils;
				kWatchCmdFileSaveBiomorph: WatchCmdSaveBiomorph;
				kWatchCmdFileSaveFossils: WatchCmdSaveFossils;
				kWatchCmdFileSaveAlbum: WatchCmdSaveAlbum;
				kWatchCmdFileCloseAlbum: WatchCmdCloseAlbum;

				kWatchCmdEditUndo: WatchCmdUndo;
				kWatchCmdEditCut: WatchCmdCut;
				kWatchCmdEditCopy: WatchCmdCopy;
				kWatchCmdEditPaste: WatchCmdPaste(breedModel);
				kWatchCmdEditClear: WatchCmdClear(breedModel);
				kWatchCmdEditHighlightBiom: WatchCmdHighlightBiom;
				kWatchCmdEditAddBiom: WatchCmdAddBiom(breedModel);
				kWatchCmdEditShowAlbum: WatchCmdShowAlbum;

				kWatchCmdOperationBreed: WatchCmdBreed(breedModel^.boxes);
				kWatchCmdOperationDrift:  WatchCmdDrift;
				kWatchCmdOperationEngineering: WatchCmdEngineering;
				kWatchCmdOperationHopeMonster: WatchCmdHopeMonster(breedModel^.boxes);
				kWatchCmdOperationInitFossRec: WatchCmdInitFossRec;
				kWatchCmdOperationPlayFossil: WatchCmdPlayFossil;
				kWatchCmdOperationRecordFossil: WatchCmdRecordFossil;
				kWatchCmdOperationTriangle: WatchCmdTriangle;
			
				kWatchCmdBoxMoreRows: WatchCmdMoreRows(breedModel^.boxes);
				kWatchCmdBoxFewerRows: WatchCmdFewerRows(breedModel^.boxes);
				kWatchCmdBoxMoreColumns: WatchCmdMoreColumns(breedModel^.boxes);
				kWatchCmdBoxFewerColumns: WatchCmdFewerColumns(breedModel^.boxes);
				kWatchCmdBoxThickerPen: WatchCmdThickerPen;
				kWatchCmdBoxThinnerPen: WatchCmdThinnerPen;
				kWatchCmdBoxDriftSweep: WatchCmdDriftSweep;
				kWatchCmdBoxTriangleTop: WatchCmdTriangleTop;
				kWatchCmdBoxTriangleLeft: WatchCmdTriangleLeft;
				kWatchCmdBoxTriangleRight: WatchCmdTriangleRight;
{				kWatchCmdBoxHideWindow: WatchCmdHideWindow);}



				kWatchCmdPedigreeDisplayPedigree: WatchCmdDisplayPedigree;
				kWatchCmdPedigreeDrawOut: WatchCmdPedigreeDrawOut;
				kWatchCmdPedigreeNoMirrors: WatchCmdPedigreeNoMirrors;
				kWatchCmdPedigreeSingleMirror: WatchCmdPedigreeSingleMirror;
				kWatchCmdPedigreeDoubleMirror: WatchCmdPedigreeDoubleMirror;
				kWatchCmdPedigreeMove: WatchCmdPedigreeMove;
				kWatchCmdPedigreeDetach: WatchCmdPedigreeDetach;
				kWatchCmdPedigreeKill: WatchCmdPedigreeKill;

				kWatchCmdHelpCurrent: WatchCmdHelpCurrent;
				kWatchCmdHelpMisc: WatchCmdHelpMisc;

				kWatchCmdSpecClosePlayback: WatchCmdClosePlayback;
				kWatchCmdSpecBreedCurrent: WatchCmdBreedCurrent(breedModel^.boxes);
			
				kHICommandQuit: 
					status := eventNotHandledErr;
				otherwise
					BiomorphCommand(theCommand.commandID);
			end;
				MenuGreyAdjust;
				CommandHandler := status;
		end;


end.