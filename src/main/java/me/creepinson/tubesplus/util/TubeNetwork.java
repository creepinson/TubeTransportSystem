package me.creepinson.tubesplus.util;

import me.creepinson.creepinoutils.api.util.BlockUtils;
import me.creepinson.creepinoutils.api.util.math.Vector3;
import me.creepinson.tubesplus.block.BlockTube;
import me.creepinson.tubesplus.tile.TileEntityTube;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project tubesplus
 **/
public class TubeNetwork {
    public static final double INCREMENT = 0.01;
    public final World world;
    private Set<Vector3> tubes;
    public final double maxSpeed = 10;
    public final double minSpeed = 0.01;
    private double speed = 0.1;

    public TubeNetwork(@Nonnull World world) {
        this.world = world;
        this.tubes = new HashSet<>();
    }

/*    public Set<Vector3> getDestinations() {
        return tubes.stream().filter(t -> world.getBlockState(t.toBlockPos()) instanceof BlockDestinationTube).collect(Collectors.toSet());
    }*/

    public void routeEntity(Entity entity) {

    }

    public void refreshConnectedTubes(Vector3 startingPosition) {
        tubes = BlockUtils.getTiles(world, startingPosition, TileEntityTube.class);
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
