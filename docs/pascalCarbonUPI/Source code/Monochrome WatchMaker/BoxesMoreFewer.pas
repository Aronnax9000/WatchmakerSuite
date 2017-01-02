unit BoxesMoreFewer;

interface

	uses Globals, BoxesDefs, HIBoxTypes, MacOSAll;
	procedure DoRowMore(boxes: HIBoxesPtr);
procedure DoRowLess(boxes: HIBoxesPtr);
procedure DoColMore(boxes: HIBoxesPtr);
procedure DoColLess(boxes: HIBoxesPtr);

implementation

procedure DoRowMore(boxes: HIBoxesPtr);
	begin
		Nrows := Nrows + 2;
		IF NRows * Ncols > MaxBoxes THEN
			NRows := NRows - 2;
		EraseRect(Prect);
		DoShowBoxes(true);
		IF special <> nil THEN
			{Delayvelop(Special, boxes^.midbox^.midpoint);}
		OldSpecial := nil;
		{DoBreed(boxes);}
	end; {DoRowMore}

procedure DoRowLess(boxes: HIBoxesPtr);
	begin
		IF Nrows > 1 THEN
			Nrows := Nrows - 2;
		EraseRect(Prect);
		DoShowBoxes(true);
		IF special <> nil THEN
			{Delayvelop(Special, boxes^.midbox^.midpoint);}
		OldSpecial := nil;
		{DoBreed(boxes);}
	end; {DoRowLess}

procedure DoColMore(boxes: HIBoxesPtr);
	begin
		Ncols := Ncols + 2;
		IF NRows * Ncols > MaxBoxes THEN
			NCols := NCols - 2;
		EraseRect(Prect);
		DoShowBoxes(true);
		IF special <> nil THEN
			{Delayvelop(Special, boxes^.midbox^.midpoint);}
		OldSpecial := nil;
		{DoBreed(boxes);}
	end; {DoColMore}

procedure DoColLess(boxes: HIBoxesPtr);
	begin
		IF NCols > 1 THEN
			NCols := Ncols - 2;
		EraseRect(Prect);
		DoShowBoxes(true);
		IF special <> nil THEN
			{Delayvelop(Special, boxes^.midbox^.midpoint);}
		OldSpecial := nil;
		{DoBreed(boxes);}
	end; {DoColLess}



end.
