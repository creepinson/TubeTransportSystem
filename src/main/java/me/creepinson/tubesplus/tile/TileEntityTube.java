package me.creepinson.tubesplus.tile;

import me.creepinson.creepinoutils.api.creepinoutilscore.math.Vector3;
import me.creepinson.creepinoutils.util.IConnectable;
import me.creepinson.creepinoutils.util.util.CreepinoUtils;
import me.creepinson.creepinoutils.util.util.math.ForgeVector;
import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.block.BlockTube;
import me.creepinson.tubesplus.gui.container.ContainerTubeNetworkConfig;
import me.creepinson.tubesplus.util.TubeNetwork;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/
public class TileEntityTube extends TileEntity implements IConnectable, IInteractionObject {
    private TubeNetwork network;
    private double tmpSpeed;

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
            this.getNetwork().setWorld(world);
            this.getNetwork().refreshConnectedTubes(new ForgeVector(pos));
            refresh();
        }
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (this.getNetwork() != null) {
            compound.setTag("network", this.getNetwork().serializeNBT());
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.setNetwork(new TubeNetwork(world));
        getNetwork().deserializeNBT(compound.getCompoundTag("network"));
        this.tmpSpeed = getNetwork().getSpeed();

    }

    @Override
    public boolean canConnectToStrict(IBlockAccess world, Vector3 pos, EnumFacing side) {
        return world.getBlockState(new ForgeVector(pos.add(side.getXOffset(), side.getYOffset(), side.getZOffset())).toBlockPos()).getBlock() instanceof BlockTube && CreepinoUtils.getBlockMetadata(world, pos.add(side.getXOffset(), side.getYOffset(), side.getZOffset())) == CreepinoUtils.getBlockMetadata(world, pos);
    }

    @Override
    public boolean canConnectTo(IBlockAccess world, Vector3 pos, EnumFacing side) {
        return world.getBlockState(new ForgeVector(pos.add(side.getXOffset(), side.getYOffset(), side.getZOffset())).toBlockPos()).getBlock() instanceof BlockTube;
//        return world.getBlockState(pos.toBlockPos()).getBlock() == this && CreepinoUtils.getBlockMetadata(world, pos.toBlockPos().add(side.getXOffset(), side.getYOffset(), side.getZOffset())) ==  CreepinoUtils.getBlockMetadata(world, pos.toBlockPos());
    }

    public void updateSpeed() {
        if (this.getNetwork() != null) {
            this.getNetwork().refreshConnectedTubes(new ForgeVector(pos));
            Iterator<Vector3> it = this.getNetwork().getTubes().iterator();
            while (it.hasNext()) {
                Vector3 v = it.next();

                TileEntity te = world.getTileEntity(new ForgeVector(v).toBlockPos());
                if (te == null || te.isInvalid()) {
                    it.remove();
                }

                if (te instanceof TileEntityTube) {
                    TileEntityTube t = (TileEntityTube) te;
                    if (t.getNetwork() != null) {
                        t.getNetwork().setSpeed(this.getNetwork().getSpeed());

                    }
                }
            }
        }
    }

    public void refresh() {
        List<TileEntityTube> tubes = new ArrayList<>();
        for (EnumFacing f : EnumFacing.values()) {
            TileEntity t = world.getTileEntity(pos.offset(f));
            if (t instanceof TileEntityTube) {
                tubes.add((TileEntityTube) t);
            }
        }
        if (!tubes.isEmpty()) {
            for (TileEntityTube tile : tubes) {
                if (tile != null && !tile.isInvalid()) {
                    if (tile.getNetwork() != null && this.getNetwork() == null) {
                        tile.getNetwork().refreshConnectedTubes(new ForgeVector(pos));
                        this.setNetwork(tile.getNetwork());
                        if (!world.isRemote)
                            TubesPlus.getInstance().debug("Connecting to existing network at " + tile.getPos());
                    }
                }
            }
        } else {
            if (this.getNetwork() == null) {
                this.setNetwork(new TubeNetwork(world));
                this.getNetwork().refreshConnectedTubes(new ForgeVector(this.getPos()));
                if (!world.isRemote)
                    TubesPlus.getInstance().debug("Creating new network at " + this.getPos());
            }
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerTubeNetworkConfig(this);
    }

    @Override
    public String getGuiID() {
        return TubesPlus.getInstance().modId + ":tubeconfig";
    }

    @Override
    public String getName() {
        return "Tube Network Configuration";
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }
}
