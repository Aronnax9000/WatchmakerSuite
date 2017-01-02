{Arthromorphs   by Richard Dawkins and Ted Kaehler}
{Ted's initial version: 25 Nov 90}
{Current version: 8 Dec 90}
{Since we both are confused by handles and pointers in Pascal, this does not use any of either!}

{There is a Record called Atom that holds a little part of an animal.  It has fields for a Height, }
{a Width, and an Angle.}
{	When it is used to describe a Segment, Height and Width are for the oval,}
{		and Angle is not used}
{	When it is used to describe a Joint, Height is the thickness of the leg-part,}
{		Width is the length, and Angle is the angle from the previous joint}
{	When it is used to describe a Claw,  Height is the thickness of the claw-part,}
{		Width is the length, and Angle is the between the claw halves}

{Remember that the true Joint length is the multiplication of all the factors:}
{The Animal's joint length, this Section's joint length, this Segment's joint length, and the Joint's own joint length.}
{Thus a Segment actually has three parts: its factor for Segment size, its }
{	factor for Joint size, and its factor for Claw size.  Each of these are Atoms.  Thus a}
{	Segment has three Atoms.  They are distinguished by having different kinds: SegmentTrunk,}
{	SegmentJoint, and SegmentClaw.}
{An Animal-record also has three Atoms in it AnimalTrunk, AnimalJoint, and AnimalClaw.}

{How are Atoms hooked together?  Here is a sample Animal.  Each line is an Atom, but I don't}
{	show the values inside it, like Height: 20 Width: 30, etc.}
{AnimalTrunk}
{	AnimalJoint}
{	AnimalClaw}
{		SectionTrunk}
{			SectionJoint}
{			SectionClaw}
{				SegmentTrunk}
{					SegmentJoint}
{					SegmentClaw}
{						Joint}
{						Joint}
{						Joint}
{							Claw}
{				SegmentTrunk}
{					SegmentJoint}
{					SegmentClaw}
{						Joint}
{						Joint}
{						Joint}
{							Claw}

{A Section sets the tone for all segments within it:  Head, Thorax, Abdomen are sections}

{In the above set of Atoms, there are two fields for connecting Atoms together.}
{	NextLikeMe  hooks the atom to the next atom on the same level.}
{	FirstBelowMe  hooks the atom to the first atom on a lower level.}
{Look at the diagram above.  When an atom points to another with NextLikeMe, they}
{have the same level of indentation.  When an atom points to another with }
{FirstBelowMe, the atom is indented one more level.}
{The first SegmentTrunk points way down to the second SegmentTrunk with NextLikeMe.}
{The Joints point to the next with NextLikeMe.  However, the AnimalClaw}
{points to SegmentTrunk using FirstBelowMe.  Note that the three atoms that}
{make up an Animal are split.  AnimalJoint is pointed to with FirstBelowMe even}
{though it is part of the animal description.  I had to do this so that AnimalTrunk could use its}
{NextLikeMe to point at the next animal.  Likewise with Segments.}

{All atoms are stored in a big Array called the BoneYard.  You find an atom}
{by knowing its index (the integer that is its place in the array).  The two "pointers" NextLikeMe }
{and FirstBelowMe are not pointers at all, but simply integers.}
{An indivudual Animal can have its atoms spread out all over the BoneYard, but }
{each atom in it holds the index of the next atom in it.  Thus we can walk down }
{the parts of an animal very easily.  Atoms that are not being used are labelled Free.}


unit MyGlobals;
interface
{$ifc  undefined THINK_PASCAL}
uses MacOSAll;
{$endc}
	const
		MaxBoxes = 49;
		DelayTicks = 3;
		rgbRedColor: RGBColor = (red: $FFFF; green: $0000; blue: $0000);
		rgbGreenColor: RGBColor = (red: $0000; green: $FFFF; blue: $0000);
		windowEventDrawContentType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowDrawContent);
		windowEventCloseType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowClose);
		windowEventBoundsChangedType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowBoundsChanged);
		windowEventResizeCompletedType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowResizeCompleted);
		windowEventHandleContentClickType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowHandleContentClick);
		windowEventHandleUpdateType: EventTypeSpec = (eventClass: kEventClassWindow; eventKind: kEventWindowUpdate);
		windowEventControlHitType: EventTypeSpec = (eventClass: kEventClassControl; eventKind: kEventControlHit);
		commandEventType: EventTypeSpec = (eventClass: kEventClassCommand; eventKind: kEventCommandProcess);

	type
		Pressure = (positive, zero, negative);
		Concentration = (FirstSegmentOnly, LastSegmentOnly, AnySegment);

	var
		NRows, NCols: LongInt;
		MidBox: integer;
		Special, NBoxes, Hot: integer;
		Prect: rect;
		box: array[0..MaxBoxes] of rect;
		upregion: RgnHandle;
		centre: array[0..MaxBoxes] of point;
		
		VerticalOffset: integer; 
		HorizontalOffset: integer; 
		OldVerticalOffset: integer; 
		OldHorizontalOffset: integer; 
		thickscale: integer;
		
		wantColor: Boolean; 
		sideways: Boolean; 
		centring: Boolean; 
		resizing: Boolean; 
		startingUp: boolean;
		
		LegsMut: Boolean; 
		ClawsMut: Boolean; 
		AnimalTrunkMut: Boolean; 
		AnimalLegsMut: Boolean; 
		AnimalClawsMut: Boolean;		
		SectionTrunkMut: Boolean; 
		SectionLegsMut: Boolean; 
		SectionClawsMut: Boolean; 
		SegmentTrunkMut: Boolean; 
		SegmentLegsMut: Boolean; 
		SegmentClawsMut: Boolean;
		WidthMut: Boolean; 
		HeightMut: Boolean; 
		AngleMut: Boolean; 
		DuplicationMut: Boolean; 
		DeletionMut: Boolean; 
		AgreeToExit: boolean;
		MutationPressure: pressure;
		FocusOfAttention: concentration;
		Overlap: real;
		
		{$ifc not undefined THINK_PASCAL}
		BreedingWindow: WindowPtr;
		{$elsec}
		BreedingWindow: WindowRef;
		{$endc}
		{$ifc not undefined THINK_PASCAL}
		GenomeWindow : WindowPtr;   		{Window pointer}
		{$elsec}
		GenomeWindow : WindowRef;   		{Window pointer}
		{$endc}
		doneFlag: boolean; 			{Exit program flag}

	function booleanToInt(arg: boolean):integer;
implementation
	function booleanToInt(arg: boolean):integer;
	begin
		if arg then booleanToInt := 1 else booleanToInt := 0;
	end;

end.