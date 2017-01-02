unit MenuGreyAdjustDefs;

interface

	uses 
		AlbumDefs,
		AlbumUtil, 
		CheckPastableDef,
		FossilsExistDefs, 
		Globals, 
		HIBoxTypes,
		InitializeMenusDef,
		MacOSAll, 
		ModeDefs,
		MorphGlobals,
		SerializationUtil, 
		SetItemStateDefs;

procedure MenuGreyAdjust;

implementation


procedure MorphMenuGreyAdjust;
	begin
			SetItemState(kMobiMenuIndexMutation, 2, Mut[1]);

	end;


procedure MenuGreyAdjust;

	var
		Br: boolean;
		forkSize: Int64;
	begin
{SetItemState(AM, 0, (TheMode <> Drifting) AND (TheMode <> Highlighting) AND (TheMode <> Albuming));  }
									{Don't know why the apple menu is disabled -Alun}
		OldMode := theMode;
		SetItemState(kWatchMenuIndexFile, kWatchMenuFileLoadAlbum, (Album.menagerieSize < MaxAlbum) AND NOT albumfull); {Load to Album}
		SetItemState(kWatchMenuIndexFile, kWatchMenuFileLoadFossils, TRUE);{(theMode=Breeding) AND (NOT Fossilizing)}
		{Load to SlideBox}
		SetItemState(kWatchMenuIndexFile, kWatchMenuFileSaveBiomorph, Special <> nil); {Save Biomorph}
		FSGetForkSize(Slides, forkSize);
		SetItemState(kWatchMenuIndexFile, kWatchMenuFileSaveFossils, WatchmakerFileSize(Slides) > 0);
		{Save Fossil File}
		SetItemState(kWatchMenuIndexFile, kWatchMenuFileSaveAlbum, ((NOT AlbumEmpty)));{ AND (TheMode=Albuming)}
                            {OR (TheMode=Moving)}
		SetItemState(kWatchMenuIndexFile, kWatchMenuFileCloseAlbum, (Album.menagerieSize > 0) AND (NOT AlbumEmpty) AND (TheMode = Albuming));{Close Album}
		SetItemState(kWatchMenuIndexEdit, kWatchMenuEditClear, (Special <> nil) AND (TheMode = Albuming));
		SetItemState(kWatchMenuIndexEdit, kWatchMenuEditCopy, (Special <> nil));
		CheckPastable;
		SetItemState(kWatchMenuIndexEdit, kWatchMenuEditPaste, Pastable OR ((frontwindow <> MainWindow) AND (FrontWindow <> PlaybackWindow)));
		{Highlight Biomorph:-}
		SetItemState(kWatchMenuIndexEdit, kWatchMenuEditHighlightBiom, ((TheMode = Breeding) OR (TheMode = Highlighting)));
		{Add to Album:-}
		SetItemState(kWatchMenuIndexEdit, kWatchMenuEditAddBiom, 
			(((TheMode = Highlighting) 
			OR (TheMode = engineering) 
			OR (TheMode = Triangling) 
			OR (TheMode = Phyloging) 
			OR (TheMode = Moving) 
			OR (TheMode = Detaching) 
			OR (TheMode = Breeding) 
			OR (TheMode = Randoming))) AND NOT AlbumFull);
		{Zoom:=}
		SetItemState(kWatchMenuIndexEdit, kWatchMenuEditShowAlbum, (Album.menagerieSize > 0) AND (NOT AlbumEmpty));
		SetItemState(kWatchMenuIndexOperation, kWatchMenuOperationBreed, Special <> nil);  {Breed}
		SetItemState(kWatchMenuIndexOperation, kWatchMenuOperationDrift, Special <> nil);  {Drift}
		SetItemState(kWatchMenuIndexOperation, kWatchMenuOperationEngineering, Special <> nil);  {3 Engineering}
		{4 Hopeful Monster}
		{5 Zero Fossil Record}

		{Replay Fossil Record}
		SetItemState(kWatchMenuIndexOperation, kWatchMenuOperationPlayFossil, (FossilsExist(Slides)) AND (theMode = Breeding));

		IF FossilsExist(Slides) THEN
			SetMenuItemText(MenuList[kWatchMenuIndexOperation], kWatchMenuOperationInitFossRec, 'Reinitialize Fossil Record')
		ELSE
			SetMenuItemText(MenuList[kWatchMenuIndexOperation], kWatchMenuOperationInitFossRec, 'Initialize Fossil Record');
		SetItemState(kWatchMenuIndexOperation, kWatchMenuOperationRecordFossil, FossilsExist(Slides));
		CheckMenuItem(MenuList[kWatchMenuIndexOperation], kWatchMenuOperationRecordFossil, Fossilizing); {Recording Fossils?}
		Br := (TheMode = Breeding) OR (TheMode = Highlighting) OR (TheMode = Preliminary) OR (TheMode = Drifting);

		SetItemState(kWatchMenuIndexBox, kWatchMenuBoxMoreRows, Br AND (NBoxes < MaxBoxes));
		SetItemState(kWatchMenuIndexBox, kWatchMenuBoxFewerRows, Br AND (NRows >= 3));
		SetItemState(kWatchMenuIndexBox, kWatchMenuBoxMoreColumns, Br AND (NBoxes < MaxBoxes));
		SetItemState(kWatchMenuIndexBox, kWatchMenuBoxFewerColumns, Br AND (NCols >= 3));
		SetItemState(kWatchMenuIndexBox, kWatchMenuBoxThickerPen, theMode = engineering);
		SetItemState(kWatchMenuIndexBox, kWatchMenuBoxThinnerPen, (theMode = engineering) AND (MyPenSize > 1));
		SetItemState(kWatchMenuIndexPedigree, kWatchMenuPedigreeDisplayPedigree, Special <> nil);
		SetItemState(kWatchMenuIndexPedigree, kWatchMenuPedigreeDrawOut, (TheMode = Moving) OR (TheMode = Detaching) OR (TheMode = Phyloging) OR (TheMode = Killing));
		SetItemState(kWatchMenuIndexPedigree, kWatchMenuPedigreeNoMirrors, TheMode = Phyloging);
		SetItemState(kWatchMenuIndexPedigree, kWatchMenuPedigreeSingleMirror, TheMode = Phyloging);
		SetItemState(kWatchMenuIndexPedigree, kWatchMenuPedigreeDoubleMirror, TheMode = Phyloging);
		SetItemState(kWatchMenuIndexPedigree, kWatchMenuPedigreeMove, (TheMode = Moving) OR (TheMode = Detaching) OR (TheMode = Phyloging) OR (TheMode = Killing));
		SetItemState(kWatchMenuIndexPedigree, kWatchMenuPedigreeDetach, (TheMode = Moving) OR (TheMode = Detaching) OR (TheMode = Phyloging) OR (TheMode = Killing));
		SetItemState(kWatchMenuIndexPedigree, kWatchMenuPedigreeKill, (TheMode = Moving) OR (TheMode = Detaching) OR (TheMode = Phyloging) OR (TheMode = Killing));
		SetItemState(kWatchMenuIndexPedigree, 11, FALSE);{(TheMode=Moving) OR (TheMode=Detaching) OR (TheMode=Phyloging) OR (TheMode=Killing)}
		{Gone? - ABC}
		SetItemState(kWatchMenuIndexHelp, kWatchMenuHelpCurrent, TRUE);
		SetItemState(kWatchMenuIndexHelp, kWatchMenuHelpMisc, TRUE);
		
		MorphMenuGreyAdjust;
	end; {MenuGreyAdjust}

end.