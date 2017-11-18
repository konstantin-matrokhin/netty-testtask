package ru.kvlt.testtask;

import ru.kvlt.testtask.network.ConnectionManager;

public class ServerApp {

    public static void main(String[] args) {
        ConnectionManager.get().start();
    }

}
