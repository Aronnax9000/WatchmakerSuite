{Originally part of Ted.pas, this unit creates the basic infrastructure for}
{Arthromorph atoms.}
unit unit_atoms;
{Arthromorphs   by Richard Dawkins and Ted Kaehler}
{Ted's initial version: 25 Nov 90}
{Current version: 8 Dec 90}
{Since we both are confused by handles and pointers in Pascal, this does not use any of either!}

{There is a Record called Atom that holds a little part of an animal.  It has fields for a Height, }
{a Width, and an Angle.}
{	When it is used to describe a Segment, Height and Width are for the oval,}
{		and Angle is not used}
{	When it is used to describe a Joint, Height is the thickness of the leg-part,}
{		Width is the length, and Angle is the angle from the previous joint}
{	When it is used to describe a Claw,  Height is the thickness of the claw-part,}
{		Width is the length, and Angle is the between the claw halves}

{Remember that the true Joint length is the multiplication of all the factors:}
{The Animal's joint length, this Section's joint length, this Segment's joint length, and the Joint's own joint length.}
{Thus a Segment actually has three parts: its factor for Segment size, its }
{	factor for Joint size, and its factor for Claw size.  Each of these are Atoms.  Thus a}
{	Segment has three Atoms.  They are distinguished by having different kinds: SegmentTrunk,}
{	SegmentJoint, and SegmentClaw.}
{An Animal-record also has three Atoms in it AnimalTrunk, AnimalJoint, and AnimalClaw.}

{How are Atoms hooked together?  Here is a sample Animal.  Each line is an Atom, but I don't}
{	show the values inside it, like Height: 20 Width: 30, etc.}
{AnimalTrunk}
{	AnimalJoint}
{	AnimalClaw}
{		SectionTrunk}
{			SectionJoint}
{			SectionClaw}
{				SegmentTrunk}
{					SegmentJoint}
{					SegmentClaw}
{						Joint}
{						Joint}
{						Joint}
{							Claw}
{				SegmentTrunk}
{					SegmentJoint}
{					SegmentClaw}
{						Joint}
{						Joint}
{						Joint}
{							Claw}

{A Section sets the tone for all segments within it:  Head, Thorax, Abdomen are sections}

{In the above set of Atoms, there are two fields for connecting Atoms together.}
{	NextLikeMe  hooks the atom to the next atom on the same level.}
{	FirstBelowMe  hooks the atom to the first atom on a lower level.}
{Look at the diagram above.  When an atom points to another with NextLikeMe, they}
{have the same level of indentation.  When an atom points to another with }
{FirstBelowMe, the atom is indented one more level.}
{The first SegmentTrunk points way down to the second SegmentTrunk with NextLikeMe.}
{The Joints point to the next with NextLikeMe.  However, the AnimalClaw}
{points to SegmentTrunk using FirstBelowMe.  Note that the three atoms that}
{make up an Animal are split.  AnimalJoint is pointed to with FirstBelowMe even}
{though it is part of the animal description.  I had to do this so that AnimalTrunk could use its}
{NextLikeMe to point at the next animal.  Likewise with Segments.}

{All atoms are stored in a big Array called the BoneYard.  You find an atom}
{by knowing its index (the integer that is its place in the array).  The two "pointers" NextLikeMe }
{and FirstBelowMe are not pointers at all, but simply integers.}
{An indivudual Animal can have its atoms spread out all over the BoneYard, but }
{each atom in it holds the index of the next atom in it.  Thus we can walk down }
{the parts of an animal very easily.  Atoms that are not being used are labelled Free.}
interface

uses
{$IFC UNDEFINED THINK_Pascal}

{$ENDC}
  unit_tell_error;

const
  Scale = 10;


  YARDSIZE = 65536;
  {2500 would allow 18 Animals with 15 segments each and 4 joints per segment.}
type
  AtomKind = (
    Free,
    AnimalTrunk,
    AnimalJoint,
    AnimalClaw,
    SectionTrunk,
    SectionJoint,
    SectionClaw,
    SegmentTrunk,
    SegmentJoint,
    SegmentClaw,
    Joint,
    Claw);
  {18 bytes apiece}


  AtomHdl = ^AtomPtr;
  AtomPtr = ^Atom;
  Atom = record
    Kind: AtomKind;
    {Height also used for Thickness of a Joint}
    Height: real;
    {Width is also used for Length of a Joint}
    Width: real;
    {Angle also used in an AnimalTrunk to store number of atoms in the animal}
    Angle: real;
    {also used in SectionTrunk to store the Overlap of segments}
    {also used in SegmentTrunk to store the rank number of the segment}
    NextLikeMe: AtomHdl;
    {where to look in the BoneYard for the next atom. 0 means end of chain}
    {slightly more or less than 100.  Treat as Percentage}
    FirstBelowMe: AtomHdl;
    {where to look in the BodneYard for the next atom. 0 means end of chain}
    gradientGene: integer;
    {Only for AnimalTrunk. Was stored in NextLikeMe in AnimalTrunk}
  end;


  KindsData = array[AtomKind] of integer;


  SegCountStateHandle = ^SegCountStatePtr;
  SegCountStatePtr = ^SegCountState;
  SegCountState = RECORD
    segmentCounter: INTEGER;
    secondSegmentAtomNo: INTEGER;
    count: integer;
  end;

{Called by Copy, CopyExceptNext, and NewAtom}
function allocateAtom: AtomHdl;
procedure deallocate(which: AtomHdl);
procedure kill(which: AtomHdl);
function countAtoms (which: AtomHdl): integer;
function findNth (which: AtomHdl; pick: integer; state: SegCountStateHandle): AtomHdl;
function copy (which: AtomHdl): AtomHdl;
function copyExceptNext (which: AtomHdl): AtomHdl;
procedure countSeg (which: AtomHdl; state: SegCountStateHandle);

procedure ZeroAtomCount;
function getAtomCount: INTEGER;

implementation
{$IFC UNDEFINED THINK_Pascal}
  uses
   Memory, Types;
{$ENDC}


var
  atomCount: INTEGER;

  function getAtomCount: INTEGER;
  begin
    getAtomCount := atomCount;
  end;

procedure ZeroAtomCount;
begin
  atomCount := 0;
end;

procedure deallocate(which: AtomHdl);
begin
  which^^.firstBelowMe := nil;
  which^^.nextLikeMe := nil;
  DisposeHandle(Handle(which));
  atomCount := atomCount - 1;
end;

function allocateAtom: AtomHdl;
var
  thisAtom: AtomHdl;
begin
    if atomCount > YardSize then
      begin
           TellError ('More than YardSize atoms');
           thisAtom := nil;
      end
    else
      begin
        thisAtom := AtomHdl(NewHandle(SizeOf(Atom)));
        if thisAtom = nil then
           TellError('AllocateAtom failed.');
        with thisAtom^^ do
          begin
            firstBelowMe := nil;
            nextLikeMe := nil;
          end;
        atomCount := atomCount + 1;

    end;
    AllocateAtom := thisAtom;
end;


{Creating and destroying Animals}
procedure kill(which: AtomHdl);
{Destroy this animal.   Mark all of its Atoms as Free again.}
{Recursively step through the animal}
begin
  if which <> nil then
  begin
    kill(which^^.firstBelowMe);
    kill(which^^.nextLikeMe);
    deallocate(which);    {Free this Atom}
  end;
end; {Kill}

  {Duplicate this entire animal.}
  {Return the index of the start of the new animal.}
	{It is a very good idea to Kill the old animal first.}
  {That way, we can reuse its atoms.}
	function copy (which: AtomHdl): AtomHdl;
		var
			newPlace: AtomHdl;
	begin
		  newPlace := AllocateAtom;		{Grab a new atom}
		  newPlace^^ := which^^;

      if which^^.FirstBelowMe <> nil then
			  newPlace^^.FirstBelowMe := Copy(which^^.FirstBelowMe);

      if which^^.NextLikeMe <> nil then
			  NewPlace^^.NextLikeMe := Copy(which^^.NextLikeMe);
    Copy := newPlace;			{Return the index of the new one}
	end;

  {Duplicate Subtree starting at the atom which, but don't copy NextLikeMe.}
  {Leave old value there}
	{Copy the things I own, but not the things after me}
  function copyExceptNext (which: AtomHdl): AtomHdl;
		var
			newPlace: AtomHdl;
	begin
		newPlace := AllocateAtom;		{Grab a new atom}
		NewPlace^^ := which^^;
		if which^^.FirstBelowMe <> nil then
      {Normal COPY from here on}
		  NewPlace^^.FirstBelowMe := Copy(which^^.FirstBelowMe);
		CopyExceptNext := newPlace;			{Return the index of the new one}

  end;

	function findNth (which: AtomHdl; pick: integer; state: SegCountStateHandle): AtomHdl;
	{travel over the Animal, counting Atoms and return the Nth}
	begin
		state^^.count := state^^.count + 1;
		if which^^.kind = SegmentTrunk then
			state^^.segmentCounter := state^^.segmentcounter + 1;
		if state^^.segmentCounter = 2 then
			state^^.secondSegmentAtomNo := state^^.count;
		if state^^.count >= pick then
			FindNth := which		{We are done!}
		else
			with which^^ do
				begin
					if FirstBelowMe <> nil then
						FindNth := FindNth(FirstBelowMe, pick, state);
					if not (state^^.count >= pick) then
						if (NextLikeMe <> nil) then
							FindNth := FindNth(NextLikeMe, pick, state);
					if not (state^^.count >= pick) then
						FindNth := nil;		{not there yet}
				end;
  end;

	procedure countSeg (which: AtomHdl; state: SegCountStateHandle);
	begin
		with which^^ do
			begin
				if kind = SegmentTrunk then
					begin
						state^^.SegmentCounter := state^^.SegmentCounter + 1;
						which^^.angle := state^^.SegmentCounter;
					end;
				if firstBelowMe <> nil then
					countSeg(firstBelowMe, state);
				if NextLikeMe <> nil then
					countSeg(nextLikeMe, state);
      end;
  end;

	function countAtoms (which: AtomHdl): integer;
	{travel over the Animal, counting Atoms}
		var
			count: integer;
	begin
		count := 1;	{count me}
		with which^^ do
			begin
				if firstBelowMe <> nil then
					count := count + countAtoms(firstBelowMe);
				if nextLikeMe <> nil then
					count := count + countAtoms(nextLikeMe);
			end;
		countAtoms := count;	{Me and all below me}
	end;

end.
