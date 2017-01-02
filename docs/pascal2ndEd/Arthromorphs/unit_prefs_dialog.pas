unit unit_prefs_dialog;

interface

uses
  unit_preferences;

procedure D_Preferences(prefs: ArthromorphPreferencesHandle);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Dialogs, Types, Windows, QuickDraw, TextUtils,
{$ENDC}
  unit_dialog_helper, unit_boxes, unit_atoms,
  unit_tell_error;

const {These are the item numbers for controls in the Dialog}
  I_OK = 1;
  I_Colour = 2;
  I_Sideways = 3;
  I_NRows = 7;
  I_NCols = 8;
  I_Centring = 9;
  I_TickDelay = 10;
  I_DecimationFactor = 12;
  I_discrepThreshold = 14;

{Resisted the temptation to call this procedure "killingSpree" -- ABC}
procedure cullBoundaries(theBoxes: BoxesHandle; low, high: integer);
var
  i: integer;
  theBox: AnimalBoxHandle;
begin
  theBox := theBoxes^^.firstAnimalBox;
  if low <> 1 then
    for i := 1 to low - 1 do
      theBox := theBox^^.next;

  for i := low to high do
    with theBox^^ do
      begin
       if BreedersChoice <> nil then
       begin
         kill(BreedersChoice);
         BreedersChoice := nil;
         if hasPicture then
         begin
           KillPicture(AnimalPicture);
           hasPicture := false;
         end;
         theBox := theBox^^.next;
       end;

    end;
end;

