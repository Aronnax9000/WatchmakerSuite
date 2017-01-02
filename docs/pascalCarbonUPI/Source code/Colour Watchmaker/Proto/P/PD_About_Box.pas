unit PD_About_Box;

{File name: About_Box}
{Function: Handle a modal dialog}
{ History: 20/6/91 Original by Prototyper 3.0   }

interface

uses
	PCommonExhibition, Common_Exhibition, {Common and types}
	PUtils_Exhibition, Utils_Exhibition ,{General Utilities}
	PA_Warning_Alert,{Alerts}
	MacOSAll;

	{Handle the modal dialog}
	procedure M_PD_About_Box;


{=======================================================}

implementation

var 
		ExitDialog : boolean;  												{Flag used to exit the Dialog}
		MyPt : Point;  														{Current list selection point}
		MyErr : OSErr;    													{OS error returned}
		AboutWindow : WindowRef;     										{Pointer to this dialog}
		SavedPort : GrafPtr;     											{Previous GrafPtr}


{=======================================================}
function HandleWindowAboutCloseEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var
		status: OSStatus;
	begin
		status := noErr;
		QuitAppModalLoopForWindow(AboutWindow);
		HandleWindowAboutCloseEvent := status;
	end;

																{End of procedure}


{=======================================================}

		function MyFilter (theDialog : DialogPtr;
		                   var theEvent : EventRecord;
		                   var itemHit : integer) : boolean;
		var
			MyPt : Point;   													{Current list selection point}
			tempRect : Rect;   												{Temporary rectangle}
			DType : Integer;    												{Type of dialog item}
			DItem : Handle;     												{Handle to the dialog item}
			CItem : controlhandle;     										{Control handle}
			chCode : Integer;   												{Key entered}
			LTemp : longint;    												{Used for time delay}
			MyCmdKey : char;     											{The command key}
			CmdDown : boolean;   											{Flag for command key used}

		begin
			MyFilter := FALSE;

			{MyFilter:= D_Filter_About_Box(theDialog, theEvent, itemHit);}{Call the user routine}

			if (theEvent.what = updateEvt)  and (WindowPtr(theEvent.message) = theDialog) then{Only do on an update}
				begin
				BeginUpdate(theDialog);    									{Start the update}
				DrawDialog(theDialog);     									{Draw the controls}
				MyFilter := TRUE;  											{Pass out this special itemHit number}
				itemHit := 32000;  											{Our special ID}
				end;
			if (theEvent.what = MouseDown) then  						{Only do on a mouse click}
				begin
				MyPt := theEvent.where; 									{Get the point where the mouse was clicked}
				GlobalToLocal(MyPt);  										{Convert global to local}


				end;
			if (theEvent.what = KeyDown) then
				begin
				with theEvent do
					begin
					chCode := BitAnd (message, CharCodeMask);   		{Get character}
					MyCmdKey := CHR(chCode);	    						{Change to ASCII}
					CmdDown := Odd(modifiers div CmdKey);   			{Get command key state}
					if CmdDown then     										{Do if command key was down}
						begin
						{$ifc not undefined THINK_Pascal}
						{FIXME}
						if ((MyCmdKey = 'x') or (MyCmdKey = 'X')) then
							begin
							DlgCut(theDialog);
							MyFilter := TRUE;
							end
						else if ((MyCmdKey ='c') or (MyCmdKey = 'C')) then
							begin
							DlgCopy(theDialog);
							MyFilter := TRUE;
							end
						else if ((MyCmdKey ='v') or (MyCmdKey = 'V')) then
							begin
							DlgPaste(theDialog);
							MyFilter := TRUE;
							end;
						{$endc}
						end
					else if (chCode = 13) or (chCode = $03) then   		{CR or Enter}
						begin
						MyFilter := TRUE;    									{Flag we got a hit}
						itemHit := 1;  											{Default for CR}
						GetDialogItem (theDialog ,itemHit, DType, DItem, tempRect);{Get the item}
						if (DType = (ctrlItem + btnCtrl)) then     			{If a button then ...}
							begin
							CItem := Pointer (DItem);    						{Make it a controlhandle}
							HiliteControl(CItem, 10);    						{Hilite it}
							LTemp := TickCount + 15;   						{Flash the button for 1/4 second}
							repeat
							until (Ltemp < TickCount);
							HiliteControl(CItem, 0); 							{UnHilite it}
							end;
						end;
					end;
				end;

		end;

