{The Project should have the following files in it:     }
{     µRunTime.lib     LSP This is for main Pascal runtime library}
{     Interface.lib      LSP This is the Mac trap interfaces}
{     PrintCalls.Lib    LSP This is the print routine library interface}
{     MacPrint.p        LSP This is the print equates for print calls}
{     InitTheMenus.Pas      This initializes the Menus.}
{     Error_Alert       Alert}
{     Preferences       Modal Dialog}
{     Engineering_Window       Modeless Dialog}
{     Genome_Window       Window}
{     Breeding_Window       Window}
{     About_Arthromorphs       Window}
{    HandleTheMenus         Handle the menu selections.}
{Set  RUN OPTIONS to use the  resource file Brand_New.RSRC  }
{ RMaker file to use is Brand_New.R  }
{   Brand_New.Pas      Main program  }
program Brand_New;
{Program name:  Brand_New.Pas  }
{Function:  This is the main module for this program.  }
{History: 12/15/90 Original by Prototyper.   }
{                       }
	uses
		{$ifc  undefined THINK_PASCAL}
		MacOSAll,
		{$endc}
		MyGlobals, Error_Alert, 
		Preferences, Engineering_Window, Genome_Window, Breeding_Window, About_Arthromorphs, InitTheMenus, 
		HandleTheMenus, Initialize, HandleEvents, CarbonEventHandlers, Drawing, Ted;


	procedure QuitGracefully;
		var
			j: integer;
	begin
		for j := 1 to YardSize do
			DisposeHandle(Handle(BoneYard[j]));
		for j := 1 to MiniSize do
			DisposeHandle(Handle(MiniYard[j]));
		for j := 1 to NRows * NCols do
			KillPicture(AnimalPicture[j]);
	end; {QuitGracefully}


begin   								{Start of main body}

	MoreMasters;    				{This reserves space for more handles}
	{$ifc not undefined THINK_PASCAL}
	InitGraf(@thePort); 			{Quickdraw Init}
	InitFonts;  					{Font manager init}
	InitWindows;    				{Window manager init}
	InitMenus;  					{Menu manager init}
	TEInit; 						{Text edit init}
	InitDialogs(nil);   			{Dialog manager}
	{$endc}
	FlushEvents(everyEvent, 0);{Clear out all events}
	InitCursor; 					{Make an arrow cursor}
	Init_My_Menus;  				{Initialize menu bar}

	MyInit;
	{$ifc not undefined THINK_PASCAL}
	ClassicEventLoop;
	{$elsec}
	InstallEventHandler(GetApplicationEventTarget, CommandHandler, 1, @commandEventType, nil, nil);
	RunApplicationEventLoop;
	{$endc}
	QuitGracefully;

end.
