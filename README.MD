## Project Information

novel is a set of **front and back-end separation** based on the latest **Java** technology stack Spring Boot 3 + Vue 3.
novel project, including novel recommendation, work search, novel ranking, novel reading, novel review, author area, news release and other functions.

## Development Environment

- MySQL 8.0
- Redis 7.0
- JDK 17
- IntelliJ IDEA 2022.3 (optional)
- Node 16.13.1

## Back-end technology selection

| Technology            |   Version    | Description         | Official website                                                   |                                        Learn                                        |
|-------------------|:------------:|---------------------|------------------------------------------------------|:-----------------------------------------------------------------------------------:|
| Spring Boot       |    3.0.0     | Containers + MVC framework         | [click](https://spring.io/projects/spring-boot)      |        [click](https://docs.spring.io/spring-boot/docs/3.0.0/reference/html)        |
| MyBatis           |    3.5.9     | ORM framework              | [click](http://www.mybatis.org)                         |                [click](https://mybatis.org/mybatis-3/zh/index.html)                 |
| MyBatis-Plus      |    3.5.3     | MyBatis Enhancement tools        | [click](https://baomidou.com/)                          |                     [click](https://baomidou.com/pages/24112f/)                     |
| JJWT              |    0.11.5    | JWT Login support           | [click](https://github.com/jwtk/jjwt)                   |                                          -                                          |
| Lombok            |   1.18.24    | Simplified Object Wrapper            | [click](https://github.com/projectlombok/lombok)        |                   [click](https://projectlombok.org/features/all)                   |
| Caffeine          |    3.1.0     | Local cache support              | [click](https://github.com/ben-manes/caffeine)          |           [click](https://github.com/ben-manes/caffeine/wiki/Home-zh-CN)            |
| Redis             |     7.0      | Distributed cache support             | [click](https://redis.io)                               |                           [click](https://redis.io/docs)                            |
| MySQL             |     8.0      | Database Services               | [click](https://www.mysql.com)                          | [click](https://docs.oracle.com/en-us/iaas/mysql-database/doc/getting-started.html) |
| Sentinel          |    1.8.4     | Flow control components              | [click](https://github.com/alibaba/Sentinel)            |        [click](https://github.com/alibaba/Sentinel/wiki/%E4%B8%BB%E9%A1%B5)         |
| Springdoc-openapi |    2.0.0     | Swagger 3 Automatic generation of interface documentation  | [click](https://github.com/springdoc/springdoc-openapi) |                           [click](https://springdoc.org/)                           |
| Undertow          | 2.2.17.Final | Java High performance web server developed | [click](https://undertow.io)                            |                   [click](https://undertow.io/documentation.html)                   |
| Docker            |      -       | Container Engine              | [click](https://www.docker.com/)                        |                                          -                                          |


## Front-end technology selection

| Technology            |   Version    | Description         | Official website                                                   |                                        Learn                                        |
| :----------------- | :-----: | -------------------------- | --------------------------------------- | :-------------------------------------------------: |
| Vue.js        |  3.3.4  | Progressive JavaScript Framework | [click](https://vuejs.org)  |   [click](https://staging-cn.vuejs.org/guide/introduction.html)    |
| Vue Router            |  4.2.2  | Official Routing for Vue.js                    | [click](https://router.vuejs.org)                  | [click](https://router.vuejs.org/zh/guide/) |
| Vuex            |  4.1.0  | State Management Pattern         | [click](https://vuex.vuejs.org/)              |  [click](https://vuex.vuejs.org/guide/) |
| Element-plus               | 2.3.6  | A component library for designers and developers based on Vue 3   | [click](https://element-plus.org)            |   [click](https://element-plus.org/zh-CN/guide/design.html)   |
| Axios              | 1.4.0  | A promise-based HTTP Client for node.js and the browser   | [click](https://axios-http.com/)            |   [click](https://axios-http.com/docs/intro)   |
| Animate.css              | 4.1.1 | A library of ready-to-use, cross-browser animations for use in web projects   | [click](https://animate.style/)            |   [click](https://animate.style/)   |
## Coding specification

- Specification approach: strict adherence to the Ali coding statute.
- Uniform naming: the profile is named to the maximum extent possible.
- Clear sub-packaging: clear hierarchy to quickly locate code locations.
- Completeness of comments: highly descriptive and reduces the code reading workload of developers.
- Tool specification: the use of uniform jar packages avoids content conflicts.
- Clean code: high readability and maintainability.
- Dependency versions: all dependencies use the latest available version for learning new technologies.

## Package Structure

```
com
 +- example
    +- novel_backend
        +- NovelApplication.java -- Project Launch Category
        |
        +- core -- Project core modules, including various tools, configurations, constants, etc.
        |   +- common -- Business-agnostic generic modules
        |   |   +- constant -- General constants 
        |   |   +- req -- Generic request data format encapsulation, e.g. paging request data
        |   |   +- resp -- Interface response tools and response data format encapsulation
        |   |     
        +- controller -- The main purpose is to handle various Http requests, various basic parameter checks, or simple processing of non-reusable business, returning JSON data, etc.
        +- dto -- Data transfer objects, including encapsulation of various Http request and response data
        |   +- req -- Http Request data encapsulation
        |   +- resp -- Http Response data encapsulation
        |
        +- dao -- Data access layer, interacting with the underlying MySQL
        |   +- entity -- Database entity classes
        |   +- mapper -- sqlImplementation Classes
        |
        +- manager -- Generic business processing layer, encapsulation of third party platforms, sinking of generic capabilities to the Service layer and combined reuse of multiple DAOs
        +- service -- Relatively specific business logic service layers
        +- controller -- The main purpose is to handle various Http requests, various basic parameter checks, or simple processing of non-reusable business, returning JSON data, etc.
        |   +- front -- Fiction related interfaces


```
