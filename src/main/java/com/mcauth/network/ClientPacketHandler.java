package com.mcauth.network;

import com.mcauth.core.hook.PacketClientCommand;
import com.mcauth.main.McAuth;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.FMLRelauncher;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.util.Random;

public class ClientPacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager iNetworkManager, Packet250CustomPayload packet250CustomPayload, Player player) {

        if(FMLRelauncher.side().equals(Side.CLIENT.name())) {

            SoundManager.sndSystem.

            System.out.println("CLIENT HANDLER");

            Random r = new Random();
            byte[] bytes = new byte[256];
            r.nextBytes(bytes);

            iNetworkManager.addToSendQueue(new Packet250CustomPayload(McAuth.MOD_CHANNEL, bytes));
        } else {
            //PacketClientCommand.authenticate();
        }
       }
}
