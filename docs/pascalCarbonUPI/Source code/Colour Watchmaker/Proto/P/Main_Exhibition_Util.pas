unit Main_Exhibition_Util;

{ History: 20/6/91 Original by Prototyper 3.0   }

interface
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
		PIMenu_Exhibition, PDoMenuExhibition, MacOSAll;


	function WNEIsImplemented: boolean;  							{See if WaitNextEvent is available}
	{$ifc not undefined THINK_Pascal}
	procedure DoClassicEventLoop;
	{$endc}

implementation

{=======================================================}

	{Routine: WNEIsImplemented}
	{Purpose: See if the MultiFinder trap, WaitNextEvent, is available}
	{$ifc not undefined THINK_Pascal}

	function WNEIsImplemented: boolean;  							{See if WaitNextEvent is available}
		const
			WNETrapNumber = $A860;   									{The expected trap number}
		var
			theWorld: SysEnvRec;     										{Environment record}
			discardError: OSErr; 											{Error code returned}

	begin
		HasColorQD := FALSE;     										{Init to no color QuickDraw}
		HasFPU := FALSE; 												{Init to no floating point chip}
		InTheForeground := TRUE;    									{Init to a foreground app}
		discardError := SysEnvirons(1, theWorld);   				{Check how old this system is}
		if (theWorld.machineType < 0) then     						{Negative means really old}
			WNEIsImplemented := FALSE  								{Really old ROMs, no WNE possible}
		else
			begin
				WNEIsImplemented := TrapAvailable(WNETrapNumber, ToolTrap);{See if trap is there}
				HasColorQD := theWorld.hasColorQD;    					{Flag for Color QuickDraw being available}
				HasFPU := theWorld.hasFPU;  								{Flag for Floating Point Math Chip being available}
			end;
	end;
	{$elsec}
	function WNEIsImplemented: boolean;  							{See if WaitNextEvent is available}

	begin
		HasColorQD := TRUE;     										{Init to no color QuickDraw}
		HasFPU :=TRUE; 												{Init to no floating point chip}
		InTheForeground := TRUE;    									{Init to a foreground app}
		WNEIsImplemented := TRUE;  								{Really old ROMs, no WNE possible}
	end;
{$endc}
{=======================================================}

	{Routine: Handle_User_Event}
	{Purpose: Check for user events}

	procedure Handle_User_Event; 									{Check for user events}
		var
			TheUserEvent: UserEventRec;    								{The user event}

	begin
		GetUserEvent(TheUserEvent);   								{Check for any user events}
		if (TheUserEvent.ID <> UserEvent_None) then 				{Only do if we have any}
			begin

				case TheUserEvent.ID of   									{Key off the Event ID}
					UserEvent_Open_Window:      							{Open a Window, Dialog, or Alert}
						begin
							case (TheUserEvent.ID2) of 						{Do the appropiate window}
								Res_D_About_Box: 
									M_PD_About_Box;    		{Open this modal dialog}
								Res_W_BreedWindow: 
									Open_BreedWindow;{Open this window}
								Res_D_Timing_Dialogue: 
									M_PD_Timing_Dialogue;{Open this modal dialog}
								Res_A_Warning_Alert: 
									A_PA_Warning_Alert;{Open this alert}
								otherwise   										{Handle others}
									begin
									end;
							end; 													{End of the case}
						end;

					UserEvent_Close_Window:     							{Close a Window or Modeless dialog}
						begin
							case (TheUserEvent.ID2) of 						{Do the appropiate window}
								Res_W_BreedWindow: 
									Close_BreedWindow(BreedWin);{Close this window}
								otherwise   										{Handle others}
									begin
									end;
							end; 													{End of the case}
						end;
					otherwise     												{Not standard, must be program specific}
						begin
							Handle_UserEvent(TheUserEvent); 				{Let program specific handle it}
						end;
				end;   															{End of case}

			end;    														{End of handling a user event}
	end;

