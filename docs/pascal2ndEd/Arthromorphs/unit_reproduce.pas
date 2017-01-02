unit unit_reproduce;

interface
uses

  unit_muts, unit_mutate, unit_atoms;
function Reproduce (muts: ArthromorphMutationsHandle; which: AtomHdl): AtomHdl;


implementation
uses
{$IFC UNDEFINED THINK_Pascal}
  Types,
{$ENDC}
  unit_tell_error;
{Reproduce copies an animal and calls Mutate}
{Please kill the old animal before calling this.}
{We may need to use his atoms.}
function Reproduce(muts: ArthromorphMutationsHandle; which: AtomHdl): AtomHdl;
	var
		counter: INTEGER;
    newAnimal: AtomHdl;
		done: boolean;
    er: Str255;
begin
	counter := 0;
	newAnimal := Copy(which);
	repeat
		counter := counter + 1;
    {If it fails, just try again until we succeed at changing something}
		done := Mutate(muts, newAnimal);
	until done or (counter > 1000);
	if counter > 1000 then
		begin
      er :='Timed out, perhaps attempting impossible duplication or deletion';
			TellError(er);
			Reproduce := which;
		end
	else
		Reproduce := newAnimal;		{Return it}
end;

end.

