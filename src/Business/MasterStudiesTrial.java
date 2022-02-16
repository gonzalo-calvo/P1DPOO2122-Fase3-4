package Business;

public class MasterStudiesTrial extends Trial{

    private String masterName;
    private int creditNum;
    private int passProbability;

    public MasterStudiesTrial(String trialName, String masterName, int creditNum, int passProbability) {
        name = trialName;
        this.masterName = masterName;
        this.creditNum = creditNum;
        this.passProbability = passProbability;
    }

    public MasterStudiesTrial() {

    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public int getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
    }

    public int getPassProbability() {
        return passProbability;
    }

    public void setPassProbability(int passProbability) {
        this.passProbability = passProbability;
    }

    public void setValuesFromCSV(String line){
        String[] values = line.split("_");
        //System.out.println("line: " + line);
        name = values[1];
        masterName = values[2];
        creditNum = Integer.parseInt(values[3]);
        passProbability = Integer.parseInt(values[4]);
    }

    public String toCSV(){
        return "2_" + name + "_" + masterName + "_" + creditNum + "_" + passProbability;
    }

    public void printDetails(){
        System.out.println("\nTrial: " + this.name + " (Master studies)");
        System.out.println("Master: " + this.masterName);
        System.out.println("ECTS: " + this.creditNum + ", with a " + this.passProbability + "chance to pass each one");
    }
}
