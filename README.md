# PsiStatistics


## Описание
Плагин собирает статистику самых используемых PSi элементов в .kt файле

## Подключение
1. Зайдите в последние релизы этого репозитория
2. Скачайте .zip файл с плагином
3. Далее в IDEA зайдите в Setting -> Pluging ->*значок гайки* -> Install Plugin From Disk
4. Выберите скачаннывй .zip файл

## Использование
Для запуска работы плагина откройте любой .kt файл, далее нажмите сочетание клавив Ctrl Q + Ctrl U.
Возле вашего файла создастся .txt файл с собранной статистикой

## Визуализация статистики
 - Для этого перейдите в репозиторий с консольным приложением и скачайте оттуда последний релиз "build_graphic.py"
 - Для запуска визуализации на компьютере должен быть установен Python 3.9 и библиотека для построения графиков matplotlib
 - Откройте командную строку Windows. 
 - Введите python build_graphic.py -f <Имя файла с собранной статистикой>
 - В этой же папке появится .png файл с графиком
