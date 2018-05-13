public class SettingsWindow {

    private ControlP5 cp5;
    private PGraphics pg;
    private CustomColorPicker colorPickerPrimary;
    private CustomColorPicker colorPickerSecondary;
    PFont drusicFont;
    private boolean settingsWindowVisible;

    public SettingsWindow() {
        this.cp5 = new ControlP5(myPApplet);
        this.pg = createGraphics(850, 650);
        this.settingsWindowVisible = false;
        this.drusicFont = loadFont("LandslideSample-48.vlw");
        this.updateMouseStatus();
    }

    public void initializeWindowComponents() {
        this.cp5.setVisible(this.isVisibile());
        pg.beginDraw();
        /**/
            colorPickerPrimary = new CustomColorPicker(width / 2 - 425 + 45,
                                                       height / 2 - 315 + 120,
                                                       rgbToHsb(settings.getColor(1)[0], settings.getColor(1)[1], settings.getColor(1)[2], 255));

            colorPickerSecondary = new CustomColorPicker(width / 2 + 425 - 285 - 40,
                                                         height / 2 - 315 + 120,
                                                         rgbToHsb(settings.getColor(2)[0], settings.getColor(2)[1], settings.getColor(2)[2], 255));
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
            this.cp5.setGraphics(pg, width / 2 - 425, height / 2 - 325);
            noFill();
            
            //this.pg.background(rgbToHsb(181, 181, 181, 60));
            this.pg.background(rgbToHsb(117, 117, 117, 34));
            
            fill(rgbToHsb(191, 191, 191, 100));
            stroke(rgbToHsb(settings.getColor(1)[0],
                                    settings.getColor(1)[1],
                                    settings.getColor(1)[2], 255));
            rect(width / 2 - 425, height / 2 - 325, 850, 650);

            pg.textFont(this.drusicFont);
            pg.text("Drusic", 12, 60);

            pg.textSize(20);
            pg.text("Disegno Principale", 32 + 25, 110);
            pg.text("Disegno Secondario", 32 + 425 + 80, 110);
            
            colorPickerPrimary.display();
            colorPickerPrimary.update();
            colorPickerSecondary.display();
            colorPickerSecondary.update();
        /**/
        pg.endDraw();
        updateColors();
    }

    private void updateColors() {
        int []primaryColor = hsbToRgb(hue(colorPickerPrimary.activeColor),
                                      saturation(colorPickerPrimary.activeColor),
                                      brightness(colorPickerPrimary.activeColor));
        settings.changeColor(primaryColor[0], primaryColor[1], primaryColor[2], 1);

        int []secondaryColor = hsbToRgb(hue(colorPickerSecondary.activeColor),
                                        saturation(colorPickerSecondary.activeColor),
                                        brightness(colorPickerSecondary.activeColor));
        settings.changeColor(secondaryColor[0], secondaryColor[1], secondaryColor[2], 2);
    }

}
