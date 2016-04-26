package io.github.pancake.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.github.pancake.persistence.base.Pancake;

/**
 * Utility class which filters the pancake order.
 * Pancakes with non-orderable amounts are being dropped.
 * 
 * @author Adorjan Nagy
 */
@Component
public class PancakeOrderFilter {
    private static final String ZERO = "0";

    public PancakeOrderFilter() {
    }

    public Map<Pancake, String> getNonZeroPancakeOrder(Map<Pancake, String> pancakesWithAmounts) {
        Map<Pancake, String> pancakesWithNonZeroAmounts = new HashMap<Pancake, String>();
        for (Pancake pancake : pancakesWithAmounts.keySet()) {
            String pancakeAmount = pancakesWithAmounts.get(pancake);
            if (isOrderableAmount(pancakeAmount)) {
                pancakesWithNonZeroAmounts.put(pancake, pancakeAmount);
            }
        }
        return pancakesWithNonZeroAmounts;
    }

    private boolean isOrderableAmount(String amount) {
        return !(StringUtils.isEmpty(amount) || ZERO.equals(amount));
    }
}
