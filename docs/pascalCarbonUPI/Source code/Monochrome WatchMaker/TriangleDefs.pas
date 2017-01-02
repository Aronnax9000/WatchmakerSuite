unit TriangleDefs;

interface
uses
	MacOSAll, 
	Globals, 
	Miscellaneous, 
	ErrorUnit, 
	ModeDefs, 
	PicDefs, 
	RectDrawDefs,
	PersonDefs;


	procedure Triangle (VAR r1, r2, r3: single; b: HIPoint; m: HIPoint; theView: HIViewRef);
	procedure DrawTriangle (a, b, c: HIPoint; context: CGContextRef);

implementation

	procedure Triangle (VAR r1, r2, r3: single; b: HIPoint; m: HIPoint; theView: HIViewRef);
		VAR
			x, y, k: single;
			screenBounds: HIRect;
		begin
			HIViewGetBounds(theView, screenBounds);
			k := round(200 * screenBounds.size.height / 340);
			x := m.x - b.x;
			y := (screenBounds.size.height - m.y) - (screenBounds.size.height - b.y);
			r1 := y / k;
			r3 := (x - y / 2) / k;
			r2 := (k - x - y / 2) / k;
		end; {triangle}


	procedure DrawTriangle (a, b, c: HIPoint; context: CGContextRef);
		begin
			{FIXME save/restore graphics state, set color to black?}
			CGContextBeginPath(context);					
			CGContextMoveToPoint(context, a.x, a.y);
			CGContextAddLineToPoint(context, b.x, b.y);
			CGContextAddLineToPoint(context, c.x, c.y);
			CGContextAddLineToPoint(context, a.x, a.y);
			CGContextStrokePath(context);
		end; {drawtriangle}




end.