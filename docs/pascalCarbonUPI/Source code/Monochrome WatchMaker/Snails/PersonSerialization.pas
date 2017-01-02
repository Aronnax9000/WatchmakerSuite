{This file is part of Watchmaker Suite Copyright 1990-2015 by Richard Dawkins.}
{Distributed under the terms of the GNU General Public License, version 3.0}
{See COPYING}

unit PersonSerialization;

interface

	uses MacOSAll, Globals, SerializationUtil, PersonDefs;



	type


	PersonSerializer = record
			WOpening: arrayQuad; 
			DDisplacement: arrayQuad; 
			SShape: arrayQuad; 
			TTranslation: arrayQuad;
			Coarsegraininess: arrayDuo; 
			Reach: arrayDuo; 
			GeneratingCurve: arrayDuo;
			TranslationGradient: arrayQuad; 
			DGradient: arrayQuad;
			Handedness: byte;
			padding: byte;
		end;
	PersonSerializerPtr = ^PersonSerializer;

procedure SetPersonSerializerFromPerson(dest: PersonSerializerPtr; src: PersonPtr);
procedure SetPersonFromPersonSerializer(dest: PersonPtr; src: PersonSerializerPtr);

implementation

procedure SetPersonSerializerFromPerson(dest: PersonSerializerPtr; src: PersonPtr);
	begin
	
		dest^.WOpening := arrayQuad(src^.WOpening);
		dest^.DDisplacement := arrayQuad(src^.DDisplacement);
		dest^.TTranslation := arrayQuad(src^.TTranslation);
		dest^.SShape := arrayQuad(src^.SShape);
		dest^.Coarsegraininess := arrayDuo(src^.Coarsegraininess);
		dest^.Reach := arrayDuo(src^.Reach);
		dest^.GeneratingCurve := arrayDuo(src^.GeneratingCurve);
		dest^.TranslationGradient := arrayQuad(src^.TranslationGradient);
		dest^.DGradient := arrayQuad(src^.DGradient);
		dest^.Handedness := src^.Handedness;
		{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
		mangleArrayQuad(dest^.WOpening);
		mangleArrayQuad(dest^.DDisplacement);
		mangleArrayQuad(dest^.TTranslation);
		mangleArrayQuad(dest^.SShape);
		mangleArrayDuo(dest^.Coarsegraininess);
		mangleArrayDuo(dest^.Reach);
		mangleArrayDuo(dest^.GeneratingCurve);
		mangleArrayQuad(dest^.TranslationGradient);
		mangleArrayQuad(dest^.DGradient);
		{$endc}
	end;
procedure SetPersonFromPersonSerializer(dest: PersonPtr; src: PersonSerializerPtr);
	begin
		{$ifc defined FPC_LITTLE_ENDIAN or not defined FPC_BIG_ENDIAN}
		mangleArrayQuad(src^.WOpening);
		mangleArrayQuad(src^.DDisplacement);
		mangleArrayQuad(src^.TTranslation);
		mangleArrayQuad(src^.SShape);
		mangleArrayDuo(src^.Coarsegraininess);
		mangleArrayDuo(src^.Reach);
		mangleArrayDuo(src^.GeneratingCurve);
		mangleArrayQuad(src^.TranslationGradient);
		mangleArrayQuad(src^.DGradient);
		{$endc}
	
		dest^.WOpening := single(src^.WOpening);
		dest^.DDisplacement := single(src^.DDisplacement);
		dest^.TTranslation := single(src^.TTranslation);
		dest^.SShape := single(src^.SShape);
		dest^.Coarsegraininess := SInt16(src^.Coarsegraininess);
		dest^.Reach := SInt16(src^.Reach);
		dest^.GeneratingCurve := SInt16(src^.GeneratingCurve);
		dest^.TranslationGradient := single(src^.TranslationGradient);
		dest^.DGradient := single(src^.DGradient);
		dest^.Handedness := src^.Handedness;
	end;



end.
