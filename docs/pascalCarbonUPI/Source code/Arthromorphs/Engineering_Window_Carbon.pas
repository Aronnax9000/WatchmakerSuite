unit Engineering_Window_Carbon;

interface
	uses MacOSAll, MyGlobals, Engineering_Window_Constants, Ted;
	const
	
		kEngrControlPressure: ControlID = (signature: UInt32('DAWK'); id: kEngrPressure);
		kEngrControlFocus: ControlID = (signature: UInt32('DAWK'); id: kEngrFocus);
	
		kEngrControlButtonOK: ControlID = (signature: UInt32('DAWK'); id: kEngrButtonOK);
		kEngrControlSegAll: ControlID = (signature: UInt32('DAWK'); id: kEngrSegAll);
		kEngrControlSegNone: ControlID = (signature: UInt32('DAWK'); id: kEngrSegNone);
		kEngrControlMutAll: ControlID = (signature: UInt32('DAWK'); id: kEngrMutAll);
		kEngrControlMutNone: ControlID = (signature: UInt32('DAWK'); id: kEngrMutNone);
		kEngrControlButtonCancel: ControlID = (signature: UInt32('DAWK'); id: kEngrButtonCancel);
		kEngrControlSegAnimalTrunk: ControlID = (signature: UInt32('DAWK'); id: kEngrSegAnimalTrunk);
		kEngrControlSegAnimalLegs: ControlID = (signature: UInt32('DAWK'); id: kEngrSegAnimalLegs);
		kEngrControlSegAnimalClaws: ControlID = (signature: UInt32('DAWK'); id: kEngrSegAnimalClaws);
		kEngrControlSegSectionTrunk: ControlID = (signature: UInt32('DAWK'); id: kEngrSegSectionTrunk);
		kEngrControlSegSectionLegs: ControlID = (signature: UInt32('DAWK'); id: kEngrSegSectionLegs);
		kEngrControlSegSectionClaws: ControlID = (signature: UInt32('DAWK'); id: kEngrSegSectionClaws);
		kEngrControlSegSegmentTrunk: ControlID = (signature: UInt32('DAWK'); id: kEngrSegSegmentTrunk);
		kEngrControlSegSegmentLegs: ControlID = (signature: UInt32('DAWK'); id: kEngrSegSegmentLegs);
		kEngrControlSegSegmentClaws: ControlID = (signature: UInt32('DAWK'); id: kEngrSegSegmentClaws);
		kEngrControlMutWidth: ControlID = (signature: UInt32('DAWK'); id: kEngrMutWidth);
		kEngrControlMutHeight: ControlID = (signature: UInt32('DAWK'); id: kEngrMutHeight);
		kEngrControlMutAngle: ControlID = (signature: UInt32('DAWK'); id: kEngrMutAngle);
		kEngrControlMutDuplication: ControlID = (signature: UInt32('DAWK'); id: kEngrMutDuplication);
		kEngrControlMutDeletion: ControlID = (signature: UInt32('DAWK'); id: kEngrMutDeletion);
		kEngrControlSegLegs: ControlID = (signature: UInt32('DAWK'); id: kEngrSegLegs);
		kEngrControlSegClaws: ControlID = (signature: UInt32('DAWK'); id: kEngrSegClaws);

		kEngrCommandOK = UInt32('OKBT');
		kEngrCommandCancel = UInt32('CNCL');
		kEngrCommandMutAll = UInt32('MTAL');
		kEngrCommandMutNone = UInt32('MTNO');
		kEngrCommandSegAll = UInt32('SGAL');
		kEngrCommandSegNone = UInt32('SGNO');
	procedure InitializeEngrWindowCarbon;
	
