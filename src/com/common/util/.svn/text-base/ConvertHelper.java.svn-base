package com.common.util;

/**
 * 转换辅助类
 * 
 * @author yang.xinwen
 * @date 20160115
 */

public class ConvertHelper {
    /**
     * byte数组转换成int
     * 
     * @param bytes
     *            4个字节数组
     * @return 32位int值
     */
    public static int byteArrayToInt(byte[] res) {
        // return ByteArray.read32bit(bytes, 0);

        return byteArrayToInt(res, 0);
    }

    /**
     * byte数组转换成int
     * 
     * @param bytes
     *            4个字节数组
     * @return 32位int值
     */
    public static int byteArrayToInt(byte[] res, int index) {
        // return ByteArray.read32bit(bytes, 0);
        if (res.length < index + 4)
            return 0;
        int target = (res[index + 0] & 0xff) | ((res[index + 1] << 8) & 0xff00) | ((res[index + 2] << 24) >>> 8)
                | (res[index + 3] << 24);
        return target;
    }

    /**
     * int值转换成byte数组
     * 
     * @param value
     *            32位
     * @return 4个字节数组
     */
    public static byte[] intToByteArray(int res) {
        // byte[] bytes=new byte[4];
        // ByteArray.write32bit(value, bytes, 0);
        // return bytes;

        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);
        targets[1] = (byte) (res >> 8);
        targets[2] = (byte) (res >> 16);
        targets[3] = (byte) (res >> 24);
        return targets;
    }

}
