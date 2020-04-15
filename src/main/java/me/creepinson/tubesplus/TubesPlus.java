package me.creepinson.tubesplus;

import me.creepinson.creepinoutils.base.BaseMod;
import me.creepinson.creepinoutils.base.CreativeTab;
import me.creepinson.creepinoutils.util.util.CreativeTabCallback;
import me.creepinson.tubesplus.client.gui.GuiHandler;
import me.creepinson.tubesplus.handler.RegistryHandler;
import me.creepinson.tubesplus.network.PacketTubeSpeed;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/

@Mod(modid = TubesPlus.MOD_ID, name = TubesPlus.MOD_NAME, version = TubesPlus.MOD_VERSION/*, dependencies = "required-after:"*/)
public class TubesPlus extends BaseMod {
    public static final String MOD_ID = "tubesplus", MOD_ID_SHORT = "tubes", MOD_NAME = "Tubes Plus", MOD_URL = "", MOD_VERSION = "1.0.0", MOD_DEPENDENCIES = "";
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    @Mod.Instance(TubesPlus.MOD_ID)
    private static TubesPlus INSTANCE;

    // TODO: move to settings file
    private boolean gravityEnabled;

    public static TubesPlus getInstance() {
        return INSTANCE;
    }

    public TubesPlus() {
        super(MOD_URL, null, MOD_ID, MOD_VERSION);
        genConfig = false;
//      this.hasCreativeTab = false;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event, new CreativeTabCallback() {
            @Override
            public void init(CreativeTab tab) {
                tab.setItem(new ItemStack(Items.APPLE));
            }
        });
//        EasyRegistry.registerBlockWithItem(BlockHandler.ANIMATION_TEST_BLOCK, new ResourceLocation(MOD_ID, "animation_test_block"));
    }

    @Mod.EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        GameRegistry.registerTileEntity(TileEntityTube.class, RegistryHandler.BLOCK_TUBE.getRegistryName());
        NETWORK.registerMessage(PacketTubeSpeed.Handler.class, PacketTubeSpeed.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());

        //        GameRegistry.registerTileEntity(TileEntityAnimationTest.class, new ResourceLocation(MOD_ID, "tile_animation_test"));
    }

    @Mod.EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    public boolean isGravityEnabled() {
        return gravityEnabled;
    }
}
