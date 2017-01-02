
unit unit_snapshot;
interface
uses
{$IFC UNDEFINED THINK_Pascal}
  Types,
{$ENDC}
  unit_snail_types, unit_globals, unit_miscellaneous;

	procedure Snapshot (prefs: SnailPreferencesHandle; box: Rect; biomorph: person);
implementation
uses
{$IFC UNDEFINED THINK_Pascal}
		Quickdraw, Memory, ToolUtils,
{$ENDC}
  unit_drawpic;

	procedure Snapshot (prefs: SnailPreferencesHandle; box: Rect; biomorph: person);
		var
			Midpoint: Point;
			SavePort: GrafPtr;
			SaveBitMap: BitMap;
	begin
		if prefs^^.theMode = Sweeping then
			ClipRect(prefs^^.PRect)
		else
			ClipRect(businessPart);
		with box do
			begin
				MidPoint.h := left + (right - left) div 2;
				MidPoint.v := top + (bottom - top) div 2
			end;
		GetPort(SavePort);
		SaveBitMap := SavePort^.PortBits;
		SetPortBits(AlbumBitMap[0]);
		EraseRect(box); {offscreen}
		DrawPic(prefs, MidPoint, biomorph);
		SetPort(SavePort);
		SetPortBits(SaveBitMap);
		CopyBits(AlbumBitMap[0], SavePort^.PortBits, box, box, srcCopy, nil);
		ClipRect(MainPtr^.PortRect);
    {Pause('End of Snapshot ');}
	end; {Snapshot}

 end.

