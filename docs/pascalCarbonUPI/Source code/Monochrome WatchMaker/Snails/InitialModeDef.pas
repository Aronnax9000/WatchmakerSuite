unit InitialModeDef;

interface

	uses MacOSAll, EngineeringDefs, Globals, ModeDefs, InitializeAnimalsDefs;

	procedure InitialMode;


implementation

	procedure InitialMode;
		begin
			Child[special] := snail;
			SetMode(MainWindow, engineering);
			DoEngineer;
		end;

end.