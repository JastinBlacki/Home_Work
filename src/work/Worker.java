package work;

import data.Data;

import java.util.ArrayDeque;
import java.util.Timer;
import java.util.TimerTask;

public class Worker {
    private static Worker instance;

    public static Worker getInstance(){
        if(instance == null)
            instance = new Worker();
        return instance;
    }

    private Worker(){
        queue = new ArrayDeque<Data>();
        running = false;
        myTimer = new Timer();
        work = new WorkByShedule();
    }

    private final ArrayDeque<Data> queue;
    private boolean running;
    private Timer myTimer;
    private WorkByShedule work;
    private final int PERIOD_LONG = 1000;
    private final int PERIOD_SHORT = 25;
    private int period = PERIOD_LONG;
    private int period_old = PERIOD_SHORT;
    private int emptyCounterOfQueueSize = 0;

    public boolean isRunning(){
        return running;
    }

    private class WorkByShedule extends TimerTask{
        @Override
        public void run(){
            if(running){
                workWithDataToQueue();
            }
            updateToQueueInfo(queue.size());
            if (period == PERIOD_LONG) {
                emptyCounterOfQueueSize++;
                if(emptyCounterOfQueueSize > 15)
                    StopWork();
            }
        }
    }

    private void workWithDataToQueue(){
        if(queue.size()>0){
            System.out.println(queue.poll());;
        }
        else System.err.println("No Data!");
    }

    private void updateToQueueInfo(int queueSize){
        period = (queueSize == 0) ? PERIOD_LONG : PERIOD_SHORT;
        if(period != period_old){
            restartWork();
        }
        period_old = period;
    }

    private void restartWork(){
        myTimer.cancel();
        myTimer = new Timer();
        work = new WorkByShedule();
        myTimer.scheduleAtFixedRate(work, 0, period);
        emptyCounterOfQueueSize = 0;
    }

    private void StartWork(){
        myTimer.scheduleAtFixedRate(work, 0, period);
        running = true;
    }

    private void StopWork(){
        myTimer.cancel();
        running = false;
    }

    public void startWorkProcess(){
        StartWork();
    }

    public void stopWorkProcess(){
        StopWork();
    }

    public void AddDataToWork(Data data){
        addDataToQueue(data);
    }

    private void addDataToQueue(Data data){
        synchronized (queue){
            queue.add(data);
        }
    }

}
