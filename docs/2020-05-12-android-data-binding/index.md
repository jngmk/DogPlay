---
title: 'Android Data Binding'
date: '2020-05-12 23:12'
slug: '/dev/app/2020-05-12-android-data-binding'
category1: 'dev'
category2: 'app'
tags: ['app', 'android', 'data binding']
---



###### 안드로이드 앱을 처음 개발해보게 되었다. 공식문서를 보며 데이터 바인딩을 공부했고, 그 내용을 요약했다.

<!-- end -->

데이터 결합 라이브러리는 선언적 형식으로 레이아웃의 UI 구성요소를 앱의 데이터 소스와 결합할 수 있는 지원 라이브러리이다.

레이아웃은 흔히 UI 프레임워크 메서드를 호출하는 코드가 포함된 `Activity` 에서 정의됩니다. 예를 들어 아래 코드는 `findViewById()`를 호출하여 `TextView` 위젯을 찾아 `viewModel` 변수의 `userName` 속성에 결합한다.

```kotlin
findViewById<TextView>(R.id.sample_text).apply {
  text = viewModel.userName
}
```

다음 예는 데이터 결합 라이브러리를 사용하여 레이아웃 파일에서 직접 위젯에 텍스트를 할당하는 방법을 보여준다. 이 방법을 사용하면 위의 코드를 작성할 필요가 없다.

```xml
<TextView
        android:text="@{viewmodel.userName}" />
```

레이아웃 파일에서 구성요소를 결합하면 활동에서 많은 UI 프레임워크 호출을 삭제할 수 있어 파일이 더욱 단순화되고 유지관리 또한 쉬워진다. 앱 성능이 향상되며 메모리 누수 및 null 포인터 예외를 방지할 수 있다.



## 설정 방법

데이터 결합 라이브러리는 유연성과 광범위한 호환성을 모두 제공하는 지원 라이브러리이며 Android 4.0(API 레벨 14) 이상을 실행하는 기기에서 사용할 수 있다.

데이터 결합을 사용하도록 앱을 구성하려면 다음 예에서와 같이 `dataBinding` 요소를 앱 모듈의 `build.gradle` 파일에 추가해야 한다.

```
android {
        ...
        dataBinding {
            enabled = true
        }
    }
```

> **참고:** 앱 모듈이 데이터 결합을 직접 사용하지 않더라도 데이터 결합을 사용하는 라이브러리에 종속되는 앱 모듈에서는 데이터 결합을 구성해야 한다.



## 레이아웃 및 결합 표현식

데이터 결합 레이아웃 파일은 약간 차이가 있으며 `layout` 루트 태그로 시작하고 `data` 요소 및 `view` 루트 요소가 뒤따른다. 이 뷰 요소는 결합되지 않은 레이아웃 파일에 루트가 있는 요소이다. 다음 코드는 샘플 레이아웃 파일을 보여준다.

```xml
<?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android">
       <data>
           <variable name="user" type="com.example.User"/>
       </data>
       <LinearLayout
           android:orientation="vertical"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
           <TextView android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:text="@{user.firstName}"/>
           <TextView android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:text="@{user.lastName}"/>
       </LinearLayout>
    </layout>
```

`data` 내의 `user` 변수는 이 레이아웃 내에서 사용할 수 있는 속성을 설명한다.

 ```xml
<variable name="user" type="com.example.User" />
 ```

레이아웃 내의 표현식은 '`@{}`' 구문을 사용하여 속성(attribute properties)에서 작성된다. 여기서 `TextView` 텍스트는 `user` 변수의 `firstName` 속성으로 설정된다.

```xml
<TextView android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="@{user.firstName}" />
```



### 데이터 객체

`User` 항목을 설명하기 위해 간단한 기존 객체가 있다고 가정해 보겠다.

```kotlin
		data class User(val firstName: String, val lastName: String)
```



### 데이터 결합

각 레이아웃 파일의 결합 클래스가 생성된다. 기본적으로 클래스 이름은 레이아웃 파일 이름을 기반으로 하여 파스칼 표기법으로 변환하고 'Binding' 접미사를 추가한다. 위의 레이아웃 파일 이름은 `activity_main.xml`이므로 생성되는 클래스는 `ActivityMainBinding` 이다. 이 클래스는 레이아웃 속성(예: `user` 변수)에서 레이아웃 뷰까지 모든 결합을 보유하며 결합 표현식의 값을 할당할 수 있다. 권장되는 결합 생성 방법은 다음 예에서와 같이 레이아웃을 확장하는 동안 결합을 생성하는 것이다.

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)

        binding.user = User("Test", "User")
    }
