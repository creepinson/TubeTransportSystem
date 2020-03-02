package me.creepinson.tubesplus.util;

import net.minecraft.entity.Entity;

public interface IAttractableTileEntity {
  GravityDirection getCurrentGravity(Entity paramEntity);
  
  boolean isStillInAttractedState(Entity paramEntity);
}
