unit unit_docwindow_activate;


interface
{$IFC UNDEFINED THINK_Pascal}
uses Events, Quickdraw;
{$ENDC}

procedure DoActivate(var theEvent: EventRecord);
procedure DoActivateDocWindow (myWindowPtr: WindowPtr; becomingActive: Boolean);


implementation
uses
{$IFC UNDEFINED THINK_Pascal}
TextEdit, Controls, Windows,
{$ENDC}
unit_docrec,
unit_doadjustscrollbar;

procedure DoActivateDocWindow (myWindowPtr: WindowPtr; becomingActive: Boolean);

	var
		docRecHdl: DocRecHandle;
		editRecHdl: TEHandle;

begin
	docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
	editRecHdl := docRecHdl^^.editRecHdl;

	if (becomingActive) then
		begin
			SetPort(myWindowPtr);

			editRecHdl^^.viewRect.bottom := (((editRecHdl^^.viewRect.bottom - editRecHdl^^.viewRect.top) div editRecHdl^^.lineHeight) * editRecHdl^^.lineHeight) + editRecHdl^^.viewRect.top;

			editRecHdl^^.destRect.bottom := editRecHdl^^.viewRect.bottom;

			TEActivate(editRecHdl);
			HiliteControl(docRecHdl^^.vScrollbarHdl, 0);
			DoAdjustScrollbar(myWindowPtr);
		end

	else
		begin
			TEDeactivate(editRecHdl);
			HiliteControl(docRecHdl^^.vScrollbarHdl, 255);
		end;
end;
	{of procedure DoActivateDocWindow}

  procedure DoActivate(var theEvent: EventRecord);

  var
    myWindowPtr: WindowPtr;
    becomingActive: boolean;

  begin
    myWindowPtr := WindowPtr(theEvent.message);
    if (BAnd(theEvent.modifiers, activeFlag) = activeFlag) then
      becomingActive := True
    else
      becomingActive := False;
    DoActivateDocWindow(myWindowPtr, becomingActive);
  end;


end.

