package no.hvl.dat159;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import no.hvl.dat159.util.EncodingUtil;
import no.hvl.dat159.util.HashUtil;

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
	 * 
	 */
	public void signTxUsing(PrivateKey privateKey) {
		Signature sign = Signature.getInstance("SHA256WithRSA", "SunRsaSign");
		sign.initSign(privateKey);
		byte[] dataBytes = message.getBytes();//TODO Summarize all the inputs and outputs in a message 
		sign.update(dataBytes);
		signature = sign.sign();
	}

	/**
	 * 
	 */
	public boolean isValid(UtxoMap utxoMap) {
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
		Set<Entry<Input, Output>> umap = utxoMap.getAllUtxos();
		for (Input input : inputs) {
			if(!umap.contains(input))
				return false;
		}

		//All inputs must belong to the sender of this transaction
		for (Input input : inputs) {
			if(this.getSenderPublicKey()) //TODO see how to compare this with the inputs
				return false;
		}

		//No inputs can be zero or negative
		for (Input input : inputs) {//TODO SEE IF THIS IS REALLY CORRECT
			if(outputs.get(input.getPrevOutputIndex()).getValue() <= 0)
				return false;
		}

		//The list of inputs must not contain duplicates//TODO DO IT
		Set<Integer> set1 = new HashSet<>();
		for (Input input : inputs)
		{
			if (!set1.add(yourInt))
			{
				setToReturn.add(yourInt);
			}
		}
		
		//The total input amount must be equal to (or less than, if we 
		//allow fees) the total output amount
		if(inputs.size() > outputs.size())//TODO LESS OR GREATER?
			return false;
		
		//The signature must belong to the sender and be valid//TODO is it okay?
		Signature sign = Signature.getInstance("SHA256WithRSA", "SunRsaSign");
		sign.initVerify(senderPublicKey);	
		if(!sign.verify(signature))
			return false;
		
		//The transaction hash must be correct
		//TODO how do I do this
		
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
		return EncodingUtil.bytesToBinary(HashUtil.sha256(TxId));
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
