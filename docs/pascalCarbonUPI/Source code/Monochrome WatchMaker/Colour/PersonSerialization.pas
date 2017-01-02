{This file is part of Watchmaker Suite Copyright 1990-2015 by Richard Dawkins.}
{Distributed under the terms of the GNU General Public License, version 3.0}
{See COPYING}

unit PersonSerialization;

interface

	uses MacOSAll, Globals, SerializationUtil, PersonDefs;

	const
		kSwellTypeSwell = 0;
		kSwellTypeSame = 1;
		kSwellTypeShrink = 2;
		kCompletenessTypeSingle = 0;
		kCompletenessTypeDouble = 1;
		kSpokesTypeNorthOnly = 0;
		kSpokesTypeNSouth = 1;
		kSpokesTypeRadial = 2;

	type

	chromosomeArrayDuo = ARRAY[1..9] OF arrayDuo;
	PersonSerializer = record
			gene: chromosomeArrayDuo;
			dgene: ARRAY[1..10] of byte;
			SegNoGene: arrayDuo;
			SegDistGene: arrayDuo;
			CompletenessGene: byte;
			SpokesGene: byte;
			tricklegene: arrayDuo; 
			mutsizegene: arrayDuo;
			mutprobgene: arrayDuo;	
		end;
	PersonSerializerPtr = ^PersonSerializer;

procedure SetPersonSerializerFromPerson(dest: PersonSerializerPtr; src: PersonPtr);
procedure SetPersonFromPersonSerializer(dest: PersonPtr; src: PersonSerializerPtr);

implementation

procedure SetPersonSerializerFromPerson(dest: PersonSerializerPtr; src: PersonPtr);
var index: integer;
begin
	with dest^ do
		begin
			for index := 1 to 9 do
				begin
					gene[index] := arrayDuo(src^.gene[index]);
					{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
					mangleArrayDuo(gene[index]);
					{$endc}
				end;
			for index := 1 to 10 do
				case src^.dgene[index] of
					Swell: dgene[index] := kSwellTypeSwell;
					Same: dgene[index] := kSwellTypeSame;
					Shrink: dgene[index] := kSwellTypeShrink;
				end;
			case src^.CompletenessGene of
				CompletenessTypeSingle: CompletenessGene := kCompletenessTypeSingle;
				CompletenessTypeDouble: CompletenessGene := kCompletenessTypeDouble;
			end;
			case src^.SpokesGene of
				NorthOnly: SpokesGene := kSpokesTypeNorthOnly;
				NSouth: SpokesGene := kSpokesTypeNSouth;
				Radial: SpokesGene := kSpokesTypeRadial;
			end;
			SegNoGene := arrayDuo(src^.SegNoGene);
			SegDistGene := arrayDuo(src^.SegDistGene);
			tricklegene := arrayDuo(src^.tricklegene); 
			mutsizegene := arrayDuo(src^.mutsizegene);
			mutprobgene := arrayDuo(src^.mutprobgene);
			{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
			mangleArrayDuo(SegNoGene);
			mangleArrayDuo(SegDistGene);
			mangleArrayDuo(tricklegene);
			mangleArrayDuo(mutsizegene);
			mangleArrayDuo(mutprobgene);
			{$endc}
		end;
	end;
procedure SetPersonFromPersonSerializer(dest: PersonPtr; src: PersonSerializerPtr);
var index: integer;
begin
	with dest^ do
		begin
			for index := 1 to 9 do
				begin
					{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
					mangleArrayDuo(src^.gene[index]);
					{$endc}
					gene[index] := SInt16(src^.gene[index]);
				end;
			for index := 1 to 10 do
				case src^.dgene[index] of
					kSwellTypeSwell: dgene[index] := Swell;
					kSwellTypeSame: dgene[index] := Same;
					kSwellTypeShrink: dgene[index] := Shrink;
				end;
			case src^.CompletenessGene of
				kCompletenessTypeSingle: CompletenessGene := CompletenessTypeSingle;
				kCompletenessTypeDouble: CompletenessGene := CompletenessTypeDouble;
			end;
			case src^.SpokesGene of
				kSpokesTypeNorthOnly: SpokesGene := NorthOnly;
				kSpokesTypeNSouth: SpokesGene := NSouth;
				kSpokesTypeRadial: SpokesGene := Radial;
			end;
			{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
			mangleArrayDuo(src^.SegNoGene);
			mangleArrayDuo(src^.SegDistGene);
			mangleArrayDuo(src^.tricklegene);
			mangleArrayDuo(src^.mutsizegene);
			mangleArrayDuo(src^.mutprobgene);
			{$endc}
			SegNoGene := SInt16(src^.SegNoGene);
			SegDistGene := SInt16(src^.SegDistGene);
			tricklegene := SInt16(src^.tricklegene); 
			mutsizegene := SInt16(src^.mutsizegene);
			mutprobgene := SInt16(src^.mutprobgene);
		end;
	end;



end.