{=======================================================}

	{Routine: DoKeyEvent}
	{Purpose: Handle a key pressed}

	procedure DoKeyEvent;  											{Handle key presses}
		var
			charCode: integer; 												{Key code}
			ch: char; 															{Key pressed in Ascii}
			mResult: longint;    												{Menu list and item, if a command key}
			theMenu, theItem: integer;     									{Menu list and item, if command key}

	begin
		if (HandleKey(myevent)) then    								{Allow for special key handling}
			begin

				with myevent do
					begin

						charCode := BitAnd(message, CharCodeMask);{Get the character}
						ch := CHR(charCode);    								{Change it to ASCII}

						if (Odd(modifiers div CmdKey)) then  				{See if Command key is down}
							begin
								mResult := MenuKey(ch);    						{See if a menu selection}
								theMenu := HiWord(mResult);   					{Get the menu list number}
								theItem := LoWord(mResult);    					{Get the menu item number}
								if (theMenu <> 0) then    							{See if a list was selected}
									Handle_My_Menu(theMenu, theItem);  		{Do the menu selection}

								if ((ch = 'f') or (ch = 'F')) then
									begin
										if FileMenuEnabled then
											DisableMenuItem(Menu_File, 0)
										else
											EnableMenuItem(Menu_File, 0);
										DrawMenuBar;
										FileMenuEnabled := not FileMenuEnabled
									end;
								if ((ch = 'x') or (ch = 'X')) and (theInput <> nil) then{See if a standard Cut}
									TECut(theInput);    								{Handle a Cut in a TE area}
								if ((ch = 'c') or (ch = 'C')) and (theInput <> nil) then{See if a standard Copy}
									TECopy(theInput); 								{Handle a Copy in a TE area}
								if ((ch = 'v') or (ch = 'V')) and (theInput <> nil) then{See if a standard Paste}
									TEPaste(theInput);     							{Handle a Paste in a TE area}
							end  													{End of Command key special condition}
						else if (theInput <> nil) then
							TEKey(ch, theInput);  								{Place the normal key stroke}
					end;    														{End of with}

			end;   															{End of Standard keystroke handling}
	end;

{=======================================================}

	{Routine: DoDiskEvent}
	{Purpose: Handle a diskette inserted}

	procedure DoDiskEvent;  											{Handle disk inserted}
		var
			theError: integer;  												{Error returned from mount}

	begin
		{$ifc not undefined THINK_Pascal}
		if (HandleDisk(myevent)) then   								{Allow for special disk inserted handling}
			begin

				if (HiWord(myevent.message) <> noErr) then  			{See if a diskette mount error}
					begin   														{due to unformatted diskette inserted}
						myEvent.where.h := ((screenbits.bounds.Right - screenbits.bounds.Left) div 2) - (304 div 2);{Center horz}
						myEvent.where.v := ((screenbits.bounds.Bottom - screenbits.bounds.Top) div 3) - (104 div 2);{Top 3ed vertically}
						InitCursor;    												{Make sure it has an arrow cursor}
						theError := DIBadMount(myEvent.where, myevent.message);{Let the OS handle the diskette}
					end;

			end;   															{End of Standard disk inserted handling}
		{$endc}
	end;

{=======================================================}

	{Routine: DoGrow}
	{Purpose: Handle a window resize}

	procedure DoGrow (WhichWindow: WindowPtr);   				{Handle a window being resized}
		var
			OldRect: Rect;   													{Window rect before the grow}
			myPt: Point;     													{Point for tracking grow box}
			GrowRect: Rect;    												{Set the grow bounds}
			mResult: longint;    												{Result from the grow}
			tmpBitMap: BitMap;
	begin
		if (WhichWindow <> nil) then 									{See if we have a legal window}
			begin
				SetPortWindowPort(whichWindow);    									{Get ready to draw in this window}

				myPt := myEvent.where; 									{Get mouse position}
				GlobalToLocal(myPt);  										{Make it relative}
				GetWindowPortBounds(whichWindow, OldRect);{Save the rect before resizing}
				
				with GetQDGlobalsScreenBits(tmpBitMap)^.bounds do 									{use the screens size}
					SetRect(GrowRect, 4, 4, (right - left) - 4, (bottom - top) - 4);{l,t,r,b}
				{$ifc not undefined THINK_Pascal}
				mResult := GrowWindow(whichWindow, myEvent.where, GrowRect);{Grow it}
				{$elsec}
				mResult := GrowWindow(whichWindow, myEvent.where, @GrowRect);{Grow it}
				{$endc}
				SizeWindow(whichWindow, LoWord(mResult), HiWord(mResult), TRUE);{Resize to result}


				SetPortWindowPort(whichWindow);    									{Get ready to draw in this window}
				GetWindowPortBounds(whichWindow, GrowRect);
				myPt.h := GrowRect.right - GrowRect.left; {Local right edge}
				myPt.v := GrowRect.bottom - GrowRect.top; {Local bottom edge}

				SetRect(GrowRect, 0, myPt.v - 15, myPt.h + 15, myPt.v + 15); {Position for horz scrollbar area}
				EraseRect(GrowRect); 										{Erase old area}
				InvalWindowRect(whichWindow, GrowRect);  										{Flag us to update it}

				SetRect(GrowRect, myPt.h - 15, 0, myPt.h + 15, myPt.v + 15);  {Position for vert scrollbar area}
				EraseRect(GrowRect); 										{Erase old area}
				InvalWindowRect(whichWindow, GrowRect);  										{Flag us to update it}

				DrawGrowIcon(whichWindow);   							{Draw the grow Icon again}
			end;   															{End of (WhichWindow <> nil)}
	end;

