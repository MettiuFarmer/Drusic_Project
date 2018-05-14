public class PresetSaver {

    private int idNumber;

    public PresetSaver(int id) {
        this.idNumber = id;
    }

    public void saveAndUpload() {
        String data = "";
        
        int[] tmpColor;

        tmpColor = settings.getColor(1);
        data += Integer.toString(tmpColor[0]);
        data += "-";
        data += Integer.toString(tmpColor[1]);
        data += "-";
        data += Integer.toString(tmpColor[2]);
        data += "-";
        data += Integer.toString(settings.getModelPrimary());
        data += "-";
        data += Float.toString(settings.getSensitivityPrimary());
        
        data += "@";

        tmpColor = settings.getColor(2);
        data += Integer.toString(tmpColor[0]);
        data += "-";
        data += Integer.toString(tmpColor[1]);
        data += "-";
        data += Integer.toString(tmpColor[2]);
        data += "-";
        data += Integer.toString(settings.getModelSecondary());
        data += "-";
        data += Float.toString(settings.getSensitivitySecondary());

        try {
            URL uploadUrl = new URL("http://localhost/drusic_api.php?function=uploadPreset&presetNumber=" + this.idNumber + "&preset=" + data);
            URLConnection urlConn = uploadUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String inputLine;
            
            /*
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            */

            in.close();
        } catch (MalformedURLException e1) {} catch (IOException e2) {}
    }

    public void downloadAndLoad() {
        try {
            URL uploadUrl = new URL("http://localhost/drusic_api.php?function=downloadPreset&presetNumber=" + this.idNumber);
            URLConnection urlConn = uploadUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String inputLine;
            String presetsToDecompose = "";

            while ((inputLine = in.readLine()) != null) {
                presetsToDecompose = inputLine;
            }
            
            if (presetsToDecompose != "") {
                String []presetsDecomposed = presetsToDecompose.split("@");
                
                String []presetPrimary = presetsDecomposed[0].split("-");
                String []presetSecondary = presetsDecomposed[1].split("-");

                settings.changeColor(Integer.parseInt(presetPrimary[0]),
                                     Integer.parseInt(presetPrimary[1]),
                                     Integer.parseInt(presetPrimary[2]), 1);
                settings.setModelPrimary(Integer.parseInt(presetPrimary[3]));
                settings.setSensitivityPrimary(Float.parseFloat(presetPrimary[4]));

                settings.changeColor(Integer.parseInt(presetSecondary[0]),
                                     Integer.parseInt(presetSecondary[1]),
                                     Integer.parseInt(presetSecondary[2]), 2);
                settings.setModelSecondary(Integer.parseInt(presetSecondary[3]));
                settings.setSensitivitySecondary(Float.parseFloat(presetSecondary[4]));
            }

            in.close();
        } catch (MalformedURLException e1) {} catch (IOException e2) {}
    }

}
