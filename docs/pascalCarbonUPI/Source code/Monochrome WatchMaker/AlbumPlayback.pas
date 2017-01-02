unit AlbumPlayback;

interface

uses
	DevelopDefs,
	HIBoxTypes,
	MacOSAll, 
	MenuGreyAdjustDefs, 
	ModeDefs, 
	BoxesDefs, 
	PlaybackGeomUtil, 
	InitializeMenusDef,
	SnapDevelopDef;

procedure ClosePlayBack;
procedure StartPlayBack;

procedure MyAction (theControl: ControlRef; PartCode: ControlPartCode);MWPascal;
function ShowFossil(theControl: ControlRef; PartCode: ControlPartCode): OSStatus;MWPascal;

implementation

uses Globals, SerializationUtil,  Miscellaneous, PersonSerialization, PlaybackWindowDefs;


function ShowFossil(theControl: ControlRef; PartCode: ControlPartCode):OSStatus;MWPascal;
var p: integer;
	bytesToRead: integer;
	perSerializer: PersonSerializer;
	status: OSStatus;
	actualCount: integer;
	tmpPlaybackMidPoint: HIPoint;
	tmpPlaybackRect: HIRect;
begin
(* FIXME
	FossilCounter := GetControlValue(theControl);
	ResetDataFork(Slides);
	p := 0;
	bytesToRead := SizeOf(PersonSerializer);
	{??? It looks as if this reads only the last biomorph -- Graefen or Dawkins}
	{Yes it does: the whole point is to read to the end, updating p, the count of biomorphs. - Alan Canon}
	REPEAT
		status := FSReadFork(slides, fsAtMark, 0, bytesToRead, @perSerializer, @actualCount);
		if status <> eofErr then				
			SetPersonFromPersonSerializer(@theBiomorph, @perSerializer);
		p := p + 1
	UNTIL (status = eofErr) OR (p = NumberinFile - FossilCounter);

	DelayedDrawing := TRUE;
	ZeroMargin := FALSE;
	tmpPlaybackMidPoint := GetPlaybackMidPoint;
	tmpPlaybackRect := PlayBackRect;
	Develop(theBiomorph, tmpPlaybackMidPoint);
	DelayedDrawing := FALSE;
	{SetPortWindowPort(PlaybackWindow);}
	tmpPlaybackRect := PlayBackRect;
	Snapshot(MyPic, tmpPlaybackRect, theBiomorph);
	Child[special] := theBiomorph;
*)
	HiWindowFlush(PlaybackWindow);
	
ShowFossil := noerr;
end;


procedure MyAction (theControl: ControlRef; PartCode: ControlPartCode);MWPascal;
	begin
		CASE PartCode OF
			kControlButtonPart: 
				begin
					{FossilCounter := GetControlValue(theControl)}
					ShowFossil(theControl, 0);
				end;
			OTHERWISE   {SysBeep(1)}
		end; {Cases}
		{FIXME}
		Special^.denizen := @theBiomorph
	end; {MyAction}



procedure StartPlayBack;
	VAR
		bytesRead: LongInt;
		SizeOfPersonSerializer: longint;
		err: OSStatus;
		{tempRect: Rect;}
		actualCount: ByteCount;
		PerSerializer: PersonSerializer;
		tmpPlaybackRect: Rect;

	begin
		SmallMenus;
		{iF FrontWindow = MainWindow THEN}
			{StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);}
		NumberInFile := 0;
		err := ResetDataFork(Slides);
		SizeOfPersonSerializer := SizeOf(PersonSerializer);
		err := GetEOF(slides, bytesRead);
		IF bytesRead >= SizeOfPersonSerializer THEN
			REPEAT		{??? This reading is done twice in the procedure}
				NumberInFile := NumberInFile + 1;
				err := FSReadFork(Slides, fsAtMark, 0, SizeOfPersonSerializer, @perSerializer, @actualCount);
			UNTIL err = eofErr;
		SetPersonFromPersonSerializer(@theBiomorph, @perSerializer);
		err := ResetDataFork(Slides);
		{v1.1  was reset(Slides);}
		NumberInFile := NumberInFile - 1;
		{SetupBoxes(MainWindow); }
		{FIXME we could show genebox on fossil record} 
		InitializePlaybackWindow(NumberInFile - 1);
		SaveMode := theMode;
		SetMode(MainWindow, PlayingBack);
		FossilCounter := 0;
		FirstBiomorph := theBiomorph;
		{FIXME}
		Special^.denizen := @theBiomorph;
		DelayedDrawing := true;
		ZeroMargin := FALSE;
		{FIXME Develop(theBiomorph, GetPlaybackMidPoint);	}
		{These were commented out in the original source, probably because}
		{They were calling Snapshot in the Window update routines. - ABC}
		DelayedDrawing := FALSE;
		tmpPlaybackRect := PlayBackRect;

		{FIXME Snapshot(MyPic, tmpPlaybackRect, theBiomorph); }
		SetControlValue(MyControl, 0);

		HiWindowFlush(PlaybackWindow);
	end; {StartPlayBack}


procedure ClosePlayBack;
{NB this didn't really close the window, it doesn't even hide it, it just moves it behind the main window}
{v1.1 changed it to hide the window instead (caused problems when I hide the main window in bg)}
	begin
		{HideWindow(PlaybackWindow);}
		{QuitAppModalLoopForWindow(PlaybackWindow);}
		DisposeWindow(PlayBackWindow);
		SelectWindow(MainWindow);
		Frontw := MainWindow;
		SetPortWindowPort(MainWindow);
		SetMode(MainWindow, SaveMode);
		FlushEvents(EveryEvent, 0);
		LargeMenus;
		MenuGreyAdjust;
	end; {ClosePlayBack}

end.