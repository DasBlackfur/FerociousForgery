package me.blackfur.ferociousforgery.items;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import me.blackfur.ferociousforgery.screens.FormingAnvilScreen;
import me.blackfur.ferociousforgery.utility.DummyBaker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.resource.metadata.AnimationFrameResourceMetadata;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgingBlankModelGenerator {
    public static final Map<NbtCompound, BakedModel> MODEL_CACHE = new HashMap<>();
    public static final Map<NbtCompound, Identifier> TEXTURE_CACHE = new HashMap<>();

    public static int getColor(int material) {
        return material;
    }

    public BakedModel getModel(ItemStack stack) {
        if ((!MODEL_CACHE.containsKey(stack.getOrCreateNbt())) || (!TEXTURE_CACHE.containsKey(
                stack.getOrCreateNbt()))) {
            TEXTURE_CACHE.put(stack.getOrCreateNbt(),
                              MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("ff-blank",
                                                                                                       new NativeImageBackedTexture(
                                                                                                               getImage(
                                                                                                                       stack))));
            Map<String, Either<SpriteIdentifier, String>> tmpMap = new HashMap<>();
            tmpMap.put("layer0", Either.left(new SpriteIdentifier(TEXTURE_CACHE.get(stack.getOrCreateNbt()),
                                                                  TEXTURE_CACHE.get(stack.getOrCreateNbt()))));
            BakedModel handheldModel = MinecraftClient.getInstance().getBakedModelManager().getModel(
                    new ModelIdentifier("minecraft", "iron_shovel", "inventory"));
            JsonUnbakedModel tmpModel = ModelLoader.ITEM_MODEL_GENERATOR.create((spriteIdentifier -> getSprite(stack)),
                                                                                new JsonUnbakedModel(null,
                                                                                                     new ArrayList<>(),
                                                                                                     tmpMap, null,
                                                                                                     JsonUnbakedModel.GuiLight.ITEM,
                                                                                                     handheldModel.getTransformation(),
                                                                                                     new ArrayList<>()));
            MODEL_CACHE.put(stack.getOrCreateNbt(),
                            tmpModel.bake(new DummyBaker(tmpModel), (id) -> getSprite(stack), ModelRotation.X0_Y0,
                                          new Identifier("troll:troll")));
        } return MODEL_CACHE.get(stack.getOrCreateNbt());
    }

    public Sprite getSprite(ItemStack stack) {
        return new Sprite(TEXTURE_CACHE.get(stack.getOrCreateNbt()),
                          new SpriteContents(new Identifier("minecraft:dynamic/ff-blank_1"),
                                             new SpriteDimensions(16, 16), getImage(stack),
                                             new AnimationResourceMetadata(
                                                     ImmutableList.of(new AnimationFrameResourceMetadata(0, -1)), 16,
                                                     16, 1, false)), 16, 16, 0, 0);
    }

    public NativeImage getImage(ItemStack stack) {
        int width = 16;
        int height = 16;
        NativeImage nativeImage = new NativeImage(width, height, false);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                assert stack.getNbt() != null;
                nativeImage.setColor(i, j, getColor(
                        FormingAnvilScreen.getColor(stack.getNbt().getInt("materialID"), stack.getNbt().getInt("heat"),
                                                    stack.getNbt().getIntArray("Model")[i * 16 + j])));
            }
        }

        return nativeImage;
    }
}
