unit Breeding_Window;

{File name: Breeding_Window.Pas}
{Function: Handle a Window}
{History: 12/15/90 Original by Prototyper.   }

interface
	uses
		{$ifc not undefined THINK_PASCAL}
		ThinkPascalUPIBridge,
		{$elsec}
		MacOSAll, 
		{$endc}
		MyGlobals, Ted, Genome_Window, Drawing;

	{$ifc not undefined THINK_PASCAL}
	{Close our window}
	procedure Close_Breeding_Window (whichWindow: WindowPtr);
	{Update our window, someone uncovered a part of us}
	procedure Update_Breeding_Window (whichWindow: WindowPtr);
	{$endc}

	{Open our window and draw everything}
	procedure Open_Breeding_Window;

	{Handle action to our window, like controls}
	procedure Do_Breeding_Window (myEvent: EventRecord);

implementation



	procedure DrawContent;
	var
		tmpBitMap: BitMap;
		tmpRect: Rect;
	begin
		if resizing then
			begin
				{$ifc not undefined THINK_PASCAL}
				ClipRect(screenbits.bounds);
				EraseRect(BreedingWindow^.portrect);
				{$elsec}
				GetQDGlobalsScreenBits(tmpBitMap);
				ClipRect(tmpBitMap.bounds);
				GetPortBounds(GetWindowPort(BreedingWindow), tmpRect);
				EraseRect(tmpRect);
				{$endc}
			end;
		UpDateAnimals;
	end;

	{$ifc not undefined THINK_Pascal}
	{Close our window}
	procedure Close_Breeding_Window;

	begin   								{Start of Window close routine}
		if (BreedingWindow <> nil) and ((BreedingWindow = whichWindow) or (ord4(whichWindow) = -1)) then{See if we should close this window}
			begin
				DisposeWindow(BreedingWindow);{Clear window and controls}
				BreedingWindow := nil;    		{Make sure other routines know we are open}
			end;    						{End for if (BreedingWindow<>nil)}
	end;    								{End of procedure}

	{Update our window, someone uncovered a part of us}
	procedure UpDate_Breeding_Window;
		var
			savePort: GrafPtr;{Place to save the last port}
			
	begin   						{Start of Window update routine}
		if (BreedingWindow <> nil) and (BreedingWindow = whichWindow) then{Handle an open when already opened}
			begin
				GetPort(savePort);{Save the current port}
				SetPort(BreedingWindow);{Set the port to my window}
				DrawContent;
				SelectWindow(BreedingWindow);
				DrawControls(BreedingWindow);{Draw all the controls}
				DrawGrowIcon(BreedingWindow);{Draw the Grow box}
				SetPort(savePort);{Restore the old port}
			end;    				{End for if (BreedingWindow<>nil)}
	end;    						{End of procedure}
{$elsec}

function HandleWindowBreedingCloseEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var
		status: OSStatus;
	begin
		status := noErr;
		BreedingWindow := nil;
		status := eventNotHandledErr;
		HandleWindowBreedingCloseEvent := status;
	end;

function HandleWindowBreedingDrawContentEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
var
	status: OSStatus;
begin
	status := noErr;
	DrawContent;
	InvalGenomeWindow;
	HandleWindowBreedingDrawContentEvent := status;
end;

function HandleWindowBreedingHandleContentClickEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
var
	status: OSStatus;
	myPt: Point;
	tmpRect: Rect;
begin
	status := noErr;
	{$ifc not undefined THINK_PASCAL}
	myPt := myEvent.where;{Get mouse position}
	tmpRect := BreedingWindow^.portBits.bounds;
	{$elsec}
	{FIXME should really get this from the EventRef. kEventParamDirectObject kEventMouseDown kEventParamMouseLocation typeHIPoint}
	GetMouse(myPt);
	GetPortBounds(GetWindowPort(BreedingWindow), tmpRect);
	{$endc}
	with tmpRect do{Make it relative}
		begin
			myPt.h := myPt.h + left;
			myPt.v := myPt.v + top;
		end;
	evolve(myPt);
	InvalGenomeWindow;
	HandleWindowBreedingHandleContentClickEvent := status;
