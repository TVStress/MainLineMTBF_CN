package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.After;
import org.junit.Test;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created BY WYX on 2016/12/12
 */
public class DesktopStress extends LetvTestCase {

    @Test
    @CaseName("桌面管理")
    public void testDeskManager() throws UiObjectNotFoundException, RemoteException {
        /*Random r =new Random();
        int m,n,lable1Count,lable2Count,deskCount;
        for (int Loop =0;Loop <getIntParams("Loop");Loop++){
            System.out.print("...........Loop:" + Loop);
            addStep("进入桌面管理");
            gotoHomeScreen("应用");
            UiObject2 deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
            verify("桌面应用管理没有找到",deskManager != null);
            deskManager.click();
            press_center(1);
            sleepInt(1);

            UiObject2 home =waitForObj(By.res("com.stv.launcher:id/rlv_in_use"));
            verify("开机界面没有找到",home !=null);
            UiObject2 lable1 =waitForObj(By.res("com.stv.launcher:id/rlv_in_use")).getChildren().get(0);
            lable1Count = lable1.getChildCount();
            UiObject2 lable2 = waitForObj(By.res("com.stv.launcher:id/rlv_to_add")).getChildren().get(0);
            lable2Count =lable2.getChildCount();
            if (lable1Count%2 == 0){
                m = r.nextInt(lable1.getChildCount());
                m = Math.abs(r.nextInt() % lable1.getChildCount());
                press_right(m-1);
                sleepInt(5);
                press_center(1);
                press_down(1);
                press_center(1);
                sleepInt(1);
                press_back(1);
                sleepInt(20);
                deskCount = deskManager.getChildCount();
                check("增减后的应用桌面数量不符",lable1Count!=deskCount-1);
            }else {
                n =r.nextInt(lable2.getChildCount());
                n = Math.abs(r.nextInt() % lable2.getChildCount());
                press_down(1);
                press_right(n);
                press_center(1);
                press_up(1);
                sleepInt(1);
                press_center(1);
                press_back(1);
                sleepInt(20);
                deskCount =deskManager.getChildCount();
                check("增减后的应用桌面数量不符",lable1Count!=deskCount+1);
            }

        }*/
        Random r = new Random();
        int m,m1,n,homeCount;

        addStep("进入桌面管理");
        gotoHomeScreen("应用");
        UiObject2 deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
        verify("桌面应用管理没有找到",deskManager != null);
        deskManager.click();
        press_center(1);
        sleepInt(1);

        for(int  Loop = 0; Loop < getIntParams("Loop"); Loop++){

            System.out.println("........looper : "+(Loop+1));

            UiObject2 home = waitForObj(By.res("com.stv.launcher:id/rlv_in_use"));
            verify("开机界面列表没有找到",home != null);
            press_down(1);

            addStep("增加桌面"); // m是调整后的桌面数量
            UiObject2 update = waitForObj(By.res("com.stv.launcher:id/rlv_to_add")).getChildren().get(0);
            m = r.nextInt(update.getChildCount());
            m = Math.abs(r.nextInt() % update.getChildCount());
            press_right(m);
            press_center(1);
            press_up(1);

            addStep("按遥控器左右键移动桌面图标，进行桌面排序");
            n = r.nextInt(home.getChildCount());
            n = Math.abs(r.nextInt() % home.getChildCount());
            //press_center(1);


            if (Loop%2 == 0 ){
                press_right(n);
            }else{
                press_left(n);
            }

            press_center(1);
            press_back(1);
            sleepInt(20);

            addStep("进入桌面管理");
            gotoHomeScreen("应用");
            UiObject2 home1 = waitForObj(By.res("com.stv.launcher:id/tab_strip")).getChildren().get(0);
            homeCount = home1.getChildCount();
            addStep("homeCount为"+homeCount);
            addStep("m为"+m);
            check("增减后的应用桌面数量不符",homeCount != m);
            deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
            verify("桌面应用管理没有找到",deskManager != null);
            deskManager.click();
            press_center(1);
            sleepInt(1);

            addStep("减少桌面");
            press_right(4);
            press_center(1);
            press_down(1);
            press_center(1);
            UiObject2 updata1 = waitForObj(By.res("com.stv.launcher:id/rlv_to_add")).getChildren().get(0);
            m1 = updata1.getChildCount();
            press_back(1);
            sleepInt(5);

            addStep("验证桌面数量");
            homeCount =home1.getChildCount();
            addStep("homeCount为"+homeCount);
            addStep("m为"+m1);
            check("增减后的应用桌面数量不符",homeCount != m);

            deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
            verify("桌面应用管理没有找到",deskManager != null);
            deskManager.click();
            press_center(1);
            sleepInt(1);

        }
    }

