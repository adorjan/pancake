package io.github.pancake.web;

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
import io.github.pancake.facade.PancakeOrderFacade;
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
    private final PancakeOrderFacade pancakeOrderFacade;

    /**
     * @param pancakeFacade
     * @param pancakeOrderFacade
     */
    @Autowired
    public PancakeController(PancakeFacade pancakeFacade, PancakeOrderFacade pancakeOrderFacade) {
        this.pancakeFacade = pancakeFacade;
        this.pancakeOrderFacade = pancakeOrderFacade;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    protected ModelAndView order(ModelAndView model) {
        model.setViewName("order");
        model.addObject("pancakes", pancakeFacade.getOrderablePancakes());
        return model;
    }

    @RequestMapping(value = "/orderconfirmation", method = {RequestMethod.GET, RequestMethod.POST})
    protected ModelAndView orderConfirmation(ModelAndView model, HttpServletRequest request) {
        model.setViewName("orderconfirmation");
        Map<Pancake, String> orderedPancakes = pancakeOrderFacade.getNonZeroPancakeOrder(request);
        model.addObject("orderedPancakes", orderedPancakes);
        logger.info("Pancake order [{}] arrived from e-mail address [{}].", orderedPancakes, request.getParameter("eMailAddress"));
        return model;
    }

    void setLogger(Logger logger) {
        this.logger = logger;
    }
}
