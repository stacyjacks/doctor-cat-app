# Doctor Cat app

Doctor Cat is an Android application, which helps you to take better care of your cat, by creating reminders for tasks and appointments you need to see to. To build this app I used:

* Kotlin
* MVVM (Model-View-ViewModel) design pattern
* LiveData
* Navigation Component
* Data binding
* Kotlin Coroutines
* Room Persistence Library
* Firebase Authentication

## Project description

If your cat is sick, and you need to take them to the vet, or to administer medication, you can set reminders that won't let you forget the stuff you need to do, through timely –but unintrusive– __notifications__.

Or, if your feline has special dietary needs throughout the day, you can also set reminders for when they need to be given X or Y foods.

Additionally, to make this app a little bit more dynamic, the user is provided with unlimited cat facts, which have been implemented for fun as a floating marquee text at the bottom, next to the "Add reminder" floating action button.

The list of reminders is displayed using a ``RecyclerView``, and each item opens a detail ``fragment`` of the individual __reminder__ set, where additional information is displayed.

This sample app implements Koin for the purpose of ``Dependency Injection`` and easier ``testing``.

The project targets API 30, with a min SDK 26.

## List of third-party libraries used in this project:
* [Retrofit 2](https://github.com/square/retrofit)
* [Moshi](https://github.com/square/moshi)
* [Glide](https://github.com/bumptech/glide)
* [Material components](https://github.com/material-components/material-components-android)
* [Stetho](https://github.com/facebookarchive/stetho)
* [Koin](https://insert-koin.io/)

## License
[GNU General Public License v3](https://www.gnu.org/licenses/gpl-3.0.en.html)

Cat facts provided by https://alexwohlbruck.github.io/cat-facts/docs/.
