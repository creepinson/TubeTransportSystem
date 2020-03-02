package me.creepinson.tubesplus.block;

import me.creepinson.creepinoutils.base.BaseBlock;
import me.creepinson.creepinoutils.base.BaseBlockWithTile;
import me.creepinson.tubesplus.tile.TileEntityGravityController;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
public class BlockGravityController extends BaseBlockWithTile {

    public BlockGravityController(Material mat, ResourceLocation name, CreativeTabs tab) {
        super(mat, name, tab);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGravityController();
    }
}
