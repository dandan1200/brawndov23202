package au.edu.sydney.brawndo.erp.spfea.contact.concreteHandlers;

import au.edu.sydney.brawndo.erp.contact.SMS;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactHandler;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactMethod;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactRequest;


public class SMSHandler implements ContactHandler {
    private ContactHandler successor;
    @Override
    public void setSuccessor(ContactHandler successor) {
        this.successor = successor;
    }
    @Override
    public boolean handleRequest(ContactRequest request) {
        if (request.getContactMethod() == ContactMethod.SMS) {
            String smsPhone = request.getCustomer().getPhoneNumber();
            if (null != smsPhone) {
                SMS.sendInvoice(request.getAuthToken(), request.getCustomer().getfName(), request.getCustomer().getlName(), request.getData(), smsPhone);
                return true;
            }
        }

        if (null != successor) {
            return successor.handleRequest(request);
        }

        return false;
    }
}
