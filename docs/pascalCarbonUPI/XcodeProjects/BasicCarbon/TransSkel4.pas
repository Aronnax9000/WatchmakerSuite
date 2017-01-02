{
TransSkel Pascal 4.0.2

A small and easy-to-use Mac application framework.

This is the very first version (2007) of the new Carbon Event-savy version of TransSkel.
There already was a Carbon version (TransSkel Pascal 2.7) but it used WaitNextEvent and
was very "classic" in its style overall.

This version uses Carbon Events, and is also designed to use command-based menus,
NIB files etc. "Classic" style is still possible in most aspects, but the important
thing is that it is redesigned to use the modern Carbon Events internally and that
the modern APIs should always be the preferred way.

The original TransSkel was written by Paul duBois. This version is based on TransSkel
Pascal 2.0-2.7, branched from TransSkel at TransSkel 2.0 when Owen Hartnett
translated the package to Pascal.

This version uses surprisingly much code from the original package. When I made the
modern "Skel" (not the TransSkel using one but the standalone demo), all code was
rewritten, but for TransSkel, the total design is so good that only some parts needed
total replacement (the event loop and its nearest functions). Compatibility with old
TransSkel code is pretty good.

4.0a1 (summer 07): First version after revision
4.0a2 (march 08): Exported SkelBackgroundWithDuration
4.0 (080415): TransEdit is back (old-style) but not fully working yet. Fixed a serious bug when closing window.
4.0.1 (080603): Cleanup. kEventWindowClose always handled even if no close proc is provided.
4.0.2 (081009): Some improvements in CarbonStandardFile.

NOT DONE YET:
- TransDisplay and TransEdit should be replaced by fairly small packages using MLTE.
(Done for TransDisplay.)
- Many demos can be revised to use newer APIs, especially CG and MLTE instead of
QuickDraw and TextEdit.
- I would like to make additions to make modern dialog management easier. This is an
interesting problem that requires some new ideas. The unit "ViewManager" solves a lot
of this and makes dialog (actually view) management really nice and easy.
- Replace FSSpec with FSRef. In particular, Apple Event callbacks should be passed FSRefs.
}

unit TransSkel4;
interface
uses MacOSAll;

// Procedure pointer types for callbacks.
type
	TSMouseProcPtr = PROCEDURE(thePoint: Point; theTime: UInt32; theMods: Integer);
	TSKeyProcPtr = PROCEDURE(theChar: char; theMods: integer);
	TSEventProcPtr = PROCEDURE(theitem: integer; var theEvent: EventRecord);
	TSOtherEventProcPtr = FUNCTION(var theEvent: EventRecord): Boolean;
	TSBooleanProcPtr = PROCEDURE(myBool: Boolean);
	TSIntProcPtr = PROCEDURE(myInt: integer);
	TSMenuProcPtr = PROCEDURE(myMenu: MenuHandle);
	TSNoArgProcPtr = PROCEDURE();
	TSFilterProcPtr = PROCEDURE(theDialog: DialogPtr; var theEvent: EventRecord; var result: Boolean);
	TSAEFileProcPtr = PROCEDURE(fs: FSSpec; isLastInBatch: Boolean);
	TSLongProcPtr = FUNCTION(myInt: Longint): Boolean;

// Custom carbon events can be handled with the standard handler interface.
	TSCarbonEventProcPtr = EventHandlerUPP; // or maybe FUNCTION(theEvent: EventRef; ): Boolean;

{$SETC supportDialogs = TRUE}

	procedure SkelInit;
	function SkelGetMainNib: IBNibRef;
	procedure SkelMain; // Use RunApplicationEventLoop instead.
	procedure SkelWhoa;
	procedure SkelClobber;
	procedure SkelSetCommandProc(theCommandProc: TSLongProcPtr);
	function SkelMenu (theMenu: MenuHandle; pSelect: TSIntProcPtr; pClobber: TSMenuProcPtr; DrawBar: Boolean): Boolean;
	function SkelHMenu (theMenu: MenuHandle; pSelect: TSIntProcPtr; pClobber: TSMenuProcPtr): Boolean; {Added by Ingemar 22/8 -93}
	procedure SkelRmveMenu (theMenu: MenuHandle);
	procedure SkelApple (aboutTitle: Str255; aboutProc: TSIntProcPtr);
	function SkelWindow (theWind: WindowPtr; pMouse: TSMouseProcPtr; pKey: TSKeyProcPtr;
					pUpdate: TSBooleanProcPtr; pActivate: TSBooleanProcPtr;
					pClose, pClobber: TSNoArgProcPtr; pIdle: TSNoArgProcPtr; frontOnly: Boolean): Boolean;
	function SkelCustomWindow (theWind: WindowPtr; pMouse: TSMouseProcPtr;
					pKey: TSKeyProcPtr; pUpdate: TSBooleanProcPtr;
					pActivate: TSBooleanProcPtr; pClose, pClobber: TSNoArgProcPtr;
					pIdle: TSNoArgProcPtr; frontOnly: Boolean;
					idleTime: EventTime; customEventProc: TSCarbonEventProcPtr;
					customEventTypes: EventTypeSpecPtr; customEventCount: Longint): Boolean;
	procedure SkelRmveWind (theWind: WindowPtr);
{ $IFC supportDialogs }
	function SkelDialog (theDialog: DialogPtr; pEvent: TSEventProcPtr; pClose, pClobber: TSNoArgProcPtr; pFilter: TSFilterProcPtr): Boolean; {pFilter added by Ingemar 18/9-93}
	procedure SkelRmveDlog (theDialog: DialogPtr);
{ $ENDC}
	procedure SkelGrowBounds (theWind: WindowPtr; hLO, vLo, hHi, vHi: integer);
	procedure SkelEventMask (mask: integer);
	procedure SkelGetEventMask (var mask: integer);
	procedure SkelBackground (p: TSNoArgProcPtr);
	procedure SkelBackgroundWithDuration (p: TSNoArgProcPtr; intervalTime: EventTime);
	procedure SkelGetBackground (var p: TSNoArgProcPtr);
	procedure SkelEventHook (p: TSOtherEventProcPtr);
	procedure SkelGetEventHook (var p: TSOtherEventProcPtr);
{ $IFC supportDialogs }
	procedure SkelDlogMask (mask: integer);
	procedure SkelGetDlogMask (var mask: integer);
{ $ENDC}
{Two new procedures for WNE-support, added by Ingemar 12/11-93}
	procedure SkelSetSleep (newSleep: Longint);
	procedure SkelSetMouseRgn (newMouseRgn: RgnHandle);
{Utility function, added by Ingemar 13/7-94}
	function FindWindowByRefcon (theRefCon: Longint): WindowPtr;
{Suspend/resume, added by Ingemar 23/7 -94}
	procedure SkelSetSuspendResume (p: TSBooleanProcPtr);
	function SkelGetSuspendResume: TSBooleanProcPtr;
{Mouse moved events, added by Ingemar jan -95:}
	procedure SkelSetMouseMoved (p: TSNoArgProcPtr);
	function SkelGetMouseMoved: TSNoArgProcPtr;
{Apple Event support, by Ingemar 10/4 -02}
	procedure SkelAppleEvent (openAppProc: TSNoArgProcPtr; openDocProc, printProc: TSAEFileProcPtr; quitProc: TSNoArgProcPtr);
	function SkelGetVersion: AnsiString; // Version string!


