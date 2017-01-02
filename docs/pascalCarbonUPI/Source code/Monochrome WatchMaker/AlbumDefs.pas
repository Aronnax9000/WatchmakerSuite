unit AlbumDefs;

interface

	uses MacOSAll, PersonDefs, HIBoxTypes;

	const
		MaxAlbum = 100;	{v1.1 was set to 15, but turbo version said 100}


	type
		Menagerie = RECORD
			Member: ARRAY[1..MaxAlbum] OF HIBoxPtr;
			Place: ARRAY[1..MaxAlbum] OF RECORD
					Page: smallint;
					BoxNo: HIBoxPtr;
				end;
			menagerieSize: integer;
		end;

	var
		AlbumBitMap: ARRAY[0..4] OF GWorldPtr;
		AlbumChanged, Danger, Pastable: Boolean;
		
		{When Albuming is on, Zoomed indicates that we are looking at "quadrant" view, with up to all four}
		{album pages displayed in a 2x2 matrix on the screen.}
		Zoomed: Boolean;
		Album: Menagerie;
		ThisMenagerie: Menagerie;
		AlbumEmpty: Boolean;
end.