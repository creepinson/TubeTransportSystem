package me.creepinson.tubesplus;

import me.creepinson.creepinoutils.base.BaseMod;
import me.creepinson.tubesplus.client.gui.GuiHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

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

    public static TubesPlus getInstance() {
        return INSTANCE;
    }

    @SidedProxy(clientSide = "me.creepinson.tubesplus.ClientProxy", serverSide = "me.creepinson.tubesplus.CommonProxy")
    public static CommonProxy proxy;

    public TubesPlus() {
        super(MOD_URL, MOD_ID, MOD_ID_SHORT, MOD_NAME, MOD_VERSION);

//      this.hasCreativeTab = false;
    }

    public static void debug(String s) {
        if (getInstance().isDebug()) {
            getInstance().getLogger().info(s);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event, proxy);
//        EasyRegistry.registerBlockWithItem(BlockHandler.ANIMATION_TEST_BLOCK, new ResourceLocation(MOD_ID, "animation_test_block"));
    }

    @Mod.EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());

        //        GameRegistry.registerTileEntity(TileEntityAnimationTest.class, new ResourceLocation(MOD_ID, "tile_animation_test"));
    }

    @Mod.EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
