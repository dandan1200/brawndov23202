package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GenerateInvoiceDataLongSubStrategy implements GenerateInvoiceDataStrategy {
    @Override
    public String generateInvoiceData(Order order) {
        if (order instanceof SubscriptionOrderImpl) {
            SubscriptionOrderImpl orderImpl = (SubscriptionOrderImpl) order;
            Map<Product, Integer> products = orderImpl.getProducts();

            StringBuilder sb = new StringBuilder();

            sb.append("Thank you for your Brawndo© order!\n");
            sb.append("Your order comes to: $");
            sb.append(String.format("%,.2f", orderImpl.getRecurringCost()));
            sb.append(" each week, with a total overall cost of: $");
            sb.append(String.format("%,.2f", orderImpl.getTotalCost()));
            sb.append("\nPlease see below for details:\n");
            List<Product> keyList = new ArrayList<>(products.keySet());
            keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

            for (Product product: keyList) {
                sb.append("\tProduct name: ");
                sb.append(product.getProductName());
                sb.append("\tQty: ");
                sb.append(products.get(product));
                sb.append("\tCost per unit: ");
                sb.append(String.format("$%,.2f", product.getCost()));
                sb.append("\tSubtotal: ");
                sb.append(String.format("$%,.2f\n", product.getCost() * products.get(product)));
            }

            return sb.toString();
        }
        else {
            return "Error: invalid order";
        }
    }
}
