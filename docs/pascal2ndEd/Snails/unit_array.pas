unit unit_array;

interface

uses unit_snail_types;

PROCEDURE DoArray (prefs: SnailPreferencesHandle; theBiomorph: person; WDetails, DDetails, TDetails: MarchingOrders);


implementation
uses
{$IFC UNDEFINED THINK_Pascal}
Types, Quickdraw, QuickdrawText,
{$ENDC}
unit_develop, unit_globals, unit_miscellaneous, unit_findthescale;

PROCEDURE theBusiness (prefs: SnailPreferencesHandle; other: str255; var b: integer; W, X: real; colChap: MarchingOrders);
  var offsetPoint: Point;
	BEGIN
		b := b + 1;
		WITH theBiomorph DO
			BEGIN
				WOpening := W;
				CASE colChap.kind OF
					Dis:
						DDisplacement := X;
					Trans:
						TTRanslation := X;
				END; {cases}
				offsetPoint := develop(prefs, theBiomorph, centre[b]);
			END;
	END;


	PROCEDURE OtherW (prefs: SnailPreferencesHandle; other: str255; rowChap: MarchingOrders; var b: integer; D, T, W, X: real);
{enter all these routines with all three starting values set, with b zeroed and with SetUpBoxes called to fix NRows & NCols}
		VAR
			j: integer;
      var offsetPoint: Point;
		BEGIN
			X := rowChap.start;
			WITH theBiomorph DO
				BEGIN
					WHILE X <= rowChap.till DO
						BEGIN
							W := WDetails.start;
							FOR j := 1 TO round(WDetails.till) DO
								BEGIN
									b := b + 1;
									WITH theBiomorph DO
										BEGIN
											WOpening := W;
											CASE rowChap.kind OF
												Dis:
													BEGIN
														DDisplacement := X;
														TTranslation := T;
													END;
												Trans:
													BEGIN
														TTRanslation := X;
														DDisplacement := D;
													END;
											END; {cases}
											offsetPoint := develop(prefs, theBiomorph, centre[b]);
										END;
									IF b <= NCols THEN
										WITH prefs^^.box[b] DO
											BEGIN
												MoveTo(left + 50, top - 5);
												DrawString('W=');
												DrawReal(W);
											END;
									IF B - NCols * (B DIV NCols) = 1 THEN
										WITH prefs^^.box[b] DO
											BEGIN
												MoveTo(left + 2, bottom - 2);
												DrawString(concat(other, '='));
												DrawReal(X);
											END;
									X := X + rowChap.by;
								END;
							W := W * WDetails.by;
						END;
				END;
		END;{OtherW}

  	PROCEDURE OtherOther (prefs: SnailPreferencesHandle; rowString, colString: str255; rowChap, colChap: MarchingOrders; var b: integer; var W: real);
{enter all these routines with all three starting values set, with b zeroed and with SetUpBoxes called to fix NRows & NCols}

		VAR
			X, Y: real;
      offsetPoint: Point;
		BEGIN
			W := WDetails.start;
			Y := rowChap.start;
			WITH theBiomorph DO
				BEGIN
					WHILE round(10 * Y) <= round(10 * rowChap.till) DO
						BEGIN
							X := colChap.start;
							WHILE round(10 * X) <= round(10 * colChap.till) DO
								BEGIN
									b := b + 1;
									IF b <= NCols THEN
										WITH prefs^^.box[b] DO
											BEGIN
												MoveTo(left + 50, top - 5);
												DrawString(concat(colString, '='));
												DrawReal(X);
											END;
									IF B - NCols * (B DIV NCols) = 1 THEN
										WITH prefs^^.box[b] DO
											BEGIN
												MoveTo(left + 2, bottom - 2);
												DrawString(concat(rowString, '='));
												DrawReal(Y);
											END;
									WITH theBiomorph DO
										BEGIN
											WOpening := W;
											CASE rowChap.kind OF
												Dis:
													DDisplacement := Y;
												Trans:
													TTRanslation := Y;
											END; {cases}
											CASE colChap.kind OF
												Dis:
													DDisplacement := X;
												Trans:
													TTRanslation := X;
											END; {cases}
											offsetPoint := develop(prefs, theBiomorph, centre[b]);
										END;
									X := X + colChap.by;
								END;
							Y := Y + rowChap.by;
						END;
				END;
		END;{OtherOther}



