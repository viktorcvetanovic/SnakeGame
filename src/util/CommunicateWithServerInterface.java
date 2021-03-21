/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author vikto
 */
public interface CommunicateWithServerInterface {

    public static final String SERVERURL = "127.0.0.1";
    public static final int PORT = 1200;

    public void sendInfoToServer();

    public void readInfoFromServer();
}
