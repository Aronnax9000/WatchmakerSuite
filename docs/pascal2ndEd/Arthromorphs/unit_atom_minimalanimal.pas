unit unit_atom_minimalanimal;

{$mode macpas}{$H+}

interface

uses
  unit_atoms;


function MinimalAnimal: AtomHdl;

implementation
function NewAtom: AtomHdl;
  {Create a new atom with generic values in it}
  {NewAtom has 1.0 in factors and 0 in pointers as a nice default}
var
  new: AtomHdl;
begin
  new := AllocateAtom;

  with new^^ do
  begin
    Height := 1.0;
    Width := 1.0;
    Angle := 1.0;
    NextLikeMe := nil;
    FirstBelowMe := nil;
  end;
  NewAtom := new;
end;

{Original documentation states: I still vote for AnimalJoint . Width = 20}
{and AnimalJoint . Angle = 0.25 in the default animal .}
function MinimalAnimal: AtomHdl;
var
  aa, bb: AtomHdl;
begin

      aa := NewAtom;
      aa^^.Kind := Claw;

      bb := NewAtom;
      bb^^.Kind := Joint;
      bb^^.Width := 5;
      bb^^.angle := 2;
      bb^^.FirstBelowMe := aa;

      aa := NewAtom;
      aa^^.Kind := SegmentClaw;
      aa^^.FirstBelowMe := bb;

      bb := NewAtom;
      bb^^.Kind := SegmentJoint;
      bb^^.NextLikeMe := aa;
      bb^^.angle := 2;

      aa := NewAtom;
      aa^^.Kind := SegmentTrunk;
      aa^^.FirstBelowMe := bb;

      bb := NewAtom;
      bb^^.Kind := SectionClaw;
      bb^^.FirstBelowMe := aa;

      aa := NewAtom;
      aa^^.Kind := SectionJoint;
      aa^^.NextLikeMe := bb;

      bb := NewAtom;
      bb^^.Kind := SectionTrunk;
      bb^^.Angle := 0.5; {Segment overlap, by convention}
      bb^^.FirstBelowMe := aa;

      aa := NewAtom;
      aa^^.Kind := AnimalClaw;
      aa^^.FirstBelowMe := bb;

      bb := NewAtom;
      bb^^.Kind := AnimalJoint;
      bb^^.NextLikeMe := aa;
      bb^^.Width := 5;    {make it visible}
      bb^^.angle := 5;

      aa := NewAtom;
      aa^^.Kind := AnimalTrunk;
      aa^^.FirstBelowMe := bb;
      aa^^.gradientGene := -2; {Gradient, by convention}
      aa^^.Angle := CountAtoms(aa);
      aa^^.Height := 20;
      aa^^.Width := 20;
  MinimalAnimal := aa;
end;

end.

