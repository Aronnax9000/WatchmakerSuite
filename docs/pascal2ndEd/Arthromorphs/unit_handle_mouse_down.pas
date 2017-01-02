unit unit_handle_mouse_down;

interface

uses
{$IFC UNDEFINED THINK_Pascal}
  Quickdraw, Events,
{$ENDC}
  unit_preferences;

function handleMouseDown(prefs: ArthromorphPreferencesHandle;
    whichWindow: WindowPtr;
  myEvent: EventRecord;
  code: integer): boolean;

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Types, Windows, Menus, ToolUtils, Desk,
{$ENDC}
unit_handle_the_menus,
  unit_breeding_window, unit_genome_window,
  unit_about_arthromorphs, unit_watchdocrec;

function handleMouseDownInMenuBar(prefs: ArthromorphPreferencesHandle;
  myEvent: EventRecord): boolean;
var
  mResult: longint;        {Menu list and item selected values}
  theMenu, theItem: integer;{Menu list and item selected}
begin     {Get the menu selection and handle it}
  mResult := MenuSelect(myEvent.Where);{Do menu selection}
  theMenu := HiWord(mResult);{Get the menu list number}
  theItem := LoWord(mResult);{Get the menu list item number}
  handleMouseDownInMenuBar := Handle_My_Menu(prefs, theMenu, theItem);{Handle the menu}
end; {End of inMenuBar}

procedure handleMouseDownInDrag(myEvent: EventRecord; whichWindow: WindowPtr);
var
  tempRect: Rect;
begin     {Do dragging the window}
  tempRect := screenbits.bounds;{Get screen area,  l,t,r,b, drag area}
  SetRect(tempRect, tempRect.Left + 10, tempRect.Top + 25,
    tempRect.Right - 10, tempRect.Bottom - 10);
  DragWindow(whichWindow, myEvent.where, tempRect);{Drag the window}
end;      {End of InDrag}

procedure handleWindowGrowing(myEvent: EventRecord;
  whichWindow: WindowPtr);
var
  myPt: Point;
  mResult: longint; {GrowWindow result}
  tempRect: Rect;
begin
  SetPort(whichWindow);{Get ready to draw in this window}
  myPt := myEvent.where;{Get mouse position}
  GlobalToLocal(myPt);{Make it relative}
  {OldRect := WhichWindow^.portRect; ABC}{Save the rect before resizing}
  with screenbits.bounds do{use the screens size}
    {l,t,r,b}
    SetRect(tempRect, 15, 15, (right - left), (bottom - top) - 20);

  mResult := GrowWindow(whichWindow, myEvent.where, tempRect);{Grow it}
  {Resize to result}
  SizeWindow(whichWindow, LoWord(mResult), HiWord(mResult), True);

  InvalRect(WhichWindow^.portRect);

  SetPort(whichWindow);{Get ready to draw in this window}
  {Position for horz scrollbar area}
  SetRect(tempRect, 0, myPt.v - 15, myPt.h + 15, myPt.v + 15);
  EraseRect(tempRect);{Erase old area}
  InvalRect(tempRect);{Flag us to update it}
  {Position for vert scrollbar area}
  SetRect(tempRect, myPt.h - 15, 0, myPt.h + 15, myPt.v + 15);
  EraseRect(tempRect);{Erase old area}
  InvalRect(tempRect);{Flag us to update it}
  DrawGrowIcon(whichWindow);{Draw the grow Icon again}

end;

procedure handleMouseDownInGrow(myEvent: EventRecord; whichWindow: WindowPtr;
  prefs: ArthromorphPreferencesHandle);

begin     {Handle the growing}
  handleWindowGrowing(myEvent, whichWindow);
	case (GetWResourceRefCon(whichWindow)) of{Do the appropiate window}
		1: {Genome Window}
      begin
        MyAdjustScrollBars(whichWindow, true);
      end;
		2: {breeding window}
			begin
        prefs^^.resizing := true;
      end;
    3: {about arthromorphs}
	end;    			{End of the case}


