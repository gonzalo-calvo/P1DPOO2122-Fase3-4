package Composer_Model;

public abstract class Trial {
    protected String name;
    protected int type;

    public Trial(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Trial() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}

