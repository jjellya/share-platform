package com.ad.simulateLogin;

public interface IEncryptNoKey
{
	public byte[] Encryption(byte[] data)throws Exception;
	public byte[] Encryption(String data)throws Exception;
	public String EncryptionToString(byte[] data)throws Exception;
	public String EncryptionToString(String data)throws Exception;

	public byte[] Decryption(byte[] data)throws Exception;
	public byte[] Decryption(String data)throws Exception;
	public String DecryptionToString(byte[] data)throws Exception;
	public String DecryptionToString(String data)throws Exception;
	
}
