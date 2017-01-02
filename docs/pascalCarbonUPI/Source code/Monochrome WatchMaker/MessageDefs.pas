unit MessageDefs;

interface

uses
	CursorDefs,
	MacOSAll, 
	Globals,  DialogGeomDefs;

procedure DireMessage (string1ID, string2ID: Integer; VAR Verdict: Integer; YesNo: Boolean);
procedure MemoryMessage (ID: Integer; InMessage: Str255; VAR Verdict: Integer);
 { 27291 for Fewer album screens, 27295 for Careful}
procedure SimpleMessage (ID: Integer; VAR Verdict: Integer);
procedure HelpMessage (StrID: Integer);
procedure SyringeMessage;

implementation


{v1.1 changed DLOGs to ALRTs.}
procedure DireMessage (string1ID, string2ID: Integer; VAR Verdict: Integer; YesNo: Boolean);
	VAR
		{tmpRectPtr: RectPtr;}
		{boundsRect: Rect;}
		theItem: Integer;
		string1, string2: Str255;
	begin

		{tmpRectPtr := GetPortBounds(GetWindowPort(MainWindow), boundsRect);}
		{StoreOffScreen(boundsRect, MyBitMap);}
		GetIndString(string1, kAlertStringsID, string1ID);
		GetIndString(string2, kAlertStringsID, string2ID);
		ParamText(string1, string2, '', '');
		IF YesNo THEN
			theItem := CautionAlert(151, NIL)
		ELSE
			theItem := CautionAlert(17089, NIL);
		Verdict := theItem;

		{GetPortBounds(GetWindowPort(MainWindow), boundsRect);}
		{ReStoreOffScreen(boundsRect, MyBitMap);}
		FlushEvents(EveryEvent, 0);

		{FIXME MainWindow, I think. -- ABC}
		ValidWindowRect(MainWindow, Prect);
	end; { of proc DireMessage }


procedure MemoryMessage (ID: Integer; InMessage: Str255; VAR Verdict: Integer);
 {27291 for Fewer album screens, 27295 for Careful}
{*** Change this to handle appropriate type alerts with strings from STR# ***}
{ v1.1 changed to alerts, position correctly on screen,}
{ ID 4405 (got to quit) message changed to DisplayError call}
	VAR
		theItem: Integer;

	begin
		SetCursor(CursList[CrossCursor]^^);
		IF (ID = 27295) THEN
			InMessage := '';
		ParamText('', InMessage, '', '');
		theItem := CautionAlert(ID, NIL);
		Verdict := theItem;
		FlushEvents(EveryEvent, 0);
		{FIXME MainWindow, I think. -- ABC}
		ValidWindowRect(MainWindow, Prect);
	end; {MemoryMessage}


procedure SimpleMessage (ID: Integer; VAR Verdict: Integer);
{v1.1 redesigned the alert slightly and made it a Caution Alert. This procedure}
{seems to be always called with the same string ID. If more are added, the alert}
{may need to be made less specific}
	VAR
		S: Stringhandle;
	begin
		SetCursor(CursList[CrossCursor]^^);

		S := GetString(ID);
		ParamText(S^^, '', '', '');

		verdict := CautionAlert(17089, NIL);	{changed v1.1}

		FlushEvents(EveryEvent, 0);
		{FIXME MainWindow, I think. -- ABC}
		ValidWindowRect(MainWindow, Prect);
	end; {SimpleMessage}


procedure HelpMessage (StrID: Integer);
	VAR
		HelpPtr: dialogPtr;
		theItem: Integer;
		S1, S2, S3, S4: Str255;
	begin
		GetIndString(S1, StrID, 1);
		GetIndString(S2, StrID, 2);
		GetIndString(S3, StrID, 3);
		GetIndString(S4, StrID, 4);
		ParamText(S1, S2, S3, S4);

		HelpPtr := GetNewDialog(2000, NIL, Pointer(-1));
		ModalDialog(NIL, theItem);             { put dialog box up; get result }
		DisposeDialog(HelpPtr);               { get rid of dialog box         }
  		FlushEvents(EveryEvent, 0);

		{FIXME MainWindow, I think. -- ABC}
		ValidWindowRect(MainWindow, Prect);
	end; { of proc HelpMessage }


procedure SyringeMessage;
{v1.1 Changed to alert, and set the first two alert stages to simple beeps}
	{VAR}

		{boundsRect: Rect;}
	begin

		{GetPortBounds(GetWindowPort(MainWindow), boundsRect);}
		{StoreOffScreen(boundsRect, MyBitMap);}		

		Alert(30130, NIL);

		{ReStoreOffScreen(boundsRect, MyBitMap);}
		FlushEvents(EveryEvent, 0);

		{FIXME MainWindow, I think. -- ABC}
		ValidWindowRect(MainWindow, Prect);

end; { of proc SyringeMessage }



end.