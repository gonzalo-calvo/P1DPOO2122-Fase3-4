package Business.Trials;

/**
 * Subclasse MasterStudiesTrial de la classe pare trial
 */
public class MasterStudiesTrial extends Trial {

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


    /**
     * Mètode que se li passa una String i extreu valors per omplir informació de la trial
     * @param line String tipos CSV amb l'informació de la trial
     */
    public void setValuesFromCSV(String line){
        String[] values = line.split("_");
        name = values[1];
        masterName = values[2];
        creditNum = Integer.parseInt(values[3]);
        passProbability = Integer.parseInt(values[4]);
    }

    /**
     * Mètode que genera una string amb les dades de la trial per guarda-lo als fitxers csv
     * @return Retorna una string amb les dades de la trial
     */
    public String toCSV(){
        return "2_" + name + "_" + masterName + "_" + creditNum + "_" + passProbability;
    }



}
