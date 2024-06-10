package essentialcraft.common.block;

import DummyCore.Client.IBlockColor;
import DummyCore.Client.IModelRegisterer;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class BlockDyeable extends Block implements IBlockColor, IModelRegisterer {

	public static final String[] COLOR_NAMES = {
			"white", "red", "green", "brown", "blue", "purple", "cyan", "lightgray",
			"gray", "pink", "lime", "yellow", "lightblue", "magenta", "orange", "black"};
	public static final int[] COLOR_VALUES = {
			0xF0F0F0, 0xB3312C, 0x3B511A, 0x51301A, 0x253192, 0x7B2FBE, 0x287697, 0xABABAB,
			0x434343, 0xD88198, 0x41CD34, 0xDECF2A, 0x6689D3, 0xC354CD, 0xEB8844, 0x1E1B1B};
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tint) {
		int metadata = state.getValue(COLOR).getDyeDamage();
		if(metadata == 0) {
			metadata = 15;
		}
		else if(metadata == 15) {
			metadata = 0;
		}
		if(metadata == OreDictionary.WILDCARD_VALUE) {
			metadata = 0;
		}
		return COLOR_VALUES[metadata];
	}

	public BlockDyeable(Material material, MapColor mapColor) {
		super(material, mapColor);
		setDefaultState(blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
	}

	public BlockDyeable(Material material) {
		this(material, material.getMaterialMapColor());
	}

	@Override
	public boolean isOpaqueCube(IBlockState s) {
		return material != Material.GLASS;
	}

	@Override
	public boolean isFullCube(IBlockState s) {
		return material != Material.GLASS;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return material == Material.GLASS ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockAccess.getBlockState(pos.offset(side)).getBlock() != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack is = player.getHeldItem(hand);
		if(!is.isEmpty() && OreDictionary.getOreIDs(is).length > 0 && !(is.getItem() instanceof ItemBlock)) {
			for(int i = 0; i < OreDictionary.getOreIDs(is).length; ++i) {
				String oreDictName = OreDictionary.getOreName(OreDictionary.getOreIDs(is)[i]);
				if(oreDictName != null && !oreDictName.isEmpty() && !oreDictName.equalsIgnoreCase("unknown")) {
					int color = -1;
					for(int i1 = 0; i1 < COLOR_NAMES.length; ++i1) {
						String dyeName = "dye"+COLOR_NAMES[i1];
						if(oreDictName.equalsIgnoreCase(dyeName)) {
							color = i1;
							break;
						}
					}
					if(color != -1) {
						if(color == 0) {
							color = 15;
						}
						else if(color == 15) {
							color = 0;
						}
						if(player.isSneaking()) {
							for(BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(2, 2, 2), pos.add(-2, -2, -2))) {
								Block b = world.getBlockState(blockPos).getBlock();
								if(b == this) {
									b.recolorBlock(world, blockPos, side, EnumDyeColor.byDyeDamage(color));
									world.markBlocksDirtyVertical(blockPos.getX(), blockPos.getZ(), blockPos.getY()-2, blockPos.getY()+2);
								}
							}
						}
						else {
							recolorBlock(world, pos, side, EnumDyeColor.byDyeDamage(color));
							world.markBlocksDirtyVertical(pos.getX(), pos.getZ(), pos.getY()-2, pos.getY()+2);
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
		int meta = getMetaFromState(world.getBlockState(pos));
		if(meta == 0) {
			meta = 15;
		}
		else if(meta == 15) {
			meta = 0;
		}
		if(meta != color.getDyeDamage()) {
			meta = color.getDyeDamage();
			if(meta == 0) {
				meta = 15;
			}
			else if(meta == 15) {
				meta = 0;
			}
			world.setBlockState(pos, getStateFromMeta(meta), 2);
			return true;
		}
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int color = meta;
		if(color == 0) {
			color = 15;
		}
		else if(color == 15) {
			color = 0;
		}
		return getDefaultState().withProperty(COLOR, EnumDyeColor.byDyeDamage(color));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int color = state.getValue(COLOR).getDyeDamage();
		if(color == 0) {
			color = 15;
		}
		else if(color == 15) {
			color = 0;
		}
		return color;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, COLOR);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(COLOR).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("essentialcraft:"+getRegistryName().getPath(), "inventory"));
	}
}
