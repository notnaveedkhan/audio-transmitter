# Java Audio Transmitter CLI Tool

![Java](https://img.shields.io/badge/Java-8%2B-orange)
![License](https://img.shields.io/badge/License-MIT-blue)

## Introduction

The Java Audio Transmitter CLI tool allows you to send and receive audio data over a network using the User Datagram Protocol (UDP). It provides a straightforward Command Line Interface (CLI) that enables you to start and stop the audio listener and sender on your computers. This tool is built using Java and leverages the DatagramSocket and DatagramPacket classes for UDP communication.

## Features

- Transmit audio from one computer to another using UDP.
- Start and stop the audio sender to transmit audio data.
- Start and stop the audio listener to receive audio data.

## Requirements

- Java 8 or higher.

## Installation

To use the Java Audio Transmitter CLI tool, follow these steps:

1. Clone the repository:

```bash
git clone https://github.com/notnaveedkhan/audio-transmitter.git
cd audio-transmitter
```

2. Compile the Java code:

```bash
javac com/example/*.java com/example/threads/*.java com/example/util/*.java
```

3. Run the tool:

```bash
java com.example.Application
```

## Usage

Once you run the tool, you'll be presented with a simple menu to control the audio listener and sender. Here are the available options:

1. Start audio listener: Begins listening for incoming audio data.
2. Start audio sender: Starts sending audio data to the specified listener address and port.
3. Stop audio listener: Stops the audio listener.
4. Stop audio sender: Stops the audio sender.
5. Update audio sender: Update the listener address and port for the audio sender.

To use the tool, follow the on-screen instructions and input the corresponding menu option number. When starting the audio sender, you'll be prompted to enter the listener's IP address and port.

## Contributing

Contributions to the Java Audio Transmitter CLI tool are welcome! If you encounter any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or inquiries, feel free to contact the project owner:

- GitHub: [@notnaveedkhan](https://github.com/notnaveedkhan)

## Conclusion

Thank you for using the Java Audio Transmitter CLI tool. We hope you find it useful and welcome your feedback to make it even better!
