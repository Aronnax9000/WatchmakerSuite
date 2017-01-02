unit BiomorphsDevelop;

interface
	uses MacOSAll, Common_Exhibition, PCommonExhibition, PA_Warning_Alert, BiomorphsDraw;

	function Rainbow: longint; {delivers the number of colours available with the present hardware;}
	function Randint (Max: longint): longint;
	function Odd (i: INTEGER): boolean;
	function RandSwell (Indgene: Swelltype): SwellType;
	function RandLimbType: LimbType;
	function RandLimbFill: LimbFillType;
	procedure Reproduce (parent: person; var child: person);
	function CheckAbort: BOOLEAN;
	function LongintColour (IntColour: Integer): LongInt;
	function IntColour (LongintColour: Longint): Integer;
	{function ChangedColour (oldColour, Amount: integer): integer;}

	procedure delayvelop (var Biomorph: Person; Here: Point);

	procedure develop (var biomorph: person; Here: point);


implementation

	function OldPixelDepth (thisWindowRef: WindowRef): integer;
{Raise 2 to the power PixelDepth in order to obtain the number of colours available with the present hardware}
		var
			CPtr: WindowRef;
	begin
		CPtr := WindowRef(ThisWindowRef);
		{$ifc not undefined THINK_Pascal}
		OldPixelDepth := CPtr^.portPixMap^^.pixelSize;
		{$elsec}
		OldPixelDepth := 8;
		{FIXME}
		{$endc}

	end;
	{$ifc not undefined THINK_Pascal}
	function PixelDepth: integer;
		var
			cd: GDHandle;
			pmh: PixMapHandle;
	begin
		cd := GetMainDevice;
		pmh := cd^^.gdPMap;
		pixelDepth := pmh^^.pixelSize
	end;
	{$elsec}
	function PixelDepth: integer;
	var graf: GrafPtr;
	begin
		GetPort(graf);
		pixelDepth := GetPortPixMap(graf)^^.pixelSize;
	end;
	{$endc}
	function Rainbow: longint; {delivers the number of colours available with the present hardware;}
		var
			j, n: integer;
			a: longint;
	begin
	
		n := PixelDepth;
		if n < 32 then
			begin
				a := 1;
				if n > 0 then
					for j := 1 to n do
						a := a * 2;
				Rainbow := a;
			end
		else
			Rainbow := maxlongint;
	end;

	function randint (Max: longint): longint;
	begin
		randint := 1 + (abs(random) mod max);
	end; {randint}

	function Odd (i: INTEGER): boolean;
	begin
		Odd := 2 * (i div 2) <> i
	end;

	function RandSwell (Indgene: Swelltype): SwellType;
		var
			r: 1..3;
	begin
		case Indgene of
			Shrink: 
				Randswell := Same;
			Same: 
				if randint(2) = 1 then
					Randswell := Shrink
				else
					Randswell := Swell;
			Swell: 
				RandSwell := Same
		end {Cases}
	end; {RandSwell}


	function RandLimbType: LimbType;
		var
			Randy: INTEGER;
	begin
		Randy := RandInt(3);
		case Randy of
			1: 
				RandLimbType := Stick;
			2: 
				RandLimbType := Oval;
			3: 
				RandLimbType := Rectangle;
		end {CASES}
	end; {RandLimbType}

	function RandLimbFill: LimbFillType;
		var
			Randy: INTEGER;
	begin
		Randy := RandInt(2);
		case Randy of
			1: 
				RandLimbFill := Open;
			2: 
				RandLimbFill := Filled;
		end {CASES}
	end; {RandLimbType}
