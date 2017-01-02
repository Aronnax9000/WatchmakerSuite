unit unit_dogetselectlength;


interface
 {$IFC UNDEFINED THINK_Pascal}
 uses TextEdit;

 {$ENDC}
 function DoGetSelectLength(editRecHdl: TEHandle): integer;

implementation
function DoGetSelectLength(editRecHdl: TEHandle): integer;

var
  selectionLength: integer;

begin
  selectionLength := editRecHdl^^.selEnd - editRecHdl^^.selStart;
  DoGetSelectLength := selectionLength;
end;
end.

