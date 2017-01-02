unit unit_doadjustmenus;

interface
{$IFC UNDEFINED THINK_Pascal}
{$ENDC}

procedure DoAdjustMenus;


implementation

uses
{$IFC UNDEFINED THINK_Pascal}
Quickdraw, Menus, Windows,
TextEdit,
Scrap,
{$ENDC}
  unit_docrec,
  unit_globals;


procedure DoAdjustMenus;

var
  fileMenuHdl, editMenuHdl: MenuHandle;
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  editRecHdl: TEHandle;
  scrapOffset: longint;

begin
  fileMenuHdl := GetMHandle(mFile);
  editMenuHdl := GetMHandle(mEdit);

  if (gNumberOfWindows > 0) then
  begin
    myWindowPtr := FrontWindow;
    docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
    editRecHdl := docRecHdl^^.editRecHdl;

    EnableItem(fileMenuHdl, iClose);

    if (editRecHdl^^.selStart < editRecHdl^^.selEnd) then
    begin
      EnableItem(editMenuHdl, iCut);
      EnableItem(editMenuHdl, iCopy);
      EnableItem(editMenuHdl, iClear);
    end
    else
    begin
      DisableItem(editMenuHdl, iCut);
      DisableItem(editMenuHdl, iCopy);
      DisableItem(editMenuHdl, iClear);
    end;

    if (GetScrap(nil, 'TEXT', scrapOffset) > 0) then
      EnableItem(editMenuHdl, iPaste)
    else
      DisableItem(editMenuHdl, iPaste);

    if (editRecHdl^^.teLength > 0) then
    begin
      EnableItem(fileMenuHdl, iSaveAs);
      EnableItem(editMenuHdl, iSelectAll);
    end
    else
    begin
      DisableItem(fileMenuHdl, iSaveAs);
      DisableItem(editMenuHdl, iSelectAll);
    end;
  end
  else
  begin
    DisableItem(fileMenuHdl, iClose);
    DisableItem(fileMenuHdl, iSaveAs);
    DisableItem(editMenuHdl, iClear);
    DisableItem(editMenuHdl, iSelectAll);
  end;

  DrawMenuBar;
end;

end.

