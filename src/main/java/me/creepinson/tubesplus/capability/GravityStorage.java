package me.creepinson.tubesplus.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * This class is responsible for saving and reading mana data from or to server
 */
public class GravityStorage implements IStorage<IGravity>
{
    @Override
    public NBTBase writeNBT(Capability<IGravity> capability, IGravity instance, EnumFacing side)
    {
        return Gravity.saveNBTData((Gravity) instance);
    }

    @Override
    public void readNBT(Capability<IGravity> capability, IGravity instance, EnumFacing side, NBTBase nbt)
    {
        Gravity.loadNBTData((NBTTagCompound) nbt, (Gravity) instance);
    }
}