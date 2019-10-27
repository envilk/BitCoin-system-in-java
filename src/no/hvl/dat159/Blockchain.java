package no.hvl.dat159;

import java.util.ArrayList;
import java.util.List;

/**
 * A blockchain
 */
public class Blockchain {
	
	/**
	 * This blockchain's constant block reward (the miner's reward for mining
	 * a block).
	 */
	public static final long BLOCK_REWARD = 1_0;
	
	/**
	 * The minimum number of leading zeros in the binary encoding of a block
	 * hash to make a valid mined block => The time it takes to mine a block 
	 * is multiplied by 2 for each additional leading zero that is required.
	 * Recommended value: 15-25 
	 */
	public static final int MINING_DIFFICULTY = 15; 	

	/**
	 * A regular expression for the mining target:
	 * In the 256 char binary representation of the block hash, the
	 * MINING_DIFFICULTY first chars must be "0", and the rest must be "0" or "1".
	 * It can be compared with matches(MINING_TARGET) to see if it is a valid 
	 * mined block.
	 */
	public static final String MINING_TARGET 
			= "^0{" + MINING_DIFFICULTY + "}(0|1){" + (256-MINING_DIFFICULTY) + "}$";
	
	/* --------------------------------------------------------------------- */

	/*
	 * The list of blocks in the blockchain.
	 */
	private List<Block> blocks = new ArrayList<>();

	/* --------------------------------------------------------------------- */

	/**
	 * Set the genesis block of the blockchain.
	 * This is only allowed if the blockchain is empty.
	 */
	public void setGenesisBlock(Block genesisBlock) {
		if(blocks.isEmpty()) 
			blocks.add(genesisBlock);
	}
	
	public void appendBlock(Block newBlock) {
		blocks.add(newBlock);
	}

	public int getHeight() {
		return blocks.size();
	}
	
	public String getLastBlockHash() {
		return blocks.get(blocks.size()-1).getBlockHashAsHexString();
	}

	public List<Block> getBlocks() {
		return blocks;
	}
	
	public void printOverview() {
		System.out.println("      Mining difficulty: " + MINING_DIFFICULTY);
		System.out.println("      Block reward     : " + BLOCK_REWARD);
		System.out.println("      Number of blocks : " + getHeight());
		System.out.println("      Last blockhash   : " + getLastBlockHash());
	}
}
