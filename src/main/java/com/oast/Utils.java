package com.oast;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Integer newtonSymbolx(Integer n, Integer k) {
        Integer result = 1;
        for (int i = 1; i <= k; i++)
            result = result * (n - i + 1) / i;
        return result;
    }

    public static List<Integer> prepareEmptyListWithNZeroElementsx(int N) {
        List<Integer> list = new ArrayList();
        for (int i = 0; i < N; i++) {
            list.add(0);
        }
        return list;
    }


}
