Kontinuous
===============

Kontinuous is a web framework inspired by first-class frameworks like Play!, Ruby on Rails and Grails.
Kontinuous has been written on Kotlin (http://kotlin.jetbrains.org/).

# Features

Now, we implemented following features

* Configuration DSL
* nitializers
* Routes
* Persistence
* Authentication
* View Templates

### Configuration DSL
Configuration of routes, initializers, authentication (in future any other settings) wrote on DSL based on Kotlin. So configuration will check at compile time.

### Initializers
It's an section of configuration where user can initialize anything while server is starting
For Example:
```initialize {
            HibernateSession.init(this)
        }```

### Routes
Routes also described in the configuration
For Example:
 ```get("/assets/*file", AssetController.at)```

### Persistence
Now persistance layer based on Hibernate
Hibernate session available from request object inside action

### Authentication
It's made as extension of configuration DSL, request object and in future it will be extracted into plugin

### View Templates
It's just groovy templates.
        ```authenticated("/login") {
            get("/assets/*file", AssetController.at)
        }```


# Sample Application
Also available on GitHub https://github.com/kontinuous/kontinuous-samples

## Licence

This software is licensed under the Apache 2 license, quoted below.

Copyright 2013 Alien Invaders (http://www.ailabs.ru).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
