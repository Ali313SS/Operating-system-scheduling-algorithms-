import java.util.*;

public class Preemptive_Priority1 {
    public static void main(String[] args) {

        Scanner in=new Scanner(System.in);
        System.out.println("Enter Number of process");
        int N=in.nextInt();
        ArrayList<Process>Arr=new ArrayList<>();
        for (int i=0; i<N; i++)
        {
            int A,B,P;
            System.out.println("Enter Arrival Time for p" + (i + 1));
            A=in.nextInt();
            System.out.println("Enter Brust Time for p" + (i + 1));
            B=in.nextInt();
            System.out.println("Enter Priority for p" + (i + 1));
            P=in.nextInt();
            Arr.add(new Process(i,P,B,A));
        }
        Collections.sort(Arr);
        Queue<Process>Q=new LinkedList<>();
        for (Process it:Arr) {
            Q.add(new Process(it.id,it.priority,it.burstTime,it.Arrival));
        }
        Premmptive_Priority PP=new Premmptive_Priority(Q);
        System.out.println("P\t\tA.T\t\tB.T\t\tP.r\t\tW.T\t\tT.A.T");
        for (int i = 0; i < N; i++) {

            System.out.println((i + 1) + "\t\t " + Arr.get(i).priority + "\t\t " +Arr.get(i).burstTime + "\t\t " +Arr.get(i).Arrival+"\t\t "+ PP.Get_Waiting_Time_for(i+1) + "\t\t " + PP.Get_Turnaround_Time_for(i + 1));
        }
        System.out.println("Averge WaitingTime:" + PP.Get_Averege_Waiting_Time());
        System.out.println("Averge TurnaroundTime:" + PP.Get_Averege_Turnaround_Time());


    }
}
class Process implements Comparable<Process> {
    int id;
    int Arrival;
    int burstTime;
    int priority;
    public Process(int id,int Arrival,  int burstTime,int priority) {
        this.id = id;
        this.Arrival=Arrival;
        this.burstTime = burstTime;
        this.priority=priority;

    }
    @Override
    public int compareTo(Process other) {
        if(this.priority!=other.priority)
            return this.priority-other.priority;
        if(this.Arrival==other.Arrival)
            return this.id-other.id;
        return this.Arrival-other.Arrival;
    }
}
class Premmptive_Priority{
    private int WaitingTime[];
    private int TurnaroundTime[];
    private int N;
    public Premmptive_Priority(Queue<Process>Q)
    {
        N=Q.size();
        WaitingTime=new int[N];
        TurnaroundTime=new int[N];
        build(Q);
    }
    private void build(Queue<Process>Q)
    {
        for(Process it:Q) {
            WaitingTime[it.id] = -it.burstTime - it.Arrival;
            TurnaroundTime[it.id]=it.burstTime;

        }
        int CurntTime=0;
        PriorityQueue<Process>PQ=new PriorityQueue<>();
        while (!Q.isEmpty()||!PQ.isEmpty())
        {
            while (!Q.isEmpty()&&Q.peek().Arrival<=CurntTime)
                PQ.add(Q.poll());
            if(!PQ.isEmpty())
            {
                Process D=PQ.poll();
                int NextProcess=(int)2E9;
                if(!Q.isEmpty())
                    NextProcess=Q.peek().Arrival;
                int Ex=Math.min(D.burstTime,NextProcess-CurntTime);
                D.burstTime-=Ex;
                CurntTime+=Ex;
                if(D.burstTime>0)
                    PQ.add(D);
                else {
                    WaitingTime[D.id] += CurntTime;
                    TurnaroundTime[D.id] += WaitingTime[D.id];
                }
            }
            else
            {
                CurntTime=Q.peek().Arrival;
            }
        }

    }
    int Get_Waiting_Time_for(int idx) {
        return this.WaitingTime[idx - 1];
    }
    int Get_Turnaround_Time_for(int idx) {
        return this.TurnaroundTime[idx - 1];
    }
    double Get_Averege_Waiting_Time() {
        double Sum=0;
        for (int it:this.WaitingTime)
            Sum+=it;
        return Sum/N;
    }
    double Get_Averege_Turnaround_Time() {
        double Sum=0;
        for (int it:this.TurnaroundTime)
            Sum+=it;
        return Sum/N;
    }
}
