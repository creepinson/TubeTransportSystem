package me.creepinson.tubesplus;

import me.creepinson.creepinoutils.EasyRegistry;
import me.creepinson.creepinoutils.base.BaseBlock;
import me.creepinson.creepinoutils.base.BaseProxy;
import me.creepinson.tubesplus.block.BlockStation;
import me.creepinson.tubesplus.block.BlockTube;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/
public class CommonProxy extends BaseProxy {

    @Override
    public void preInit() {
        TubesPlus.getInstance().creativeTab.setItem(new ItemStack(Items.APPLE));
    }

    @Override
    public void registerBlocks() {

    }



    @Override
    public void registerItems() {

    }

    @Override
    public void registerTileEntities() {

    }

    @Override
    public void registerRecipes() {

    }

    @Override
    public void registerPackets() {

    }
}
