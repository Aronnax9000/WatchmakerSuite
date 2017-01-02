unit BiomorphCommandDef;

interface

	uses 
		BoxesDefs,
		Globals, MacOSAll, MorphGlobals, InitializeAnimalsDefs, EngineeringDefs, DevelopDefs,
		SetItemStateDefs, ModeDefs;
	
	procedure BiomorphCommand (commandId: UInt32);

implementation

	procedure BiomorphCommand (commandId: UInt32);
		BEGIN
			IF (commandId <> kSnailCmdBoxSideView) AND (commandId <> kSnailCmdAnimalCustomise) AND (theMode = engineering) THEN
				EraseRect(Prect);

			CASE commandId OF
				kSnailCmdBoxSideView: 				
					BEGIN
						SideView := NOT SideView;
						CheckMenuItem(MenuList[kWatchMenuIndexBox], kSnailMenuBoxSideView, SideView);
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;
(*
				kSnailCmdAnimalCustomise: 
					IF GenesDialog THEN
						BEGIN
							DoEngineer;
						END; *)
				kSnailCmdAnimalSnail: 
					BEGIN
						Child[special] := Snail;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalTurritella: 
					BEGIN
						Child[special] := Turritella;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalBivalve: 
					BEGIN
						Child[special] := Bivalve;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalAmmonite: 
					BEGIN
						Child[special] := Ammonite;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalNautilus: 
					BEGIN
						Child[special] := Nautilus;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalBrachiopod: 
					BEGIN
						Child[special] := Brachiopod;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalCone: 
					BEGIN
						Child[special] := Cone;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalWhelk: 
					BEGIN
						Child[special] := Whelk;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalScallop: 
					BEGIN
						Child[special] := Scallop;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalEloise: 
					BEGIN
						Child[special] := Eloise;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalGallaghers: 
					BEGIN
						Child[special] := Gallaghers;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalRapa: 
					BEGIN
						Child[special] := Rapa;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalLightning: 
					BEGIN
						Child[special] := Lightning;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalSundial: 
					BEGIN
						Child[special] := Sundial;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalFig: 
					BEGIN
						Child[special] := Fig;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalTun: 
					BEGIN
						Child[special] := Tun;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalRazorShell: 
					BEGIN
						Child[special] := RazorShell;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;

				kSnailCmdAnimalJapaneseWonder: 
					BEGIN
						Child[special] := JapaneseWonder;
						EraseRect(Box[MidBox]);
						Develop(Child[special], centre[MidBox]);
					END;
			END; {Cases}
		END;

end.