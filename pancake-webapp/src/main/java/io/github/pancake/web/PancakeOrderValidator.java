package io.github.pancake.web;

import java.util.HashMap;
import java.util.Map;

import io.github.pancake.persistence.base.Pancake;

/**
 * Utility class which validates the pancake order.
 * 
 * @author Adorjan Nagy
 */
public class PancakeOrderValidator {
    private PancakeOrderValidator() {
        super();
    }

    public Map<Pancake, String> getValidPancakeOrder(Map<Pancake, String> pancakesWithAmounts) {
        Map<Pancake, String> pancakesWithNonZeroAmounts = new HashMap<Pancake, String>();
        for (Pancake pancake : pancakesWithAmounts.keySet()) {
            String pancakeAmount = pancakesWithAmounts.get(pancake);
            if (pancakeAmount != null && !"".equals(pancakeAmount) && !"0".equals(pancakeAmount)) {
                pancakesWithNonZeroAmounts.put(pancake, pancakeAmount);
            }
        }
        return pancakesWithNonZeroAmounts;
    }
}
