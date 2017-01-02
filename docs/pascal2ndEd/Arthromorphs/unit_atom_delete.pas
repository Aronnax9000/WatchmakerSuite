unit unit_atom_delete;



interface

uses unit_atoms;

{Delete a section of the animal somewhere near the atom which.}
{Caller must correct the AtomCount of the whole animal.}
{Return false if failed}
{Must have a hold on the atom above what we delete.  If chosen atom is:  }
{AnimalTrunk   delete first Sec}
{  AnimalJoint   delete first Sec}
{  AnimalClaw  delete first Sec}
{    SectionTrunk  delete next Sec}
{      SectionJoint    delete first Seg}
{      SectionClaw    delete first Seg}
{        SegmentTrunk    delete next Seg}
{          SegmentJoint    delete first Joint}
{          SegmentClaw    delete first Joint}
{            Joint        delete next Joint}
{            Joint        delete next Joint}
{            Joint        delete Claw}
{              Claw        fail}
{Also fail if trying to delete last example of a Kind}
function doDelete(which: AtomHdl): boolean;

implementation

function doDelete(which: AtomHdl): boolean;
var
  parent, chain: AtomHdl;
  theResult: boolean;
begin
  theResult := False;  {unless we actually succeed in killing one}
  parent := which;
  if (parent^^.kind = AnimalTrunk) then
  begin
    parent := parent^^.firstBelowMe;    {AnimalJoint}
  end;
  if (parent^^.kind = AnimalJoint) or (parent^^.kind = SectionJoint) or
    (parent^^.kind = SegmentJoint) then
  begin
    parent := parent^^.firstBelowMe;    {AnimalClaw is parent}
  end;
  if parent <> nil then
    with parent^^ do
      if (kind = SectionTrunk) or (kind = SegmentTrunk) or (kind = Joint) then
      begin    {Delete NextLikeMe of parent}
        if (nextLikeMe <> nil) then
        begin
          {May be 0}
          chain := nextLikeMe^^.nextLikeMe;
          {So Kill won't get the rest of chain}
          nextLikeMe^^.nextLikeMe := nil;
          {won't be killing last one, since parent qualifies as one}
          kill(nextLikeMe);
          nextLikeMe := chain;
          theResult := True;
        end;
      end
      else    {Try to delete FirstBelow}
      if (firstBelowMe <> nil) then
        {we know FirstBelow exists}
      begin
        {Atom after one we will delete}
        chain := firstBelowMe^^.nextLikeMe;
        FirstBelowMe^^.NextLikeMe := nil;
        if (chain <> nil) then      {FirstBelow is not only one }
        begin
          kill(firstBelowMe);
          firstBelowMe := chain;
          theResult := True;
        end;
      end;

  doDelete := theResult;
end; {DoDelete}

end.

