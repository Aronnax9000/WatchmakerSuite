unit unit_dofilemenu;



interface
{$IFC UNDEFINED THINK_Pascal}
{$ENDC}
procedure DoFileMenu(menuItem: integer);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
Windows, Quickdraw, TextEdit,

{$ENDC}
  unit_docrec, unit_doclosewindow,
  unit_donewdocwindow,
  unit_globals, unit_files;


procedure DoFileMenu(menuItem: integer);

var
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  editRecHdl: TEHandle;

begin
  case (menuItem) of

    iNew:
    begin
      myWindowPtr := DoNewDocWindow;
      if (myWindowPtr <> nil) then
        ShowWindow(myWindowPtr);
    end;

    iOpen:
    begin
      DoOpenCommand;
    end;

    iClose:
    begin
      DoCloseWindow(FrontWindow);
    end;

    iSaveAs:
    begin
      docRecHdl := DocRecHandle(GetWRefCon(FrontWindow));
      editRecHdl := docRecHdl^^.editRecHdl;
      DoSaveAsFile(editRecHdl);
    end;

    iQuit:
    begin
      gDone := True;
    end;
  end;
  {of case statement}
end;

end.

