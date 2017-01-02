unit DoAboutDef;
{Thanks is given by the reimplementor to Martha Ellison of the Brown School and Sanderson of Oundle.}
interface

	uses
		CursorDefs,
		Globals,
		MorphGlobals,
		MacOSAll;

	var 	
		AboutWindow: WindowRef;

	procedure DoAbout;

	
implementation

	function HandleWindowAboutCloseEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var
			status: OSStatus;
		begin
			status := noErr;
			AboutWindow := nil;
			status := eventNotHandledErr;
			HandleWindowAboutCloseEvent := status;
		end;

	{Bring up 'About...' box using a dialog box}
	{FIXME clone for Snails About dialog, which must be different and I REALLY want to see it! ABC}
	procedure DoAbout;
		VAR
			status: OSStatus;
			theNib: IBNibRef;
			tempCursor: Cursor;
		begin
			SetCursor(CursList[CrossCursor]^^);      				{set to my cursor}
			if (AboutWindow = nil) then    	{Handle an open when already opened}
			begin

				status := CreateNibReference(CFSTR(NibFileNameSansExtension), theNib);
				if status <> NoErr then exit;
				CreateWindowFromNib(theNib, CFSTR('About'), AboutWindow);

				InstallStandardEventHandler(GetWindowEventTarget(AboutWindow));
				InstallEventHandler(GetWindowEventTarget(AboutWindow), HandleWindowAboutCloseEvent, 1, @windowEventCloseType, nil, nil);
				SetPort(GetWindowPort(AboutWindow));  		{Prepare to write into our window}
				ShowWindow(AboutWindow);   	{Show the window now}
				SelectWindow(AboutWindow);{Bring our window to the front}
				
			end 							{End for if (AboutWindow<>nil)}
			else
				SelectWindow(AboutWindow);{Already open, so show it}
									{End of procedure}


			SetCursor(GetQDGlobalsArrow(tempCursor)^);
		end; { of proc DoAbout }

end.