import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.io.*; 
import java.util.*; 
import java.sql.*; 
import java.awt.Desktop; 
import java.net.URI; 
import processing.sound.*; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class input extends PApplet {

// Imports








// PApplet variable initialization
PApplet myPApplet = this;

// Global variables declaration
MicrophoneInput microphone;
float []spectrumInitializer;
float []spectrum;
int numberOfBands;
Settings settings;
AnimationHandler animationHandler;

public void setup() {
    // Processing settings
    //fullScreen(P3D);
    
    

    // Variables initialization
    microphone = new MicrophoneInput();
    spectrumInitializer = new float[2048];
    spectrum = new float[1024];
    numberOfBands = 1024;
    settings = new Settings();
    animationHandler = new AnimationHandler();

    // Defaults initialization
    settings.changeColor(255, 153, 0, 1);
    settings.changeColor(0, 0, 0, 2);
    settings.changeColor(0, 0, 0, 3);
}

public void draw() {
    // Updating the actual spectrum
    microphone.getFft().analyze(spectrumInitializer);
    spectrum = createSpectrum();
    print(spectrum[1023]);
    print(" ");

    // Setting the background
    int []backgroundColor = settings.getColor(3);
    background(backgroundColor[0],
               backgroundColor[1],
               backgroundColor[2]);
    
    animationHandler.routeAnimation();
}

public float[] createSpectrum() {
    float[] spectrum = new float[2048];
    int count = 0;

    for (int i = 0; i < 1535; i++) {
        if (i % 3 == 0 && i <= 1000) {
            spectrum[i] = spectrumInitializer[count];
        } else if (i > 1000 && i % 2 == 0) {
            spectrum[i] = spectrumInitializer[count];
        } else {
            spectrum[i] = spectrumInitializer[count];
            count++;
        }
    }

    return spectrum;
}
public class AnimationHandler {

    OndaAnimation onda;

    public AnimationHandler() {
        // Initialize all the animations
        onda = new OndaAnimation();
    }

    public void routeAnimation() {
        switch (settings.getModelPrimary()) {
            case 0:
                break;
            
            case 1:
                onda.drawOnda(1);
                break;
            
            case 2:
                // saturn
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
                // onda
                break;
            
            case 2:
                // saturn
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
                adapted[i] = spectrum[i] * settings.getSensitivityPrimary() * pow(i + 1, 1.2f);
                medium = medium + adapted[i];
                map(adapted[i],
                    0, max(spectrum) * 200 * pow(i + 1, 1.2f),
                    0, height - 50);
            }
            medium /= 100;
            adapted[spectrum.length] = medium;
        } else {
            for (int i = 0; i < spectrum.length; i++) {
                adapted[i] = spectrum[i] * settings.getSensitivitySecondary() * pow(i + 1, 1.2f);
                medium = medium + adapted[i];
                map(adapted[i],
                    0, max(spectrum) * 200 * pow(i + 1, 1.2f),
                    0, height - 50);
            }
            medium /= 100;
            adapted[spectrum.length] = medium;
        }

        return adapted;
    }

}
public class MicrophoneInput {

    // Class variables
    AudioIn audioInput;
    FFT fft;

    public MicrophoneInput() {
        // Initialize and start the microphone audio input
        this.audioInput = new AudioIn(myPApplet, 0);
        this.audioInput.start();

        // Get the FFT from the audio input
        this.fft = new FFT(myPApplet, numberOfBands);
        this.fft.input(this.audioInput);
    }

    // Return the fft variable
    public FFT getFft() {
        return this.fft;
    }

}
public class OndaAnimation {

    // Empty class initializer
    public OndaAnimation() {}

    public void drawOnda(int whatDraw) {
        int spectrumLength = spectrum.length;

        float []adaptedSpectrum = animationHandler.adaptSpectrum(whatDraw);

        int i = 0;

        beginShape();
        while (i != width) {
            if (i != (spectrumLength - 1)) {
                stroke(settings.getColor(whatDraw)[0],
                       settings.getColor(whatDraw)[1],
                       settings.getColor(whatDraw)[2]);
                strokeWeight(3);
                noFill();
                curveVertex(i, height - 50 - adaptedSpectrum[i]);
                i++;
            }
        }
        endShape();
    }
}
public class Settings implements Serializable {

    // Class variables:

    // -> background variables
    int []colorBackground;

    // -> primary draw variables
    int []colorPrimary;
    int modelPrimary;
    int sensitivityPrimary;
    
    // -> secondary draw variables
    int []colorSecondary;
    int modelSecondary;
    int sensitivitySecondary;

    public Settings() {
        // Initialize class variables:

        // -> background variables
        this.colorBackground = new int[3];

        // -> primary draw variables
        this.colorPrimary = new int[3];
        this.sensitivityPrimary = 125;
        this.modelPrimary = 1;

        // -> secondary draw variables
        this.colorSecondary = new int[3];
        this.sensitivitySecondary = 125;
        this.modelSecondary = 0;
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

    public int getModelPrimary() {
        return this.modelPrimary;
    }
    
    public int getModelSecondary() {
        return this.modelSecondary;
    }

    public int getSensitivityPrimary() {
        return this.sensitivityPrimary;
    }
    
    public int getSensitivitySecondary() {
        return this.sensitivitySecondary;
    }

}
  public void settings() {  size(1000, 800);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "input" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
