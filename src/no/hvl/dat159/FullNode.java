package no.hvl.dat159;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import no.hvl.dat159.util.DateTimeUtil;

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
	 * 2. Add the coinbase transaction to a new block and mine the block
	 * 3. Validate the block. If valid:
	 *		4. Add the block to the blockchain
	 *		5. Update the utxo set
	 * else
	 *		up to you
	 */
	public void mineAndAddGenesisBlock() {
		CoinbaseTx ct = new CoinbaseTx(blockchain.getHeight(), "Mined "+DateTimeUtil.getTimestamp()+" by "+wallet.getId(), wallet.getAddress());
		Block newBlock = new Block("0", ct, null);
		newBlock.mine();
		if(newBlock.isValidAsGenesisBlock() && ct.isValid(utxoMap)) {
			blockchain.appendBlock(newBlock);
			utxoMap.addOutput(new Input(ct.getTxId(), 0), ct.getOutput());
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
	 * @throws SignatureException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public void mineAndAppendBlockContaining(Transaction tx) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
	
		CoinbaseTx ct = new CoinbaseTx(blockchain.getHeight(), "Mined "+DateTimeUtil.getTimestamp()+" by "+wallet.getId(), wallet.getAddress());
		Block newBlock = new Block(blockchain.getLastBlockHash(), ct, tx);
		newBlock.mine();
		if(newBlock.isValid() && tx.isValid(utxoMap) && ct.isValid(utxoMap)) {
			blockchain.appendBlock(newBlock);
			for (Input input : tx.getInputs()) {//For each input inside tx transaction, that input row in the utxoMap should be removed
				utxoMap.removeOutput(input);
			}
			utxoMap.addOutput(new Input(ct.getTxId(), 0), ct.getOutput());
			//Here I have to create the new INPUTS in the utxoMap from the outputs of the transaction tx
			int outputIndex = 0;
			for (Output output : tx.getOutputs()) {
				utxoMap.addOutput(new Input(tx.getTxId(), outputIndex), output);
				outputIndex++;
			}
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
