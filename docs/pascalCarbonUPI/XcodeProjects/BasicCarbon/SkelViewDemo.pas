{SkelViewDemo, demonstrates the SkelView interface to making two}
{simple plain HIViews with drawing, keyboard input and mouse downs.}
{By Ingemar Ragnemalm 2008}

program SkelViewDemo;
	uses
		MacOSAll, TransSkel4, SkelView;

var
	gWhereQD: Point;
	gWhere: CGPoint;
//	w: WindowPtr;

// This should really be in FPCMacOSAll/RTL
FUNCTION CGRectMake(x: Single; y: Single; width: Single; height: Single): CGRect;
var
	cgr: CGRect;
begin
	cgr.origin.x := x;
	cgr.origin.y := y;
	cgr.size.width := width;
	cgr.size.height := height;
	return cgr;
end;

// *** CG-based view ***

	procedure DoViewUpdate(theView: HIViewRef; cgc: CGContextRef; r: CGRect; userData: Pointer);
	begin
		CGContextSetRGBFillColor(cgc, 1, 0.95, 0.9, 0.5);
		CGContextFillRect(cgc, r);
		{Circle, anti-aliased}
		CGContextSetRGBFillColor(cgc, 0.4, 0.3, 0.2, 1);
		CGContextFillEllipseInRect(cgc, CGRectMake(gWhere.x-5, gWhere.y-5, 10, 10));
	end;
	
	procedure DoKey (theView: HIViewRef; key: Char; mods: Longint; userData: Pointer);
	begin
		WriteLn('Key ', key);
		gWhere.x := 0;
		gWhere.y := 0;
		HIViewSetNeedsDisplay(theView, true);
	end;
	
	procedure DoMouse (theView: HIViewRef; where: CGPoint; mods: Longint; userData: Pointer);
	begin
		WriteLn('Mouse CG');
		
		// Save data and order a redisplay
		gWhere := where;
		
		HIViewSetNeedsDisplay(theView, true);
	end;
	
// *** QuickDraw-based view ***

	procedure DoViewUpdateQD(theView: HIViewRef; r: Rect; userData: Pointer);
	begin
		EraseRect(r);
		FrameRect(r);

		SetRect(r, -5, -5, 5, 5);
		OffsetRect(r, gWhereQD.h, gWhereQD.v);
		PaintOval(r);
	end;
	
	procedure DoKeyQD (theView: HIViewRef; key: Char; mods: Longint; userData: Pointer);
	begin
		WriteLn('Key QD ', key);
		gWhereQD.h := 0;
		gWhereQD.v := 0;
		HIViewSetNeedsDisplay(theView, true);
	end;
	
	procedure DoMouseQD (theView: HIViewRef; where: Point; mods: Longint; userData: Pointer);
	begin
		WriteLn('Mouse QD');
		
		// Save data and order a redisplay
		gWhereQD := where;
		HIViewSetNeedsDisplay(theView, true);
	end;
	
	procedure MakeWindow;
		var
			w: WindowPtr;
			err: OSErr;
	begin
		err := CreateWindowFromNib(SkelGetMainNib, CFSTR('SkelWindow'), w);
		if err <> noErr then WriteLn(err);
		ShowWindow(w);
		SetPortWindowPort(w);
		SkelWindow(w, nil, nil, nil, nil, nil, nil, nil, false);

		InstallSkelViewHandler(w, 'View', 0,
				DoViewUpdate, DoMouse, DoKey, nil);
		InstallQDSkelViewHandler(w, 'View', 1,
				DoViewUpdateQD, DoMouseQD, DoKeyQD, nil);
	end;
	
begin
	SkelInit;								{ initialize }
	SkelApple('(About SkelView demo…', nil);
	
	MakeWindow;
	
	SkelMain;								{ loop 'til Quit selected }
	SkelClobber;							{ clean up }
end.
