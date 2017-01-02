unit FlickerTriangleDefs;

interface

	uses 
		
		ConcoctDefs,
		DevelopDefs,
		Globals,
		MacOSAll,
		ModeDefs,
		PersonDefs,
		TriangleDefs,
		TriangleUtils;

	procedure FlickerTriangle (MLoc: Point);

implementation

	procedure FlickerTriangle (MLoc: Point);
	VAR
		infant: person;
		margcentre, offset: Integer;
	begin
		Triangle(r1, r2, r3, b, MLoc);
		Concoct(r1, r2, r3, topan, leftan, rightan, infant);
		IF theMode <> triangling THEN
			begin
				IF infant.gene[9] > 6 THEN
					infant.gene[9] := 6;
				IF infant.SegNoGene > 2 THEN
					infant.SegNoGene := 2;
			end;
		DelayedDrawing := TRUE;
		ZeroMargin := TRUE;
		Develop(infant, MLoc);
		{At this point Margin really does correctly frame the biomorph -- Alan Graefen or Richard Dawkins}
		{No it doesn't. -- ABC}
		WITH margin DO
			margcentre := top + (bottom - top) DIV 2;
		offset := margcentre - MLoc.v;
		CursSnap(MyPic, Margin, offset, infant);
	end; {FlickerTriangle}

end.