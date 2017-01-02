{$LONGSTRINGS ON}

unit GeneboxEventHandlerDef;

interface

uses 
	MacOSAll, 
	GeneboxTypes, 
	PersonDefs, 
	HIBoxTypes;

	function GeneboxEventHandler(inCallRef: EventHandlerCallRef; theEvent: EventRef; userData: Pointer):OSStatus;MWPascal;
	procedure ShowChangedGene (an1: PersonPtr; theGeneBoxes: HIGeneBoxesPtr);

implementation

	procedure DrawInt (i: Integer; context: CGContextRef);
		var tempString: str255;
		ansiString: string;
	begin
		NumToString(i, tempString);
		if i > 0 then
			tempString := '+' + tempString;
		
		ansiString := tempString;
		CGContextShowText(context, PChar(ansiString), length(ansiString));
	end;


	function GeneboxControlDraw(theGeneBox: HIGeneBoxPtr; context: CGContextRef): OSStatus;
		var 
			status: OSStatus = noerr;
			bounds: HIRect;
			thestring: string;
			biomorph: PersonPtr;
			geneNumber: integer;
			numToStringTargetShortString: Str255;
			blobString: string;
			whereDrawn: HIPoint;
			trans: CGAffineTransform;
			strokeColor: CGColorRef;
		begin
			{Set up text stuff}
			trans := CGAffineTransformMakeScale(1, -1);
			CGContextSetTextMatrix(context, trans);
			CGContextSelectFont(context, 'Helvetica', 14, kCGEncodingMacRoman);
			CGContextSetTextDrawingMode(context, kCGTextFill);
			
			{Erase Rect}
			HIViewGetBounds(theGeneBox^.view, bounds);
			strokeColor := CGColorGetConstantColor(kCGColorWhite);
			CGContextSetFillColorWithColor(context, strokeColor);
			CGContextFillRect( context, bounds);
			
			{Draw outline}
			strokeColor := CGColorGetConstantColor(kCGColorBlack);
			CGContextSetStrokeColorWithColor(context, strokeColor);	
			CGContextSetFillColorWithColor(context, strokeColor);
			CGContextStrokeRectWithWidth(context, bounds, 1);
			
			blobString := '' + chr(165);
			biomorph := theGeneBox^.parent^.biomorph;
			geneNumber:= theGeneBox^.GeneBoxNo;
			WITH bounds DO
				begin
					CGContextSetTextPosition(context, bounds.size.width / 2 - 8, 14);
					CASE geneNumber OF
						1..9: 
							begin
								NumToString(biomorph^.gene[geneNumber], numToStringTargetShortString);
								theString := numToStringTargetShortString;
								CGContextSetTextPosition(context, (bounds.size.width - stringwidth(thestring)) / 2, 14);
								whereDrawn := CGContextGetTextPosition(context);
								CGContextShowText(context, PChar(thestring), length(thestring));
								{Per Richard Dawkins, March 30 2015: The Shrink/Same/Swell flag on Gene 9 is}
								{really a 17th Gene for Twig Thickness. I'm promoting it to its own genebox. - ABC}
								if geneNumber <> 9 then 
									CASE biomorph^.dGene[geneNumber] OF
										Shrink: 
											begin
												CGContextSetTextPosition(context, 2, 21);
												CGContextShowText(context, PChar(blobString), length(blobString));
											end;
										Swell: 
											begin
												CGContextSetTextPosition(context, 2, 7);
												CGContextShowText(context, PChar(blobString), length(blobString));
											end;
									end; {dGene cases}
							end; {1..9}
						10: 
							DrawInt(biomorph^.SegNoGene, context);
						11: 
							begin
								DrawInt(biomorph^.SegDistGene, context);
								CASE biomorph^.dGene[10] OF
									Shrink: 
										begin
											CGContextSetTextPosition(context, 2, 21);
											CGContextShowText(context, PChar(blobString), length(blobString));
										end;
									Swell: 
										begin
											CGContextSetTextPosition(context, 2, 7);
											CGContextShowText(context, PChar(blobString), length(blobString));
										end;
								end; {dGene cases}
							end;
						12: 
							begin
								CGContextSetTextPosition(context, 2, 14);
								CASE biomorph^.CompletenessGene OF
									CompletenessTypeSingle: {CGContextShowText(context, PChar(AsymString);}
										begin
											CGContextShowText(context, PChar(AsymString), length(AsymString)); 
										end;
									CompletenessTypeDouble: 
										CGContextShowText(context, PChar(BilatString), length(BilatString));
								end
							end;
						13: 
							begin
								CGContextSetTextPosition(context, 2, 14);
								CASE biomorph^.SpokesGene OF
									NorthOnly: 
										CGContextShowText(context, PChar(SingleString), length(SingleString));
									NSouth: 
										CGContextShowText(context, PChar(UpDnString), length(UpDnString));
									Radial: 
										CGContextShowText(context, PChar(RadialString), length(RadialString));
								end
							end;
						14: 
							DrawInt(biomorph^.tricklegene, context);
						15: 
							DrawInt(biomorph^.MutSizegene, context);
						16: 
							DrawInt(biomorph^.MutProbGene, context);
						17:
							begin
								CASE biomorph^.dGene[9] OF
									Shrink: 
										CGContextShowText(context, PChar('Shrink'), length(PChar('Shrink')));
									Same:
										CGContextShowText(context, PChar('Same'), length(PChar('Same')));
									Swell: 
										CGContextShowText(context, PChar('Swell'), length(PChar('Swell')));
								end; {dGene cases}
							end;
					end; {Gene Cases}
				end; {WITH GeneBox}
			GeneboxControlDraw := status;
		end; {ShowGeneBox}

	procedure ShowGeneBoxes(GeneBoxNo: integer; theGeneBoxes: HIGeneBoxesPtr);
		var
			k: integer = 0;
			geneBox: HIGeneBoxPtr;
		begin
			geneBox := theGeneBoxes^.first;
			while(geneBox <> nil) do
				begin
					k := k + 1;
					if geneBox^.GeneBoxNo = GeneBoxNo then
						begin
							HIViewSetNeedsDisplay(geneBox^.view, true);
							exit;
						end;
					geneBox := geneBox^.next
				end;
			
		end;

	procedure ShowChangedGene (an1: PersonPtr; theGeneBoxes: HIGeneBoxesPtr);
		VAR
			k: Integer;
			an2: PersonPtr;
		begin
			an2 := theGeneBoxes^.biomorph;
			theGeneBoxes^.biomorph := an1;
			IF OldBox <> nil THEN
				begin
					FOR k := 1 TO 9 DO
						IF (an1^.gene[k] <> an2^.gene[k]) OR ((an1^.dgene[k] <> an2^.dgene[k]) and (k <> 9)) THEN
							ShowGeneBoxes(k, theGeneBoxes);
							
					IF (an1^.dgene[10] <> an2^.dgene[10]) THEN
						ShowGeneBoxes(k, theGeneBoxes);
					IF an1^.SegNoGene <> an2^.SegNoGene THEN
						ShowGeneBoxes(10, theGeneBoxes);
					IF (an1^.SegDistGene <> an2^.SegDistGene) OR (an1^.dgene[10] <> an2^.dgene[10]) THEN
						ShowGeneBoxes(11, theGeneBoxes);
					IF an1^.CompletenessGene <> an2^.CompletenessGene THEN
						ShowGeneBoxes(12, theGeneBoxes);
					IF an1^.SpokesGene <> an2^.SpokesGene THEN
						ShowGeneBoxes(13, theGeneBoxes);
					IF an1^.TrickleGene <> an2^.TrickleGene THEN
						ShowGeneBoxes(14, theGeneBoxes);
					IF an1^.MutSizeGene <> an2^.MutSizeGene THEN
						ShowGeneBoxes(15, theGeneBoxes);
					IF an1^.MutProbGene <> an2^.MutProbGene THEN
						ShowGeneBoxes(16, theGeneBoxes);
					IF an1^.dgene[9] <> an2^.dgene[9] THEN
						ShowGeneBoxes(17, theGeneBoxes);
				end
		end; {ShowChangedGene}





	function GeneboxEventHandler(inCallRef: EventHandlerCallRef; theEvent: EventRef; userData: Pointer):OSStatus;MWPascal;
		VAR
			status: OSStatus = noerr;
			context: CGContextRef;
			theGeneBox: HIGeneBoxPtr;
		begin
			theGeneBox := HIGeneBoxPtr(userData);
			GetEventParameter (theEvent, kEventParamCGContextRef, typeCGContextRef, nil, sizeof (CGContextRef), nil, @context);
			case GetEventClass(theEvent) of
				kEventClassControl:
					case GetEventKind(theEvent) of
						kEventControlDraw: 	GeneboxControlDraw(theGeneBox, context);		
					end;
			end;
			GeneboxEventHandler := status;
		end;
end.