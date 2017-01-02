unit CarbonEventHandlers;
interface
uses MacOSAll, MenuConstants, About_Arthromorphs, Breeding_Window, Ted, Genome_Window, Engineering_Window, Preferences, Serialization, Drawing;
function CommandHandler(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSStatus;MWPascal;
implementation

function CommandHandler(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
var 
	status: OSStatus;
	theCommand: HICommand;
begin
	status := NoErr;
	GetEventParameter(theEvent, kEventParamDirectObject, typeHICommand, nil, sizeof(HICommand), nil, @theCommand);
	case theCommand.commandID of
		kArthAppleAboutCommand: Open_About_Arthromorphs; 
		kArthFileNewCommand: begin Open_Breeding_Window; NewMinimal; InvalGenomeWindow end; 
		kArthFileOpenCommand: begin LoadArthromorph; InvalGenomeWindow; end;
		kArthFileSaveCommand: begin SaveArthromorph; end;
		kArthFileSaveAsCommand: begin SaveArthromorph; end;
		kArthOperationBreedCommand: Open_Breeding_Window;
		kArthOperationShowAsTextCommand: Open_Genome_Window;
		kArthOperationEngineerCommand: D_Engineering_Window;
		kArthViewPreferencesCommand: D_Preferences;
		{kArthMenuFileQuitCommand: begin end;}
		kHICommandQuit: status := eventNotHandledErr;
	end;
		CommandHandler := status;
end;
end.
