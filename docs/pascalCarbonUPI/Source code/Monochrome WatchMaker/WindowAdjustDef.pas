unit WindowAdjustDef;
{Note from Alan Canon.}
{This is the unit WindowAdjust, which I think, under OS X, is a lot of hooey. But here it is.}
{I haven't the time to evaluate it. I'd say demolish it and see if it breaks anything, and if}
{it doesn't, "dike it out." - ABC}
interface

	uses 
		MacOSAll,
		ModeDefs,
		Globals;

	procedure WindowAdjust;
	
implementation


	procedure WindowAdjust;
		VAR
			ActualFront, ShouldBeFront: WindowPtr;
			tempRect: Rect;
		begin
			ActualFront := FrontWindow;
			IF theMode = PlayingBack THEN
				ShouldBeFront := PlaybackWindow
			ELSE 
				ShouldBeFront := MainWindow;
			IF ShouldBeFront <> ActualFront THEN
				begin
					EraseRect(GetWindowPortBounds(ActualFront, tempRect)^);
					SelectWindow(ShouldBeFront);
					SetPort(ShouldBeFront)
				end
		end; {WindowAdjust}


end.