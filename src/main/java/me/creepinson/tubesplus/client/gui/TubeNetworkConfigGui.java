package me.creepinson.tubesplus.client.gui;

import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.gui.container.ContainerTubeNetworkConfig;
import me.creepinson.tubesplus.network.PacketTubeSpeed;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
@SideOnly(Side.CLIENT)
public class TubeNetworkConfigGui extends GuiContainer //extend GuiContainer if you want your gui to have an inventory
{
    //If you want your gui to change based on TileEntity values, reference the tile entity in the constructor
    //you must pass the tile entity using "return new GuiCustomClass(world.getTileEntity(x, y, z))" in the GuiHandler
    private GuiButton speedSlider;

    public TubeNetworkConfigGui(TileEntityTube te) {
        super(new ContainerTubeNetworkConfig(te));
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        ContainerTubeNetworkConfig container = ((ContainerTubeNetworkConfig) inventorySlots);
        if (container.tile.getNetwork() != null) {
            this.buttonList.add(speedSlider = new GuiSlider(0, (width / 2) - 75, 50, 150, 25, "Tube Speed: ", "", container.tile.getNetwork().minSpeed, container.tile.getNetwork().maxSpeed, container.tile.getNetwork().getSpeed(), true, true, new GuiSlider.ISlider() {
                @Override
                public void onChangeSliderValue(GuiSlider slider) {
                    container.tile.getNetwork().setSpeed(slider.sliderValue);
                    container.tile.updateSpeed();
                    TubesPlus.NETWORK.sendToServer(new PacketTubeSpeed(container.tile.getPos(), slider.sliderValue));
                }
            }));
        }
        /* Parameters:
         * button id used when checking what to do when a button is pressed
         * The X position of the button
         * The Y position of the button
         * The width
         * The height (keep this at 20 if you can)
         * The text to be displayed on the button*/
    }

    @Override
    public void actionPerformed(GuiButton button) {

    }

    @Override
    public void drawScreen(int x, int y, float f) {
        drawDefaultBackground();


        drawRect(width / 4, 0, width - (width / 4), height / 2, Color.gray.getRGB());
        // Make sure your background texture is a multiple of 256x256.
        // The xSizeOfTexture and ySizeOfTexture assume that the texture is 256x256. so 128 and 128 always reference half of the texture.
        // Look in the Gui class to see what else you can do here (like rendering textures and strings)
        String str = "Tube Network Configuration";
        this.drawCenteredString(fontRenderer, str, width / 2, 25, Color.white.getRGB()); //this is where the white variable we set up at the beginning is used
        super.drawScreen(x, y, f);
        /*Here is a trick:
           If you reset the texture after "super.drawScreen(x, y, f);" (this.mc.renderEngine.bindTexture("path/to/the/background/texture"),
           you can draw on top of everything, including buttons.
           Use this to texture buttons, if you don't want them to have text.`
        */
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}