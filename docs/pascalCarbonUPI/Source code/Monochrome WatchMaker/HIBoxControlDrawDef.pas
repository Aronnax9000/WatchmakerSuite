unit HIBoxControlDrawDef;

interface

	uses 
		AnimationTypes,
		Globals,
		DrawPicDefs,
		PersonDefs, {for testing only}
		HIBoxTypes,
		MacOSAll; 

	procedure HIBoxControlDraw(theBox: HIBoxPtr; context: CGContextRef);
	
implementation
	procedure HIBoxControlDraw(theBox: HIBoxPtr; context: CGContextRef);
		var 
			placeHolderBounds: HIRect;
			tempChromosome: integer;
		begin
			HIViewGetBounds(theBox^.view, placeHolderBounds);
			placeHolderBounds := CGRectInset(placeHolderBounds, 10, 10);
			tempChromosome := theBox^.denizen^.gene[Gene9];
			if theBox^.denizen^.gene[Gene9] = 0 then
				begin
				
					{Draw placeholder rect}
					CGContextSetStrokeColorWithColor(context, CGColorCreateGenericRGB(1, 1, 0, 1));	
					CGContextStrokeRectWithWidth(context, placeHolderBounds, 1);
					CGContextBeginPath(context);
					CGContextMoveToPoint(context, placeHolderBounds.origin.x, placeHolderBounds.origin.y);
					CGContextAddLineToPoint(context, 
						placeHolderBounds.origin.x + placeHolderBounds.size.width, 
						placeHolderBounds.origin.y + placeHolderBounds.size.height);
					CGContextStrokePath(context);
				end
			else
				begin
					CGContextSetStrokeColorWithColor(context, CGColorCreateGenericRGB(0, 1, 0, 1));	
					CGContextStrokeRectWithWidth(context, placeHolderBounds, 1);
					{CGContextBeginPath(context);}

					CGContextSetStrokeColorWithColor(context, CGColorCreateGenericRGB(0, 0, 1, 1));	
					CGContextStrokeRectWithWidth(context, theBox^.margin, 1);
					
					DrawPic(theBox^.MyPic, theBox^.offcentre, theBox, context);

				end;
			case theBox^.animationState of
				HIBoxStateIdle:;{shouldn't happen.}

				{etc}
			end;
		end;
end.