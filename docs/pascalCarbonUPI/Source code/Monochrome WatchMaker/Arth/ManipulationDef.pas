unit ManipulationDef;

interface

	uses
		BoxesDefs,
		GeneboxDefs,
		Globals,
		LeftRightPosRung,
		MacOSAll,
		MessageDefs,
		Miscellaneous,
		PersonDefs,
		SnapDevelopDef;
	
	procedure Manipulation (MLoc: point);

implementation



	procedure Manipulation (MLoc: point);
		VAR
			j, chosenbox: Integer;
			refrain: Boolean;
		begin
			ChosenBox := 0;
			IF Mloc.v < GeneBox[1].bottom THEN
				FOR j := 1 TO 16 DO
					IF PtInRect(MLoc, GeneBox[j]) THEN
						ChosenBox := j;
			j := ChosenBox;
			IF ChosenBox = 0 THEN
				SyringeMessage
			ELSE
				begin
					WITH child[special] DO
						CASE ChosenBox OF
							1..8: 
								CASE LeftRightPos(Mloc, GeneBox[j]) OF
									LeftThird: 
										gene[j] := gene[j] - MutSizeGene;
									RightThird: 
										gene[j] := gene[j] + MutSizeGene;
									MidThird: 
										CASE Rung(Mloc, GeneBox[j]) OF
											TopRung: 
												dGene[j] := Swell;
											MidRung: 
												dGene[j] := Same;
											BottomRung: 
												dGene[j] := Shrink;
										end; {MidThird}
								end; {CASE 1..8}
							9: 
								CASE LeftRightPos(Mloc, GeneBox[j]) OF
									LeftThird: 
										gene[j] := gene[j] - 1;
									RightThird: 
										begin
											gene[j] := gene[j] + 1;
											SizeWorry := SegNoGene * TwoToThe(gene[9]);
											IF SizeWorry > WorryMax THEN
												Gene[9] := Gene[9] - 1;
										end;
									MidThird: 
										CASE Rung(Mloc, GeneBox[j]) OF
											TopRung: 
												dGene[j] := Swell;
											MidRung: 
												dGene[j] := Same;
											BottomRung: 
												dGene[j] := Shrink;
										end; {MidThird}
								end; {CASE 1..8}
							10: 
								CASE LeftRightPos(Mloc, GeneBox[10]) OF
									LeftThird: 
										SegNoGene := SegNoGene - 1;
									MidThird: 
										;   {No Action}
									RightThird: 
										begin
											SegNoGene := SegNoGene + 1;
											SizeWorry := SegNoGene * TwoToThe(gene[9]);
											IF SizeWorry > WorryMax THEN
												SegNoGene := SegNoGene - 1;
										end;
								end;
							11: 
								CASE LeftRightPos(Mloc, GeneBox[11]) OF
									LeftThird: 
										SegDistGene := SegDistGene - TrickleGene;
									MidThird: 
										CASE Rung(Mloc, GeneBox[j]) OF
											TopRung: 
												dGene[10] := Swell;
											MidRung: 
												dGene[10] := Same;
											BottomRung: 
												dGene[10] := Shrink;
										end; {MidThird}
									RightThird: 
										SegDistGene := SegDistGene + TrickleGene;
								end;
							12: 
								CASE LeftRightPos(Mloc, GeneBox[12]) OF
									LeftThird: 
										CompletenessGene := CompletenessTypeSingle;
									MidThird: 
										;    {No Action}
									RightThird: 
										CompletenessGene := CompletenessTypeDouble;
								end;
							13: 
								CASE LeftRightPos(Mloc, GeneBox[13]) OF
									LeftThird: 
										SpokesGene := NorthOnly;
									MidThird: 
										SpokesGene := NSouth;
									RightThird: 
										SpokesGene := Radial;
								end;
							14: 
								CASE LeftRightPos(Mloc, GeneBox[j]) OF
									LeftThird: 
										begin
											tricklegene := tricklegene - 1;
											IF tricklegene < 1 THEN
												tricklegene := 1;
										end;
									RightThird: 
										tricklegene := tricklegene + 1;
									MidThird: 
										; {No action}
								end;
							15: 
								CASE LeftRightPos(Mloc, GeneBox[j]) OF
									LeftThird: 
										begin
											MutSizegene := MutSizegene - 1;
											IF MutSizegene < 1 THEN
												MutSizegene := 1
										end;
									RightThird: 
										MutSizegene := MutSizegene + 1;
									MidThird: {No action}
								end;
							16: 
								CASE LeftRightPos(Mloc, GeneBox[j]) OF
									LeftThird: 
										begin
											MutProbGene := MutProbGene - 1;
											IF MutProbgene < 1 THEN
												MutProbgene := 1
										end;
									RightThird: 
										begin
											MutProbGene := MutProbGene + 1;
											IF MutProbgene > 100 THEN
												MutProbgene := 100
										end;
									MidThird: {No action}
								end;
						end;
					WITH child[special] DO
						begin
							Refrain := (Gene[9] > 12) OR (Gene[9] < 1) OR (SegNoGene < 1) OR (ChosenBox >= 15);{££££}
							IF Gene[9] < 1 THEN
								Gene[9] := 1;
							IF SegNoGene < 1 THEN
								SegNoGene := 1
						end;
					IF NOT Refrain THEN
						SnapDevelop(child[special], centre[MidBox]);
					ShowGeneBox(chosenbox, child[special]);
				end
		end; {Manipulation}
		
end.