unit PersonDefs;

interface

	uses MacOSAll;
	
	const
		YardSize = 10000;
		miniSize = 200;
		scale = 10;

	type
		Pressure = (positive, zero, negative);
		Concentration = (FirstSegmentOnly, LastSegmentOnly, AnySegment);
		AtomKind = (Free, AnimalTrunk, AnimalJoint, AnimalClaw, SectionTrunk, 
			SectionJoint, SectionClaw, SegmentTrunk, SegmentJoint, SegmentClaw, Joint, Claw);
		
		Atom = record
			Kind: AtomKind;
			Height: single;		{also used for Thickness of a Joint}
			Width: single;		{also used for Length of a Joint}
			Angle: single;		{also used in an AnimalTrunk to store the number of atoms in the animal}
						{also used in SectionTrunk to store the Overlap of segments}
						{also used in SegmentTrunk to store the rank number of the segment}
				
			{where to look in the BoneYard for the next atom. 0 means end of chain}
			{Also used in AnimalTrunk to store Gradient gene, slightly more or less than 100.  Treat as Percentage}
			NextLikeMe: SInt16;
				
			{where to look in the BoneYard for the next atom. 0 means end of chain}
			FirstBelowMe: SInt16;
		end;
			
		AtomPtr = ^Atom;
		AtomHdl = ^AtomPtr;
		
		AtomArray = array[1..Yardsize] of AtomHdl;		{for the real thing, use 2500}
		SmallAtomArray = array[1..miniSize] of AtomHdl;		{Just holds one animal, compactly}
		AnimalStarts = array[0..MaxBoxes] of integer;

		LevelLocs = array[1..10] of integer;		{stores indexes of where we are when travelling through an animal}
		{to copy it.  1 spare, 2 AnimalTrunk, 3 AnimalJoint, 4  SectionTrunk, 5 SectionJoint, 6 SegmentTrunk, }
			{7 SegmentJoint, 8 Joint, 9 Claw, 10 spare}
		KindsData = array[AtomKind] of integer;		{a number for each kind of Atom}
		CumParams = array[1..9] of real;		{where the AnimalTrunk.Width is multiplied by SegmentTrunk.Width}
		
		Person = Atom;
		PersonPtr = ^Person;

implementation


end.