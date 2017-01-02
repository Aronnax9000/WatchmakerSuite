unit unit_domenuchoice;


interface

{$IFC UNDEFINED THINK_Pascal}
{$ENDC}
procedure DoMenuChoice(menuChoice: longint);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
Types, Menus, Windows, ToolUtils, OSUtils, Desk,
{$ENDC}
  Balloons,
  unit_doclosewindow,
  unit_docwindow_activate, unit_globals, unit_dofilemenu,
  unit_dohelpmenu, unit_doeditmenu;

procedure DoMenuChoice(menuChoice: longint);

var
  menuID, menuItem: integer;
  itemName: Str255;
  daDriverRefNum: integer;

begin
  menuID := HiWord(menuChoice);
  menuItem := LoWord(menuChoice);

  if (menuID = 0) then
    Exit(DoMenuChoice);

  case (menuID) of
    mApple:
    begin
      if (menuItem = iAbout) then
        SysBeep(10)
      else
      begin
        GetItem(GetMHandle(mApple), menuItem, itemName);
        daDriverRefNum := OpenDeskAcc(itemName);
      end;
    end;

    mFile:
    begin
      DoFileMenu(menuItem);
    end;

    mEdit:
    begin
      DoEditMenu(menuItem);
    end;

    kHMHelpMenuID:
    begin
      if (FrontWindow <> nil) then
        DoActivateDocWindow(FrontWindow, False);
      DoHelpMenu(menuItem);
    end;
  end;
  {of case statement}

  HiliteMenu(0);
end;


end.

