unit PedigreeTypes;

interface

	uses 
		MacOSAll,
		PersonDefs;

	TYPE

		FullPtr = ^Full;
		FullHandle = ^FullPtr;
		Full = RECORD
				genome: person;
				surround: HIRect;
				origin, centre: HIPoint;
				parent: FullHandle;
				firstborn: FullHandle;
				lastborn: FullHandle;
				eldersib: FullHandle;
				youngersib: FullHandle;
				Prec, Next: FullHandle;
				Damaged{,Blackened}: Boolean;
				snapHandle: Handle;
				snapBytes: Integer;
				snapBounds: HIRect;
			end;
		GodPtr = ^God;
		GodHandle = ^GodPtr;
		God = RECORD
				Adam: FullHandle;
				PreviousGod, NextGod: GodHandle;
			end;
			
	var
		SpecialFull, OldSpecialFull, RunningFull, ThatFull: FullHandle;
		FullSize: Size;
		RootGod, theGod, SaveGod, HitListHead: GodHandle;


implementation

end.