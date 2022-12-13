package work;

import data.Data;

import java.io.*;

public class Work implements Runnable{
    private final String fileName;
    private final Worker worker;

    public Work(String fileName){
        this.fileName = fileName;
        worker = Worker.getInstance();
    }

    @Override
    public void run(){
        File file = new File(fileName);
        String readLine;
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                ){
            while ((readLine = bufferedReader.readLine()) != null){
                worker.AddDataToWork(new Data(fileName, readLine));
                Thread.sleep((long) (Math.random() * 151 + 53));
            }
        } catch (Exception ignored){
        }
    }
}
