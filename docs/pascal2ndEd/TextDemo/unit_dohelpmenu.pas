unit unit_dohelpmenu;


interface


{$IFC UNDEFINED THINK_Pascal}
{$ENDC}
procedure DoHelpMenu(menuItem: integer);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
Types, Menus,
{$ENDC}
  Balloons, UHelpDialogPascal;

procedure DoHelpMenu(menuItem: integer);

var
  helpMenuHdl: MenuHandle;
  origHelpItems, numItems: integer;
  ignored: OSErr;

begin
  ignored := HMGetHelpMenuHandle(helpMenuHdl);

  numItems := CountMItems(helpMenuHdl);
  origHelpItems := numItems - 1;

  if (menuItem > origHelpItems) then
    DoHelp;
end;
end.

