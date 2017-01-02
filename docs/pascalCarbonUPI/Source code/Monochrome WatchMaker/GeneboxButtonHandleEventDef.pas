unit GeneboxButtonHandleEventDef;

interface

	uses
		CursorDefs,
		GeneboxManipulationDef,
		HIBoxTypes,
		MacOSAll; 
	
	function HIGeneBoxButtonHandleEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer):OSStatus;MWPascal;

implementation


	function HIGeneBoxButtonHandleEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer):OSStatus;MWPascal;
		var 
			status: OSStatus;
			theButton: HIGeneBoxButtonPtr;
			eventPartCode: HIViewPartCode; {kControlIndicatorPart;}
			eventMouseLocation: HIPoint;
		begin
			status := noerr;		
			GetEventParameter (theEvent, kEventParamWindowMouseLocation, typeHIPoint, nil, sizeof (HIPoint), nil, @eventMouseLocation);
			GetEventParameter (theEvent, kEventParamControlPart, typeControlPartCode, nil, sizeof (HIViewPartCode), nil, @eventPartCode);
			theButton := HIGeneBoxButtonPtr(userData);
			case GetEventClass(theEvent) of
				 kEventClassMouse:
					case GetEventKind(theEvent) of
						kEventMouseMoved:
							case theButton^.buttonType of
								GBButtonLeft:
									SetCursor(CursList[leftCursor]^^);
								GBButtonTopRung: 
									SetCursor(CursList[UpCursor]^^);
								GBButtonMidRung: 
									SetCursor(CursList[EqCursor]^^);
								GBButtonBotRung: 
									SetCursor(CursList[DownCursor]^^);
								GBButtonRight:
									SetCursor(CursList[rightCursor]^^);
							end
					end;
				kEventClassControl:
					case GetEventKind(theEvent) of
							
						kEventControlHitTest: {Return nonzero part code to indicate we do trap mouse clicks}
							begin
								eventPartCode := 1;
								SetEventParameter (theEvent, kEventParamControlPart, typeControlPartCode, sizeof(HIViewPartCode), @eventPartCode);
								status := noerr;
							end;
						kEventControlDraw:; {FIXME}
						kEventControlTrack:
							Manipulation(theButton);
						otherwise
					end;
			end;
			HIGeneBoxButtonHandleEvent := status;
		end;
end.