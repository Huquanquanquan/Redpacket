package cn.mokier.redpacket.packet;

import cn.mokier.redpacket.config.Chat;
import lombok.Builder;
import lombok.Data;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.swing.text.TextAction;
import java.util.List;

@Data
@Builder
public class Packet {

    private String name;
    private Player player;
    private int total;
    private int split;
    private boolean isPoints;
    private boolean isRandom;
    private List<Player> receive;
    private List<Integer> list;

    /**
     * 领取红包
     * @param player 领取的玩家
     * @return true  已经全部领取
     */
    public void receive(Player player) {
        //判断是否是发起者
        if(this.player.equals(player)) {
            Chat.sendLangMessage(player, "isCreatePlayer", false);
            return;
        }

        //判断是否已经领取
        if(receive.contains(player)) {
            Chat.sendLangMessage(player, "isReceive", false);
            return;
        }

        //给与奖励
        int random = (int)(0+Math.random()*((list.size()-1)-0+1));
        int number = list.get(random);
        if(!isPoints) {
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "adminpay "+player.getName()+" "+number);
        }else {
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "b give "+player.getName()+" "+number);
        }
        list.remove(random);

        //添加领取记录
        receive.add(player);

        Chat.sendLangMessage(player, "receive", true,
                "%number%", ""+number);

        //判断是否全部领完
        if(list.isEmpty()) {
            PacketManager.getPacketMap().remove(name);
        }
    }

}
