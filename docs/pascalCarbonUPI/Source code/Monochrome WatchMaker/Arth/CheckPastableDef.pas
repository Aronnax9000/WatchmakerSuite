unit CheckPastableDef;

interface
	uses
		Globals,
		PersonDefs,
		ModeDefs,
		MacOSAll;
	procedure CheckPastable;
	function MemberIsSaveCandidate(biomorph: Person): Boolean;
	
implementation

	procedure CheckPastable;
		var j: integer;
		begin

			Pastable := FALSE;
			IF (TheMode = Albuming) AND (CopiedAnimal.Gene[9] > 0) THEN
				begin {worth checking whether looking at vacant slot}
					j := 0;
					REPEAT
						j := j + 1;
					UNTIL (Album.Place[j].Page = CurrentPage) AND (Album.Place[j].BoxNo = Special);
					IF Album.member[j].Gene[9] = 0 THEN
						Pastable := TRUE
				end;
		end;
	
	function MemberIsSaveCandidate(biomorph: Person): Boolean;
	begin
		MemberIsSaveCandidate := biomorph.Gene[9] > 0;
	end;
end.