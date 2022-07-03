package Business.Trials;

/**
 * Subclasse DoctoralThesisDefenseTrial de la classe pare trial
 */
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


    /**
     * Mètode que se li passa una String i extreu valors per omplir informació de la trial
     * @param line String tipos CSV amb l'informació de la trial
     */
    public void setValuesFromCSV(String line){
        String[] values = line.split("_");
        name = values[1];
        fieldOfStudy = values[2];
        difficulty = Integer.parseInt(values[3]);
    }

    /**
     * Mètode que genera una string amb les dades de la trial per guarda-lo als fitxers csv
     * @return Retorna una string amb les dades de la trial
     */
    public String toCSV(){
        return "3_" + name + "_" + fieldOfStudy + "_" + difficulty;
    }



}
