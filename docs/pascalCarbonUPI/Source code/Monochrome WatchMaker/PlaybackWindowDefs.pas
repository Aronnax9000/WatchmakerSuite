unit PlaybackWindowDefs;

interface

	uses 
		AlbumPlayback, 
		DevelopDefs,
		Globals, 
		MacOSAll, 
		Miscellaneous,
		PlaybackGeomUtil;

	const 
		PlaybackScrollbarID: ControlID = (signature: UInt32('DAWK'); id: 1);


	procedure InitializePlayBackWindow(numberOfBiomorphs: integer);


implementation

	function HandleScrollbarValueChangedEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		begin
			ShowFossil(ControlRef(userData), 0);
			HandleScrollbarValueChangedEvent := noerr;
		end; 

	function HandleScrollbarControlHitEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var 
			cpc: ControlPartCode;
			playbackScrollBar: ControlRef;
			controlValue: integer;
			maxValue: integer;
			minValue: integer;
			status: OSStatus;
		begin
			GetEventParameter (theEvent, kEventParamControlPart, typeControlPartCode, nil, sizeof (ControlPartCode), nil, @cpc);
			GetEventParameter (theEvent, kEventParamControlRef, typeControlRef, nil, sizeof (ControlRef), nil, @playbackScrollBar);
			
			controlValue := GetControlValue(playbackScrollBar);
			minValue := GetControlMinimum(playbackScrollBar);
			maxValue := GetControlMaximum(playbackScrollBar);
			case cpc of
				kControlDownButtonPart: 
					begin
						if controlValue < maxValue then
							SetControlValue(playbackScrollBar, controlValue + 1);
					end;
				kControlUpButtonPart: 
					begin
						if controlValue > minValue then
							SetControlValue(playbackScrollBar, controlValue - 1);
					end;
				otherwise
					status := eventNotHandledErr;
			end;

			HandleScrollbarControlHitEvent := status;
		end; 


	function HandlePlayBackWindowClose(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var
			status: OSStatus;
		begin
			status := noErr;
			ClosePlayback;
			HandlePlayBackWindowClose := status;
		end;

	function GetScrollbarThumbstrip(theWindow: WindowRef): Rect;
		var ThumbStrip: rect;
		begin
			GetWindowPortBounds(PlaybackWindow, ThumbStrip);

			WITH ThumbStrip DO
				{Removed 1 offsets because OS X windows don't have frames by default.}
				begin
					left := right - ScrollBarWidth;
					bottom := bottom - ScrollBarWidth;
				end;


			GetScrollbarThumbstrip := ThumbStrip;
		end;		

	{userData holds the window associated with the handler.}
	function HandlePlaybackWindowResized(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var
			status: OSStatus;
			theBoundsRect: Rect;
			playbackScrollBar: ControlRef;
		begin
			status := noErr;
(* FIXME
			GetWindowPortBounds(userData, theBoundsRect);
			EraseRect(theBoundsRect);
			GetControlByID(userData, PlaybackScrollbarID, playbackScrollBar);
			SetControlBounds(playbackScrollbar, GetScrollbarThumbstrip(userData));
			DrawControls(userData);
			Develop(theBiomorph, GetPlaybackMidPoint);	
		*)
			HandlePlaybackWindowResized := status;
		end;

		

	procedure InitializePlayBackWindow(numberOfBiomorphs: integer);
		const
			inAttributes:  array[1..4] of SInt32 = (
				kHIWindowBitCloseBox,
				kHIWindowBitZoomBox,
				kHIWindowBitCollapseBox,
				kHIWindowBitResizable
			);	
		var
			ThumbStrip: Rect;
			boundsRect: HIRect;
			status: OSStatus;
			mainID: CGDirectDisplayID;
			mainDevice: GDHandle;
			playbackScrollBar: ControlRef;

		begin

			mainID := CGMainDisplayID;
			status := DMGetGDeviceByDisplayID(mainID, mainDevice, true);
			HIWindowGetAvailablePositioningBounds(
				mainID, 
				kHICoordSpaceScreenPixel, 
				boundsRect);
			boundsRect.origin.x := boundsRect.origin.x + 50;
			boundsRect.origin.y := boundsRect.origin.y + GetMBarHeight + 80;
			boundsRect.size.height := boundsRect.size.height - GetMBarHeight - 130;
			boundsRect.size.width := boundsRect.size.width - 100;
			status := HIWindowCreate (
				kDocumentWindowClass,
				@inAttributes,
				nil,
				kHICoordSpaceScreenPixel,
				boundsRect,
				PlaybackWindow
			);
			InstallStandardEventHandler(GetWindowEventTarget(PlaybackWindow));
			InstallEventHandler(GetWindowEventTarget(PlaybackWindow), HandlePlayBackWindowClose, 1, @windowEventCloseType, nil, nil);
			SetWindowTitleWithCFString(PlaybackWindow, CFSTR('Fossil Record'));
			ThumbStrip := GetScrollbarThumbstrip(PlaybackWindow);
			CreateScrollBarControl(PlaybackWindow, ThumbStrip, 0, 0, numberOfBiomorphs, 1, true, MyAction, playbackScrollBar);
			SetControlID(playbackScrollBar, PlaybackScrollbarID);
			InstallEventHandler(GetControlEventTarget(playbackScrollBar), HandleScrollbarValueChangedEvent, 1, @eventControlValueFieldChangedType, playbackScrollBar, nil);
			InstallEventHandler(GetControlEventTarget(playbackScrollBar), HandleScrollbarControlHitEvent, 1, @windowEventControlHitType, playbackScrollBar, nil);
			InstallEventHandler(GetWindowEventTarget(PlaybackWindow), HandlePlaybackWindowResized, 1, @windowEventResizeCompletedType, PlaybackWindow, nil);

			SetPortWindowPort(PlaybackWindow);
			ShowWindow(PlaybackWindow);
			SelectWindow(PlaybackWindow);
			frontw := PlaybackWindow;
			MyControl := playbackScrollBar;
			{RunAppModalLoopForWindow(PlaybackWindow);}
	end;


end.