unit HandleEvents;

interface
uses
		{$ifc  undefined THINK_PASCAL}
		MacOSAll,
		{$endc}
		MyGlobals, Error_Alert, 
		Preferences, Engineering_Window, Genome_Window, Breeding_Window, About_Arthromorphs, InitTheMenus, HandleTheMenus, Initialize;
{$ifc not undefined THINK_PASCAL}
procedure ClassicEventLoop;
{$endc}
implementation
{$ifc not undefined THINK_PASCAL}

procedure ClassicEventLoop;
	var 									{Main variables}
		myEvent: EventRecord;  		{Event record for all events}
		doneFlag: boolean; 			{Exit program flag}
		code: integer; 				{Determine event type}
		whichWindow: WindowPtr;    	{See which window for event}
		tempRect: Rect;          	{Rect for dragging}
		mResult: longint;  			{Menu list and item selected values}
		theMenu, theItem: integer;{Menu list and item selected}
		chCode: integer;   			{Key code}
		ch: char;  					{Key pressed in Ascii}
		theInput: TEHandle;  			{Used in text edit selections}
		Is_A_Dialog: boolean;    		{Flag for modless dialogs}
		myPt: Point; 					{Temp Point, used in Zoom}
		caretTime: longint;              {How much time WaitNextEvent should sleep}
		{$ifc  undefined THINK_PASCAL}
		tmpBitMap: BitMap;
		tmpBitMapPtr: BitMapPtr;
		tempRectPtr: RectPtr;
		status: OSStatus;
		{$endc}
		
begin
	theInput := nil;  				{Init to no text edit selection active}
	doneFlag := FALSE;  			{Do not exit program yet}
	caretTime := GetCaretTime;
	repeat  						{Start of main event loop}
		if (theInput <> nil) then{See if a TE is active}
			TEIdle(theInput);   	{Blink the cursor if everything is ok}
		{$ifc not undefined THINK_PASCAL}
		SystemTask; 				{For support of desk accessories}
		{$endc}

		if WaitNextEvent(everyEvent, myEvent, caretTime, nil) then{If event then...}
			begin   					{Start handling the event}
				code := FindWindow(myEvent.where, whichWindow);{Get which window the event happened in}

				Is_A_Dialog := IsDialogEvent(myEvent);{See if a modeless dialog event}
				if Is_A_Dialog then{Handle a dialog event}
					begin
						if (myEvent.what = UpDateEvt) then{Handle the update of a Modeless Dialog}
							begin
								whichWindow := WindowPtr(myEvent.message); {Get the window the update is for}
								BeginUpdate(whichWindow);{Set update clipping area}
								EndUpdate(whichWindow);{Return to normal clipping area}
							end
					end 					{End of Is_A_Dialog}
				else    				{Otherwise handle a window}
					begin

						case myEvent.what of{Decide type of event}
							MouseDown:{Mouse button pressed}
								begin   		{Handle the pressed button}
									if (code = inMenuBar) then{See if a menu selection}
										begin   	{Get the menu selection and handle it}
											mResult := MenuSelect(myEvent.Where);{Do menu selection}
											theMenu := HiWord(mResult);{Get the menu list number}
											theItem := LoWord(mResult);{Get the menu list item number}
											Handle_My_Menu(doneFlag, theMenu, theItem, theInput);{Handle the menu}
										end;    	{End of inMenuBar}

									if (code = InDrag) then{See if in a window drag area}
										begin   	{Do dragging the window}
											{$ifc not undefined THINK_PASCAL}
											tempRect := screenbits.bounds;{Get screen area,  l,t,r,b, drag area}
											{$elsec}
											tmpBitMapPtr := GetQDGlobalsScreenBits(tmpBitMap);
											tempRect := tmpBitMap.bounds;
											{$endc}
											SetRect(tempRect, tempRect.Left + 10, tempRect.Top + 25, tempRect.Right - 10, tempRect.Bottom - 10);{}
											{$ifc not undefined THINK_PASCAL}
											DragWindow(whichWindow, myEvent.where, tempRect);{Drag the window}
											{$elsec}
											DragWindow(whichWindow, myEvent.where, @tempRect);{Drag the window}
											{$endc}
										end;    	{End of InDrag}

									if ((code = inGrow) and (whichWindow <> nil)) then{In a grow area of the window}
										begin   	{Handle the growing}
											{$ifc not undefined THINK_PASCAL}
											SetPort(whichWindow);{Get ready to draw in this window}
											{$elsec}
											SetPort(GetWindowPort(whichWindow));{Get ready to draw in this window}
											{$endc}											
											myPt := myEvent.where;{Get mouse position}
											GlobalToLocal(myPt);{Make it relative}
											{OldRect is never used - Alan Canon}
											{OldRect := WhichWindow^.portRect;}{Save the rect before resizing}
											{$ifc not undefined THINK_PASCAL}
											tempRect := screenbits.bounds;{Get screen area,  l,t,r,b, drag area}
											{$elsec}
											tmpBitMapPtr := GetQDGlobalsScreenBits(tmpBitMap);
											tempRect := tmpBitMap.bounds;
											{$endc}
											
											with tempRect do{use the screens size}
												SetRect(tempRect, 15, 15, (right - left), (bottom - top) - 20);{l,t,r,b}