```

런타임 시 앱의 UI에는 **테스트** 사용자가 표시된다. 또는 다음 예에서와 같이 `LayoutInflater`를 사용하여 뷰를 가져올 수 있다.

```kotlin
    val binding: ActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater())
```

`Fragment`, `ListView` 또는 `RecyclerView` 어댑터 내부에서 데이터 결합 항목을 사용하고 있다면, 다음 코드 예에서와 같이 결합 클래스 또는 `DataBindingUtil` 클래스의 `inflate()` 메서드를 사용할 수도 있다.

```kotlin
    val listItemBinding = ListItemBinding.inflate(layoutInflater, viewGroup, false)
    // or
    val listItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)
```



#### null 포인터 예외 방지

생성된 데이터 결합 코드는 자동으로 `null` 값을 확인하고 null 포인터 예외를 방지한다. 예를 들어 `@{user.name}` 표현식에서 `user`가 `null`이면 `user.name`에 `null`이 기본값으로 할당된다. age의 유형이 `int`인 `user.age`를 참조하면 데이터 결합은 `0`의 기본값을 사용한다.



### 이벤트 처리

데이터 결합을 사용하면 뷰에서 전달되는 표현식 처리 이벤트를 작성할 수 있다(예: `onClick()` 메서드). 이벤트 속성 이름은 몇 가지 예외를 제외하고 리스너 메서드의 이름에 따라 결정된다. 예를 들어 `View.OnClickListener`에는 `onClick()` 메서드가 있으므로 이 이벤트의 속성은 `android:onClick` 이다.

클릭 이벤트에는 충돌을 방지하기 위해 `android:onClick` 이외의 다른 속성이 필요한 특수한 이벤트 핸들러가 있다. 다음 속성을 사용하여 이러한 유형의 충돌을 방지할 수 있다.

| 클래스       | 리스너 setter                                     | 속성                    |
| :----------- | :------------------------------------------------ | :---------------------- |
| SearchView   | `setOnSearchClickListener(View.OnClickListener)`  | `android:onSearchClick` |
| ZoomControls | `setOnZoomInClickListener(View.OnClickListener)`  | `android:onZoomIn`      |
| ZoomControls | `setOnZoomOutClickListener(View.OnClickListener)` | `android:onZoomOut`     |



#### 메서드 참조

이벤트는 `android:onClick` 이 활동의 메서드에 할당되는 방식과 유사하게 핸들러 메서드에 직접 결합될 수 있다. `View` `onClick` 속성과 비교했을 때 주요 이점은 표현식이 컴파일 타임에 처리되므로, 메서드가 없거나 서명이 올바르지 않으면 **컴파일 타임 오류가 발생한다는 점**이다.

메서드 참조와 리스너 결합의 주요 차이점은 실제 리스너 구현이 이벤트가 트리거될 때가 아니라 데이터가 결합될 때 생성된다는 점이다. 이벤트가 발생할 때 표현식을 계산하려면 `리스너 결합`을 사용해야 한다.

핸들러에 이벤트를 할당하려면 호출할 메서드 이름이 될 값을 사용하여 일반 결합 표현식을 사용해야 한다. 

```kotlin
    class MyHandlers {
        fun onClickFriend(view: View) { ... }
    }
```

결합 표현식은 다음과 같이 뷰의 클릭 리스너를 `onClickFriend()` 메서드에 할당할 수 있다.

```xml
<?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android">
       <data>
           <variable name="handlers" type="com.example.MyHandlers"/>
           <variable name="user" type="com.example.User"/>
       </data>
       <LinearLayout
           android:orientation="vertical"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
           <TextView android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:text="@{user.firstName}"
               android:onClick="@{handlers::onClickFriend}"/>
       </LinearLayout>
    </layout>
```



#### 리스터 결합

이벤트가 발생할 때 실행되는 결합 표현식이다. 리스너 결합은 메서드 참조와 비슷하다. 하지만 리스너 결합을 사용하면 **임의의 데이터 결합 표현식을 실행할 수 있다.** 이 기능은 Gradle 버전 2.0 이상을 위한 Android Gradle 플러그인으로 사용할 수 있다.

리스너 결합에서는 반환 값만 리스너의 예상 반환 값과 일치하면 된다.

```kotlin
    class Presenter {
        fun onSaveClick(task: Task){}
    }
```

그리고 다음과 같이 클릭 이벤트를 `onSaveClick()` 메서드에 결합할 수 있다.

```xml
<?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android">
        <data>
            <variable name="task" type="com.android.example.Task" />
            <variable name="presenter" type="com.android.example.Presenter" />
        </data>
        <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent">
            <Button android:layout_width="wrap_content" android:layout_height="wrap_content"
            android:onClick="@{() -> presenter.onSaveClick(task)}" />
        </LinearLayout>
    </layout>
