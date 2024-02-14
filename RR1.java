import java.util.*;

public class RR1
{
    public static void main(String []args)
    {

        int N,Q;
        Scanner in=new Scanner(System.in);
        System.out.println("Enter Number of process");
        N=in.nextInt();
        System.out.println("Enter Quantum");
        Q=in.nextInt();
        ArrayList<Process>D=new ArrayList<>();
        int BrustTime[]=new int[N];
        for (int i=0; i<N; i++)
        {
            int A,B;
            System.out.println("Enter Arrival Time for p" + (i + 1));
            A= in.nextInt();
            System.out.println("Enter Brust Time for p" + (i + 1));
            B= in.nextInt();
            BrustTime[i]=B;
            D.add(new Process(i,A,B));
       }
        Collections.sort(D);
        Queue<Process>P=new LinkedList<>(D);
        RR S=new RR(P,D,Q);
        System.out.println("P\t\tA.T\t\tB.T\t\tW.T\t\tT.A.T");
        for (int i = 0; i < N; i++) {

            System.out.println((i + 1) + "\t\t " + D.get(i).Arrival + "\t\t " +BrustTime[i] + "\t\t " + S.Get_Waiting_Time_for(i + 1) + "\t\t " + S.Get_Turnaround_Time_for(i + 1));
        }
        System.out.println("Averge WaitingTime:" + S.Get_Averege_Waiting_Time());
        System.out.println("Averge TurnaroundTime:" + S.Get_Averege_Turnaround_Time());

    }
}
class Process implements Comparable<Process> {
    int id;
    int Arrival;
    int burstTime;

    public Process(int id,int Arrival,  int burstTime) {
        this.id = id;
        this.Arrival=Arrival;
        this.burstTime = burstTime;

    }
    @Override
    public int compareTo(Process other) {

        if(this.Arrival==other.Arrival)
            return this.id-other.id;
        return this.Arrival-other.Arrival;
    }
}
class RR{
    int N,Q;
    int WaitingTime[];
    int TurnaroundTime[];
    public RR(Queue<Process>P,ArrayList<Process>D,int q)
    {
        N=P.size();
        TurnaroundTime=new int[N];
        WaitingTime=new int[N];
        this.Q=q;
        for (int i=0; i<N; i++)
        {
            WaitingTime[i]=-D.get(i).burstTime-D.get(i).Arrival;
            TurnaroundTime[i]=-D.get(i).Arrival;
        }
        build(P);
    }
    void build(Queue<Process>P) {
        int CurntTime = 0;
        Queue<Process> queue = new LinkedList<>();
        while (!P.isEmpty() || !queue.isEmpty()) {
            int end = CurntTime + Q;
            while (!P.isEmpty()) {
                if (end >= P.peek().Arrival)
                    queue.add(P.poll());
                else
                    break;
            }
            if(queue.isEmpty())
                CurntTime=P.peek().Arrival;
            else {
                int W=Math.min(queue.peek().burstTime,Q);
                Process T=queue.poll();
                T.burstTime-=W;
                CurntTime+=W;
                if(T.burstTime==0)
                {
                    WaitingTime[T.id]+=CurntTime;
                    TurnaroundTime[T.id]+=CurntTime;
                }
                else queue.add(T);
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
