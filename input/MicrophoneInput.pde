public class MicrophoneInput {

    // Class variables
    AudioIn audioInput;
    FFT fft;

    public MicrophoneInput() {
        // Initialize and start the microphone audio input
        this.audioInput = new AudioIn(myPApplet, 0);
        this.audioInput.start();

        // Get the FFT from the audio input
        this.fft = new FFT(myPApplet, numberOfBands);
        this.fft.input(this.audioInput);
    }

    // Return the fft variable
    public FFT getFft() {
        return this.fft;
    }

}
