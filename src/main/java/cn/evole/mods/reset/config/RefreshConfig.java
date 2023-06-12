 package cn.evole.mods.reset.config;

 import cn.evole.config.YmlConfig;
 import cn.evole.config.api.ConfigField;
 import lombok.Getter;
 import lombok.Setter;

 import java.util.HashMap;
 import java.util.Map;
 import java.util.Objects;

 /**
 * Author cnlimiter
 * CreateTime 2023/6/8 1:50
 * Name ResetConfig
 * Description
 */
@Getter
@Setter
public class RefreshConfig extends YmlConfig {
    public RefreshConfig(String folder) {
        super(folder + "\\refresh.yml");
    }


     @ConfigField("任务")
     private Map<String, WorldRefresh> instance = new HashMap<>();

     public void add(String path, WorldRefresh refresh){
         this.instance.put(path, refresh);
     }

     public WorldRefresh get(String path){
         return Objects.requireNonNull(this.instance.get(path));
     }
}
