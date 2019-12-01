
package cpu.schedulers.simulator;

import java.util.ArrayList;

public class Input {
    int numberOfProcesses;
    int timeQuantum;
    int timeContext;
    
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> colors = new ArrayList<String>();
    ArrayList<Integer> arrivalTimes = new ArrayList<Integer>();
    ArrayList<Integer> burstTimes = new ArrayList<Integer>();
    ArrayList<Integer> priorityNumbers = new ArrayList<Integer>();
}
