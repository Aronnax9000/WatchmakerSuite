unit unit_draw;

interface

uses
{$IFC UNDEFINED THINK_Pascal}
{$ENDC}
  unit_atoms, unit_boxes, unit_preferences;

{$IFC UNDEFINED THINK_Pascal}
{$ENDC}
function DrawInBox(prefs: ArthromorphPreferencesHandle; theAnimalBox: AnimalBoxHandle;
  verticalOffset: integer): integer;

implementation

  {$IFC UNDEFINED THINK_Pascal}
uses
  QuickDraw, OSUtils, Types;

  {$ENDC}

type
  PolesPtr = ^Poles;
  Poles = record
    NorthPole, SouthPole, WestPole, EastPole: integer;
  end;

  {CumParams: where the AnimalTrunk.Width is multiplied by SegmentTrunk.Width}
  CumParams = array[1..9] of real;

  procedure Dline(x, y, endx, endy, thick: integer; thePoles: PolesPtr);
  begin
    {thick := round(thick div thickscale);}
    {if thick < 1 then thick := 1;}
    with thePoles^ do
    begin
      if endy < NorthPole then
        NorthPole := endy;
      if endy > SouthPole then
        SouthPole := endy;
      if endx < WestPole then
        WestPole := endx;
      if endx > EastPole then
        EastPole := endx;
    end;
    PenSize(thick, thick);
    MoveTo(x - thick div 2, y - thick div 2);
    LineTo(endX - thick div 2, endY - thick div 2);
    PenSize(1, 1);
  end;

procedure DrawALine(x, y, endx, endy, thick: integer; thePoles: PolesPtr;
  sideways: boolean);
begin
  if sideways then
    Dline(y, x, endy, endx, thick, thePoles)
  else
    Dline(x, y, endx, endy, thick, thePoles);
end; {Drawline}

procedure DOval(x, y, Width, Height: integer; thePoles: PolesPtr; wantColor: boolean);
var
  r: rect;
begin
  with r do
  begin
    left := x;
    top := y;
    right := left + Width;
    bottom := top + Height;
    with thePoles^ do
    begin
      if top < NorthPole then
        NorthPole := top;
      if bottom > SouthPole then
        SouthPole := bottom;
      if left < WestPole then
        WestPole := left;
      if right > EastPole then
        EastPole := right;
    end;
  end;
  if WantColor then
  begin
    backcolor(GreenColor);
    eraseOval(r);
  end
  else
    fillOval(r, ltgray);
  pensize(2, 2);
  frameOval(r);
  pensize(1, 1);
  backColor(whiteColor);
end;

procedure DrawOval(x, y, Width, Height: integer; thePoles: PolesPtr;
  sideways: boolean; wantColor: boolean);
begin
  if sideways then
    DOval(y, x, Height, Width, thePoles, wantColor)
  else
    DOval(x, y, Width, Height, thePoles, wantColor);
end; {DrawOval}

procedure DrawSeg(x, y: integer; Width, Height: real; thePoles: PolesPtr;
  sideWays, wantColor: boolean);
{We must adjust the position before drawing the oval}
var
  halfW: integer;
begin
  Width := Width;
  Height := Height;
  halfW := round(Width / 2);
  DrawOval(x - halfW, y, round(Width), round(Height), thePoles, sideways, wantColor);
  forecolor(BlackColor);
  {convert from center of oval to left corner}
end;{DrawSeg}


