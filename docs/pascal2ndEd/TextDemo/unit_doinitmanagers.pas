unit unit_doinitmanagers;


interface


	procedure DoInitManagers;

implementation
  {$IFC UNDEFINED THINK_Pascal}
  uses
  	Fonts, TextEdit, Dialogs, Quickdraw, Windows, Menus, Events,
    Memory;
  {$ENDC}

	procedure DoInitManagers;

	begin
		MaxApplZone;
		MoreMasters;

		InitGraf(@thePort);
		InitFonts;
		InitWindows;
		InitMenus;
		TEInit;
		InitDialogs(nil);

		InitCursor;
		FlushEvents(everyEvent, 0);
	end;
		{of procedure DoInitManagers}

end.

