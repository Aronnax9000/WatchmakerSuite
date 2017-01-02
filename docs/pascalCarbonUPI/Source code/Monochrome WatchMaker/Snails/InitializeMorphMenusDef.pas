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
		FOR j := 6 TO MutTypeNo DO
			Mut[j] := FALSE;
		FOR j := 1 TO 5 DO
			Mut[j] := TRUE;				{** changed 1.1 **}

end;


	procedure BuildMorphSpecificMenuList;
		var windowMenu: MenuRef;
		begin
			CreateStandardWindowMenu (0, windowMenu);
			{Build MenuList from Menus loaded from resource fork, or created on the fly (in the case of the Window Menu)}
			MenuList[kWatchMenuIndexApple] := GetMenu(kWatchMenuApple);{ read menus in from resource fork  }
			MenuList[kWatchMenuIndexFile] := GetMenu(kWatchMenuFile);
			MenuList[kWatchMenuIndexEdit] := GetMenu(kWatchMenuEdit);
			MenuList[kWatchMenuIndexOperation] := GetMenu(kWatchMenuOper);
			MenuList[kWatchMenuIndexBox] := GetMenu(kWatchMenuBox);
			MenuList[kSnailMenuIndexAnimal] := GetMenu(kSnailMenuAnimal);
			MenuList[kWatchMenuIndexPedigree] := GetMenu(kWatchMenuPedigree);
			MenuList[kWatchMenuIndexWindow] := windowMenu;
			MenuList[kWatchMenuIndexHelp] := GetMenu(kWatchMenuHelp);
		end;
	
	procedure SetMorphMenuCommands;
		begin

			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox], kSnailMenuBoxSideView, kSnailCmdBoxSideView);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalCustomise, kSnailCmdAnimalCustomise);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalSnail, kSnailCmdAnimalSnail);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalTurritella, kSnailCmdAnimalTurritella);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalBivalve, kSnailCmdAnimalBivalve);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalAmmonite, kSnailCmdAnimalAmmonite);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalNautilus, kSnailCmdAnimalNautilus);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalBrachiopod, kSnailCmdAnimalBrachiopod);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalCone, kSnailCmdAnimalCone);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalWhelk, kSnailCmdAnimalWhelk);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalScallop, kSnailCmdAnimalScallop);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalEloise, kSnailCmdAnimalEloise);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalGallaghers, kSnailCmdAnimalGallaghers);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalRapa, kSnailCmdAnimalRapa);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalLightning, kSnailCmdAnimalLightning);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalSundial, kSnailCmdAnimalSundial);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalFig, kSnailCmdAnimalFig);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalTun, kSnailCmdAnimalTun);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalRazorShell, kSnailCmdAnimalRazorShell);
			SetMenuItemCommandID(MenuList[kSnailMenuIndexAnimal], kSnailMenuAnimalJapaneseWonder, kSnailCmdAnimalJapaneseWonder);
		end;


end.