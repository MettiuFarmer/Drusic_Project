// Imports
import java.io.*;
import java.util.*;
import java.sql.*;
import java.awt.Desktop;
import java.net.URI;
import processing.sound.*;
import controlP5.*;

// PApplet variable initialization
PApplet myPApplet = this;

// Global variables declaration
MicrophoneInput microphone;
float []spectrumInitializer;
float []spectrum;
int numberOfBands;
Settings settings;
AnimationHandler animationHandler;

void setup() {
    // Processing settings
    //fullScreen(P3D);
    size(1000, 800);
    smooth();

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

void draw() {
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

float[] createSpectrum() {
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
