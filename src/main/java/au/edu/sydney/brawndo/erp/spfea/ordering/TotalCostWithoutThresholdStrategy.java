package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

public class TotalCostWithoutThresholdStrategy implements TotalCostStrategy {

    @Override
    public double getTotalCost(Order order) {
        if (order instanceof OrderImpl) {
            OrderImpl orderImpl = (OrderImpl) order;
            double cost = 0.0;
            for (Product product: orderImpl.getProducts().keySet()) {
                cost +=  orderImpl.getProducts().get(product) * product.getCost() * orderImpl.getDiscountRate();
            }
            return cost;
        }
        else {
            return 0.0;
        }
    }
}

