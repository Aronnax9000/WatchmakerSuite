{$LONGSTRINGS ON}
unit GeneboxDefs;

interface
	uses 
		GeneboxEventHandlerDef,
		Globals, 
		HIBoxTypes, 
		GeneboxTypes,
		GeneboxButtonDefs,
		HIBoxDefs, 
		HIViewUtils,
		MacOSAll, 
		PersonDefs, 
		RectDrawDefs;



	var
		GeneBox: ARRAY[1..17] OF HIRect;


	function MakeGeneBoxes (breedModel: HIBreedModelPtr): OSStatus;
	procedure InitializeGeneboxStrings;

implementation


	procedure InitializeGeneboxStrings;
		var tmpStr: Str255;
		begin
			GetIndString(tmpStr, 12947, 1);
			AsymString := tmpStr;
			GetIndString(tmpStr, 12947, 2);
			BilatString := tmpStr;
			GetIndString(tmpStr, 12947, 3);
			SingleString := tmpStr;
			GetIndString(tmpStr, 12947, 4);
			UpDnString := tmpStr;
			GetIndString(tmpStr, 12947, 5);
			RadialString := tmpStr;
		end;

	function CreateGeneBoxesView(geneBoxes: HIGeneBoxesPtr; geneBoxViewBounds: HIRect): OSStatus;
		const
			controlDrawEventTypeSpec: EventTypeSpec = 
				(eventClass: kEventClassControl; eventKind: kEventControlDraw);

		var 
			status: OSStatus = noerr;
		begin
			HIImageViewCreate(nil, geneBoxes^.view);
			HIViewChangeFeatures(  geneBoxes^.view, kHIViewAllowsSubviews, 0 );
			status := HIViewAddSubview(geneBoxes^.parent^.view, geneBoxes^.view);
			HIViewSetFrame(geneBoxes^.view, geneBoxViewBounds);
			HIViewSetVisible(geneBoxes^.view, true);
			InstallEventHandler(GetControlEventTarget(geneBoxes^.view), 
				HIViewSetNeedsDisplayOnSubviewsEventHandler, 
				1, @controlDrawEventTypeSpec, nil, nil);
			
			CreateGeneBoxesView := status;
		end;
	
	function MakeGeneBoxes(breedModel: HIBreedModelPtr): OSStatus;
		VAR
			status: OSStatus = noerr;
			GeneBoxNo: Integer;
			bounds: HIRect;
			theGeneBox: HIGeneBoxPtr;
			thePreviousGeneBox: HIGeneBoxPtr = nil; {for creating linked list}
			theGeneBoxView: HIViewRef;
		begin

			new(breedModel^.geneBoxes);

			breedModel^.geneBoxes^.parent := breedModel;
			breedModel^.geneBoxes^.biomorph := breedModel^.Boxes^.MidBox^.denizen;
			
			HIViewGetBounds(breedModel^.view, bounds);
			bounds.size.height := GenesHeight;
			CreateGeneBoxesView(breedModel^.geneBoxes, bounds);
			
			
			bounds.size.width := bounds.size.width / 17;
			with breedModel^.geneBoxes^ do
				begin
					first := nil;
					FOR GeneBoxNo := 1 TO 17 DO
						begin
							new(theGeneBox);
							theGeneBox^.parent := breedModel^.geneBoxes;
							theGeneBox^.GeneBoxNo := GeneBoxNo;
							HIImageViewCreate(nil, theGeneBoxView);
							theGeneBox^.view := theGeneBoxView;
							HIViewChangeFeatures( theGeneBoxView, kHIViewAllowsSubviews, 0 );
							HIGeneBoxButtonCreateButtons(theGeneBox);
							HIGeneBoxButtonSetFrames(theGeneBox);
							HIViewAddSubview(view, theGeneBox^.view);
							HIViewSetFrame(theGeneBox^.view, bounds);
							HIViewSetVisible(theGeneBox^.view, true);
							bounds.origin.x := bounds.origin.x + bounds.size.width;

							if first = nil then
								first := theGeneBox;
							if thePreviousGeneBox <> nil then
								thePreviousGeneBox^.next := theGeneBox;
							thePreviousGeneBox := theGeneBox;
							InstallEventHandler(GetControlEventTarget(theGeneBoxView),
								GeneboxEventHandler,
								1,
								@eventControlDrawType, theGeneBox, nil);

						end;
				end;
			MakeGeneBoxes := status;			

		end; {GeneBoxTemplate}

end.