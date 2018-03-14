package com.sky.game.mina.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.mina.util.Base64;
 
 
/**
 * DES{3DES加密解密的工具类 }
 */
public class DES {
    private  final String Algorithm = "DESede"; 
    private static DES des = null;
    protected String _genaratedKey = "";
    protected byte[] _desKey = null;
    
    /**
     * 单例 
     * @return
     */
    public static DES des() {
    	if (des == null)
    		des = new DES();
    	return des;
    }
    
    /**
     * 加密方法
     * @param src Դ源数据的字节数组
     * @return 
     */
    public byte[] encrypt(String _desKey, byte[] src) {
        try {
             SecretKey deskey = new SecretKeySpec(desKey(_desKey), Algorithm);    //生成密钥Կ
             Cipher c1 = Cipher.getInstance(Algorithm);    //实例化负责加密/解密的Cipher工具类
             c1.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式
             return c1.doFinal(src);
         } catch (java.security.NoSuchAlgorithmException e1) {
             e1.printStackTrace();
         } catch (javax.crypto.NoSuchPaddingException e2) {
             e2.printStackTrace();
         } catch (java.lang.Exception e3) {
             e3.printStackTrace();
         }
         return null;
     }
    
    /**
     * 解密函数
     * @param src 密文的字节数组
     * @return
     */
    public byte[] decrypt(String _desKey, byte[] src) {      
        try {
//        	src = Base64.decodeBase64(src);
            SecretKey deskey = new SecretKeySpec(desKey(_desKey), Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);    //初始化为解密模式
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
     }
    
    /**
     * 随机生成一个Key
     * @return
     */
	public String genrateKey(String key) {
		List<String> letters = Arrays.asList(key.split(""));
		Collections.shuffle(letters);
		_genaratedKey = "";
		for (String letter : letters) {
			_genaratedKey += letter;
		}
		
		_desKey = desKey(_genaratedKey);
		
		return _genaratedKey;
	}
    /*
     * 根据字符串生成密钥字节数组 
     * @param keyStr 密钥字符串
     */
    private byte[] desKey(String keyStr) {
        byte[] key = new byte[24];     //声明一个24位的字节数组，默认里面都是0
		try {
			 byte[] temp = keyStr.getBytes("UTF-8");
			 /*
	         * 执行数组拷贝
	         * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
	         */
	        if(key.length > temp.length){
	        	 //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
	            System.arraycopy(temp, 0, key, 0, temp.length);
	        }else{
	        	//如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
	            System.arraycopy(temp, 0, key, 0, key.length);
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}    
       
        return key;
    } 
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	String desKey="504107060920821";
    	String str = "123456789kjasfdjkba";
    	byte[] encrypt = DES.des().encrypt(desKey,str.getBytes("UTF-8"));
    	byte[] decrypt = DES.des().decrypt(desKey,encrypt);
    	String ming = new String(decrypt,"UTF-8");
    	System.out.println(ming);
	}
}
