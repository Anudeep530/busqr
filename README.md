

### **BusQR: Android Attendance Application**

**BusQR** is an Android application developed using **Kotlin**, **Java**, and **XML**. It provides an efficient solution for authenticating users and marking attendance through barcode and QR code scanning.

---

### **Features**
- **User Login**: Users log in to the application via API calls to the database.
- **ID Card Authentication**: Scans the barcode on ID cards to verify user identity after login.
- **Attendance Marking**: Scans the unique QR code of buses to mark attendance.
- **Retrofit Integration**: Uses the Retrofit library for handling API calls and data synchronization.

---

### **Technologies Used**
- **Languages**: Kotlin, Java
- **UI Design**: XML
- **Networking**: Retrofit Library

---

### **How It Works**
1. **User Login**:
   - Users log in to the app using their credentials.
   - Login is authenticated through API calls to the database using Retrofit.
2. **ID Card Authentication**:
   - After logging in, users scan the barcode on their ID cards to verify their identity.
3. **Attendance Process**:
   - Upon successful authentication, the app scans the bus's unique QR code.
   - Marks attendance and syncs with the server.

---

### **Setup Instructions**
1. Clone the repository:
   ```bash
   git clone https://github.com/Anudeep530/BusQR.git
   ```
2. Open the project in **Android Studio**.
3. Sync the project to download dependencies.
4. Build and run the application on an emulator or physical device.

---

### **Libraries Used**
- **Retrofit**: For API calls and data synchronization.
- **ZXing**: For barcode and QR code scanning (if applicable).

---

### **Contributing**
Feel free to fork the repository, open issues, and submit pull requests to contribute!

---

### **License**
This project is licensed under the [MIT License](LICENSE).

--- 

### **Contact**
For any queries or support, please reach out at: **your-email@example.com**.

---
