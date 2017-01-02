unit unit_doupdate;

{$mode macpas}{$H+}

interface
  {$IFC UNDEFINED THINK_Pascal}
  uses Events;
  {$ENDC}
 	procedure DoUpdate (var theEvent: EventRecord);

implementation
  uses
    {$IFC UNDEFINED THINK_Pascal}
   Quickdraw, TextEdit, Controls, Windows,
  {$ENDC}
    unit_docrec, unit_dodrawdatapanel;
 	procedure DoUpdate (var theEvent: EventRecord);

		var
			myWindowPtr: WindowPtr;
			docRecHdl: DocRecHandle;
			editRecHdl: TEHandle;
			oldPort: GrafPtr;

	begin
		myWindowPtr := WindowPtr(theEvent.message);
		docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
		editRecHdl := docRecHdl^^.editRecHdl;

		GetPort(oldPort);
		SetPort(myWindowPtr);

		BeginUpdate(WindowPtr(theEvent.message));

		EraseRect(myWindowPtr^.portRect);
		TEUpdate(myWindowPtr^.portRect, editRecHdl);
    DrawGrowIcon(myWindowPtr);
		UpdateControls(myWindowPtr, myWindowPtr^.visRgn);

		DoDrawDataPanel(myWindowPtr);

		EndUpdate(WindowPtr(theEvent.message));

		SetPort(oldPort);
	end;
		{of procedure DoUpdate}

end.

