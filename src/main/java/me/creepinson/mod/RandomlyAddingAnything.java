package me.creepinson.mod;

import com.draco18s.hardlib.EasyRegistry;
import me.creepinson.mod.base.BaseMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project forge-mod-template
 **/

@Mod(modid = RandomlyAddingAnything.MOD_ID, name = RandomlyAddingAnything.MOD_NAME, version = RandomlyAddingAnything.MOD_VERSION/*, dependencies = "required-after:"*/)
public class RandomlyAddingAnything extends BaseMod {
    public static final String MOD_ID = "raa", MOD_ID_SHORT = "raa", MOD_NAME = "Randomly Adding Anything", MOD_URL = "", MOD_VERSION = "1.0.0", MOD_DEPENDENCIES = "";
    public static final boolean DEBUG = true; // DEFAULT = false

    // TODO: make creativecore not a requirement

    @Mod.Instance(RandomlyAddingAnything.MOD_ID)
    private static RandomlyAddingAnything INSTANCE;

    public static RandomlyAddingAnything getInstance() {
        return INSTANCE;
    }

    @SidedProxy(clientSide = "me.creepinson.mod.ClientProxy", serverSide = "me.creepinson.mod.CommonProxy")
    public static CommonProxy proxy;

    public RandomlyAddingAnything() {
        super(MOD_URL, MOD_ID, MOD_ID_SHORT, MOD_NAME, MOD_VERSION);

//      this.hasCreativeTab = false;
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
//        GameRegistry.registerTileEntity(TileEntityAnimationTest.class, new ResourceLocation(MOD_ID, "tile_animation_test"));
    }

    @Mod.EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
