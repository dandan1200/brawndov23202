package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Objects;

public class ProductImpl implements Product {
    /**
     * Unshared concrete flyweight
     * To hold intrinsic state
     */

    private final String name;
    private final double[] manufacturingData;
    private final double cost;

    private ProductTypeFlyweight productType;

    /**
     * Constructs a new product object and assigns the product type flyweight from the get flyweight static method
     * @param name the name of the product
     * @param cost the cost of the product
     * @param manufacturingData the product manufacturing data
     * @param recipeData the product recipe data
     * @param marketingData the product marketing data
     * @param safetyData the product safety data
     * @param licensingData the product licensing data
     */
    public ProductImpl(String name,
                       double cost,
                       double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        this.name = name;
        this.cost = cost;
        this.manufacturingData = manufacturingData;

        this.productType = ProductTypeFlyweight.getProductTypeFlyweight(recipeData,marketingData, safetyData, licensingData);
    }

    @Override
    public String getProductName() {
        return name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double[] getManufacturingData() {
        return manufacturingData;
    }

    @Override
    public double[] getRecipeData() {
        return this.productType.getRecipeData();
    }

    @Override
    public double[] getMarketingData() {
        return this.productType.getMarketingData();
    }

    @Override
    public double[] getSafetyData() {
        return this.productType.getSafetyData();
    }

    @Override
    public double[] getLicensingData() {
        return this.productType.getLicensingData();
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }

    @Override
    public boolean equals(Object product) {
        if (product == this) {
            return true;
        } else if (!(product instanceof Product)) {
            return false;
        }

        Product otherProduct = (Product) product;

        return this.hashCode() == otherProduct.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.cost, this.manufacturingData, this.productType);
    }
}
