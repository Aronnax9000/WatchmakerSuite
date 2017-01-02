unit RandSwellDef;

interface

uses 
	MacOSAll, 
	Miscellaneous,
	PersonDefs;

function RandSwell (Indgene: Swelltype): SwellType;

implementation

function RandSwell (Indgene: Swelltype): SwellType;
	begin
		CASE Indgene OF
			Shrink: 
				Randswell := Same;
			Same: 
				IF randint(2) = 1 THEN
					Randswell := Shrink
				ELSE
					Randswell := Swell;
			Swell: 
				RandSwell := Same
		end {Cases}
	end; {RandSwell}
	
end.