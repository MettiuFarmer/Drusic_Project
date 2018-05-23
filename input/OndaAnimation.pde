public class OndaAnimation {

    // Empty class initializer
    public OndaAnimation() {}

    public void draw(int whatDraw) {
        int spectrumLength = spectrum.length;

        float []adaptedSpectrum = animationHandler.adaptSpectrum(whatDraw);

        int i = 0;

        beginShape();
        while (i != width) {
            if (i != (spectrumLength - 1)) {
                stroke(rgbToHsb(settings.getColor(whatDraw)[0],
                                settings.getColor(whatDraw)[1],
                                settings.getColor(whatDraw)[2], 255));
                strokeWeight(3);
                noFill();
                curveVertex(i, height - 50 - adaptedSpectrum[i]);
                i++;
            }
        }
        endShape();
    }
}
