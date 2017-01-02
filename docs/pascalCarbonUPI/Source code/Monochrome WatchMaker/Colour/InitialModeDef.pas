unit InitialModeDef;

interface

	uses MacOSAll, EngineeringDefs, Globals, ModeDefs, EvolveDefs, InitializeAnimalsDefs;

	procedure InitialMode;


implementation

	procedure InitialMode;
		begin
			DoBreed;
		end;

end.