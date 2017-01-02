unit Serialization;
{$PACKENUM 2}
{$PACKRECORDS 2}
interface

	uses Ted, MacOSAll, MyGlobals, Drawing, SerializationUtil;

	type
		AtomCarbonSerializer = record
			Kind: Byte;
			padding: Byte;
			Height: arrayQuad;		{also used for Thickness of a Joint}
			Width: arrayQuad;		{also used for Length of a Joint}
			Angle: arrayQuad;		{also used in an AnimalTrunk to store the number of atoms in the animal}
						{also used in SectionTrunk to store the Overlap of segments}
						{also used in SegmentTrunk to store the rank number of the segment}
				
			{where to look in the BoneYard for the next atom. 0 means end of chain}
			{Also used in AnimalTrunk to store Gradient gene, slightly more or less than 100.  Treat as Percentage}
			NextLikeMe: arrayDuo;
				
			{where to look in the BoneYard for the next atom. 0 means end of chain}
			FirstBelowMe: arrayDuo;
		end;

	var 
		f: file of Atom;
		
	procedure SaveArthromorph;
	procedure LoadArthromorph;
	{$ifc not undefined THINK_PASCAL}
	procedure StartDocument;
	{$endc}

implementation


	function Extract (which: integer): integer;
	{Copy this animal from the BoneYard to the MiniYard.}
	{Return index of copy in MiniYard}
{Afterwards: Since Animal is compact in the front part of MiniYard, just copy atoms}
{	from 1 to MiniFree-1 into the file}
		var
			newPlace, ii: integer;
	begin
		if BoneYard[which]^^.Kind = AnimalTrunk then
			begin	{Once at the start of the copy.  Erase the MiniYard}
				MiniFree := 1;
				for ii := 1 to miniSize do
					begin
						MiniYard[ii]^^.Kind := Free;
					end;
			end;

	{Duplicate this entire animal in the other yard.   }
	{Return the index of the start of the new animal.}
		newPlace := miniFree;		{Grab a new atom}
		miniFree := miniFree + 1;		{our Allocate since all are free}
		MiniYard[newPlace]^^ := BoneYard[which]^^;
		if BoneYard[which]^^.FirstBelowMe <> 0 then
			MiniYard[newPlace]^^.FirstBelowMe := Extract(BoneYard[which]^^.FirstBelowMe);
		if (BoneYard[which]^^.NextLikeMe <> 0) and (BoneYard[which]^^.kind <> AnimalTrunk) then
			MiniYard[newPlace]^^.NextLikeMe := Extract(BoneYard[which]^^.NextLikeMe);
		Extract := newPlace;			{Return the index of the new one}
	end;
{Example of use:-}
{Extract(BreedersChoice[ii]);	}
	{Copy this animal out to the MiniYard}
	{Now write MiniYard from 1 to MiniFree-1 out into a file}

	function Deposit (which: integer): integer;
	{Caller must copy Animal from a file directly into the MiniYard, then call Deposit(1)}
	{Here we copy the animal from the MiniYard into the BoneYard.}
	{Return the index of the start of the new animal in the BoneYard.}
		var
			newPlace: integer;
	begin
		newPlace := AllocateAtom;		{Grab a new atom in the BoneYard}
		BoneYard[NewPlace]^^ := MiniYard[which]^^;
		if MiniYard[which]^^.FirstBelowMe <> 0 then
			BoneYard[NewPlace]^^.FirstBelowMe := Deposit(MiniYard[which]^^.FirstBelowMe);
		if (MiniYard[which]^^.NextLikeMe <> 0) and (BoneYard[NewPlace]^^.kind <> AnimalTrunk) then
			BoneYard[NewPlace]^^.NextLikeMe := Deposit(MiniYard[which]^^.NextLikeMe);
		Deposit := newPlace;			{Return the index of the new one}
	end;
{Example of use:-}
	{Read file into the MiniYard, then call this to move it to the BoneYard}
{BreedersChoice[ii] := Deposit(1);}
		{Install the animal in MiniYard in the BoneYard and return its start}




	function MyFilter (param: ParmBlkPtr): BOOLEAN;
		var
			Wanted: BOOLEAN;
	begin
		Wanted := (param^.ioFlFndrInfo.fdCreator = 'JOHN') and (param^.ioFlFndrInfo.fdType = 'DATA');
		MyFilter := not wanted;
	end;

	
	procedure SaveArthromorph;
		var
			{$ifc not undefined THINK_PASCAL}
			theReply: SFReply;
			where: point;
			{$elsec}
			status: OSStatus;
			forkRefNum: Sint16;
			tempAtom: AtomCarbonSerializer;
			actualCount: ByteCount;
		dialogOptions: NavDialogCreationOptions;
		outDialog: NavDialogRef;
		outReply: NavReplyRecord;
