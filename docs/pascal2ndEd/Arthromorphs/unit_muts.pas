unit unit_muts;

{$mode macpas}{$H+}

interface


type
	Pressure = (Positive, Zero, Negative);
	Concentration = (FirstSegmentOnly, LastSegmentOnly, AnySegment);


  type ChangeType = (
    ChangeNone,
    ChangeTwiddleHeight,
    ChangeTwiddleWidth,
    ChangeTwiddleAngle,
    ChangeDuplicateEntireSubtree,
    ChangeDeleteSubtree,
    ChangeReverseAngle,
    ChangeReverseGradientSign
  );


  {Mutation flags controlled by Engineering Window}
  ArthromorphMutationsHandle = ^ArthromorphMutationsPtr;
  ArthromorphMutationsPtr = ^ArthromorphMutations;
  ArthromorphMutations = RECORD
    AnimalTrunkMut: BOOLEAN;
    AnimalLegsMut: BOOLEAN;
    AnimalClawsMut: BOOLEAN;
	  SectionTrunkMut: BOOLEAN;
    SectionLegsMut: BOOLEAN;
    SectionClawsMut: BOOLEAN;
    SegmentTrunkMut: BOOLEAN;
    SegmentLegsMut: BOOLEAN;
    SegmentClawsMut: BOOLEAN;
    TrunkMut: BOOLEAN;
    LegsMut: BOOLEAN;
    ClawsMut: BOOLEAN;
	  WidthMut: BOOLEAN;
    heightMut: BOOLEAN;
    AngleMut: BOOLEAN;
    DuplicationMut, DeletionMut: BOOLEAN;
    MutationPressure: Pressure;
		FocusOfAttention: Concentration;
  end;

  function NewMutations: ArthromorphMutationsHandle;


implementation


{$IFC UNDEFINED THINK_Pascal}
  uses Memory;
{$ENDC}

procedure MakeAllBodyMutations(muts: ArthromorphMutationsHandle; State: boolean);
begin
  with muts^^ do
    begin
      TrunkMut := State;
      LegsMut := State;
      ClawsMut := State;
      AnimalTrunkMut := State;
      AnimalLegsMut := State;
      AnimalClawsMut := State;
      SectionTrunkMut := State;
      SectionLegsMut := State;
      SectionClawsMut := State;
      SegmentTrunkMut := State;
      SegmentLegsMut := State;
      SegmentClawsMut := State;

    end;
end;

procedure MakeAllAtomMutations(muts: ArthromorphMutationsHandle; State: boolean);
begin
  with muts^^ do
    begin
      WidthMut := State;
      heightMut := State;
      AngleMut := State;
      DuplicationMut := State;
      DeletionMut := State;
    end;
end;

function NewMutations: ArthromorphMutationsHandle;
var
  muts: ArthromorphMutationsHandle;
begin
  muts := ArthromorphMutationsHandle(NewHandle(SizeOf(ArthromorphMutations)));
  {Initialize Mutations Flags}
  MakeAllBodyMutations(muts, True);
  MakeAllAtomMutations(muts, True);
  with muts^^ do
    begin
      MutationPressure := Zero;
      FocusOfAttention := AnySegment;
    end;
  NewMutations := muts;
end;


end.

