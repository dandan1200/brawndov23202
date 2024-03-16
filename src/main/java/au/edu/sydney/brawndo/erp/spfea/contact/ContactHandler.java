package au.edu.sydney.brawndo.erp.spfea.contact;

import java.util.Arrays;
import java.util.List;

public interface ContactHandler {

    /**
     * Handles the request and starts the chain of responsibilities to send the message.
     * @param request the request object.
     * @return true if the message was sent successfully, false otherwise.
     */
    boolean handleRequest(ContactRequest request);

    /**
     * Sets the successor in the chain of responsibilities for this contact handler
     * @param handler the successor handler
     */
    void setSuccessor(ContactHandler handler);
    static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Merchandiser",
                "Phone call",
                "SMS"
        );
    }
}
