// The SkelView, HIView similar to a TransSkel window.
// That means that the API is callback-based, and that a lot
// of repetitive code is eliminated from the interesting code.
// The HIView is typically a user pane.
// Note that you can't draw in mouse and key callbacks. Call HIViewSetNeedsDisplay to redraw.
// One thing is missing so far: The ability to install and handle other events like TransSkel window handlers can.
// Written by Ingemar Ragnemalm march-april 2008

unit SkelView;
interface
uses
	MacOSAll;

type
	VMDrawProcPtr = procedure(theView: HIViewRef; cgContext: CGContextRef; viewRect: CGRect; userData: Pointer);
	VMQDDrawProcPtr = procedure(theView: HIViewRef; viewRect: Rect; userData: Pointer);
	VMMouseProcPtr = procedure(theView: HIViewRef; where: HIPoint; mods: Longint; userData: Pointer);
	VMQDMouseProcPtr = procedure(theView: HIViewRef; where: Point; mods: Longint; userData: Pointer);
	VMKeyProcPtr = procedure(theView: HIViewRef; key: Char; mods: Longint; userData: Pointer);

procedure InstallSkelViewHandler(theWind: WindowRef; sig: OSType; id: Longint;
		theDrawProc: VMDrawProcPtr; theMouseProc: VMMouseProcPtr; theKeyProc: VMKeyProcPtr; userData: Pointer);
//procedure InstallQDSkelViewHandler(theWind: WindowRef; sig: OSType; id: Longint;
//		theDrawProc: VMDrawProcPtr; theMouseProc: VMQDMouseProcPtr; theKeyProc: VMKeyProcPtr);
procedure InstallQDSkelViewHandler(theWind: WindowRef; sig: OSType; id: Longint;
		theDrawProc: VMQDDrawProcPtr; theMouseProc: VMQDMouseProcPtr; theKeyProc: VMKeyProcPtr; userData: Pointer);

implementation

// These should not be here but they should be somewhere. Maybe in View Manager.

	function CGRectToRect(cgr: CGRect): Rect;
	var
		r: Rect;
	begin
		r.left := Trunc(cgr.origin.x);
		r.top := Trunc(cgr.origin.y);
		r.right := Trunc(r.left + cgr.size.width);
		r.bottom := Trunc(r.top + cgr.size.height);
		return r;
	end;	
	
	function RectToCGRect(r: Rect): CGRect;
	var
		cgr: CGRect;
	begin
		cgr.origin.x := r.left;
		cgr.origin.y := r.top;
		cgr.size.width := r.right - r.left;
		cgr.size.height := r.bottom - r.top;
		return cgr;
	end;	
	
type	
	SkelViewDataRec = record
		window: WindowRef;
		viewControlID: HIViewID;
		
		drawProc: VMDrawProcPtr;
		drawQDProc: VMQDDrawProcPtr;
		mouseProc: VMMouseProcPtr;
		mouseQDProc: VMQDMouseProcPtr;
		keyProc: VMKeyProcPtr;
		focused: Boolean;
		
		userDataPtr: Pointer;
	end;
	SkelViewDataPtr = ^SkelViewDataRec;
	
function SkelViewEventHandlerProc(nextHandler: EventHandlerCallRef; inEvent: EventRef;
								inUserData: Pointer ):OSStatus; MWPascal;
var
	myViewDataPtr: SkelViewDataPtr;
	control: ControlRef;
	err: OSErr;
	
// For filtering
	eventClass: UInt32;
	eventKind: UInt32;
	key: Char;
	where: Point;
	mouseLoc: HIPoint;
	
	tempCG: Boolean;
	savePort, viewPort: GrafPtr;
	cgc: CGContextRef;
	outRect: CGRect;
	outQDRect: Rect;
	theMods: Longint;
	dragColor, saveColor: RGBColor;
	inPart: HIViewPartCode;
