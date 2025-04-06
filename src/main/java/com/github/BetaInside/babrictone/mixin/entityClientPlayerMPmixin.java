package com.github.BetaInside.babrictone.mixin;

import com.github.BetaInside.babrictone.event.events.chatEvent;
import com.github.BetaInside.babrictone.babrictone;
import net.minecraft.src.EntityClientPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityClientPlayerMP.class)
public class entityClientPlayerMPmixin {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onSendChatMessage(String message, CallbackInfo ci) {

        chatEvent event = new chatEvent(message);
        babrictone.onEvent(event);
        if (event.cancelled())
            ci.cancel();

    }

}
