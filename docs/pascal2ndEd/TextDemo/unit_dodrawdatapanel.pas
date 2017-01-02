unit unit_dodrawdatapanel;



interface
{$IFC UNDEFINED THINK_Pascal}
      uses QuickDraw;
{$ENDC}
procedure DoDrawDataPanel (myWindowPtr: WindowPtr);

implementation
uses
{$IFC UNDEFINED THINK_Pascal}
      Types, TextEdit, Controls, Windows, QuickdrawText, Fonts, TextUtils,
{$ENDC}
  unit_docrec;


procedure DoDrawDataPanel (myWindowPtr: WindowPtr);

	var
		docRecHdl: DocRecHandle;
		editRecHdl: TEHandle;
		controlHdl: ControlHandle;
		panelRect: Rect;
		textString: Str255;
		familyID: integer;
    height, width: integer;
begin
	docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
	editRecHdl := docRecHdl^^.editRecHdl;
	controlHdl := docRecHdl^^.vScrollbarHdl;

	GetFNum('Geneva', familyID);
	TextFont(familyID);
	TextSize(9);

  width := myWindowPtr^.portRect.right - myWindowPtr^.portRect.left;
  height := myWindowPtr^.portRect.bottom - myWindowPtr^.portRect.top;

  {window is 448 x 253}
	MoveTo(3, height - 4);
	DrawString('teLength            nLines        lineHeight');

	MoveTo(193, height - 4);
	DrawString('destRect.top            controlValue        contrlMax');

	MoveTo(0, height - 15);
	LineTo(432, height - 15);

	MoveTo(191, height - 14);
	LineTo(191, height - 1);
	MoveTo(283, height - 14);
	LineTo(283, height - 1);
	MoveTo(363, height - 14);
	LineTo(363, height - 1);

	SetRect(panelRect, 42, height - 14, 76, height - 1);
	EraseRect(panelRect);
	SetRect(panelRect, 106, height - 14, 128, height - 1);
	EraseRect(panelRect);
	SetRect(panelRect, 174, height - 14, 190, height - 1);
	EraseRect(panelRect);
	SetRect(panelRect, 250, height - 14, 282, height - 1);
	EraseRect(panelRect);
	SetRect(panelRect, 342, height - 14, 361, height - 1);
	EraseRect(panelRect);
	SetRect(panelRect, 412, height - 14, 433, height - 1);
	EraseRect(panelRect);

	NumToString(LONGINT(editRecHdl^^.teLength), textString);
	MoveTo(45, height - 4);
	DrawString(textString);

	NumToString(LONGINT(editRecHdl^^.nLines), textString);
	MoveTo(108, height - 4);
	DrawString(textString);

	NumToString(LONGINT(editRecHdl^^.lineHeight), textString);
	MoveTo(176, height - 4);
	DrawString(textString);

	NumToString(LONGINT(editRecHdl^^.destRect.top), textString);
	MoveTo(251, height - 4);
	DrawString(textString);

	NumToString(LONGINT(GetCtlValue(controlHdl)), textString);
	MoveTo(344, height - 4);
	DrawString(textString);

	NumToString(LONGINT(GetCtlMax(controlHdl)), textString);
	MoveTo(414, height - 4);
	DrawString(textString);

	TextSize(10);
end;
	{of procedure DoDrawDataPanel}


end.

