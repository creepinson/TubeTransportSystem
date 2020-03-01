package me.creepinson.tubesplus.client.gui;

import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // I don't use any containers in my GUIs (GuiContainer), but if I did, I'd return them here.
        // You can only get away with what I'm doing here if your GUIs extend GuiScreen and not GuiContainer
        // Servers get containers - Clients get GUIs
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof IInteractionObject) {
            return ((IInteractionObject)tileEntity).createContainer(player.inventory, player);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TubesPlus.debug("TEST");

        TileEntity tileEntity = world.getTileEntity(pos);
        switch (ID) {
            default:
                return new TubeNetworkConfigGui((TileEntityTube) tileEntity);
        }
        // It is common Java convention to "break;" a switch case, but because all my cases end in returns, I don't need to
    }


}