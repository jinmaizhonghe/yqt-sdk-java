package com.payplus.api.security;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * 3DES加密
 *
 * @author：kai.yang
 * @since：2017年4月17日 下午4:25:07
 * @version:
 */
public class _3DESUtil {

   
    public static final String CIPHER_ALGORITHM = "Desede/ECB/NoPadding";

    // ----------------------------3DES----------------------------
    /**
     * 生成3DES密钥.
     *
     * @param key_byte
     *            seed key
     * @throws Exception
     * @return javax.crypto.SecretKey Generated DES key
     */
    public static SecretKey genTripleDesKey(byte[] byteKey) {
        if (byteKey == null) {
            return null;
        }
        return new SecretKeySpec(byteKey, "DESede");
    }

    public static SecretKey genTripleDesKey(String strKey) {
        if (strKey == null) {
            return null;
        }
        return genTripleDesKey(strKey.getBytes());
    }

    /**
     * 3DES 解密(byte[]).
     *
     * @param key
     *            SecretKey
     * @param crypt
     *            byte[]
     * @throws Exception
     * @return byte[]
     * @throws Exception
     */
    public static byte[] tripleDesDecrypt(SecretKey key, byte[] data) throws Exception {
        byte[] data2 = zerosPadding(data);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data2);
    }

    /**
     * 3DES 解密.
     * 因为3DES是对称加密算法，key是24位，当只有16位时，后8位取key的前8位
     *
     * @param key
     *            SecretKey
     * @param crypt
     *            byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] tripleDesDecrypt(byte[] byteKey, byte[] data) throws Exception {
        byte[] keys = null;
        if (byteKey.length == 16) {
            keys = new byte[24];
            System.arraycopy(byteKey, 0, keys, 0, 16);
            System.arraycopy(byteKey, 0, keys, 16, 8);
        } else {
            keys = byteKey;
        }

        return processDecryptZeroPadding( tripleDesDecrypt(genTripleDesKey(keys), data));
    }

    /**
     * 解密后对最后补0位数据进行处理
     * @param tripleDesDecrypt
     * @return
     */
    private static byte[] processDecryptZeroPadding(byte[] tripleDesDecrypt) {
        int leg = tripleDesDecrypt.length;
        byte[] sub1Des = new byte[8];
        System.arraycopy(tripleDesDecrypt, leg - 8, sub1Des, 0, 8);
        int subLength = 0;
        for (int i = sub1Des.length - 1; i > 0; i--) {
            // 1.从最后一位开始扫描
            // 出现第一个不为0的值的位置后的位置为补位数据，需要剔除
            if (sub1Des[i] == 0) {
                subLength = i;
            } else {
                break;
            }
        }

        //2.如果存在补位数据，对原字节数组进行截取
        byte[] result =null;
        if (subLength > 0) {
            result= new byte[tripleDesDecrypt.length - 8 + subLength];
            System.arraycopy(tripleDesDecrypt, 0, result, 0, tripleDesDecrypt.length - 8 + subLength);
        }else{
            result=tripleDesDecrypt;
        }
        return result;
    }

    /**
     * 3DES加密(byte[]).
     *
     * @param key
     *            SecretKey
     * @param src
     *            byte[]
     * @throws Exception
     * @return byte[]
     * @throws NoSuchPaddingException
     * @throws Exception
     */
    public static byte[] tripleDesEncrypt(SecretKey key, byte[] data) throws Exception {
        byte[] data2 = zerosPadding(data);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data2);
    }

    /**
     * 后位为0补偿机制
     *
     * @param data
     * @return
     */
    public static byte[] zerosPadding(byte[] data) {
        byte[] data2 = null;
        if (data.length == 0 || data == null) {
            return null;
        } else {
            int n = data.length % 8;
            if (n > 0) {
                data2 = new byte[data.length + (8 - n)];
                System.arraycopy(data, 0, data2, 0, data.length);
            } else {
                data2 = data;
            }
        }
        return data2;
    }

    /**
     * 3DES加密(byte[]).
     * 因为3DES是对称加密算法，key是24位，当只有16位时，后8位取key的前8位
     *
     * @param byteKey
     *            SecretKey
     * @param data
     *            byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] tripleDesEncrypt(byte[] byteKey, byte[] data) throws Exception {
        byte[] keys = null;
        if (byteKey.length == 16) {
            keys = new byte[24];
            System.arraycopy(byteKey, 0, keys, 0, 16);
            System.arraycopy(byteKey, 0, keys, 16, 8);
        } else {
            keys = byteKey;
        }
        return tripleDesEncrypt(genTripleDesKey(keys), data);
    }

    // ----------------------------DES----------------------------
    /**
     * 获得DES加密的密钥。需要JCE的支持，如果jdk版本低于1.4，则需要 安装jce-1_2_2才能正常使用。
     *
     * @return Key 返回对称密钥
     * @throws java.security.NoSuchAlgorithmException
     * @see util.EncryptUtil 其中包括加密和解密的方法
     */
    public static Key getDesKey(byte[] key) {
        if (key == null) {
            return null;
        }
        try {
            Security.insertProviderAt(new SunJCE(), 1);
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom(key));
            return generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("解密失败,获取秘钥出错", e);
        }
    }

    public static Key getDesKey(String key) {
        if (key == null) {
            return null;
        }
        return getDesKey(key.getBytes());
    }

    /**
     * DES加密
     *
     * @param key
     *            密钥
     * @param data
     *            需要加密的数据
     * @return byte[] 加密后的数据
     * @throws util.EncryptException
     */
    public static byte[] desEncrypt(byte[] byteKey, byte[] data) {
        byte[] encryptData = new byte[data.length];
        DesEncrypt de = new DesEncrypt();
        de.ENCRYPT(byteKey, data, encryptData, data.length);
        return encryptData;
    }

    /**
     * DES解密
     *
     * @param key
     *            密钥
     * @param raw
     *            待解密的数据
     * @return byte[] 解密后的数据
     * @throws util.EncryptException
     */
    public static byte[] desDecrypt(byte[] byteKey, byte[] data) {
        byte[] plainData = new byte[data.length];
        DesEncrypt de = new DesEncrypt();
        de.DECRYPT(byteKey, plainData, data, data.length);
        return plainData;
    }

    public static String getHexStr(byte[] data) {
        if (data == null) {
            return "";
        }
        StringBuffer str = new StringBuffer();
        for (byte b : data) {
            String temp = Integer.toHexString(b & 0xFF);
            if (temp.length() == 1) {
                str.append("0" + temp);
            } else {
                str.append(temp);
            }
            str.append(",");
        }
        return str.substring(0, str.length() - 1).toString();
    }

    public static String des3DecodeToString(String key, String data) throws Exception {
        return new String(tripleDesDecrypt(ByteArraysTo16Utils._16StringToBytes(key.length() > 32 ? key.substring(0, 32) : key),
                ByteArraysTo16Utils._16StringToBytes(data)));
    }

    public static String des3EecodeToString(String key, String data) throws Exception {
        // 解决乱码问题，和pos编码规则一致，转换为16进制数
        return ByteArraysTo16Utils.printHexString(tripleDesEncrypt(ByteArraysTo16Utils._16StringToBytes(key.length() > 32 ? key.substring(0, 32) : key),
                ByteArraysTo16Utils._16StringToBytes(ByteArraysTo16Utils.printHexString(data.getBytes()))));
    }
}
