unit AlbumDoClear;

interface

	uses 
	AlbumUtil, BoxesDefs, Globals, MacOSAll, DevelopDefs;
	
	procedure DoClear (Really: Boolean); {otherwise paste}

	
implementation

procedure DoClear (Really: Boolean);
{If really is false, we paste}
	VAR
		k, j: Integer;
		RoomInAlbum: Boolean;
		tempRect: Rect;
	begin
		j := 0;
		AlbumChanged := TRUE;
		REPEAT
			j := j + 1;
		UNTIL (Album.Place[j].Page = CurrentPage) AND (Album.Place[j].BoxNo = Special);
		IF Really THEN
			begin
				EraseRect(Box[Special]);
				OldSpecial := 0;
				Child[special] := Album.Member[j];
				Album.Member[j].Gene[9] := 0;
				AlbumEmpty := TRUE;
				IF Album.menagerieSize > 0 THEN
					FOR k := 1 TO Album.menagerieSize DO
						IF Album.Member[k].Gene[9] > 0 THEN
							AlbumEmpty := FALSE;
				StoreOffScreen(PRect, AlbumBitMap[CurrentPage]);
				StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);
			end
		ELSE
			begin {Paste}
				IF Album.Member[j].Gene[9] = 0 THEN {Currently selected slot cleared}
					begin
						Album.member[j] := CopiedAnimal;
						EraseRect(Box[special]);
						Delayvelop(Album.member[j], Centre[special]);
						AlbumEmpty := FALSE;
						Child[special] := CopiedAnimal;
						StoreOffScreen(PRect, AlbumBitMap[CurrentPage]);
						InvertRect(Box[special]);
						OldSpecial := Special;
						StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);
					end
				ELSE IF RoomInAlbum THEN {FIXME not initialized - ABC}
					begin
        {InvertRect(Box[special]);}
						OldSpecial := Special;
						AddtoAlbum(CopiedAnimal);
					end;
			end;
	end; {DoClear}
end.