{=======================================================}

	{Routine: DoDrag}
	{Purpose: Drag a window around}

	procedure DoDrag (WhichWindow: WindowPtr);    				{Handle a window being dragged}
		var
			OldRect: Rect;   													{Window rect before the drag}
			tempRect: Rect;     												{temporary rect}
			tmpBitMap: BitMap;
	begin
		GetWindowPortBounds(whichWindow, OldRect); {Save the rect before resizing}
		
		
		tempRect := GetQDGlobalsScreenBits(tmpBitMap)^.bounds;  							{Get screen area,  l,t,r,b, drag area}
		SetRect(tempRect, tempRect.Left + 4, tempRect.Top + 4, tempRect.Right - 4, tempRect.Bottom - 4);
		{$ifc not undefined THINK_Pascal}
		DragWindow(whichWindow, myEvent.where, tempRect);{Drag the window}
		{$elsec}
		DragWindow(whichWindow, myEvent.where, @tempRect);{Drag the window}
		{$endc}

		case (GetWRefCon(whichWindow)) of   						{Do the appropiate window}
			Res_W_BreedWindow: 
				Moved_BreedWindow(OldRect, whichWindow);{Moved this window}
			otherwise    													{Handle others}
				begin
				end;
		end;  																{End of the case}

	end;

{=======================================================}

	{Routine: DoGoAway}
	{Purpose: Close a window}

	procedure DoGoAway (WhichWindow: WindowPtr);    			{Handle a window goaway box}
		var
			OldRect: Rect;   													{Window rect before the drag}
			tempRect: Rect;     												{temporary rect}

	begin
		if TrackGoAway(whichWindow, myEvent.where) then{See if mouse released in GoAway box}
			begin  															{Handle the GoAway}
				case (GetWRefCon(whichWindow)) of    					{Do the appropiate window}
					Res_W_BreedWindow: 
						Close_BreedWindow(whichWindow);{Close this window}
					otherwise     												{Handle others}
						begin
						end;
				end;   															{End of the case}
			end;   															{End of TrackGoAway}

	end;

{=======================================================}

	{Routine: DoInContent}
	{Purpose: Pressed in the content area}

	procedure DoInContent (WhichWindow: WindowPtr);  			{Handle a hit in the window}

	begin
		if (whichWindow <> FrontWindow) then     					{See if already selected or not, in front if selected}
			SelectWindow(whichWindow) 								{Select this window to make it active}
		else  																{If already in front the already selected}
			begin  															{Handle the press in the content}
				SetPortWindowPort(whichWindow);    									{Get ready to draw in this window}
				case (GetWRefCon(whichWindow)) of    					{Do the appropiate window}
					Res_W_BreedWindow: 
						Do_BreedWindow(myEvent);{Handle this window}
					otherwise     												{Handle others}
						begin
						end;
				end;   															{End of the case}
			end;   															{End of else}

	end;

