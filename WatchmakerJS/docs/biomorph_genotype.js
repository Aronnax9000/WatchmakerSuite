
const TRICKLE = 10;
const MutTypeNo = 9;

var mut = new Array(MutTypeNo);

function initializeMut() {
    mut[0] = true;  // Segmentation // {** changed 1.1 **}
    mut[1] = true;  // Gradient {** changed 1.1 **}
    mut[2] = true;  // Asymmetry {** changed 1.1 **}
    mut[3] = true;  // Radial Sym {** changed 1.1 **}
    mut[4] = true;  // Scaling Factor {** changed 1.1 **}
    mut[5] = false; // Mutation Size
    mut[6] = false; // Mutation Rate
    mut[7] = true;  // Tapering Twigs
    mut[8] = true;
}

var SwellType = {
        Swell: 1,
        Shrink: 2,
        Same: 3,
          properties: {
            1: {name: "Swell"},
            2: {name: "Shrink"},
            3: {name: "Same"}
          }
        };




function Chromosome() {
  return new Array(9); // indexed 0-8, unlike Pascal 1-based arrays.
}

var CompletenessType = {
        Single: 1,
        Double: 2,
          properties: {
            1: {name: "Single"},
            2: {name: "Double"}
          }
        };

var SpokesType = {
        NorthOnly: 1,
        NSouth: 2,
        Radial: 3,
          properties: {
            1: {name: "NorthOnly"},
            2: {name: "NSouth"},
            3: {name: "Radial"}
          }
        };

function personToString() {
    var htmlResult = 
        "Gene: " + this.gene + " DGene: ";
    for(i = 0; i < 10; i++) {
        htmlResult +=  SwellType.properties[this.dGene[i]].name; 
        if(i<9) htmlResult += ",";
    }
    htmlResult +=  " SegNoGene: " + this.segNoGene +  
        " SegDistGene: " + this.segDistGene +  
        " CompletenessGene: " + CompletenessType.properties[this.completenessGene].name +  
        " SpokesGene: " + SpokesType.properties[this.spokesGene].name +  
        " TrickleGene: " + this.trickleGene +  
        " MutSizeGene: " + this.mutSizeGene +  
        " MutProbGene: " + this.mutProbGene;
    return htmlResult;
}

function personToHtml() {
    var h4open = "<h4>";
    var h4close = "</h4>";
    var breaktag = "<br />";
    var htmlResult = h4open + name + h4close + 
        "Gene: " + this.gene + breaktag + "DGene: ";
    for(i = 0; i < 10; i++) {
        htmlResult +=  SwellType.properties[this.dGene[i]].name; 
        if(i<9) htmlResult += ",";
    }
    htmlResult += breaktag + "SegNoGene: " + this.segNoGene +  
        breaktag + "SegDistGene: " + this.segDistGene +  
        breaktag + "CompletenessGene: " + CompletenessType.properties[this.completenessGene].name +  
        breaktag + "SpokesGene: " + SpokesType.properties[this.spokesGene].name +  
        breaktag + "TrickleGene: " + this.trickleGene +  
        breaktag + "MutSizeGene: " + this.mutSizeGene +  
        breaktag + "MutProbGene: " + this.mutProbGene;
    return htmlResult;
}

