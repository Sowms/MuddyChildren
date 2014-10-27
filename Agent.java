
import java.util.ArrayList;
public class Agent {

	String name;
	ArrayList<String> possibleWorlds;
	ArrayList<String> myWorlds;
	ArrayList<String> remWorlds;
	int N;
	int myPos;
	int numWorlds;
	int round=1;
	public Agent(String name, int N) {
		this.name = name;
		this.N = N;
		numWorlds = (int) Math.pow(2, N);
		possibleWorlds = new ArrayList<String>();
		myWorlds = new ArrayList<String>();
		remWorlds = new ArrayList<String>();
		boolean[] flips = new boolean[N];
		for (int i=0 ; i<N; i++)
			flips[i] = true;
		for (int i=0; i<numWorlds; i++) {
			String world = "";
			for (int j=N-1; j>=0; j--) {
				int placeValue = (int) Math.pow(2, j);
				if (i%placeValue == 0) 
					flips[j] = !flips[j];
				if (flips[j]) 
					world = world + "1";
				else
					world = world + "0";
			}
			possibleWorlds.add(world);
		}
	}
	
	public void setTrueWorld(String world) {
		
		String posWorld = "", negWorld = "";
		for (int i=0; i<N; i++) {
			if (world.charAt(i) == '?') {
				negWorld = negWorld + "0";
				posWorld = posWorld + "1";
				myPos = i;
			}
			else {
				negWorld = negWorld + world.charAt(i);
				posWorld = posWorld + world.charAt(i);
			}
		}
		myWorlds.add(negWorld);
		myWorlds.add(posWorld);
	}
	
	public void removeWorld(String world) {
		possibleWorlds.remove(world);
		if (myWorlds.contains(world))
			myWorlds.remove(world);
	}

	public int getAns() {
		if (myWorlds.size() == 1) 
			return (int) (myWorlds.get(0).charAt(myPos) - 48);
		return -1;
	}

	public void notifyAns(int pos, int muddy, int times) {
		
		//System.out.println(name+"-|-"+myWorlds+"|"+pos+"|"+possibleWorlds+"|"+times+round+remWorlds);
		if (myWorlds.size()==1) {
			possibleWorlds.removeAll(possibleWorlds);
			possibleWorlds.addAll(myWorlds);
			return;
		}
		if (muddy == -1) {
			int size = possibleWorlds.size();
			if (round != times) {
				round = times;
				size = remWorlds.size();
				for (int i=0; i<size; i++) 
					removeWorld(remWorlds.get(i));
				remWorlds.removeAll(remWorlds);
			}
			size = possibleWorlds.size();
			for (int i=0; i<size; i++) {
				String curWorld = possibleWorlds.get(i);
				char state = curWorld.charAt(pos);
				if (state=='0')
					state = '1';
				else
					state = '0';
				String flippedWorld = curWorld.substring(0,pos)+state+curWorld.substring(pos+1);
				if(!possibleWorlds.contains(flippedWorld)) {
				//	System.out.println(curWorld+"|"+flippedWorld+"|"+name+pos);
					remWorlds.add(curWorld);
				}
			}
			//System.out.println(name+"-|-"+myWorlds+"|"+pos+"|"+possibleWorlds+"|"+times+round+remWorlds);
		}
		else {
			String trueWorld = "";
			for (int i=0; i<myWorlds.size(); i++) { 
				String remWorld = myWorlds.get(i);
				char state = remWorld.charAt(pos);
				if (state=='0')
					state = '1';
				else
					state = '0';
				String flippedWorld = remWorld.substring(0,pos)+state+remWorld.substring(pos+1);
				//System.out.println(name+"|"+remWorld+"|"+flippedWorld+"|"+pos);
				if (possibleWorlds.contains(flippedWorld))
					removeWorld(flippedWorld);
				else {
					trueWorld = remWorld;
					break;
				}
			}
			//System.out.println(name+"|"+myWorlds);
			if (!trueWorld.equals("")) {
				possibleWorlds.removeAll(possibleWorlds);
				myWorlds.removeAll(myWorlds);
				myWorlds.add(trueWorld);
				possibleWorlds.add(trueWorld);
			}
			//System.out.println(name+"|"+myWorlds);
		}
	}
}
