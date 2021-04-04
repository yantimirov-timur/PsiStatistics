# PsiStatistics


## Описание
Плагин собирает статистику самых используемых Psi элементов в .kt файле

## Подключение
1. Зайдите в последние релизы этого репозитория
2. Скачайте .zip файл с плагином
3. Далее в IDEA зайдите в `Settings` -> `Plugin` ->*`значок шестеренки`* -> `Install Plugin From Disk`
4. Выберите скачанный .zip файл

## Использование
Для запуска работы плагина откройте любой .kt файл, далее нажмите сочетание клавив Ctrl Q + Ctrl U.
Возле вашего файла создастся .txt файл с собранной статистикой

## Визуализация статистики
 - Для этого перейдите realeases и скачайте оттуда последний релиз "graphic_build.py" (*исходный код приложения находится в пакете "visualisator"*)
 - Для запуска визуализации на компьютере должен быть установен Python 3.8+ и библиотека для построения графиков matplotlib
 - Откройте командную строку Windows. 
 - Введите: python graphic_build.py --f <Имя файла с собранной статистикой>(*файл должен оканчиваться на Psi_Stat*)
 - В этой же директории появится .png файл с графиком
 - *Желательно, чтобы консольное приложение и файл с собранной статистикой были в одной директории, чтобы не прописывать пути к файлам вручную*

## Примеры
*Все файлы, с которых собиралась статистика и сама статистика находятся в пакете examples*

В качестве примера использованы:
 - маленький файл с одним небольшним классом 
 - один большой файл из проекта с задачами на курс `Алгоритмы и Структуры данных`


 - График файла с небольшим классом:  
![alt-текст](https://github.com/yantimirov-timur/PsiStatistics/blob/master/examples/plots/PsiStatisticStudent.kt_PsiStat.png)

 - График файла с задачами
![alt-текст](https://github.com/yantimirov-timur/PsiStatistics/blob/master/examples/plots/PsiStatisticKtBinarySearchTree.kt_PsiStat.png)


## Дополнительная информация
Статистика собирается не по всем PSI элементам, а только по `LeafPsiElements`. То есть, только по листьям этого дерева,
так как именно они несут наиболее полезную и содержательную информацию. Также из сбора статистики листьев был умышленно исключен `PsiWhiteSpace`, так как 
этот элемент не несет никакой полезной информации и при этом его "вес" значительно больше других элементов (примерно в 2-3 раза), что мешало построению графиков

## Итоговый анализ
Наиболее популярным элементом в исследуемых файлах был `IDENTIFIER`, `LPAR`, `RPAR`, `EQ`.
Также был проведен анализ всех PSI элементов. Наиболее частым является `BLOCK`, `CALL_EXPRESSION`, `OPERATION_REFERENCE`, `MODIFIER_LIST`.
