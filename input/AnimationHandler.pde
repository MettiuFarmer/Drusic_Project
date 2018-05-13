public class AnimationHandler {

    private OndaAnimation onda;
    private SaturnAnimation saturn;
    private HyperlineAnimation hyperLine;
    private MagicsphereAnimation magicSphere;
    public int numberOfAnimations;

    public AnimationHandler() {
        // Initialize all the animations
        this.onda = new OndaAnimation();
        this.saturn = new SaturnAnimation();
        this.hyperLine = new HyperlineAnimation();
        this.magicSphere = new MagicsphereAnimation();

        this.numberOfAnimations = 4;
    }

    public void routeAnimation() {
        switch (settings.getModelPrimary()) {
            case 0:
                break;
            
            case 1:
                this.onda.draw(1);
                break;
            
            case 2:
                this.saturn.draw(1);
                break;
            
            case 3:
                this.hyperLine.draw(1);
                break;
            
            case 4:
                this.magicSphere.draw(1);
                break;
            
            default:
                break;	
        }

        switch (settings.getModelSecondary()) {
            case 0:
                break;
            
            case 1:
                this.onda.draw(2);
                break;
            
            case 2:
                this.saturn.draw(2);
                break;
            
            case 3:
                this.hyperLine.draw(2);
                break;
            
            case 4:
                this.magicSphere.draw(2);
                break;
            
            default:
                break;	
        }
    }

    public float[] adaptSpectrum(int whatDraw) {
        float medium = 0;
        float []adapted = new float[spectrum.length + 1];

        if (whatDraw == 1) {
            for (int i = 0; i < spectrum.length; i++) {
                if (max(spectrum) == 0) {
                    break;
                }
                adapted[i] = spectrum[i] * settings.getSensitivityPrimary() * pow(i + 1, 1.2);
                medium = medium + adapted[i];
                map(adapted[i],
                    0, max(spectrum) * 200 * pow(i + 1, 1.2),
                    0, height - 50);
            }
            medium /= 100;
            adapted[spectrum.length] = medium;
        } else {
            for (int i = 0; i < spectrum.length; i++) {
                if (max(spectrum) == 0) {
                    break;
                }
                adapted[i] = spectrum[i] * settings.getSensitivitySecondary() * pow(i + 1, 1.2);
                medium = medium + adapted[i];
                map(adapted[i],
                    0, max(spectrum) * 200 * pow(i + 1, 1.2),
                    0, height - 50);
            }
            medium /= 100;
            adapted[spectrum.length] = medium;
        }

        return adapted;
    }

}
