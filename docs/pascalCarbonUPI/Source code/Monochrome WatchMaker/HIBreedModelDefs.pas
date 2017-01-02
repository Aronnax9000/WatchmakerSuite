unit HIBreedModelDefs;

interface

uses MacOSAll, HIBoxTypes;

	function HIBreedModelGetGeneBoxes(breedModel: HIBreedModelPtr; var geneBoxes: Pointer): OSStatus;
	function HIBreedModelGetView(breedModel: HIBreedModelPtr; var theView: HIViewRef): OSStatus;
	function HIBreedModelGetBoxes(breedModel: HIBreedModelPtr; var theBoxes: Pointer): OSStatus;

implementation

	function HIBreedModelGetBoxes(breedModel: HIBreedModelPtr; var theBoxes: Pointer): OSStatus;
		var status: OSStatus = noerr;
		begin
			theBoxes := breedModel^.boxes;
			HIBreedModelGetBoxes := status;
		end;

	function HIBreedModelGetGeneBoxes(breedModel: HIBreedModelPtr; var geneBoxes: Pointer): OSStatus;
		var status: OSStatus = noerr;
		begin
			geneBoxes := breedModel^.geneBoxes;
			HIBreedModelGetGeneBoxes := status;

		end;

	function HIBreedModelGetView(breedModel: HIBreedModelPtr; var theView: HIViewRef): OSStatus;
		var status: OSStatus = noerr;
		begin
			theView := breedModel^.view;
			HIBreedModelGetView := status;
		end;

end.
