package io.github.pancake.filter;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.pancake.persistence.base.Pancake;

/**
 * Test class for {@link PancakeOrderFilter}.
 *
 * @author Adorjan Nagy
 */
public class PancakeOrderFilterTest {
    private PancakeOrderFilter underTest;
    private String cinnamonAmount;
    private String cocoaAmount;
    private String nutellaAmount;
    private Map<Pancake, String> nonZeroPancakeOrder;
    private Map<Pancake, String> pancakeOrderWithZeroAmountToo;

    @BeforeMethod
    public void beforeMethod() {
        underTest = new PancakeOrderFilter();
        cinnamonAmount = "5";
        cocoaAmount = "0";
        nutellaAmount = "4";
        pancakeOrderWithZeroAmountToo = getPancakeOrderWithZeroAmountToo();
        nonZeroPancakeOrder = getNonZeroPancakeOrder();
    }

    @Test
    public void testGetNonZeroPancakeOrderShouldDropPancakesWithNonOrderableAmountsWhenInvoked() {
        // GIVEN in setUp
        // WHEN
        Map<Pancake, String> result = underTest.getNonZeroPancakeOrder(pancakeOrderWithZeroAmountToo);
        // THEN
        Assert.assertEquals(result, nonZeroPancakeOrder);
    }

    private Map<Pancake, String> getPancakeOrderWithZeroAmountToo() {
        Map<Pancake, String> pancakeOrder = getNonZeroPancakeOrder();
        pancakeOrder.put(Pancake.COCOA, cocoaAmount);
        return pancakeOrder;
    }

    private Map<Pancake, String> getNonZeroPancakeOrder() {
        Map<Pancake, String> pancakeOrder = new HashMap<Pancake, String>();
        pancakeOrder.put(Pancake.CINNAMON, cinnamonAmount);
        pancakeOrder.put(Pancake.NUTELLA, nutellaAmount);
        return pancakeOrder;
    }
}