OurPrompt: CFStringREf;
userAction: NavUserAction;
length: CFIndex;
			firstAEDesc: AEDesc;
			numString: Str255;
			fileRef: FSRef;
			dataForkName: HFSUniStr255;
			saveFileName: HFSUniStr255;
			{$endc}
			i: integer;
	begin
		i := extract(BreedersChoice[MidBox]);

		{$ifc not undefined THINK_PASCAL}
		with where do
			begin
				h := 100;
				v := 100;
			end;
		SFPutFile(where, 'Save this Arthromorph as:', '', nil, theReply);
		if theReply.good then
			begin {not cancel}
				Error := SetVol(nil, theReply.vRefNum);
				if Error = NoErr then
					ReWrite(f, theReply.fName);
				for i := 1 to MiniFree - 1 do
					write(f, MiniYard[i]^^);
				Close(f);
			end; {not Cancel}
		{$elsec}
		OurPrompt := CFStringCreateWithPascalString(nil, 'Save this Arthromorph as:', CFStringGetSystemEncoding);
		NavGetDefaultDialogCreationOptions(dialogOptions);
		dialogOptions.windowTitle := OurPrompt;
		status := NavCreatePutFileDialog(@dialogOptions, 'DATA', 'JOHN', nil, nil, outDialog);
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
			CFStringGetCharacters(outreply.saveFileName, CFRangeMake(0, length), saveFileName.unicode);
			status := FSCreateFileUnicode(fileRef, length, saveFileName.unicode, kFSCatInfoNone, nil, @fileRef, nil);
			status := FSOpenFork(fileRef, dataForkName.length, dataForkName.unicode, fsWrPerm, forkRefNum);
			for i := 1 to MiniFree - 1 do
				begin
					case MiniYard[i]^^.Kind of
						Free: tempAtom.Kind := kFree;
						AnimalTrunk: tempAtom.Kind := kAnimalTrunk;
						AnimalJoint: tempAtom.Kind := kAnimalJoint;
						AnimalClaw: tempAtom.Kind := kAnimalClaw;
						SectionTrunk: tempAtom.Kind := kSectionTrunk;
						SectionJoint: tempAtom.Kind := kSectionJoint;
						SectionClaw: tempAtom.Kind := kSectionClaw;
						SegmentTrunk: tempAtom.Kind := kSegmentTrunk;
						SegmentJoint: tempAtom.Kind := kSegmentJoint;
						SegmentClaw: tempAtom.Kind := kSegmentClaw;
						Joint: tempAtom.Kind := kJoint;
						Claw: tempAtom.Kind := kClaw;
					end;
					tempAtom.height := arrayQuad(MiniYard[i]^^.height);
					tempAtom.width := arrayQuad(MiniYard[i]^^.width);
					tempAtom.angle := arrayQuad(MiniYard[i]^^.angle);
					tempAtom.NextLikeMe := arrayDuo(MiniYard[i]^^.NextLikeMe);
					tempAtom.FirstBelowMe := arrayDuo(MiniYard[i]^^.FirstBelowMe);
					
					{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
					mangleArrayQuad(tempAtom.height);
					mangleArrayQuad(tempAtom.width);
					mangleArrayQuad(tempAtom.angle);
					mangleArrayDuo(tempAtom.NextLikeMe);
					mangleArrayDuo(tempAtom.FirstBelowMe);
					{$endc}
					FSWriteFork(forkRefNum, fsAtMark, 0, sizeOf(tempAtom), @tempAtom, @actualCount);
				end;
			status := FSCloseFork(forkRefNum);
			end;
		{$endc}
	end; {SaveArthromorph}
	
	procedure LoadArthromorph;
		var
			i: integer;
			Exists: boolean;
			atPtr: AtomPtr;
			atHdl: AtomHdl;


			{$ifc not undefined THINK_PASCAL}
			where: point;
			theReply: SFReply;
			theTypeList: SFTypeList;
			{$elsec}
			status: OSStatus;
			forkRefNum: SInt16;
			actualCount: ByteCount;
			sizeOfAtom: SInt16;
			tempAtom: AtomCarbonSerializer;
			userAction: NavUserAction;	
			{$endc}
	begin

		{Open the file choose dialog, either Classic Mac (THINK_PASCAL) or Carbon}

		{$ifc not undefined THINK_PASCAL}
		with where do
			begin
				h := 100;
				v := 100;
			end;
		theTypeList[0] := 'DATA';
		SFGetFile(where, 'Load which Arthromorph?', @MyFilter, -1, theTypeList, nil, theReply);
		if not theReply.good then {else Cancel }
			exit;

		Error := SetVol(nil, theReply.vRefNum);
		if Error = NoErr then
			ReSet(f, theReply.fname);

		{$elsec}
		status := DoFileChooseNav(forkRefNum, userAction, false);
		if userAction <> kNavUserActionChoose then
			exit; 
		{$endc}
		i := BreedersChoice[MidBox];
		Exists := (i > 0) and (i < YardSize);
		if Exists then
			Kill(i);
		i := 0;
		{$ifc not undefined THINK_PASCAL}
		while (i <= MiniSize) and (not eof(f)) do
		{$elsec}
		sizeOfAtom := sizeof(AtomCarbonSerializer);
		while (i <= MiniSize) and (status <> eofErr) do
		{$endc}
			begin
				i := i + 1;
				atHdl := MiniYard[i];
				atPtr := atHdl^;
				{$ifc not undefined THINK_PASCAL}
				read(f, atPtr^);
				{$elsec}
				status := FSReadFork(forkRefNum, fsAtMark, 0, sizeOfAtom, @tempAtom, @actualCount);
				case tempAtom.Kind of
					kFree: atPtr^.Kind := Free;
					kAnimalTrunk: atPtr^.Kind := AnimalTrunk;
					kAnimalJoint: atPtr^.Kind := AnimalJoint;
					kAnimalClaw: atPtr^.Kind := AnimalClaw;
					kSectionTrunk: atPtr^.Kind := SectionTrunk;
					kSectionJoint: atPtr^.Kind := SectionJoint;
					kSectionClaw: atPtr^.Kind := SectionClaw;
					kSegmentTrunk: atPtr^.Kind := SegmentTrunk;
					kSegmentJoint: atPtr^.Kind := SegmentJoint;
					kSegmentClaw: atPtr^.Kind := SegmentClaw;
					kJoint: atPtr^.Kind := Joint;
					kClaw: atPtr^.Kind := Claw;
				end;
				{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
				mangleArrayQuad(tempAtom.height);
				mangleArrayQuad(tempAtom.width);
				mangleArrayQuad(tempAtom.angle);
				mangleArrayDuo(tempAtom.NextLikeMe);
				mangleArrayDuo(tempAtom.FirstBelowMe);
				{$endc}
				atPtr^.height := single(tempAtom.height);
				atPtr^.width := single(tempAtom.width);
				atPtr^.angle := single(tempAtom.angle);
				atPtr^.NextLikeMe := SInt16(tempAtom.NextLikeMe);
				atPtr^.FirstBelowMe := SInt16(tempAtom.FirstBelowMe);
				{$endc}
			end;
		{$ifc not undefined THINK_PASCAL}
		Close(f);
		{$elsec}
		if status <> eofErr then
			begin
				TellError('Error on read');
				Exit
			end;
		status := FSCloseFork(forkRefNum);
		if status <> noErr then
			begin
				TellError('Error on close fork');
				Exit
			end;
		{$endc}
		
		BreedersChoice[MidBox] := Deposit(1);
		FirstGeneration;
		ValidWindowRect(BreedingWindow, Prect);
		
	end; {LoadArthromorph}
	{$ifc not undefined THINK_PASCAL}
	procedure StartDocument;
		var
			j, i, NB, vRefNum: INTEGER;
			theFile: AppFile;
			ErrorCode: OSErr;
	begin
		j := 0;
		GetAppFiles(1, theFile);
		with theFile do
			if fType = 'APPL' then
				SysBeep(1)
			else
				begin
					ErrorCode := SetVol(nil, vRefNum);
					if ErrorCode <> noErr then
						SysBeep(1)
					else
						begin
							Reset(f, fName);
							i := 0;
							while (i <= MiniSize) and (not eof(f)) do
								begin
									i := i + 1;
									read(f, MiniYard[i]^^);
								end;
							Close(f);
							BreedersChoice[MidBox] := Deposit(1);
							FirstGeneration;
							ValidRect(Prect);
						end
				end;
			
	end; {StartDocument}
	{$endc}

end.