package Business.Trials;

/**
 * Subclasse BudgetRequestTrial de la classe pare trial
 */
public class BudgetRequestTrial extends Trial {

    private String entityName;
    private int moneyAmount;


    public BudgetRequestTrial(String trialName, String entityName, int moneyAmount) {
        name = trialName;
        this.entityName = entityName;
        this.moneyAmount = moneyAmount;
    }

    public BudgetRequestTrial() {
    }


    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }


    /**
     * Mètode que se li passa una String i extreu valors per omplir informació de la trial
     * @param line String tipos CSV amb l'informació de la trial
     */
    public void setValuesFromCSV(String line){
        String[] values = line.split("_");
        name = values[1];
        entityName = values[2];
        moneyAmount = Integer.parseInt(values[3]);
    }

    /**
     * Mètode que genera una string amb les dades de la trial per guarda-lo als fitxers csv
     * @return Retorna una string amb les dades de la trial
     */
    public String toCSV(){
        return "4_" + name + "_" + entityName + "_" + moneyAmount;
    }


}
