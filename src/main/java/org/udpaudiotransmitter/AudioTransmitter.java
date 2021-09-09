package org.udpaudiotransmitter;

import org.udpaudiotransmitter.threads.AudioReceiverThread;
import org.udpaudiotransmitter.threads.AudioSenderThread;
import org.udpaudiotransmitter.util.ConsoleInput;

import javax.sound.sampled.LineUnavailableException;
import java.net.*;

public class AudioTransmitter {

    private final ConsoleInput input;
    private boolean audioListenerStarted;
    private boolean audioSenderStarted;
    private boolean isClosed;

    // sockets
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port = 4385;
    private String ipAddress = "127.0.0.1";

    // threads
    private AudioReceiverThread audioReceiverThread;
    private AudioSenderThread audioSenderThread;

    public AudioTransmitter() {
        input = new ConsoleInput(System.in);
        audioListenerStarted = false;
        audioSenderStarted = false;
        isClosed = false;
    }

    private void printMainMenu() {
        if (!audioListenerStarted && !audioSenderStarted) {
            System.out.println("1) Start audio listener");
            System.out.println("2) Start audio sender");
        }
        else if (audioListenerStarted && !audioSenderStarted) {
            System.out.println("1) Stop audio listener");
            System.out.println("2) Start audio sender");
        }
        else if (!audioListenerStarted && audioSenderStarted) {
            System.out.println("1) Start audio listener");
            System.out.println("2) Stop audio sender");
            System.out.println("u) Update audio sender.");
        }
        else {
            System.out.println("[Error] Something went wrong in print menu.");
        }
        System.out.println("3) Shutdown audio transmitter");
        System.out.print("@> ");
    }

    public void start() {
        while(!isClosed) {
            printMainMenu();
            String option = input.getWord();
            if (option.equals("1")) {
                if (!audioListenerStarted) {
                    startAudioListener();
                } else {
                    stopAudioListener();
                }
            }
            else if (option.equals("2")) {
                if (!audioSenderStarted) {
                    startAudioSender();
                } else {
                    stopAudioSender();
                }
            }
            else if (option.equals("3")) {
                System.out.println("Shutting down audio transmitter ...");
                isClosed = true;
            }
            else if (option.equals("u")) {
                if (audioSenderStarted) {
                    updateAudioSender();
                } else {
                    System.out.println("[Error] You have selected invalid option.");
                }
            }
            else {
                System.out.println("[Error] You have selected invalid option.");
            }
        }
    }

    private void updateAudioSender() {
        boolean isSuccess = false;
        while (!isSuccess) {
            try {
                System.out.print("Enter port of listener (0 mean default port: 4385): ");
                int tempPort = input.inputInt();
                if (tempPort == 0) {
                    tempPort = port;
                }
                System.out.print("Enter address of listener (0 mean localhost): ");
                String tempAddress = input.getLine();
                if (tempAddress.equals("0")) {
                    tempAddress = ipAddress;
                }
                InetAddress ip = InetAddress.getByName(tempAddress);
                port = tempPort;
                ipAddress = tempAddress;
                audioSenderThread.setPort(port);
                audioSenderThread.setIp(ip);
                System.out.printf("[Info] Audio sender is updated now sending data on port %d of address %s%n", port, ipAddress);
                isSuccess = true;
            } catch (NumberFormatException ignored) {
                System.out.println("[Error] Input value must be a numeric value");
            } catch (UnknownHostException e) {
                System.out.println("[Error] Some error has occurred to deal with provided listener address.");
                System.out.println("[Reason] " + e.getMessage());
            }
        }
    }

    private void stopAudioSender() {
        audioSenderThread.setStopSender(true);
        while (audioSenderThread.isAlive()) {
            audioSenderThread.stop();
        }
        audioSenderStarted = false;
        socket.close();
        System.out.println("[Info] Audio sender has stopped.");
    }

    private void startAudioSender() {
        boolean isSuccess = false;
        while (!isSuccess) {
            try {
                System.out.print("Enter port of listener (0 mean default port: 4385): ");
                int tempPort = input.inputInt();
                if (tempPort == 0) {
                    tempPort = port;
                }
                System.out.print("Enter address of listener (0 mean localhost): ");
                String tempAddress = input.getLine();
                if (tempAddress.equals("0")) {
                    tempAddress = ipAddress;
                }

                socket = new DatagramSocket();
                InetAddress ip = InetAddress.getByName(tempAddress);
                port = tempPort;
                ipAddress = tempAddress;

                audioSenderThread = new AudioSenderThread(socket, port, ip);
                audioSenderThread.start();

                audioSenderStarted = true;
                isSuccess = true;
                System.out.printf("[Info] Audio sender is sending data on port %d of address %s%n", port, ipAddress);

            } catch (NumberFormatException ignored) {
                System.out.println("[Error] Input value must be a numeric value");
            } catch (SocketException e) {
                System.out.println("[Error] Some error has occurred while starting the sender server.");
                System.out.println("[Reason] " + e.getMessage());
            } catch (UnknownHostException e) {
                System.out.println("[Error] Some error has occurred to deal with provided listener address.");
                System.out.println("[Reason] " + e.getMessage());
            } catch (LineUnavailableException e) {
                System.out.println("[Error] Some error has occurred while initializing audio sender objects.");
                System.out.println("[Reason] " + e.getMessage());
            }
        }
    }

    private void stopAudioListener() {
        audioReceiverThread.setStopReceiving(true);
        while (audioReceiverThread.isAlive()) {
            audioReceiverThread.stop();
        }
        audioListenerStarted = false;
        socket.close();
        System.out.println("[Info] Audio listener has stopped.");
    }

    private void startAudioListener() {
        boolean isSuccess = false;
        while (!isSuccess) {
            System.out.print("Enter on which port you want to listen(0 mean default port: 4385): ");
            try {
                int tempPort = input.inputInt();
                if (tempPort == 0) {
                    tempPort = port;
                }
                socket = new DatagramSocket(tempPort);
                port = tempPort;

                audioReceiverThread = new AudioReceiverThread(socket, port);
                audioReceiverThread.start();

                audioListenerStarted = true;
                isSuccess = true;
                System.out.printf("[Info] Audio listener has started on port %d%n", port);

            } catch (NumberFormatException ignored) {
                System.out.println("[Error] Input value must be a numeric value");
            } catch (SocketException e) {
                System.out.println("[Error] Some error has occurred while starting the listener server.");
                System.out.println("[Reason] " + e.getMessage());
            } catch (LineUnavailableException e) {
                System.out.println("[Error] Some error has occurred while initializing audio receiving objects.");
                System.out.println("[Reason] " + e.getMessage());
            }
        }
    }

}
