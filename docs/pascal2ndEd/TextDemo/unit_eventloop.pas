unit unit_eventloop;
interface
{$IFC UNDEFINED THINK_Pascal}
{$ENDC}

procedure EventLoop;

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
Events, Quickdraw, Windows,
{$ENDC}
unit_globals, unit_doevents, unit_doadjustcursor, unit_doidle;
{ ×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××× EventLoop }

	procedure EventLoop;

		var
			theEvent: EventRecord;
			gotEvent: boolean;
			sleepTime: longint;

	begin
		gDone := false;
		gCursorRegion := NewRgn;
		sleepTime := GetCaretTime;

		while not (gDone) do
			begin
				gotEvent := WaitNextEvent(everyEvent, theEvent, sleepTime, gCursorRegion);

				if ((not gInBackground) and (gNumberOfWindows > 0)) then
					DoAdjustCursor(FrontWindow, gCursorRegion);

				if (gotEvent) then
					DoEvents(theEvent)
				else
					begin
						if (gNumberOfWindows > 0) then
							DoIdle;
					end;
			end;
	end;
		{of procedure EventLoop}

end.


