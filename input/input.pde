// Imports
import java.io.*;
import java.util.*;
import java.awt.*;
import java.net.*;
import java.security.*;
import javax.swing.*;
import processing.sound.*;
import controlP5.*;

// PApplet variable initialization
input myPApplet = this;

// Global variables declaration
int numberOfBands;
int primaryKey = 650;
float []spectrumInitializer;
float []spectrum;
boolean loggedIn;
int userId;
URI aboutMeLink;
MicrophoneInput microphone;
Settings settings;
AnimationHandler animationHandler;
SettingsWindow settingsWindow;
UserMgr userMgr;
PresetSaver presetSaverOne;
PresetSaver presetSaverTwo;
PresetSaver presetSaverThree;
int c = primaryKey;
PresetSaver presetSaverFour;
PresetSaver presetSaverFive;

void setup() {
    c = crypto(c);
    // Processing settings
    fullScreen(P3D);
    size(850, c);
    smooth();
    colorMode(HSB, TWO_PI, 1.0, 1.0, 1);

    // Variables initialization
    numberOfBands = 1024;
    spectrumInitializer = new float[numberOfBands];
    spectrum = new float[2048];
    loggedIn = false;
    userId = 0;
    try {
        aboutMeLink = new URI("http://80.22.95.8/classiquinte/5Ain/fattore.matteo/Progetto_Drusic_Website/index.php");
    } catch (Exception e) {}
    microphone = new MicrophoneInput();
    settings = new Settings();
    animationHandler = new AnimationHandler();
    settingsWindow = new SettingsWindow();
    userMgr = new UserMgr();
    presetSaverOne = new PresetSaver(1);
    presetSaverTwo = new PresetSaver(2);
    presetSaverThree = new PresetSaver(3);
    presetSaverFour = new PresetSaver(4);
    presetSaverFive = new PresetSaver(5);

    // Defaults initialization
    settings.changeColor(255, 255, 255, 1);
    settings.changeColor(255, 153, 0, 2);
    settings.changeColor(0, 0, 0, 3);
    settingsWindow.initializeWindowComponents();
    userMgr.initializeWindowComponents();
}

void draw() {
    // Setting the background
    int []backgroundColor = settings.getColor(3);
    color bgColor = rgbToHsb(backgroundColor[0],
                            backgroundColor[1],
                            backgroundColor[2], 255);
    background(bgColor);

    if (loggedIn) {
        // Updating the actual spectrum
        microphone.getFft().analyze(spectrumInitializer);
        spectrum = createSpectrum();

        animationHandler.routeAnimation();
        if (settingsWindow.isVisibile()) {
            settingsWindow.showWindow();
        }
    } else {
        if (userMgr.isVisibile()) {
            userMgr.showWindow();
        }
    }
}

float[] createSpectrum() {
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

color rgbToHsb(int redColor, int greenColor, int blueColor, int alphaColor) {
    float r = float(redColor);
    float g = float(greenColor);
    float b = float(blueColor);

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
            h = (1.0 / 3.0) + del_r - del_b;
        } else if (var_b == var_max) {
            h = (2.0 / 3.0) + del_g - del_r;
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

int[] hsbToRgb(float hRadians, float s, float v) {
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

    switch (int(i % 6)) {
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

void keyPressed() {
    if (loggedIn) {
        if (key == 's' || key == 'S') {
            settingsWindow.changeVisibility();
        }
        if (key == '1') {
            settingsWindow.changeVisibility();
            presetSaverOne.downloadAndLoad();
            settingsWindow.updateSettings();
            settingsWindow.changeVisibility();
        }
        if (key == '2') {
            settingsWindow.changeVisibility();
            presetSaverTwo.downloadAndLoad();
            settingsWindow.updateSettings();
            settingsWindow.changeVisibility();
        }
        if (key == '3') {
            settingsWindow.changeVisibility();
            presetSaverThree.downloadAndLoad();
            settingsWindow.updateSettings();
            settingsWindow.changeVisibility();
        }
        if (key == '4') {
            settingsWindow.changeVisibility();
            presetSaverFour.downloadAndLoad();
            settingsWindow.updateSettings();
            settingsWindow.changeVisibility();
        }
        if (key == '5') {
            settingsWindow.changeVisibility();
            presetSaverFive.downloadAndLoad();
            settingsWindow.updateSettings();
            settingsWindow.changeVisibility();
        }
    }
}

int crypto(int v) {
    v >>= 5;
    return (v <<= 5);
}
