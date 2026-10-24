package com.mcauth.proxy;

import com.mcauth.main.McAuth;
import com.mcauth.network.ClientPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ClientProxy extends CommonProxy {



    public void init() {

        File file = new File(Minecraft.getMinecraftDir().getAbsolutePath(), "nanori");
        try {
            if(!file.exists() && !file.mkdirs()) {
                throw new RuntimeException("Couldn't create key dir");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        String absPath = file.getAbsolutePath();
        File privateKeyFile = new File(absPath, "privkey.enc");
        File publicKeyFile = new File(absPath, "pubkey.enc");

        PrivateKey privateKey;
        PublicKey publicKey;

        if(!privateKeyFile.exists()) {
            KeyPairGenerator keyGen = null;
            try {
                keyGen = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            keyGen.initialize(4096);

            KeyPair pair = keyGen.generateKeyPair();
            publicKey = pair.getPublic();
            privateKey = pair.getPrivate();

            try {
                saveBytesToFile(publicKeyFile, publicKey.getEncoded());
                saveBytesToFile(privateKeyFile, privateKey.getEncoded());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(readKey(privateKeyFile));
            privateKey = keyFactory.generatePrivate(privateSpec);
            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readKey(publicKeyFile));
            publicKey = keyFactory.generatePublic(publicSpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Keys read or generated successfully");


        NetworkRegistry.instance().registerChannel(new ClientPacketHandler(privateKey, publicKey), McAuth.MOD_CHANNEL);
    }

    private static byte[] readKey(File privateKeyFile) throws IOException {
        FileInputStream fis = new FileInputStream(privateKeyFile);
        try {
            byte[] bytes = new byte[(int) privateKeyFile.length()];
            fis.read(bytes);
            return bytes;
        } finally {
            fis.close();
        }
    }

    private static void saveBytesToFile(File file, byte[] bytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
        try {
            fos.write(bytes);
        } finally {
            fos.close();
        }
    }
}
