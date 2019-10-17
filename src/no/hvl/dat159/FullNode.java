package no.hvl.dat159;

/**
 * Contains both the full Blockchain and the full UtxoMap.
 * Also contains the wallet for the mining rewards + fees.
 * 
 * Services:
 * - Receive a transaction, validate the transaction, create and mine 
 * 	 a new block, validate and append the mined block to the 
 *   blockchain + update the UxtoMap.
 * - Provide a limited UtxoMap that matches one particular address 
 *   to wallets.
 */
public class FullNode {
	
	private Blockchain blockchain;
	private UtxoMap utxoMap;
	private Wallet wallet;

	/**
	 * Initializes a node containing a blockchain, a UTXO-map and a wallet
	 * for block rewards. Then initializes the blockchain by mining and
	 * adding a genesis block.
	 */
	public FullNode(String walletId) {
		//TODO
	}

	/**
	 * Does what it says.
	 */
	public void mineAndAddGenesisBlock() {
		//TODO
		//1. Create the coinbase transaction
		//2. Add the coinbase transaction to a new block and mine the block
		//3. Validate the block. If valid:
			//4. Add the block to the blockchain
			//5. Update the utxo set
		//else
			//up to you
	}
	
	/**
	 * Does what it says.
	 */
	public void mineAndAppendBlockContaining(Transaction tx) {
		//TODO
		//1. Create the coinbase transaction
		//2. Add the two transactions to a new block and mine the block
		//3. Validate the block. If valid:
			//4. Add the block to the blockchain
			//5. Update the utxo set with the new coinbaseTx
			//6. Update the utxo set with the new tx
		//else
			//up to you
	}

	public Blockchain getBlockchain() {
		return blockchain;
	}

	public UtxoMap getUtxoMap() {
		return utxoMap;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void printOverview() {
		System.out.println();
		System.out.println("Full node overview");
		System.out.println("------------------");
		System.out.println("   Associated wallet");
		wallet.printOverviewIndented();
		System.out.println("   Associated blockchain");
		blockchain.printOverview();
	}
	
}
