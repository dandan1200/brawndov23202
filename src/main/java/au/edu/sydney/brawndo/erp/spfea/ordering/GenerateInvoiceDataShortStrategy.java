package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;

public class GenerateInvoiceDataShortStrategy implements GenerateInvoiceDataStrategy {

    @Override
    public String generateInvoiceData(Order order) {
        return String.format("Your business account has been charged: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", order.getTotalCost());
    }
}
