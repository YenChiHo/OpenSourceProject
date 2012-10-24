class Polynomial {
	protected static int n;
    protected static double[] a;
    
    public void polynomial(){};
    public void polynomial(int n) {
          Polynomial.n=n;
          a=new double [n+1];
    };
    public void polynomial(int n,double[] a) {
    	Polynomial.n=n;
    	Polynomial.a=new double [n+1];
        int i;
        for(i=0;i<=n;i++) Polynomial.a[i]=a[i];
    };
    public static double f(double x) {
        double sum;
        int i;
        sum=a[n];
        for(i=n;i>0;i--) sum=sum*x+a[i-1];
        return sum;
    };
    public static double df(double x) {
        double sum1,sum2;
        int i;
        sum1=a[n];
        sum2=0.0;
        for(i=n;i>0;i--)
          {
              sum2=sum2*x+sum1;
              sum1=sum1*x+a[i-1];
          }
        return sum2;
    };
}