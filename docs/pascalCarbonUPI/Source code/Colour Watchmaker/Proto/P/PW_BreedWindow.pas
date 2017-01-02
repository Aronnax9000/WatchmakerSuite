unit PW_BreedWindow;

{File name: BreedWindow}
{Function: Handle a Window}
{ History: 20/6/91 Original by Prototyper 3.0   }

interface

	uses
		PCommonExhibition, Common_Exhibition, {Common and types}
		PUtils_Exhibition, Utils_Exhibition, {General Utilities}
		PA_Warning_Alert,{Alerts}
		PD_About_Box, PD_Timing_Dialogue,{Modal Dialogs}
		BreedWindow, Biomorphs, MacOSAll;

	{Initialize us so all our routines can be activated}
	procedure Init_BreedWindow;

	{Close our window}
	procedure Close_BreedWindow (whichWindow: WindowPtr);

	{Handle resizing scrollbars}
	procedure Resized_BreedWindow (OldRect: Rect; whichWindow: WindowPtr);

	{Our window was moved}
	procedure Moved_BreedWindow (OldRect: Rect; whichWindow: WindowPtr);

	{Update our window, someone uncovered a part of us}
	procedure Update_BreedWindow (whichWindow: WindowPtr);

	{Open our window and draw everything}
	procedure Open_BreedWindow;

	{Handle activation of our window}
	procedure Activate_BreedWindow (whichWindow: WindowPtr; Do_An_Activate: boolean);

	{Handle action to our window, like controls}
	procedure Do_BreedWindow (myEvent: EventRecord);



{=======================================================}

implementation

	var
		ScrollHHandle: ControlHandle; 											{Scrollbar for horz scrolling}
		ScrollVHandle: ControlHandle; 											{Scrollbar for vert scrolling}


{=======================================================}

	{Routine: Init_BreedWindow}
	{Purpose: Initialize our window data to not in use yet}

	procedure Init_BreedWindow;

	begin
		BreedWin := nil;     									{Make sure other routines know we are not valid yet}

		ScrollHHandle := nil;    											{Scrollbar is not valid yet}
		ScrollVHandle := nil;    											{Scrollbar is not valid yet}


		U_Init_BreedWindow; 											{Call the user window init routine}

	end;


{=======================================================}

	{Routine: Close_BreedWindow}
	{Purpose: Close out the window}

	procedure Close_BreedWindow;

	begin
		if (BreedWin <> nil) and ((BreedWin = whichWindow) or (ord4(whichWindow) = -1)) then{See if we should close this window}
			begin

				U_Close_BreedWindow;    									{Call the user window close routine}


				DisposeWindow(BreedWin);   					{Clear window and controls}
				BreedWin := nil;    								{Make sure other routines know we are closed}
			end;   															{End for if (MyWindow<>nil)}

	end;     																	{End of procedure}


