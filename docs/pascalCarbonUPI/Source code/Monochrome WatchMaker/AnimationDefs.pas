unit AnimationDefs;

interface

uses MacOSAll, AnimationTypes, HIBoxTypes;

	procedure HIBoxAnimate(theTimer: EventLoopTimerRef; userData: Pointer); MWPascal;
	procedure InitializeAnimationTimer(theBoxes: HIBoxesPtr);
	procedure StartAnimationTimer;

	

implementation

	var animationTimer: EventLoopTimerRef;
	
	
	procedure StartAnimationTimer;
		begin
			SetEventLoopTimerNextFireTime(animationTimer, kEventDurationNoWait);
		end;
		
	procedure StopAnimationTimer;
		begin
			SetEventLoopTimerNextFireTime(animationTimer, kEventDurationForever)
		end;



	procedure HIBoxAnimate(theTimer: EventLoopTimerRef; userData: Pointer); MWPascal;
		var 
			boxes: HIBoxesPtr;
			box: HIBoxPtr;
			keepOnTicking: boolean = false;
		begin
			boxes := HIBoxesPtr(userData);
			box := boxes^.first;
			while box <> nil do
				begin
					if (box^.animationState <> HIBoxStateIdle) then
						begin
							HIViewSetNeedsDisplay(box^.view, true);
							keepOnTicking := true;
							break;
						end;
					box := box^.next;
				end;
			if not keepOnTicking then
				StopAnimationTimer;			
		end;
		
	procedure InitializeAnimationTimer(theBoxes: HIBoxesPtr);
		begin
			InstallEventLoopTimer(GetMainEventLoop, 0, (kEventDurationSecond / 15), HIBoxAnimate, nil, animationTimer);
		end;


end.