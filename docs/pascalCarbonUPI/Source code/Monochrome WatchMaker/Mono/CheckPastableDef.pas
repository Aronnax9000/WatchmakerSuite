unit CheckPastableDef;

interface
	uses
		AlbumDefs,
		Globals,
		HIBoxTypes,
		PersonDefs,
		ModeDefs,
		MacOSAll;
	procedure CheckPastable;
	function MemberIsSaveCandidate(biomorph: PersonPtr): Boolean;
	
implementation

	procedure CheckPastable;
		var j: integer;
		begin

			Pastable := FALSE;
			IF (TheMode = Albuming) AND (CopiedAnimal^.Gene[9] > 0) THEN
				begin {worth checking whether looking at vacant slot}
					j := 0;
					REPEAT
						j := j + 1;
					UNTIL (Album.Place[j].Page = CurrentPage) AND (Album.Place[j].BoxNo = Special);
					IF Album.member[j]^.denizen^.Gene[9] = 0 THEN
						Pastable := TRUE
				end;
		end;
	
	function MemberIsSaveCandidate(biomorph: PersonPtr): Boolean;
	begin
		MemberIsSaveCandidate := biomorph^.Gene[9] > 0;
	end;
end.