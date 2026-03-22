# 📱 Calling App (Kotlin - Android)

## 🎯 Overview

A simple calling app built using **Kotlin** demonstrating basic Android concepts like UI design, state management, and navigation using **MVVM architecture**.

---

## ✨ Features

* 📞 Dial Pad Screen (0–9, *, #)
* 📤 Outgoing Call Screen (Calling state)
* 📥 Incoming Call Popup (Accept / Reject)
* 📲 Active Call Screen (Timer + Controls)
* 🔄 Call State Management:

  ```
  Idle → Calling → Ringing → Active → Ended
  ```

---

## 🛠 Tech Stack

* Kotlin
* MVVM Architecture
* ViewBinding
* Fragments + DialogFragment
* Handler (for delay & timer)

---

## 📸 Screenshots

### 🔢 Dial Pad

![Dial Pad](screenshots/dialpad.jpg)

### 📤 Outgoing Call

![Outgoing](screenshots/outgoing.jpg)

### 📥 Incoming Call

![Incoming](screenshots/incoming.jpg)

### 📲 Active Call

![Active](screenshots/active.jpg)

---

## ⚙️ How It Works

* User enters number → clicks Call
* App moves to **Calling Screen**
* After delay → shows **Incoming Call Popup**
* Accept → Active Call Screen (timer starts)
* Reject/End → Call Ends

---

## 🚫 Note

* This app simulates call flow (no real calling/SIM integration)
* Focus is on UI + state handling

---

## 📦 Setup

1. Clone repo
2. Open in Android Studio
3. Run on emulator/device

---

## 👨‍💻 Author

**Vikram Singh**
