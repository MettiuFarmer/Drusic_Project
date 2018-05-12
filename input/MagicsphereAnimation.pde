public class MagicsphereAnimation {

    // Empty class initializer
    public MagicsphereAnimation() {}

    public void draw(int whatDraw) {
        float []adaptedSpectrum = animationHandler.adaptSpectrum(whatDraw);

        if (max(adaptedSpectrum) != 0) {
            noFill();

            float magic = 0;
            for (int i = 0; i < 500; i++) {
                magic += adaptedSpectrum[i + 250];
            }

            stroke(settings.getColor(whatDraw)[0],
                settings.getColor(whatDraw)[1],
                settings.getColor(whatDraw)[2]);
            strokeWeight(1);

            pushMatrix();
            /**/
                translate(width / 2, height / 2);
                rotateX(magic * 0.0191);
                rotateY(magic * 0.0193);
                rotateZ(magic * 0.0192);
                map(magic,
                    min(adaptedSpectrum), max(adaptedSpectrum),
                    19, height / 2);
                sphereDetail((int) 11);
                sphere((int) (magic / 20));
            /**/
            popMatrix();

            delay(5);
        }
    }

}
