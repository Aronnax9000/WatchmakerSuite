unit unit_scrollactionproc;

interface

{$IFC UNDEFINED THINK_Pascal}
uses
  Quickdraw, Windows, TextEdit, Controls;
{$ENDC}
procedure ScrollActionProc(controlHdl: ControlHandle; partCode: integer);

implementation

uses unit_docrec, unit_dodrawdatapanel;


procedure ScrollActionProc(controlHdl: ControlHandle; partCode: integer);

var
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  editRecHdl: TEHandle;
  linesToScroll: integer;
  controlValue, controlMax: integer;

begin
  if (partCode <> 0) then
  begin
    myWindowPtr := controlHdl^^.contrlOwner;
    docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
    editRecHdl := docRecHdl^^.editRecHdl;

    case (partCode) of

      inUpButton, inDownButton:
      begin
        linesToScroll := 1;
      end;

      inPageUp, inPageDown:
      begin
        linesToScroll := ((editRecHdl^^.viewRect.bottom - editRecHdl^^.viewRect.top) div
          editRecHdl^^.lineHeight) - 1;
      end;
    end;
    {of case statement}

    if ((partCode = inDownButton) or (partCode = inPageDown)) then
      linesToScroll := -linesToScroll;

    controlValue := GetCtlValue(controlHdl);
    controlMax := GetCtlMax(controlHdl);

    linesToScroll := controlValue - linesToScroll;
    if (linesToScroll < 0) then
      linesToScroll := 0
    else if (linesToScroll > controlMax) then
      linesToScroll := controlMax;

    SetCtlValue(controlHdl, linesToScroll);

    linesToScroll := controlValue - linesToScroll;

    if (linesToScroll <> 0) then
      TEScroll(0, linesToScroll * editRecHdl^^.lineHeight, editRecHdl);

    DoDrawDataPanel(myWindowPtr);
  end;

end;


end.

