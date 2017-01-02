unit HIBoxesEventHandlerDef;

interface

	uses
		HIBoxEventHandlerDef,
		HIBoxTypes,
		HIViewUtils, 
		MacOSAll;

	{function HIBoxesEventHandler(inCallRef: EventHandlerCallRef; inEvent: EventRef; userData: Pointer):OSStatus;MWPascal;}
	function HIBoxesInstallViewEventHandlers(theBoxes: HIBoxesPtr): OSStatus;
	
implementation

	function HIBoxesEventHandler(inCallRef: EventHandlerCallRef; inEvent: EventRef; userData: Pointer):OSStatus;MWPascal;
		VAR
			status: OSStatus = eventNotHandledErr;
			theBox: HIBoxPtr;
			viewContext: CGContextRef;
			boxFrameRect: HIRect;
			theBoxes: HIBoxesPtr;
		begin
			theBoxes := HIBoxesPtr(userData);
			status := GetEventParameter (inEvent, kEventParamCGContextRef, typeCGContextRef, nil, sizeof (CGContextRef), nil, @viewContext);
			case GetEventClass(inEvent) of
				kEventClassMouse:;
				kEventClassControl:
				case GetEventKind(inEvent) of
				
					kEventControlDraw: 			
						begin
							theBox := theBoxes^.first;
							CGContextSetStrokeColorWithColor(viewContext, CGColorGetConstantColor(kCGColorBlack));
							repeat
								boxFrameRect := theBox^.bounds;
								if (theBox = theboxes^.MidBox) then
									CGContextStrokeRectWithWidth(viewContext, boxFrameRect, 3)
								else
									CGContextStrokeRectWithWidth(viewContext, boxFrameRect, 1);
								theBox := theBox^.next;
							until theBox = nil;
							HIViewSetNeedsDisplayOnSubviews(theBoxes^.view)
						end;
				end;
			end;
			HIBoxesEventHandler := status;
		end;
			
		function HIBoxesInstallViewEventHandlers(theBoxes: HIBoxesPtr): OSStatus;
			const
				eventsOfInterest: array [0..3] of EventTypeSpec = ( 
					(eventClass: kEventClassControl; eventKind: kEventControlDraw),
					{(eventClass: kEventClassMouse; eventKind: kEventMouseMoved),}
					(eventClass: kEventClassControl; eventKind: kEventControlTrack),
					(eventClass: kEventClassControl; eventKind: kEventControlHitTest),
					(eventClass: kEventClassHIBox; eventKind: kEventHIBoxMouseOver)
				);
			var
				status: OSStatus;
				theBox: HIBoxPtr;
			begin

				status := InstallEventHandler(GetControlEventTarget(theBoxes^.view), 
					HIBoxesEventHandler, 
					1, {Just grab ControlDraw event}
					@eventsOfInterest, theBoxes, nil);
				theBox := theBoxes^.first;
				
				while(theBox <> nil) do
					begin
						HIBoxInstallViewEventHandlers(theBox);
						theBox := theBox^.next
					end;
				HIBoxesInstallViewEventHandlers := status;
			
			end;

end.