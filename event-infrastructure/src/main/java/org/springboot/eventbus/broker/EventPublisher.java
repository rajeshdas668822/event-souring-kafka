package org.springboot.eventbus.broker;

import org.springboot.eventbus.command.Command;
import org.springboot.eventbus.util.EventPattern;

public interface EventPublisher {

	/**
	 *
	 * @param command
	 */
	public  void publish(Command command);

	public <T> T send(Command command);

	public <T> T asyncSend(Command command);

}
