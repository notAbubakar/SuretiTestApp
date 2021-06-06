# SuretiTestApp

This app is built using all the latest android technologies and following the best android lifecycle practices including:
1. Dependency injection with Dagger Hilt for creation of dependent objects outside of a class and provide those objects to a class through different ways like constructor injection etc.
2. Google's recommended MVVM architecture but right now, there is no local storage like Room or SQLite and only works if you have internet access (no caching).
3. View Binding for compile-time safe rendering.
4. Retrofit2 for fetching data from the server and GSON for converting the JSON data into kotlin objects.
5. Using coroutines for background fetching of the data from the REST API without freezing the app.

There might be some bugs lurking around so have fun :D

P.S: Also didn't added loading bars while fetching the data and the Sign Up screen don't have empty field checks but the new user created has null data anyway so didn't focused on that much.

Cheers!
