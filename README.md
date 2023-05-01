# DoseLoop
<p><b> Medicine Reminder Mobile App.

This mobile application is designed to help users remember to take their medication on time every day. The application has a simple user interface that allows users to easily customize their medication schedules and set up reminders for each medication. The MVVM architecture ensures a clean separation of concerns, making maintenance and testing of the application much simpler. Overall, this medicine reminder mobile application provides an easy-to-use solution for managing medication schedules.

The project was designed to be used in conjunction with an automatic dispenser, which releases the appropriate medication at the right time. SMS messages bind the application with the dispenser. The dispenser has a pre-installed SIM card that receives messages with commands to set various settings, such as time, phone numbers and etc. We utilized the Android SmsManager with Sms API for this purpose.</b></p>

###  Tech and libraries
- Kotlin
- Minimum SDK Level 28
- ViewModel
- [Android SmsManager](https://developer.android.com/reference/android/telephony/SmsManager)
- Sms Api
- Voice Control
- Navigation
- SharedPreferences
- Data binding
- MutableLiveData
- [Lottie Animation](https://lottiefiles.com)

### Main Features
- Zero UI:  
    - Voice control allows users to add and change phone numbers and times for taking medicine by using their voices, which is particularly helpful for anyone who has difficulty manipulating a touch screen. However, there is room for improvement in the future.


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
    
### Overview of the app


<div align="left">
    <img alt="dev.gif" width="260" align="center" src="https://user-images.githubusercontent.com/43030856/235527993-c7e2bbc9-c4fd-46ae-ad6c-9880662ea886.gif" />
    <img alt="dev.gif" width="260" align="center" src="https://user-images.githubusercontent.com/43030856/235509457-b588b327-4abd-4eba-b0f8-9dcc18c50095.gif" />
    <img alt="dev.gif" width="260" align="center" src="https://user-images.githubusercontent.com/43030856/235509493-851f2ded-f647-4bcd-bedc-7fbfe0980dfe.gif" />
</div>


- Assets used in the app
  
  - LottieFiles Animation
  - Logo provided by customer
  - Material Design Icons
  - Self-drawn background (pattern illustration)


### License

[MIT License](https://github.com/Metropolia-ZeroUI/DoseLoop/blob/master/LICENSE.txt)

Copyright (c) 2023 Metropolia-ZeroUI

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
