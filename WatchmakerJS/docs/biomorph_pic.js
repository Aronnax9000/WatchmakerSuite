/* 
 * QuickDraw style point, with h (horizontal) and v (vertical) 
 */
function Point() {
   this.h = 0;
   this.v = 0;
}

/*
 * Globals, line 247.
 * 
 * Lin = RECORD
 *     StartPt, EndPt: Point;
 *     Thickness: 1..8;
 * END;
 * LinPtr = ^Lin;
 * LinHandle = ^LinPtr;
 */
function Lin() {
    this.startPt = new Point();
    this.endPt = new Point();
    this.thickness = 1;
    this.nextLin = null;
}

/*
 * Globals, line 253.
 *     Pic = RECORD
 *          BasePtr: Ptr;
 *          MovePtr: LinPtr;
 *          Origin: Point;
 *          PicSize: Integer;
 *          PicPerson: person
 *      END;
 *      
 * 
 */

function Pic() {
    this.basePtr = null; // The first Lin
    this.movePtr = null; // The current Lin (used in walking the array)
    this.origin = new Point(); // a Point
    this.picSize = 0; // Number of Lins
    this.picPerson = null; // the biomorph that this is a picture of.
}
/*
 PROCEDURE ZeroPic (VAR thisPic: Pic; Here: Point);
    BEGIN
        WITH thisPic DO
            BEGIN
                MovePtr = LinPtr(BasePtr);
                PicSize = 0;
                Origin = Here
            END
    END; {ZeroPic}
 */
function zeroPic(thisPic, here) {
    if(thisPic.basePtr != null) { 
        // Pic has lines. Walk the singly linked list all the way to the end,
        // disconnect each Lin from the next.
        var walkPtr = thisPic.basePtr;
        while(walkPtr != null) {
            // Gotta grab a reference to the next element in the list 
            // before we disconnect it from the current one.
            var nextLin = walkPtr.nextLin
            walkPtr.nextLin = null;
            walkPtr = nextLin;
        }
        thisPic.picSize = 0;
        thisPic.origin = here;
    }
}
/*
 * Globals, line 28.
 */
const PICSIZEMAX = 4095;


/*
 * PROCEDURE PicLine (VAR thisPic: Pic; x, y, xnew, ynew, thick: Integer);
    BEGIN
        IF thick > 8 THEN
            thick = 8;
        WITH thisPic DO
            BEGIN
                IF PicSize >= PicSizeMax THEN
                    BEGIN
{Message(GetString(TooLargeString));}
 {used the help dialog! v1.1 changed to alert}
                        DisplayError(-147, 'Biomorph too large, or other problem', ' ', StopError);
                        ExitToShell
                    END
                ELSE
                    WITH MovePtr^ DO
                        BEGIN
                            StartPt.h = x;
                            StartPt.v = y;
                            EndPt.h = xnew;
                            EndPt.v = ynew;
                            Thickness = Thick
                        END;
                MovePtr = linptr(size(MovePtr) + 10);  {advance 'array subscript' by number}
{                                    of bytes occupied by one lin}
                PicSize = PicSize + 1
            END
    END; {PicLine}

 */
function picLine(thisPic, x, y, xnew, ynew, thick) {
    if(thick > 8)
        thick = 8;
    if(thisPic.PicSize >= PICSIZEMAX) {
        // {Message(GetString(TooLargeString));}
        // {used the help dialog! v1.1 changed to alert}
        alert('Biomorph too large, or other problem');
        return;
    } else {
        newLin = new Lin();
        newLin.startPt.h = x;
        newLin.startPt.v = y;
        newLin.endPt.h = xnew;
        newLin.endPt.v = ynew;
        newLin.thickness = thick;
        if(thisPic.basePtr == null) { // First Lin in the Pic.
            thisPic.basePtr = newLin; // set the base pointer to the new Lin
        } else { // Pic already has at least one Lin.
            // Link the new Lin onto the Lin at end of the Pic
            thisPic.movePtr.nextLin = newLin; 
        }
        thisPic.movePtr = newLin; // Point to the new end of the list

        thisPic.picSize++;
    }
} // {PicLine}

var PicStyleType = {LF: 1, RF: 2, FF: 3, LUD: 4, RUD:5, FUD:6, LSW:7, RSW:8, FSW:9};
var Compass = {NorthSouth:1, EastWest:2};

var thePenSize = new Point();
var myPenSize = new Point();
myPenSize.h = 1;
myPenSize.v = 1;
console.log("Initialized myPenSize");

function penSize(hThickness, vThickness) {
    thePenSize.h = hThickness;
    thePenSize.v = vThickness;
    console.log("penSize " + thePenSize.h + "," + thePenSize.v)
}

var orientation = Compass.NorthSouth;

