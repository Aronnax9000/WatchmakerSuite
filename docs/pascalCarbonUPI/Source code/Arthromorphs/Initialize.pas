unit Initialize;
interface
	uses
		{$ifc  undefined THINK_PASCAL}
		MacOSAll,
		{$endc}
		MyGlobals, Ted, Richard, Breeding_Window, Drawing;
	procedure MyInit;

implementation
	{$ifc not undefined THINK_PASCAL}
	var
		DocumentMessage, DocumentCount: integer;
	{$endc}

	procedure MyInit;
	begin
		thickscale := 1;
		wantColor := false;
		sideways := false;
		resizing := false;
		centring := false;
		verticalOffset := 0;
		HorizontalOffset := 0;
		overlap := 1.0; {in case animal has no value}
		NRows := 3;
		NCols := 5; {Defaults}
		NBoxes := NRows * NCols;
		MidBox := 1 + (NRows * NCols) div 2;
		upregion := NewRgn;
		InitBoneyard;
		{$ifc not undefined THINK_PASCAL}
		CountAppFiles(DocumentMessage, DocumentCount);
		if DocumentCount > 0 then {at least one biomorph double-clicked}
			begin
				StartDocument;
			end;
		{$endc}
		startingUp := true;
		MakeAllBodyMutations(true);
		MakeAllAtomMutations(true);
		mutationPressure := zero;
		FocusOfAttention := AnySegment;
		Open_Breeding_Window;
		Breed;
	end;

end.