implementation

const
	kSkelVersion = '4.0.1';

{	Window and Menu handler types, constants, variables.}

{	whList and mhList are the lists of window and menu handlers.}
{	whClobOnRmve and mhClobOnRmve are true if the handler disposal proc}
{	is to be called when a handler is removed.  They are temporarily set}
{	false when handlers are installed for windows or menus that already}
{	have handlers - the old handler is removed WITHOUT calling the}
{	disposal proc.}

{	Default lower limits on window sizing of 80 pixels both directions is}
{	sufficient to allow text windows room to draw a grow box and scroll}
{	bars without having the thumb and arrows overlap.  These values may}
{	be changed if such a constraint is undesirable with SkelGrowBounds.}
{	Default upper limits are for the Macintosh, not the Lisa, but are set}
{	per machine in SkelInit.}

	type
		WHandlerHnd = ^WHandlerPtr;
		WHandlerPtr = ^WHandler;
		WHandler = record
				whWind: WindowPtr;	{window/dialog to be handled	}
				whClobber: TSNoArgProcPtr;	{ data structure disposal proc	}
				whMouse: TSMouseProcPtr;		{ mouse-click handler proc		}
				whKey: TSKeyProcPtr;		{ key-click handler proc			}
				whUpdate: TSBooleanProcPtr;		{ update handler proc				}
				whActivate: TSBooleanProcPtr;	{ activate event handler proc	}
				whClose: TSNoArgProcPtr;		{ close "event" handler proc		}
				whIdle: TSNoArgProcPtr;			{ main loop proc					}
{ $IFC supportDialogs }
				whEvent: TSEventProcPtr;		{ dialog event proc				}
				whFilter: TSFilterProcPtr;		{ dialog filter proc ADDED BY INGEMAR 18/9 -93}
{ $ENDC }
				whHasGrow: Boolean;	{ can window grow?				}
				whGrow: Rect;			{ limits on window sizing		}
				whSized: Boolean;		{ true = window was resized	}
				whFrontOnly: Boolean;	{ true = idle only when active	}
				whNext: WHandlerHnd;	{ next window handler			}
				whIdleTimer: EventLoopTimerRef; // Timer for whIdle!
				whCustomEvent: TSCarbonEventProcPtr; {Handler for any other Carbon events than the supported ones}
			end;
		
		MHandlerPtr = ^MHandler;
		MHandlerHnd = ^MHandlerPtr;
		MHandler = record
				mhID: integer;				{ menu id									}
				mhSelect: TSIntProcPtr;			{ item selection handler proc			}
				mhClobber: TSMenuProcPtr;		{ menu disposal handler proc			}
				mhNext: MHandlerHnd;		{ next menu handler						}
			end;
			
	var
		whList: WHandlerHnd;				{ list of menu handlers }
		whClobOnRmve: Boolean;
		growRect: Rect;
		mhList: MHandlerHnd;
		mhClobOnRmve: Boolean;

