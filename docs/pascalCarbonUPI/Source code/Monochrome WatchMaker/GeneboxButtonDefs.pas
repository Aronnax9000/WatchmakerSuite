unit GeneboxButtonDefs;

interface
	uses MacOSAll, HIBoxTypes, Globals, GeneboxManipulationDef, GeneboxButtonHandleEventDef, PersonDefs;
	procedure HIGeneBoxButtonSetFrames(box: HIGeneBoxPtr);
	procedure HIGeneBoxButtonCreateButtons(box: HIGeneBoxPtr);

implementation

	{Lay out the five gene box buttons (left, right, top, mid, bot) in the genebox}
	procedure HIGeneBoxButtonSetFrames(box: HIGeneBoxPtr);
		var newBounds: HIRect;
		begin
			HIViewGetBounds(box^.view, newBounds);
			newBounds.size.width := newBounds.size.width / 3;
			HIViewSetFrame(box^.leftButton^.view, newBounds);
			newBounds.origin.x := newBounds.origin.x + newBounds.size.width * 2;
			HIViewSetFrame(box^.rightButton^.view, newBounds);
			newBounds.origin.x := newBounds.origin.x - newBounds.size.width;
			if box^.hasRungs then
				begin
					newBounds.size.height := newBounds.size.height / 3;
					HIViewSetFrame(box^.topRungButton^.view, newBounds);
					newBounds.origin.y := newBounds.size.height;
					HIViewSetFrame(box^.midRungButton^.view, newBounds);
					newBounds.origin.y := newBounds.origin.y + newbounds.size.height;
					HIViewSetFrame(box^.botRungButton^.view, newBounds)
				end
			else
				begin
					HIViewSetFrame(box^.midRungButton^.view, newBounds);
				end;
		end;


		

	procedure HIGeneBoxButtonCreateButtons(box: HIGeneBoxPtr);

		procedure HIGeneBoxCreateBoxButtonByType(buttonType: HIGeneBoxButtonType);
			const
				eventsOfInterest: array [0..2] of EventTypeSpec = ( 
					(eventClass: kEventClassControl; eventKind: kEventControlTrack),
					(eventClass: kEventClassControl; eventKind: kEventControlDraw),
					(eventClass: kEventClassControl; eventKind: kEventControlHitTest)
				);

			var 
				newButton: HIGeneBoxButtonPtr;
				newView: HIViewRef;

			begin
				New(newButton);
				newButton^.parent := box;
				HIImageViewCreate(nil, newView);
				newButton^.buttonType := buttonType;
				HIViewAddSubview(box^.parent^.view, newView);
				
				case newButton^.buttonType of
					GBButtonLeft:
						box^.leftButton := newButton;
					GBButtonTopRung:
						box^.topRungButton := newButton;
					GBButtonMidRung:
						box^.midRungButton := newButton;
					GBButtonBotRung: 
						box^.botRungButton := newButton;
					GBButtonRight:
						box^.rightButton := newButton;
				end;
				InstallEventHandler(GetControlEventTarget(newView), HIGeneBoxButtonHandleEvent, 3, @eventsOfInterest, newButton, nil);
						
				{Install event handlers here}
			end;
		begin
			HIGeneBoxCreateBoxButtonByType(GBButtonLeft);
			HIGeneBoxCreateBoxButtonByType(GBButtonRight);
			HIGeneBoxCreateBoxButtonByType(GBButtonMidRung);
			
			if (box^.hasRungs) then
				begin
					HIGeneBoxCreateBoxButtonByType(GBButtonTopRung);
					HIGeneBoxCreateBoxButtonByType(GBButtonBotRung);
				end;
		end;



end.
