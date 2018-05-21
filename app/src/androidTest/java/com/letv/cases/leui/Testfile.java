package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
//import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.Test;


public class Testfile extends LetvTestCase {



    @Test
    public void st(){
        UiObject2 t =waitForObj(By.res(""));
        check(t !=null);
                t.click();
        sleepInt(2);
        press_right(1);
        press_center(1);
        press_menu(1);
    }

}
