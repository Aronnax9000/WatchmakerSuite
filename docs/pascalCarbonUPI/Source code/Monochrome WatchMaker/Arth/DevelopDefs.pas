unit DevelopDefs;

interface

	uses 
		 
		DrawPicDefs,
		Globals, 
		MacOSAll,
		Miscellaneous,
		ModeDefs,
		PicDefs,
		PersonDefs;


	procedure Develop (VAR biomorph: person; Here: point);
	procedure Delayvelop (VAR Biomorph: Person; Here: Point);

implementation

procedure Develop (VAR biomorph: person; Here: point);
	begin
	end; {develop}

{FIXME bring in simple-minded Delayvelop from Snails}
procedure Delayvelop (VAR Biomorph: Person; Here: Point);

	begin

	end; {Delayvelop}
end.