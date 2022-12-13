package data;

public class Data {
    private final String fromFileName;
    private final String dataString;

    public Data(String fromFileName, String dataString){
        this.fromFileName = fromFileName;
        this.dataString = dataString;
    }

    @Override
    public String toString(){return fromFileName + ": Data = " + dataString;}
}
