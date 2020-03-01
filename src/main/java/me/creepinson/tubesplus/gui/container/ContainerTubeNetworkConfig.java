package me.creepinson.tubesplus.gui.container;

import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
public class ContainerTubeNetworkConfig extends Container {

    public final TileEntityTube tile;

    public ContainerTubeNetworkConfig(TileEntityTube te) {
        super();
        this.tile = te;
    }


    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.getNetwork() != null;
    }
}