{EraseRect(Oldrect);}

											{$ifc not undefined THINK_PASCAL}
											mResult := GrowWindow(whichWindow, myEvent.where, tempRect);{Grow it}
											{$elsec}
											mResult := GrowWindow(whichWindow, myEvent.where, @tempRect);{Grow it}
											{$endc}
											SizeWindow(whichWindow, LoWord(mResult), HiWord(mResult), TRUE);{Resize to result}
											Resizing := true;
											{$ifc not undefined THINK_PASCAL}
											InvalRect(WhichWindow^.portRect);
											{$elsec}
											tempRectPtr := GetPortBounds(GetWindowPort(whichWindow), tempRect);
											status := InvalWindowRect(GetWindowFromPort(GetQDGlobalsThePort), tempRect);
											{$endc}


											case (GetWRefCon(whichWindow)) of{Do the appropiate window}
												2: begin
												    {This routine only handled scroll bars, which were never implemented.} 
													{Resized_Breeding_Window(OldRect, whichWindow);}{Resized this window}
													end;
												otherwise{Handle others}
													begin{Others}
													end;{End of the otherwise}
											end;{End of the case}
											{$ifc not undefined THINK_PASCAL}
											SetPort(whichWindow);{Get ready to draw in this window}
											{$elsec}
											SetPort(GetWindowPort(whichWindow));{Get ready to draw in this window}
											{$endc}

											SetRect(tempRect, 0, myPt.v - 15, myPt.h + 15, myPt.v + 15); {Position for horz scrollbar area}
											EraseRect(tempRect);{Erase old area}
											{$ifc not undefined THINK_PASCAL}
											InvalRect(tempRect);{Flag us to update it}
											{$elsec}
											status := InvalWindowRect(whichWindow, tempRect);
											{$endc}
											SetRect(tempRect, myPt.h - 15, 0, myPt.h + 15, myPt.v + 15);  {Position for vert scrollbar area}
											EraseRect(tempRect);{Erase old area}
											{$ifc not undefined THINK_PASCAL}
											InvalRect(tempRect);{Flag us to update it}
											{$elsec}
											status := InvalWindowRect(whichWindow, tempRect);
											{$endc}
											DrawGrowIcon(whichWindow);{Draw the grow Icon again}


										end;    	{End of doing the growing}

									if (code = inZoomIn) or (code = inZoomOut) then{Handle Zooming windows}
										begin   	{}
											if (WhichWindow <> nil) then{See if we have a legal window}
												begin{}
												
													{$ifc not undefined THINK_PASCAL}
													SetPort(whichWindow);{Get ready to draw in this window}
													{$elsec}
													SetPort(GetWindowPort(whichWindow));{Get ready to draw in this window}
													{$endc}

													myPt := myEvent.where;{Get mouse position}
													GlobalToLocal(myPt);{Make it relative}
													{OldRect is never used -- Alan Canon}
													{OldRect := whichWindow^.portRect;}{Save the rect before resizing}

													if TrackBox(whichWindow, myPt, code) then{Zoom it}
														begin
															ZoomWindow(whichWindow, code, TRUE);{Resize to result}
															SetRect(tempRect, 0, 0, 32000, 32000);{l,t,r,b}
															EraseRect(tempRect);{Make sure we update the whole window effectively}
															{$ifc not undefined THINK_PASCAL}
															InvalRect(tempRect);{Flag us to update it}
															{$elsec}
															status := InvalWindowRect(whichWindow, tempRect);
															{$endc}
															case (GetWRefCon(whichWindow)) of{Do the appropiate window}
																2: 
																	begin
																		{Only handled never-implemented scroll bars}
																		{Resized_Breeding_Window(OldRect, whichWindow)}{Resized this window}
																	end;
																otherwise{Handle others dialogs}
																	begin{Others}
																	end;{End of the otherwise}
															end;{End of the case}
														end;
												end;
										end;

									if (code = inGoAway) then{See if in a window goaway area}
										begin   	{Handle the goaway button}
											if TrackGoAway(whichWindow, myEvent.where) then{See if mouse released in GoAway box}
												begin{Handle the GoAway}
													case (GetWRefCon(whichWindow)) of{Do the appropiate window}
														1: 
															Close_Genome_Window(whichWindow);{Close this window}
														2: 
															Close_Breeding_Window(whichWindow);{Close this window}
														3: 
															Close_About_Arthromorphs(whichWindow);{Close this window}
														otherwise{Handle others dialogs}
															begin{Others}
															end;{End of the otherwise}
													end;{End of the case}
												end;{End of TrackGoAway}
										end;    	{End of InGoAway}

									if (code = inContent) then{See if in a window}
										begin   	{Handle the hit inside a window}
											if (whichWindow <> FrontWindow) then{See if already selected or not, in front if selected}
												SelectWindow(whichWindow){Select this window to make it active}
											else{If already in front the already selected}
												begin{Handle the button in the content}
												
													{$ifc not undefined THINK_PASCAL}
													SetPort(whichWindow);{Get ready to draw in this window}
													{$elsec}
													SetPort(GetWindowPort(whichWindow));{Get ready to draw in this window}
													{$endc}

													case (GetWRefCon(whichWindow)) of{Do the appropiate window}
														1: 
															Do_Genome_Window(myEvent);{Handle this window}
														2: 
															Do_Breeding_Window(myEvent);{Handle this window}
														3: 
															Do_About_Arthromorphs(myEvent);{Handle this window}
														otherwise{Handle others dialogs}
															begin{Others}
															end;{End of the otherwise}
													end;{End of the case}
												end;{End of else}
										end;    	{End of inContent}

									if (code = inSysWindow) then{See if a DA selection}
										{$ifc not undefined THINK_PASCAL}
										SystemClick(myEvent, whichWindow);{Let other programs in}
										{$endc}

								end;    		{End of MouseDown}

							KeyDown, AutoKey:{Handle key inputs}
								begin   		{Get the key and handle it}
									with myevent do{Check for menu command keys}
										begin   	{}
											chCode := BitAnd(message, CharCodeMask);{Get character}
											ch := CHR(chCode);{Change to ASCII}
											if (Odd(modifiers div CmdKey)) then{See if Command key is down}
												begin{}
													mResult := MenuKey(ch);{See if menu selection}
													theMenu := HiWord(mResult);{Get the menu list number}
													theItem := LoWord(mResult);{Get the menu item number}
													if (theMenu <> 0) then{See if a list was selected}
														Handle_My_Menu(doneFlag, theMenu, theItem, theInput);{Do the menu selection}
													if ((ch = 'x') or (ch = 'X')) and (theInput <> nil) then{}
														TECut(theInput);{Handle a Cut in a TE area}
													if ((ch = 'c') or (ch = 'C')) and (theInput <> nil) then{}
														TECopy(theInput);{Handle a Copy in a TE area}
													if ((ch = 'v') or (ch = 'V')) and (theInput <> nil) then{}
														TEPaste(theInput);{Handle a Paste in a TE area}
												end
											else if (theInput <> nil) then{}
												TEKey(ch, theInput);{}
										end;
								end;    		{End for KeyDown,AutoKey}

							UpDateEvt:{Update event for a window}
								begin   		{Handle the update}
									whichWindow := WindowPtr(myEvent.message);{Get the window the update is for}
									BeginUpdate(whichWindow);{Set the clipping to the update area}
									case (GetWRefCon(whichWindow)) of{Do the appropiate window}
										1: 
											Update_Genome_Window(whichWindow);{Update this window}
										2: 
											Update_Breeding_Window(whichWindow);{Update this window}
										3: 
											Update_About_Arthromorphs(whichWindow);{Update this window}
										otherwise   	{Handle others dialogs}
											begin   		{Others}
											end;    		{End of the otherwise}
									end;    			{End of the case}
									EndUpdate(whichWindow);{Return to normal clipping area}
								end;    		{End of UpDateEvt}

							DiskEvt:   	{Disk inserted event}
								begin   		{Handle a disk event}
									if (HiWord(myevent.message) <> noErr) then{See if a diskette mount error}
										begin   	{due to unformatted diskette inserted}
											{$ifc not undefined THINK_PASCAL}
											tempRect := screenBits.bounds;
											{$elsec}
											tmpBitMapPtr := GetQDGlobalsScreenBits(tmpBitMap);
											tempRect := tmpBitMap.bounds;
											{$endc}
											with tempRect do
											begin
											myEvent.where.h := ((right - left) div 2) - (304 div 2);{Center horz}
											myEvent.where.v := ((bottom - top) div 3) - (104 div 2);{Top 3ed vertically}
											end;
											InitCursor;{Make sure it has an arrow cursor}
											{$ifc not undefined THINK_PASCAL}
											chCode := DIBadMount(myEvent.where, myevent.message);{Let the OS handle the diskette}
											{$endc}
										end;
								end;   		{End of DiskEvt}

							app1Evt:    	{Check for events generated by this program}
								begin   		{Start handling our events}
									if (HiWord(myEvent.message) = 1) and (LoWord(myEvent.Message) = 1) then{See if OPEN event for this window ID}
										Open_Genome_Window;{Open the window}
									if (HiWord(myEvent.message) = 2) and (LoWord(myEvent.Message) = 1) then{See if CLOSE event for this window ID}
										Close_Genome_Window(WindowPtr(ord4(-1)));{Close the window}
									if (HiWord(myEvent.message) = 1) and (LoWord(myEvent.Message) = 2) then{See if OPEN event for this window ID}
										Open_Breeding_Window;{Open the window}
									if (HiWord(myEvent.message) = 2) and (LoWord(myEvent.Message) = 2) then{See if CLOSE event for this window ID}
										Close_Breeding_Window(WindowPtr(ord4(-1)));{Close the window}
									if (HiWord(myEvent.message) = 1) and (LoWord(myEvent.Message) = 3) then{See if OPEN event for this window ID}
										Open_About_Arthromorphs;{Open the window}
									if (HiWord(myEvent.message) = 2) and (LoWord(myEvent.Message) = 3) then{See if CLOSE event for this window ID}
										Close_About_Arthromorphs(WindowPtr(ord4(-1)));{Close the window}
								end;    		{End handling our events}

							ActivateEvt:{Window activated event}
								begin   		{Handle the activation}
									whichWindow := WindowPtr(myevent.message);{Get the window to be activated}
									if odd(myEvent.modifiers) then{Make sure it is Activate and not DeActivate}
										begin{Handle the activate}
											SelectWindow(whichWindow);{Activate the window by selecting it}
											case (GetWRefCon(whichWindow)) of{Do the appropiate window}
												2: 
													DrawGrowIcon(whichWindow);{Draw the Grow box}
												otherwise{Handle others }
													begin{Others}
													end;{End of the otherwise}
											end;{End of the case}
										end;{End of Activate}
								end;    		{End of ActivateEvt}

							otherwise   	{Used for debugging, to see what other events are coming in}
								begin
								
							{?? ADDED FOR DEBUGGING, CATCHING OTHER EVENTS}
								end;    		{End of otherwise}

						end;    			{End of case}

					end;    				{End for not a modeless dialog event}
			end;						{end of GetNextEvent}
	until doneFlag; 				{End of the event loop}
	end;
	{$endc}
end.    								{End of the program}