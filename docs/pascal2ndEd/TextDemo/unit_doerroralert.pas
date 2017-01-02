unit unit_doerroralert;

{$mode macpas}{$H+}

interface

const       		eWindow = 3;

procedure DoErrorAlert (errorCode: integer);

implementation
  {$IFC UNDEFINED THINK_Pascal}
  uses Dialogs, SegLoad, Types, TextUtils;
  {$ENDC}

  CONST
  		rErrorStrings = 128;
      rErrorAlert = 128;

	procedure DoErrorAlert (errorCode: integer);

		var
			errorString: Str255;
			ignored: integer;

	begin
		GetIndString(errorString, rErrorStrings, errorCode);
		ParamText(errorString, '', '', '');

		if (errorCode < eWindow) then
			begin
				ignored := StopAlert(rErrorAlert, nil);
				ExitToShell;
			end
		else
			ignored := CautionAlert(rErrorAlert, nil);
	end;
		{of procedure DoErrorAlert}

end.

