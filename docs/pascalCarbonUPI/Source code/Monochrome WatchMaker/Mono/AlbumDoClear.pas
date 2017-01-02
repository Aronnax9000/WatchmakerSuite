unit AlbumDoClear;

interface

	uses 
	AlbumDefs,
	AlbumUtil, 
	BoxesDefs, 
	Globals, 
	HIBoxTypes,
	MacOSAll, DevelopDefs;
	
procedure DoClear (Really: Boolean; breedModel: HIBreedModelPtr);

	
implementation

procedure DoClear (Really: Boolean; breedModel: HIBreedModelPtr);
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
				{EraseRect(Box[Special]);}
				OldSpecial := nil;
				Special^.denizen := Album.Member[j]^.denizen;
				Album.Member[j]^.denizen^.Gene[9] := 0;
				AlbumEmpty := TRUE;
				IF Album.menagerieSize > 0 THEN
					FOR k := 1 TO Album.menagerieSize DO
						IF Album.Member[k]^.denizen^.Gene[9] > 0 THEN
							AlbumEmpty := FALSE;
				StoreOffScreen(PRect, AlbumBitMap[CurrentPage]);
				StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);
			end
		ELSE
			begin {Paste}
				IF Album.Member[j]^.denizen^.Gene[9] = 0 THEN {Currently selected slot cleared}
					begin
						Album.member[j]^.denizen := CopiedAnimal;
						(* FIXME
						EraseRect(Box[special]);
						Delayvelop(Album.member[j], Centre[special]);
						AlbumEmpty := FALSE;
						Child[special] := CopiedAnimal;
						StoreOffScreen(PRect, AlbumBitMap[CurrentPage]);
						InvertRect(Box[special]); *)
						OldSpecial := Special;
						StoreOffScreen(GetWindowPortBounds(MainWindow, tempRect)^, MyBitMap);
					end
				ELSE IF RoomInAlbum THEN {FIXME not initialized - ABC}
					begin
        {InvertRect(Box[special]);}
						OldSpecial := Special;
						AddtoAlbum(CopiedAnimal, breedModel);
					end;
			end;
	end; {DoClear}
end.