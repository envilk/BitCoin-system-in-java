package no.hvl.dat159;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A Map of UTXOs.
 * This is an additional datastructure to the list of blocks itself.
 * The purpose of the UtxoMap is to keep track of unspent outputs, so
 * you don't have to search the entire blockchain ev every time to
 * find suitable outputs to spend or to check whether referred outputs
 * are valid to use or not.
 * It is organized as a Map to simplify lookup.
 */
public class UtxoMap {
	
	/*
	 * 
	 */
	private Map<Input, Output> utxos = new HashMap<>();
	
	/**
	 * Collects all UTXOs and returns these as a Set of K,V-pairs.
	 */
	public Set<Entry<Input, Output>> getAllUtxos() {
		return utxos.entrySet();
	}
	
	/**
	 * Collects UTXOs matching to a specific address and returns these
	 * as a Set of K,V-pairs.
	 */
	public Set<Entry<Input, Output>> getUtxosForAddress(String address) {
		//TODO
		return null;
	}
	
	public void addOutput(Input input, Output output) {
		utxos.put(input, output);
	}
	
	public void removeOutput(Input input) {
		utxos.remove(input);
	}
}
