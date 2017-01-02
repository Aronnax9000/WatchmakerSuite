unit CreateHistoryFileCarbon;

interface

uses MacOSAll;

function CreateHistoryFile: OSStatus;

implementation

uses Globals, SerializationUtil;
{************************************************}
{*	 We follow this strategy to deal with servers and locked volumes:	  	    *}
{*		1) Try to save into the temp folder on the default disk	(sys 7)			*}
{* 	2) Try to save in the System folder													*}
{*		3) Try to save in the same folder as the application							*}
{* 	4) Ask the user																					*}
{* 	5) If all the above fail, quit with message to user								*}
{*    6) If there is a fossil history file already, delete it							*}
{************************************************}

function CreateHistoryFile: OSStatus;
	var historyFileName: CFStringRef;
			homeFolder: FSRef;
			fileRef: FSRef;
			dataForkName: HFSUniStr255;
			saveFileName: HFSUniStr255;
			length: CFIndex;
			status: OSStatus;	
		catalogInfo: FSCatalogInfo;
		fileInf: FileInfoPtr;
	begin
		if slides <> -1 then
			FSCloseFork(slides);
		status := FSFindFolder(kOnAppropriateDisk, kCurrentUserFolderType, kCreateFolder, homeFolder);
		historyFileName := CFStr('Fossil History');
		length := CFStringGetLength(historyFileName);
		CFStringGetCharacters(historyFileName, CFRangeMake(0, length), saveFileName.unicode);
		fileInf := @catalogInfo.finderInfo[0];
		BlockZero(fileInf, sizeof(FileInfo));
		fileInf^.fileCreator := 'DAWK';
		fileInf^.fileType := 'FOSS';
		status := FSMakeFSRefUnicode(homeFolder, length, saveFileName.unicode, kTextEncodingUnknown, fileRef);
		if status <> fnfErr then
			status := FSDeleteObject(fileRef);
		status := FSCreateFileUnicode(homeFolder, length, saveFileName.unicode, kFSCatInfoNone, @catalogInfo, @fileRef, nil);
		status := FSGetDataForkName(dataForkName);
		status := FSOpenFork(fileRef, dataForkName.length, dataForkName.unicode, fsWrPerm, slides);

		CreateHistoryFile := status;
end;

end.