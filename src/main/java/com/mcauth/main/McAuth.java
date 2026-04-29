package com.mcauth.main;

import com.mcauth.network.ClientPacketHandler;
import com.mcauth.network.ServerPacketHandler;
import com.mcauth.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "McAuth", name = "McAuth", version = "1.0")
@NetworkMod(
        clientSideRequired = true,
        serverSideRequired = true,
        channels = {McAuth.MOD_CHANNEL}
)
public class McAuth {

    public static final String MOD_CHANNEL = "McAuth";
    @Mod.Instance("McAuth")
    public static McAuth instance;

    @SidedProxy(clientSide = "com.mcauth.proxy.ClientProxy",
            serverSide = "com.mcauth.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Init
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }
}