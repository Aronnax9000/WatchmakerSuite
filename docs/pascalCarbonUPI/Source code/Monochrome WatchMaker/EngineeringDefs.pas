unit EngineeringDefs;

interface
uses
	MacOSAll, 
	GeneboxDefs, 
	Globals, 
	BoxesDefs, 
	HIBoxDefs,
	DevelopDefs,
	ModeDefs;

procedure DoEngineer;

implementation

procedure DoEngineer;
	begin
		SetMode(MainWindow, engineering);
		{EraseRect(GetWindowPortBounds(MainWindow, mainWindowBounds)^);}
		{SetupBoxes(MainWindow);}
		{boxes^.midbox^.denizen := special^.denizen;}
		{special := boxes^.midbox;}
		{Delayvelop(special, boxes^.midbox^.midpoint);}
		{MakeGeneBox(special^.denizen, MainWindow); FIXME}
	end; {DoEngineer}
end.