
import java.util.Scanner;
import java.util.Random;

public class Cockroach {
	
	public static Scanner input=new Scanner(System.in);
	public static Random randomGenerator=new Random();
	//Given square in the middle of a tile floor
	private int floorX;//the number of square unit of x
	private int floorY;//the number of square unit of y
	private int floorXY;//area of the square by unit x y
	private int startX;//cockroach start point x
	private int startY;//cockroach start point y
	private int currentX;//currently cockroach at X
	private int currentY;//currently cockroach at y
	
	//limitXY=floorX*floorY means cockroach walkthrough all the unit of the floor
	//initial to 1 because cockroach start already at start point (x,y)
	private int limitXY=1;
	
	private int totalWalkStep=0;//total walk step of cockroach do 
	private int[][]  walkXY;//initial all to 0,store the number of cockroach walk every place
	
	//This ensures the program will terminate
	//if limitXY exceed this ITERATION_LIMIT, program will terminate
	public static final int ITERATION_LIMIT=50000;
	
	//initialization
	public Cockroach()
	{
		System.out.print("Floor size x:");
		floorX=input.nextInt();
		floorX=(floorX>2 && floorX<41)?floorX:3;
		System.out.print("Floor size y:");
		floorY=input.nextInt();
		floorY=(floorY>1 && floorY<21)?floorY:2;
		System.out.print("Starting point x:");
		startX=input.nextInt();
		startX=(startX<floorX)?startX:0;
		System.out.print("Starting point y:");
		startY=input.nextInt();
		startY=(startY<floorY)?startY:0;
		
		floorXY=floorX*floorY;//calculate area
		
		currentX=startX;//start point X assign to current point X
		currentY=startY;//start point Y assign to current point Y
		
		walkXY=new int[floorY][floorX];//create the array(area) size
		walkXY[startY][startX]=1;//cockroach reachstarting point
	}//end constructor Cockroach()
	
	//the output message before cockroach walking
	public String outputBeforeWalking()
	{
		return String.format("array size %dx%d=%d, starting point(%d,%d)\n\nStart:\n%s",floorX,floorY,floorXY,startX,startY,currentStatus());
	}//end String outputBeforeWalking()
	
	//output cockroach walking status 
	//0 means not walkthrough yet
	//output number means walkthrough times
	public String currentStatus()
	{
		int i=0,j=0;//index use
		String output="";
		for(j=0;j<floorY;j++)
		{
			for(i=0;i<floorX;i++)
			{
				output+=walkXY[j][i]+"\t";
			}
			output+="\n";
		}
		
		return output;
	}//end String currentStatus()
	
	//Simulate cockroach Randomly(Druken) Walking
	public void cockroachWalking()
	{
		//Check limitXY equal to floor size means finish walk
		while(limitXY!=floorXY)
		{
		//imove jmove
		ijMove();
		//update limitXY
		if(walkXY[currentY][currentX]==0)
		{
			limitXY++;
		}
		//update walkXY[][]
		walkXY[currentY][currentX]++;
		//update totalWalkStep
		totalWalkStep++;
		
		//check totalWalkStep, if exceed ITERATION_limit, then exit
		if(totalWalkStep==ITERATION_LIMIT)
			{
			System.out.println("Maximum number(50,000) of square that the buy enter during this experiment,TERMINATE!");
			return;
			}
		}//end while (limitXY!-floorXY)
	}//end method cockroachWalking
	
	//generate random walking imove 
	private void ijMove()
	{
		//generate random number between 0~7
		int randomInt=randomGenerator.nextInt(8);
		//declare imove Array of integers
		int[] imoveArray={-1,0,1,1,1,0,-1,-1};
		int[] jmoveArray={1,1,1,0,-1,-1,-1,0};
		
		//Check does cockroach reach wall(limit of flooor)
		//if exceed outside,retry ijMove()
		if(currentX==0 && imoveArray[randomInt]==-1){ijMove();return;}
		else if(currentX==(floorX-1) && imoveArray[randomInt]==1){ijMove();return;}
		else if(currentY==0 && jmoveArray[randomInt]==-1){ijMove();return;}
		else if(currentY==(floorY-1) && jmoveArray[randomInt]==1){ijMove();return;}
		
		//if not exceed,cockroach ready to move
		currentX+=imoveArray[randomInt];
		currentY+=jmoveArray[randomInt];
		
	}//end method ijMove()

	//the output message after cockroach walking finish
	public String outputAfterWalking()
	{
		return String.format("\nTotal Number of legal move:%d\n\nEnd:\n%s", totalWalkStep,currentStatus());
		
	}//end String output AfterWalking()
	
	public static void main(String argc[])
	{
		//Construct cockroach
		Cockroach cockroach=new Cockroach();
		System.out.print(cockroach.outputBeforeWalking());
		
		//drunken cockroach start walking
		cockroach.cockroachWalking();
		
		//output walking result
		System.out.print(cockroach.outputAfterWalking());
	}//end method main
	
}//end Class Cockroach
