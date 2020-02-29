package me.creepinson.tubesplus.block;

import me.creepinson.creepinoutils.api.util.CreepinoUtils;
import me.creepinson.creepinoutils.api.util.math.Vector3;
import me.creepinson.creepinoutils.base.BaseBlockWithTile;
import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.TubeNetwork;
import me.creepinson.tubesplus.client.gui.TubeNetworkConfigGui;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/
public class BlockTube extends BaseBlockWithTile {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

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
        state = world.getBlockState(pos);
        TubeNetwork network = ((TileEntityTube) world.getTileEntity(pos)).getNetwork();

        if (network == null) {
            if (!world.isRemote)
                TubesPlus.debug("Tube network at " + pos.toString() + " is null!");

            CreepinoUtils.entityAccelerate(entity, state.getValue(FACING).getOpposite());
            CreepinoUtils.entityLimitSpeed(entity, 0.1);
        } else {
            CreepinoUtils.entityAccelerate(entity, state.getValue(FACING).getOpposite(), network.getSpeed());
            CreepinoUtils.entityLimitSpeed(entity, network.getSpeed());
        }
        if (world.isAirBlock(pos.offset(EnumFacing.DOWN)) && state.getValue(FACING) != EnumFacing.UP && state.getValue(FACING) != EnumFacing.DOWN) {
            entity.motionY = 0;
        }
        entity.velocityChanged = true;

        CreepinoUtils.entityResetFall(entity);

    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (((TileEntityTube) world.getTileEntity(pos)) == null || (((TileEntityTube) world.getTileEntity(pos)) != null && ((TileEntityTube) world.getTileEntity(pos)).isInvalid())) {
            return true;
        }
        return !((TileEntityTube) world.getTileEntity(pos)).canConnectToStrict(world, new Vector3(pos), CreepinoUtils.getDirectionFromSide(new Vector3(pos), side.ordinal()));
    }

/*    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, @Nullable Entity entity, boolean isActualState) {
        if (entity == null)
            return;

        EnumFacing dir = state.getValue(FACING);
        List<AxisAlignedBB> axis = new ArrayList<AxisAlignedBB>();

        for (EnumFacing d : EnumFacing.values())
            if (!((TileEntityTube) world.getTileEntity(pos)).canConnectTo(world, new Vector3(pos), d)) {
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
    }*/

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
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TileEntityTube tile = (TileEntityTube) world.getTileEntity(fromPos);
        if (tile != null && !tile.isInvalid()) {
            TubeNetwork network = tile.getNetwork();
            network.refreshConnectedTubes(new Vector3(fromPos));
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityTube tile = ((TileEntityTube) world.getTileEntity(pos));
        if (tile != null && !tile.isInvalid()) {
            if (player.getHeldItem(hand) != ItemStack.EMPTY) return false;

            if (player.isSneaking()) {
                TubesPlus.debug("TEST");
                player.openGui(TubesPlus.getInstance(), 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileEntityTube) {
                TileEntityTube thisTube = (TileEntityTube) tileEntity;
                thisTube.refresh();

            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTube();
    }
}
