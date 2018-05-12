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
input myPApplet = this;

// Global variables declaration
int numberOfBands;
float []spectrumInitializer;
float []spectrum;
MicrophoneInput microphone;
Settings settings;
AnimationHandler animationHandler;

public void setup() {
    // Processing settings
    //fullScreen(P3D);
    
    

    // Variables initialization
    numberOfBands = 1024;
    spectrumInitializer = new float[numberOfBands];
    spectrum = new float[2048];
    microphone = new MicrophoneInput();
    settings = new Settings();
    animationHandler = new AnimationHandler();

    // Defaults initialization
    settings.changeColor(255, 153, 0, 1);
    settings.changeColor(254, 254, 254, 2);
    settings.changeColor(0, 0, 0, 3);
}

public void draw() {
    // Updating the actual spectrum
    microphone.getFft().analyze(spectrumInitializer);
    spectrum = createSpectrum();

    // Setting the background
    int []backgroundColor = settings.getColor(3);
    background(backgroundColor[0],
               backgroundColor[1],
               backgroundColor[2]);
    
    animationHandler.routeAnimation();
}

public float[] createSpectrum() {
    float[] newSpectrum = new float[2048];
    int count = 0;

    for (int i = 0; i < 1535; i++) {
        if (i % 3 == 0 && i <= 1000) {
            newSpectrum[i] = spectrumInitializer[count];
        } else if (i > 1000 && i % 2 == 0) {
            newSpectrum[i] = spectrumInitializer[count];
        } else {
            newSpectrum[i] = spectrumInitializer[count];
            count++;
        }
    }

    return newSpectrum;
}
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
                if (max(spectrum) == 0) {
                    break;
                }
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
class Circle {

    int x;  // Center of the circle: X
    int y;  // Center of the circle: Y
    int r;
    float alpha;
    int yspeed = 3;  // Speed of the animation to move the circle
    int id;
    int[] colore;

    Circle(int X, int Y) {
        this.x = X;
        this.y = Y;
        this.colore = new int[3];
    }

    public Circle(){}

    // Function to move the circle up
    public void up(int a) {
        this.yspeed = a;
        if (this.y > 150) {
            this.y = this.y - this.yspeed;
        }
    }

    // Function to move the circle down
    public void down(int a) {
        this.yspeed = a;
        if (this.y < 450) {
            this.y = this.y + this.yspeed;
        }
    }

    // Makes the circle visible
    public void show(int coloDisegno) {
        stroke(settings.getColor(coloDisegno)[0], settings.getColor(coloDisegno)[1], settings.getColor(coloDisegno)[2]);
        strokeWeight(3);
        fill(0);
        ellipse(this.x, this.y, 50, 50);
        fill(settings.getColor(coloDisegno)[0], settings.getColor(coloDisegno)[1], settings.getColor(coloDisegno)[2]);
        ellipse(this.x, this.y, 10, 10);
    }

    public void muovi(float a, float b, float c, float d) {}

    public void setColor(int a, int b, int c) {
        this.colore = new int[]{a, b, c};
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int a) {
        this.x = a;
    }

    public void setY(int a) {
        this.y = a;
    }

    public void toPolar() {
        float x2 = pow(this.x, 2);
        float y2 = pow(this.y, 2);

        this.r = (int) sqrt(x2 + y2);

        this.alpha = atan(this.y / this.x);
    }

    public void alpha(float f) {
        f = map(f, 0, 10, 0, 7);
        this.alpha += (f * 0.001f);
    }

    public float getAlpha() {
        return this.alpha;
    }

    public int getR() {
        return this.r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setAlpha(float a) {
        this.alpha = a;
    }

}
public class MicrophoneInput {

    // Class variables
    private AudioIn audioInput;
    private FFT fft;

    public MicrophoneInput() {
        // Initialize the FFT and the microphone audio input
        this.fft = new FFT(myPApplet, numberOfBands);
        this.audioInput = new AudioIn(myPApplet, 0);

        // Start the microphone audio input and get the FFT from the it
        this.audioInput.start();
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

    public void draw(int whatDraw) {
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
        this.sensitivityPrimary = 50;
        this.modelPrimary = 2;

        // -> secondary draw variables
        this.colorSecondary = new int[3];
        this.sensitivitySecondary = 25;
        this.modelSecondary = 2;
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
