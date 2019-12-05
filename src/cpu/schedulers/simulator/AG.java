package cpu.schedulers.simulator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class AG {
    int arrivalTime;
    String name;
    int quantumTime;
    int burstTime;
    int priority;
    int AGFactor =0;
    int Worked = 0;
    Queue <AG> ReadyProcess = new LinkedList<>();
    String Color = "NON";
    ArrayList<AG>processes = new ArrayList<AG>();
    ArrayList<AG>processesAWT = new ArrayList<AG>();
    ArrayList<AG>Available = new ArrayList<AG>();
    ArrayList<save>outPut = new ArrayList<save>();

    class save{
        String name , color;
        int AT , BT;        //AT = begin time , BT = End time
        int WT , TAT;       //Waiting time  , Turn around time
        public void setData(String a , String col, int b , int c){name = a; color = col ;AT = b ; BT = c;}
    }
    
    private void setAGFactor() {
        this.AGFactor = burstTime+arrivalTime+priority;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setQuantumTime(int quantumTime) {
        this.quantumTime = quantumTime;
    }

    private boolean isAlive(AG process){
        if (process.burstTime == 0) {processes.remove(process); ReadyProcess.remove(process); return false;}
        else return true;
    }

    private int run(int time){
        if (this.burstTime<time){
            int tmp = this.burstTime;
            this.burstTime = 0;
            return tmp;
        }
        else if (time<this.halfQuantum()){
            this.Worked+=time;
            this.burstTime -= time;
            return time;
        }
        else {
            this.Worked += this.halfQuantum();
            this.burstTime -= this.halfQuantum();
            return this.halfQuantum();
        }
    }

    private void dealQuantum(){//incase of switching
        if (this.Worked == this.quantumTime)this.quantumTime += Math.ceil(this.quantumTime*0.10);
        else if (this.Worked<this.quantumTime)this.quantumTime += (this.quantumTime-this.Worked);
        this.Worked = 0;
    }
    private void updateReady(int currentTime){
        for (int i=0; i<processes.size(); i++){
            if (!ReadyProcess.contains(processes.get(i)) && processes.get(i).arrivalTime<=currentTime)ReadyProcess.add(processes.get(i));
        }
    }

    private int halfQuantum(){
        return (int)Math.ceil(this.quantumTime/2.0);
    }

    private  void saveProcess(int AT, int BT,String name , String color){
        save tmp = new save();
        tmp.AT = AT;
        tmp.BT = BT;
        tmp.name = name;
        tmp.color = color;
        outPut.add(tmp);
    }


    private void updateNON(int currentTime){
        sort();
        Available = new ArrayList<AG>();
        for (int i=0; i<processes.size(); i++){
            if (processes.get(i).arrivalTime<=currentTime){Available.add(processes.get(i));}
        }
        sortAG();
    }

    private boolean fullQuantum(){
        if (this.Worked == this.quantumTime)return true;
        else return false;
    }

    public ArrayList<save> SolveAG(){

        int currentTime = 0;
        save tmp = new save();
        AG currentProcess = processes.get(0);
        currentTime = currentProcess.arrivalTime;
        updateReady(currentTime);
        updateNON(currentTime);
        tmp.AT = currentTime;
        int processStart = currentTime;

        while (processes.size()>0) {
            currentTime += currentProcess.run(currentProcess.halfQuantum());
            updateReady(currentTime);
            updateNON(currentTime);
            if(!isAlive(currentProcess)) {saveProcess(processStart,currentTime,currentProcess.name , currentProcess.Color); currentProcess = null;}
            ////non preemptive
            if (currentProcess!=null && Available.size()>1 && !Available.get(0).equals(currentProcess)){
                currentProcess.dealQuantum();
                saveProcess(processStart,currentTime,currentProcess.name , currentProcess.Color);
                processStart = currentTime;
                currentProcess = Available.get(0);
                continue;
            }
            while (Available.size()>0 && Available.get(0).equals(currentProcess)){
                currentTime += currentProcess.run(1);
                updateReady(currentTime);
                updateNON(currentTime);

                if(!isAlive(currentProcess)) {
                    saveProcess(processStart,currentTime,currentProcess.name , currentProcess.Color);
                    currentProcess = null;
                    break;
                    }

                    else if (currentProcess.fullQuantum()) {
                    currentProcess.dealQuantum();
                    saveProcess(processStart,currentTime,currentProcess.name , currentProcess.Color);
                    processStart =currentTime;
                    currentProcess = ReadyProcess.remove();
                    break;
                    }

                    else if (!Available.get(0).equals(currentProcess)){
                    saveProcess(processStart,currentTime,currentProcess.name , currentProcess.Color);
                    processStart = currentTime;
                    currentProcess.dealQuantum();
                    currentProcess = Available.get(0);
                    break;
                    }
            }

            if (currentProcess == null && ReadyProcess.size()>0){
                processStart = currentTime;
                currentProcess = ReadyProcess.remove();
            }

            else if (currentProcess == null && processes.size()>0){
                for (int i=0; i<processes.size(); i++){
                    if (processes.get(i).arrivalTime>currentTime){currentTime=processes.get(i).arrivalTime;currentProcess=processes.get(i);processStart=currentTime; break;}
                }
            }

    }
        return outPut;
    }

    AG(){}////Default Constructor
    AG(int ArrivalTime, int BurstTime, String name){
        this.arrivalTime = ArrivalTime;
        this.burstTime = BurstTime;
        this.name = name;
    }
    AG(Input input){
        AG tmp = new AG();
        for (int i=0; i<input.numberOfProcesses; i++){
            tmp.setBurstTime(input.burstTimes.get(i));
            tmp.setArrivalTime(input.arrivalTimes.get(i));
            tmp.setPriority(input.priorityNumbers.get(i));
            tmp.setQuantumTime(input.timeQuantum);
            tmp.name = input.names.get(i);
            tmp.Color = input.colors.get(i);
            tmp.setAGFactor();
            ///tmp is ready
            processes.add(tmp);
            tmp = new AG();
            ///processes is ready
        }
        sort();
        for (int i=0; i<processes.size(); i++)
        processesAWT.add(new AG(processes.get(i).arrivalTime,processes.get(i).burstTime,processes.get(i).name));
        outPut = SolveAG();
        outPut.sort(Comparator.comparing(save->save.name));
        /*
        System.out.println("name color begin end WT TAT");
        for(int i=0 ; i < outPut.size();++i){
            AG.save o = outPut.get(i);
            System.out.println(o.name+ " "+o.color + " "+ o.AT+" " +  o.BT);
        }
        */
    }
    public double getAwt(){
        double AWT =0;
        HashMap<String,Integer> at = new HashMap<String, Integer>();
        HashMap<String,Integer> et = new HashMap<String, Integer>();
        HashMap<String,Integer> wt = new HashMap<String, Integer>();
        for (int i=0; i<processesAWT.size(); i++){
            String name = processesAWT.get(i).name;
            int AT = processesAWT.get(i).arrivalTime;
            int WT =  processesAWT.get(i).burstTime;
            at.put(name,AT);
            wt.put(name,WT);
        }
        for (int i=0; i<outPut.size(); i++){
            String name = outPut.get(i).name;
            int BT = outPut.get(i).BT;
            et.put(name,BT);
        }
        for (String i : at.keySet()){
            AWT += ((et.get(i) - at.get(i))-wt.get(i));
        }
        return (AWT/processesAWT.size());
    }
    public double getATAT(){
        double ATAT =0;
        HashMap<String,Integer> at = new HashMap<String, Integer>();
        HashMap<String,Integer> et = new HashMap<String, Integer>();
        for (int i=0; i<processesAWT.size(); i++){
            String name = processesAWT.get(i).name;
            int AT = processesAWT.get(i).arrivalTime;
            at.put(name,AT);
        }
        for (int i=0; i<outPut.size(); i++){
            String name = outPut.get(i).name;
            int BT = outPut.get(i).BT;
            et.put(name,BT);
        }
        for (String i : at.keySet()){
            ATAT += (et.get(i) - at.get(i));
        }
        return ATAT/processesAWT.size();
    }
    private void sort(){
        for (int i=0; i<processes.size(); i++){
            AG smaller = processes.get(i);
            int index = i;
            for (int j=i+1; j<processes.size(); j++){
                if (processes.get(j).arrivalTime<smaller.arrivalTime){
                    smaller = processes.get(j);
                    index = j;
                }
            }
            AG tmp  = processes.get(i);
            processes.set(i,smaller);
            processes.set(index,tmp);
        }
    }
    private void sortAG(){
        for (int i=0; i<Available.size(); i++){
            AG smaller = Available.get(i);
            int index = i;
            for (int j=i+1; j<Available.size(); j++){
                if (Available.get(j).AGFactor<smaller.AGFactor){
                    smaller = Available.get(j);
                    index = j;
                }
            }
            AG tmp  = Available.get(i);
            Available.set(i,smaller);
            Available.set(index,tmp);
        }
    }
}
