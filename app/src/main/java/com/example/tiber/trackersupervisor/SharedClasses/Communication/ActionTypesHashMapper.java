package com.example.tiber.trackersupervisor.SharedClasses.Communication;

import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.LocationData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * Created by tiber on 4/14/2016.
 */
public class ActionTypesHashMapper {

    private static Hashtable<RequestedAction,TypePairContainer> messageActionTypesMapper =
            new Hashtable<RequestedAction,TypePairContainer>();

    static {
        // maps the requested action
        // to the type of  the encapsulated data on the request
        // and with the type of encapsulated data on response
        // example: messageActionTypesMapper.put(RequestedAction.CHECK_ACCESS,new TypePairContainer(Credentials.class, User.class));
        messageActionTypesMapper.put(RequestedAction.GET_CLIENTS_FOR_SUPERVISOR, new TypePairContainer(String.class,new TypeToken<ArrayList<String>>(){}.getType()));
        //////ADD more when implement new request - response
    }

    public static Type getRequestDataClass(RequestedAction key) throws KeyNotMappedException {
        TypePairContainer typePairContainer = messageActionTypesMapper.get(key);
        if(null == typePairContainer)
            throw new KeyNotMappedException("Key : " + key.toString() + " not mapped to any types combination.");
        Type ret = typePairContainer.getRequestDataType();
        return ret;
    }


    public static Type getResponseDataClass(RequestedAction key) throws KeyNotMappedException {
        TypePairContainer typePairContainer = messageActionTypesMapper.get(key);
        if(null == typePairContainer)
            throw new KeyNotMappedException("Key : " + key.toString() + " not mapped to any types combination.");
        Type ret = typePairContainer.getResponseDataType();
        return ret;
    }


}
