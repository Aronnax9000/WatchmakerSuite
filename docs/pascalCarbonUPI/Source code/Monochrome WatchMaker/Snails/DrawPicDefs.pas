unit DrawPicDefs;

interface
	uses Globals, MacOSAll, PersonDefs, PicDefs, ModeDefs, SerializationUtil;
	procedure DrawPic (ThisPic: Pic; Place: Point; VAR Biomorph: person);

implementation

uses MorphGlobals;


PROCEDURE MirrorBits (SourceBitmap: BitMap; RectOfInterest: Rect; VAR DestBitMap: BitMap);
{Delivers a BitMap whose RectOfInterest is the left-right mirror image of the same rect in SourceBitmap}
{Assumes that the two bitmaps have already had space assigned to them and have the same dimensions but different pointers}
	VAR
		row, ColBit, ReverseColbit, nrows, RowOffset: integer;
		BitsAcross: Longint;{needs to be Longint because of BitTst}
		SourceBit: Boolean;
		SrcRowBaseAddr: PtrUInt; 
		DstRowBaseAddr: PtrUInt;
	BEGIN

		NRows := RectOfInterest.bottom - RectOfInterest.top;
		BitsAcross := RectOfInterest.right - RectOfInterest.left;
		FOR row := 1 TO NRows DO
			BEGIN
				RowOffset := SourceBitMap.rowbytes * (row - 1);
				SrcRowBaseAddr := PtrUInt(PtrUInt(SourceBitmap.BaseAddr) + RowOffset);
				DstRowBaseAddr := PtrUInt(PtrUInt(DestBitMap.BaseAddr) + RowOffset);
				FOR colbit := 0 TO BitsAcross DO
					BEGIN
						ReverseColBit := BitsAcross - ColBit - 1;
						SourceBit := BitTst(Ptr(SrcRowBaseAddr), ColBit);
						IF SourceBit THEN
							BitSet(Ptr(DstRowBaseAddr), ReverseColBit)
						ELSE
							BitClr(Ptr(DstRowBaseAddr), ReverseColBit);
					END;
			END;
	END; {MirrorBits}