implementation

	var
		EngineeringWindow: WindowRef;{Pointer to this dialog}

		ControlRoot: ControlRef;
		
		ControlPressure: ControlRef;
		ControlFocus: ControlRef;
		
		ControlButtonOK: ControlRef;
		ControlSegAll: ControlRef;
		ControlSegNone: ControlRef;
		ControlMutAll: ControlRef;
		ControlMutNone: ControlRef;
		ControlButtonCancel: ControlRef;
		ControlSegAnimalTrunk: ControlRef;
		ControlSegAnimalLegs: ControlRef;
		ControlSegAnimalClaws: ControlRef;
		ControlSegSectionTrunk: ControlRef;
		ControlSegSectionLegs: ControlRef;
		ControlSegSectionClaws: ControlRef;
		ControlSegSegmentTrunk: ControlRef;
		ControlSegSegmentLegs: ControlRef;
		ControlSegSegmentClaws: ControlRef;
		ControlMutWidth: ControlRef;
		ControlMutHeight: ControlRef;
		ControlMutAngle: ControlRef;
		ControlMutDuplication: ControlRef;
		ControlMutDeletion: ControlRef;
		ControlSegLegs: ControlRef;
		ControlSegClaws: ControlRef;

	procedure InvalEngrWindow;
		var
			tmpRect: Rect;
		begin
			if EngineeringWindow <> nil then
			begin
				InvalWindowRect(EngineeringWindow, GetWindowPortBounds(EngineeringWindow, tmpRect)^)
			end;
		end;
		
		
	procedure InitializeEngrControls;
	begin
		GetRootControl(EngineeringWindow, ControlRoot);
		GetControlByID(EngineeringWindow, kEngrControlButtonOK, ControlButtonOK);
		GetControlByID(EngineeringWindow, kEngrControlSegAll, ControlSegAll);
		GetControlByID(EngineeringWindow, kEngrControlSegNone, ControlSegNone);
		GetControlByID(EngineeringWindow, kEngrControlMutAll, ControlMutAll);
		GetControlByID(EngineeringWindow, kEngrControlMutNone, ControlMutNone);
		GetControlByID(EngineeringWindow, kEngrControlButtonCancel, ControlButtonCancel);
		GetControlByID(EngineeringWindow, kEngrControlSegAnimalTrunk, ControlSegAnimalTrunk);
		GetControlByID(EngineeringWindow, kEngrControlSegAnimalLegs, ControlSegAnimalLegs);
		GetControlByID(EngineeringWindow, kEngrControlSegAnimalClaws, ControlSegAnimalClaws);
		GetControlByID(EngineeringWindow, kEngrControlSegSectionTrunk, ControlSegSectionTrunk);
		GetControlByID(EngineeringWindow, kEngrControlSegSectionLegs, ControlSegSectionLegs);
		GetControlByID(EngineeringWindow, kEngrControlSegSectionClaws, ControlSegSectionClaws);
		GetControlByID(EngineeringWindow, kEngrControlSegSegmentTrunk, ControlSegSegmentTrunk);
		GetControlByID(EngineeringWindow, kEngrControlSegSegmentLegs, ControlSegSegmentLegs);
		GetControlByID(EngineeringWindow, kEngrControlSegSegmentClaws, ControlSegSegmentClaws);
		GetControlByID(EngineeringWindow, kEngrControlMutWidth, ControlMutWidth);
		GetControlByID(EngineeringWindow, kEngrControlMutHeight, ControlMutHeight);
		GetControlByID(EngineeringWindow, kEngrControlMutAngle, ControlMutAngle);
		GetControlByID(EngineeringWindow, kEngrControlMutDuplication, ControlMutDuplication);
		GetControlByID(EngineeringWindow, kEngrControlMutDeletion, ControlMutDeletion);
		GetControlByID(EngineeringWindow, kEngrControlSegLegs, ControlSegLegs);
		GetControlByID(EngineeringWindow, kEngrControlSegClaws, ControlSegClaws);
		GetControlByID(EngineeringWindow, kEngrControlPressure, ControlPressure);
		GetControlByID(EngineeringWindow, kEngrControlFocus, ControlFocus);
		
	end;

	function ValidateEngrDialog: boolean;
		var result: boolean;
		begin
			result := true;
			if not (
				boolean(GetControlValue(ControlMutDeletion)) or
				boolean(GetControlValue(ControlMutDuplication)) or
				boolean(GetControlValue(ControlMutAngle)) or
				boolean(GetControlValue(ControlMutHeight)) or
				boolean(GetControlValue(ControlMutWidth))
			) then
				begin
					TellError('You must allow at least one class of mutation');
					result := false;
				end;

			if not (
				boolean(GetControlValue(ControlSegAnimalTrunk)) or
				boolean(GetControlValue(ControlSegAnimalLegs)) or
				boolean(GetControlValue(ControlSegAnimalClaws)) or
				boolean(GetControlValue(ControlSegSectionTrunk)) or
				boolean(GetControlValue(ControlSegSectionLegs)) or
				boolean(GetControlValue(ControlSegSectionClaws)) or
				boolean(GetControlValue(ControlSegSegmentTrunk)) or
				boolean(GetControlValue(ControlSegSegmentLegs)) or
				boolean(GetControlValue(ControlSegSegmentClaws)) or
				boolean(GetControlValue(ControlSegLegs)) or
				boolean(GetControlValue(ControlSegClaws))
			) then
				begin
					TellError('You must allow at least one body part to mutate');
					result := false;
				end;
				
			if not(
				boolean(GetControlValue(ControlSegSectionTrunk)) or
				boolean(GetControlValue(ControlSegSectionLegs)) or
				boolean(GetControlValue(ControlSegSegmentTrunk)) or
				boolean(GetControlValue(ControlSegSegmentLegs)) or
				boolean(GetControlValue(ControlSegLegs)) or
				boolean(GetControlValue(ControlMutAngle)) or
				boolean(GetControlValue(ControlMutHeight)) or
				boolean(GetControlValue(ControlMutWidth))
			) then
				begin
					TellError('You cannot duplicate or delete claws or whole animal');
					result := false;
				end;
			ValidateEngrDialog := result;
		end;

	procedure GetEngrDialogControlValues;
		begin
			AnimalTrunkMut := boolean(GetControlValue(ControlSegAnimalTrunk));
			AnimalLegsMut := boolean(GetControlValue(ControlSegAnimalLegs));
			AnimalClawsMut := boolean(GetControlValue(ControlSegAnimalClaws));
			SectionTrunkMut := boolean(GetControlValue(ControlSegSectionTrunk));
			SectionLegsMut := boolean(GetControlValue(ControlSegSectionLegs));
			SectionClawsMut := boolean(GetControlValue(ControlSegSectionClaws));
			SegmentTrunkMut := boolean(GetControlValue(ControlSegSegmentTrunk));
			SegmentLegsMut := boolean(GetControlValue(ControlSegSegmentLegs));
			SegmentClawsMut := boolean(GetControlValue(ControlSegSegmentClaws));
			LegsMut := boolean(GetControlValue(ControlSegLegs));
			ClawsMut := boolean(GetControlValue(ControlSegClaws));
			WidthMut := boolean(GetControlValue(ControlMutWidth));
			HeightMut := boolean(GetControlValue(ControlMutHeight));
			AngleMut := boolean(GetControlValue(ControlMutAngle));
			DuplicationMut := boolean(GetControlValue(ControlMutDuplication));
			DeletionMut := boolean(GetControlValue(ControlMutDeletion));

			{And now the radio buttons}

			case GetControlValue(ControlPressure) of 
				1: MutationPressure := Positive;
				2: MutationPressure := Zero;
				3: MutationPressure := Negative;
			end;

			case GetControlValue(ControlFocus) of 
				1: FocusOfAttention := FirstSegmentOnly;
				2: FocusOfAttention := LastSegmentOnly;
				3: FocusOfAttention := AnySegment;
			end;
		end;

	procedure SetEngrDialogControlValues;
		begin
			SetControlValue(ControlSegAnimalTrunk, integer(AnimalTrunkMut));
			SetControlValue(ControlSegAnimalLegs, integer(AnimalLegsMut));
			SetControlValue(ControlSegAnimalClaws, integer(AnimalClawsMut));
			SetControlValue(ControlSegSectionTrunk, integer(SectionTrunkMut));
			SetControlValue(ControlSegSectionLegs, integer(SectionLegsMut));
			SetControlValue(ControlSegSectionClaws, integer(SectionClawsMut));
			SetControlValue(ControlSegSegmentTrunk, integer(SegmentTrunkMut));
			SetControlValue(ControlSegSegmentLegs, integer(SegmentLegsMut));
			SetControlValue(ControlSegSegmentClaws, integer(SegmentClawsMut));
			SetControlValue(ControlSegLegs, integer(LegsMut));
			SetControlValue(ControlSegClaws, integer(ClawsMut));
			SetControlValue(ControlMutWidth, integer(WidthMut));
			SetControlValue(ControlMutHeight, integer(HeightMut));
			SetControlValue(ControlMutAngle, integer(AngleMut));
			SetControlValue(ControlMutDuplication, integer(DuplicationMut));
			SetControlValue(ControlMutDeletion, integer(DeletionMut));

			{And  now the radio buttons}
			SetControlValue(ControlPressure, 
				integer(MutationPressure = positive) +
				integer(MutationPressure = zero) * 2 +
				integer(MutationPressure = negative) * 3);
			
			SetControlValue(ControlFocus, 
				integer(FocusOfAttention = FirstSegmentOnly) +
				integer(FocusOfAttention = LastSegmentOnly) * 2 +
				integer(FocusOfAttention = AnySegment) * 3
			);
		end;

	procedure MakeAllBodyMutationControls (State: boolean);
	var
		intState: integer;
	begin
		intState := integer(state);
		SetControlValue(ControlSegLegs, intState);
		SetControlValue(ControlSegClaws, intState);
		SetControlValue(ControlSegAnimalTrunk, intState);
		SetControlValue(ControlSegAnimalLegs, intState);
		SetControlValue(ControlSegAnimalClaws, intState);
		SetControlValue(ControlSegSectionTrunk, intState);
		SetControlValue(ControlSegSectionLegs, intState);
		SetControlValue(ControlSegSectionClaws, intState);
		SetControlValue(ControlSegSegmentTrunk, intState);
		SetControlValue(ControlSegSegmentLegs, intState);
		SetControlValue(ControlSegSegmentClaws, intState)
	end;

	procedure MakeAllAtomMutationControls (State: boolean);
	var
		intState: integer;
	begin
		intState := integer(state);
		SetControlValue(ControlMutWidth, intState);
		SetControlValue(ControlMutHeight, intState);
		SetControlValue(ControlMutAngle, intState);
		SetControlValue(ControlMutDuplication, intState);
		SetControlValue(ControlMutDeletion, intState)
	end;
	
	{Get results after dialog}
	procedure ProcessEngrDialogResults;
	begin 
		if ValidateEngrDialog then 
			begin 
				GetEngrDialogControlValues;
				QuitAppModalLoopForWindow(EngineeringWindow);
			end;
	end;

	function HandleEngrCommand(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var 
		status: OSStatus;
		theCommand: HICommand;
	begin
		status := NoErr;
		GetEventParameter(theEvent, kEventParamDirectObject, typeHICommand, nil, sizeof(HICommand), nil, @theCommand);
		case theCommand.commandID of
		kEngrCommandOK: ProcessEngrDialogResults;
		kEngrCommandCancel: QuitAppModalLoopForWindow(EngineeringWindow);
		kEngrCommandMutAll: MakeAllAtomMutationControls(true);
		kEngrCommandMutNone: MakeAllAtomMutationControls(false);
		kEngrCommandSegAll: MakeAllBodyMutationControls(true);
		kEngrCommandSegNone: MakeAllBodyMutationControls(false);
		otherwise status := eventNotHandledErr;
		end;
		HandleEngrCommand := status;
	end;

	function HandleWindowEngrCloseEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var
			status: OSStatus;
		begin
			status := noErr;
			QuitAppModalLoopForWindow(EngineeringWindow);
			HandleWindowEngrCloseEvent := status;
		end;
	
	function HandleWindowEngrOKEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
	var
		status: OSStatus;
	begin
		status := noErr;
		ProcessEngrDialogResults;
		QuitAppModalLoopForWindow(EngineeringWindow);
		HandleWindowEngrOKEvent := status;
	end;
	
	function HandleWindowEngrDrawContentEvent(nextHandler: EventHandlerCallRef; theEvent: EventRef; userData: Pointer): OSSTatus;MWPascal;
		var
			status: OSStatus;
			tempRect: Rect;
		begin
			status := eventNotHandledErr;
			DrawEngrContent(GetControlBounds(ControlButtonOK, tempRect)^);
			HandleWindowEngrDrawContentEvent := status;
		end;


	procedure InstallEngrEventHandlers;
		var status: OSStatus;
		begin
			{status := InstallStandardEventHandler(GetWindowEventTarget(EngineeringWindow));}
			status := InstallEventHandler(GetWindowEventTarget(EngineeringWindow), HandleWindowEngrCloseEvent, 1, @windowEventCloseType, nil, nil);
			status := InstallEventHandler(GetWindowEventTarget(EngineeringWindow), HandleWindowEngrDrawContentEvent, 1, @windowEventDrawContentType, nil, nil);
			status := InstallEventHandler(GetWindowEventTarget(EngineeringWindow), HandleEngrCommand, 1, @commandEventType, nil, nil);
		end;

	procedure InitializeEngrWindowCarbon;
		var
			status: OSStatus;
			theNib: IBNibRef;
		begin   							{Start of dialog handler}
			status := CreateNibReference(CFSTR('Brand_New'), theNib);
			if status <> NoErr then exit;
			CreateWindowFromNib(theNib, CFSTR('Engineering Window'), EngineeringWindow);
			DisposeNibReference(theNib);
			InitializeEngrControls;
			SetEngrDialogControlValues;
			InstallEngrEventHandlers;
			ShowWindow(EngineeringWindow);
			SelectWindow(EngineeringWindow);{Lets see it}
			{ActivateWindow(EngineeringWindow, true);}{Open a dialog box}
			{InvalEngrWindow;}
			(*SetPort(GetWindowPort(EngineeringWindow));
			status := HandleWindowEngrDrawContentEvent(nil, nil, nil);*)
			{HIWindowFlush(EngineeringWindow);}
			RunAppModalLoopForWindow(EngineeringWindow);{Wait until an item is hit}
			DisposeWindow(EngineeringWindow);{Flush the dialog out of memory}
		end;
end.
