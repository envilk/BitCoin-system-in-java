package no.hvl.dat159.application;

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
    	//TODO
		
		/*
		 * 2. Next, you should create two additional wallets and reference
		 * 		the three wallets from three variables.
		 */
    	//TODO
		
		/*
		 * 3. Next, you should create a Transaction to transfer some money
		 * 		from the miner's (full node's) wallet address to one of the 
		 * 		other wallet addresses. The full node should receive this 
		 * 		transaction, validate the transaction, mine a new block and 
		 * 		append it to the blockchain.
		 */
    	//TODO

		/*
		 * 4. Repeat the above (transfer some money from one wallet address
		 * 		to another and record this in the blockchain ledger). 
		 */
    	//TODO
		
		/*
		 * 5. Repeat the above (transfer some money from one wallet address
		 * 		to another and record this in the blockchain ledger). 
		 */
    	//TODO

		/*
		 * 6. Now, it is time to look at the finished result. Print out:
		 * 		- An overview of the full node
		 * 		- An overview of each of the three wallets
		 * 		- An overview of each of the four blocks in the blockchain
		 */
    	//TODO
	}
	
}
