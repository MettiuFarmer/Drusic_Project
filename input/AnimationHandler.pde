public class AnimationHandler {

    OndaAnimation onda;
    SaturnAnimation saturn;

    public AnimationHandler() {
        // Initialize all the animations
        onda = new OndaAnimation();
        saturn = new SaturnAnimation();
    }

    public void routeAnimation() {
        switch (settings.getModelPrimary()) {
            case 0:
                break;
            
            case 1:
                onda.draw(1);
                break;
            
            case 2:
                saturn.draw(1);
                break;
            
            case 3:
                // hyper line
                break;
            
            case 4:
                // magic sphere
                break;
            
            default:
                break;	
        }

        switch (settings.getModelSecondary()) {
            case 0:
                break;
            
            case 1:
                onda.draw(2);
                break;
            
            case 2:
                saturn.draw(2);
                break;
            
            case 3:
                // hyper line
                break;
            
            case 4:
                // magic sphere
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
