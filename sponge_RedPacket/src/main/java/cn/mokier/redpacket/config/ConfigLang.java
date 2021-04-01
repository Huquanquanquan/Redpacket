package cn.mokier.redpacket.config;

import cn.mokier.redpacket.RedPacket;
import cn.mokier.redpacket.utils.ConfigUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

public class ConfigLang {

  private static File file;
  private static HashMap configMap;

  public static void initialization(Path dataFolder) {
    saveDefaultConfig(dataFolder);
    configMap = getConfigMap();
  }

  private static void saveDefaultConfig(Path dataFolder) {
    if(!dataFolder.toFile().exists()) dataFolder.toFile().mkdirs();
    Optional<Asset> config = Sponge.getAssetManager().getAsset(RedPacket.instance, "lang.yml");
    try {
      ConfigLang.file = new File(dataFolder.toFile(), "lang.yml");
      if(!ConfigLang.file.exists()) {
        config.orElseThrow(IOException::new).copyToDirectory(dataFolder);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static HashMap getConfigMap() {
    HashMap map = null;
    InputStream input = null;
    InputStreamReader reader = null;
    try {
      input = new FileInputStream(file);
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

  public static  <T> T getValue(String key) {
    return (T) configMap.get(key);
  }

  public static Text getText(String key, String... replaces) {
    return TextSerializers.FORMATTING_CODE.deserialize(ConfigUtils.filterVar(getValue(key), replaces));
  }

  public static String getOriginText(String key) {
    return getValue(key);
  }
}