begin
//	WriteLn('Draw view event');
	
	eventClass := GetEventClass(inEvent);
	eventKind  := GetEventKind(inEvent);
	theMods := GetCurrentEventKeyModifiers;
	
	// Standard bye-bye
	if eventClass = kEventClassControl then
		if eventClass = kEventControlDispose then
		begin
			DisposePtr(inUserData);
			Return noErr;
		end;	
		
	myViewDataPtr := SkelViewDataPtr(inUserData);
	with myViewDataPtr^ do
	begin
		// Hämta ref till view
		err := GetControlByID( window, viewControlID, control );
		// Är inte detta fånigt? Varför lagrar jag inte det i recorden?
		
		if eventClass = kEventClassControl then
		begin
			if eventKind = kEventControlDraw then
			begin
				GetPort(savePort);
				tempCG := false;
				if drawProc = nil then
				begin // no CG callback - use QD
					err := GetEventParameter (inEvent, kEventParamGrafPort,
						typeGrafPtr, nil, SizeOf(GrafPtr), nil, @viewPort);
					if err = noErr then
						SetPort(viewPort);
					cgc := nil;
				end
				else
				begin // CG callback, draw with CG
					err := GetEventParameter (inEvent, kEventParamCGContextRef,
						typeCGContextRef, nil, SizeOf(CGContextRef), nil, @cgc);
					if err <> noErr then
					begin
						// No CG parameter available, use QuickDraw port and make CG ref from there
						err := GetEventParameter (inEvent, kEventParamGrafPort,
							typeGrafPtr, nil, SizeOf(GrafPtr), nil, @viewPort);
						if err <> noErr then
							viewPort := savePort;
						QDBeginCGContext(viewPort, cgc);
				
						tempCG := true;
					end;
				end;
				
				err := HIViewGetBounds(control, outRect);
				if err <> noErr then WriteLn('HIViewGetBounds ', err);
				
				// It the view can accept keyboard input,
				// make room for a focus frame, and draw if focused!
				if keyProc <> nil then
				begin
					//Inset CGRect
					outRect := CGRectInset(outRect, 1, 1);
					
					if focused then
					begin
						WriteLn('Drawing selection border');
						err := GetThemeBrushAsColor( kThemeBrushFocusHighlight, 32, true, dragColor );
						if cgc = nil then
						begin
							// QD focus frame
							outQDRect := CGRectToRect(outRect);
							GetForeColor(saveColor);
							RGBForeColor(dragColor);
							PenSize(2, 2);
							FrameRect(outQDRect);
							RGBForeColor(saveColor);							
							PenSize(1, 1);
						end
						else
						begin
							// CG focus frame
							CGContextSetRGBStrokeColor( cgc, dragColor.red/65535, dragColor.green/65535,
									dragColor.blue/65535, 1 );
							CGContextSetLineWidth( cgc, 2 );
							CGContextStrokeRect(cgc, outRect);
						end;
					end;
					
					//Inset CGRect
					outRect := CGRectInset(outRect, 1, 1);
				end;

				outQDRect := CGRectToRect(outRect);
		
				// Call drawing callback
				if drawProc <> nil then
					drawProc(control, cgc, outRect, userDataPtr);
				if drawQDProc <> nil then
					drawQDProc(control, outQDRect, userDataPtr);
		
				SetPort(savePort);
				if tempCG then
					QDEndCGContext(viewPort, cgc);
			end
			else
			if eventKind = kEventControlSetFocusPart then
			begin
				WriteLn('Got focus');
				GetEventParameter( inEvent, kEventParamControlPart, typeControlPartCode, nil, sizeof( HIViewPartCode ), nil, @inPart );
				case inPart of
					kControlFocusNextPart, kControlFocusPrevPart:
					if myViewDataPtr^.focused then
						return eventNotHandledErr // Can't move to next inside - go somewhere else! (Necessary for tab.)
					else
						myViewDataPtr^.focused := true;
					kControlFocusNoPart:
						myViewDataPtr^.focused := false;
				end;// case
				// Signal redraw:
				HIViewSetNeedsDisplay( control, true); // or HIViewRender for immediate action
			end
			else
			if eventKind = kEventControlClick then
			begin
				// Set focus! Important!
				if keyProc <> nil then
				if not focused then
				begin
					err := GetControlByID(myViewDataPtr^.window, myViewDataPtr^.viewControlID, control );
					SetKeyboardFocus(myViewDataPtr^.window, control, kControlFocusNoPart);
					SetKeyboardFocus(myViewDataPtr^.window, control, kControlFocusNextPart);
				end;
				
				// Get mouse location
				err := GetEventParameter(inEvent, kEventParamMouseLocation,
						typeHIPoint, nil, SizeOf(mouseLoc), nil, @mouseLoc);
				
				HIPointConvert( mouseLoc, kHICoordSpace72DPIGlobal, nil, kHICoordSpaceView, control );
				
				where.h := Trunc(mouseLoc.x);
				where.v := Trunc(mouseLoc.y);
				// Port?
