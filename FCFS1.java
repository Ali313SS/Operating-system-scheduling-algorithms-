import java.util.*;
public class FCFS1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N;
        System.out.println("Enter Number of process");
        N = in.nextInt();
        Process P[] = new Process[N];
        int Brust[] = new int[N];
        int Arrival[] = new int[N];
        for (int i = 0; i < N; i++) {
            int A, B;
            System.out.println("Enter Arrival Time for p" + (i + 1));
            A = in.nextInt();
            System.out.println("Enter Brust Time for p" + (i + 1));
            B = in.nextInt();
            Arrival[i] = A;
            Brust[i] = B;
            P[i] = new Process(i,A, B);

        }
        Arrays.sort(P);
        FCFS F=new FCFS(P);
        System.out.println("P\t\tA.T\t\tB.T\t\tW.T\t\tT.A.T");
        for (int i = 0; i < N; i++) {

            System.out.println((i + 1) + "\t\t " + P[i].Arrival + "\t\t " +P[i].burstTime + "\t\t " + F.Get_Waiting_Time_for(i + 1) + "\t\t " + F.Get_Turnaround_Time_for(i + 1));
        }
        System.out.println("Averge WaitingTime:" + F.Get_Averege_Waiting_Time());
        System.out.println("Averge TurnaroundTime:" + F.Get_Averege_Turnaround_Time());
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
    class FCFS {
        private int WatingTime[];
        private int Trunaround[];
        private int N ;

        public FCFS(Process[] Arr) {
            int W = 0;
            N=Arr.length;
            WatingTime = new int[N];
            Trunaround = new int[N];
            for (Process it : Arr) {
                W=Math.max(W,it.Arrival);
                WatingTime[it.id] = W-it.Arrival;
                Trunaround[it.id] = W +it.burstTime - it.Arrival;
                W += it.burstTime;
            }
        }

        int Get_Waiting_Time_for(int idx) {
            return this.WatingTime[idx - 1];
        }

        int Get_Turnaround_Time_for(int idx) {
            return this.Trunaround[idx - 1];
        }
        double Get_Averege_Waiting_Time() {
            double Sum=0;
            for (int it:this.WatingTime)
                Sum+=it;
            return Sum/N;
        }
        double Get_Averege_Turnaround_Time() {
            double Sum=0;
            for (int it:this.Trunaround)
                Sum+=it;
            return Sum/N;
        }
    }

