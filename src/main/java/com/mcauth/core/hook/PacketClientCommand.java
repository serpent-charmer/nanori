package com.mcauth.core.hook;

import com.mcauth.main.McAuth;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet0KeepAlive;
import net.minecraft.network.packet.Packet205ClientCommand;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class PacketClientCommand {

    private static Map<NetLoginHandler, Packet205ClientCommand> connected = new WeakHashMap<NetLoginHandler, Packet205ClientCommand>();

    public static void authenticate() {
        System.out.println("AUTHENTICATING");
        System.out.println(connected);
        for (Map.Entry<NetLoginHandler, Packet205ClientCommand> netLoginHandlerStringEntry : connected.entrySet()) {
            netLoginHandlerStringEntry.getKey().handleClientCommand(netLoginHandlerStringEntry.getValue());
        }
    }

    public static void onProcessClientCommand(Object packetObj, Object handlerObj) {
        final Packet205ClientCommand packet = (Packet205ClientCommand) packetObj;
        final NetLoginHandler handler = (NetLoginHandler) handlerObj;
        System.out.println(packet);
        System.out.println(handler);
        connected.put(handler, packet);
        //while(true) {
        Random r = new Random();
        byte[] bytes = new byte[256];
        r.nextBytes(bytes);
        Packet250CustomPayload payload = new Packet250CustomPayload(McAuth.MOD_CHANNEL, bytes);
        System.out.println(handler.getPlayer());
        handler.myTCPConnection.addToSendQueue(payload);



//        if (packet.forceRespawn == 0) {
//            System.out.println("Получена команда начала игры (forceRespawn 0)");
//
//            if (handler instanceof NetLoginHandler) {
//                NetLoginHandler loginHandler = (NetLoginHandler) handler;
//
//            }
//        }
    }
}
