package com.mcauth.proxy;

import com.mcauth.main.McAuth;
import com.mcauth.network.ClientPacketHandler;
import com.mcauth.network.ServerPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ServerProxy extends CommonProxy {
    public void init() {
        NetworkRegistry.instance().registerChannel(new ServerPacketHandler(), McAuth.MOD_CHANNEL);
    }
}
