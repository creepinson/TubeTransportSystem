package me.creepinson.tubesplus.tile;

import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.capability.Gravity;
import me.creepinson.tubesplus.capability.GravityProvider;
import me.creepinson.tubesplus.capability.IGravity;
import me.creepinson.tubesplus.client.GravityClientUtils;
import me.creepinson.tubesplus.util.GravityDirection;
import me.creepinson.tubesplus.util.IAttractableTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.List;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
public class TileEntityGravityController extends TileEntity implements IAttractableTileEntity, ITickable {
    public double gravityRange = 10.0D;
    public int attractUpdateTickCount;
    public int type;

    @Override
    public void onLoad() {
        super.onLoad();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGravity(RenderWorldLastEvent event) {
        if(Minecraft.getMinecraft().player.hasCapability(GravityProvider.GRAVITY_CAP, null) && Minecraft.getMinecraft().player.getCapability(GravityProvider.GRAVITY_CAP, null).isAttracted() && Minecraft.getMinecraft().player.getCapability(GravityProvider.GRAVITY_CAP, null).isAttractedBy(this)) {
            GravityClientUtils.orientCameraByGravity(0.03999999910593033F);
        }
        GravityClientUtils.orientCameraByGravity(0.03999999910593033F);

    }


    private static boolean checkAttractedRangeSphere(double centerX, double centerY, double centerZ, double playerX, double playerY, double playerZ, double argAttarctedRange) {
        double xRel = centerX - playerX;
        double zRel = centerZ - playerZ;
        double yRel = centerY - playerY;
        return (Math.sqrt(xRel * xRel + yRel * yRel + zRel * zRel) <= argAttarctedRange);
    }

    private static boolean checkAttractedRangeSquare(double centerX, double centerY, double centerZ, double playerX, double playerY, double playerZ, double argAttarctedRange) {
        return (playerX <= centerX + argAttarctedRange && playerX >= centerX - argAttarctedRange && playerY <= centerY + argAttarctedRange && playerY >= centerY - argAttarctedRange && playerZ <= centerZ + argAttarctedRange && playerZ >= centerZ - argAttarctedRange);
    }

    private static boolean checkAttractedRangeXbaseCylinder(double centerX, double centerY, double centerZ, double playerX, double playerY, double playerZ, double argAttarctedRange) {
        double yRel = centerY - playerY;
        double zRel = centerZ - playerZ;
        return (playerX <= centerX + argAttarctedRange && playerX >= centerX - argAttarctedRange && Math.sqrt(yRel * yRel + zRel * zRel) <= argAttarctedRange);
    }

    private static boolean checkAttractedRangeYbaseCylinder(double centerX, double centerY, double centerZ, double playerX, double playerY, double playerZ, double argAttarctedRange) {
        double xRel = centerX - playerX;
        double zRel = centerZ - playerZ;
        return (playerY <= centerY + argAttarctedRange && playerY >= centerY - argAttarctedRange && Math.sqrt(xRel * xRel + zRel * zRel) <= argAttarctedRange);
    }

    private static boolean checkAttractedRangeZbaseCylinder(double centerX, double centerY, double centerZ, double playerX, double playerY, double playerZ, double argAttarctedRange) {
        double xRel = centerX - playerX;
        double yRel = centerY - playerY;
        return (playerZ <= centerZ + argAttarctedRange && playerZ >= centerZ - argAttarctedRange && Math.sqrt(xRel * xRel + yRel * yRel) <= argAttarctedRange);
    }


    public static boolean inGravityRange(Entity entity, GravityDirection gDirection, double attractCenterX, double attractCenterY, double attractCenterZ, double gravityRange, int type) {
        double playerPosY;
        switch (gDirection) {
            case downTOup_YP:
                playerPosY = entity.posY - (entity.fallDistance / 2.0F);
                break;
            default:
                playerPosY = entity.posY;
        }
        switch (type) {
            case 1:
                return checkAttractedRangeSquare(attractCenterX, attractCenterY, attractCenterZ, entity.posX, playerPosY, entity.posZ, gravityRange);
            case 2:
                return checkAttractedRangeXbaseCylinder(attractCenterX, attractCenterY, attractCenterZ, entity.posX, playerPosY, entity.posZ, gravityRange);
            case 3:
                return checkAttractedRangeYbaseCylinder(attractCenterX, attractCenterY, attractCenterZ, entity.posX, playerPosY, entity.posZ, gravityRange);
            case 4:
                return checkAttractedRangeZbaseCylinder(attractCenterX, attractCenterY, attractCenterZ, entity.posX, playerPosY, entity.posZ, gravityRange);
        }
        return checkAttractedRangeSphere(attractCenterX, attractCenterY, attractCenterZ, entity.posX, playerPosY, entity.posZ, gravityRange);
    }


    private void addEffectsToPlayers() {
        BlockPos center = pos.add(new BlockPos(0.5, 0.5, 0.5));
        AxisAlignedBB axisalignedbb = new AxisAlignedBB(this.pos.getX(), this.pos.getY(), this.pos.getZ(), (this.pos.getX() + 1), (this.getPos().getZ() + 1), (this.getPos().getZ() + 1)).expand(this.gravityRange, this.gravityRange, this.gravityRange);
        List list = this.world.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
        Iterator<EntityPlayer> iterator = list.iterator();
        while (iterator.hasNext()) {
            EntityPlayer entityPlayer = iterator.next();
            if (entityPlayer.isSneaking())
                continue;
            IGravity gravity = entityPlayer.getCapability(GravityProvider.GRAVITY_CAP, null);
            if (gravity == null)
                continue;
            if (inGravityRange((Entity) entityPlayer, gravity.getDirection(), center.getX(), center.getY(), center.getZ(), this.gravityRange, this.type)) {
                if (!gravity.isAttracted()) {
                    attractUpdateTickCount = 1;
                    gravity.setAttractedBy(this);
                    continue;
                }
                if (gravity.isAttractedBy(this) && attractUpdateTickCount % 60 == 0)
                    gravity.setAttractedBy(this);
            }
        }
    }


    public static boolean isGravityReverse(Entity entity, boolean setReverseOff) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
               /* if (itemStack != ItemStack.EMPTY && itemStack.getItem() == SMModContainer.GravityControllerItem) {
                    if (itemStack.hasTagCompound()) {
                        NBTTagCompound tag = itemStack.getTagCompound();
                        boolean gcon = tag.func_74767_n("stmn.g_reverse");
                        if (gcon && setReverseOff)
                            tag.func_74757_a("stmn.g_reverse", false);
                        return gcon;
                    }
                    return false;
                }*/
            }
        }
        return false;
    }


    @Override
    public GravityDirection getCurrentGravity(Entity entity) {
        GravityDirection gravityDirNew;
        double centerX = this.pos.getX() + 0.5D;
        double centerY = this.pos.getY() + 0.5D;
        double centerZ = this.pos.getZ() + 0.5D;
        double xRel = centerX - entity.posX;
        double zRel = centerZ - entity.posZ;
        double yRel = centerY - entity.posY - (entity.lastTickPosY - entity.lastTickPosY / 2.0F);
        boolean reverse = isGravityReverse(entity, false);
        switch (this.type) {
            case 2:
                if (Math.abs(zRel) > Math.abs(yRel)) {
                    if (zRel > 0.0D) {
                        gravityDirNew = reverse ? GravityDirection.northTOsouth_ZP : GravityDirection.southTOnorth_ZN;
                    } else {
                        gravityDirNew = reverse ? GravityDirection.southTOnorth_ZN : GravityDirection.northTOsouth_ZP;
                    }
                } else if (yRel > 0.0D) {
                    gravityDirNew = reverse ? GravityDirection.downTOup_YP : GravityDirection.upTOdown_YN;
                } else {
                    gravityDirNew = reverse ? GravityDirection.upTOdown_YN : GravityDirection.downTOup_YP;
                }
                return gravityDirNew;
            case 3:
                if (Math.abs(xRel) > Math.abs(zRel)) {
                    if (xRel > 0.0D) {
                        gravityDirNew = reverse ? GravityDirection.westTOeast_XP : GravityDirection.eastTOwest_XN;
                    } else {
                        gravityDirNew = reverse ? GravityDirection.eastTOwest_XN : GravityDirection.westTOeast_XP;
                    }
                } else if (zRel > 0.0D) {
                    gravityDirNew = reverse ? GravityDirection.northTOsouth_ZP : GravityDirection.southTOnorth_ZN;
                } else {
                    gravityDirNew = reverse ? GravityDirection.southTOnorth_ZN : GravityDirection.northTOsouth_ZP;
                }
                return gravityDirNew;
            case 4:
                if (Math.abs(xRel) > Math.abs(yRel)) {
                    if (xRel > 0.0D) {
                        gravityDirNew = reverse ? GravityDirection.westTOeast_XP : GravityDirection.eastTOwest_XN;
                    } else {
                        gravityDirNew = reverse ? GravityDirection.eastTOwest_XN : GravityDirection.westTOeast_XP;
                    }
                } else if (yRel > 0.0D) {
                    gravityDirNew = reverse ? GravityDirection.downTOup_YP : GravityDirection.upTOdown_YN;
                } else {
                    gravityDirNew = reverse ? GravityDirection.upTOdown_YN : GravityDirection.downTOup_YP;
                }
                return gravityDirNew;
        }
        if (Math.abs(xRel) > Math.abs(zRel) && Math.abs(xRel) > Math.abs(yRel)) {
            if (xRel > 0.0D) {
                gravityDirNew = !reverse ? GravityDirection.westTOeast_XP : GravityDirection.eastTOwest_XN;
            } else {
                gravityDirNew = !reverse ? GravityDirection.eastTOwest_XN : GravityDirection.westTOeast_XP;
            }
        } else if (Math.abs(zRel) >= Math.abs(xRel) && Math.abs(zRel) > Math.abs(yRel)) {
            if (zRel > 0.0D) {
                gravityDirNew = !reverse ? GravityDirection.northTOsouth_ZP : GravityDirection.southTOnorth_ZN;
            } else {
                gravityDirNew = !reverse ? GravityDirection.southTOnorth_ZN : GravityDirection.northTOsouth_ZP;
            }
        } else if (yRel > 0.0D) {
            gravityDirNew = !reverse ? GravityDirection.downTOup_YP : GravityDirection.upTOdown_YN;
        } else {
            gravityDirNew = !reverse ? GravityDirection.upTOdown_YN : GravityDirection.downTOup_YP;
        }
        return gravityDirNew;
    }


    public boolean isStillInAttractedState(Entity entity) {
        return inGravityRange(entity, entity.getCapability(GravityProvider.GRAVITY_CAP, null).getDirection(), this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, this.gravityRange, this.type);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            addEffectsToPlayers();
        }
    }
}