package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;

public class GenerateInvoiceDataShortSubStrategy implements GenerateInvoiceDataStrategy {

    @Override
    public String generateInvoiceData(Order order) {
        if (order instanceof SubscriptionOrderImpl) {
            SubscriptionOrderImpl orderImpl = (SubscriptionOrderImpl) order;
            return String.format("Your business account will be charged: $%,.2f each week, with a total overall cost of: $%,.2f" +
                    "\nPlease see your Brawndo© merchandising representative for itemised details.", orderImpl.getRecurringCost(), orderImpl.getTotalCost());
        } else {
            return "Error: Invalid order";
        }
    }
}
