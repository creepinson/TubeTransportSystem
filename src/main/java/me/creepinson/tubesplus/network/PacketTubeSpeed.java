package me.creepinson.tubesplus.network;

import io.netty.buffer.ByteBuf;
import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
public class PacketTubeSpeed implements IMessage {
    public double getSpeed() {
        return speed;
    }

    public BlockPos getPos() {
        return new BlockPos(x, y, z);
    }

    private double speed;
    private int x, y, z;

    public PacketTubeSpeed() {

    }

    public PacketTubeSpeed(BlockPos pos, double newSpeed) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.speed = newSpeed;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.speed = buf.readDouble();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.speed);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);

    }


    public static class Handler implements IMessageHandler<PacketTubeSpeed, IMessage> {

        @Override
        public IMessage onMessage(PacketTubeSpeed message, MessageContext ctx) {
//            TubesPlus.debug("Processing packet data.");
            TileEntity te = ctx.getServerHandler().player.getServerWorld().getTileEntity(message.getPos());
            if(te instanceof TileEntityTube && !te.isInvalid()) {
                TileEntityTube tile = (TileEntityTube)te;
                if(tile.getNetwork() != null) {
                    tile.getNetwork().setSpeed(message.getSpeed());
                    tile.updateSpeed();
                }
            }
            return null;
        }
    }


}
