package aero.sita.asl.testTasks.SpringIntegrationTask.handlers;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import junit.framework.Assert;

/**
 * Test to check the processing of InputFileRouter
 * 
 * @author syed.subhani
 *
 */
public class InputFileRouterTest {

	private InputFileRouter router;

	/**
	 * Setup the resources
	 */
	@Before
	public void setUp() {
		router = new InputFileRouter();
	}

	/**
	 * Tidying up resources
	 */
	@After
	public void tearDown() {
		router = null;
	}

	/**
	 * Test to verify the message is directed to problemChannel if the content
	 * is not valid.
	 */
	@Test
	public void routeInvalidMessage() {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("file_name", "abc.txt");
		headers.put("correlation_id", "askssdjeddlxer");
		MessageHeaders messageHeaders = new MessageHeaders(headers);
		Message<String> message = MessageBuilder.createMessage("1234\n1234x\n32", messageHeaders);
		String channel = router.handleFile(message);
		Assert.assertEquals("Message redirected to wrong channel ", "problemChannel", channel);
	}

	/**
	 * Test to verify the message is directed to outputChannel if the content is
	 * valid.
	 */
	@Test
	public void routeValidMessage() {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("file_name", "abc.txt");
		headers.put("correlation_id", "askssdjeddlxer");
		MessageHeaders messageHeaders = new MessageHeaders(headers);
		Message<String> message = MessageBuilder.createMessage("1234\n1234\n32345", messageHeaders);
		String channel = router.handleFile(message);
		Assert.assertEquals("Message redirected to wrong channel ", "outputChannel", channel);
	}
}
