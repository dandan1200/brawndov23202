package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;

public interface GenerateInvoiceDataStrategy {
    /**
     * Generates the invoice data according to a specified strategy algorithm for the given order.
     * @param order the order object
     * @return invoice data string.
     */
    String generateInvoiceData(Order order);
}