{=======================================================}


		procedure M_PD_About_Box;
			var
				tempRect : Rect;    											{Temporary rectangle}
				DType : Integer;     											{Type of dialog item}
				DItem : Handle; 												{Handle to the dialog item}
				CItem: controlhandle;  						{Control handle}
				itemHit : Integer;   											{Get selection}
		{$ifc not undefined THINK_Pascal}
		{$elsec}
		status: OSStatus;
		theNib: IBNibRef;
		{$endc}


{=======================================================}

		{This is an update routine for non-controls in the dialog} 
		{This is executed after the dialog is uncovered by an alert} 
		procedure Refresh_Dialog;  										{Refresh the dialogs non-controls}
			var 
				rTempRect:Rect;    											{Temp rectangle used for drawing}

			begin 
				SetPortWindowPort(AboutWindow);     									{Point to our dialog window}
				GetDialogItem(AboutWindow,Res_Dlg_OK2,DType,DItem,rTempRect);{Get the item handle}
				PenSize(3, 3);  												{Change pen to draw thick default outline}
				InsetRect(rTempRect, -4, -4);     							{Draw outside the button by 1 pixel}
				FrameRoundRect(rTempRect, 16, 16);    					{Draw the outline}
				PenSize(1, 1);  												{Restore the pen size to the default value}


				{D_Refresh_About_Box(AboutWindow);}   					{Call user refresh routine}

		end; 

{=======================================================}


			begin 																{Start of dialog handler}
				GetPort(SavedPort);   										{Get the previous GrafPtr}

				
				{$if not undefined THINK_Pascal}
				AboutWindow := GetNewDialog(Res_D_About_Box, nil,  Pointer(-1) );{Bring in the dialog resource}
				{$elsec}
				status := CreateNibReference(CFSTR('Exhibition'), theNib);
				if status <> NoErr then exit;
				CreateWindowFromNib(theNib, CFSTR('About Box'), AboutWindow);
				DisposeNibReference(theNib);
			
				InstallStandardEventHandler(GetWindowEventTarget(AboutWindow));
				InstallEventHandler(GetWindowEventTarget(AboutWindow), 
					HandleWindowAboutCloseEvent, 1, 
					@windowEventCloseType, nil, nil);
			
				{$endc}

				ShowWindow(AboutWindow);  								{Open a dialog box}
				SelectWindow(AboutWindow); 								{Lets see it}
				SetPortWindowPort(AboutWindow);     									{Prepare to add conditional text}
				{$ifc not undefined THINK_Pascal}
				{Setup initial conditions}
				Refresh_Dialog;     											{Draw any Lists, lines, or rectangles}
				ExitDialog:=FALSE;     										{Do not exit dialog handle loop yet}
				repeat     														{Start of dialog handle loop}
					ModalDialog(@MyFilter, itemHit);     					{Wait until an item is hit}
					if (itemHit = 32000) then  								{Check for update}
						begin
						Refresh_Dialog;  										{Draw any Lists, lines, or rectangles}
						EndUpdate(AboutWindow);    							{End of the update}
						end
					else
						begin
						GetDialogItem(AboutWindow, itemHit, DType, DItem, tempRect);{Get item information}
						CItem := Pointer(DItem);    							{Get the control handle}
						end;

					D_Hit_About_Box(AboutWindow,itemHit,ExitDialog);{Let user handle the item hit}

					{Handle it real time}
					if (ItemHit =Res_Dlg_OK2) then   						{Handle the Button being pressed}
						begin
					ExitDialog := TRUE; 										{Close a modal dialog}
						end;


				until ExitDialog;     											{Handle dialog items until exit selected}
				DisposeDialog(AboutWindow);   								{Flush the dialog out of memory}
				{$elsec}
				RunAppModalLoopForWindow(AboutWindow);
				DisposeWindow(AboutWindow);
				{$endc}
				SetPort(SavedPort);   										{Restore the previous GrafPtr}
			end;  																{End of procedure}
end.    																		{End of unit}
