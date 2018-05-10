import javax.swing.*;

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
  
  void setWindowComponents() {
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
    color sfondo=color(192,192,192,100);
    pg.background(sfondo);
    
    pg.stroke(s.getColore(1)[0], s.getColore(1)[1], s.getColore(1)[2]);
    
    pg.textFont(this.font1);
    pg.text("Drusic", 12, 60);
    
    rect(width/2-400, height/2-300, 800, 600);
    
    this.s.setColore((int)this.cp5.getController("Rp").getValue(), (int)this.cp5.getController("Gp").getValue(), (int)this.cp5.getController("Bp").getValue(), 1);
    this.s.setColore((int)this.cp5.getController("Rs").getValue(), (int)this.cp5.getController("Gs").getValue(), (int)this.cp5.getController("Bs").getValue(), 2);
    /*print(this.cp5.getController("Sens").getValue());
    print("\n");*/
    this.s.setSensivityp((int)this.cp5.getController("Sensp").getValue());
    this.s.setSensivitys((int)this.cp5.getController("Senss").getValue());
    this.s.setModelp((int)this.cp5.getController("modelSelectp").getValue());
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
  
  void setVis(boolean a){
    this.visible=a;
    if(!a){
      this.cp5.setVisible(this.visible);
    }
  }
  
  public void controlEvnt(ControlEvent theEvent) {
    println(theEvent.getController().getName());
  }

  public void changeSettings(Settings s2) {
    this.s.setModelp(s2.getModelp());
    this.s.setColore(s2.getColore(1)[0], s2.getColore(1)[1], s2.getColore(1)[2], 1);
    this.s.setColore(s2.getColore(2)[0], s2.getColore(2)[1], s2.getColore(2)[2], 2);
    this.s.setColore(s2.getColore(3)[0], s2.getColore(3)[1], s2.getColore(3)[2], 3);
    this.s.setSize(s2.getSizeX(), s2.getSizeY());
    this.s.setModels(s2.getModels());
    this.s.setSensivityp(s2.getSensivityp());
    this.s.setSensivitys(s2.getSensivitys());

    this.cp5.getController("modelSelectp").setValue(s2.getModelp());
    this.cp5.getController("modelSelects").setValue(s2.getModels());
    this.cp5.getController("Rp").setValue(s2.getColore(1)[0]);
    this.cp5.getController("Gp").setValue(s2.getColore(1)[1]);
    this.cp5.getController("Bp").setValue(s2.getColore(1)[2]);
    this.cp5.getController("Rs").setValue(s2.getColore(2)[0]);
    this.cp5.getController("Gs").setValue(s2.getColore(2)[1]);
    this.cp5.getController("Bs").setValue(s2.getColore(2)[2]);
    this.cp5.getController("Sensp").setValue(s2.getSensivityp());
    this.cp5.getController("Senss").setValue(s2.getSensivitys());
  }

}