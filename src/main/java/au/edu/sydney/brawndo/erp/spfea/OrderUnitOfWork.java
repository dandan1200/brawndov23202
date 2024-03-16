package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderUnitOfWork {
    private List<Order> unsavedOrders = null;

    public OrderUnitOfWork(){
        this.unsavedOrders = new ArrayList<Order>();
    }
    public Order getOrder(int id) {
        for (Order o: unsavedOrders) {
            if (o.getOrderID() == id) {
                return o;
            }
        }
        return null;
    }

    public List<Order> getAll() {
        return unsavedOrders;
    }

    public void commit(AuthToken token) {
        for (Order o: unsavedOrders) {
            TestDatabase.getInstance().saveOrder(token, o);
        }
    }

    public boolean popOrder(int id) {
        for (Order o: unsavedOrders) {
            if (o.getOrderID() == id) {
                unsavedOrders.remove(o);
                return true;
            }
        }
        return false;
    }

    public void addOrder(Order o) {
        unsavedOrders.add(o);
    }

}
