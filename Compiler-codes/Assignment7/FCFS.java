import java.util.Scanner;
public class FCFS {
int Count=0;
int[][] processData;
Scanner scan = new Scanner(System.in);
public void scheduleFCFS(){
processData[0][2] = 0;
processData[0][3] = processData[0][1];
for(int i=1;i<Count;i++){
processData[i][2] = processData[i-1][3];
processData[i][3] = processData[i][1]+processData[i][2];
}
}
public void setValue(){
System.out.print("Enter Number Of processes : ");
Count = scan.nextInt();
processData = new int[Count][4];
for(int i=0;i<Count;i++){
System.out.print("Processes["+i+"] :-> ");
processData[i][0] = i;
processData[i][1] = scan.nextInt();
}
}
public void DisplayValue(){
System.out.println("------------------------------------------------------------------------");
System.out.println("Process Number || Brust Time || WatingTime || Turn Around Time");
System.out.println("------------------------------------------------------------------------");
for(int i=0;i<Count;i++){
System.out.println("Processes["+processData[i][0]+"]\t||\t"+processData[i][1]+"\t||\t"+processData[i][2]+"\t||\t"+processData[i][3]);
}
System.out.println("-------------------------------------------------------------------------");
}
public static void main(String a[]){
FCFS fs = new FCFS();
fs.setValue();
System.out.println("\n\n***********First Come First Server :(FCFS) :***********\n");
System.out.println("\n\nBefore Scheduling\n");
fs.DisplayValue();
fs.scheduleFCFS();
System.out.println("\n\nAfter Scheduling\n");fs.DisplayValue();
}
}
