package au.edu.sydney.brawndo.erp.spfea.contact;

import au.edu.sydney.brawndo.erp.spfea.contact.concreteHandlers.*;

public class ContactHandlerChain {
    private SMSHandler smsHandler;
    private MailHandler mailHandler;
    private EmailHandler emailHandler;
    private PhoneCallHandler phoneCallHandler;
    private MerchandiserHandler merchandiserHandler;
    private CarrierPigeonHandler carrierPigeonHandler;

    /**
     * Creates a new contact handler chain object and initializes the successors for each object in the chain.
     */
    public ContactHandlerChain() {
        smsHandler = new SMSHandler();
        mailHandler = new MailHandler();
        emailHandler = new EmailHandler();
        phoneCallHandler = new PhoneCallHandler();
        merchandiserHandler = new MerchandiserHandler();
        carrierPigeonHandler = new CarrierPigeonHandler();

        // Set the successor for each handler in the chain
        smsHandler.setSuccessor(mailHandler);
        mailHandler.setSuccessor(emailHandler);
        emailHandler.setSuccessor(phoneCallHandler);
        phoneCallHandler.setSuccessor(merchandiserHandler);
        merchandiserHandler.setSuccessor(carrierPigeonHandler);
    }
    public boolean handleRequest(ContactRequest request) {
        return smsHandler.handleRequest(request);
    }
}
