package me.blackfur.ferociousforgery.screens;

import net.minecraft.screen.PropertyDelegate;

public class FormingAnvilPropertyDelegate implements PropertyDelegate {
    private final int[] toolInts = new int[258];

    public FormingAnvilPropertyDelegate() {

    }
    @Override
    public int get(int index) {
        return toolInts[index];
    }

    @Override
    public void set(int index, int value) {
        toolInts[index] = value;
    }

    @Override
    public int size() {
        return toolInts.length;
    }
}
