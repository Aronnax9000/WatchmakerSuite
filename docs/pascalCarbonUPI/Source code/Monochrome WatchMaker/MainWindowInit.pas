unit MainWindowInit;

interface

uses
	AlbumDefs,
	AlbumUtil,
	InitializeAnimalsDefs,
	InitialModeDef,
	CursorAdjustDefs,
	BoxesDefs,
	GeneboxEventHandlerDef,
	HIBoxDefs,
	HIBoxTypes,
	HIViewUtils,
	DevelopDefs,
	EvolveDefs,
	GeneBoxDefs,
	GeneBoxTypes,
	Globals,
	HIBreedModelDefs,
	MacOSAll, 
	MainWindowDefs, 
	MenuGreyAdjustDefs, 
	ModeDefs, 
	WatchmakerCommands;

	function InitializeMainWindow(breedModel: HIBreedModelPtr): OSStatus;


implementation

	function HandleMouseMovedEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var
			status: OSStatus;
		begin
			status := noErr;
			DoMouseMoved(theEvent, MainWindow);
			HandleMouseMovedEvent := status;
		end;





	function HandleMainWindowEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var
			status: OSStatus = noerr;
			theWindow: WindowRef;
			theBoundsRect: Rect;
			boxIndex: HIBoxPtr;
			theViewMousedOver: HIViewRef;
			theNewMouseOverEvent: EventRef;
			theBounds: HIRect;
		begin
			
			theWindow := WindowRef(userData^);
			case GetEventClass(theEvent) of 
				kEventClassMouse:
					case GetEventKind(theEvent) of
						kEventMouseMoved:
							begin
								HIViewGetViewForMouseEvent(HIViewGetRoot(theWindow), theEvent, theViewMousedOver);
								HIViewGetFrame(theViewMousedOver, theBounds);
								status := CreateEvent(nil, kEventClassHIBox, kEventHIBoxMouseOver, GetEventTime(theEvent),
									kEventAttributeUserEvent, theNewMouseOverEvent);
								status := SendEventToEventTarget(theNewMouseOverEvent, GetControlEventTarget(theViewMousedOver));
							end;
					end;
				kEventClassWindow:
					case GetEventKind(theEvent) of
						kEventWindowClose:
							begin
								status := eventNotHandledErr;
								{MainWindow := nil;}
							end;
						kEventWindowResizeCompleted: 
							begin
								status := noErr;
								GetWindowPortBounds(theWindow, theBoundsRect);
								PRect := theBoundsRect; {Safe to delete when PRect is gone from Globals - ABC}
								{SetupBoxes(userData);}
								InitializeQuadrants(theWindow);
								EraseRect(theBoundsRect);
								case TheMode of
									Albuming: 
										begin
											if zoomed then
												zoom 
											else
												ShowAlbumPage(OldQuadrant);

										end;
									Engineering: WatchCmdEngineering;
									
									Breeding:
										begin
											DrawBoxes(true);
											{FIXME MakeGeneBox (boxes^.midbox^.denizen, userData);}
											(*boxIndex := boxes^.first;
											while boxIndex <> nil do
											begin
												{delayvelop(boxIndex, boxIndex^.MidPoint);}
												boxIndex := boxIndex^.next;
											end; *)
										end;
									Highlighting:
										begin
										
										end;
								end;

							end;
					end;
			end;
			HandleMainWindowEvent := status;
		end;
	

	function InitializeMainWindow(breedModel: HIBreedModelPtr): OSStatus;
		const
			inAttributes:  array[1..5] of SInt32 = (
				kHIWindowBitCloseBox,
				kHIWindowBitZoomBox,
				kHIWindowBitCollapseBox,
				kHIWindowBitResizable,
				kHIWindowBitCompositing
			);
			controlDrawEventTypeSpec: EventTypeSpec = 
				(eventClass: kEventClassControl; eventKind: kEventControlDraw);

			
			eventsOfInterest: array [0..2] of EventTypeSpec = ( 
				(eventClass: kEventClassMouse; eventKind: kEventMouseMoved),
				(eventClass: kEventClassWindow; eventKind: kEventWindowClose),
				(eventClass: kEventClassWindow; eventKind: kEventWindowResizeCompleted)
			);
		var
			status: OSStatus = noerr;
			mainID: CGDirectDisplayID;
			mainDevice: GDHandle;
			mainWindowBounds: HIRect;
			viewBounds: HIRect;
			boxIndex: HIBoxPtr;
		begin
			if MainWindow = nil then
				begin
					mainID := CGMainDisplayID;
					status := DMGetGDeviceByDisplayID(mainID, mainDevice, true);
					HIWindowGetAvailablePositioningBounds(
						mainID, 
						kHICoordSpaceScreenPixel, 
						mainWindowBounds);
					mainWindowBounds.origin.y := mainWindowBounds.origin.y + GetMBarHeight;
					mainWindowBounds.size.height := mainWindowBounds.size.height - GetMBarHeight;
					status := HIWindowCreate (
						kDocumentWindowClass,
						@inAttributes,
						nil,
						kHICoordSpaceScreenPixel,
						mainWindowBounds,
						MainWindow
					);
				 	InstallStandardEventHandler(GetWindowEventTarget(MainWindow));
					InstallEventHandler(GetWindowEventTarget(MainWindow), HandleMainWindowEvent, 3, @eventsOfInterest, @MainWindow, nil);
					

					{Get contentview}
					HIViewFindByID( HIViewGetRoot(MainWindow), kHIViewWindowContentID, breedModel^.view );
					InstallEventHandler(GetControlEventTarget(breedModel^.view), 
						HIViewSetNeedsDisplayOnSubviewsEventHandler, 
						1, @controlDrawEventTypeSpec, nil, nil);
					
					
					{Create boxes}
					HIBoxesBuildBoxes(breedModel);

					{Boxes View}
					HIImageViewCreate(nil, breedModel^.boxes^.view);
					HIViewChangeFeatures( breedModel^.boxes^.view, kHIViewAllowsSubviews, 0 );
					{Add boxes view to content view}
					HIViewAddSubview(BreedModel^.view, breedModel^.boxes^.view);

					HIViewGetBounds(BreedModel^.view, viewBounds);
					viewBounds.origin.y := viewBounds.origin.y + GenesHeight;
					viewBounds.size.height := viewBounds.size.height - GenesHeight;
				
					HIViewSetFrame(breedModel^.boxes^.view, viewBounds);
					HIViewSetVisible(breedModel^.boxes^.view, true);
					HIViewGetBounds(breedModel^.boxes^.view, viewBounds);
					
					HIBoxesCalculateSizes(breedModel^.boxes, viewBounds);
					status := HIBoxesCreateViews(breedModel^.boxes);
					HIViewGetBounds(breedModel^.boxes^.view, viewBounds);
					

					InitializeAnimals(breedModel^.boxes);
					{DoBreed(breedModel^.boxes);}
					
					{Gene Box view}
					MakeGeneBoxes(breedModel);
					Delayvelop(breedModel^.boxes^.midbox, breedModel^.boxes^.midbox^.midpoint);
					
					(*
					boxIndex := breedModel^.boxes^.first;
					
					while boxIndex <> nil Do 
						if boxIndex <> breedModel^.boxes^.midbox then
							begin
								boxIndex := boxIndex^.next;
							end;
					*)
					HIViewSetNeedsDisplay(breedModel^.view, true);


				end;
				ShowWindow(MainWindow);
				SetPortWindowPort(MainWindow);                 					{ set window to current graf port   }
				SelectWindow(MainWindow);           					{ and make window active            }
				frontw := MainWindow;       						{ remember that it's in front       }
				{HIWindowFlush(MainWindow);}
				InitializeMainWindow := status;
		end;

end.