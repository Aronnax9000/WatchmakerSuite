unit LeftRightPosRung;

interface

uses MacOSAll;
type
	HorizPos = (LeftThird, MidThird, RightThird);
	VertPos = (TopRung, MidRung, BottomRung);

function LeftRightPos (MLoc: HIPoint; Box: HIRect): HorizPos;
function Rung (Mloc: HIPoint; Box: HIRect): VertPos;

implementation

function LeftRightPos (MLoc: HIPoint; Box: HIRect): HorizPos;
	var 
		boxWidth: CGFloat;
		thirdBoxWidth: CGFloat;
	begin
		boxWidth := box.size.width;
		thirdBoxWidth := boxWidth / 3;
		WITH Box DO
			IF MLoc.x < box.origin.x + thirdBoxWidth THEN
				LeftRightPos := LeftThird
			ELSE IF Mloc.x > boxWidth - thirdBoxWidth THEN
				LeftRightPos := RightThird
			ELSE
				LeftRightPos := MidThird
	end;

function Rung (Mloc: HIPoint; Box: HIRect): VertPos;
	var
		boxHeight: CGFloat;
		thirdBoxHeight: CGFloat;
	begin
		boxHeight := Box.size.height;
		thirdBoxHeight := boxHeight / 3;
		WITH Box DO
			IF MLoc.y < thirdBoxHeight THEN
				Rung := TopRung
			ELSE IF MLoc.y > boxHeight - thirdBoxHeight THEN
				Rung := BottomRung
			ELSE
				Rung := MidRung
	end;


end.

