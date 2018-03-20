# catchMe

### Description
An Android reflex game with two variations- catch block or test your reflex. Application is connected with firebase to store the high scores.

### Technologies used in the project
- Android (Android Studio and Java)
- Firebase

### How it works
Application has two variations:
1. Catch block variation. User can select the difficulty level and application is drawing the grid of blocks (adequate to the level) on the screen. Then, application is selecting random block, and user has to click on it before specified time pass. The more block user clicks, the shorter is time.
2. Reflex activity. Application is drawing a single, big button on the screen with "Start" caption. When user clicks on it, button changes it's caption to "Ready" and in the random time is changing the button caption to "Click". User has to click on the "Click" button in the shortest possible time.
Application is connected with Firebase, so all scores are stored in the database.

### How to set up the project
1. Create your own, empty project in Android Studio
2. Copy ``` app ``` folder from GitHub to your project
3. Bind the Firebase by pasting correct ``` google-services.json ``` file inside the ``` app ``` directory. You will find more about setting up the Android Studio project with Firebase on the Google Firebase main page.
