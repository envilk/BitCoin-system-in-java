package no.hvl.dat159.application;

import no.hvl.dat159.Blockchain;
import no.hvl.dat159.FullNode;
import no.hvl.dat159.Transaction;
import no.hvl.dat159.Wallet;

public class DemoApplication {

	public static void main(String... blablabla) throws Exception {

		/*
		 * In this assignment, we are going to look at how to represent and record
		 * monetary transactions. We will use Bitcoin as the basis for the assignment,
		 * but there will be a lot of simplifications!
		 */

		/*
		 * 1. First, you should create the one and only FullNode.
		 * 		The full node will create it's internal Wallet, create the 
		 *      (centralized) Blockchain and UtxoMap, mine the genesis Block 
		 *      and add it to the blockchain (and update the UtxoMap).
		 */
		FullNode fullnode = new FullNode("0");
		Blockchain blockchain;
		fullnode.mineAndAddGenesisBlock();
		blockchain = fullnode.getBlockchain();

		/*
		 * 2. Next, you should create two additional wallets and reference
		 * 		the three wallets from three variables.
		 */
		Wallet wall1 = new Wallet("1", fullnode);
		Wallet wall2 = new Wallet("2", fullnode);

		/*
		 * 3. Next, you should create a Transaction to transfer some money
		 * 		from the miner's (full node's) wallet address to one of the 
		 * 		other wallet addresses. The full node should receive this 
		 * 		transaction, validate the transaction, mine a new block and 
		 * 		append it to the blockchain.
		 */
		Transaction tx1 = fullnode.getWallet().createTransaction(3, wall1.getAddress());
		fullnode.mineAndAppendBlockContaining(tx1);

		/*
		 * 4. Repeat the above (transfer some money from one wallet address
		 * 		to another and record this in the blockchain ledger). 
		 */
		Transaction tx2 = wall1.createTransaction(3, wall2.getAddress());
		fullnode.mineAndAppendBlockContaining(tx2);

		/*
		 * 5. Repeat the above (transfer some money from one wallet address
		 * 		to another and record this in the blockchain ledger). 
		 */
		Transaction tx3 = wall2.createTransaction(3, wall2.getAddress());
		fullnode.mineAndAppendBlockContaining(tx3);

		/*
		 * 6. Now, it is time to look at the finished result. Print out:
		 * 		- An overview of the full node
		 * 		- An overview of each of the three wallets
		 * 		- An overview of each of the four blocks in the blockchain
		 */
		fullnode.printOverview();
		fullnode.getWallet().printOverview();
		wall1.printOverview();
		wall2.printOverview();
		blockchain = fullnode.getBlockchain();
		blockchain.printOverview();
	}

}
