unit CursorAdjustDefs;

interface

uses
	CursorDefs,
	MacOSAll, 
	Globals, 
	GeneboxDefs, 
	MessageDefs, 
	ModeDefs;

procedure CursorAdjust(theWindow: WindowRef; mousePoint: HIPoint);
implementation



	{    purpose         change cursors depending upon location}
	procedure CursorAdjust(theWindow: WindowRef; mousePoint: HIPoint);
		VAR
			boundsRect: Rect;
		begin
			IF theWindow = frontw THEN
				begin
					GetWindowPortBounds(theWindow, boundsRect);
					IF TheMode = Breeding THEN
						SetCursor(CursList[BreedCursor]^^)
					ELSE IF theMode = Preliminary THEN
						SetCursor(CursList[plusCursor]^^)
					ELSE IF (theMode = highlighting) OR (theMode = Albuming) THEN
						SetCursor(CursList[BlackCursor]^^)
					ELSE IF theMode = randoming THEN
						SetCursor(CursList[RandCursor]^^)
					ELSE IF theMode = phyloging THEN
						SetCursor(CursList[DrawOutCursor]^^)
					ELSE IF (theMode = moving) THEN
						SetCursor(CursList[HandCursor]^^)
					ELSE IF (theMode = Detaching) THEN
						SetCursor(CursList[ScissorCursor]^^)
					ELSE IF (theMode = Killing) THEN
						SetCursor(CursList[GunCursor]^^)
					ELSE IF (theMode = Triangling) THEN
						begin
							IF Naive THEN
								begin
									Naive := FALSE;
									SetCursor(CursList[BlankCursor]^^)
								end
							ELSE
								SetCursor(theCursor)
						end
					ELSE
						SetCursor(CursList[crossCursor]^^){    else make a cross  }
				end;
		end; { of proc CursorAdjust }
end.