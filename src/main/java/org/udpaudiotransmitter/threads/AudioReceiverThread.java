package org.udpaudiotransmitter.threads;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AudioReceiverThread extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;
    private boolean stopReceiving;

    private SourceDataLine speakers;


    public AudioReceiverThread(DatagramSocket socket, int port) throws LineUnavailableException {
        this.socket = socket;
        this.port = port;
        stopReceiving = false;
        AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        speakers.open(format);
    }

    @Override
    public void run() {
        byte[] buffer;
        speakers.start();
        while (!isStopReceiving()) {
            try {
                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                speakers.write(packet.getData(), 0, packet.getLength());
            } catch (IOException e) {
                System.out.println("[Error] Something went wrong in connection while listening to sender.");
            }
        }
        speakers.close();
    }

    public boolean isStopReceiving() {
        return stopReceiving;
    }

    public void setStopReceiving(boolean stopReceiving) {
        this.stopReceiving = stopReceiving;
    }

}
