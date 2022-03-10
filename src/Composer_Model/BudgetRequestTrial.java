package Composer_Model;

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

    public void setValuesFromCSV(String line){
        String[] values = line.split("_");
        //System.out.println("line: " + line);
        name = values[1];
        entityName = values[2];
        moneyAmount = Integer.parseInt(values[3]);
    }

    public String toCSV(){
        return "4_" + name + "_" + entityName + "_" + moneyAmount;
    }

    public void printDetails(){
        System.out.println("\nTrial: " + this.name + " (Budget request)");
        System.out.println("Entity: " + this.entityName);
        System.out.println("Budget: " + this.moneyAmount + " €");
    }
}