function personSetForm(form) {
	var inputGene1 = form['gene1'];
	var inputGene2 = form['gene2'];
	var inputGene3 = form['gene3'];
	var inputGene4 = form['gene4'];
	var inputGene5 = form['gene5'];
	var inputGene6 = form['gene6'];
	var inputGene7 = form['gene7'];
	var inputGene8 = form['gene8'];
	var inputGene9 = form['gene9'];
	var inputDGene1 = form['dGene1'];
	var inputDGene2 = form['dGene2'];
	var inputDGene3 = form['dGene3'];
	var inputDGene4 = form['dGene4'];
	var inputDGene5 = form['dGene5'];
	var inputDGene6 = form['dGene6'];
	var inputDGene7 = form['dGene7'];
	var inputDGene8 = form['dGene8'];
	var inputDGene9 = form['dGene9'];
	var inputDGene10 = form['dGene10'];
	var inputCompletenessGene = form['completenessGene'];
	var inputSpokesGene = form['spokesGene'];
	var inputSegNoGene = form['segNoGene'];
	var inputSegDistGene = form['segDistGene'];
	var inputTrickleGene = form['trickleGene'];
	var inputMutSizeGene = form['mutSizeGene'];
	var inputMutProbGene = form['mutProbGene'];

	inputGene1.value = this.gene[0];
	inputGene2.value = this.gene[1];
	inputGene3.value = this.gene[2];
	inputGene4.value = this.gene[3];
	inputGene5.value = this.gene[4];
	inputGene6.value = this.gene[5];
	inputGene7.value = this.gene[6];
	inputGene8.value = this.gene[7];
	inputGene9.value = this.gene[8];
	

	inputDGene1.selectedIndex = this.dGene[0] - 1;
	inputDGene2.selectedIndex = this.dGene[1] - 1;
	inputDGene3.selectedIndex = this.dGene[2] - 1;
	inputDGene4.selectedIndex = this.dGene[3] - 1;
	inputDGene5.selectedIndex = this.dGene[4] - 1;
	inputDGene6.selectedIndex = this.dGene[5] - 1;
	inputDGene7.selectedIndex = this.dGene[6] - 1;
	inputDGene8.selectedIndex = this.dGene[7] - 1;
	inputDGene9.selectedIndex = this.dGene[8] - 1;
	inputDGene10.selectedIndex = this.dGene[9] - 1;


	inputCompletenessGene.selectedIndex = this.completenessGene - 1;
	inputSpokesGene.selectedIndex = this.spokesGene - 1;

	inputSegNoGene.value = this.segNoGene;
	inputSegDistGene.value = this.segDistGene;
	inputTrickleGene.value = this.trickleGene;
	inputMutSizeGene.value = this.mutSizeGene;
	inputMutProbGene.value = this.mutProbGene;

}

function personFromForm(form) {
	var inputGene1 = form['gene1'];
	var inputGene2 = form['gene2'];
	var inputGene3 = form['gene3'];
	var inputGene4 = form['gene4'];
	var inputGene5 = form['gene5'];
	var inputGene6 = form['gene6'];
	var inputGene7 = form['gene7'];
	var inputGene8 = form['gene8'];
	var inputGene9 = form['gene9'];
	
	this.gene[0] = Number(inputGene1.value);
	this.gene[1] = Number(inputGene2.value);
	this.gene[2] = Number(inputGene3.value);
	this.gene[3] = Number(inputGene4.value);
	this.gene[4] = Number(inputGene5.value);
	this.gene[5] = Number(inputGene6.value);
	this.gene[6] = Number(inputGene7.value);
	this.gene[7] = Number(inputGene8.value);
	this.gene[8] = Number(inputGene9.value);
	
	var inputDGene1 = form['dGene1'];
	var inputDGene2 = form['dGene2'];
	var inputDGene3 = form['dGene3'];
	var inputDGene4 = form['dGene4'];
	var inputDGene5 = form['dGene5'];
	var inputDGene6 = form['dGene6'];
	var inputDGene7 = form['dGene7'];
	var inputDGene8 = form['dGene8'];
	var inputDGene9 = form['dGene9'];
	var inputDGene10 = form['dGene10'];

	this.dGene[0] = Number(inputDGene1.selectedIndex) + 1;
	this.dGene[1] = Number(inputDGene2.selectedIndex) + 1;
	this.dGene[2] = Number(inputDGene3.selectedIndex) + 1;
	this.dGene[3] = Number(inputDGene4.selectedIndex) + 1;
	this.dGene[4] = Number(inputDGene5.selectedIndex) + 1;
	this.dGene[5] = Number(inputDGene6.selectedIndex) + 1;
	this.dGene[6] = Number(inputDGene7.selectedIndex) + 1;
	this.dGene[7] = Number(inputDGene8.selectedIndex) + 1;
	this.dGene[8] = Number(inputDGene9.selectedIndex) + 1;
	this.dGene[9] = Number(inputDGene10.selectedIndex) + 1;

	
	var inputCompletenessGene = form['completenessGene'];
	var inputSpokesGene = form['spokesGene'];
	
	this.completenessGene = Number(inputCompletenessGene.selectedIndex) + 1;
	this.spokesGene = Number(inputSpokesGene.selectedIndex) + 1;
	
	
	var inputSegNoGene = form['segNoGene'];
	var inputSegDistGene = form['segDistGene'];
	var inputTrickleGene = form['trickleGene'];
	var inputMutSizeGene = form['mutSizeGene'];
	var inputMutProbGene = form['mutProbGene'];
	
	this.segNoGene = Number(inputSegNoGene.value);
	this.segDistGene = Number(inputSegDistGene.value);
	this.trickleGene = Number(inputTrickleGene.value);
	this.mutSizeGene = Number(inputMutSizeGene.value);
	this.mutProbGene = Number(inputMutProbGene.value);

}

