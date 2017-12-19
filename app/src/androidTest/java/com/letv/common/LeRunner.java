package com.letv.common;

import org.junit.After;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.List;

/**
 * Created by letv on 16-8-15.
 */
public class LeRunner extends BlockJUnit4ClassRunner {

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public LeRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    /**
     * here I invoke my custom class: LeRunAfters
     */
    @Override
    protected Statement withAfters(FrameworkMethod method, Object target, Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(
                After.class);
        return afters.isEmpty() ? statement : new LeRunAfters(statement, afters,
                target);
    }
}
