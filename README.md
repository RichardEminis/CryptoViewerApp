# Декомпозиция задачи
## 1. Подготовка проекта

    Декомпозиция задачи:
    Декомпозиция задачи и оценка времени.
    Время: 2 часа
    Создание нового проекта в Android Studio:
    Настройка проекта, добавление необходимых зависимостей (Retrofit, Coroutines, Navigation).
    Время: 1 час
    Настройка структуры проекта:
    Разделение на пакеты (ui, model, repository, di, utils, network).
    Время: 1 час

## 2. Настройка Retrofit и моделей данных

    Создание интерфейса ApiService:
    Определение методов для запросов API (/coins/markets, /coins/{id}).
    Время: 1 час
    Создание моделей данных для получения информации от API:
    Определение классов и других необходимых моделей.
    Время: 1 час
    Инициализация Hilt в приложении:
    Создание класса Application.
    Время: 0.5 часа
    Настройка RetrofitInstance:
    Создание singleton класса для инициализации Retrofit.
    Время: 1 час

## 3. Реализация репозитория

    Создание CryptoRepository:
    Определение методов для работы с данными.
    Время: 1 час

## 4. Реализация ViewModel

    Создание CryptoViewModel:
    Реализация бизнес-логики для взаимодействия с репозиторием, управление состояниями экрана (загрузка, ошибка, успех).
    Время: 3 часа

## 5. Реализация экрана со списком криптовалют

    Создание XML-макета для списка криптовалют:
    Создание layout файла с RecyclerView, Toolbar, и Chips для выбора валюты.
    Время: 3 часа
    Реализация CryptoListFragment:
    Настройка адаптера для RecyclerView, отображение списка криптовалют, обработка состояний (загрузка, ошибка).
    Время: 3 часа
    Настройка обработки кликов по элементам списка:
    Переход на экран детальной информации при клике на элемент списка.
    Время: 1 час

## 6. Реализация экрана детальной информации о криптовалюте

    Создание XML-макета для экрана деталей:
    Создание layout файла с ImageView для изображения криптовалюты, TextView для описания и категорий.
    Время: 3 часа
    Реализация CryptoDetailsFragment:
    Отображение деталей криптовалюты, обработка состояний (загрузка, ошибка).
    Время: 3 часа

## 7. Реализация навигации

    Настройка NavHostFragment:
    Добавление навигации между фрагментами (список -> детали и обратно).
    Время: 2 часа

## 8. Дополнительное задание: Pull to Refresh

    Настройка обновления списка криптовалют при свайпе вниз.
    Время: 2 часа

## 9. Тестирование и отладка

    Тестирование приложения:
    Проверка работы приложения, исправление ошибок.
    Время: 6 часов

## 10. Документация и финальная проверка

    Написание README.md:
    Описание проекта.
    Время: 1 час
    Проверка финальной версии:
    Финальное тестирование, убедиться в соответствии требованиям задания.
    Время: 2 часа
    
## Итоговое время на выполнение задачи: 37,5 часов
