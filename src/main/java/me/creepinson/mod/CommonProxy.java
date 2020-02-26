package me.creepinson.mod;

import com.draco18s.hardlib.EasyRegistry;
import me.creepinson.mod.base.BaseBlock;
import me.creepinson.mod.base.BaseProxy;
import me.creepinson.mod.block.BlockStation;
import me.creepinson.mod.block.BlockTube;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project randomlyaa
 **/
public class CommonProxy extends BaseProxy {

    public static BaseBlock BLOCK_TUBE;
    public static BaseBlock BLOCK_STATION;

    @Override
    public void preInit() {
        RandomlyAddingAnything.getInstance().creativeTab.setItem(new ItemStack(Items.APPLE));

        BLOCK_TUBE = new BlockTube(Material.CLOTH, new ResourceLocation(RandomlyAddingAnything.MOD_ID, "tube"), RandomlyAddingAnything.getInstance().creativeTab);
        BLOCK_STATION = new BlockStation(Material.CLOTH, new ResourceLocation(RandomlyAddingAnything.MOD_ID, "station"), RandomlyAddingAnything.getInstance().creativeTab);
    }

    @Override
    public void registerBlocks() {

        EasyRegistry.registerBlockWithItem(BLOCK_TUBE, new ResourceLocation(RandomlyAddingAnything.MOD_ID, "tube"));
        EasyRegistry.registerBlockWithItem(BLOCK_STATION, new ResourceLocation(RandomlyAddingAnything.MOD_ID, "station"));
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