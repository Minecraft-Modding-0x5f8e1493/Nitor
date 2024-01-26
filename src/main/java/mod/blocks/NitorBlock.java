package mod.blocks;

import mod.blocks.entities.NitorEntity;
import mod.client.NitorRenderer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NitorBlock extends Block implements BlockEntityProvider {
    public NitorBlock(DyeColor color) {
        super(FabricBlockSettings.create()
                .sounds(BlockSoundGroup.WOOL)
                .mapColor(color)
                .luminance(15)
                .noBlockBreakParticles()
        );
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (!world.isClient)
            dropStack(world, pos, new ItemStack(this));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NitorEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient)
            return (world1, pos, state1, blockEntity) -> NitorRenderer.entityTick(world1, pos, state, blockEntity);

        throw new RuntimeException("World not is client!");
    }
}
