package xerca.xercapaint.common.packets;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import xerca.xercapaint.common.CommandExport;

import java.util.function.Supplier;

public class ExportPaintingPacketHandler {
    public static void handle(final ExportPaintingPacket message, Supplier<NetworkEvent.Context> ctx) {
        if (!message.isMessageValid()) {
            System.err.println("Packet was invalid");
            return;
        }

        ctx.get().enqueueWork(() -> processMessage(message));
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void processMessage(ExportPaintingPacket msg) {
        if(CommandExport.doExport(Minecraft.getInstance().player, msg.getName())){
            Minecraft.getInstance().player.sendMessage(new TranslatableComponent("export.success", msg.getName()).withStyle(ChatFormatting.GREEN), Util.NIL_UUID);
        }else{
            Minecraft.getInstance().player.sendMessage(new TranslatableComponent("export.fail", msg.getName()).withStyle(ChatFormatting.RED), Util.NIL_UUID);
        }
    }
}
