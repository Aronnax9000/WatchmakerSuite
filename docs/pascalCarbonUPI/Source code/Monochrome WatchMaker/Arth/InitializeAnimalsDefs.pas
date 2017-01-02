unit InitializeAnimalsDefs;

interface

	uses Globals,  MacOsAll, PersonDefs;

	procedure InitializeAnimals;

implementation

	procedure MakeGenes (VAR genotype: person; a, b, c, d, e, f, g, h, i: Integer);
		VAR
			j: Integer;
		begin
			WITH genotype DO
				begin
					FOR j := 1 TO 10 DO
						dgene[j] := same;
					SegNoGene := 1;
					SegDistGene := 150;
					CompletenessGene := CompletenessTypeDouble;
					SpokesGene := NorthOnly;
					TrickleGene := Trickle;
					MutSizeGene := Trickle DIV 2;
					MutProbGene := 10;
					gene[1] := a;
					gene[2] := b;
					gene[3] := c;
					gene[4] := d;
					gene[5] := e;
					gene[6] := f;
					gene[7] := g;
					gene[8] := h;
					gene[9] := i;
				end;
		end; {makegenes}

	procedure Chess (VAR genotype: person);
		begin
			makegenes(genotype, -trickle, 3 * trickle, -3 * trickle, -3 * trickle, trickle, -2 * trickle, 6 * trickle, -5 * trickle, 7);
		end; {chess}


	procedure BasicTree (VAR genotype: person);
		begin
			makegenes(genotype, -10, -20, -20, -15, -15, 0, 15, 15, 7);
			WITH genotype DO
				begin
					SegNoGene := 2;
					SegDistGene := 150;
					CompletenessGene := CompletenessTypeSingle;
					dgene[4] := shrink;
					dgene[5] := shrink;
					dgene[6] := shrink;
					dgene[9] := shrink;
					tricklegene := 9;
				end;
		end; {root}


	procedure Insect (VAR genotype: person);
		begin
			makegenes(genotype, trickle, trickle, -4 * trickle, trickle, -trickle, -2 * trickle, 8 * trickle, -4 * trickle, 6);
		end; {insect}

	procedure InitializeAnimals;
		begin
			BasicTree(topan);   {3 defaults if all else fails}
			Insect(leftan);
			Chess(rightan);
			Insect(target);
			BasicTree(CopiedAnimal);
			CopiedAnimal.Gene[9] := 0;
			Special := MidBox;
			BasicTree(Child[Special]);
		end;
end.