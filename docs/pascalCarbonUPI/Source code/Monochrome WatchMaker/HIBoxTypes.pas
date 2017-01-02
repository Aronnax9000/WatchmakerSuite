unit HIBoxTypes;

interface

	uses 
		AnimationTypes,
		MacOSAll, 
		PersonDefs, 
		PicDefs;

	const
		kEventClassHIBox = UInt32('DAWK');
		kEventHIBoxMouseOver = UInt32('MOOV');
		kEventHIBoxSpecialChanged = UInt32('SPCH');
		
		HIBoxMouseOverEventTypeSpec: EventTypeSpec = (eventClass: kEventClassHIBox; eventKind: kEventHIBoxMouseOver);	
		HIBoxSpecialChangedEventTypeSpec: EventTypeSpec = (eventClass: kEventClassHIBox; eventKind: kEventHIBoxSpecialChanged);	

	
	type
		{HIBox: the boxes of which biomorphs are denizens. Think of them as doghouses.}
		
		HIBoxPtr = ^HIBox;
		HIBoxesPtr = ^HIBoxes;

		HIBox = record
			parent: HIBoxesPtr; {Pointer to the containing HIBoxes }
			next: HIBoxPtr; {Pointer to the next sibling (singly linked) }
			bounds: HIRect; {The proper home bounds of this box (origin and size) }
				{relative to the enclosing view (frame) }
			midPoint: HIPoint; {The midpoint of this box}
			view: HIViewRef; { Image View with contents of this box }
			denizen: PersonPtr; {Biomorph which is the model for this view.}
			animationState: HIBoxAnimationState;
			slidingDest: HIRect; {If not nil, box is sliding towards this destination.}
			{If box is sliding, fraction of distance remaining to the destination.}
			{1.0 means it's just begun the slide.}
			{In Classic Watchmaker for Mac, slide worked only in the incoming-to-midbox direction.}
			{In the documentary The Blind Watchmaker (1987), the PC version of Colour Watchmaker}
			{featured sliding of newly reproduced biomorphs from the MidBox outwards to the}
			{destination box, along with growth (scaling).}

			slidingFraction: single;
			{Biomorphs "grow" over time, moving from the MidBox to their proper home.}
			{Maturity tracks their progress, from 0.0 (newborn) to 1.0 (mature.)}
			maturity: single;
			highlighted: boolean;
			row: integer;
			col: integer;
			MyPic: MorphPicPtr;
			Margin: HIRect;
			offcentre: HIPoint;
		end;
		
		HIBreedModelPtr = ^HIBreedModel;

		
		HIBoxes = record 
			parent: HIBreedModelPtr;
			NRows: shortint; {The number of rows, passed in (required) at initialization}
			NCols: shortint;  {The number of columns, passed in (required) at initialization}
			NBoxes: shortint; {Filled in by CreateBoxesDef based on the input rows * cols}
			First: HIBoxPtr; {Pointer to the first Box in the list}
			MidBox: HIBoxPtr; {Pointer to the middle box in the list, in 1-based order, equal to NBoxes div 2 + 1}
			LastBox: HIBoxPtr; {Guard to keep from iterating into BarnYard used for other purposes }
			BusinessPart: HIRect; { Overall bounding rectangle of the whole matrix of boxes, gleaned from the view when HIBoxes is initialized }
			BoxSize: HISize; { Prototype dimensions in pixels for each box (width, height) calculated by division of bounding rectangle }
			Special: HIBoxPtr; {The "special" box, out of the boxes in this set. This is best changed using a call to SetSpecial, which}
							{works according to these semantics:
							{1. If there is a Gene box set associated with the same BreedModel as the parent of the special, a call to "ShowChangedGene" should be made with this box's denizen,}
							{which will in turn dirty the views for the appropriate individual gene boxes (so that they will be redrawn.}
			OldBox: HIBoxPtr;				
			view: HIViewRef;
		end;
		
		{GeneBoxes: The user interface elements used to display and manipulate the values of individual genes.}
	
		HIGeneBoxButtonType = (GBButtonLeft, GBButtonTopRung, GBButtonMidRung, GBButtonBotRung, GBButtonRight);
	
		HIGeneBoxButtonPtr = ^HIGeneBoxButton;
		
		HIGeneBoxPtr = ^HIGeneBox;
		
		HIGeneBoxesPtr = ^HIGeneBoxes;
		
		HIGeneBoxButton = record
			buttonType: HIGeneBoxButtonType;
			parent: HIGeneBoxPtr;
			view: HIViewRef;
		end;
	
		HIGeneBox = record
			parent: HIGeneBoxesPtr;
			next: HIGeneBoxPtr;
			view: HIViewRef;
			GeneBoxNo: integer;
			hasRungs: boolean;
			leftButton: HIGeneBoxButtonPtr;
			rightButton: HIGeneBoxButtonPtr;
			midRungButton: HIGeneBoxButtonPtr;
			{Next two are optional (only if GeneBoxNo < 9 or = 11 - ABC}
			topRungButton: HIGeneBoxButtonPtr;
			botRungButton: HIGeneBoxButtonPtr;
		end;
			
		HIGeneBoxes = record
			parent: HIBreedModelPtr;
			biomorph: PersonPtr;
			view: HIViewRef; {geneboxView}
			first: HIGeneBoxPtr;
			geneBoxWidth: integer;
		end;

		HIBreedModel = record
			sparkline: HIPoint;
			view: HIViewRef;
			boxes: HIBoxesPtr;
			geneBoxes: HIGeneBoxesPtr;
		end;

		var
			special: HIBoxPtr;
			oldspecial: HIBoxPtr; 
			OldBox: HIBoxPtr;
			
		{BreedModel: An aggregation of a set of Boxes, a Special one of which is observed by a set of Gene boxes}

end.