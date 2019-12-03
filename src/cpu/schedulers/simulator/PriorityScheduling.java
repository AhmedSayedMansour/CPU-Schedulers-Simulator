package cpu.schedulers.simulator;

import java.util.*;

public class PriorityScheduling {
    double AWT = 0, ATAT = 0;
    class save {
        String name , color; 
        int ST , CT , WT ,TT;   //start time , complete time , waiting time , turn around time
        public void setData(String a , String col, int b , int c,int d , int e){
            name = a;
            color = col ;
            ST = b ;
            CT = c;
            WT=d;
            TT=e;
        }
    }
    class Process { 
        String name, color;	
        int AT, BT, p;
        public void setData(String a , String col, int b , int c,int d) {
            name=a;
            color=col;
            AT=b;
            BT=c;
            p=d;	
        }
    }
    class MyComparator implements Comparator {
        public int compare(Object o1,Object o2) 
        { 

            Process p1 = (Process)o1; 
            Process p2 = (Process)o2; 
            if (p1.AT < p2.AT) 
                return (-1); 

            else if (p1.AT == p2.AT && p1.p > p2.p) 
                return (-1); 

            else
                return (1); 
        }
    }   

    ArrayList<Process>process=new  ArrayList<>();
    ArrayList<save> save = new ArrayList<save>();
    int n=0;    
    PriorityScheduling(Input in){
        n=in.numberOfProcesses;
        for(int i=0 ; i<n ; i++)
        {		
            Process a= new Process();
            a.setData(in.names.get(i), in.colors.get(i), in.arrivalTimes.get(i), in.burstTimes.get(i), in.priorityNumbers.get(i));
            process.add(a);
        }
        run();
        for (int i=0 ; i < save.size() ; ++i){
            AWT += save.get(i).WT;
            ATAT += save.get(i).TT;
        }
        AWT/=n;
        ATAT/=n;
    }
	
    public void run() {
        int time=0;
        PriorityQueue <Process> p = new	PriorityQueue <Process>(n,new MyComparator());
        for (int i=0;i<n;i++)   p.add(process.get(i));
        time=p.peek().AT;
        while (!p.isEmpty()) {
            Process a= p.poll();
            save b=new save();
            b.setData(a.name, a.color, time, time+a.BT, time-a.AT,(time+a.BT)-a.AT);
            save.add(b);
            time=time+a.BT;
        }
    }
}
