package me.creepinson.tubesplus;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/

public class ClientProxy extends CommonProxy {
    @Override
    public void registerBlocks() {
        super.registerBlocks();
        ModelLoader.setCustomModelResourceLocation(CommonProxy.BLOCK_TUBE.createItemBlock(), 0, new ModelResourceLocation(CommonProxy.BLOCK_TUBE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(CommonProxy.BLOCK_STATION.createItemBlock(), 0, new ModelResourceLocation(CommonProxy.BLOCK_STATION.getRegistryName(), "inventory"));

    }

}
