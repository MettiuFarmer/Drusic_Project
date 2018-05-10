class Presets{

    public Presets() { }

    public void salvaPreset(int i) throws IOException {

        FileOutputStream out = new FileOutputStream("preset" + Integer.toString(i) + ".save");
        ObjectOutputStream preset = new ObjectOutputStream(out);
        preset.writeObject(sett);
    }

    public void caricaPreset(int i) throws IOException{
        try {
            FileInputStream in = new FileInputStream("preset" + Integer.toString(i) + ".save");
            ObjectInputStream s = new ObjectInputStream(in);
            Settings importedSettings = (Settings) s.readObject();
            sett.setModelp(importedSettings.getModelp());
            sett.setColore(importedSettings.getColore(1)[0], importedSettings.getColore(1)[1], importedSettings.getColore(1)[2], 1);
            sett.setColore(importedSettings.getColore(2)[0], importedSettings.getColore(2)[1], importedSettings.getColore(2)[2], 2);
            sett.setColore(importedSettings.getColore(3)[0], importedSettings.getColore(3)[1], importedSettings.getColore(3)[2], 3);
            sett.setSize(importedSettings.getSizeX(), importedSettings.getSizeY());
            sett.setModels(importedSettings.getModels());
            sett.setSensivityp(importedSettings.getSensivityp());
            sett.setSensivitys(importedSettings.getSensivitys());
            sw.changeSettings(importedSettings);
        } catch (ClassNotFoundException e) {}
    }

    private byte[] compressPresets() throws IOException {
        byte[] byteArray = new byte[0];
        try {
            ArrayList<Object> presets = new ArrayList<Object>();
            for (int i = 0; i < 5; i++) {
                FileInputStream in = new FileInputStream("preset" + Integer.toString(i) + ".save");
                ObjectInputStream s = new ObjectInputStream(in);
                presets.add((Settings) s.readObject());
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            ObjectOutputStream objout = new ObjectOutputStream(out);
            objout.writeObject(presets);
            byteArray = out.toByteArray();
        } catch (ClassNotFoundException e) {}
        return byteArray;
    }

    private void decompressPresets() throws IOException {
        //todo
    }

    public void uploadPresetsToDB(int presets) throws Exception{
        try {
            byte[] data = compressPresets();
            //todo: upload (BUT create new column before!)
            conn.insertPreset(presets, data);
        } catch (IOException e) {}
    }

    public void downloadPresetsFromDB() throws Exception{
        decompressPresets();
        //todo
    }
}