package me.blackfur.ferociousforgery.items;

import com.mojang.datafixers.util.Either;
import me.blackfur.ferociousforgery.FerociousForgery;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgingBlankRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    boolean hasRendered = false;
    BakedModel cache;

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(FerociousForgery.FORGING_BLANK)) {
            if (!hasRendered) {
                Map<String, Either<SpriteIdentifier, String>> tmpMap = new HashMap<>();
                tmpMap.put("layer0", Either.left(
                        new SpriteIdentifier(new Identifier("minecraft:textures/atlas/blocks.png"),
                                             new Identifier("minecraft:item/iron_shovel"))));
                BakedModel handheldModel = MinecraftClient.getInstance().getBakedModelManager().getModel(
                        new ModelIdentifier("minecraft", "iron_shovel", "inventory"));
                JsonUnbakedModel tmpModel = ModelLoader.ITEM_MODEL_GENERATOR.create((spriteIdentifier -> {
                    return new SpriteIdentifier(new Identifier("minecraft:textures/atlas/blocks.png"),
                                                new Identifier("minecraft:item/iron_shovel")).getSprite();

                }), new JsonUnbakedModel(null, new ArrayList<>(), tmpMap, null, null, handheldModel.getTransformation(),
                                         new ArrayList<>()));
                this.cache = tmpModel.bake(
                        new ModelLoader(null, MinecraftClient.getInstance().getProfiler(), null, null).new BakerImpl(
                                (id, spriteId) -> {
                                    return new SpriteIdentifier(new Identifier("minecraft:textures/atlas/blocks.png"),
                                                                new Identifier("minecraft:item/iron_shovel")).getSprite();
                                }, new Identifier("troll:troll")), (id) -> {
                            return new SpriteIdentifier(new Identifier("minecraft:textures/atlas/blocks.png"),
                                                        new Identifier("minecraft:item/iron_shovel")).getSprite();
                        }, ModelRotation.X0_Y0, new Identifier("troll:troll"));
                this.hasRendered = true;
            } else {
                return;
            }
        }
    }
}
