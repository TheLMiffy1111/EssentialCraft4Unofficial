package essentialcraft.common.block;

import DummyCore.Client.IModelRegisterer;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDreadCactus extends BlockCactus implements IModelRegisterer {

	public BlockDreadCactus() {
		super();
		setSoundType(SoundType.PLANT);
	}

	@Override
	public void onEntityCollision(World p_149670_1_, BlockPos p_149670_2_, IBlockState p_149670_3_, Entity p_149670_5_) {
		super.onEntityCollision(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_5_);
		if(p_149670_5_ instanceof EntityLivingBase) {
			EntityLivingBase base = (EntityLivingBase) p_149670_5_;
			base.addPotionEffect(new PotionEffect(MobEffects.POISON,100,0));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(AGE).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:cacti", "inventory"));
	}
}
