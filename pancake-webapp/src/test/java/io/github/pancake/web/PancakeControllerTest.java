package io.github.pancake.web;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
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
import io.github.pancake.persistence.base.Pancake;

/**
 * Test class for {@link PancakeController}.
 *
 * @author Adorjan Nagy
 */
public class PancakeControllerTest {
    private PancakeController underTest;
    @Mock
    private PancakeFacade mockPanckakeFacade;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private Logger mockLogger;
    @Mock
    private PancakeOrderValidator mockPancakeOrderValidator;
    @Mock
    private ModelAndView mockModelAndView;
    private Map<Pancake, String> orderedPancakes;
    private List<Pancake> pancakes;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PancakeController(mockPanckakeFacade);
        orderedPancakes = new HashMap<Pancake, String>();
        pancakes = new ArrayList<Pancake>();
    }

    @Test
    public void testOrderShouldAddOrderedPancakesToModelWhenInvoked() {
        // GIVEN in setUp
        // WHEN
        underTest.order(mockModelAndView);
        // THEN
        verify(mockModelAndView, atLeastOnce()).addObject("pancakes", pancakes);
    }

    @Test
    public void testOrderConfirmationShouldAddOrderedPancakesToModelWhenInvoked() {
        // GIVEN in setUp
        Mockito.when(mockPancakeOrderValidator.getValidPancakeOrder(orderedPancakes)).thenReturn(orderedPancakes);
        // WHEN
        underTest.orderConfirmation(mockModelAndView, mockRequest, mockPancakeOrderValidator);
        // THEN
        verify(mockModelAndView, atLeastOnce()).addObject("orderedPancakes", orderedPancakes);
    }

    @Test
    public void testOrderConfirmationShouldWriteIntoLogWhenAPancakeOrderArrives() {
        // GIVEN
        underTest.setLogger(mockLogger);
        // WHEN
        underTest.orderConfirmation(mockModelAndView, mockRequest, mockPancakeOrderValidator);
        // THEN
        verify(mockLogger, atLeastOnce()).info(Mockito.eq("Pancake order [{}] arrived from e-mail address [{}]."), Mockito.any(),
                Mockito.anyString());
    }
}
