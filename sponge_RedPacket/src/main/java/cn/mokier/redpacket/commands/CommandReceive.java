package cn.mokier.redpacket.commands;

import cn.mokier.redpacket.config.Chat;
import cn.mokier.redpacket.packet.Packet;
import cn.mokier.redpacket.packet.PacketManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandReceive implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            String name = args.<String>getOne("name").get();

            Packet packet = PacketManager.getPacket(name);
            if(packet==null) {
                Chat.sendLangMessage(src, "noPacket", false);
                return CommandResult.success();
            }

            packet.receive(player);
        }

        return CommandResult.success();
    }

}
