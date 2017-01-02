unit BarnYardDefs;

interface
	uses MacOSAll, PersonDefs;

	function PersonCreate(var newPerson: PersonPtr): PersonPtr;
	procedure PersonDestroy(victim: PersonPtr);
	
implementation

	var BarnYard: PersonNodePtr = nil;
	var lastPersonInBarnYard: PersonNodePtr = nil;
	
	function PersonCreate(var newPerson: PersonPtr): PersonPtr;
		var node: PersonNodePtr;
		begin
			New(newPerson);
			newPerson^.gene[9] := 0;
			New(node);
			node^.biomorph := newPerson;
			node^.nextSibling := nil;
			if BarnYard = nil then
				begin
					BarnYard := node;
				end
			else
				begin
					lastPersonInBarnYard^.nextSibling := node;
				end;
			lastPersonInBarnYard := node;

			PersonCreate := newPerson;
		end;
	
	{Remove the "victim" from the BarnYard linked list.}
	procedure PersonDestroy(victim: PersonPtr);
		var 
			candidateVictim: PersonNodePtr;
			candidateVictimPreviousSibling: PersonNodePtr = nil;
		begin
			candidateVictim := BarnYard; {oldest chicken gets it first (it's a mercy.) - ABC }
			{Go down the line. "Papers, please." - ABC}
			while (candidateVictim <> nil) and (victim <> candidateVictim^.biomorph) do 
				begin
					{Before moving on to the next, remember the one we just checked,}
					{so we can stitch up the nextSibling link later.}
					candidateVictimPreviousSibling := candidateVictim;
					candidateVictim := candidateVictim^.nextSibling;
				end;
				
			
			if victim = candidateVictim^.biomorph then {Shoulda stayed in bed.}
			
				begin
					{If the victim came from anywhere but the head of the line}	
					if candidateVictimPreviousSibling <> nil then
						{Make its previous sibling aware of the next of kin of the poor sod. - ABC}
						candidateVictimPreviousSibling^.nextSibling := candidateVictim^.nextSibling
					else
						{A moment of silence to commemorate the passing of the matriarch. - ABC}
						BarnYard := candidateVictim^.nextSibling;

					if candidateVictim = lastPersonInBarnYard then
						lastPersonInBarnYard := candidateVictimPreviousSibling;

					{Fear no more the heat of the sun.}
					Dispose(victim);
					{And ditto for the node she rode in on.}
					Dispose(candidateVictim)
				end
		end;
		

end.