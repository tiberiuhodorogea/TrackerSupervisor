package com.example.tiber.trackersupervisor.SharedClasses.Communication;


import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;

/**
 * Created by tiber on 4/14/2016.
 */
public abstract class MessageFactory  {
    abstract AbstractMessage create(RequestedAction requestedAction, Object data) throws KeyNotMappedException;
    abstract AbstractMessage create(String jsonMessage);

}

