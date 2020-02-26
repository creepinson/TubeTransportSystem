package me.creepinson.mod.tile;

import me.creepinson.mod.TubeNetwork;
import me.creepinson.mod.api.IConnectable;
import me.creepinson.mod.api.util.CreepinoUtils;
import me.creepinson.mod.api.util.math.Vector3;
import me.creepinson.mod.block.BlockTube;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project randomlyaa
 **/
public class TileEntityTube extends TileEntity implements IConnectable {
    private TubeNetwork network;

    public TubeNetwork getNetwork() {
        return network;
    }

    public void setNetwork(TubeNetwork newNet) {
        this.network = newNet;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (network == null) {
            ((TileEntityTube) world.getTileEntity(pos)).setNetwork(new TubeNetwork(world));
        }
        network.refreshConnectedTubes(new Vector3(pos));
    }

    public boolean canConnectToStrict(IBlockAccess world, Vector3 pos, EnumFacing side) {
        return world.getBlockState(pos.add(side.getXOffset(), side.getYOffset(), side.getZOffset()).toBlockPos()).getBlock() instanceof BlockTube && CreepinoUtils.getBlockMetadata(world, pos.toBlockPos().add(side.getXOffset(), side.getYOffset(), side.getZOffset())) == CreepinoUtils.getBlockMetadata(world, pos.toBlockPos());
    }

    @Override
    public boolean canConnectTo(IBlockAccess world, Vector3 pos, EnumFacing side) {
        return world.getBlockState(pos.add(side.getXOffset(), side.getYOffset(), side.getZOffset()).toBlockPos()).getBlock() instanceof BlockTube;
//        return world.getBlockState(pos.toBlockPos()).getBlock() == this && CreepinoUtils.getBlockMetadata(world, pos.toBlockPos().add(side.getXOffset(), side.getYOffset(), side.getZOffset())) ==  CreepinoUtils.getBlockMetadata(world, pos.toBlockPos());
    }
}
