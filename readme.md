# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

Flyweight pattern

- Flyweight interface: ProductFlyweight
- Concrete flyweight : ProductTypeFlyweight
- Client : ProductImpl

### Too Many Orders

Strategy pattern
2 Strategies used.

GenerateInvoiceData

Participants:

- Strategy Interface : GenerateInvoiceDataStrategy
- Concrete Strategies : GenerateInvoiceDataLongStrategy, GenerateInvoiceDataLongSubStrategy, GenerateInvoiceDataShortStrategy, GenerateInvoiceDataShortSubStrategy
- Context : OrderImpl

TotalCostStrategy

Participants:

- Strategy Interface : TotalCostStrategy
- Concrete Strategies : TotalCostStrategyWithThreshold, TotalCostStrategyWithoutThreshold
- Context : OrderImpl

#### Alternative Solution (400 words max)

##### Solution Summary

In this solution, I implemented two strategies for the two algorithm methods that were differentiated by order type. These were the generateInvoiceData method and the total cost method.
For the generateInvoiceData method, there were 4 different algorithms implemented across the order types. Two were long invoice data two were short. For each long and short method there was a version of the algorithm for subscription orders and a version for regular orders.
I created a strategy class for each of these algorithms and added a GenerateInvoiceDataStrategy object to the order class.
When the generateInvoiceData method is called in the order object, the strategy method is invoked and the values returned back to the client.

Similarly, there were two different totalCost algorithms implemented across the order types.
These were, totalCostWithThreshold and totalCostWithoutThreshold. I made a strategy class for each.
Each order object similarly held a totalCostStrategy object and when the totalCost method was called the algorithm in the strategy was invoked.

##### Solution Benefit

How did you solution solve the problem, be brief.
This solution solves the issue of having too many order types. With this method there are only two order type classes, a general OrderImpl type and a SubscriptionOrderImpl which is a sub type.
With these classes we can hold any combination of the required algorithms and implement as many order types as we like. Additionally, if there were more order types to implement with new algorithms you will only need to create an additional strategy class with the required algorith which can then be added easily to an order object.
This method is highly scalable, expandable and maintainable due to the simplicity of the new classes that would need to be created in future.
Furthermore, there is no need to amend any of the order objects to implement new algorithms, you will only need to include the new strategy class in the client SPFEAFacade when creating new orders.


### Bulky Contact Method

Chain of responsibility pattern

Participants:
Handler: ContactHandler
ConcreteHandler: all classes in the concreteHandler folder.
Client: ContactHandlerChain

### System Lag
Lazy load pattern
Lazy load pattern is implemented in the CustomerImpl class.
The Client is the SPFEAFacade
The Loader is the CustomerImpl class
The Subject is the TestDatabase class

### Hard to Compare Products
Value object pattern
Participants:
Value object : ProductImpl
Client : BusinessBulkDiscountOrder.java, FirstOrder.java, NewOrderImpl.java, Order66.java

### Slow Order Creation
Unit of work pattern

Participants:
Unit of work : OrderUnitOfWork
This class stores a list of unsaved orders after creation and allows adding and removing from this list. It also allows the program to get the list to add to the database list of orders for 'getAllOrders'
When the user logs out, the commit method is called and all unsaved orders are added to the database.