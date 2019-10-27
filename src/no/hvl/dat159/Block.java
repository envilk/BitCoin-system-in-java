package no.hvl.dat159;

import no.hvl.dat159.util.EncodingUtil;
import no.hvl.dat159.util.HashUtil;

/**
 * The basic building block in the blockchain.
 */
public class Block {
	
	/*
	 * The "block header" consists of the prevBlockHash, merkleRoot and nonce.
	 * The prevBlockHash is a 64 char long hex representation of the previous hash.
	 * The merkleRoot is a "fingerprint" representing all the transactions in the
	 * block. The input to calculating the blockHash is this "block header".
	 */
	private String prevBlockHash;
	private String merkleRoot;
	private long nonce;

	/*
	 * The "block body" contains a coinbaseTx and one ordinary transaction.
	 */
	private CoinbaseTx coinbaseTx;
	private Transaction transaction;

	/* --------------------------------------------------------------------- */

	/**
	 * Constructs a non-mined block. To be a valid block, the block must
	 * then be "mined" so that the blockHash matches the MINING_TARGET 
	 * binary pattern.
	 */
	public Block(String prevBlockHash, CoinbaseTx coinbaseTx, Transaction tx) {
		//Remember to calculate the Merkle root
		if(tx == null)
			this.merkleRoot = EncodingUtil.bytesToHex(HashUtil.sha256(coinbaseTx.getTxId()));
		else
			this.merkleRoot = EncodingUtil.bytesToHex(HashUtil.sha256(coinbaseTx.getTxId() + tx.getTxId()));
		this.coinbaseTx = coinbaseTx;
		this.transaction = tx;
		this.nonce = 0;
		this.prevBlockHash = prevBlockHash;
	}
	
	/**
	 * "Mines" the block, that is selecting a nonce so that the hash puzzle
	 * requirement is satisfied.
	 */
	public void mine() {
		nonce = 0;
		while (!getBlockHashAsBinaryString().matches(Blockchain.MINING_TARGET)) {
			nonce++;
		}
	}
	
	/**
	 * Calculates whether the block is valid or not. To be a valid block,
	 * ALL contents must be valid, and the hash of the block must satisfy
	 * the hash puzzle requirement.
	 */
	public boolean isValid() {
		return getBlockHashAsBinaryString().matches(Blockchain.MINING_TARGET) // The block must be mined
				&& this.coinbaseTx != null // The data in the block must be valid
				&& this.transaction != null;
	}
	
	/**
	 * Calculates whether the block is valid as the genesis block or not. 
	 * The genesis block does not contain an ordinary transactions, just a
	 * coinbase transaction. Other than that:
	 * ALL contents must be valid, and the hash of the block must satisfy
	 * the hash puzzle requirement.
	 */
	public boolean isValidAsGenesisBlock() {
		return getBlockHashAsBinaryString().matches(Blockchain.MINING_TARGET) // The block must be mined
				&& this.coinbaseTx != null; // The data in the block must be valid
	}
	
	/**
	 * Calculates and encodes the block hash as a String of "0"s and "1"s.
	 * Can be useful in the mining process to see if the hash puzzle is solved.
	 */
	public String getBlockHashAsBinaryString() {
		return EncodingUtil.bytesToBinary(HashUtil.sha256(getBlockHashAsHexString()));
	}

	/**
	 * Calculates and encodes the block hash as a hexadecimal String.
	 */
	public String getBlockHashAsHexString() {
		if(this.transaction == null) {
			return EncodingUtil.bytesToHex(HashUtil.sha256(this.merkleRoot + this.nonce
					+ this.coinbaseTx.getTxId()));
		}
		else {
		return EncodingUtil.bytesToHex(HashUtil.sha256(this.merkleRoot + this.nonce + this.prevBlockHash 
				+ this.coinbaseTx.getTxId() + this.transaction.getTxId()));
		}
	}
	
	public void printOverview() {
		System.out.println();
		System.out.println("Block overview for block  " + getBlockHashAsHexString());
		System.out.print("-----------------------------------------");
		System.out.println("-----------------------------");
		System.out.println("   Prev block hash : " + prevBlockHash);
		System.out.println("   Nonce           : " + nonce);
		System.out.println("   Coinbase tx     : " + coinbaseTx);
		System.out.println("   The other tx    : " + transaction);
	}
	
	/* --------------------------------------------------------------------- */
	
}
