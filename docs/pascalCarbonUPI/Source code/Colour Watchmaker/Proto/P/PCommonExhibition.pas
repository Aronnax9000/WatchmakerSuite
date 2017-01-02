unit PCommonExhibition;  												{Common}

{Unit name:  PCommonExhibition.p  }
{Function:  Common for the Prototyper specific code.}
{History: 26/6/91 Original by Prototyper.   }

interface

	uses MacOSAll;
{=======================================================}



	const
	windowEventDrawContentType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowDrawContent);
	windowEventCloseType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowClose);
	windowEventBoundsChangedType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowBoundsChanged);
	windowEventResizeCompletedType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowResizeCompleted);
	windowEventHandleContentClickType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowHandleContentClick);
	windowEventHandleUpdateType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowUpdate);
	windowEventControlHitType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlHit);
	commandEventType: EventTypeSpec = (eventClass: kEventClassCommand; eventKind: kEventCommandProcess);
	MouseMovedEventType: EventTypeSpec = (eventClass: kEventClassMouse; eventKind: kEventMouseMoved);
	eventControlValueFieldChangedType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlValueFieldChanged);
	eventControlTrackType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlTrack);

		{User event definitions}
		UserEvent_None = 0;    													{No user events available}
		UserEvent_Open_Window = 1;     										{Open Window or modeless dialog}
		UserEvent_Close_Window = 2;    										{Close Window or modeless dialog}
		{IDs 0 to 999 reserved for Prototyper, all others available for special use}
		kCoulMenuIndexApple = 1;    { index into MenuList for Apple Menu }
		kCoulMenuIndexFile = 2;    { ditto for File Menu                }
		kCoulMenuIndexEdit = 3;

		{Menu list resource IDs}
		kCoulMenuApple = 256;    												{Menu resource ID}
		kCoulMenuAppleAbout = 1;

		kCoulMenuFile = 257;  													{Menu resource ID}
		kCoulMenuFileTiming = 1;
		kCoulMenuFileQuit = 2;

		kCoulMenuEdit = 258;    												{Menu resource ID}
		kCoulMenuEditCopy = 4;
		kCoulMenuEditPaste = 5;

		kCoulMenuOper = 259;   											{Menu resource ID}
		kCoulMenuOperBreed = 1;
		kCoulMenuOperNewRandom = 2;

		{Commands}
		kCoulCmdAppleAbout = UInt32('apab');
		kCoulCmdFileTiming = UInt32('fiti');  											{Menu resource ID}
		kCoulCmdOperBreed = UInt32('opbr');
		kCoulCmdOperNewRandom = UInt32('opnr');


		{Window resource IDs, also controls grouped with the appropiate window}

		Res_A_Warning_Alert = 260;  									{Alert}
		Res_Alrt_OK3 = 1;   												{...Button}
		{Static text item   Medium_Alert is 2}

		Res_D_About_Box = 262;   										{Modal Dialog}
		Res_Dlg_OK2 = 1;     												{...Button}

		Res_D_Timing_Dialogue = 257;     								{Modal Dialog}
		Res_Dlg_OK = 1;  													{...Button}
		Res_Dlg_Revert_to_New_S = 2;   								{...Radio Button}
		Res_Dlg_Don_t_revert_au = 3;     								{...Radio Button}
		Res_Dlg_Random_New_Star = 4;   								{...Radio Button}
		Res_Dlg_Random_New_Star2 = 5; 								{...Radio Button}
		{Static text item   Static_Text is 6}
		{Static text item   Static_Text2 is 7}
		{Static text item   Static_Text3 is 8}
		{Static text item   Static_Text4 is 9}
		{Static text item   Static_Text6 is 10}
		Res_Dlg_2 = 11;  													{...Edit text default string}
		Res_Dlg_5 = 12;  													{...Edit text default string}

		Res_W_BreedWindow = 256;    									{Window}

		{Icon resource IDs}

		{Picture resource IDs}



{=======================================================}


	type

		UserEventHRec = ^UserEventPRec;     							{Handle definition}
		UserEventPRec = ^UserEventRec;  								{Pointer definition}
		UserEventRec = record   											{User Event Record definition}
				ID: integer;     														{ID for the type of user event}
				ID2: integer;   														{Optionally used, 2nd ID, sometimes used}
				Data1: longint;     													{Optionally used, Extra data}
				Data2: longint;     													{Optionally used, Extra data}
				theHandle: Handle;     												{Optionally used, Handle}
				Next: UserEventHRec;     											{Handle of next event in the list}
			end;


	var
		UserEventList: UserEventHRec; 									{User Event record list start}
		myEvent: EventRecord;   											{Event record for all events}
		WNE: boolean;  														{WaitNextEvent trap is available}
		SleepValue: integer;   												{Sleep value for Wait on events}
		doneFlag: boolean; 													{Exit program flag}
		theInput: TEHandle;    												{Used in text edit selections}
		tempRect: Rect;    													{Temporary rect, not for long term use}
		sTemp: Str255;    													{Temporary string, not for long term use}
		HasColorQD: boolean; 												{Flag for Color QuickDraw being available}
		HasFPU: boolean;   													{Flag for Floating Point Math Chip being available}
		InTheForeground: boolean;    										{Flag for running in MultiFinder foreground}
		tempChar: char;    													{For temporary use}

		Menu_Apple: MenuRef;   										{Menu handle}
		Menu_File: MenuRef; 											{Menu handle}
		Menu_Edit3: MenuRef;    										{Menu handle}
		Menu_Operation: MenuRef;  									{Menu handle}

		{Variables for the modal dialog titled  "About Box"}

		{Window variables for the window titled  "BreedWindow"}
		BreedWin: WindowRef;    								{Window pointer}

		{Variables for the modal dialog titled  "Timing Dialogue"}
		DRSelected_G1_Timing_Dialogue: integer;     					{Group member currently selected}
		DRSelected_G2_Timing_Dialogue: integer;     					{Group member currently selected}

		{Variables for the alert titled  "Warning Alert"}



{=======================================================}


implementation

end.    																		{End of the Unit}