unit unit_grow_child;



interface

uses
  unit_preferences, unit_boxes;

type
  GrowChildOperation = (
    DrawingSparkline,
    Waiting,
    ErasingSparkline,
    Reproducing,
    Drawing,
    GrowChildComplete);

  GrowChildStateHandle = ^GrowChildStatePtr;
  GrowChildStatePtr = ^GrowChildState;

  GrowChildState = record
    operation: GrowChildOperation;
    theBox: AnimalBoxHandle;
    VerticalOffset: integer;
    preserveLivingSpecimens: boolean;
  end;

procedure GrowChild(prefs: ArthromorphPreferencesHandle; state: GrowChildStateHandle);
function NewGrowChildState(theBoxes: BoxesHandle): GrowChildStateHandle;
procedure DisposeGrowChildState(state: GrowChildStateHandle);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Quickdraw, Memory, Types,
{$ENDC}
  unit_delay, unit_atoms, unit_reproduce,
  unit_draw, unit_tell_error;

function NewGrowChildState(theBoxes: BoxesHandle): GrowChildStateHandle;
var
  state: GrowChildStateHandle;
begin
  state := GrowChildStateHandle(NewHandle(SizeOf(GrowChildState)));
  with state^^ do
  begin
    operation := DrawingSparkLine;
    theBox := theBoxes^^.firstAnimalBox;
    VerticalOffset := 0;
  end;
  NewGrowChildState := state;
end;

procedure DisposeGrowChildState(state: GrowChildStateHandle);
begin
  DisposeHandle(Handle(state));
end;

procedure DrawSparkLine(theBoxes: BoxesHandle; theAnimalBox: AnimalBoxHandle);
begin
  {Switch the Pen to XOR Mode, to exactly reverse all pixles on the draw}
  {This is so the sparkline can be erased by reversing them all back on}
  {the second draw}
  PenMode(PatXor);
  with theBoxes^^ do
  begin
    {Draw the sparkline}
    MoveTo(midBox^^.Centre.h, midBox^^.Centre.v);
    LineTo(theAnimalBox^^.Centre.h, theAnimalBox^^.Centre.v);
    {Go back into a normal Pen mode.}
    PenMode(PatCopy);
  end;
end;

procedure EraseSparkLine(theBoxes: BoxesHandle; theAnimalBox: AnimalBoxHandle);
begin
  {DrawSparkline uses PenMode(PatXor), so the same routine that draws it}
  {will erase it.}
  DrawSparkline(theBoxes, theAnimalBox);
end;

{GrowChild draws a sparkline between the centre of the midbox}
{and the box centre for box j. }
procedure GrowChild(prefs: ArthromorphPreferencesHandle; state: GrowChildStateHandle);
var
  segState: SegCountStateHandle;
begin
  with state^^ do
    with prefs^^ do
      with theBoxes^^ do
      begin
        case operation of
          DrawingSparkline:
          begin
            Cliprect(pRect);
            DrawSparkline(theBoxes, theBox);
            operation := Waiting;
          end;

          Waiting:
          begin
            DelayTicks(tickdelay);
            operation := ErasingSparkLine;
          end;

          ErasingSparkLine:
          begin
            EraseSparkline(theBoxes, theBox);
            operation := Reproducing;
          end;

          Reproducing:
          begin
            {Unless we're the parent (the animal in the MidBox) kill the animal}
            {that lives there. Since the Second Edition assigns rather than}
            {copies the special (selected) animal to the MidBox, doing the}
            {comparison here by animal rather than by box allows the cull}
            {to skip culling animals in boxes that are also in the midbox.}
            with theBox^^ do
            begin
              if BreedersChoice <> midBox^^.BreedersChoice then
                kill(BreedersChoice);
              {Populate the new box with a freshly reproduced animal}
              BreedersChoice :=
                reproduce(muts, midBox^^.BreedersChoice);

              {Do a sanity check to make sure we're going to draw a valid animal}
              if (theBox^^.BreedersChoice^^.kind <> AnimalTrunk) then
                TellError('Breeders Choice is not an animal');
              segState := SegCountStateHandle(NewHandle(SizeOf(SegCountState)));
              segState^^.segmentCounter := 0;
              segState^^.count := 0;
              segState^^.secondSegmentAtomNo := 0;

              CountSeg(BreedersChoice, segState);
              DisposeHandle(Handle(segState));
              operation := Drawing;
            end;
          end;

          Drawing:
          begin
            verticalOffSet :=
              DrawInBox(prefs, theBox, verticaloffset);
            operation := GrowChildComplete;
          end;

        end;
      end;
end;

end.
