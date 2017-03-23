package com.common.util;

import org.apache.http.util.ByteArrayBuffer;

/**
 * TAS 协议辅助类
 * 
 * @author Administrator
 *
 */
public class TASProtoHelper {
    /**
     * 为google Proto体添加NTAS协议头并返回
     * 
     * @param protoBody
     * @param funCode
     * @param sessionId
     * @return
     */
    public static byte[] getNTAS(byte[] protoBody, int funCode, int sessionId) {
        ByteArrayBuffer buff = new ByteArrayBuffer(protoBody.length + 8);
        buff.append(ConvertHelper.intToByteArray(funCode), 0, 4); // 设置funCode
        buff.append(ConvertHelper.intToByteArray(sessionId), 0, 4); // 设置sessionId
        buff.append(protoBody, 0, protoBody.length); // 设置googlebuf数据体
        return buff.toByteArray();
    }

    /**
     * 获取NTAS协议的google Proto协议体部分
     * 
     * @param body
     * @return
     */
    public static byte[] getProtoForNTAS(byte[] body) {
        byte[] msgBody = new byte[body.length - 8];
        System.arraycopy(body, 8, msgBody, 0, msgBody.length);
        return msgBody;
    }

    /**
     * 获取NTAS协议的功能码
     * 
     * @param body
     * @return
     */
    public static int getFunCodeForNTAS(byte[] body) {
        int funCode = ConvertHelper.byteArrayToInt(body);
        return funCode;
    }

    /**
     * 获取NTAS协议的SessionId
     * 
     * @param body
     * @return
     */
    public static int getSessionIdForNTAS(byte[] body) {
        int funCode = ConvertHelper.byteArrayToInt(body, 4);
        return funCode;
    }
}
