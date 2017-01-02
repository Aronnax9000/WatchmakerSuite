unit FossilsExistDefs;

interface

uses MacOSAll, Globals;
function FossilsExist(forkNum: SInt16): Boolean;

implementation

function FossilsExist(forkNum: SInt16): Boolean;
{replace, but not precisely mimic, Turbo's built-in function}
	VAR
		Err: OSStatus;
		filePos: LongInt;
	begin
		Err := GetFPos(slides, FilePos);
		FossilsExist := FilePos > 0
 
	end; {FossilsExist}

 
 end.