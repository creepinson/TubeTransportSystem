package me.creepinson.tubesplus.util;


import com.theoparis.creepinoutils.util.BlockUtils;
import me.creepinson.tubesplus.tile.TubeTile;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/
public class TubeNetwork implements INBTSerializable<CompoundNBT>, Serializable {
	private static final long serialVersionUID = 1L;
	public static final double INCREMENT = 0.01;
    public World world;
    private Set<BlockPos> tubes;
    public final double maxSpeed = 10;
    public final double minSpeed = 0.01;
    private double speed = 0.1;
    public static final double maxAcceleration = 5;
    public boolean isInverted;
    public BlockPos destination = BlockPos.ZERO;
    
    public TubeNetwork(@Nullable World world) {
        this.world = world;
        this.tubes = new HashSet<>();
    }

    public TubeNetwork() {
        this(null);
    }

/*    public Set<Vector3> getDestinations() {
        return tubes.stream().filter(t -> world.getBlockState(t.toBlockPos()) instanceof BlockDestinationTube).collect(Collectors.toSet());
    }*/

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("speed", this.speed);
        nbt.putBoolean("inverted", this.isInverted);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.speed = nbt.getDouble("speed");
        this.isInverted = nbt.getBoolean("inverted");
    }

    public void routeEntity(Entity entity) {
        // TODO: add destination routing
    }

    public void refreshConnectedTubes(BlockPos startingPosition) {
        tubes = BlockUtils.INSTANCE.getTiles(world, startingPosition, TubeTile.class);
    }

    public Set<BlockPos> getTubes() {
        return tubes;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if (speed < minSpeed) speed = minSpeed;
        if (speed > maxSpeed) speed = maxSpeed;
        this.speed = speed;
    }

    public World getWorld() {
        return world;
    }
}
