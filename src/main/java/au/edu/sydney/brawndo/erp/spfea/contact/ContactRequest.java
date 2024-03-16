package au.edu.sydney.brawndo.erp.spfea.contact;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class ContactRequest {
    private AuthToken authToken;
    private Customer customer;
    private ContactMethod contactMethod;
    private String data;

    /**
     * Creates a contact request object with the specified parameters.
     * @param authToken the user authentication token
     * @param customer the customer object
     * @param contactMethod the contact method enum value
     * @param data the data to include in the message.
     */
    public ContactRequest(AuthToken authToken, Customer customer, ContactMethod contactMethod, String data) {
        this.authToken = authToken;
        this.customer = customer;
        this.contactMethod = contactMethod;
        this.data = data;
    }

    /**
     * Gets the user authentication token
     * @return the authentication token of the object
     */

    public AuthToken getAuthToken() {
        return authToken;
    }

    /**
     * Gets the customer object
     * @return the customer object
     */
    public Customer getCustomer() {
        return customer;
    }
    /**
     * Gets the contact method enum value
     * @return the contact method
     */

    public ContactMethod getContactMethod() {
        return contactMethod;
    }

    /**
     * Gets the data for the message
     * @return the data of the object
     */
    public String getData() {
        return data;
    }

}
