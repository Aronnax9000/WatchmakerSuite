unit HandleTheMenus;

{File name :  HandleTheMenus.Pas  }
{Function: Handle all menu selections.}
{          This procedure is called when a menu item is selected.}
{          There is one CASE statement for all Lists.  There is}
{          another CASE for all the commands in each List.}
{History: 12/12/90 Original by Prototyper.   }
{                       }

interface

	uses
		{$ifc not undefined THINK_PASCAL}
		ThinkPascalUPIBridge,
		{$elsec}
		MacOSAll, 
		{$endc}
		MyGlobals, Ted, Richard, Error_Alert, Preferences, Engineering_Window, Genome_Window, Breeding_Window, About_Arthromorphs,
		MenuConstants;
		{$ifc not undefined THINK_Pascal}
	procedure Handle_My_Menu (var doneFlag: boolean; theMenu, theItem: integer; var theInput: TEHandle);{Handle menu selection}
{$endc}
implementation
		{$ifc not undefined THINK_Pascal}

	procedure Handle_My_Menu;   		{Handle menu selections realtime}
		var
			DNA: integer;  			{For opening DAs}
			BoolHolder: boolean;   	{For SystemEdit result}
			DAName: Str255;    		{For getting DA name}
			SavePort: GrafPtr; 		{Save current port when opening DAs}
			sTemp: Str255;

	begin   							{Start of procedure}

		case theMenu of 			{Do selected menu list}

			kArthMenuApple: 
				begin
					case theItem of{Handle all commands in this menu list}
						kArthMenuAppleAbout: 
							begin
								Open_About_Arthromorphs;{Open a window for this menu selection}
							end;
						otherwise   	{Handle the DAs}
							begin   		{Start of Otherwise}
								{$ifc not undefined THINK_PASCAL}
								GetPort(SavePort);{Save the current port}
								GetMenuItemText(AppleMenu, theItem, DAName);{Get the name of the DA selected}
								DNA := OpenDeskAcc(DAName);{Open the DA selected}
								SetPort(SavePort);{Restore to the saved port}
								{$elsec}
								NumToString(theItem, sTemp);
								TellError(sTemp);
								{$endc}
							end;    		{End of Otherwise}

					end;    			{End of item case}
				end;    				{End for this list}

			kArthMenuFile: 
				begin
					case theItem of{Handle all commands in this menu list}
						kArthMenuFileNew: 
							begin
								NewMinimal;
								Open_Breeding_Window;{Open a window for this menu selection}
							end;
						kArthMenuFileOpen: 
							begin
								Open_Breeding_Window;{Open a window for this menu selection}
								LoadArthromorph;
							{Call the SFGetFile OS routine}
							end;
						kArthMenuFileClose: 
							begin
							{Call the SFPutFile OS routine}
							end;
						kArthMenuFileSave: 
							begin
								SaveArthromorph;
							{Call the SFPutFile OS routine}
							end;
						kArthMenuFileSaveAs: 
							begin
								SaveArthromorph;
							{Call the SFPutFile OS routine}
							end;
						kArthMenuFileQuit: 
							begin
								doneFlag := TRUE;
							end;
						otherwise
							begin   		{Start of the Otherwise}
							end;    		{End of Otherwise}

					end;    			{End of item case}
				end;    				{End for this list}

			kArthMenuEdit: 
				begin
					{$ifc not undefined THINK_PASCAL}
					BoolHolder := SystemEdit(theItem - 1);{Let the DA do the edit to itself}
					{$elsec}
					BoolHolder := false;
					{$endc}
					if not (BoolHolder) then{If not a DA then we get it}
						begin   			{Handle by using a Case statment}
							case theItem of{Handle all commands in this menu list}
								kArthMenuEditUndo: 
									begin
										A_Error_Alert;{Call a alert for this menu selection}
									end;
								kArthMenuEditCut: 
									begin
								{?? ADD IN HERE WHAT THIS COMMAND SHOULD DO}
									end;
								kArthMenuEditCopy: 
									begin
								{?? ADD IN HERE WHAT THIS COMMAND SHOULD DO}
									end;
								kArthMenuEditPaste: 
									begin
								{?? ADD IN HERE WHAT THIS COMMAND SHOULD DO}
									end;
								kArthMenuEditClear: 
									begin
								{?? ADD IN HERE WHAT THIS COMMAND SHOULD DO}
									end;
								kArthMenuEditSelectAll: 
									begin
								{?? ADD IN HERE WHAT THIS COMMAND SHOULD DO}
									end;
								kArthMenuEditShowClipboard: 
									begin
								{?? ADD IN HERE WHAT THIS COMMAND SHOULD DO}
									end;
								otherwise{Send to a DA}
									begin   	{Start of the Otherwise}
									end;    	{End of Otherwise}

							end;    			{End of not BoolHolder}
						end;    			{End of item case}
				end;    				{End for this list}

			kArthMenuOperation: 
				begin
					case theItem of{Handle all commands in this menu list}
						kArthMenuOperationBreed: 
							begin
								Open_Breeding_Window;{Open a window for this menu selection}
								Breed;
							end;
						kArthMenuOperationShowAsText: 
							begin
								Open_Genome_Window;
								{PrintMiddle;}
								{Close_Breeding_Window(WindowPtr(ord4(-1)));}
							end;
						kArthMenuOperationEngineer: 
							begin
								D_Engineering_Window;
								{Close_Genome_Window(WindowPtr(ord4(-1)));}{Close a window for this menu selection}
							end;
						otherwise
							begin   		{Start of the Otherwise}
							end;    		{End of Otherwise}

					end;    			{End of item case}
				end;    				{End for this list}

			kArthMenuView: 
				begin
					case theItem of{Handle all commands in this menu list}
						kArthMenuViewPreferences: 
							begin
								Close_Breeding_Window(WindowPtr(ord4(-1)));
								D_Preferences;{Call a dialog for this menu selection}
							end;
						otherwise
							begin   		{Start of the Otherwise}
							end;    		{End of Otherwise}

					end;    			{End of item case}
				end;    				{End for this list}

			otherwise
				begin   				{Start of the Otherwise}
				end;    				{End of Otherwise}

		end;    					{End for the Lists}

		HiliteMenu(0);  			{Turn menu selection off}
	end;    							{End of procedure Handle_My_Menu}
{$endc}
end.    								{End of unit}