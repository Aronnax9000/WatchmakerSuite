unit CheckPastableDef;

interface
	uses
		Globals, PersonDefs, ModeDefs,
		MacOSAll;
		
	procedure CheckPastable;
	function MemberIsSaveCandidate(biomorph: Person): Boolean;
implementation

	procedure CheckPastable;
		var j: integer;
		begin
			Pastable := FALSE;
			IF (TheMode = Albuming) THEN
				BEGIN {worth checking whether looking at vacant slot}
					j := 0;
					REPEAT
						j := j + 1;
					UNTIL (Album.Place[j].Page = CurrentPage) AND (Album.Place[j].BoxNo = Special);
					Pastable := TRUE
				END;
		end;
		
	function MemberIsSaveCandidate(biomorph: Person): Boolean;
		begin
			{FIXME Check and call from Serialization SaveAnimals}
			MemberIsSaveCandidate := false;
		end;
end.