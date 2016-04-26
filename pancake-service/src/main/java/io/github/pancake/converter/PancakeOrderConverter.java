package io.github.pancake.converter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.pancake.facade.PancakeFacade;
import io.github.pancake.filter.PancakeOrderFilter;
import io.github.pancake.persistence.base.Pancake;

/**
 * Gathers the pancake order from request and converts to a Map<Pancake, String>.
 * By applying PancakeOrderFilter the converted pancake order doesn't contain pancakes with non-orderable amounts.
 * 
 * @author Adorjan Nagy
 */
@Component
public class PancakeOrderConverter {
    private final PancakeFacade pancakeFacade;
    private final PancakeOrderFilter pancakeOrderFilter;

    /**
     * @param pancakeFacade
     * @param pancakeOrderFilter
     */
    @Autowired
    public PancakeOrderConverter(PancakeFacade pancakeFacade, PancakeOrderFilter pancakeOrderFilter) {
        this.pancakeFacade = pancakeFacade;
        this.pancakeOrderFilter = pancakeOrderFilter;
    }

    public Map<Pancake, String> getNonZeroPancakeOrder(HttpServletRequest request) {
        return pancakeOrderFilter.getNonZeroPancakeOrder(getPancakeOrder(request));
    }

    private Map<Pancake, String> getPancakeOrder(HttpServletRequest request) {
        Map<Pancake, String> pancakeOrder = new HashMap<Pancake, String>();
        for (Pancake pancake : pancakeFacade.getOrderablePancakes()) {
            pancakeOrder.put(pancake, request.getParameter(pancake.toString()));
        }
        return pancakeOrder;
    }
}
