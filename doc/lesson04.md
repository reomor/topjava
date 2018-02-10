# Онлайн проект <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfkxqbVpwZUd5anQ2TXE4bm5HbXhtVmkxMUxFSjhNQ1hXYVVTTTZEMzkzN2s">Материалы занятия</a>

### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте
#### **Apply 4_0_fix.patch**
- В `JdbcUserRepositoryImpl.save()` и `UserServiceImpl.update` добавил проверку на несуществующей в базе `User.id`
- Зарефакторил в `InMemoryMealRepositoryImpl.getAll/getBetween`: `filter()` перед `sorted()`

**Примечание**: в ответе на [Why is SELECT * considered harmful?](https://stackoverflow.com/questions/3639861) есть случаи, когда она допустима (наш случай):
> When * means "a row"

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW3

> `SpringMain, InMemoryAdminRestControllerTest, InMemoryAdminRestControllerSpringTest` починим в патче **4_5_create_mock_test_ctx.patch (видео 4)**

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFdVhaMklZQVNkUGc">JdbcMealRepositoryImpl + MealServiceTest</a>
#### **Apply 4_1_HW3.patch**
> - В `JdbcUserRepositoryImpl` поменял `MapSqlParameterSource` на `BeanPropertySqlParameterSource` (поля для вставки определяются через отражение в бине и метаданные в SQL запросе). 
 В `JdbcMealRepositoryImpl` остается `MapSqlParameterSource`, т.к. в отсутствует `Meal.userId`. См. дополнительно [CombinedSqlParameterSource](https://www.codota.com/java/spring/scenarios/549bbb5dda0a9536b85ad5f3/org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource?tag=spring1b)
> - Новый Postgres драйвер <a href="https://jdbc.postgresql.org/documentation/head/8-date-time.html">поддерживает Java 8 Date and Time</a>. Преобразования c `Timestamp` уже не нужны.
> - В meals добавил составной индекс `INDEX meals_unique_user_datetime_idx ON meals(user_id, date_time)` для повышения скорости запросов по этим полям:
> - Удалил лишние `MealsUtil.MEALS` и `main()`

- <a href="http://www.techonthenet.com/postgresql/between.php">POSTGRESQL: BETWEEN CONDITION</a>
- **[Сравнение времени выполнения для разных индексов](meals_index.md)**
  - <a href="http://stackoverflow.com/questions/970562/postgres-and-indexes-on-foreign-keys-and-primary-keys">На id как на primary key индекс создается автоматически</a>.
  - все запросы в таблицу meals у нас идут с `user_id`
  - по полю `date_time` также есть запросы + мы по нему сортируем список результатов, те они- хорошие кандидаты для индексирования. 
  - следует иметь в виду, индексы ускоряют операции чтения, но замедляют вставку и удаление, поэтому необходим анализ в реальном приложении
  - [Оптимизация запросов. Основы EXPLAIN в PostgreSQL](https://habrahabr.ru/post/203320/)
  - [Оптимизация запросов. Часть 2](https://habrahabr.ru/post/203386/)
  - [Оптимизация запросов. Часть 3](https://habrahabr.ru/post/203484/)
  - [Документация Postgres: индексы](https://postgrespro.ru/docs/postgresql/9.6/indexes.html)

#### **Apply 4_2_HW3_optional.patch**

## Занятие 4:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFU005ZzBNZmZnTVU">Методы улучшения качества кода</a>
- **Внимание!! В IDEA 2017.1 и 2 любой QAPlugin крэшится**. Можно заменить [FindBugs-IDEA](http://andrepdo.github.io/findbugs-idea)
- Сделайте интеграцию своего GitHub репозитория и подключите сверху своего [README.md](https://github.com/JavaWebinar/topjava/blob/master/README.md) ([Raw](https://raw.githubusercontent.com/JavaWebinar/topjava/master/README.md)) интергацию с
  - <a href="https://www.codacy.com">Codacy Check code</a> (проверка стиля и поиск багов в коде). В <a href="https://www.codacy.com/app/javawebinar/topjava/dashboard">Codacy Issues</a>
     - отключил в настройках (remove pattern) проверку assert в JUnit (проверяем через матчеры) и [`meta http-equiv="content-type"` в JSP](https://stackoverflow.com/a/45440410/548473)
     - сделал remove pattern на Cross Site Scripting (XSS), будем делать защиту на последнем занятии   
  - [VersionEye](https://www.versioneye.com) (проверка зависимостей Maven на актуальность). Кликни на значке `dependencies` в versioneye своего проекта, чтобы взять ссылку для `read.me`
  - <a href="https://travis-ci.org/">Сборку и тесты Travis</a> (результат выполнения тестов проекта)
     - [Travis CI Tutorial](https://dzone.com/articles/travis-ci-tutorial-java-projects)
     - <a href="https://docs.travis-ci.com/user/languages/java/">Сборка Java проекта</a>

#### **Apply 4_3_improve_code.patch**
> - Перенес проверки предусловий `Assert` из `InMemory` репозиториев в сервисы
> - Добавил конфигурацию `.travis.yml`
> - По [рапорту VersionEye](https://www.versioneye.com/user/projects/5a14435c0fb24f2a6d408cde) обновил зависимости Maven (будем пробовать Spring 5!)

- <a href="https://ru.wikipedia.org/wiki/Контрактное_программирование">Контрактное программирование</a>, <a href="http://neerc.ifmo.ru/wiki/index.php?title=Программирование_по_контракту">Программирование по контракту</a>
- <a href="http://www.sw-engineering-candies.com/blog-1/comparison-of-ways-to-check-preconditions-in-java">Comparison Preconditions in Java</a>
- IDEA [Analyze | Inspect Code](https://www.jetbrains.com/help/idea/2017.1/running-inspections.html)
- <a href="https://code.google.com/archive/p/findbugs/wikis/IntellijFindBugsPlugins.wiki">QAPlug vs FindBugs</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=1faq0dtek-RcBENfISkod35PGU5jcuOsB">Spring: инициализация и популирование DB</a>
#### **Apply 4_4_init_and_populate_db.patch**
-  [Инициализация базы при старте приложения](https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#jdbc-initializing-datasource-xml)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNTNWV04weDBGSmc">Подмена контекста при тестировании</a>
#### **Apply 4_5_create_mock_test_ctx.patch**

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVWZYcHoyUF9qX2M">ORM. Hibernate. JPA.</a>
<a href="https://en.wikipedia.org/wiki/Entity%E2%80%93relationship_model">Entity</a>- класс (объект Java), который в ORM маппится в таблицу DB.

> - ВНИМАНИЕ: патч меняет `postgres.properties`, в котором у вас возможно свои креденшелы к базе.
> - `hibernate-core` 5.2.x включает `hibernate-entitymanager` и `hibernate-java8`, Time API конверторы уже не нужны.
>    -  <a href="http://stackoverflow.com/questions/23718383/jpa-support-for-java-8-new-date-and-time-api">JPA support for Java 8 new date and time API</a>
>    -  <a href="http://stackoverflow.com/questions/31965179/whats-new-in-hibernate-5">What's new in Hibernate 5?</a>
>    -  <a href="http://stackoverflow.com/a/33001846/548473">JPA support for Java 8 new date and time API</a>
> - [EL implementation provided by the container. In a Java SE you have to add an implementation as dependency to your POM file](http://hibernate.org/validator/documentation/getting-started/#unified-expression-language-el): добавил `javax.el` зависимость со `scope=provided`

#### **Apply 4_6_add_jpa.patch**
> - Тесты и приложение ломаются. `MealServiceTest` починится после выполнения HW04 (`JpaMealRepositoryImpl`)
> - При [настройке JPA в IDEA](https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%94%D0%BE%D0%B1%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-jpa) НЕ скачивайте библиотеку javaee.jar (и любую другую). **Все зависимости в проект попадают только через Maven.**
> - Если вы используете Java 9, то возникают проблемы с `JAXBException` (пакет `java.xml.bind`). [См. решение](https://www.concretepage.com/forum/thread?qid=531)
- Дополнительно:
    -  <a href="http://ru.wikipedia.org/wiki/ORM">ORM</a>.
    -  <a href="http://habrahabr.ru/post/265061/">JPA и Hibernate в вопросах и ответах</a>
    -  <a href="https://easyjava.ru/data/jpa/jpa-entitymanager-upravlyaem-sushhnostyami/">JPA EntityManager: управляем сущностями</a>
    -  [Field vs property access](http://stackoverflow.com/a/6084701/548473)
    -  <a href="http://www.quizful.net/post/Hibernate-3-introduction-and-writing-hello-world-application">Hibernate: введение и написания Hello world приложения</a>
    - [15 reasons why we need to choose Hibernate over JDBC](https://habiletechnologies.com/blog/reasons-to-choose-hibernate-over-jdbc)
    -  <a href="http://en.wikibooks.org/wiki/Java_Persistence/Mapping">Mapping: описания модели Hibernate (hbm.xml/annotation)</a>.
    -  <a href="https://ru.wikipedia.org/wiki/Hibernate_(библиотека)">Hibernate</a>. Другие ORM: <a href="http://en.wikipedia.org/wiki/TopLink">TopLink</a>, <a href="http://en.wikipedia.org/wiki/EclipseLink">EсlipseLink</a>, <a href="http://en.wikipedia.org/wiki/Ebean">EBean</a> (<a href="http://www.playframework.com/documentation/2.2.x/JavaEbean">used in Playframework</a>).
    -  <a href="http://ru.wikipedia.org/wiki/Java_Persistence_API">JPA (wiki)</a>. <a href="https://en.wikipedia.org/wiki/Java_Persistence_API">JPA (english wiki)</a>. <a href="http://www.jpab.org/All/All/All.html">JPA Performance Benchmark</a>
    -  <a href="http://en.wikibooks.org/wiki/Java_Persistence/Inheritance">Отображения наследования объектов на таблицы</a>
    -  <a href="http://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing">Стратегии генерации PK</a>
    -  <a href="http://validator.hibernate.org">hibernate-validator</a>. <a href="http://stackoverflow.com/questions/14730329/jpa-2-0-exception-to-use-javax-validation-package-in-jpa-2-0">JSR-303 -> JSR-349</a>
    -  <a href="https://web.archive.org/web/20170514002949/http://java.devcolibri.com:80/post/15">Описание связей в модели. Ленивая загрузка объекта.</a>
    -  <a href="http://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html/architecture.html#d0e61">JPA definitions</a>
    -  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions">Spring expressions: выражения в конфигурации</a>
    -  <a href="http://docs.jboss.org/hibernate/orm/4.2/devguide/en-US/html/ch11.html">HQL</a>/ <a href="http://ru.wikipedia.org/wiki/Java_Persistence_Query_Language">JPQL</a>.
    -  Динамические запросы (которые формируются в коде): <a href="http://www.objectdb.com/java/jpa/query/criteria">JPA Criteria API</a>. <a href="http://www.querydsl.com/">Unified Queries for Java</a>
    -  <a href="https://bitbucket.org/montanajava/jpaattributeconverters">Using the Java 8 Date Time Classes with JPA</a>

#### **Apply 4_7_add_named_query_and_transaction.patch**

-  <a href="http://ru.wikipedia.org/wiki/Транзакция_(информатика)">Транзакция. ACID. Уровни изоляции транзакций.</a>
-  <a href="http://www.tutorialspoint.com/spring/spring_transaction_management.htm">Spring Transaction Management</a>
-  <a href="https://jira.spring.io/browse/DATAJPA-601">readOnly и Propagation.SUPPORTS</a>
-  <a href="http://habrahabr.ru/post/232381/">`@Transactional` в тестах. Настройка EntityManagerFactory</a>

Справочник:
   - <a href="https://www.youtube.com/watch?v=dFASbaIG-UU">Видео: Вячеслав Круглов — Как начинающему Java-разработчику подружиться со своей базой данных?</a>
   - <a href="http://www.youtube.com/watch?v=YzOTZTt-PR0">Видео: Николай Алименков — Босиком по граблям Hibernate</a>
   - <a href="https://www.ibm.com/developerworks/ru/library/j-ts2/">Стратегии работы с транзакциями</a>
   - <a href="https://easyjava.ru/tag/jpa/">Примеры работы с JPA</a>
   - <a href="http://www.byteslounge.com/tutorials/spring-transaction-propagation-tutorial">Spring transaction propagation tutorial</a>
   - <a href="https://dzone.com/refcardz/getting-started-with-jpa">Getting Started with JPA</a>
   - <a href="http://en.wikibooks.org/wiki/Java_Persistence">Java Persistence</a>
   - <a href="https://easyjava.ru/category/data/jpa/">Разделы по Java Persistence API</a>
   - <a href="http://docs.spring.io/spring-framework/docs/4.0.x/spring-framework-reference/html/transaction.html">Spring Framework transaction management</a>
   - <a href="http://www.baeldung.com/persistence-with-spring-series/">Spring Persistence Tutorial</a>
   - <a href="http://www.objectdb.com/java/jpa/persistence/managed#Entity_Object_Life_Cycle">Working with JPA Entity Objects</a>
   - <a href="http://www.ibm.com/developerworks/ru/library/j-ts1/">Стратегии работы с транзакциями: Распространенные ошибки</a>
   - <a href="http://habrahabr.ru/post/208400/">Принципы работы СУБД. MVCC</a>
   - <a href="https://ru.wikipedia.org/wiki/MVCC">MVCC</a>


###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSTJEQ1Rvd3Jvc2c">Добавляем поддержку HSQLDB</a>

#### **Apply 4_8_add_hsqldb.patch**

> - ВНИМАНИЕ: патч меняет `postgres.properties`
> - IDEA может `${jdbc.initLocation}` подчеркивать красным - тупит...

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

>  Есть несколько аналогичных "встроенных" баз данных. H2, HSQLDB, Derby, SQLite. Почему был выбран HSQLDB?

Просто с ней приходилось работать. HSQLDB и H2 наиболее популярны, в новом курсе по spring-boot планирую использовать H2.
Здесь интересное краткое описание <a href="http://easyjava.ru/data/vstraivaemye-bazy-dannyx-v-java/">встраиваемых баз данных в Java</a>. 
В HSQLDB нет репликаций, кластеризайии и объем данным ограничен несколькими TB. Для большого количества приложений она подходит и для продакшена. См.
- <a href="http://stackoverflow.com/questions/4152911/what-is-hsqldb-limitations">What is HSQLDB limitations?</a>
- <a href="https://habrahabr.ru/sandbox/23199/">HSQLDB в режиме in-process</a>

> Чистого JPA не существует, т.е. это всего лишь интерфейс, спецификация? Говорим JPA, подразумеваем какой-то ORM фрэймворк? А что тогда используют чистый jdbc, Spring-jdbc, MyBatis? MyBatis не реализует JPA?

<a href="https://ru.wikipedia.org/wiki/ORM">ORM</a> это технология связывания БД и объектов приложения, а <a href="https://ru.wikipedia.org/wiki/Java_Persistence_API">JPA</a> - это JavaEE спецификация (API) этой технологии.
Реализации JPA - Hibernate, OpenJPA, EclipceLink, но, например, Hibernate может работать по собственному API (без JPA, которая появиласть позже). Spring-JDBC, MyBatis, JDBI не реализуют JPA, это обертки к JDBC. Все ORM и JPA также реализованы поверх JDBC.

> В зависимостях maven `hibernate-entitymanager` тянет за собой `jboss-logging`. Как будет происходить логгирование?

<a href="http://stackoverflow.com/questions/11639997/how-do-you-configure-logging-in-hibernate-4-to-use-slf4j">How do you configure logging in Hibernate 4 to use SLF4J</a>: в нашем проекте автоматически подхватывается `logback-classic`.

> В чем преимущество Hibernate ?

Hibernate (как любая ORM) реализует маппинг таблиц в объекты Java. Когда мы добавим роли к пользователю вы увидете, насколько код будет проще, чем в jdbc. Также см. <a href="https://www.sitepoint.com/5-reasons-to-use-jpa-hibernate/">5 Reasons to Use JPA / Hibernate</a>

> Чем отличается `@Column(nullable = false)`  от  `@NotNull` и есть ли необходимость указывать обе аннотации ?

`@Column(nullable = false)` это атрибуты колонки таблицы базы. `@NotNull` - это валидация, которая происходит в приложении перед вставкой в базу. Если колонка ненулевая, то `NOT NULL` объязательна. Валидация- опциональна. Также см.
<a href="http://stackoverflow.com/questions/7439504/">@NotNull vs @Column(nullable = false)</a>

> почему мы в в бине `entityManagerFactory` не указали диалект базы данных?

Он [автоматически определяется из `DataSource` драйвера](http://stackoverflow.com/a/39817822/548473)

> В чем разница между `persist` и `merge`

<a href="http://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge">Подробный ответ со Stackovwrflow</a> с объяснением разницы. Упрощенно:
  - `merge`, в отличие от `persist`, если entity нет в текущей сессии, делает запрос в базу данных
  - entity, переданный в `merge` не меняется. Нужно использовать возвращаемый результат

--------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW4

- 1: Сделать из `Meal` Hibernate entity
  - <a href="http://stackoverflow.com/questions/17137307">Hibernate Validator: @NotNull, @NotEmpty, @NotBlank</a>
  - <a href="https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne">Реализация ManyToOne</a>
- 2: Имплементировать и протестировать `JpaMealRepositoryImpl`

#### Optional

- 3: Добавить в тесты `MealServiceTest` функциональность `@Rule`:
  - 3.1: проверку Exception
  - 3.2: вывод в лог времени выполнения каждого теста 
  - 3.3: вывод сводки в конце класса: имя теста - время выполнения 
-  <a href="https://github.com/junit-team/junit/wiki/Rules">JUnit @Rules</a>
-  <a href="http://blog.qatools.ru/junit/junit-rules-tutorial#expectedexcptn">замена ExpectedException</a>

---------------------
### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Подсказки по HW4
-  1: Тк. JPQL работает с объектами мы не можем использовать userId для сохранения. Можно сделать например так:

        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);

   При этом от User нам нужет только id. Создается lazy прокси над id, которая обращается к базе при запросе любого поля. Т.е. у нас запроса в базу за юзером не будет- проверьте по логам Hibernate

**Внимание: проверять запросы Hibernate нужно через run. Если делаете debug и брекпойнт, то могут делаться лишние запросы к базе (дебаггер дергает `toString`)**
   
- 2: В JPQL запросах можно писать: `m.user.id=:userId`
- 3: При реализации `JpaMealRepositoryImpl` предпочтительно не использовать `try-catch` в логике реализации. Но если очень хочется, то ловить только специфичекские эксепшены (пр. `NoResultException`), чтобы, например, при отсутствии коннекта к базе приложение отвечало адекватно.
- 4: Мы будем смотреть генерацию db скриптов из модели, для корректной генерации нужно в `Meal` добавить `uniqueConstraints`
- 5: Когда будете чинить `MealServiceTest`, помните, что тесты пишутся для приложения, а не наоборот.
- 6: При записи в базу через `namedQuery` валидация ентити не работает, только валидация в бд

--------------------------------
Новая информация плохо оседает в голове, когда дается в виде патчей, поэтому, чтобы она стала "твоей" нужно еще раз проделать это самостоятельно. Домашнее задание на этом уроке небольшое, а полученных знаний уже достаточно, чтобы после его выполнения начинать делать выпускной проект, сделанный на нашем стеке.
## [Выпускной проект](graduation.md)
- Для проекта я взял реальное тестовое задание, поэтому жалоб не неясность формулировок принимать не буду- сделайте как поняли. Представьте, что это ваше тестовое задание на работу.
- Общение в канале Slack *#graduate_project*
- Ревью проекта входит в участие с проверкой домашних заданий (ревьюится один раз!). Отдать на ревью нужно до 15.02 (если идешь на проект "<a href="http://javaops.ru/reg/masterjava">Многомодульный maven. Многопоточность. XML (JAXB/StAX).
    Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA) (Masterjava)</a>", то срок до 31.05).
- По завершению ты сможешь занести этот проект в свое портфолио и резюме как собственный, без всяких оговорок.
- Обязательно проверяйся [по рекомендациям в конце выпускного](graduation.md#-Рекомендации)

### Успехов в выполнении!