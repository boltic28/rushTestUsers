Задание CRUD (Idea 15.0.3 Ult, vaadin)
====================

Используемые технологии и их версии:
 - JavaSE  1.8.0_74
 - Maven 3.3.9
 - Tomcat 8.0.30
 - Vaadin 7.6.2
 - MySQL server 5.7
 - Google chrome(view project)

 Описание.
=======================

    Для запуска приложения не требуется никаких дополнительных манипуляций. После запуска, приложение 
    попробует подключиться к базе данных по адресу localhost: 3306/test  root root; Если в данной базе 
    имеется набор значений, то он будет загружен в приложение, если база пуста то  отобразится пустая 
    таблица. Значения можно сгенерировать из приложения - кнопка: GENERATE.
    
    
    
    Приложение создавал на основе примера использование библиотеки Vaadin (Vaadin.com),
    поэтому бонусом прикручен фильтр, с нуля что-то мне слабо было начать, но на момент
    написания приложения много узнал нового, и я думаю что смог бы при  некоторых усилиях
    написать и с нуля такое. (только со сборкой надо бы подразобраться :))

    В коде есть методы которые не используются, но я считаю что они должны быть( закрытие коннекта )

    Да по поводу boolean isAdmin, так сложилось что она у меня не boolean, sorry.
    Это потому му что я не допер ещё как устроен и работает BeanFieldGroup<User>, ну это
    исправимо.

