package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
//import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.Test;


public class testfile3 extends LetvTestCase{


    @Test
    @CaseName("t")
    public void test()throws UiObjectNotFoundException,RemoteException{
        launchApp(PkgName.HomeTime);
    }
}
