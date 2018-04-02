package noobchain;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.google.gson.GsonBuilder;
import noobchain.model.Block;
import noobchain.util.NoobChainUtil;

public class NoobChain {

	final static Logger LOG = Logger.getLogger(NoobChain.class);

	public static ArrayList<Block> blockchain = new ArrayList<Block>();

	public static int qtyOfBlocks = 3;

	public static void main(String[] args) {
		// add our blocks to the blockchain ArrayList:
		createBlocks(qtyOfBlocks);

		LOG.info(String.format("Blockchain is Valid: %s", NoobChainUtil.isChainValid(blockchain)));

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		LOG.info("The blockchain result");
		LOG.info(blockchainJson);
		// LOG.info(blockchainJson);
	}

	/**
	 * Create required blocks
	 * 
	 * @qtyofBlocks - Number of blocks
	 */
	private static void createBlocks(final int qtyofBlocks) {
		for (int i = 0; i < qtyOfBlocks; i++) {
			Block b = null;
			if (blockchain.isEmpty()) {
				b = new Block("Block id" + i, "0");
			} else {
				b = new Block("Block id" + i, blockchain.get(i - 1).getHash());
			}
			LOG.info("Trying to Mine block: " + i);
			b.mineBlock(NoobChainUtil.difficulty);
			blockchain.add(b);
		}
	}
}