program Main_Exhibition;

{Program name:  Main_Exhibition.p  }
{Function:  This is the main module for this program.  }
{ History: 20/6/91 Original by Prototyper 3.0   }

	uses
		{$ifc undefined THINK_Pascal}
		CarbonEventHandlers,
		{$endc}
		PCommonExhibition, Common_Exhibition, {Common and types}
		PUtils_Exhibition, Utils_Exhibition, {General Utilities}
		InitExitExhibition, EventsExhibition, {Init, Exit, and extra event handlers}
		PA_Warning_Alert,{Alerts}
		PD_About_Box, PD_Timing_Dialogue,{Modal Dialogs}
		PW_BreedWindow,{Windows}
		PIMenu_Exhibition, PDoMenuExhibition, 
		Main_Exhibition_Util,
		MacOSAll;



begin    																	{Start of main body}

	MoreMasters; 														{This reserves space for more handles}
	{$ifc not undefined THINK_Pascal}
	MaxApplZone; 														{Give us room for memory allocation}
	InitGraf(@thePort);   												{Quickdraw Init}
	InitFonts;   															{Font manager init}
	InitWindows;   														{Window manager init}
	InitMenus;  															{Menu manager init}
	TEInit;  																{Text edit init}
	InitDialogs(nil);    													{Dialog manager}
	{$endc}
	FlushEvents(everyEvent, 0);     								{Clear out all events}
	InitCursor; 															{Make an arrow cursor}

	doneFlag := FALSE;    												{Do not exit program yet}

	Init_My_Menus;   													{Initialize menu bar}

	theInput := nil;  														{Init to no text edit selection active}

	SleepValue := 40; 													{Set sleep value}
	WNE := WNEIsImplemented;  										{See if WaitNextEvent is available}

	UserEventList := nil; 												{No user events yet}

	ApplInit_Exhibition;   												{Handle extra program initialization}
	Init_BreedWindow;    												{Initialize the window routines}
	I_PD_Timing_Dialogue;   											{Initialize the modal dialog globals}
	I_A_PA_Warning_Alert;     										{Initialize the alert globals}

	Open_BreedWindow;  												{Open the window routines at program start}

	{$ifc not undefined THINK_Pascal}
	DoClassicEventLoop;
	{$elsec}
	InstallEventHandler(GetApplicationEventTarget, CommandHandler, 1, @commandEventType, nil, nil);

	RunApplicationEventLoop;
	
	{$endc}
	

	ApplExit_Exhibition; 													{Handle extra program termination code}

end.     																	{End of the program}