package com.TheRPGAdventurer.ROTD.server.initialization;

import com.TheRPGAdventurer.ROTD.server.blocks.BlockDragonNest;
import net.minecraft.block.Block;

public class ModBlocks {
	
	public static Block pileofsticks = new BlockDragonNest("pileofsticks");
//	public static Block dragon_shulker_box = new BlockDragonShulker();
	//public static Block pileofstickssnow = new BlockDragonNest("pileofstickssnow");
	
	public static final Block[] BLOCKS = {
		pileofsticks,
//			dragon_shulker_box//pileofstickssnow,

	};
	
}