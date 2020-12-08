import java.util.Scanner;
public class RoundRobin {
int Count=0;
int[][] processData;
int[] CCycle; // Complete Cycle
int[] PCycle; // Partial Cycle
int[] TCycle; // Total Cycle
String[] Queue;
boolean[] Status;
int[] startQue;
int[] endQue;
int RTT = 0;
Scanner scan = new Scanner(System.in);
public void scheduleRR(){
int TotalProcess = CCycle.length;
for(int i = 0,j=0;i < Queue.length || j<Queue.length;i++){
int ProNo = i % TotalProcess;
if(CCycle[ProNo] != 0){
Queue[j] = "process["+ProNo+"]";
if(j==0)
startQue[j] = 0;
else
startQue[j] = endQue[j-1] + 1;
if(!Status[ProNo]){
processData[ProNo][2] = startQue[j];
Status[ProNo] = true;}
endQue[j] = startQue[j]+RTT-1;
j++;
CCycle[ProNo] = CCycle[ProNo] - 1;
}else{
if(Status[ProNo]){
Queue[j] = "process["+ProNo+"]";
if(j==0)
startQue[j] = 0;
else
startQue[j] = endQue[j-1] + 1;
endQue[j] = startQue[j]+PCycle[ProNo]-1;
processData[ProNo][3] = endQue[j];
PCycle[ProNo]=0;
j++;
Status[ProNo]= false;
}
}
}
for (int i = 0; i < Queue.length ; i++){
int temp = (endQue[i]-startQue[i]+1);
System.out.println(" "+Queue[i]+" "+startQue[i]+" "+endQue[i] +""+temp );
if(i%Count == Count-1)
System.out.println("");
}}
public void setValue(){
System.out.print("Enter Number Of processes : ");
Count = scan.nextInt();
System.out.print("Enter Round Trip Time : ");
RTT = scan.nextInt();
processData = new int[Count][4];
CCycle = new int[Count];
PCycle = new int[Count];
TCycle = new int[Count];
for(int i=0;i<Count;i++){
System.out.print("Processes["+i+"] :-> ");
processData[i][0] = i;
processData[i][1] = scan.nextInt();
CCycle[i] = processData[i][1]/RTT;
PCycle[i] = processData[i][1]%RTT;
if(PCycle[i]==0)
TCycle[i] = CCycle[i];
else
TCycle[i] = CCycle[i] + 1;
System.out.println("P["+i+"] CC : "+CCycle[i]+" PC : "+PCycle[i]+" TC :"+TCycle[i]);
}
Status = new boolean[Count];
for(int i=0;i<Count;i++){
Status[i]= false;
}
initQueue();
}
public void initQueue(){
int MaxQueLen = 0;
for(int i=0;i<TCycle.length;i++){
MaxQueLen = MaxQueLen + TCycle[i];
}
Queue = new String[MaxQueLen];
startQue = new int[MaxQueLen];
endQue = new int[MaxQueLen];
System.out.println("Queue Length : "+MaxQueLen);
}
public void DisplayValue(){
System.out.println("------------------------------------------------------------------------");
System.out.println("Process Number || Brust Time || Wating Time || TurnAround Time");
System.out.println("------------------------------------------------------------------------");
for(int i=0;i<Count;i++){
System.out.println("Processes["+processData[i]
[0]+"]\t||\t"+processData[i][1]+"\t||\t"+processData[i][2]+"\t||\t"+processData[i][3]);
}
System.out.println("-------------------------------------------------------------------------");
}
public static void main(String a[]){RoundRobin rr = new RoundRobin();
rr.setValue();
System.out.println("\n\n***********Round Robin : (RR) :***********\n");
System.out.println("\n\nBefore Scheduling\n");rr.DisplayValue();
rr.scheduleRR();
System.out.println("\n\nAfter Scheduling\n");
rr.DisplayValue();
}
}
