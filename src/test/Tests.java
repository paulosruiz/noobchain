package test;

import org.junit.Test;

import org.junit.Assert;
import noobchain.util.NoobChainUtil;

public class Tests {

	public String hashString = "Paulo";
	public String resultHash = "325d8eea60b3081478a9195a828d9c4c6744756ff4c64180f28d327e7e38e2e0";

	@Test
	public void testHash() throws Exception {
		String test = NoobChainUtil.applySha256(hashString);
		
		Assert.assertEquals("Hash Equals", resultHash,test);
		//Assert.assertEquals(resultHash, test);

	}
	
	
	
}