{=======================================================}

	{Routine: DoUpdate}
	{Purpose: Got an update event}

	procedure DoUpdate; 												{Handle an update to the window}
		var
			whichWindow: WindowPtr;    									{See which window for event}

	begin
		whichWindow := WindowPtr(myEvent.message);{Get the window the update is for}

		BeginUpdate(whichWindow);    						{Set the clipping to the update area}
		case (GetWRefCon(whichWindow)) of   						{Do the appropiate window}
			Res_W_BreedWindow: 
				Update_BreedWindow(whichWindow);{Update this window}
			otherwise    													{Handle others}
				begin
				end;
		end;  																{End of the case}
		EndUpdate(whichWindow);  							{Return to normal clipping area}
	end;

{=======================================================}

	{Routine: DoActivate}
	{Purpose: Got an activate or deactivate event}

	procedure DoActivate;   											{Handle an activate of the window}
		var
			Do_An_Activate: Boolean;     									{Flag to pass}
			whichWindow: WindowPtr;    									{See which window for event}

	begin
		whichWindow := WindowPtr(myEvent.message);{Get the window the update is for}

		Do_An_Activate := odd(myEvent.modifiers);  				{Make sure it is Activate and not DeActivate}
		case (GetWRefCon(whichWindow)) of   						{Do the appropiate window}
			Res_W_BreedWindow: 
				Activate_BreedWindow(whichWindow, Do_An_Activate);{Activate or deactivate this window}
			otherwise    													{Handle others}
				begin
				end;
		end;  																{End of the case}
	end;

{=======================================================}
{$ifc not undefined THINK_Pascal}

procedure DoClassicEventLoop;
begin
	repeat   																{Start of main event loop}

		ApplLoop_Exhibition;  											{Let into main loop}

		Handle_User_Event;   											{Check for user events}

		if (theInput <> nil) then    										{See if a TE is active}
			TEIdle(theInput);    											{Blink the cursor if everything is ok}

		if WNE then 														{See if do the MultiFinder way}
			DoIt := WaitNextEvent(everyEvent, myEvent, SleepValue, nil){Wait for an event}
		else
			begin
				{$ifc not undefined THINK_Pascal}
				SystemTask;     												{For support of desk accessories}
				{$endc}
				DoIt := GetNextEvent(everyEvent, myEvent); 			{See if an event is ready}
			end;

		ApplEvent_Exhibition(DoIt, myEvent);   					{Let us at the event first}

		if DoIt then   													{If event then...}
			begin   														{Start handling the event}


				case myEvent.what of  									{Decide type of event}
					MouseDown:  											{Mouse button pressed}
						begin     												{Handle the pressed button}
							code := FindWindow(myEvent.where, whichWindow);{Get which window the event happened in}

							case code of     									{Decide type of event again}
								inMenuBar: 									{In the menubar}
									begin
										mResult := MenuSelect(myEvent.Where);{Do menu selection}
										theMenu := HiWord(mResult);    				{Get the menu list number}
										theItem := LoWord(mResult);     				{Get the menu list item number}
										Handle_My_Menu(theMenu, theItem); 		{Handle the menu}
									end;    										{End of inMenuBar}

								InDrag:  										{In window drag area}
									DoDrag(whichWindow);  				{Go drag the window}

								inGrow: 										{In window grow area}
									DoGrow(whichWindow); 				{Handle the growing}

								inGoAway:  									{In window goaway area}
									DoGoAway(whichWindow); 			{Handle the goaway button}

								inContent:   									{In window  contents}
									DoInContent(whichWindow);     		{Handle the hit inside a window}
								{$ifc not undefined THINK_Pascal}

								inSysWindow:  								{See if a DA selectio}
									SystemClick(myEvent, whichWindow);{Let other programs in}
								{$endc}
								otherwise    									{Handle others}
									begin
									end;
							end;    										{End of code case}
						end; 													{End of MouseDown}

					KeyDown, AutoKey:  									{Handle key inputs}
						DoKeyEvent;    									{Get the key and handle it}

					UpDateEvt:    											{Update event for a window}
						DoUpdate;   										{Handle the update}

					DiskEvt:   												{Disk inserted event}
						DoDiskEvent;    									{Handle a disk event}

					ActivateEvt: 											{Window activated event}
						DoActivate;     									{Handle the activation}

					otherwise 												{Used for debugging, to see what other events are coming in}
						begin
						end;

				end;    														{End of case}

			end;    														{end of GetNextEvent}
	until doneFlag;  													{End of the event loop}
end;

{$endc}

end.     																	{End of the program}