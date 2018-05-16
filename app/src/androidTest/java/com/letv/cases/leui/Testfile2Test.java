package com.letv.cases.leui;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pc7 on 5/9/18.
 */
public class Testfile2Test extends Testfile2 {

    private  Testfile2 mTestfile;


    @Before
    public void setUp()throws Exception{
        mTestfile = new Testfile2();
    }



    @Test
    public void sum() throws Exception {
//        assertEquals(6d,mTestfile.sum(1,5.8),0);
        double t =mTestfile.sum(6.5,6.2);
        System.out.println("hello world"+"\t" + t);
    }

}