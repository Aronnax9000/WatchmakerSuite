{This file is part of Watchmaker Suite Copyright 1990-2015 by Richard Dawkins.}
{Distributed under the terms of the GNU General Public License, version 3.0}
{See COPYING}

unit PersonSerialization;

interface

	uses MacOSAll, Globals, SerializationUtil, PersonDefs;

	const
		kFree = 0;
		kAnimalTrunk = 1;
		kAnimalJoint = 2;
		kAnimalClaw = 3;
		kSectionTrunk = 4;
		kSectionJoint = 5;
		kSectionClaw = 6;
		kSegmentTrunk = 7;
		kSegmentJoint = 8;
		kSegmentClaw = 9;
		kJoint = 10;
		kClaw = 11;

	type
		AtomCarbonSerializer = record
			Kind: Byte;
			padding: Byte;
			Height: arrayQuad;		{also used for Thickness of a Joint}
			Width: arrayQuad;		{also used for Length of a Joint}
			Angle: arrayQuad;		{also used in an AnimalTrunk to store the number of atoms in the animal}
						{also used in SectionTrunk to store the Overlap of segments}
						{also used in SegmentTrunk to store the rank number of the segment}
				
			{where to look in the BoneYard for the next atom. 0 means end of chain}
			{Also used in AnimalTrunk to store Gradient gene, slightly more or less than 100.  Treat as Percentage}
			NextLikeMe: arrayDuo;
				
			{where to look in the BoneYard for the next atom. 0 means end of chain}
			FirstBelowMe: arrayDuo;
		end;

procedure SetPersonSerializerFromPerson(dest: PersonSerializerPtr; src: PersonPtr);
procedure SetPersonFromPersonSerializer(dest: PersonPtr; src: PersonSerializerPtr);

implementation

	procedure SetPersonSerializerFromPerson(dest: PersonSerializerPtr; src: PersonPtr);
		var index: integer;
		begin
			case MiniYard[i]^^.Kind of
				Free: dest^.Kind := kFree;
				AnimalTrunk: dest^.Kind := kAnimalTrunk;
				AnimalJoint: dest^.Kind := kAnimalJoint;
				AnimalClaw: dest^.Kind := kAnimalClaw;
				SectionTrunk: dest^.Kind := kSectionTrunk;
				SectionJoint: dest^.Kind := kSectionJoint;
				SectionClaw: dest^.Kind := kSectionClaw;
				SegmentTrunk: dest^.Kind := kSegmentTrunk;
				SegmentJoint: dest^.Kind := kSegmentJoint;
				SegmentClaw: dest^.Kind := kSegmentClaw;
				Joint: dest^.Kind := kJoint;
				Claw: dest^.Kind := kClaw;
			end;
			dest^.height := arrayQuad(MiniYard[i]^^.height);
			dest^.width := arrayQuad(MiniYard[i]^^.width);
			dest^.angle := arrayQuad(MiniYard[i]^^.angle);
			dest^.NextLikeMe := arrayDuo(MiniYard[i]^^.NextLikeMe);
			dest^.FirstBelowMe := arrayDuo(MiniYard[i]^^.FirstBelowMe);
			
			{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
			mangleArrayQuad(dest^.height);
			mangleArrayQuad(dest^.width);
			mangleArrayQuad(dest^.angle);
			mangleArrayDuo(dest^.NextLikeMe);
			mangleArrayDuo(dest^.FirstBelowMe);
			{$endc}
		end;
		
	procedure SetPersonFromPersonSerializer(dest: PersonPtr; src: PersonSerializerPtr);
		var index: integer;
		begin
			case src^.Kind of
				kFree: dest^.Kind := Free;
				kAnimalTrunk: dest^.Kind := AnimalTrunk;
				kAnimalJoint: dest^.Kind := AnimalJoint;
				kAnimalClaw: dest^.Kind := AnimalClaw;
				kSectionTrunk: dest^.Kind := SectionTrunk;
				kSectionJoint: dest^.Kind := SectionJoint;
				kSectionClaw: dest^.Kind := SectionClaw;
				kSegmentTrunk: dest^.Kind := SegmentTrunk;
				kSegmentJoint: dest^.Kind := SegmentJoint;
				kSegmentClaw: dest^.Kind := SegmentClaw;
				kJoint: dest^.Kind := Joint;
				kClaw: dest^.Kind := Claw;
			end;
			{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
			mangleArrayQuad(src^.height);
			mangleArrayQuad(src^.width);
			mangleArrayQuad(src^.angle);
			mangleArrayDuo(src^.NextLikeMe);
			mangleArrayDuo(src^.FirstBelowMe);
			{$endc}
			dest^.height := single(src^.height);
			dest^.width := single(src^.width);
			dest^.angle := single(src^.angle);
			dest^.NextLikeMe := SInt16(src^.NextLikeMe);
			dest^.FirstBelowMe := SInt16(src^.FirstBelowMe);
		end;



end.
