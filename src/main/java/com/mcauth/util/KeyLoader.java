package com.mcauth.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class KeyLoader {

    /**
     * Обходит указанную директорию и собирает карту "Имя игрока" -> "Публичный ключ"
     * @param baseDir Корневая папка (например, новая папка внутри MinecraftServer.getServer().getFile(""))
     * @return Map<String, PublicKey>
     */
    public static Map<String, PublicKey> loadPlayerKeys(File baseDir) {
        Map<String, PublicKey> keyMap = new HashMap<String, PublicKey>();

        if (baseDir == null || !baseDir.exists() || !baseDir.isDirectory()) {
            System.out.println("[ModName] Указанная директория не существует или не является папкой.");
            return keyMap;
        }

        File[] playerFolders = baseDir.listFiles();
        if (playerFolders == null) return keyMap;

        for (File playerDir : playerFolders) {
            // Проверяем, что это папка игрока (например, joe, ann, bill)
            if (playerDir.isDirectory()) {
                String playerName = playerDir.getName();
                File pubkeyFile = new File(playerDir, "pubkey");

                // Если файл pubkey существует, пробуем его прочитать
                if (pubkeyFile.exists() && pubkeyFile.isFile()) {
                    try {
                        byte[] keyBytes = readKey(pubkeyFile);
                        PublicKey publicKey = convertBytesToPublicKey(keyBytes);

                        keyMap.put(playerName, publicKey);
                        System.out.println("[ModName] Успешно загружен ключ для игрока: " + playerName);

                    } catch (Exception e) {
                        System.err.println("[ModName] Ошибка при чтении ключа для игрока " + playerName + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }

        return keyMap;
    }

    /**
     * Преобразует массив байт в PublicKey с использованием X509EncodedKeySpec.
     * По умолчанию используется алгоритм RSA (замените на "EC" или другой при необходимости).
     */
    private static PublicKey convertBytesToPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        // В 1.4.7 чаще всего использовался RSA. Измените алгоритм, если у вас другой (например, DSA или EC)
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
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
}

