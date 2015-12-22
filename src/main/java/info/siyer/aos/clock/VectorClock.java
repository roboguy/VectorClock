package info.siyer.aos.clock;

import info.siyer.aos.utils.IEncapsulateClock;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/*
 * Implements Vector Clock
 */

public class VectorClock extends ConcurrentHashMap<String, Integer> {

	private static final long serialVersionUID = -7626729528051789514L;

	/*
	 * Default No args Constructor
	 */
	public VectorClock() {

	}

	/*
	 * increment(clock, nodeId): increment a vector clock at "nodeId"
	 */
	public void increment(String nodeID) {

		Integer old = this.get(nodeID);
		int value = (old == null) ? 1 : old + 1;
		this.put(nodeID, new Integer(value));
	}

	/*
	 * Prints given clock to Console
	 */
	public void printVectorClock() {
		Iterator<Entry<String, Integer>> iter = this.entrySet().iterator();

		System.out.print("[ ");
		Entry<String, Integer> first = iter.next();
		System.out.print(first.getKey() + " => " + first.getValue());

		while (iter.hasNext()) {
			Entry<String, Integer> entry = iter.next();
			System.out.print(", " + entry.getKey() + " => " + entry.getValue());
		}
		System.out.print(" ]");
	}

	/*
	 * merge(a, b): given two vector clocks, returns a new vector clock with all
	 * values greater than those of the merged clocks
	 */
	public VectorClock merge(VectorClock clock) {
		VectorClock result = new VectorClock();
		for (String entry : this.keySet()) {
			result.put(entry, this.get(entry));
		}
		for (String entry : clock.keySet()) {
			if (!result.containsKey(entry) || result.get(entry) < clock.get(entry)) {
				result.put(entry, clock.get(entry));
			}
		}
		return result;
	}

	/*
	 * compare(a, b) / ascSort(a, b): compare two vector clocks; returns -1 for
	 * a < b and 1 for a > b; 0 for concurrent and identical values. Can be used
	 * to sort an array of objects by their "clock" key via
	 * [].sort(VClock.ascSort)
	 */
	public int compare(VectorClock clock) {
		
		boolean isEqual = true;
		boolean isGreater = true;
		boolean isSmaller = true;
		
		for (String entry : this.keySet()) {
			if (clock.containsKey(entry)) {
				if (this.get(entry) < clock.get(entry)) {
					isEqual = false;
					isGreater = false;
				}
				if (this.get(entry) > clock.get(entry)) {
					isEqual = false;
					isSmaller = false;
				}
			}
			// Else assume zero (default value is 0).
			else if (clock.get(entry) != 0) {
				isEqual = false;
				isSmaller = false;
			}
		} // For Each key in this Vector Clock ENDS

		// Go over all elements in Clock two.
		for (String entry : clock.keySet()) {
			// Only elements we have not found in One still need to be checked.
			if (!this.containsKey(entry) && (clock.get(entry) != 0)) {
				isEqual = false;
				isGreater = false;
			}
		}

		// Return based on determined information.
		if (isEqual) {
			return 0;
		} else if (isGreater && !isSmaller) {
			return 1;
		} else if (isSmaller && !isGreater) {
			return -1;
		} else {
			return 0;
		}
	}

	/*
	 * isConcurrent(a, b): if A and B are equal, or if they occurred
	 * concurrently.
	 */
	public Boolean isConcurrent(VectorClock clock) {
		
		
		boolean isEqual = true;
		boolean isGreater = true;
		boolean isSmaller = true;
		
		for (String entry : this.keySet()) {
			if (clock.containsKey(entry)) {
				if (this.get(entry) < clock.get(entry)) {
					isEqual = false;
					isGreater = false;
				}
				if (this.get(entry) > clock.get(entry)) {
					isEqual = false;
					isSmaller = false;
				}
			}
			// Else assume zero (default value is 0).
			else if (clock.get(entry) != 0) {
				isEqual = false;
				isSmaller = false;
			}
		} // For Each key in this Vector Clock ENDS

		// Go over all elements in Clock two.
		for (String entry : clock.keySet()) {
			// Only elements we have not found in One still need to be checked.
			if (!this.containsKey(entry) && (clock.get(entry) != 0)) {
				isEqual = false;
				isGreater = false;
			}
		}

		// Return based on determined information.
		if (isEqual) {
			return false;
		} else if (isGreater && !isSmaller) {
			return false;
		} else if (isSmaller && !isGreater) {
			return false;
		} else {
			return true;
		}
		
	}

	/*
	 * isIdentical(a, b): if every value in both vector clocks is equal.
	 */
	public Boolean isIdentical(VectorClock clock2) {
		// TODO: Implement isIdentical
		return false;
	}

	public static VectorClock deserializeClock(IEncapsulateClock encap) {

		Gson gson = new Gson();
		String clock = encap.getClock();
		HashMap<String, Integer> desc = gson.fromJson(clock,
				new TypeToken<HashMap<String, Integer>>() {
				}.getType());

		VectorClock descVC = new VectorClock();
		descVC.putAll(desc);

		return descVC;
	}

	public static String serializeClock(VectorClock clock) {
		Gson gson = new Gson();
		String json = gson.toJson(clock);
		return json;
	}
}