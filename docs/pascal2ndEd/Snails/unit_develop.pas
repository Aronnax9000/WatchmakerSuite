unit unit_develop;


interface



uses
{$IFC UNDEFINED THINK_Pascal}
  Types, Quickdraw,
 {$ENDC}
  unit_snail_types;

type RealPoint = RECORD
 x, y: real;
end;

function Develop(prefs: SnailPreferencesHandle;
  var theShell: person;
  Where: point
): point;

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  ToolUtils,
{$ENDC}
  unit_snapshot, unit_findthescale, unit_drawpic;

procedure EnlargeMarginAndDoPicLine(var margin: Rect; var MyPic: Pic;
  p1, p2, mn: RealPoint);
var
  point1: Point;
  point2: Point;
begin
  point1.h := round(p1.x - mn.x);
  point1.v := round(p1.y - mn.y);
  point2.h := round(p2.x - mn.x);
  point2.v := round(p2.y - mn.y);
  with margin do
  begin
    if point1.h < left then
      left := point1.h;
    if point1.v < top then
      top := point1.v;
    if point2.h > right then
      right := point2.h;
    if point2.v > bottom then
      bottom := point2.v;
  end;
  PicLine(MyPic, point1.h, point1.v, point2.h, point2.v);
end;

procedure DrawTop(var margin: Rect; var MyPic: Pic; var theShell: person; where: Point);
var
  mn: RealPoint;
  v1: RealPoint;
  v2: RealPoint;
  u1: RealPoint;
  u2: RealPoint;
  p1: RealPoint;
  p2: RealPoint;
  denom, theSize, W, D: real;
  RAD: real;
  PI, I, BD, WI: real;
  M, start, inc: integer;
  p: real;

begin
  with theShell do
  begin
    DGradient := 1;
    W := WOpening;
    D := DDisplacement;
    Inc := Coarsegraininess;
    start := reach * 360;
    rad := 100;
  end;
  theSize := 0.74;
  denom := 136 * theSize;
  mn.x := (-(100 / denom) * where.h);
  mn.y := (-(100 / denom) * where.v);
  {the smaller or more negative the number,the lower down the page}
  {mxy := 90;}

  V1.x := 0;
  v1.y := 0;
  U1.x := 0;
  U1.y := 0;
  PI := 3.14159;
  m := start;
  repeat
    p := (start - (start - m) * (1 - theShell.DGradient)) / start;
    D := theShell.DDisplacement * p;
    i := m / 360;
    BD := 2 * PI * I;
    WI := RAD * EXP(-I * LN(W));
    V2.x := theShell.handedness * (-WI * COS(BD));
    V2.y := WI * SIN(BD);
    U2.x := V2.x * D;
    U2.y := V2.y * D;
    p1 := v1;
    p2 := v2;
    EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn);
    p1 := p2;
    p2 := u2;
    if m <= 0 then
      EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn);
    p1 := p2;
    p2 := u1;
    EnlargeMarginAndDoPicLine(margin, myPic, p1, p2, mn);
    v1 := v2;
    u1 := u2;
    m := m - Inc;
  until m < 0;
end;



procedure Drawshell(
  var ClipBoarding: boolean;
  var margin: Rect;
  DontDraw: Boolean;
  var MyPic: Pic;
  theShell: person; where: Point; var centre: Point);
var
  rad1, rad2, rad, Twopi, i, fw, W, D, S, T, p: real;
  {r, rad, mny, mnx,}
  m, Inc, Start: integer;
  grunge, f, g, h, k, xc, yc, xr, yr, mnx, mny: real;
  theRect: rect;
  denom, theSize: real;
begin {main procedure Drawshell}
  {Former "Setup" starts here}
  theSize := 0.8;
  denom := 136 * theSize;

  rad := 100;
  Twopi := 2 * 3.14159;
  mny := round(-(100 / denom) * where.v * 1.088);
  mnx := round(-(100 / denom) * where.h * 1.088);

  centre := where;
  ClipBoarding := False;


  with theShell do
  begin
    W := WOpening;
    D := DDisplacement;
    S := SShape;
    {T := TTranslation;}{Reassigned in with theShell below}
    if CoarseGraininess < 1 then
      Coarsegraininess := 1;
    Inc := Coarsegraininess;
    if reach < 1 then
      reach := 1;
    start := reach * 360;
  end; {with theShell}


  rad1 := 1.088 * (rad + rad * D) / 2;
  rad2 := 1.088 * (rad - rad * D) / 2;

  m := start;

  repeat

    p := (start - (start - m) * (1 - theShell.TranslationGradient)) / start;
    T := theShell.TTranslation * p;
    i := m / 360;
    fw := exp(-i * ln(W));
    grunge := fw * cos(TwoPi * i);

    xc := theShell.handedness * (rad1 * grunge);
    yc := -rad1 * T * (1 - fw);
    xr := theShell.handedness * (rad2 * grunge);
    yr := -rad2 * fw * S;
    {the minus signs are to invert the whole snail}

    h := (yc - yr - mny);
    g := (xc - xr - mnx);
    f := (yc + yr - mny);
    k := (xc + xr - mnx);


    with theRect do
    begin
      top := round(f);
      bottom := round(h);
      left := round(g);
      right := round(k);
      if left <= right then
      begin
        if left < margin.left then
          margin.left := left;
        if right > margin.right then
          margin.right := right;
      end
      else
      begin
        if right < margin.left then
          margin.left := right;
        if left > margin.right then
          margin.right := left;
      end;
      if top < margin.top then
        margin.top := top;
      if bottom > margin.bottom then
        margin.bottom := bottom;
    end;
    begin
      if not DontDraw then
        with theRect do
          PicLine(MyPic, left, top, right, bottom);
    end; {DontDraw is set by Pedigree so only Margin is measured, no drawing}
    m := m - Inc;
  until m <= 0;
end; {DrawShell}

function Develop(prefs: SnailPreferencesHandle;
  var theShell: person;
  Where: point
): point;

var
  offCentre: point;
  centre: point;
  Offset, Height, Width, newheight, newwidth, newtop, newbottom,
  newleft, newright: integer;

begin {develop}
  with prefs^^ do
    begin
      SetCursor(CursList[watchCursor]^^);
      ZeroPic(MyPic, where);
      if zeromargin then
        with margin do
        begin
          left := Where.h;
          right := Where.h;
          top := Where.v;
          bottom := Where.v;
        end;
      if SideView then
        DrawShell(ClipBoarding, margin, DontDraw,MyPic, theShell, where, centre)
      else
        DrawTop(margin, MyPic, theShell, where);

      FindTheScale(prefs, theScale);
      with Margin do
      begin
        Height := bottom - top;
        Width := right - left;
        newheight := round(Height * theScale);
        newwidth := round(Width * thescale);
        newtop := top + (Height - newheight) div 2;
        newbottom := newtop + newheight;
        newleft := left + (Width - newwidth) div 2;
        newright := newleft + newwidth;
      end;
      setrect(Margin, newleft, newtop, newright, newbottom);
      with Margin do
      begin
        centre.v := top + (bottom - top) div 2;
        offset := centre.v - where.v;
        Top := Top - Offset;
        Bottom := Bottom - Offset;
        OffCentre.v := Where.v - Offset;
        centre.h := left + (right - left) div 2;
        offset := centre.h - where.h;
        left := left - Offset;
        right := right - Offset;
        OffCentre.h := Where.h - Offset;
      end;
      if not DelayedDrawing then
        DrawPic(prefs, offCentre, theShell);
    end;
  Develop := offCentre;
end; {Develop}

end.
