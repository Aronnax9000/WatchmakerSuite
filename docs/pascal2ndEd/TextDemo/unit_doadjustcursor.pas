unit unit_doadjustcursor;

interface
{$IFC UNDEFINED THINK_Pascal}
uses Quickdraw;
{$ENDC}
procedure DoAdjustCursor(myWindowPtr: WindowPtr; mouseRegion: RgnHandle);

implementation
uses
{$IFC UNDEFINED THINK_Pascal}
Types, Events, ToolUtils,
{$ENDC}
unit_globals;
procedure DoAdjustCursor(myWindowPtr: WindowPtr; mouseRegion: RgnHandle);

var
  oldPort: GrafPtr;
  arrowRegion, iBeamRegion: RgnHandle;
  cursorRect: Rect;
  mouseXY: Point;

begin
  if (gInBackground) then
  begin
    SetCursor(arrow);
    Exit(DoAdjustCursor);
  end;

  GetPort(oldPort);
  SetPort(myWindowPtr);

  arrowRegion := NewRgn;
  iBeamRegion := NewRgn;
  SetRectRgn(arrowRegion, -32768, -32768, 32766, 32766);

  cursorRect := myWindowPtr^.portRect;
  cursorRect.bottom := cursorRect.bottom - 15;
  cursorRect.right := cursorRect.right - 15;
  LocalToGlobal(cursorRect.topLeft);
  LocalToGlobal(cursorRect.botRight);

  RectRgn(iBeamRegion, cursorRect);
  DiffRgn(arrowRegion, iBeamRegion, arrowRegion);

  GetMouse(mouseXY);
  LocalToGlobal(mouseXY);

  if (PtInRgn(mouseXY, iBeamRegion)) then
  begin
    SetCursor(GetCursor(iBeamCursor)^^);
    CopyRgn(iBeamRegion, mouseRegion);
  end
  else
  begin
    SetCursor(arrow);
    CopyRgn(arrowRegion, mouseRegion);
  end;

  DisposeRgn(arrowRegion);
  DisposeRgn(iBeamRegion);

  SetPort(oldPort);
end;


end.

