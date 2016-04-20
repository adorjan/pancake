package io.github.pancake.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import io.github.pancake.facade.PancakeFacade;

/**
 * Spring MVC Controller class.
 * 
 * @author Adorjan Nagy
 */
@Controller
public class PancakeController {
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
}
