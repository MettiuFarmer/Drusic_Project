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
     
        b[i]=a[i]*s.getSensivityp()*(pow(i+1, 1.2));
        /*print("adapt: ");
        print(this.sensivity);
        print("\n");*/
        
        media = media+b[i];
        
        map(b[i], 0, max(a)*200*(pow(i+1, 1.2)), 0, height-50);
      }
     
     media = media/100;
     
     b[a.length]=media;
     
     
     //System.out.printf(""+media+"\n");
    }else{
     
      for(int i=0; i<a.length; i++){
     
        b[i]=a[i]*s.getSensivitys()*(pow(i+1, 1.2));
        /*print("adapt: ");
        print(this.sensivity);
        print("\n");*/
        
        media = media+b[i];
        
        map(b[i], 0, max(a)*200*(pow(i+1, 1.2)), 0, height-50);
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
  
  void hyperLine(float[] a, Settings s, int coloDisegno){
    
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
  
  void magicSphere(float[] a, Settings s, int coloDisegno){
    
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
      rotateX(m * 0.02);
      rotateY(m * 0.02);
      rotateZ(m * 0.02);
      
      map(m, min(a), max(a), 20, height/2);
      
      sphereDetail((int)11);
      sphere((int)(m/20));
      
      
    popMatrix();
    delay(5);
  }
  
  void route(float[] a, Settings s){
   
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
  
  void initializeStars(){
    
    
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
  
  void initializeCircles(){
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