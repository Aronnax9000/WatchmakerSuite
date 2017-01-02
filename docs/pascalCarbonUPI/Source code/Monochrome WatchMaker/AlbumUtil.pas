unit AlbumUtil;

{******************************************}
{*						v1.1 Sept 1993				*}
{*		Changed file commands to Mac File manager	*}
{*		Removed Repeat/Until loop in DoClose		*}
{******************************************}

interface
uses
	AlbumDefs,
	BoxesDefs, 
	CursorDefs,
	DevelopDefs,
	DialogGeomDefs, 
	EngineeringDefs,  
	Globals,
	HIBoxDefs,
	HIBoxTypes,
	MacOSAll,  
	MessageDefs,
	ModeDefs, 
	PersonDefs, 
	Serialization;

const EnableCurtainAnimation = false;

procedure DoLoad (WithDialogue: Boolean);

procedure GracefulDeath;
procedure DoClose;
procedure StoreBreedingScreen;
procedure AddToAlbum (Biomorph: PersonPtr; breedModel: HIBreedModelPtr);
procedure Emphasize (MLoc: point);
procedure Zoom;
procedure UnCurtainPage (p: Integer);
procedure TakeCare (Page: Integer);
function AlbumFull: Boolean;
procedure ShowAlbumPage (albumPageToShow: Integer);

function HandleZoomClicked(MLoc: point): OSStatus;
function HandleZoomMouseMoved(MLoc: HIPoint; boxArray: array of Rect; numberOfRects: integer; var saveQuadrant: integer): OSStatus;

procedure StoreOffScreen (box: Rect; VAR Snapshot: GWorldPtr);
procedure RestoreOffScreen (box: Rect; Snapshot: GWorldPtr);

procedure InitializeAlbum;
implementation

VAR
	StoreMode: Mode;
	StoreOldSpecial: HIBoxPtr;
	AlbumBounds: Rect;
	albumbox: BoxArray;
	albumCentre: CentreArray;

	procedure InitializeAlbum;
		var j: integer;
		midBoxIndex: integer;
		numBoxes: integer;
		begin
		
			GetWindowPortBounds(MainWindow, AlbumBounds);
			for  j := 0 to 4 do
			begin
				if AlbumBitmap[j] <> nil then
					DisposeGWorld(AlbumBitMap[j]);
					
				NewGWorld(AlbumBitMap[j], 0, AlbumBounds, nil, nil, 0);
			end;
			numBoxes := NRows * NCols;
			midBoxIndex := numBoxes div 2 + 1;
			CarveRectIntoBoxes(
				AlbumBounds,
				albumbox,
				albumCentre, 
				NRows, 
				NCols,
				numBoxes,
				midBoxIndex,
				GenesHeight);
		end;

procedure StoreOffScreen (box: Rect; VAR Snapshot: GWorldPtr);
	begin
		IF FrontWindow = MainWindow THEN
		begin
			CopyBits(
				GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, 
				GetPortBitMapForCopyBits(snapshot)^, 
				box, 
				box, 
				srcCopy, NIL);
		end;
	end; {StoreOffScreen}


procedure RestoreOffScreen (box: Rect; Snapshot: GWorldPtr);
	begin
		CopyBits(GetPortBitMapForCopyBits(Snapshot)^, GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, box, box, srcCopy, NIL);
		ValidWindowRect(MainWindow, box);
	end; {RestoreOffScreen}

procedure ShowAlbumPage (albumPageToShow: Integer);

	var 
		windowBounds: Rect;
		albumPageBounds: Rect;
		
	begin {StoreOffScreen(MainWindow^.PortRect,MyBitMap);}
		GetWindowPortBounds(MainWindow, windowBounds);
		GetPortBounds(AlbumBitMap[albumPageToShow], albumPageBounds);
		IF OldSpecial <> nil THEN
			OldSpecial^.Highlighted := false; {FIXME turn off highlighting... trigger redraw? -ABC}
			{InvertRect(Box[OldSpecial]);}
			
		OldSpecial := nil;

		CopyBits(
			GetPortBitMapForCopyBits(AlbumBitMap[albumPageToShow])^, 
			GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, 
			albumPageBounds, 
			windowBounds, 
			srcCopy, NIL);
		HiWindowFlush(MainWindow);
	end; {ShowAlbumPage}


procedure TakeCare (Page: Integer);
	VAR
		j: Integer;
		PageStart: Integer;
	begin
	(*
		if Album.menagerieSize = 0 then exit;
		PageStart := ((Page - 1) * NBoxes) MOD Album.menagerieSize;
		FOR j := 1 TO PBoxNo[Page] DO
			begin
				Special := j;
				Child[j] := Album.Member[PageStart + j]
			end;
		OldSpecial := nil;
		SetMode(MainWindow, Albuming);
		NActiveBoxes := PBoxNo[Page];
		CurrentPage := Page;
		SetCursor(CursList[BlackCursor]^^);
		*)
	end; {TakeCare}


