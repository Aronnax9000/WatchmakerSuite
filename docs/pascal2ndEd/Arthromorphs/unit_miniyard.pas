unit unit_miniyard;

interface

uses
  unit_atoms;

const
  MINISIZE = 200;

type
	FileAtomHandle = ^FileAtomPtr;
	FileAtomPtr = ^FileAtom;
  FileAtom = record
				kind: AtomKind;
				height: real;		{also used for Thickness of a Joint}
				width: real;		{also used for Length of a Joint}
				angle: real;		{also used in an AnimalTrunk to store the number of atoms in the animal}
							{also used in SectionTrunk to store the Overlap of segments}
							{also used in SegmentTrunk to store the rank number of the segment}
				nextLikeMe: Integer;		{where to look in the BoneYard for the next atom. 0 means end of chain}
{Also used in AnimalTrunk to store Gradient gene, slightly more or less than 100.  Treat as Percentage}
				firstBelowMe: Integer;		{where to look in the BoneYard for the next atom. 0 means end of chain}
			end;

  FileAtomArrayHandle = ^FileAtomArrayPtr;
  FileAtomArrayPtr = ^FileAtomArray;
  FileAtomArray = RECORD
        freeIndex: INTEGER;
        fileAtoms: array[1..MINISIZE] of FileAtomHandle;
  end;
  {Just holds one animal, compactly}
procedure CopyFileAtomFromAtom(theFileAtom: FileAtomHandle; theAtom: AtomHdl);

procedure CopyAtomFromFileAtom(theAtom: AtomHdl; theFileAtom: FileAtomHandle);

implementation


procedure CopyAtomFromFileAtom(theAtom: AtomHdl; theFileAtom: FileAtomHandle);
begin
  with theAtom^^ do
  begin
    kind := theFileAtom^^.kind;
    angle := theFileAtom^^.angle;
    width := theFileAtom^^.width;
    height := theFileAtom^^.height;
  end;
end;
procedure CopyFileAtomFromAtom(theFileAtom: FileAtomHandle; theAtom: AtomHdl);
begin
  with theFileAtom^^ do
  begin
    kind := theAtom^^.kind;
    angle := theAtom^^.angle;
    width := theAtom^^.width;
    height := theAtom^^.height;
  end;

end;
end.

