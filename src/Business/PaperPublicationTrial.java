package Business;

public class PaperPublicationTrial extends Trial{
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




    public String getTrialNameDetail() {
        return name + " (Paper submission)";
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

    public void setValuesFromCSV(String line){
        String[] values = line.split("_");
        System.out.println("line: " + line);
        name = values[1];
        journalName = values[2];
        quartile = Integer.parseInt(values[3]);
        acceptanceProbability = Integer.parseInt(values[4]);
        revisionProbability = Integer.parseInt(values[5]);
        rejectionProbability = Integer.parseInt(values[6]);
    }

    public String toCSV(){
        return "1_" + name + "_" + journalName + "_" + quartile + "_" + acceptanceProbability + "_" + revisionProbability + "_" + rejectionProbability;
    }

    public void printDetails(){
        System.out.println("Trial: " + this.name + "(Paper publication)");
        System.out.println("Journal: " + this.journalName + " (" + this.quartile + ")");
        System.out.println("Chances: " + this.acceptanceProbability + "% acceptance, " + this.revisionProbability + "% revision, " + this.rejectionProbability + "% rejection\n");

    }





}
