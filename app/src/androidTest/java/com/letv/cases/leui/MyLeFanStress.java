package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.provider.Contacts;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;
import junit.framework.Assert;
import org.junit.Test;
import java.util.regex.Pattern;
public class MyLeFanStress extends LetvTestCase{
    int count=0;
    @Test
    @CaseName("我的乐范个人帐号和全部特权遍历")
    public void testmyLeFan()throws UiObjectNotFoundException,RemoteException{
        addStep("进入我的乐范");
        gotoHomeScreen("我的乐范");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                mylefan();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("我的乐范");
                    mylefan();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void mylefan()throws UiObjectNotFoundException,RemoteException{
        press_down(1);
        press_back(2);
        press_down(1);
        UiObject2 personalGrowth=phone.findObject(By.clazz("android.widget.FrameLayout").res("com.stv.plugin.ucenter:id/login_message_layout"));
        personalGrowth.click();
        personalGrowth.click();
        sleepInt(2);
        UiObject2 supertvlogin=phone.findObject(By.text("超级电视帐号"));
        if(supertvlogin!=null){
            AccountLogin();
        }
        addStep("进入乐范我的成长值");
        UiObject2 myGrowth=phone.findObject(By.text("我的成长值"));
        check("未进入我的成长值",myGrowth!=null);
        UiObject2 growthlist=waitForObj(By.res("com.stv.ucenter:id/tv_gd_title").text("成长值明细"));
        check("未进入我的成长明细",growthlist!=null);
        growthlist.click();
        sleepInt(2);
        String arr[]={"全部明细","消费成长","开机成长","退货扣除"};
        for(int i=0;i<arr.length;i++){
            UiObject2 arrlist=waitForObj(By.text(arr[i]));
            arrlist.click();
            press_right(2);
            press_left(2);
            sleepInt(1);
        }
        press_back(3);
        sleepInt(1);
        press_down(1);
        UiObject2 presonPrivilege=waitForObj(By.clazz("android.widget.FrameLayout").res("com.stv.plugin.ucenter:id/privilege_item1"));
        check("未进入个人特权",presonPrivilege !=null);
        presonPrivilege.click();
        presonPrivilege.click();
        press_left(2);
        UiObject2 allPrivilege=phone.findObject(By.text("全部特权"));
        check("未进入全部特权",allPrivilege!=null);
        String arrprivilege[]={"福利特权","购物特权","续费特权","金融特权","服务特权","游戏特权"};
        for(int k=0;k<2;k++) {
            for (int j = 0; j < arrprivilege.length; j++) {
                press_left(3);
                UiObject2 priv = waitForObj(By.clazz("android.widget.RadioButton").text(arrprivilege[j]));
                check("未进入" + arrprivilege[j], priv != null);
                press_down(1);
                press_right(3);
            }
            press_up(7);
        }
        press_back(2);
    }

    @Test
    @CaseName("我的乐范中卡券、订单地、赠品、兑换、会员续费遍历")
    public void testPersonMyLeFan()throws UiObjectNotFoundException,RemoteException{
        addStep("进入我的乐范");
        gotoHomeScreen("我的乐范");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                PersonMyLeFan();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("我的乐范");
                    PersonMyLeFan();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
            press_back(2);
        }
    }
    public void PersonMyLeFan()throws UiObjectNotFoundException,RemoteException{
        press_down(1);
        press_back(2);
        press_down(3);
        addStep("进入我的卡券列表遍历");
        UiObject2 myCardVoucher=waitForObj(By.res("com.stv.plugin.ucenter:id/function_item1"));
        check("未进入我的卡券",myCardVoucher !=null);
        myCardVoucher.click();
        myCardVoucher.click();
        press_left(1);
        press_up(7);
        String arrmycard[]={"全部","会员券","联名券","购物券","游戏券"};
        for(int i=0;i<arrmycard.length;i++) {
            press_left(1);
            UiObject2 allcard = waitForObj(By.text(arrmycard[i]));
            check("未进入卡券列表中的"+arrmycard[i],allcard!=null);
            press_right(2);
            press_left(2);
            press_down(1);
        }
        press_back(1);
        sleepInt(3);

        addStep("进入我的乐范中我的订单列表遍历");
        UiObject2 myOrderForm=waitForObj(By.res("com.stv.plugin.ucenter:id/function_item2"));
        check("未进入我的订单",myOrderForm !=null);
        myOrderForm.click();
        myOrderForm.click();
        press_left(1);
        press_up(7);
        String arrmyOrderForm[]={"购物订单","影视会员","体育会员","CIBN会员"};
        for(int j=0;j<arrmyOrderForm.length;j++) {
            press_left(1);
            UiObject2 allmyOrderForm = waitForObj(By.text(arrmyOrderForm[j]));
            check("未进入我的乐范中我的订单列表中的"+arrmyOrderForm[j],allmyOrderForm!=null);
            press_right(2);
            press_left(2);
            press_down(1);
        }
        press_back(1);
        sleepInt(3);

        addStep("进入我的乐范赠品领取遍历");
        UiObject2 complimentary=waitForObj(By.res("com.stv.plugin.ucenter:id/function_item3"));
        check("未进入我的乐范我的卡券",complimentary !=null);
        complimentary.click();
        complimentary.click();
        press_left(1);
        press_up(3);
        String arrcomplimentary[]={"CIBN会员","华数会员"};
        for(int k=0;k<arrcomplimentary.length;k++) {
            press_left(1);
            UiObject2 allComplimentary = waitForObj(By.text(arrcomplimentary[k]));
            check("未进入我的乐范我的订单列表中的"+arrcomplimentary[k],allComplimentary!=null);
            press_right(2);
            press_left(2);
            press_down(1);
        }
        press_back(1);
        sleepInt(3);

        addStep("进入我的乐范时长兑换");
        UiObject2 howLongFor=waitForObj(By.res("com.stv.plugin.ucenter:id/function_item4"));
        check("未进入我的乐范时长兑换",howLongFor !=null);
        howLongFor.click();
        howLongFor.click();
        press_back(1);
        sleepInt(3);


        addStep("进入我的乐范帐号安全");
        UiObject2 accountSecurity=waitForObj(By.res("com.stv.plugin.ucenter:id/function_item5"));
        check("未进我的乐范入帐号安全",accountSecurity!=null);
        accountSecurity.click();
        accountSecurity.click();
        press_right(2);
        press_back(1);
        sleepInt(3);


        addStep("进入我的乐范会员续费");
        UiObject2 member=waitForObj(By.res("com.stv.plugin.ucenter:id/function_item6"));
        check("未进入我的乐范我的卡券",member!=null);
        member.click();
        member.click();
        press_left(1);
        press_up(4);
        String arrmember[]={"超级影视会员","超级体育会员","CIBN超级会员"};
        for(int l=0;l<arrmember.length;l++) {
            press_left(1);
            UiObject2 allmember = waitForObj(By.text(arrmember[l]));
            check("未进入我的乐范我的订单列表中的"+arrmember[l],allmember!=null);
            press_right(2);
            press_left(2);
            press_down(1);
        }
        press_back(1);
        sleepInt(2);















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
