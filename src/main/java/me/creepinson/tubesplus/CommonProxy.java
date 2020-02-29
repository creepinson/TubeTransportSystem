package me.creepinson.tubesplus;

import com.draco18s.hardlib.EasyRegistry;
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

    public static BaseBlock BLOCK_TUBE;
    public static BaseBlock BLOCK_STATION;

    @Override
    public void preInit() {
        TubesPlus.getInstance().creativeTab.setItem(new ItemStack(Items.APPLE));

        BLOCK_TUBE = new BlockTube(Material.CLOTH, new ResourceLocation(TubesPlus.MOD_ID, "tube"), TubesPlus.getInstance().creativeTab);
        BLOCK_STATION = new BlockStation(Material.CLOTH, new ResourceLocation(TubesPlus.MOD_ID, "station"), TubesPlus.getInstance().creativeTab);
    }

    @Override
    public void registerBlocks() {

        EasyRegistry.registerBlockWithItem(BLOCK_TUBE, new ResourceLocation(TubesPlus.MOD_ID, "tube"));
        EasyRegistry.registerBlockWithItem(BLOCK_STATION, new ResourceLocation(TubesPlus.MOD_ID, "station"));
        GameRegistry.registerTileEntity(TileEntityTube.class, BLOCK_TUBE.getRegistryName());
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
