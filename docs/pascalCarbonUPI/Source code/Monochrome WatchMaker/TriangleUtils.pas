unit TriangleUtils;

interface

	uses 
		AlbumDefs,
		CursorDefs,
		DevelopDefs,
		DrawPicDefs,
		Globals,
		HIBoxDefs,
		MacOSAll, 
		TriangleDefs, 
		HIBoxTypes,
		ConcoctDefs,
		ModeDefs,
		PersonDefs,
		PicDefs;

	procedure PlotTriangle (MLoc: HIPoint; theView: HIViewRef);
	procedure MainTriangle(theBoxes: HIBoxesPtr; theView: HIViewRef; context: CGContextRef);
	procedure AtLeast (VAR OutRect: Rect);
	procedure OwnCursor (theRect: Rect; fromBitMap: BitMap; VAR theCursor: Cursor);
	procedure CursSnap (ThisPic: MorphPic; offset: Integer; box: HIBoxPtr);
	procedure MainTriangle(theBoxes: HIBoxesPtr; context: CGContextRef);

implementation

	procedure PlotTriangle (MLoc: HIPoint; theView: HIViewRef);
		VAR
			margcentre, offset: CGFloat;
			OffCentre: HIPoint;
		begin
			triangle(r1, r2, r3, b, MLoc, theView);
			concoct(r1, r2, r3, topan, leftan, rightan, Special^.denizen);
			zeromargin := TRUE;
			DelayedDrawing := true;
		(*
			develop(Special, Mloc);
			WITH margin DO
				margcentre := top + (bottom - top) DIV 2;
			offset := margcentre - MLoc.v;
			WITH Margin DO
				begin
					Top := Top - Offset;
					Bottom := Bottom - Offset
				end;
			WITH OffCentre DO
				begin
					h := MLoc.h;
					v := MLoc.v - offset;
				end;
			InsetRect(margin, 2, 2);
			EraseRect(margin);
			FrameRect(margin);
			DrawPic(MyPic, offcentre, child[special]);
			HiWindowFlush(MainWindow);
			*)
	{MakeGeneBox(child[special]);}
		end; {PlotTriangle}

	procedure MainTriangle(theBoxes: HIBoxesPtr; context: CGContextRef);
		VAR
			screenBounds: HIRect;
			ScreenWidth, ScreenHeight: CGFloat;
			Storeanimal: PersonPtr;
			strokeColor: CGColorRef;
		begin

			AlreadyTriangling := FALSE;
			IF Special <> nil THEN
				Storeanimal := special^.denizen;
			LastMouse.x := -1;
			LastMouse.y := -1;
			strokeColor := CGColorGetConstantColor(kCGColorWhite);
			CGContextSetFillColorWithColor(context, strokeColor);
			HIViewGetBounds(theBoxes^.view, screenBounds);
			CGContextFillRect(context, screenBounds);
			WITH screenBounds DO
				begin
					ScreenWidth := size.width;
					ScreenHeight := size.height
				end;
			a.x := 234 * ScreenWidth / 512;
			a.y := 51 * ScreenHeight / 342;
			b.x := 134 * ScreenWidth / 512;
			b.y := 250 * ScreenHeight / 342;
			c.x := 333 * ScreenWidth / 512;
			c.y := 250 * ScreenHeight / 342;
			strokeColor := CGColorGetConstantColor(kCGColorBlack);
			CGContextSetFillColorWithColor(context, strokeColor);
			drawtriangle(a, b, c, context);
			mous.x := 0;
			mous.y := 0;
			DrawTriangle(a, b, c, context); 
			IF special = nil THEN
				special := theBoxes^.first;
			PlotTriangle(a, context);
			PlotTriangle(b, context);
			PlotTriangle(c, context);
			IF special <> nil THEN
				Special^.denizen := storeanimal;
		end; {maintriangle}


	procedure OwnCursor (theRect: Rect; fromBitMap: BitMap; VAR theCursor: Cursor);

		VAR
			Box: Rect;
			aBitMap: BitMap;
			myBits16: Bits16;

		function MakeBits16 (theBitMap: BitMap; VAR theBits16: Bits16): Boolean;
			TYPE
				BitsPtr = ^Bits16;
			VAR
				thePtr: BitsPtr;

			begin
				thePtr := BitsPtr(NewPtr(SizeOf(BitsPtr)));
				IF MemError <> noErr THEN
					ExitToShell;
				thePtr := BitsPtr(theBitMap.BaseAddr);
				theBits16 := thePtr^;
				DisposePtr(Ptr(thePtr));
				MakeBits16 := TRUE;
			end; {Make Bits16}

		function BigEnough (VAR theRect: Rect): Boolean;
			VAR
				Width, Height: Integer;
			begin
				WITH theRect DO
					begin
						Width := Right - Left;
						Height := Bottom - Top;
					end;
				BigEnough := (Width > 16) AND (Height > 16);
			end; {LargerRect}

		begin
			SetRect(Box, 0, 0, 16, 16);
			WITH theRect DO
				begin
					left := left + 2;
					right := right - 2;
					top := top + 2;
					bottom := bottom - 2
				end;
			aBitMap.rowBytes := 2;
			aBitMap.bounds := box;
			aBitMap.baseAddr := NewPtr(SizeOf(Bits16));
			CopyBits(fromBitMap, aBitMap, TheRect, box, srcCopy, NIL);
			IF MakeBits16(aBitMap, MyBits16) AND (BigEnough(theRect) OR (theMode = Triangling)) THEN
				begin
					theCursor.data := MyBits16;
					theCursor.mask := curslist[breedcursor]^^.mask;
					theCursor.hotSpot.v := 8;
					theCursor.hotSpot.h := 8;
					DisposePtr(aBitMap.baseAddr);
				end
			ELSE
				theCursor := curslist[crosscursor]^^;
		end; {OwnCursor}


	function DivisibleByEight (n: Integer): Boolean;
		begin
			DivisibleByEight := 8 * (n DIV 8) = n
		end;


	procedure AtLeast (VAR OutRect: Rect);
		begin
			InsetRect(OutRect, -3, -3);
			WITH OutRect DO
				begin
					WHILE NOT (DivisibleByEight(left)) DO
						left := left - 1;
					WHILE NOT (DivisibleByEight(right)) DO
						right := right + 1
				end
		end;

	procedure MakeNiceBox (Inbox: HIRect; VAR outbox: HIRect);
		VAR
			width, height: Integer;
		begin
			OutBox := InBox;
			WITH OutBox DO
				begin
					{height := bottom - top;}{1+}
					{width := right - left;}{1+}
					IF size.width < size.height THEN
						size.width := size.height
					ELSE
						size.height := size.width;
						(* FIXME
					left := 8;
					Right := left + Width;
					Top := 8;
					Bottom := Top + Height; 
					AtLeast(outbox);
					right := right + 1;*)
				end;
		end; {MakeNiceBox}

	procedure CursSnap (ThisPic: MorphPic; offset: Integer; box: HIBoxPtr);
		VAR
			Midpoint: HIPoint;
			NiceBox: HIRect;
			Offcentre: HIPoint;
			SavePort: GrafPtr;
			saveDev: GDHandle;
			tempRect: HIRect;
		begin 
		(*
			GetGWorld(savePort, saveDev);
			SetGWorld(AlbumBitMap[0], nil);
			MakeNiceBox(box^.bounds, NiceBox); {FIXME}
			WITH NiceBox DO
				begin
					MidPoint.v := top + (bottom - top) DIV 2;
					MidPoint.h := left + (right - left) DIV 2;
				end;
			EraseRect(NiceBox); {offscreen}
			Offcentre := MidPoint;
			Offcentre.v := Offcentre.v - offset;
			DrawPic(ThisPic, Offcentre, box);
			OwnCursor(Nicebox, GetPortBitMapForCopyBits(AlbumBitMap[0])^, theCursor);
			SetGWorld(savePort, saveDev);
			CopyBits(
				GetPortBitMapForCopyBits(AlbumBitMap[0])^,
				GetPortBitMapForCopyBits(MainWindow)^,
				box,
				box,
				srcCopy,
				nil);
			
			ClipRect(GetWindowPortBounds(MainWindow, tempRect)^);
			*)
		end; {CursSnap}


end.