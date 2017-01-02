unit HIViewUtils;

interface

	uses MacOSAll;
	
	function HIViewSetNeedsDisplayOnSubviews(parentView: HIViewRef):OSStatus;
	function HIViewSetNeedsDisplayOnSubviewsEventHandler(inCallRef: EventHandlerCallRef; theEvent: EventRef; userData: Pointer):OSStatus;MWPascal;


implementation

	function HIViewSetNeedsDisplayOnSubviews(parentView: HIViewRef):OSStatus;
		var
			status: OSStatus = noerr;
			subView: HIViewRef;
		begin
			 subView := HIViewGetFirstSubview(parentView);
			 while(subView <> nil) do
				begin
					HIViewSetNeedsDisplay(subView, true);
					subView := HIViewGetNextView(subView);
				end;
			 HIViewSetNeedsDisplayOnSubviews := status;
		end;

	function HIViewSetNeedsDisplayOnSubviewsEventHandler(inCallRef: EventHandlerCallRef; theEvent: EventRef; userData: Pointer):OSStatus;MWPascal;
		VAR
			status: OSStatus = eventNotHandledErr;
			theView: HIViewRef;
			
		begin
			status := GetEventParameter (theEvent, kEventParamControlRef, typeControlRef, nil, sizeof (ControlRef), nil, @theView);

			case GetEventClass(theEvent) of
				kEventClassControl:
					case GetEventKind(theEvent) of
						kEventControlDraw: HIViewSetNeedsDisplayOnSubviews(theView);
					end;
			end;
			HIViewSetNeedsDisplayOnSubviewsEventHandler := status;
		end;

end.