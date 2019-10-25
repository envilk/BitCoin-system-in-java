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
		blockchain = new Blockchain();
		utxoMap = new UtxoMap();
		wallet = new Wallet(walletId, this);
	}

	/**
	 * Does what it says.
	 * 1. Create the coinbase transaction
	 * 2. Add the two transactions to a new block and mine the block
	 * 3. Validate the block. If valid:
	 *		4. Add the block to the blockchain
	 *		5. Update the utxo set
	 * else
	 *		up to you
	 */
	public void mineAndAddGenesisBlock() {
		CoinbaseTx ct = new CoinbaseTx(blockchain.getHeight(), "Money for myself", wallet.getAddress());
		Block newBlock = new Block(null, ct, null);
		newBlock.mine();
		if(newBlock.isValidAsGenesisBlock() && ct.isValid(utxoMap)) {
			blockchain.appendBlock(newBlock);
			utxoMap.addOutput(null, ct.getOutput());
		}
		else
			System.out.println("Genesis block is not valid");
	}
	
	/**
	 * Does what it says.
	 * 1. Create the coinbase transaction
	 * 2. Add the two transactions to a new block and mine the block
	 * 3. Validate the block. If valid:
	 *		4. Add the block to the blockchain
	 *		5. Update the utxo set with the new coinbaseTx
	 *		6. Update the utxo set with the new tx
	 * else
	 *		up to you
	 */
	public void mineAndAppendBlockContaining(Transaction tx) {
	
		CoinbaseTx ct = new CoinbaseTx(blockchain.getHeight(), "Money for myself", wallet.getAddress());
		Block newBlock = new Block(blockchain.getLastBlockHash(), ct, tx);
		newBlock.mine();
		if(newBlock.isValid() && tx.isValid(utxoMap) && ct.isValid(utxoMap)) {
			blockchain.appendBlock(newBlock);
			utxoMap.addOutput(null, ct.getOutput());
			utxoMap.addOutput(tx.getInputs(), tx.getOutputs());
		}
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
