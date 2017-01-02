unit unit_mandelbrot;



interface

{$IFC UNDEFINED THINK_Pascal}
uses Types;
{$ENDC}

const horizResolution = 320;
  vertResolution = 240;

type
  ComplexNumber = record
    r: extended;
    i: extended;
  end;
  ColArray = array[0..horizResolution - 1] of INTEGER;
  RowArrayPtr = ^RowArray;
  RowArray = array[0..vertResolution - 1] of ColArray;
  MandelGridPtr = ^MandelGrid;
  MandelGrid = record
    rows: RowArrayPtr;
    topleft, bottomright: ComplexNumber;
    boundsRect: Rect;
    maxCount: INTEGER;
  end;


implementation
{$IFC UNDEFINED THINK_Pascal}
uses Quickdraw, Memory;
{$ENDC}

function CalcZ(grid: MandelGridPtr; p: Point): ComplexNumber;
var z: ComplexNumber;
begin
  with grid^ do
    begin
      z.r := topleft.r + (p.h -  boundsRect.left) * (bottomright.r - topleft.r) / (boundsRect.right - boundsRect.left);
      z.i := topleft.i + (p.v -  boundsRect.top) * (bottomright.i - topleft.i) / (boundsRect.bottom - boundsRect.top);
    end;
  CalcZ := z;
end;

function AddComplex(a,b: ComplexNumber): ComplexNumber;
var z: ComplexNumber;
begin
  z.r := a.r + b.r;
  z.i := a.i + b.i;
  AddComplex := z;
end;

function MultiplyComplex(a,b: ComplexNumber): ComplexNumber;
var z: ComplexNumber;
begin
  z.r := a.r * b.r - a.i * b.i;
  z.i := 2 * a.i * b.i;
  MultiplyComplex := z;
end;

function SquareComplex(a: ComplexNumber): ComplexNumber;
begin
  SquareComplex := MultiplyComplex(a,a);
end;

function IterateMandelbrot(z, c: ComplexNumber): ComplexNumber;
begin
  IterateMandelbrot := AddComplex(SquareComplex(z), c);
end;

function FurtherThan2FromOrigin(a: ComplexNumber): boolean;
begin
  FurtherThan2FromOrigin :=  a.r * a.r + a.i * a.i > 4;
end;

function MakeComplex(a,b: Extended): ComplexNumber;
var z: ComplexNumber;
begin
   z.r := a;
   z.i := b;
   MakeComplex := z;
end;


function calculateIterations(c: ComplexNumber; maxCount: INTEGER): INTEGER;
var
  z: ComplexNumber;
  n: LONGINT;
begin
  z := MakeComplex(0,0);
  n := 0;
  while (n < maxCount) and not FurtherThan2FromOrigin(z) do
    begin
      z := IterateMandelBrot(z, c);
      n := n + 1;
    end;
  if n = maxCount then
    calculateIterations := 0
  else
    calculateIterations := n;
end;



procedure MandelbrotArray(grid: MandelGridPtr);
var
  v, h: integer;
  p: Point;

begin
  for v := 0 to vertResolution - 1 do
    for h := 0 to horizResolution - 1 do
      begin
        p.h := h;
        p.v := v;

        grid^.rows^[v][h] := CalculateIterations(CalcZ(grid, p), grid^.maxCount);
      end;
end;


procedure PopulateBasicGrid(g: MandelGridPtr);
begin
  g^.topleft := MakeComplex(-2,2);
  g^.bottomright := MakeComplex(2,-2);
  g^.maxCount := 100;
  SetRect(g^.boundsRect, 0,0, horizResolution, vertResolution);

end;

procedure Scan;
  var grid: MandelGridPtr;
begin
  New(grid);
  PopulateBasicGRid(grid);
  MandelBrotArray(grid);
end;

end.
