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
    private final Logger logger = LoggerFactory.getLogger(PancakeController.class);
    private final PancakeFacade pancakeFacade;

    /**
    * @param pancakeFacade
    */
    @Autowired
    public PancakeController(PancakeFacade pancakeFacade) {
        this.pancakeFacade = pancakeFacade;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    protected ModelAndView order() {
        ModelAndView model = new ModelAndView();
        model.setViewName("order");
        model.addObject("pancakes", pancakeFacade.getOrderablePancakes());
        return model;
    }

    @RequestMapping(value = "/orderconfirmation", method = {RequestMethod.GET, RequestMethod.POST})
    protected ModelAndView orderConfirmation(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName("orderconfirmation");
        Map<String, String> orderedPancakes = getOrderedPancakes(request);
        model.addObject("orderedPancakes", orderedPancakes);
        if(!orderedPancakes.isEmpty()) {
            logger.info("Pancake order [{}] arrived from e-mail address [{}].", orderedPancakes, request.getParameter("eMailAddress"));
        }
        return model;
    }

    private Map<String, String> getOrderedPancakes(HttpServletRequest request) {
        Map<String, String> orderedPancakes = new HashMap<String, String>();
        for (Pancake pancake : pancakeFacade.getOrderablePancakes()) {
            String pancakeType = pancake.toString();
            String pancakeAmount = request.getParameter(pancakeType);
            if (pancakeAmount != null && pancakeAmount != "") {
                orderedPancakes.put(pancakeType, pancakeAmount);
            }
        }
        return orderedPancakes;
    }
}
