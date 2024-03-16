package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SubscriptionOrderImpl extends OrderImpl implements SubscriptionOrder {

    private int numShipments;

    /**
     * Creates a new SubscriptionOrder object based on the specified parameters.
     * @param id the id of the order
     * @param customerID the customer ID of the order
     * @param date the date the order was placed
     * @param discountThreshold the discount threshold number
     * @param discountRate the rate of the discount
     * @param numShipments number of shipments in the subscription
     */
    public SubscriptionOrderImpl(int id, int customerID, LocalDateTime date, int discountThreshold, double discountRate, int numShipments) {
        super(id, customerID, date, discountThreshold, discountRate);
        this.numShipments = numShipments;
    }

    @Override
    public double getRecurringCost() {
        return super.getTotalCost();
    }

    @Override
    public int numberOfShipmentsOrdered() {
        return numShipments;
    }

    @Override
    public Order copy() {
        Map<Product, Integer> products = super.getProducts();

        Order copy = new SubscriptionOrderImpl(getOrderID(), getCustomer(), getOrderDate(), getDiscountThreshold(), getDiscountRate(), numShipments); {
        };
        ((OrderImpl) copy).setGenerateInvoiceDataStrategy(this.generateInvoiceDataStrategy);
        ((OrderImpl) copy).setTotalCostStrategy(this.totalCostStrategy);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f per shipment, $%,.2f total", super.getOrderID(), getRecurringCost(), super.getTotalCost());
    }

    @Override
    public String longDesc() {

        double fullCost = 0.0;
        double discountedCost = super.getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(super.getProducts().keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * super.getProducts().get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    super.getProducts().get(product),
                    product.getCost(),
                    subtotal));
        }

        return String.format(super.isFinalised() ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of shipments: %d\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                super.getOrderID(),
                super.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                numShipments,
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost,
                getTotalCost()

        );
    }

    @Override
    public double getTotalCost() {
        return super.getTotalCost() * numShipments;
    }

}
