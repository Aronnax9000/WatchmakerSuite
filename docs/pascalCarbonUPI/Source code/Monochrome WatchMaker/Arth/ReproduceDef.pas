unit ReproduceDef;

interface
uses
	Globals, MacOSAll, Miscellaneous,
	MorphGlobals,
	PersonDefs, RandSwellDef;
VAR
	min: Integer;

procedure Reproduce (parent: person; VAR child: person);
	
implementation

procedure Reproduce (parent: person; VAR child: person);
	VAR
		j: Integer;
	function Direction: Integer;
		begin
			IF randint(2) = 2 THEN
				direction := child.MutSizegene
			ELSE
				direction := -child.MutSizegene
		end;
	function Direction9: Integer;
		begin
			IF randint(2) = 2 THEN
				direction9 := 1
			ELSE
				direction9 := -1
		end;

	begin
		child := parent;
		WITH child DO
			begin
				IF Mut[7] THEN
					IF Randint(100) < MutProbGene THEN
						begin
							REPEAT
								MutProbGene := MutProbGene + direction9;
							UNTIL (abs(MutProbGene) <= 100) AND (MutProbGene <> 0);
						end;
				FOR j := 1 TO 8 DO
					IF Randint(100) < MutProbGene THEN
						Gene[j] := Gene[j] + direction;
				IF Randint(100) < MutProbGene THEN
					Gene[9] := Gene[9] + direction9;
				IF Gene[9] < 1 THEN
					Gene[9] := 1;
				SizeWorry := SegNoGene * TwoToThe(gene[9]);
				IF SizeWorry > WorryMax THEN
					Gene[9] := Gene[9] - 1;
				IF Mut[1] THEN
					IF RandInt(100) < MutProbGene THEN
						begin
							j := direction9;
							SegNoGene := SegNoGene + j;
							IF j > 0 THEN
								begin
									SizeWorry := SegNoGene * TwoToThe(gene[9]);
									IF SizeWorry > WorryMax THEN
										SegNoGene := SegNoGene - 1;
								end;
						end;
				IF SegNoGene < 1 THEN
					SegNoGene := 1;
				IF (Mut[2]) AND (SegNoGene > 1) THEN
					begin
						FOR j := 1 TO 8 DO
							IF Randint(100) < MutProbGene DIV 2 THEN
								dGene[j] := RandSwell(dgene[j]);
						IF Randint(100) < MutProbGene DIV 2 THEN
							dGene[10] := RandSwell(dgene[10]);
					end;
				IF Mut[8] THEN
					IF (Mut[9] AND (randint(100) < MutProbGene)) THEN
						dGene[9] := RandSwell(dGene[9]);
				IF (Mut[1]) AND (SegNoGene > 1) THEN
					IF Randint(100) < MutProbGene THEN
						SegDistGene := SegDistGene + Direction9;
				IF Mut[3] THEN
					IF Randint(100) < MutProbGene DIV 2 THEN
						IF CompletenessGene = CompletenessTypeSingle THEN
							CompletenessGene := CompletenessTypeDouble
						ELSE
							CompletenessGene := CompletenessTypeSingle;
				IF Mut[4] THEN
					IF Randint(100) < MutProbGene DIV 2 THEN
						CASE SpokesGene OF
							NorthOnly: 
								SpokesGene := NSouth;
							NSouth: 
								begin
									IF Direction9 = 1 THEN
										SpokesGene := Radial
									ELSE
										SpokesGene := NorthOnly
								end;
							Radial: 
								SpokesGene := NSouth
						end;
				IF Mut[5] THEN
					IF Randint(100) < abs(MutProbGene) THEN
						begin
							TrickleGene := Tricklegene + direction9;
							IF TrickleGene < 1 THEN
								TrickleGene := 1
						end;
				IF Mut[6] THEN
					IF Randint(100) < abs(MutProbGene) THEN
						begin
							MutSizeGene := MutSizeGene + direction9;
							IF MutSizeGene < 1 THEN
								MutSizeGene := 1
						end;
			end
	end; {reproduce}

end.