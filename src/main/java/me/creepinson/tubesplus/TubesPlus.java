package me.creepinson.tubesplus;

import mcp.MethodsReturnNonnullByDefault;
import me.creepinson.tubesplus.client.gui.TubeNetworkConfigGui;
import me.creepinson.tubesplus.handler.TubesPlusRegistryHandler;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/

@Mod(TubesPlus.MOD_ID)
public class TubesPlus {
    public static final String MOD_ID = "tubesplus";

    public static final Logger LOGGER = LogManager.getLogger();
    private static ItemGroup itemGroup;

    public TubesPlus() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
/*        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);*/
        itemGroup = new ItemGroup(MOD_ID) {
            @Override
            public @NotNull ItemStack createIcon() {
                return new ItemStack(TubesPlusRegistryHandler.TUBE_ITEM.get());
            }
        };
        TubesPlusRegistryHandler.init();
//      this.hasCreativeTab = false;
    }

    public static ItemGroup getItemGroup() {
        return itemGroup;
    }

    public void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(TubesPlusRegistryHandler.TUBE_CONFIG_CONTAINER.get(), TubeNetworkConfigGui::new);
    }

    public void setup(FMLCommonSetupEvent event) {

    }

}
