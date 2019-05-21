### JStarter

- this library contains all base classes needed for our projects

## libraries used 
- reactive extension Rxjava2
- dependency injection Dagger2
- architecture components (LiveData and ViewModel )

we also used MvRx concepts

[![](https://jitpack.io/v/team0se7en/JStarter.svg)](https://jitpack.io/#team0se7en/JStarter)

## How to
### from jcenter

```Gradle
dependencies {
	...
    implementation 'com.roacult.team7:Jstarter:1.0.1'
}
```
### or from jitpack

Add it in your root build.gradle at the end of repositories:
```Gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency in your app build.gradle file
```Gradle
dependencies {
	...
    implementation 'com.github.team0se7en:JStarter:1.0.1'
}
```
