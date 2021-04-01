package cn.mokier.redpacket.commands;

import cn.mokier.redpacket.config.Chat;
import cn.mokier.redpacket.config.Config;
import cn.mokier.redpacket.config.ConfigLang;
import cn.mokier.redpacket.packet.PacketManager;
import cn.mokier.redpacket.utils.ConfigUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.List;

public class CommandPoints implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            int total = args.<Integer>getOne("total").get();
            int split = args.<Integer>getOne("split").get();
            String message = args.<String>getOne("message").get();

            //如果机制不是随机也不是平均
            if(!message.contains("随机") && !message.contains("平均")) {
                Chat.sendLangMessage(src, "noRandomMessage", false);
                return CommandResult.success();
            }else {
                if(message.contains("随机")) {
                    message = "随机";
                }else {
                    message = "平均";
                }
            }

            //新建红包
            String packetName = PacketManager.create(player, total, split, true, message.contains("随机"));

            //发送信息
            List<String> sendList = ConfigLang.getValue("send");
            Text hover = TextSerializers.FORMATTING_CODE.deserialize(ConfigLang.getValue("hover"));
            for(String s : sendList) {
                String send = ConfigUtils.filterVar(s,
                        "%player%", player.getName(),
                        "%total%", ""+total,
                        "%split%", ""+split,
                        "%isPoints%", "点券",
                        "%isRandom%", message);

                Text text = Text.builder()
                        .append(TextSerializers.FORMATTING_CODE.deserialize(send))
                        .onHover(TextActions.showText(hover))
                        .onClick(TextActions.runCommand("/fhb receive "+packetName))
                        .build();
                Chat.sendBroadcastMessage(text);
            }

        }
        return CommandResult.success();
    }

}
