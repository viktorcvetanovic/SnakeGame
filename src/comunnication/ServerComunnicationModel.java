/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunnication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vikto
 * @param <T>
 */
public class ServerComunnicationModel<T> implements Serializable {

    private CommunicationReasonsEnum reason;
    private Map<String, T> map;

    public ServerComunnicationModel() {
        map = new HashMap();
    }

    public ServerComunnicationModel(CommunicationReasonsEnum reason, Map<String, T> map) {
        this.reason = reason;
        this.map = map;
    }

    public CommunicationReasonsEnum getReason() {
        return reason;
    }

    public void setReason(CommunicationReasonsEnum reason) {
        this.reason = reason;
    }

    public Map<String, T> getMap() {
        return map;
    }

    public void setMap(Map<String, T> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "ServerComunnicationModel{" + "reason=" + reason + ", map=" + map + '}';
    }

}
