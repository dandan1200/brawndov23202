package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

public class TotalCostWithThresholdStrategy implements TotalCostStrategy {

    @Override
    public double getTotalCost(Order order) {
        if (order instanceof OrderImpl) {
            OrderImpl orderImpl = (OrderImpl) order;
            double cost = 0.0;
            for (Product product: orderImpl.getProducts().keySet()) {
                int count = orderImpl.getProducts().get(product);
                if (count >= orderImpl.getDiscountThreshold()) {
                    cost +=  count * product.getCost() * orderImpl.getDiscountRate();
                } else {
                    cost +=  count * product.getCost();
                }
            }
            return cost;
        }
        else {
            return 0.0;
        }
    }
}

