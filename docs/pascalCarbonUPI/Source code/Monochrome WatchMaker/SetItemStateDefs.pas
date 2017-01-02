unit SetItemStateDefs;

interface

uses 
	Globals,
	MacOSAll,
	MorphGlobals;
procedure SetItemState (Mndx, Indx: Integer; Flag: Boolean);

implementation
procedure SetItemState (Mndx, Indx: Integer; Flag: Boolean);
{}
{    purpose         if true, enables item Indx of menu Mndx; else disables}
{    last update     22 Aug 86}
{}
	begin
		IF Flag THEN
			EnableMenuItem(MenuList[Mndx], Indx)
		ELSE
			DisableMenuItem(MenuList[Mndx], Indx)
	end; { of proc SetItemState }

end.
