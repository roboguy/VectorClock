package info.siyer.aos;

import static org.junit.Assert.assertEquals;
import info.siyer.aos.clock.VectorClock;
import info.siyer.aos.utils.Encapsulator;
import info.siyer.aos.utils.IEncapsulateClock;

import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



/**
 * Unit test for simple App.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VectorClockTest {
	
	VectorClock vc = new VectorClock();
	
	@Test
	public void aTestPrint(){
		
		System.out.println(" **** Print Test ****");
		
		vc.put("0", new Integer(0));
		vc.put("1", new Integer(0));
		vc.put("2", new Integer(0));
		
		
		vc.printVectorClock();
		System.out.println();
	}
	
	@Test
	public void bTestIncrement(){
		System.out.println(" **** Increment Test ****");
		
		vc.increment("0");
		vc.increment("2");
		vc.increment("2");
		vc.increment("2");
		vc.increment("1");
		vc.printVectorClock();
		
		System.out.println();
	}
	
	@Test
	public void seralizeTest(){
		
		System.out.println(" **** Seralize Test ****");
		VectorClock vc2 = new VectorClock();

		vc2.put("0", new Integer(0));
		vc2.put("1", new Integer(0));
		vc2.put("2", new Integer(0));
		
		Gson gson = new Gson();
		String json = gson.toJson(vc2);
		System.out.println("to JSON: " + json);
		
		Encapsulator encap = new Encapsulator();
		encap.setClock(json);
		encap.setOtherAttrs("Other Attrs");
		encap.setVersionNumber(50);
		
		String encapStr = gson.toJson(encap);
		System.out.println("Encap String : " + encapStr);
		
		Encapsulator deserl = gson.fromJson(encapStr, Encapsulator.class);
		VectorClock descVC = deserializeClock(deserl);
		
		System.out.println();
		System.out.println();
		String json2 = gson.toJson(descVC);
		System.out.println(json2);
		System.out.println();
	}
	
	@Test
	public void concurrentTest(){
		System.out.println(" **** Concurrency Test ****");
		VectorClock vc0 = new VectorClock();
		vc0.put("0", 1);
		vc0.put("1", 0);
		vc0.put("2", 0);
		
		VectorClock vc1 = new VectorClock();
		vc1.put("0", 0);
		vc1.put("1", 1);
		vc1.put("2", 0);
		
		VectorClock vc2 = new VectorClock();
		vc2.put("0", 0);
		vc2.put("1", 0);
		vc2.put("2", 1);
		
		boolean isConcurrent = vc1.isConcurrent(vc0);
		assertEquals(isConcurrent, true);
		
		
		vc2.increment("2");
		
		vc1 = vc1.merge(vc2);
		vc1.increment("1");
		
		System.out.println("Printing VC1:");
		vc1.printVectorClock();
		System.out.println();
		
		assertEquals(vc1.isConcurrent(vc2), false);
		
		VectorClock temp = vc1;
		
		vc0.increment("0");
		
		vc1 = vc1.merge(vc0);
		vc1.increment("1");
		
		assertEquals(vc1.isConcurrent(vc0), false);
		
		System.out.println("Printing Temp Clk: ");
		temp.printVectorClock();
		System.out.println();
		
		System.out.println("Printing VC1:");
		vc1.printVectorClock();
		System.out.println();
		
		System.out.println(vc1.compare(temp));
		System.out.println(vc1.serializeClock(vc1));
	}

	private VectorClock deserializeClock(IEncapsulateClock encap) {
		
		Gson gson = new Gson();
		String clock = encap.getClock();
		HashMap<String, Integer> desc = gson.fromJson(clock, new TypeToken<HashMap<String, Integer>>(){}.getType());
		
		VectorClock descVC = new VectorClock();
		descVC.putAll(desc);
		
		return descVC;
	}
	
	public String serializeClock(VectorClock clock){
		Gson gson = new Gson();
		String json = gson.toJson(clock);
		return json;
	}
	
	
	
}
