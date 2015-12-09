package hyweb.jo.fun.valid;

import hyweb.jo.IJOFunction;
import hyweb.jo.util.JOFunctional;

/**
 * @author william
 */
public class cid implements IJOFunction<Boolean, String> {

    private final static int[] COMPANY_ID_LOGIC_MULTIPLIER = {1, 2, 1, 2, 1, 2, 4, 1};

    @Override
    public Boolean exec(String line) throws Exception {
        try {
            int aSum = 0;
            for (int i = 0; i < COMPANY_ID_LOGIC_MULTIPLIER.length; i++) {
                //公司統編與邏輯乘數相乘.
                int aMultiply = Integer.parseInt(line.substring(i, i + 1))
                        * COMPANY_ID_LOGIC_MULTIPLIER[i];
                //將相乘的結果, 取十位數及個位數相加.
                int aAddition = ((aMultiply / 10) + (aMultiply % 10));
                //如果公司統編的第 7 位是 7 時, 會造成相加結果為 10 的特殊情況, 所以直接以 1 代替進行加總.
                aSum += (aAddition == 10) ? 1 : aAddition;
            }
            //判斷總和的餘數, 假使為 0 公司統編正確回傳 true, 其它值則反之.
            return (aSum % 10 == 0);
        } catch (Throwable e) {
            //如果 aCompanyId 參數為 null, 或者不是八位數, 或為其它非數值字串, 均傳回 false.
            return false;
        }
    }

}