function Person() {
  this.gene = new Chromosome();
  this.dGene = new Array(10);
  this.segNoGene = 0;
  this.segDistGene = 0;
  this.completenessGene = CompletenessType.Single;
  this.spokesGene = SpokesType.NorthOnly;
  this.trickleGene = TRICKLE;
  this.mutSizeGene = 0;
  this.mutProbGene = 0;
  this.toHtml = personToHtml;
  this.toString = personToString;
  this.setForm = personSetForm;
  this.fromForm = personFromForm;
}

function makeGenes(genotype, a, b, c, d, e, f, g, h, i) {
    for(j = 0; j<10; j++) {
        genotype.dGene[j] = SwellType.Same;
    }
    genotype.segNoGene = 1;
    genotype.segDistGene = 150;
    genotype.completenessGene = CompletenessType.Double;
    genotype.spokesGene = SpokesType.NorthOnly;
    genotype.trickleGene = TRICKLE;
    genotype.mutSizeGene = (TRICKLE/2>>0) // Trickle div 2;
    genotype.mutProbGene = 10;
    genotype.gene[0] = a;
    genotype.gene[1] = b;
    genotype.gene[2] = c;
    genotype.gene[3] = d;
    genotype.gene[4] = e;
    genotype.gene[5] = f;
    genotype.gene[6] = g;
    genotype.gene[7] = h;
    genotype.gene[8] = i;
}

function chess (genotype) {
    makeGenes(genotype, 
    -TRICKLE, 
    3 * TRICKLE, 
    -3 * TRICKLE, 
    -3 * TRICKLE, 
    TRICKLE, 
    -2 * TRICKLE, 
    6 * TRICKLE, 
    -5 * TRICKLE, 
    7);
}

function basicTree(genotype) {
    makeGenes(genotype, -10, -20, -20, -15, -15, 0, 15, 15, 7);
    genotype.segNoGene = 2;
    genotype.segDistGene = 150;
    genotype.completenessGene = CompletenessType.Single;
    genotype.dGene[3] = SwellType.Shrink;
    genotype.dGene[4] = SwellType.Shrink;
    genotype.dGene[5] = SwellType.Shrink;
    genotype.dGene[8] = SwellType.Shrink;
    genotype.trickleGene = 9;
}

function insect(genotype) {
    makeGenes(
        genotype, 
        TRICKLE, 
        TRICKLE, 
        -4 * TRICKLE, 
        TRICKLE, 
        -TRICKLE, 
        -2 * TRICKLE, 
        8 * TRICKLE, 
        -4 * TRICKLE, 
        6);
}


function randInt(ceiling) {
    return Math.floor(Math.random() * ceiling) + 1;  
}

function direction(child) {
  if(randInt(2) == 2) 
    return child.mutSizeGene;
  else
    return -child.mutSizeGene;
}
function direction9() {
  if(randInt(2) == 2)
    return 1;
  else
    return -1;
}

function copyBiomorph(child, parent) {
    child.gene = parent.gene.slice();
    child.dGene = parent.dGene.slice();
    child.segNoGene = parent.segNoGene;
    child.segDistGene = parent.segDistGene;
    child.completenessGene = parent.completenessGene;
    child.spokesGene = parent.spokesGene;
    child.trickleGene = parent.trickleGene;
    child.mutSizeGene = parent.mutSizeGene;
    child.mutProbGene = parent.mutProbGene;
    return child;
}

const WORRYMAX = 4095;

