package no.hvl.dat159;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Set;
import java.util.Map.Entry;

import no.hvl.dat159.util.HashUtil;

/**
 * A Wallet keeps the keys and creates signed transactions to be
 * sent to the "network"/node.
 * A wallet also has a name/id to make it easier to identify.
 */
public class Wallet {

	private String id;
	private KeyPair keyPair;

	/*
	 * The single node in this "network" that the wallets knows about.
	 */
	private FullNode networkNode;

	/**
	 * 
	 */
	public Wallet(String id, FullNode node) {
		this.id = id;
		this.networkNode = node;

	}

	/**
	 * 1. Calculate the balance
	 * 2. Check if there are sufficient funds --- Exception?
	 * 3. Choose a number of UTXO to be spent - We take ALL 
	 *   (= the complete wallet balance)!
	 * 4. Calculate change
	 * 5. Create an "empty" transaction
	 * 6. Add chosen inputs (=ALL)
	 * 7. Add 1 or 2 outputs, depending on change
	 * 8. Sign the transaction
	 */
	public Transaction createTransaction(long value, String address) throws Exception {
		long balance = calculateBalance();
		if(!(balance >= value)) throw new Exception();
		UtxoMap utxoObject = networkNode.getUtxoMap();
		Set<Entry<Input, Output>> utxoSet = utxoObject.getUtxosForAddress(getAddress());
		long change = balance - value;
		Transaction tx = new Transaction(getPublicKey());
		for (Entry<Input, Output> entry : utxoSet) {
			Input input = entry.getKey();
			tx.addInput(input);
		}
		if(change != 0)//If there is change
			tx.addOutput(new Output(change, getAddress()));//For sender
		tx.addOutput(new Output(value, address));//For receiver
		tx.signTxUsing(keyPair.getPrivate());
		return tx;
	}

	public String getId() {
		return id;
	}

	/**
	 * 
	 */
	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}

	/**
	 * 
	 */
	public String getAddress() {
		return HashUtil.pubKeyToAddress(getPublicKey());
	}

	/**
	 * 
	 */
	public long calculateBalance() {
		UtxoMap utxoObject = networkNode.getUtxoMap();
		Set<Entry<Input, Output>> utxoSet = utxoObject.getUtxosForAddress(getAddress());
		long balance = 0;
		for (Entry<Input, Output> entry : utxoSet) {
			Output output = entry.getValue();
			balance += output.getValue();
		}
		return balance;
	}

	/**
	 * 
	 */
	public int getNumberOfUtxos() {
		return networkNode.getUtxoMap().getAllUtxos().size();
	}

	public void printOverview() {
		System.out.println();
		System.out.println(id + " overview");
		System.out.println("----------------------");
		System.out.println("   Address    : " + getAddress());
		System.out.println("   Balance    : " + calculateBalance());
		System.out.println("   # of UTXOs : " + getNumberOfUtxos());

	}

	public void printOverviewIndented() {
		System.out.println("      " + id + " with address : " + getAddress());
	}

}
