function tree(x, y, lgth, dir, biomorph, dx, dy, thick, myPic, oddOne, order) {
	if(dir < 0)
		dir = dir + 8;
	if(dir >= 8)
		dir = dir - 8;
	
	if(biomorph.trickleGene < 1)
		biomorph.trickleGene = 1;

	var xnew = x + Math.trunc(lgth * dx[dir] / biomorph.trickleGene);
	var ynew = y + Math.trunc(lgth * dy[dir] / biomorph.trickleGene);
	
	if(biomorph.dGene[8] == SwellType.Shrink) 
		thick = lgth;
	else if(biomorph.dGene[8] == SwellType.Swell) 
		thick = 1 + biomorph.gene[8] - lgth;
	else {
		thick = 1;
	}

	picLine(myPic, x, y, xnew, ynew, thick * myPenSize);

	if(lgth > 1)
		if(oddOne) {
			tree(xnew, ynew, lgth - 1, dir + 1, biomorph, dx, dy, thick, myPic, oddOne, order);
			if(lgth < order)
				tree(xnew, ynew, lgth - 1, dir - 1, biomorph, dx, dy, thick, myPic, oddOne, order);
		} else {
			tree(xnew, ynew, lgth - 1, dir - 1, biomorph, dx, dy, thick, myPic, oddOne, order);
			if(lgth < order)
				tree(xnew, ynew, lgth - 1, dir + 1, biomorph, dx, dy, thick, myPic, oddOne, order);
		}
} // {tree}
/*
 Pascal original has order passed-by-reference.
 Since JavaScript passes simple types by value,
 the dirty workaround (in this and the Java edition) is to 
 return the new value for order, and pray the calling
 routine assigns the return value to order.
*/
function plugIn(gene, dx, dy) {
	var order = gene[8]; 
	dx[3] = gene[0];
	dx[4] = gene[1];
	dx[5] = gene[2];
	dy[2] = gene[3];
	dy[3] = gene[4];
	dy[4] = gene[5];
	dy[5] = gene[6];
	dy[6] = gene[7];
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

function develop (biomorph, myPic, drawingContext, zeroMargin) {
	// // // // console.log("Develop here:" + here.toString() + " Margin:" + margin.toString() + " Delayed:" + delayedDrawing);
	var dx = [0,0,0,0,0,0,0,0];
	var dy = [0,0,0,0,0,0,0,0];
	
	var x; 
	var y; 
	var seg; 
	var upExtent; 
	var downExtent;
	var wid; 
	var ht; 
	var thick;

	var oldHere;
    
	clipBoarding = false;
	here = new Point(0,0);
	var centre = here.copy();
	var order = plugIn(biomorph.gene, dx, dy); // Pass-by value workaround returns order as result.
	// // // // console.log("develop order:" + order)
	zeroPic(myPic, here);

	if(biomorph.segNoGene < 1)
		biomorph.segNoGene = 1;

	var	extraDistance;
	if(biomorph.dGene[9] == SwellType.Swell)
		extraDistance = biomorph.trickleGene;
	else if(biomorph.dGene[9] == SwellType.Shrink)
		extraDistance = -biomorph.trickleGene;
	else
		extraDistance = 0;
	
	var running = biomorph.gene.slice();
	// // // console.log("biomorph.gene " + biomorph.gene + "running:" + running);
	var incDistance = 0;
	// // console.log("biomorph.segNoGene " + biomorph.segNoGene);
	for(seg = 0; seg < biomorph.segNoGene; seg++) {
		var oddOne = (seg % 2) == 1;
		// // console.log("oddOne " + oddOne + " seg" + seg);
		if(seg > 0) {
			oldHere = here.copy();
			here.v += (biomorph.segDistGene + incDistance)/biomorph.trickleGene>>0;
			incDistance += extraDistance;
			if(biomorph.dGene[8] == SwellType.Shrink)
				thick = biomorph.gene[8];
			else
				thick = 1;
			// // // // console.log("picLine A");
			picLine(myPic, oldHere.h, oldHere.v, here.h, here.v, thick);
			var dGene = biomorph.dGene;
			for(j = 0; j<8; j++) {
				// // console.log("SwellType[" + j + "] " + SwellType.properties[dGene[j]].name);
				if(dGene[j] == SwellType.Swell) {
					// // console.log("Swell[" + j + "] trickle: " + biomorph.trickleGene);
					running[j] += biomorph.trickleGene;
				}
				if(dGene[j] == SwellType.Shrink) {
					// // console.log("Shrink[" + j + "] trickle: " + biomorph.trickleGene);
					running[j] -= biomorph.trickleGene;
				}
			}
			if(running[8] < 1) {
				running[8] = 1;
			}
			 // // console.log("before plugin running " + running + " dx" + dx + "dy " + dy + " order" + order);
			
			order = plugIn(running, dx, dy);
			 // // console.log("running " + running + " dx" + dx + "dy " + dy + " order" + order);
		}	
		var sizeWorry = biomorph.segNoGene * twoToThe(biomorph.gene[8]);
		if(sizeWorry > WORRYMAX)
			biomorph.gene[8] = biomorph.gene[8] - 1;
		if(biomorph.gene[8] < 1) {
			biomorph.gene[8] = 1;
		}
		// // console.log("call to tree order " + order + " gene8" + biomorph.gene[8]);
		tree(here.h, here.v, order, 2, biomorph, dx, dy, thick, myPic, oddOne, order);
	}
	var spokesGene = biomorph.spokesGene;
	
	
	var margin = myPic.margin;
	
	if(! (spokesGene == SpokesType.NorthOnly && biomorph.completenessGene == CompletenessType.Single)) {
	
		if(centre.h - margin.left > margin.right - centre.h)
			margin.right = centre.h + (centre.h - margin.left)
		else
			margin.left = centre.h - (margin.right - centre.h);
		var upExtent = centre.v - margin.top; //{can be zero if biomorph goes down}
		var downExtent = margin.bottom - centre.v;
		
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
				margin.top = centre.v - Math.trunc(wid/2) - 1;
				margin.bottom = centre.v + Math.trunc(wid/2) + 1;
			} else {
				margin.left = centre.h - Math.trunc(ht/2) - 1;
				margin.right = centre.h + Math.trunc(ht/2) + 1;
			}
		}
	}
	myPic.picPerson = biomorph;
}// {develop}
	
function delayvelop (biomorph, myPic, drawingContext) {
	// // // // console.log("DelayVelop called with biomorph " + biomorph.toString() + " here:" + here.toString());
	develop(biomorph, myPic);
	var margin = myPic.margin;
	// console.log("Margin " + margin.toString());
	var	offCentre = new Point((margin.left + margin.right) / 2, (margin.top + margin.bottom) / 2);
	// console.log("offCentre " + offCentre.toString());
	drawPic(myPic, offCentre, biomorph, drawingContext);
}