end;
	
function HandleWindowBreedingBoundsChangedEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
var
	status: OSStatus;
	tempRect: Rect;
	attributes: UInt32;
begin
	status := GetEventParameter(theEvent, kEventParamAttributes, typeUInt32, nil, sizeof(UInt32), nil, @attributes);
	if BitAnd(attributes, kWindowBoundsChangeSizeChanged) <> 0 then
	begin
		Resizing := true;
		status := InvalWindowRect(BreedingWindow, GetWindowPortBounds(BreedingWindow, tempRect)^);
		HandleWindowBreedingBoundsChangedEvent := status;
	end;
end;
	
	{$endc}

	{Open our window and draw everything}
	procedure Open_Breeding_Window;
	var theNib: IBNibRef;
		status: OSStatus;
	begin   						{Start of Window open routine}

		if (BreedingWindow = nil) then
			begin
			
				{$ifc not undefined THINK_PASCAL}
				BreedingWindow := GetNewWindow(2, nil, Pointer(-1));{Get the window from the resource file}
				SetPort(BreedingWindow);{Set the port to my window}
				{$elsec}
				status := CreateNibReference(CFSTR('Brand_New'), theNib);
				if status <> NoErr then
					exit;
				CreateWindowFromNib(theNib, CFSTR('Breeding Window'), BreedingWindow);
				DisposeNibReference(theNib);
				{InstallStandardEventHandler(GetWindowEventTarget(BreedingWindow));}
				InstallEventHandler(GetWindowEventTarget(BreedingWindow), HandleWindowBreedingDrawContentEvent, 1, @windowEventDrawContentType, nil, nil);
				InstallEventHandler(GetWindowEventTarget(BreedingWindow), HandleWindowBreedingCloseEvent, 1, @windowEventCloseType, nil, nil);
				InstallEventHandler(GetWindowEventTarget(BreedingWindow), HandleWindowBreedingBoundsChangedEvent, 1, @windowEventBoundsChangedType, nil, nil);
				InstallEventHandler(GetWindowEventTarget(BreedingWindow), HandleWindowBreedingHandleContentClickEvent, 1, @windowEventHandleContentClickType, nil, nil)
				
				;SetPort(GetWindowPort(BreedingWindow))
				{$endc}

			end; 					{End for if (BreedingWindow<>nil)}
		ShowWindow(BreedingWindow);{Show the window now}
		SelectWindow(BreedingWindow);{Bring our window to the front}
	end;    						{End of procedure}

		{=================================}

			{Handle action to our window, like controls}
	procedure Do_Breeding_Window;
		var
			code: integer;   		{Location of event in window or controls}
			whichWindow: WindowPtr;{Window pointer where event happened}
			myPt: Point; 			{Point where event happened}
			theControl: ControlHandle;{Handle for a control}
			tmpRect: Rect;

	begin   						{Start of Window handler}
		if (BreedingWindow <> nil) then{Handle only when the window is valid}
			begin
				code := FindWindow(myEvent.where, whichWindow);{Get where in window and which window}

				if (myEvent.what = MouseDown) and (BreedingWindow = whichWindow) then
					begin
						myPt := myEvent.where;{Get mouse position}
						{$ifc not undefined THINK_PASCAL}
						tmpRect := BreedingWindow^.portBits.bounds;
						{$elsec}
						GetPortBounds(GetWindowPort(BreedingWindow), tmpRect);
						{$endc}
						with tmpRect do{Make it relative}
							begin
								myPt.h := myPt.h + left;
								myPt.v := myPt.v + top;
							end;
						evolve(myPt)
					end;

				if (BreedingWindow = whichWindow) and (code = inContent) then{for our window}
					begin

						code := FindControl(myPt, whichWindow, theControl);{Get type of control}

						if (code <> 0) then{Check type of control}
							code := TrackControl(theControl, myPt, nil);{Track the control}

					end;    			{End for if (BreedingWindow=whichWindow)}
			end;    				{End for if (BreedingWindow<>nil)}
	end;    						{End of procedure}

		{=================================}


end.    						{End of unit}