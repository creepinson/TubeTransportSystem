package me.creepinson.tubesplus.network;

import io.netty.buffer.ByteBuf;
import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.tile.TubeTile;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
public class TubeUpdatePacket {
    private double speed;
    private int x, y, z;
    private boolean isInverted;

    public TubeUpdatePacket(BlockPos pos, double newSpeed, boolean inverted) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.speed = newSpeed;
        this.isInverted = inverted;
    }

    public TubeUpdatePacket(PacketBuffer buf) {
        this.speed = buf.readDouble();
        this.isInverted = buf.readBoolean();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isInverted() {
        return isInverted;
    }

    public BlockPos getPos() {
        return new BlockPos(x, y, z);
    }

    public void encode(PacketBuffer buf) {
        buf.writeDouble(this.speed);
        buf.writeBoolean(this.isInverted);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // TODO: debug config option for debug logging
            TubesPlus.LOGGER.info("Processing packet data: Pos" + this.getPos() + "; speed: " + this.getSpeed());
            TileEntity te = ctx.get().getSender().getServerWorld().getTileEntity(this.getPos());
            if (te instanceof TubeTile && !te.isRemoved()) {
                TubeTile tile = (TubeTile) te;
                if (tile.getNetwork() != null) {
                    tile.getNetwork().setSpeed(this.getSpeed());
                    tile.getNetwork().isInverted = this.isInverted();

                    tile.refresh();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
