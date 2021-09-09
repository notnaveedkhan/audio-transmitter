package org.udpaudiotransmitter.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AudioReceiverThread extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;
    private boolean stopReceiving;

    public AudioReceiverThread(DatagramSocket socket, int port) {
        this.socket = socket;
        this.port = port;
        stopReceiving = false;
    }

    @Override
    public void run() {
        byte[] buffer;

        while (!isStopReceiving()) {
            try {
                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println("Length = " + packet.getLength());
                System.out.println(new String(packet.getData(), 0, packet.getLength()));
            } catch (IOException e) {
                System.out.println("[Error] Something went wrong in connection while listening to sender.");
            }
        }

    }

    public boolean isStopReceiving() {
        return stopReceiving;
    }

    public void setStopReceiving(boolean stopReceiving) {
        this.stopReceiving = stopReceiving;
    }

}
