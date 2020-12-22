package me.creepinson.tubesplus.tile;

import com.theoparis.creepinoutils.util.api.IConnectable;
import com.theoparis.creepinoutils.util.text.GroupTextComponent;
import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.block.TubeBlock;
import me.creepinson.tubesplus.gui.container.TubeNetworkConfigContainer;
import me.creepinson.tubesplus.handler.TubesPlusRegistryHandler;
import me.creepinson.tubesplus.util.TubeNetwork;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/
public class TubeTile extends TileEntity implements INamedContainerProvider, IConnectable {
    private TubeNetwork network;

    public TubeTile() {
        super(TubesPlusRegistryHandler.TUBE_TILE.get());
    }

    @Override
    public void setWorldAndPos(@NotNull World world, @NotNull BlockPos pos) {
        super.setWorldAndPos(world, pos);
        this.setNetwork(new TubeNetwork(world));
    }

    public TubeNetwork getNetwork() {
        return network;
    }

    public void setNetwork(TubeNetwork newNet) {
        this.network = newNet;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.refresh();
        if (this.getNetwork() != null) {
//            this.getNetwork().setSpeed(tmpSpeed == 0 ? this.getNetwork().getSpeed() : tmpSpeed);
            this.getNetwork().refreshConnectedTubes(pos);
            refresh();
        }
    }

    @Override
    public @NotNull CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public @NotNull CompoundNBT write(@NotNull CompoundNBT compound) {
        super.write(compound);
        if (this.getNetwork() != null)
            compound.put("network", this.getNetwork().serializeNBT());


        return compound;
    }

    @Override
    public void read(@NotNull BlockState state, @NotNull CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("network") && this.network != null) {
            getNetwork().deserializeNBT(nbt.getCompound("network"));
        }
    }


    @Override
    public boolean canConnectToStrict(@NotNull IWorld world, @NotNull BlockPos pos, Direction side) {
        return world.getBlockState(pos.add(Objects.requireNonNull(side).getXOffset(), side.getYOffset(), side.getZOffset())).getBlock() instanceof TubeBlock && world.getBlockState(pos.add(side.getXOffset(), side.getYOffset(), side.getZOffset())) == world.getBlockState(pos);
    }

    @Override
    public boolean canConnectTo(@NotNull IWorld world, @NotNull BlockPos pos, Direction side) {
        return world.getBlockState(pos.add(Objects.requireNonNull(side).getXOffset(), side.getYOffset(), side.getZOffset())).getBlock() instanceof TubeBlock;
    }

    public void refresh() {
        if (this.getNetwork() != null) {
            this.getNetwork().refreshConnectedTubes(pos);
            Iterator<BlockPos> it = this.getNetwork().getTubes().iterator();
            while (it.hasNext()) {
                BlockPos v = it.next();
                if (world != null) {
                    TileEntity te = world.getTileEntity(v);

                    if (te == null || te.isRemoved()) {
                        it.remove();
                    }

                    if (te instanceof TubeTile) {
                        TubeTile t = (TubeTile) te;
                        if (t.getNetwork() != null) {
                            t.getNetwork().setSpeed(this.getNetwork().getSpeed());
                            t.getNetwork().isInverted = this.getNetwork().isInverted;
                        }
                    }
                }
            }
        }
    }

    public void updateConnectedBlocks() {
        List<TubeTile> tubes = new ArrayList<>();
        if (world != null) {
            for (Direction f : Direction.values()) {
                TileEntity t = world.getTileEntity(pos.offset(f));
                if (t instanceof TubeTile) {
                    tubes.add((TubeTile) t);
                }
            }
            if (!tubes.isEmpty()) {
                for (TubeTile tile : tubes) {
                    if (tile != null && !tile.isRemoved()) {
                        if (tile.getNetwork() != null && this.getNetwork() == null) {
                            tile.getNetwork().refreshConnectedTubes(pos);
                            this.setNetwork(tile.getNetwork());
                            if (!world.isRemote)
                                TubesPlus.LOGGER.info("Connecting to existing network at " + tile.getPos());
                        }
                    }
                }
            } else {
                if (this.getNetwork() == null) {
                    this.setNetwork(new TubeNetwork(world));
                    this.getNetwork().refreshConnectedTubes(pos);
                    if (!world.isRemote)
                        TubesPlus.LOGGER.info("Creating new network at " + this.getPos());
                }
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int id, @NotNull PlayerInventory playerInventory, @NotNull PlayerEntity player) {
        return new TubeNetworkConfigContainer(id, playerInventory, pos);
    }

    @Override
    public @NotNull ITextComponent getDisplayName() {
        return new GroupTextComponent().string("Tube Network Configuration");
    }

}
