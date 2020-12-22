package me.creepinson.tubesplus.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.theoparis.creepinoutils.util.client.gui.Checkbox;
import com.theoparis.creepinoutils.util.text.GroupTextComponent;
import me.creepinson.tubesplus.gui.container.TubeNetworkConfigContainer;
import me.creepinson.tubesplus.handler.TubesPlusRegistryHandler;
import me.creepinson.tubesplus.network.TubeUpdatePacket;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

import java.awt.*;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Tubes Plus Mod
 **/

public class TubeNetworkConfigGui extends ContainerScreen<TubeNetworkConfigContainer> //extend GuiContainer if you want your gui to have an inventory
{
    private Checkbox invert;

    public TubeNetworkConfigGui(TubeNetworkConfigContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void init() {
        this.buttons.clear();
        TubeNetworkConfigContainer container = getContainer();
        if (container.tile.getNetwork() != null) {
            // If you want your gui to change based on TileEntity values, reference the tile entity in the constructor
            // you must pass the tile entity using "return new GuiCustomClass(world.getTileEntity(x, y, z))" in the GuiHandler
            this.buttons.add(new Slider((width / 2) - 75, 50, 150, 25, new StringTextComponent("Tube Speed: "), new StringTextComponent(""), container.tile.getNetwork().minSpeed, container.tile.getNetwork().maxSpeed, container.tile.getNetwork().getSpeed(), true, true, (slider) -> {
                double currentVal = ((Slider) slider).sliderValue;
                container.tile.getNetwork().setSpeed(currentVal);
                container.tile.refresh();
                TubesPlusRegistryHandler.NETWORK.sendToServer(new TubeUpdatePacket(container.tile.getPos(), currentVal, invert.isChecked()));
            }));
            invert = new Checkbox(width / 2 - 75, 85, 50, 50, new StringTextComponent("Invert"), container.tile.getNetwork().isInverted, button -> {
                container.tile.getNetwork().isInverted = !container.tile.getNetwork().isInverted;
                // container.tile.updateSpeed();
                // TODO: update inverting on all tubes in network
                double newValue = -container.tile.getNetwork().getSpeed();
                container.tile.getNetwork().setSpeed(newValue);
                container.tile.refresh();
                TubesPlusRegistryHandler.NETWORK.sendToServer(new TubeUpdatePacket(container.tile.getPos(), newValue, invert.isChecked()));
            });
            this.buttons.add(invert);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // TODO: make it look nicer (gradient?)
        fill(matrixStack, width / 4, 0, width - (width / 4), height / 2, Color.gray.getRGB());
        // Make sure your background texture is a multiple of 256x256.
        // The xSizeOfTexture and ySizeOfTexture assume that the texture is 256x256. so 128 and 128 always reference half of the texture.
        // Look in the Gui class to see what else you can do here (like rendering textures and strings)
        String str = "Tube Network Configuration";
        font.drawString(matrixStack, str, width / 2f, 25f, Color.white.getRGB()); //this is where the white variable we set up at the beginning is used
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}