procedure ChangeBoxSize(prefs: ArthromorphPreferencesHandle; nRows, nCols: INTEGER);
var theNewBoxes: BoxesHandle;
  i: integer;
  theBox: AnimalBoxHandle;
  theOldBox: AnimalBoxHandle;
  begin
  with prefs^^ do
    begin

    {Resizing := True;}
    {Update the values from NRows and NCols from the appropriate text boxes.}

    i :=  (nRows * nCols) - theBoxes^^.NBoxes;
    if i = 0 then
      RedimensionBoxes(theBoxes, nRows, nCols)
    else
      begin
        theNewBoxes := NewBoxes(nRows, nCols);
        if i > 0 then {More boxes}
          begin
            theOldBox := theBoxes^^.firstAnimalBox;
            theBox := theNewBoxes^^.firstAnimalBox;
            for i := 1 to (theNewBoxes^^.NBoxes div 2) - (theBoxes^^.NBoxes div 2) do
              begin
                theBox := theBox^^.next;
              end;

            while theOldBox <> nil do
              begin
                theBox^^.BreedersChoice := theOldBox^^.BreedersChoice;
                theBox^^.hasPicture := theOldBox^^.hasPicture;
                theBox^^.AnimalPicture := theOldBox^^.AnimalPicture;
                theOldBox^^.hasPicture := false;
                theOldBox^^.AnimalPicture := nil;
                theBox := theBox^^.next;
                theOldBox := theOldBox^^.next;
              end;
            DisposeBoxes(theBoxes);
            theBoxes := theNewBoxes;
            SetupBoxes(theBoxes, prefs^^.BreedingWindow^.portRect);
            enlargebrood := true;

          end
        else
          begin {fewer boxes}
            {kill extra animals below the midbox}
            cullBoundaries(theBoxes, 1, CalculateLowDiscard(theBoxes^^.NBoxes, theNewBoxes^^.NBoxes));
            cullBoundaries(theBoxes, CalculateHighDiscard(theBoxes^^.NBoxes, theNewBoxes^^.NBoxes), theBoxes^^.NBoxes);
            i := 1;
            theBox := theNewBoxes^^.firstAnimalBox;
            theOldBox := theBoxes^^.firstAnimalBox;
            for i := 1 to (theBoxes^^.NBoxes div 2) - (theNewBoxes^^.NBoxes div 2) do
              theOldBox := theOldBox^^.next;
            while theBox <> nil do
              with theBox^^ do
                begin
                  BreedersChoice := theOldBox^^.BreedersChoice;
                  AnimalPicture := theOldBox^^.AnimalPicture;
                  hasPicture := theOldBox^^.hasPicture;
                  theOldBox^^.hasPicture := false; {so dispose doesn't kill pictures we have transfered to the new owner}
                  theBox := theBox^^.next;
                  theOldBox := theOldBox^^.next;
                end;
            DisposeBoxes(theBoxes);
            theBoxes := theNewBoxes;
            resizing := true;
          end;
        end;

    end;

end;


procedure D_Preferences(prefs: ArthromorphPreferencesHandle);
var
  GetSelection: DialogPtr;{Pointer to this dialog}
  DItemColour: Handle; {Handle to the dialog item}
  DItemSideways: Handle; {Handle to the dialog item}
  DItemCentring: Handle; {Handle to the dialog item}
  DItemTickDelay: Handle; {Handle to the dialog item}
  DItemDecimationFactor: Handle; {Handle to the dialog item}
  DItemDiscrepThreshold: Handle; {Handle to the dialog item}
  DItemNRows: Handle; {Handle to the dialog item}
  DItemNCols: Handle; {Handle to the dialog item}
  itemHit: integer; {Get selection}
  ExitDialog: boolean;       {Flag used to exit the Dialog}
  {Temporary rectangle, filled in by GetDItem but not used here.}
  tempRect: Rect;
  {Type of dialog item, , filled in by GetDItem but not used here.}
  DType: integer;
  appearanceChanged: boolean;
  nRows, nCols: integer;
  errStr: Str255;
begin {Start of dialog handler}
  {Bring in the dialog resource}
  GetSelection := GetNewDialog(8, nil, Pointer(-1));

  {Obtain handles on the dialog box items}
  GetDItem(GetSelection, I_Colour, DType, DItemColour, tempRect);
  GetDItem(GetSelection, I_Sideways, DType, DItemSideways, tempRect);
  GetDItem(GetSelection, I_Centring, DType, DItemCentring, tempRect);
  GetDItem(GetSelection, I_NRows, DType, DItemNRows, tempRect);
  GetDItem(GetSelection, I_NCols, DType, DItemNCols, tempRect);
  GetDItem(GetSelection, I_TickDelay, DType, DItemTickDelay, tempRect);
  GetDItem(GetSelection, I_DecimationFactor, DType, DItemDecimationFactor, tempRect);
  GetDItem(GetSelection, I_discrepThreshold, DType, DItemdiscrepThreshold, tempRect);
  FrameRoundRectAroundDialogItem(GetSelection, I_OK);

  ShowWindow(GetSelection); {Open a dialog box}
  SelectWindow(GetSelection); {Lets see it}

  SetTextEditAttributesForDialog(GetSelection);

  {Draw a rounded rectangle around the OK button}
  FrameRoundRectAroundDialogItem(GetSelection, I_OK);

  {Setup initial conditions}
  with prefs^^ do
  begin
    {Set checkbox states for Colour, Sideways}
    SetCheckboxStateFromBoolean(DItemColour, wantColor);
    SetCheckboxStateFromBoolean(DItemSideways, sideways);
    SetCheckboxStateFromBoolean(DItemCentring, centring);

    {Set the current value of NRows and NCols into dialog}
    SetTextboxTextFromNum(DITemNRows, theBoxes^^.NRows);
    SetTextboxTextFromNum(DITemNCols, theBoxes^^.NCols);
    SetTextboxTextFromNum(DITemTickDelay, tickDelay);
    SetTextboxTextFromNum(DItemDecimationFactor, decimationFactor);
    SetTextboxTextFromNum(DItemDiscrepThreshold, discrepThreshold);
  end;
  ExitDialog := False; {Do not exit dialog handle loop yet}
  appearanceChanged := False;

  repeat {Start of dialog handle loop}
    ModalDialog(nil, itemHit); {Wait until an item is hit}

    case ItemHit of
      I_OK:
        begin
          nRows := GetTextboxTextAsNum(DItemNRows);
          nCols := GetTextboxTextAsNum(DItemNCols);

          if (prefs^^.theBoxes^^.NRows <> nRows) or
            (prefs^^.theBoxes^^.NCols <> nCols) then
            if nRows * nCols > MaxBoxes then
              begin
                NumToString(MaxBoxes, errStr);
                TellError(concat('I can''t make more than ', errStr, ' boxes.'));
              end
            else
              begin
                ChangeBoxSize(prefs, nRows, nCols);
                appearanceChanged := true;
                ExitDialog := TRUE;
              end
          else
            ExitDialog := TRUE;

        end;
      I_Colour:
        ToggleCheckbox(GetSelection, I_Colour);
      I_Sideways:
        ToggleCheckbox(GetSelection, I_Sideways);
      I_Centring:
        ToggleCheckbox(GetSelection, I_Centring);
    end; {cases}
  until ExitDialog;       {Handle dialog items until exit selected}

  with prefs^^ do
  begin
    {Get results after dialog}
    tickDelay := GetTextboxTextAsNum(DItemTickDelay);
    decimationFactor := GetTextboxTextAsNum(DItemDecimationFactor);
    discrepThreshold := GetTextboxTextAsNum(DItemDiscrepThreshold);
  end;
  {Get checkbox states and update global variables wantColor, and sideways}

  with prefs^^ do
  begin
    if (wantColor <> GetCtrlBool(DItemColour))
    or (sideways <> GetCtrlBool(DItemSideways))
    or (centring <> GetCtrlBool(DItemCentring)) then
    begin
      appearanceChanged := true;
      redraw := True;
      wantColor := GetCtrlBool(DItemColour);
      sideways := GetCtrlBool(DItemSideways);
      centring := GetCtrlBool(DItemCentring);
    end;
    {Flush the dialog out of memory}
    DisposeDialog(GetSelection);
    if appearanceChanged then
        InvalidateWindow(BreedingWindow)
;
  end;
end;  {procedure D_Preferences}

end.
