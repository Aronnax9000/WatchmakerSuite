unit BiomorphsClipboard;

interface

uses MacOSAll;

	{$ifc not undefined THINK_Pascal}
	{FIXME}
	procedure DoPaste;
	procedure SendToClipBoard;
	{$endc}

implementation
{$ifc not undefined THINK_Pascal}
	procedure PictureToScrap (theBiomorph: person);
		type
			BiPtr = ^person;
		var
			LENGTH: LongInt;
			Source: Ptr;
			BiSource: BiPtr;
	begin
		if ZeroScrap <> NoErr then
			begin
				SysBeep(1);
			end
		else
			begin
				HLock(handle(MyPicture));
				Length := MyPicture^^.PicSize;
				Source := Ptr(MyPicture^);
				if PutScrap(Length, 'PICT', Ptr(MyPicture^)) <> NoErr then
					Message('Problem with fitting Biomorph Picture in ClipBoard')
				else
					begin
						HUnlock(handle(MyPicture));
						Length := SizeOf(person);
						BiSource := BiPtr(NewPtr(Length));
						BiSource^ := theBiomorph;
						if PutScrap(Length, 'BIOC', Ptr(BiSource)) <> NoErr then
							Message('Problem with storing Biomorph Genes in ClipBoard ');
					end;
			end
	end;


	function GetFromScrap: person;
		type
			BiPtr = ^person;
			biHdl = ^BiPtr;
		var
			bHdl: biHdl;
			length, offset: Longint;
	begin
		bHdl := biHdl(NewHandle(0));
		length := GetScrap(Handle(bHdl), 'BIOC', offset);
		if length < 0 then
			begin
				EmptiedClip;
				Message(' Nothing to Paste . You can only Paste if you have previously Copied , either from Blind Watchmaker or Scrapbook ');
			end
		else
			begin
				GetFromScrap := bHdl^^;
				FilledClip;
			end;
	end; {GetFromScrap}
	
	
	procedure DoPaste;
	begin
		if DeskScrap then
			ClipBiomorph := GetFromScrap;
 {Special:=MidBox;}
		if clipfull then
			begin
				Child[special] := ClipBiomorph;
				RGBBackColor(chooseColor(child[special].BackColorGene));
				if theMode = Breeding then
					begin
						EraseRect(Box[special]);
						FrameRect(Box[special])
					end
				else
					eraserect(Prect);
				Delayvelop(child[special], centre[special]);
				BackColor(WhiteColor);
			end
	end;

	procedure SendToClipBoard;
		var
			HS: INTEGER;
	begin
		pensize(2, 2);
		penpat(white);
		framerect(Box[Midbox]);
		penpat(black);
		pensize(1, 1);
		MyPicture := OpenPicture(Box[MidBox]);
		RGBBackColor(ChooseColor(Child[special].BackColorGene));
		EraseRect(Box[MidBox]);
		Delayvelop(Child[Special], Centre[MidBox]);
		CopiedAnimal := Child[special];
		ClipBiomorph := Child[special];
		ClosePicture;
		pensize(2, 2);
		penpat(black);
		framerect(Box[Midbox]);
		pensize(1, 1);
		HS := GetHandleSize(Handle(MyPicture));
		if (HS = 0) or (HS > 32000) then
			Message('Biomorph too large, or otherwise impossible to send to ClipBoard')
		else
			PictureToScrap(Child[special]);
		KillPicture(MyPicture);
		BackColor(whiteColor);
		FilledClip;
		DeskScrap := FALSE;
	end; {SendToClipBoard}
{$elsec}
{FIXME}
{$endc}
end.