{	Variables for default Apple menu handler.  appleID is set to 1 if}
{	SkelApple is called and is the id of the Apple menu, appleAboutProc}
{	is the procedure to execute if there is an About... item and it's}
{	chosen from the Apple menu.  If doAbout is true, then the menu}
{	contains the About... item, otherwise it's just desk accessories.}
// OBSOLETE - almost. A typical nib-using app will not use these.

		appleMenu: MenuHandle;
		appleID: integer;
		//appleAboutProc: ProcPtr;
		doAbout: Boolean;

{	"caching" global variables, for speeding up GetWDHandler.}
		oldWindow: WindowPtr;
		oldWDHandler: WHandlerHnd;

{ "command" events (identifier-based menu commands etc) are all passed to this proc: }
		gCarbonMenuCommandProc: TSLongProcPtr;
		
{ Background proc }
		pBkgnd: TSNoArgProcPtr;
		gBackgroundTimer: EventLoopTimerRef;

{ "About" menu selection }
		gAboutProc: TSIntProcPtr; // TSNoArgProcPtr; Takes integer in case you want the classic-style menu management

{	Get handler associated with user or dialog window.}
{	Return nil if window doesn't belong to any known handler.}
{	This routine is absolutely fundamental to TransSkel.}

	function GetWDHandler (theWind: WindowPtr): WHandlerHnd;
		var
			h: WHandlerHnd;
	begin
		h := WhList;
		GetWDHandler := nil;
		if theWind = oldWindow then			{  caching code 	}
			GetWDHandler := oldWDHandler
		else
			while h <> nil do
				if h^^.whWind = theWind then
					begin
						oldWindow := theWind;			{ Load in new values for new window }
						oldWDHandler := h;
						GetWDHandler := h;
						h := nil;
					end
				else
					h := WHandlerHnd(h^^.whNext);
	end;

{ Get Handler associated with user window.  Return nil if window doesn't}
{  have a Handler. }

	function GetWHandler (theWind: WindowPtr): WHandlerHnd;
		var
			h: WHandlerHnd;
	begin
{BUG FIXED by Ingemar 19/9-93. This function retured garbage when passed a dialog}
		h := GetWDHandler(theWind);
		GetWHandler := nil; {default, moved up by Ingemar}
		if h <> nil then
			begin
				if GetWindowKind(theWind) <> dialogKind then
					GetWHandler := h;
			end;
	end;


{	Get handler associated with dialog window.}
{	Return nil if window doesn't belong to any known handler.}

	function GetDHandler (theDialog: WindowPtr): WHandlerHnd;
		var
			h: WHandlerHnd;
	begin
{BUG FIXED by Ingemar 19/9-93. This function retured garbage when passed a non-dialog}
		h := GetWDHandler(theDialog);
		GetDHandler := nil; {default - moved up by Ingemar}
		if h <> nil then
			begin
				if GetWindowKind(theDialog) = dialogKind then
					GetDHandler := h;
			end;
	end;



{ -------------------------------------------------------------------- }
{							Apple Event support							}
{ -------------------------------------------------------------------- }

	var
		gOpenAppProc: TSNoArgProcPtr; gOpenDocProc, gPrintProc: TSAEFileProcPtr; gQuitProc: TSNoArgProcPtr;

	function HandleOpenDoc (var theAE: AppleEvent; var reply: AppleEvent; refcon: LongInt): OSErr;
		var
			myErr: OSErr;						{ errors from the system }
			myDocList: AEDescList;				{ the list of descriptors }
			myFSSpec: FSSpec;					{ an FSSpec record for files  (Change to FRef?) }
			numItems: LONGINT;					{ how many files are there? }
			myKeyword: AEKeyword;				{ ignored -- for AEGetNthPtr }
			myType: DescType;					{ ignored -- real type of data returned }
			realSize: Size;						{ ignored -- real size of data returned }
			count: INTEGER;						{ loop variable counter }
	begin
		if gOpenDocProc = nil then
			myErr := errAEEventNotHandled
		else
			begin
				myErr := AEGetParamDesc(theAE, keyDirectObject, typeAEList, myDocList);
				if myErr = noErr then
					begin
						myErr := AECountItems(myDocList, numItems);
						if myErr = noErr then
							begin
								for count := 1 to numItems do
									begin
										myErr := AEGetNthPtr(myDocList, count, typeFSS, @myKeyword, @myType, @myFSSpec, sizeof(FSSpec), @realSize);
										if myErr = noErr then
											gOpenDocProc(myFSSpec, count = numItems);
										{procedure MyOpenDocProc(fs: FSSpec; isLastInBatch: Boolean);}
									end;
							end;
					end;
			end;
		HandleOpenDoc := myErr;
	end; { HandleOpenDoc }

	function HandlePrintDoc (var theAE: AppleEvent; var reply: AppleEvent; refcon: LongInt): OSErr;
	begin
{Should be almost the same as HandleOpenDoc!}
		HandlePrintDoc := errAEEventNotHandled;
	end; {HandlePrintDoc}

	function HandleOpenApp (var theAE: AppleEvent; var reply: AppleEvent; refcon: LongInt): OSErr;
	begin
		if gOpenAppProc <> nil then
			gOpenAppProc();
		HandleOpenApp := noErr;
	end;

	function HandleQuit (var theAE: AppleEvent; var reply: AppleEvent; refcon: LongInt): OSErr;
	begin
//WriteLn('Quit Apple Event');

		if gQuitProc = nil then
			SkelWhoa
		else
			gQuitProc();
		HandleQuit := noErr;
	end; {HandleQuit}

	procedure InitTSAE;
	begin
		AEInstallEventHandler(kCoreEventClass, kAEOpenApplication, NewAEEventHandlerUPP(AEEventHandlerProcPtr(@HandleOpenApp)), 0, False);
		AEInstallEventHandler(kCoreEventClass, kAEOpenDocuments, NewAEEventHandlerUPP(AEEventHandlerProcPtr(@HandleOpenDoc)), 0, False);
		AEInstallEventHandler(kCoreEventClass, kAEPrintDocuments, NewAEEventHandlerUPP(AEEventHandlerProcPtr(@HandlePrintDoc)), 0, False);
		AEInstallEventHandler(kCoreEventClass, kAEQuitApplication, NewAEEventHandlerUPP(AEEventHandlerProcPtr(@HandleQuit)), 0, False);
	end;

	procedure SkelAppleEvent (openAppProc: TSNoArgProcPtr; openDocProc, printProc: TSAEFileProcPtr; quitProc: TSNoArgProcPtr);
	begin
		gOpenAppProc := openAppProc;
		gOpenDocProc := openDocProc;
		gPrintProc := printProc;
		gQuitProc := quitProc;
	end;
	
{*** End Apple Event support ***}


{For debugging}
function IToString(i: UInt32): Str255;
var
	s: Str255;
begin
	s := '    ';
	BlockMove(@i, @s[1], 4);
	return s;
end;

	procedure DoClobber (h: WHandlerHnd);
		var
			p: TSNoArgProcPtr;
			curPort: GrafPtr;
			windowPort: CGrafPtr;
	begin
		if (h <> nil) then
			begin
				GetPort(curPort);
				SetPortWindowPort(h^^.whWind);
				windowPort := GetWindowPort(h^^.whWind); // Must get this BEFORE calling the clobber proc! - fixed 080415
				p := h^^.whClobber;
				if p <> nil then
					p();
				
				// Remove timer!
				if h^^.whIdleTimer <> nil then
					RemoveEventLoopTimer(h^^.whIdleTimer);

				{Change 040901:}
				{If h^^.whWind = curPort, set the port to something valid!}
				if windowPort = curPort then
				begin
					if FrontWindow <> nil then
						SetPortWindowPort(FrontWindow)
					else
					begin
					{Sadly, Carbon has no Window Manager port!}
{$IFC TARGET_API_MAC_CARBON = FALSE}
						GetCWMgrPort(curPort);
						SetPort(curPort);
{$ENDC}
					end;
				end
				else
					SetPort(curPort);
			end;
	end;


	function WindowEventHandler (nextHandler: EventHandlerCallRef; theEvent: EventRef;
							userData: Pointer): OSStatus; MWPascal;
	var
		eventClass: UInt32;
		eventKind: UInt32;
		theTime: EventTime;
		where: Point;
		key: Char;
		err: OSErr;
		wasHandled: Boolean;
		theMods: Longint; {UInt32?}
		theWind: WindowPtr;
		h: WHandlerHnd;
		curPort: CGrafPtr;
		
		keyFunc: TSKeyProcPtr;
	begin
		eventClass := GetEventClass(theEvent);
		eventKind  := GetEventKind(theEvent);
		
		theTime := GetEventTime(theEvent);
		theMods := GetCurrentEventKeyModifiers;
		
		err := GetEventParameter (theEvent, kEventParamDirectObject,
			typeWindowRef, nil, SizeOf(WindowPtr), nil, @theWind);
			
		if err <> noErr then // Happens for keyboard funcs
		begin
			theWind := FrontWindow;
			if eventClass <> kEventClassKeyboard then
				WriteLn('Error getting window: ', err);
		end;
		
		h := GetWDHandler(theWind); // Dialog or Window!
		if h = nil then
		begin
//WriteLn('Event without handler ', Ord(theWind));
			return eventNotHandledErr;
		end;
		wasHandled := false;

		// Custom event support
		// Can filter an event by returning noErr;
		// Any event that should use standard handling should return something else (e.g. eventNotHandledErr)
		if h^^.whCustomEvent <> nil then
		begin
//WriteLn('Event for window with custom event handler');
			err := h^^.whCustomEvent(nextHandler, theEvent, userData);
			if err = noErr then
				return noErr;
		end;
		
		if eventClass = kEventClassKeyboard then
		begin
			keyFunc := h^^.whKey;
			if keyFunc <> nil then // If we have a callback installed for the window
			begin
				err := GetEventParameter (theEvent, kEventParamKeyMacCharCodes,
					typeChar, nil, SizeOf(Char), nil, @key);
				{h^^.whKey}keyFunc(key, theMods);
				wasHandled := true;
			end;
		end
		else
// This event not used. I use window content clicks instead.
//		if (eventClass = kEventClassMouse) and (eventKind = kEventMouseDown) then
//		begin
//		end
//		else
		if eventClass = kEventClassWindow then
		begin
			case eventKind of
			kEventWindowClose:
			begin
					if h^^.whClose <> nil then // If we have a callback installed for the window
					begin
						GetPort(curPort);
						SetPortWindowPort(h^^.whWind); // Set the port to theWind so the callee can figure it out
						h^^.whClose;
						SetPort(curPort);
						wasHandled := true;
					end
					else
					begin
// This never happened before, but should work now. 080603
						HideWindow(h^^.whWind);
						wasHandled := true;
						Return noErr;
					end;
			end;
			{kEventWindowUpdate,} kEventWindowDrawContent:
			begin
					if h^^.whUpdate <> nil then // If we have a callback installed for the window
					begin
						GetPort(curPort);
						SetPortWindowPort(h^^.whWind);
						
//						if eventKind = kEventWindowUpdate then
//							BeginUpdate(h^^.whWind);
						h^^.whUpdate(h^^.whSized);
						h^^.whSized := false; // Reset - we must have a new resize event to pass true again
//						if eventKind = kEventWindowUpdate then
//							EndUpdate(h^^.whWind);
						SetPort(curPort);
						wasHandled := true;
					end;
			end;
//			kEventWindowClickDragRgn:
//			begin
//WriteLn('click drag event');
//			end;
			
			kEventWindowActivated, kEventWindowDeactivated:
			begin
//WriteLn('activate event');
//				if h <> nil then
					if h^^.whActivate <> nil then // If we have a callback installed for the window
					begin
						GetPort(curPort);
						SetPortWindowPort(h^^.whWind);
						h^^.whActivate(eventKind = kEventWindowActivated);
						SetPort(curPort);
						wasHandled := true;
					end;
			end;
			
			kEventWindowBoundsChanged:
			// kEventWindowResizeStarted?
			// kEventWindowResizeCompleted?
			begin
			// Live resize support!
					if h^^.whUpdate <> nil then // If we have a callback installed for the window
					begin
						h^^.whSized := true; // Should be cleared at update
						
						GetPort(curPort);
						SetPortWindowPort(h^^.whWind);
						h^^.whUpdate(h^^.whSized);
						h^^.whSized := false; // Reset - we must have a new resize event to pass true again
						
						SetPort(curPort);
						wasHandled := true;
					end;
			end;
			
			kEventWindowClickContentRgn:
			begin
				if h^^.whMouse <> nil then // If we have a callback installed for the window
				begin
					err := GetEventParameter (theEvent, kEventParamMouseLocation,
						typeQDPoint, nil, SizeOf(Point), nil, @where);
					
					GetPort(curPort);
					SetPortWindowPort(theWind);
					GlobalToLocal(where);
					
					h^^.whMouse(where, Trunc(theTime*60), theMods); // Hur skall theTime skalas? Eller skall den skickas som double?
					wasHandled := true;
					SetPort(curPort);
		// Possible improvement: new-style point option?
				end;
			end;
			
			otherwise
				WriteLn('unknown window event ', eventKind);
				// Call handler for other events here?
			
			end; {case}
		end
		else
			WriteLn('unknown event class: ', IToString(eventClass));
		
		if not wasHandled then
			CallNextEventHandler(nextHandler, theEvent);
		WindowEventHandler := noErr;
	end; {WindowEventHandler}
	
	{Classic-style menu handler}
	function DoMenuCommand (menu, item: Longint): Boolean;
	var
		mh: MHandlerHnd;
		p: TSIntProcPtr;
	begin
		DoMenuCommand := false; // false indicates no success
		
		mh := mhList;
		while (mh <> nil) do
			begin
				p := mh^^.mhSelect;
				if ((menu = mh^^.mhID) and (p <> nil)) then
					begin
						p(item);
						mh := nil;
						return true; // Found something
					end
				else
					mh := mh^^.mhNext;
			end;
//		HiliteMenu(0);
	end;

// Temporary - For debugging commands
function LongToString(l: Longint): Str255;
type
	Chars = packed record
	a,b,c,d: Char;
	end;
var
	s: Str255;
begin
	s := '    ';
	s[1] := Chars(l).d;
	s[2] := Chars(l).c;
	s[3] := Chars(l).b;
	s[4] := Chars(l).a;
	return s;
end;
	
	{Menu command and other command events}
	function CommandEventHandler (nextHandler: EventHandlerCallRef;
							theEvent: EventRef;
							userData: Pointer): OSStatus;MWPascal;
	var
		aCommand: HICommand;
		status: OSStatus;
		wasHandled: Boolean;
		menuItem, menuID: Integer;
	begin
		wasHandled := false;
		status := eventNotHandledErr;
		GetEventParameter(theEvent, kEventParamDirectObject, typeHICommand, nil, sizeof(HICommand), nil, @aCommand);

		if (kHICommandFromMenu and aCommand.attributes) <> 0 then
		begin
			menuItem := aCommand.menuItemIndex;
			menuID := GetMenuID(aCommand.menuRef);
			// Look for this in menu handler list.
			wasHandled := DoMenuCommand (menuID, menuItem);
		end;

		if not wasHandled then
		if aCommand.commandID = kHICommandAbout then
		if gAboutProc <> nil then
		begin
			gAboutProc(1);
			wasHandled := true;
		end;
		
		if not wasHandled then
		if gCarbonMenuCommandProc <> nil then
		begin
			{Global callback for all HICommands}
			wasHandled := gCarbonMenuCommandProc(aCommand.commandID);
		end;
		
		if wasHandled then
			status := noErr;
		CommandEventHandler := status;
	end;

	procedure SkelSetCommandProc(theCommandProc: TSLongProcPtr);
	begin
		gCarbonMenuCommandProc := theCommandProc;
	end;

	procedure InstallApplicationCarbonEventHandlers;
	var
		typeList: array [0..2] of EventTypeSpec =
		(
			(eventClass: kEventClassCommand; eventKind: kEventCommandProcess),
			(eventClass: kEventClassMenu; eventKind: kEventMenuTargetItem),
			(eventClass: kEventClassMenu; eventKind: kEventMenuMatchKey) // ???
		);
		err: OSErr;
	begin
		err := InstallEventHandler(GetApplicationEventTarget(), CommandEventHandler, Length(typeList), @typeList[0], nil, nil);
		if  (err <> noErr) Then Halt(err); // Return status;
	end;


// Global ref to main.nib (if any)
	var
		gNibRef: IBNibRef;
		gGotAutoMenu: Boolean;
	
	// SkelInit loads main.nib if it exists, and a menubar called MenuBar.
	// If this is not desired, simply use other names.
	procedure SkelInit;
	var
		err: OSErr;
	begin
		InstallApplicationCarbonEventHandlers;
		SkelAppleEvent(nil, nil, nil, nil); // Onödigt
		InitTSAE;
		
		// By default, try to open main.nib and load MenuBar.
		gNibRef := nil;
		err := CreateNibReference(CFSTR('main'), gNibRef);
		gGotAutoMenu := false;
		if err = noErr then
		begin
			err := SetMenuBarFromNib(gNibRef, CFSTR('MenuBar'));
			gGotAutoMenu := err = noErr;
		end
		else
			WriteLn('No main nib');
	end;
	
	// Get the reference to main.nib, for loading nib resources.
	function SkelGetMainNib: IBNibRef;
	begin
		return gNibRef;
	end;
	
	procedure SkelMain;
	begin
		RunApplicationEventLoop;
	end;
	
	procedure SkelWhoa;
	begin
		QuitApplicationEventLoop;
	end;

{	Clobber all the menu, window and dialog handlers}
	procedure SkelClobber;
	begin
		oldWDHandler := nil;
		oldWindow := nil;
		while (whList <> nil) do
			begin
				SkelRmveWind(whList^^.whWind);
			end;
		while (mhList <> nil) do
			begin
				SkelRmveMenu(GetMenuHandle(mhList^^.mhID));
			end;
	end;

{-- Menu management --}
{SkelMenu and its cousins should usually not be needed.}
{If you read your menus from nib, and managed with commands,}
{all you need is a command handler callback (SkelSetCommandProc).}
{However, for old code, code structured by menu or dynamic menus,}
{this is still practical! So use these whenever suitable.}

	function CommonSkelMenu (theMenu: MenuHandle; pSelect: TSIntProcPtr; pClobber: TSMenuProcPtr): Boolean;
		var
			mh: MHandlerHnd;
			myHand: Handle;
	begin
		mhClobOnRmve := false;
		SkelRmveMenu(theMenu);
		mhClobOnRmve := true;
		myHand := NewHandle(Sizeof(MHandler));
		CommonSkelMenu := false;
		if myHand <> nil then
			begin
				CommonSkelMenu := true;					{ show we really got the memory }
				mh := MHandlerHnd(myHand);
				mh^^.mhNext := mhList;
				mhList := MHandlerHnd(myHand);
				mh^^.mhID := GetMenuID(theMenu);	{ get menu id number }
				mh^^.mhSelect := pSelect;			{ install selection handler }
				mh^^.mhClobber := pClobber;		{ install disposal handler }
			end;
	end;

	function SkelMenu (theMenu: MenuHandle; pSelect: TSIntProcPtr; pClobber: TSMenuProcPtr; DrawBar: Boolean): Boolean;
		var
			success: Boolean;
	begin
		success := CommonSkelMenu(theMenu, pSelect, pClobber);
		SkelMenu := success;
		if success then
			begin
				InsertMenu(theMenu, 0);			{ put menu at end of menu bar }
				if DrawBar then
					DrawMenuBar;
			end;
	end;

	function SkelHMenu (theMenu: MenuHandle; pSelect: TSIntProcPtr; pClobber: TSMenuProcPtr): Boolean; {Added by Ingemar 22/8 -93}
		var
			success: Boolean;
	begin
		success := CommonSkelMenu(theMenu, pSelect, pClobber);
		SkelHMenu := success;
		if success then
			begin
				InsertMenu(theMenu, -1);			{ put menu at end of menu bar }
			end;
	end;

	procedure SkelRmveMenu (theMenu: MenuHandle);
		var
			mID: integer;
			h, h2: MHandlerHnd;
			p: TSMenuProcPtr;
			returnflag: Boolean;
	begin
		mID := GetMenuID(theMenu);
		returnflag := false;
		if mhlist <> nil then
			begin
				if mhList^^.mhID = mID then
					begin
						h2 := mhlist;
						mhList := h2^^.mhNext;
					end
				else
					begin
						h := mhList;
						while (h <> nil) and not returnflag do
							begin
								h2 := h^^.mhNext;
								if (h2 = nil) then
									begin
										h := nil;
										returnflag := true;
									end
								else if h2^^.mhID = mID then
									begin
										h^^.mhNext := h2^^.mhNext;
										h := nil;
									end;
								if h <> nil then
									h := h2;
							end;
					end;
				if not returnflag then
					begin
						DeleteMenu(mID);
						DrawMenuBar;
						p := h2^^.mhClobber;
						if mhClobOnRmve and (p <> nil) then
							p(theMenu);
						DisposeHandle(Handle(h2));
					end;
			end;
	end;

	procedure DoAppleItem (item: integer);
	begin
		if doAbout then
			if gAboutProc <> nil then
				gAboutProc(item);
	end;

	procedure DoAppleClobber(ignoredMenu: MenuHandle); // ignoredMenu = appleMenu
	begin
		DisposeMenu(appleMenu);
	end;

	procedure SkelApple (aboutTitle: Str255; aboutProc: TSIntProcPtr);
		var
			appleTitle: Str255;
	begin
		gAboutProc := aboutProc;
		
		// If gNibRef is nil, we can assume that no menu bar has been loaded.
		// If there is a main nib, the program is responsible for creating an Apple menu.
		if not gGotAutoMenu {gNibRef = nil} then
		begin
			WriteLn('No auto-menu, making apple menu');
			
			appleTitle := ' ';
			appleTitle[1] := char($14);
			appleID := 1;
			appleMenu := NewMenu(appleID, appleTitle);
			if aboutTitle <> '' then
				begin
					doAbout := true;
					AppendMenu(appleMenu, aboutTitle);
					AppendMenu(appleMenu, '(-');
				end;
			AppendResMenu(appleMenu, 'DRVR');
			SkelMenu(appleMenu, DoAppleItem, DoAppleClobber, false);
		end;
	end;
	
	procedure DoWindowTimer (theTimer: EventLoopTimerRef; inAction: EventLoopIdleTimerMessage; userData: Pointer); MWPascal;
	var
		hHand: WhandlerHnd;
		savePort: CGrafPtr;
	begin
		{We currently have no mechanism for reporting end of idle time}
		if inAction = kEventLoopIdleTimerStopped then Exit(DoWindowTimer);
		
		hHand := WhandlerHnd(userData);
		with hHand^^ do
		begin
			if whIdle = nil then
				Exit(DoWindowTimer);
			if whFrontOnly then
				if FrontWindow <> whWind then
					Exit(DoWindowTimer);
			GetPort(savePort);
			SetPortWindowPort(whWind);
			whIdle;
			SetPort(savePort);
		end;
	end;

type
// Dynamic array for event list built with AddEvent
	EventTypeSpecList = array of EventTypeSpec;

// Fixed-size for custom event list
	EventTypeSpecListArr = array [0..100] of EventTypeSpec;
	EventTypeSpecListPtr = ^EventTypeSpecListArr;
	
	{Utility function for building a list of events}
	{Assumes that the list is big enough to never overflow!}
	procedure AddEvent(eventClass, eventKind: UInt32; var eventList: EventTypeSpecList);
	begin
		SetLength(eventList, Length(eventList)+1);
		eventList[Length(eventList)-1].eventClass := eventClass;
		eventList[Length(eventList)-1].eventKind := eventKind;
	end;

{Window management}
{New version of SkelWindow, with duration of the timer as well as custom event type list}
	function SkelCustomWindow (theWind: WindowPtr; pMouse: TSMouseProcPtr;
						pKey: TSKeyProcPtr; pUpdate: TSBooleanProcPtr;
						pActivate: TSBooleanProcPtr; pClose, pClobber: TSNoArgProcPtr;
						pIdle: TSNoArgProcPtr; frontOnly: Boolean;
						idleTime: EventTime; customEventProc: TSCarbonEventProcPtr;
						customEventTypes: EventTypeSpecPtr; customEventCount: Longint): Boolean;
// Adds several arguments more over SkelWindow
		var
			hHand: WhandlerHnd;
			eventList: EventTypeSpecList;
			err: OSErr;
			i: Integer;
			e: EventTypeSpecListPtr;
	begin
		whClobOnRmve := false;
		SkelRmveWind(theWind);
		whClobOnRmve := true;

{	Get new handler, attach to list of handlers.  It is attached to the beginning of the list, which is simpler;}
{	the order should be irrelevant to the hose, anyway. }

		hHand := WHandlerHnd(NewHandle(Sizeof(WHandler)));
		SkelCustomWindow := false;
		if hHand <> nil then
			begin
				hHand^^.whNext := whList;
				whList := hHand;
				with hHand^^ do
					begin
						SkelCustomWindow := true;		{ Show that we got the memory }
						whWind := theWind;
						whMouse := pMouse;
						whKey := pKey;
						whUpdate := pUpdate;
						whActivate := pActivate;
						whClose := pClose;
						whClobber := pClobber;
						whIdle := pIdle;
						whFrontOnly := frontOnly;
						whSized := false;
						whGrow := GrowRect;
						whIdleTimer := nil;
						whCustomEvent := customEventProc;
					end;
				InstallStandardEventHandler(GetWindowEventTarget(theWind));
				
// Install timer
				if pIdle <> nil then
				begin
					InstallEventLoopIdleTimer (GetMainEventLoop(), idleTime, idleTime,
						DoWindowTimer, Pointer(hHand), hHand^^.whIdleTimer);
				end;
				
// Inspect all callbacks and install corresponding events
				SetLength(eventList, 0);
				
				// Always add this? Or only when we have an update callback?
				if pUpdate <> nil then
					AddEvent(kEventClassWindow, kEventWindowBoundsChanged, eventList);
				
				if pMouse <> nil then
					AddEvent(kEventClassWindow, kEventWindowClickContentRgn, eventList);
				if pKey <> nil then
					AddEvent(kEventClassKeyboard, kEventRawKeyDown, eventList);
				if pKey <> nil then
					AddEvent(kEventClassKeyboard, kEventRawKeyRepeat, eventList);
				if pUpdate <> nil then
				begin
					//AddEvent(kEventClassWindow, kEventWindowUpdate, eventList);
					AddEvent(kEventClassWindow, kEventWindowDrawContent, eventList);
				end;
				if pActivate <> nil then
					AddEvent(kEventClassWindow, kEventWindowActivated, eventList);
				if pActivate <> nil then
					AddEvent(kEventClassWindow, kEventWindowDeactivated, eventList);
//				if pClose <> nil then
// Change by Ingemar 080603: Always install kEventWindowClose, to avoid that the OS disposes the window.
// TransSkel will only close it, so the WindowPtr stays valid. This simplifies thing like About boxes.
					AddEvent(kEventClassWindow, kEventWindowClose, eventList);
	
	// These are the events for the standard procs.
	// It is also possible to pass an array of other events that should
	// be processed by a custom procedure, so any event can be handled.
	// The custom event proc is called before TransSkel's standard processing.
					
	// Add custom list and custom handler, if they exist!
				if customEventProc <> nil then
					if customEventTypes <> nil then
						if customEventCount > 0 then
						begin
							e := EventTypeSpecListPtr(customEventTypes);
							for i := 0 to customEventCount-1 do
							begin
								AddEvent(e^[i].eventClass, e^[i].eventKind, eventList);
							end;
						end;
				
				err := InstallEventHandler(GetWindowEventTarget(theWind), WindowEventHandler, Length(eventList), @eventList[0], nil, nil);
				if err <> noErr then
					WriteLn('InstallEventHandler err ', err);
			end;
		SetPortWindowPort(theWind); {Is this allowed for hidden windows? I thought so, but… /Ingemar, dec 93}
		
		return true;
	end;

	function SkelWindow (theWind: WindowPtr; pMouse: TSMouseProcPtr; pKey: TSKeyProcPtr; pUpdate: TSBooleanProcPtr;
			pActivate: TSBooleanProcPtr; pClose, pClobber: TSNoArgProcPtr; pIdle: TSNoArgProcPtr; frontOnly: Boolean): Boolean;
	begin
		return SkelCustomWindow(theWind, pMouse, pKey, pUpdate, pActivate, pClose, pClobber,
							pIdle, frontOnly, kEventDurationSecond, nil, nil, 0);
	end;

	procedure SkelRmveWind;
	var
		h, h2: WHandlerHnd;
		returnflag: Boolean;
	begin
		if theWind = oldWindow then
			begin
				oldWindow := nil;
{•    oldWDHandler := nil;•}
			end;

		if (whList <> nil) then
			begin
				returnflag := false;
				if whList^^.whWind = theWind then
					begin
						h2 := whlist;
						whList := whList^^.whNext;
					end
				else
					begin
						h := whList;
						while (h <> nil) and not returnflag do
							begin
								h2 := h^^.whNext;
								if (h2 = nil) then
									begin
										h := nil;
										returnflag := true;
									end
								else if h2^^.whWind = theWind then
									begin
										h^^.whNext := h2^^.whNext;
										h := nil;
									end;
								if h <> nil then
									h := h2;
							end;
					end;
				if not returnflag then
					begin
						if (whClobOnRmve) then
							DoClobber(h2);
						DisposeHandle(Handle(h2));
					end;
			end;
	end;

	function DoDialog (theEvent: EventRecord): Boolean;
		var
			dh: WHandlerHnd;
			theDialog: DialogPtr;
			what: integer;
			item: integer;
			tmpPort: GrafPtr;
			hasfilter, filtered: Boolean;
	begin
		what := theEvent.what;
{Filter procedure, Added by Ingemar 18/9 -93:}
		if (what = updateEvt) or (what = activateEvt) then
			theDialog := GetDialogFromWindow(WindowPtr(theEvent.message)) { Big bad bug fixed 030712 / Ingemar}
		else
			theDialog := GetDialogFromWindow(FrontWindow);

		dh := WHandlerHnd(GetDHandler(GetDialogWindow(theDialog)));
		filtered := false;
		hasfilter := dh <> nil;
		if hasfilter then
			hasfilter := dh^^.whFilter <> nil;
		if hasfilter then
			dh^^.whFilter(theDialog, theEvent, filtered);
		DoDialog := filtered;
		if not filtered then
{end of filter proc handling}
		begin
			if (what = updateEvt) then // Skall inte behövas!!!
			begin
				SetPortDialogPort(theDialog);
				DrawDialog(theDialog);
			end;

				if IsDialogEvent(theEvent) then
					begin
						if DialogSelect(theEvent, theDialog, item) then
									begin
										dh := WHandlerHnd(GetDHandler(GetDialogWindow(theDialog)));
										if (dh <> nil) then
											if (dh^^.whEvent <> nil) then
												begin
													GetPort(tmpPort);
													SetPortDialogPort(theDialog);
													dh^^.whEvent(item, theEvent);
													SetPort(tmpPort);
												end;
									end;
						DoDialog := true;
					end
				else
					DoDialog := false;
		end;
	end;

	procedure ConvertWindowClickContentToEventRecord(theEvent: EventRef; var myEvent: EventRecord);
	var
		err: OSErr;
		myWindow: WindowRef;
		myModifiers: Longint;
	begin
		err := GetEventParameter(theEvent, kEventParamDirectObject, typeWindowRef, nil, 
				SizeOf(WindowRef), nil, @myWindow);
		if err = noErr then
		begin
			GetEventParameter(theEvent, 
				kEventParamKeyModifiers, typeUInt32, nil,
				SizeOf(myModifiers), nil, @myModifiers);
			GetEventParameter(theEvent, 
				kEventParamMouseLocation, typeQDPoint, nil,
				SizeOf(Point), nil, @myEvent.where);

			myEvent.what := mouseDown;
			myEvent.message := Longint(myWindow);
			myEvent.modifiers := myModifiers;
			myEvent.when := Trunc(GetCurrentEventTime * 60); {EventTimeToTicks}
		end
		else
			myEvent.what := nullEvent;
	end;
	
	procedure ConvertDrawContentToEventRecord(theEvent: EventRef; var myEvent: EventRecord);
	var
		err: OSErr;
		myWindow: WindowRef;
		myModifiers: Longint;
	begin
		err := GetEventParameter(theEvent, kEventParamDirectObject, typeWindowRef, nil, 
				SizeOf(WindowRef), nil, @myWindow);
		if err = noErr then
		begin
			GetEventParameter(theEvent, 
				kEventParamKeyModifiers, typeUInt32, nil,
				SizeOf(myModifiers), nil, @myModifiers);
			
			myEvent.what := updateEvt;
			myEvent.message := Longint(myWindow);
			myEvent.modifiers := myModifiers;
			myEvent.when := Trunc(GetCurrentEventTime * 60); {EventTimeToTicks}
		end
		else
			myEvent.what := nullEvent;
	end;
	
	
	// Internal handler of Carbon Events in classic dialogs
	function DialogEventHandler (nextHandler: EventHandlerCallRef; inEvent: EventRef;
							userData: Pointer): OSStatus; MWPascal;
	var
		eventClass: UInt32;
		eventKind: UInt32;
		classicEvent: EventRecord;
		success: Boolean;
	begin
		eventClass := GetEventClass(inEvent);
		eventKind  := GetEventKind(inEvent);
		
//		WriteLn('Dialog event incoming ', IToString(eventClass), ' ', eventKind);
		
		if	((eventClass = kEventClassWindow) and (eventKind = kEventWindowClickContentRgn)) then
		begin
			ConvertWindowClickContentToEventRecord(inEvent, classicEvent);
			DoDialog(classicEvent);
			return noErr;
		end;
		
		if	((eventClass = kEventClassWindow) and (eventKind = kEventWindowDrawContent)) then
		begin
			ConvertDrawContentToEventRecord(inEvent, classicEvent);
			DoDialog(classicEvent);
			return noErr;
		end;
		
		if	((eventClass = kEventClassWindow) and (eventKind = kEventWindowClickContentRgn)) or
			((eventClass = kEventClassKeyboard) and (eventKind = kEventRawKeyDown)) or
			((eventClass = kEventClassKeyboard) and (eventKind = kEventRawKeyRepeat)) or
//			((eventClass = kEventClassWindow) and (eventKind = kEventWindowDrawContent)) or
			((eventClass = kEventClassWindow) and (eventKind = kEventWindowUpdate))
// Why did I use kEventWindowUpdate instead of kEventWindowDrawContent? I don't remember,
// But maybe it doesn't matter. The old-style dialog routines are not vital, pure backwards
// compatibility, so it will not make a big difference.
		then
			begin
				success := ConvertEventRefToEventRecord(inEvent, classicEvent);
				if success then
					DoDialog(classicEvent)
				else
					return eventNotHandledErr;
				return noErr;
			end;
		return eventNotHandledErr;
	end; {DialogEventHandler}

{Dialog management - old style!}
{SkelDialog is for legacy code only. Use HIView (e.g. with ViewManager) for new dialogs.}
	function SkelDialog (theDialog: DialogPtr; pEvent: TSEventProcPtr; pClose, pClobber: TSNoArgProcPtr; pFilter: TSFilterProcPtr): Boolean; {pFilter added by Ingemar 18/9-93}
	var
		wh: WHandlerHnd;
		aBool: Boolean;
	const
		customEventTypes: array [0..4] of EventTypeSpec =
		(
			( eventClass: kEventClassWindow; eventKind: kEventWindowClickContentRgn ),		//	Sent before the tab control switches its control value
			( eventClass: kEventClassKeyboard; eventKind: kEventRawKeyDown ),
			( eventClass: kEventClassKeyboard; eventKind: kEventRawKeyRepeat ),
			( eventClass: kEventClassWindow; eventKind: kEventWindowUpdate ),
			( eventClass: kEventClassWindow; eventKind: kEventWindowDrawContent )
		);
	begin
		aBool := SkelCustomWindow (GetDialogWindow(theDialog), nil, nil, nil,
						nil, pClose, pClobber,
						nil, false, 0, @DialogEventHandler,
						@customEventTypes[0], Length(customEventTypes));
		if aBool then
			WriteLn('Dialog installed');
		
		if aBool <> false then
			begin
				wh := GetWDHandler(GetDialogWindow(theDialog));
				wh^^.whEvent := pEvent;
{Added by Ingemar 18/9 -93:}
				wh^^.whFilter := pFilter; {Install a filter function to be called *before* IsDialogEvent!}
				
				if wh^^.whCustomEvent = nil then
					WriteLn('NIL!!!');
			end;
		SkelDialog := aBool;
	end;

	procedure SkelRmveDlog (theDialog: DialogPtr);
	begin
		SkelRmveWind(GetDialogWindow(theDialog));
	end;

{OBSOLETE?}
	procedure SkelGrowBounds (theWind: WindowPtr; hLO, vLo, hHi, vHi: integer);
	begin
	end;

{OBSOLETE?}
	procedure SkelEventMask (mask: integer);
	begin
	end;

{OBSOLETE?}
	procedure SkelGetEventMask (var mask: integer);
	begin
	end;

	procedure DoBackgroundTimer(theTimer: EventLoopTimerRef; userData: Pointer); MWPascal;
	begin
		if pBkgnd <> nil then
			pBkgnd;
	end;

	procedure SkelBackgroundWithDuration (p: TSNoArgProcPtr; intervalTime: EventTime);
	begin
		// Uninstall any previous timer
		if pBkgnd <> nil then
		if gBackgroundTimer <> nil then
		begin
			RemoveEventLoopTimer(gBackgroundTimer);
			gBackgroundTimer := nil;
		end;

		pBkgnd := p;
		if p <> nil then
		begin
			// Install timer!
			InstallEventLoopTimer (GetMainEventLoop(), intervalTime, intervalTime,
				DoBackgroundTimer, nil, gBackgroundTimer);
		end;
	end;

// The default gives a background process at 1 Hz.
	procedure SkelBackground (p: TSNoArgProcPtr);
	begin
		SkelBackgroundWithDuration(p, kEventDurationSecond);
	end;

	procedure SkelGetBackground (var p: TSNoArgProcPtr);
	begin
		p := pBkgnd;
	end;

{Hook for other events - needs activator and must be processed differently! Unnecessary?}
	procedure SkelEventHook (p: TSOtherEventProcPtr);
	begin
	end;

	procedure SkelGetEventHook (var p: TSOtherEventProcPtr);
	begin
	end;

	procedure SkelDlogMask (mask: integer);
	begin
	end;

	procedure SkelGetDlogMask (var mask: integer);
	begin
	end;

{Two new procedures for WNE-support, added by Ingemar 12/11-93}
	procedure SkelSetSleep (newSleep: Longint);
	begin
	end;

	procedure SkelSetMouseRgn (newMouseRgn: RgnHandle);
	begin
	end;

{Utility function, added by Ingemar 13/7-94}
	function FindWindowByRefcon (theRefCon: Longint): WindowPtr;
	begin
		return nil;
	end;

{Suspend/resume, added by Ingemar 23/7 -94}
	procedure SkelSetSuspendResume (p: TSBooleanProcPtr);
	begin
	end;

	function SkelGetSuspendResume: TSBooleanProcPtr;
	begin
		return nil;
	end;

{Mouse moved events, added by Ingemar jan -95:}
{OBSOLETE?}
	procedure SkelSetMouseMoved (p: TSNoArgProcPtr);
	begin
	end;

	function SkelGetMouseMoved: TSNoArgProcPtr;
	begin
		return nil;
	end;
	
	function SkelGetVersion: AnsiString;
	begin
		return kSkelVersion;
	end;

end.
