unit unit_doeditmenu;
interface

{$IFC UNDEFINED THINK_Pascal}
uses
  Quickdraw, TextEdit;
{$ENDC}

procedure DoEditMenu(menuItem: integer);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
Types, Windows,
Memory, Scrap,
{$ENDC}
  unit_docrec, unit_doerroralert,
  unit_dodrawdatapanel,
  unit_dogetselectlength, unit_doadjustscrollbar,
  unit_globals;

procedure DoEditMenu(menuItem: integer);

var
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  editRecHdl: TEHandle;
  totalSize, contigSize, newSize, scrapOffset: longint;
  selectionLength: integer;
  ignored: OSErr;

begin
  myWindowPtr := FrontWindow;
  docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
  editRecHdl := docRecHdl^^.editRecHdl;

  case (menuItem) of

    iUndo:
    begin
    end;

    iCut:
    begin
      if (ZeroScrap = noErr) then
      begin
        PurgeSpace(totalSize, contigSize);
        selectionLength := DoGetSelectLength(editRecHdl);
        if (selectionLength > contigSize) then
          DoErrorAlert(eNoSpaceCut)
        else
        begin
          TECut(editRecHdl);
          DoAdjustScrollbar(myWindowPtr);
          if (TEToScrap <> noErr) then
            ignored := ZeroScrap;
        end;
      end;
    end;

    iCopy:
    begin
      if (ZeroScrap = noErr) then
      begin
        TECopy(editRecHdl);
        if (TEToScrap <> noErr) then
          ignored := ZeroScrap;
      end;
    end;

    iPaste:
    begin
      newSize := editRecHdl^^.teLength + GetScrap(nil, 'TEXT', scrapOffset);
      if (newSize > kMaxTELength) then
        DoErrorAlert(eNoSpacePaste)
      else
      begin
        if (TEFromScrap = noErr) then
        begin
          TEPaste(editRecHdl);
          DoAdjustScrollbar(myWindowPtr);
        end;
      end;
    end;

    iClear:
    begin
      TEDelete(editRecHdl);
      DoAdjustScrollbar(myWindowPtr);
    end;

    iSelectAll:
    begin
      TESetSelect(0, editRecHdl^^.teLength, editRecHdl);
    end;
  end;
  {of case statement}

  DoDrawDataPanel(myWindowPtr);
end;

end.

