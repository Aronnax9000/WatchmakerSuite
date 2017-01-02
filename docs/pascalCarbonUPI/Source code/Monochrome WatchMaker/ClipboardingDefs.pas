unit ClipboardingDefs;

interface

	uses 
		ErrorUnit,
		Globals,
		HIBoxTypes,
		MacOSAll;
	procedure SendToClipBoard;

implementation

procedure PictureToScrap;
	VAR
		LENGTH: LongInt;
		Source: Ptr;
		myFlavorFlags: ScrapFlavorFlags;
		myScrapRef : ScrapRef;
		myErr: OSStatus;
	begin
		IF ClearCurrentScrap <> NoErr THEN
			begin
				Sysbeep(1); {write('ZeroScrap Error')}
			end
		ELSE
			begin
				HLock(handle(MyPicture));
				Length := MyPicture^^.PicSize;
				Source := Ptr(MyPicture^);
				myFlavorFlags := kScrapFlavorMaskNone; {kScrapFlavorMaskSenderOnly}
				myErr := GetCurrentScrap(myScrapRef);
				if myErr = noErr then
					myErr := PutScrapFlavor(myScrapRef, 'PICT', myFlavorFlags, length, source);
			end;
		ClipBoarding := TRUE;
	end;
procedure SendToClipBoard;
	VAR
		HS: Integer;
		errString, helpString: Str255;
	begin
{		MyPicture := OpenPicture(boxes^.MidBox);} {Fixme}
		{Delayvelop(Special, boxes^.midbox^.midpoint);}
		CopiedAnimal := Special^.denizen;
		ClosePicture;
		HS := GetHandleSize(Handle(MyPicture));
		IF (HS = 0) OR (HS > 32000) THEN
			begin
				errString := GetString(131)^^;
				helpString := GetString(132)^^;
				DisplayError(0, errString, helpString, StopError);
{was: (uses Help dialog! ) Message(GetString(TooLargeString))}
			end
		ELSE
			PictureToScrap;
		KillPicture(MyPicture);
	end; {SendToClipBoard}


end.