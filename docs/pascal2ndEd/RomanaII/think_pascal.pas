unit think_pascal;

{think_pascal native functions (Not part of the Mac Toolbox) }
{Alan Canon October 2015}

interface
{$IFC UNDEFINED THINK_Pascal}
uses Types;
{$ENDC}

function Memavail: longint; {Macintosh ThinkPascal, native, not implemented in FPC}
procedure Rewrite(var f: file; Name: Str63); overload;
procedure Reset(var f: file; Name: Str63);

implementation

function Memavail: longint;
begin
  Memavail := 2147483647;
end; {Macintosh ThinkPascal, native, not implemented in FPC}

procedure Rewrite(var f: file; Name: Str63);
begin
end;

procedure Reset(var f: file; Name: Str63);
begin
end;


end.
