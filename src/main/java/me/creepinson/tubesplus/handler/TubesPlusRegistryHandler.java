package me.creepinson.tubesplus.handler;

import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.block.TubeBlock;
import me.creepinson.tubesplus.gui.container.TubeNetworkConfigContainer;
import me.creepinson.tubesplus.network.TubeUpdatePacket;
import me.creepinson.tubesplus.tile.TubeTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
@Mod.EventBusSubscriber(modid = TubesPlus.MOD_ID)
public class TubesPlusRegistryHandler {
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TubesPlus.MOD_ID);
    public static final RegistryObject<TileEntityType<TubeTile>> TUBE_TILE = TILES.register("tube_tile", () -> TileEntityType.Builder.create(TubeTile::new, TubesPlusRegistryHandler.TUBE_BLOCK.get()).build(null));

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TubesPlus.MOD_ID);
    public static final RegistryObject<TubeBlock> TUBE_BLOCK = BLOCKS.register("tube", () -> new TubeBlock(Block.Properties.create(Material.ROCK).variableOpacity().notSolid()));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TubesPlus.MOD_ID);
    public static final RegistryObject<Item> TUBE_ITEM = ITEMS.register("tube", () -> new BlockItem(TUBE_BLOCK.get(), new Item.Properties().group(TubesPlus.getItemGroup())));

    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TubesPlus.MOD_ID);
    public static final RegistryObject<ContainerType<TubeNetworkConfigContainer>> TUBE_CONFIG_CONTAINER = CONTAINERS.register("tube_config", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new TubeNetworkConfigContainer(windowId, inv, pos);
    }));

    // Packets/Networking
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TubesPlus.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        NETWORK.messageBuilder(TubeUpdatePacket.class, 0).decoder(TubeUpdatePacket::new).encoder(TubeUpdatePacket::encode).consumer(TubeUpdatePacket::handle).add();
    }
}
