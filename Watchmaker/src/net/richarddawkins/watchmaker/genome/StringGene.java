package net.richarddawkins.watchmaker.genome;

public class StringGene extends SimpleGene {

    public StringGene(Genome genome, String name) {
        super(genome, name);
       
    }

    @Override
    public boolean genomicallyEquals(Gene gene) {
        StringGene stringGene = (StringGene) gene;
       return this.getValue().equals(stringGene.getValue());
    }

    protected String value; 
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void geneManipulated(GeneManipulationEvent gbme) {
        // TODO Auto-generated method stub
        
    }

}
