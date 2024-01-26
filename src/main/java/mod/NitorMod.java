package mod;

import mod.blocks.NitorBlock;
import mod.blocks.entities.NitorEntity;
import mod.utils.ColorUtil;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JIngredients;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NitorMod implements ModInitializer {
    public static final String MOD_ID = "nitormod";

    private static final JState NITOR_BASE_STATE = JState.state(JState.variant(JState.model("minecraft:block/air")));
    private static final JModel NITOR_BASE_ITEM_MODEL = JModel.model("item/generated").noAmbientOcclusion();
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID);
    public static final List<Block> NITOR_BLOCKS = new ArrayList<>();
    public static BlockEntityType<NitorEntity> NITOR;

    @Override
    public void onInitialize() {
        for (DyeColor color : DyeColor.values()) {
            NITOR_BLOCKS.add(registerNitor(new Identifier(MOD_ID, String.format("nitor_%s", color.getName())), color));
        }

        NITOR = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "nitor"),
                BlockEntityType.Builder
                        .create(NitorEntity::new, NITOR_BLOCKS.toArray(new Block[0]))
                        .build(null)
        );

        Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "group"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.nitormod.nitor"))
                        .icon(() -> NitorMod.NITOR_BLOCKS.get(ThreadLocalRandom.current().nextInt(0, NitorMod.NITOR_BLOCKS.size() - 1)).asItem().getDefaultStack())
                        .entries(((displayContext, entries) -> NitorMod.NITOR_BLOCKS.forEach(entries::add)))
                        .build()
        );

        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
    }

    private Block registerNitor(@NotNull Identifier identifier, @NotNull DyeColor color) {
        RESOURCE_PACK.addRecoloredImage(new Identifier(MOD_ID, "item/" + identifier.getPath()),
                getClass().getResourceAsStream("/assets/nitormod/textures/item/nitor.png"),
                (operand -> ColorUtil.tint(operand, color.getMapColor().color)));

        NITOR_BASE_ITEM_MODEL.textures(JModel.textures().layer0(MOD_ID + ":item/" + identifier.getPath()));

        RESOURCE_PACK.addModel(NITOR_BASE_ITEM_MODEL,
                new Identifier(identifier.getNamespace(), "item/" + identifier.getPath()));

        RESOURCE_PACK.addBlockState(NITOR_BASE_STATE, identifier);

        NitorBlock blockNitor = Registry.register(Registries.BLOCK, identifier, new NitorBlock(color));
        BlockItem itemNitor = Registry.register(Registries.ITEM, identifier, new BlockItem(blockNitor, new Item.Settings()));

        RESOURCE_PACK.addRecipe(identifier, JRecipe.shapeless(
                JIngredients.ingredients()
                        .add(JIngredient.ingredient().item(DyeItem.byColor(color)))
                        .add(JIngredient.ingredient().item(Items.FIRE_CHARGE))
                        .add(JIngredient.ingredient().item(Items.BLAZE_POWDER)),
                JResult.item(itemNitor))
        );

        return blockNitor;
    }
}
