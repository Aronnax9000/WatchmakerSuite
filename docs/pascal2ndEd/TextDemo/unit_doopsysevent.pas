unit unit_doopsysevent;


interface

{$IFC UNDEFINED THINK_Pascal}
uses
  Events;
{$ENDC}

procedure DoOpSysEvent(var theEvent: EventRecord);


implementation
uses
{$IFC UNDEFINED THINK_Pascal}
Menus, Windows,
{$ENDC}
  unit_docwindow_activate, unit_globals;

procedure DoOpSysEvent(var theEvent: EventRecord);

begin
  if (BAnd(BSR(theEvent.message, 24), $000000FF) = suspendResumeMessage) then
  begin
    if (BAnd(theEvent.message, resumeFlag) = 0) then
      gInBackground := True
    else
      gInBackground := False;

    if (gNumberOfWindows > 0) then
      DoActivateDocWindow(FrontWindow, not gInBackground);
    HiliteMenu(0);
  end;
end;

end.

