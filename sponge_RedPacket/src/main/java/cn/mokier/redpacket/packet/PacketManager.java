package cn.mokier.redpacket.packet;

import cn.mokier.redpacket.utils.NumberUtils;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacketManager {

    @Getter
    private static Map<String, Packet> packetMap;
    private static List<Integer> list;

    public static void initialization() {
        packetMap = new HashMap<>();
    }

    /**
     * 新建红包
     * @param player  红包发起者
     * @param total  总额
     * @param split  份数
     * @param isPoints  是否点券
     * @param isRandom  是否随机
     * @return
     */
    public static String create(Player player, int total, int split, boolean isPoints, boolean isRandom) {
        String randomName = RandomStringUtils.randomAlphanumeric(10);
        list = NumberUtils.splitNumber(total, split, isRandom);

        for(int i : list) {
            System.out.println(i);
        }

        Packet packet = Packet.builder()
                .name(randomName)
                .player(player)
                .total(total)
                .split(split)
                .isPoints(isPoints)
                .isRandom(isRandom)
                .receive(new ArrayList<>())
                .list(list)
                .build();

        packetMap.put(randomName, packet);

        return randomName;
    }

    /**
     * 通过红包名获得红包
     * @param name  红包名
     * @return
     */
    public static Packet getPacket(String name) {
        return packetMap.get(name);
    }

}
