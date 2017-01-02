unit unit_globals;

interface

{$IFC UNDEFINED THINK_Pascal}
uses
  Types, Menus, Quickdraw;
{$ENDC}

const

  mApple = 128;
  iAbout = 1;
  mFile = 129;
  iNew = 1;
  iOpen = 2;
  iClose = 4;
  iSaveAs = 6;
  iQuit = 12;
  mEdit = 130;
  iUndo = 1;
  iCut = 3;
  iCopy = 4;
  iPaste = 5;
  iClear = 6;
  iSelectAll = 7;

  kMaxTELength = 32767;


  rMenubar = 128;
  rVScrollbar = 128;
  eMenuBar = 1;
  eMenu = 2;
  eEditRecord = 5;
  eExceedChara = 6;
  eNoSpaceCut = 7;
  eNoSpacePaste = 8;

var

  gNumberOfWindows: integer;
  menubarHdl: Handle;
  menuHdl: MenuHandle;
  ignoredWindowPtr: WindowPtr;
  theErr: OSErr;
  gDone: boolean;
  gCursorRegion: RgnHandle;
  gInBackground: boolean;
implementation

end.

