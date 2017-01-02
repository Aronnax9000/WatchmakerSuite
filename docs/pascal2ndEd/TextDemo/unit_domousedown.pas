unit unit_domousedown;

interface
{$IFC UNDEFINED THINK_Pascal}
uses
  Events;
{$ENDC}

procedure DoMouseDown(var theEvent: EventRecord);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Menus, Desk, Quickdraw, TextEdit, Types, Controls, Windows, ToolUtils,
{$ENDC}
 unit_docrec, unit_scrollactionproc, unit_dodrawdatapanel,
 unit_doadjustmenus, unit_doadjustscrollbar, unit_domenuchoice,
unit_doclosewindow;

procedure DoInContent(var theEvent: EventRecord);

var
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  editRecHdl: TEHandle;
  mouseXY: Point;
  controlHdl: ControlHandle;
  partCode, controlValue: integer;
  shiftKeyPosition: boolean;
  ignored: OSErr;
  findWindowCode: INTEGER;
begin
  shiftKeyPosition := False;
  myWindowPtr := FrontWindow;
  docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
  editRecHdl := docRecHdl^^.editRecHdl;


  mouseXY := theEvent.where;
  GlobalToLocal(mouseXY);

  partCode := FindControl(mouseXY, myWindowPtr, controlHdl);
  if (partCode <> 0) then
  begin
    case (partCode) of

      inUpButton, inDownButton, inPageUp, inPageDown:
      begin
        ignored := TrackControl(controlHdl, mouseXY, ProcPtr(@ScrollActionProc));
      end;

      inThumb:
      begin
        controlValue := GetCtlValue(controlHdl);
        partCode := TrackControl(controlHdl, mouseXY, nil);
        if (partCode <> 0) then
        begin
          controlValue := controlValue - GetCtlValue(controlHdl);
          if (controlValue <> 0) then
            TEScroll(0, controlValue * editRecHdl^^.lineHeight, editRecHdl);
        end;
        DoDrawDataPanel(myWindowPtr);
      end;
    end;
    {of case statement}
  end

  else if (PtInRect(mouseXY, editRecHdl^^.viewRect)) then
  begin
    if (BAnd(theEvent.modifiers, shiftKey) <> 0) then
      shiftKeyPosition := True;
    TEClick(mouseXY, shiftKeyPosition, editRecHdl);
  end;

end;

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

procedure DoMouseDown(var theEvent: EventRecord);

var
  myWindowPtr: WindowPtr;
  partCode: integer;

begin
  partCode := FindWindow(theEvent.where, myWindowPtr);

  case (partCode) of

    inMenuBar:
    begin
      DoAdjustMenus;
      DoMenuChoice(MenuSelect(theEvent.where));
    end;

    inSysWindow:
    begin
      SystemClick(theEvent, myWindowPtr);
    end;

    inContent:
    begin
      if (myWindowPtr <> FrontWindow) then
        SelectWindow(myWindowPtr)
      else
        DoInContent(theEvent);
    end;

    inDrag:
    begin
      DragWindow(myWindowPtr, theEvent.where, screenBits.bounds);
    end;

    inGoAway:
    begin
      if (TrackGoAway(myWindowPtr, theEvent.where)) then
        DoCloseWindow(FrontWindow);
    end;

    inGrow: {Resize code goes here.}
    begin
       handleWindowGrowing(theEvent, myWindowPtr);
       DoAdjustTEViewRect(myWindowPtr);
       DoAdjustScrollbar(myWindowPtr);
       DoAdjustScrollSizes(myWindowPtr);
    end;
  end;
  {of case statement}
end;

end.