{=======================================================}

	{Routine: Resized_BreedWindow}
	{Purpose: We were resized or zoomed, update the scrolling scrollbars}

	procedure Resized_BreedWindow; 									{Resized this window}
		var
			SavePort: GrafPtr;   										{Place to save the last port}
			temp2Rect: Rect; 												{temp rectangle}

	begin
		if (BreedWin = whichWindow) then 				{Only do if the window is us}
			begin
				GetPort(SavePort);     										{Save the current port}
				SetPortWindowPort(BreedWin);    							{Set the port to my window}

				U_Resized_BreedWindow(OldRect);  						{Call the user window resized routine}

				GetWindowPortBounds(BreedWin, temp2Rect){ := BreedWin^.PortRect}; 			{Get the window rectangle}
				EraseRect(temp2Rect);    									{Erase the new window area}
				InvalWindowRect(BreedWin, temp2Rect);     									{Set to update the new window area}
				{$ifc not undefined THINK_Pascal}
				if (ScrollHHandle <> nil) then     							{Only do if the control is valid}
					begin
						HLock(Handle(ScrollHHandle));     						{Lock the handle while we use it}
						tempRect := ScrollHHandle^^.contrlRect;     			{Get the last control position}
						tempRect.Top := tempRect.Top - 4;  					{Widen the area to update}
						tempRect.Right := tempRect.Right + 16;     			{Widen the area to update}
						InvalRect(tempRect);   									{Flag old position for update routine}
						tempRect := ScrollHHandle^^.contrlRect;     			{Get the last control position}
						temp2Rect := BreedWin^.PortRect;  		{Get the window rectangle}
						Index := temp2Rect.Right - temp2Rect.Left - 13;{Get the scroll area width}
						tempRect.Left := 0;  										{Pin at left edge}
						HideControl(ScrollHHandle);   							{Hide it during size and move}
						SizeControl(ScrollHHandle, Index, 16);   				{Make it 16 pixels high, std width}
						MoveControl(ScrollHHandle, tempRect.Left - 1, temp2Rect.bottom - temp2Rect.top - 15);{Size it correctly}
						ShowControl(ScrollHHandle);  							{Safe to show it now}
						HUnLock(Handle(ScrollHHandle)); 						{Let it float again}
					end;   															{End for scroll handle not nil)}

				if (ScrollVHandle <> nil) then     							{Only do if the control is valid}
					begin
						HLock(Handle(ScrollVHandle));     						{Lock the handle while we use it}
						tempRect := ScrollVHandle^^.contrlRect;     			{Get the last control position}
						tempRect.Left := tempRect.Left - 4; 					{Widen the area to update}
						tempRect.Bottom := tempRect.Bottom + 16;    		{Widen the area to update}
						InvalRect(tempRect);   									{Flag old position for update routine}
						tempRect := ScrollVHandle^^.contrlRect;     			{Get the last control position}
						temp2Rect := BreedWin^.PortRect;  		{Get the window rectangle}
						Index := temp2Rect.bottom - temp2Rect.top - 13;{Get the scroll area height}
						tempRect.Top := 0;  										{Pin at top edge}
						HideControl(ScrollVHandle);   							{Hide it during size and move}
						SizeControl(ScrollVHandle, 16, Index);   				{Make it 16 pixels wide, std height}
						MoveControl(ScrollVHandle, temp2Rect.right - temp2Rect.Left - 15, tempRect.Top - 1);{Size it correctly}
						ShowControl(ScrollVHandle);  							{Safe to show it now}
						HUnLock(Handle(ScrollVHandle)); 						{Let it float again}
					end;
					   															{End for scroll handle not nil)}
				{FIXME}
				{$endc}
				SetPort(SavePort);     										{Restore the old port}
			end;  																{End for window is us}
	end;     																	{End of procedure}



{=======================================================}

	{Routine: Moved_BreedWindow}
	{Purpose: We were moved, possibly to another screen and screen depth}

	procedure Moved_BreedWindow;  									{Moved this window}
		var
			SavePort: GrafPtr;   										{Place to save the last port}

	begin
		if (BreedWin = whichWindow) then 				{Only do if the window is us}
			begin
				GetPort(SavePort);     										{Save the current port}
				SetPortWindowPort(BreedWin);    							{Set the port to my window}

				U_Moved_BreedWindow(OldRect);   						{Call the user window moved routine}

				SetPort(SavePort);     										{Restore the old port}
			end;  																{End for window is us}
	end;     																	{End of procedure}


{=======================================================}

	{Routine: UpDate_BreedWindow}
	{Purpose: Update our window}

	procedure UpDate_BreedWindow;
		var
			SavePort: GrafPtr;   										{Place to save the last port}

	begin
		if (BreedWin <> nil) and (BreedWin = whichWindow) then{Handle the update to our window}
			begin
				GetPort(SavePort);     										{Save the current port}
				SetPortWindowPort(BreedWin);   							{Set the port to my window}



				U_Update_BreedWindow;  									{Call the user window update routine}

				DrawControls(BreedWin);    					{Draw all the controls}
				SetPort(SavePort);     										{Restore the old port}
			end;  																{End for if (MyWindow<>nil)}
	end;     																	{End of procedure}

{=======================================================}

function HandleMouseMovedEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
var
	status: OSStatus;
begin
	status := noErr;
	DoMouseMoved(theEvent);
	HandleMouseMovedEvent := status;
end;


function HandleBreedWinContentClickEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
var
	status: OSStatus;
	Mloc: Point;
	theHiPoint: HIPoint;
