package biomesoplenty.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBOPBones extends ItemBlock
{
    private static final String[] types = new String[] {"bones_small", "bones_medium", "bones_large", "bones_medium_side_1", "bones_medium_side_2"};
    
    public ItemBOPBones(int par1)
    {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(int meta)
    {
        return meta & 15;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int meta = itemstack.getItemDamage();
        if (meta < 0 || meta >= types.length)
            meta = 0;
        
        return types[meta];
    }
    
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        int id = world.getBlockId(x, y, z);

        if (id == Block.snow.blockID && (world.getBlockMetadata(x, y, z) & 7) < 1)
        {
            side = 1;
        }
        else if (id != Block.vine.blockID && id != Block.tallGrass.blockID && id != Block.deadBush.blockID
                && (Block.blocksList[id] == null || !Block.blocksList[id].isBlockReplaceable(world, x, y, z)))
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }
        }

        if (itemstack.stackSize == 0)
        {
            return false;
        }
        else if (!player.canPlayerEdit(x, y, z, side, itemstack))
        {
        	return false;
        }
        else if (y == 255 && Block.blocksList[this.itemID].blockMaterial.isSolid())
        {
        	return false;
        }
        else if (world.canPlaceEntityOnSide(this.itemID, x, y, z, false, side, player, itemstack))
        {	
        	Block block = Block.blocksList[this.itemID];
        	int j1 = this.getMetadata(itemstack.getItemDamage());
        	
        	if (itemstack.getItemDamage() == 1)
        	{
        		if (side == 2 || side == 3)
        		{
        			j1 = 3;
        		}

        		if (side == 4 || side == 5)
        		{
        			j1 = 4;
        		}
        	}
        	
        	int k1 = Block.blocksList[this.itemID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, j1);

        	if (placeBlockAt(itemstack, player, world, x, y, z, side, par8, par9, par10, k1))
        	{	
        		world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
        		--itemstack.stackSize;
        	}

        	return true;
        }
        else
        {
        	return false;
        }
    }
}
