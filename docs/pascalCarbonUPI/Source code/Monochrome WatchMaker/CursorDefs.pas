unit CursorDefs;

interface

	uses MacOSAll;
	
	const
		{CURSORS > made these contiguous numbers so that they can be read in a loop and checked - Alun}
		leftCursID = 135;
		leftCursor = 5;
		rightCursID = 136;
		rightCursor = 6;
		UpCursId = 137;
		UpCursor = 7;
		DownCursID = 138;
		DownCursor = 8;
		EqCursID = 139;
		EqCursor = 9;
		InjectCursID = 140;
		InjectCursor = 10;
		QCursID = 141;
		QCursor = 11;
		BlackCursId = 142;
		BlackCursor = 12;
		BoxCursID = 143;
		BoxCursor = 13;
		RandCursID = 144;
		RandCursor = 14;
		BreedCursID = 145;
		BreedCursor = 15;
		HandCursID = 146;
		HandCursor = 16;
		DrawOutCursID = 147;
		DrawOutCursor = 17;
		ScissorCursId = 148;
		ScissorCursor = 18;
		GunCursID = 149;
		GunCursor = 19;
		LensCursID = 150;
		LensCursor = 20;
		BlankCursID = 151;
		BlankCursor = 21;
	type
		CursorList = ARRAY[iBeamCursor..BlankCursor] OF CursHandle;

	var
	  { ******** Screen stuff ************}
		CursList: CursorList;   					{ used to hold cursor handles }
		theCursor: Cursor;

	procedure LoadCursors;
	
	
implementation
	{* Load Cursors from resources.   v1.1 renumbered cursors to allow looping. }
	{* We no longer lock handles, just prevent them from being purged. *}
	{* Note for OS X version (March 2015: these cursors are loaded from a .r (Rez) resource file, incredibly. *}
	procedure LoadCursors;
		VAR
			indx: Integer;
		begin
			FOR Indx := iBeamCursor TO watchCursor DO					{ get four standard system cursors}
				begin
					CursList[Indx] := GetCursor(Indx); 							{ read in from system resource}
					HNoPurge(Handle(CursList[Indx]));
				end;

			FOR indx := leftCursID TO BlankCursID DO						{Now load custom cursors}
				begin
					CursList[indx - 130] := GetCursor(indx);
					HNoPurge(Handle(CursList[indx - 130]));					{make sure they don't get purged}
				end;
			SetCursor(CursList[watchCursor]^^);								{ bring up watch cursor}
		end;	{LoadCursors}

end.