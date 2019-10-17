package no.hvl.dat159;

/**
 * 
 */
public class Output {
	
	private long value;
	private String address;
	
	public Output(long value, String address) {
		this.value = value;
		this.address = address;
	}

	public long getValue() {
		return value;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "[value=" + value + ", address=" + address + "]";
	}
}
