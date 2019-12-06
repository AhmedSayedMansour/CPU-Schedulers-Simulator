package cpu.schedulers.simulator;

import java.util.*;

public class PriorityScheduling {

    class save {

        String name, color;
        int ST, CT, WT, TT;

        public void setData(String a, String col, int b, int c, int d, int e) {
            name = a;
            color = col;
            ST = b;
            CT = c;
            WT = d;
            TT = e;
        }
    }

    class Process {

        String name, color;
        int AT, BT, p;

        public void setData(String a, String col, int b, int c, int d) {
            name = a;
            color = col;
            AT = b;
            BT = c;
            p = d;
        }

    }

    class MyComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {

            Process p1 = (Process) o1;
            Process p2 = (Process) o2;
            if (p1.AT < p2.AT) {
                return (-1);
            } else if (p1.AT == p2.AT && p1.p < p2.p) {
                return (-1);
            } else if (p1.AT == p2.AT && p1.p == p2.p && p1.BT > p1.BT) {
                return (-1);
            } else {
                return (1);
            }
        }
    }

    class comp implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            Process p1 = (Process) o1;
            Process p2 = (Process) o2;
            if (p1.p < p2.p) {
                return (-1);
            } else if (p1.p == p2.p && p1.BT <= p1.BT) {
                return (-1);
            } else {
                return 1;
            }
        }

    }
    Double ATAT = 0.0, AWT = 0.0;
    ArrayList<Process> process = new ArrayList<>();
    ArrayList<save> save = new ArrayList<save>();
    int n = 0;

    PriorityScheduling(Input in) {
        n = in.numberOfProcesses;
        for (int i = 0; i < n; i++) {

            Process a = new Process();
            a.setData(in.names.get(i), in.colors.get(i), in.arrivalTimes.get(i), in.burstTimes.get(i), in.priorityNumbers.get(i));
            process.add(a);

        }
        run();
        for (int i = 0; i < n; i++) {
            ATAT += save.get(i).TT;
            AWT += save.get(i).WT;
        }

        ATAT /= n;
        AWT /= n;
    }

    public void run() {
        int time = 0;
        PriorityQueue<Process> readyqueue = new PriorityQueue<Process>(new MyComparator());
        PriorityQueue<Process> WorkQueue = new PriorityQueue<Process>(new comp());
        for (int i = 0; i < n; i++) {

            readyqueue.add(process.get(i));
        }

        time = readyqueue.peek().AT;
        while (!readyqueue.isEmpty()) {

            while (!readyqueue.isEmpty()) {
                Process a = readyqueue.peek();
                if (a.AT <= time) {
                    readyqueue.remove();
                    if (a.p > 5) {
                        a.p--;
                    }
                    WorkQueue.add(a);
                } else {
                    break;
                }
            }
            Process c = WorkQueue.poll();
            save b = new save();
            b.setData(c.name, c.color, time, time + c.BT, time - c.AT, (time + c.BT) - c.AT);
            save.add(b);
            time = time + c.BT;
            while (!WorkQueue.isEmpty()) {
                Process a = WorkQueue.poll();
                readyqueue.add(a);
            }
        }

    }

}
