/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import main.Main;
import scenes.StartGameScreen;
import util.SingletonCommunication;

/**
 *
 * @author vikto
 */
public class ObserverThread implements Runnable {

    SingletonCommunication singletonCommunication = SingletonCommunication.getInstance();
    private volatile boolean exit = false;

    @Override
    public void run() {
        while (!exit) {
            Main.readModel = singletonCommunication.readInfoFromServer(StartGameScreen.socket);
        }
    }

    public void exit() {
        exit = true;
    }

}
