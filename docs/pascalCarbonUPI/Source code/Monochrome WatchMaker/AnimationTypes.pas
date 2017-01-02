unit AnimationTypes;

interface

	uses MacOSAll;

	type
		(* 
			Cues to the ControlDraw event handler for a box. 
			The handler is responsible for setting the AnimationState back to idle
			once the appropriate drawing has been done.
		*)
		HIBoxAnimationState = (
			HIBoxStateIdle, {No drawing necessary}
			HIBoxStateErase, {Erase entire box}
			HIBoxStateEraseFrame, {Erase frame of box}
			HIBoxStateDrawFrame, {Draw frame of box} 
			HIBoxStateDrawDenizen {Draw a fraction of the denizen 
				from the last drawn line to the fraction of the total indicated 
				or the end of the denizen's lines.}
		);
	type 
	
	WatchOp = (
		EvolveFromBox, {EvolveFromBox issues EraseBox for 
			all boxes but special, and issues MoveToCentre for special.}
		EraseBox, {Sets the box state to Erase and flags it for redraw}		
		DrawFrame, {Sets the box state to DrawFrame and flags it for redraw.}
		DrawContent, {Draws a complete biomorph inside the box}
		EraseContent, {Erases the content inside the frame}
		EraseFrame, {Erases the frame (prior to moving towards center)}
		Spark, {Draw a spark from the midpoint of the midbox to the indicated box midpoint}
		MoveTowardsCentre, {Move the view frame for the box towards the centre a fraction, update
		  fraction complete.}
		CopyToMidbox, {Copy the denizen of the current box to the midbox. Followed by EraseBox for
		  this box, and issuance of one instance of Reproduce for each of the boxes besides the
		  MidBox.}
		Reproduce, {Copy/mutate into one other box. Sets fractionComplete = 0}
		GrowTowardsHome
		{Move/scale the view frame for the box towards home a fraction,} 
		{draw more segments, increase fraction complete. When complete,}
		{DrawFrame}
	);
	
	InstructionPtr = ^Instruction;
		
	Instruction = record 
		operation: WatchOp;
		data: Pointer;
	end;

	InstructionNodePtr = ^InstructionNode;

	(*
		Instruction Nodes link instructions into a singly linked list.
		An Instruction node also carries state that indicates whether the
		instruction is complete or not, and the fraction completed. If
		the instruction is not complete, the timer will restart itself after the
		interval specified, otherwise the timer will restart itself immediately.
	*)
	InstructionNode = record
		thisInstruction: InstructionPtr;
		next: InstructionNodePtr;
		stillGoing: boolean;
		pauseBeforeNextFraction: single;
	end;
	InstructionNodeListPtr = ^InstructionNodeList;
	InstructionNodeList = record
		first: InstructionNodePtr;
		last: InstructionNodePtr;
	end;
	AnimatorPtr = ^Animator;
	Animator = Record
		instructions: InstructionNodeListPtr;
		timer: EventLoopTimerRef;
	end;

implementation


	function InstructionListCreate(nodeList: InstructionNodeListPtr): OSStatus;
	var
		status: OSStatus = noerr;
	begin
		new(nodeList);
		nodeList^.first := nil;
		nodeList^.last := nil;
		InstructionListCreate := status;
	end;
	
	function InstructionNodeRemove(
		nodeList: InstructionNodeListPtr;
		node: InstructionNodePtr): OSStatus;
	var
		status: OSStatus = noerr;
		nodeIndex: InstructionNodePtr;
		nodePrevIndex: InstructionNodePtr;
	begin
		nodeIndex := nodeList^.first;
		nodePrevIndex := nil;
		while nodeIndex <> nil Do
			begin
				if nodeIndex = node then
					begin
						if nodeIndex = nodeList^.first then
							nodeList^.first := node^.next;
						if nodePrevIndex <> nil then
							nodePrevIndex^.next := node^.next;
						exit
					end;
				nodePrevIndex := nodeIndex;
				nodeIndex := nodeIndex^.next;
			end;
		InstructionNodeRemove := status;
	end;

	function InstructionAdd(
		buffer: InstructionNodeListPtr; 
		state: WatchOp;
		data: Pointer;
		var newInstructionNode: InstructionNodePtr): OSStatus;
	var
		status: OSStatus = noerr;
		newInstruction: InstructionPtr;
	begin
		New(newInstruction);
		newInstruction^.operation := state;
		newInstruction^.data := data;
		New(newInstructionNode);
		newInstructionNode^.thisInstruction := newInstruction;
		if buffer^.last <> nil then
			buffer^.last^.next := newInstructionNode;
		buffer^.last := newInstructionNode;
		if buffer^.first = nil then
			buffer^.first := newInstructionNode;
		InstructionAdd := status;
	end;

end.