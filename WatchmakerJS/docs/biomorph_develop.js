var myPenSize = 1;
function Rect() {
	this.left = 0;
	this.right = 0;
	this.top = 0;
	this.bottom = 0;
}

function tree(x, y, lgth, dir, biomorph, dx, dy, margin, thick, myPic) {
	var xnew;
	var ynew;
	if(dir < 0)
		dir = dir + 8;
	if(dir >= 8)
		dir = dir - 8;
	
	if(biomorph.trickleGene < 1)
		biomorph.trickleGene = 1;
	xnew = x + lgth * (dx[dir]/biomorph.trickleGene>>0);
	ynew = y + lgth * (dy[dir]/biomorph.trickleGene>>0);
	if(x < margin.left)
		margin.left = x;
	if(x > margin.right)
		margin.right = x;
	if(y > margin.bottom)
		margin.bottom = y;
	if(y < margin.top)
		margin.top = y;
	if(xnew < margin.left)
		margin.left = xnew;
	if(xnew > margin.right)
		margin.right = xnew;
	if(ynew > margin.bottom)
		margin.bottom = ynew;
	if(ynew < margin.top)
		margin.top = ynew;
	// {if((x<>xnew) OR (y<>ynew)) }
	if(biomorph.dGene[9] == SwellType.Shrink)
		thick = lgth;
	else if(biomorph.dGene[9] == SwellType.Swell)
		thick = 1 + biomorph.gene[9] - lgth;
	else
		thick = 1;
	picLine(myPic, x, y, xnew, ynew, thick * myPenSize);
	if(lgth > 1)
		if(oddone) {
			tree(xnew, ynew, lgth - 1, dir + 1, biomorph, dx, dy, margin, thick, myPic);
			if(lgth < order)
				tree(xnew, ynew, lgth - 1, dir - 1, biomorph, dx, dy, margin, thick, myPic);
		} else {
			tree(xnew, ynew, lgth - 1, dir - 1, biomorph, dx, dy, margin, thick, myPic);
			if(lgth < order)
				tree(xnew, ynew, lgth - 1, dir + 1, biomorph, dx, dy, margin, thick, myPic);
		}
} // {tree}

function plugIn(gene, dx, dy) {
	var order = gene[9]; // Pass-by-value bug: dirty workaround is return the value.
	dx[3] = gene[1];
	dx[4] = gene[2];
	dx[5] = gene[3];
	dy[2] = gene[4];
	dy[3] = gene[5];
	dy[4] = gene[6];
	dy[5] = gene[7];
	dy[6] = gene[8];
	dx[1] = -dx[3];
	dy[1] = dy[3];
	dx[0] = -dx[4];
	dy[0] = dy[4];
	dx[7] = -dx[5];
	dy[7] = dy[5];
	dx[2] = 0;
	dx[6] = 0;
	return order;
} // {PlugIn}

var clipBoarding = false;
var zeroMargin = false;

function copyPoint(parent) {
	var child = new Point();
	child.h = parent.h;
	child.v = parent.v;
	return child;
}

var Mode = {
	Preliminary:1, 
	Breeding:2, 
	Albuming:3, 
	Phyloging:4, 
	Killing:5, 
	Moving:6, 
	Detaching:7, 
	Randoming:8, 
	Engineering:9, 
	Drifting:10, 
	Highlighting:11, 
	PlayingBack:12, 
	Triangling:13, 
	Sweeping:14
};

var theMode = Mode.Breeding;

