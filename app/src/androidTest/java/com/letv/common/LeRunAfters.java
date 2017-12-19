package com.letv.common;

import org.junit.internal.runners.statements.RunAfters;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by letv on 16-8-15.
 */
public class LeRunAfters extends RunAfters{

    private static final String TAG = "TestRunner";

    private final Statement next;

    private final Object target;

    private final List<FrameworkMethod> afters;

    public LeRunAfters(Statement next, List<FrameworkMethod> afters, Object target) {
        super(next,afters,target);
        this.next = next;
        this.afters = afters;
        this.target = target;
    }

    /**
     * add screenshot in catch, so we will get screenshot every time case failed
     */
    @Override
    public void evaluate() throws Throwable {
        List<Throwable> errors = new ArrayList<Throwable>();
        try {
            next.evaluate();
        } catch (Throwable e) {
            LetvTestCase.screenShot();
            errors.add(e);
        } finally {
            for (FrameworkMethod each : afters) {
                try {
                    each.invokeExplosively(target);
                } catch (Throwable e) {
                    errors.add(e);
                }
            }
        }
        MultipleFailureException.assertEmpty(errors);
    }
}
