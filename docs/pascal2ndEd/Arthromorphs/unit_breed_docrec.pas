unit unit_breed_docrec;


interface

uses
  unit_watchdocrec, unit_evolve;
type
  BreedingDocRecHandle = ^BreedingDocRecPtr;
  BreedingDocRecPtr = ^BreedingDocRec;
  BreedingDocRec = RECORD
    wDocRec: WatchDocRec;
    eState: EvolveStateHandle;
  end;
implementation

end.

