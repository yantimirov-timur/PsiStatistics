# PsiStatistics


## Описание
Плагин собирает статистику самых используемых PSi элементов в .kt файле

## Подключение
1. Зайдите в последние релизы этого репозитория
2. Скачайте .zip файл с плагином
3. Далее в IDEA зайдите в `Settings` -> `Plugin` ->*`значок шестеренки`* -> `Install Plugin From Disk`
4. Выберите скачанный .zip файл

## Использование
Для запуска работы плагина откройте любой .kt файл, далее нажмите сочетание клавив Ctrl Q + Ctrl U.
Возле вашего файла создастся .txt файл с собранной статистикой

## Визуализация статистики
 - Для этого перейдите релизы и скачайте оттуда последний релиз "graphic_build.py" (*исходный код приложения находится в пакете "visualisator"*)
 - Для запуска визуализации на компьютере должен быть установен Python 3.9 и библиотека для построения графиков matplotlib
 - Откройте командную строку Windows. 
 - Введите python graphic_build.py --f <Имя файла с собранной статистикой>(*файл должен оканчиваться на Psi_Stat*)
 - В этой же директории появится .png файл с графиком
 - *Желательно, чтобы консольное приложение и файл с собранной статистикой были в одной директории, чтобы не прописывать пути к файлам*

## Примеры
*Все файлы, с которых собиралась статистика находятся в пакете examples*

В качестве примера использованы:
 - маленький файл с одним небольшним классом 
 - один большой файл из проекта с задачами на курс `Алгоритмы и Структуры данных`

Статистика маленького файла
![График](https://github.com/yantimirov-timur/PsiStatistics/blob/master/examples/plots/PsiStatisticStudent.kt_PsiStat.png)

Статистика большого файла
![График](https://github.com/yantimirov-timur/PsiStatistics/blob/master/examples/plots/PsiStatisticKtBinarySearchTree.kt_PsiStat.png)

## Дополнительная информация
Статистика собраная не по всем PSI элементам, а только по `LeafPsiElements`. То есть, только по листьям этого дерева,
так как именно они несут наиболее полезную и содержательную информацию. Также, из статистики был исключен `PsiWhiteSpace`, так как этот элемент 
не несет никакой полезной информации, при этом его количество в 2-3 раза больше других, что мешало построению графика.

## Итоговый анализ
Из исследуемых файлов видно, что чаще всего встречаются элементы: `IDENTIFIER`, `DOT`,`LPAR`,`RPAP`
Также, если смотреть не только на "листья", а на все элементы, то чаще всего встречаются:





