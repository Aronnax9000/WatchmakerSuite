unit unit_doevents;


interface
uses
{$IFC UNDEFINED THINK_Pascal}
Events,
{$ENDC}
unit_dokeyevent;

procedure DoEvents (var theEvent: EventRecord);

implementation


uses
{$IFC UNDEFINED THINK_Pascal}
Menus,
{$ENDC}
 unit_doupdate, unit_docwindow_activate, unit_domousedown,
unit_doadjustmenus, unit_domenuchoice, unit_doopsysevent;

procedure DoEvents (var theEvent: EventRecord);

	var
		charCode: char;

begin
	case (theEvent.what) of

		mouseDown:
			begin
				DoMouseDown(theEvent);
			end;

		keyDown, autoKey:
			begin
				charCode := chr(BAnd(theEvent.message, charCodeMask));
				if (BAnd(theEvent.modifiers, cmdKey) <> 0) then
					begin
						DoAdjustMenus;
						DoMenuChoice(MenuKey(charCode));
					end
				else
					DoKeyEvent(charCode);
			end;

		updateEvt:
			begin
				DoUpdate(theEvent);
			end;

		activateEvt:
			begin
				DoActivate(theEvent);
			end;

		osEvt:
			begin
				DoOpSysEvent(theEvent);
			end;

	end;
		{of case statement}

end;
	{of procedure DoEvents}
end.

