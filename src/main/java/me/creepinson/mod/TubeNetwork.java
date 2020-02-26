package me.creepinson.mod;

import me.creepinson.mod.api.util.BlockUtils;
import me.creepinson.mod.api.util.math.Vector3;
import me.creepinson.mod.block.BlockTube;
import me.creepinson.mod.tile.TileEntityTube;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project randomlyaa
 **/
public class TubeNetwork {
    public static final double INCREMENT = 0.1;
    public final World world;
    private Set<Vector3> tubes;
    private double speed = 0.1;
    private double maxSpeed = 5;

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
        tubes = BlockUtils.getBlocks(world, startingPosition, BlockTube.class);
        Iterator<Vector3> it = tubes.iterator();
        while(it.hasNext()) {
            Vector3 v = it.next();

            TileEntity te = world.getTileEntity(v.toBlockPos());
            if(te == null || te.isInvalid()) {
                it.remove();
            }

            if(te instanceof TileEntityTube) {
                TileEntityTube tile = (TileEntityTube)te;
                tile.setNetwork(this);
            }
        }
    }

    public Set<Vector3> getTubes() {
        return tubes;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if(speed <= 0) speed = 0.1;
        if(speed >= maxSpeed) speed = maxSpeed;
        this.speed = speed;
    }
}
