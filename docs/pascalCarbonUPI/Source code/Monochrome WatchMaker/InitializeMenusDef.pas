unit InitializeMenusDef;

interface

	uses 
		Globals,
		HIBoxTypes,
		MacOSAll,
		InitializeMorphMenusDef,
		ModeDefs,
		MorphGlobals,
		SetItemStateDefs;

	procedure InitializeMenus;

		procedure SmallMenus;
	procedure LargeMenus;
	procedure OwnEditMenu;
implementation
	procedure SmallMenus;
	var windowMenu: MenuRef;
		begin
			ClearMenuBar;
			InsertMenu(MenuList[kWatchMenuIndexApple], 0);			 {This was commented out - Alun}
			InsertMenu(SpecialBreedMenu, 0);
			InsertMenu(MenuList[kWatchMenuIndexHelp], 0);
			CreateStandardWindowMenu (0, windowMenu);
			InsertMenu(windowMenu, 0);

			DrawMenuBar;                   { draw updated menu bar to screen   }
		end; {SmallMenus}


	procedure LargeMenus;
		VAR
			indx: Integer;
		begin
			ClearMenuBar;
			FOR Indx := 1 TO MenuCnt DO       { place menus in menu bar           }
				InsertMenu(MenuList[Indx], 0);
			OwnEditMenu;
			DrawMenuBar;                   { draw updated menu bar to screen   }
		end; {LargeMenus}


	procedure OwnEditMenu;
		begin
			SetItemState(kWatchMenuIndexEdit, 1, FALSE);
			SetItemState(kWatchMenuIndexEdit, 2, FALSE);
			SetItemState(kWatchMenuIndexEdit, 3, FALSE);
			SetItemState(kWatchMenuIndexEdit, 4, (Special <> nil) AND ((TheMode = Highlighting) OR (TheMode = Albuming)));
			SetItemState(kWatchMenuIndexEdit, 5, FALSE);
			SetItemState(kWatchMenuIndexEdit, 6, FALSE);
		end; {OwnEditMenu}

	procedure InitializeMenus;
		var j: integer;
		begin
			BuildMorphSpecificMenuList;

			{Track our special Breeding Menu}
			SpecialBreedMenu := GetMenu(kWatchMenuSpec);
			
			{Set Menu Command IDs}
			SetMenuItemCommandID(MenuList[kWatchMenuIndexApple],  kWatchMenuAppleAbout, kWatchCmdAppleAbout);
			
			SetMenuItemCommandID(MenuList[kWatchMenuIndexFile],  kWatchMenuFileLoadAlbum, kWatchCmdFileLoadAlbum);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexFile],  kWatchMenuFileLoadFossils, kWatchCmdFileLoadFossils);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexFile],  kWatchMenuFileSaveBiomorph, kWatchCmdFileSaveBiomorph);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexFile],  kWatchMenuFileSaveFossils, kWatchCmdFileSaveFossils);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexFile],  kWatchMenuFileSaveAlbum, kWatchCmdFileSaveAlbum);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexFile],  kWatchMenuFileCloseAlbum, kWatchCmdFileCloseAlbum);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexFile],  kWatchMenuFileQuit, kWatchCmdFileQuit);
			
			SetMenuItemCommandID(MenuList[kWatchMenuIndexEdit],  kWatchMenuEditUndo, kWatchCmdEditUndo);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexEdit],  kWatchMenuEditCut, kWatchCmdEditCut);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexEdit],  kWatchMenuEditCopy, kWatchCmdEditCopy);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexEdit],  kWatchMenuEditPaste, kWatchCmdEditPaste);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexEdit],  kWatchMenuEditClear, kWatchCmdEditClear);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexEdit],  kWatchMenuEditHighlightBiom, kWatchCmdEditHighlightBiom);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexEdit],  kWatchMenuEditAddBiom, kWatchCmdEditAddBiom);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexEdit],  kWatchMenuEditShowAlbum, kWatchCmdEditShowAlbum);

			SetMenuItemCommandID(MenuList[kWatchMenuIndexOperation],  kWatchMenuOperationBreed, kWatchCmdOperationBreed);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexOperation],  kWatchMenuOperationDrift, kWatchCmdOperationDrift);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexOperation],  kWatchMenuOperationEngineering, kWatchCmdOperationEngineering);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexOperation],  kWatchMenuOperationHopeMonster, kWatchCmdOperationHopeMonster);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexOperation],  kWatchMenuOperationInitFossRec, kWatchCmdOperationInitFossRec);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexOperation],  kWatchMenuOperationPlayFossil, kWatchCmdOperationPlayFossil);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexOperation],  kWatchMenuOperationRecordFossil, kWatchCmdOperationRecordFossil);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexOperation],  kWatchMenuOperationTriangle, kWatchCmdOperationTriangle);

			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxMoreRows, kWatchCmdBoxMoreRows);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxFewerRows, kWatchCmdBoxFewerRows);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxMoreColumns, kWatchCmdBoxMoreColumns);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxFewerColumns, kWatchCmdBoxFewerColumns);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxDriftSweep, kWatchCmdBoxDriftSweep);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxTriangleTop, kWatchCmdBoxTriangleTop);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxTriangleLeft, kWatchCmdBoxTriangleLeft);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexBox],  kWatchMenuBoxTriangleRight, kWatchCmdBoxTriangleRight);			

			SetMenuItemCommandID(MenuList[kWatchMenuIndexPedigree],  kWatchMenuPedigreeDisplayPedigree, kWatchCmdPedigreeDisplayPedigree);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexPedigree],  kWatchMenuPedigreeDrawOut, kWatchCmdPedigreeDrawOut);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexPedigree],  kWatchMenuPedigreeNoMirrors, kWatchCmdPedigreeNoMirrors);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexPedigree],  kWatchMenuPedigreeSingleMirror, kWatchCmdPedigreeSingleMirror);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexPedigree],  kWatchMenuPedigreeDoubleMirror, kWatchCmdPedigreeDoubleMirror);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexPedigree],  kWatchMenuPedigreeMove, kWatchCmdPedigreeMove);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexPedigree],  kWatchMenuPedigreeDetach, kWatchCmdPedigreeDetach);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexPedigree],  kWatchMenuPedigreeKill, kWatchCmdPedigreeKill);

			SetMenuItemCommandID(MenuList[kWatchMenuIndexHelp],  kWatchMenuHelpCurrent, kWatchCmdHelpCurrent);
			SetMenuItemCommandID(MenuList[kWatchMenuIndexHelp],  kWatchMenuHelpMisc, kWatchCmdHelpMisc);

			SetMenuItemCommandID(SpecialBreedMenu,  kWatchMenuSpecClosePlayback, kWatchCmdSpecClosePlayback);
			SetMenuItemCommandID(SpecialBreedMenu,  kWatchMenuSpecBreedCurrent, kWatchCmdSpecBreedCurrent);
			SetMenuItemCommandID(SpecialBreedMenu,  kWatchMenuSpecQuitPlayback, kWatchCmdSpecQuitPlayback);


			SetMorphMenuCommands;

			
			{Display main menu}
			LargeMenus;

			{Handle initial menu states}
			CheckMenuItem(MenuList[kWatchMenuIndexPedigree], kWatchMenuPedigreeNoMirrors, TRUE);
			CheckMenuItem(MenuList[kWatchMenuIndexPedigree], kWatchMenuPedigreeSingleMirror, FALSE);
			CheckMenuItem(MenuList[kWatchMenuIndexPedigree], kWatchMenuPedigreeDoubleMirror, FALSE);
			
			SetItemState(kWatchMenuIndexEdit, kWatchMenuEditPaste, FALSE);
			FOR j := 1 TO 6 DO
				SetItemState(kWatchMenuIndexEdit, j, FALSE);

			{OS X provdes the built-in application menu, so we don't need the Quit items from the resource's File Menu}
			DeleteMenuItem(MenuList[kWatchMenuIndexFile], kWatchMenuFileQuit);
			DeleteMenuItem(SpecialBreedMenu, kWatchMenuSpecQuitPlayback);

			
		end;


end.