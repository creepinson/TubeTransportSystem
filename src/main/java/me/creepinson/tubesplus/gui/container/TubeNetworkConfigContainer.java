package me.creepinson.tubesplus.gui.container;

import com.theoparis.creepinoutils.util.text.GroupTextComponent;
import me.creepinson.tubesplus.handler.TubesPlusRegistryHandler;
import me.creepinson.tubesplus.tile.TubeTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.INameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
public class TubeNetworkConfigContainer extends Container implements INameable {

    public TubeTile tile;

    public TubeNetworkConfigContainer(int id, PlayerInventory playerInv, BlockPos tilePos) {
        super(TubesPlusRegistryHandler.TUBE_CONFIG_CONTAINER.get(), id);
        TileEntity te = playerInv.player.world.getTileEntity(tilePos);
        if (te instanceof TubeTile && !te.isRemoved()) this.tile = (TubeTile) te;
    }



    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return tile.getNetwork() != null;
    }

    @Override
    public ITextComponent getName() {
        return new GroupTextComponent().string("Tube Network Configuration");
    }
}
