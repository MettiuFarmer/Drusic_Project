import processing.sound.*;
import java.io.*;
import controlP5.*;
import java.sql.*;
import java.util.*;
import java.awt.Desktop;
import java.net.URI;

DBConnection conn = new DBConnection();
MicIn mic;
float[] spectrum;
float[] spectrumHi;
Settings sett = new Settings();
Animazioni anim;
boolean pan;
PGraphics pg;
SettingWindow sw;
boolean settingsWindowSetted;
Presets p = new Presets();

void setup() {
    fullScreen(P3D);
    //size(1000, 800);
    frameRate(75);
    smooth();

    this.pan = false;

    mic = new MicIn(this);
    spectrum = new float[2048];
    spectrumHi = new float[1024];

    anim = new Animazioni();
    this.anim.initializeStars();
    this.anim.initializeCircles();
    this.sett.setModelp(1);
    this.sett.setModels(0);

    this.sett.setSensivityp(125);
    this.sett.setSensivitys(125);

    this.sett.setColore(255, 153, 0, 1);
    this.sett.setColore(0, 0, 0, 2);
    this.sett.setColore(0, 0, 0, 3);

    try{
      sw = new SettingWindow(sett, this);
    }catch(Exception e){
    }

    this.settingsWindowSetted = false;
}      

void draw() {
    int[] col;

    mic.getFft().analyze(spectrumHi);
    spectrum = createSpectrum(spectrumHi);
    //print(spectrum.length);
    col = this.sett.getColore(3);
    background(col[0], col[1], col[2]);
    anim.route(spectrum, sett);

    if (this.pan) {
        sw.show();
    } else {
        noCursor();
    }

    if (!settingsWindowSetted) {
        this.sw.setWindowComponents();
        this.settingsWindowSetted = true;
    }
    
    //print("sensp:" + this.sett.getSensivityp() + "  senss:" + this.sett.getSensivitys() + "\n");
}

float[] createSpectrum(float[] inp) {
    float[] spectr = new float[2048];
    int count = 0;

    for (int i = 0; i < 1535; i++) {
        if (i % 3 == 0 && i <= 1000) {
            spectr[i] = inp[count];
        } else if(i>1000 && i % 2 == 0){
            spectr[i] = inp[count];
        }else{
            spectr[i] = inp[count];
            count++;
        }
        //print("ciao" + i + "\n" );
    }

    return spectr;
}

void keyPressed() {
    if (key == 's' || key == 'S') {
        this.pan =! this.pan;
        this.sw.setVis(this.pan);
    }
    try{
        if(key == '1'){
            p.caricaPreset(1);
        }
        if(key == '2'){
            p.caricaPreset(2);
        }
        if(key == '3'){
            p.caricaPreset(3);
        }
        if(key == '4'){
            p.caricaPreset(4);
        }
        if(key == '5'){
            p.caricaPreset(5);
        }
    }catch(IOException e){}

}