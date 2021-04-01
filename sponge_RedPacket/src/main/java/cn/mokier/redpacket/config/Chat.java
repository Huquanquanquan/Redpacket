package cn.mokier.redpacket.config;

import cn.mokier.redpacket.utils.ConfigUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Chat {

    public static void sendBroadcastMessage(Text msg) {
        for(Player player : Sponge.getServer().getOnlinePlayers()) {
            player.sendMessage(msg);
        }
    }

    public static void sendLangMessage(CommandSource source, String node, boolean isFilter, String... replaces) {
        sendMessage(source, ConfigLang.getOriginText(node), isFilter, replaces);
    }

    public static void sendMessage(CommandSource source, String msg, boolean isFilter, String... replaces) {
        String message = isFilter ? ConfigUtils.filterVar(msg, replaces) : msg;
        source.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message));
    }

}
