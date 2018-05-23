public class HyperlineAnimation {

    private Circle []circlesForPrimary;
    private Circle []circlesForSecondary;

    public HyperlineAnimation () {
        circlesForPrimary = new Circle[5];
        circlesForSecondary = new Circle[5];

        for (int i = 0; i < 5; i++) {
            this.circlesForPrimary[i] = new Circle();
            this.circlesForPrimary[i].setX((i + 1) * width / 6);
            this.circlesForPrimary[i].setY(height / 2);
        }

        for (int i = 0; i < 5; i++) {
            this.circlesForSecondary[i] = new Circle();
            this.circlesForSecondary[i].setX((i + 1) * width / 6);
            this.circlesForSecondary[i].setY(height / 2);
        }
    }

    public void draw(int whatDraw) {
        float []adaptedSpectrum = animationHandler.adaptSpectrum(whatDraw);
        float []medium = new float[5];

        if (whatDraw == 1) {
            for (int i = 0; i < 5; i++) {
                medium[i] = 0;
                for (int j = (2045 / 5) * i; j < (2045 / 5) * i + 409; j++) {
                    medium[i] += adaptedSpectrum[j];
                }
                medium[i] /= 500;
                this.circlesForPrimary[i].setY((int) ((height / 10) * 9 - medium[i]));
                this.circlesForPrimary[i].show(whatDraw);
            }

            for (int i = 0; i < 4; i++) {
                line(this.circlesForPrimary[i].getX(),
                     this.circlesForPrimary[i].getY(),
                     this.circlesForPrimary[i + 1].getX(),
                     this.circlesForPrimary[i + 1].getY());
            }
        } else {
            for (int i = 0; i < 5; i++) {
                medium[i] = 0;
                for (int j = (2045 / 5) * i; j < (2045 / 5) * i + 409; j++) {
                    medium[i] += adaptedSpectrum[j];
                }
                medium[i] /= 500;
                this.circlesForSecondary[i].setY((int) ((height / 10) * 9 - medium[i]));
                this.circlesForSecondary[i].show(whatDraw);
            }

            for (int i = 0; i < 4; i++) {
                line(this.circlesForSecondary[i].getX(),
                     this.circlesForSecondary[i].getY(),
                     this.circlesForSecondary[i + 1].getX(),
                     this.circlesForSecondary[i + 1].getY());
            }
        }
    }

}
