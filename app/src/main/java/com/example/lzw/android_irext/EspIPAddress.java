package com.example.lzw.android_irext;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class EspIPAddress {
    private static boolean getAddressFlag=false;
    private static List<InetAddress> mInetAddressList = new ArrayList<>();

    public static boolean isGetAddressFlag() {
        return getAddressFlag;
    }

    public static void setGetAddressFlag(boolean getAddressFlag) {
        EspIPAddress.getAddressFlag = getAddressFlag;
    }

    public static List<InetAddress> getmInetAddressList() {
        return mInetAddressList;
    }

    public static void setmInetAddressList(List<InetAddress> mInetAddressList) {
        EspIPAddress.mInetAddressList.clear();
        EspIPAddress.mInetAddressList = mInetAddressList;
    }
}
