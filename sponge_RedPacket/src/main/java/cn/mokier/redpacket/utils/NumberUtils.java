package cn.mokier.redpacket.utils;

import java.util.ArrayList;
import java.util.List;

public class NumberUtils {

    /**
     * 把一个整数拆分成诺干份
     * @param total  要拆分的整数
     * @param split  拆分的次数
     * @return
     */
    public static List<Integer> splitNumber(int total, int split, boolean isRandom) {
        List<Integer> list = new ArrayList<>();
        if(isRandom) {
            for(int i=1;i<split;i++) {
                int range = total/5;
                int random = (int)(1+Math.random()*(range-1+1));

                total -= random;
                list.add(random);
            }

            list.add(total);
        }else {
            int number = total/split;
            for(int i=0;i<split;i++) {
                list.add(number);
            }
        }

        return list;
    }

    public static void main(String[] rags){
        System.out.println(1);
    }

}
