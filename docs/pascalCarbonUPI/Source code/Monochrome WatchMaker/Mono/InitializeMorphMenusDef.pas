unit InitializeMorphMenusDef;

interface

	uses 
		Globals,
		MacOSAll,
		ModeDefs,
		MorphGlobals,
		SetItemStateDefs;


	procedure BuildMorphSpecificMenuList;
	procedure SetMorphMenuCommands;

	

procedure InitializeMutations;

implementation

	procedure InitializeMutations;
	var j: integer;
	begin
		FOR j := 6 TO 7 DO
			Mut[j] := FALSE;
		Mut[8] := true;
		Mut[9] := true;
		FOR j := 1 TO 5 DO
			Mut[j] := TRUE;				{** changed 1.1 **}

		FOR j := 1 TO MutTypeNo DO
			CheckMenuItem(MenuList[kMobiMenuIndexMutation], j, Mut[j]);
	end;



	procedure BuildMorphSpecificMenuList;
		var	windowMenu: MenuRef;

		begin
			CreateStandardWindowMenu (0, windowMenu);

			{Build MenuList from Menus loaded from resource fork, or created on the fly (in the case of the Window Menu)}
			MenuList[kWatchMenuIndexApple] := GetMenu(kWatchMenuApple);{ read menus in from resource fork  }
			MenuList[kWatchMenuIndexFile] := GetMenu(kWatchMenuFile);
			MenuList[kWatchMenuIndexEdit] := GetMenu(kWatchMenuEdit);
			MenuList[kWatchMenuIndexOperation] := GetMenu(kWatchMenuOperation);
			MenuList[kWatchMenuIndexBox] := GetMenu(kWatchMenuBox);
			MenuList[kMobiMenuIndexMutation] := GetMenu(kMobiMenuMutation);
			MenuList[kWatchMenuIndexPedigree] := GetMenu(kWatchMenuPedigree);
			MenuList[kWatchMenuIndexWindow] := windowMenu;
			MenuList[kWatchMenuIndexHelp] := GetMenu(kWatchMenuHelp);
		end;
		
	procedure SetMorphMenuCommands;
		begin
			{Morph-specific}
			
			
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxThickerPen, kWatchCmdBoxThickerPen);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxThinnerPen, kWatchCmdBoxThinnerPen);


			SetMenuItemCommandID(MenuList[kMobiMenuIndexMutation],  kMobiMenuMutationSegmentation, kMobiCmdMutationSegmentation);
			SetMenuItemCommandID(MenuList[kMobiMenuIndexMutation],  kMobiMenuMutationGradient, kMobiCmdMutationGradient);
			SetMenuItemCommandID(MenuList[kMobiMenuIndexMutation],  kMobiMenuMutationAsymmetry, kMobiCmdMutationAsymmetry);
			SetMenuItemCommandID(MenuList[kMobiMenuIndexMutation],  kMobiMenuMutationRadialSym, kMobiCmdMutationRadialSym);
			SetMenuItemCommandID(MenuList[kMobiMenuIndexMutation],  kMobiMenuMutationScalingFactor, kMobiCmdMutationScalingFactor);
			SetMenuItemCommandID(MenuList[kMobiMenuIndexMutation],  kMobiMenuMutationMutationSize, kMobiCmdMutationMutationSize);
			SetMenuItemCommandID(MenuList[kMobiMenuIndexMutation],  kMobiMenuMutationMutationRate, kMobiCmdMutationMutationRate);
			SetMenuItemCommandID(MenuList[kMobiMenuIndexMutation],  kMobiMenuMutationTaperingTwigs, kMobiCmdMutationTaperingTwigs);
		end;

end.