begin
	status := noErr;
	GetEventParameter (theEvent, kEventParamWindowMouseLocation, typeHIPoint, nil, sizeof (HIPoint), nil, @theHIPoint);
	HIPointConvert(theHIPoint, kHICoordSpaceScreenPixel, nil, kHICoordSpaceWindow, BreedWin);
	Mloc.h := trunc(theHiPoint.x);
	Mloc.v := trunc(theHiPoint.y);
	MyDoEvent(BreedWin, Mloc);
	HandleBreedWinContentClickEvent := status;
end;

	{Routine: Open_BreedWindow}
	{Purpose: Open our window}

	procedure Open_BreedWindow;
		var
			theLong: longint;   												{Used for HotSpot setup}
		{$ifc not undefined THINK_Pascal}
		{$elsec}
		status: OSStatus;
		theNib: IBNibRef;
		{$endc}

	begin

		if (BreedWin = nil) then   							{See if already opened}
			begin


				{$ifc not undefined THINK_Pascal}
				BreedWin := GetNewCWindow(Res_W_BreedWindow, nil, Pointer(-1));{Get the window from the resource file}
				{$elsec}
				status := CreateNibReference(CFSTR('Exhibition'), theNib);
				if status <> NoErr then exit;
				CreateWindowFromNib(theNib, CFSTR('BreedWindow'), BreedWin);
				DisposeNibReference(theNib);
				InstallStandardEventHandler(GetWindowEventTarget(BreedWin));
				InstallEventHandler(
					GetWindowEventTarget(BreedWin), 
					HandleBreedWinContentClickEvent, 
					1, 
					@windowEventHandleContentClickType, BreedWin, nil);
				
				InstallEventHandler(
					GetWindowEventTarget(BreedWin), 
					HandleMouseMovedEvent, 
					1, 
					@MouseMovedEventType, nil, nil);
				{$endc}
				SetPortWindowPort(BreedWin);    							{Prepare to write into our window}

				U_Open_BreedWindow;     									{Call the users window open routine}

				ShowWindow(BreedWin); 						{Show the window now}

			end    															{End for if (MyWindow<>nil)}
		else
			SelectWindow(BreedWin);     					{Already open, so show it}

	end; 																	{End of procedure}


{=======================================================}

	{Routine: Activate_BreedWindow}
	{Purpose: We activated or deactivated.}

	procedure Activate_BreedWindow;    								{Activated or deactivated this window}
		var
			SavePort: GrafPtr;   										{Place to save the last port}

	begin
		if (BreedWin = whichWindow) then 				{Only do if the window is us}
			begin
				GetPort(SavePort);     										{Save the current port}
				SetPortWindowPort(BreedWin);    							{Set the port to my window}

				if (Do_An_Activate) then 									{Handle the activate}
					begin

					end     														{End for activate}
				else
					begin   														{Start of deactivate}

						if (theInput <> nil) then 									{See if there is already a TE area}
							TEDeactivate(theInput); 								{Yes, so turn it off}
						theInput := nil;  											{Deactivate the TE area}
					end;    														{End for deactivate}

				U_Activate_BreedWindow(Do_An_Activate);  			{Call the user window activate routine}

				SetPort(SavePort);     										{Restore the old port}
			end;  																{End for window is us}
	end;     																	{End of procedure}


{=======================================================}

	{Routine: Do_BreedWindow}
	{Purpose: Handle action to our window, like controls}

	procedure Do_BreedWindow;
		var
			code: integer;   													{Location of event in window or controls}
			whichWindow: WindowPtr;    									{Window pointer where event happened}
			myPt: Point;     													{Point where event happened}
			theControl: ControlHandle;    									{Handle for a control}



	begin     																{Start of Window handler}
		if (BreedWin <> nil) then 							{Handle only when the window is valid}
			begin
				code := FindWindow(myEvent.where, whichWindow);{Get where in window and which window}

				if (BreedWin = whichWindow) then
					U_DoEvent_BreedWindow(BreedWin, myEvent);

				if (myEvent.what = MouseDown) and (BreedWin = whichWindow) then
					begin
						myPt := myEvent.where;  								{Get mouse position}
						GlobalToLocal(myPt);   									{Make it relative}

					end;

			end;   															{End for if (MyWindow<>nil)}
	end; 																	{End of procedure}

	{=================================}


end.    																		{End of unit}