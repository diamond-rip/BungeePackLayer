package io.th0rgal.packsmanager.velocity.util;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Common {

    public static void sendMessage(Audience audience, String... messages) {
        for (String message : messages) {
            audience.sendMessage(text(message));
        }
    }

    public static Component text(String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

}
