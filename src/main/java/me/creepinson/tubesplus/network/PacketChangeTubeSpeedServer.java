package me.creepinson.tubesplus.network;


import io.netty.buffer.ByteBuf;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketChangeTubeSpeedServer implements IMessage {

    public double speed;
    public int x, y, z;

    static TileEntityTube machine;

    public PacketChangeTubeSpeedServer() {

    }

    public PacketChangeTubeSpeedServer(TileEntityTube tileEntityMachine, double speed) {
        this.speed = speed;
        this.x = tileEntityMachine.getPos().getX();
        this.y = tileEntityMachine.getPos().getY();
        this.z = tileEntityMachine.getPos().getZ();
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
        buf.writeDouble(speed);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class Handler implements IMessageHandler<PacketChangeTubeSpeedServer, IMessage> {


        @Override
        public IMessage onMessage(PacketChangeTubeSpeedServer message, MessageContext ctx) {
            TileEntity tileEntity = ctx.getServerHandler().player.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
            if (tileEntity instanceof TileEntityTube) {
                TileEntityTube tile = (TileEntityTube) tileEntity;
                tile.getNetwork().setSpeed(message.speed);
                tile.updateSpeed();
                tile.refresh();
            }
            return null;
        }
    }

}