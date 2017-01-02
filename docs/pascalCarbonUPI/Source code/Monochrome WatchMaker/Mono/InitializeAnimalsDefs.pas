unit InitializeAnimalsDefs;

interface

	uses Globals, HIBoxTypes, MacOsAll, PersonDefs, DevelopDefs;

	procedure InitializeAnimals(boxes: HIBoxesPtr);

implementation

	procedure MakeGenes (genotype: PersonPtr; a, b, c, d, e, f, g, h, i: Integer);
		VAR
			j: Integer;
		begin

			FOR j := 1 TO 10 DO
				genotype^.dgene[j] := same;
			genotype^.SegNoGene := 1;
			genotype^.SegDistGene := 150;
			genotype^.CompletenessGene := CompletenessTypeDouble;
			genotype^.SpokesGene := NorthOnly;
			genotype^.TrickleGene := Trickle;
			genotype^.MutSizeGene := Trickle DIV 2;
			genotype^.MutProbGene := 10;
			genotype^.gene[1] := a;
			genotype^.gene[2] := b;
			genotype^.gene[3] := c;
			genotype^.gene[4] := d;
			genotype^.gene[5] := e;
			genotype^.gene[6] := f;
			genotype^.gene[7] := g;
			genotype^.gene[8] := h;
			genotype^.gene[9] := i;

		end; {makegenes}

	procedure Chess (genotype: PersonPtr);
		begin
			makegenes(genotype, 
				-trickle, 
				3 * trickle, 
				-3 * trickle, 
				-3 * trickle, 
				trickle, 
				-2 * trickle, 
				6 * trickle, 
				-5 * trickle, 
				7);
		end; {chess}


	procedure BasicTree (genotype: PersonPtr);
		begin
			makegenes(genotype, -10, -20, -20, -15, -15, 0, 15, 15, 7);

			genotype^.SegNoGene := 2;
			genotype^.SegDistGene := 150;
			genotype^.CompletenessGene := CompletenessTypeSingle;
			genotype^.dgene[4] := shrink;
			genotype^.dgene[5] := shrink;
			genotype^.dgene[6] := shrink;
			genotype^.dgene[9] := shrink;
			genotype^.tricklegene := 9;

		end; {root}


	procedure Insect (genotype: PersonPtr);
		begin
			makegenes(genotype, trickle, trickle, -4 * trickle, trickle, -trickle, -2 * trickle, 8 * trickle, -4 * trickle, 6);
		end; {insect}

	procedure InitializeAnimals(boxes: HIBoxesPtr);
		begin
			New(topan);
			BasicTree(topan);   {3 defaults if all else fails}
			New(leftan);
			Insect(leftan);
			New(rightan);
			Chess(rightan);
			new(Target);
			Insect(target);
			new(CopiedAnimal);
			BasicTree(CopiedAnimal);
			CopiedAnimal^.Gene[9] := 0;
			Special := boxes^.MidBox;
			BasicTree(Special^.denizen);
		end;
end.