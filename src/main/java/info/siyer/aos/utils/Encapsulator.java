package info.siyer.aos.utils;

/*
 * Example VectorClock Encapsulator that implements the IEncapsulateClock interface
 */

public class Encapsulator implements IEncapsulateClock {

	String clock;
	Integer versionNumber;
	String otherAttrs;

	public Encapsulator() {
		
	}

	@Override
	public String getClock() {
		return clock;
	}
	
	@Override
	public void setClock(String clock) {
		this.clock = clock;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getOtherAttrs() {
		return otherAttrs;
	}

	public void setOtherAttrs(String otherAttrs) {
		this.otherAttrs = otherAttrs;
	}

}
