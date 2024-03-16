package au.edu.sydney.brawndo.erp.spfea.contact.concreteHandlers;

import au.edu.sydney.brawndo.erp.contact.Mail;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactHandler;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactMethod;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactRequest;


public class MailHandler implements ContactHandler {
    private ContactHandler successor;
    @Override
    public void setSuccessor(ContactHandler successor) {
        this.successor = successor;
    }
    @Override
    public boolean handleRequest(ContactRequest request) {
        if (request.getContactMethod() == ContactMethod.MAIL) {
            String address = request.getCustomer().getAddress();
            String suburb = request.getCustomer().getSuburb();
            String state = request.getCustomer().getState();
            String postcode = request.getCustomer().getPostCode();
            if (null != address && null != suburb &&
                    null != state && null != postcode) {
                Mail.sendInvoice(request.getAuthToken(), request.getCustomer().getfName(), request.getCustomer().getlName(), request.getData(), address, suburb, state, postcode);
                return true;
            }
        }

        if (null != successor) {
            return successor.handleRequest(request);
        }

        return false;
    }
}
