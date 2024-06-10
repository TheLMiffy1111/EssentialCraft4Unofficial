package essentialcraft.api;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class StructureBlock {

	public Block blk;
	public int metadata;
	public int x, y, z;

	public StructureBlock(Block block, int meta, int x, int y, int z) {
		blk = block;
		metadata = meta;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public StructureBlock(Block block, int meta, BlockPos pos) {
		this(block, meta, pos.getX(), pos.getY(), pos.getZ());
	}
}
