# 💬 SmartChat

<div align="center">

### 🚀 Internship Task 3 – Multithreaded Chat Application

A Java-based client-server chat application built using **Socket Programming** and **Multithreading**, enabling multiple users to communicate with each other in real time through a clean console interface.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Sockets](https://img.shields.io/badge/Socket-Programming-blue?style=for-the-badge)
![Multithreading](https://img.shields.io/badge/Multithreading-Concurrency-success?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen?style=for-the-badge)

</div>

---

# 📖 Overview

**SmartChat** is a console-based chat application developed in Java using a **client-server architecture**. The server accepts multiple client connections simultaneously, while each client communicates in real time through dedicated threads.

This project demonstrates practical implementation of:

- Socket Programming
- Multithreading
- Client-Server Communication
- Object-Oriented Programming
- Exception Handling

---

# ✨ Features

- 💬 Real-time messaging
- 👥 Multiple clients connected simultaneously
- 🌐 Client-Server architecture
- ⚡ Dedicated thread for every connected client
- 📢 Automatic message broadcasting
- ✅ Join and leave notifications
- 📝 Server activity logging
- 🛡 Graceful exception handling
- 🖥 Clean console-based interface

---

# 🛠 Technologies Used

- ☕ Java 17
- 🔌 Socket Programming
- 🧵 Multithreading
- 📖 BufferedReader
- ✍️ PrintWriter
- 🏗 Object-Oriented Programming
- 💻 VS Code

---

# 📂 Project Structure

```text
SmartChat/
│
├── Client/
│   ├── ChatClient.java
│   ├── ClientUI.java
│   └── MessageReceiver.java
│
├── Server/
│   ├── ChatServer.java
│   ├── ClientHandler.java
│   └── ServerLogger.java
│
├── README.md
└── .gitignore
```

---

# ⚙ How It Works

```text
        Client 1
            │
            │
            ▼
      ┌────────────┐
      │ ChatServer │
      └────────────┘
        ▲    ▲    ▲
        │    │    │
   Client2 Client3 Client4
```

The server continuously listens for incoming client connections. Every new client is assigned a dedicated thread, allowing multiple users to exchange messages simultaneously without interrupting each other.

---

# 🚀 How to Run

### Compile Server

```bash
javac Server/*.java
```

### Run Server

```bash
java Server.ChatServer
```

---

### Compile Client

```bash
javac Client/*.java
```

### Run Client

```bash
java Client.ChatClient
```

Run multiple client instances to experience real-time communication.

---

# 💻 Sample Output

### Server

```text
====================================
SMART CHAT SERVER
====================================

Server started on Port 5000

Waiting for clients...

Shubham joined the chat.

Aryan joined the chat.

[Shubham]

Hello Everyone!

[Aryan]

Hi!

Shubham left the chat.
```

---

### Client

```text
====================================
SMART CHAT CLIENT
====================================

Enter Username:

Shubham

Connected Successfully!

Hello Everyone!
```

---

# 📚 Learning Outcomes

Through this project, I gained hands-on experience with:

- Java Socket Programming
- Multithreading
- Client-Server Architecture
- Concurrent Programming
- Network Communication
- Object-Oriented Design
- Exception Handling

---

# 🔮 Future Enhancements

- 🔒 Private Messaging
- 😊 Emoji Support
- 📂 File Sharing
- 🖥 Graphical User Interface (JavaFX)
- 💾 Chat History
- 👥 User Authentication

---

# 👨‍💻 Developed By

**Shubham**

B.Tech – Computer Science & Engineering

Internship Task 3 Submission

---

<div align="center">

⭐ If you found this project useful, consider giving it a star.

Made with ❤️ using Java

</div>
