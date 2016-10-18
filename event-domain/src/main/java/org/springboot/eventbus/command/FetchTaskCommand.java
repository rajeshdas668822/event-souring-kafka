package org.springboot.eventbus.command;

import org.springboot.eventbus.domain.User;
import org.springboot.eventbus.util.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rdas on 9/29/2016.
 */
public class FetchTaskCommand<User> extends Command {

    public FetchTaskCommand (UUID id, User user,CommandType type){
        this.id = id;
        this.body = user;
        this.type = type;

    }


    /*@Override
    public Map<String, Object> getEntries() {
        final Map<String, Object> commandEntries = new HashMap<>();

        commandEntries.put(Constant.MAPKEY_ID, this.id);
        commandEntries.put(Constant.MAPKEY_USER_ID, this.user.getUserId());
        commandEntries.put(Constant.MAPKEY_HANDLER_NAME, "FetchTaskCommand");
        super.setEntries(commandEntries);
        return commandEntries;
    }*/
}
