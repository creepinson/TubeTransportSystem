package me.creepinson.tubesplus.event;

import lombok.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
public class TubeTriggerEvent extends Event {
    protected World world;
    protected BlockPos tubePosition;
    protected BlockState tubeState;
    protected Entity transporting;
}
