import java.util.Scanner;


public class MuddyChildren {
	
	public static int N;
	public static void main (String[] args) {
		
		/*Agent A = new Agent("A",3);
		for (int i=0; i<8; i++) {
			System.out.println(A.possibleWorlds[i]);
		}
		A.setTrueWorld("?10");
		for (int i=0; i<2; i++) {
			System.out.println(A.myWorlds[i]);
		}*/
		System.out.print("Enter the number of children: ");
		Scanner input = new Scanner(System.in);
		//get number of children
		N = input.nextInt();
		//creating agents
		Agent[] children = new Agent[N];
		for (int i=0; i<N; i++) {
			String agentName = "";
			char name = (char) (i + 65); 
			agentName = agentName + name;
			children[i] = new Agent(agentName,N);
		}
		//get true world
		System.out.print("Enter the true world: ");
		String trueWorld = input.next();
		for (int i=0; i<N; i++) {
			String iWorld = trueWorld.substring(0,i)+"?"+trueWorld.substring(i+1);
			children[i].setTrueWorld(iWorld);
		}
		System.out.println("Possible Worlds");
		for (int i=0; i<N; i++)
			System.out.println(children[i].possibleWorlds);
		//announce
		System.out.println("Public Announcement..");
		String zeroWorld = "";
		for (int i=0; i<N; i++)
			zeroWorld = zeroWorld + "0";
		for (int i=0; i<N; i++) {
			children[i].removeWorld(zeroWorld);
			System.out.println(children[i].possibleWorlds);
		}
		//Rounds begin
		boolean[] ans = new boolean[N];
		int times = 1;
		input.close();
		while (true) {
			if (times%2 == 0) {
				int round = times/2;
				System.out.println("Round "+round);
			}
			
			int i;
			for (i=0; i<N ; i++)
				if (!ans[i])
					break;
			if (i==N)
				break;
			int[] muddy = new int[N];
			for (i=0; i<N; i++)
				System.out.println(children[i].name+":"+children[i].possibleWorlds);
			for (i=0; i<N; i++) { 
				muddy[i] = children[i].getAns();
				if (muddy[i] != -1)
					ans[i] = true;
			}
			for (i=0; i<N; i++) {
				for (int j=0; j<N; j++)
				//	if (j!=i)
						children[i].notifyAns(j,muddy[j],times);
			}
			//if (times%2==0) 
				for (i=0; i<N; i++) 
					printAns(children[i].name, muddy[i]);
			times++;
		}
	}
	private static void printAns(String name, int muddy) {
		if (muddy == -1)
			System.out.println(name+": "+"No answer");
		else if (muddy==0)
			System.out.println(name+": No");
		else
			System.out.println(name+": Yes");
	}

}
