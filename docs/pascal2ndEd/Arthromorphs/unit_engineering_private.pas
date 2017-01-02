unit unit_engineering_private;

interface

uses
{$IFC UNDEFINED THINK_Pascal}
Types, Dialogs, Quickdraw,
{$ENDC}
  unit_muts, unit_dialog_helper, unit_tell_error;

type
  EngineeringWindowHandlesHandle = ^EngineeringWindowHandlesPtr;
  EngineeringWindowHandlesPtr = ^EngineeringWindowHandles;
  EngineeringWindowHandles = RECORD
    CAnimalTrunkMut: Handle;
	  CAnimalLegsMut: Handle;
	  CAnimalClawsMut: Handle;
	  CSectionTrunkMut: Handle;
	  CSectionLegsMut: Handle;
	  CSectionClawsMut: Handle;
	  CSegmentTrunkMut: Handle;
	  CSegmentLegsMut: Handle;
	  CSegmentClawsMut: Handle;
	  CTrunkMut: Handle;
	  CLegsMut: Handle;
	  CClawsMut: Handle;
	  CWidthMut: Handle;
	  CHeightMut: Handle;
	  CAngleMut: Handle;
	  CDuplicationMut: Handle;
	  CDeletionMut: Handle;
	  CMutPressPos: Handle;
	  CMutPressZero: Handle;
	  CMutPressNeg: Handle;
	  CFocusFirst: Handle;
	  CFocusLast: Handle;
	  CFocusNone: Handle;
  end;

  procedure MakeAllBodyMutationCheckboxes (State: boolean; handles:
    EngineeringWindowHandlesHandle);
	procedure MakeAllAtomMutationCheckboxes (State: boolean; handles:
    EngineeringWindowHandlesHandle);
  procedure AdjustCheckBoxes(muts: ArthromorphMutationsHandle; handles: EngineeringWindowHandlesHandle);
	procedure Refresh_Dialog(GetSelection: DialogPtr);
  function NewDialogItemHandles(
      GetSelection: DialogPtr): EngineeringWindowHandlesHandle;
  function HandleEngineeringWindowEvents(GetSelection: DialogPtr; handles:
    EngineeringWindowHandlesHandle): boolean;
  function HandleOKButtonPressed(handles: EngineeringWindowHandlesHandle): boolean;
  procedure SetGlobalsFromDialogState(muts: ArthromorphMutationsHandle;
    handles: EngineeringWindowHandlesHandle);

