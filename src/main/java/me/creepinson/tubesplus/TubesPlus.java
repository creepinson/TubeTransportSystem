package me.creepinson.tubesplus;

import com.google.gson.JsonObject;
import me.creepinson.creepinoutils.api.util.GsonUtils;
import me.creepinson.creepinoutils.base.BaseMod;
import me.creepinson.creepinoutils.base.ModConfig;
import me.creepinson.tubesplus.capability.Gravity;
import me.creepinson.tubesplus.capability.GravityStorage;
import me.creepinson.tubesplus.capability.IGravity;
import me.creepinson.tubesplus.client.gui.GuiHandler;
import me.creepinson.tubesplus.handler.RegistryHandler;
import me.creepinson.tubesplus.network.PacketTubeSpeed;
import me.creepinson.tubesplus.tile.TileEntityGravityController;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.io.FileWriter;
import java.util.concurrent.Callable;

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

    @SidedProxy(clientSide = "me.creepinson.tubesplus.ClientProxy", serverSide = "me.creepinson.tubesplus.CommonProxy")
    public static CommonProxy proxy;

    public TubesPlus() {
        super(MOD_URL, MOD_ID, MOD_ID_SHORT, MOD_NAME, MOD_VERSION);
        genConfig = false;
//      this.hasCreativeTab = false;
    }

    public static void debug(String s) {
        if (getInstance().isDebug()) {
            getInstance().getLogger().info(s);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new ModConfig(this, "config") {
            @Override
            public void generate() {

            }

            @Override
            protected JsonObject upgrade(JsonObject json, int version) {

                return json;
            }

            @Override
            protected void load(JsonObject jsonObject) {
                if (jsonObject.has("debug")) {
                    debug = jsonObject.get("debug").getAsBoolean();
                } else {
                    debug = false;
                }

                if(jsonObject.has("gravityEnabled")) {
                    gravityEnabled = jsonObject.get("gravityEnabled").getAsBoolean();
                }

            }

            @Override
            protected void save(FileWriter fileWriter) {
                JsonObject main = new JsonObject();
                main.addProperty("debug", false);
                main.addProperty("configVersion", CURRENT_VERSION);
                main.addProperty("gravityEnabled", true);
                GsonUtils.getGson().toJson(main, fileWriter);
            }
        };
        super.preInit(event, proxy);
//        EasyRegistry.registerBlockWithItem(BlockHandler.ANIMATION_TEST_BLOCK, new ResourceLocation(MOD_ID, "animation_test_block"));
    }

    @Mod.EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        CapabilityManager.INSTANCE.register(IGravity.class, new GravityStorage(), Gravity::new);

        GameRegistry.registerTileEntity(TileEntityTube.class, RegistryHandler.BLOCK_TUBE.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityGravityController.class, RegistryHandler.BLOCK_GRAVITY_CONTROLLER.getRegistryName());
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
