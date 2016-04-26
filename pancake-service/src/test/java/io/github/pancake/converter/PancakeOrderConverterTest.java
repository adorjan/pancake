package io.github.pancake.converter;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.pancake.facade.PancakeFacade;
import io.github.pancake.filter.PancakeOrderFilter;
import io.github.pancake.persistence.base.Pancake;

/**
 * Test class for {@link PancakeOrderConverter}.
 *
 * @author Adorjan Nagy
 */
public class PancakeOrderConverterTest {
    private String cinnamonAmount;
    @Mock
    private PancakeFacade mockPancakeFacade;
    @Spy
    private PancakeOrderFilter spyPancakeOrderFilter;
    @Mock
    private HttpServletRequest mockRequest;
    private Map<Pancake, String> nonZeroPancakeOrder;
    private String nutellaAmount;
    private List<Pancake> pancakes;
    private PancakeOrderConverter underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PancakeOrderConverter(mockPancakeFacade, spyPancakeOrderFilter);
        cinnamonAmount = "5";
        nutellaAmount = "4";
        pancakes = getPancakes();
        nonZeroPancakeOrder = getNonZeroPancakeOrder();
    }

    @Test
    public void testGetNonZeroPancakeOrderShouldReturnConvertedNonZeroPancakeOrderFromRequestWhenInvoked() {
        // GIVEN
        given(mockPancakeFacade.getOrderablePancakes()).willReturn(pancakes);
        given(mockRequest.getParameter(Pancake.CINNAMON.toString())).willReturn(cinnamonAmount);
        given(mockRequest.getParameter(Pancake.NUTELLA.toString())).willReturn(nutellaAmount);
        // WHEN
        Map<Pancake, String> result = underTest.getNonZeroPancakeOrder(mockRequest);
        // THEN
        Assert.assertEquals(result, nonZeroPancakeOrder);
    }

    private List<Pancake> getPancakes() {
        List<Pancake> pancakes = new ArrayList<Pancake>();
        pancakes.add(Pancake.CINNAMON);
        pancakes.add(Pancake.NUTELLA);
        return pancakes;
    }

    private Map<Pancake, String> getNonZeroPancakeOrder() {
        Map<Pancake, String> nonZeroPancakeOrder = new HashMap<Pancake, String>();
        nonZeroPancakeOrder.put(Pancake.CINNAMON, cinnamonAmount);
        nonZeroPancakeOrder.put(Pancake.NUTELLA, nutellaAmount);
        return nonZeroPancakeOrder;
    }
}
