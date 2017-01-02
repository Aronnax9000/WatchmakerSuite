unit PersonDefs;

interface

uses MacOSAll;

const
	WorryMax = 32767; {Was 4095 in Classic version}
	Gene1 = 1;
	Gene2 = 2;
	Gene3 = 3;
	Gene4 = 4;
	Gene5 = 5;
	Gene6 = 6;
	Gene7 = 7;
	Gene8 = 8;
	Gene9 = 9;

	DGene1 = 1;
	DGene2 = 2;
	DGene3 = 3;
	DGene4 = 4;
	DGene5 = 5;
	DGene6 = 6;
	DGene7 = 7;
	DGene8 = 8;
	DGene9 = 9;
	DGene10 = 10;

type
	SwellType = (Swell, Same, Shrink);
	Compass = (NorthSouth, EastWest);
	CompletenessType = (CompletenessTypeSingle, CompletenessTypeDouble);
	SpokesType = (NorthOnly, NSouth, Radial);
	
	chromosome = ARRAY[1..9] OF Integer;

	PersonPtr = ^Person;

	Person = RECORD
			Gene: chromosome;
			dGene: ARRAY[1..10] OF SwellType;
			SegNoGene: SInt16;
			SegDistGene: SInt16;
			CompletenessGene: CompletenessType;
			SpokesGene: SpokesType;
			TrickleGene: SInt16; 
			MutSizeGene: SInt16;
			MutProbGene: SInt16;
		end;		

	PersonNodePtr = ^PersonNode;
	
	PersonNode = RECORD
		nextSibling: PersonNodePtr;
		biomorph: PersonPtr;
	end;
	
	function PersonGetGene(targetBiomorph: PersonPtr; geneIndex: integer): integer;
	function PersonSetGene(targetBiomorph: PersonPtr; geneIndex: integer; value: integer): OSStatus;
	function PersonAddToGene(targetBiomorph: PersonPtr; geneIndex: integer; value: integer): OSStatus;

	function PersonGetDGene(targetBiomorph: PersonPtr; geneIndex: integer): SwellType;
	function PersonSetDGene(targetBiomorph: PersonPtr; geneIndex: integer; value: SwellType): OSStatus;

	function PersonGetSegNoGene(targetBiomorph: PersonPtr): SInt16;
	function PersonSetSegNoGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
	function PersonAddToSegNoGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;

	function PersonGetSegDistGene(targetBiomorph: PersonPtr): SInt16;
	function PersonSetSegDistGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
	function PersonAddToSegDistGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;

	function PersonGetCompletenessGene(targetBiomorph: PersonPtr): CompletenessType;
	function PersonSetCompletenessGene(targetBiomorph: PersonPtr; value: CompletenessType): OSStatus;
	function PersonToggleCompletenessGene(targetBiomorph: PersonPtr): OSStatus;

	function PersonGetSpokesGene(targetBiomorph: PersonPtr): SpokesType;
	function PersonSetSpokesGene(targetBiomorph: PersonPtr; value: SpokesType): OSStatus;

	function PersonGetTrickleGene(targetBiomorph: PersonPtr): SInt16;
	(* If the value is less than one, set to one, else value. *)
	function PersonSetTrickleGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus; 
	function PersonAddToTrickleGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus; 

	function PersonGetMutSizeGene(targetBiomorph: PersonPtr): SInt16;
	function PersonSetMutSizeGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
	function PersonAddToMutSizeGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;

	function PersonGetMutProbGene(targetBiomorph: PersonPtr): SInt16;
	function PersonSetMutProbGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
	function PersonAddToMutProbGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;


