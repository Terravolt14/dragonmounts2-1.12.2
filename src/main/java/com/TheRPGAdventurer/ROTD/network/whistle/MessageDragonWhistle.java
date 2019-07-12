package com.TheRPGAdventurer.ROTD.network.whistle;

import com.TheRPGAdventurer.ROTD.inits.ModSounds;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public class MessageDragonWhistle extends AbstractMessage<MessageDragonWhistle> {

    public UUID dragonId;
    public byte controlState;

    public MessageDragonWhistle(UUID dragonId, byte controlState) {
        this.dragonId = dragonId;
        this.controlState = controlState;
    }

    public MessageDragonWhistle() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        dragonId = packetBuf.readUniqueId();
        controlState = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        packetBuf.writeUniqueId(dragonId);
        buf.writeByte(controlState);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
    }

    @Override
    public void onServerReceived(MinecraftServer server, MessageDragonWhistle message, EntityPlayer player, MessageContext messageContext) {
        Entity entity = server.getEntityFromUuid(message.dragonId);
        if (player.world.isRemote) return;
        if (entity instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) entity;
            dragon.setSitting(false);
            dragon.setWhistleState(message.controlState);
            player.world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 1.0F, 1.0F);

        } else player.sendStatusMessage(new TextComponentTranslation("whistle.msg.fail"), true);
    }
}