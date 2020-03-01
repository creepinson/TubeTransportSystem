package me.creepinson.tubesplus;

import me.creepinson.creepinoutils.base.BaseBlock;
import me.creepinson.tubesplus.block.BlockStation;
import me.creepinson.tubesplus.block.BlockTube;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
@Mod.EventBusSubscriber(modid = TubesPlus.MOD_ID)
public class RegistryHandler {
    public static BaseBlock BLOCK_TUBE;
    public static BaseBlock BLOCK_STATION;



    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BLOCK_TUBE = new BlockTube(Material.CLOTH, new ResourceLocation(TubesPlus.MOD_ID, "tube"), TubesPlus.getInstance().creativeTab);
        BLOCK_STATION = new BlockStation(Material.CLOTH, new ResourceLocation(TubesPlus.MOD_ID, "station"), TubesPlus.getInstance().creativeTab);
        event.getRegistry().registerAll(BLOCK_TUBE, BLOCK_STATION);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(BLOCK_TUBE.createItemBlock(), BLOCK_STATION.createItemBlock());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(BLOCK_TUBE.createItemBlock(), 0, new ModelResourceLocation(BLOCK_TUBE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BLOCK_STATION.createItemBlock(), 0, new ModelResourceLocation(BLOCK_STATION.getRegistryName(), "inventory"));

    }
}
