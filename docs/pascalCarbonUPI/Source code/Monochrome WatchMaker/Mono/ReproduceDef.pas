unit ReproduceDef;

	interface
	uses
		Globals, 
		MacOSAll, 
		Miscellaneous,
		MorphGlobals,
		PersonDefs, 
		RandSwellDef;
	VAR
		min: Integer;

	procedure Reproduce (parent: personptr; child: personptr);
	
implementation

	procedure Reproduce (parent: personptr; child: personptr);
		VAR
			j: Integer;
			SizeWorry: Longint;
			mutProbGene: SInt16;
		function Direction: Integer;
			begin
				IF randint(2) = 2 THEN
					direction := PersonGetMutSizeGene(child)
				ELSE
					direction := -PersonGetMutSizeGene(child)
			end;
		function Direction9: Integer;
			begin
				IF randint(2) = 2 THEN
					direction9 := 1
				ELSE
					direction9 := -1
			end;

		begin
			child^ := parent^;
			IF Mut[Mut7MutationRate] and (RandInt100 < PersonGetMutProbGene(child)) THEN
				REPEAT
					PersonAddToMutProbGene(child, direction9);
				{Dawkins took the absolute value for the <= test (not necessary) and}
				{tested for inequality with zero. I relaxed the first check and }
				{strengthened the second (to formally disallow negative probabilities - ABC}
				UNTIL (PersonGetMutProbGene(child) <= 100) 
					AND (PersonGetMutProbGene(child) > 0);

			mutProbGene := PersonGetMutProbGene(child);

			FOR j := Gene1 TO Gene8 DO
				IF RandInt100 < mutProbGene THEN
					PersonAddToGene(child, j, direction);
					
			IF RandInt100 < mutProbGene THEN
				PersonAddToGene(child, Gene9, direction9);
			IF PersonGetGene(child, Gene9) < 1 THEN
				PersonSetGene(child, Gene9, 1);

			SizeWorry := PersonGetSegNoGene(child) * TwoToThe(PersonGetGene(child, Gene9));
			IF SizeWorry > WorryMax THEN
				PersonAddToGene(child, Gene9, -1);

			IF Mut[Mut1Segmentation] AND (RandInt(100) < mutProbGene) THEN
				begin
					j := direction9;
					PersonAddToSegNoGene(child, j);
					IF j > 0 THEN
						begin
							SizeWorry := PersonGetSegNoGene(child) * TwoToThe(PersonGetGene(child, Gene9));
							IF SizeWorry > WorryMax THEN
								PersonAddToSegNoGene(child, -1);
						end;
				end;
				
			
				
			IF (Mut[Mut2Gradient]) AND (PersonGetSegNoGene(child) > 1) THEN
				begin
					FOR j := DGene1 TO DGene8 DO
						IF RandInt100 < MutProbGene DIV 2 THEN
							PersonSetDGene(child, j, RandSwell(PersonGetDGene(child, j)));
					IF RandInt100 < MutProbGene DIV 2 THEN
						PersonSetDGene(child, DGene10, RandSwell(PersonGetDGene(child, DGene10)));
				end;
				
			IF Mut[Mut8TaperingTwigs] AND Mut[Mut9] AND (randInt100 < MutProbGene) THEN
				PersonSetDGene(child, DGene9, RandSwell(PersonGetDGene(child, DGene9)));
			
			IF Mut[Mut1Segmentation] AND (PersonGetSegNoGene(child) > 1) AND (RandInt100 < MutProbGene) THEN
				PersonAddToSegDistGene(child, Direction9);
				
			IF Mut[Mut3Asymmetry] AND (RandInt100 < MutProbGene DIV 2) THEN
				PersonToggleCompletenessGene(child);
											
			IF Mut[Mut4RadialSym] AND (RandInt100 < MutProbGene DIV 2) THEN
				CASE PersonGetSpokesGene(child) OF
					NorthOnly: 
						PersonSetSpokesGene(child, NSouth);
					NSouth: 
						begin
							IF Direction9 = 1 THEN
								PersonSetSpokesGene(child, Radial)
							ELSE
								PersonSetSpokesGene(child, NorthOnly);
						end;
					Radial: 
						PersonSetSpokesGene(child, NSouth)
				end;
				
			IF Mut[Mut5ScalingFactor] AND (RandInt100 < MutProbGene) THEN
				PersonAddToTrickleGene(child, direction9);
					
			IF Mut[Mut6MutationSize] AND (RandInt100 < MutProbGene) THEN
				PersonAddToMutSizeGene(child, direction9);
		end; {reproduce}

end.