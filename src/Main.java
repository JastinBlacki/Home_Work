import data.Data;
import work.Work;
import work.Worker;

public class Main {
    private final static int size = 8;
    public static void main(String[] args) throws InterruptedException{
        Worker worker = Worker.getInstance();
        worker.startWorkProcess();
        Thread[] myThreads = new Thread[size];
        Work[] myWork = new Work[size];
        for (int i = 0; i < size; i++) {
            myWork[i] = new Work(i + ".txt");
            myThreads[i] = new Thread(myWork[i]);
        }
        for (Thread t: myThreads) t.start();
        while (worker.isRunning()) Thread.currentThread().join(50);
        System.out.println("Work is Done!");
    }

}