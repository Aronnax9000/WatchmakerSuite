unit BoxesDefs;

interface

uses MacOSAll, Globals, ModeDefs, MorphGlobals;

type
	BoxArray = ARRAY[1..MaxBoxes] of Rect;
	CentreArray = ARRAY[1..MaxBoxes] of Point;

var
	{box: BoxArray;}
	
	centre: CentreArray;
	

procedure DoShowBoxes(thickerMidBox: boolean);
procedure DrawBoxes(thickerMidBox: boolean);

procedure InitializeQuadrants(theWindow: WindowRef); {For zooming album}


	procedure CarveRectIntoBoxes(
		theRect: Rect; 
		var theBoxArray: BoxArray; 
		var theCentreArray: CentreArray; 
		rowCount: integer; 
		colCount: integer;
		var NBoxesOut: integer;
		var MidBoxOut: integer;
		topMargin: integer);
		
implementation

	procedure CarveRectIntoBoxes(
		theRect: Rect; 
		var theBoxArray: BoxArray; 
		var theCentreArray: CentreArray; 
		rowCount: integer; 
		colCount: integer;
		var NBoxesOut: integer;
		var MidBoxOut: integer;
		topMargin: integer);
	var
		j, l, t: integer;
		row: integer;
		column: integer;
		boxwidth: Integer;
		boxheight: Integer;
	begin
		NBoxesOut := rowCount * colCount;
		MidBoxOut := NBoxesOut div 2 + 1;
		boxwidth := (theRect.right - theRect.left) DIV colCount;
		boxheight := (theRect.bottom - theRect.top - topMargin) DIV rowCount;
			
		j := 0;
		FOR row := 1 TO rowCount DO
			FOR column := 1 TO colCount DO
				begin
					j := j + 1;
					l := theRect.left + boxwidth * (column - 1);
					t := theRect.top + topMargin + boxheight * (row - 1);
					setrect(theBoxArray[j], l, t, l + boxwidth, t + boxheight);
					theCentreArray[j].h := theBoxArray[j].left + boxwidth DIV 2;
					theCentreArray[j].v := theBoxArray[j].top + boxheight DIV 2
				end; {row & column loop}
	end;

procedure DrawBoxes(thickerMidBox: boolean);
	VAR
		j: Integer;
	begin
		(*
		FOR j := 1 TO NBoxes DO
			begin
				if (j = MidBox) and thickerMidBox then
					PenSize(3, 3);
				FrameRect(box[j]);
				if (j = MidBox) and thickerMidBox then
					PenSize(1, 1);
			end; *)
	end; {BoxesOnly}




procedure InitializeQuadrants(theWindow: WindowRef); {For zooming album}
var bounds: Rect;
j: integer;
topOffset: integer;
begin
	topOffset := 0; {was 15, but why waste the space? -- ABC}
		GetWindowPortBounds(theWindow, bounds);

		FOR j := 1 TO 4 DO
			begin
				SetRect(Quadrant[j], 0, 0, 0, 0);
				Quadrant[j].top := bounds.top;
				Quadrant[j].left := bounds.left;
				Quadrant[j].right := bounds.right;
				Quadrant[j].bottom := bounds.bottom;
			end;

		Quadrant[1].top := bounds.top + topOffset;
		Quadrant[2].top := bounds.top + topOffset;
		Quadrant[1].right := bounds.Left + (bounds.right - bounds.Left) DIV 2;
		Quadrant[1].Bottom := bounds.Top + (bounds.Bottom - bounds.Top) DIV 2;
		Quadrant[3].Right := Quadrant[1].Right;
		Quadrant[3].Top := Quadrant[1].Bottom;
		Quadrant[2].Left := Quadrant[1].Right;
		Quadrant[2].Bottom := Quadrant[1].Bottom;
		Quadrant[4].Left := Quadrant[2].Left;
		Quadrant[4].Top := Quadrant[2].Bottom;
end;



procedure DoShowBoxes(thickerMidBox: boolean);
	begin
		DrawBoxes(thickerMidBox);
		SetMode(MainWindow, Preliminary);
	end;

end.