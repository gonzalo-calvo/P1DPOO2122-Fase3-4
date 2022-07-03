package Business.Players;

/**
 * Classe DoctorPlayer es una classe filla de la classe player
 */
public class DoctorPlayer extends Player {

    public DoctorPlayer() {
    }

    public DoctorPlayer(String name, int level, int investigationPoints) {
        super(name, level, investigationPoints);
    }

    /**
     * @param points     Quantitat de punts generals abans de gestionar per la classe fill i finalment guardar-los
     * @param trial_type Tipos de trial per identificar si canvia de tipos de player
     */
    @Override
    public void addPoints(int points, int trial_type){
        if(points > 0){ // WIN
            if(trial_type == this.level+1){
                this.investigationPoints = 10;
            }else {
                this.investigationPoints += points * 2;
            }
        }else{ // LOSE
            this.investigationPoints += points/2;
        }
    }

}
