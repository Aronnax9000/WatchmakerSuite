unit Richard;
interface
	uses
		{$ifc undefined THINK_PASCAL}
		MacOSALL,
		{$endc} 
		MyGlobals, Ted;
		
	procedure MakeAllBodyMutations (State: boolean);
	procedure MakeAllAtomMutations (State: boolean);
	procedure PrintMiddle;

implementation

	const
		{Column width in FontInfo.widmax multiples}
		columnWidth = 4;

	var
		numFormatStr: NumFormatString;
		numParts: NumberParts;

	procedure InitializeNumberFormat;
	var
		itlHandle: Handle;
		formatString: Str255;
		formatStatus: FormatResultType;
		offset: longint;
		stringLength: longint;

	begin
		GetIntlResourceTable(smSystemScript, smNumberPartsTable, itlHandle, offset, stringLength);
		if itlHandle <> nil then
		begin
			BlockMoveData(Ptr(ord4(itlHandle^) + offset), @numParts, stringLength);
			ReleaseResource(itlHandle);
			formatString := '#####.^^';
			formatStatus := StringToFormatRec(formatString, numParts, numFormatStr);
		end;
	end;

	procedure MakeAllBodyMutations (State: boolean);
	begin
		LegsMut := State;
		ClawsMut := State;
		AnimalTrunkMut := State;
		AnimalLegsMut := State;
		AnimalClawsMut := State;
		SectionTrunkMut := State;
		SectionLegsMut := State;
		SectionClawsMut := State;
		SegmentTrunkMut := State;
		SegmentLegsMut := State;
		SegmentClawsMut := State;
	end;

	procedure MakeAllAtomMutations (State: boolean);
	begin
		WidthMut := State;
		HeightMut := State;
		AngleMut := State;
		DuplicationMut := State;
		DeletionMut := State;
	end;

	procedure ScootRectRight(r: RectPtr; distance: UInt16; metrics: FontInfo);
	begin
			r^.left := r^.left + distance * metrics.widMax;
			r^.right := r^.right + distance * metrics.widMax;

	end;

	procedure ScootRectDownItsOwnHeight(r: RectPtr);
	var 
		height: UInt16;
	begin

			height := r^.bottom - r^.top;
			r^.top := r^.top + height;
			r^.bottom := r^.bottom + height;

	end;
	
	function SingleToString(numberToConvert: single): Str255;
	var
		numberAsDouble: double;
		numberAsString: str255;
		numberX80: Extended80;
		formatStatus: FormatResultType;
	begin
		numberAsDouble := numberToConvert;
		dtox80(numberAsDouble, numberX80);
		formatStatus := ExtendedToString(numberX80, numFormatStr, numParts, numberAsString);
		SingleToString := numberAsString;
	end;


	procedure WriteInRectAndScootRight(sTemp: Str255; r: RectPtr; metrics:FontInfo; rightJustify: boolean);
	begin
		if rightJustify then
		TETextBox(Pointer(ord(@sTemp) + 1), length(sTemp), r^, teJustRight)
		else
		TETextBox(Pointer(ord(@sTemp) + 1), length(sTemp), r^, teJustLeft);
		ScootRectRight(r, columnWidth, metrics);
	end;


	procedure PrintAt (this: Atom; r: RectPtr; metrics: FontInfo);
	var
		sTemp: Str255;
		colRect: Rect;
	begin
		with this do
			begin
				{$ifc not undefined THINK_PASCAL}
				write(Height : 10 : 2, Width : 10 : 2, Angle : 10 : 2, '     ');
				case kind of
					AnimalTrunk: 
						write('AnimalTrunk');
					AnimalJoint: 
						write('    AnimalJoint');
					AnimalClaw: 
						write('    AnimalClaw');
					SectionTrunk: 
						write('        SectionTrunk');
					SectionJoint: 
						write('            SectionJoint');
					SectionClaw: 
						write('            SectionClaw');
					SegmentTrunk: 
						begin
							SegmentCounter := SegmentCounter + 1;
							write('                SegmentTrunk', SegmentCounter);
						end;
					SegmentJoint: 
						write('                    SegmentJoint');
					SegmentClaw: 
						write('                    SegmentClaw');
					Joint: 
						write('                        Joint');
					Claw: 
						write('                        Claw');
				end; {cases}
				writeln;
				{$elsec}
				colRect := r^;
				WriteInRectAndScootRight(SingleToString(height), @colRect, metrics, true);
				WriteInRectAndScootRight(SingleToString(width), @colRect, metrics, true);
				WriteInRectAndScootRight(SingleToString(angle), @colRect, metrics, true);
				ScootRectRight(@colRect, 1, metrics);
				colRect.right := colRect.right + metrics.widmax * 80;

				case kind of
					AnimalTrunk: 
						sTemp := 'AnimalTrunk';
					AnimalJoint: 
						sTemp := '    AnimalJoint';
					AnimalClaw: 
						sTemp := '    AnimalClaw';
					SectionTrunk: 
						sTemp := '        SectionTrunk';
					SectionJoint: 
						sTemp := '            SectionJoint';
					SectionClaw: 
						sTemp := '            SectionClaw';
					SegmentTrunk: 
						begin
							SegmentCounter := SegmentCounter + 1;
							NumToString(SegmentCounter, sTemp);
							sTemp := '                SegmentTrunk ' + sTemp;
						end;
					SegmentJoint: 
						sTemp := '                    SegmentJoint';
					SegmentClaw: 
						sTemp := '                    SegmentClaw';
					Joint: 
						sTemp := '                        Joint';
					Claw: 
						sTemp := '                        Claw';
				end; {cases}
				WriteInRectAndScootRight(sTemp, @colRect, metrics, false);
				ScootRectDownItsOwnHeight(r);
				{$endc}
			end
	end; {PrintAt}

	procedure Print (which: integer; r: RectPtr; metrics: FontInfo);
   {Print this animal}
   {Recursively step through the animal}
		var
			this: Atom;
	begin
		this := BoneYard[which]^^;
		with this do
			begin
				if kind <> free then
					PrintAt(this, r, metrics);
				if FirstBelowMe <> 0 then
					Print(FirstBelowMe, r, metrics);
				if (NextLikeMe <> 0) and (kind <> AnimalTrunk) then
					Print(NextLikeMe, r, metrics);
			end
	end;

	{FIXME Uses Think Pascal text window}
	procedure PrintMiddle;
		var
			sub: integer;
			r: rect;
			metrics: FontInfo;
			columnRect: Rect;
			
	begin
		{$ifc not undefined THINK_PASCAL}
		r := Prect;
		r.top := 60;
		SetTextRect(r);
		showtext;
		rewrite(output);
		writeln('Height ' : 10, 'Width' : 10, 'Angle' : 10);
		{$elsec}
		InitializeNumberFormat;
		GetFontInfo(metrics);
		SetRect(r, 0, 0, metrics.widMax * columnWidth,  metrics.ascent + metrics.descent + metrics.leading);
		columnRect := r;
		WriteInRectAndScootRight('Height', @columnRect, metrics, true);
		WriteInRectAndScootRight('Width', @columnRect, metrics, true);
		WriteInRectAndScootRight('Angle/Segs', @columnRect, metrics, true);
		ScootRectDownItsOwnHeight(@r);
		{$endc}
		sub := BreedersChoice[MidBox];
		SegmentCounter := 0;
		if sub > 0 then
			if BoneYard[sub]^^.kind = AnimalTrunk then
				Print(BreedersChoice[MidBox], @r, metrics);
	end;

end.