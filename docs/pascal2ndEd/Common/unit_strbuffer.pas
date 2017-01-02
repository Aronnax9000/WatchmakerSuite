unit unit_strbuffer;

interface
{$IFC UNDEFINED THINK_Pascal}
uses Types;
{$ENDC}
type

  StrBufferHandle = ^StrBufferPtr;
  StrBufferPtr = ^StrBuffer;
  StrBuffer = RECORD
    buf: Handle;
    bufIndex: INTEGER;
  END;
procedure writelnStrBuffer(theStrBuffer: StrBufferHandle; theString: Str255);
procedure writeStrBuffer(theStrBuffer: StrBufferHandle; theString: Str255);
procedure RewriteStrBuffer(theStrBuffer: StrBufferHandle);
function NewStrBuffer: StrBufferHandle;
procedure DisposeStrBuffer(theStrBuffer: StrBufferHandle);


implementation
{$IFC UNDEFINED THINK_Pascal}
uses Memory;
{$ENDC}
function NewStrBuffer: StrBufferHandle;
var theStrBuffer: StrBufferHandle;
begin
  theStrBuffer := StrBufferHandle(NewHandle(SizeOf(StrBuffer)));
  with theStrBuffer^^ do
    begin
      buf := NewHandle(32767);
      bufIndex := 0;
    end;
  NewStrBuffer := theStrBuffer;
end;

procedure DisposeStrBuffer(theStrBuffer: StrBufferHandle);
begin
  with theStrBuffer^^ do
    DisposeHandle(Handle(buf));
  DisposeHandle(Handle(theStrBuffer));
end;


procedure RewriteStrBuffer(theStrBuffer: StrBufferHandle);

begin
  theStrBuffer^^.bufIndex := 0;
end;

procedure AppendStrBuffer(theStrBuffer: StrBufferHandle; theString: Str255; terminate: boolean);
var
  i: longint;
  stringLength: integer;
  bufPointer: Ptr;
  strPointer: Ptr;
begin
  stringLength := length(theString);
  HLock(Handle(theStrBuffer));
  HLock(theStrBuffer^^.buf);

  bufPointer := Pointer(Ord(theStrBuffer^^.buf^) + theStrBuffer^^.bufIndex);
  strPointer := Pointer(Ord(@theString) + 1); {First byte is the length}

  for i := 1 to stringLength do
  begin
    bufPointer^ := strPointer^;
    bufPointer := Pointer(Ord(bufPointer) + 1);
    strPointer := Pointer(Ord(strPointer) + 1);
  end;
  theStrBuffer^^.bufIndex := theStrBuffer^^.bufIndex + stringLength;
  {Pointer(Ord(@rec^^.value) + 1), length(rec^^.value)}
  if terminate then
    begin
      bufPointer^ := 13; {loop already bufPointer walk off the end by 1}
      theStrBuffer^^.bufIndex := theStrBuffer^^.bufIndex + 1;
    end;
  HUnLock(theStrBuffer^^.buf);
  HLock(Handle(theStrBuffer));

end;

procedure writelnStrBuffer(theStrBuffer: StrBufferHandle; theString: Str255);
begin
  AppendStrBuffer(theStrBuffer, theString, true);
end;
procedure writeStrBuffer(theStrBuffer: StrBufferHandle; theString: Str255);
begin
  AppendStrBuffer(theStrBuffer, theString, false);
end;

end.