```



수신 중인 이벤트가 `void`가 아닌 유형의 값을 반환하면, 표현식도 같은 유형의 값을 반환해야 한다. 예를 들어 '길게 클릭' 이벤트를 수신 대기하려면 표현식에서 부울을 반환해야 한다.

```kotlin
    class Presenter {
        fun onLongClick(view: View, task: Task): Boolean { }
    }
```

```xml
android:onLongClick="@{(theView) -> presenter.onLongClick(theView, task)}"
```

`null` 객체로 인해 표현식을 계산할 수 없으면, 데이터 결합은 각기 해당하는 유형의 기본값을 반환한다. 예를 들어 참조 유형은 `null`을, `int`는 `0`을, `boolean`은 `false`를 기본값으로 반환한다.

조건자와 함께 표현식(예: 삼항)을 사용해야 한다면 `void`를 기호로 사용할 수 있다.

```xml
android:onClick="@{(v) -> v.isVisible() ? doSomething() : void}"
```



### Import, Variable, Include

#### import

가져오기를 사용하면 관리형 코드에서와 같이 레이아웃 파일 내에서 클래스를 쉽게 참조할 수 있다. 0개 이상의 `import` 요소를 `data` 요소 내에서 사용할 수 있다.

```xml
<data>
  	<import type="android.view.View"/>
</data>
```

`View` 클래스를 가져오면 결합 표현식에서 참조할 수 있다.

```xml
<TextView
       android:text="@{user.lastName}"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"/>
```

클래스 이름 충돌이 발생하면 클래스 중 하나의 이름을 별칭으로 바꿀 수 있다. 

```xml
<import type="android.view.View"/>
<import type="com.example.real.estate.View"
        alias="Vista"/>
```



#### Variable

`data` 요소 내에서 여러 `variable` 요소를 사용할 수 있다.

```xml
<data>
    <import type="android.graphics.drawable.Drawable"/>
    <variable name="user" type="com.example.User"/>
    <variable name="image" type="Drawable"/>
    <variable name="note" type="String"/>
</data>
```

다양한 구성(예: 가로 모드 또는 세로 모드)의 레이아웃 파일이 서로 다를 때 변수가 충돌할 수 있다. 이러한 레이아웃 파일 간에 충돌하는 변수 정의가 있어서는 안 된다.



#### Include

속성에 앱 네임스페이스 및 변수 이름을 사용함으로써 포함하는 레이아웃에서 포함된 레이아웃의 결합으로 변수를 전달할 수 있다.

```xml
<?xml version="1.0" encoding="utf-8"?>
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:bind="http://schemas.android.com/apk/res-auto">
       <data>
           <variable name="user" type="com.example.User"/>
       </data>
       <LinearLayout
           android:orientation="vertical"
           android:layout_width="match_parent"
           android:layout_height="match_parent">
           <include layout="@layout/name"
               bind:user="@{user}"/>
           <include layout="@layout/contact"
               bind:user="@{user}"/>
       </LinearLayout>
    </layout>
```



## 식별 가능한 데이터 개체 작업

식별 가능한 데이터 개체 중 하나가 UI에 결합되고 데이터 개체의 속성이 변경되면 UI가 자동으로 업데이트된다.



### 식별 가능한 필드

식별 가능한 필드는 단일 필드가 있는 독립적인 식별 가능한 개체이다.

```kotlin
    class User {
        val firstName = ObservableField<String>()
        val lastName = ObservableField<String>()
        val age = ObservableInt()
    }
```

다음과 같이 필드 값에 접근할 수 있다.

```kotlin
    user.firstName = "Google"
    val age = user.age
```

> **참고:** Android 스튜디오 3.1 이상을 사용하면 식별 가능한 필드를 LiveData 개체로 바꿀 수 있다. 이 기능은 앱에 추가적인 이점을 제공한다.



### 식별 가능한 컬렉션

식별 가능한 컬렉션을 통해 키를 사용하여 이러한 구조에 액세스할 수 있다.

```kotlin
    ObservableArrayMap<String, Any>().apply {
        put("firstName", "Google")
        put("lastName", "Inc.")
        put("age", 17)
    }
```

다음과 같이 레이아웃에서 문자열 키를 사용하여 맵을 찾을 수 있다.

```kotlin
<data>
        <import type="android.databinding.ObservableMap"/>
        <variable name="user" type="ObservableMap<String, Object>"/>
    </data>
    …
    <TextView
        android:text="@{user.lastName}"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    <TextView
        android:text="@{String.valueOf(1 + (Integer)user.age)}"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```

키가 정수일 때에는 `ObservableArrayList` 클래스가 유용하다.

```kotlin
    ObservableArrayList<Any>().apply {
        add("Google")
        add("Inc.")
        add(17)
    }
