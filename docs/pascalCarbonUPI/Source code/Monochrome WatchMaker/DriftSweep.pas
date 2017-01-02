unit DriftSweep;

interface

uses 
	BoxesDefs,
	DevelopDefs,
	HIBoxTypes,
	MacOSAll, 
	Globals, ReproduceDef, 

	ModeDefs,
	SnapDevelopDef;
	
procedure DoSweep(theWindow: WindowRef);
procedure DoDrift(theWindow: WindowRef);

implementation


procedure DoSweep(theWindow: WindowRef);
	var 
		tempPattern: Pattern;
		boundsRect: Rect;

	begin
	(* FIXME
		GetWindowPortBounds(theWindow, boundsRect);
		ObscureCursor;
		IF DriftOne > 0 THEN
			begin
				GetQDGlobalsWhite(tempPattern);
				PenPat(tempPattern);
				PenSize(3, 3);
				Framerect(Box[DriftOne])
			end;
		GetQDGlobalsBlack(tempPattern);
		PenPat(tempPattern);
		DriftOne := DriftOne + 1;
		IF DriftOne > NBoxes THEN
			DriftOne := 1;
		EraseRect(Box[DriftOne]);
		FrameRect(Box[DriftOne]);
		PenSize(1, 1);
		ClipRect(Box[DriftOne]);
		Child[DriftOne] := Child[Special];
		Delayvelop(Child[Special], Centre[DriftOne]);
		ClipRect(boundsRect);
		Special := DriftOne;
		Reproduce(Child[Special], Child[Special]);
		NActiveBoxes := NactiveBoxes + 1;
		IF NActiveBoxes > NBoxes THEN
			NactiveBoxes := NBoxes;
			*)
	end; {sweeping}

procedure DoDrift(theWindow: WindowRef);
	var boundsRect: Rect;
	begin
		GetWindowPortBounds(theWindow, boundsRect);
		IF (TheMode = Drifting) AND (NOT SweepOn){ AND (PtInRect(MLoc, PRect))} THEN
			begin
				ObscureCursor;
				DelayedDrawing := TRUE;
				ZeroMargin := FALSE;
				ClipRect(boundsRect);
				Develop(Special, MidScreen);
				DelayedDrawing := FALSE;
				ClipRect(boundsRect);
				{Snapshot(MyPic, Special);}
				Reproduce(Special^.denizen, Special^.denizen);
				NActiveBoxes := NactiveBoxes + 1;
				IF NActiveBoxes > NBoxes THEN
					NactiveBoxes := NBoxes;
			end; {drifting}
	end;

end.