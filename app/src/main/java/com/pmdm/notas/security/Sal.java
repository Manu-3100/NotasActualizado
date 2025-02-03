package com.pmdm.notas.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Sal {

    public static String getSal(){
        return getSal(6);
    }

    private static String getSal(int size){
        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder sb = new StringBuilder();
        while (size-- > 0)
            sb.append(chars[(int)(Math.random() * chars.length)]);
        return sb.toString();
    }

    private static String getSHA1(String texto) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(texto.getBytes("utf8"));
            return String.format("%40x", new BigInteger(1, digest.digest())).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String getHashRobusto(String texto) {

        String sal = String.valueOf(generateSal());
        String sha1 = getSHA1(texto);
        String salSha1 = sal + sha1;
        String salSha1Cifrado = getSHA1(salSha1);

        return sal + salSha1Cifrado;
    }

    private static int generateSal() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}