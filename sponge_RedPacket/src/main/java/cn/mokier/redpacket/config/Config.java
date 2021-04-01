package cn.mokier.redpacket.config;

import cn.mokier.redpacket.RedPacket;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Config {

    private static File config;
    private static Map<String, Object> configMap;

    public static void initialization(Path dataFolder) {
        saveDefaultConfig(dataFolder);
        configMap = getConfigMap();
    }

    private static void saveDefaultConfig(Path dataFolder) {
        if(!dataFolder.toFile().exists()) {
            dataFolder.toFile().mkdirs();
        }
        Optional<Asset> assets = Sponge.getAssetManager().getAsset(RedPacket.instance, "config.yml");
        config = new File(dataFolder.toFile(), "config.yml");
        if(!config.exists()) {
            assets.ifPresent(asset -> {
                try {
                    assets.orElseThrow(IOException::new).copyToDirectory(dataFolder);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static Map<String, Object> getConfigMap() {
        HashMap map = null;
        InputStream input = null;
        InputStreamReader reader = null;
        try {
            input = new FileInputStream(config);
            reader = new InputStreamReader(input, "UTF-8");
            map = new Yaml().loadAs(reader, HashMap.class);
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if(input != null) {
                    input.close();
                }
                if(reader != null) {
                    reader.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static Object getValue(String key) {
        return configMap.get(key);
    }

}