function develop (biomorph, here, myPic, margin, delayedDrawing) {
	var order; 
	var x; 
	var y; 
	var seg; 
	var upExtent; 
	var downExtent;
	var wid; 
	var ht; 
	var sizeWorry; 
	var thick;
	var dx = new Array(8);
	var dy = new Array(8);
	var	running;
	var oldHere;
	var centre;
	var	oddOne;
	var	extraDistance;
	var incDistance;

	clipBoarding = false;

	if(zeroMargin) {
		margin.left = here.h;
		margin.right = here.h;
		margin.right = here.h;
		margin.top = here.v;
		margin.bottom = here.v;
	}
	centre = copyPoint(here);
	order = plugIn(biomorph.gene, dx, dy); // Pass-by value workaround returns order as result.
	zeroPic(myPic, here);

	if(biomorph.segNoGene < 1)
		biomorph.segNoGene = 1;
	if(biomorph.dGene[9] == SwellType.Swell)
		extraDistance = biomorph.trickleGene;
	else if(biomorph.dGene[9] == SwellType.Shrink)
		extraDistance = -biomorph.trickleGene;
	else
		extraDistance = 0;
	running = biomorph.gene.splice();
	incDistance = 0;
	var segNoGenePlusOne = biomorph.segNoGene + 1;
	for(seg = 1; seg < segNoGenePlusOne; seg++) {
		oddOne = (seg % 2) == 1;
		if(seg > 1) {
			oldHere = copyPoint(here);
			here.v += (biomorph.segDistGene + incDistance)/biomorph.trickleGene>>0;
			incDistance += extraDistance;
			if(biomorph.dGene[8] == SwellType.Shrink)
				thick = biomorph.gene[8];
			else
				thick = 1;
			picLine(myPic, oldHere.h, oldHere.v, here.h, here.v, thick);
			var dGene = biomorph.dGene;
			for(j = 0; j<8; j++) {
				if(dGene[j] == SwellType.Swell)
					running[j] += biomorph.trickleGene;
				if(dGene[j] == SwellType.Shrink)
					running[j] -= biomorph.trickleGene;
			}
			if(running[8] < 1)
				running[8] = 1;
			order = plugIn(running, dx, dy);
		}	
		sizeWorry = biomorph.segNoGene * twoToThe(biomorph.gene[8]);
		if(sizeWorry > WORRYMAX)
			biomorph.gene[8] = biomorph.gene[8] - 1;
		if(biomorph.gene[8] < 1)
			biomorph.gene[8] = 1;
		tree(here.h, here.v, order, 2, biomorph, dx, dy, margin, thick, myPic);
	}
	if(centre.h - margin.left > margin.right - centre.h)
		margin.right = centre.h + (centre.h - margin.left)
	else
		margin.left = centre.h - (margin.right - centre.h);
	upExtent = centre.v - margin.top; //{can be zero if biomorph goes down}
	downExtent = margin.bottom - centre.v;
	var spokesGene = biomorph.spokesGene;
	if(((spokesGene == SpokesType.NSouth) || (spokesGene == SpokesType.Radial)) || 
			(theMode == Mode.Engineering)) // {Obscurely necessary to cope with erasing last Rect in Manipulation}
		if(upExtent > downExtent)
			margin.bottom = centre.v + upExtent;
		else
			margin.top = centre.v - downExtent;
	
	if(spokesGene == SpokesType.Radial) {
		wid = margin.right - margin.left;
		ht = margin.bottom - margin.top;
		if(wid > ht) {
			margin.top = centre.v - wid/2>>0 - 1;
			margin.bottom = centre.v + wid/2>>0 + 1;
		} else {
			margin.left = centre.h - ht/2>>0 - 1;
			margin.right = centre.h + ht/2>>0 + 1;
		}
	}
	myPic.picPerson = biomorph;
	if(delayedDrawing)
		drawPic(myPic, centre, biomorph);
}// {develop}
	
function delayvelop (biomorph, here, myPic) {
	console.log("DelayVelop called with biomorph " + biomorph);
	var margcentre;
	var offset;
	var	offCentre = new Point();
	var delayedDrawing = true;
	var	zeroMargin = true;
	var margin = new Rect();
	develop(biomorph, here, myPic, margin, delayedDrawing);
	delayedDrawing = false;
	margcentre = margin.top + (margin.bottom - margin.top)/2>>0;
	offset = margcentre - here.v;
	margin.top -= offset;
	margin.bottom -= offset;
	offCentre.h = here.h; // Biomorphs are guaranteed equal width to left and right.
	offCentre.v = here.v - offset;
	drawPic(myPic, offCentre, biomorph);
}

