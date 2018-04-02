package noobchain.model;

import java.util.Date;

import org.apache.log4j.Logger;

import noobchain.util.NoobChainUtil;

/**
 * Block of the chain
 * @author paulo.almeida.junior
 *
 */
public class Block {

	public static final Logger LOG = Logger.getLogger(Block.class);
	
	private String hash;
	private String previousHash;
	
	private String data; // our data will be a simple message.
	private long timeStamp; // as number of milliseconds since 1/1/1970.
	
	private int nonce;

	private boolean mined;

	// Block Constructor.
	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.mined = false;
		this.hash = calculateHash(); // Making sure we do this after we set the other values.
	}

	// Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = NoobChainUtil
				.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data);
		return calculatedhash;
	}

	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); // Create a string with difficulty * "0"
		LOG.info("target: " + target);
		
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}

		mined = true;
		LOG.info(String.format("Block Mined!!! hash: %s  after %d tentatives", hash,nonce));
	}

	public boolean isMined() {
		return this.mined;
	}
	/**
	 * @return the hash
	 */
	public final String getHash() {
		return hash;
	}

	/**
	 * @return the previousHash
	 */
	public final String getPreviousHash() {
		return previousHash;
	}

	/**
	 * @param hash the hash to set
	 */
	public final void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @param previousHash the previousHash to set
	 */
	public final void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
}