/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import comunnication.ServerComunnicationModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import scenes.StartGameScreen;

/**
 *
 * @author vikto
 */
public class SingletonCommunication {

    private SingletonCommunication() {
    }

    public static SingletonCommunication getInstance() {
        return SingletonCommunicationHolder.INSTANCE;
    }

    private static class SingletonCommunicationHolder {

        private static final SingletonCommunication INSTANCE = new SingletonCommunication();
    }

    public ServerComunnicationModel readInfoFromServer(Socket socket) {
        ObjectInputStream in;
        ServerComunnicationModel model = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            model = (ServerComunnicationModel) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(StartGameScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StartGameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(model);
        return model;
    }

    public void sendInfoToServer(Socket socket, ServerComunnicationModel model) {
        try {
            ObjectOutputStream data = new ObjectOutputStream(socket.getOutputStream());
            data.writeObject(model);
            data.flush();
        } catch (IOException ex) {
            Logger.getLogger(StartGameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
