import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class criptografia { //Classe responsavel por criptografar e descriptografar o campo "SENHA"
		
    private static SecretKeySpec secretkey;
	private static byte[] key; //SEtando as chaves a serem utilizadas

		public static void setKey(String myKey){ //Setando chaves e algoritmos a serem utilizados na criptografia em nosso caso foi o ADVANCED ENCRYPTION STANDARD(AES)
			try{
				key = myKey.getBytes("UTF-8");
				MessageDigest sha = MessageDigest.getInstance("SHA-1");
				key = sha.digest(key);
				key = Arrays.copyOf(key, 16);
				secretkey = new SecretKeySpec(key, "AES");
			}
			catch(Exception e) {

			}
		}

		public static String encrypt(String senha, String sec){ //Criptografia recebendo como parametro a senha do usuário e uma chave secreta definida em conta está chave secreta 
            //definirá como a criptografia irá se comportar quais letras/numeros utilizará e etc
			try {
				setKey(sec);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretkey);//Inicializamos nossa cifra
                return Base64.getEncoder().encodeToString(cipher.doFinal(senha.getBytes("UTF-8")));//utilizamos o base64 para codificar nossa senha
                
			} catch (Exception e) {
				
			}
			return null;
		}

        public static String decrypt(String senha, String sec){//mesmo esquema do metodo acima com mudanças somente na inicialização do CIPHER e do retorno do método
			try {
				setKey(sec);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretkey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(senha)));
			} catch (Exception e) {
				
			}
			return null;
		}

	
}
