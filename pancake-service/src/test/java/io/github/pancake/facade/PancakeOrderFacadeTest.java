package io.github.pancake.facade;

import static org.mockito.BDDMockito.given;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.pancake.converter.PancakeOrderConverter;
import io.github.pancake.persistence.base.Pancake;

/**
 * Test class for {@link PancakeOrderFacade}.
 *
 * @author Adorjan Nagy
 */
public class PancakeOrderFacadeTest {
    private String cinnamonAmount;
    @Mock
    private PancakeOrderConverter mockPancakeOrderConverter;
    @Mock
    private HttpServletRequest mockRequest;
    private Map<Pancake, String> nonZeroPancakeOrder;
    private String nutellaAmount;
    private PancakeOrderFacade underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PancakeOrderFacade(mockPancakeOrderConverter);
        cinnamonAmount = "5";
        nutellaAmount = "4";
        nonZeroPancakeOrder = getNonZeroPancakeOrder();
    }

    @Test
    public void testGetNonZeroPancakeOrderShouldReturnNonZeroPancakeOrderWhenInvoked() {
        // GIVEN
        given(mockPancakeOrderConverter.getNonZeroPancakeOrder(mockRequest)).willReturn(nonZeroPancakeOrder);
        // WHEN
        Map<Pancake, String> result = underTest.getNonZeroPancakeOrder(mockRequest);
        // THEN
        assertEquals(result, nonZeroPancakeOrder);
    }

    private Map<Pancake, String> getNonZeroPancakeOrder() {
        Map<Pancake, String> pancakeOrder = new HashMap<Pancake, String>();
        pancakeOrder.put(Pancake.CINNAMON, cinnamonAmount);
        pancakeOrder.put(Pancake.NUTELLA, nutellaAmount);
        return pancakeOrder;
    }
}
