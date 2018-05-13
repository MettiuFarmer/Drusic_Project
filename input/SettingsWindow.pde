public class SettingsWindow {

    private ControlP5 cp5;
    private PGraphics pg;
    private CustomColorPicker colorPickerPrimary;
    private CustomColorPicker colorPickerSecondary;
    private boolean settingsWindowVisible;

    public SettingsWindow() {
        this.cp5 = new ControlP5(myPApplet);
        this.pg = createGraphics(800, 600);
        this.settingsWindowVisible = false;
        this.updateMouseStatus();
    }

    public void initializeWindowComponents() {
        this.cp5.setVisible(this.isVisibile());
        pg.beginDraw();
        /**/
            // this.cp5.addColorPicker("picker")
            //     .setPosition(60, 100)
            //     .setColorValue(color(255, 128, 0, 128));
            
            // colorPickerPrimary = new CustomColorPicker(width / 2 - 400,
            //                                            height / 2 - 300);
            
            colorPickerPrimary = new CustomColorPicker(10, 10);
        /**/
        pg.endDraw();
    }

    public void changeVisibility() {
        this.settingsWindowVisible = !this.settingsWindowVisible;
        this.cp5.setVisible(this.isVisibile());
        this.updateMouseStatus();
    }

    public boolean isVisibile() {
        return this.settingsWindowVisible;
    }

    public void updateMouseStatus() {
        if (this.isVisibile()) {
            cursor();
        } else {
            noCursor();
        }
    }

    public void showWindow() {
        this.cp5.setVisible(this.isVisibile());
        pg.beginDraw();
        /**/
            this.cp5.setGraphics(pg, width / 2 - 400, height / 2 - 300);
            noFill();
            
            this.pg.background(rgbToHsb(181, 181, 181, 60));
            
            // this.pg.stroke(settings.getColor(1)[0],
            //                settings.getColor(1)[1],
            //                settings.getColor(1)[2]);
            // rect(width / 2 - 400, height / 2 - 300, 800, 600);
            
            colorPickerPrimary.display();
            colorPickerPrimary.update();
        /**/
        pg.endDraw();
    }

}
