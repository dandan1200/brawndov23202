package au.edu.sydney.brawndo.erp.spfea.contact.concreteHandlers;

import au.edu.sydney.brawndo.erp.contact.CarrierPigeon;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactHandler;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactMethod;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactRequest;


public class CarrierPigeonHandler implements ContactHandler {
    private ContactHandler successor;

    @Override
    public void setSuccessor(ContactHandler successor) {
        this.successor = successor;
    }
    @Override
    public boolean handleRequest(ContactRequest request) {
        if (request.getContactMethod() == ContactMethod.CARRIER_PIGEON) {
            String pigeonCoopID = request.getCustomer().getPigeonCoopID();
            if (null != pigeonCoopID) {
                CarrierPigeon.sendInvoice(request.getAuthToken(), request.getCustomer().getfName(), request.getCustomer().getlName(), request.getData(), pigeonCoopID);
                return true;
            }
        }

        if (null != successor) {
            return successor.handleRequest(request);
        }

        return false;
    }
}