    @Test
    @CaseName("桌面切换")
    public void testDeskSwitch() throws UiObjectNotFoundException, RemoteException {

        Random r = new Random();
        int k= 0 ;
        addStep("焦点在tab栏应用上");
        gotoHomeScreen("应用");
        press_back(2);
        //检查焦点在tab栏上
        /*UiObject2 App = waitForObj(By.res("com.stv.launcher:id/launcher_tab_item_title").text(Pattern.compile("App|APP|应用")));
        if(!App.isSelected())
        {
            press_up(10);
            for (int i = 0; i < 5; i++)
            {
                press_down(1);
                App = waitForObj(By.res("com.stv.launcher:id/launcher_tab_item_title").text(Pattern.compile("App|APP|应用")));
                if( App != null &&  App.isSelected()  )   break;
            }
        }
        verify("App not selected",App.isSelected());*/
        //开始测试
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {

            System.out.println(".............looper : " + Loop);

            int nLeft = r.nextInt(10);
            nLeft = Math.abs(r.nextInt() % 10);
            System.out.println("Random data : " + nLeft);

            addStep("按遥控器左键切换桌面" + nLeft + "次");
            press_left(nLeft);

            k = r.nextInt(10);
            k = Math.abs(r.nextInt() % 10);
            System.out.println("Random data : " + k);
            press_down(k);
            press_left(nLeft);
            //press_up(nLeft);
            press_right(k);
            press_back(2);

            int nRight = r.nextInt(10);
            nRight = Math.abs(r.nextInt() % 10);
            System.out.println("Random data : " + nRight);
            addStep("按遥控器右键切换桌面" + nRight + "次");
            press_right(nRight);

            k = r.nextInt(10);
            k = Math.abs(r.nextInt() % 10);
            System.out.println("Random data : " + k);
            press_down(k);
            press_left(nRight);
            //press_up(nRight);
            press_right(k);
         }
    }
    @Test
    @CaseName("应用桌面海报切换")
        public void testAppThumbnail() throws UiObjectNotFoundException, RemoteException {

        addStep("焦点在应用桌面海报上");
        gotoHomeScreen("应用");
        press_back(2);
        //检查焦点在"应用上"
        /*UiObject2 App = waitForObj(By.res("com.stv.launcher:id/launcher_tab_item_title").text(Pattern.compile("App|APP|应用")));
        if(!App.isSelected())
        {
            press_up(10);
            for (int i = 0; i < 5; i++)
            {
                press_down(1);
                App = waitForObj(By.res("com.stv.launcher:id/launcher_tab_item_title").text(Pattern.compile("App|APP|应用")));
                if( App != null &&  App.isSelected()  )   break;
            }
        }
        verify("App not selected",App.isSelected());*/

        //开始测试
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {

            System.out.println(".............looper : " + Loop);

            /*int nLeft =GenerateRandom(10);
            int nRight =GenerateRandom(10);
            int nUp =GenerateRandom(10);
            int nDown =GenerateRandom(10);

            press_down(nDown);
            press_left(nLeft);
            press_up(nUp);
            press_right(nDown);
            press_center(1);
            sleepInt(20);
            press_back(1);*/
        }

    }
    @Test
    @CaseName("游戏桌面切换海报")
    public void testSwitchGamePoster()throws  UiObjectNotFoundException, RemoteException {
        addStep("确认没有已安装的游戏");
        gotoHomeScreen("应用");
        sleepInt(1);
        press_back(2);//进入游戏大厅
        press_down(1);
        press_right(1);
        press_center(1);
        sleepInt(10);//进入游戏大厅
        press_right(8);//全部卸载
        press_down(1);
        press_left(1);
        press_down(2);
        press_right(1);
        press_center(1);//全部卸载
        press_down(1);
        press_center(1);
        sleepInt(2);
        exitApp();//退出游戏大厅
        for (int Loop=0;Loop <getIntParams("Loop");Loop++){

            System.out.println(".............looper : " + Loop);
        gotoHomeScreen("游戏");
        sleepInt(1);
        press_back(2);
        addStep("将焦点移动至任意海报");
        press_down(1);
        press_right(1);
        press_down(3);
        press_center(1);
        sleepInt(3);
        press_back(1);

        press_right(GenerateRandom(2)+1);
        press_down(1);
        press_center(1);
        sleepInt(3);
        press_back(1);
        sleepInt(1);

        press_down(1);
        press_center(1);
        sleepInt(3);
        press_back(1);
        sleepInt(1);

        press_down(1);
        press_center(1);
        sleepInt(3);
        press_back(1);
        sleepInt(1);
        }

    }

