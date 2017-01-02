unit  About_Arthromorphs;

{File name: About_Arthromorphs.Pas}
{Function: Handle a Window}
{History: 12/12/90 Original by Prototyper (Richard Dawkins and Ted Kaehler).}
{07/02/2015 Xcode + Free Pascal port by Alan Canon.}
interface
{$ifc not undefined THINK_PASCAL}
uses ThinkPascalUPIBridge;
{$elsec}
uses MacOSAll, MyGlobals;
{$endc}

	{$ifc not undefined THINK_PASCAL}
	{Close our window}
	procedure Close_About_Arthromorphs(whichWindow:WindowPtr);
	{Update our window, someone uncovered a part of us}
	procedure Update_About_Arthromorphs(whichWindow:WindowPtr);
	{$ENDC}
	
	{Open our window and draw everything}
	procedure Open_About_Arthromorphs;

	{Handle action to our window, like controls}
	procedure Do_About_Arthromorphs(myEvent:EventRecord);

implementation

var
	{$ifc not undefined THINK_PASCAL}
	AboutWindow : WindowPtr;   		{Window pointer}
	{$elsec}
	AboutWindow : WindowRef;
	{$endc}
	
procedure DrawContent;
	var
		tempRect: Rect;
		sTemp: Str255;
		lineHeight: SInt16;
		metrics: FontInfo;
	begin
		TextFont(systemFont);   	{Set the font to draw in}
		GetFontInfo(metrics);
		lineHeight := metrics.ascent + metrics.descent + metrics.leading;
		{Draw a string of text,  }
		SetRect(tempRect, 16,45,272, 45 + lineHeight);
		sTemp :='By Ted Kaehler and Richard Dawkins';
		TETextBox(Pointer(ord(@sTemp) + 1), length(sTemp), tempRect, teJustLeft);
		sTemp :='OS X Port by Alan Canon';
		tempRect.top := tempRect.top + lineHeight;
		tempRect.bottom := tempRect.bottom + lineHeight;
		TETextBox(Pointer(ord(@sTemp) + 1), length(sTemp), tempRect, teJustLeft);
		TextFont(applFont); 		{Set the default application font}
	end;
 
{$ifc not undefined THINK_PASCAL}
{Update our window, someone uncovered a part of us}
{Carbon does most of this for you, which is why this is obsolete.}
{Only DrawContent remains as the target of the Draw Content event handled by Carbon.}
{Retained for compatibility with Think Pascal 4.0}
procedure UpDate_About_Arthromorphs(whichWindow: WindowPtr);
var
	savePort : GrafPtr;   		{Place to save the last port}
begin   								{Start of Window update routine}
	if (AboutWindow <> nil) and (AboutWindow = whichWindow) then{Handle an open when already opened}
		begin
			GetPort(savePort);  		{Save the current port}
			{$ifc not undefined THINK_PASCAL}
			SetPort(AboutWindow);  		{Set the port to my window}
			{$elsec}
			SetPort(GetWindowPort(AboutWindow));  		{Set the port to my window}
			{$endc}
			DrawContent;			
			DrawControls(AboutWindow);{Draw all the controls}
			SetPort(savePort);  		{Restore the old port}
		end;    						{End for if (AboutWindow<>nil)}
end;    								{End of procedure}

	{Close our window}
	procedure Close_About_Arthromorphs;
 
	begin   								{Start of Window close routine}
		if (AboutWindow <> nil) and ((AboutWindow = whichWindow) or (ord4(whichWindow) = -1)) then{See if we should close this window}
		begin
			DisposeWindow(AboutWindow);{Clear window and controls}
			AboutWindow := nil;    		{Make sure other routines know we are open}
		end;    						{End for if (AboutWindow<>nil)}
	end;    								{End of procedure}

{$elsec}

function HandleWindowAboutCloseEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var
		status: OSStatus;
	begin
		status := noErr;
		AboutWindow := nil;
		status := eventNotHandledErr;
		HandleWindowAboutCloseEvent := status;
	end;

function HandleWindowAboutDrawContentEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
var
	status: OSStatus;
begin
	status := noErr;
	DrawContent;
	HandleWindowAboutDrawContentEvent := status;
end;

{$endc}

{Open our window and draw everything}
procedure Open_About_Arthromorphs;
	var status: OSStatus;
		theNib: IBNibRef;
	begin   								{Start of Window open routine}
		if (AboutWindow = nil) then    	{Handle an open when already opened}
		begin
			{$ifc not undefined THINK_PASCAL}
			AboutWindow:= GetNewWindow(3,nil, Pointer(-1));{Get the window from the resource file}
			SetPort(AboutWindow);  		{Prepare to write into our window}
			{$elsec}
			status := CreateNibReference(CFSTR('Brand_New'), theNib);
			if status <> NoErr then exit;
			CreateWindowFromNib(theNib, CFSTR('About Arthromorphs'), AboutWindow);

			InstallStandardEventHandler(GetWindowEventTarget(AboutWindow));
			InstallEventHandler(GetWindowEventTarget(AboutWindow), HandleWindowAboutDrawContentEvent, 1, @windowEventDrawContentType, nil, nil);
			InstallEventHandler(GetWindowEventTarget(AboutWindow), HandleWindowAboutCloseEvent, 1, @windowEventCloseType, nil, nil);
			SetPort(GetWindowPort(AboutWindow));  		{Prepare to write into our window}
			{$endc}
			ShowWindow(AboutWindow);   	{Show the window now}
			SelectWindow(AboutWindow);{Bring our window to the front}
			
		end 							{End for if (AboutWindow<>nil)}
		else
			SelectWindow(AboutWindow);{Already open, so show it}
	end;    								{End of procedure}

{=================================}
 
	{Handle action to our window, like controls}
	procedure Do_About_Arthromorphs;
var
		code:integer;   				{Location of event in window or controls}
		whichWindow:WindowPtr;  		{Window pointer where event happened}
		myPt:Point; 					{Point where event happened}
		theControl:ControlHandle;   	{Handle for a control}
		boundsRect: Rect;
		{$ifc  undefined THINK_PASCAL}
		boundsRectPtr: RectPtr;
		{$endc}

begin   								{Start of Window handler}
		if (AboutWindow <> nil) then   	{Handle only when the window is valid}
		begin
			code := FindWindow(myEvent.where, whichWindow);{Get where in window and which window}
			
			if (myEvent.what = MouseDown) and (AboutWindow = whichWindow) then{}
			begin   					{}
				myPt := myEvent.where;{Get mouse position}
				{$ifc not undefined THINK_PASCAL}
				boundsRect := AboutWindow^.portBits.bounds; 
				{$elsec}
				boundsRectPtr := GetPortBounds(GetWindowPort(AboutWindow), boundsRect);
				{$endc}
				with boundsRect do{Make it relative}
				begin
					myPt.h := myPt.h + left;
					myPt.v := myPt.v + top;
				end;
				 
			 end;
			 
			if (AboutWindow = whichWindow) and (code = inContent) then{for our window}
			begin
				
				code := FindControl(myPt, whichWindow, theControl);{Get type of control}
				
				if (code <> 0) then{Check type of control}
					code := TrackControl(theControl,myPt, nil);{Track the control}
				
			end;    					{End for if (AboutWindow=whichWindow)}
		end;    						{End for if (AboutWindow<>nil)}
end;    								{End of procedure}

{=================================}
 

end.    								{End of unit}

