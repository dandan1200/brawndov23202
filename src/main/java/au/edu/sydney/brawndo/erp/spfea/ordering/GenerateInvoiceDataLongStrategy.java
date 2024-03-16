package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GenerateInvoiceDataLongStrategy implements GenerateInvoiceDataStrategy {

    @Override
    public String generateInvoiceData(Order order) {
        if (order instanceof OrderImpl) {
            OrderImpl orderImpl = (OrderImpl) order;
            StringBuilder sb = new StringBuilder();

            sb.append("Thank you for your Brawndo© order!\n");
            sb.append("Your order comes to: $");
            sb.append(String.format("%,.2f", orderImpl.getTotalCost()));
            sb.append("\nPlease see below for details:\n");
            List<Product> keyList = new ArrayList<>(orderImpl.getProducts().keySet());
            keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

            for (Product product: keyList) {
                sb.append("\tProduct name: ");
                sb.append(product.getProductName());
                sb.append("\tQty: ");
                sb.append(orderImpl.getProducts().get(product));
                sb.append("\tCost per unit: ");
                sb.append(String.format("$%,.2f", product.getCost()));
                sb.append("\tSubtotal: ");
                sb.append(String.format("$%,.2f\n", product.getCost() * orderImpl.getProducts().get(product)));
            }

            return sb.toString();
        }
        else {
            return "Error: invalid order";
        }
    }
}
