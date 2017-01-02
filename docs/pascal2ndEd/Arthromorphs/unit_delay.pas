unit unit_delay;

interface

procedure DelayTicks(nTicks: LONGINT);

implementation

{$IFC UNDEFINED THINK_Pascal}
uses Events;
{$ENDC}

procedure DelayTicks(nTicks: LONGINT);
var
  TickValue: longint;
begin
  TickValue := TickCount + nTicks; {Old was hardcoded 2 ticks ABC}
  repeat
  until TickCount > TickValue ;
end;
end.

