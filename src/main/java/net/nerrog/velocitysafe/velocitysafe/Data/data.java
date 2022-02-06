package net.nerrog.velocitysafe.velocitysafe.Data;

import java.util.List;

public class data {
    public boolean on;
    public String NotwhitelistedMessage;
    public List<playersJSON> players;

    //players
    public static class playersJSON{
        public String name;
        public String uuid;
    }

}

