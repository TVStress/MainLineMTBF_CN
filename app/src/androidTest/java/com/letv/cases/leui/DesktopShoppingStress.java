package com.letv.cases.leui;
import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import junit.framework.Assert;
import org.junit.Test;
import java.util.regex.Pattern;


public class DesktopShoppingStress extends LetvTestCase{
    int count;



    @Test
    @CaseName("进入购物桌面")
    public void testLeShop()throws UiObjectNotFoundException,RemoteException {
        gotoHomeScreen("购物");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            try {
                LeShop();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("购物");
                    LeShop();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }

        }
    }
    public void LeShop()throws UiObjectNotFoundException,RemoteException{
        press_back(2);
        addStep("进入应用购物来回滑动");
        press_down(15);
        UiObject2 shoplog=phone.findObject(By.res("com.stv.shopping:id/shopping_recycler_view"));
        check("未在购物桌面滑动",shoplog!=null);
        press_right(1);
        press_up(15);

        press_back(2);
        press_down(1);

        addStep("进入个人中心");
        UiObject2 personCenter=waitForObj(By.res("com.stv.shopping:id/child_view5"));
        check("未进入购物个人中心",personCenter!=null);
        personCenter.click();
        personCenter.click();
        sleepInt(1);
        UiObject2 pleaseLogin=waitForObj(By.text("请登录超级电视帐号"));
        UiObject2 pleaseOk=waitForObj(By.text("确定"));
        if(pleaseLogin!=null&&pleaseOk!=null) {
            pleaseOk.click();
            sleepInt(1);
            AccountLogin();
        }
        for(int j=0;j<4;j++) {
            String arr[] = {"订单", "优惠券", "退换货申请", "收货地址"};
            for (int i = 0; i < arr.length; i++) {
                UiObject2 personList = phone.findObject(By.text(arr[i]));
                addStep("进入" + arr[i]);
                check("未进个人中心列表", personList != null);
                personList.click();
                press_right(3);
                press_left(3);
                press_down(1);
            }
            press_up(2);
        }

        press_back(2);
        press_down(1);
        addStep("进入全部商品");
        UiObject2 allShop=waitForObj(By.res("com.stv.shopping:id/child_view4"));
        check("未进入全部商品",allShop!=null);
        allShop.click();
        allShop.click();
        sleepInt(1);
        String arr[] = {"首页", "家用电器", "个护美妆", "家居百货","食品茶酒","生活服务","母婴玩具","运动户外","女装/男装","文化创意","鞋包配饰"};

        for (int j = 0; j < arr.length; j++) {
            addStep("进入全部商品进入遍历");
            UiObject2 allList = phone.findObject(By.text(arr[j]));
            addStep("进入" + arr[j]);
            check("未进个人中心列表", allList != null);
            allList.click();
            if(j==6){
                press_right(6);
                press_left(5);
            }
            press_down(5);
            sleepInt(1);
            press_back(1);
            sleepInt(2);
        }
        press_back(3);
    }

    @Test
    @CaseName("进入搜索商品购买、加入购物车、删除")
    public void testSeekShop()throws UiObjectNotFoundException,RemoteException{
        gotoHomeScreen("购物");
        addStep("进入桌面购物");
        for (int Loop=0;Loop<getIntParams("Loop");Loop++){
            try {
                SeekShop();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    gotoHomeScreen("购物");
                    SeekShop();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }

    }
    public void SeekShop()throws UiObjectNotFoundException,RemoteException{
        press_back(2);
        press_down(3);
        UiObject2 seekShop=waitForObj(By.res("com.stv.shopping:id/child_view3"));
        seekShop.click();
        seekShop.click();
        sleepInt(2);
        addStep("进入购物搜索商品");
        UiObject2 seekshopin=waitForObj(By.res("com.stv.shopping:id/searchboard_left_panel")).findObject(By.res("com.stv.shopping:id/search_edit_tips_tv"));
        check("未进入购物搜索输入",seekshopin!=null);
        UiObject2 X = phone.findObject(By.clazz("android.widget.TextView").text("X"));
        UiObject2 H = phone.findObject(By.clazz("android.widget.TextView").text("H"));
        UiObject2 L = phone.findObject(By.clazz("android.widget.TextView").text("L"));
        UiObject2 J = phone.findObject(By.clazz("android.widget.TextView").text("J"));
        X.click();X.click();H.click();H.click();L.click();L.click();J.click();J.click();
        sleepInt(3);
        UiObject2 xhlj=waitForObj(By.text("西湖龙井"));
        xhlj.click();
        xhlj.click();
        sleepInt(3);
        UiObject2 shopParticulars=phone.findObject(By.text("商品详情"));
        check("未进入商品详情中",shopParticulars!=null);
        addStep("选择商品购买");
        UiObject2 nowbuy=waitForObj(By.text("立即购买"));
        nowbuy.click();
        sleepInt(2);
        addStep("商品提交订单");
        UiObject2 submitOrder=waitForObj(By.text("提交订单"));
        check("未进入提交订单",submitOrder!=null);
        submitOrder.click();
        submitOrder.click();
        press_back(1);
        press_back(1);
        addStep("加入购物车里");
        UiObject2 addBuyCar=waitForObj(By.text("加入购物车"));
        check("未加入购物车",addBuyCar!=null);
        addBuyCar.click();
        addBuyCar.click();
        sleepInt(2);
        press_right(1);
        UiObject2 rightMenu=waitForObj(By.res("com.stv.shopping:id/detail_toolbar_ll")).findObject(By.res("com.stv.shopping:id/detail_cart_view"));
        rightMenu.click();
        sleepInt(2);
        UiObject2 shopcar=phone.findObject(By.text("购物车"));
        check("未进入购物车",shopcar!=null);

        UiObject2 nullshopcar=phone.findObject(By.text("您的购物车暂无商品"));
        if(nullshopcar!=null){
            press_back(1);
        }
        UiObject2 deleteshop=waitForObj(By.res("com.stv.shopping:id/goods_item_rl")).findObject(By.res("com.stv.shopping:id/delete_button"));
        deleteshop.click();
        sleepInt(4);
        addStep("删除商品");
        UiObject2 delete=waitForObj(By.text("删除"));
        check("未弹出删除按钮",delete !=null);
        delete.click();
        sleepInt(3);
        press_back(5);
    }


    public void AccountLogin() throws UiObjectNotFoundException,RemoteException{
        UiObject2 letvAccount = waitForObj(By.clazz("android.widget.TextView").text(Pattern.compile("帐号.*|帐户: .*|超级电视帐号|会员帐号")));
        check("can't find letvAccount.", letvAccount!=null);
        sleepInt(1);
        for(int j=0;j<3;j++){
            UiObject2 accountUsed = phone.findObject(By.text("选择要登录的帐号"));     //删除帐号登陆
            if (accountUsed != null){
                press_right(1);
                check("there is no account loged in before", accountUsed != null);
                press_menu(1);
                sleepInt(1);
                UiObject2 delAccount = phone.findObject(By.res(Pattern.compile("eui.tv:id/list_item_1_title|com.stv.t2.account:id/menu_item_delete")));
                check("there is no account loged in before", delAccount != null);
                sleepInt(1);
                delAccount.click();
                sleepInt(1);
                UiObject2 confirm = phone.findObject(By.text("确定"));
                check("delete account note don't exists", confirm != null);
                confirm.click();
                sleepInt(1);
            }else {
                break;
            }
        }
        UiObject2 quitBtn1 = phone.findObject(By.clazz("android.widget.TextView").text("退出登录"));   //退出帐号登陆
        if (quitBtn1!=null) {
            addStep("已存在乐视帐号，退出登录");
            press_right(5);
            press_center(1);
            sleepInt(2);
            UiObject2 confirm = phone.findObject(By.clazz("android.widget.Button").text(Pattern.compile("确定|退出")));
            check("can't find confirm.", confirm != null);
            confirm.click();
            sleepInt(4);
        }
        UiObject2 login = null;
        for (int a=0;a<3;a++){
            login = phone.findObject(By.text(Pattern.compile("添加帐号|帐号密码登录|.*帐号登录.*")));//其他帐号登录注册
            if (login!=null) {
                addStep("使用账号密码登录");
                login.click();
                sleepInt(2);
                break;
            }
//            launchApp(AppName.LeAccount,IntentConstants.LeAccount);
            SuperTV();
        }
        if(Build.DEVICE.contains("U4")){
            press_back(1);
        }
        sleepInt(2);
        UiObject2 userName = phone.findObject(By.text(Pattern.compile("乐视帐号|会员帐号|超级电视帐号|帐        号"))).getParent().findObject(By.clazz("android.widget.EditText"));
        check("can't find userName.", userName != null);
        userName.setText(getStrParams("USERNAME"));
        sleepInt(2);
        press_down(1);

        UiObject passwd1=phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));

        check("can't find passwd.", passwd1!=null);
        sleepInt(2);
        passwd1.setText(getStrParams("PASSWORD"));
        sleepInt(1);
        UiObject2 loginNow = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
        check("can't find loginNow.", loginNow!=null);
        sleepInt(1);
        UiObject2 cancelBtn = phone.findObject(By.clazz("android.widget.TextView").text("取消"));
        loginNow.click();
        //loginNow.click();
        sleepInt(1);
        press_center(1);
        for(int i=0;i<120;i++){
            sleep(1);
            UiObject2 quitBtn = phone.findObject(By.clazz("android.widget.TextView").text("退出登录"));
            if(quitBtn!=null){
                break;
            }
        }
        if (cancelBtn!=null) {
            cancelBtn.click();
            sleepInt(4);
        }

    }
    public void SuperTV(){
        gotoHomeScreen("应用");
        press_down(3);
        addStep("进入乐视帐号");
        UiObject2 superTV = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("超级电视帐号"));
        check("未进入乐视帐号",superTV!=null);
        superTV.click();
        superTV.click();
        sleepInt(2);
    }

}
