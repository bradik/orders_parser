### Тестовое задание Java-разработчик
####Входные данные
#####CSV файл. 
Назначение столбцов: 
* Идентификатор ордера, сумма, валюта, комментарий 	

#####Пример записи:
```
1,100,USD,оплата заказа
2,123,EUR,оплата заказа
```

**Примечание**: все столбцы обязательны

#####JSON файл.
#####Пример записи:
```
{“orderId”:3,”amount”:1.23,”currency”:”USD”,”comment”:”оплата заказа”}
{“orderId”:4,”amount”:1.24,”currency”:”EUR”,”comment”:”оплата заказа”}
```

**Примечание**: все поля обязательны

Выходные данные
```
{“id”:1,“orderId”:1,”amount”:100,”comment”:”оплата заказа”,”filename”:”orders.csv”,”line”:1,”result”:”OK”}
{“id”:2,“orderId”:2,”amount”:123,”comment”:”оплата заказа”,”filename”:”orders.csv”,”line”:2,”result”:”OK”}
{“id”:3,“orderId”:3,”amount”:1.23,”comment”:”оплата заказа”,”filename”:”orders.json”,”line”:1,”result”:”OK”}
{“id”:4,“orderId”:4,”amount”:1.24,”comment”:”оплата заказа”,”filename”:”orders.json”,”line”:2,”result”:”OK”}
```

**id** - идентификатор ордера

**amount** - сумма ордера

**currency** - валюта суммы ордера

**comment** - комментарий по ордеру

**filename** - имя исходного файла

**line** - номер строки исходного файла

**result** - результат парсинга записи исходного файла.
 
**OK** - если запись конвертирована корректно,
 
или описание ошибки если запись не удалось конвертировать.

####Описание задания:

Необходимо разработать приложение парсинга входящих данных и конвертирования результат парсинга в результирующий формат.

Требуется простое решение задания,как если бы приложение могли поддерживать и сопровождать другие менее опытные разработчики 
Приложение должно быть реализовано с использованием фреймворка **Spring**.

Исходные код приложения должен быть оформлен в виде **maven** проекта и размещён на GitHub. Допускается использовать зависимости только из **публичных репозиториев**. 

Сборка конечного приложения должна быть выполнена командой: 
```cmd
mvn clean install
```

Приложение должно быть консольным.
 
Пример команды запуска: 
```cmd
java -jar orders_parser.jar orders1.csv orders2.json
```
где **orders1.csv** и **orders2.json** файлы для парсинга.

Результат выполнения должен выводиться в **stdout** поток.

**Примечание:** в stdout должны попасть только выходные данные, логов там быть не должно.

* Парсинг и конвертирование должны выполняться параллельно в несколько потоков.

* Необходимо предусмотреть корректную обработку ошибок в исходных файлах.
 
* Например, вместо числа в файле может быть строковое значение в поле amount.

* Разрешается использовать инструменты языка не выше **Java 8**.

* Необходимо учесть возможность добавления новых форматов входящих данных. Например: XLSX