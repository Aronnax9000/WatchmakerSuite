unit unit_donewdocwindow;


interface

{$IFC UNDEFINED THINK_Pascal}
uses Quickdraw;

{$ENDC}
function DoNewDocWindow: WindowPtr;

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Types, Windows, QuickdrawText, Fonts, Memory, Controls, TextEdit, Events,
{$ENDC}
  unit_docrec, unit_doerroralert, unit_globals, unit_dodrawdatapanel;

const
  rWindow = 128;
  eDocRecord = 4;

procedure SetScrollBarValue(controlHdl: ControlHandle; var linesToScroll: integer);

var
  controlValue, controlMax: integer;

begin
  controlValue := GetCtlValue(controlHdl);
  controlMax := GetCtlMax(controlHdl);

  linesToScroll := controlValue - linesToScroll;
  if (linesToScroll < 0) then
    linesToScroll := 0
  else if (linesToScroll > controlMax) then
    linesToScroll := controlMax;

  SetCtlValue(controlHdl, linesToScroll);
  linesToScroll := controlValue - linesToScroll;
end;

{of procedure SetScrollBarValue}



function CustomClikLoop: boolean;

var
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  editRecHdl: TEHandle;
  oldPort: GrafPtr;
  oldClip: RgnHandle;
  tempRect: Rect;
  mouseXY: Point;
  linesToScroll: integer;

begin
  myWindowPtr := FrontWindow;
  docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
  editRecHdl := docRecHdl^^.editRecHdl;

  GetPort(oldPort);
  SetPort(myWindowPtr);
  oldClip := NewRgn;
  GetClip(oldClip);

  SetRect(tempRect, -32767, -32767, 32767, 32767);
  ClipRect(tempRect);

  GetMouse(mouseXY);

  if (mouseXY.v < myWindowPtr^.portRect.top) then
  begin
    linesToScroll := 1;
    SetScrollBarValue(docRecHdl^^.vScrollbarHdl, linesToScroll);
    if (linesToScroll <> 0) then
      TEScroll(0, linesToScroll * (editRecHdl^^.lineHeight), editRecHdl);
  end

  else if (mouseXY.v > myWindowPtr^.portRect.bottom) then
  begin
    linesToScroll := -1;
    SetScrollBarValue(docRecHdl^^.vScrollbarHdl, linesToScroll);
    if (linesToScroll <> 0) then
      TEScroll(0, linesToScroll * (editRecHdl^^.lineHeight), editRecHdl);
  end;

  DoDrawDataPanel(myWindowPtr);

  SetClip(oldClip);
  DisposeRgn(oldClip);
  SetPort(oldPort);

  CustomClikLoop := True;
end;

{of function CustomClikLoop}



function DoNewDocWindow: WindowPtr;

var
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  destAndViewRect: Rect;
  familyID: integer;
  ignored: integer;

begin
  myWindowPtr := GetNewWindow(rWindow, nil, WindowPtr(-1));
  if (myWindowPtr = nil) then
  begin
    DoErrorAlert(eWindow);
    DoNewDocWindow := nil;
  end;

  SetPort(myWindowPtr);

  TextSize(10);
  GetFNum('Geneva', familyID);
  TextFont(familyID);

  docRecHdl := DocRecHandle(NewHandle(sizeof(DocRec)));
  if (docRecHdl = nil) then
  begin
    DoErrorAlert(eDocRecord);
    DoNewDocWindow := nil;
  end;

  SetWRefCon(myWindowPtr, longint(docRecHdl));

  gNumberOfWindows := gNumberOfWindows + 1;

  docRecHdl^^.vScrollbarHdl := GetNewControl(rVScrollbar, myWindowPtr);

  destAndViewRect := myWindowPtr^.portRect;
  destAndViewRect.right := destAndViewRect.right - 15;
  destAndViewRect.bottom := destAndViewRect.bottom - 15;
  InsetRect(destAndViewRect, 2, 2);

  MoveHHi(Handle(docRecHdl));
  HLock(Handle(docRecHdl));

  docRecHdl^^.editRecHdl := TENew(destAndViewRect, destAndViewRect);
  if (docRecHdl^^.editRecHdl = nil) then
  begin
    DisposeWindow(myWindowPtr);
    gNumberOfWindows := gNumberOfWindows - 1;
    DisposeHandle(Handle(docRecHdl));
    DoErrorAlert(eEditRecord);
    DoNewDocWindow := nil;
  end;

  HUnlock(Handle(docRecHdl));

  SetClikLoop(ProcPtr(@CustomClikLoop), docRecHdl^^.editRecHdl);
  TEAutoView(True, docRecHdl^^.editRecHdl);
  ignored := TEFeatureFlag(teFOutlineHilite, 1, docRecHdl^^.editRecHdl);

  DoNewDocWindow := myWindowPtr;
end;

{of function DoNewDocWindow}


end.
