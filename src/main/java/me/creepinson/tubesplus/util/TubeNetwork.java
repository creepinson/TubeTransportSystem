package me.creepinson.tubesplus.util;


import me.creepinson.creepinoutils.api.creepinoutilscore.math.Vector3;
import me.creepinson.creepinoutils.util.network.BaseNetwork;
import me.creepinson.creepinoutils.util.util.BlockUtils;
import me.creepinson.creepinoutils.util.util.math.ForgeVector;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/
public class TubeNetwork extends BaseNetwork<TileEntityTube> {
    public static final double INCREMENT = 0.01;
    private Set<Vector3> tubes;
    public final double maxSpeed = 1;
    public final double minSpeed = 0.01;
    private double speed = 0.1;
    public static final double maxAcceleration = 5;
    public boolean isInverted;

    public TubeNetwork(@Nonnull World world) {
        super(world);
        this.tubes = new HashSet<>();
    }

/*    public Set<Vector3> getDestinations() {
        return tubes.stream().filter(t -> world.getBlockState(t.toBlockPos()) instanceof BlockDestinationTube).collect(Collectors.toSet());
    }*/

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = super.serializeNBT();
        nbt.setDouble("speed", this.speed);
        nbt.setBoolean("inverted", this.isInverted);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        this.speed = nbt.getDouble("speed");
        this.isInverted = nbt.getBoolean("inverted");
    }

    public void routeEntity(Entity entity) {
        // TODO: add destination routing
    }

    @Override
    public void refresh() {
        super.refresh();
    }

    public void refreshConnectedTubes(Vector3 startingPosition) {
        tubes = BlockUtils.getTiles(world, new ForgeVector(startingPosition), TileEntityTube.class);
    }

    public Set<Vector3> getTubes() {
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
}
