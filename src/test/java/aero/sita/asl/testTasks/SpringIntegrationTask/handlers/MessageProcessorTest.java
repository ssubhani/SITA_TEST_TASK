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
 * Test class to check the MessageProcessor.
 * 
 * @author syed.subhani
 *
 */
public class MessageProcessorTest {

	private MessageProcessor processor;

	/**
	 * Setting the resources ready
	 */
	@Before
	public void setUp() {
		processor = new MessageProcessor();
	}

	/**
	 * Tidying resources
	 */
	@After
	public void tearDown() {
		processor = null;
	}

	/**
	 * Test to check the message content.
	 */
	@Test
	public void checkOutMessageWithHeaders() {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("file_name", "abc.txt");
		headers.put("correlation_id", "askssdjeddlxer");
		MessageHeaders messageHeaders = new MessageHeaders(headers);
		Message<?> message = MessageBuilder.createMessage("1234\n1234\n32", messageHeaders);
		Message<?> output = (Message<?>) processor.processMessage(message);
		Assert.assertEquals("Output is wrong ", "2500", output.getPayload().toString());
		Assert.assertEquals("Filename modified ", "abc.txt", output.getHeaders().get("file_name"));
		Assert.assertEquals("correlation_id modified ", "askssdjeddlxer", output.getHeaders().get("correlation_id"));
	}
}
