unit  InitTheMenus;
 
{File name:  InitTheMenus.Pas}
{Function: Pull in menu lists from a resource file.}
{          This procedure is called once at program start.}
{          AppleMenu is the handle to the Apple menu, it is also}
{          used in the procedure that handles menu events.}
{History: 12/12/90 Original by Prototyper.   }
{                       }
interface
	{$ifc not undefined THINK_PASCAL}
	uses ThinkPascalUPIBridge;
	{$elsec}
		uses MacOSAll, CarbonEventHandlers, MenuConstants;

	{$endc}
	
{$ifc not undefined THINK_PASCAL}	
	var
		AppleMenu:MenuHandle;   		{Menu handle}
		M_File:MenuHandle;  			{Menu handle}
		M_Edit:MenuHandle;  			{Menu handle}
		M_Operation:MenuHandle; 		{Menu handle}
		M_View:MenuHandle;  			{Menu handle}
	{$elsec}
	var
		AppleMenu:MenuRef;   		{Menu handle}
		M_File:MenuRef;  			{Menu handle}
		M_Edit:MenuRef;  			{Menu handle}
		M_Operation:MenuRef; 		{Menu handle}
		M_View:MenuRef;  			{Menu handle}
	{$endc}
	procedure Init_My_Menus;    		{Initialize the menus}
 
 
implementation
 
	procedure Init_My_Menus;    		{Initialize the menus}
begin   								{Start of Init_My_Menus}
		ClearMenuBar;   				{Clear any old menu bars}
 
		{ This menu is the APPLE menu, used for About and desk accessories.}
		AppleMenu := GetMenu(kArthMenuApple);{Get the menu from the resource file}
		InsertMenu (AppleMenu,0);   	{Insert this menu into the menu bar}
		{$ifc not undefined THINK_PASCAL}
		AppendResMenu(AppleMenu,'DRVR');{Add in DAs}
		M_File := GetMenu(kArthMenuFile);   	{Get the menu from the resource file}
		{$elsec}
		SetMenuItemCommandID(AppleMenu, kArthMenuAppleAbout, kArthAppleAboutCommand);
		M_File := GetMenu(kArthMenuFileCarbon);   	{Get the menu from the resource file}
		{$endc}
		SetMenuItemCommandID(M_File, kArthMenuFileNew, kArthFileNewCommand);
		SetMenuItemCommandID(M_File, kArthMenuFileOpen, kArthFileOpenCommand);
		SetMenuItemCommandID(M_File, kArthMenuFileSave, kArthFileSaveCommand);
		SetMenuItemCommandID(M_File, kArthMenuFileSaveAs, kArthFileSaveAsCommand);
		SetMenuItemCommandID(M_File, kArthMenuFileClose, kArthFileCloseCommand);
		SetMenuItemCommandID(M_File, kArthMenuFileQuit, kArthFileQuitCommand);
		InsertMenu (M_File,0);  		{Insert this menu into the menu bar}
 (*
		M_Edit := GetMenu(kArthMenuEdit);   	{Get the menu from the resource file}
		SetMenuItemCommandID(M_Edit, kArthMenuEditUndo, kArthEditUndoCommand);
		SetMenuItemCommandID(M_Edit, kArthMenuEditCut, kArthEditCutCommand);
		SetMenuItemCommandID(M_Edit, kArthMenuEditCopy, kArthEditCopyCommand);
		SetMenuItemCommandID(M_Edit, kArthMenuEditPaste, kArthEditPasteCommand);
		SetMenuItemCommandID(M_Edit, kArthMenuEditClear, kArthEditClearCommand);
		SetMenuItemCommandID(M_Edit, kArthMenuEditSelectAll, kArthEditSelectAllCommand);
		SetMenuItemCommandID(M_Edit, kArthMenuEditShowClipboard, kArthEditShowClipboardCommand);
		InsertMenu (M_Edit,0);  		{Insert this menu into the menu bar}
 *)		
		M_Operation := GetMenu(kArthMenuOperation);{Get the menu from the resource file}
		SetMenuItemCommandID(M_Operation, kArthMenuOperationBreed, kArthOperationBreedCommand);
		SetMenuItemCommandID(M_Operation, kArthMenuOperationShowAsText, kArthOperationShowAsTextCommand);
		SetMenuItemCommandID(M_Operation, kArthMenuOperationEngineer, kArthOperationEngineerCommand);
		InsertMenu (M_Operation,0);{Insert this menu into the menu bar}
 
		M_View := GetMenu(kArthMenuView);   	{Get the menu from the resource file}
		SetMenuItemCommandID(M_View, kArthMenuViewPreferences, kArthViewPreferencesCommand);
		InsertMenu (M_View,0);  		{Insert this menu into the menu bar}
 
		DrawMenuBar;    				{Draw the menu bar}
 
end;    								{End of procedure Init_My_Menus}

end.    								{End of this unit}
