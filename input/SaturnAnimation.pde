public class SaturnAnimation {

    private Circle []starsForPrimary;
    private Circle []starsForSecondary;

    public SaturnAnimation() {
        starsForPrimary = new Circle[100];
        starsForSecondary = new Circle[100];

        for (int i = 0; i < 100; i++) {
            this.starsForPrimary[i] = new Circle();
            this.starsForPrimary[i].setR((int) random(width / 2));
            this.starsForPrimary[i].setAlpha(random(360));
            this.starsForPrimary[i].setX((int) (this.starsForPrimary[i].getR() * cos(this.starsForPrimary[i].getAlpha())) + width / 2);
            this.starsForPrimary[i].setY((int) (this.starsForPrimary[i].getR() * sin(this.starsForPrimary[i].getAlpha())) + height / 2);
        }

        for (int i = 0; i < 100; i++) {
            this.starsForSecondary[i] = new Circle();
            this.starsForSecondary[i].setR((int) random(width / 2));
            this.starsForSecondary[i].setAlpha(random(360));
            this.starsForSecondary[i].setX((int) (this.starsForSecondary[i].getR() * cos(this.starsForSecondary[i].getAlpha())) + width / 2);
            this.starsForSecondary[i].setY((int) (this.starsForSecondary[i].getR() * sin(this.starsForSecondary[i].getAlpha())) + height / 2);
        }
    }

    public void draw(int whatDraw) {
        float []adaptedSpectrum = animationHandler.adaptSpectrum(whatDraw);

        if (whatDraw == 1) {
            for (int i = 0; i < 100; i++) {
                stroke(settings.getColor(whatDraw)[0],
                       settings.getColor(whatDraw)[1],
                       settings.getColor(whatDraw)[2]);
                strokeWeight(2);

                this.starsForPrimary[i].alpha(adaptedSpectrum[adaptedSpectrum.length - 1]);
                
                fill(settings.getColor(whatDraw)[0],
                       settings.getColor(whatDraw)[1],
                       settings.getColor(whatDraw)[2]);
                
                this.starsForPrimary[i].setX((int) (this.starsForPrimary[i].getR() * cos(this.starsForPrimary[i].getAlpha())) + width / 2);
                this.starsForPrimary[i].setY((int) (this.starsForPrimary[i].getR() * sin(this.starsForPrimary[i].getAlpha())) + height / 2);

                if ((int) random(adaptedSpectrum[width / 8]) > 100) {
                    ellipse(this.starsForPrimary[i].getX(),
                            this.starsForPrimary[i].getY(),
                            6, 6);
                } else {
                    ellipse(this.starsForPrimary[i].getX(),
                            this.starsForPrimary[i].getY(),
                            3, 3);
                }
            }
        } else {
            for (int i = 0; i < 100; i++) {
                stroke(settings.getColor(whatDraw)[0],
                       settings.getColor(whatDraw)[1],
                       settings.getColor(whatDraw)[2]);
                strokeWeight(2);

                this.starsForSecondary[i].alpha(adaptedSpectrum[adaptedSpectrum.length - 1]);
                
                fill(settings.getColor(whatDraw)[0],
                       settings.getColor(whatDraw)[1],
                       settings.getColor(whatDraw)[2]);
                
                this.starsForSecondary[i].setX((int) (this.starsForSecondary[i].getR() * cos(this.starsForSecondary[i].getAlpha())) + width / 2);
                this.starsForSecondary[i].setY((int) (this.starsForSecondary[i].getR() * sin(this.starsForSecondary[i].getAlpha())) + height / 2);

                if ((int) random(adaptedSpectrum[width / 8]) > 100) {
                    ellipse(this.starsForSecondary[i].getX(),
                            this.starsForSecondary[i].getY(),
                            6, 6);
                } else {
                    ellipse(this.starsForSecondary[i].getX(),
                            this.starsForSecondary[i].getY(),
                            3, 3);
                }
            }
        }
    }

}
