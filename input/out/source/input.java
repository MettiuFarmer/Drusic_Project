import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.io.*; 
import java.util.*; 
import java.sql.*; 
import java.awt.Desktop; 
import java.net.URI; 
import javax.swing.*; 
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
URI aboutMeLink;
MicrophoneInput microphone;
Settings settings;
AnimationHandler animationHandler;
SettingsWindow settingsWindow;

public void setup() {
    // Processing settings
    
    //size(1650, 800);
    
    colorMode(HSB, TWO_PI, 1.0f, 1.0f, 1);

    // Variables initialization
    numberOfBands = 1024;
    spectrumInitializer = new float[numberOfBands];
    spectrum = new float[2048];
    try {
        aboutMeLink = new URI("http://80.22.95.8/classiquinte/5Ain/fattore.matteo/Progetto_Drusic_Website/index.php");
    } catch (Exception e) {}
    microphone = new MicrophoneInput();
    settings = new Settings();
    animationHandler = new AnimationHandler();
    settingsWindow = new SettingsWindow();

    // Defaults initialization
    settings.changeColor(255, 255, 255, 1);
    settings.changeColor(255, 153, 0, 2);
    settings.changeColor(0, 0, 0, 3);
    settingsWindow.initializeWindowComponents();
}

public void draw() {
    // Updating the actual spectrum
    microphone.getFft().analyze(spectrumInitializer);
    spectrum = createSpectrum();

    // Setting the background
    int []backgroundColor = settings.getColor(3);
    int bgColor = rgbToHsb(backgroundColor[0],
                             backgroundColor[1],
                             backgroundColor[2], 255);
    background(bgColor);
    
    animationHandler.routeAnimation();
    if (settingsWindow.isVisibile()) {
        settingsWindow.showWindow();
    }
}

public float[] createSpectrum() {
    float[] newSpectrum = new float[2048];
    int count = 0;

    /*
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
    */

    for (int i = 0; i < 2048; i += 2) {
        newSpectrum[i] = spectrumInitializer[count];
        newSpectrum[i + 1] = spectrumInitializer[count];
        count++;
    }

    return newSpectrum;
}

public int rgbToHsb(int redColor, int greenColor, int blueColor, int alphaColor) {
    float r = PApplet.parseFloat(redColor);
    float g = PApplet.parseFloat(greenColor);
    float b = PApplet.parseFloat(blueColor);

    float b_ = 0;
    float h = 0;
    float s = 0;

    float var_r = r / 255;
    float var_g = g / 255;
    float var_b = b / 255;

    float var_min = min(var_r, var_g, var_b);
    float var_max = max(var_r, var_g, var_b);
    float del_max = var_max - var_min;

    b_ = var_max;

    if (del_max == 0) {
        h = 0;
        s = 0;
    } else {
        s = del_max / var_max;

        float del_r = (((var_max - var_r) / 6) + (del_max / 2)) / del_max;
        float del_g = (((var_max - var_g) / 6) + (del_max / 2)) / del_max;
        float del_b = (((var_max - var_b) / 6) + (del_max / 2)) / del_max;

        if (var_r == var_max) {
            h = del_b - del_g;
        } else if (var_g == var_max) {
            h = (1.0f / 3.0f) + del_r - del_b;
        } else if (var_b == var_max) {
            h = (2.0f / 3.0f) + del_g - del_r;
        }

        if (h < 0) {
            h += 1;
        }
        if (h > 1) {
            h -= 1;
        }

        h = h * TWO_PI;
    }
    return color(h, s, b_, map(alphaColor, 0, 255, 0, 1));
}

