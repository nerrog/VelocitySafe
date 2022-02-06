package net.nerrog.velocitysafe.velocitysafe.Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.nerrog.velocitysafe.velocitysafe.VelocitySafe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataLoader {

    public static data getJson() {
        File configJson = new File("./whitelist_safe.json");
        if(!configJson.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(configJson);
                String defaltJson = "{\"on\":true, \"NotwhitelistedMessage\": \"[Proxy]&4&lYou are not whitelisted!\",\"players\": []}";
                fileWriter.write(defaltJson);
                fileWriter.close();
                VelocitySafe.logger.info("Write default json to ./whitelist_safe.json!");
            } catch (IOException e) {
                VelocitySafe.logger.error("Error in Write default json");
                e.printStackTrace();
                return null;
            }
        }

            VelocitySafe.logger.info("Loading whitelist...");
            ObjectMapper mapper = new ObjectMapper();
            try {
                data d = mapper.readValue(new File("./whitelist_safe.json"), data.class);
                VelocitySafe.logger.info("Successful loading whitelist!");
                return d;
            } catch (IOException e) {
                VelocitySafe.logger.error("Error in loading whitelist...");
                e.printStackTrace();
                return null;
            }
    }

    public enum modifyWList{
        ADD,
        REMOVE
    }
    public enum OnOff{
        ON,
        OFF
    }

    public static boolean modifyWhitelist(modifyWList type, String playername){
        if(type.equals(modifyWList.ADD)){
            //追加
            data.playersJSON newPlayer = new data.playersJSON();
            newPlayer.name = playername;
            newPlayer.uuid = "";

            VelocitySafe.whitelist.players.add(newPlayer);
            try{
                writeWhitelist(VelocitySafe.whitelist);
            }catch (IOException e){
                VelocitySafe.logger.error("An error occurred while writing whitelist_safe.json\n"+e.getMessage());
            }
            return true;
        }else {
            //削除

            //プレイヤーの検索
            data.playersJSON targetPlayer = new data.playersJSON();
            for (int i=0; i < VelocitySafe.whitelist.players.size(); i++){
                if (VelocitySafe.whitelist.players.get(i).name.equals(playername)){
                    targetPlayer = VelocitySafe.whitelist.players.get(i);
                }
            }

            //見つかったか
            if (targetPlayer.name == null){
                return false;
            }

            VelocitySafe.whitelist.players.remove(targetPlayer);

            //書き込み
            try{
                writeWhitelist(VelocitySafe.whitelist);
            }catch (IOException e){
                VelocitySafe.logger.error("An error occurred while writing whitelist_safe.json\n"+e.getMessage());
            }

            return true;
        }

    }

    public static boolean switchWhitelist(OnOff type){
        if(type.equals(OnOff.ON)){
            //ON
            if (!VelocitySafe.whitelist.on){
                VelocitySafe.whitelist.on = true;

                //書き込み
                try{
                    writeWhitelist(VelocitySafe.whitelist);
                }catch (IOException e){
                    VelocitySafe.logger.error("An error occurred while writing whitelist_safe.json\n"+e.getMessage());
                }
                return true;
            }
        }else {
            //OFF
            if (VelocitySafe.whitelist.on){
                VelocitySafe.whitelist.on = false;

                //書き込み
                try{
                    writeWhitelist(VelocitySafe.whitelist);
                }catch (IOException e){
                    VelocitySafe.logger.error("An error occurred while writing whitelist_safe.json\n"+e.getMessage());
                }
                return true;
            }
        }
        return false;
    }

    public static void writeWhitelist(data data) throws IOException {
        String json = new ObjectMapper().writeValueAsString(data);
        FileWriter fileWriter = new FileWriter("./whitelist_safe.json");
        fileWriter.write(json);
        fileWriter.close();
        VelocitySafe.logger.info("Write json to ./whitelist_safe.json !");
    }
}
