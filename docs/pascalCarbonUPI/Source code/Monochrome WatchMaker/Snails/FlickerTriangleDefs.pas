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

PROCEDURE FlickerTriangle (MLoc: Point);
	VAR
		infant: person;
		margcentre, offset: Integer;
	BEGIN
		triangle(r1, r2, r3, b, MLoc);
		concoct(r1, r2, r3, topan, leftan, rightan, infant);
		IF theMode <> triangling THEN
			BEGIN
			END;
		DelayedDrawing := TRUE;
		ZeroMargin := TRUE;
		Develop(infant, MLoc);
    {At this point Margin really does correctly frame the biomorph}
		WITH margin DO
			margcentre := top + (bottom - top) DIV 2;
		offset := margcentre - MLoc.v;
		CursSnap(MyPic, Margin, offset, infant);
	END; {FlickerTriangle}

end.