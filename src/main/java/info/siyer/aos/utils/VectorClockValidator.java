package info.siyer.aos.utils;

import info.siyer.aos.clock.VectorClock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VectorClockValidator {
	
	private static VectorClock entryClock1 = null;
	private static VectorClock exitClock1 = null;
	private static String nodeID = "";
	private static String opeartion = "";
	static boolean violation = false;
	
	public static void main(String[] args) {
		
		if(args.length < 1){
			System.out.println("Enter clock filename.");
			System.exit(0);
		}
		String fileName = args[0];
		System.out.println(" ** RUNNING VALIDATOR **");
		validate(fileName);
		
	}

	private static void validate(String fileName) {
		System.out.println("Validating File " + fileName);
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(scanner.hasNextLine()){
			String line = scanner.nextLine();
			String[] split = line.split("::");
			
			nodeID = split[0];
			opeartion = split[3];
			
			Encapsulator entry1  = new Encapsulator();
			entry1.setClock(split[1]);
			
			Encapsulator exit1  = new Encapsulator();
			exit1.setClock(split[2]);
			
			entryClock1 = VectorClock.deserializeClock(entry1);
			exitClock1 = VectorClock.deserializeClock(exit1);
			Integer comparedValue = entryClock1.compare(exitClock1);
			System.out.println(comparedValue);
			
		} else {
			System.out.println("No Operations Performed, Hence No Violations Occoured.");
			System.exit(0);
		}
		
		
		while(scanner.hasNext()){
			String nextLine = scanner.nextLine();
			String[] splitLine = nextLine.split("::");
			
			String nextNodeID = splitLine[0];
			String nextOpeartion = splitLine[3];
			
			Encapsulator entry2  = new Encapsulator();
			entry2.setClock(splitLine[1]);
			
			Encapsulator exit2  = new Encapsulator();
			exit2.setClock(splitLine[2]);
			
			VectorClock entryClock2 = VectorClock.deserializeClock(entry2);
			VectorClock exitClock2 = VectorClock.deserializeClock(exit2);
			
			Integer comparedValue = entryClock2.compare(exitClock2);
			System.out.println(comparedValue);
			
			if(!opeartion.equals("READ") || !nextOpeartion.equals("READ")){
				violation = exitClock1.isConcurrent(entryClock2);
				if(violation){
					System.out.println("A Violation Occurred between " + nodeID + " & " + nextNodeID) ;
					System.out.println("Node: " + nodeID + " Performed: " + opeartion + " with exit clock: " );
					exitClock1.printVectorClock();
					
					System.out.println();
					System.out.println("Node: " + nextNodeID + " Performed: " + nextOpeartion + " with entry clock: " );
					entryClock2.printVectorClock();
					
					System.out.println("\nConcurrent Write Execution");
					
				}else {
					swap(entryClock2, exitClock2, opeartion, nodeID);
				}
			}
			
			
		}//While Loop ENDS
		if(!violation){
			System.out.println("NO VIOLATIONS OCCURRED");
		}
	}

	private static void swap(VectorClock entryClock2, VectorClock exitClock2,
			String opeartion2, String nodeID2) {
			entryClock1 = entryClock2;
			exitClock1 = exitClock2;
			opeartion = opeartion2;
			nodeID = nodeID2;
	}

}
