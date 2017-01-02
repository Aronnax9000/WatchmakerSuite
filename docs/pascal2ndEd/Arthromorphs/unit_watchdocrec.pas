unit unit_watchdocrec;

interface

{$IFC UNDEFINED THINK_Pascal}
uses
  Controls, Quickdraw, TextEdit;

{$ENDC}

type
  WatchDocRecHandle = ^WatchDocRecPtr;
  WatchDocRecPtr = ^WatchDocRec;

  WatchDocRec = record
    resourceRefCon: longint;
    vScrollBar: ControlHandle;
    hScrollBar: ControlHandle;
    editRec: TEHandle;
  end;

function GetWResourceRefCon(theWindow: WindowPtr): longint;
function NewWatchDocRec(theWindow: WindowPtr): WatchDocRecHandle;
procedure DisposeWatchDocRec(rec: WatchDocRecHandle);
procedure MyAdjustScrollBars(window: WindowPtr; resizeScrollBars: boolean);
procedure TrackScrollBar(theControl: ControlHandle; partCode: integer);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Types, Memory, Windows, Fonts, QuickdrawText,
{$ENDC}
  unit_tell_error;

function NewWatchDocRec(theWindow: WindowPtr): WatchDocRecHandle;

var
  boundsRect: Rect;
  title: Str255;
  Visible: boolean;
  Value: integer;
  min: integer;
  max: integer;
  procid: integer;
  refcon: longint;
  rec: WatchDocRecHandle;
  destRect: Rect;
  viewRect: Rect;
begin
  title := '';
  Visible := True;
  Value := 0;
  min := 0;
  procid := scrollBarProc;
  refcon := longint(Ord(theWindow));

  rec := WatchDocRecHandle(NewHandle(SizeOf(WatchDocRec)));
  rec^^.resourceRefCon := GetWRefCon(theWindow);
  HLock(Handle(rec));
  with theWindow^.portRect do
  begin
    max := bottom - 16;
    with WatchDocRecHandle(rec)^^ do
    begin
      setRect(boundsRect, right - 15, 0, right, bottom - 15);
      vScrollBar := NewControl(theWindow, boundsRect, title, Visible,
        Value, min, max, procid, refcon);
      setRect(boundsRect, 0, bottom - 15, right - 15, bottom);
      hScrollBar := NewControl(theWindow, boundsRect, title, Visible,
        Value, min, max, procid, refcon);
      destRect := theWindow^.portRect;
      destRect.left := destRect.left + 4; {Per Inside Macintosh: Text ABC}
      destRect.right := destRect.right - 20; {Per Inside Macintosh: Text ABC}
      viewRect := destRect;
      SetPort(theWindow);      {Prepare to write into our window}
      TextFont(courier);
      editRec := TENew(destRect, viewRect);
      TextFont(applFont);
      if editRec <> nil then
      begin
        TEAutoView(True, editRec);
      end;
    end;
  end;
  HUnLock(Handle(rec));
  NewWatchDocRec := rec;
end;

procedure DisposeWatchDocRec(rec: WatchDocRecHandle);
begin
  with rec^^ do
  begin
    DisposeControl(vScrollBar);
    DisposeControl(hScrollbar);
    TEDispose(editRec);
  end;
  DisposeHandle(Handle(rec));
end;

function GetWResourceRefCon(theWindow: WindowPtr): longint;
begin
  GetWResourceRefCon := WatchDocRecHandle(GetWRefCon(theWindow))^^.resourceRefCon;
end;

procedure MyGetDocWidth(window: WindowPtr; var Width: integer);
begin
  Width := WatchDocRecHandle(GetWRefCon(window))^^.editRec^^.viewRect.right;
end;

procedure MyAdjustHV(window: WindowPtr; isVert: boolean; control: ControlHandle;
  editRec: TEHandle);
var
  oldValue, oldMax, Width: integer;
  max, Lines, Value: integer;
  viewRectHeight: INTEGER;
  viewRectHeightInLines: INTEGER;
