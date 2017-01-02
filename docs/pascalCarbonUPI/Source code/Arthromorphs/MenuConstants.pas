unit MenuConstants;
interface
	const
		kArthMenuApple = 1001;   				{Menu resource ID}
		kArthMenuFile = 1002;   				{Menu resource ID}
		kArthMenuEdit = 1003;   				{Menu resource ID}
		kArthMenuOperation = 1004;   				{Menu resource ID}
		kArthMenuView = 1005;   				{Menu resource ID}
		kArthMenuFileCarbon = 1006; {Carbon-based File menu (without Quit)}

		kArthMenuAppleAbout = 1;
		kArthMenuFileNew = 1;
		kArthMenuFileOpen = 2;
		kArthMenuFileClose = 4;
		kArthMenuFileSave = 5;
		kArthMenuFileSaveAs = 6;
		kArthMenuFileQuit = 8;
		kArthMenuEditUndo = 1;
		kArthMenuEditCut = 3;
		kArthMenuEditCopy = 4;
		kArthMenuEditPaste = 5;
		kArthMenuEditClear = 6;
		kArthMenuEditSelectAll = 7;
		kArthMenuEditShowClipboard = 9;
		kArthMenuOperationBreed = 1;
		kArthMenuOperationShowAsText = 2;
		kArthMenuOperationEngineer = 3;
		kArthMenuViewPreferences = 1;

		kArthAppleAboutCommand = UInt32('abot');
		kArthFileNewCommand = UInt32('new ');
		kArthFileOpenCommand = UInt32('open');
		kArthFileCloseCommand = UInt32('clos');
		kArthFileSaveCommand = UInt32('save');
		kArthFileSaveAsCommand = UInt32('sava');
		kArthFileQuitCommand = UInt32('quit');
		kArthEditUndoCommand = UInt32('undo');
		kArthEditCutCommand = UInt32('cut ');
		kArthEditCopyCommand = UInt32('copy');
		kArthEditPasteCommand = UInt32('past');
		kArthEditClearCommand = UInt32('cler');
		kArthEditSelectAllCommand = UInt32('sela');
		kArthEditShowClipboardCommand = UInt32('shcl');
		kArthOperationBreedCommand = UInt32('bred');
		kArthOperationShowAsTextCommand = UInt32('geno');
		kArthOperationEngineerCommand = UInt32('opeg');
		kArthViewPreferencesCommand = UInt32('vupr');
	
implementation
end.