public int[] hsbToRgb(float hRadians, float s, float v) {
    float h = map(hRadians, 0, TWO_PI, 0, 1);
    
    float r, g, b, i, f, p, q, t;
    r = 0;
    g = 0;
    b = 0;

    i = floor(h * 6);
    f = h * 6 - i;
    p = v * (1 - s);
    q = v * (1 - f * s);
    t = v * (1 - (1 - f) * s);

    switch (PApplet.parseInt(i % 6)) {
        case 0:
            r = v;
            g = t;
            b = p;
            break;
        
        case 1:
            r = q;
            g = v;
            b = p;
            break;
        
        case 2:
            r = p;
            g = v;
            b = t;
            break;
        
        case 3:
            r = p;
            g = q;
            b = v;
            break;
        
        case 4:
            r = t;
            g = p;
            b = v;
            break;
        
        case 5:
            r = v;
            g = p;
            b = q;
            break;
    }

    return new int[]{Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)};
}

public void keyPressed() {
    if (key == 's' || key == 'S') {
        settingsWindow.changeVisibility();
    }
    // TODO: key to charge preset
}
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
        stroke(rgbToHsb(settings.getColor(coloDisegno)[0], settings.getColor(coloDisegno)[1], settings.getColor(coloDisegno)[2], 255));
        strokeWeight(3);
        fill(0);
        ellipse(this.x, this.y, 50, 50);
        fill(rgbToHsb(settings.getColor(coloDisegno)[0], settings.getColor(coloDisegno)[1], settings.getColor(coloDisegno)[2], 255));
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
class CustomColorPicker {

    int
        ColorPickerX, //color picker horizontal position
        ColorPickerY, //color picker vertical position
        LineY; //hue line vertical position
    float
        CrossX, //saturation+brightness cross horizontal position
        CrossY, //saturation+brightness cross horizontal position
        ColorSelectorX = 100, //color selector button horizontal position <------------------------------------------- CHANGE
        ColorSelectorY = 100; //color selector button vertical position   <------------------------------------------- CHANGE

    boolean
        isDraggingCross = false, //check if mouse is dragging the cross
        isDraggingLine = false, //check if mouse is dragging the line
        ShowColorPicker = true; //toggle color picker visibility (even = not visible, odd = visible) 

    int
        activeColor = color(PI, 0.5f, 0.5f), //contain the selected color  
        interfaceColor = color(2*PI, 1.0f, 1.0f); //change as you want               <------------------------------------------- CHANGE

    CustomColorPicker(int pickx, int picky, int startColor) {
        ColorPickerX = pickx;
        ColorPickerY = picky;
        activeColor = startColor;
        /*
        LineY = ColorPickerY + int(hue(activeColor)); //set initial Line position
        CrossX = ColorPickerX + saturation(activeColor)*255; //set initial Line position
        CrossY = ColorPickerY + brightness(activeColor)*255; //set initial Line position
        */
        LineY = ColorPickerY + PApplet.parseInt(hue(activeColor) * 40.58f);
        CrossX = ColorPickerX + (saturation(activeColor) * 255);
        CrossY = -(ColorPickerY + (brightness(activeColor) * 255) - 255);
    }

    public void update() {
        checkMouse();
        activeColor = color( (LineY - ColorPickerY)/40.58f , (CrossX - ColorPickerX)/255.0f , (255 - ( CrossY - ColorPickerY ))/255.0f ); //set current active color
    }

    public void display() {
        //drawColorSelector(); 
        drawColorPicker();
        drawLine();
        drawCross();
        //drawActiveColor();
        //drawValues();
        //drawOK();
    }

    public void drawColorSelector() {
        stroke( interfaceColor );
        strokeWeight( 1 );
        fill( 0 );
        rect( ColorSelectorX , ColorSelectorY , 20 , 20 ); //draw color selector border at its x y position
        stroke( 0 );
        fill( activeColor );
        rect( ColorSelectorX + 1 , ColorSelectorY + 1 , 18 , 18 ); //draw the color selector fill 1px inside the border
    }

    public void drawOK() {
        if ( mouseX > ColorPickerX + 285 && mouseX < ColorPickerX + 305 && mouseY > ColorPickerY + 240 && mouseY < ColorPickerY + 260 ) //check if the cross is on the darker color
        fill(0); //optimize visibility on ligher colors
        else
        fill(100); //optimize visibility on darker colors
        text( "OK", ColorPickerX + 285, ColorPickerY + 250 );
    }

