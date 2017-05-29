package aero.sita.asl.testTasks.SpringIntegrationTask.handlers;

import org.springframework.integration.file.FileNameGenerator;
import org.springframework.messaging.Message;

/**
 * Custom Generator class used to update the name of the file.
 * @author Syed.Subhani
 *
 */
public class ProcessedFileNameGenerator implements FileNameGenerator {

    /**
     * Method to update the filename by FileWritingMessageHandler
     * @param message
     * @return the updated file name
     */
    public String generateFileName(Message<?> message) {        
        return message.getHeaders().get("file_name").toString()+".PROCESSED";
    }
}
