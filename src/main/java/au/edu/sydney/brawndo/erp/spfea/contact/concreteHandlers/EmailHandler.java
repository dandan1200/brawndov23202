package au.edu.sydney.brawndo.erp.spfea.contact.concreteHandlers;

import au.edu.sydney.brawndo.erp.contact.Email;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactHandler;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactMethod;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactRequest;


public class EmailHandler implements ContactHandler {
    private ContactHandler successor;
    @Override
    public void setSuccessor(ContactHandler successor) {
        this.successor = successor;
    }
    @Override
    public boolean handleRequest(ContactRequest request) {
        if (request.getContactMethod() == ContactMethod.EMAIL) {
            String email = request.getCustomer().getEmailAddress();
            if (null != email) {
                Email.sendInvoice(request.getAuthToken(), request.getCustomer().getfName(), request.getCustomer().getlName(), request.getData(), email);
                return true;
            }
        }

        if (null != successor) {
            return successor.handleRequest(request);
        }

        return false;
    }
}
