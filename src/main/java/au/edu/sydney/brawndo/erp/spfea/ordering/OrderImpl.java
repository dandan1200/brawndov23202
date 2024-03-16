package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderImpl implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private final int id;
    private LocalDateTime date;
    private int customerID;
    private double discountRate;
    private int discountThreshold;
    private boolean finalised = false;

    protected TotalCostStrategy totalCostStrategy;
    protected GenerateInvoiceDataStrategy generateInvoiceDataStrategy;

    /**
     * Constructs a new order object with the specified properties.
     * @param id the id of the order
     * @param customerID the customer ID of the order
     * @param date the date the order was placed
     * @param discountThreshold the discount threshold number
     * @param discountRate the rate of the discount
     */
    public OrderImpl(int id, int customerID, LocalDateTime date, int discountThreshold, double discountRate) {
        this.id = id;
        this.customerID = customerID;
        this.date = date;
        this.discountThreshold = discountThreshold;
        this.discountRate = discountRate;
    }

    @Override
    public int getOrderID() {
        return id;
    }

    @Override
    public double getTotalCost() {
        return totalCostStrategy.getTotalCost(this);
//        double cost = 0.0;
//        for (Product product: products.keySet()) {
//            int count = products.get(product);
//            if (count >= discountThreshold) {
//                cost +=  count * product.getCost() * discountRate;
//            } else {
//                cost +=  count * product.getCost();
//            }
//        }
//        return cost;
    }

    @Override
    public LocalDateTime getOrderDate() {
        return date;
    }


    @Override
    public void setProduct(Product product, int qty) {
        if (finalised) throw new IllegalStateException("Order was already finalised.");

        // We can't rely on like products having the same object identity since they get
        // rebuilt over the network, so we had to check for presence and same values

        for (Product contained: products.keySet()) {
            if (product.equals(contained)) {
                product = contained;
                break;
            }
        }

        products.put(product, qty);
    }

    @Override
    public Set<Product> getAllProducts() {
        return products.keySet();
    }

    @Override
    public int getProductQty(Product product) {
        // We can't rely on like products having the same object identity since they get
        // rebuilt over the network, so we had to check for presence and same values

        for (Product contained: products.keySet()) {
            if (product.equals(contained)) {
                product = contained;
                break;
            }
        }
        Integer result = products.get(product);
        return null == result ? 0 : result;
    }


    @Override
    public String generateInvoiceData() {
        return this.generateInvoiceDataStrategy.generateInvoiceData(this);
    }

    @Override
    public int getCustomer() {
        return customerID;
    }

    @Override
    public void finalise() {
        this.finalised = true;
    }

    @Override
    public Order copy() {
        Order copy = new OrderImpl(id, customerID, date, discountThreshold, discountRate);
        ((OrderImpl) copy).setGenerateInvoiceDataStrategy(this.generateInvoiceDataStrategy);
        ((OrderImpl) copy).setTotalCostStrategy(this.totalCostStrategy);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }
    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f", id, getTotalCost());
    }


    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(products.keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * products.get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    products.get(product),
                    product.getCost(),
                    subtotal));
        }
        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost
        );
    }

    /**
     * Gets the discount rate for the order
     * @return discount rate
     */
    protected double getDiscountRate() {
        return this.discountRate;
    }

    /**
     * Gets the discount threshold for the order
     * @return discount threshold
     */
    protected int getDiscountThreshold() {
        return this.discountThreshold;
    }

    /**
     * Gets the map of products and their quantities for the order
     * @return map of products and quantities.
     */
    protected Map<Product, Integer> getProducts() {
        return products;
    }

    /**
     * Gets whether the order is finalised or not
     * @return true if finalised, false otherwise
     */
    protected boolean isFinalised() {
        return finalised;
    }

    /**
     * Sets the total cost strategy for the order
     * @param strategy totalcoststrategy object
     */
    public void setTotalCostStrategy(TotalCostStrategy strategy) {
        this.totalCostStrategy = strategy;
    }
    /**
     * Sets the generate invoice data strategy for the order
     * @param strategy generateInvoiceStrategy object
     */
    public void setGenerateInvoiceDataStrategy(GenerateInvoiceDataStrategy strategy) {
        this.generateInvoiceDataStrategy = strategy;
    }
}
