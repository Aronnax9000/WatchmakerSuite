unit FlickerTriangleDefs;

interface

	uses 
		
		ConcoctDefs,
		DevelopDefs,
		Globals,
		HIBoxTypes,
		MacOSAll,
		ModeDefs,
		PersonDefs,
		PicDefs,
		TriangleDefs,
		TriangleUtils;

	procedure FlickerTriangle (MLoc: HIPoint; theView: HIViewRef);

implementation

	procedure FlickerTriangle (MLoc: HIPoint; theView: HIViewRef);
	VAR
		infant: HIBoxPtr;
		margcentre, offset: single;
		MyPic: MorphPicPtr;
	begin
		new(infant);
		Triangle(r1, r2, r3, b, MLoc, theView);
		Concoct(r1, r2, r3, topan, leftan, rightan, infant^.denizen);
		IF theMode <> triangling THEN
			begin
				IF infant^.denizen^.gene[9] > 6 THEN
					infant^.denizen^.gene[9] := 6;
				IF infant^.denizen^.SegNoGene > 2 THEN
					infant^.denizen^.SegNoGene := 2;
			end;
		DelayedDrawing := TRUE;
		ZeroMargin := TRUE;
		Develop(infant, MLoc);
		{At this point Margin really does correctly frame the biomorph -- Alan Graefen or Richard Dawkins}
		{No it doesn't. -- ABC}
		WITH infant^.margin DO
			margcentre := size.height / 2;
		offset := margcentre - MLoc.y;
		{CursSnap(MyPic, Margin, offset, infant); FIXME arrange to be drawn}
	end; {FlickerTriangle}

end.