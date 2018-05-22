package com.letv.cases.leui;


import junit.framework.Assert;

import org.junit.Test;

/**
 * 坚其志，苦其心，劳其力，事无大小，必有所成。
 * Created by pc7 on 5/16/18.
 */

class le {

    public void verify(String msg, boolean b) {
        if (msg.equals("")) {
            verify(b);
        } else if (!b) {
//            screenShot();
            Assert.fail(msg);
        }
    }


    public void verify(boolean b) {
        if (!b) {
//            screenShot();
            Assert.fail();
        }
    }
}
public class testfile4 {

    public le lt=new le();

    @Test
    public void test5(){
        lt.verify(2==4);

    }

    @Test
    public void test6(){
        lt.verify("a=b", 1==6);
    }


}
