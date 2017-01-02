unit unit_mactoolbox_ess;

{$mode macpas}{$H+}

interface

{$IFC UNDEFINED THINK_Pascal}
uses Quickdraw;
{$ENDC}

procedure MyAdjustScrollBars(window: WindowPtr; resizeScrollBars: boolean);

implementation
{$IFC UNDEFINED THINK_Pascal}
uses Types, Controls, TextEdit, Memory, Windows;
{$ENDC}

type MyDocRecHnd = ^MyDocRecPtr;
  MyDocRecPtr = ^MyDocRec;
  MyDocRec = RECORD
    vScrollBar: ControlHandle;
    hScrollBar: ControlHandle;
    editRec: TEHandle;
  end;

procedure MyGetDocWidth(window: WindowPtr; var Width: integer);
begin
  width := window^.portRect.right - 16;
end;

procedure MyAdjustHV(window: WindowPtr; isVert: boolean; control: ControlHandle; editRec: TEHandle);
var
  oldValue, oldMax, Width: integer;
  max, Lines, Value: integer;
begin
  {calculate new maximum and current settings for the vertical or }
  { horizontal scroll bar}
  oldMax := GetControlMaximum(control);
  oldValue := GetControlValue(control);
  MyGetDocWidth(window, Width);
  if isVert then
    {adjust max setting for the vertical scroll bar}
  begin
    Lines := editRec^^.nLines;
    {since nLines isn't right if the last character is a carriage }
    { return, check for that case}
    if Ptr(Ord(editRec^^.hText^) + editRec^^.teLength - 1)^ = LONGINT(#13) then
      Lines := Lines + 1;
    max := Lines - ((editRec^^.viewRect.bottom - editRec^^.viewRect.top) div
      editRec^^.lineHeight);
  end
  else
    {adjust max setting for the horizontal scroll bar}
    max := Width - (editRec^^.viewRect.right - editRec^^.viewRect.left);
  if max < 0 then
    max := 0;
  {check for negative settings}
  SetControlMaximum(control, max); {set the max value of the control}
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
  SetControlValue(control, Value);
end; {of MyAdjustHV}



procedure MyAdjustScrollValues(window: WindowPtr);
var
  myData: MyDocRecHnd;
begin
  myData := MyDocRecHnd(GetWRefCon(window));
  HLock(Handle(myData));
  with myData^^ do
  begin
    MyAdjustHV(window, True, vScrollBar, editRec);
    MyAdjustHV(window, False, hScrollBar, editRec);
  end;
  HUnLock(Handle(myData));
end; {of MyAdjustScrollValues}

MyAdjustScrollSizes(window: WindowPtr);
begin
end;


procedure MyAdjustScrollBars(window: WindowPtr; resizeScrollBars: boolean);
var
  myData: MyDocRecHnd;
begin
  myData := MyDocRecHnd(GetWRefCon(window));
  HLock(Handle(myData));
  with myData^^ do
  begin
    HideControl(vScrollBar);
    {hide the vertical scroll bar}
    HideControl(hScrollBar);
    {hide the horizontal scroll bar}
    if resizeScrollBars then
      {move and size if needed}
      MyAdjustScrollSizes(window);
    MyAdjustScrollValues(window);
    ShowControl(vScrollBar);
    {show the vertical scroll bar}
    ShowControl(hScrollBar);
    {show the horizontal scroll bar}
  end;
  HUnLock(Handle(myData));
end; {of MyAdjustScrollbars}

end.


