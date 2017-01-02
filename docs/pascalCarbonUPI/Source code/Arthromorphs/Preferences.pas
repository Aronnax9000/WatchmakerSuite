unit Preferences;

{File name:  Preferences.pas}
{Function: Handle a dialog for setting Colour, Sideways, and number of rows and columns.}
{History:}
{  12/12/1990 Original by Richard Dawkins and Ted Kaehler, using Prototyper.}
{  08/02/2015 UPI modifications for CodeWarrior 7.1 and Xcode 3.1.4 + Free Pascal by Alan Canon}

interface

	uses
		{$ifc not undefined THINK_PASCAL}
		ThinkPascalUPIBridge,
		{$elsec}
		MacOSAll, 
		{$endc}
		MyGlobals, Boxes, Ted, Breeding_Window;

	procedure D_Preferences;

implementation

	const   								{These are the item numbers for controls in the Dialog}
		kPrefsOK = 1;
		kPrefsColour = 2;
		kPrefsSideways = 3;
		kPrefsNRows = 7;
		kPrefsNCols = 8;
		kPrefsControlOK: ControlID = (signature: UInt32('DAWK'); id: kPrefsOK);
		kPrefsControlColour: ControlID = (signature: UInt32('DAWK'); id: kPrefsColour);
		kPrefsControlSideways: ControlID = (signature: UInt32('DAWK'); id: kPrefsSideways);
		kPrefsControlNRows: ControlID = (signature: UInt32('DAWK'); id: kPrefsNRows);
		kPrefsControlNCols: ControlID = (signature: UInt32('DAWK'); id: kPrefsNCols);

	var
		{$ifc not undefined THINK_PASCAL}
		TheDialogPtr: DialogPeek;{Pointer to Dialogs definition record, contains the TE record}
		PreferencesDialog: DialogPtr;{Pointer to this dialog}
		HandleColour: Handle;
		HandleOK: Handle;
		HandleSideways: Handle;
		HandleNRows: Handle;
		HandleNCols: Handle;
		{$elsec}
		PreferencesWindow: WindowRef;
		
		ControlRoot: ControlRef;
		ControlColour: ControlRef;
		ControlOK: ControlRef;
		ControlSideways: ControlRef;
		ControlNRows: ControlRef;
		ControlNCols: ControlRef;
		{$endc}
	
	procedure InvalidatePreferencesWindow;
	var
		tmpRect: Rect;
		tmpRectPtr: RectPtr;
	begin
		tmpRectPtr:= GetPortBounds(PreferencesWindow, tmpRect);
		InvalWindowRect(PreferencesWindow, tmpRect);
	end;
	
	{$ifc not undefined THINK_Pascal}
	procedure InitializePrefsHandles;
	var
		DType: integer;
		tempRect: Rect;
	begin
		GetDialogItem(PreferencesDialog, kPrefsOK, DType, HandleOK, RectOK);{Get the item handle}
		GetDialogItem(PreferencesDialog, kPrefsNRows, DType, HandleNRows, tempRect);{Get the item handle}
		GetDialogItem(PreferencesDialog, kPrefsNCols, DType, HandleNCols, tempRect);{Get the item handle}
		GetDialogItem(PreferencesDialog, kPrefsColour, DType, HandleColour, tempRect);{Get item information}
		GetDialogItem(PreferencesDialog, kPrefsSideways, DType, HandleSideways, tempRect);{Get item information}
	end;
	{$elsec}	
	procedure InitializePrefsControls;
	begin
		GetRootControl(PreferencesWindow, ControlRoot);
		GetControlByID(PreferencesWindow, kPrefsControlOK, ControlOK);
		GetControlByID(PreferencesWindow, kPrefsControlColour, ControlColour);
		GetControlByID(PreferencesWindow, kPrefsControlSideways, ControlSideways);{Get the Checkbox handle}
		GetControlByID(PreferencesWindow, kPrefsControlNRows, ControlNRows);{Get the item handle}
		GetControlByID(PreferencesWindow, kPrefsControlNCols, ControlNCols);{Get the item handle}
	end;
	{$endc}

	function HandleWindowPrefsCloseEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var
		status: OSStatus;
	begin
		status := noErr;
		QuitAppModalLoopForWindow(PreferencesWindow);
		HandleWindowPrefsCloseEvent := status;
	end;
	
	
	{Get results after dialog}
	procedure ProcessPrefsDialogResults;
	var
		{$ifc not undefined THINK_Pascal}
		CItem: controlhandle;{Control handle}
		temp: integer;
		{$endc}
			nRowsStrTmp: Str255; 			{Get text entered, temp holding}
			nColsStrTmp: Str255; 			{Get text entered, temp holding}
			nRowsCFStringRef: CFStringRef;
			nColsCFStringRef: CFStringRef;
			status: OSStatus;
			theSize: Size;

	begin
		{$ifc not undefined THINK_Pascal}
		WantColor := GetControlValue(Pointer(HandleColour)) = 1;
		Sideways := GetControlValue(Pointer(HandleSideways)) = 1;
		GetDialogItemText(HandleNRows, nRowsStrTmp);{Get the text entered}
		GetDialogItemText(HandleNCols, nColsStrTmp);{Get the text entered}
		{$elsec}
		WantColor := GetControlValue(ControlColour) = 1;
		Sideways := GetControlValue(ControlSideways) = 1;
		status := GetControlDataSize(ControlNRows, kControlEditTextPart, kControlEditTextCFStringTag, theSize);
				
		status := GetControlData(
			ControlNRows,  
			kControlEditTextPart, 
			kControlEditTextCFStringTag, 
			sizeof(CFStringRef), 
			@nRowsCFStringRef, 
			nil);
			
 		CFStringGetPascalString(nRowsCFStringRef, @nRowsStrTmp, theSize, CFStringGetSystemEncoding);
		
		status := GetControlDataSize(ControlNCols, kControlEditTextPart, kControlEditTextCFStringTag, theSize);
				
		status := GetControlData(
			ControlNCols,  
			kControlEditTextPart, 
			kControlEditTextCFStringTag, 
			sizeof(CFStringRef), 
			@nColsCFStringRef, 
			nil);
			
 		CFStringGetPascalString(nColsCFStringRef, @nColsStrTmp, theSize, CFStringGetSystemEncoding);
		{$endc}
		StringToNum(nRowsStrTmp, NRows);
		StringToNum(nColsStrTmp, NCols);
		MidBox := 1 + (NRows * NCols) div 2;
	end;
	
	function HandleWindowPrefsOKEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var
		status: OSStatus;
	begin
		status := noErr;
		ProcessPrefsDialogResults;
		QuitAppModalLoopForWindow(PreferencesWindow);
		HandleWindowPrefsOKEvent := status;
	end;
	
	procedure DrawContent;
	var RectOK: Rect;
		tmpRectOKPtr: RectPtr;
	begin
		{$ifc not undefined THINK_Pascal}
		GetDialogItem(PreferencesDialog, kPrefsOK, DType, HandleOK, RectOK);{Get the item handle}
		{$elsec} 
		tmpRectOKPtr := GetControlBounds(ControlOK, RectOK);
		{$endc}
		PenSize(3, 3);  			{Change pen to draw thick default outline}
		InsetRect(RectOK, -4, -4);{Draw outside the button by 1 pixel}
		FrameRoundRect(RectOK, 16, 16); {Draw the outline}
		PenSize(1, 1);  			{Restore the pen size to the default value}

	end;
	
	function HandleWindowPrefsDrawContentEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var
		status: OSStatus;
	begin
		status := noErr;
		DrawContent;
		HandleWindowPrefsDrawContentEvent := status;
	end;

	{$ifc not undefined THINK_Pascal}
	function HandleItemHit(itemHit: integer): OSStatus;
	var
		status: OSStatus;
		tempRect: Rect;    		{Temporary rectangle}
		DType: Integer;    		{Type of dialog item}
		DItem: Handle; 			{Handle to the dialog item}
	begin
		status := noErr;
		GetDialogItem(PreferencesDialog, itemHit, DType, DItem, tempRect);{Get item information}
		case ItemHit of
			kPrefsOK: 
				status := eventNotHandledErr;{Exit the dialog when this selection is made}
			kPrefsColour, kPrefsSideways:
				SetControlValue(Pointer(DItem),  1 - GetControlValue(Pointer(DItem)));{Toggle the value to the opposite}
		end;
		HandleItemHit := status;		
	end;
		
	procedure DoPrefsClassicEventLoop;
	var
		status: OSStatus;
		itemHit: integer;
	begin
		repeat  					{Start of dialog handle loop}
			ModalDialog(nil, itemHit);{Wait until an item is hit}
			status := HandleItemHit(itemHit);
		until status = eventNotHandledErr;   		{Handle dialog items until exit selected}
	end;
	{$endc}


	{This is an update routine for non-controls in the dialog}
	{This is executed after the dialog is uncovered by an alert}
	{Fixing a bug where the port wasn't preserved between calls. - ABC}
	{$ifc not undefined THINK_PASCAL}
	procedure Refresh_Dialog;   		{Refresh the dialogs non-controls}
	var savePort: GrafPtr;
	begin
		GetPort(savePort);
			
		SetPort(PreferencesDialog);  	{Point to our dialog window}
		DrawContent;
		SetPort(savePort);
	end;
	{$endc}

	procedure SetPrefsDialogControlValues;
		var
			{$ifc not undefined THINK_Pascal}
			ThisEditText: TEHandle; {Handle to get the Dialogs TE record}
			{$elsec}
			status: OSStatus;
			{$endc}
			nRowsStrTmp: Str255; 			{Get text entered, temp holding}
			nColsStrTmp: Str255; 			{Get text entered, temp holding}
			nRowsCFStringRef: CFStringRef; 			{Get text entered, temp holding}
			nColsCFStringRef: CFStringRef; 			{Get text entered, temp holding}
	begin

		{$ifc not undefined THINK_PASCAL}
		TheDialogPtr := DialogPeek(PreferencesDialog);{Get to the inner record}
		ThisEditText := TheDialogPtr^.textH;{Get to the TE record}
		HLock(Handle(ThisEditText));{Lock it for safety}
		ThisEditText^^.txSize := 12;{TE Point size}
		TextSize(12);   			{Window Point size}
		ThisEditText^^.txFont := systemFont;{TE Font ID}
		TextFont(systemFont);   	{Window Font ID}
		ThisEditText^^.txFont := 0;{TE Font ID}
		ThisEditText^^.fontAscent := 12;{Font ascent}
		ThisEditText^^.lineHeight := 12 + 3 + 1;{Font ascent + descent + leading}
		HUnLock(Handle(ThisEditText));{UnLock the handle when done}
		{$endc}
		NumToString(NRows, nRowsStrTmp);
		NumToString(NCols, nColsStrTmp);

		{Set checkboxen to the state of their application counterparts.}
		{$ifc not undefined THINK_Pascal}
		SetControlValue(Pointer(HandleColour), integer(WantColor));
		SetControlValue(Pointer(HandleSideways),  integer(Sideways))
		SetDialogItemText(HandleNRows, nRowsStrTmp);    	{Set the current value of NRows into dialog}
		SetDialogItemText(HandleNCols, nColsStrTmp);    	{Set the current value of NCols into dialog}
		{$elsec}
		SetControlValue(ControlColour, integer(WantColor));
		SetControlValue(ControlSideways,  integer(Sideways));
		
		nRowsCFStringRef := CFStringCreateWithPascalString( nil, nRowsStrTmp, CFStringGetSystemEncoding);
		CFStringGetPascalString(nRowsCFStringRef, @nRowsStrTmp, CFStringGetLength(nRowsCFStringRef), CFStringGetSystemEncoding);
		
		status := SetControlData(ControlNRows,
			kControlEntireControl, 
			kControlEditTextInsertCFStringRefTag, 
			sizeof(CFStringRef), 
			@nRowsCFStringRef);

		nColsCFStringRef := CFStringCreateWithPascalString( nil, nColsStrTmp, CFStringGetSystemEncoding); 

 		status := SetControlData(ControlNCols, kControlEntireControl, kControlEditTextInsertCFStringRefTag, 
			sizeof(CFStringRef), @nColsCFStringRef);
 		{$endc}
	end;
	
	procedure InstallPrefsEventHandlers;
	begin
		InstallStandardEventHandler(GetWindowEventTarget(PreferencesWindow));
		InstallStandardEventHandler(GetControlEventTarget(ControlNRows));
		InstallStandardEventHandler(GetControlEventTarget(ControlNCols));
		InstallEventHandler(GetWindowEventTarget(PreferencesWindow), HandleWindowPrefsCloseEvent, 1, @windowEventCloseType, nil, nil);
		InstallEventHandler(GetWindowEventTarget(PreferencesWindow), HandleWindowPrefsDrawContentEvent, 1, @windowEventDrawContentType, nil, nil);
		InstallEventHandler(GetControlEventTarget(ControlOK), HandleWindowPrefsOKEvent, 1, @windowEventControlHitType, nil, nil);
	end;
	
	procedure D_Preferences;
		var
			status: OSStatus;
			theNib: IBNibRef;
	begin   							{Start of dialog handler}
		{$ifc not undefined THINK_PASCAL}
		PreferencesDialog := GetNewDialog(8, nil, Pointer(-1));{Bring in the dialog resource}
		InitializePrefsHandles;
		ShowWindow(PreferencesDialog);{Open a dialog box}
		SelectWindow(PreferencesDialog);{Lets see it}
		{$elsec}
		status := CreateNibReference(CFSTR('Brand_New'), theNib);
		if status <> NoErr then exit;
		CreateWindowFromNib(theNib, CFSTR('Preferences'), PreferencesWindow);
		InitializePrefsControls;
		ShowWindow(PreferencesWindow);{Open a dialog box}
		SelectWindow(PreferencesWindow);{Lets see it}
		{$endc}

		SetPrefsDialogControlValues;

		{$ifc not undefined THINK_Pascal}
		Refresh_Dialog;     		{Draw any Lists, popups, lines, or rectangles}
		DoPrefsClassicEventLoop;
		ProcessPrefsDialogResults;
		DisposeDialog(PreferencesDialog);{Flush the dialog out of memory}
		{$elsec}
		InstallPrefsEventHandlers;
		{Try to trigger an update event for the window}
		{DrawDialog(PreferencesDialog);}
		RunAppModalLoopForWindow(PreferencesWindow);{Wait until an item is hit}
		DisposeWindow(PreferencesWindow);{Flush the dialog out of memory}
		{$endc}


		Open_Breeding_Window;
	end;    							{End of procedure}

end.    							{End of unit}