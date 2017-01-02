unit HIBoxEventHandlerDef;

interface

	uses
		EvolveDefs,
		GeneboxEventHandlerDef,
		HIBoxControlDrawDef,
		HIBoxSlideDef,
		HIBoxTypes,
		MacOSAll;

	{function HIBoxEventHandler(inCallRef: EventHandlerCallRef; theEvent: EventRef; userData: Pointer):OSStatus;MWPascal;}
		function HIBoxInstallViewEventHandlers(theBox: HIBoxPtr): OSStatus;

	
implementation
		
	function HIBoxEventHandler(inCallRef: EventHandlerCallRef; theEvent: EventRef; userData: Pointer):OSStatus;MWPascal;
		VAR
			theEventPartCode: HIViewPartCode; {kControlIndicatorPart;}
			status: OSStatus = noerr;
			theEventMousePoint: HIPoint;
			theBox: HIBoxPtr;
			viewContext: CGContextRef;
			theView: HIViewRef;
		begin
			theBox := HIBoxPtr(userData);
			GetEventParameter (theEvent, kEventParamWindowMouseLocation, typeHIPoint, nil, sizeof (HIPoint), nil, @theEventMousePoint);
			GetEventParameter (theEvent, kEventParamControlPart, typeControlPartCode, nil, sizeof (HIViewPartCode), nil, @theEventPartCode);
			GetEventParameter (theEvent, kEventParamControlRef, typeControlRef, nil, sizeof (ControlRef), nil, @theView);
			GetEventParameter (theEvent, kEventParamCGContextRef, typeCGContextRef, nil, sizeof (CGContextRef), nil, @viewContext);
			case GetEventClass(theEvent) of
				kEventClassHIBox:
					case GetEventKind(theEvent) of
						kEventHIBoxMouseOver:
							begin
								if theBox <> OldBox then
								begin
									OldBox := theBox;
									ShowChangedGene(theBox^.denizen, theBox^.parent^.parent^.geneBoxes);
								end;
								status := noerr;
							end;
					end;
				kEventClassControl:
					case GetEventKind(theEvent) of
						 
						kEventControlHitTest: {Indicate that we do take mouse clicks}
							begin
								theEventPartCode := 1; {Any nonzero value}
								SetEventParameter (theEvent, kEventParamControlPart, typeControlPartCode, sizeof(HIViewPartCode), @theEventPartCode);
								status := noerr;
							end;
						kEventControlDraw: HIBoxControlDraw(theBox, viewContext);		
						kEventControlTrack: {clicked}
							begin
								{Change this to inject an "Evolve" instruction into the animation queue}
								{And start the animation timer.}
								evolve(theBox);
								HIViewSetNeedsDisplay(theView, true);
								status := NOERR;
							end;
					end;
			end;
			HIBoxEventHandler := status;
		end;

		function HIBoxInstallViewEventHandlers(theBox: HIBoxPtr): OSStatus;
			const
				eventsOfInterest: array [0..3] of EventTypeSpec = ( 
					(eventClass: kEventClassControl; eventKind: kEventControlDraw),
					{(eventClass: kEventClassMouse; eventKind: kEventMouseMoved),}
					(eventClass: kEventClassControl; eventKind: kEventControlTrack),
					(eventClass: kEventClassControl; eventKind: kEventControlHitTest),
					(eventClass: kEventClassHIBox; eventKind: kEventHIBoxMouseOver)
				);
			var status: OSStatus = noerr;
			begin
				status := InstallEventHandler(GetControlEventTarget(theBox^.view), 
					HIBoxEventHandler, 
					4, 
					@eventsOfInterest, theBox, nil);
				HIBoxInstallViewEventHandlers := status;
			end;

			
end.