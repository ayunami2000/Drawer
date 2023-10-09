package me.ayunami2000.drawer.mixin;

import me.ayunami2000.drawer.Command;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class MixinChatScreen {
    @Inject(at = @At("HEAD"), method = "sendMessage(Ljava/lang/String;Z)Z", cancellable = true)
    public void onSendMessage(String message, boolean addToHistory, CallbackInfoReturnable<Boolean> cir) {
        if((message = normalize(message)).isEmpty()) return;
        boolean isCommand = Command.parse(message);
        if (isCommand) {
            cir.setReturnValue(true);
        }
    }

    @Shadow
    public String normalize(String chatText) {
        return null;
    }
}