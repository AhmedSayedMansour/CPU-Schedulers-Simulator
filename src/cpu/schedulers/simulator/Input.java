
package cpu.schedulers.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class Input {
    int numberOfProcesses;
    int timeQuantum;
    static int timeContext;
    
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> colors = new ArrayList<String>();
    ArrayList<Integer> arrivalTimes = new ArrayList<Integer>();
    ArrayList<Integer> burstTimes = new ArrayList<Integer>();
    ArrayList<Integer> priorityNumbers = new ArrayList<Integer>();
    //test
    /*
    public Input() {
        numberOfProcesses = 4;
        timeQuantum = 4;
        timeContext = 2;
        names.add("p1");    colors.add("red");  arrivalTimes.add(0);    burstTimes.add(17);  priorityNumbers.add(4);
        names.add("p2");    colors.add("yellow");  arrivalTimes.add(3);    burstTimes.add(6);  priorityNumbers.add(9);
        names.add("p3");    colors.add("blue");  arrivalTimes.add(4);    burstTimes.add(10);  priorityNumbers.add(3);
        names.add("p4");    colors.add("black");  arrivalTimes.add(29);    burstTimes.add(4);  priorityNumbers.add(8);
        //names.add("p5");    colors.add("pink");  arrivalTimes.add(4);    burstTimes.add(2);  priorityNumbers.add(3);
    }
    */
}
