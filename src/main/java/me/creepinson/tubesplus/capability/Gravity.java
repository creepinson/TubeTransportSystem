package me.creepinson.tubesplus.capability;

import me.creepinson.tubesplus.util.GravityDirection;
import me.creepinson.tubesplus.util.IAttractableTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class Gravity implements IGravity {
    private boolean isAttracted = false;
    private BlockPos attractedPos = BlockPos.ORIGIN;
    private GravityDirection gravityDirection = GravityDirection.upTOdown_YN;

    private static final float TURN_SPEED_START = 0.05F;

    public float turnRate = 100.0F;

    public float prevTurnRate = 100.0F;

    public float turnSpeed = 0.0F;

    public float onChangeRoatDirX = 0.0F;

    public float onChangeRoatDirY = 0.0F;

    public float onChangeRoatDirZ = 0.0F;

    public float onChangeTurnYaw = 0.0F;

    private IAttractableTileEntity attractedBy;

    public static NBTTagCompound saveNBTData(IGravity gravity) {
        NBTTagCompound myNBT = new NBTTagCompound();
        myNBT.setFloat("turnRate", gravity.getTurnRate());
        myNBT.setFloat("turnSpeed", gravity.getTurnSpeed());
        myNBT.setBoolean("isAttracted", gravity.isAttracted());
        myNBT.setInteger("attractedPosX", gravity.getAttractedPosition().getX());
        myNBT.setInteger("attractedPosY", gravity.getAttractedPosition().getY());
        myNBT.setInteger("attractedPosZ", gravity.getAttractedPosition().getZ());
        return myNBT;
    }

    public static void loadNBTData(NBTTagCompound compound, IGravity gravity) {
        if (compound.hasKey("gravity")) {
            NBTTagCompound myNBT = compound.getCompoundTag("gravity");
            gravity.setTurnRate(myNBT.getFloat("turnRate"));
            gravity.setTurnSpeed(myNBT.getFloat("turnSpeed"));
            gravity.setAttracted(myNBT.getBoolean("isAttracted"));
            gravity.setAttractedPosition(new BlockPos(myNBT.getInteger("attractedPosX"), myNBT.getInteger("attractedPosY"), myNBT.getInteger("attractedPosZ")));
        }
    }

    @Override
    public boolean isAttracted() {
        return this.isAttracted;
    }

    @Override
    public void setAttracted(boolean val) {
        this.isAttracted = val;
    }

    @Override
    public GravityDirection getDirection() {
        return gravityDirection;
    }

    @Override
    public void setDirection(GravityDirection val) {
        this.gravityDirection = val;
    }

    @Override
    public BlockPos getAttractedPosition() {
        return this.attractedPos;
    }

    @Override
    public void setAttractedPosition(BlockPos val) {
        this.attractedPos = val;
    }

    @Override
    public boolean isZeroGravity() {
        return !isAttracted();
    }

    @Override
    public float getTurnRate() {
        return turnRate;
    }

    @Override
    public void setTurnRate(float val) {
        this.turnRate = val;
    }

    @Override
    public float getTurnSpeed() {
        return this.turnSpeed;
    }

    @Override
    public void setTurnSpeed(float val) {
        this.turnSpeed = val;
    }

    @Override
    public boolean setAttractedBy(IAttractableTileEntity tile) {
        this.attractedBy = tile;
        this.setAttracted(tile != null);
        return true;
    }

    @Override
    public boolean isAttractedBy(IAttractableTileEntity tile) {
        return this.isAttracted() && tile != null;
    }

    @Override
    public float getPreviousTurnRate() {
        return prevTurnRate;
    }

    @Override
    public void setPreviousTurnRate(float val) {
        this.prevTurnRate = val;
    }

    public Gravity() {

    }
}