unit unit_doidle;

interface

procedure DoIdle;
implementation
uses
  {$IFC UNDEFINED THINK_Pascal}
Quickdraw, Windows, TextEdit,
{$ENDC}
  unit_docrec;

procedure DoIdle;

var
  docRecHdl: DocRecHandle;
  myWindowPtr: WindowPtr;

begin
  myWindowPtr := FrontWindow;

  docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
  if (docRecHdl <> nil) then
    TEIdle(docRecHdl^^.editRecHdl);
end;

end.

