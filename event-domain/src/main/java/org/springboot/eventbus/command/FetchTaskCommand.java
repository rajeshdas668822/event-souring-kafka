package org.springboot.eventbus.command;

import org.springboot.eventbus.domain.User;
import org.springboot.eventbus.util.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/29/2016.
 */
public class FetchTaskCommand extends Command {

    User user;

    public FetchTaskCommand (UUID id, User user){
        this.id = id;
        this.user = user;
    }


    @Override
    public Map<String, Object> getEntries() {
        final Map<String, Object> commandEntries = new HashMap<>();

        commandEntries.put(Constant.MAPKEY_ID, this.id);
        commandEntries.put(Constant.MAPKEY_USER_ID, this.user.getUserId());
        return commandEntries;
    }
}
