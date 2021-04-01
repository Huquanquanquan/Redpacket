package cn.mokier.redpacket.commands;

import cn.mokier.redpacket.RedPacket;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandManager {

    public static void initialization() {
        CommandSpec money = CommandSpec.builder()
                .description(Text.of("RedPacket command"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("total"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("split"))),
                        GenericArguments.remainingJoinedStrings(Text.of("message")))
                .executor(new CommandMoney())
                .build();
        CommandSpec points = CommandSpec.builder()
                .description(Text.of("RedPacket command"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("total"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("split"))),
                        GenericArguments.remainingJoinedStrings(Text.of("message")))
                .executor(new CommandPoints())
                .build();
        CommandSpec receive = CommandSpec.builder()
                .description(Text.of("RedPacket command"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
                .executor(new CommandReceive())
                .build();

        CommandSpec main = CommandSpec.builder()
                .description(Text.of("RedPacket command"))
                .permission("redPacket.use")
                .executor(new CommandMain())
                .child(money, "money")
                .child(points, "points")
                .child(receive, "receive")
                .build();


        Sponge.getCommandManager().register(RedPacket.instance, main, "fhb");
    }

}