    public void drawValues() {
        fill( 2*PI );
        //fill( 0 );
        textSize( 10 );

        text( "H: " + PApplet.parseInt( ( LineY - ColorPickerY ) * 1.417647f ) + "\u00b0", ColorPickerX + 285, ColorPickerY +15);
        text( "S: " + PApplet.parseInt( ( CrossX - ColorPickerX ) * 0.39215f + 0.5f ) + "%", ColorPickerX + 286, ColorPickerY + 30 );
        text( "B: " + PApplet.parseInt( 100 - ( ( CrossY - ColorPickerY ) * 0.39215f ) ) + "%", ColorPickerX + 285, ColorPickerY + 45 );

        text( "R: " + PApplet.parseInt( red( activeColor )*40.85f ), ColorPickerX + 285, ColorPickerY + 70 );
        text( "G: " + PApplet.parseInt( green( activeColor )*255 ), ColorPickerX + 285, ColorPickerY + 85 );
        text( "B: " + PApplet.parseInt( blue( activeColor )*255 ), ColorPickerX + 285, ColorPickerY + 100 );

        text( hex( activeColor, 6 ), ColorPickerX + 285, ColorPickerY + 125 );
    }

    public void drawCross() {
        if ( brightness( activeColor ) < 0.35f )
        stroke( 2*PI );
        else
        stroke( 0 );

        line( CrossX - 5, CrossY, CrossX + 5, CrossY );
        line( CrossX, CrossY - 5, CrossX, CrossY + 5 );
    }

    public void drawLine() {
        stroke(0);
        line( ColorPickerX + 259, LineY, ColorPickerX + 276, LineY );
    }

    public void drawColorPicker() {
        stroke( interfaceColor );
        for ( int j = 0; j < 255; j++ ) { //draw a row of pixel with the same brightness but progressive saturation
            for ( int i = 0; i < 255; i++ ) //draw a column of pixel with the same saturation but progressive brightness
                set( ColorPickerX + j, ColorPickerY + i, color((LineY - ColorPickerY)/40.58f, j/255.0f, (255 - i)/255.0f ) );
        }

        for ( int j = 0; j < 255; j++ ) {
            for ( int i = 0; i < 20; i++ )
                set( ColorPickerX + 258 + i, ColorPickerY + j, color( j/40.58f, 1.0f, 1.0f ) );
        }
    }

    public void drawActiveColor() {
        fill( activeColor );
        stroke( 0 );
        strokeWeight( 1 );
        rect( ColorPickerX + 258, ColorPickerY+280, 40, 40 );
    }

    public void checkMouse() {
        if ( mousePressed ) {
            if (mouseX>ColorPickerX+258&&mouseX<ColorPickerX+277&&mouseY>ColorPickerY-1&&mouseY<ColorPickerY+255&&!isDraggingCross) {
                LineY=mouseY;
                isDraggingLine = true;
            }

            if (mouseX>ColorPickerX-1&&mouseX<ColorPickerX+255&&mouseY>ColorPickerY-1&&mouseY<ColorPickerY+255&&!isDraggingLine) {
                CrossX=mouseX;
                CrossY=mouseY;
                isDraggingCross = true;
            }
        } else {
            isDraggingCross = false;
            isDraggingLine = false;
        }
    }

}
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

            stroke(rgbToHsb(settings.getColor(whatDraw)[0],
                            settings.getColor(whatDraw)[1],
                            settings.getColor(whatDraw)[2], 255));
            strokeWeight(1);

            pushMatrix();
            /**/
                translate(width / 2, height / 2);
                rotateX(magic * 0.0191f);
                rotateY(magic * 0.0193f);
                rotateZ(magic * 0.0192f);
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
                stroke(rgbToHsb(settings.getColor(whatDraw)[0],
                                settings.getColor(whatDraw)[1],
                                settings.getColor(whatDraw)[2], 255));
                strokeWeight(2);

                this.starsForPrimary[i].alpha(adaptedSpectrum[adaptedSpectrum.length - 1]);
                
                fill(rgbToHsb(settings.getColor(whatDraw)[0],
                              settings.getColor(whatDraw)[1],
                              settings.getColor(whatDraw)[2], 255));
                
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
                stroke(rgbToHsb(settings.getColor(whatDraw)[0],
                                settings.getColor(whatDraw)[1],
                                settings.getColor(whatDraw)[2], 255));
                strokeWeight(2);

                this.starsForSecondary[i].alpha(adaptedSpectrum[adaptedSpectrum.length - 1]);
                
                fill(rgbToHsb(settings.getColor(whatDraw)[0],
                              settings.getColor(whatDraw)[1],
                              settings.getColor(whatDraw)[2], 255));
                
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
public class SettingsWindow {

