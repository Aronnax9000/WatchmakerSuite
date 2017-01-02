unit SnapDevelopDef;

interface

	uses 
		
		DevelopDefs,
		DrawPicDefs,
		Globals,
		HiBoxTypes,
		MacOSAll,
		ModeDefs,
		PicDefs,
		PlaybackGeomUtil,
		PersonDefs;
	
	procedure Snapshot (ThisPic: MorphPic; box: HiBoxPtr);

	procedure SnapDevelop (Biomorph: personPtr; Place: HIPoint);


implementation

	procedure Snapshot (ThisPic: MorphPic; box: HiBoxPtr);
		VAR
			Midpoint: HIPoint;
			bounds: HIRect;
			savePort: GrafPtr;
			saveDev: GDHandle;
			grafWorld: GWorldPtr;
			status: OSStatus;
			grafWorldBounds: HIRect;
			mainWindowBounds: HIRect;
		begin
		(* FIXME
			IF theMode = Sweeping THEN
				ClipRect(GetWindowPortBounds(MainWindow, mainWindowBounds)^) {FIXME}
			ELSE if theMode = Playingback then
				ClipRect(PlayBackRect)
			else
				ClipRect(businessPart);

			GetGWorld(savePort, saveDev);
			status := NewGworld(grafWorld, 0, box, nil, nil, 0);
			GetPortBounds(grafWorld, grafWorldBounds);

			WITH grafWorldBounds DO
				begin
					MidPoint.h := left + (right - left) DIV 2;
					MidPoint.v := top + (bottom - top) DIV 2
				end;

			SetGWorld(grafWorld, nil);
			EraseRect(grafWorldBounds); {offscreen}
			DrawPic(ThisPic, MidPoint, box);
			SetGWorld(savePort, saveDev);
			EraseRect(GetPortBounds(savePort, bounds)^);
			CopyBits(GetPortBitmapForCopyBits(grafWorld)^, GetPortBitMapForCopyBits(saveport)^, grafWorldBounds, box, srcCopy, nil);
			DisposeGWorld(grafWorld);
			ClipRect(GetPortBounds(savePort, box)^);
			HiWindowFlush(GetWindowFromPort(savePort));
			*)
		end; {Snapshot}

	procedure SnapDevelop (Biomorph: PersonPtr; Place: HIPoint);
		VAR
			SnappyBox: HIRect;
			theMargin: HIRect;
		begin
		(*
			DelayedDrawing := TRUE;
			SnappyBox := Margin;
			ZeroMargin := TRUE;
			Develop(biomorph, Place);
			theMargin := margin;
			WITH Snappybox DO
				begin
					IF theMargin.left < left THEN
						left := theMargin.left;
					IF theMargin.right > right THEN
						right := theMargin.right;
					IF theMargin.top < top THEN
						top := theMargin.top;
					IF theMargin.bottom > bottom THEN
						bottom := theMargin.bottom
				end;
			InsetRect(Snappybox, MyPenSize, MyPenSize);
			Snapshot(MyPic, Snappybox, Biomorph);
			*)
		end; {SnapDevelop}

end.