function twoToThe(n) {
    switch(n) {
    case 0: 
        return 1;
    case 1: 
        return 2;
    case 2: 
        return 4;
    case 3: 
        return 8;
    case 4: 
        return 16;
    case 5: 
        return 32;
    case 6: 
        return 64;
    case 7: 
        return 128;
    case 8: 
        return 256;
    case 9: 
        return 512;
    case 10: 
        return 1024;
    case 11: 
        return 2048;
    case 12: 
        return 4096;
    default:
        return 8192;
    }
}

function randSwell(indGene) {
    switch(indGene) {
    case SwellType.Shrink: 
        return SwellType.Same;
    case SwellType.Same: 
        if(randInt(2) == 1) {
            return SwellType.Shrink;
        } else {
            return SwellType.Swell;
        }
    case SwellType.Swell: 
        return SwellType.Same;
    }
}


function reproduce(parent, drawingContext) {
    // // console.log("Reproduce");
    var child = new Person();
    copyBiomorph(child, parent);
    if(mut[6]) 
        if(randInt(100) < child.mutProbGene) 
            do 
              child.mutProbGene += direction9();
            while ((Math.abs(child.mutProbGene) > 100) || (child.mutProbGene == 0));
    for(j = 0; j<8; j++) 
        if(randInt(100) < child.mutProbGene) 
            child.gene[j] += direction(child);
    if(randInt(100) < child.mutProbGene) 
        child.gene[8] += direction9();
    if(child.gene[8] < 1) 
        child.gene[8] = 1;
    var sizeWorry = child.segNoGene * twoToThe(child.gene[8]);
    // // console.log("Gene9: " + child.gene[8] + "SegNoGene: " + child.segNoGene + " SizeWorry: " + sizeWorry);
    if(sizeWorry > WORRYMAX)  
        {child.gene[8]--; 
        // // console.log("Decrementing segNoGene");
        }
    if(mut[0]) 
        if(randInt(100) < child.mutProbGene) {
            var j = direction9();
            child.segNoGene += j;
            if(j > 0) {
                sizeWorry = child.segNoGene * twoToThe(child.gene[8]);
                if(sizeWorry > WORRYMAX) 
                    child.segNoGene--;
            }
        }
    if(child.segNoGene < 1) 
        child.segNoGene = 1;
    if((mut[1]) && (child.segNoGene > 1)) {
        for(j = 0; j<8; j++) 
            if(randInt(100) < child.mutProbGene/2>>0) 
                child.dGene[j] = randSwell(child.dGene[j]);
        if(randInt(100) < child.mutProbGene/2>>0) 
            child.dGene[9] = randSwell(child.dGene[9]);
    }
    if(mut[7])
        if((mut[8] && (randInt(100) < child.mutProbGene))) 
        	child.dGene[8] = randSwell(child.dGene[8]);
    if((mut[0]) && (child.segNoGene > 1)) 
        if(randInt(100) < child.mutProbGene) 
            child.segDistGene = child.segDistGene + direction9();
    if(mut[2]) 
        if(randInt(100) < child.mutProbGene/2>>0) 
            if(child.completenessGene == CompletenessType.Single) 
                child.completenessGene = CompletenessType.Double;
            else 
                child.completenessGene = CompletenessType.Single;
    if(mut[3]) 
        if(randInt(100) < child.mutProbGene/2>>0) 
            switch(child.spokesGene) {
            case SpokesType.NorthOnly: 
                child.spokesGene = SpokesType.NSouth;
            break;
            case SpokesType.NSouth: 
                if(direction9() == 1) 
                    child.spokesGene = SpokesType.Radial;
                else 
                    child.spokesGene = SpokesType.NorthOnly;
            break;
            case SpokesType.Radial: 
                child.spokesGene = SpokesType.NSouth;
            break;
            }
    if(mut[4]) 
        if(randInt(100) < Math.abs(child.mutProbGene)) {
            child.trickleGene += direction9();
            if(child.trickleGene < 1) 
                child.trickleGene = 1;
        }
    if(mut[5]) 
        if(randInt(100) < Math.abs(child.mutProbGene)) {
            child.mutSizeGene += direction9();
            if(child.mutSizeGene < 1) 
                child.mutSizeGene = 1;
        }
    return child;
} // reproduce