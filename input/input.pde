// Imports
import java.io.*;
import java.util.*;
import java.sql.*;
import java.awt.Desktop;
import java.net.URI;
import processing.sound.*;
import controlP5.*;

// PApplet variable initialization
input myPApplet = this;

// Global variables declaration
int numberOfBands;
float []spectrumInitializer;
float []spectrum;
MicrophoneInput microphone;
Settings settings;
AnimationHandler animationHandler;

void setup() {
    // Processing settings
    fullScreen(P3D);
    //size(1000, 800);
    smooth();

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

void draw() {
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

float[] createSpectrum() {
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
