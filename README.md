# SITA_TEST_TASK

## Task Detail: 
There will be a series of files placed into the directory (C:\SITA_TEST_TASK\IN) with a number on each line.
The application is responsible for 
1. To sum all the numbers in the file and create a new file containing the resulting value in the directory (C:\SITA_TEST_TASK\OUT). 
2. The output file will have the same name as the input file with .PROCESSED appended to the end of the filename. 
3. Only process files with a .txt extension. 
4. If an error occurs, the input file should be put into the following directory (C:\SITA_TEST_TASK\ERROR).

## Dependencies
* spring integration framework 4.1.2.RELEASE
* spring framework 4.1.4.RELEASE
* log4j 1.2.17
* junit 4.10
* apache commons 1.3.2


## Maven repository to download dependencies
https://repo.maven.apache.org/maven2/


## Build the application
1. From the command prompt run mvn clean install

## Note
1. It is assumed that the input files will be placed under C:\SITA_TEST_TASK\IN, however we can configure this value in application.properties which is available at src/main/resources

## Testing the application.
1. Before running the application, place the input files under C:\SITA_TEST_TASK\IN.
2. To run the application from maven, mvn clean package exec:java -P SpringIntegrationTaskClient
3. Verify the results in C:\SITA_TEST_TASK\OUT and C:\SITA_TEST_TASK\ERROR.

## Process Flow
1. At the time when we launch the application, the inbound-channel-adapter will start automatically since we have configured auto-startup value to true.
2. Since the poller configured it wll poll for messages from the given input directory.
3. All the messages one by one enter to router and the router will do the message validation. The validation will pass if the lines in the file contains numbers. If the validation fails then router will send the message to errorChannel.
Otherwise the message will send to outputChannel.
4. On the errorChannel we have outbound-channel-adapter and this is responsible to put message into the directory specified in configuration.
5. On the outputChannel we have service-activator and the bean referred for this channel is responsible to generate the file content (sum of numbers) and will send to writeOutputChannel.
6. On the writeOutputChannel we have outbound-channel-adapter which is responsible to generate the output in the configured directory. Since we have used configured file-name-generator then the application is having the control to decide the name of the file. This generator will add ".PROCESSED" at the end of original file name.
 
