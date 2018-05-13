public class Settings implements Serializable {

    // Class variables:

    // -> background variables
    int []colorBackground;

    // -> primary draw variables
    int []colorPrimary;
    int modelPrimary;
    float sensitivityPrimary;
    
    // -> secondary draw variables
    int []colorSecondary;
    int modelSecondary;
    float sensitivitySecondary;

    public Settings() {
        // Initialize class variables:

        // -> background variables
        this.colorBackground = new int[3];

        // -> primary draw variables
        this.colorPrimary = new int[3];
        this.sensitivityPrimary = 4;
        this.modelPrimary = 2;

        // -> secondary draw variables
        this.colorSecondary = new int[3];
        this.sensitivitySecondary = 8;
        this.modelSecondary = 4;
    }

    // Method to change an environment color
    /*
    ofWhat {
        1 -> primary color
        2 -> secondary color
        3 -> background color
    }
    */
    public void changeColor(int r, int g, int b, int ofWhat) {
        switch (ofWhat) {
            case 1:
                this.colorPrimary[0] = r;
                this.colorPrimary[1] = g;
                this.colorPrimary[2] = b;
                break;
            
            case 2:
                this.colorSecondary[0] = r;
                this.colorSecondary[1] = g;
                this.colorSecondary[2] = b;
                break;
            
            case 3:
                this.colorBackground[0] = r;
                this.colorBackground[1] = g;
                this.colorBackground[2] = b;
                break;
            
            default:
                break;
        }
    }

    // Return an environment color
    /*
    ofWhat {
        1 -> primary color
        2 -> secondary color
        3 -> background color
    }
    */
    public int[] getColor(int ofWhat) {
        switch (ofWhat) {
            case 1:
                return this.colorPrimary;
            
            case 2:
                return this.colorSecondary;
            
            case 3:
                return this.colorBackground;
            
            default :
                return new int[]{0, 0, 0};
        }
    }

    public void setModelPrimary(int animationNumber) {
        this.modelPrimary = animationNumber;
    }

    public void setModelSecondary(int animationNumber) {
        this.modelSecondary = animationNumber;
    }

    public int getModelPrimary() {
        return this.modelPrimary;
    }
    
    public int getModelSecondary() {
        return this.modelSecondary;
    }

    public void setSensitivityPrimary(float sensValue) {
        this.sensitivityPrimary = sensValue;
    }
    
    public void setSensitivitySecondary(float sensValue) {
        this.sensitivitySecondary = sensValue;
    }

    public float getSensitivityPrimary() {
        return this.sensitivityPrimary;
    }
    
    public float getSensitivitySecondary() {
        return this.sensitivitySecondary;
    }

}
