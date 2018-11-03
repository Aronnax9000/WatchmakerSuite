function pointToString() {
	return "(" + this.h + "," + this.v + ")";
}

function pointCopy() {
	var child = new Point(this.h, this.v);
	return child;
}


/* 
 * QuickDraw style point, with h (horizontal) and v (vertical) 
 */
function Point(x,y) {
   this.h = x;
   this.v = y;
   
   this.toString = pointToString;
   this.copy = pointCopy;
   // // // // console.log("Point" + this.toString());
}


function rectToString() {
	return "Rect (" + this.left + "," + this.top + "),(" + this.right + "," + this.bottom + ")";
			
}


/*
 * QuickDraw style Rect, with left, right, top and bottom
 */
function Rect() {
	this.left = 0;
	this.right = 0;
	this.top = 0;
	this.bottom = 0;
	this.toString = rectToString;
}
