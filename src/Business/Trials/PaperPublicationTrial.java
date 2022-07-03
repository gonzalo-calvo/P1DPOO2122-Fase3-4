package Business.Trials;

/**
 * Subclasse PaperPublicationTrial de la classe pare trial
 */
public class PaperPublicationTrial extends Trial {

    private String journalName;
    private int quartile;
    private int acceptanceProbability;
    private int revisionProbability;
    private int rejectionProbability;


    public PaperPublicationTrial(String trialName, String journalName, int quartile, int acceptanceProbability, int revisionProbability, int rejectionProbability) {
        name = trialName;
        this.journalName = journalName;
        this.quartile = quartile;
        this.acceptanceProbability = acceptanceProbability;
        this.revisionProbability = revisionProbability;
        this.rejectionProbability = rejectionProbability;

    }

    public PaperPublicationTrial() {
    }


    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public int getQuartile() {
        return quartile;
    }

    public void setQuartile(int quartile) {
        this.quartile = quartile;
    }

    public int getAcceptanceProbability() {
        return acceptanceProbability;
    }

    public void setAcceptanceProbability(int acceptanceProbability) {
        this.acceptanceProbability = acceptanceProbability;
    }

    public int getRevisionProbability() {
        return revisionProbability;
    }

    public void setRevisionProbability(int revisionProbability) {
        this.revisionProbability = revisionProbability;
    }

    public int getRejectionProbability() {
        return rejectionProbability;
    }

    public void setRejectionProbability(int rejectionProbability) {
        this.rejectionProbability = rejectionProbability;
    }


    /**
     * Mètode que se li passa una String i extreu valors per omplir informació de la trial
     * @param line String tipos CSV amb l'informació de la trial
     */
    public void setValuesFromCSV(String line){
        String[] values = line.split("_");
        name = values[1];
        journalName = values[2];
        quartile = Integer.parseInt(values[3]);
        acceptanceProbability = Integer.parseInt(values[4]);
        revisionProbability = Integer.parseInt(values[5]);
        rejectionProbability = Integer.parseInt(values[6]);
    }

    /**
     * Mètode que genera una string amb les dades de la trial per guarda-lo als fitxers csv
     * @return Retorna una string amb les dades de la trial
     */
    public String toCSV(){
        return "1_" + name + "_" + journalName + "_" + quartile + "_" + acceptanceProbability + "_" + revisionProbability + "_" + rejectionProbability;
    }



}
