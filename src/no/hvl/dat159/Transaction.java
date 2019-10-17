package no.hvl.dat159;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

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
		//TODO
	}

	/**
	 * 
	 */
	public boolean isValid(UtxoMap utxoMap) {
	    //TODO
	    //None of the data must be null 
        //Inputs or outputs cannot be empty
	    //No outputs can be zero or negative
	    //All inputs must exist in the UTXO-set
	    //All inputs must belong to the sender of this transaction
        //No inputs can be zero or negative
        //The list of inputs must not contain duplicates
        //The total input amount must be equal to (or less than, if we 
        //allow fees) the total output amount
        //The signature must belong to the sender and be valid
        //The transaction hash must be correct
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
