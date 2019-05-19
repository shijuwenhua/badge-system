# badge-system

## installation
1. install and start mysql
```
docker run -p 3306:3306 --name mysql_wq -e MYSQL_ROOT_PASSWORD=123456 -d mysql
```

2. create database 'dengdeng' 
```
CREATE DATABASE IF NOT EXISTS dengdeng DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
```

3. maven compile & start springboot

4. visit http://localhost:8080