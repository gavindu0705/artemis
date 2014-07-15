package com.artemis.core.util;

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Random;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;


public class SecurityUtil {
	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	private static String Algorithm = "DES"; // 定义 加密算法,可用 DES,DESede,Blowfish
	private static String PK = "525E9DC4D0382C4C";
	private static byte[] PK_BYTES = DataUtil.hex2byte(PK);
	private static final Pattern TOKEN_PATTERN = Pattern.compile("[A-Fa-f0-9]+");

	public static String sign(String source) {
		return DigestUtils.md5Hex(source + PK);
	}

	/**
	 * 加密
	 * 
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		return cipherByte;
	}

	/**
	 * 解密
	 */
	public static byte[] decode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		return clearByte;
	}

	/**
	 * 生成密钥, 注意此步骤时间比较长
	 */
	public static byte[] getKey() throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}

	/**
	 * 对输入字符串进行加密
	 * 
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		try {
			return DataUtil.byte2hex(encode(s.getBytes(), PK_BYTES));
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		String url = "3BD4FCE4F320460A0C9161ADEBF8A91EB8A66B29A4D62E0480119067CE71DCFEF4D3FDDE2A15A2B05ABD64CAD7504E12625F294B90E7224F8FD4B627DBB8B9D73FEA2A0362F5E8CD760DDB084F0E3C6529BFE14CD8C5AD3B434C841E0B82D711";
		String ss = decode(url);
		System.out.println(ss);
	}

	/**
	 * 对输入的加密参数进行解密
	 * 
	 * @param s
	 * @return
	 */
	public static String decode(String s) {
		try {
			if (!TOKEN_PATTERN.matcher(s).matches()) {
				return null;
			}
			return new String(decode(DataUtil.hex2byte(s), PK_BYTES));
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 根据tokenString得到实际的值
	 * 
	 * @param tokenString
	 * @return
	 */
	public static String getSand(String tokenString) {
		if (StringUtils.isEmpty(tokenString)) {
			return null;
		}
		String sRand = SecurityUtil.decode(tokenString);
		if (StringUtils.isEmpty(sRand)) {
			return null;
		}
		return sRand;
	}

	/**
	 * 得到随机的数值
	 * 
	 * @return
	 */
	private static String getRandomSand() {
		String sRand = "";
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}

	public static String generateTokenString() {
		return encode(getRandomSand());
	}

	/**
	 * 是否是有效的验证码
	 * 
	 * @param tokenString
	 * @param sand
	 * @return
	 */
	public static boolean isValidSand(String tokenString, String sand) {
		if (StringUtils.isEmpty(sand)) {
			return false;
		}
		String anotherSand = getSand(tokenString);
		if (sand.equals(anotherSand)) {
			return true;
		}
		return false;
	}
}
