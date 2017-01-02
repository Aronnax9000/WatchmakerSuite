unit SerializationUtil;

interface
	
	uses MacOSAll;

	type
		{Think Pascal represented enumerations with a short integer (byte), and wrote them}
		{to files as a single byte, with a second nonce byte as padding. This record is used}
		{for reading and writing Atoms to files under Carbon, to preserve the use of the AtomKind}
		{enumeration in the rest of the code. - ABC}
		arrayQuad = array[ 0 .. 3] of byte; 
		arrayDuo = array[ 0 .. 1] of byte; 


	function DoFileChooseNav(var forkRefNum: SInt16; var userAction: NavUserAction; save: boolean): OSStatus;
	function mangleArrayQuad(var victim: arrayQuad): arrayQuad;
	function mangleArrayDuo(var victim: arrayDuo): arrayDuo;
	function ResetDataFork(theDataFork: SInt16): OSStatus;
	
	{Acts like Pascal's eof() function, but in addition you can specify how close to the end of the file}
	{you are allowed to be (takes care of truncated files)}
	{This version cribbed from Monochrome Watchmaker by Richard Dawkins, v. 1.1.}

	function IsEOF (refNum: SInt16; howClose: LongInt): Boolean;

	{returns the number of bytes in the file. Same as}
	{	the toolbox function GetEOF, except this returns the size instead of an error code. It has this name to emulate the }
	{   Turbo routine, except this returns bytes (since we don't use pascal type-files now).}
	{This version cribbed from Monochrome Watchmaker by Richard Dawkins, v. 1.1.}
	function WatchmakerFileSize (refNum: SInt16): LongInt;		



implementation

	{Acts like Pascal's eof() function, but in addition you can specify how close to the end of the file}
	{you are allowed to be (takes care of truncated files)}
	{This version cribbed from Monochrome Watchmaker by Richard Dawkins, v. 1.1.}
	function IsEOF (refNum: SInt16; howClose: LongInt): Boolean;
	VAR
		err: OSStatus;
		eof, positionNow: LongInt;
	begin
		IsEOF := TRUE;
		err := GetFPos(refNum, positionNow);
		err := GetEOF(refNum, eof);
		IF err <> noErr THEN
			Exit(IsEOF);		{If something went wrong, we should stop reading}
		IsEOF := (eof - positionNow) < howClose
	end;

	{returns the number of bytes in the file. Same as}
	{	the toolbox function GetEOF, except this returns the size instead of an error code. It has this name to emulate the }
	{   Turbo routine, except this returns bytes (since we don't use pascal type-files now).}
	{This version cribbed from Monochrome Watchmaker by Richard Dawkins, v. 1.1.}
	function WatchmakerFileSize (refNum: SInt16): LongInt;		
	VAR
		err: OSStatus;
		numBytes: LongInt;
	begin
		err := GetEOF(refnum, numBytes);
		IF err <> noErr THEN
			numBytes := 0;	{Problem with the file, should signal an exception here.}
		WatchmakerFileSize := numBytes;
	end;


	function ResetDataFork(theDataFork: SInt16): OSStatus;
		begin
			ResetDataFork := FSSetForkPosition ( theDataFork, fsFromStart, 0 );
		end;
	
	function DoFileChooseNav(var forkRefNum: SInt16; var userAction: NavUserAction; save: boolean): OSStatus;
	var
			outDialog: NavDialogRef;
			outReply: NavReplyRecord;
			firstAEDesc: AEDesc;
			fileRef: FSRef;
			dataForkName: HFSUniStr255;
			saveFileName: HFSUniStr255;
			length: CFIndex;
			status: OSStatus;
	begin
		status := noErr;
		if save then
			status := NavCreatePutFileDialog(nil, 'DATA', 'arth', nil, nil, outDialog)
		else
			status := NavCreateChooseFileDialog(nil, nil, nil, nil, nil, nil, outDialog);
		
		status := NavDialogRun(outDialog);
		status := NavDialogGetReply(outDialog, outreply);
		userAction := NavDialogGetUserAction(outDialog);
		NavDialogDispose(outDialog);
		if (userAction <> kNavUserActionChoose) and (userAction <> kNavUserActionSaveAs) then
				exit;
		status := AECoerceDesc(outreply.selection, typeFSRef, firstAEDesc);
		status := FSGetDataForkName(dataForkName);

		status := AEGetNthPtr(outreply.selection, 1, typeFSRef, nil, nil, @fileRef, sizeof(FSRef), nil);
		if save then
			begin
			length := CFStringGetLength(outreply.saveFileName);
			CFStringGetCharacters(outreply.saveFileName, CFRangeMake(0, length), saveFileName.unicode);
			status := FSCreateFileUnicode(fileRef, length, saveFileName.unicode, kFSCatInfoNone, nil, @fileRef, nil);
			status := FSOpenFork(fileRef, dataForkName.length, dataForkName.unicode, fsWrPerm, forkRefNum)
			end
		else
			status := FSOpenFork(fileRef, dataForkName.length, dataForkName.unicode, fsRdPerm, forkRefNum);
		DoFileChooseNav := status;
	end;
	
	function mangleArrayQuad(var victim: arrayQuad): arrayQuad;
	var
		tempByte0: byte;
		tempByte1: byte;
		
		{00112233 becomes}
		{33221100}
	begin
		tempByte0 := victim[0];
		tempByte1 := victim[1];
		victim[0] := victim[3];
		victim[1] := victim[2];
		victim[2] := tempByte1;
		victim[3] := tempByte0;
		mangleArrayQuad := victim;
	end;
	
	function mangleArrayDuo(var victim: arrayDuo): arrayDuo;
	var
		tempByte: byte;
	begin
		tempByte := victim[0];
		victim[0] := victim[1];
		victim[1] := tempByte;
		mangleArrayDuo := victim;
	end;
end.