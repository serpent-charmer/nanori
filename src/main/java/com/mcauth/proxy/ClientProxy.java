package com.mcauth.proxy;

import com.mcauth.main.McAuth;
import com.mcauth.network.ClientPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
    public void init() {
        NetworkRegistry.instance().registerChannel(new ClientPacketHandler(), McAuth.MOD_CHANNEL);
    }
}
