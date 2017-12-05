package hello.Listener;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SimpleListener {
    private static final Logger log = Logger.getLogger(SimpleListener.class);

    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");

        log.info("Message processed...");

    }
}