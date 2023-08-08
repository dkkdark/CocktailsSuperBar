# CocktailsSuperBar

Основной стэк технологий приложения: jetpack compose, hilt, room, корутины. 
Я использовала паттерн проектирования MVVM, разбила приложение на модули и постаралась придерживаться Clean Architecture при написании кода.

Удалось реализовать самое основное:
![Screenshot_20230808_223414_CocktailSuperBar](https://github.com/dkkdark/CocktailsSuperBar/assets/49618961/94477715-978e-43c1-8ea7-020e1a5a7246)

![Screenshot_20230808_223700_CocktailSuperBar](https://github.com/dkkdark/CocktailsSuperBar/assets/49618961/b91079df-4975-4109-9171-19c93a86646f)


При сохранинии во viewModel вызывается useCase.

![Screenshot_20230808_223700_CocktailSuperBar](https://github.com/dkkdark/CocktailsSuperBar/assets/49618961/8d1bb9f1-3362-47a2-a633-56d4d9619397)


То же самое здесь.
![Screenshot_20230808_223921_CocktailSuperBar](https://github.com/dkkdark/CocktailsSuperBar/assets/49618961/baf66e19-bda5-45b8-b689-1fd86421ad3f)


То, что не удалось:

- Удаление и изменение существуют только в dao, для них осталось написать usecase.

- Контейнер для FAB я бы делала с помощью canvas, в том числе при использовании xml.

- Картинку для коктеля я бы сохраняла в виде ссыли на изображение.