procedure StickInAlbum; {Only called (once) by DoLoad}
	VAR
		Bust: Boolean;
		Albstr: Stringhandle;
		bounds: Rect;
		currentAlbumBoxBounds: Rect;
		currentBoxBounds: Rect;
	begin
	(*
		IF Page = 0 THEN
			Page := 1;
		Bust := FALSE;
		{Morph is initially set to 1. It increases by 1 when a new one is stuck in album}
		WHILE (Morph <= Album.menagerieSize) AND NOT Bust DO
		
			begin
				IF (BoxNo > NBoxes) THEN
					begin
						BoxNo := 1;
						Page := Page + 1;
						IF Page > MaxPages THEN
							begin
								Bust := TRUE;
								Sysbeep(1);{4 pages of album already full}
								Album.menagerieSize := Morph;
								Page := MaxPages
							end
					end; {case of new page needed}
				IF NOT Bust THEN
					begin
						IF BoxNo = 1 THEN
							begin
								GetWindowPortBounds(MainWindow, bounds);
								ClipRect(bounds);
								EraseRect(bounds);
								OldSpecial := nil;
								MoveTo(200, 15);
								TextSize(12);
								{TextFace([]);}
								TextFace(normal);
								AlbStr := GetString(AlbumPageID);
								DrawString(Concat(AlbStr^^, ' ', char(Page + 48))); {changed v1.1}
								TextSize(9);
								HIWindowFlush(MainWindow);
								StoreOffScreen(bounds, AlbumBitMap[Page]);
							end;
						{ClipRect(Box[BoxNo]);}
						DelayVelop(Album.Member[Morph], Centre[BoxNo]);
						PBoxNo[Page] := BoxNo;{range check failed}
						Album.Place[Morph].Page := Page;
						Album.Place[Morph].BoxNo := BoxNo;
						HIWindowFlush(MainWindow);
						currentAlbumBoxBounds := albumBox[BoxNo];
						currentBoxBounds := box[BoxNo];
						CopyBits(
							GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, 
							GetPortBitMapForCopyBits(AlbumBitMap[Page])^, 
							currentBoxBounds, 
							currentAlbumBoxBounds, 
							srcCopy, NIL);
						{StoreOffScreen(Box[BoxNo], AlbumBitMap[Page]);}
						Morph := Morph + 1;
						BoxNo := BoxNo + 1;
						ClipRect(bounds);
						CurrentPage := Page;
						TakeCare(Page);
					end
			end;
			{Added by Alan Canon to display all album pages if there's more than one.}
			if page > 1 then
				zoom;
				*)
	end; {StickInAlbum}

procedure UnCurtainPage (p: Integer);
	VAR
		LSlice, RSlice: Rect;
		Mid: Integer;
		{Delay was unimplemented, but TickValue was declared so I did it myself to see if there's any groovy animation. -- ABC}
		TickValue: LongInt;
		boundsRect: Rect;
		
	begin {CurtainPage;}
		GetWindowPortBounds(MainWindow, boundsRect);
		Mid := (boundsRect.Right - boundsRect.Left) DIV 2;
		LSlice := Prect;
		WITH LSlice DO
			begin
				Right := Mid;
				Left := Mid - 8
			end;
		RSlice := Prect;
		WITH RSlice DO
			begin
				Left := Mid;
				Right := Mid + 8
			end;
		WHILE LSlice.Left > Prect.Left DO
			begin
				IF Button THEN
					BrokenOut := TRUE;
				CopyBits(GetPortBitMapForCopyBits(AlbumBitMap[p])^, GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, LSlice, LSlice, srcCopy, NIL);
				CopyBits(GetPortBitMapForCopyBits(AlbumBitMap[p])^, GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, RSlice, RSlice, srcCopy, NIL);
				OffSetRect(LSlice, -8, 0);
				OffSetRect(Rslice, 8, 0);
				if EnableCurtainAnimation then
					begin
						HIWindowFlush(MainWindow);
						TickValue := TickCount;
						while TickCount < TickValue + TickDelay do;
					end;
			end;
		EraseRect(LSlice);
		EraseRect(RSlice);
	end; {UnCurtainPage}


procedure DoLoad (WithDialogue: Boolean);
	VAR
		i, k: Integer;
		tempRect: Rect;
	begin
		InitializeAlbum;

		SetCursor(CursList[WatchCursor]^^);
		IF Album.menagerieSize > 0 THEN
			begin
				UnCurtainPage(Page);
				SetMode(MainWindow, Albuming);
			end;
		StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);
		IF WithDialogue THEN
			ReadAnimals(ThisMenagerie);
		ReStoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);

		{I think that ideally this should be handled by DoUpdate, but we}
		{    don't visit GetNextEvent before other things are done -- Alan Graefen or Richard Dawkins}
		IF ThisMenagerie.menagerieSize > 0 THEN
			begin
				k := 0;
				i := album.menagerieSize;
				NBoxes := NRows * NCols;
				WHILE (k < ThisMenagerie.menagerieSize) AND (i < MaxAlbum) DO
					begin
						k := succ(k);
						i := i + 1;
						album.member[i] := ThisMenagerie.member[k];
					end;
				Album.menagerieSize := i;
				StickInAlbum;
				{Commented out by Alan Canon so we don't have an inverted box for the now-default quadrant view}
				(*
				IF OldSpecial = 0 THEN
					begin
						OldSpecial := Special;
						InvertRect(Box[special])
					end;
				*) 
				AlbumEmpty := FALSE;
				StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);
				end;
		IF FrontWindow = MainWindow THEN
			StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);

		IF Button THEN
			REPEAT
			UNTIL NOT Button;
		FlushEvents(everyEvent, 0);
		ValidWindowRect(MainWindow, PRect);
		IF Album.menagerieSize > MaxAlbum THEN
			Album.menagerieSize := MaxAlbum;

	end; {DoLoad}