```

다음 예에서와 같이 레이아웃에서 인덱스를 통해 목록에 액세스할 수 있다.

```kotlin
<data>
        <import type="android.databinding.ObservableList"/>
        <import type="com.example.my.app.Fields"/>
        <variable name="user" type="ObservableList<Object>"/>
    </data>
    …
    <TextView
        android:text='@{user[Fields.LAST_NAME]}'
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    <TextView
        android:text='@{String.valueOf(1 + (Integer)user[Fields.AGE])}'
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```



### 식별 가능한 개체

`Observable` 인터페이스를 구현하는 클래스를 사용하면 식별 가능한 개체의 속성 변경에 관해 알림을 받으려는 리스너를 등록할 수 있다.

`Observable` 인터페이스에 리스너를 추가 및 삭제하는 메커니즘이 있지만 알림이 전송되는 시점은 개발자가 직접 결정해야 한다. 더 쉽게 개발할 수 있도록 데이터 결합 라이브러리는 리스너 등록 메커니즘을 구현하는 `BaseObservable` 클래스를 제공한다. `BaseObservable`을 구현하는 데이터 클래스는 속성이 변경될 때 알리는 역할을 한다.

```kotlin
    class User : BaseObservable() {

        @get:Bindable
        var firstName: String = ""
            set(value) {
                field = value
                notifyPropertyChanged(BR.firstName)
            }

        @get:Bindable
        var lastName: String = ""
            set(value) {
                field = value
                notifyPropertyChanged(BR.lastName)
            }
    }
```



## 양방향 데이터 결합

다음과 같이 단방향 데이터 결합을 사용하면 속성에 값을 설정하고 이 속성의 변경에 반응하는 리스너를 설정할 수 있다.

```xml
    <CheckBox
        android:id="@+id/rememberMeCheckBox"
        android:checked="@{viewmodel.rememberMe}"
        android:onCheckedChanged="@{viewmodel.rememberMeChanged}"
    />
```

다음과 같이 양방향 데이터 결합을 할 수 있다.

```xml
    <CheckBox
        android:id="@+id/rememberMeCheckBox"
        android:checked="@={viewmodel.rememberMe}"
    />
```

'=' 기호가 포함된 `@={}` 표기법은 속성과 관련된 데이터 변경사항을 받는 동시에 사용자 업데이트를 수신한다.



백업 데이터의 변경에 대응하기 위해 다음 코드 스니펫에서와 같이 레이아웃 변수를 `Observable` 일반적으로 `BaseObservable`의 구현으로 만들고 `@Bindable` 을 사용할 수 있다.

```kotlin
    class LoginViewModel : BaseObservable {
        // val data = ...

        @Bindable
        fun getRememberMe(): Boolean {
            return data.rememberMe
        }

        fun setRememberMe(value: Boolean) {
            // Avoids infinite loops.
            if (data.rememberMe != value) {
                data.rememberMe = value

                // React to the change.
                saveData()

                // Notify observers of a new value.
                notifyPropertyChanged(BR.remember_me)
            }
        }
    }
```



### Converter

`View` 개체에 결합된 변수를 표시하기 전에 먼저 형식 지정, 변환 또는 변경을 해야 하면 `Converter` 개체를 사용하면 된다.

예를 들어 날짜를 보여주는 `EditText` 개체를 사용한다.

```xml
<EditText
        android:id="@+id/birth_date"
        android:text="@={Converter.dateToString(viewmodel.birthDate)}"
    />
```

`viewmodel.birthDate` 속성에는 `Long` 유형의 값이 포함되어 있으므로 변환기를 사용하여 형식을 지정해야 한다.

또한 양방향 표현식을 사용 중이므로 사용자가 제공한 문자열을 백업 데이터 유형(이 사례에서는 `Long`)으로 다시 변환하는 방법을 라이브러리에 알려주는 `역변환기`도 있어야 한다. 이 프로세스는 변환기 중 하나에 `@InverseMethod` 을 추가하고 이 annotation이 `역변환기`를 참조하도록 함으로써 완료할 수 있다.

```kotlin
    object Converter {
        @InverseMethod("stringToDate")
        fun dateToString(
            view: EditText, oldValue: Long,
            value: Long
        ): String {
            // Converts long to String.
        }

        fun stringToDate(
            view: EditText, oldValue: String,
            value: String
        ): Long {
            // Converts String to long.
        }
    }
```

