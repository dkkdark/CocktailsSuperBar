# CocktailsSuperBar

Основной стэк технологий приложения: jetpack compose, hilt, room, корутины. 
Я использовала паттерн проектирования MVVM, разбила приложение на модули и постаралась придерживаться Clean Architecture при написании кода.

Удалось реализовать самое основное:

![Screenshot_20230808_223414_Cockt](https://github.com/dkkdark/CocktailsSuperBar/assets/49618961/7f066410-ec4a-46b0-809c-f19b1e945aa1)


![Screenshot_20230808_223700_Cockt](https://github.com/dkkdark/CocktailsSuperBar/assets/49618961/1e35f3dd-5d4d-4ab0-8df4-3f2e4ae64692)



При сохранинии во viewModel вызывается useCase.


![Screenshot_20230808_223752_Cockt](https://github.com/dkkdark/CocktailsSuperBar/assets/49618961/bcf16625-5309-4e8b-9479-d0ca3b9a45cc)


То же самое здесь.

![Screenshot_20230808_223921_Cockt](https://github.com/dkkdark/CocktailsSuperBar/assets/49618961/a1076800-0923-47a3-96b2-eedc4a1a4d81)



То, что не удалось:

- Удаление и изменение существуют только в dao, для них осталось написать usecase.

- Контейнер для FAB я бы делала с помощью canvas, в том числе при использовании xml.

- Картинку для коктеля я бы сохраняла в виде ссыли на изображение.