procedure StoreBreedingScreen;
	VAR
		j: Integer;
		tempRect: Rect;
	begin
		StoreMode := TheMode; {either breeding or highlighting}
		StoreOldSpecial := OldSpecial;
		FOR j := 1 TO BreedNBoxes DO
			StoreChild[j] := Child[j];

		{AlbumBitMap[0] is special reserve bitmap for breeding screen}
		StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, AlbumBitMap[0]);

		SomethingToRestore := (theMode = Breeding) OR (theMode = Highlighting)
	end; {StoreBreedingScreen}


procedure RestoreBreedingScreen(boxes: HIBoxesPtr);
	VAR
		j: Integer;
		tempRect: Rect;
	begin
		IF OldSpecial <> nil THEN
			OldSpecial^.Highlighted := false;
			{InvertRect(Box[OldSpecial]);}
		FOR j := 1 TO BreedNBoxes DO
			Child[j] := StoreChild[j];
		NRows := BreedNRows;
		NCols := BreedNCols;
		SetMode(MainWindow, Preliminary); {To prevent SetupBoxes(MainWindow) drawing boxes}
		{SetupBoxes(MainWindow);}
		Special := boxes^.MidBox;
		SetMode(MainWindow, StoreMode);
		{AlbumBitMap[0] is special reserve bitmap for breeding screen}
		ReStoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, AlbumBitMap[0]);

		NActiveBoxes := NBoxes;
		OldSpecial := StoreOldSpecial;
		SomethingToRestore := FALSE;
		IF TheMode = Highlighting THEN
			begin
				IF OldSpecial <> nil THEN
					OldSpecial^.Highlighted := false;
					{InvertRect(Box[OldSpecial]);}
				OldSpecial := nil
			end;
	end;{RestoreBreedingScreen}


procedure AddToAlbum (Biomorph: PersonPtr; breedModel: HIBreedModelPtr);
	VAR
		StoreSpecial: HIBoxPtr;
	begin
		AlbumChanged := TRUE;
		StoreSpecial := Special;
		ThisMenagerie.menagerieSize := 1;
		ThisMenagerie.Member[1]^.denizen := Biomorph; {In order to use Load}
		DoLoad(FALSE);
		Special := StoreSpecial;
		RestoreBreedingScreen(breedModel^.boxes);
		IF TheMode = engineering THEN
			DoEngineer;
	end; {AddToAlbum}




procedure GracefulDeath;
{This is used only for abnormal terminations, normal ones simple set finished to true and exit the main loop}
{v1.1 fixed}
	VAR
		string1, string2: Str255;
		theItem: Integer;
	begin
		IF AlbumChanged THEN
			begin
				GetIndString(string1, kAlertStringsID, kAlbumID);
				GetIndString(string2, kAlertStringsID, kClosingID);
				ParamText(string1, string2, '', '');
				theItem := CautionAlert(152, NIL);
				IF theItem = 1 THEN
					DoSave(3); {Yes}
			end;

		IF FossilsToSave THEN
			begin
				GetIndString(string1, kAlertStringsID, kFossilsID);
				ParamText(string1, string2, '', '');
				theItem := CautionAlert(152, NIL);
				IF theItem = 1 THEN
					SaveSlides; {Yes}
			end;
		FSCloseFork(Slides);

		ExitToShell;		{Removed from cleanup to allow normal termination}
	end; {GracefulDeath}


