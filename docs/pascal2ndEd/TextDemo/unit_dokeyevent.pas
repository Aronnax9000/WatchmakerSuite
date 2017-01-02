unit unit_dokeyevent;



interface


procedure DoKeyEvent(charCode: char);
implementation

uses
{$IFC UNDEFINED THINK_Pascal}

  Events, Types, Quickdraw, Menus, Windows,
TextEdit,
Memory, ToolUtils, OSUtils, Scrap,
StandardFile, Controls, Files, Desk,

{$ENDC}
  unit_docrec, unit_globals, unit_doerroralert,
unit_dogetselectlength, unit_dodrawdatapanel, unit_doadjustscrollbar;

const   kTab = $09;
  kDel = $7F;




procedure DoKeyEvent(charCode: char);

var
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  editRecHdl: TEHandle;
  selectionLength: integer;

begin
  myWindowPtr := FrontWindow;
  docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
  editRecHdl := docRecHdl^^.editRecHdl;

  if (charCode = char(kTab)) then
  begin
    { Do tab key handling here if required. }
  end
  else if (charCode = char(kDel)) then
  begin
    selectionLength := DoGetSelectLength(editRecHdl);
    if (selectionLength = 0) then
      editRecHdl^^.selEnd := editRecHdl^^.selEnd + 1;
    TEDelete(editRecHdl);
    DoAdjustScrollbar(myWindowPtr);
  end
  else
  begin
    selectionLength := DoGetSelectLength(editRecHdl);
    if ((editRecHdl^^.teLength - selectionLength + 1) < kMaxTELength) then
    begin
      TEKey(charCode, editRecHdl);
      DoAdjustScrollbar(myWindowPtr);
    end
    else
      DoErrorAlert(eExceedChara);
  end;

  DoDrawDataPanel(myWindowPtr);
end;
end.

