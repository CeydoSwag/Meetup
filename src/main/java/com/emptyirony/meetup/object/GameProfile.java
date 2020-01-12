package com.emptyirony.meetup.object;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Data;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/1/11 19:49
 * 4
 */
@Data
public class GameProfile {
    private static Map<UUID,GameProfile> cache = new HashMap<>();
    private static MongoDatabase database = new MongoClient("127.0.0.1", 27017).getDatabase("meetup");

    private final UUID uuid;
    private int kills;
    private int played;
    private int wins;

    public GameProfile(UUID uuid) {
        this.uuid = uuid;

        Document document = database.getCollection("info").find(Filters.eq("uuid", uuid.toString())).first();
        if (document == null){
            document = new Document();
            document.put("uuid",uuid.toString());
            document.put("kills",0);
            document.put("played",0);
            document.put("wins",0);
        }

        this.kills = document.getInteger("kills");
        this.played = document.getInteger("played");
        this.wins = document.getInteger("wins");

        cache.put(uuid,this);
    }

    public static GameProfile getProfileByUUID(UUID uuid){
        return cache.get(uuid);
    }
}
