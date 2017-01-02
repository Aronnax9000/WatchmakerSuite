unit unit_slide;

{$mode macpas}{$H+}

interface

uses
{$IFC UNDEFINED THINK_Pascal}
  Types, Quickdraw,
{$ENDC}
  unit_preferences;


type

  SlideStateHandle = ^SlideStatePtr;
  SlideStatePtr = ^SlideState;
  SlideState = RECORD
  tickDelay: longint;
  discrepThreshold: longint;
  decimationFactor: Longint;
  xToMove: integer;
  yToMove: integer;
  xMoved: integer;
  yMoved: integer;
  dx: integer;
  dy: integer;
  slideRect: Rect;
  upregion: RgnHandle;
end;

function NewSlideState(
    prefs: ArthromorphPreferencesHandle;
    LiveRect,
    DestRect: Rect): SlideStateHandle;
procedure DisposeSlideState(state: SlideStateHandle);
function SlideFrame(
  state: SlideStateHandle
  ): boolean;

implementation
uses
{$IFC UNDEFINED THINK_Pascal}
  Memory,
{$ENDC}
  unit_delay;

function sgn(x: integer): integer;
begin
  if x < 0 then
    sgn := -1
  else if x > 0 then
    sgn := 1
  else
    sgn := 0;
end; {sgn}


function SlideFrame(
  state: SlideStateHandle
  ): boolean;
var
  xDiscrep, yDiscrep: integer;
  dh, dv: integer;

begin
  with state^^ do
  begin
    xDiscrep := xToMove - xMoved;

    if xDiscrep <= discrepThreshold then
      dh := xDiscrep
    else
      dh := (xDiscrep) div decimationFactor;
    if dh = 0 then dh := 1;

    yDiscrep := yToMove - yMoved;
    if Ydiscrep <= discrepThreshold then
      dv := yDiscrep
    else
      dv := (yDiscrep) div decimationFactor;
    if dv = 0 then dv := 1;

    if (xMoved < xToMove) or (yMoved < yToMove) then
      ScrollRect(SlideRect, dx * dh, dy * dv, upregion);
    xMoved := xMoved + ABS(dh);
    yMoved := yMoved + ABS(dv);
    DelayTicks(tickDelay);
    SlideFrame := (xMoved >= xToMove) and (yMoved >= yToMove);
  end;
end;

function NewSlideState(
  prefs: ArthromorphPreferencesHandle;
  LiveRect,
  DestRect: Rect): SlideStateHandle;
var state: SlideStateHandle;
  distx, disty: integer;
begin
  state := SlideStateHandle(NewHandle(sizeOf(SlideState)));
  distx := DestRect.left - LiveRect.left;
  disty := DestRect.bottom - LiveRect.bottom;
  with state^^ do
  begin
    dx := sgn(distx);
    dy := sgn(disty);
    xToMove := ABS(distx);
    yToMove := ABS(disty);
    xMoved := 0;
    yMoved := 0;
    tickDelay := prefs^^.tickDelay;
    decimationFactor := prefs^^.decimationFactor;
    discrepThreshold := prefs^^.discrepThreshold;
    upregion := NewRgn;
    {SlideRect will be resized so that it encloses LiveRect and DestRect}
    UnionRect(LiveRect, DestRect, SlideRect);
    end;
  NewSlideState := state;
end;

procedure DisposeSlideState(state: SlideStateHandle);
begin
  DisposeRgn(state^^.upregion);
  state^^.upregion := nil;
  DisposeHandle(Handle(state));
end;



end.

