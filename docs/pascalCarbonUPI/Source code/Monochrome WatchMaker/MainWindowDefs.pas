unit MainWindowDefs;

interface

	uses 
		AnimationDefs,
		AlbumDefs,
		AlbumUtil, 
		BoxesDefs,
		CursorAdjustDefs,
		CursorDefs,
		DoSaltationDef,
		DriftSweep, 
		EvolveDefs, 
		FlickerTriangleDefs, 
		GeneboxDefs, 
		Globals, 
		HIBoxDefs,
		HIBoxTypes,
		MacOSAll,
		GeneboxManipulationDef,
		ModeDefs, 
		Pedigree, 
		PedigreeTypes,
		{TriangleDefs,}
		TriangleUtils; 
		
procedure DoMouseMoved(theEvent: EventRef; theWindow: WindowRef);

procedure DoMouseDown (theEvent: EventRef; theWindow: WindowRef);

implementation


procedure DoMouseDown (theEvent: EventRef; theWindow: WindowRef);
{    purpose         identify where mouse was clicked and handle it}
	VAR
		MLoc: Point;
		structureWidths: Rect;
		titleBarHeight: integer;
		theHiPoint: HIPoint;
		context: CGContextRef;
	begin
		GetWindowStructureWidths(theWindow, structureWidths);
		titleBarHeight := structureWidths.top;
		GetEventParameter (theEvent, kEventParamWindowMouseLocation, typeHIPoint, nil, sizeof (HIPoint), nil, @theHIPoint);

		{HIPointConvert(theHIPoint, kHICoordSpaceScreenPixel, nil, kHICoordSpaceWindow, MainWindow);}
		theHiPoint.y := theHiPoint.y - titleBarHeight;

		Mloc.h := trunc(theHiPoint.x);
		Mloc.v := trunc(theHiPoint.y);


				IF TheMode = triangling THEN
					PlotTriangle(theHIPoint, context)
				ELSE IF TheMode = breeding THEN
					begin
						SetCursor(CursList[watchCursor]^^);
						{evolve(theHiPoint);}
						SetCursor(CursList[crossCursor]^^);
						StartAnimationTimer;
					end
				ELSE IF TheMode = randoming THEN
					{DoSaltation(boxes);} {FIXME}
				ELSE IF (TheMode = Phyloging) THEN
					begin
						RunningFull := SpecialFull;
						{GetMouse(MP);}
						IF MouseInBox(theHIPoint, RunningFull) THEN
							DrawOutFrom(RunningFull)
					end
				ELSE IF TheMode = Moving THEN
					begin
						RunningFull := SpecialFull;
						{GetMouse(MP);}
						IF MouseInBox(theHIPoint, RunningFull) THEN
							FollowMouse(RunningFull)
					end
				ELSE IF TheMode = Detaching THEN
					begin
						RunningFull := SpecialFull;
						{GetMouse(MP);}
						IF MouseInBox(theHIPoint, RunningFull) THEN
							begin
								IF IsAnAdam(RunningFull) THEN
									SysBeep(1)
								ELSE
									Detach(RunningFull)
							end
					end
				ELSE IF TheMode = Killing THEN
					begin
						RunningFull := SpecialFull;
						{GetMouse(MP);}
						IF MouseInBox(theHIPoint, RunningFull) THEN
							Shoot(RunningFull)
					end
			(*	ELSE IF TheMode = engineering THEN
					begin
						Manipulation(theHIPoint);
					end *)
				ELSE IF TheMode = highlighting THEN
					emphasize(MLoc)
				ELSE IF (TheMode = Albuming) then
					if not Zoomed THEN
						emphasize(MLoc)
					else
						HandleZoomClicked(MLoc)
	
	end; { of proc DoMouseDown }


procedure DoMouseMoved(theEvent: EventRef; theWindow: WindowRef);
var j: integer;
theHIPoint: HIPoint;
structureWidths: Rect;
mousePoint: Point;
theBox: HIBoxPtr;
theView: HIViewRef;
begin	

	GetEventParameter (theEvent, kEventParamWindowMouseLocation, typeHIPoint, nil, sizeof (HIPoint), nil, @theHIPoint);
{HIPointConvert(theHIPoint, kHICoordSpaceScreenPixel, nil, kHICoordSpaceWindow, theWindow);}
{	GetWindowStructureWidths(theWindow, structureWidths);}
{	theHiPoint.y := theHiPoint.y - structureWidths.top; }	{Shave off title bar height}
	CursorAdjust(theWindow, theHiPoint);
	
		IF ((theMode = Breeding) OR (theMode = Highlighting)) AND (frontwindow = theWindow) THEN
			begin
(*				theBox := boxes^.first;
				j := 0;
				while (CGRectContainsPoint(theBox^.bounds, theHiPoint) = 0) and (theBox <> nil) do 
					begin
						j := j + 1;
						theBox := theBox^.next;
					end;*)
				IF (theBox <> nil) AND (theBox <> OldBox) THEN
					begin
						IF oldbox <> nil THEN
					{		ShowChangedGene(theBox^.denizen, oldBox^.denizen); FIXME Mouseover HIBox}
						OldBox := theBox
					end;
			end;

		IF (theMode = Drifting) AND SweepOn THEN
			DoSweep(theWindow);

		IF (TheMode = Drifting) AND NOT SweepOn THEN
			DoDrift(theWindow);
		if (TheMode = Albuming) then
			begin
				if Zoomed then
					HandleZoomMouseMoved(theHIPoint, quadrant, Page, OldQuadrant)
			end;
		IF theMode = triangling THEN
			begin
				FlickerTriangle(theHIPoint, theView); {FIXME}
			end;
end;



end.
