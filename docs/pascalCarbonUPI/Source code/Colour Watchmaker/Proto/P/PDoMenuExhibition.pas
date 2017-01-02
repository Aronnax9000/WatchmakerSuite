unit  PDoMenuExhibition;   												{Handle a selection of the menu bar}

{Unit name:  PDoMenuExhibition.p  }
{Function:  This module calls the appropiate menu list}
{     handler routine.}
{ History: 20/6/91 Original by Prototyper 3.0   }

interface

uses
	PCommonExhibition, Common_Exhibition,{Common and types}
	PUtils_Exhibition, Utils_Exhibition, {General Utilities}
	{AppleMenu,}
	FileMenu,
	Edit3Menu,
	OperationMenu,  {Menu handlers}
	PA_Warning_Alert,{Alerts}
	PD_About_Box,PD_Timing_Dialogue,{Modal Dialogs}
	PW_BreedWindow,{Windows}
	DoMenuExhibition, {Extra menu handler}
	MacOSAll;

	{Handle the menu selection}
	procedure Handle_My_Menu( theMenu,theItem:integer);

implementation


{=======================================================}


	{Routine: DoMenuApple}
	{Purpose: Handle all menu items in this list}

		procedure DoMenuApple(theItem:integer);    					{Handle this menu selection}
		var
			SkipProcessing : boolean;     									{TRUE says skip processing}
			DNA : integer;  													{For opening DAs}
			DAName : Str255;     											{For getting DA name}
			SavePort : GrafPtr;   											{Save current port when opening DAs}

		begin     																{Start of procedure}

			SkipProcessing := FALSE;     									{Set to not skip the processing of this menu item}

			{Do_AppleMenu(TRUE,theItem,SkipProcessing);}   			{Pre menu handler}

			if not (SkipProcessing) then  									{See if process the menu list}
				begin

				case theItem of 												{Handle all commands in this menu list}

					kCoulMenuAppleAbout:
						begin
				M_PD_About_Box; 											{Call a modal dialog}
						end;
					{$ifc not undefined THINK_Pascal}
					otherwise     												{Handle the DAs}
						begin
							GetPort(SavePort);   								{Save the current port}
							GetMenuItem(Menu_Apple, theItem, DAName);{Get the name of the DA selected}
							DNA := OpenDeskAcc(DAName);     				{Open the DA selected}
							SetPort(SavePort);   								{Restore to the saved port}
						end;
					{$endc}

				end;   															{End of item case}

				end;   															{End of not SkipProcessing}

			{Do_AppleMenu(FALSE,theItem,SkipProcessing);}  			{Post menu handler}

	end;     																	{End of procedure}

{=======================================================}


	{Routine: DoMenuFile}
	{Purpose: Handle all menu items in this list}

		procedure DoMenuFile(theItem:integer);  						{Handle this menu selection}
		var
			SkipProcessing : boolean;     									{TRUE says skip processing}

		begin     																{Start of procedure}

			SkipProcessing := FALSE;     									{Set to not skip the processing of this menu item}

			Do_FileMenu(TRUE,theItem,SkipProcessing); 				{Pre menu handler}

			if not (SkipProcessing) then  									{See if process the menu list}
				begin

				case theItem of 												{Handle all commands in this menu list}

					kCoulMenuFileTiming:
						begin
				M_PD_Timing_Dialogue;   									{Call a modal dialog}
						end;

					kCoulMenuFileQuit:
						begin
							doneFlag := TRUE;
						end;

					otherwise
						begin
						end;

				end;   															{End of item case}

				end;   															{End of not SkipProcessing}

			Do_FileMenu(FALSE,theItem,SkipProcessing);     			{Post menu handler}

	end;     																	{End of procedure}

{=======================================================}


	{Routine: DoMenuEdit3}
	{Purpose: Handle all menu items in this list}

		procedure DoMenuEdit3(theItem:integer);     					{Handle this menu selection}
		var
			SkipProcessing : boolean;     									{TRUE says skip processing}
			BoolHolder : boolean;  											{For SystemEdit result}

		begin     																{Start of procedure}

			SkipProcessing := FALSE;     									{Set to not skip the processing of this menu item}

			Do_Edit3Menu(TRUE,theItem,SkipProcessing);    			{Pre menu handler}

			if not (SkipProcessing) then  									{See if process the menu list}
				begin
				{$ifc not undefined THINK_Pascal}
				BoolHolder := SystemEdit ( theItem - 1 );  				{Let the DA do the edit to itself}
				{$elsec}
				BoolHolder := false;
				{$endc}

				if not (BoolHolder) then    									{If not a DA then we get it}
					begin   														{Handle by using a Case statment}

					case theItem of  											{Handle all commands in this menu list}

						kCoulMenuEditCopy:
							begin
							end;

						kCoulMenuEditPaste:
							begin
							end;

						otherwise 												{Send to a DA}
							begin
							end;

					end;    														{End of item case}
					end;    														{End of not BoolHolder}

				end;   															{End of not SkipProcessing}

			Do_Edit3Menu(FALSE,theItem,SkipProcessing);  			{Post menu handler}

	end;     																	{End of procedure}

{=======================================================}


	{Routine: DoMenuOperation}
	{Purpose: Handle all menu items in this list}

		procedure DoMenuOperation(theItem:integer);   				{Handle this menu selection}
		var
			SkipProcessing : boolean;     									{TRUE says skip processing}

		begin     																{Start of procedure}

			SkipProcessing := FALSE;     									{Set to not skip the processing of this menu item}

			Do_OperationMenu(TRUE,theItem,SkipProcessing);  		{Pre menu handler}

			if not (SkipProcessing) then  									{See if process the menu list}
				begin

				case theItem of 												{Handle all commands in this menu list}

					kCoulMenuOperBreed:
						begin
						end;

					kCoulMenuOperNewRandom:
						begin
						end;

					otherwise
						begin
						end;

				end;   															{End of item case}

				end;   															{End of not SkipProcessing}

			Do_OperationMenu(FALSE,theItem,SkipProcessing); 		{Post menu handler}

	end;     																	{End of procedure}

{=======================================================}

	{Routine: Handle_My_Menu}
	{Purpose: Vector off to the appropiate menu list handler}

		procedure Handle_My_Menu( theMenu,theItem:integer);

		begin

			case theMenu of    												{Do selected menu list}

				kCoulMenuApple:
						DoMenuApple(theItem); 								{Go handle this menu list}

				kCoulMenuFile:
						DoMenuFile(theItem);    								{Go handle this menu list}

				kCoulMenuEdit:
						DoMenuEdit3(theItem);  								{Go handle this menu list}

				kCoulMenuOper:
						DoMenuOperation(theItem);     						{Go handle this menu list}

				otherwise
					begin
						Handle_Other_Menus(theMenu,theItem); 			{Handle other special menus}
					end;

			end;  																{End for the Lists}

			HiliteMenu(0);  													{Turn menu selection off}
		end; 																	{End of procedure Handle_My_Menu}

end.    																		{End of unit}

