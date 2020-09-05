package com.study.self.util.encrypt;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptUtil {
	
	private static final int keySize = 1024;
	
	private static final String RSA_ALGORITHM = "RSA";

	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";


	public static String getSha256(String data){
		if(data == null || "".equals(data)){
			throw new IllegalArgumentException("sha256 data should not be null");
		}
		return getSha(data,AlgorithmEnum.SHA_256.getAlgorithm());
	}

	public static String getSha512(String data){
		if(data == null || "".equals(data)){
			throw new IllegalArgumentException("sha128 data should not be null");
		}
		return getSha(data,AlgorithmEnum.SHA_512.getAlgorithm());
	}

	public static String getMD5(String data){
		if(data == null || "".equals(data)){
			throw new IllegalArgumentException("sha128 data should not be null");
		}
		return getSha(data,AlgorithmEnum.MD5.getAlgorithm());
	}

	public static String getSha1(String data){
		if(data == null || "".equals(data)){
			throw new IllegalArgumentException("sha1 data should not be null");
		}
		return getSha(data,AlgorithmEnum.SHA_1.getAlgorithm());
	}

	public static String getSha(String data ,String algorithm){
		if(data == null || "".equals(data)){
			throw new IllegalArgumentException("sha256 data should not be null");
		}
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] digest1 = digest.digest(data.getBytes(StandardCharsets.UTF_8));
			return byteToHexString(digest1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] genKeyPair() throws Exception {
		KeyPairGenerator kIKeyPairGenerator;
		kIKeyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		kIKeyPairGenerator.initialize(keySize);
		KeyPair genKeyPair = kIKeyPairGenerator.genKeyPair();
		RSAPrivateKey private1 = (RSAPrivateKey)genKeyPair.getPrivate();
		RSAPublicKey public1 = (RSAPublicKey)genKeyPair.getPublic();
		return new String[]{base64Encode(private1.getEncoded()),base64Encode(public1.getEncoded())};
	}
	
	
	public static RSAPrivateKey getRSAPrivateKey(String base64Str) throws Exception {
		byte[] decode = base64Decode(base64Str);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decode);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		return (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
	}
	
	
	public static RSAPublicKey getRSAPublicKey(String base64Str) throws Exception {
		byte[] decode = base64Decode(base64Str);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		return (RSAPublicKey)keyFactory.generatePublic(keySpec);
	}
	
	public static byte[] encryptByRSAPrivateKey(RSAPrivateKey encodedKey, byte[] byts) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, encodedKey);
		return cipher.doFinal(byts);
	}
	
	public static byte[] encryptByRSAPublicKey(RSAPublicKey encodedKey, byte[] byts) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, encodedKey);
		return cipher.doFinal(byts);
	} 
	
	public static byte[] decryptByRSAPrivateKey(RSAPrivateKey decodedKey, String base64Str) throws Exception {
		byte[] decode = base64Decode(base64Str);
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE,decodedKey);
		return cipher.doFinal(decode);
	}
	
	public static byte[] decryptByRSAPublicKey(RSAPublicKey decodedKey, String base64Str) throws Exception {
		byte[] decode = base64Decode(base64Str);
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, decodedKey);
		return cipher.doFinal(decode);
	}
	
	/**
	 *
	 * @param data
	 * @param base64SignData
	 * @param base64PublicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyBase64SignData(String data, String base64SignData,String base64PublicKey) throws Exception {
		if(data == null || base64SignData == null){
			throw new IllegalArgumentException("data or sign data must not be null or empty");
		}
		if(base64PublicKey == null){
			throw new IllegalArgumentException("pubic key must not be null or empty");
		}
		RSAPublicKey rsaPublicKey = getRSAPublicKey(base64PublicKey);
		return verifyBase64SignData(data, base64SignData, rsaPublicKey);
	}
	/**
	 *
	 * @param data
	 * @param base64SignData
	 * @param rsaPublicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyBase64SignData(String data, String base64SignData,RSAPublicKey rsaPublicKey) throws Exception {
		if(data == null || base64SignData == null){
			throw new IllegalArgumentException("data or sign data must not be null or empty");
		}
		if(rsaPublicKey == null){
			throw new IllegalArgumentException("pubic key must not be null or empty");
		}
		byte[] bytes = base64Decode(base64SignData);
		return verifySignData(data, bytes,rsaPublicKey);
	}

	public static boolean verifySignData(String data , byte[] signData,RSAPublicKey rsaPublicKey) throws Exception {
		if(data == null || rsaPublicKey == null){
			throw new IllegalArgumentException("data or public key must not be null or empty");
		}
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(rsaPublicKey);
		signature.update(data.getBytes(StandardCharsets.UTF_8));
		return signature.verify(signData);
	}
	
	public static String bytesToString(byte[] bytes) {
		return new String(bytes,StandardCharsets.UTF_8);
	}

	public static String base64Encode(byte[] bys) {
		return new String(Base64.getEncoder().encode(bys),StandardCharsets.UTF_8);
	}
	
	public static byte[] base64Decode(String bustr) {
		return Base64.getDecoder().decode(bustr.getBytes(StandardCharsets.UTF_8));
	}
	
	public static String base64DecodeToString(String bustr) {
		return new String(base64Decode(bustr),StandardCharsets.UTF_8);
	}
	
	public static byte[] base64Decode(byte[] bys) {
		return Base64.getDecoder().decode(bys);
	}
	
	public static String base64DecodeToString(byte[] bys) {
		return new String(base64Decode(bys),StandardCharsets.UTF_8);
	}

	public static String byteToHexString(byte[] bytes){
		if(bytes == null){
			throw new IllegalArgumentException("byte array should not be null");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0;i < bytes.length;i++){
			int by = bytes[i] & 0xff;
			String hexString = Integer.toHexString(by);
			if(hexString.length() < 2){
				stringBuilder.append(0);
			}
			stringBuilder.append(hexString);
		}
		return stringBuilder.toString();
	}
}
