unit ConcoctDefs;

interface

	uses Globals, MacOSAll, TriangleDefs, PersonDefs, Miscellaneous;

	procedure Concoct (r1, r2, r3: real; a, b, c: person; VAR new: person);

implementation


	procedure Concoct (r1, r2, r3: real; a, b, c: person; VAR new: person);
		VAR
			j: Integer;
		function Force3 (r: real): Integer;
			VAR
				i: Integer;
			begin
				i := round(r);
				IF i > 2 THEN
					i := 2;
				IF i < 0 THEN
					i := 0;
				Force3 := i
			end; {Force3}
		function Force2 (r: real): Integer;
			VAR
				i: Integer;
			begin
				i := round(r);
				IF i > 1 THEN
					i := 1;
				IF i < 0 THEN
					i := 0;
				Force2 := i
			end; {Force2}
		begin
			WITH new DO
				begin
					SegNoGene := round(r1 * a.SegNoGene + r2 * b.SegNoGene + r3 * c.SegNoGene);
					IF SegNoGene < 1 THEN
						SegNoGene := 1;
					SegDistGene := round(r1 * a.SegDistGene + r2 * b.SegDistGene + r3 * c.SegDistGene);
					CompletenessGene := CompletenessType(Force2(r1 * Integer(a.CompletenessGene) + r2 * Integer(b.CompletenessGene) + r3 * Integer(c.CompletenessGene)));
					SpokesGene := SpokesType(Force3(r1 * Integer(a.SpokesGene) + r2 * Integer(b.SpokesGene) + r3 * Integer(c.SpokesGene)));
					FOR j := 1 TO 9 DO
						gene[j] := round(r1 * a.gene[j] + r2 * b.gene[j] + r3 * c.gene[j]);
					SizeWorry := SegNoGene * TwoToThe(gene[9]);
					IF SizeWorry > WorryMax THEN
						Gene[9] := Gene[9] - 1;
					IF gene[9] < 1 THEN
						gene[9] := 1;
					tricklegene := round(r1 * a.tricklegene + r2 * b.tricklegene + r3 * c.tricklegene);
					MutSizeGene := round(r1 * a.MutSizeGene + r2 * b.MutSizeGene + r3 * c.MutSizeGene);
					MutProbGene := round(r1 * a.MutProbGene + r2 * b.MutProbGene + r3 * c.MutProbGene);
					IF mutprobgene < 1 THEN
						mutprobgene := 1;
					IF mutprobgene > 100 THEN
						mutprobgene := 100;
					FOR j := 1 TO 10 DO
						dgene[j] := swelltype(Force3(r1 * Integer(a.dgene[j]) + r2 * Integer(b.dgene[j]) + r3 * Integer(c.dgene[j])));
				end
		end; {concoct}
		
end.