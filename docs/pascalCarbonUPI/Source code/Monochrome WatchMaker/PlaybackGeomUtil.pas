unit PlaybackGeomUtil;

interface

uses MacOSAll, Globals;

function PlayBackRect: Rect;
function GetPlayBackMidPoint: Point;


implementation


	function PlayBackRect: Rect;
		var boundsRect: Rect;
		begin
			GetWindowPortBounds(PlaybackWindow, boundsRect);
			with boundsRect do
			begin
				right := right - Scrollbarwidth;
				bottom := bottom - Scrollbarwidth
				
			end;

			PlayBackRect := boundsRect;
			
		end;

	function GetPlayBackMidPoint: Point;
		var theMidPoint: Point;
			pbRect: Rect;
		begin
			pbRect := PlayBackRect;
			with pbRect Do
			begin
				theMidPoint.h := (right - left) DIV 2;
				theMidPoint.v := (bottom - top) DIV 2
			end;
			GetPlayBackMidPoint := theMidPoint;
		end;

end.