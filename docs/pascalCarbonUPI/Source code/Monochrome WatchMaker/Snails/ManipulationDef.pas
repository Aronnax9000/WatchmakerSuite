unit ManipulationDef;

interface

	uses
		BoxesDefs,
		DevelopDefs,
		GeneboxDefs,
		Globals,
		LeftRightPosRung,
		MacOSAll,
		MessageDefs,
		Miscellaneous,
		MorphGlobals,
		PersonDefs,
		ReproduceDef,
		SnapDevelopDef;
	
	procedure Manipulation (MLoc: point);

implementation

PROCEDURE Manipulation (MLoc: point);
	VAR
		j, chosenbox: Integer;
	BEGIN
		ChosenBox := 0;
		IF Mloc.v < GeneBox[1].bottom THEN
			FOR j := 1 TO 16 DO
				IF PtInRect(MLoc, GeneBox[j]) THEN
					ChosenBox := j;
		j := ChosenBox;
		IF ChosenBox = 0 THEN
			SyringeMessage
		ELSE
			WITH child[special] DO
				BEGIN
					CASE ChosenBox OF
						1: 
							CASE LeftRightPos(Mloc, GeneBox[j]) OF
								LeftThird: 
									WOpening := Margarine(WOpening, -1);
								RightThird: 
									WOpening := Margarine(WOpening, 1);
								MidThird: 
									;
							END; {pos cases}
						2: 
							CASE LeftRightPos(Mloc, GeneBox[j]) OF
								LeftThird: 
									DDisplacement := DDisplacement - DMutSize;
								RightThird: 
									DDisplacement := DDisplacement + DMutSize;
								MidThird: 
									;
							END; {pos cases}
						3: 
							CASE LeftRightPos(Mloc, GeneBox[j]) OF
								LeftThird: 
									SShape := SShape - SMutSize;
								RightThird: 
									SShape := SShape + SMutSize;
								MidThird: 
									;
							END; {pos cases}
						4: 
							CASE LeftRightPos(Mloc, GeneBox[j]) OF
								LeftThird: 
									TTranslation := TTranslation - TMutSize;
								RightThird: 
									TTranslation := TTranslation + TMutSize;
								MidThird: 
									;
							END; {pos cases}
					END; {case ChosenBox}
{IF WOpening < =0 then WOpening:=0;}
					IF DDisplacement < 0 THEN
						DDisplacement := 0;
					IF DDisplacement > 100 THEN
						DDisplacement := 100;
				END;
		EraseRect(Box[MidBox]);
		ShowGeneBox(chosenbox, child[special]);
		Develop(child[special], centre[MidBox]);
	END; {Manipulation}		
end.