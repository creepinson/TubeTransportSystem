package me.creepinson.tubesplus.capability;

import me.creepinson.tubesplus.util.GravityDirection;
import me.creepinson.tubesplus.util.IAttractableTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * storage capability
 */
public interface IGravity
{
    boolean isAttracted();
    void setAttracted(boolean val);

    GravityDirection getDirection();
    void setDirection(GravityDirection val);

    BlockPos getAttractedPosition();
    void setAttractedPosition(BlockPos val);

    boolean isZeroGravity();

    float getTurnRate();
    void setTurnRate(float val);

    float getTurnSpeed();
    void setTurnSpeed(float val);

    boolean setAttractedBy(IAttractableTileEntity tile);

    boolean isAttractedBy(IAttractableTileEntity tile);

    float getPreviousTurnRate();
    void setPreviousTurnRate(float val);
}