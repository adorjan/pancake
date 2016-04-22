package io.github.pancake.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import io.github.pancake.facade.PancakeFacade;
import io.github.pancake.persistence.base.Pancake;

/**
 * Spring MVC Controller class.
 * 
 * @author Adorjan Nagy
 */
@Controller
public class PancakeController {
    private Logger logger = LoggerFactory.getLogger(PancakeController.class);
    private final PancakeFacade pancakeFacade;

    /**
    * @param pancakeFacade
    */
    @Autowired
    public PancakeController(PancakeFacade pancakeFacade) {
        this.pancakeFacade = pancakeFacade;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    protected ModelAndView order(ModelAndView model) {
        model.setViewName("order");
        model.addObject("pancakes", pancakeFacade.getOrderablePancakes());
        return model;
    }

    @RequestMapping(value = "/orderconfirmation", method = {RequestMethod.GET, RequestMethod.POST})
    protected ModelAndView orderConfirmation(ModelAndView model, HttpServletRequest request, PancakeOrderValidator pancakeOrderValidator) {
        model.setViewName("orderconfirmation");
        Map<Pancake, String> pancakeOrder = getPancakeOrder(request, pancakeOrderValidator);
        model.addObject("orderedPancakes", pancakeOrder);
        logger.info("Pancake order [{}] arrived from e-mail address [{}].", pancakeOrder, request.getParameter("eMailAddress"));
        return model;
    }

    Map<Pancake, String> getPancakeOrder(HttpServletRequest request, PancakeOrderValidator pancakeOrderValidator) {
        Map<Pancake, String> pancakeOrder = new HashMap<Pancake, String>();
        for (Pancake pancake : pancakeFacade.getOrderablePancakes()) {
            pancakeOrder.put(pancake, request.getParameter(pancake.toString()));
        }
        return pancakeOrderValidator.getValidPancakeOrder(pancakeOrder);
    }

    void setLogger(Logger logger) {
        this.logger = logger;
    }
}
