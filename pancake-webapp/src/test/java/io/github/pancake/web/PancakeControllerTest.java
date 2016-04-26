package io.github.pancake.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.pancake.facade.PancakeFacade;
import io.github.pancake.facade.PancakeOrderFacade;
import io.github.pancake.persistence.base.Pancake;

/**
 * Test class for {@link PancakeController}.
 *
 * @author Adorjan Nagy
 */
public class PancakeControllerTest {
    private String cinnamonAmount;
    @Mock
    private Logger mockLogger;
    @Mock
    private ModelAndView mockModelAndView;
    @Mock
    private PancakeFacade mockPancakeFacade;
    @Mock
    private PancakeOrderFacade mockPancakeOrderFacade;
    @Mock
    private HttpServletRequest mockRequest;
    private ModelAndView model;
    private String nutellaAmount;
    private Map<Pancake, String> orderedPancakes;
    private String orderedPancakesKey;
    private List<Pancake> pancakes;
    private String pancakesKey;
    private PancakeController underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PancakeController(mockPancakeFacade, mockPancakeOrderFacade);
        model = new ModelAndView();
        cinnamonAmount = "5";
        nutellaAmount = "4";
        orderedPancakes = getNonZeroPancakeOrder();
        orderedPancakesKey = "orderedPancakes";
        pancakes = getPancakes();
        pancakesKey = "pancakes";
    }

    @Test
    public void testOrderShouldAddOrderablePancakesToModelWhenInvoked() {
        // GIVEN
        given(mockPancakeFacade.getOrderablePancakes()).willReturn(pancakes);
        // WHEN
        ModelAndView result = underTest.order(model);
        // THEN
        assertEquals(result.getModel().get(pancakesKey), pancakes);
    }

    @Test
    public void testOrderConfirmationShouldAddNonZeroOrderedPancakesToModelWhenInvoked() {
        // GIVEN
        given(mockPancakeOrderFacade.getNonZeroPancakeOrder(mockRequest)).willReturn(orderedPancakes);
        // WHEN
        ModelAndView result = underTest.orderConfirmation(model, mockRequest);
        // THEN
        assertEquals(result.getModel().get(orderedPancakesKey), orderedPancakes);
    }

    @Test
    public void testOrderConfirmationShouldWriteIntoLogWhenAPancakeOrderArrives() {
        // GIVEN
        underTest.setLogger(mockLogger);
        // WHEN
        underTest.orderConfirmation(mockModelAndView, mockRequest);
        // THEN
        verify(mockLogger).info(Mockito.eq("Pancake order [{}] arrived from e-mail address [{}]."), Mockito.any(), Mockito.anyString());
    }

    private List<Pancake> getPancakes() {
        List<Pancake> pancakes = new ArrayList<Pancake>();
        pancakes.add(Pancake.CINNAMON);
        pancakes.add(Pancake.NUTELLA);
        return pancakes;
    }

    private Map<Pancake, String> getNonZeroPancakeOrder() {
        Map<Pancake, String> pancakeOrder = new HashMap<Pancake, String>();
        pancakeOrder.put(Pancake.CINNAMON, cinnamonAmount);
        pancakeOrder.put(Pancake.NUTELLA, nutellaAmount);
        return pancakeOrder;
    }
}
