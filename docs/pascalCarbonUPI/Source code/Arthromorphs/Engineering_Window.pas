unit Engineering_Window;

{File name:  Engineering_Window.Pas  }
{Function: Handle a dialog}
{History: 1/4/91 Original by Prototyper.   }
{Carbon port by Alan Canon March 1, 2015}
interface

	uses
		{$ifc not undefined THINK_PASCAL}
		ThinkPascalUPIBridge, 
		{$elsec}
		MacOSAll,
		{$endc}
		MyGlobals, Ted, Error_Alert;

	procedure D_Engineering_Window;

implementation

	uses 
		Engineering_Window_Classic,
		Engineering_Window_Carbon;

	procedure D_Engineering_Window;
		begin
			{$ifc not undefined THINK_Pascal}
			InitializeEngrWindowClassic;
			{$elsec}
			InitializeEngrWindowCarbon;
			{$endc}
		end;

end.