unit PersonDefs;

interface

uses MacOSAll;

	const
		MutTypeNo = 13;
		GenesHeight = 0;
		{PicSizeMax = 3200;}
		GeneCount = 26;
		trickle = 10;
		{DiceID = 15208;}
		{HandID = 129;}
		{DelayTicks = 4;}



	type
		SwellType = (Swell, Same, Shrink);
		HorizPos = (LeftThird, MidThird, RightThird);
		VertPos = (TopRung, MidRung, BottomRung);
		SmallNumber = -1..1;
		chromosome = array[1..9] of INTEGER;
		Compass = (NorthSouth, EastWest);
		CompletenessType = (Single, Double);
		SpokesType = (NorthOnly, NSouth, Radial);
		LimbType = (Stick, Oval, Rectangle);
		LimbFillType = (Open, Filled);
		person = record
				gene: chromosome;
				colorgene: array[1..8] of Longint;{index in clut}
				BackColorGene: LongInt;{index in clut}
				dgene: array[1..10] of SwellType;
				SegNoGene: INTEGER;
				SegDistGene: INTEGER;
				CompletenessGene: CompletenessType;
				SpokesGene: SpokesType;
				tricklegene, mutsizegene, mutprobgene: INTEGER;
				LimbShapeGene: LimbType;
				LimbFillGene: LimbFillType;
				ThicknessGene: INTEGER;
				bioPicture: picHandle;
			end;

	

implementation


end.