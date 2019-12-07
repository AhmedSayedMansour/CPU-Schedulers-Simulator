package cpu.schedulers.simulator;

import java.util.ArrayList;
import java.util.Comparator;
 
public class NP_SJF {
 
    static ArrayList<save> processes = new ArrayList<>();  //The output queue
    double averageWaitingTime = 0 , averageTurnaroundTime = 0;
 
    NP_SJF(Input in)
    {
        for(int i=0 ; i<in.numberOfProcesses ; i++)
        {
            processes.add(new save(in.names.get(i) , in.colors.get(i) , in.arrivalTimes.get(i) , in.burstTimes.get(i)));
        }
        run();
    }
    class save{
        String name , color;
        int arrivalTime , burstTime;
        int waitingTime , turnAroundTime;
        int beginTime , endTime;
 
        save(String n , String col, int a , int b)
        {
            name = n;
            color = col;
            arrivalTime = a;
            burstTime = b;
        }
    }
 
    public void run()
    {
        Comparator<save> comparator = Comparator.comparing(save->save.arrivalTime);
        comparator = comparator.thenComparing(save->save.burstTime);
 
        processes.sort(comparator);
 
        int lastProcess = 0;
        for (int i=0 ; i<processes.size() ; i++)
        {
            if(processes.get(i).arrivalTime <= lastProcess)
                processes.get(i).beginTime = lastProcess;
            else
                processes.get(i).beginTime = lastProcess + processes.get(i).arrivalTime;
            processes.get(i).endTime = processes.get(i).beginTime + processes.get(i).burstTime;
 
            lastProcess = processes.get(i).endTime;
 
            processes.get(i).turnAroundTime = processes.get(i).endTime - processes.get(i).arrivalTime;
            processes.get(i).waitingTime = processes.get(i).turnAroundTime - processes.get(i).burstTime;
        }
 
        //Calculate Average waiting and turnaround time
        for(int i=0 ; i<processes.size() ; i++)
        {
            averageTurnaroundTime += processes.get(i).turnAroundTime;
            averageWaitingTime += processes.get(i).waitingTime;
        }
        averageTurnaroundTime /= processes.size();
        averageWaitingTime /= processes.size();
    }
}