package no.hvl.dat159;

/**
 * 
 */
public class Input {
	
	private String prevTxId;
	private int prevOutputIndex; // 0..x within transaction
	
	public String getPrevTxId() {
		return prevTxId;
	}

	public void setPrevTxId(String prevTxId) {
		this.prevTxId = prevTxId;
	}

	public int getPrevOutputIndex() {
		return prevOutputIndex;
	}

	public void setPrevOutputIndex(int prevOutputIndex) {
		this.prevOutputIndex = prevOutputIndex;
	}

	public Input(String prevTxId, int prevOutputIndex) {
		this.prevTxId = prevTxId;
		this.prevOutputIndex = prevOutputIndex;
	}

	@Override
	public String toString() {
		return "[prevTxId=" + prevTxId.substring(0, 25) 
				+ "..., prevOutputIndex=" + prevOutputIndex + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + prevOutputIndex;
		result = prime * result + ((prevTxId == null) ? 0 : prevTxId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Input other = (Input) obj;
		if (prevOutputIndex != other.prevOutputIndex)
			return false;
		if (prevTxId == null) {
			if (other.prevTxId != null)
				return false;
		} else if (!prevTxId.equals(other.prevTxId))
			return false;
		return true;
	}
}
