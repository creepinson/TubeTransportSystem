package me.creepinson.mod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project randomlyaa
 **/

public class ClientProxy extends CommonProxy {
    @Override
    public void registerBlocks() {
        super.registerBlocks();
        ModelLoader.setCustomModelResourceLocation(CommonProxy.BLOCK_TUBE.createItemBlock(), 0, new ModelResourceLocation(CommonProxy.BLOCK_TUBE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(CommonProxy.BLOCK_STATION.createItemBlock(), 0, new ModelResourceLocation(CommonProxy.BLOCK_STATION.getRegistryName(), "inventory"));

    }

}
