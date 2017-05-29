package aero.sita.asl.testTasks.SpringIntegrationTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test to verify the spring configuration and the spring integration
 * 
 * @author Syed.Subhani
 *
 */
public class SpringIntegrationTaskClientTest {

	private static final File RESOURCES_DIR = new File("src/test/resources");

	/**
	 * Test for Invalid config file
	 * 
	 * @throws Exception
	 */
	@Test
	public void clientProcessCheckForInvalidConfigFile() throws Exception {
		SpringIntegrationTaskClient.setConfigFile("no-config.xml");
		Assert.assertNotNull("Spring config file is not found", SpringIntegrationTaskClient.getConfigFile());
		SpringIntegrationTaskClient.main(null);
	}

	/**
	 * Test to check the End-End Flow of the application.
	 * 
	 * @throws Exception
	 */
	@Test
	public void clientProcessCheckForValidConfigFile() throws Exception {
		SpringIntegrationTaskClient.setConfigFile("test/test-config.xml");
		Assert.assertNotNull("Spring config file is not found", SpringIntegrationTaskClient.getConfigFile());
		SpringIntegrationTaskClient.main(null);

		File outDir = new File(RESOURCES_DIR + "/OUT");
		File errorDir = new File(RESOURCES_DIR + "/ERROR");
		Assert.assertTrue("OUT directory not created ", outDir.exists());
		Assert.assertTrue("ERROR directory not created ", errorDir.exists());
		File outFile = new File(outDir + "/" + "valid-file.txt.PROCESSED");
		File errorFile = new File(errorDir + "/" + "invalid-file.txt");
		File originalErrorFile = new File(RESOURCES_DIR + "/IN/invalid-file.txt");
		Assert.assertTrue("Output not generated", outFile.exists());
		Assert.assertEquals("The genearated PROCESSED file is not valid ", 1, countLineNumbers(outFile));
		Assert.assertEquals("The genearated PROCESSED file doesn't have the right number ", 2468, readLine(outFile));
		Assert.assertTrue("Invalid file not passed to ERROR directory ", errorFile.exists());
		Assert.assertTrue("The generated file in ERROR directory is not correct ",
				FileUtils.contentEquals(errorFile, originalErrorFile));

		FileUtils.deleteDirectory(outDir);
		FileUtils.deleteDirectory(errorDir);
	}

	/**
	 * Utility method to read number of lines from the file.
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private int countLineNumbers(File file) throws Exception {
		int count = 0;
		FileReader fr = new FileReader(file);
		LineNumberReader lnr = new LineNumberReader(fr);
		while (lnr.readLine() != null) {
			count++;
		}
		lnr.close();
		fr.close();
		return count;
	}

	/**
	 * Read the first line from the file and return as a number.
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private long readLine(File file) throws Exception {
		long count = 0;
		BufferedReader br = new BufferedReader(new FileReader(file));
		count = Long.valueOf(br.readLine()).longValue();
		br.close();
		return count;
	}
}