begin
  {calculate new maximum and current settings for the vertical or }
  { horizontal scroll bar}
  oldMax := GetCtlMax(control);
  oldValue := GetCtlValue(control);
  MyGetDocWidth(window, Width);
  if isVert then
    {adjust max setting for the vertical scroll bar}
  begin
    Lines := editRec^^.nLines;
    {since nLines isn't right if the last character is a carriage }
    { return, check for that case}
    if Ptr(Ord(editRec^^.hText^) + editRec^^.teLength - 1)^ = 13 then
      Lines := Lines + 1;
    viewRectHeight:= (editRec^^.viewRect.bottom - editRec^^.viewRect.top);
    viewRectHeightInLines := viewRectHeight div
      editRec^^.lineHeight;
    max := Lines div viewRectHeightInLines;
  end
  else
    {adjust max setting for the horizontal scroll bar}
    max := Width - (editRec^^.viewRect.right - editRec^^.viewRect.left);
  if max < 0 then
    max := 0;
  {check for negative settings}
  SetCtlMax(control, max); {set the max value of the control}
  if isVert then {adjust current setting for vertical scroll bar}
    Value := (editRec^^.viewRect.top - editRec^^.destRect.top) div editRec^^.lineHeight
  else
    {adjust current setting for the horizontal scroll bar}
    Value := editRec^^.viewRect.left - editRec^^.destRect.left;
  if Value < 0 then
    Value := 0
  else if Value > max then
    Value := max; {don't allow current setting to be greater than the }
  { maximum setting}
  SetCtlValue(control, Value);
end; {of MyAdjustHV}



procedure MyAdjustScrollValues(window: WindowPtr);
var
  myData: WatchDocRecHandle;
  hTE: TEHandle;
begin
  myData := WatchDocRecHandle(GetWRefCon(window));
  hTE := myData^^.editRec;

  HLock(Handle(myData));
  with myData^^ do
  begin
    MyAdjustHV(window, True, vScrollBar, editRec);
    MyAdjustHV(window, False, hScrollBar, editRec);
  end;
  HUnLock(Handle(myData));
end; {of MyAdjustScrollValues}

procedure MyAdjustScrollSizes(window: WindowPtr; vScrollBar, hScrollBar: ControlHandle);
var
  r: Rect;
begin
  r := window^.portRect;
  with r do
  begin
    MoveControl(vScrollBar, right - 15, 0);
    SizeControl(vScrollBar, 16, bottom - 15);
    MoveControl(hScrollBar, 0, bottom - 15);
    SizeControl(hScrollBar, right - 15, 16);
  end;
end;

procedure TrackScrollBar(theControl: ControlHandle; partCode: integer);
var
  theWindow: WindowPtr;
  rec: WatchDocRecHandle;
  curr, min, max, lines: integer;
  distanceToScroll: integer;
begin
  theWindow := WindowPtr(GetCRefCon(theControl));
  rec := WatchDocRecHandle(GetWRefCon(theWindow));
  if (theControl = rec^^.hScrollBar) or (theControl = rec^^.vScrollBar) then
  begin
    curr := GetCtlValue(theControl);
    max := GetCtlMax(theControl);
    min := GetCtlMin(theControl);
    Lines := rec^^.editRec^^.nLines;
    {since nLines isn't right if the last character is a carriage }
    { return, check for that case}
    if Ptr(Ord(rec^^.editRec^^.hText^) + rec^^.editRec^^.teLength - 1)^ = 13 then
      Lines := Lines + 1;
    if partCode <> 0 then
    begin

      case partCode of
        inButton:
        begin
          TellError('In Button');
        end;
        inCheckBox:
        begin
          TellError('In Checkbox');
        end;
        inUpButton:
        begin
          if curr > min then
          begin
            SetPort(theWindow);
            curr := curr - 1;
            SetCtlValue(theControl, curr);
            with rec^^.editRec^^ do
            begin
              distanceToScroll := lineHeight * (lineHeight * Lines) div
              (viewRect.bottom - viewRect.top);
            end;
            TEScroll(0,distanceToScroll, rec^^.editRec);
            InvalRect(theControl^^.contrlRect);
          end;
        end;
        inDownButton:
        begin
          if curr < max then
          begin
            SetPort(theWindow);
            curr := curr + 1;
            SetCtlValue(theControl, curr);
            with rec^^.editRec^^ do
            begin
              distanceToScroll := lineHeight * (lineHeight * Lines) div
              (viewRect.bottom - viewRect.top);
            end;
            TEScroll(0, -distanceToScroll, rec^^.editRec);
            InvalRect(theControl^^.contrlRect);
          end;
        end;
        inPageUp:
        begin
          TellError('In PageUp');
        end;
        inPageDown:
        begin
          TellError('In PageDown');
        end;
        inThumb:
        begin
          TellError('In thumb');
        end;
      end;

    end;

  end;

end;

procedure MyAdjustScrollBars(window: WindowPtr; resizeScrollBars: boolean);
var
  myData: WatchDocRecHandle;
  viewRect: Rect;
begin
  myData := WatchDocRecHandle(GetWRefCon(window));
  HLock(Handle(myData));
  with myData^^ do
  begin
    viewRect := window^.portRect;
    viewRect.right := viewRect.right - 20;
    viewRect.bottom := viewRect.bottom - 20;
    myData^^.editRec^^.viewRect := viewRect;

    HideControl(vScrollBar);
    {hide the vertical scroll bar}
    HideControl(hScrollBar);
    {hide the horizontal scroll bar}
    if resizeScrollBars then
      {move and size if needed}
      MyAdjustScrollSizes(window, vScrollBar, hScrollBar);
    MyAdjustScrollValues(window);
    ShowControl(vScrollBar);
    {show the vertical scroll bar}
    ShowControl(hScrollBar);
    {show the horizontal scroll bar}
  end;
  HUnLock(Handle(myData));
end; {of MyAdjustScrollbars}




end.
