package com.mcauth.proxy;

import com.mcauth.main.McAuth;
import com.mcauth.network.ServerPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.server.MinecraftServer;

import java.io.File;

public class ServerProxy extends CommonProxy {
    public void init() {
        NetworkRegistry.instance().registerChannel(new ServerPacketHandler(), McAuth.MOD_CHANNEL);
        File file = new File(MinecraftServer.getServer().getFile("").getAbsolutePath(), "nanori");
        try {
            if(!file.exists() && !file.mkdirs()) {
                throw new RuntimeException("Couldn't create key dir");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
