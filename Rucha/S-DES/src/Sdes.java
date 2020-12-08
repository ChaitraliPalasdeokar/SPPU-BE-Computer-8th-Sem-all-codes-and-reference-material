import java.util.Scanner;

public class Sdes {
	int k1[]=new int[8];
	int k2[]=new int[8];
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Plaintext");
		int P[]=new int[8];
		for(int i=0;i<8;i++)
			P[i]=sc.nextInt();
		System.out.println("Enter a 10 bit key");
		int[] key=new int[10];
		for(int i=0;i<10;i++)
			key[i]=sc.nextInt();
		Sdes obj=new Sdes();
		obj.generateKey(key);
		int[] cipher=obj.encryptText(P);
		System.out.print("Ciphertext: ");
		for(int i=0;i<cipher.length;i++) {
			System.out.print(cipher[i]+" ");
		}


	}

	private int[] fk(int[] ip,int[] k12) {
		int leftip[]=new int[4];
		int rightip[]=new int[4];

		for(int i=0;i<4;i++)
			leftip[i]=ip[i];
		for(int i=0;i<4;i++)
			rightip[i]=ip[i+4];
		int ep[]=new int[8];
		int input_arrep[]= {1,2,3,4};
		int output_arrep[]= {4,1,2,3,2,3,4,1};
		ep=permutate(rightip,input_arrep,output_arrep);

		int ep_xor_k1[]=new int[8];
		ep_xor_k1=xor(ep,k12);
		System.out.println();
		System.out.print("ep_Xor_K1: ");
		for(int i=0;i<8;i++) {
			System.out.print(ep_xor_k1[i]+" ");
		}
		int ls0[]=new int[4];
		int ls1[]=new int[4];

		for(int j=0;j<4;j++)
			ls0[j]=ep_xor_k1[j];
		System.out.print("LS0: ");
		for(int j=0;j<4;j++)
			System.out.print(ls0[j]+"  ");
		for(int j=0;j<4;j++)
			ls1[j]=ep_xor_k1[j+4];
		System.out.println();
		System.out.print("LS1: ");
		for(int j=0;j<4;j++)
			System.out.print(ls1[j]+"  ");
		int S0[][]= {{01,00,11,10},{11,10,01,00},{00,10,01,11},{11,01,11,10}};
		int S1[][]={{00,01,10,11},{10,00,01,11},{11,00,01,00},{10,01,00,11}};

		//for S0
		int rowbin[]=new int[2];
		int colbin[]=new int[2];
		
		rowbin[0]=ls0[0];
		rowbin[1]=ls0[3];
		
		
		colbin[0]=ls0[1];
		colbin[1]=ls0[2];
		int row=binaryToDecimal(rowbin);
		int col=binaryToDecimal(colbin);

		System.out.println("Row "+row);
		System.out.println("Col"+col);
		int s0=S0[row][col];
		System.out.println("s0 "+s0);
		
		//for s1
		rowbin[0]=ls1[0];
		rowbin[1]=ls1[3];
		
		
		colbin[0]=ls1[1];
		colbin[1]=ls1[2];
		int row1=binaryToDecimal(rowbin);
		int col1=binaryToDecimal(colbin);

		System.out.println("Row "+row1);
		System.out.println("Col"+col1);
		int s1=S1[row1][col1];
		System.out.println("s1 "+s1);
		
		//converting an integer to an int array
		int s01[]=new int[2];
		int s11[]=new int[2];
		
		s01=intToIntArray(s01, s0);
		s11=intToIntArray(s11, s1);
		
		int[] concat=new int[4];
		for(int i=0;i<2;i++)
			concat[i]=s01[i];
		for(int i=0;i<2;i++)
			concat[i+2]=s11[i];
		
		int p4[]=new int[4];
		int ip_arr[]= {1,2,3,4};
		int op_arr[]= {2,4,3,1};
		p4=permutate(concat,ip_arr,op_arr);
		System.out.print("P4:");
		for(int i=0;i<4;i++)
			System.out.print(p4[i]+" ");
		int lIp_xor_p4[]=new int[4];
		lIp_xor_p4=xor(leftip,p4);
		System.out.print("Left ip:");
		for(int i=0;i<4;i++)
			System.out.print(leftip[i]+" ");
		int final_cipher1[]=new int[8];
		for(int i=0;i<4;i++) {
			final_cipher1[i]=lIp_xor_p4[i];
		}
		for(int i=0;i<4;i++) {
			final_cipher1[i+4]=rightip[i];
		}
		System.out.print("Final cipher: ");
		for(int i=0;i<8;i++)
			System.out.print(final_cipher1[i]+" ");
		return final_cipher1;
		
	}
	private int[] encryptText(int[] p) {
		int ip[]=new int[8];
		int input_arr[]= {1,2,3,4,5,6,7,8};
		int output_arr[]= {2,6,3,1,4,8,5,7};
		ip=permutate(p,input_arr,output_arr);

		int cipher1[]=new int[8];
		cipher1=fk(ip,k1);
		int temp[]=new int[4];
		for(int i=0;i<4;i++) {
			temp[i]=cipher1[i];
		}
		for(int i=0;i<4;i++) {
			cipher1[i]=cipher1[i+4];
		}
		for(int i=0;i<4;i++)
			cipher1[i+4]=temp[i];
		System.out.print("Cipher 1: ");
		for(int i=0;i<8;i++)
			System.out.print(cipher1[i]+" ");
		
		int cipher2[]=new int[8];
		cipher2=fk(cipher1,k2);
		
		int ciphertext[]=new int[8];
		int ip_array[]= {1,2,3,4,5,6,7,8};
		int op_array[]= {4,1,3,5,7,2,8,6};
		ciphertext=permutate(cipher2,ip_array,op_array);
				
		return ciphertext;
		
	}
	private int[] intToIntArray(int arr[],int n) {
		for(int i=1;i>=0;i--) {
			arr[i]=n%10;
			n=n/10;
			
		}
		return arr;
	}
	private int binaryToDecimal(int[] bin) {
		int rbin[]=new int[bin.length];
		int j=bin.length-1;
		for(int i=0;i<bin.length;i++) {
			rbin[j]=bin[i];
			j--;
		}
//		System.out.print("rbin: ");
//		for(int i=0;i<bin.length;i++) {
//			System.out.print(rbin[i]+" ");
//		}
		int sum=0;
		int cnt0=0;

		for(int i=0;i<rbin.length;i++) {
			if(bin[i]==0) {
				cnt0++;
				//rbin[i]=1;
			}
			if(cnt0==rbin.length)
				return 0;
			else {
				sum+=rbin[i]*Math.pow(2, i);
			}
		}
		return sum;
	}

	private int[] xor(int[] ep, int[] k1) {
		int xor[]=new int[ep.length];
		if(ep.length==k1.length) {
			for(int i=0;i<ep.length;i++)
				xor[i]=ep[i]^k1[i];
		}
		else {
			System.out.println("Error: Both arrays must have same length");
		}
		return xor;
	}

	private void generateKey(int key[]) {

		int p10[]=new int[10];
		int input_arr[]= {1,2,3,4,5,6,7,8,9,10};
		int output_arr[]= {3,5,2,7,4,10,1,9,8,6};

		p10=permutate(key,input_arr,output_arr);
		System.out.print("P10: ");
		for(int i=0;i<10;i++)
			System.out.print(p10[i]+"  ");
		int lls1[]=new int[5];
		int rls1[]=new int[5];

		for(int i=0;i<5;i++) {
			lls1[i]=p10[i];
		}

		for(int i=0;i<5;i++) {
			rls1[i]=p10[i+5];
		}

		lls1=left_shift(lls1);
		rls1=left_shift(rls1);
		System.out.print("LLs1: ");
		for(int i=0;i<lls1.length;i++) {
			System.out.print(lls1[i]+" ");
		}
		System.out.println();
		System.out.print("RLS1: ");
		for(int i=0;i<rls1.length;i++) {
			System.out.print(rls1[i]+" ");
		}

		int concat[]=new int[10];
		int i=0;
		for(i=0;i<5;i++) {
			concat[i]=lls1[i];
		}
		for(i=0;i<5;i++) {
			concat[i+5]=rls1[i];
		}
		System.out.print("Concat: ");
		for(int i1=0;i1<10;i1++)
			System.out.print(concat[i1]+" ");

		int op_arrp8[]= {6,3,7,4,8,5,10,9};
		k1=permutate(concat,input_arr,op_arrp8);
		System.out.println("");
		System.out.print("k1: ");
		for(int j=0;j<8;j++) {
			System.out.print(k1[j]+" ");
		}
		// for k2
		int lls2[]=new int[5];
		int rls2[]=new int[5];
		lls1=left_shift(lls1);
		rls1=left_shift(rls1);


		lls2=left_shift(lls1);
		rls2=left_shift(rls1);
		for(i=0;i<5;i++) {
			concat[i]=lls2[i];
		}
		for(i=0;i<5;i++) {
			concat[i+5]=rls2[i];
		}

		k2=permutate(concat,input_arr,op_arrp8);
		System.out.println("K2 ");
		for(int j=0;j<8;j++) {
			System.out.print(k2[j]+" ");
		}



	}
	private int[] left_shift(int[] ls1) {
		//System.out.println("In left shift");
		int temp=ls1[0];
		//ls1[4]=ls1[0];
		for(int i=0;i<(ls1.length-1);i++) {
			//System.out.print("i "+i);
			ls1[i]=ls1[i+1];
		}
		ls1[4]=temp;

		//System.out.println(ls1);
		return ls1;
	}

	public int[] permutate(int arr[],int input_arr[],int output_arr[]) {
		//int dup_arr[]=new int[output_arr.length];
		int dup_arr1[]=new int[output_arr.length];	
		//dup_arr=arr;
		if(input_arr.length==output_arr.length) {
			int index=0;
			//print("i"," input_arr"," output_arr "," arr[input_arr[i]-1]"," arr[output_arr[i]-1]"," dup_arr[input_arr[i]-1]"," arr[output_arr[i]-1]")
			for(int i=0;i<output_arr.length;i++) {
				index=output_arr[i]-1;
				//print(i,"  ",input_arr[i]-1,"  ",output_arr[i]-1,"  ",arr[input_arr[i]-1]," ",arr[output_arr[i]-1],dup_arr[input_arr[i]-1],dup_arr[output_arr[i]-1])
				dup_arr1[i]=arr[index];
				//System.out.println(dup_arr1);
			}
		}
		else {
			for(int i=0;i<output_arr.length;i++) {
				dup_arr1[i]=arr[output_arr[i]-1];

			}
		}
		arr=dup_arr1;
		return arr;

	}
}
