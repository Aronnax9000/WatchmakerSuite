unit unit_drawpic;

interface

uses
{$IFC UNDEFINED THINK_Pascal}
  Types,
{$ENDC}
  unit_snail_types, unit_miscellaneous;

procedure DrawPic(prefs: SnailPreferencesHandle; Place: Point; var Biomorph: person);
procedure InitDrawPic;

implementation


{$IFC UNDEFINED THINK_Pascal}
uses  Quickdraw, Memory, ToolUtils;

{$ENDC}
var
  RectOfInterest: Rect;
  MugShot, MirrorShot: bitmap;
  CurrentGeneratingCurve: integer;

var
  GenCurveRect: rect;

{Sets the unit-scoped Rect 'GenCurveRect' to 512x512 dimensions, sets the }
{unit scoped variable 'CurrentGeneratingCurve' to 0, then calls MakeOffScreen}
{with the 512x512 rectangle to initialize two 512x512 BitMaps 'MugShot' and}
{'MirrorShot'. The boolean value 'burst' returned by MakeOffScreen is }
{discarded.}
procedure InitDrawPic;
var
  burst: boolean;
begin
  CurrentGeneratingCurve := 0;
  SetRect(GenCurveRect, 0, 0, 511, 511);

  MakeOffScreen(GenCurveRect, MugShot, burst);
  MakeOffScreen(GenCurveRect, MirrorShot, burst);

end;

{Adjusts the 'startPt' and 'endPt' fields of the most recent Lin in a Pic }
{based on the value of 'theScale' from SnailPreferences, and the 'Origin' point}
{of the Pic.}
procedure quarantine(prefs: SnailPreferencesHandle; ThisPic: Pic);
begin
  with ThisPic.MovePtr^ do
    with prefs^^ do
    begin
      startpt.v := thisPic.Origin.v + round(theScale * (Startpt.v - thisPic.Origin.v));
      endpt.v := thisPic.Origin.v + round(theScale * (endpt.v - thisPic.Origin.v));
      startpt.h := thisPic.Origin.h + round(theScale * (Startpt.h - thisPic.Origin.h));
      endpt.h := thisPic.Origin.h + round(theScale * (Endpt.h - thisPic.Origin.h));
    end;
end;

{Retrieves the custom snail outline designated by the Biomorph's }
{'GeneratingCurve' field. Sets the unit-scoped Rect 'RectOfInterest' to a }
{(0,0) origin rectangle of the same dimensions as the outline's frame, but }
{adds 1 to the width if necessary to ensure that RectOfInterest's width is }
{even. Draws the generating curve image to MugShot after first drawing a }
{white rectangle using RectOfInterest. Sets the clipping rectangle to }
{the SnailPrefrences' 'Prect' rectangle, and sets thedrawing target }
{to the supplied SaveBitMap. Finally, calls MirrorBits with MugShot as the }
{source BitMap, the RectOfInterest, and MirrorShot as the destination BitMap.}

  procedure ChangeTheBitMaps(
    prefs: SnailPreferencesHandle; Biomorph: person; var SaveBitMap: BitMap);
  var
    StrangePicture: PicHandle;
  begin
    StrangePicture := GetPicture(Biomorph.GeneratingCurve);
    with StrangePicture^^.PicFrame do
    begin
      SetRect(RectOfInterest, 0, 0, right - left, bottom - top);
      with RectOfInterest do
        if odd(right - left) then
          right := right + 1;
    end;
    ClipRect(RectOfInterest);
    SetPortBits(MugShot);
    FillRect(RectOfInterest, White); {Off screen}
    DrawPicture(StrangePicture, RectOfInterest);
    ClipRect(prefs^^.Prect);
    SetPortBits(SaveBitMap);
    MirrorBits(MugShot, RectOfInterest, MirrorShot);
  end;


  procedure ActualLine(prefs: SnailPreferencesHandle; biomorph: Person;
    place: Point;j,JThreshold: integer; SaveBitMap: BitMap);
  var
    Width, Height: integer;
    temp: integer;
    doMirror, TooThin: boolean;
    VertOffset, HorizOffset: integer;
    y0, y1, x0, x1: integer;
    theRect: rect;
  begin
    with prefs^^ do
      with MyPic.MovePtr^ do
      begin
        quarantine(prefs, MyPic);
        VertOffset := round(theScale * (MyPic.Origin.v - Place.v));
        HorizOffset := round(theScale * (MyPic.Origin.h - Place.h));
        y0 := StartPt.v - VertOffset;
        y1 := EndPt.v - VertOffset;
        x0 := StartPt.h - HorizOffset;
        x1 := EndPt.h - HorizOffset;
      end;
    if not prefs^^.sideView then
    begin
      MoveTo(x0, y0);
      LineTo(x1, y1);
    end
    else
    begin
      doMirror := False;
      {If the end point is left of the start point, the generating curve }
      {will need to be flipped left-to-right. }
      if x1 < x0 then
      begin {here we need a mirror image}
        DoMirror := True;
        temp := x0;
        x0 := x1;
        x1 := temp;
      end;
      setRect(theRect, x0, y0, x1, y1);
      with theRect do
      begin
        Width := abs(right - left);
        Height := abs(bottom - top);
        TooThin := (Width < prefs^^.threshold) or (Height < prefs^^.threshold);
        if (Biomorph.GeneratingCurve = 0) or (prefs^^.theMode = Triangling) or
          ((J < JThreshold) and TooThin) then
        begin
          {No generating curve, just use an oval}
          if Width = 0 then
          begin
            MoveTo(left, top);
            LineTo(right, bottom);
          end
          else
            FrameOval(theRect);
        end
        else
        begin
          {Use a generating curve, which may need to be flipped left-to-right}
          if odd(Width) then
            right := right + 1;
          if not doMirror then
            CopyBits(MugShot, SaveBitMap, RectOfInterest, theRect, srcOr, nil)
          else
            CopyBits(Mirrorshot, SaveBitMap, RectOfInterest, theRect, srcOr, nil);
        end;
      end;
    end;
  end; {ActualLine}

procedure DrawPic(prefs: SnailPreferencesHandle; Place: Point; var Biomorph: person);
{Pic already contains its own origin, meaning the coordinates at which}
{ it was originally drawn. Now draw it at Place}


var
  j: integer;
    Mid2, belly2, RingCounter: integer;
  JThreshold: integer;
  theRect: rect;
  SavePort: GrafPtr;
  SaveBitMap: BitMap;


begin
  GetPort(SavePort);
  SaveBitMap := SavePort^.PortBits;
  with prefs^^.MyPic do
  begin
    if (Biomorph.GeneratingCurve < 128) or (Biomorph.GeneratingCurve >
      MaxResources) then
      Biomorph.GeneratingCurve := 0
    else
    begin
      if (Biomorph.GeneratingCurve <> CurrentGeneratingCurve) then
        ChangeTheBitMaps(prefs, biomorph, SaveBitMap);
    end;
    CurrentGeneratingCurve := Biomorph.GeneratingCurve;
    MovePtr := linptr(BasePtr); {reposition at base of grabbed space}
    JThreshold := PicSize * 7 div 8;
    for j := 1 to PicSize do
    begin
      ActualLine(prefs, biomorph, place, j, jthreshold, SaveBitMap); {sometimes rangecheck error}
      MovePtr := linptr(size(MovePtr) + 8);
    end;
  end;
  PenSize(1, 1);
end; {DrawPic}

end.
