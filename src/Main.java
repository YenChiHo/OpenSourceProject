import java.io.*;



public class Main{
	
    public static void main(String [] args) throws IOException {
	    int n,i ;
	    System.out.println("This is the calculator to solve the root of the polynomial function.");
	    System.out.println("Please input n=? (the highest degree of the polynomial)");
	    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
	    String str1 = br1.readLine();
	    n = Integer.parseInt(str1);
	    System.out.println("Please input cofficient=? (from lower degree to higher degree)");
	    double[] a;
	    a = new double[n+1];
	    for(i=0;i<=n;i++)
	       {
	    	  System.out.println("a"+i+"=");
	          BufferedReader br2=new BufferedReader(new InputStreamReader(System.in));
	          String str2 = br2.readLine();
	          a[i] = Double.parseDouble(str2);
	       }
	    Polynomial pn = new Polynomial();
	    pn.n=n;
	    pn.a=a;
	    double x=0.0,dx=0.0,xcrit=1.0*Math.E-8,xini=0.0;
	    int ncount=0,nmax=30;
	    x=xini;
	    do { dx=Polynomial.f(x) / Polynomial.df(x);
	         x-=dx;
	         ncount++;
	         if(ncount>nmax) break;
	       } while (Math.abs(dx)>xcrit);
	    System.out.println("root="+x);
	}
}