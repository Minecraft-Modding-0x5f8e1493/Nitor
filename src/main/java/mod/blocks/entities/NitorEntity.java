package mod.blocks.entities;

import mod.NitorMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class NitorEntity extends BlockEntity {
    public NitorEntity(BlockPos pos, BlockState state) {
        super(NitorMod.NITOR, pos, state);
    }
}
