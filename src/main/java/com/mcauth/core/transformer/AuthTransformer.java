package com.mcauth.core.transformer;

import cpw.mods.fml.relauncher.FMLRelauncher;
import cpw.mods.fml.relauncher.IClassTransformer;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.network.packet.Packet205ClientCommand;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class AuthTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, byte[] bytes) {
        if(FMLRelauncher.side().equals(Side.CLIENT.name())) {
            return bytes;
        }

        if(name.equals("net.minecraft.network.packet.Packet205ClientCommand")) {
            return transformNetLoginHandler(bytes);
        }

        return bytes;
    }

    private byte[] transformNetLoginHandler(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        for (Object methodObj : classNode.methods) {
            // Ищем метод handleLogin (в обфускации 1.4.7 обычно "a" с параметром Packet1Login)
            MethodNode method = (MethodNode) methodObj;
            System.out.println(method.name);
            if (method.name.equals("processPacket") || (method.name.equals("func_73279_a"))) {
                method.instructions.clear();
                InsnList inject = new InsnList();
                inject.add(new VarInsnNode(Opcodes.ALOAD, 0));
                inject.add(new VarInsnNode(Opcodes.ALOAD, 1));
                inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                        "com/mcauth/core/hook/PacketClientCommand",
                        "onProcessClientCommand",
                        "(Ljava/lang/Object;Ljava/lang/Object;)V"));
                inject.add(new InsnNode(Opcodes.RETURN));
                method.instructions.insert(inject);
//
//                InsnList list = new InsnList();
//                // Загружаем 'this' (NetLoginHandler)
//                list.add(new VarInsnNode(Opcodes.ALOAD, 0));
//                // Загружаем первый аргумент (Packet1Login)
//                list.add(new VarInsnNode(Opcodes.ALOAD, 1));
//                // Вызываем наш статический метод для проверки
//                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
//                        "com/example/core/Hooks", "onHandleLogin", "(Ljava/lang/Object;Ljava/lang/Object;)V"));
//
//                // Вставляем наш код в самое начало метода
//                method.instructions.insert(list);
//                System.out.println("CoreMod: NetLoginHandler patched!");
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
