unit unit_disk;

interface

uses unit_miniyard, unit_preferences;

procedure SaveArthromorph(prefs: ArthromorphPreferencesHandle);
procedure LoadArthromorph(prefs: ArthromorphPreferencesHandle);
procedure StartDocument(prefs: ArthromorphPreferencesHandle);


implementation
uses
{$IFC UNDEFINED THINK_Pascal}
  Types, Files, StandardFile, OSUtils, SegLoad, think_pascal, Memory,
{$ENDC}
  unit_dialog_helper, unit_atoms;


function Extract(miniYard: FileAtomArrayHandle; which: AtomHdl): integer;
  {Copy this animal from the BoneYard to the MiniYard.}
  {Return index of copy in MiniYard}
  {Afterwards: Since Animal is compact in the front part of MiniYard, just copy atoms}
  {  from 1 to MiniFree-1 into the file}
var
  newPlace, i: integer;

begin
  with miniYard^^ do
  begin
    if which^^.kind = AnimalTrunk then
      begin  {Once at the start of the copy.  Populate the MiniYard with Free Atoms}
        freeIndex := 1;
        for i := 1 to MINISIZE do
          begin
               miniYard^^.fileAtoms[i] := FileAtomHandle(NewHandle(SizeOf(FileAtom)));
               miniYard^^.fileAtoms[i]^^.kind := Free;
          end;
      end;
      {Duplicate this entire animal in the other yard.   }
      {Return the index of the start of the new animal.}
    newPlace := freeIndex;    {Grab a new atom}
    freeIndex := freeIndex + 1;    {our Allocate since all are free}
    {CopyFileAtomFromAtom(MiniYard[newPlace], which);}
    if which^^.FirstBelowMe <> nil then
      MiniYard^^.fileAtoms[newPlace]^^.FirstBelowMe := Extract(miniYard, which^^.FirstBelowMe);
    if (which^^.NextLikeMe <> nil) and (which^^.kind <> AnimalTrunk) then
      MiniYard^^.fileAtoms[newPlace]^^.NextLikeMe := Extract(miniYard, which^^.NextLikeMe);
    Extract := newPlace;      {Return the index of the new one}
  end;
end;

{Example of use:-}
{Extract(BreedersChoice[ii]);  }
{Copy this animal out to the MiniYard}
{Now write MiniYard from 1 to MiniFree-1 out into a file}


function deposit(miniYard: FileAtomArrayHandle; which: integer): AtomHdl;
  {Caller must copy Animal from a file directly into the MiniYard, then call Deposit(1)}
  {Here we copy the animal from the MiniYard into the BoneYard.}
  {Return the index of the start of the new animal in the BoneYard.}
var
  newPlace: AtomHdl;
begin
  newPlace := AllocateAtom;    {Grab a new atom in the BoneYard}
  with miniYard^^ do
    begin
      CopyAtomFromFileAtom(newPlace, fileAtoms[which]);
      if fileAtoms[which]^^.firstBelowMe <> 0 then
        newPlace^^.firstBelowMe := deposit(miniyard, fileAtoms[which]^^.firstBelowMe);
      if (fileAtoms[which]^^.nextLikeMe <> 0) and (newPlace^^.kind <>
        AnimalTrunk) then
        NewPlace^^.nextLikeMe := deposit(miniYard, fileAtoms[which]^^.nextLikeMe);
      end;
  Deposit := newPlace;      {Return the index of the new one}
end;

{Example of use:-}
{Read file into the MiniYard, then call this to move it to the BoneYard}
{BreedersChoice[ii] := Deposit(1);}
{Install the animal in MiniYard in the BoneYard and return its start}

procedure SaveArthromorph(prefs: ArthromorphPreferencesHandle);
var
  where: point;
  theReply: SFReply;
  Error: OSErr;
  i: integer;
  f: file of FileAtom;
  miniYard: FileAtomArrayHandle;
begin
  with where do
  begin
    h := 100;
    v := 100;
  end;
  miniYard := FileAtomArrayHandle(NewHandle(SizeOf(FileAtomArray)));

  i := extract(miniYard, prefs^^.theBoxes^^.MidBox^^.BreedersChoice);
  SFPutFile(where, 'Save this Arthromorph as:', '', nil, theReply);
  if theReply.good then
  begin {not cancel}
    Error := SetVol(nil, theReply.vRefNum);
    if Error = NoErr then
      ReWrite(f, theReply.fName);
    for i := 1 to miniYard^^.freeIndex - 1 do
      Write(f, miniyard^^.fileAtoms[i]^^);
    Close(f);
  end; {not Cancel}
end; {SaveArthromorph}

function MyFilter(param: ParmBlkPtr): boolean;
var
  Wanted: boolean;
begin
  Wanted := (param^.ioFlFndrInfo.fdCreator = 'JOHN') and
    (param^.ioFlFndrInfo.fdType = 'DATA');
  MyFilter := not wanted;
end;


procedure LoadArthromorph(prefs: ArthromorphPreferencesHandle);
var
  where: point;
  theReply: SFReply;
  theTypeList: SFTypeList;
  Error: OSErr;
  i: integer;
  f: file of FileAtom;
  miniYard: FileAtomArrayHandle;
begin
  with where do
  begin
    h := 100;
    v := 100;
  end;
  theTypeList[0] := 'DATA';
  SFGetFile(where, 'Load which Arthromorph?', @MyFilter, -1, theTypeList, nil, theReply);
  if theReply.good then {else Cancel }
  begin
    if prefs^^.theBoxes^^.MidBox^^.BreedersChoice = nil then
      Kill(prefs^^.theBoxes^^.MidBox^^.BreedersChoice);
    Error := SetVol(nil, theReply.vRefNum);
    if Error = NoErr then
      ReSet(f, theReply.fname);
    i := 0;
    miniYard := FileAtomArrayHandle(NewHandle(SizeOf(FileAtomArray)));

    while (i <= MINISIZE) and (not EOF(f)) do
    begin
      i := i + 1;
      Read(f, miniYard^^.fileAtoms[i]^^);
    end;
    Close(f);
    prefs^^.theBoxes^^.MidBox^^.BreedersChoice := Deposit(miniYard, 1);

    prefs^^.resizing := true;
    InvalidateWindow(prefs^^.BreedingWindow);

  end; {not Cancel}
end; {LoadArthromorph}

procedure StartDocument(prefs: ArthromorphPreferencesHandle);
var
  i: integer;
  theFile: AppFile;
  ErrorCode: OSErr;
  f: file of FileAtom;
  miniYard: FileAtomArrayHandle;
begin
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
        miniYard := FileAtomArrayHandle(NewHandle(SizeOf(FileAtomArray)));
        with miniYard^^ do
          begin
            freeIndex := 1;
            while (freeIndex <= MINISIZE) and (not EOF(f)) do
            begin
              miniYard^^.fileAtoms[i] := FileAtomHandle(NewHandle(SizeOf(FileAtom)));
              Read(f, miniYard^^.fileAtoms[freeIndex]^^);
              freeIndex := freeIndex + 1;
            end;
            Close(f);
            prefs^^.theBoxes^^.MidBox^^.BreedersChoice := Deposit(miniYard, 1);
            prefs^^.resizing := true;
            for i := 1 to freeIndex - 1 do
              DisposeHandle(Handle(fileAtoms[freeIndex]));
            DisposeHandle(Handle(miniYard));
            InvalidateWindow(prefs^^.BreedingWindow);

      end;
      end;
    end;
end; {StartDocument}

end.
