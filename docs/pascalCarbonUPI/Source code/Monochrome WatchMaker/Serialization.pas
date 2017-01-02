unit Serialization;

interface
	uses
	AlbumDefs,
	HIBoxTypes,
	MacOSAll, 
	Globals, Miscellaneous, SerializationUtil, 
	DevelopDefs,
	PersonSerialization, CreateHistoryFileCarbon, 
	PersonDefs, ModeDefs, CheckPastableDef, BoxesDefs;

	procedure DoSave (HowMuch: Integer);
	procedure ReadAnimals (VAR ThisMenagerie: Menagerie);
	procedure SaveSlides;
	procedure ResetFossils;
implementation

uses  ErrorUnit, AlbumPlayback;
{FIXME Carbonize}
procedure SaveAnimals (ThisMenagerie: Menagerie; Foss: Boolean);
		{In the case of a single biomorph, Child[special] has already been}
{		placed in ThisMenagerie [ 1 ] }

	VAR
		
		j: Integer;
		MyFileType: OSType;
		bytesToSave: Longint;
		thePrompt: StringHandle;
		perSerializer: PersonSerializer;
		SizeOfPersonSerializer: longint;

		length: CFIndex;
		OurPrompt: CFStringRef;
		status: OSStatus;
		ThisFile: SInt16;
		dialogOptions: NavDialogCreationOptions;
		outDialog: NavDialogRef;
		outReply: NavReplyRecord;
		firstAEDesc: AEDesc;
		fileRef: FSRef;
		dataForkName: HFSUniStr255;
		saveFileName: HFSUniStr255;
		actualCount: ByteCount;
		userAction: NavUserAction;
	begin
		DotheSave := FALSE;
		IF Foss THEN
			ThePrompt := GetString(9520) {Save Fossils}
		ELSE
			begin
				IF ThisMenagerie.menagerieSize = 1 THEN
					ThePrompt := GetString(3866) {Save Biomorph}
				ELSE
					ThePrompt := GetString(10281) {Save Album}
			end;
		IF ThisMenagerie.menagerieSize = 1 THEN
			MyFiletype := 'BIOM'
		ELSE
			MyFiletype := 'COLL';		

		OurPrompt := CFStringCreateWithPascalString(nil, ThePrompt^^, CFStringGetSystemEncoding);
		NavGetDefaultDialogCreationOptions(dialogOptions);
		dialogOptions.windowTitle := OurPrompt;
		if LastPutFileName <> '' then
			dialogOptions.saveFileName := CFStringCreateWithPascalString(nil, LastPutFileName, CFStringGetSystemEncoding);
		
		status := NavCreatePutFileDialog(@dialogOptions, MyFiletype, 'DAWK', nil, nil, outDialog);
		status := NavDialogRun(outDialog);
		status := NavDialogGetReply(outDialog, outreply);
		userAction := NavDialogGetUserAction(outDialog);
		NavDialogDispose(outDialog);
		if userAction = kNavUserActionSaveAs then
			begin
				status := AECoerceDesc(outreply.selection, typeFSRef, firstAEDesc);
				status := FSGetDataForkName(dataForkName);

				status := AEGetNthPtr(outreply.selection, 1, typeFSRef, nil, nil, @fileRef, sizeof(FSRef), nil);
				length := CFStringGetLength(outreply.saveFileName);
				CFStringGetPascalString(outreply.saveFileName, @LastPutFileName, length, kCFStringEncodingASCII);
				CFStringGetCharacters(outreply.saveFileName, CFRangeMake(0, length), saveFileName.unicode);
				status := FSCreateFileUnicode(fileRef, length, saveFileName.unicode, kFSCatInfoNone, nil, @fileRef, nil);
				status := FSOpenFork(fileRef, dataForkName.length, dataForkName.unicode, fsWrPerm, ThisFile);

				IF foss THEN
					FossilsToSave := FALSE;
				SizeOfPersonSerializer := SizeOf(PersonSerializer);
				WITH ThisMenagerie DO
					FOR j := 1 TO menagerieSize DO
					
						IF MemberIsSaveCandidate(Member[j]^.denizen) THEN
							begin
								bytesToSave := SizeOfPersonSerializer;
								SetPersonSerializerFromPerson(@perSerializer, @Member[j]);

								status := FSWriteFork(ThisFile, fsAtMark, 0, bytesToSave, @perSerializer, @actualCount);

								IF status <> noErr THEN
									begin
										IOError(status, 'Check disk and try again');
										FSCloseFork(ThisFile);
										Exit(SaveAnimals)
									end;
							end
						ELSE {SysBeep(1)}
							;{****}
				FSCloseFork(ThisFile);
			end
		ELSE
			Finished := FALSE;
		CFStringGetPascalString(outreply.saveFileName, @LastPutFileName, length, kCFStringEncodingASCII);
				
		{LastPutFileName := MyReply.fname;}
		{FIXME remember the last file name we saved it under - ABC}
		{IF theMode = engineering THEN}
			{Delayvelop(Special, boxes^.midbox^.midpoint);} {Correct displacement bug}
		FlushEvents(EveryEvent, 0);
		ValidWindowRect(MainWindow, Prect);
	end; {Save Animals}


