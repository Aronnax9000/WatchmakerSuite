unit unit_main_event_handler;


interface

uses
{$IFC UNDEFINED THINK_Pascal}
  Events,
{$ENDC}
  unit_preferences;
function handleEvent(prefs: ArthromorphPreferencesHandle;
  myEvent: EventRecord): BOOLEAN;

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
Types, Quickdraw, Windows, Dialogs, Menus, ToolUtils,
  DiskInit,
{$ENDC}
unit_breeding_window, unit_handle_the_menus,
  unit_genome_window, unit_about_arthromorphs,
unit_handle_mouse_down, unit_watchdocrec;



procedure handleActivateEvt(whichWindow: WIndowPtr; myEvent: EventRecord);
begin   		{Handle the activation}
	whichWindow := WindowPtr(myevent.message);{Get the window to be activated}
	if odd(myEvent.modifiers) then{Make sure it is Activate and not DeActivate}
		begin{Handle the activate}
			SelectWindow(whichWindow);{Activate the window by selecting it}

			case (GetWResourceRefCon(whichWindow)) of{Do the appropiate window}
        1: ActivateGenomeWindow(whichWindow, myEvent);
        2:
					DrawGrowIcon(whichWindow);{Draw the Grow box}
				otherwise{Handle others }
			end;{End of the case}
		end;{End of Activate}
end; {End of ActivateEvt}

procedure handleWindowOpenEvent(windowId: INTEGER; prefs: ArthromorphPreferencesHandle);
begin
    case windowId of
      1:Open_Genome_Window(prefs);
      2:Open_Breeding_Window(prefs);
      3:Open_About_Arthromorphs(prefs);
    end;
end;

procedure handleWindowCloseEvent(windowId: INTEGER; prefs: ArthromorphPreferencesHandle);
begin
    case windowId of
      1:Close_Genome_Window(prefs);
      2:Close_Breeding_Window(prefs);
      3:Close_About_Arthromorphs(prefs);
    end;
end;

procedure handleApplEvt(myEvent: EventRecord; prefs: ArthromorphPreferencesHandle);
begin
  if HiWord(myEvent.message) = 1 then
    handleWindowOpenEvent(LoWord(myEvent.Message), prefs)
  else if HiWord(myEvent.message) = 2 then
    handleWindowCloseEvent(LoWord(myEvent.Message), prefs)
end;

procedure handleDiskEvt(myEvent: EventRecord);
var
  chCode: integer; {Key code}

begin {Handle a disk event}
	if (HiWord(myevent.message) <> noErr) then{See if a diskette mount error}
		begin   	{due to unformatted diskette inserted}
			myEvent.where.h := ((screenbits.bounds.Right - screenbits.bounds.Left) div 2) - (304 div 2);{Center horz}
			myEvent.where.v := ((screenbits.bounds.Bottom - screenbits.bounds.Top) div 3) - (104 div 2);{Top 3ed vertically}
			InitCursor;{Make sure it has an arrow cursor}
			chCode := DIBadMount(myEvent.where, myevent.message);{Let the OS handle the diskette}
		end;
end; {handleDiskEvt}

procedure handleUpDateEvt(prefs: ArthromorphPreferencesHandle; myEvent: EventRecord);
var whichWindow: WIndowPtr;
		begin {Handle the update}
			whichWindow := WindowPtr(myEvent.message);{Get the window the update is for}
			BeginUpdate(whichWindow);{Set the clipping to the update area}
			case (GetWResourceRefCon(whichWindow)) of{Do the appropiate window}
				1:
					Update_Genome_Window(prefs);{Update this window}
				2:
					Update_Breeding_Window(prefs);{Update this window}
				3:
					Update_About_Arthromorphs(prefs);{Update this window}
				otherwise   	{Handle others dialogs}
					begin   		{Others}
					end;    		{End of the else}
			end;    			{End of the case}
			EndUpdate(whichWindow);{Return to normal clipping area}
		end;    		{End of UpDateEvt}


function handleKeyDownAutoKey(prefs: ArthromorphPreferencesHandle; myEvent: EventRecord): boolean;
var
  chCode: integer;   			{Key code}
	ch: char;  					{Key pressed in Ascii}
	mResult: longint;  			{Menu list and item selected values}
	theMenu, theItem: integer;{Menu list and item selected}
  eventDoneFlag: boolean;
		begin   		{Get the key and handle it}
      eventDoneFlag := false;
			with myevent do{Check for menu command keys}
				begin   	{}
					chCode := BitAnd(message, CharCodeMask);{Get character}
					ch := CHR(chCode);{Change to ASCII}
					if (Odd(modifiers div CmdKey)) then{See if Command key is down}
						begin{}
							mResult := MenuKey(ch);{See if menu selection}
							theMenu := HiWord(mResult);{Get the menu list number}
							theItem := LoWord(mResult);{Get the menu item number}
							if (theMenu <> 0) then{See if a list was selected}
                {Do the menu selection}
								eventDoneFlag := Handle_My_Menu(prefs, theMenu, theItem);
						end
				end;
      handleKeyDownAutoKey := eventDoneFlag;
		end; {End for KeyDown,AutoKey}

function handleWindowEvent(prefs: ArthromorphPreferencesHandle;
  myEvent: EventRecord;
  whichWindow: WindowPtr;
  findWindowCode: INTEGER): BOOLEAN;
var eventDoneFlag: BOOLEAN;
begin
  eventDoneFlag := FALSE;
	case myEvent.what of{Decide type of event}
		UpDateEvt:{Update event for a window}
      handleUpDateEvt(prefs, myEvent);
    MouseDown:{Mouse button pressed}
      eventDoneFlag := handleMouseDown(prefs, whichWindow, myEvent, findWindowCode);
		KeyDown, AutoKey:{Handle key inputs}
      eventDoneFlag := handleKeyDownAutoKey(prefs, myEvent);
		DiskEvt:   	{Disk inserted event}
      handleDiskEvt(myEvent);
		app1Evt:    	{Check for events generated by this program}
      handleApplEvt(myEvent, prefs);
		ActivateEvt:{Window activated event}
      handleActivateEvt(whichWindow, myEvent);
		otherwise
	end;    			{End of case}
  handleWindowEvent := eventDoneFlag;
end;


function handleEvent(prefs: ArthromorphPreferencesHandle; myEvent: EventRecord): BOOLEAN;
var
  eventDoneFlag: boolean;
  whichWindow: WindowPtr;    	{See which window for event}
  findWindowCode: integer; 				{Determine event type}
	Is_A_Dialog: boolean;    		{Flag for modeless dialogs}

begin   					{Start handling the event}
  eventDoneFlag := false;
  {Get which window the event happened in}
	findWindowCode := FindWindow(myEvent.where, whichWindow);

	Is_A_Dialog := IsDialogEvent(myEvent);{See if a modeless dialog event}
	if Is_A_Dialog then{Handle a dialog event}
		begin
      {Handle the update of a Modeless Dialog}
			if (myEvent.what = UpDateEvt) then
				begin
          {Get the window the update is for}
					whichWindow := WindowPtr(myEvent.message);
					BeginUpdate(whichWindow);{Set update clipping area}
					EndUpdate(whichWindow);{Return to normal clipping area}
				end
		end {End of Is_A_Dialog}
	else {else handle a window}
      eventDoneFlag := eventDoneFlag or handleWindowEvent(prefs, myEvent, whichWindow, findWindowCode);
	{End for not a modeless dialog event}
  handleEvent := eventDoneFlag;
end;						{end of GetNextEvent}


end.

