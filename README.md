## Library Management System with JavaFX

Library Management System is a desktop application designed to help users maintain and organize an academic library. The application allows you to log in add, update, delete books and authors, follow the book borrowing and return process, search for books and authors...

### Running in IntelliJ IDEA (JavaFX)

1. **Open the project**
   - In IntelliJ, choose **Open** and select the project folder. Mark `src` as *Sources Root* if IntelliJ does not do it automatically.
   - Set the **Project SDK** to **JDK 8** (simplest, because JavaFX is bundled). For JDK 11+, you must add a JavaFX SDK and use `--module-path`/`--add-modules` VM options.

2. **Add required libraries**
   - Add the jars under `lib/JavaFxLibs/`, `lib/jfoenix-8.0.1.jar`, and any other `lib` jars (e.g., MySQL connector) via **File → Project Structure → Libraries** so they are on the compile/runtime classpath.
   - If you use JDK 11+, also add a downloaded JavaFX SDK (`<javafx-sdk>/lib`) to the module path and include VM options such as:
     ```
     --module-path <path-to-javafx-sdk>/lib --add-modules=javafx.controls,javafx.fxml
     ```

3. **Configure the run configuration**
   - Create an **Application** configuration with **Main class:** `Main` and the project root as the working directory.

4. **Set up the database**
   - Import `bibliofx.sql` into a MySQL database named `bibliofx` on `127.0.0.1:3306` using user `root` and an empty password (defaults in code). If your credentials differ, update `src/dao/DBConnection.java` accordingly.

Once the database is ready and dependencies are resolved, click **Run** to launch the login window.

## Screenshots


<p align="center" width="100%">
    <img src="https://user-images.githubusercontent.com/56236490/89303646-605cf580-d664-11ea-8aa3-35aeebfbbbb3.png">
</p>
<p align="center" width="100%">
    <img src="https://user-images.githubusercontent.com/56236490/89303653-6226b900-d664-11ea-88ef-818032d88b80.png">
</p>
<p align="center" width="100%">
    <img src="https://user-images.githubusercontent.com/56236490/89303661-64891300-d664-11ea-896b-dc6e9937077f.png">
</p>
<p align="center" width="100%">
    <img src="https://user-images.githubusercontent.com/56236490/89303660-64891300-d664-11ea-904b-3b53a51a7db2.png">
</p>
<p align="center" width="100%">
    <img src="https://raw.githubusercontent.com/AKH-cpu/library-management-system-javafx/master/screenshots/livres.png">
</p>
<p align="center" width="100%">
    <img src="https://raw.githubusercontent.com/AKH-cpu/library-management-system-javafx/master/screenshots/emprunt.png">
</p>



<!--
![A](https://user-images.githubusercontent.com/56236490/89303646-605cf580-d664-11ea-8aa3-35aeebfbbbb3.png)

![B](https://user-images.githubusercontent.com/56236490/89303653-6226b900-d664-11ea-88ef-818032d88b80.png)

![C](https://user-images.githubusercontent.com/56236490/89303660-64891300-d664-11ea-904b-3b53a51a7db2.png)

![D](https://user-images.githubusercontent.com/56236490/89303661-64891300-d664-11ea-896b-dc6e9937077f.png)

![livres](https://user-images.githubusercontent.com/56236490/89303661-64891300-d664-11ea-896b-dc6e9937077f.png)

![emprunt](https://raw.githubusercontent.com/AKH-cpu/library-management-system-javafx/master/screenshots/emprunt.png)
-->
[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/8BohNiMbCWs/0.jpg)](http://www.youtube.com/watch?v=8BohNiMbCWs)