function moveTo(h, v) {
	console.log("moveTo " + h + "," + v);
}
function lineTo(h, v) {
	console.log("lineTo " + h + "," + v);

}

function actualLine(picStyle, orientation, thisPic, place, mid2, belly2) {
	console.log("actualLine");
    var origin = thisPic.origin;
    var movePtr = thisPic.movePtr;
    var thickness = movePtr.thickness;
    console.log("actualLine thickness:" + thickness);
    penSize(thickness, thickness);
    var vertOffset;
    var horizOffset;
    var x0;
    var x1;
    var y0;
    var y1;
    var startPt = movePtr.startPt;
    var endPt = movePtr.endPt;
    if(orientation == Compass.NorthSouth) {
        vertOffset = origin.v - place.v;
        horizOffset = origin.h - place.h;
        y0 = startPt.v - vertOffset;
        y1 = endPt.v - vertOffset;
        x0 = startPt.h - horizOffset;
        x1 = endPt.h - horizOffset;
    } else {
        vertOffset = thisPic.Origin.h - place.v;
        horizOffset = thisPic.Origin.v - place.h;
        y0 = StartPt.h - vertOffset;
        y1 = EndPt.h - vertOffset;
        x0 = StartPt.v - horizOffset;
        x1 = EndPt.v - horizOffset;
    }
    switch(picStyle) {
    case PicStyleType.LF: 
        moveTo(x0, y0);
        lineTo(x1, y1);
    break;
    case PicStyleType.RF: 
	    moveTo(mid2 - x0, y0);
        lineTo(mid2 - x1, y1);
    break;
    case PicStyleType.FF: 
	    moveTo(x0, y0);
	    lineTo(x1, y1);
	    moveTo(mid2 - x0, y0);
	    lineTo(mid2 - x1, y1);
    break;
    case PicStyleType.LUD: 
        moveTo(x0, y0);
        lineTo(x1, y1);
        moveTo(mid2 - x0, belly2 - y0);
        lineTo(mid2 - x1, belly2 - y1);
    break;
    case PicStyleType.RUD: 
        moveTo(mid2 - x0, y0);
        lineTo(mid2 - x1, y1);
        moveTo(x0, belly2 - y0);
        lineTo(x1, belly2 - y1);
    break;
    case PicStyleType.FUD: 
    	moveTo(x0, y0);
        lineTo(x1, y1);
        moveTo(mid2 - x0, y0);
        lineTo(mid2 - x1, y1);
        moveTo(x0, belly2 - y0);
        lineTo(x1, belly2 - y1);
        moveTo(mid2 - x0, belly2 - y0);
        lineTo(mid2 - x1, belly2 - y1);
    break;
    } // {CASES}
} // {ActualLine}

//{Pic already contains its own origin, meaning the coordinates at which}
//{ it was originally drawn. Now draw it at place}
function drawPic(thisPic, place, biomorph) {
	var mid2;
	var belly2;
	// {To correct initialisation bug, due to call in DoUpdate}
    var picStyle = PicStyleType.FF; 
    switch(biomorph.completenessGene) {
    case CompletenessType.Single: 
        switch(biomorph.spokesGene) {
        case SpokesType.NorthOnly: 
        	picStyle = PicStyleType.LF;
        	break;
        case SpokesType.NSouth: 
            picStyle = PicStyleType.LUD;
        	break;
        case SpokesType.Radial: 
            picStyle = PicStyleType.LUD;
            break;
        }
        break;
    case CompletenessType.Double: 
        switch(biomorph.spokesGene) {
        case SpokesType.NorthOnly: 
            picStyle = PicStyleType.FF;
            break;
        case SpokesType.NSouth: 
            picStyle = PicStyleType.FUD;
            break;
        case SpokesType.Radial: 
        	picStyle = PicStyleType.FUD;
        	break;
        }
        break;
    }
    penSize(myPenSize, myPenSize);
    mid2 = 2 * place.h;
    belly2 = 2 * place.v;
    // {reposition at base of grabbed space}
    thisPic.movePtr = thisPic.basePtr; 
    while(true) {
    	
    	actualLine(picStyle, Compass.NorthSouth, thisPic, place, mid2, belly2); // {sometimes rangecheck error}
        if(biomorph.SpokesGene == SpokesType.Radial) 
        	if(biomorph.completenessGene = CompletenessType.Single) 
                actualLine(PicStyleType.RUD, Compass.EastWest, thisPic, place, mid2, belly2);
            else
            	actualLine(picStyle, Compass.EastWest, thisPic, place, mid2, belly2);
        if(thisPic.movePtr.nextLin == null)
        	break; // Leave iteration with thisPic.movePtr pointing to the last Lin.
        // Advance to next Lin.
        thisPic.movePtr = thisPic.movePtr.nextLin;
    }
    penSize(1, 1);
} // {DrawPic}