(*
	function LongintColour (IntColour: Integer): LongInt;
		var
			temp: Longint;
	begin
		if IntColour < 0 then
			Temp := 65536 + IntColour
		else
			Temp := IntColour;
		LongintColour := Temp;
	end; {LongintColour}

	function IntColour (LongintColour: Longint): Integer;
		var
			temp: Integer;
	begin
	{JHP: LongintColour is sometimes less that -32768, for example when the}
	{ cursor is moved into the menu bar when the application is in Triangle mode  }
		if LongintColour > 32767 then
			Temp := LongintColour - 65536
		else if LongintColour < -32768 then
			Temp := LongIntColour + 65536
		else
			Temp := LongIntColour;
		IntColour := Temp;
	end; {IntColour}
*)
	procedure reproduce (parent: person; var child: person);

		var
			j: INTEGER;	{Loop control}

		function direction: INTEGER;
		begin
			if randint(2) = 2 then
				direction := child.MutSizegene
			else
				direction := -child.MutSizegene
		end;

		function direction9: INTEGER;
		begin
			if randint(2) = 2 then
				direction9 := 1
			else
				direction9 := -1
		end;

	begin
		child := parent;
		with child do
			begin
				if Mut[7] then
					if Randint(100) < MutProbGene then
						begin
							MutProbGene := MutProbGene + direction9;
							if MutProbGene < 1 then
								MutProbGene := 1;
							if MutProbGene > 100 then
								MutProbGene := 100;
						end;
				if Mut[13] then
					begin
						for j := 1 to 8 do
							if Randint(100) < MutProbGene then
								Gene[j] := Gene[j] + direction;
						if Randint(100) < MutProbGene then
							Gene[9] := Gene[9] + direction9;
						if Gene[9] < 1 then
							Gene[9] := 1;
						if (Gene[9] > 8) then
							Gene[9] := 8;
					end;
				if Mut[10] then
					for j := 1 to 8 do
						if Randint(100) < MutProbGene then
							begin
{IF direction9 = 1 THEN}
{ColorGene[j] := ColorGene[j] + direction9 * colorLeap}
{else}
								ColorGene[j] := ColorGene[j] + direction9;
								if (ColorGene[j] > rainbow) then
									ColorGene[j] := rainbow;
								if (ColorGene[j] < 0) then
									ColorGene[j] := 0;
							end;
				if Mut[8] then
					if Randint(100) < MutProbGene then
						LimbShapeGene := RandLimbType;
				if Mut[9] then
					if Randint(100) < MutProbGene then
						LimbFillGene := RandLimbFill;
				if Mut[11] then
					if Randint(100) < MutProbGene then
						begin
{IF direction9 = 1 THEN}
{BackColorGene := BackColorGene + direction9 * colorLeap}
{else}
							BackColorGene := BackColorGene + direction9;
							if (BackColorGene > rainbow - 1) then
								backColorGene := rainbow - 1;
							if (BackColorGene < 0) then
								BackColorGene := 0;
						end;
				if Mut[12] then
					if RandInt(100) < MutProbGene then
						ThicknessGene := ThicknessGene + direction9;
				if ThicknessGene < 1 then
					ThicknessGene := 1;
				if Mut[1] then
					if RandInt(100) < MutProbGene then
						SegNoGene := SegNoGene + Direction9;
				if SegNoGene < 1 then
					SegNoGene := 1;
				if (Mut[2]) and (SegNoGene > 1) then
					begin
						for j := 1 to 8 do
							if Randint(100) < MutProbGene div 2 then
								dGene[j] := RandSwell(dgene[j]);
						if Randint(100) < MutProbGene div 2 then
							dGene[10] := RandSwell(dgene[10]);
					end;
				if (Mut[1]) and (SegNoGene > 1) then
					if Randint(100) < MutProbGene then
						SegDistGene := SegDistGene + Direction9;
				if Mut[3] then
					if Randint(100) < MutProbGene div 2 then
						if CompletenessGene = Single then
							CompletenessGene := Double
						else
							CompletenessGene := Single;
				if Mut[4] then
					if Randint(100) < MutProbGene div 2 then
						case SpokesGene of
							NorthOnly: 
								SpokesGene := NSouth;
							NSouth: 
								begin
									if Direction9 = 1 then
										SpokesGene := Radial
									else
										SpokesGene := NorthOnly
								end;
							Radial: 
								SpokesGene := NSouth
						end;
				if Mut[5] then
					if Randint(100) < MutProbGene then
						begin
							TrickleGene := Tricklegene + direction9;
							if TrickleGene < 1 then
								TrickleGene := 1
						end;
				if Mut[6] then
					if Randint(100) < MutProbGene then
						begin
							MutSizeGene := MutSizeGene + direction9;
							if MutSizeGene < 1 then
								MutSizeGene := 1
						end;
			end
	end; {reproduce}



	function CheckAbort: BOOLEAN;
		var
			theWindow: WindowRef;
			myEvent: eventRecord;
			MLoc: Point;
			MenuInf: LONGINT;
			WLoc, Menu, Item: INTEGER;
			Ab: BOOLEAN;
			theKeys: KeyMap;
	begin
		Ab := FALSE;
		GetKeys(theKeys);
{IF theKeys[56] THEN Ab:=TRUE;}
   { BEGIN}
{    MLoc:=myEvent.Where;}
{    WLoc:=FindWindow(MLoc,theWindow);}
{    IF WLoc=InMenuBar THEN}
{        BEGIN MenuInf:=MenuSelect(MLoc);}
{        Menu:=HiWord(MenuInf);}
{        Item:=LoWord(MenuInf);}
{        IF (Menu=OperMenu) AND (Item=9) THEN Ab:=TRUE;}
{        END        }
{    END;}
		CheckAbort := Ab
	end; {CheckAbort}

	procedure develop (var biomorph: person; Here: point);
		var
			order, j, x, y, seg, Upextent, Downextent, wid, ht: integer;
			dx, dy: array[0..7] of integer;
			Running: chromosome;
			OldHere, Centre: Point;
			OddOne: BOOLEAN;
			ExtraDistance, IncDistance: INTEGER;
			DummyColour: integer;

		procedure tree (x, y, lgth, dir: integer);
			var
				xnew, ynew, subscript: INTEGER;
		begin
			if dir < 0 then
				dir := dir + 8;
			if dir >= 8 then
				dir := dir - 8;
			xnew := x + lgth * dx[dir] div biomorph.tricklegene;
			ynew := y + lgth * dy[dir] div biomorph.tricklegene;
			with margin do
				begin
					if x < left then
						left := x;
					if (x + biomorph.thicknessGene) > right then
						right := x + biomorph.thicknessGene;
					if (y + biomorph.thicknessGene) > bottom then
						bottom := y + biomorph.thicknessGene;
					if y < top then
						top := y;
					if xnew < left then
						left := xnew;
					if xnew > right then
						right := xnew;
					if ynew > bottom then
						bottom := ynew;
					if ynew < top then
						top := ynew;
				end;
   {IF (x<>xnew) OR (y<>ynew) THEN }
			subscript := (Biomorph.gene[9] - lgth) mod 8 + 1;
			PicLine(MyPic, x, y, xnew, ynew, biomorph.colorgene[subscript]);
			AbortFlag := CheckAbort;
			if (lgth > 1) and (not AbortFlag) then
				begin
					if oddone then
						begin
							tree(xnew, ynew, lgth - 1, dir + 1);
							if lgth < order then
								tree(xnew, ynew, lgth - 1, dir - 1)
						end
					else
						begin
							tree(xnew, ynew, lgth - 1, dir - 1);
							if lgth < order then
								tree(xnew, ynew, lgth - 1, dir + 1)
						end
				end
		end; {tree}

		procedure PlugIn (Gene: chromosome);
		begin
			order := gene[9];
			dx[3] := gene[1];
			dx[4] := gene[2];
			dx[5] := gene[3];
			dy[2] := gene[4];
			dy[3] := gene[5];
			dy[4] := gene[6];
			dy[5] := gene[7];
			dy[6] := gene[8];
			dx[1] := -dx[3];
			dy[1] := dy[3];
			dx[0] := -dx[4];
			dy[0] := dy[4];
			dx[7] := -dx[5];
			dy[7] := dy[5];
			dx[2] := 0;
			dx[6] := 0;
		end; {PlugIn}

	begin {develop}
		AbortFlag := FALSE;
		if zeromargin then
			with margin do
				begin
					left := Here.h;
					right := Here.h;
					right := Here.h;
					top := Here.v;
					bottom := Here.v;
				end;
		Centre := Here;
		PlugIn(Biomorph.gene);
		ZeroPic(MyPic, Here);
		with biomorph do
			begin
				if SegNoGene < 1 then
					SegNoGene := 1;
				if dGene[10] = Swell then
					Extradistance := Tricklegene
				else if dGene[10] = Shrink then
					Extradistance := -Tricklegene
				else
					ExtraDistance := 0;
				Running := gene;
				IncDistance := 0;
				for seg := 1 to SegNoGene do
					begin
						OddOne := odd(seg);
						if seg > 1 then
							begin
								OldHere := Here;
								Here.v := Here.v + (SegDistGene + IncDistance) div Tricklegene;
								IncDistance := IncDistance + ExtraDistance;
								dummycolour := 100;
								PicLine(MyPic, OldHere.h, Oldhere.v, Here.h, Here.v, dummycolour);
								for j := 1 to 8 do
									begin
										if dGene[j] = Swell then
											Running[j] := Running[j] + Tricklegene;
										if dGene[j] = Shrink then
											Running[j] := Running[j] - Tricklegene;
									end;
            {IF dGene[9]=Swell THEN Running[9]:=Running[9]+1;}
{            IF dGene[9]=Shrink THEN Running[9]:=Running[9]-1;}
								if Running[9] < 1 then
									Running[9] := 1;
								PlugIn(Running)
							end;
						tree(Here.h, Here.v, order, 2);
					end;
			end;
		if not AbortFlag then
			with biomorph do
				with margin do
					begin
						if Centre.h - left > right - Centre.h then
							right := Centre.h + (Centre.h - left)
						else
							left := Centre.h - (right - Centre.h);
						Upextent := Centre.v - top; {can be zero if biomorph goes down}
						Downextent := bottom - Centre.v;
						if ((SpokesGene = NSouth) or (SpokesGene = Radial)) or (TheMode = Engineering) then
							{Obscurely necessary to cope with erasing last rect in Manipulation}
							begin
								if UpExtent > DownExtent then
									bottom := Centre.v + UpExtent
								else
									top := Centre.v - DownExtent
							end;
						if SpokesGene = Radial then
							begin
								wid := right - left;
								ht := bottom - top;
								if wid > ht then
									begin
										top := centre.v - wid div 2 - 1;
										bottom := centre.v + wid div 2 + 1
									end
								else
									begin
										left := centre.h - ht div 2 - 1;
										right := centre.h + ht div 2 + 1
									end
							end
					end;
		MyPic.PicPerson := biomorph;
		if not DelayedDrawing then
			DrawPic(MyPic, Centre, biomorph);
	end; {develop}
	procedure delayvelop (var Biomorph: Person; Here: Point);
		var
			margcentre, offset: INTEGER;
			OffCentre: Point;
	begin
		DelayedDrawing := TRUE;
		Zeromargin := TRUE;
		develop(Biomorph, Here);
		DelayedDrawing := FALSE;
		with margin do
			margcentre := top + (bottom - top) div 2;
		offset := margcentre - Here.v;
		with Margin do
			begin
				Top := Top - Offset;
				Bottom := Bottom - Offset
			end;
		with OffCentre do
			begin
				h := Here.h;
				v := Here.v - offset;
			end;
		DrawPic(MyPic, offcentre, Biomorph);
		HIWindowFlush(BreedWin);

	end; {Delayvelop}

end.