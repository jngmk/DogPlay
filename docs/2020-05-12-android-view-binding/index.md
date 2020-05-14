---
title: 'Android View Biniding'
date: '2020-05-12 21:07'
slug: '/dev/app/2020-05-12-android-view-binding'
category1: 'dev'
category2: 'app'
tags: ['app', 'android', 'view binding']
---



###### 안드로이드 앱을 처음 개발해보게 되었다. 공식문서를 보며 뷰 바인딩을 공부했고, 그 내용을 요약했다.

<!-- end -->

뷰 바인딩 기능을 사용하면 뷰와 상호작용하는 코드를 쉽게 작성할 수 있다. 모듈에서 사용 설정된 뷰 바인딩은 모듈에 있는 각 XML 레이아웃 파일의 `바인딩 클래스`를 생성합니다. 바인딩 클래스의 인스턴스에는 상응하는 레이아웃에 ID가 있는 모든 뷰의 직접 참조가 포함된다.

대부분의 경우 뷰 바인딩이 `findViewById`를 대체한다.



## 설정 방법

뷰 바인딩은 모듈별로 사용 설정한다. 모듈에서 뷰 바인딩을 사용 설정하려면 다음 예와 같이 `viewBinding` 요소를 `build.gradle` 파일에 추가해야 한다.

```xml
android {
        ...
        viewBinding {
            enabled = true
        }
    }
```

바인딩 클래스를 생성하는 동안 레이아웃 파일을 무시하려면 `tools:viewBindingIgnore="true"` 속성을 레이아웃 파일의 루트 뷰에 추가해야 한다.

```xml
<LinearLayout
            ...
            tools:viewBindingIgnore="true" >
        ...
    </LinearLayout>
```



## 사용 방법

모듈에서 뷰 바인딩이 사용 설정되면 모듈에 포함된 각 XML 레이아웃 파일의 바인딩 클래스가 생성된다. 각 바인딩 클래스에는 루트 뷰 및 ID가 있는 모든 뷰의 참조가 포함된다. XML 파일의 이름을 카멜 대/소문자 표기로 변환하고 끝에 'Binding'을 추가하면 바인딩 클래스 이름이 생성된다.

예를 들어 레이아웃 파일 이름이 `result_profile.xml`인 경우는 다음과 같다.

```xml
<LinearLayout ...>
        <TextView android:id="@+id/name" />
        <ImageView android:cropToPadding="true" />
        <Button android:id="@+id/button"
            android:background="@drawable/rounded_button" />
    </LinearLayout>
```

생성된 바인딩 클래스 이름은 `ResultProfileBinding`이다. 이 클래스에는 `name`이라는 `TextView`와 `button`이라는 `Button` 등 두 필드가 있다. 레이아웃의 `ImageView`에는 ID가 없으므로 바인딩 클래스에 참조가 없다.

또한 모든 바인딩 클래스에는 상응하는 레이아웃 파일의 루트 뷰에 관한 직접 참조를 제공하는 `getRoot()` 메서드가 포함된다. 이 예에서는 `ResultProfileBinding` 클래스의 `getRoot()` 메서드가 `LinearLayout` 루트 뷰를 반환된다.

생성된 바인딩 클래스의 인스턴스를 가져오려면 정적 `inflate()` 메서드를 호출해야 한다. 일반적으로 `setContentView()`도 호출하고, 클래스의 루트 뷰를 매개변수로 전달하여 화면에서 활성 뷰로 설정한다. 이 예에서는 활동에서 `ResultProfileBinding.inflate()`를 호출한다.

```kotlin
private lateinit var binding: ResultProfileBinding

    @Override
    fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        binding = ResultProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
```

이제 바인딩 클래스의 인스턴스를 뷰를 참조하는 데 사용할 수 있다.

```kotlin
binding.name.text = viewModel.name
binding.button.setOnClickListener { viewModel.userClicked() }
```



## findViewById와의 차이점

뷰 바인딩에는 `findViewById`를 사용하는 것에 비해 다음과 같은 중요한 장점이 있다.

- **Null 안전:** 뷰 바인딩은 뷰의 직접 참조를 생성하므로 유효하지 않은 뷰 ID로 인해 null 포인터 예외가 발생할 위험이 없다. 또한 레이아웃의 일부 구성에만 뷰가 있는 경우 바인딩 클래스에서 참조를 포함하는 필드가 `@Nullable`로 표시된다.
- **유형 안전:** 각 바인딩 클래스에 있는 필드의 유형이 XML 파일에서 참조하는 뷰와 일치합니다. 즉, 클래스 변환 예외가 발생할 위험이 없다.

이러한 차이점은 레이아웃과 코드 사이의 비호환성으로 인해 런타임이 아닌 컴파일 시 빌드가 실패하게 된다는 것을 의미한다.



## 데이터 바인딩 라이브러리와의 차이점

`뷰 바인딩`과 `데이터 바인딩` 라이브러리는 둘 다 모두 뷰를 직접 참조하는 데 사용할 수 있는 바인딩 클래스를 생성한다. 하지만 다음과 같이 중요한 차이점이 있다.

- 데이터 바인딩 라이브러리는 `<layout>` 태그를 사용하여 생성된 데이터 바인딩 레이아웃만 처리한다.
- 뷰 바인딩은 레이아웃 변수 또는 레이아웃 식을 지원하지 않으므로 레이아웃을 XML의 데이터와 바인딩하는 데 사용할 수 없다.

