unit unit_mutate;

interface

uses unit_atoms, unit_muts;

function mutate(muts: ArthromorphMutationsHandle; which: AtomHdl): boolean;

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Quickdraw, SegLoad, Memory, Types,
{$ENDC}
  unit_atom_delete, unit_tell_error;

function randInt(Max: integer): integer;
begin
  {delivers integer between 1 and Max;}
  randInt := (abs(Random) mod Max) + 1;
end;

{How much to grow or shrink a Length or height or Angle}
function GetFactor(mutationPressure: Pressure): real;
var
  choose: integer;
begin
  case mutationPressure of
    positive:
      choose := 2 + randint(2);
    zero:
      choose := randint(4);
    negative:
      choose := randint(2);
  end; {cases}
  case choose of
    1:       {Richard, you can play with these factors}
      GetFactor := 0.50;
    2:
      GetFactor := 0.9;
    3:
      GetFactor := 1.1;
    4:
      GetFactor := 1.5;
  end; {cases}
end;

function IsMutOK(thisAtom: AtomHdl; muts: ArthromorphMutationsHandle): boolean;
var
  MutOK: boolean;
begin
  MutOK := False;
  with muts^^ do
    with thisAtom^^ do
      case kind of
        Free:
          MutOK := False;
        AnimalTrunk:
          if AnimalTrunkMut then
            MutOK := True;
        AnimalJoint:
          if AnimalLegsMut then
            MutOK := True;
        AnimalClaw:
          if AnimalClawsMut then
            MutOK := True;
        SectionTrunk:
          if SectionTrunkMut then
            MutOK := True;
        SectionJoint:
          if SectionLegsMut then
            MutOK := True;
        SectionClaw:
          if SectionClawsMut then
            MutOK := True;
        SegmentTrunk:
          if SegmentTrunkMut then
            MutOK := True;
        SegmentJoint:
          if SegmentLegsMut then
            MutOK := True;
        SegmentClaw:
          if SegmentClawsMut then
            MutOK := True;
        Joint:
          if LegsMut then
            MutOK := True;
        Claw:
          if ClawsMut then
            MutOK := True;
      end; {cases }
  IsMutOK := MutOK;
end;

function IsSegmentMutOK(thisAtom: AtomHdl; muts: ArthromorphMutationsHandle;
  SegmentCounter, SecondSegmentAtomNo, LastSegment, Count: integer): boolean;
var
  MutOK: boolean;
  CouldBe: boolean;
begin
  MutOK := True;
  with muts^^ do
    case FocusOfAttention of
      FirstSegmentOnly:
        if SecondSegmentAtomNo > 0 then
        begin
          if Count < SecondSegmentAtomNo then
          begin
            with thisAtom^^ do
              CouldBe := (kind = SegmentTrunk) or
                (kind = SegmentJoint) or (kind = SegmentClaw) or
                (kind = joint) or (kind = claw);
            if not CouldBe then
              MutOK := False;
          end;
        end
        else
          MutOK := False;
      LastSegmentOnly:
        if SegmentCounter <> LastSegment then
          MutOk := False;
      AnySegment:
      {No need for action.  MutOK retains its present value}
    end; {cases}
  IsSegmentMutOK := MutOK;
end;

function doMutation(which: AtomHdl; target: AtomHdl; muts: ArthromorphMutationsHandle;
  change: ChangeType): boolean;
var
  ok: boolean;
  extraClaw: AtomHdl;
  factor: real;
