unit Common_Exhibition;  												{Common}

{Unit name:  Common_Exhibition.p  }
{Function:  Common variables for program specific code.}
{History: 6/10/91 Original by Prototyper.   }

interface

{=======================================================}


	uses
		PCommonExhibition, MacOSAll;


	const
		MutTypeNo = 13;
		GenesHeight = 0;
		maxBoxes = 50;
		PicSizeMax = 3200;
		GeneCount = 26;
		trickle = 10;
		DiceID = 15208;
		HandID = 129;
		DelayTicks = 4;



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

		Lin = record
				StartPt, EndPt: Point;
				Col: integer
			end;
		LinPtr = ^Lin;
		Pic = record
				BasePtr: Ptr;
				MovePtr: LinPtr;
				Origin: Point;
				PicSize: INTEGER;
				PicPerson: person
			end;
		BaseHandle = ^Ptr;

		mode = (preliminary, breeding, albuming, randoming, engineering, drifting, highlighting, PlayingBack, triangling, sweeping);

	var
		Mut: array[1..MutTypeNo] of BOOLEAN;
		theMode: mode;
		upregion: RgnHandle;
		nRows, nCols, nBoxes, nActiveBoxes, MidBox, Special, myPenSize, oldBox: integer;
		PRect, BusinessPart, ThumbStrip, Margin: rect;
		box: array[0..MaxBoxes] of rect;
		centre: array[0..MaxBoxes] of point;
		MyPic: Pic;
		child: array[1..maxBoxes] of person;
		theBiomorph, copiedanimal, PasteBiomorph, ClipBiomorph, ourHero: Person;
		abortFlag, deskScrap, deskPaste, clipFull, YesRevert, YesRhythm, FileMenuEnabled: boolean;
		GeneBox: array[1..26] of rect;
		LastMouse, NowMouse: Point;
		MonsterInterval, MonsterRevert, OldCount, NewCount, AfterAppmenuCount, BeforeAppmenuCount: LongInt;
		TimeOfEvent: LongWord;
		theTime: LongWord;
		MyPicture: PicHandle;
		Dice, Hand, Watch: CursHandle;


	{moved from Main_Exhibition by Alan Canon}
		DoIt: boolean;   														{Flag saying an event is ready}
		code: integer;  														{Determine event type}
		whichWindow: WindowPtr;   										{See which window for event}
		mResult: longint;   													{Menu list and item selected values}
		theMenu, theItem: integer;    										{Menu list and item selected}


	{moved from Biomorphs by Alan Canon}
		zeroMargin: boolean;
		delayedDrawing: boolean;


{=======================================================}


implementation


{=======================================================}

end.    																		{End of the Unit}