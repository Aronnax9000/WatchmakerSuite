unit unit_docrec;

interface

   {$IFC UNDEFINED THINK_Pascal}
uses TextEdit, Controls;

{$ENDC}

const
    kTextInset = 4;
		kReturn = $0D;
		kEnter = $03;
type

  DocRec = record
    editRecHdl: TEHandle;
    vScrollbarHdl: ControlHandle;
  end;

  DocRecPtr = ^DocRec;
  DocRecHandle = ^DocRecPtr;


implementation

end.
