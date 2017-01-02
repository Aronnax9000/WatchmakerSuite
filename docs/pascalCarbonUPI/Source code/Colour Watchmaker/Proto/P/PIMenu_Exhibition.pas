unit  PIMenu_Exhibition;    												{Initialize the menu bar}

{Unit name:  PIMenu_Exhibition.p  }
{Function:  This module loads in the menu lists and places them}
{     into the menubar.}
{ History: 20/6/91 Original by Prototyper 3.0   }

interface

uses
	PCommonExhibition, Common_Exhibition, {Common}
	MacOSAll; {Extra menu init}
	{Initialize the menubar}
	procedure Init_My_Menus;

implementation

	procedure InitializeCommands;
	begin
		SetMenuItemCommandID(Menu_Apple,  kCoulMenuAppleAbout, kCoulCmdAppleAbout);
		SetMenuItemCommandID(Menu_File,  kCoulMenuFileTiming, kCoulCmdFileTiming);
		SetMenuItemCommandID(Menu_Operation, kCoulMenuOperBreed, kCoulCmdOperBreed);
		SetMenuItemCommandID(Menu_Operation, kCoulMenuOperNewRandom, kCoulCmdOperNewRandom);
	end;

	{Routine: Init_My_Menus}
	{Purpose: Load in the menu lists and initialize the menubar}

		procedure Init_My_Menus;

		begin
			ClearMenuBar; 													{Clear any old menu bars}

			{ This menu is the APPLE menu, used for About and desk accessories.}
			Menu_Apple := GetMenu(kCoulMenuApple);   				{Get the menu from the resource file}
			InsertMenu (Menu_Apple,0);     								{Insert this menu into the menu bar}
			{$ifc not undefined THINK_Pascal}
			AddResMenu(Menu_Apple,'DRVR'); 							{Add in DAs}
			{$endc}

			Menu_File := GetMenu(kCoulMenuFile);    					{Get the menu from the resource file}
			InsertMenu (Menu_File,0);   									{Insert this menu into the menu bar}

			Menu_Edit3 := GetMenu(kCoulMenuEdit);    				{Get the menu from the resource file}
			InsertMenu (Menu_Edit3,0); 									{Insert this menu into the menu bar}

			Menu_Operation := GetMenu(kCoulMenuOper); 		{Get the menu from the resource file}
			InsertMenu (Menu_Operation,0);    							{Insert this menu into the menu bar}

			{$ifc undefined THINK_Pascal}
			DeleteMenuItem(Menu_File, kCoulMenuFileQuit);
			InitializeCommands;
			{$endc}

			DrawMenuBar; 													{Draw the menu bar}

	end;     																	{End of procedure Init_My_Menus}

	end.     																	{End of this unit}
