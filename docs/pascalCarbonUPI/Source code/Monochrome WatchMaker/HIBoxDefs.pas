{This file is part of Richard Dawkins' Watchmaker Suite}
{Author Alan Canon}
{Date: March 26, 2015}
{ HIGeometry-aware implementation of the box model used for the breeding window and album page view of }
{ Watchmaker. The two important inputs are the dimensions of both the box array itself (nrows x ncols),}
{ together with a pixel size specification, giving the width and height of each box in pixels. This value}
{ is used to initialize each of the individual boxes in the collection.}
{ }
{ Classic Watchmaker Suite used static arrays to hold boxes, which necessitated extra global state, namely }
{ holding an index to the last box in use. This is bummed out by the curent implementation, which uses a singly linked list.}
{ The use of pointers also effectively allows the number of boxes to grow without limit. }
{ }
{ The HIBoxes record collects together a number of box-related values which were separate global variables in }
{ classic Watchmaker. This allows the values to be tightly coupled to an individual view, so that different }
{ views can be sliced up in different, independent ways. }
{ }
{ The whole purpose of the HIBoxes structure is to create and organize a collection of rectangular boxes in a two-dimensional }
{ coordinate plane. 

{ The HIBox data structure is made so that it can be a member of a singly-linked list, optionally holding a reference to }
{ its next sibling. It holds two pieces of geometric information about the box: its bounding rectangle (in pixels) }
{ and its midpoint. In Classic Watchmaker, the bounding rectangles and midpoints were maintained in separate static arrays. }
{ }
{ Routines are provided for both HIBoxes creation and disposal: since the boxes are linked together with pointers, they }
{ must be carefully deallocated when no longer needed. The DisposeHIBoxes function does this, by walking the list of HIBox }
{ instances, checking for a next sibling before offing the one in its hands. }
{ }
{ The choice of HIGeometry anticipates the Quartz 2D drawing paradigm, and the reitrement of QuickDraw from }
{ Watchmaker as the application reaches towards Core Graphics and Cocoa. }

unit HIBoxDefs;
interface
	uses 
		AnimationDefs,
		AnimationTypes,
		MacOSAll, 
		PersonDefs, 
		Globals, 
		BarnYardDefs, 
		HIBoxTypes, 
		HIBoxEventHandlerDef, 
		HIBoxesEventHandlerDef, 
		PicDefs;

		


	function HIBoxesCreateViews(theBoxes: HIBoxesPtr): OSStatus;

	procedure HIBoxesCalculateSizes(theBoxes: HIBoxesPtr; theRect: HIRect);
	procedure HIBoxesBuildBoxes(breedModel: HIBreedModelPtr);

implementation



	procedure DisposeHIBoxes(var boxes: HIBoxesPtr);
		var tempBox: HIBoxPtr;
		begin
			if boxes^.first <> nil then
			repeat
				tempBox := boxes^.first^.next;
				Dispose(boxes^.first);
				boxes^.first := tempBox;
				
			until tempBox = nil;
		end;	
	


	function SparkEventHandler(inCallRef: EventHandlerCallRef; inEvent: EventRef; userData: Pointer):OSStatus;MWPascal;
		var
			status: OSStatus = noerr;
			boxIndex: HIBoxPtr;
			viewContext: CGContextRef;
			kind: eventKind;
			midboxFrame: HIRect;
			liveFrame: HIRect;
			boxes: HIBoxesPtr;
		begin
			boxes := HIBoxesPtr(userData);
			kind := GetEventKind(inEvent);
			case kind of
				kEventControlDraw: 			
					begin
						GetEventParameter (inEvent, kEventParamCGContextRef, typeCGContextRef, nil, sizeof (CGContextRef), nil, @viewContext);
						boxIndex := boxes^.first;
						while (boxIndex <> nil) do
							begin
								HIViewGetFrame(boxes^.MidBox^.view, midboxFrame); 
								HIViewGetFrame(boxIndex^.view, liveFrame); 
								case boxIndex^.animationState of
									HIBoxStateDrawFrame:
										begin
											CGContextMoveToPoint(viewContext, 
												boxes^.MidBox^.bounds.origin.x + boxes^.MidBox^.bounds.size.width / 2, 
												boxes^.MidBox^.bounds.origin.y + boxes^.MidBox^.bounds.size.height / 2);
											CGContextAddLineToPoint(viewContext, 
												boxIndex^.bounds.origin.x + boxIndex^.bounds.size.width / 2, 
												boxIndex^.bounds.origin.y + boxIndex^.bounds.size.height / 2);
											CGContextStrokePath(viewContext);
											break
										end;
									HIBoxStateEraseFrame:
										begin
											CGContextMoveToPoint(viewContext, 
												boxes^.MidBox^.bounds.origin.x + boxes^.MidBox^.bounds.size.width / 2, 
												boxes^.MidBox^.bounds.origin.y + boxes^.MidBox^.bounds.size.height / 2);
											CGContextAddLineToPoint(viewContext, 
												boxIndex^.bounds.origin.x + boxIndex^.bounds.size.width / 2, 
												boxIndex^.bounds.origin.y + boxIndex^.bounds.size.height / 2);
											CGContextStrokePath(viewContext);
											break
										end;
									
									{FIXME}{HIBoxStateEvolve:
										begin
											break
										end;}
									otherwise
										boxIndex := boxIndex^.next;
								end;
							end;
					end;
			end;
			SparkEventHandler := status;
		end;
		
		
		procedure HIBoxesBuildBoxes(breedModel: HIBreedModelPtr);
		var
			row: shortint;
			col: shortint;
			midboxIndex: shortint;	
			prevBox: HIBoxPtr;
			newBox: HIBoxPtr;
			theBoxes: HIBoxesPtr;

		begin
			New(breedModel^.boxes);
			theBoxes := breedModel^.boxes;
			theBoxes^.parent := breedModel;
			theBoxes^.NRows := NRows;
			theBoxes^.NCols := NCols;
			midboxIndex := (NRows * NCols) div 2 + 1;

			prevBox :=nil;
			theBoxes^.NBoxes := 0;
			theBoxes^.first := nil;
			FOR row := 1 TO theBoxes^.nrows DO
				FOR col:= 1 TO theBoxes^.ncols DO
					begin
						New(newBox);
						newBox^.parent := theBoxes;
						newbox^.row := row;
						newBox^.col := col;
						if theBoxes^.first = nil then
							theBoxes^.first := newBox;
						newBox^.next := nil; 
						PersonCreate(newBox^.denizen);
						newBox^.animationState := HIBoxStateIdle;
						newBox^.highlighted := false;
						MorphPicCreate(newBox^.MyPic);
		
						{Make new box the "next" of previous box, if any}
						if prevBox <> nil then
							begin
								prevBox^.next := newBox;
								{Keep track of newBox for next time, to add any}
								{sibling just as above}
							end;
						prevBox := newBox;
						theBoxes^.NBoxes := theBoxes^.NBoxes + 1;
						if theBoxes^.NBoxes = midboxIndex then
							theBoxes^.MidBox := newBox
					end; {row & column loop}
			theBoxes^.LastBox := newBox
		end;
		

		
		procedure HIBoxesCalculateSizes(theBoxes: HIBoxesPtr; theRect: HIRect);
			var 
				newBox: HIBoxPtr;
			begin
				 newBox := nil;
			
				theBoxes^.BusinessPart := theRect;
				theBoxes^.BoxSize.width := theBoxes^.BusinessPart.Size.width / theBoxes^.ncols;
				theBoxes^.BoxSize.height := theBoxes^.BusinessPart.Size.Height / theBoxes^.nrows;
				newBox := theBoxes^.first;
				
				while(newBox <> nil) do
				begin
					newBox^.bounds.size := theBoxes^.BoxSize;
								
					newBox^.bounds.origin.x := theBoxes^.BoxSize.width * (newBox^.col - 1);
					newBox^.bounds.origin.y := theBoxes^.BoxSize.height * (newBox^.row - 1);
					newBox^.bounds.size.width := newBox^.bounds.size.width;
					newBox^.bounds.size.height := newBox^.bounds.size.height;
					newBox^.slidingDest := newBox^.bounds;
					
					{Local midpoint}
					newBox^.MidPoint.x := theBoxes^.BoxSize.width / 2;
					newBox^.MidPoint.y := theBoxes^.BoxSize.height / 2;
					newBox := newBox^.next
				end
		end;	

		function HIBoxesCreateViews(theBoxes: HIBoxesPtr): OSStatus;
			var 
				status: OSStatus = noerr;
				insetToDodgeBoxes: CGFloat = 1.5;
				newBox: HIBoxPtr;
			begin
				HIViewGetBounds(theBoxes^.view, theBoxes^.BusinessPart);
				theBoxes^.BoxSize.width := theBoxes^.BusinessPart.Size.width / theBoxes^.ncols - 2 * insetToDodgeBoxes;
				theBoxes^.BoxSize.height := theBoxes^.BusinessPart.Size.Height / theBoxes^.nrows - 2 * insetToDodgeBoxes;
				newBox := theBoxes^.first;
				
				while(newBox <> nil) do
					begin
						HIImageViewCreate(nil, newBox^.view);
						status := HIViewAddSubview(theBoxes^.view, newBox^.view);
						case status of
							errControlIsNotEmbedder:
								begin
									status := noerr;
									
								end;
							errNeedsCompositedWindow:
								begin
								end;
							otherwise
								begin
									HIViewSetFrame(newBox^.view, newBox^.bounds);
									HIViewSetVisible(newBox^.view, true);
									
									{TESTME} HIViewChangeFeatures(newBox^.view, kControlHandlesTracking, 0);

									HIViewSetFrame(newBox^.view, newBox^.bounds);
								end;
						end;
						newBox := newBox^.next

					end;
				HIBoxesInstallViewEventHandlers(theBoxes);
				HIBoxesCreateViews := status;
					
			end;
		
end.
