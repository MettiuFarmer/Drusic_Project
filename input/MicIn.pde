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