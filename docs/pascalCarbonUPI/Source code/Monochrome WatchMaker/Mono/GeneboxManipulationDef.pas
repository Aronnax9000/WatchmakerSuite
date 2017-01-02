unit GeneboxManipulationDef;

interface

	uses
		BoxesDefs,
		GeneboxTypes,
		
		Globals,
		HIBoxTypes,
		LeftRightPosRung,
		MacOSAll,
		MessageDefs,
		Miscellaneous,
		PersonDefs,
		SnapDevelopDef;
	
	procedure Manipulation (theButton: HIGeneBoxButtonPtr);

implementation


{FIXME do SyringeMessage if any breeding boxen are clicked while in Engineering mode - ABC}
	procedure Manipulation (theButton: HIGeneBoxButtonPtr);
		VAR
			refrain: Boolean;
			SizeWorry: LongInt;
			denizen: PersonPtr;
			GeneBoxNo: integer;
		begin

			denizen := theButton^.parent^.parent^.biomorph;
			GeneBoxNo := theButton^.parent^.GeneBoxNo;
			with denizen^ do
				begin
					case theButton^.buttonType of
						GBButtonLeft:
							case GeneBoxNo of
								1..8:
									gene[GeneBoxNo] := gene[GeneBoxNo] - MutSizeGene;
								9: 
									gene[9] := gene[9] - 1;
								10: 
									SegNoGene := SegNoGene - 1;
								11: 
									SegDistGene := SegDistGene - TrickleGene;
								12: 
									CompletenessGene := CompletenessTypeSingle;
								13: 
									SpokesGene := NorthOnly;
								14: 
									begin
										tricklegene := tricklegene - 1;
										IF tricklegene < 1 THEN
											tricklegene := 1;
									end;
								15: 
									begin
										MutSizegene := MutSizegene - 1;
										IF MutSizegene < 1 THEN
											MutSizegene := 1
									end;
								16: 
									begin
										MutProbGene := MutProbGene - 1;
										IF MutProbgene < 1 THEN
											MutProbgene := 1
									end;
								17:
									dGene[9] := Shrink;
							end;
						GBButtonTopRung: 
							case GeneBoxNo of
								1..8: 
									dGene[GeneBoxNo] := Swell;
								11: 
									dGene[10] := Swell;
							end;
						GBButtonMidRung: 
							case GeneBoxNo of
								1..8: 
									dGene[GeneBoxNo] := Same;
								11: 
									dGene[10] := Same;
								13: 
									SpokesGene := NSouth;
								17:
									dGene[9] := Same;
							end;
						GBButtonBotRung: 
							case GeneBoxNo of
								1..8: 
									dGene[GeneBoxNo] := Shrink;
								11: 
									dGene[10] := Shrink;
							end;
						GBButtonRight:
							case GeneBoxNo of
								1..8:
									gene[GeneBoxNo] := gene[GeneBoxNo] + MutSizeGene;
								9: 
									begin
										gene[9] := gene[9] + 1;
										SizeWorry := SegNoGene * TwoToThe(gene[9]);
										IF SizeWorry > WorryMax THEN
											Gene[9] := Gene[9] - 1;
									end;
								10: 
									begin
										SegNoGene := SegNoGene + 1;
										SizeWorry := SegNoGene * TwoToThe(gene[9]);
										IF SizeWorry > WorryMax THEN
											SegNoGene := SegNoGene - 1;
									end;
								11: 
									SegDistGene := SegDistGene + TrickleGene;
								12: 
									CompletenessGene := CompletenessTypeDouble;
								13: 
									SpokesGene := Radial;
								14: 
									tricklegene := tricklegene + 1;
								15: 
									MutSizegene := MutSizegene + 1;
								16: 
									begin
										MutProbGene := MutProbGene + 1;
										IF MutProbgene > 100 THEN
											MutProbgene := 100
									end;
								17:
									dGene[9] := Swell;
							end;
						otherwise
					end;
					IF Gene[9] < 1 THEN
						Gene[9] := 1;
					IF Gene[9] > MaxGene9 THEN
						Gene[9] := MaxGene9;
						
					IF SegNoGene < 1 THEN
						SegNoGene := 1;
					{Refrain means "refrain from drawing" meaning no phenotypic effect or some other weirdness.}
					{There was a guard here that prevents Gene 9 from effectively exceeding 12 - ABC}
					{Genes 15 and 16 are related to evolvability and have no immediate phenotypic effect - ABC}
					{Removed the check for (Gene[9] < 1) OR (SegNoGene < 1) OR since it is cleaned up already. - ABC}
					Refrain := (GeneBoxNo = 15) OR (GeneBoxNo = 16);
				end;
				{FIXME indicate that the genebox view] needs to be redrawn }
				HIViewSetNeedsDisplay(theButton^.parent^.view, true);
					
				if not refrain then
					begin
						HIViewSetNeedsDisplay(theButton^.parent^.parent^.parent^.boxes^.MidBox^.view, true);
						{Indicate that the HIBox needs to be redrawn, to render the new phenotype }
					end;
		
		end; {Manipulation}
		
end.