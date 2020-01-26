package krevik.github.io.pep.item;

import net.minecraft.block.Blocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ProgrammedEnderPearl extends EnderPearlItem {
    public ProgrammedEnderPearl(Properties p_i48501_1_) {
        super(p_i48501_1_.maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand p_77659_3_) {
        ItemStack stack = entity.getHeldItem(p_77659_3_);
        world.playSound((PlayerEntity)null, entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        if(stack.getOrCreateChildTag("target_pos")!=null){
            CompoundNBT nbt = stack.getOrCreateChildTag("target_pos");
            int posX=nbt.getInt("target_pos_x");
            int posY=nbt.getInt("target_pos_y");
            int posZ=nbt.getInt("target_pos_z");
            BlockPos targetPos = new BlockPos(posX,posY,posZ);
            if(targetPos!=null) {
                if(!world.isRemote()) {
                    entity.teleportKeepLoaded(targetPos.getX() + 0.5F, targetPos.getY() + 0.5F, targetPos.getZ() + 0.5F);
                }
                entity.getCooldownTracker().setCooldown(this, 20);
                entity.addStat(Stats.ITEM_USED.get(this));
                if (!entity.abilities.isCreativeMode) {
                    stack.shrink(1);
                }
            }
        }

        return new ActionResult(ActionResultType.SUCCESS, stack);
    }

    public void setTargetPos(ItemStack stack,BlockPos target){

        CompoundNBT compoundNBT = stack.getOrCreateChildTag("target_pos");
        compoundNBT.putInt("target_pos_x", target.getX());
        compoundNBT.putInt("target_pos_y", target.getY());
        compoundNBT.putInt("target_pos_z", target.getZ());
    }

    public void setName(ItemStack stack,String name){
        CompoundNBT compoundNBT = stack.getOrCreateChildTag("name");
        compoundNBT.putString("title", name);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        if(stack.getOrCreateChildTag("target_pos")!=null){
            CompoundNBT nbt = stack.getOrCreateChildTag("target_pos");
            int posX=nbt.getInt("target_pos_x");
            int posY=nbt.getInt("target_pos_y");
            int posZ=nbt.getInt("target_pos_z");
            BlockPos targetPos = new BlockPos(posX,posY,posZ);
            if(targetPos!=null) {
                list.add(new StringTextComponent("posX: " + targetPos.getX()));
                list.add(new StringTextComponent("posY: " + targetPos.getY()));
                list.add(new StringTextComponent("posZ: " + targetPos.getZ()));
                if(world!=null) {
                    if (world.isAreaLoaded(targetPos, 5)) {
                        if(targetPos.getY()<0){
                            list.add(new StringTextComponent(I18n.format("pep.caution.no.solid")));
                        }
                        if (!world.getBlockState(targetPos.down()).isSolid()) {
                            if (!world.getBlockState(targetPos.down(2)).isSolid()) {
                                if (!world.getBlockState(targetPos.down(3)).isSolid()) {
                                    if (!world.getBlockState(targetPos.down(4)).isSolid()) {
                                        list.add(new StringTextComponent(I18n.format("pep.caution.no.solid")));
                                    }
                                }
                            }
                        }
                        if (world.getBlockState(targetPos.down()).getBlock() == Blocks.LAVA ||
                                world.getBlockState(targetPos).getBlock() == Blocks.LAVA ||
                                world.getBlockState(targetPos.up()).getBlock() == Blocks.LAVA) {
                            list.add(new StringTextComponent(I18n.format("pep.caution.lava")));
                        }
                        if (world.getBlockState(targetPos).isSolid() && (world.getBlockState(targetPos.up()).isSolid() || world.getBlockState(targetPos.down()).isSolid())) {
                            list.add(new StringTextComponent(I18n.format("pep.caution.suffocation")));
                        }
                    }
                }
            }
        }
        if(stack.getOrCreateChildTag("name")!=null){
            CompoundNBT nbt = stack.getOrCreateChildTag("name");
            String name = nbt.getString("title");
            if(!name.isEmpty()&&name!=null){
                list.add(new StringTextComponent("name: " + name));
            }
        }
    }

}