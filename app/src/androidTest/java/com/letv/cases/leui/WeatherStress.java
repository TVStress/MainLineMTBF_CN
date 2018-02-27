package com.letv.cases.leui;

import android.content.pm.LauncherApps;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import junit.framework.Assert;

import org.junit.Test;
import java.util.Random;
import java.util.regex.Pattern;



public class WeatherStress extends  LetvTestCase{
   int count = 0;
    @Test
    @CaseName("多次进入天气应用，查看天气界面显示")
    public void testWeatherEntry() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("....................looper : " + Loop);

            try {
                testSubWeatherEntry();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    testSubWeatherEntry();

                } catch (RuntimeException re) {
                    screenShot();
                    org.junit.Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void testSubWeatherEntry(){

        addStep("进入天气应用");
       // gotoHomeScreen("应用");
        launchApp(AppName.Weather, IntentConstants.WeatherCIBN);

        addStep("查看天气显示");
        sleepInt(1);

        UiObject2 city = phone.findObject(By.res("sina.mobile.tianqitongstv:id/current_city"));
        verify("没有当前城市的显示",city != null );


        UiObject2 tempUnit = phone.findObject(By.res("sina.mobile.tianqitongstv:id/celsius"));
        verify("没有当前温度单位的显示",tempUnit != null );

        UiObject2 today = phone.findObject(By.res("sina.mobile.tianqitongstv:id/dayInfo_date").text("今天"));
        verify("没有今天的显示",today != null );


        press_back(1);
        press_up(4);
    }

    @Test
    @CaseName("切换不同城市后，再次进入天气应用，查看天气界面显示")
    public void testWeather() throws UiObjectNotFoundException, RemoteException{

        for (int Loop = 0; Loop < getIntParams("Loop");Loop++) {

            addStep("....................looper : " + Loop);

            try {

                addStep("进入天气应用");
              //  gotoHomeScreen("应用");
                launchApp(AppName.Weather, IntentConstants.WeatherCIBN);

                //更新天气和切换城市
                String cityName =  Weather();

                addStep("退出天气应用");
                press_back(2);

                addStep("再次进入天气应用");
                launchApp(AppName.Weather, IntentConstants.WeatherCIBN);

                addStep("查看天气界面");

                UiObject2 city = phone.findObject(By.res("sina.mobile.tianqitongstv:id/current_city"));
                verify("没有当前城市的显示",city != null );

                if (!city.getText().equals(cityName))
                    throw new RuntimeException("没有显示上次切换的城市");

                UiObject2 tempUnit = phone.findObject(By.res("sina.mobile.tianqitongstv:id/celsius"));
                verify("没有当前温度单位的显示",tempUnit != null );

                UiObject2 today = phone.findObject(By.res("sina.mobile.tianqitongstv:id/dayInfo_date").text("今天"));
                verify("没有今天的显示",today != null );

                press_back(2);


            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入天气应用");
                 //   gotoHomeScreen("应用");
                    launchApp(AppName.Weather, IntentConstants.WeatherCIBN);
                    //更新天气和切换城市
                    String cityName =  Weather();

                    addStep("退出天气应用");
                    press_back(2);

                    addStep("再次进入天气应用");
                    launchApp(AppName.Weather, IntentConstants.WeatherCIBN);

                    addStep("查看天气界面");

                    UiObject2 city = phone.findObject(By.res("sina.mobile.tianqitongstv:id/current_city"));
                    verify("没有当前城市的显示",city != null );

                    if (!city.getText().equals(cityName))
                        throw new RuntimeException("没有显示上次切换的城市");

                    UiObject2 tempUnit = phone.findObject(By.res("sina.mobile.tianqitongstv:id/celsius"));
                    verify("没有当前温度单位的显示",tempUnit != null );

                    UiObject2 today = phone.findObject(By.res("sina.mobile.tianqitongstv:id/dayInfo_date").text("今天"));
                    verify("没有今天的显示",today != null );

                    press_back(2);


                } catch (RuntimeException re) {
                    screenShot();
                    org.junit.Assert.fail(re.getMessage());
                }
            }


        }
    }

    @Test
    @CaseName("应用桌面操作天气应用")
    public void testWeatherApp() throws UiObjectNotFoundException, RemoteException{

        for (int Loop = 0; Loop < getIntParams("Loop");Loop++) {

            addStep("....................looper : " + Loop);

            try {
                addStep("打开应用桌面，进入天气应用");
                gotoHomeScreen("应用");
                launchApp(AppName.Weather, IntentConstants.WeatherCIBN);

                //更新天气和切换城市
                Weather();

                addStep("退出天气应用");
                press_back(2);
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开应用桌面，进入天气应用");
                    //gotoHomeScreen("应用");
                    launchApp(AppName.Weather, IntentConstants.WeatherCIBN);

                    //更新天气和切换城市
                    Weather();

                    addStep("退出天气应用");
                    press_back(2);

                } catch (RuntimeException re) {
                    screenShot();
                    org.junit.Assert.fail(re.getMessage());
                }
            }

        }
    }
    public String Weather() throws UiObjectNotFoundException, RemoteException {
        String cityName = null;
        addStep("更新天气");
        sleepInt(1);
        press_menu(1);
        press_up(1);
        UiObject2 update = phone.findObject(By.clazz("android.widget.TextView").text(Pattern.compile("更新|刷新")));
        if (update == null) sleepInt(1);
        check("更新和城市按钮不存在", update != null);
        update.click();
        sleepInt(5);

        addStep("切换城市");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                press_menu(1);
                press_up(1);
                press_down(1);
                UiObject2 city = phone.findObject(By.clazz("android.widget.TextView").text("城市"));
                city.click();
                sleepInt(2);
                press_down(2);
                press_down(GenerateRandom(4));
                press_right(GenerateRandom(4));
                UiObject2 selectCity = phone.findObject(By.res(Pattern.compile("com.stv.weather:id/grid_city_text|sina.mobile.tianqitongstv:id/grid_city_text")).focused(true));
                cityName=selectCity.getText();
                addStep("查看"+cityName+"的天气");
                press_center(1);
                sleepInt(5);
                UiObject2 newCity=waitForObj(By.text(cityName));
                check("已选择的城市天气没有加载成功",newCity!=null);
            }
        }
        sleepInt(5);

        return cityName;
    }
}
