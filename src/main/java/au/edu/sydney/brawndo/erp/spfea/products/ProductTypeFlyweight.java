package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.*;

public class ProductTypeFlyweight implements ProductFlyweight {
    /**
     * Concrete flyweight class
     * Holds extrinsic state
     */

    private double[] recipeData;
    private double[] marketingData;
    private double[] safetyData;
    private double[] licensingData;

    private static List<ProductTypeFlyweight> productTypes = new ArrayList<ProductTypeFlyweight>();


    /**
     * Constructs a new product flyweight with the given arrays of properties and keeps those values in the object.
     *
     * @param recipeData the product recipe data
     * @param marketingData the product marketing data
     * @param safetyData the product safety data
     * @param licensingData the product licensing data
     */
    private ProductTypeFlyweight(double[] recipeData,
                                 double[] marketingData,
                                 double[] safetyData,
                                 double[] licensingData)
    {
        this.licensingData = licensingData;
        this.recipeData = recipeData;
        this.marketingData = marketingData;
        this.safetyData = safetyData;
    }

    /**
     * Static class acting as a builder to retrive an already created product flyweight or create a new one if one doesn't exist.
     * @param recipeData the product recipe data
     * @param marketingData the product marketing data
     * @param safetyData the product safety data
     * @param licensingData the product licensing data
     * @return ProductTypeFlyweight object
     */

    public static ProductTypeFlyweight getProductTypeFlyweight(double[] recipeData,
                                                               double[] marketingData,
                                                               double[] safetyData,
                                                               double[] licensingData)  {
        ProductTypeFlyweight productTypeFlyweight = null;

        for (ProductTypeFlyweight search : productTypes) {
            if (ProductTypeFlyweight.isEqual(search,recipeData,marketingData, safetyData, licensingData)) {
                productTypeFlyweight = search;
                break;
            }
        }
        if (productTypeFlyweight == null) {
            productTypeFlyweight = new ProductTypeFlyweight(recipeData,marketingData, safetyData, licensingData);
            productTypes.add(productTypeFlyweight);
        }
        return productTypeFlyweight;
    }

    /**
     * Returns the recipe data for the product flyweight object
     * @return recipe data array
     */

    public double[] getRecipeData() {
        return recipeData;
    }
    /**
     * Returns the marketing data for the product flyweight object
     * @return marketing data array
     */

    public double[] getMarketingData() {
        return marketingData;
    }

    /**
     * Returns the safety data for the product flyweight object
     * @return safety data array
     */
    public double[] getSafetyData() {
        return safetyData;
    }

    /**
     * Returns the licensing data for the product flyweight object
     * @return licensing data array
     */
    public double[] getLicensingData() {
        return licensingData;
    }

    /**
     * Checks whether a product type flyweight is equal to the provided arrays
     * @param product the current product to compare against
     * @param recipeData the product recipe data
     * @param marketingData the product marketing data
     * @param safetyData the product safety data
     * @param licensingData the product licensing data
     * @return true if the product type flyweight is equal to the data, false otherwise.
     */
    public static boolean isEqual(ProductTypeFlyweight product,
                                  double[] recipeData,
                                  double[] marketingData,
                                  double[] safetyData,
                                  double[] licensingData) {
        return (Arrays.equals(product.getLicensingData(), licensingData)
            && Arrays.equals(product.getMarketingData(), recipeData)
            && Arrays.equals(product.getRecipeData(), marketingData)
            && Arrays.equals(product.getSafetyData(), safetyData)
        );
    }

}
