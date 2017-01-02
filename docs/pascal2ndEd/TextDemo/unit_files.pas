unit unit_files;


interface
{$IFC UNDEFINED THINK_Pascal}
uses
  TextEdit;
{$ENDC}
procedure DoOpenCommand;
procedure DoSaveAsFile(editRecHdl: TEHandle);

implementation
uses
{$IFC UNDEFINED THINK_Pascal}
Types, Windows, Quickdraw, Files,
Memory,
StandardFile,
{$ENDC}
  unit_docrec,
  unit_donewdocwindow;
procedure DoOpenFile(fileSpec: FSSpec);

var
  myWindowPtr: WindowPtr;
  docRecHdl: DocRecHandle;
  editRecHdl: TEHandle;
  fileRefNum: integer;
  textLength: longint;
  textBufferHdl: Handle;
  ignored: OSErr;

begin
  myWindowPtr := DoNewDocWindow;
  if (myWindowPtr = nil) then
    Exit(DoOpenFile);

  docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
  editRecHdl := docRecHdl^^.editRecHdl;

  SetWTitle(myWindowPtr, fileSpec.Name);

  ignored := FSpOpenDF(fileSpec, fsCurPerm, fileRefNum);

  ignored := SetFPos(fileRefNum, fsFromStart, 0);
  ignored := GetEOF(fileRefNum, textLength);

  if (textLength > 32767) then
    textLength := 32767;

  textBufferHdl := NewHandle(Size(textLength));

  ignored := FSRead(fileRefNum, textLength, textBufferHdl^);

  MoveHHi(textBufferHdl);
  HLock(textBufferHdl);

  TESetText(textBufferHdl^, textLength, editRecHdl);

  HUnlock(textBufferHdl);
  DisposeHandle(textBufferHdl);

  ignored := FSClose(fileRefNum);

  editRecHdl^^.selStart := 0;
  editRecHdl^^.selEnd := 0;

  ShowWindow(myWindowPtr);
end;

{of procedure DoOpenFile}


procedure DoOpenCommand;

var
  fileReply: StandardFileReply;
  fileTypes: SFTypeList;

begin
  fileTypes[0] := 'TEXT';

  StandardGetFile(nil, 1, fileTypes, fileReply);
  if (fileReply.sfGood) then
    DoOpenFile(fileReply.sfFile);
end;


procedure DoSaveAsFile(editRecHdl: TEHandle);

var
  fileReply: StandardFileReply;
  myWindowPtr: WindowPtr;
  fileRefNum: integer;
  dataLength: longint;
  editTextHdl: Handle;
  ignored: OSErr;

begin
  StandardPutFile('Save as:', 'Untitled', fileReply);
  if (fileReply.sfGood) then
  begin
    myWindowPtr := FrontWindow;
    SetWTitle(myWindowPtr, fileReply.sfFile.Name);

    if not (fileReply.sfReplacing) then
      ignored := FSpCreate(fileReply.sfFile, ' KJB', 'TEXT', fileReply.sfScript);

    ignored := FSpOpenDF(fileReply.sfFile, fsCurPerm, fileRefNum);

    dataLength := editRecHdl^^.teLength;
    editTextHdl := editRecHdl^^.hText;
    ignored := FSWrite(fileRefNum, dataLength, editTextHdl^);

    ignored := FSClose(fileRefNum);
  end;
end;


end.

