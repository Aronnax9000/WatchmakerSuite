unit BiomorphCommandDef;

interface

	uses Globals, MacOSAll, MorphGlobals,
		SetItemStateDefs;
	
	procedure BiomorphCommand (commandId: UInt32);

implementation

	
	procedure DoMutationMenuCommands(item: UInt32);
	begin
		ClipBoarding := FALSE;
		CheckMenuItem(MenuList[kMobiMenuIndexMutation], Item, NOT Mut[Item]);
		Mut[Item] := NOT Mut[Item];
		SetItemState(kMobiMenuIndexMutation, 2, Mut[1]);
		IF NOT Mut[1] THEN
			begin
				Mut[2] := FALSE;
				CheckMenuItem(MenuList[kMobiMenuIndexMutation], 2, FALSE)
			end
	end;

	procedure BiomorphCommand (commandId: UInt32);
		begin
			case commandId of
				kMobiCmdMutationSegmentation: DoMutationMenuCommands(kMobiMenuMutationSegmentation);
				kMobiCmdMutationGradient: DoMutationMenuCommands(kMobiMenuMutationGradient);
				kMobiCmdMutationAsymmetry: DoMutationMenuCommands(kMobiMenuMutationAsymmetry);
				kMobiCmdMutationRadialSym: DoMutationMenuCommands(kMobiMenuMutationRadialSym);
				kMobiCmdMutationScalingFactor: DoMutationMenuCommands(kMobiMenuMutationScalingFactor);
				kMobiCmdMutationMutationSize: DoMutationMenuCommands(kMobiMenuMutationMutationSize);
				kMobiCmdMutationMutationRate: DoMutationMenuCommands(kMobiMenuMutationMutationRate);
				kMobiCmdMutationTaperingTwigs: DoMutationMenuCommands(kMobiMenuMutationTaperingTwigs);
				
			end;
		end;

end.