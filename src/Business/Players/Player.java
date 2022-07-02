package Business.Players;

/**
 * La classe player de tipus abstracta i crearà objectes per tal de que juguin les partides
 */
public abstract class Player {

    protected String name;
    protected int level;
    protected int investigationPoints;

    public Player(String name, int level, int investigationPoints) {
        this.name = name;
        this.level = level;
        this.investigationPoints = investigationPoints;
    }

    public Player() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAlive() {
        return investigationPoints >= 1;
    }

    public int getInvestigationPoints() {
        return investigationPoints;
    }

    public void setInvestigationPoints(int investigationPoints) {
        this.investigationPoints = investigationPoints;
    }


    /**
     * Mètode que genera una string amb les dades del jugador per guarda-lo als fitxers csv
     * @return Retorna una string amb les dades del jugador
     */
    public String toCSV(){
        return name + "-" + investigationPoints +  "-" + level;
    }

    /**
     * Mètode que se li passa una String i extreu valors per omplir informació de cada jugador
     * @param line String tipos CSV amb l'informació del jugador
     */
    public void setPlayerValuesFromCSV(String line){
        String[] values = line.split("-");
        name = values[0];
        investigationPoints = Integer.parseInt(values[1]);
        //System.out.println("Value of values[2]: " + values[2]);
        //System.out.println("Before: " + setTrueFalseFromString(values[2]));
        //System.out.println("After: " + alive);
        level = Integer.parseInt(values[2]);
    }

    /**
     * Mètode canviat mitjançant override en el que, segons el tipos de jugador, guardarà una puntuació o una altre ja que cada "fill" tindrà una puntuació o una altre
     * @param points Quantitat de punts generals abans de gestionar per la classe fill i finalment guardar-los
     * @param trial_type Tipos de trial per identificar si canvia de tipos de player
     */
   public void addPoints(int points, int trial_type){
    }

}
