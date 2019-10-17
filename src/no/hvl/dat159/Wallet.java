package no.hvl.dat159;

import java.security.KeyPair;
import java.security.PublicKey;

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
		//TODO
	}
	
	/**
	 * 
	 */
    public Transaction createTransaction(long value, String address) throws Exception {
    	//TODO
        // 1. Calculate the balance
        // 2. Check if there are sufficient funds --- Exception?
        // 3. Choose a number of UTXO to be spent - We take ALL 
        //   (= the complete wallet balance)!
        // 4. Calculate change
        // 5. Create an "empty" transaction
        // 6. Add chosen inputs (=ALL)
        // 7. Add 1 or 2 outputs, depending on change
        // 8. Sign the transaction
        return null;
    }

    public String getId() {
		return id;
	}

    /**
     * 
     */
	public PublicKey getPublicKey() {
		//TODO
		return null;
    }

	/**
	 * 
	 */
    public String getAddress() {
		//TODO
		return null;
    }
    
    /**
     * 
     */
    public long calculateBalance() {
    	//TODO
    	return 0;
    }

    /**
     * 
     */
    public int getNumberOfUtxos() {
    	//TODO
    	return 0;
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
