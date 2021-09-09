package org.udpaudiotransmitter.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class AudioSenderThread extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;
    private InetAddress ip;
    private boolean stopSender;

    public AudioSenderThread(DatagramSocket socket, int port, InetAddress ip) {
        this.socket = socket;
        this.port = port;
        this.ip = ip;
        stopSender = false;
    }

    @Override
    public void run() {
        while (!stopSender) {
            String data = "My demo message";
            byte[] buffer = data.getBytes(StandardCharsets.UTF_8);
            packet = new DatagramPacket(buffer, buffer.length, ip, port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.out.println("[Error] Something went wrong in connection while sending data to listener.");
            }
        }
    }

    public boolean isStopSender() {
        return stopSender;
    }

    public void setStopSender(boolean stopSender) {
        this.stopSender = stopSender;
    }
}
