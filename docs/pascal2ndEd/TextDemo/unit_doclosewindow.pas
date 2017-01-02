unit unit_doclosewindow;


interface

{$IFC UNDEFINED THINK_Pascal}
uses
  Quickdraw;
{$ENDC}

procedure DoCloseWindow(myWindowPtr: WindowPtr);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
Types, Windows, TextEdit, Memory, Controls,
{$ENDC}
  unit_docrec, unit_globals;





procedure DoCloseWindow(myWindowPtr: WindowPtr);

var
  docRecHdl: DocRecHandle;

begin
  docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));

  DisposeControl(docRecHdl^^.vScrollbarHdl);
  TEDispose(docRecHdl^^.editRecHdl);
  DisposeHandle(Handle(docRecHdl));
  DisposeWindow(myWindowPtr);

  gNumberOfWindows := gNumberOfWindows - 1;
end;



end.

