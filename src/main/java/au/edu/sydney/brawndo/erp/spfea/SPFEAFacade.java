package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthModule;
import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactHandler;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactHandlerChain;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactMethod;
import au.edu.sydney.brawndo.erp.spfea.contact.ContactRequest;
import au.edu.sydney.brawndo.erp.spfea.ordering.*;
import au.edu.sydney.brawndo.erp.spfea.products.ProductDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SPFEAFacade {
    private AuthToken token;
    private ContactHandlerChain contactHandler;

    private OrderUnitOfWork unsavedOrders = new OrderUnitOfWork();

    /**
     * Logs in the user with the provided username and password.
     *
     * @param userName the username
     * @param password the password
     * @return true if login is successful, false otherwise
     */
    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);
        contactHandler = new ContactHandlerChain();

        return null != token;
    }

    /**
     * Retrieves a list of all order IDs.
     *
     * @return a list of all order IDs
     * @throws SecurityException if the user is not logged in
     */
    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        orders.addAll(unsavedOrders.getAll());

        List<Integer> result = new ArrayList<>();

        for (Order order: orders) {
            result.add(order.getOrderID());
        }

        return result;
    }

    /**
     * Creates a new order.
     *
     * @param customerID        the ID of the customer
     * @param date              the date of the order
     * @param isBusiness        true if the customer is a business customer, false otherwise
     * @param isSubscription    true if the order is a subscription order, false otherwise
     * @param discountType      the discount type
     * @param discountThreshold the discount threshold
     * @param discountRateRaw   the discount rate (raw value)
     * @param numShipments      the number of shipments
     * @return the ID of the created order
     * @throws SecurityException       if the user is not logged in
     * @throws IllegalArgumentException if the discount rate is not a percentage or the customer ID is invalid
     */
    public Integer createOrder(int customerID, LocalDateTime date, boolean isBusiness, boolean isSubscription, int discountType, int discountThreshold, int discountRateRaw, int numShipments) {
        if (null == token) {
            throw new SecurityException();
        }

        if (discountRateRaw < 0 || discountRateRaw > 100) {
            throw new IllegalArgumentException("Discount rate not a percentage");
        }

        double discountRate = 1.0 - (discountRateRaw / 100.0);

        Order order;

        if (!TestDatabase.getInstance().getCustomerIDs(token).contains(customerID)) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();


        if (isSubscription) {
            order = new SubscriptionOrderImpl(id, customerID, date,discountThreshold, discountRate, numShipments);
            if (1 == discountType) { // 1 is flat rate
                    if (isBusiness) {

                        //Previously 'NewOrderImplSubscription'
                        ((OrderImpl) order).setTotalCostStrategy(new TotalCostWithoutThresholdStrategy());
                        ((OrderImpl) order).setGenerateInvoiceDataStrategy(new GenerateInvoiceDataShortSubStrategy());

                    } else {
                        //Previously 'Order66Subscription'
                        ((OrderImpl) order).setTotalCostStrategy(new TotalCostWithoutThresholdStrategy());
                        ((OrderImpl) order).setGenerateInvoiceDataStrategy(new GenerateInvoiceDataLongSubStrategy());

                    }
                } else if (2 == discountType) { // 2 is bulk discount
                    if (isBusiness) {
                        //Previously 'BusinessBulkDiscountSubscription'
                        ((OrderImpl) order).setTotalCostStrategy(new TotalCostWithThresholdStrategy());
                        ((OrderImpl) order).setGenerateInvoiceDataStrategy(new GenerateInvoiceDataShortSubStrategy());

                    } else {
                        //Previously 'FirstOrderSubscription'
                        ((OrderImpl) order).setTotalCostStrategy(new TotalCostWithThresholdStrategy());
                        ((OrderImpl) order).setGenerateInvoiceDataStrategy(new GenerateInvoiceDataLongSubStrategy());

                    }
            } else {return null;}
        } else {
            order = new OrderImpl(id, customerID, date, discountThreshold, discountRate);
            if (1 == discountType) {
                if (isBusiness) {
                    //Previously 'NewOrderImpl'
                    ((OrderImpl) order).setTotalCostStrategy(new TotalCostWithoutThresholdStrategy());
                    ((OrderImpl) order).setGenerateInvoiceDataStrategy(new GenerateInvoiceDataShortStrategy());
                    //order = new NewOrderImpl(id, date, customerID, discountRate);
                } else {
                    //Previously 'Order66'
                    ((OrderImpl) order).setTotalCostStrategy(new TotalCostWithoutThresholdStrategy());
                    ((OrderImpl) order).setGenerateInvoiceDataStrategy(new GenerateInvoiceDataLongStrategy());
                }
            } else if (2 == discountType) {
                if (isBusiness) {
                    //Previously 'BusinessBulkDiscountOrder'
                    ((OrderImpl) order).setTotalCostStrategy(new TotalCostWithThresholdStrategy());
                    ((OrderImpl) order).setGenerateInvoiceDataStrategy(new GenerateInvoiceDataShortStrategy());

                } else {
                    //Previously 'FirstOrder'
                    ((OrderImpl) order).setTotalCostStrategy(new TotalCostWithThresholdStrategy());
                    ((OrderImpl) order).setGenerateInvoiceDataStrategy(new GenerateInvoiceDataLongStrategy());

                }
            } else {return null;}
        }

        unsavedOrders.addOrder(order);
        return order.getOrderID();
    }
    /**
     * Retrieves a list of all customer IDs.
     *
     * @return a list of all customer IDs
     * @throws SecurityException if the user is not logged in
     */
    public List<Integer> getAllCustomerIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.getCustomerIDs(token);
    }
    /**
     * Retrieves the customer with the given ID.
     *
     * @param id the ID of the customer
     * @return the customer object
     * @throws SecurityException if the user is not logged in
     */
    public Customer getCustomer(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        return new CustomerImpl(token, id);
    }
    /**
     * Removes an order with the given ID.
     *
     * @param id the ID of the order to remove
     * @return true if the order was successfully removed, false otherwise
     * @throws SecurityException if the user is not logged in
     */
    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        if (unsavedOrders.popOrder(id)) {
            return true;
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    /**
     * Retrieves a list of all products.
     *
     * @return a list of all products
     * @throws SecurityException if the user is not logged in
     */
    public List<Product> getAllProducts() {
        if (null == token) {
            throw new SecurityException();
        }

        return new ArrayList<>(ProductDatabase.getTestProducts());
    }

    /**
     * Finalizes an order with the given order ID and sends a contact request to the specified contact methods.
     *
     * @param orderID         the ID of the order to finalize
     * @param contactPriority a list of contact methods in priority order
     * @return true if the contact request was successfully sent, false otherwise
     * @throws SecurityException if the user is not logged in
     */
    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }

        List<ContactMethod> contactPriorityAsMethods = new ArrayList<>();

        if (null != contactPriority) {
            for (String method: contactPriority) {
                switch (method.toLowerCase()) {
                    case "merchandiser":
                        contactPriorityAsMethods.add(ContactMethod.MERCHANDISER);
                        break;
                    case "email":
                        contactPriorityAsMethods.add(ContactMethod.EMAIL);
                        break;
                    case "carrier pigeon":
                        contactPriorityAsMethods.add(ContactMethod.CARRIER_PIGEON);
                        break;
                    case "mail":
                        contactPriorityAsMethods.add(ContactMethod.MAIL);
                        break;
                    case "phone call":
                        contactPriorityAsMethods.add(ContactMethod.PHONECALL);
                        break;
                    case "sms":
                        contactPriorityAsMethods.add(ContactMethod.SMS);
                        break;
                    default:
                        break;
                }
            }
        }

        if (contactPriorityAsMethods.size() == 0) { // needs setting to default
            contactPriorityAsMethods = Arrays.asList(
                    ContactMethod.MERCHANDISER,
                    ContactMethod.EMAIL,
                    ContactMethod.CARRIER_PIGEON,
                    ContactMethod.MAIL,
                    ContactMethod.PHONECALL
            );
        }

        Order order = unsavedOrders.getOrder(orderID);
        order.finalise();

        boolean sent = false;
        for (ContactMethod method : contactPriorityAsMethods) {
            ContactRequest request = new ContactRequest(token, getCustomer(order.getCustomer()), method, order.generateInvoiceData());
            sent = contactHandler.handleRequest(request);
            if (sent) {
                break;
            }
        }
        return sent;
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        unsavedOrders.commit(token);
        AuthModule.logout(token);
        token = null;
    }

    /**
     * Retrieves the total cost of an order with the given order ID.
     *
     * @param orderID the ID of the order
     * @return the total cost of the order
     * @throws SecurityException if the user is not logged in
     */
    public double getOrderTotalCost(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return 0.0;
        }

        return order.getTotalCost();
    }

    /**
     * Sets the product and quantity for an order with the given order ID.
     *
     * @param orderID  the ID of the order
     * @param product a single product
     * @param qty the quantity of the product
     * @throws SecurityException if the user is not logged in
     */
    public void orderLineSet(int orderID, Product product, int qty) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            System.out.println("got here");
            return;
        }

        order.setProduct(product, qty);

    }

    /**
     * Gets the long description for an order with given order ID.
     *
     * @param orderID
     * @return the order long description
     */
    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    /**
     * Gets the short description for an order with given order ID.
     *
     * @param orderID
     * @return the order short description.
     */
    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }


        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    /**
     * Gets a list of the known contact methods.
     *
     * @return a list of known contact methods.
     */
    public List<String> getKnownContactMethods() {if (null == token) {
        throw new SecurityException();
    }

        return ContactHandler.getKnownMethods();
    }
}
