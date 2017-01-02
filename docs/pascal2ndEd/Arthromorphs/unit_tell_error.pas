unit unit_tell_error;


interface

procedure TellError (what: string);

implementation

uses
{$IFC UNDEFINED THINK_Pascal}
  Dialogs,
{$ENDC}
  unit_error_alert;

procedure TellError (what: string);
begin
	ParamText(what, '', '', '');
	A_Error_Alert;
end;
end.