procedure DoClose;
	VAR
		Verdict: Integer;
	begin
		Verdict := 0;
		IF AlbumChanged THEN
			begin
				DireMessage(kAlbumID, kClosingID, Verdict, true);
				IF Verdict = 1 THEN
					DoSave(3); {Yes}
				IF Verdict = 3 THEN
					Finished := FALSE; {Cancel}
			end;

		IF (Verdict <> 3) OR (NOT AlbumChanged) THEN
			begin
				Page := 0;
				BoxNo := 1;
				Morph := 1;
				CurrentPage := 0;
				Album.menagerieSize := 0;
				EraseRect(Prect);
				SetMode(MainWindow, Preliminary);
				NActiveBoxes := 0;
				AlbumEmpty := TRUE
			end;
	end;


procedure Emphasize (MLoc: point);
	VAR
		j: Integer;
	begin
	(* FIXME
		j := 0;
		REPEAT
			j := j + 1
		UNTIL (PtInRect(Mloc, box[j])) OR (j > NActiveBoxes);
		IF j <= NActiveBoxes THEN
			begin
				IF danger THEN
					danger := FALSE
				ELSE
					begin
						special := j;
						IF oldspecial > 0 THEN
							InvertRect(box[OldSpecial]);
						InvertRect(box[special]);
						oldspecial := special
					end
			end;
			*)
	end; {emphasize}


procedure Zoom;
	VAR
	
		j: Integer;
		albumPageBounds: Rect;

	begin

		
		{FIXME}
		SetPortWindowPort(MainWindow);
		
		FOR j := 1 TO Page DO
			begin
				EraseRect(Quadrant[j]);
				GetPortBounds(AlbumBitMap[j], albumPageBounds);
				CopyBits(
					GetPortBitMapForCopyBits(AlbumBitMap[j])^, 
					GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, 
					albumPageBounds, 
					Quadrant[j], srcCopy, NIL);
			end;
				
		HIWindowFlush(MainWindow);
		OldQuadrant := 0;
		Zoomed := true;
	end; {Zoom}

function HandleZoomClicked(MLoc: point): OSStatus;
var windowBounds: Rect;
albumPageBounds: REct;
begin
	GetPortBounds(AlbumBitMap[OldQuadrant], albumPageBounds);
	GetWindowPortBounds(MainWindow, windowBounds);

		CopyBits(
			GetPortBitMapForCopyBits(AlbumBitMap[OldQuadrant])^, 
			GetPortBitMapForCopyBits(GetWindowPort(MainWindow))^, albumPageBounds, windowBounds, srcCopy, NIL);
		TakeCare(OldQuadrant);
		{FIXME? I thought it looked cleaner not to emphasize one straight off the bat. -- ABC}
		(*
		WITH MLoc DO
			begin
				h := 2 * (h - Quadrant[OldQuadrant].left);
				v := 2 * (v - Quadrant[OldQuadrant].Top)
			end;
		emphasize(Mloc); *)
		zoomed := false;
		HiWindowFlush(MainWindow);
		HandleZoomClicked := noerr;
end;

function HandleZoomMouseMoved(MLoc: HIPoint; boxArray: array of Rect; numberOfRects: integer; var saveQuadrant: integer): OSStatus;
var 	
	tempCursor: Cursor;
	j: integer;
begin
	if MLoc.y < boxArray[1].top then
		SetCursor(GetQDGlobalsArrow(tempCursor)^)
	ELSE
		begin
		(* FIXME
			SetCursor(CursList[LensCursor]^^);
			j := 0;
			REPEAT
				j := j + 1
			UNTIL (j = numberOfRects) OR (PtInRect(Mloc, boxArray[j]));
			IF (saveQuadrant <> j) AND (j <= numberOfRects) THEN
			begin
				PenSize(3, 3);
				PenMode(PatXor);
				IF saveQuadrant <> 0 THEN
					begin 
						{PenPat(White);}
						FrameRect(Quadrant[saveQuadrant]);
						{PenPat(Black)}
					end;
				IF j <= Page THEN
					begin
						FrameRect(boxArray[j]);
						saveQuadrant := j;
					end;
				PenSize(1, 1);
				PenMode(PatCopy);
				HiWindowFlush(MainWindow);
				
			end; *)
		end;
	HandleZoomMouseMoved := noerr;
end;

function AlbumFull: Boolean;
	VAR
		AF: Boolean;
	begin
		AF := FALSE;
		IF (Page = 4) THEN
			begin
				IF PBoxNo[Page] = (AlbumNRows * AlbumNCols) THEN
					AF := TRUE
			end;
		AlbumFull := AF
	end; {AlbumFull}


end.