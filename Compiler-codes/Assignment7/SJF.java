import java.util.Scanner;
public class SJF {
int Count=0;
int[][] processData;
Scanner scan = new Scanner(System.in);
public static void main(String a[]){
System.out.println("\n\n***********Shortest Job First : (SJF) :***********\n");
SJF sjf = new SJF();
sjf.setValue();
System.out.println("\n\nBefore Scheduling\n");
sjf.DisplayValue();
sjf.scheduleSJF();
System.out.println("\n\nAfter Scheduling\n");
sjf.DisplayValue();
}
public void scheduleSJF(){
scheduleSorting();
scheduleCalculation();
}
public void scheduleSorting(){
for(int i=0;i<Count;i++){
int ele=i;
int eleData=processData[i][1];
for(int j=i+1;j<Count;j++){
if(eleData > processData[j][1]){
eleData= processData[j][1];ele=processData[j][0];
}
}
swapProcess(i,ele);
}
}
public void swapProcess(int p1,int p2){
int[] temp = processData[p1];
processData[p1] = processData[p2];
processData[p2] = temp;
}
public void scheduleCalculation(){
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
System.out.println("Processes["+i+"] :-> ");
processData[i][0] = i;
System.out.print("\t\tBrust Time["+i+"] :-> ");processData[i][1] = scan.nextInt();
}
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
}
