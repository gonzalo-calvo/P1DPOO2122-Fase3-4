package Composer_Model;

public class DoctoralThesisDefenseTrial extends Trial {


    private String fieldOfStudy;
    private int difficulty;

    public DoctoralThesisDefenseTrial(String trialName, String fieldOfStudy, int difficulty) {
        name = trialName;
        this.fieldOfStudy = fieldOfStudy;
        this.difficulty = difficulty;
    }

    public DoctoralThesisDefenseTrial() {
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setValuesFromCSV(String line){
        String[] values = line.split("_");
        //System.out.println("line: " + line);
        name = values[1];
        fieldOfStudy = values[2];
        difficulty = Integer.parseInt(values[3]);
    }

    public String toCSV(){
        return "3_" + name + "_" + fieldOfStudy + "_" + difficulty;
    }

    public void printDetails(){
        System.out.println("\nTrial: " + this.name + " (Doctoral thesis defense)");
        System.out.println("Field: " + this.fieldOfStudy);
        System.out.println("Difficulty: " + this.difficulty);
    }
}
