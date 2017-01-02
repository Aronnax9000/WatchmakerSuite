unit  Genome_Window;

{File name: Genome_Window.Pas}
{Function: Handle a Window}
{History: 12/12/90 Original by Prototyper.   }

interface


	{$ifc not undefined THINK_PASCAL}
	uses MyGlobals;
	{Close our window}
	procedure Close_Genome_Window(whichWindow:WindowPtr);
	{Update our window, someone uncovered a part of us}
	procedure Update_Genome_Window(whichWindow:WindowPtr);

	{$elsec}
	uses MacOSAll, Richard, MyGlobals;
	{$endc}
	


	{Open our window and draw everything}
	procedure Open_Genome_Window;

	{Handle action to our window, like controls}
	procedure Do_Genome_Window(myEvent:EventRecord);
	procedure InvalGenomeWindow;
	

implementation

	procedure InvalGenomeWindow;
	var
		tmpRectPtr: RectPtr;
		tmpRect: Rect;
	begin
		if GenomeWindow <> nil then
		begin
			tmpRectPtr := GetWindowPortBounds(GenomeWindow, tmpRect);
			InvalWindowRect(GenomeWindow, tmpRect)
		end;
	end;


 
{$ifc not undefined THINK_Pascal} 
	{Close our window}
	procedure Close_Genome_Window;
 
begin   								{Start of Window close routine}
		if (GenomeWindow <> nil) and ((GenomeWindow = whichWindow) or (ord4(whichWindow) = -1)) then{See if we should close this window}
		begin
			DisposeWindow(GenomeWindow);{Clear window and controls}
			GenomeWindow := nil;    		{Make sure other routines know we are open}
		end;    						{End for if (GenomeWindow<>nil)}
end;    								{End of procedure}
 
	{Update our window, someone uncovered a part of us}
	procedure UpDate_Genome_Window;
var
		savePort : GrafPtr;   		{Place to save the last port}
		sTemp: Str255;
		tempRect: Rect;

begin   								{Start of Window update routine}
		if (GenomeWindow <> nil) and (GenomeWindow = whichWindow) then{Handle an open when already opened}
		begin
			GetPort(savePort);  		{Save the current port}
			SetPort(GenomeWindow);  		{Set the port to my window}
			TextFont(systemFont);   	{Set the font to draw in}
			{Draw a string of text,  }
			PrintMiddle;
			TextFont(applFont); 		{Set the default application font}

			DrawControls(GenomeWindow);{Draw all the controls}
			SetPort(savePort);  		{Restore the old port}
		end;    						{End for if (GenomeWindow<>nil)}
end;    								{End of procedure}
{$endc}

function HandleWindowGenomeDrawContentEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
var
	status: OSStatus;
	tmpRectPtr: RectPtr;
	tmpRect: Rect;

begin
	status := noErr;
	tmpRectPtr := GetWindowPortBounds(GenomeWindow, tmpRect);
	EraseRect(tmpRect);
	PrintMiddle;
	HandleWindowGenomeDrawContentEvent := status;
end;
function HandleWindowGenomeCloseEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var
		status: OSStatus;
	begin
		status := noErr;
		GenomeWindow := nil;
		status := eventNotHandledErr;
		HandleWindowGenomeCloseEvent := status;
	end;
	
	{Open our window and draw everything}
	procedure Open_Genome_Window;
	var status: OSStatus;
	theNib: IBNibRef;

begin   								{Start of Window open routine}
 
 
		if (GenomeWindow = nil) then    	{Handle an open when already opened}
		begin
			
			{$ifc not undefined THINK_PASCAL}
			GenomeWindow:= GetNewWindow(1,nil, Pointer(-1));{Get the window from the resource file}
			SetPort(GenomeWindow);  		{Prepare to write into our window}
			{$elsec}
			status := CreateNibReference(CFSTR('Brand_New'), theNib);
				if status <> NoErr then
					exit;
			CreateWindowFromNib(theNib, CFSTR('Genome Window'), GenomeWindow);
			InstallStandardEventHandler(GetWindowEventTarget(GenomeWindow));
			InstallEventHandler(GetWindowEventTarget(GenomeWindow), HandleWindowGenomeDrawContentEvent, 1, @windowEventDrawContentType, nil, nil);
			InstallEventHandler(GetWindowEventTarget(GenomeWindow), HandleWindowGenomeCloseEvent, 1, @windowEventCloseType, nil, nil);
			{$endc}
			ShowWindow(GenomeWindow);   	{Show the window now}
			SelectWindow(GenomeWindow); {Bring our window to the front}
			
		end {End for if (GenomeWindow <> nil)}
		else
			SelectWindow(GenomeWindow); {Already open, so show it}
		
end; {End of procedure}

{=================================}
 
	{Handle action to our window, like controls}
	procedure Do_Genome_Window;
var
		code:integer;   				{Location of event in window or controls}
		whichWindow:WindowPtr;  		{Window pointer where event happened}
		myPt:Point; 					{Point where event happened}
		theControl:ControlHandle;   	{Handle for a control}
		tmpRect: Rect;
		{$ifc  undefined THINK_PASCAL}
		tmpRectPtr: RectPtr;
		{$endc}


begin   								{Start of Window handler}
		if (GenomeWindow <> nil) then   	{Handle only when the window is valid}
		begin
			code := FindWindow(myEvent.where, whichWindow);{Get where in window and which window}
			
			if (myEvent.what = MouseDown) and (GenomeWindow = whichWindow) then
			begin
				myPt := myEvent.where;{Get mouse position}
						{$ifc not undefined THINK_PASCAL}
						tmpRect := GenomeWindow^.portBits.bounds;
						{$elsec}
						tmpRectPtr := GetPortBounds(GetWindowPort(GenomeWindow), tmpRect);
						{$endc}
						with tmpRect do{Make it relative}
				begin
					myPt.h := myPt.h + left;
					myPt.v := myPt.v + top;
				end;
				 
			 end;
			 
			if (GenomeWindow = whichWindow) and (code = inContent) then{for our window}
			begin
				
				code := FindControl(myPt, whichWindow, theControl);{Get type of control}
				
				if (code <> 0) then{Check type of control}
					code := TrackControl(theControl,myPt, nil);{Track the control}
				
			end;    					{End for if (GenomeWindow=whichWindow)}
		end;    						{End for if (GenomeWindow<>nil)}
end;    								{End of procedure}

{=================================}
 

end.    								{End of unit}