procedure DrawClaw(params: CumParams; x, y, xCenter: integer;
  thePoles: PolesPtr; sideWays: boolean);
{Draw a claw, note that we don't use which at all.  Param info is already folded in}
var
  oldX, oldY, leftOldX, leftX, thick: integer;
  ang: real;
begin
  foreColor(RedColor);
  oldX := x;
  oldY := y;
  ang := params[9] / 2.0;
  {half claw opening, in radians}
  x := round(x + params[8] * sin(ang));    {line end point   width*sine(angle)}
  y := round(y + params[8] * cos(ang));    {line end point}
  thick := 1 + trunc(params[7]);    {1 is minimum thickness}
  DrawALine(oldX, oldY, x, y, thick, thePoles, sideways);    {right side, top of claw}

  leftX := xCenter - (x - xCenter);    {do the left side, top of claw}
  leftOldX := xCenter - (oldX - xCenter);
  DrawALine(leftOldX, oldY, leftX, y, thick, thePoles, sideways);

  {Bottom of the claw}
  y := round(y - 2.0 * params[8] * cos(ang));
  DrawALine(oldX, oldY, x, y, thick, thePoles, sideways);        {right side}
  DrawALine(leftOldX, oldY, leftX, y, thick, thePoles, sideways);  {left side}
  ForeColor(BlackColor);
end;

procedure Draw(prefs: ArthromorphPreferencesHandle; which: AtomHdl;
  params: CumParams; x, y, xCenter: integer; var ySeg: integer;
  gradientFactor, overlap: real; thePoles: PolesPtr);
{Starting at the atom 'which', multiply its numbers into the array of params.}
{At the bottom, draw the part starting at x,y}
{params accumulates the final Joint width, Claw angle, etc.}
{params: 1 Seg height, 2 Seg width, 3 (not used), 4 Joint thickness, 5 Joint length, 6 Joint angle,}
{   7 Claw thickness, 8 Claw length, 9 Claw angle between pincers}
{x,y are current local point, xCenter is the centerline of the animal (left and right Joints need this)}
var
  myPars: CumParams;
  oldX, oldY, leftOldX, leftX, offset, thick: integer;
  ang, jointscale: real;
begin
  with prefs^^ do
  begin
    jointscale := 0.5;
    myPars := params;
    {local copy so next segment builds on section above, not this segment}
    with which^^ do
    begin
      if kind = AnimalTrunk then
      begin
        GradientFactor := GradientGene;
        if GradientFactor > 1000 then
          sysbeep(1);
      end;
      offset := ParamOffset[Kind];    {where in params to begin, see InitBoneYard}
      params[offset] := params[offset] * Height;    {fold in this atom's params}
      params[offset + 1] := params[offset + 1] * Width;
      params[offset + 2] := params[offset + 2] * Angle;
      {Must be a real number, even if not used in this Atom}
      if kind = SectionTrunk then
        overlap := angle;{by convention}
      if Kind = SegmentTrunk then
      begin
        if GradientFactor > 1000 then
          sysbeep(1);
        params[2] := params[2] + GradientFactor * angle;
        Params[1] := Params[1] + GradientFactor * angle;
        DrawSeg(x, ySeg, params[2], params[1], thePoles, sideways, wantColor);
        {Draw the oval in the right place. 2 = Width , 1 = Height }
        oldY := ySeg;  {Save for next segment}
        x := x + round(params[2] / 2.0);{joint starts at the side of the segment}
        y := ySeg + round(params[1] / 2.0);
        {joint starts half way down the segment }
      end;
      if Kind = Joint then
      begin
        {both next joint (NextLikeMe) and claw (FirstBelowMe) want x,y at end of this joint}
        oldX := x;
        oldY := y;
        ang := params[6];
        x := round(x + jointscale * params[5] * cos(ang));
        {line end point   width*sine(angle)}
        y := round(y + jointscale * params[5] * sin(ang));    {line end point}
        thick := 1 + trunc(params[4]);    {1 is minimum thickness}
        DrawALine(oldX, oldY, x, y, thick, thePoles, sideways);    {right side leg}
        leftX := xCenter - (x - xCenter);    {do the left side leg}
        leftOldX := xCenter - (oldX - xCenter);
        DrawALine(leftOldX, oldY, leftX, y, thick, thePoles, sideways);
        foreColor(blackColor);
      end;
      if kind = Claw then
        DrawClaw(params, x, y, xCenter, thePoles, sideways)
      {all work is done in here}
      else
        {TED:  why else?  Presumably because claw is the end of the line?}
      begin
        if FirstBelowMe <> nil then
          Draw(prefs, FirstBelowMe, params, x, y, xCenter, ySeg,
            gradientFactor, overlap, thePoles);
        {build on my cumulative numbers}
        if Kind = SegmentTrunk then
        begin
          x := xCenter;
          ySeg := round(oldY + overlap * params[1]);{Seg}
          {Jump down by height of this segment to the next segment}
        end;
        if NextLikeMe <> nil then
        begin
          if (Kind = AnimalJoint) or (Kind = SectionJoint) or (Kind = SegmentJoint) then
            Draw(prefs, NextLikeMe, params, x, y, xCenter, ySeg,
              gradientFactor, overlap, thePoles)    {build on me}
          else if kind <> AnimalTrunk then
            Draw(prefs, NextLikeMe, myPars, x, y, xCenter, ySeg,
              gradientFactor, overlap, thePoles);
          {build on my parent's numbers}
        end;
        {Note that each Joint builds on the length of the SegJoint, not the joint just before it.}
        {This is consistant with the way Sections and Segments work.}
      end;
    end;
  end;
end; {Draw}


procedure DrawAnimal(prefs: ArthromorphPreferencesHandle;
  theAnimalBox: AnimalBoxHandle; x, y: integer; thePoles: PolesPtr);
{An example of how to call Draw for an animal}
var
  params: CumParams;
  ii, ySeg: integer;
begin
  for ii := 1 to 9 do
    params[ii] := 1.0;    {clear it all out}
  ySeg := y;
  with prefs^^ do
    Draw(prefs, theAnimalBox^^.BreedersChoice, params, x, y, x,
      ySeg, 0, 0, thePoles);
  {x = xCenter when we start}
end;

function DrawInBox(prefs: ArthromorphPreferencesHandle; theAnimalBox: AnimalBoxHandle;
  verticalOffset: integer): integer;
var
  where: rect;
  theCentre, start, boxwidth, boxheight: integer;
  thePoles: Poles;
  MidRiff: integer;

begin
  with prefs^^ do
    with theBoxes^^ do
    begin
      where := theAnimalBox^^.Box;
      boxwidth := where.right - where.left;
      boxheight := where.bottom - where.top;

      with thePoles do
      begin
        if sideways then
        begin
          theCentre := where.top + boxheight div 2;
          start := where.left + boxwidth div 2;
          WestPole := Prect.right;
          EastPole := Prect.left;
          if centring or (theAnimalBox = MidBox) then
          begin
            hidePen;
            DrawAnimal(prefs, theAnimalBox, theCentre, start, @thePoles);
            {return with NorthPole and SouthPole updated}
            ShowPen;
            Midriff := WestPole + (EastPole - WestPole) div 2;
            verticalOffset := Start - Midriff;
          end;
        end
        else
        begin
          start := where.top + boxheight div 2;
          theCentre := where.left + boxwidth div 2;
          NorthPole := Prect.bottom;
          SouthPole := Prect.top;
          if centring or (theAnimalBox = MidBox) then
          begin
            hidePen; {Preliminary dummy draw to measure North & South extent of animal}
            DrawAnimal(prefs, theAnimalBox, theCentre, start, @thePoles);
            {return with NorthPole and SouthPole updated}
            ShowPen;
            Midriff := NorthPole + (SouthPole - NorthPole) div 2;
            verticalOffset := Start - Midriff;
          end;
        end;
        with theAnimalBox^^ do
        begin
          if hasPicture then
            KillPicture(AnimalPicture); {redraw Picture in correct position}
          if BreedersChoice <> nil then
          begin
            AnimalPicture := OpenPicture(Box);
            hasPicture := true;
            showpen;
            ClipRect(Box);
            DrawAnimal(prefs, theAnimalBox, theCentre, start + VerticalOffset, @thePoles);
            ClosePicture;
          end;
        end;
      end;
      hidepen;
      ClipRect(Prect);
    end;
  DrawInBox := VerticalOffset;
end; {DrawInBox}

end.
