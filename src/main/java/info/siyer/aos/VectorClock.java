package info.siyer.aos;

import java.io.Serializable;

public class VectorClock implements Serializable {
	
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -7626729528051789514L;
	
	//TODO: Add DataStructure to hold vector clock.
	
	public VectorClock(){
		//TODO: Implement Constructor
	}

	/*
	 * increment(clock, nodeId): increment a vector clock at "nodeId"
	 */
	private void increment(VectorClock clock, Integer nodeID){
		//TODO: Implement increment
	}
	
	/*
	 * merge(a, b): given two vector clocks, returns a new vector clock with all values greater 
	 * than those of the merged clocks
	 */
	private void merge(VectorClock clock1, VectorClock clock2){
		//TODO: Implement merge
	}
	
	/*
	 * compare(a, b) / ascSort(a, b): compare two vector clocks; 
	 * returns -1 for a < b and 1 for a > b; 0 for concurrent and identical values. 
	 * Can be used to sort an array of objects by their "clock" key via [].sort(VClock.ascSort)
	 */
	private void compare(VectorClock clock1, VectorClock clock2){
		//TODO: implement compare
	}
	
	/*
	 * isConcurrent(a, b): if A and B are equal, or if they occurred concurrently.
	 */
	private Boolean isConcurrent(VectorClock clock1, VectorClock clock2){
		//TODO: Implement isConcurrent
		return true;
	}
	
	/*
	 * isIdentical(a, b): if every value in both vector clocks is equal.
	 */
	private Boolean isIdentical(VectorClock clock1, VectorClock clock2){
		//TODO: Implement isIdentical
		return false;
	}
}