    private ControlP5 cp5;
    private PGraphics pg;
    private CustomColorPicker colorPickerPrimary;
    private CustomColorPicker colorPickerSecondary;
    private PFont drusicFont;
    private Desktop desktop;
    private boolean settingsWindowVisible;

    public SettingsWindow() {
        this.cp5 = new ControlP5(myPApplet);
        this.pg = createGraphics(850, 650);
        this.settingsWindowVisible = false;
        this.drusicFont = loadFont("LandslideSample-48.vlw");
        this.desktop = Desktop.getDesktop();
        this.updateMouseStatus();
    }

    public void initializeWindowComponents() {
        this.cp5.setVisible(this.isVisibile());
        pg.beginDraw();
        /**/
            colorPickerPrimary = new CustomColorPicker(width / 2 - 425 + 45,
                                                       height / 2 - 315 + 120,
                                                       rgbToHsb(settings.getColor(1)[0], settings.getColor(1)[1], settings.getColor(1)[2], 255));

            colorPickerSecondary = new CustomColorPicker(width / 2 + 425 - 285 - 40,
                                                         height / 2 - 315 + 120,
                                                         rgbToHsb(settings.getColor(2)[0], settings.getColor(2)[1], settings.getColor(2)[2], 255));

            this.cp5.addSlider("sensPrimary")
                .setLabel("Sensibilita\'")
                .setRange(0, 350)
                .setValue(settings.getSensitivityPrimary())
                .setPosition(45, 400)
                .setSize(200, 20);
            
            this.cp5.addSlider("sensSecondary")
                .setLabel("Sensibilita\'")
                .setRange(0, 350)
                .setValue(settings.getSensitivityPrimary())
                .setPosition(525, 400)
                .setSize(200, 20);
            
            this.cp5.addButton("changeAnimationPrimary")
                .setLabel("Cambia Animazione")
                .setPosition(45, 450)
                .setSize(100, 20)
                .activateBy(ControlP5.RELEASE)
                .setValue(settings.getModelPrimary())
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        int animationNumberPrimary = (int) callbackEvent.getController().getValue();
                        animationNumberPrimary++;

                        if (animationNumberPrimary > animationHandler.numberOfAnimations) {
                            animationNumberPrimary = 0;
                        }

                        callbackEvent.getController().setValue(animationNumberPrimary);
                    }
                });
            
            this.cp5.addButton("changeAnimationSecondary")
                .setLabel("Cambia Animazione")
                .setPosition(525, 450)
                .setSize(100, 20)
                .activateBy(ControlP5.RELEASE)
                .setValue(settings.getModelSecondary())
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        int animationNumberSecondary = (int) callbackEvent.getController().getValue();
                        animationNumberSecondary++;

                        if (animationNumberSecondary > animationHandler.numberOfAnimations) {
                            animationNumberSecondary = 0;
                        }

                        callbackEvent.getController().setValue(animationNumberSecondary);
                    }
                });
            
            this.cp5.addButton("savePreset1")
                .setPosition(45, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 1")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {

                    }
                });
            
            this.cp5.addButton("savePreset2")
                .setPosition(160, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 2")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {

                    }
                });
            
            this.cp5.addButton("savePreset3")
                .setPosition(275, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 3")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {

                    }
                });
            
            this.cp5.addButton("savePreset4")
                .setPosition(390, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 4")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {

                    }
                });
            
            this.cp5.addButton("savePreset5")
                .setPosition(505, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 5")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {

                    }
                });
        /**/
        pg.endDraw();
    }

    public void changeVisibility() {
        this.settingsWindowVisible = !this.settingsWindowVisible;
        this.cp5.setVisible(this.isVisibile());
        this.updateMouseStatus();
    }

    public boolean isVisibile() {
        return this.settingsWindowVisible;
    }

    public void updateMouseStatus() {
        if (this.isVisibile()) {
            cursor();
        } else {
            noCursor();
        }
    }

    public void showWindow() {
        this.cp5.setVisible(this.isVisibile());
        pg.beginDraw();
        /**/
            this.cp5.setGraphics(pg, width / 2 - 425, height / 2 - 325);
            noFill();
            
            //this.pg.background(rgbToHsb(181, 181, 181, 60));
            this.pg.background(rgbToHsb(117, 117, 117, 34));
            
            fill(rgbToHsb(191, 191, 191, 100));
            stroke(rgbToHsb(settings.getColor(1)[0],
                                    settings.getColor(1)[1],
                                    settings.getColor(1)[2], 255));
            rect(width / 2 - 425, height / 2 - 325, 850, 650);

            pg.textFont(this.drusicFont);
            pg.text("Drusic", 12, 60);

            pg.textSize(20);
            pg.text("Disegno Principale", 32 + 25, 105);
            pg.text("Disegno Secondario", 32 + 425 + 80, 105);

            pg.text("About Drusic", 725, 620);
            
            colorPickerPrimary.display();
            colorPickerPrimary.update();
            colorPickerSecondary.display();
            colorPickerSecondary.update();
        /**/
        pg.endDraw();

        updateColors();
        updateSensibilities();
        updateAnimationNumbers();
        checkForAboutPress();
    }

    private void updateColors() {
        int []primaryColor = hsbToRgb(hue(colorPickerPrimary.activeColor),
                                      saturation(colorPickerPrimary.activeColor),
                                      brightness(colorPickerPrimary.activeColor));
        settings.changeColor(primaryColor[0], primaryColor[1], primaryColor[2], 1);

        int []secondaryColor = hsbToRgb(hue(colorPickerSecondary.activeColor),
                                        saturation(colorPickerSecondary.activeColor),
                                        brightness(colorPickerSecondary.activeColor));
        settings.changeColor(secondaryColor[0], secondaryColor[1], secondaryColor[2], 2);
    }

    private void updateSensibilities() {
        settings.setSensitivityPrimary(this.cp5.getController("sensPrimary").getValue());
        settings.setSensitivitySecondary(this.cp5.getController("sensSecondary").getValue());
    }

    private void updateAnimationNumbers() {
        settings.setModelPrimary((int) this.cp5.getController("changeAnimationPrimary").getValue());
        settings.setModelSecondary((int) this.cp5.getController("changeAnimationSecondary").getValue());
    }

    private void checkForAboutPress() {
        if (mousePressed) {
            // fill(rgbToHsb(255, 0, 0, 255)); noStroke();
            // rect(width / 2 + 425 - 130, height / 2 + 325 - 55, 110, 40);
            if (mouseX > width / 2 + 425 - 130 && mouseX < width / 2 + 425 - 130 + 110) {
                if (mouseY > height / 2 + 325 - 55 && mouseY < height / 2 + 325 - 55 + 40) {
                    // fill(rgbToHsb(0, 255, 0, 255)); noStroke();
                    // rect(width / 2 + 425 - 130, height / 2 + 325 - 55, 110, 40);
                    if (Desktop.isDesktopSupported()) {
                        try {
                            this.desktop.browse(aboutMeLink);
                        } catch (Exception e) {}
                    }
                }
            }
            
        }
    }

}
  public void settings() {  fullScreen(P3D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "input" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