PROCEDURE DoArray (prefs: SnailPreferencesHandle; theBiomorph: person; WDetails, DDetails, TDetails: MarchingOrders);
	VAR
		b, verdict: integer;
		W, D, T, X: real;
		s, s1, s2, s3, theString: str255;
		HoldConstant, RowChap, ColChap: MarchingOrders;



	PROCEDURE WOther (other: str255);
{enter all these routines with all three starting values set, with b zeroed and with SetUpBoxes called to fix NRows & NCols}
		VAR
			j: integer;
      var offsetPoint: Point;
		BEGIN
			W := WDetails.start;
			WITH theBiomorph DO
				BEGIN
					FOR j := 1 TO round(WDetails.till) DO
						BEGIN
							X := colChap.start;
							WHILE round(10 * X) <= round(10 * colChap.till) DO
								BEGIN
									b := b + 1;
									WITH theBiomorph DO
										BEGIN
											WOpening := W;
											CASE colChap.kind OF
												Dis:
													BEGIN
														DDisplacement := X;
														TTranslation := T;
													END;
												Trans:
													BEGIN
														TTRanslation := X;
														DDisplacement := D;
													END;
											END; {cases}
											offsetPoint := develop(prefs, theBiomorph, centre[b]);
										END;
									IF b <= NCols THEN
										WITH prefs^^.box[b] DO
											BEGIN
												MoveTo(left + 50, top - 5);
												DrawString(concat(other, '='));
												DrawReal(X);
											END;
									IF B - NCols * (B DIV NCols) = 1 THEN
										WITH prefs^^.box[b] DO
											BEGIN
												MoveTo(left + 2, bottom - 2);
												DrawString('W=');
												DrawReal(W);
											END;
									X := X + colChap.by;
								END;
							W := W * WDetails.by;
						END;
				END;
		END;{WOther}


	BEGIN
    with prefs^^ do
      begin
		  theMode := Arraying;
		  HoldConstant := DDetails;
		  RowChap := WDetails;
		  ColChap := TDetails;
		  IF LayoutDialog(HoldConstant, RowChap, ColChap, NRows, NCols) THEN
			  BEGIN
				  NBoxes := NRows * NCols;
				  EraseRect(PRect);
				  SetupBoxes(prefs);
				  b := 0;
				  WITH box[1] DO
					  WITH theBiomorph DO
						  BEGIN
							  MoveTo(left + 2, top - 13);
							  DrawString('   Graininess = ');
							  DrawReal(CoarseGraininess);
							  DrawString('     Reach = ');
							  DrawReal(reach);
						  END;
				  b := 0;
				  W := WDetails.start;
				  D := DDetails.start;
				  T := TDetails.start;
				  CASE rowChap.kind OF
					  Wop:
						  CASE colChap.kind OF
							  Dis:
								  BEGIN
									  DrawString('   T=');
									  DrawReal(TDetails.start);
									  WOther('D');
								  END;
							  Trans:
								  BEGIN
									  DrawString('   D=');
									  DrawReal(DDetails.start);
									  WOther('T');
								  END;
						  END; {cases}
					  Dis:
						  CASE colChap.kind OF
							  Wop:
								  BEGIN
									  DrawString('   T=');
									  DrawReal(TDetails.start);
									  OtherW(prefs, 'D', rowChap, b, D, T, W, X);
								  END;
							  Trans:
								  BEGIN
									  DrawString('   W=');
									  DrawReal(WDetails.start);
									  OtherOther(prefs, 'D', 'T', rowChap, colChap, b, w);
								  END;
						  END; {cases}
					  Trans:
						  CASE colChap.kind OF
							  Wop:
								  BEGIN
									  DrawString('   D=');
									  DrawReal(DDetails.start);
									  OtherW(prefs, 'T', rowChap, b, D, T, W, X);
								  END;
							  Dis:
								  BEGIN
									  DrawString('   W=');
									  DrawReal(WDetails.start);
									  OtherOther(prefs, 'T', 'D', rowChap, colChap, b, w);
								  END;
						  END; {cases}
				  END; {cases}
				  StoreOffScreen(MainPtr^.PortRect, MyBitMap);
			  END;

      end;
  END; {DoArray}

end.