//				GlobalToLocal(where);
				if mouseProc <> nil then
					mouseProc(control, mouseLoc, theMods, userDataPtr);
				if mouseQDProc <> nil then
					mouseQDProc(control, where, theMods, userDataPtr);
			end;
		end
		else
		if {(eventClass = kEventClassTextInput) or} (eventClass = kEventClassKeyboard) then
		begin {eventKind = kEventRawKeyDown}
WriteLn('KEYDOWN!!!');
			err := GetEventParameter (inEvent, kEventParamKeyMacCharCodes,
					typeChar, nil, SizeOf(Char), nil, @key);
			if Ord(key) = kTabCharCode then
				return eventNotHandledErr; // for tab focusing
			if keyProc <> nil then
				keyProc(control, key, theMods, userDataPtr);
		end;
	end;
	
	return noErr;
end;

procedure InternalInstallSkelViewHandler(theWind: WindowRef; sig: OSType; id: Longint;
		theDrawProc: VMDrawProcPtr; theQDDrawProc: VMQDDrawProcPtr; theMouseProc: VMMouseProcPtr; theQDMouseProc: VMQDMouseProcPtr; theKeyProc: VMKeyProcPtr; userData: Pointer);
var
	control: ControlRef;
	err: OSStatus;
	myDrawViewDataPtr: SkelViewDataPtr;
const
	viewEvents: array [0..4] of EventTypeSpec =
	(
		( eventClass: kEventClassControl; eventKind: kEventControlDispose ),
		( eventClass: kEventClassControl; eventKind: kEventControlDraw ),
		( eventClass: kEventClassControl; eventKind: kEventControlClick ),
		( eventClass: kEventClassControl; eventKind: kEventControlSetFocusPart ),
//		( eventClass: kEventClassControl; eventKind: kEventControlActivate), // Unnecessary?
//		( eventClass: kEventClassControl; eventKind: kEventControlDeactivate), // Unnecessary?
//		( eventClass: kEventClassControl; eventKind: kEventControlGetClickActivation), // Unnecessary?
//		( eventClass: kEventClassTextInput; eventKind: kEventTextInputUnicodeForKeyEvent), // Unnecessary?
		( eventClass: kEventClassKeyboard; eventKind: kEventRawKeyDown )
	);
begin
	myDrawViewDataPtr := SkelViewDataPtr(NewPtrClear(SizeOf(SkelViewDataRec)));
	with myDrawViewDataPtr^ do
	begin
		viewControlID.signature	:= OSType(sig);
		viewControlID.id		:= id;
		err := GetControlByID( theWind, viewControlID, control );
	
		window := theWind;
		drawProc := theDrawProc;
		drawQDProc := theQDDrawProc;
		mouseProc := theMouseProc;
		mouseQDProc := theQDMouseProc;
		keyProc := theKeyProc;
		focused := false;
		userDataPtr := userData;
		
		err	:= InstallEventHandler( GetControlEventTarget(control), SkelViewEventHandlerProc, Length(viewEvents), viewEvents, myDrawViewDataPtr, nil );
		if err <> noErr then WriteLn('Failed to install event handler ', err);
		
		HIViewSetNeedsDisplay(control, true);
	end;
end;

procedure InstallSkelViewHandler(theWind: WindowRef; sig: OSType; id: Longint;
		theDrawProc: VMDrawProcPtr; theMouseProc: VMMouseProcPtr; theKeyProc: VMKeyProcPtr; userData: Pointer);
begin
	InternalInstallSkelViewHandler(theWind, sig, id,
		theDrawProc, nil, theMouseProc, nil, theKeyProc, userData);
end;

procedure InstallQDSkelViewHandler(theWind: WindowRef; sig: OSType; id: Longint;
		theDrawProc: VMQDDrawProcPtr; theMouseProc: VMQDMouseProcPtr; theKeyProc: VMKeyProcPtr; userData: Pointer);
begin
	InternalInstallSkelViewHandler(theWind, sig, id,
		nil, theDrawProc, nil, theMouseProc, theKeyProc, userData);
end;

end.
