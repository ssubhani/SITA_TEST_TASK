package aero.sita.asl.testTasks.SpringIntegrationTask.handlers;

import org.apache.log4j.Logger;
import org.springframework.messaging.Message;

/**
 * Router class to send requests to appropriate channel.
 * 
 * @author Syed.Subhani
 *
 */
public class InputFileRouter {

    /* LOGGER to use */
    private static final Logger LOGGER = Logger.getLogger(InputFileRouter.class);
    private static final String HEADER_FILE_NAME = "file_name";
    private static final String MSG_RECEIVED = "%s received. Content: %s";
    private static final String MSG_SENT = "File %s is sending to Chanel: %s";

    /**
     * Method to receive and process the incoming payload and send to the right
     * channel.
     * 
     * @param msg
     * @return
     */
    public String handleFile(Message<String> msg) {
        String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);
        String content = msg.getPayload();
        LOGGER.debug(String.format(MSG_RECEIVED, fileName, content));
        String channel = validateContent(content);
        LOGGER.info(String.format(MSG_SENT, fileName, channel));
        return channel;
    }

    /**
     * Method to decide the right channel.
     * 
     * @param content
     * @return the channel
     */
    private String validateContent(String content) {
        String[] lines = content.split("\n");
        boolean isValid = true;
        for (String line : lines) {
            try {
                Long.parseLong(line.trim());
            } catch (NumberFormatException e) {
                isValid = false;
                break;
            }
        }
        if (isValid) {
            return "outputChannel";
        }
        return "problemChannel";
    }
}
