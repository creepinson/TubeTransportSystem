package me.creepinson.tubesplus.handler;

import me.creepinson.creepinoutils.base.BaseBlock;
import me.creepinson.creepinoutils.base.BaseBlockWithTile;
import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.block.BlockGravityController;
import me.creepinson.tubesplus.block.BlockStation;
import me.creepinson.tubesplus.block.BlockTube;
import me.creepinson.tubesplus.capability.GravityProvider;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
    public static BaseBlock BLOCK_GRAVITY_CONTROLLER;
    public static BaseBlock BLOCK_TUBE;
    public static BaseBlock BLOCK_STATION;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BLOCK_TUBE = new BlockTube(Material.IRON, new ResourceLocation(TubesPlus.MOD_ID, "tube"), TubesPlus.getInstance().creativeTab);
        BLOCK_STATION = new BlockStation(Material.CLOTH, new ResourceLocation(TubesPlus.MOD_ID, "station"), TubesPlus.getInstance().creativeTab);
        BLOCK_GRAVITY_CONTROLLER = new BlockGravityController(Material.CLOTH, new ResourceLocation(TubesPlus.MOD_ID, "gravity_controller"), TubesPlus.getInstance().creativeTab);

        event.getRegistry().registerAll(BLOCK_TUBE, BLOCK_STATION, BLOCK_GRAVITY_CONTROLLER);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(BLOCK_TUBE.createItemBlock(), BLOCK_STATION.createItemBlock(), BLOCK_GRAVITY_CONTROLLER.createItemBlock());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (EnumFacing f : EnumFacing.values()) {
            // Metadata for facing state property
            ModelLoader.setCustomModelResourceLocation(BLOCK_TUBE.createItemBlock(), f.ordinal(), new ModelResourceLocation(BLOCK_TUBE.getRegistryName(), "inventory"));
        }

        ModelLoader.setCustomModelResourceLocation(BLOCK_STATION.createItemBlock(), 0, new ModelResourceLocation(BLOCK_STATION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BLOCK_GRAVITY_CONTROLLER.createItemBlock(), 0, new ModelResourceLocation(BLOCK_GRAVITY_CONTROLLER.getRegistryName(), "inventory"));
    }

    public static final ResourceLocation GRAVITY_CAP = new ResourceLocation(TubesPlus.getInstance().modId, "gravity");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<EntityLivingBase> event) {
        event.addCapability(GRAVITY_CAP, new GravityProvider());
        TubesPlus.debug("Added gravity capability for " + event.getObject().getName());
    }

}
