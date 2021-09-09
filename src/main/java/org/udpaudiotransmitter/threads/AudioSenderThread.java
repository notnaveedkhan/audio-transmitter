package org.udpaudiotransmitter.threads;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AudioSenderThread extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;
    private InetAddress ip;
    private boolean stopSender;

    private TargetDataLine microphone;

    public AudioSenderThread(DatagramSocket socket, int port, InetAddress ip) throws LineUnavailableException {
        this.socket = socket;
        this.port = port;
        this.ip = ip;
        stopSender = false;
        AudioFormat format = new AudioFormat(48000.0f, 16, 1, true, true);
        microphone = AudioSystem.getTargetDataLine(format);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
    }

    @Override
    public void run() {
        microphone.start();
        byte[] buffer;
        while (!stopSender) {
            buffer = new byte[100];
            int read = microphone.read(buffer, 0, buffer.length);
            packet = new DatagramPacket(buffer, read, ip, port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.out.println("[Error] Something went wrong in connection while sending data to listener.");
            }
        }
        microphone.close();
    }

    public boolean isStopSender() {
        return stopSender;
    }

    public void setStopSender(boolean stopSender) {
        this.stopSender = stopSender;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void close() {
        microphone.close();
    }

}
