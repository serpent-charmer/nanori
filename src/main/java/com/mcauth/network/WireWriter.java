package com.mcauth.network;

import com.mcauth.main.McAuth;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.nio.ByteBuffer;
import java.util.Random;

public class WireWriter {

    public Packet250CustomPayload challenge() {
        Random r = new Random();
        ByteBuffer bb = ByteBuffer.allocate(256);
        bb.putInt(PacketTypes.CHALLENGE.ordinal());

        return new Packet250CustomPayload(McAuth.MOD_CHANNEL, bb.array());
    }

}
