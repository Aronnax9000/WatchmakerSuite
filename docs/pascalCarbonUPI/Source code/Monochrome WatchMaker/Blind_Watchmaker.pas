program Blind_Watchmaker;
{$r Mono/BW2.r}
{$define WATCHMAKER_QUICKDRAW}
{************************************************************************}
{*      v1.1 Sept 1993                                                  *}
{*                                                                      *}
{*      Disabled Mutation-Gradient menu item when segmentation off      *}
{*      Call WaitNextEvent if available, else systemTask/GetNextEvent   *}
{*      Remove Repeat/Until loop on save changes.                       *}
{*      Added QuitConfirmed function and moved savechanges check there  *}
{*      Changed sleep time - when drift sweep is on, small time for bg  *}
{*        tasks;  other modes give lots of bg time.                     *}
{************************************************************************}


uses
	MacOSAll, SerializationUtil, 
	Globals, 
	Miscellaneous, 
	EvolveDefs, 
	AlbumUtil, 
	AlbumPlayback,
	HIBoxTypes,
	InitialModeDef,
	TriangleUtils, 
	Serialization, 
	ModeDefs,
	InitializeMenusDef, 
	CommandHandlerDef, 
	Initialize, 
	InitializeAnimalsDefs,
	WatchmakerCommands, 
	CursorAdjustDefs, 
	MenuGreyAdjustDefs, PersonDefs,
	WindowAdjustDef;

var breedModel: HIBreedModelPtr;

begin { main body of program}
	MoreMasters;		{10 are called automatically by Think Pascal}
	MoreMasters;		{needs to be called from CODE 1, not init segment}
	MoreMasters;
	{Are the above really necessary in 2015? - ABC}
	new(breedModel);
	InitializeProgram(breedModel);
	WindowAdjust;
	MenuGreyAdjust;
	InstallEventHandler(GetApplicationEventTarget, CommandHandler, 1, @commandEventType, breedModel, nil);
	
	RunApplicationEventLoop;
	FSCloseFork(Slides);                                 { clean everything up       }
end. { of main progr}