    @Test
    @CaseName("同步剧场切换海报")
    public void testSwitchTheaterPoster()throws  UiObjectNotFoundException, RemoteException {
        for(int Loop = 0;Loop <getIntParams("Loop");Loop++){
            System.out.println(".............looper : " + Loop);
            for(int j=0;j<5;j++){
                addStep("进入同步剧场桌面");
                gotoHomeScreen("同步剧场");
                Random r = new Random();
                int a = r.nextInt(4)+2;
                press_down(a);
                press_right(GenerateRandom(3));
                press_center(1);
                sleepInt(5);
                exitApp();
                sleepInt(2);
                press_back(2);
            }

        }

    }

    @Test
    @CaseName("儿童桌面切换海报")
    public void testSwitchChildrenPoster()throws  UiObjectNotFoundException, RemoteException {
        for(int Loop = 0;Loop <getIntParams("Loop");Loop++){
            System.out.println(".............looper : " + Loop);
            for(int j=0;j<5;j++){
                addStep("进入儿童桌面");
                gotoHomeScreen("儿童");
                Random r = new Random();
                int a = r.nextInt(10)+2;
                press_down(a);
                press_right(GenerateRandom(3));
                press_center(1);
                sleepInt(5);
                exitApp();
                sleepInt(2);
                press_back(2);
            }

        }

    }

    @Test
    @CaseName("购物桌面切换海报")
    public void testSwitchShoppingPoster()throws  UiObjectNotFoundException, RemoteException {
        for(int Loop = 0;Loop <getIntParams("Loop");Loop++){
            System.out.println(".............looper : " + Loop);
            for(int j=0;j<5;j++){
                addStep("进入购物桌面");
                gotoHomeScreen("购物");
                Random r = new Random();
                int a = r.nextInt(10)+5;
                press_down(a);
                press_right(GenerateRandom(3));
                press_center(1);
                sleepInt(5);
                press_back(1);
                //exitApp();
                sleepInt(2);
                press_back(2);
            }

        }

    }

    @Test
    @CaseName("体育桌面切换海报")
    public void testSwitchSportsPoster()throws  UiObjectNotFoundException, RemoteException {
        for(int Loop = 0;Loop <getIntParams("Loop");Loop++){
            System.out.println(".............looper : " + Loop);
            for(int j=0;j<5;j++){
                addStep("进入体育桌面");
                gotoHomeScreen("体育");
                Random r = new Random();
                int a = r.nextInt(10)+5;
                press_down(a);
                press_right(GenerateRandom(3));
                press_center(1);
                sleepInt(5);
               //press_back(1);
                exitApp();
                sleepInt(2);
                press_back(2);
            }

        }

    }

    @Test
    @CaseName("搜索桌面切换海报")
    public void testSwitchSearchPoster()throws  UiObjectNotFoundException, RemoteException {
        for(int Loop = 0;Loop <getIntParams("Loop");Loop++){
            System.out.println(".............looper : " + Loop);
            for(int j=0;j<5;j++){
                addStep("进入搜索桌面");
                gotoHomeScreen("搜索");
                Random r = new Random();
                int a = r.nextInt(10)+4;
                press_down(a);
                press_right(GenerateRandom(3));
                press_center(1);
                sleepInt(5);
                //press_back(1);
                exitApp();
                sleepInt(2);
                press_back(2);
            }

        }

    }

    @Test
    @CaseName("乐见桌面切换海报")
    public void testSwitchLeViewPoster()throws  UiObjectNotFoundException, RemoteException {
        for(int Loop = 0;Loop <getIntParams("Loop");Loop++){
            System.out.println(".............looper : " + Loop);
            for(int j=0;j<5;j++){
                addStep("进入乐见桌面");
                gotoHomeScreen("乐见");
                Random r = new Random();
                int a = r.nextInt(10)+3;
                press_down(a);
                press_right(GenerateRandom(2));
                press_center(1);
                sleepInt(5);
                //press_back(1);
                exitApp();
                sleepInt(2);
                press_back(2);
            }

        }

    }

