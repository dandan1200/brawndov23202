package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;

/**
 * Interface for the total cost strategy
 */
public interface TotalCostStrategy {
    /**
     * Gets the total cost for the specified order using the strategy implementation of the algorithm.
     * @param order the order object
     */
    double getTotalCost(Order order);
}
