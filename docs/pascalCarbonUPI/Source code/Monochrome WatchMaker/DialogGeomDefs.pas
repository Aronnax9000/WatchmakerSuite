unit DialogGeomDefs;
{$mode macpas}
interface

	uses MacOSAll;
	
	{ Dialog Manager utilities		}

	procedure PositionDialog (theType: ResType; theID: integer);
	procedure FindDlogPosition (theType: ResType; theID: integer; VAR corner: Point);


implementation

{****************************************************}
{ PositionDialog}
{}
{		Center the bounding box of a dialog or alert in the upper third}
{		of the screen.  This is the preferred location according to the}
{		Human Interface Guidelines.}
{}
{****************************************************}

procedure PositionDialog (theType: ResType; theID: integer);

	VAR
		tmpBitMap: BitMap;
		boundsRect: Rect;
		theRect: Rect;
		theRectPtr: RectPtr;		{ Ptr to bounding box of dialog	}
		theTemplate: Handle;		{ Handle to resource template	}
		left,							{ Left side of centered rect		}
		top: integer;				{ Top side of centered rect		}

	begin
		{ The first field of the resource template for DLOG's and ALRT's 	}
		{ is its bounding box.  Get a pointer to this rectangle.  This   			}
		{ handle dereferencing is safe since the remaining statements in 	}
		{ this function do not move memory (assignment and simple math). 	}

		theTemplate := GetResource(theType, theID);
		IF resError <> noErr THEN
			Exit(PositionDialog);	{If we fail to load it, forget about positioning}

		theRectPtr := RectPtr(theTemplate^);
		theRect := theRectPtr^;
		IF (theRect.left >= theRect.right) OR (theRect.top >= theRect.bottom) THEN
			Exit(PositionDialog);
 {Position Dialog is creating problems that I don't understand.  RD}
{e.g. theRect can have a left bigger than its right}

		{ Center horizontally on screen	}

		boundsRect := GetQDGlobalsScreenBits(tmpBitMap)^.bounds;
		left := (boundsRect.right - (theRect.right - theRect.left)) DIV 2;

		{ Leave twice as much space as above	 }

		top := (boundsRect.bottom - (theRect.bottom - theRect.top)) DIV 3;

		{ Don't put rect under menu bar	}

		IF top < GetMBarHeight + 7 THEN
			top := GetMBarHeight + 7;

		theRect.right := theRect.right + left - theRect.left;
		theRect.left := left;
		theRect.bottom := theRect.bottom + top - theRect.top;
		theRect.top := top;

		theRectPtr^ := theRect;
	end;


{****************************************************}
{ FindDLOGPosition}
{}
{ 		Return the coordinates of the top left corner of a dialog or alert}
{ 		which centers the box in the upper third of the main screen. This is}
{ 		the preferred location according to the Human Interface Guidelines.}
{}
{****************************************************}

procedure FindDlogPosition (theType: ResType; theID: integer; VAR corner: Point);

	TYPE
		RectPtr = ^Rect;
		RectHandle = ^RectPtr;

	VAR
		theRect: Rect;		{ Bounding box of dialog				}
		tmpBitMap: BitMap;
		boundsRect: Rect;

	begin

		{ The first field of the resource template for DLOG's and ALRT's 	}
		{ is its bounding box.  Access this rectangle.  This   					}
		{ handle dereferencing is safe since the remaining statements in 	}
		{ this function do not move memory (assignment and simple math). 	}

		theRect := RectHandle(GetResource(theType, theID))^^;
		boundsRect := GetQDGlobalsScreenBits(tmpBitMap)^.bounds;

		{ Center horizontally on screen	}

		corner.h := (boundsRect.right - (theRect.right - theRect.left)) DIV 2;

		{ Leave twice as much space as above	 }

		corner.v := (boundsRect.bottom - (theRect.bottom - theRect.top)) DIV 3;

		{ Don't put rect under menu bar	}

		IF corner.v < GetMBarHeight + 7 THEN
			corner.v := GetMBarHeight + 7;
	end;

end.
