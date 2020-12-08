import java.util.Scanner;

public class s_des 
{
	String pt;
	String key;
	
	String ip_pt;
	
	int[] ip;
	int[] ip_inv;
	
	int[] exp;
	
	int[] p10;
	int[] p8;
	int[] p4;
	
	int[][] SBox0;
	int[][] SBox1;
	
	public s_des()
	{	
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the plain text(8-bit) :- ");
		pt = sc.nextLine();
		
		System.out.print("\nEnter the Key(10-bit) :- ");
		key = sc.nextLine();

		ip = new int[]{1,5,2,0,3,7,4,6};	//Initial Permutation
		ip_inv = new int[]{3,0,2,4,6,1,7,5};	//Final Permutation

		exp = new int[]{3,0,1,2,1,2,3,0};	//Expansion Permutation
		
		p10 = new int[]{2,4,1,6,3,9,0,8,7,5};	//P10 Permutation
		p8 = new int[]{5,2,6,3,7,4,9,8};	//P8 Permutation
		p4 = new int[]{1,3,2,0};	//P4 Permutation
		
		SBox0 = new int[][]{ { 1,0,3,2 }, { 3,2,1,0 } ,{ 0,2,1,3 }, { 3,1,3,2 }};
		SBox1 = new int[][]{ { 0,1,2,3 }, { 2,0,1,3 } ,{ 3,0,1,0 }, { 2,1,0,3 }};


		ip_pt = "";

		for(int  i=0;i<ip.length;i++)
			ip_pt += pt.charAt(ip[i]);
		
		System.out.println("IP(Plain_Text) :- "+ip_pt);
	}
	
	
	public String leftrotate(String s, int k)
	{
	    k = k%s.length();
	    return s.substring(k) + s.substring(0, k);
	} 
	
	public String key_generate(int p1)
	{	
		String p = "";
			
		for(int i=0;i<p10.length;i++)
			p += key.charAt(p10[i]);
		
		String l = p.substring(0,5);
		String r = p.substring(5,10);
		
		if(p1 == 1)
		{
			l = leftrotate(l, 1);
			r = leftrotate(r, 1);
		}
		
		else
		{
			l = leftrotate(l, 3);
			r = leftrotate(r, 3);
		}
		
		p = "";
		p += l;
		p += r;
		
		String q = "";
		
		for(int i=0;i<p8.length;i++)
			q += p.charAt(p8[i]);
	
		return q;
	}
	
	public String find_sbox(String lp,int i)
	{
		String temp = "",a = "",b = "";
		
		a += lp.charAt(0);
		a += lp.charAt(3);
		
		b += lp.charAt(1);
		b += lp.charAt(2);
		
		int a1 = Integer.parseInt(a,2);
		int b1 = Integer.parseInt(b,2);
		
		int a2;
		
		if(i == 1)
			a2 = SBox0[a1][b1];
		
		else
			a2 = SBox1[a1][b1];
		
		temp = Integer.toBinaryString(a2);
		
		if(a2 == 0)
			temp += "0";
		
		return temp;
	}
	
	public String Fun(String k,String p) 
	{
		String sub_ip = "",exp_sub = "",temp = "";
		
		sub_ip = p.substring(4);
		
		for(int i=0;i<exp.length;i++)
			exp_sub += sub_ip.charAt(exp[i]);

		String result = xor_conv(k,exp_sub);
		
		String lp = result.substring(0, 4);
		String rp = result.substring(4);

		String w1 = find_sbox(lp,1);
		String w2 = find_sbox(rp,2);
		
		w1 += w2;
		
		for(int i=0;i<p4.length;i++)
			temp += w1.charAt(p4[i]);
	
		return temp;
	}
	
	public String xor_conv(String f1, String lp1) 
	{
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < f1.length(); i++)
		    sb.append((f1.charAt(i) ^ lp1.charAt(i)));

		String result = sb.toString();
		
		return result;
	}
	
	public String fin_per(String temp)
	{
		String ans = "";
		
		for(int i=0;i<ip_inv.length;i++)
			ans += temp.charAt(ip_inv[i]);
		
		return ans;
	}
	
	public String encrypt(String k1, String k2) 
	{
		System.out.println("\nEncryption...............................");
		
		String f1 = Fun(k1,ip_pt);
		System.out.println("\nOUTPUT for 1st key "+f1);
		
		String lp1 = ip_pt.substring(0, 4);
		String rp1 = ip_pt.substring(4);
		
		String result = xor_conv(f1,lp1);
		rp1 += result;
		
		String f2 = Fun(k2,rp1);
		System.out.println("OUTPUT for 2nd key "+f2);
		
		lp1 = rp1.substring(0, 4);
		rp1 = rp1.substring(4);

		result = xor_conv(f2,lp1);
		result += rp1; 
		
		String encrpyted_text = fin_per(result);
		System.out.println("\nThe final encryption Result :- "+encrpyted_text);
		
		return encrpyted_text;
	}
	
	public void decrpyt(String ct,String k1, String k2) 
	{
		System.out.println("\nDecrpytion......................");
		
		String ct_pt = "";
		
		for(int  i=0;i<ip.length;i++)
			ct_pt += ct.charAt(ip[i]);
		
		System.out.println("IP(Cipher_Text) :- "+ct_pt);
		
		String f1 = Fun(k2,ct_pt);
		System.out.println("\nOUTPUT for 1st key "+f1);
		
		String lp1 = ct_pt.substring(0, 4);
		String rp1 = ct_pt.substring(4);
		
		String result = xor_conv(f1,lp1);
		rp1 += result;
		
		String f2 = Fun(k1,rp1);
		System.out.println("OUTPUT for 2nd key "+f2);
		
		lp1 = rp1.substring(0, 4);
		rp1 = rp1.substring(4);

		result = xor_conv(f2,lp1);
		result += rp1; 
		
		String decrpyted_text = fin_per(result);
		System.out.println("\nThe decrpytion process result :- "+decrpyted_text);
	}
	
	public static void main(String[] args)
	{
		s_des s1 = new s_des();
		String k1 = s1.key_generate(1);
		String k2 = s1.key_generate(2);
		
		System.out.println("\n1st Key :- "+k1+"\n2nd Key :- "+k2);
		
		String cipher_text = s1.encrypt(k1,k2);
		
		s1.decrpyt(cipher_text,k1,k2);
	}
}
