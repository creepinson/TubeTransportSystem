package me.creepinson.mod.block;

import me.creepinson.mod.CommonProxy;
import me.creepinson.mod.api.IConnectable;
import me.creepinson.mod.api.util.CreepinoUtils;
import me.creepinson.mod.api.util.math.Vector3;
import me.creepinson.mod.base.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project randomlyaa
 **/
public class BlockStation extends BaseBlock implements IConnectable {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final int SHIFT = 8;

    public BlockStation(Material mat, ResourceLocation name, CreativeTabs tab) {
        super(mat, name, tab);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).ordinal();
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity == null)
            return;
        int meta = CreepinoUtils.getBlockMetadata(world, pos);

        if (!entity.isSneaking() && meta >= SHIFT && world.getBlockState(pos.add(0, 1, 0)).getBlock() == CommonProxy.BLOCK_TUBE && CreepinoUtils.getBlockMetadata(world, pos.add(0, 1, 0)) == EnumFacing.UP.ordinal()) {
            CreepinoUtils.entityAccelerate(entity, EnumFacing.UP);
            CreepinoUtils.entityAccelerate(entity, EnumFacing.UP);
        }

        CreepinoUtils.entityLimitSpeed(entity, 0.5);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {

        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return !canConnectTo(blockAccess, new Vector3(pos), CreepinoUtils.getDirectionFromSide(new Vector3(pos), side.ordinal()));
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, @Nullable Entity entity, boolean isActualState) {
        if (entity == null)
            return;
        int meta = CreepinoUtils.getBlockMetadata(world, pos);
        List<AxisAlignedBB> axis = new ArrayList<AxisAlignedBB>();

        if (meta == EnumFacing.NORTH.ordinal() || meta == EnumFacing.NORTH.ordinal() + SHIFT) {
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.SOUTH));
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.EAST));
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.WEST));
        } else if (meta == EnumFacing.SOUTH.ordinal() || meta == EnumFacing.SOUTH.ordinal() + SHIFT) {
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.NORTH));
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.EAST));
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.WEST));
        } else if (meta == EnumFacing.EAST.ordinal() || meta == EnumFacing.EAST.ordinal() + SHIFT) {
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.WEST));
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.NORTH));
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.SOUTH));
        } else if (meta == EnumFacing.WEST.ordinal() || meta == EnumFacing.WEST.ordinal() + SHIFT) {
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.EAST));
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.NORTH));
            axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.SOUTH));
        }

        if (meta >= SHIFT) { // top
            if (entity.isSneaking() && CreepinoUtils.getBlockMetadata(world, pos.add(0, 1, 0)) == EnumFacing.UP.ordinal())
                axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.UP));
            else if (world.getBlockState(pos.add(0, 1, 0)).getBlock() != CommonProxy.BLOCK_TUBE)
                axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), EnumFacing.UP));
        } else if (entity.posY >= pos.getY())
            if (entity.isSneaking() && CreepinoUtils.getBlockMetadata(world, pos.subtract(new BlockPos(0, 1, 0))) == EnumFacing.DOWN.ordinal())
                axis.add(CreepinoUtils.getCollisionBoxPartFloor(new Vector3(pos)));
            else if (world.getBlockState(pos.subtract(new BlockPos(0, 1, 0))).getBlock() != CommonProxy.BLOCK_TUBE)
                axis.add(CreepinoUtils.getCollisionBoxPartFloor(new Vector3(pos)));
            else if (CreepinoUtils.getBlockMetadata(world, pos.subtract(new BlockPos(0, 1, 0))) != EnumFacing.DOWN.ordinal())
                axis.add(CreepinoUtils.getCollisionBoxPartFloor(new Vector3(pos)));

        for (AxisAlignedBB a : axis)
            if (a != null && axisAlignedBB.intersects(a))
                list.add(a);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canConnectTo(IBlockAccess blockAccess, Vector3 pos, EnumFacing d) {
        if (d != EnumFacing.UP && d != EnumFacing.DOWN)
            return false;
        Block block = blockAccess.getBlockState(new Vector3(d.getXOffset(), d.getYOffset(), d.getZOffset()).toBlockPos()).getBlock();
        int meta = CreepinoUtils.getBlockMetadata(blockAccess, pos.add(d.getXOffset(), d.getYOffset(), d.getZOffset())), thisMeta = CreepinoUtils.getBlockMetadata(blockAccess, pos);
        return block == this && thisMeta >= SHIFT ? meta == thisMeta - SHIFT : thisMeta + SHIFT == meta;

    }

    @Override
    public boolean canConnectToStrict(IBlockAccess world, Vector3 pos, EnumFacing d) {
        return canConnectTo(world, pos, d);
    }
}
