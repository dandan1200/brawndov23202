package au.edu.sydney.brawndo.erp.spfea.contact.concreteHandlers;

import au.edu.sydney.brawndo.erp.contact.Merchandiser;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactHandler;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactMethod;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactRequest;


public class MerchandiserHandler implements ContactHandler {
    private ContactHandler successor;
    @Override
    public void setSuccessor(ContactHandler successor) {
        this.successor = successor;
    }
    @Override
    public boolean handleRequest(ContactRequest request) {
        if (request.getContactMethod() == ContactMethod.MERCHANDISER) {
            String merchandiser = request.getCustomer().getMerchandiser();
            String businessName = request.getCustomer().getBusinessName();
            if (null != merchandiser && null != businessName) {
                Merchandiser.sendInvoice(request.getAuthToken(), request.getCustomer().getfName(), request.getCustomer().getlName(), request.getData(), merchandiser,businessName);
                return true;
            }
        }

        if (null != successor) {
            return successor.handleRequest(request);
        }

        return false;
    }
}
