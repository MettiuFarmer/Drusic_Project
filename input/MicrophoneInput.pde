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
