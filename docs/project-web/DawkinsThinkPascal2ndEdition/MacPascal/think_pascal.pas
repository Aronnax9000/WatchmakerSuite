unit think_pascal;

{think_pascal native functions (Not part of the Mac Toolbox) }
{Alan Canon October 2015}

interface

uses Types;

const
  {Constants from Error.pas, whose name conflicts with a FPC interface.}
  {eofErr = -39;}                {End of file}
  {memFullErr = -108;} {ABC: Can't make it see the value in Errors.}
  _GestaltDispatch = $A0AD;
{Added by ABC from ThinkPascal 4.0 Traps.p}
function Memavail: longint; {Macintosh ThinkPascal, native, not implemented in FPC}
procedure Rewrite(var f: file; Name: Str63); overload;
procedure Reset(var f: file; Name: Str63);
procedure SetTextRect(r: Rect);
procedure ShowText;
procedure ReWrite(var f: Text); overload;

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

procedure SetTextRect(r: Rect);
begin
end;

procedure ShowText;
begin
end;

procedure ReWrite(var f: Text);
begin
end;

end.
