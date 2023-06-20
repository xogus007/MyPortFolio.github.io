package com.lkw.searchbar.unlogin.bottomsheet;

import java.util.HashMap;
import java.util.Map;

public class ArrayInfoManager {
    private static Map<Integer, String> arrayInfo = new HashMap<>();

    public static void setArrayInfo(Map<Integer, String> info) {
        arrayInfo = info;
    }

    public static Map<Integer, String> getArrayInfo() {
        return arrayInfo;
    }
}
