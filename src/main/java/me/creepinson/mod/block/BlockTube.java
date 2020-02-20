package me.creepinson.mod.block;

import me.creepinson.mod.api.IConnectable;
import me.creepinson.mod.api.util.CreepinoUtils;
import me.creepinson.mod.api.util.math.Vector3;
import me.creepinson.mod.base.BaseBlock;
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
public class BlockTube extends BaseBlock implements IConnectable {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockTube(Material mat, ResourceLocation name, CreativeTabs tab) {
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

        CreepinoUtils.entityAccelerate(entity, state.getValue(FACING));
        CreepinoUtils.entityLimitSpeed(entity, 0.5);
        CreepinoUtils.entityResetFall(entity);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

        return !canConnectTo(blockAccess, new Vector3(pos), CreepinoUtils.getDirectionFromSide(new Vector3(pos), side.ordinal()));
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, @Nullable Entity entity, boolean isActualState) {
        if (entity == null)
            return;

        EnumFacing dir = state.getValue(FACING);
        List<AxisAlignedBB> axis = new ArrayList<AxisAlignedBB>();

        for (EnumFacing d : EnumFacing.values())
            if (!canConnectTo(world, new Vector3(pos), d)) {
                if ((dir == EnumFacing.UP || dir == EnumFacing.DOWN) && (d == EnumFacing.UP || d == EnumFacing.DOWN))
                    continue;
                if ((dir == EnumFacing.NORTH || dir == EnumFacing.SOUTH) && (d == EnumFacing.NORTH || d == EnumFacing.SOUTH))
                    continue;
                if ((dir == EnumFacing.EAST || dir == EnumFacing.WEST) && (d == EnumFacing.EAST || d == EnumFacing.WEST))
                    continue;
                axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), d));
            }

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
    public boolean isConnectable() {
        return true;
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
    public void setConnectable(boolean b) {

    }

    @Override
    public boolean canConnectTo(IBlockAccess world, Vector3 pos, EnumFacing side) {
        return world.getBlockState(pos.toBlockPos().add(side.getXOffset(), side.getYOffset(), side.getZOffset())) == world.getBlockState(pos.toBlockPos());
    }
}