    @Test
    @CaseName("应用桌面无序进入应用")
    public void testRandomLaunchAPP()throws UiObjectNotFoundException, RemoteException {
        addStep("进入应用桌面");
        gotoHomeScreen("应用");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {

            System.out.println(".............looper : " + Loop);
            for(int j=0;j<5;j++){
                Random r = new Random();
                int a =r.nextInt(2)+2;
                press_down(a);
                press_right(GenerateRandom(4));
                press_center(1);
                sleepInt(5);
                press_back(2);
                sleepInt(1);
                UiObject2 App = waitForObj(By.res("com.stv.launcher:id/launcher_tab_item_title").text("应用"));
                if(App ==null){
                    exitApp();
                    press_back(2);
                }else {
                    break;
                }

            }

            /*int nLeft =GenerateRandom(10);
            int nRight =GenerateRandom(10);
            int nUp =GenerateRandom(10);
            int nDown =GenerateRandom(10);

            press_down(nDown);
            press_left(nLeft);
            press_up(nUp);
            press_right(nDown);
            press_center(1);
            sleepInt(20);
            press_back(1);*/
        }




    }

    //扁鹊性能测试脚本
    //扁鹊性能测试脚本
    //扁鹊性能测试脚本
    //扁鹊性能测试脚本
    @Test
    @CaseName("扁鹊性能测试脚本")
    public void testSwitchDesktop()throws  UiObjectNotFoundException, RemoteException {

        gotoHomeScreen("应用|App|app");
        UiObject2 deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
        //   verify("桌面应用管理没有找到",deskManager != null);
        deskManager.click();
        deskManager.click();
        press_right(1);
        press_up(1);
        press_back(1);
        press_left(1);
        press_home(1);

        for (int j = 0; j < 20 * getIntParams("Loop"); j++) {
            int i = 0;
            press_home(1);
            while (i < GenerateRandom(10)) {
                press_right(1);
                sleepInt(1);
                i++;
            }
            while (i < GenerateRandom(10)) {
                press_left(1);
                i++;
            }
            while (i < GenerateRandom(10)) {
                press_right(1);
                i++;
            }
            while (i < GenerateRandom(10)) {
                press_left(1);
                sleepInt(1);
                i++;
            }
        }

    }

    @Test
    @CaseName("扁鹊性能测试脚本")
    public void testAPPtest()throws  UiObjectNotFoundException, RemoteException {
        press_right(2);
        gotoHomeScreen("应用|App|app");
        UiObject2 deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt").text("桌面管理"));
        //   verify("桌面应用管理没有找到",deskManager != null);
        deskManager.click();
        deskManager.click();
        press_right(1);
        press_up(1);
        press_back(1);
        press_left(1);
        press_home(1);
        gotoHomeScreen("应用|App|app");
        for (int j = 0; j < 20 * getIntParams("Loop"); j++) {
            press_down(1);
            press_down(GenerateRandom(5));
            press_center(1);
            press_back(1);
            press_right(GenerateRandom(5));
            press_center(1);
            press_back(1);
            press_up(GenerateRandom(5));
            press_center(1);
            press_back(1);
            press_left(GenerateRandom(5));
            press_center(1);
            press_back(1);
        }
        gotoHomeScreen("应用|App|app");
        for (int j = 0; j < 20 * getIntParams("Loop"); j++) {
            press_down(1);
            press_center(1);
            press_back(3);
            press_center(2);
            press_back(3);
            press_right(1);
            press_center(2);
            press_back(3);
            press_center(2);
            press_back(3);
            press_right(1);
            press_center(1);
            press_back(3);
            press_center(2);
            press_back(3);
        }
        gotoHomeScreen("应用|App|app");
        for (int j = 0; j < 20 * getIntParams("Loop"); j++) {
            press_down(1);
            press_right(1);
            press_center(1);
            press_back(3);
            press_center(2);
            press_back(3);
            press_right(1);
            press_center(2);
            press_back(3);
            press_center(2);
            press_back(3);

        }
        gotoHomeScreen("应用|App|app");
        for (int j = 0; j < 20 * getIntParams("Loop"); j++) {
            press_down(1);
            press_right(2);
            press_center(1);
            press_back(3);

        }
    }
}


