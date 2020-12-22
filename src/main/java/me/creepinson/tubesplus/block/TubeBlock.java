package me.creepinson.tubesplus.block;

import com.theoparis.creepinoutils.util.BlockUtils;
import com.theoparis.creepinoutils.util.CreepinoUtils;
import com.theoparis.creepinoutils.util.VelocityUtil;
import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.event.TubeTriggerEvent;
import me.creepinson.tubesplus.handler.TubesPlusRegistryHandler;
import me.creepinson.tubesplus.tile.TubeTile;
import me.creepinson.tubesplus.util.TubeNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/
public class TubeBlock extends DirectionalBlock {
    public TubeBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (MinecraftForge.EVENT_BUS.post(new TubeTriggerEvent(world, pos, state, entity))) return;
        BlockUtils.INSTANCE.getTile(world, pos).filter(t -> t instanceof TubeTile).map(t -> (TubeTile) t).ifPresent(tile -> {
            TubeNetwork network = tile.getNetwork();

            if (network == null) {
                if (!world.isRemote)
                    TubesPlus.LOGGER.warn("Tube network at " + pos.toString() + " is null!");

                VelocityUtil.INSTANCE.accelerate(entity, state.get(FACING), 0.1F);
            } else {
//            double entitySpeed = Math.abs(Math.sqrt(entity.getMotion().x * entity.e + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ));
                double speed = network.getSpeed();
                if (network.isInverted)
                    VelocityUtil.INSTANCE.accelerate(entity, state.get(FACING).getOpposite(), speed);
                else
                    VelocityUtil.INSTANCE.accelerate(entity, state.get(FACING), speed);

                //                TubesPlus.LOGGER("Speed: " + network.getSpeed());
                //            CreepinoUtils.entityLimitSpeed(entity, network.getSpeed()*speed);

            }
            if (world.isAirBlock(pos.offset(Direction.DOWN)) && state.get(FACING) != Direction.UP && state.get(FACING) != Direction.DOWN) {
                entity.setMotion(entity.getMotion().x, 0, entity.getMotion().z);
                entity.velocityChanged = true;
            }

            CreepinoUtils.INSTANCE.resetEntityFall(entity);
        });
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getFace());
    }


/*    @Override
    public void addCollisionBoxToList(BlockState state, World world, BlockPos pos, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, @Nullable Entity entity, boolean isActualState) {
        if (entity == null)
            return;

        Direction dir = state.get(FACING);
        List<AxisAlignedBB> axis = new ArrayList<AxisAlignedBB>();

        for (Direction d : Direction.values())
            if (!((TileEntityTube) world.getTileEntity(pos)).canConnectTo(world, new Vector3(pos), d)) {
                if ((dir == Direction.UP || dir == Direction.DOWN) && (d == Direction.UP || d == Direction.DOWN))
                    continue;
                if ((dir == Direction.NORTH || dir == Direction.SOUTH) && (d == Direction.NORTH || d == Direction.SOUTH))
                    continue;
                if ((dir == Direction.EAST || dir == Direction.WEST) && (d == Direction.EAST || d == Direction.WEST))
                    continue;
                axis.add(CreepinoUtils.getCollisionBoxPart(new Vector3(pos), d));
            }

        for (AxisAlignedBB a : axis)
            if (a != null && axisAlignedBB.intersects(a))
                list.add(a);
    }*/

    private static Optional<TubeTile> getTE(World world, BlockPos pos) {
        return BlockUtils.INSTANCE.getTile(world, pos).filter(tile -> tile instanceof TubeTile).map(tile -> (TubeTile) tile);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        getTE(world, pos).ifPresent(tile -> {
            /*TubeNetwork network = ((TubeTile) tile).getNetwork();
            if (network != null) {
                network.refreshConnectedTubes(fromPos);
            }*/
            ((TubeTile) tile).updateConnectedBlocks();
        });
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) getTE(world, pos).map(tile -> {
            if (player.getHeldItem(hand) != ItemStack.EMPTY) return tile;

            if (player.isSneaking())
                NetworkHooks.openGui((ServerPlayerEntity) player, tile, extraData -> {
                    extraData.writeBlockPos(pos);
                });

            return tile;
        });
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, world, pos, oldState, isMoving);
        if (!world.isRemote)
            getTE(world, pos).ifPresent(TubeTile::updateConnectedBlocks);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TubesPlusRegistryHandler.TUBE_TILE.get().create();
    }
}
