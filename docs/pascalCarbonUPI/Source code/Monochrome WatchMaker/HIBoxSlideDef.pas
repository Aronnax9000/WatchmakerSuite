unit HIBoxSlideDef;

interface

	uses
		AnimationTypes,
		HIBoxTypes,
		MacOSAll;
	
	procedure Slide (liveBox: HIBoxPtr; destFrame: HIRect);

	
implementation
	function Sgn (x: single): single;
		begin
			IF x < 0 THEN
				sgn := -1
			ELSE IF x > 0 THEN
				sgn := 1
			ELSE
				sgn := 0
		end; {sgn}

	{slide halfway to destination}
	procedure Slide (liveBox: HIBoxPtr; destFrame: HIRect);
		VAR
			dh, dv: single; 
			dx, dy: single; 
			xToMove, yToMove: single; 
			distx, disty: single;
			liveFrame: HIRect;
		begin
			HIViewGetFrame(liveBox^.view, liveFrame);
			
			distx := destFrame.origin.x - liveFrame.origin.x;
			disty := destFrame.origin.y - liveFrame.origin.y;

			dx := sgn(distx);
			dy := sgn(disty);
			xToMove := ABS(distx);
			yToMove := ABS(disty);
	
			ObscureCursor;
			
			IF (xToMove < 1) or (yToMove < 1) THEN 
				begin
					dh := xToMove;
					dv := yToMove;
					liveBox^.slidingFraction := 0
				end
			ELSE
				begin
					dh := xToMove / 2;
					dv := yToMove / 2;
					liveBox^.slidingFraction := liveBox^.slidingFraction / 2
				end;

			IF (xToMove > 0) OR (yToMove > 0) THEN
				begin
					liveFrame.origin.x := liveFrame.origin.x + dx * dh;
					liveFrame.origin.y := liveFrame.origin.y + dy * dv;
					{Note that theBox^.bounds still holds the original home for the box}
					{When GrowChild targets the box, we will turn it invisible, set its frame to}
					{the middle, grow the child in it, and then move it back out to where it belongs.}
					HIViewSetFrame(liveBox^.view, liveFrame);
					{ScrollRect(SlideRect, dx * dh, dy * dv, upregion);}
				end;
			{
			if liveBox^.slidingFraction = 0 then
				case liveBox^.animationState of
					HIBoxStateMoveToHome: liveBox^.animationState := HIBoxStateEvolve; 
					HIBoxStateMoveToCentre: liveBox^.animationState := HIBoxStateReproduce;
				end;
				}
		end; {Slide}


end.