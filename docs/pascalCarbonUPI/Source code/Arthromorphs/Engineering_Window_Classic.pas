unit Engineering_Window_Classic;

interface
	uses {$ifc undefined THINK_Pascal}
		MacOSAll,
		{$endc}
		MyGlobals, Ted, Engineering_Window_Constants;
	
	procedure InitializeEngrWindowClassic;
implementation

	var
		EngineeringWindow: DialogPtr;{Pointer to this dialog}
		HandleButtonOK: Handle;
		HandleSegAnimalTrunk: Handle;
		HandleSegAnimalLegs: Handle;
		HandleSegAnimalClaws: Handle;
		HandleSegSectionTrunk: Handle;
		HandleSegSectionLegs: Handle;
		HandleSegSectionClaws: Handle;
		HandleSegSegmentTrunk: Handle;
		HandleSegSegmentLegs: Handle;
		HandleSegSegmentClaws: Handle;
		HandleMutWidth: Handle;
		HandleMutHeight: Handle;
		HandleMutAngle: Handle;
		HandleMutDuplication: Handle;
		HandleMutDeletion: Handle;
		HandleSegLegs: Handle;
		HandleSegClaws: Handle;
		HandlePressurePositive: Handle;
		HandlePressureZero: Handle;
		HandlePressureNegative: Handle;
		HandleFocus1stSeg: Handle;
		HandleFocusLastSeg: Handle;
		HandleFocusNo: Handle;

	
	procedure InitializeEngrControls;
	var
		DType: integer;
		tempRect: Rect;
	begin
 			GetDialogItem(EngineeringWindow, kEngrButtonOK, DType, HandleButtonOK, tempRect);{Get the item handle}
 			GetDialogItem(EngineeringWindow, kEngrSegAnimalTrunk, DType, HandleSegAnimalTrunk, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegAnimalLegs, DType, HandleSegAnimalLegs, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegAnimalClaws, DType, HandleSegAnimalClaws, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegSectionTrunk, DType, HandleSegSectionTrunk, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegSectionLegs, DType, HandleSegSectionLegs, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegSectionClaws, DType, HandleSegSectionClaws, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegSegmentTrunk, DType, HandleSegSegmentTrunk, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegSegmentLegs, DType, HandleSegSegmentLegs, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegSegmentClaws, DType, HandleSegSegmentClaws, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegLegs, DType, HandleSegLegs, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrSegClaws, DType, HandleSegClaws, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrMutWidth, DType, HandleMutWidth, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrMutHeight, DType, HandleMutHeight, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrMutAngle, DType, HandleMutAngle, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrMutDuplication, DType, HandleMutDuplication, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrMutDeletion, DType, HandleMutDeletion, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrPressurePositive, DType, HandlePressurePositive, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrPressureZero, DType, HandlePressureZero, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrPressureNegative, DType, HandlePressureNegative, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrFocus1stSeg, DType, HandleFocus1stSeg, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrFocusLastSeg, DType, HandleFocusLastSeg, tempRect);{Get the item handle}
			GetDialogItem(EngineeringWindow, kEngrFocusNo, DType, HandleFocusNo, tempRect);{Get the item handle}
	end;

	procedure SetEngrDialogControlValues;
		{$ifc not undefined THINK_Pascal}
		var
			TheDialogPtr: DialogPeek;{Pointer to Dialogs definition record, contains the TE record}

			ThisEditText: TEHandle; {Handle to get the Dialogs TE record}
		{$endc}
		begin
			{$ifc not undefined THINK_Pascal}
			TheDialogPtr := DialogPeek(EngineeringWindow);{Get to the inner record}
			ThisEditText := TheDialogPtr^.textH;{Get to the TE record}
			HLock(Handle(ThisEditText));{Lock it for safety}
			ThisEditText^^.txSize := 12;{TE Point size}
			TextSize(12);   			{Window Point size}
			ThisEditText^^.txFont := systemFont;{TE Font ID}
			TextFont(systemFont);   	{Window Font ID}
			ThisEditText^^.txFont := 0;{TE Font ID}
			ThisEditText^^.fontAscent := 12;{Font ascent}
			ThisEditText^^.lineHeight := 12 + 3 + 1;{Font ascent + descent + leading}
			HUnLock(Handle(ThisEditText));{UnLock the handle when done}
			{$endc}
			{Setup initial conditions}
			
			SetControlValue(Pointer(HandleSegAnimalTrunk), integer(AnimalTrunkMut));
			SetControlValue(Pointer(HandleSegAnimalLegs), integer(AnimalLegsMut));
			SetControlValue(Pointer(HandleSegAnimalClaws), integer(AnimalClawsMut));
			SetControlValue(Pointer(HandleSegSectionTrunk), integer(SectionTrunkMut));
			SetControlValue(Pointer(HandleSegSectionLegs), integer(SectionLegsMut));
			SetControlValue(Pointer(HandleSegSectionClaws), integer(SectionClawsMut));
			SetControlValue(Pointer(HandleSegSegmentTrunk), integer(SegmentTrunkMut));
			SetControlValue(Pointer(HandleSegSegmentLegs), integer(SegmentLegsMut));
			SetControlValue(Pointer(HandleSegSegmentClaws), integer(SegmentClawsMut));
			SetControlValue(Pointer(HandleSegLegs), integer(LegsMut));
			SetControlValue(Pointer(HandleSegClaws), integer(ClawsMut));
			SetControlValue(Pointer(HandleMutWidth), integer(WidthMut));
			SetControlValue(Pointer(HandleMutHeight), integer(HeightMut));
			SetControlValue(Pointer(HandleMutAngle), integer(AngleMut));
			SetControlValue(Pointer(HandleMutDuplication), integer(DuplicationMut));
			SetControlValue(Pointer(HandleMutDeletion), integer(DeletionMut));

			{And  now the radio buttons}
			SetControlValue(Pointer(HandlePressurePositive), integer(MutationPressure = positive));
			SetControlValue(Pointer(HandlePressureZero), integer(MutationPressure = zero));
			SetControlValue(Pointer(HandlePressureNegative), integer(MutationPressure = negative));
			SetControlValue(Pointer(HandleFocus1stSeg), integer(FocusOfAttention = FirstSegmentOnly));
			SetControlValue(Pointer(HandleFocusLastSeg), integer(FocusOfAttention = LastSegmentOnly));
			SetControlValue(Pointer(HandleFocusNo), integer(FocusOfAttention = AnySegment));
		end;

	procedure MakeAllBodyMutationControls (State: boolean);
	begin
		SetControlValue(Pointer(HandleSegLegs), integer(state));
		SetControlValue(Pointer(HandleSegClaws), integer(state));
		SetControlValue(Pointer(HandleSegAnimalTrunk), integer(state));
		SetControlValue(Pointer(HandleSegAnimalLegs), integer(state));
		SetControlValue(Pointer(HandleSegAnimalClaws), integer(state));
		SetControlValue(Pointer(HandleSegSectionTrunk), integer(state));
		SetControlValue(Pointer(HandleSegSectionLegs), integer(state));
		SetControlValue(Pointer(HandleSegSectionClaws), integer(state));
		SetControlValue(Pointer(HandleSegSegmentTrunk), integer(state));
		SetControlValue(Pointer(HandleSegSegmentLegs), integer(state));
		SetControlValue(Pointer(HandleSegSegmentClaws), integer(state))
	end;

	procedure MakeAllAtomMutationControls (State: boolean);
	begin
		SetControlValue(Pointer(HandleMutWidth), integer(state));
		SetControlValue(Pointer(HandleMutHeight), integer(state));
		SetControlValue(Pointer(HandleMutAngle), integer(state));
		SetControlValue(Pointer(HandleMutDuplication), integer(state));
		SetControlValue(Pointer(HandleMutDeletion), integer(state))
	end;

	{This is an update routine for non-controls in the dialog}
	{This is executed after the dialog is uncovered by an alert}
	procedure Refresh_Dialog;   		{Refresh the dialogs non-controls}
		var
			savePort:GrafPtr;
			tempRect: Rect;	
			DType: integer;
			
		begin
			GetPort(savePort);
			SetPort(EngineeringWindow);{Point to our dialog window}
			GetDialogItem(EngineeringWindow, kEngrButtonOK, DType, HandleButtonOK, tempRect);{Get the item handle}
			DrawEngrContent(tempRect);
			SetPort(savePort);
		end;
	

	procedure HandleClassicEvent;
		var DType: integer;
			DItem: Handle;
			tempRect: Rect;
			Index: integer;
			temp:integer;
		begin
			if not (
				boolean(GetControlValue(Pointer(HandleMutDeletion))) or
				boolean(GetControlValue(Pointer(HandleMutDuplication))) or
				boolean(GetControlValue(Pointer(HandleMutAngle))) or
				boolean(GetControlValue(Pointer(HandleMutHeight))) or
				boolean(GetControlValue(Pointer(HandleMutWidth)))) then
				begin
					AgreeToExit := false;
					TellError('You must allow at least one class of mutation');
					exit
				end;

			if not (
				boolean(GetControlValue(Pointer(HandleSegAnimalTrunk))) or
				boolean(GetControlValue(Pointer(HandleSegAnimalLegs))) or
				boolean(GetControlValue(Pointer(HandleSegAnimalClaws))) or
				boolean(GetControlValue(Pointer(HandleSegSectionTrunk))) or
				boolean(GetControlValue(Pointer(HandleSegSectionLegs))) or
				boolean(GetControlValue(Pointer(HandleSegSectionClaws))) or
				boolean(GetControlValue(Pointer(HandleSegSegmentTrunk))) or
				boolean(GetControlValue(Pointer(HandleSegSegmentLegs))) or
				boolean(GetControlValue(Pointer(HandleSegSegmentClaws))) or
				boolean(GetControlValue(Pointer(HandleSegLegs))) or
				boolean(GetControlValue(Pointer(HandleSegClaws)))
			) then
				begin
					AgreeToExit := false;
					TellError('You must allow at least one body part to mutate');
					exit
				end;
				
			if not(
				boolean(GetControlValue(Pointer(HandleSegSectionTrunk))) or
				boolean(GetControlValue(Pointer(HandleSegSectionLegs))) or
				boolean(GetControlValue(Pointer(HandleSegSegmentTrunk))) or
				boolean(GetControlValue(Pointer(HandleSegSegmentLegs))) or
				boolean(GetControlValue(Pointer(HandleSegLegs))) or
				boolean(GetControlValue(Pointer(HandleMutAngle))) or
				boolean(GetControlValue(Pointer(HandleMutHeight))) or
				boolean(GetControlValue(Pointer(HandleMutWidth))) 
			) then
				begin
					AgreeToExit := false;
					TellError('You cannot duplicate or delete claws or whole animal');
					exit;
				end;
					
			Index := kEngrPressurePositive;   				{Start at the first radio in this group}
			repeat  					{Look until we have found the radio selected}
				GetDialogItem(EngineeringWindow, Index, DType, DItem, tempRect);{Get the radio handle}
				temp := GetControlValue(Pointer(DItem));{Get the radio value}
				Index := Index + 1;{Go to next radio}
			until (temp <> 0) or (Index > kEngrPressureNegative);{Go till we find it}
			temp := Index - kEngrPressurePositive + 1; 		{The indexed radio selection}
			case temp of
				2: 
					MutationPressure := positive;
				3: 
					mutationPressure := zero;
				4: 
					MutationPressure := negative;
			end; {cases}

			Index := kEngrFocus1stSeg;{Start at the first radio in this group}
			repeat  					{Look until we have found the radio selected}
				GetDialogItem(EngineeringWindow, Index, DType, DItem, tempRect);{Get the radio handle}
				temp := GetControlValue(Pointer(DItem));{Get the radio value}
				Index := Index + 1;{Go to next radio}
			until (temp <> 0) or (Index > kEngrFocusNo);{Go till we find it}
			temp := Index - kEngrFocus1stSeg + 1;{The indexed radio selection}
			case temp of
				2: 
					FocusOfAttention := FirstSegmentOnly;
				3: 
					FocusOfAttention := LastSegmentOnly;
				4: 
					FocusOfAttention := AnySegment;
			end; {cases}
			
			if AgreeToExit then
				begin
					DuplicationMut := boolean(GetControlValue(Pointer(HandleMutDuplication)));{Get the checkbox value}
				
						{FIXME add other checkboxen and radio buttons}
					SectionClawsMut := boolean(GetControlValue(Pointer(HandleSegSectionClaws)));{Get the checkbox value}
					SegmentTrunkMut := boolean(GetControlValue(Pointer(HandleSegSegmentTrunk)));{Get the checkbox value}
					SegmentLegsMut := boolean(GetControlValue(Pointer(HandleSegSegmentLegs)));{Get the checkbox value}
					SegmentClawsMut := boolean(GetControlValue(Pointer(HandleSegSegmentClaws)));{Get the checkbox value}
					LegsMut := boolean(GetControlValue(Pointer(HandleSegLegs)));{Get the checkbox value}
					ClawsMut := boolean(GetControlValue(Pointer(HandleSegClaws)));{Get the checkbox value}
				end;

	end; {OK button pressed}

	procedure ProcessEngrEventLoop;
	var
		ExitDialog, Accept: boolean;   		{Flag used to exit the Dialog}
		itemHit: integer;
		DType: integer;
		DItem: Handle;
		tempRect: Rect;
		Index: integer;
	begin
		ExitDialog := FALSE;  		{Do not exit dialog handle loop yet}
		repeat  					{Start of dialog handle loop}
			ModalDialog(nil, itemHit);{Wait until an item is hit}
			GetDialogItem(EngineeringWindow, itemHit, DType, DItem, tempRect);{Get item information}
			case iTemHit of
			kEngrButtonOK:
				begin
					Accept := true;
					ExitDialog := TRUE;{Exit the dialog when this selection is made}
				end;				
			kEngrSegAll:
				begin
					{FIXME Copy routine from Richard.pas and adjust so it just does controls}
					MakeAllBodyMutationControls(true);
				end;				
			kEngrSegNone:
				begin
					{FIXME Copy routine from Richard.pas and adjust so it just does controls}
					MakeAllBodyMutationControls(false);
				end;
			kEngrMutAll:
				begin
					{FIXME Copy routine from Richard.pas and adjust so it just does controls}
					MakeAllAtomMutationControls(true);
				end;
			kEngrMutNone:
				begin
					{FIXME Copy routine from Richard.pas and adjust so it just does controls}
					MakeAllAtomMutationControls(false);
				end;
			kEngrButtonCancel:
				begin
					Accept := false;
					ExitDialog := TRUE;{Exit the dialog when this selection is made}
				end;
			{Segment Mutation checkboxen}
			kEngrSegAnimalTrunk,
			kEngrSegAnimalLegs,
			kEngrSegAnimalClaws,
			kEngrSegSectionTrunk,
			kEngrSegSectionLegs,
			kEngrSegSectionClaws,
			kEngrSegSegmentTrunk,
			kEngrSegSegmentLegs,
			kEngrSegSegmentClaws,
			kEngrMutWidth,
			kEngrMutHeight,
			kEngrMutAngle,
			kEngrMutDuplication,
			kEngrMutDeletion,
			kEngrSegLegs,
			kEngrSegClaws:
				SetControlValue(Pointer(DItem), 1 - GetControlValue(Pointer(DItem)));
			{Radio Buttons}
			
			kEngrPressurePositive, kEngrPressureZero, kEngrPressureNegative:
				begin
					for Index := kEngrPressurePositive to kEngrPressureNegative do{Clear all other radios}
						begin
							GetDialogItem(EngineeringWindow, Index, DType, DItem, tempRect);{Get the Radio handle}
							SetControlValue(Pointer(DItem), 0);{Turn the radio selection OFF}
						end;    			{End of clear the radio selections loop}
					SetControlValue(Pointer(DItem), 1);{Turn the one radio selection ON}
				end;

			kEngrFocus1stSeg, kEngrFocusLastSeg, kEngrFocusNo:
				begin
					for Index := kEngrFocus1stSeg to kEngrFocusNo do{Clear all other radios}
						begin
							GetDialogItem(EngineeringWindow, Index, DType, DItem, tempRect);{Get the Radio handle}
							SetControlValue(Pointer(DItem), 0);{Turn the radio selection OFF}
						end;    			{End of clear the radio selections loop}
					SetControlValue(Pointer(DItem), 1);{Turn the one radio selection ON}
				end;
			END;
		until ExitDialog;   		{Handle dialog items until exit selected}
		
		{Get results after dialog}
		if Accept then
			HandleClassicEvent;
	end;
	procedure ProcessEngrDialogResults;
	begin
	end;


	procedure InitializeEngrWindowClassic;
	begin
		EngineeringWindow := GetNewDialog(4, nil, Pointer(-1));{Bring in the dialog resource}
		InitializeEngrControls;
		SetEngrDialogControlValues;
		ShowWindow(EngineeringWindow);{Open a dialog box}
		SelectWindow(EngineeringWindow);{Lets see it}
		Refresh_Dialog;     		{Draw any Lists, popups, lines, or rectangles}
		ProcessEngrEventLoop;
		ProcessEngrDialogResults;
		DisposeDialog(EngineeringWindow);{Flush the dialog out of memory}
		RunAppModalLoopForWindow(EngineeringWindow);{Wait until an item is hit}
		DisposeWindow(EngineeringWindow);{Flush the dialog out of memory}
	end;

end.