end;      {End of doing the growing}

procedure handleMouseDownInZoom(myEvent: EventRecord; whichWindow: WindowPtr;
  code: integer);
var
  myPt: Point;
  tempRect: Rect;
begin
  if (WhichWindow <> nil) then{See if we have a legal window}
  begin
    SetPort(whichWindow);{Get ready to draw in this window}

    myPt := myEvent.where;{Get mouse position}
    GlobalToLocal(myPt);{Make it relative}

    if TrackBox(whichWindow, myPt, code) then{Zoom it}
    begin
      ZoomWindow(WhichWindow, code, True);{Resize to result}
      SetRect(tempRect, 0, 0, 32000, 32000);{l,t,r,b}
      {Make sure we update the whole window effectively}
      EraseRect(tempRect);
      {Tell ourselves to update, redraw, the window contents}
      InvalRect(tempRect);
    end;
  end;
end; {handleMouseDownInZoom}

procedure handleMouseDownInGoAway(myEvent: EventRecord; whichWindow: WindowPtr; prefs: ArthromorphPreferencesHandle);
begin     {Handle the goaway button}
  {See if mouse released in GoAway box}
  if TrackGoAway(whichWindow, myEvent.where) then
  begin{Handle the GoAway}
    case (GetWResourceRefCon(whichWindow)) of{Do the appropiate window}
      1:
        Close_Genome_Window(prefs);{Close this window}
      2:
        Close_Breeding_Window(prefs);{Close this window}
      3:
        Close_About_Arthromorphs(prefs);{Close this window}
      {otherwise}{Handle others dialogs}
    end;{End of the case}
  end;{End of TrackGoAway}
end;      {End of InGoAway}

procedure handleMouseDownInContent(prefs: ArthromorphPreferencesHandle;
    myEvent: EventRecord;
  whichWindow: WindowPtr);
begin     {Handle the hit inside a window}
  {See if already selected or not, in front if selected}
  if (whichWindow <> FrontWindow) then
    SelectWindow(whichWindow){Select this window to make it active}
  else{If already in front the already selected}
  begin{Handle the button in the content}
    SetPort(whichWindow);{Get ready to draw in this window}
    case (GetWResourceRefCon(whichWindow)) of{Do the appropiate window}
      1:
        Do_Genome_Window(prefs, myEvent);{Handle this window}
      2:
        Do_Breeding_Window(prefs, myEvent);{Handle this window}
      3:
        Do_About_Arthromorphs;{Handle this window}
      {otherwise}{Handle others dialogs}
    end;{End of the case}
  end;{End of else}
end;      {End of inContent}


function handleMouseDown(prefs: ArthromorphPreferencesHandle;
    whichWindow: WindowPtr;
  myEvent: EventRecord;
  code: integer): boolean;
var
  eventDoneFlag: boolean;
begin       {Handle the pressed button}
  eventDoneFlag := False;
  case code of{where in the user interface the mousedown occurred.}
    inMenuBar: {In a menu selection}
      eventDoneFlag := handleMouseDownInMenuBar(prefs, myEvent);
    inDrag:
      handleMouseDownInDrag(myEvent, whichWindow);
    inGrow: {In a grow area of the window}
      handleMouseDownInGrow(myEvent, whichWindow, prefs);
    inZoomIn, inZoomOut:
      handleMouseDownInZoom(myEvent, whichWindow, code);
    inGoAway: {In a window goaway area}
      handleMouseDownInGoAway(myEvent, whichWindow, prefs);
    inContent: {In a window}
      handleMouseDownInContent(prefs, myEvent, whichWindow);
    inSysWindow: {In a Desk Accessory selection}
      SystemClick(myEvent, whichWindow);{Let other programs in}
  end;
  handleMouseDown := eventDoneFlag;
end; {End of MouseDown}

end.

