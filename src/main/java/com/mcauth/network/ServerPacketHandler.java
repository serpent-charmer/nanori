package com.mcauth.network;

import com.mcauth.core.hook.PacketClientCommand;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.net.SocketAddress;
import java.util.HashSet;

public class ServerPacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager iNetworkManager, Packet250CustomPayload packet250CustomPayload, Player player) {
        PacketClientCommand.authenticate();
        System.out.println("SERVER HANDLER");
    }
}
