unit ConcoctDefs;

interface

	uses Globals, MacOSAll, TriangleDefs, PersonDefs, Miscellaneous;

	{The segmented nature of Arthromorphs means that they cannot triangle: this routine does nothing.}
	procedure Concoct (r1, r2, r3: real; a, b, c: person; VAR new: person);

implementation

	procedure Concoct (r1, r2, r3: real; a, b, c: person; VAR new: person);
		begin
		end; {concoct}
		
end.