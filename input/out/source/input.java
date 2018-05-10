import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 
import java.io.*; 
import controlP5.*; 
import java.sql.*; 
import java.util.*; 
import java.awt.Desktop; 
import java.net.URI; 
import javax.swing.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class input extends PApplet {









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

public void setup() {
    
    //size(1000, 800);
    frameRate(75);
    

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

public void draw() {
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
}

public float[] createSpectrum(float[] inp) {
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

public void keyPressed() {
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
class Animazioni{
    
    Circle[] stars=new Circle[100];
    Circle[] circles=new Circle[5];
    
    public void muovi(float x, float y, float x1, float y1){}
    
    public void setColor(int r, int g, int b){} 
    
    public Animazioni(){ } 

    
    public float[] adapt(float[] a, Settings s, int model) {
        
        float media=0;
        float[] b = new float[a.length+1];
        
        if(model==1){
        
        for(int i=0; i<a.length; i++){
        
            b[i]=a[i]*s.getSensivityp()*(pow(i+1, 1.2f));
            /*print("adapt: ");
            print(this.sensivity);
            print("\n");*/
            
            media = media+b[i];
            
            map(b[i], 0, max(a)*200*(pow(i+1, 1.2f)), 0, height-50);
        }
        
        media = media/100;
        
        b[a.length]=media;
        
        
        //System.out.printf(""+media+"\n");
        }else{
        
        for(int i=0; i<a.length; i++){
        
            b[i]=a[i]*s.getSensivitys()*(pow(i+1, 1.2f));
            /*print("adapt: ");
            print(this.sensivity);
            print("\n");*/
            
            media = media+b[i];
            
            map(b[i], 0, max(a)*200*(pow(i+1, 1.2f)), 0, height-50);
        }
        
        media = media/100;
        
        b[a.length]=media;
        }
        
        return b;
    }
        
    
    public void onda(float[] a, Settings s, int coloDisegno){
        
        int l=a.length;
        int x=width;
        
        int i=0;
        a = adapt(a, s, coloDisegno);
        
        beginShape();
        while(i!=x){
        if(i != (l-1)){
            stroke(s.getColore(coloDisegno)[0], s.getColore(coloDisegno)[1], s.getColore(coloDisegno)[2]);
            strokeWeight(3);
            noFill();
            curveVertex(i, height - 50 - a[i]);
            i++;
            
        }
        }
        endShape();
        
        
    }
    
    public void hyperLine(float[] a, Settings s, int coloDisegno){
        
        a=adapt(a, s, coloDisegno);
        
        float[] media=new float[5];
        
        for(int i=0; i<5; i++){
        media[i]=0;
        for(int k=(2045/5)*i; k<(2045/5)*i+409; k++){
            media[i]=media[i]+a[k];
        }
        media[i]=media[i]/500;
        this.circles[i].setY((int)((height/10)*(9)-media[i]));
        this.circles[i].show(s, coloDisegno);
        }
        
        for(int i=0; i<4; i++){
        line(this.circles[i].getX(), this.circles[i].getY(), this.circles[i+1].getX(), this.circles[i+1].getY());
        }
        
    }
    
    public void saturn(float[] a, Settings s, int coloDisegno){
        
        a=adapt(a, s, coloDisegno);
        
        for(int i=0; i<100; i++){
        
            stroke(s.getColore(coloDisegno)[0], s.getColore(coloDisegno)[1], s.getColore(coloDisegno)[2]);
            strokeWeight(1);
        
            List b = Arrays.asList(a);

            this.stars[i].alpha(a[100]);
        
            fill(s.colorePrincipale[0], s.colorePrincipale[1], s.colorePrincipale[2]);
            
            this.stars[i].setX((int)(this.stars[i].getR()*(cos(this.stars[i].getAlpha())))+width/2);
            this.stars[i].setY((int)(this.stars[i].getR()*(sin(this.stars[i].getAlpha())))+height/2);
            
            if((int)random(a[width/8])>100){
            ellipse((this.stars[i].getX()), (this.stars[i].getY()), 6, 6);
            }else{
            ellipse((this.stars[i].getX()), (this.stars[i].getY()), 3, 3);
            }
            
            
        }  
    }
    
    public void magicSphere(float[] a, Settings s, int coloDisegno){
        
        a=adapt(a, s, coloDisegno);
        
        noFill();
        
        float m=0;
        
        for(int i=0; i<500; i++){
        m=m+a[i+250];
        }
        
        stroke(s.getColore(coloDisegno)[0], s.getColore(coloDisegno)[1], s.getColore(coloDisegno)[2]);
        strokeWeight(1);
        
        pushMatrix();
        translate(width/2, height/2);
        rotateX(m * 0.02f);
        rotateY(m * 0.02f);
        rotateZ(m * 0.02f);
        
        map(m, min(a), max(a), 20, height/2);
        
        sphereDetail((int)11);
        sphere((int)(m/20));
        
        
        popMatrix();
        delay(5);
    }
    
    public void route(float[] a, Settings s){
    
        switch(s.getModels()){
        case 0:
            break;
            
        case 1:
            onda(a, s, 2);
        break;
        
        case 2:
            //initializeStars();
            saturn(a, s, 2);
            break;
            
        case 3:
            //initializeCircles();
            hyperLine(a, s, 2);
            break;
            
        case 4:
            magicSphere(a, s, 2);
            break;
        }
        
        switch(s.getModelp()){
        case 0:
            break;
            
        case 1:
            onda(a, s, 1);
        break;
        
        case 2:
            //initializeStars();
            saturn(a, s, 1);
            break;
            
        case 3:
            //initializeCircles();
            hyperLine(a, s, 1);
            break;
        
        case 4:
            magicSphere(a, s, 1);
            break;
        }
        
        
        
    }
    
    public void initializeStars(){
        
        
        for(int i=0; i<100; i++){
        if(this.stars[i]==null){
            this.stars[i]=new Circle();
            this.stars[i].setR((int)random(width/2));
            this.stars[i].setAlpha(random(360));
            this.stars[i].setX((int)(this.stars[i].getR()*(cos(this.stars[i].getAlpha())))+width/2);
            this.stars[i].setY((int)(this.stars[i].getR()*(sin(this.stars[i].getAlpha())))+height/2);
            delay(1);
        }
        }
    
    }
    
    public void initializeCircles(){
        for(int i=0; i<5; i++){
        if(this.circles[i]==null){
            this.circles[i]=new Circle();
            this.circles[i].setX((i+1)*width/6);
            this.circles[i].setY(height/2);
            delay(1);
        }
        }
    }
    
}
/*

drusic  Version 1.0 pre-Alpha

*/

//Circle class

class Circle extends Animazioni{
    
    int x; //center of the circle X
    int y; //center of the circle Y
    int r;
    float alpha;
    int yspeed = 3; //speed of the animation to move the circle
    int id;
    int[] colore;
    
    //constructor method
    Circle(int X, int Y){
        this.x=X;
        this.y=Y;
        this.colore=new int[3];
    }
    
    public Circle(){}
    
    //function to move the circle up
    public void up(int a){
        this.yspeed=a;
        if(this.y>150){
        this.y=this.y-this.yspeed;
        }
    }
    
    //function to move the circle down
    public void down(int a){
        this.yspeed=a;
        if(this.y<450){
        this.y=this.y+this.yspeed;
        }
    }
    
    //makes the circle visible
    public void show(Settings s, int coloDisegno){
        stroke(s.getColore(coloDisegno)[0], s.getColore(coloDisegno)[1], s.getColore(coloDisegno)[2]);
        strokeWeight(3);
        fill(0);
        ellipse(this.x, this.y, 50, 50);
        fill(s.getColore(coloDisegno)[0], s.getColore(coloDisegno)[1], s.getColore(coloDisegno)[2]);
        ellipse(this.x, this.y, 10, 10);
    }
    
    public void muovi(float a, float b, float c, float d){}
    
    public void setColor(int a, int b, int c){
        this.colore=new int[]{a, b, c};
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void setX(int a){
        this.x=a;
    }
    
    public void setY(int a){
        this.y=a;
    }
    
    public void toPolar(){
        float x2=(pow((this.x), 2));
        float y2=(pow((this.y), 2));
        
        this.r=(int)sqrt(x2+y2);
        
        this.alpha=atan((this.y)/(this.x));
    }
    
    public void alpha(float f){
        f=map(f, 0, 10, 0, 7);
        this.alpha+=(f*0.001f);
    }
    
    public float getAlpha(){
        return this.alpha;
    }
    
    public int getR(){
        return this.r;
    }
    
    public void setR(int r){
        this.r=r;
    }
    
    public void setAlpha(float a){
        this.alpha=a;
    }
}
class DBConnection{
    
    private Connection conn;
    private Statement stmt;
    
    public DBConnection(){
        String host = "jdbc:mysql://80.22.95.8:3306/5afattoredb";
        String uName = "5afattore";
        String uPass = "fazzingher";
        
        try{
        this.conn = DriverManager.getConnection(host, uName, uPass);
        this.stmt = this.conn.createStatement();
        }catch(Exception ex){
        
        }
        
    }
    
    public Connection getConnection(){
        return this.conn;
    }
    
    public void insertPreset(int idPreset, byte[] preset) throws Exception{
        this.stmt.execute("INSERT INTO progetto");
    }
}
class MicIn{
    
    PApplet pa;
    FFT fft;
    AudioIn in;
    int bands = 2048;

    public MicIn(input pa){
        this.pa = pa;
        fft = new FFT(this.pa, 1024);
        in = new AudioIn(this.pa, 0);
        
        in.start();
        
        fft.input(in);
    }
    
    public FFT getFft(){
        return this.fft;
    }
    
    public int getBands(){
        return this.bands;
    }
    
}
class Presets{

    public Presets() { }

    public void salvaPreset(int i) throws IOException {

        FileOutputStream out = new FileOutputStream("preset" + Integer.toString(i) + ".save");
        ObjectOutputStream preset = new ObjectOutputStream(out);
        preset.writeObject(sett);
    }

    public void caricaPreset(int i) throws IOException{
        try {
            FileInputStream in = new FileInputStream("preset" + Integer.toString(i) + ".save");
            ObjectInputStream s = new ObjectInputStream(in);
            Settings importedSettings = (Settings) s.readObject();
            sett.setModelp(importedSettings.getModelp());
            sett.setColore(importedSettings.getColore(1)[0], importedSettings.getColore(1)[1], importedSettings.getColore(1)[2], 1);
            sett.setColore(importedSettings.getColore(2)[0], importedSettings.getColore(2)[1], importedSettings.getColore(2)[2], 2);
            sett.setColore(importedSettings.getColore(3)[0], importedSettings.getColore(3)[1], importedSettings.getColore(3)[2], 3);
            sett.setSize(importedSettings.getSizeX(), importedSettings.getSizeY());
            sett.setModels(importedSettings.getModels());
            sett.setSensivityp(importedSettings.getSensivityp());
            sett.setSensivitys(importedSettings.getSensivitys());
            sw.changeSettings(importedSettings);
        } catch (ClassNotFoundException e) {}
    }

    private byte[] compressPresets() throws IOException {
        byte[] byteArray = new byte[0];
        try {
            ArrayList<Object> presets = new ArrayList<Object>();
            for (int i = 0; i < 5; i++) {
                FileInputStream in = new FileInputStream("preset" + Integer.toString(i) + ".save");
                ObjectInputStream s = new ObjectInputStream(in);
                presets.add((Settings) s.readObject());
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            ObjectOutputStream objout = new ObjectOutputStream(out);
            objout.writeObject(presets);
            byteArray = out.toByteArray();
        } catch (ClassNotFoundException e) {}
        return byteArray;
    }

    private void decompressPresets() throws IOException {
        //todo
    }

    public void uploadPresetsToDB(int presets) throws Exception{
        try {
            byte[] data = compressPresets();
            //todo: upload (BUT create new column before!)
            conn.insertPreset(presets, data);
        } catch (IOException e) {}
    }

    public void downloadPresetsFromDB() throws Exception{
        decompressPresets();
        //todo
    }
}
static class Settings implements Serializable{
    
    int nBandeVolute;
    int[] colorePrincipale;
    int[] coloreSecondario;
    int[] coloreSfondo;
    int sizeX;
    int sizeY;
    int selection;
    int modelp;
    int models;
    int sensivityp;
    int sensivitys;
    
    public Settings(){
        this.colorePrincipale=new int[3];
        this.coloreSecondario=new int[3];
        this.coloreSfondo=new int[3];
        this.sizeX=800;
        this.sizeY=600;
        this.sensivityp=125;
        this.sensivitys=125;
    }
    
    public void setNBande(int n){
        this.nBandeVolute=n;
    }
    
    public void setColore(int r, int g, int b, int type){
        
        switch(type){
        case 1:
            this.colorePrincipale[0]=r;
            this.colorePrincipale[1]=g;
            this.colorePrincipale[2]=b;
            break;
            
        case 2:
            this.coloreSecondario[0]=r;
            this.coloreSecondario[1]=g;
            this.coloreSecondario[2]=b;
            break;
            
        case 3:
            this.coloreSfondo[0]=r;
            this.coloreSfondo[1]=g;
            this.coloreSfondo[2]=b;
            break;
        
        }
        
    }
    
    public int[] getColore(int type){
        
        switch(type){
        
        case 1:
            return this.colorePrincipale;
        
            
        case 2:
            return this.coloreSecondario;
        
            
        case 3:
            return this.coloreSfondo;
        
        
        default:
            return new int[]{0, 0, 0};
        
        
        }
        
    }
    
    public void setSize(int l, int h){
        this.sizeX=l;
        this.sizeY=h;
    }
    
    public int getSizeX(){
        return this.sizeX;
    }
    
    public int getSizeY(){
        return this.sizeY;
    }
    
    public int getModelp(){
        return this.modelp;
    }
    
    public void setModelp(int modelp){
        this.modelp=modelp;
    }
    
        public int getModels(){
        return this.models;
    }
    
    public void setModels(int models){
        this.models=models;
    }
    
    public void setSensivityp(int sns){
        this.sensivityp=sns;
    }
    
    public int getSensivityp(){
        return this.sensivityp;
    }
    
    public void setSensivitys(int sns){
        this.sensivitys=sns;
    }
    
    public int getSensivitys(){
        return this.sensivitys;
    }

}


class SettingWindow {

    Settings s;
    PFont font1;
    PFont font2;
    PApplet pa;
    PGraphics pg;
    ControlP5 cp5;
    boolean visible;
    URI aboutMe;
    Desktop desk;

    public SettingWindow(Settings s, input pa) throws Exception {
        this.pa=pa;
        this.cp5=new ControlP5(pa);
        this.s=s;
        this.pg=createGraphics(800, 600); 
        this.font1=loadFont("LandslideSample-48.vlw");
        this.font2=loadFont("mimich-48.vlw");
        this.visible=false;
        this.aboutMe=new URI("http://80.22.95.8/classiquinte/5Ain/fattore.matteo/Progetto_Drusic_Website/index.php");
        this.desk=Desktop.getDesktop();
    }

    public void setWindowComponents() {
        this.cp5.setVisible(this.visible);
        pg.beginDraw();

        this.cp5.addButton("Salva preset 1")
        .activateBy(ControlP5.RELEASE)
        .setPosition(400, 20)
        .onPress(new CallbackListener() { // a callback function that will be called onPress
        
        public void controlEvent(CallbackEvent theEvent) {
            try {
            p.salvaPreset(1);
            }catch (IOException e) { e.printStackTrace(); }
        }
        });

        this.cp5.addButton("Salva preset 2")
        .activateBy(ControlP5.RELEASE)
        .setPosition(480, 20)
        .onPress(new CallbackListener() { // a callback function that will be called onPress
        
        public void controlEvent(CallbackEvent theEvent) {
            try {
            p.salvaPreset(2);
            }catch (IOException e) { e.printStackTrace(); }
        }
        });

        this.cp5.addButton("Salva preset 3")
        .activateBy(ControlP5.RELEASE)
        .setPosition(560, 20)
        .onPress(new CallbackListener() { // a callback function that will be called onPress
        
        public void controlEvent(CallbackEvent theEvent) {
            try {
            p.salvaPreset(3);
            }catch (IOException e) { e.printStackTrace(); }
        }
        });

        this.cp5.addButton("Salva preset 4")
        .activateBy(ControlP5.RELEASE)
        .setPosition(440, 50)
        .onPress(new CallbackListener() { // a callback function that will be called onPress
        
        public void controlEvent(CallbackEvent theEvent) {
            try {
            p.salvaPreset(4);
            }catch (IOException e) { e.printStackTrace(); }
        }
        });

        this.cp5.addButton("Salva preset 5")
        .activateBy(ControlP5.RELEASE)
        .setPosition(520, 50)
        .onPress(new CallbackListener() { // a callback function that will be called onPress
        
        public void controlEvent(CallbackEvent theEvent) {
            try {
            p.salvaPreset(5);
            }catch (IOException e) { e.printStackTrace(); }
        }
        });
        
        //------------------------Primario-------------------------------------
        this.cp5.addSlider("Rp")
        .setRange(0,255)
        .setValue(this.s.getColore(1)[0])
        .setPosition(10,170)
        .setSize(100,10);
        
        this.cp5.addSlider("Gp")
        .setRange(0,255)
        .setValue(this.s.getColore(1)[1])
        .setPosition(10,190)
        .setSize(100,10);
        
        this.cp5.addSlider("Bp")
        .setRange(0,255)
        .setValue(this.s.getColore(1)[2])
        .setPosition(10,210)
        .setSize(100,10);
        
        this.cp5.addSlider("Sensp")
        .setRange(10, 500)
        .setValue(this.s.getSensivityp())
        .setPosition(10, 250)
        .setSize(100, 10);
        
        this.cp5.addButton("modelSelectp")
        .setPosition(10, 270)
        .activateBy(ControlP5.RELEASE)
        .setValue(this.s.getModelp())
        .onPress(new CallbackListener() { // a callback function that will be called onPress
        public void controlEvent(CallbackEvent theEvent) {
            int value=(int)theEvent.getController().getValue();
            value++;
            
            if(value==5){
            value=1;
            }
            
            theEvent.getController().setValue(value);
        }
        });
        //-------------------------Secondario------------------------------------ 
        
        this.cp5.addSlider("Rs")
        .setRange(0,255)
        .setValue(this.s.getColore(1)[0])
        .setPosition(410,170)
        .setSize(100,10);
        
        this.cp5.addSlider("Gs")
        .setRange(0,255)
        .setValue(this.s.getColore(1)[1])
        .setPosition(410,190)
        .setSize(100,10);
        
        this.cp5.addSlider("Bs")
        .setRange(0,255)
        .setValue(this.s.getColore(1)[2])
        .setPosition(410,210)
        .setSize(100,10);
        
        this.cp5.addSlider("Senss")
        .setRange(10, 500)
        .setValue(this.s.getSensivitys())
        .setPosition(410, 250)
        .setSize(100, 10);
        
        this.cp5.addButton("modelSelects")
        .setPosition(410, 270)
        .activateBy(ControlP5.RELEASE)
        .setValue(this.s.getModels())
        .onPress(new CallbackListener() { // a callback function that will be called onPress
        public void controlEvent(CallbackEvent theEvent) {
            int value=(int)theEvent.getController().getValue();
            value++;
            
            if(value==5){
            value=0;
            }
            
            theEvent.getController().setValue(value);
        }
        });
        
        //------------------------------------------------------------- 
        
        
        
        pg.endDraw();
    }

    public void show(){ 
        this.cp5.setVisible(this.visible);
        
        pg.beginDraw();
        cursor();
        
        this.cp5.setGraphics(pg, width/2-400, height/2-300);
        noFill();
        int sfondo=color(192,192,192,100);
        pg.background(sfondo);
        
        pg.stroke(s.getColore(1)[0], s.getColore(1)[1], s.getColore(1)[2]);
        
        pg.textFont(this.font1);
        pg.text("Drusic", 12, 60);

        pg.textSize(20);
        pg.text("impostazioni", 150, 60);

        rect(width/2-400, height/2-300, 800, 600);
        
        /* ---------------------- */
        /*        Primary         */
        /* ---------------------- */
        // Colore -> R G B
        this.s.setColore((int)this.cp5.getController("Rp").getValue(), (int)this.cp5.getController("Gp").getValue(), (int)this.cp5.getController("Bp").getValue(), 1);
        // Sensibilit\u00e0
        this.s.setSensivityp((int)this.cp5.getController("Sensp").getValue());
        // Modello
        this.s.setModelp((int)this.cp5.getController("modelSelectp").getValue());

        /* ---------------------- */
        /*        Secondary       */
        /* ---------------------- */
        // Colore -> R G B
        this.s.setColore((int)this.cp5.getController("Rs").getValue(), (int)this.cp5.getController("Gs").getValue(), (int)this.cp5.getController("Bs").getValue(), 2);
        // Sensibilit\u00e0
        this.s.setSensivitys((int)this.cp5.getController("Senss").getValue());
        // Modello
        this.s.setModels((int)this.cp5.getController("modelSelects").getValue());
        
        pg.textSize(20);
        pg.fill(255, 255, 255);
        pg.text("About Drusic", 10, 580); 
        
        if((this.visible==true && mousePressed && mouseX>((width/2)-400)) && mouseX<((width/2)-300) && mouseY<((height/2)+300) && mouseY>((height/2)+250)){
        
        if (Desktop.isDesktopSupported()) {
            try{
                this.desk.browse(this.aboutMe);
            }catch(Exception e){
            }
        }
        
        delay(500);
        
        }
        
        pg.endDraw();
    }

    public void setVis(boolean a){
        this.visible=a;
        if(!a){
        this.cp5.setVisible(this.visible);
        }
    }

    public void controlEvnt(ControlEvent theEvent) {
        println(theEvent.getController().getName());
    }

    public void changeSettings(Settings s2) {
        // Change generic settings
        this.s.setModelp(s2.getModelp());
        this.s.setColore(s2.getColore(1)[0], s2.getColore(1)[1], s2.getColore(1)[2], 1);
        this.s.setColore(s2.getColore(2)[0], s2.getColore(2)[1], s2.getColore(2)[2], 2);
        this.s.setColore(s2.getColore(3)[0], s2.getColore(3)[1], s2.getColore(3)[2], 3);
        this.s.setSize(s2.getSizeX(), s2.getSizeY());
        this.s.setModels(s2.getModels());
        this.s.setSensivityp(s2.getSensivityp());
        this.s.setSensivitys(s2.getSensivitys());

        // Change Primary's settings
        this.cp5.getController("Rp").setValue(s2.getColore(1)[0]);
        this.cp5.getController("Gp").setValue(s2.getColore(1)[1]);
        this.cp5.getController("Bp").setValue(s2.getColore(1)[2]);
        this.cp5.getController("modelSelectp").setValue(s2.getModelp());
        this.cp5.getController("Sensp").setValue(s2.getSensivityp());

        // Change Secondary's settings
        this.cp5.getController("Rs").setValue(s2.getColore(2)[0]);
        this.cp5.getController("Gs").setValue(s2.getColore(2)[1]);
        this.cp5.getController("Bs").setValue(s2.getColore(2)[2]);
        this.cp5.getController("modelSelects").setValue(s2.getModels());
        this.cp5.getController("Senss").setValue(s2.getSensivitys());
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
