unit unit_doadjustscrollbar;

interface
{$IFC UNDEFINED THINK_Pascal}
	uses
		Quickdraw;
{$ENDC}
procedure DoAdjustScrollbar (myWindowPtr: WindowPtr);
procedure DoAdjustScrollSizes(myWindowPtr: WindowPtr);
procedure DoAdjustTEViewRect(myWindowPtr: WindowPtr);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Types, TextEdit, Windows, Controls,
{$ENDC}
   unit_docrec;
procedure DoAdjustTEViewRect(myWindowPtr: WindowPtr);
var hTE: TEHandle;
begin
  hTE := DocRecHandle(GetWRefCon(myWindowPtr))^^.editRecHdl;
  with hte^^.viewRect do
    begin
      right := myWindowPtr^.portRect.right - 15;
      bottom := myWindowPtr^.portRect.bottom - 15;
    end;
end;


procedure DoAdjustScrollSizes(myWindowPtr: WindowPtr);
var
  docRecHdl: DocRecHandle;
begin
  docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));

  with myWindowPtr^.portRect do
  begin
    MoveControl(docRecHdl^^.vScrollbarHdl, right - 15, -1);
    SizeControl(docRecHdl^^.vScrollbarHdl, 16, bottom - 13);
  end;
end;

procedure DoAdjustScrollbar (myWindowPtr: WindowPtr);

	var
		docRecHdl: DocRecHandle;
		editRecHdl: TEHandle;
		numberOfLines, controlMax, controlValue: integer;

begin
	docRecHdl := DocRecHandle(GetWRefCon(myWindowPtr));
	editRecHdl := docRecHdl^^.editRecHdl;

	numberOfLines := editRecHdl^^.nLines;
	if (integer(Ptr(longint(@editRecHdl^^.hText^^) + integer(editRecHdl^^.teLength) - 1)^) = kReturn) then
		numberOfLines := numberOfLines + 1;

	controlMax := numberOfLines - (editRecHdl^^.viewRect.bottom - editRecHdl^^.viewRect.top) div editRecHdl^^.lineHeight;

	if (controlMax < 0) then
		controlMax := 0;
	SetCtlMax(docRecHdl^^.vScrollbarHdl, controlMax);

	controlValue := (editRecHdl^^.viewRect.top - editRecHdl^^.destRect.top) div editRecHdl^^.lineHeight;
	if (controlValue < 0) then
		controlValue := 0
	else if (controlValue > controlMax) then
		controlValue := controlMax;

	SetCtlValue(docRecHdl^^.vScrollbarHdl, controlValue);

	TEScroll(0, (editRecHdl^^.viewRect.top - editRecHdl^^.destRect.top) - (GetCtlValue(docRecHdl^^.vScrollbarHdl) * editRecHdl^^.lineHeight), editRecHdl);
end;
	{of procedure DoAdjustScrollbar}

end.

