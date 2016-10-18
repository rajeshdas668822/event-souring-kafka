package org.springboot.eventbus.command;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.apache.activemq.command.CommandTypes;


/**
 * A base class for handling the created commands from the application layer
 */
public   class Command<T> implements Serializable {

    protected UUID id;
    protected  Map<String,Object> entries;
    protected  String name;
    protected CommandType type;
    protected  T body;
    protected Object response;
    protected boolean isSyncRequest;

    public boolean isSyncRequest() {
        return isSyncRequest;
    }

    public void setSyncRequest(boolean syncRequest) {
        isSyncRequest = syncRequest;
    }



    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }


   

	public CommandType getType() {
		return type;
	}

	public Command() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Command) && ((Command) obj).getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }


    public    Map<String, Object> getEntries(){
    	return entries;
    }

	public void setEntries(Map<String, Object> entries) {
		this.entries = entries;
	}

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
