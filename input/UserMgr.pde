public class UserMgr {

    private ControlP5 cp5;
    private PGraphics pg;
    private PFont drusicFont;
    private boolean userWindowVisible;

    public UserMgr() {
        this.cp5 = new ControlP5(myPApplet);
        this.pg = createGraphics(850, 650);
        this.drusicFont = loadFont("LandslideSample-48.vlw");
        this.userWindowVisible = true;
        this.updateMouseStatus();
    }

    public void changeVisibility() {
        this.userWindowVisible = !this.userWindowVisible;
        this.cp5.setVisible(this.isVisibile());
        this.updateMouseStatus();
    }

    public boolean isVisibile() {
        return this.userWindowVisible;
    }

    public void updateMouseStatus() {
        if (this.isVisibile()) {
            cursor();
        } else {
            noCursor();
        }
    }

    public void initializeWindowComponents() {
        this.cp5.setVisible(this.isVisibile());
        pg.beginDraw();
        /**/
            this.cp5.addTextfield("username")
                .setPosition(20, 240)
                .setFont(new ControlFont(createFont("Calibri", 14, true)))
                .setSize(200, 24)
                .setFocus(true);
            
            this.cp5.addTextfield("password")
                .setPosition(20, 290)
                .setSize(200, 24)
                .setPasswordMode(true);
            
            this.cp5.addButton("LogIn")
                .setSize(100, 20)
                .setPosition(70, 340)
                .activateBy(ControlP5.RELEASE)
                .onPress(new CallbackListener() {
                    public void controlEvent(CallbackEvent callbackEvent) {
                        userMgr.logIn();
                    }
                });
        /**/
        pg.endDraw();
    }

    public void showWindow() {
        this.cp5.setVisible(this.isVisibile());
        pg.beginDraw();
        /**/
            this.cp5.setGraphics(pg, width / 2 - 425, height / 2 - 325);
            noFill();

            this.pg.background(rgbToHsb(117, 117, 117, 34));
            
            fill(rgbToHsb(191, 191, 191, 100));
            stroke(rgbToHsb(settings.getColor(1)[0],
                                    settings.getColor(1)[1],
                                    settings.getColor(1)[2], 255));
            rect(width / 2 - 425, height / 2 - 325, 850, 650);

            pg.fill(rgbToHsb(255, 255, 255,255));
            pg.textFont(this.drusicFont);
            pg.text("Drusic - LogIn", 12, 60);

            pg.textSize(18);
            pg.text("Eseguire l\'accesso.\nPer gli utenti non registrati la registrazione avverra\' automaticamente al login.\nN.B: Non sara\' possibile modificare lo username.", 12, 120);

        /**/
        pg.endDraw();
    }

    public void logIn() {
        String username = this.cp5.get(Textfield.class, "username").getText();
        String password = this.cp5.get(Textfield.class, "password").getText();
        try {
            URL uploadUrl = new URL("http://localhost/drusic_api.php?function=logIn&user=" + username + "&pass=" + this.md5(password));
            URLConnection urlConn = uploadUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String inputLine;
            String result = "";
            
            while ((inputLine = in.readLine()) != null) {
                result = inputLine;
            }

            System.out.println(result);
            try {
                if (Integer.parseInt(result) != 0) {
                    loggedIn = true;
                    userId = Integer.parseInt(result);
                    this.changeVisibility();
                }
            } catch (Exception e) {}

            in.close();
        } catch (MalformedURLException e1) {} catch (IOException e2) {}
    }

    private String md5(String pw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder(32);
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xFF));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {}
        return "";
    }

}