implementation

	function PersonGetGene(targetBiomorph: PersonPtr; geneIndex: integer): integer;
		begin
			PersonGetGene := targetBiomorph^.gene[geneIndex];
		end;

	function PersonSetGene(targetBiomorph: PersonPtr; geneIndex: integer; value: integer): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.gene[geneIndex] := value;
			PersonSetGene := status;
		end;

	function PersonAddToGene(targetBiomorph: PersonPtr; geneIndex: integer; value: integer): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.gene[geneIndex] := targetBiomorph^.gene[geneIndex] + value;
			PersonAddToGene := status;
		end;

	function PersonGetDGene(targetBiomorph: PersonPtr; geneIndex: integer): SwellType;
		begin
			PersonGetDGene := targetBiomorph^.dGene[geneIndex];
		end;

	function PersonSetDGene(targetBiomorph: PersonPtr; geneIndex: integer; value: SwellType): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.dGene[geneIndex] := value;
			PersonSetDGene := status;
		end;

	function PersonGetSegNoGene(targetBiomorph: PersonPtr): SInt16;
		begin
			PersonGetSegNoGene := targetBiomorph^.SegNoGene;
		end;

	function PersonSetSegNoGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
		var
			status: OSStatus = noerr;
		begin
	IF PersonGetSegNoGene(targetBiomorph) < 1 THEN
				PersonSetSegNoGene(targetBiomorph, 1);
			targetBiomorph^.SegNoGene := value;
			PersonSetSegNoGene := status;
		end;

	function PersonAddToSegNoGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.SegNoGene := targetBiomorph^.SegNoGene + value;
			PersonAddToSegNoGene := status;
		end;

	function PersonGetSegDistGene(targetBiomorph: PersonPtr): SInt16;
		begin
			PersonGetSegDistGene := targetBiomorph^.SegNoGene;
		end;
		
	function PersonSetSegDistGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.SegDistGene := value;
			PersonSetSegDistGene := status;
		end;

	function PersonAddToSegDistGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.SegDistGene := targetBiomorph^.SegDistGene + value;
			PersonAddToSegDistGene := status;
		end;

	function PersonGetCompletenessGene(targetBiomorph: PersonPtr): CompletenessType;
		begin
			PersonGetCompletenessGene := targetBiomorph^.CompletenessGene;
		end;
		
	function PersonSetCompletenessGene(targetBiomorph: PersonPtr; value: CompletenessType): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.CompletenessGene := value;
			PersonSetCompletenessGene := status;
		end;

	function PersonToggleCompletenessGene(targetBiomorph: PersonPtr): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			IF targetBiomorph^.CompletenessGene = CompletenessTypeSingle THEN
				targetBiomorph^.CompletenessGene := CompletenessTypeDouble
			ELSE
				targetBiomorph^.CompletenessGene := CompletenessTypeSingle;
			PersonToggleCompletenessGene := status;
		end;

	function PersonGetSpokesGene(targetBiomorph: PersonPtr): SpokesType;
		begin
			PersonGetSpokesGene := targetBiomorph^.SpokesGene;
		end;
		
	function PersonSetSpokesGene(targetBiomorph: PersonPtr; value: SpokesType): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.SpokesGene := value;
			PersonSetSpokesGene := status;
		end;

	function PersonGetTrickleGene(targetBiomorph: PersonPtr): SInt16; 
		begin
			PersonGetTrickleGene := targetBiomorph^.TrickleGene;
		end;
		
	function PersonSetTrickleGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus; 
		var
			status: OSStatus = noerr;
		begin
			IF value < 1 THEN
				targetBiomorph^.TrickleGene := 1
			else
				targetBiomorph^.TrickleGene := value;

			PersonSetTrickleGene := status;
		end;

	function PersonAddToTrickleGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus; 
		begin
			PersonAddToTrickleGene := PersonSetTrickleGene(targetBiomorph, targetBiomorph^.TrickleGene + value);
		end;

	function PersonGetMutSizeGene(targetBiomorph: PersonPtr): SInt16;
		begin
			PersonGetMutSizeGene := targetBiomorph^.MutSizeGene;
		end;
		
	function PersonSetMutSizeGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			IF value < 1 THEN
				targetBiomorph^.MutSizeGene := 1
			else
				targetBiomorph^.MutSizeGene := value;
			PersonSetMutSizeGene := status;
		end;

	function PersonAddToMutSizeGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
		begin
			PersonAddToMutSizeGene := PersonSetMutSizeGene(targetBiomorph, targetBiomorph^.MutSizeGene + value);
		end;

	function PersonGetMutProbGene(targetBiomorph: PersonPtr): SInt16;
		begin
			PersonGetMutProbGene := targetBiomorph^.MutProbGene;
		end;

	function PersonSetMutProbGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.MutProbGene := value;
			PersonSetMutProbGene := status;
		end;

	function PersonAddToMutProbGene(targetBiomorph: PersonPtr; value: SInt16): OSStatus;
		var
			status: OSStatus = noerr;
		begin
			targetBiomorph^.MutProbGene := targetBiomorph^.MutProbGene + value;
			PersonAddToMutProbGene := status;
		end;

end.