procedure SaveSlides;
	VAR
		ThisMenagerie: Menagerie;
		aFossil: PersonPtr;
		perSerializer: PersonSerializer;
		err: OSStatus;
		actualCount: ByteCount;
		SizeOfPersonSerializer: longint;
	begin
		DotheSave := FALSE;	{put it here in case we don't need to call SaveAnimals}
		ThisMenagerie.menagerieSize := 0;
		err := ResetDataFork(Slides);		{v1.1  was reset(Slides);}

		SizeOfPersonSerializer := SizeOf(PersonSerializer);
		
		WHILE NOT IsEOF(slides, SizeOfPersonSerializer) DO
			begin
				
				FSReadFork(slides, fsAtMark, 0, SizeOfPersonSerializer, @perSerializer, @actualCount);
				new(AFossil);
				SetPersonFromPersonSerializer(aFossil, @perSerializer);
				ThisMenagerie.menagerieSize := ThisMenagerie.menagerieSize + 1;
				ThisMenagerie.Member[ThisMenagerie.menagerieSize]^.denizen := aFossil
			end; {while}

		SaveAnimals(ThisMenagerie, TRUE);

	end; {SaveSlides}



procedure DoSave (HowMuch: Integer);
	VAR
		ThatMenagerie: Menagerie;
	begin
		CASE HowMuch OF
			1: 
				begin
					ThatMenagerie.member[1]^.denizen := Special^.denizen;
					ThatMenagerie.menagerieSize := 1;
					SaveAnimals(ThatMenagerie, FALSE)
				end; {Save Biomorph}
			2: 
				begin
				end;
			3: 
				begin
					SaveAnimals(Album, FALSE);
					IF DoTheSave THEN
						AlbumChanged := FALSE
				end
		end {CASES}
	end; {DoSave}



procedure ResetFossils;
	VAR
		err: OSStatus;
	begin
		Fossilizing := TRUE; 	{Reset Fossil Record}
		err := SetEOF(slides, 0);
	end; {ResetFossils}

{FIXME}



procedure ReadAnimals (VAR ThisMenagerie: Menagerie);
	VAR
		ThisFile: Integer;
		j: Integer;
		status: OSStatus;
		perSerializer: PersonSerializer;
		SizeOfPersonSerializer: longint;
		actualCount: BYteCount;
		userAction: NavUserAction;

	begin
		status := noErr;
		DoFileChooseNav(thisFile, userAction, false);
		if userAction = kNavUserActionChoose then
			begin
				IF Album.menagerieSize > 0 THEN
					AlbumChanged := TRUE;
				
				IF LoadingFossils THEN
					FSSetForkSize(Slides, fsFromStart, 0);
				
				j := 0;
				SizeOfPersonSerializer := SizeOf(PersonSerializer);
				WHILE NOT ((IsEOF(ThisFile, sizeOfPersonSerializer)) OR (j >= MaxAlbum)) DO
					begin
						j := j + 1;
						IF NOT LoadingFossils THEN
							begin
								FSReadFork(thisFile, fsAtMark, 0, SizeOfPersonSerializer, @perSerializer, @actualCount);
								SetPersonFromPersonSerializer(@ThisMenagerie.member[j], @perSerializer);						
							end
						ELSE
							begin
								FSReadFork(ThisFile, fsAtMark, 0, SizeOfPersonSerializer, @perSerializer, @actualCount);
								status := FSWriteFork(Slides, fsAtMark, 0, SizeOfPersonSerializer, @perSerializer, @actualCount);
								if status = fnOpnErr then exit;								
							end;
					end;    {while}
				FSCloseFork(ThisFile);
				IF NOT LoadingFossils THEN
					begin
						ThisMenagerie.menagerieSize := j;
						SetMode(MainWindow, albuming);
					end
			end
		ELSE
			begin {Sysbeep(1);}
				ThisMenagerie.menagerieSize := 0
			end;
		IF ThisMenagerie.menagerieSize > MaxAlbum THEN
			ThisMenagerie.menagerieSize := MaxAlbum;
		{LastGetFileName := MyReply.fname;}
		{FIXME save file name}
		IF LoadingFossils AND (userAction = kNavUserActionChoose) AND (status = NoErr) THEN
			begin
				status := ResetDataFork(Slides);		{v1.1  was reset(Slides);}
				StartPlayBack;
			end;
	end; {ReadAnimal}

end.