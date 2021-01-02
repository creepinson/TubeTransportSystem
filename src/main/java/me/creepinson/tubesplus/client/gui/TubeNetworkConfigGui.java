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
import net.minecraftforge.fml.network.PacketDistributor;

import java.awt.*;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Tubes Plus Mod
 **/

public class TubeNetworkConfigGui extends ContainerScreen<TubeNetworkConfigContainer> //extend GuiContainer if you want your gui to have an inventory
{
    private Checkbox invert;
    private Slider speedSlider;

    public TubeNetworkConfigGui(TubeNetworkConfigContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = width - (width / 4);
        this.ySize = height / 2;
    }

    @Override
    public void init() {
        this.buttons.clear();
        TubeNetworkConfigContainer container = getContainer();
        if (container.tile.getNetwork() != null) {
            // If you want your gui to change based on TileEntity values, reference the tile entity in the constructor
            // you must pass the tile entity using "return new GuiCustomClass(world.getTileEntity(x, y, z))" in the GuiHandler
            this.addButton(this.speedSlider = new Slider(0, 25, 100, 25, new StringTextComponent("Tube Speed: "), new StringTextComponent(""), container.tile.getNetwork().minSpeed, container.tile.getNetwork().maxSpeed, container.tile.getNetwork().getSpeed(), true, true, (slider) -> {}, (slider) -> {
                double currentVal = this.speedSlider.sliderValue;
                container.tile.getNetwork().setSpeed(currentVal);
                container.tile.refresh();
                TubesPlusRegistryHandler.NETWORK.send(PacketDistributor.SERVER.with(() -> null), new TubeUpdatePacket(container.tile.getPos(), currentVal, invert.isChecked()));
            }));
            invert = new Checkbox(0, 75, 10, 30, new StringTextComponent("Invert"), container.tile.getNetwork().isInverted, button -> {
                container.tile.getNetwork().isInverted = !container.tile.getNetwork().isInverted;
                // container.tile.updateSpeed();
                // TODO: update inverting on all tubes in network
                double newValue = container.tile.getNetwork().isInverted ? -container.tile.getNetwork().getSpeed() : container.tile.getNetwork().getSpeed();
                container.tile.getNetwork().setSpeed(newValue);
                container.tile.refresh();
                TubesPlusRegistryHandler.NETWORK.send(PacketDistributor.SERVER.with(() -> null), new TubeUpdatePacket(container.tile.getPos(), newValue, invert.isChecked()));
            });
            this.addButton(invert);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
       
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // TODO: make it look nicer (gradient?)
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.fillGradient(matrixStack, x, y, 0, 0, Color.gray.getRGB(), Color.darkGray.getRGB());
        font.drawString(matrixStack, this.title.getString(), 8.0f, 6.0f, Color.white.getRGB()); //this is where the white variable we set up at the beginning is used
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}