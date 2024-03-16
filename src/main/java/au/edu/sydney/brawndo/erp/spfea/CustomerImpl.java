package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;

/**
 * Implementation of the Customer interface.
 * Represents a customer in the system.
 */
public class CustomerImpl implements Customer {

    private final AuthToken authToken;
    private final int id;
    private String fName;
    private String lName;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String suburb;
    private String state;
    private String postCode;
    private String merchandiser;
    private String businessName;
    private String pigeonCoopID;

    /**
     * Constructs a CustomerImpl object with the provided authentication token and ID.
     *
     * @param token the authentication token for the customer
     * @param id    the ID of the customer
     */
    public CustomerImpl(AuthToken token, int id) {

        this.id = id;
        this.authToken = token;
        this.lName = null;
        this.phoneNumber = null;
        this.emailAddress = null;
        this.address = null;
        this.suburb = null;
        this.state = null;
        this.postCode = null;
        this.merchandiser = null;
        this.businessName = null;
        this.pigeonCoopID = null;
    }
    /**
     * Retrieves the ID of the customer.
     *
     * @return the ID of the customer
     */
    public int getId() {
        return id;
    }

    @Override
    public String getfName() {
        if (fName == null) {
            this.fName = TestDatabase.getInstance().getCustomerField(this.authToken, id, "fName");
        }
        return fName;
    }

    @Override
    public String getlName() {
        if (lName == null) {
            this.lName = TestDatabase.getInstance().getCustomerField(this.authToken, id, "lName");
        }
        return lName;
    }

    @Override
    public String getPhoneNumber() {
        if (phoneNumber == null) {
            this.phoneNumber = TestDatabase.getInstance().getCustomerField(this.authToken, id, "phoneNumber");
        }
        return phoneNumber;
    }

    @Override
    public String getEmailAddress() {
        if (emailAddress == null) {
            this.emailAddress = TestDatabase.getInstance().getCustomerField(this.authToken, id, "emailAddress");
        }
        return emailAddress;
    }

    @Override
    public String getAddress() {
        if (address == null) {
            this.address = TestDatabase.getInstance().getCustomerField(this.authToken, id, "address");
        }
        return address;
    }

    @Override
    public String getSuburb() {
        if (suburb == null) {
            this.suburb = TestDatabase.getInstance().getCustomerField(this.authToken, id, "suburb");
        }
        return suburb;
    }

    @Override
    public String getState() {
        if (state == null) {
            this.state = TestDatabase.getInstance().getCustomerField(this.authToken, id, "state");
        }
        return state;
    }

    @Override
    public String getPostCode() {
        if (postCode == null) {
            this.postCode = TestDatabase.getInstance().getCustomerField(this.authToken, id, "postCode");
        }
        return postCode;
    }

    @Override
    public String getMerchandiser() {
        if (merchandiser == null) {
            this.merchandiser = TestDatabase.getInstance().getCustomerField(this.authToken, id, "merchandiser");
        }
        return merchandiser;
    }

    @Override
    public String getBusinessName() {
        if (businessName == null) {
            this.businessName = TestDatabase.getInstance().getCustomerField(this.authToken, id, "businessName");
        }
        return businessName;
    }

    @Override
    public String getPigeonCoopID() {
        if (pigeonCoopID == null) {
            this.pigeonCoopID = TestDatabase.getInstance().getCustomerField(this.authToken, id, "pigeonCoopID");
        }
        return pigeonCoopID;
    }
}

