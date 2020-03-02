package me.creepinson.tubesplus.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/

public class GravityProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(IGravity.class)
    public static final Capability<IGravity> GRAVITY_CAP = null;

    private IGravity instance = GRAVITY_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == GRAVITY_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == GRAVITY_CAP ? GRAVITY_CAP.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return GRAVITY_CAP.getStorage().writeNBT(GRAVITY_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        GRAVITY_CAP.getStorage().readNBT(GRAVITY_CAP, this.instance, null, nbt);
    }
}