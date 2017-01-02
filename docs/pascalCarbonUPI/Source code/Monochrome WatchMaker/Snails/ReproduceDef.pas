unit ReproduceDef;

interface
uses
	Globals, MacOSAll, Miscellaneous, PersonDefs, MorphGlobals;
VAR
	min: Integer;

procedure Reproduce (parent: person; VAR child: person);

{These three should probably go in their own unit - ABC}
FUNCTION Direction: Integer;
FUNCTION Direction9: Integer;
FUNCTION margarine (W: single; direction: integer): single;
	
implementation


FUNCTION margarine (W: single; direction: integer): single;
{we want to change by large amounts when low, small amounts when large}
	VAR
		m, logged, logchanged, WMutSize: single;
	BEGIN
		WMutSize := 0.1;
		logged := ln(W);
		logchanged := logged + WMutSize * direction;
		IF logchanged > 20 THEN
			logchanged := 20;
		m := exp(logchanged);
		IF m < 1 THEN
			m := 1;
		margarine := m
	END;

FUNCTION Direction: Integer;
	BEGIN
		IF randint(2) = 2 THEN
			direction := 1
		ELSE
			direction := -1
	END;
	
FUNCTION Direction9: Integer;
	VAR
		r: integer;
	BEGIN
		r := randint(5);
		CASE r OF
			5: 
				direction9 := 2;
			4: 
				direction9 := 1;
			3: 
				direction9 := 0;
			2: 
				direction9 := -1;
			1: 
				direction9 := -2;
			OTHERWISE
				direction9 := 0;
		END; {cases}
	END;


PROCEDURE Reproduce (parent: person; VAR child: person);
	VAR
		j: Integer;

	BEGIN
		child := parent;
		WITH child DO
			BEGIN
				BEGIN
					IF Randint(100) < MutProb THEN
						WOpening := Margarine(WOpening, direction);
					IF Randint(100) < MutProb THEN
						BEGIN
							DDisplacement := DDisplacement + direction9 * DMutSize;
							IF DDisplacement < 0 THEN
								DDisplacement := 0;
							IF DDisplacement > 1 THEN
								DDisplacement := 1;
						END;
					IF (Randint(100) < MutProb) AND SideView THEN
						BEGIN {Don't let Translation gene drift when you can't see its consequences}
							TTranslation := TTranslation + direction9 * TMutSize;
						END;
					IF (Randint(100) < 1) THEN
						Handedness := -Handedness;
				END;
			END
	END; {reproduce}

end.