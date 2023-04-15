# DoseLoop
<p><b> Medicine Reminder Mobile App.

This mobile application is designed to help users remember to take their medication on time every day.
The application has a simple user interface that users to easily customize their medication schedules and set up reminders for each medication.
The MVVM architecture ensures a clean separation of concerns, making maintenance and testing of the application much simpler.
Overall, this medicine reminder mobile application provides an easy-to-use and solution for managing medication schedules.</b> </p>

###  Tech and libraries
- Kotlin
- Minimum SDK Level 28
- MVVM
- SMS Manager
- Voice Control
- Data binding
- Android SharedPreferences
- Lottie Animation

### Main Features
- Zero UI


### Dependencies

<p><b> build.gradle(Module)</p></b>

    dependencies {
        implementation 'androidx.core:core-ktx:1.7.0'
        implementation 'androidx.appcompat:appcompat:1.4.1'
        implementation 'com.google.android.material:material:1.5.0'
        implementation 'androidx.constraintlayout:constraintlayout:2.1.3'

        // Navigation component
        implementation 'androidx.navigation:navigation-fragment-ktx:2.5.3'
        implementation 'androidx.navigation:navigation-ui-ktx:2.5.3'

        testImplementation 'junit:junit:4.13.2'
        androidTestImplementation 'androidx.test.ext:junit:1.1.3'
        androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

        // Material components
        implementation 'com.google.android.material:material:1.9.0-alpha02'

        implementation 'androidx.cardview:cardview:1.0.0'

        // Lottie Animation
        implementation 'com.airbnb.android:lottie:3.4.0'
    }
