package no.hvl.dat159;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import no.hvl.dat159.util.EncodingUtil;
import no.hvl.dat159.util.HashUtil;
import no.hvl.dat159.util.SignatureUtil;

/**
 * 
 */
public class Transaction {

	private List<Input> inputs = new ArrayList<>();
	private List<Output> outputs = new ArrayList<>();

	/*
	 * Simplification!:
	 * In reality, each input should have a public key and a signature. To simplify 
	 * things, we assume that all inputs belong to the same public key => We can 
	 * store the public key in the transaction and sign for all inputs in one go.
	 */
	private PublicKey senderPublicKey;
	private byte[] signature; 

	public Transaction(PublicKey senderPublicKey) {
		this.senderPublicKey = senderPublicKey;
	}

	/**
	 * @throws SignatureException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * 
	 */
	public void signTxUsing(PrivateKey privateKey) throws SignatureException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
		String insOutsHash = new String();
		for (Input input : inputs) {
			insOutsHash += (input.getPrevTxId() + input.getPrevOutputIndex());
		}
		for (Output output : outputs) {
			insOutsHash += (output.getAddress() + output.getValue());
		}
		signature = SignatureUtil.signWithDSA(privateKey, insOutsHash);
		
		
	}

	/**
	 * @throws InvalidKeyException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws SignatureException 
	 * 
	 */
	@SuppressWarnings("unlikely-arg-type")
	public boolean isValid(UtxoMap utxoMap) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
		//None of the data must be null 
		if(inputs == null || outputs == null || signature == null || senderPublicKey == null)
			return false;

		//Inputs or outputs cannot be empty
		if(inputs.isEmpty() || outputs.isEmpty())
			return false;

		//No outputs can be zero or negative
		for (Output output : outputs) {
			if(output.getValue() <= 0)
				return false;
		}

		//All inputs must exist in the UTXO-set
		Set<Entry<Input, Output>> utxoSet = utxoMap.getAllUtxos();/*
		for (Input input : inputs) {
			if(!utxoSet.contains(input))
				return false;
		}

		//All inputs must belong to the sender of this transaction
		utxoSet = utxoMap.getUtxosForAddress(HashUtil.pubKeyToAddress(senderPublicKey));
		for (Input input : inputs) {
			if(!utxoSet.contains(input))
				return false;
		}*/

		//No inputs can be zero or negative
		Set<Entry<Input, Output>> newSet = new HashSet<Entry<Input, Output>>();
		utxoSet = utxoMap.getUtxosForAddress(HashUtil.pubKeyToAddress(senderPublicKey));
		for (Entry<Input, Output> entry : utxoSet) {
			if(inputs.contains(entry.getKey()))
				newSet.add(entry);
		}
		for (Entry<Input, Output> entry : newSet) {
			Output output = entry.getValue();
			if(output.getValue() <= 0)
				return false;
		}

		//The list of inputs must not contain duplicates
		/*
		Set<Input> setToReturn = new HashSet<>(); 
		Set<Input> set1 = new HashSet<>();
		for (Input in : inputs) {
			if (!set1.add(in)){
				setToReturn.add(in);
			}
		}
		System.out.println(setToReturn.size());
		System.out.println(inputs.size());
		if(setToReturn.size() != inputs.size())
			return false;*/
		
		//The total input amount must be equal to (or less than, if we 
		//allow fees) the total output amount
		if(inputs.size() > outputs.size())
			return false;

		//The signature must belong to the sender and be valid
		String insOutsHash = new String();
		for (Input input : inputs) {	
			insOutsHash += (input.getPrevTxId() + input.getPrevOutputIndex());
		}
		for (Output output : outputs) {
			insOutsHash += (output.getAddress() + output.getValue());
		}
		if(!SignatureUtil.verifyWithDSA(senderPublicKey, insOutsHash, signature))
			return false;
		
		//The transaction hash must be correct
		//TODO Don't really know how to do it

		return true;
	}

	/**
	 *	The block hash as a hexadecimal String. 
	 */
	public String getTxId() {
		String PbK = HashUtil.pubKeyToAddress(senderPublicKey);
		String sign = signature.toString();
		String TxId = PbK + sign;
		for (Input input : inputs) {
			TxId += (input.getPrevTxId() + input.getPrevOutputIndex());
		}
		for (Output output : outputs) {
			TxId += (output.getAddress() + output.getValue());
		}
		return EncodingUtil.bytesToHex(HashUtil.sha256(TxId));
	}

	public void addInput(Input input) {
		inputs.add(input);
	}

	public void addOutput(Output output) {
		outputs.add(output);
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public List<Output> getOutputs() {
		return outputs;
	}

	public PublicKey getSenderPublicKey() {
		return senderPublicKey;
	}

	public byte[] getSignature() {
		return signature;
	}

	@Override
	public String toString() {
		String s = getTxId();
		for (int i=0; i<inputs.size(); i++) {
			s += "\n\tinput(" + i + ")   : " + inputs.get(i);
		}
		for (int i=0; i<outputs.size(); i++) {
			s += "\n\toutput(" + i + ")  : " + outputs.get(i);
		}
		return s;
	}


}