PROCEDURE DrawPic (ThisPic: Pic; Place: Point; VAR Biomorph: person);
		VAR
			j, y0, y1, x0, x1, VertOffset, HorizOffset, width, height, JThreshold: Integer;
			theRect: rect;
			SavePort: GrafPtr;
			SaveBitMap: GWorldPtr;
			savePortBounds: Rect;
			LinSize: integer;

 {Pic already contains its own origin, meaning the coordinates at which}
{ it was originally drawn. Now draw it at Place}
	PROCEDURE quarantine;
		BEGIN
			WITH ThisPic.MovePtr^ DO
				BEGIN
					startpt.v := thisPic.Origin.v + round(theScale * (Startpt.v - thisPic.Origin.v));
					endpt.v := thisPic.Origin.v + round(theScale * (endpt.v - thisPic.Origin.v));
					startpt.h := thisPic.Origin.h + round(theScale * (Startpt.h - thisPic.Origin.h));
					endpt.h := thisPic.Origin.h + round(theScale * (Endpt.h - thisPic.Origin.h));
				END;
		END;


	PROCEDURE ActualLine;
		VAR
			temp: integer;
			doMirror, TooThin: boolean;
		BEGIN
			WITH ThisPic.MovePtr^ DO
				BEGIN
					quarantine;
					VertOffset := round(theScale * (ThisPic.Origin.v - Place.v));
					HorizOffset := round(theScale * (ThisPic.Origin.h - Place.h));
					y0 := StartPt.v - VertOffset;
					y1 := EndPt.v - VertOffset;
					x0 := StartPt.h - HorizOffset;
					x1 := EndPt.h - HorizOffset
				END;
			IF NOT sideView THEN
				BEGIN
					MoveTo(x0, y0);
					LineTo(x1, y1)
				END
			ELSE
				BEGIN
					doMirror := false;
					IF x1 < x0 THEN
						BEGIN {here we need a mirror image}
							DoMirror := true;
							temp := x0;
							x0 := x1;
							x1 := temp;
						END;
					setRect(theRect, x0, y0, x1, y1);
					WITH theRect DO
						BEGIN
							width := abs(right - left);
							height := abs(bottom - top);
							TooThin := (width < threshold) OR (height < threshold);
							IF (Biomorph.GeneratingCurve = 0) OR (theMode = Triangling) OR ((J < JThreshold) AND TooThin) THEN
								BEGIN
									IF width = 0 THEN
										BEGIN
											MoveTo(left, top);
											LineTo(right, bottom);
										END
									ELSE
										FrameOval(theRect);
								END
							ELSE
								BEGIN
									IF odd(width) THEN
										right := right + 1;
									IF NOT doMirror THEN
										CopyBits(
											GetPortBitMapForCopyBits(MugShot)^, 
											GetPortBitMapForCopyBits(SaveBitMap)^, 
											RectOfInterest, theRect, srcOr, NIL)
									ELSE
										CopyBits(GetPortBitMapForCopyBits(Mirrorshot)^, 
										GetPortBitMapForCopyBits(SaveBitMap)^, 
										RectOfInterest, theRect, srcOr, NIL);
								END
						END;
				END
		END; {ActualLine}

	PROCEDURE ChangeTheBitMaps(ResourceID: Integer);
		VAR
			StrangePicture: PicHandle;
			tmpPattern: Pattern;
			tmpPicture: Picture;
		BEGIN
			
			StrangePicture := GetPicture(ResourceID);
			tmpPicture := StrangePicture^^;
			WITH tmpPicture.PicFrame DO
				BEGIN
					{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
					mangleArrayDuo(arrayDuo(left));
					mangleArrayDuo(arrayDuo(right));
					mangleArrayDuo(arrayDuo(bottom));
					mangleArrayDuo(arrayDuo(top));
					mangleArrayDuo(arrayDuo(tmpPicture.PicSize));
					{$endc}
					SetRect(RectOfInterest, 0, 0, right - left, bottom - top);
					WITH RectOfInterest DO
						IF odd(right - left) THEN
							right := right + 1;
				END;
			ClipRect(RectOfInterest);
			SetPortBits(GetPortBitMapForCopyBits(MugShot)^);
			FillRect(RectOfInterest, GetQDGlobalsWhite(tmpPattern)^); {Off screen}
			DrawPicture(StrangePicture, RectOfInterest);
			Cliprect(Prect);
			SetPort(SaveBitMap);
			MirrorBits(GetPortBitMapForCopyBits(MugShot)^, RectOfInterest, GetPortBitMapForCopyBits(MirrorShot)^);
		END;


	BEGIN
		GetPort(SavePort);
		LinSize := SizeOf(Lin);
		{SaveBitMap := GetPortBitMapForCopyBits(SavePort)^;}
		NewGWorld(SaveBitMap, 0, GetPortBounds(SavePort, savePortBounds)^, nil, nil, 0);
		WITH ThisPic DO
			BEGIN
				
				IF ByPassGeneratingCurves or (Biomorph.GeneratingCurve < 128) OR (Biomorph.GeneratingCurve > MaxResources) THEN
					Biomorph.GeneratingCurve := 0
				ELSE
					BEGIN
						IF (Biomorph.GeneratingCurve <> CurrentGeneratingCurve) THEN
							ChangeTheBitMaps(Biomorph.GeneratingCurve);;
					END;
				CurrentGeneratingCurve := Biomorph.GeneratingCurve;
				MovePtr := linptr(BasePtr); {reposition at base of grabbed space}
				JThreshold := PicSize * (LinSize - 1) DIV LinSize;
				FOR j := 1 TO PicSize DO
					BEGIN
						ActualLine; {sometimes rangecheck error}
						ThisPic.MovePtr := linptr(size(ThisPic.MovePtr) + LinSize);
					END;
			END;
		PenSize(1, 1);
	END; {DrawPic}

end.