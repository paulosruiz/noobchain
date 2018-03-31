
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.google.gson.GsonBuilder;

import noobchain.model.Block;

public class NoobChain {

	final static Logger LOG = Logger.getLogger(NoobChain.class);

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static void main(String[] args) {
		// add our blocks to the blockchain ArrayList:

		blockchain.add(new Block("Hi im the first block", "0"));
		LOG.info("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);
/*
		blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).hash));
		LOG.info("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);

		blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).hash));
		LOG.info("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);
*/
		LOG.info("Blockchain is Valid: " + isChainValid());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		LOG.info("The block chain " + blockchainJson);
		// LOG.info(blockchainJson);
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		// loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// compare registered hash and calculated hash:
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				LOG.error("Current Hashes not equal");
				return false;
			}
			// compare previous hash and registered previous hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				LOG.error("Previous Hashes not equal");
				return false;
			}
			// check if hash is solved
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				LOG.warn("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
}