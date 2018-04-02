package noobchain.util;

import java.security.MessageDigest;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import noobchain.model.Block;

public class NoobChainUtil {

	
	final static Logger LOG = Logger.getLogger(NoobChainUtil.class);

	public static final String EncryptMode = "SHA-256";
	public static final String EncodeMode = "UTF-8";

	public static int difficulty = 1;

	// Applies Sha256 to a string and returns the result.
	public static String applySha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance(EncryptMode);
			// Applies sha256 to our input,
			byte[] hash = digest.digest(input.getBytes(EncodeMode));
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Boolean isChainValid(ArrayList<Block> blockchain) {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		// loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// compare registered hash and calculated hash:
			if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
				LOG.error("Current Hashes not equal");
				return false;
			}
			// compare previous hash and registered previous hash
			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				LOG.error("Previous Hashes not equal");
				return false;
			}
			// check if hash is solved
			if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
				LOG.warn("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
}