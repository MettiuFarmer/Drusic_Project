public class SettingsWindow {

    private ControlP5 cp5;
    private PGraphics pg;
    private CustomColorPicker colorPickerPrimary;
    private CustomColorPicker colorPickerSecondary;
    private PFont drusicFont;
    private Desktop desktop;
    private boolean settingsWindowVisible;

    public SettingsWindow() {
        this.cp5 = new ControlP5(myPApplet);
        this.pg = createGraphics(850, 650);
        this.settingsWindowVisible = false;
        this.drusicFont = loadFont("LandslideSample-48.vlw");
        this.desktop = Desktop.getDesktop();
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

            this.cp5.addSlider("sensPrimary")
                .setLabel("Sensibilita\'")
                .setRange(0, 350)
                .setValue(settings.getSensitivityPrimary())
                .setPosition(45, 400)
                .setSize(200, 20);
            
            this.cp5.addSlider("sensSecondary")
                .setLabel("Sensibilita\'")
                .setRange(0, 350)
                .setValue(settings.getSensitivityPrimary())
                .setPosition(525, 400)
                .setSize(200, 20);
            
            this.cp5.addButton("changeAnimationPrimary")
                .setLabel("Cambia Animazione")
                .setPosition(45, 450)
                .setSize(100, 20)
                .activateBy(ControlP5.RELEASE)
                .setValue(settings.getModelPrimary())
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        int animationNumberPrimary = (int) callbackEvent.getController().getValue();
                        animationNumberPrimary++;

                        if (animationNumberPrimary > animationHandler.numberOfAnimations) {
                            animationNumberPrimary = 0;
                        }

                        callbackEvent.getController().setValue(animationNumberPrimary);
                    }
                });
            
            this.cp5.addButton("changeAnimationSecondary")
                .setLabel("Cambia Animazione")
                .setPosition(525, 450)
                .setSize(100, 20)
                .activateBy(ControlP5.RELEASE)
                .setValue(settings.getModelSecondary())
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        int animationNumberSecondary = (int) callbackEvent.getController().getValue();
                        animationNumberSecondary++;

                        if (animationNumberSecondary > animationHandler.numberOfAnimations) {
                            animationNumberSecondary = 0;
                        }

                        callbackEvent.getController().setValue(animationNumberSecondary);
                    }
                });
            
            this.cp5.addButton("savePreset1")
                .setPosition(45, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 1")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        // TODO: save preset metodology (1)
                    }
                });
            
            this.cp5.addButton("savePreset2")
                .setPosition(160, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 2")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        // TODO: save preset metodology (2)
                    }
                });
            
            this.cp5.addButton("savePreset3")
                .setPosition(275, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 3")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        // TODO: save preset metodology (3)
                    }
                });
            
            this.cp5.addButton("savePreset4")
                .setPosition(390, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 4")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        // TODO: save preset metodology (4)
                    }
                });
            
            this.cp5.addButton("savePreset5")
                .setPosition(505, 550)
                .setSize(100, 20)
                .setLabel("Salva Preset 5")
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        // TODO: save preset metodology (5)
                    }
                });
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
            pg.text("Disegno Principale", 32 + 25, 105);
            pg.text("Disegno Secondario", 32 + 425 + 80, 105);

            pg.text("About Drusic", 725, 620);
            
            colorPickerPrimary.display();
            colorPickerPrimary.update();
            colorPickerSecondary.display();
            colorPickerSecondary.update();
        /**/
        pg.endDraw();

        updateColors();
        updateSensibilities();
        updateAnimationNumbers();
        checkForAboutPress();
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

    private void updateSensibilities() {
        settings.setSensitivityPrimary(this.cp5.getController("sensPrimary").getValue());
        settings.setSensitivitySecondary(this.cp5.getController("sensSecondary").getValue());
    }

    private void updateAnimationNumbers() {
        settings.setModelPrimary((int) this.cp5.getController("changeAnimationPrimary").getValue());
        settings.setModelSecondary((int) this.cp5.getController("changeAnimationSecondary").getValue());
    }

    private void checkForAboutPress() {
        if (mousePressed) {
            // fill(rgbToHsb(255, 0, 0, 255)); noStroke();
            // rect(width / 2 + 425 - 130, height / 2 + 325 - 55, 110, 40);
            if (mouseX > width / 2 + 425 - 130 && mouseX < width / 2 + 425 - 130 + 110) {
                if (mouseY > height / 2 + 325 - 55 && mouseY < height / 2 + 325 - 55 + 40) {
                    // fill(rgbToHsb(0, 255, 0, 255)); noStroke();
                    // rect(width / 2 + 425 - 130, height / 2 + 325 - 55, 110, 40);
                    if (Desktop.isDesktopSupported()) {
                        try {
                            this.desktop.browse(aboutMeLink);
                        } catch (Exception e) {}
                    }
                }
            }
            
        }
    }

}
