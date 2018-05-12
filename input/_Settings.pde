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