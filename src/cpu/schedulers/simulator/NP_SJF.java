package cpu.schedulers.simulator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
public class NP_SJF {

    ArrayList<String> Names = new ArrayList<>();
    ArrayList<Integer> arrivalTimes = new ArrayList<>();
    ArrayList<Integer> burstTimes = new ArrayList<>();
    ArrayList<String> Colors = new ArrayList<>();
    static ArrayList<save> processes = new ArrayList<>();  //The output queue
    double averageWaitingTime = 0 , averageTurnaroundTime = 0;

    NP_SJF(Input in)
    {
        for(int i=0 ; i<in.numberOfProcesses ; i++)
        {
            Names.add(in.names.get(i));
            Colors.add(in.colors.get(i));
            arrivalTimes.add(in.arrivalTimes.get(i));
            burstTimes.add(in.burstTimes.get(i));
        }
        run();
    }
    class save{
        String name , color;
        int arrivalTime , burstTime;
        int waitingTime , turnAroundTime;
        int endTime;

        public void setData(String n , String col, int a , int b){
            name = n;
            color = col;
            arrivalTime = a;
            burstTime = b;
        }
        public  void setTimes()
        {
            turnAroundTime = endTime - arrivalTime;
            waitingTime = turnAroundTime - burstTime;
        }
    }

    public void run()
    {
        int end = Collections.min(arrivalTimes);
        while(arrivalTimes.size() !=0)
        {
            int small = 0;
            //Get the smallest arrival time
            for(int i=1 ; i<arrivalTimes.size() ; i++)
            {
                if(arrivalTimes.get(i) < arrivalTimes.get(small)) small = i;
            }
            //Check if there any process with the same arrival time but with a smaller burst time than this process
            for(int i=0 ; i<arrivalTimes.size() ; i++)
            {
                if(arrivalTimes.get(i) == arrivalTimes.get(small) && burstTimes.get(i) < burstTimes.get(small))
                    small = i;
            }

            // Save processes
            save obj = new save();
            obj.setData(Names.get(small) , Colors.get(small) , arrivalTimes.get(small) , burstTimes.get(small));
            obj.endTime = end + obj.burstTime;
            obj.setTimes();
            end = obj.endTime;
            //Add the selected process to queue
            processes.add(obj);

            // Remove the finished process
            arrivalTimes.remove(small);
            burstTimes.remove(small);
            Names.remove(small);
            Colors.remove(small);
        }
        // Calculate Average waiting and turnaround time
        for(int i=0 ; i<processes.size() ; i++)
        {
            averageTurnaroundTime += processes.get(i).turnAroundTime;
            averageWaitingTime += processes.get(i).waitingTime;
        }
        averageTurnaroundTime /= processes.size();
        averageWaitingTime /= processes.size();
    }
}