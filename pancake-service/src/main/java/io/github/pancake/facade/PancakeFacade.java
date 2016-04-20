package io.github.pancake.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.pancake.factory.PancakeFactory;
import io.github.pancake.persistence.base.Pancake;

/**
 * PancakeFactory bean provider class.
 * 
 * @author Adorjan Nagy
 */
@Service
public class PancakeFacade {
    private final PancakeFactory pancakeFactory;

    /**
     * @param pancakeFactory
     */
    @Autowired
    public PancakeFacade(PancakeFactory pancakeFactory) {
        this.pancakeFactory = pancakeFactory;
    }

    public List<Pancake> getOrderablePancakes() {
        return pancakeFactory.getObject();
    }
}
