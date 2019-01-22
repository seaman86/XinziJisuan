import java.util.HashMap;
import java.util.Map;

/**
 * 版权：    上海云砺信息科技有限公司
 * 创建者:   wangqiuhua
 * 创建时间:  2019-01-21 16:18
 * 功能描述:
 * 修改历史:
 */


public class MainApp {

    public static void main(String[] args) {
        //个人税前薪资
        float xinziTotal = 20000F;
        //个税起征点
        float qizhengdian = 5000F;
        //专项附加减免(房贷1000，养老：1000)
        float zhuanxiangTotal = 000F;

        //个人扣除五险一金应交个人金额
        float yingJiaoTotal = xinziTotal - getSubWuxianYijin();

        //计算12个月的个税
        //12个月的缴税
        Float[] YueJiaoList = new Float[12];
        for (int yuefen = 1; yuefen < 13; yuefen++) {
            setSubOneShui(yuefen, yingJiaoTotal, qizhengdian, zhuanxiangTotal, YueJiaoList);
        }

        //输出没有实发
        float pingjunTotal = 0;
        for (int i = 0; i < 12; i++) {
            System.out.println((i + 1) + "月工资：" + (yingJiaoTotal - YueJiaoList[i]) + ",缴税额：" + YueJiaoList[i]);
            pingjunTotal += (yingJiaoTotal - YueJiaoList[i]);
        }
        System.out.println("税前月收入：" + xinziTotal);
        System.out.println("税前年收入：" + xinziTotal * 12);
        System.out.println("税后年收入：" + pingjunTotal + "，平均月工资：" + (pingjunTotal / 12));
    }


    //计算五险一金扣除
    static float getSubWuxianYijin() {
        //企业基数
        float qiyeJishu = 21396F;
        //养老-个人比例
        float yamgLaoGerenBilv = 0.08F;
        //医疗-个人比例
        float yiLioaGerenBilv = 0.02F;
        //失业-个人比例
        float shiYeGerenBilv = 0.005F;
        //工商-个人比例
        float gongshangGerenBilv = 0.00F;
        //公积金-个人比例
        float shengyuGerenBilv = 0.00F;

        float subTotal = 0F;
        subTotal += getSubYanglao(qiyeJishu, yamgLaoGerenBilv);
        subTotal += getSubYiliao(qiyeJishu, yiLioaGerenBilv);
        subTotal += getSubShiye(qiyeJishu, shiYeGerenBilv);
        subTotal += getSubGongshang(qiyeJishu, gongshangGerenBilv);
        subTotal += getSubShenyu(qiyeJishu, shengyuGerenBilv);

        //公积金-个人比例
        float gongjijinGerenBilv = 0.07F;
        subTotal += getSubGongjijin(qiyeJishu, gongjijinGerenBilv);
        return subTotal;
    }


    //计算养老金险扣除
    static float getSubYanglao(float qiyeJishu, float gerenBilv) {
        return qiyeJishu * gerenBilv;
    }

    //计算医疗险扣除
    static float getSubYiliao(float qiyeJishu, float gerenBilv) {
        return qiyeJishu * gerenBilv;
    }

    //计算失业险扣除
    static float getSubShiye(float qiyeJishu, float gerenBilv) {
        return qiyeJishu * gerenBilv;
    }

    //工伤险
    static float getSubGongshang(float qiyeJishu, float gerenBilv) {
        return qiyeJishu * gerenBilv;
    }

    //生育险
    static float getSubShenyu(float qiyeJishu, float gerenBilv) {
        return qiyeJishu * gerenBilv;
    }

    //计算公积金扣除
    static float getSubGongjijin(float qiyeJishu, float gerenBilv) {
        return qiyeJishu * gerenBilv;
    }


    //缴税比例
    static Map<Float, Float> ShuiDui = new HashMap<Float, Float>() {
        {
            put(36000F, 0.03F);
            put(144000F, 0.1F);
            put(300000F, 0.2F);
            put(420000F, 0.25F);
            put(660000F, 0.3F);
            put(960000F, 0.35F);
            put(1F, 0.45F);
        }
    };

    //速算扣除数
    static Map<Float, Float> ShuiDuiSukou = new HashMap<Float, Float>() {
        {
            put(36000F, 0.0F);
            put(144000F, 2520F);
            put(300000F, 16920F);
            put(420000F, 31920F);
            put(660000F, 52920F);
            put(960000F, 85920F);
            put(1F, 181920F);
        }
    };


    /**
     * @param yuefen          月份
     * @param yingJiaoTotal   应交个税金额
     * @param qizhengdian     个税起征点
     * @param zhuanxiangTotal 专项减免
     * @param YueJiaoList     存月缴税数组
     */
    static void setSubOneShui(int yuefen, float yingJiaoTotal, float qizhengdian, float zhuanxiangTotal, Float[] YueJiaoList) {
        //累积缴税金额
        float oneShui = yingJiaoTotal * yuefen - qizhengdian * yuefen - zhuanxiangTotal * yuefen;

        //档次
        float danci = 0F;
        if (oneShui <= 36000F) {
            danci = 36000F;
        } else if (oneShui <= 144000F) {
            danci = 144000F;
        } else if (oneShui <= 300000F) {
            danci = 300000F;
        } else if (oneShui <= 144000F) {
            danci = 144000F;
        } else if (oneShui <= 420000F) {
            danci = 420000F;
        } else if (oneShui <= 660000F) {
            danci = 660000F;
        } else if (oneShui <= 960000F) {
            danci = 960000F;
        } else if (oneShui > 960000F) {
            danci = 1F;
        }

        float shuiduilv = ShuiDui.get(danci);

        float sukou = ShuiDuiSukou.get(danci);

        float leijiYuekou = 0F;
        for (int i = 1; i < yuefen; i++) {
            leijiYuekou += YueJiaoList[i - 1];
        }

        YueJiaoList[yuefen - 1] = oneShui * shuiduilv - sukou - leijiYuekou;
    }


}