implementation
  {$IFC UNDEFINED THINK_Pascal}
  uses Memory;
  {$ENDC}
	const
    {These are the item numbers for controls in the Dialog}
		I_OK = 1;
		I_All = 2;
		I_None = 3;
		I_All4 = 4;
		I_None6 = 5;
		I_Cancel = 6;
		I_Animal_Trunk = 7;
		I_Animal_Legs = 8;
		I_Animal_Claws = 9;
		I_Section_Trunk = 10;
		I_Section_Legs = 11;
		I_Section_Claws = 12;
		I_Segment_Trunk = 13;
		I_Segment_Legs = 14;
		I_Segment_Claws = 15;
		I_Width = 16;
		I_Height = 17;
		I_Angle = 18;
		I_Duplication = 19;
		I_Deletion = 20;
		I_Legs = 21;
		I_Claws = 22;
    {New for Second Edition: notice that it's out of order. ABC 2015-11-05}
    I_Trunk = 31;
		I_MutPressPos = 23;
		I_MutPressZero = 24;
		I_MutPressNeg = 25;
		I_Focus_on_1st_seg = 26;
		I_Focus_on_last_seg = 27;
		I_No_focus = 28;


  procedure MakeAllBodyMutationCheckboxes (State: boolean;
    handles: EngineeringWindowHandlesHandle);
	begin
    with handles^^ do
      begin
		    SetCheckboxStateFromBoolean(CTrunkMut, State);
		    SetCheckboxStateFromBoolean(CLegsMut, State);
		    SetCheckboxStateFromBoolean(CClawsMut, State);
		    SetCheckboxStateFromBoolean(CAnimalTrunkMut, State);
		    SetCheckboxStateFromBoolean(CAnimalLegsMut, State);
		    SetCheckboxStateFromBoolean(CAnimalClawsMut, State);
		    SetCheckboxStateFromBoolean(CSectionTrunkMut, State);
		    SetCheckboxStateFromBoolean(CSectionLegsMut, State);
		    SetCheckboxStateFromBoolean(CSectionClawsMut, State);
		    SetCheckboxStateFromBoolean(CSegmentTrunkMut, State);
		    SetCheckboxStateFromBoolean(CSegmentLegsMut, State);
		    SetCheckboxStateFromBoolean(CSegmentClawsMut, State);
      end;
  end;

	procedure MakeAllAtomMutationCheckboxes (State: boolean;
    handles: EngineeringWindowHandlesHandle);
	begin
    with handles^^ do
      begin
		    SetCheckboxStateFromBoolean(CWidthMut, State);
		    SetCheckboxStateFromBoolean(CHeightMut, State);
		    SetCheckboxStateFromBoolean(CAngleMut, State);
		    SetCheckboxStateFromBoolean(CDuplicationMut, State);
		    SetCheckboxStateFromBoolean(CDeletionMut, State);
      end;
  end;

  procedure AdjustCheckBoxes(muts: ArthromorphMutationsHandle;
    handles: EngineeringWindowHandlesHandle);
		begin
			{Setup initial conditions}
      with handles^^ do
        with Muts^^ do
          begin
            {Checkboxes}
			      SetCheckboxStateFromBoolean(CAnimalTrunkMut, AnimalTrunkMut);
			      SetCheckboxStateFromBoolean(CAnimalLegsMut, AnimalLegsMut);
			      SetCheckboxStateFromBoolean(CAnimalClawsMut, AnimalClawsMut);
			      SetCheckboxStateFromBoolean(CSectionTrunkMut, SectionTrunkMut);
			      SetCheckboxStateFromBoolean(CSectionLegsMut, SectionLegsMut);
			      SetCheckboxStateFromBoolean(CSectionClawsMut, SectionClawsMut);
			      SetCheckboxStateFromBoolean(CSegmentTrunkMut, SegmentTrunkMut);
			      SetCheckboxStateFromBoolean(CSegmentLegsMut, SegmentLegsMut);
			      SetCheckboxStateFromBoolean(CSegmentClawsMut, SegmentClawsMut);
			      SetCheckboxStateFromBoolean(CTrunkMut, TrunkMut);
			      SetCheckboxStateFromBoolean(CLegsMut, LegsMut);
			      SetCheckboxStateFromBoolean(CClawsMut, ClawsMut);
			      SetCheckboxStateFromBoolean(CWidthMut, WidthMut);
			      SetCheckboxStateFromBoolean(CHeightMut, HeightMut);
			      SetCheckboxStateFromBoolean(CAngleMut, AngleMut);
			      SetCheckboxStateFromBoolean(CDuplicationMut, DuplicationMut);
			      SetCheckboxStateFromBoolean(CDeletionMut, DeletionMut);

            {And now the radio buttons}
			      SetCheckboxStateFromBoolean(CMutPressPos, MutationPressure = positive);
			      SetCheckboxStateFromBoolean(CMutPressZero, MutationPressure = zero);
			      SetCheckboxStateFromBoolean(CMutPressNeg, MutationPressure = negative);
			      SetCheckboxStateFromBoolean(CFocusFirst, FocusOfAttention = FirstSegmentOnly);
			      SetCheckboxStateFromBoolean(CFocusLast, FocusOfAttention = LastSegmentOnly);
			      SetCheckboxStateFromBoolean(CFocusNone, FocusOfAttention = AnySegment);
          end;
		end; {AdjustCheckBoxes}

    {Refresh the dialogs non-controls}
  	{This is an update routine for non-controls in the dialog}
	  {This is executed after the dialog is uncovered by an alert}
		procedure Refresh_Dialog(GetSelection: DialogPtr);
			var
				tempRect: Rect;     		{Temp rectangle used for drawing}

		begin
			SetPort(GetSelection);  	{Point to our dialog window}

      FrameRoundRectAroundDialogItem (GetSelection, I_OK);

			{Draw a rectangle, Rectangle1  }
			SetRect(tempRect, 18, 35, 170, 306);{left,top,right,bottom}
			FrameRect(tempRect);    	{Frame this rectangle area}

			{Draw a rectangle, Rectangle2  }
			SetRect(tempRect, 191, 36, 326, 196);{left,top,right,bottom}
			FrameRect(tempRect);    	{Frame this rectangle area}

			{Draw a rectangle, Rectangle4  }
			SetRect(tempRect, 192, 215, 327, 273);{left,top,right,bottom}
			FrameRect(tempRect);    	{Frame this rectangle area}

			{Draw a rectangle, Rectangle1  }
			SetRect(tempRect, 16, 312, 170, 381);{left,top,right,bottom}
			FrameRect(tempRect);    	{Frame this rectangle area}
		end;

  function NewDialogItemHandles(
    GetSelection: DialogPtr): EngineeringWindowHandlesHandle;
  var
    DType: INTEGER;
    tempRect: Rect;
    handles: EngineeringWindowHandlesHandle;
  begin
    handles := EngineeringWindowHandlesHandle(NewHandle(sizeof(EngineeringWindowHandles)));
    with handles^^ do
      begin
        {Get item handles for dialog box items}
		    GetDItem(GetSelection, I_Animal_Trunk, DType, CAnimalTrunkMut, tempRect);
		    GetDItem(GetSelection, I_Animal_Legs, DType, CAnimalLegsMut, tempRect);
		    GetDItem(GetSelection, I_Animal_Claws, DType, CAnimalClawsMut, tempRect);
		    GetDItem(GetSelection, I_Section_Trunk, DType, CSectionTrunkMut, tempRect);
		    GetDItem(GetSelection, I_Section_Legs, DType, CSectionLegsMut, tempRect);
		    GetDItem(GetSelection, I_Section_Claws, DType, CSectionClawsMut, tempRect);
		    GetDItem(GetSelection, I_Segment_Trunk, DType, CSegmentTrunkMut, tempRect);
		    GetDItem(GetSelection, I_Segment_Legs, DType, CSegmentLegsMut, tempRect);
		    GetDItem(GetSelection, I_Segment_Claws, DType, CSegmentClawsMut, tempRect);
		    GetDItem(GetSelection, I_Trunk, DType, CTrunkMut, tempRect);
		    GetDItem(GetSelection, I_Legs, DType, CLegsMut, tempRect);
		    GetDItem(GetSelection, I_Claws, DType, CClawsMut, tempRect);
		    GetDItem(GetSelection, I_Width, DType, CWidthMut, tempRect);
		    GetDItem(GetSelection, I_Height, DType, CHeightMut, tempRect);
		    GetDItem(GetSelection, I_Angle, DType, CAngleMut, tempRect);
		    GetDItem(GetSelection, I_Duplication, DType, CDuplicationMut, tempRect);
		    GetDItem(GetSelection, I_Deletion, DType, CDeletionMut, tempRect);
        {And now the radio buttons}
		    GetDItem(GetSelection, I_MutPressPos, DType, CMutPressPos, tempRect);
		    GetDItem(GetSelection, I_MutPressZero, DType, CMutPressZero, tempRect);
		    GetDItem(GetSelection, I_MutPressNeg, DType, CMutPressNeg, tempRect);
		    GetDItem(GetSelection, I_Focus_on_1st_seg, DType, CFocusFirst, tempRect);
		    GetDItem(GetSelection, I_Focus_on_last_seg, DType, CFocusLast, tempRect);
		    GetDItem(GetSelection, I_No_focus, DType, CFocusNone, tempRect);
      end;
    NewDialogItemHandles := handles;
  end;

  function HandleEngineeringWindowEvents(
    GetSelection: DialogPtr;
    handles: EngineeringWindowHandlesHandle): boolean;
  var
    ExitDialog: BOOLEAN;
    Accept: BOOLEAN;
    ItemHit: INTEGER;
  begin
    ExitDialog := FALSE;  		{Do not exit dialog handle loop yet}
    Accept := FALSE;
		repeat  					{Start of dialog handle loop}
			ModalDialog(nil, itemHit);{Wait until an item is hit}

      case ItemHit of
      I_OK:
				begin
					Accept := true;
					{?? Code to handle this button goes here}
					ExitDialog := TRUE;{Exit the dialog when this selection is made}
				end;
			I_All:
					MakeAllBodyMutationCheckboxes(true, handles);
			I_None:
					MakeAllBodyMutationCheckboxes(false, handles);
			I_All4:
					MakeAllAtomMutationCheckboxes(true, handles);
			I_None6:
					MakeAllAtomMutationCheckboxes(false, handles);
			I_Cancel:
				begin
					Accept := false;
					ExitDialog := TRUE;{Exit the dialog when this selection is made}
				end;
			I_Animal_Trunk, I_Animal_Legs, I_Animal_Claws, I_Section_Trunk,
      I_Section_Legs, I_Section_Claws, I_Segment_Trunk, I_Segment_Legs,
      I_Segment_Claws, I_Width, I_Height, I_Angle, I_Duplication, I_Deletion,
      I_Trunk, I_Legs, I_Claws:
        ToggleCheckbox(GetSelection, itemHit);
      I_MutPressPos..I_MutPressNeg: {Radio Buttons}
        HandleRadioButtonHit(GetSelection, I_MutPressPos, I_MutPressNeg, ItemHit);
      I_Focus_on_1st_seg..I_No_focus:
        HandleRadioButtonHit(GetSelection, I_Focus_on_1st_seg, I_No_focus, ItemHit);
      otherwise
      end;
		until ExitDialog;   		{Handle dialog items until exit selected}
    HandleEngineeringWindowEvents := Accept;
  end;

  function HandleOKButtonPressed(handles: EngineeringWindowHandlesHandle): boolean;
  var
    DearthOfAtomMuts: boolean;
    DearthOfBodyMuts: boolean;
    AnimalOrClawsOnly: boolean;
    DupDeleteOnly: boolean;
    AllInputsValid: boolean;
  begin
		DearthOfAtomMuts := true;
		DearthOfBodyMuts := true;
		AnimalOrClawsOnly := true;
		DupDeleteOnly := true;
    AllInputsValid := true;
    with handles^^ do
      begin
		    DearthOfAtomMuts := not (
          GetCtrlBool(CDeletionMut) or
          GetCtrlBool(CDuplicationMut) or
          GetCtrlBool(CAngleMut) or
          GetCtrlBool(CHeightMut) or
          GetCtrlBool(CWidthMut)
		    );

		    DupDeleteOnly := not (
          GetCtrlBool(CAngleMut) or
          GetCtrlBool(CHeightMut) or
          GetCtrlBool(CWidthMut)
        );

        DearthOfBodyMuts := not (
		      GetCtrlBool(CAnimalTrunkMut) or
          GetCtrlBool(CAnimalLegsMut) or
		      GetCtrlBool(CAnimalClawsMut) or
		      GetCtrlBool(CSectionClawsMut) or
		      GetCtrlBool(CSegmentClawsMut) or
          GetCtrlBool(CClawsMut) or
          GetCtrlBool(CSectionTrunkMut) or
		      GetCtrlBool(CSectionLegsMut) or
		      GetCtrlBool(CSegmentTrunkMut) or
		      GetCtrlBool(CSegmentLegsMut) or
		      GetCtrlBool(CTrunkMut) or
		      GetCtrlBool(CLegsMut)
        );
        AnimalOrClawsOnly := not (
          GetCtrlBool(CSectionTrunkMut) or
		      GetCtrlBool(CSectionLegsMut) or
		      GetCtrlBool(CSegmentTrunkMut) or
		      GetCtrlBool(CSegmentLegsMut) or
		      GetCtrlBool(CTrunkMut) or
		      GetCtrlBool(CLegsMut)
        );
      end;
		if DearthOfAtomMuts then
			begin
				AllInputsValid := false;
				TellError('You must allow at least one class of mutation');
			end;

		if DearthOfBodyMuts then
			begin
				AllInputsValid := false;
				TellError('You must allow at least one body part to mutate');
			end;

		if AnimalOrClawsOnly and DupDeleteOnly then
			begin
				AllInputsValid := false;
				TellError('You cannot duplicate or delete claws or whole animal');
			end;

    HandleOKButtonPressed := AllInputsValid;
  end;
  procedure SetGlobalsFromDialogState(muts: ArthromorphMutationsHandle; handles: EngineeringWindowHandlesHandle);
  begin
    with handles^^ do
      with Muts^^ do
        begin
          AnimalTrunkMut := GetCtrlBool(CAnimalTrunkMut);
			    AnimalLegsMut := GetCtrlBool(CAnimalLegsMut);
			    AnimalClawsMut := GetCtrlBool(CAnimalClawsMut);
			    SectionTrunkMut := GetCtrlBool(CSectionTrunkMut);
			    SectionLegsMut := GetCtrlBool(CSectionLegsMut);
			    SectionClawsMut := GetCtrlBool(CSectionClawsMut);
			    SegmentTrunkMut := GetCtrlBool(CSegmentTrunkMut);
			    SegmentLegsMut := GetCtrlBool(CSegmentLegsMut);
			    SegmentClawsMut := GetCtrlBool(CSegmentClawsMut);
			    WidthMut := GetCtrlBool(CWidthMut);
			    HeightMut := GetCtrlBool(CHeightMut);
			    AngleMut := GetCtrlBool(CAngleMut);
			    DuplicationMut := GetCtrlBool(CDuplicationMut);
			    DeletionMut := GetCtrlBool(CDeletionMut);
			    TrunkMut := GetCtrlBool(CTrunkMut);
			    LegsMut := GetCtrlBool(CLegsMut);
			    ClawsMut := GetCtrlBool(CClawsMut);

          if GetCtrlBool(CMutPressPos) then
            MutationPressure := Positive
          else if GetCtrlBool(CMutPressZero) then
            MutationPressure := Zero
          else if GetCtrlBool(CMutPressNeg) then
            MutationPressure := Negative;

          if GetCtrlBool(CFocusFirst) then
            FocusOfAttention := FirstSegmentOnly
          else if GetCtrlBool(CFocusLast) then
            FocusOfAttention := LastSegmentOnly
          else if GetCtrlBool(CFocusNone) then
            FocusOfAttention := AnySegment;
        end;

  end;

end.

