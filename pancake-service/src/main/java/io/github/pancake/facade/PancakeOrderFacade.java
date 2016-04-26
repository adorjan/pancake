package io.github.pancake.facade;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.pancake.converter.PancakeOrderConverter;
import io.github.pancake.persistence.base.Pancake;

/**
 * From the request this service class provides the pancake order.
 * The provided pancake order doesn't contain pancakes with non-orderable amounts.
 * 
 * @author Adorjan Nagy
 */
@Service
public class PancakeOrderFacade {
    private final PancakeOrderConverter pancakeOrderConverter;

    /**
     * @param pancakeOrderConverter
     */
    @Autowired
    public PancakeOrderFacade(PancakeOrderConverter pancakeOrderConverter) {
        this.pancakeOrderConverter = pancakeOrderConverter;
    }

    public Map<Pancake, String> getNonZeroPancakeOrder(HttpServletRequest request) {
        return pancakeOrderConverter.getNonZeroPancakeOrder(request);
    }
}