begin
  with muts^^ do
    with target^^ do
    begin
      OK := True;
      if ((change = ChangeDuplicateEntireSubtree) or
        (change = ChangeDeleteSubtree)) and ((Kind = Claw)) then
        OK := False; {Forbid delete or dup of claw}
      if ((change = ChangeTwiddleAngle) or
        (change = ChangeReverseAngle)) and
        ((kind = AnimalTrunk) or (kind = SegmentTrunk)) then
        {These atoms have no Angle part. SectionTrunk does, }
        {because 'angle' is overlap, by convention}
        OK := False;
      if OK then
        case change of
          ChangeTwiddleHeight:
          begin
            factor := GetFactor(mutationPressure);    {See table above}
            if heightMut then
              Height := Height * factor
            else
              OK := False;
          end;
          ChangeTwiddleWidth:
          begin
            factor := GetFactor(mutationPressure);    {See table above}
            if WidthMut then
              Width := Width * factor
            else
              OK := False;
          end;
          ChangeTwiddleAngle:
          begin
            factor := GetFactor(mutationPressure);    {See table above}
            if AngleMut then
            begin
              Angle := angle * factor;
              if (kind = SectionTrunk) then
              begin
                angle := abs(angle); {forbid backward overlaps}
                if angle > 1 then
                  angle := 1; {Otherwise disembodied segments}
              end;
            end
            else
              OK := False;
          end;
          ChangeDuplicateEntireSubtree:
          begin
            if DuplicationMut then
            begin
              if kind = AnimalTrunk then
                {Special case, means GradientFactor}
                gradientGene := gradientGene + 1
              else
                NextLikeMe := copyExceptNext(target);
              {Insert copy of me after me}
              {CopyExceptNext makes sure NextLikeMe of copy }
              {now points to old NextLikeMe of target}
              {So brothers are kept, and new subtree is inserted}
              {last joint has claw.  When duplicate,}
              {get rid of extra claw}
              if (kind = Joint) and (firstBelowMe <> nil) then
              begin
                extraClaw := firstBelowMe;
                firstBelowMe := nil;
                Kill(extraClaw);
              end;
              {A little wasteful to count entire animal again}
              which^^.Angle := countAtoms(which);
            end
            else
              OK := False;
          end; {change=4}
          ChangeDeleteSubtree:
          begin
            if deletionMut then
            begin
              if kind = AnimalTrunk then
                {special case. by convention means GradientFactor}
                gradientGene := gradientGene - 1;
              {Delete.  Complex because we need to}
              { talk to the atom above where we delete}
              OK := doDelete(target);  {there is a problem here}
              if OK then
                which^^.angle := countAtoms(which);

              {A little wasteful to count entire animal again}
            end
            else
              OK := False;
          end;
          ChangeReverseAngle:
            if (kind <> SectionTrunk) then
            begin
              if angleMut then
                angle := -angle {reverse an angle}
              else
                OK := False;
            end
        end;
    end;
  doMutation := OK;
end;

function mutate(muts: ArthromorphMutationsHandle; which: AtomHdl): boolean;
  {Mutate first picks an atom randomly from the Animal.}
  {  From num of atoms, picks one and step down to it}
  {    Flip a coin for what to do: change height, Width, Angle,}
  {   Dup part, Delete part, Flip angle}
  {      Test if legal to do it and do it (else return false)}
  {        Delete does not delete the first-and-only of its Kind}
  {Forbid: Angle mod if none, delete last Section, or Seg }
  {    Delete Animal, Dup Animal,   Delete Claw, Dup Claw}
  {Range limits on some modifications??  Only angles can be negative.}
var
  size, pick: integer;
  target: AtomHdl;
  change: ChangeType;
  lastSegment: integer;
  {atomNumber: integer; for debugging ABC}
  OK, MutOK: boolean;
  state: SegCountStateHandle;
begin

  with muts^^ do
  begin
    if which^^.kind <> AnimalTrunk then
      TellError('Not an animal');
    state := SegCountStateHandle(NewHandle(SizeOf(SegCountState)));
    state^^.secondSegmentAtomNo := 0;
    state^^.Count := 0;
    state^^.segmentCounter := 0;
    {atomNumber := countAtoms(which); for debugging ABC}
    lastSegment := state^^.segmentCounter;
    {As a convention, we keep the number of Atoms in this animal}
    {in AnimalTrunk's Angle field}
    size := trunc(which^^.angle);
    {a number from 1 to size.  Index of the atom we will modify}
    pick := randint(size);
    target := findNth(which, pick, state);    {find the Nth atom}
    if target = nil then
    begin
      {Aren't pick atoms in this Animal}
      TellError('Atom count is wrong.  Fatal.  Quitting');
      ExitToShell;
    end;

    {Decide what to do}
    change := ChangeType(randInt(7)); {seven basic operations}
    { 1 twiddle height, 2 twiddle Width, 3 twiddle Angle}
    {4 Duplicate entire subtree, 5 Delete subtree}
    { 6 reverse an angle , 7 reverse sign of Gradient}

    if (change = ChangeReverseGradientSign) and
      (target^^.kind = AnimalTrunk) then
      target^^.gradientGene := -target^^.gradientGene;

    if (change = ChangeDuplicateEntireSubtree) then
      {If Dup and target is second or third part of an Animal, Section, or Segment,}
      {Then jump down to the next part of the animal}
    begin
      if (target^^.kind = AnimalJoint) or
        (target^^.kind = SectionJoint) or (target^^.kind = SegmentJoint) then
      begin
        target := target^^.nextLikeMe;   {AnimalClaw}
      end;
      if (target^^.kind = AnimalClaw) or
        (target^^.kind = SectionClaw) or (target^^.kind = SegmentClaw) then
        target := target^^.firstBelowMe;
      {SectionTrunk .. where we want to be }
    end;

    MutOK := isMutOK(target, muts) and IsSegmentMutOK(
      target, muts, state^^.segmentCounter,
      state^^.secondSegmentAtomNo, lastSegment, state^^.Count);

    if MutOK then
      OK := doMutation(which, target, muts, change);
  end;
  DisposeHandle(Handle(state));
  Mutate := OK and MutOK;
end;


end.

