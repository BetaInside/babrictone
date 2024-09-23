package org.example.mixin;

import net.minecraft.src.GuiMainMenu;
import org.example.ExampleMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class ExampleMixin {
    @Inject(method = "initGui", at = @At("HEAD"))
    private void initGui(CallbackInfo ci) {
        ExampleMod.LOGGER.info("This line was printed by an example mod mixin!");
    }
}
