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

## API example
1. get user badge list
> http://localhost:8080/getUserBadgesDetailList/oU_QP1Og4zKhImr9f7MLvm-bh1rM
```
[
    {
        "id": 1,
        "title": "测试badge",
        "description": "测试",
        "icon": "http://img3.imgtn.bdimg.com/it/u=1160786852,2482799599&fm=26&gp=0.jpg",
        "upgradeRequiredTime": 0,
        "upgradeBadgeId": 0,
        "upgradeBadgeTitle": "",
        "completedRequiredActivities": 0,
        "userActivityList": [
            {
                "id": 1,
                "creator": "",
                "title": "测试activity",
                "description": "测试",
                "icon": "http://img3.imgtn.bdimg.com/it/u=1160786852,2482799599&fm=26&gp=0.jpg",
                "requiredAttendTimes": 20,
                "type": "",
                "status": null,
                "attendTimes": 0
            },
            {
                "id": 4,
                "creator": "测试",
                "title": "测试2",
                "description": "测试",
                "icon": "http://pic.58pic.com/58pic/13/77/08/09j58PICyV2_1024.jpg",
                "requiredAttendTimes": 1,
                "type": "",
                "status": "Compeleted",
                "attendTimes": 4
            }
        ],
        "badgeList": []
    }
]
```

2. get user badge detail
> http://localhost:8080/getUserBadgesDetail/oU_QP1Og4zKhImr9f7MLvm-bh1rM/1
```
{
    "id": 1,
    "title": "测试badge",
    "description": "测试",
    "icon": "http://img3.imgtn.bdimg.com/it/u=1160786852,2482799599&fm=26&gp=0.jpg",
    "upgradeRequiredTime": 0,
    "upgradeBadgeId": 0,
    "upgradeBadgeTitle": "",
    "completedRequiredActivities": 0,
    "userActivityList": [
        {
            "id": 1,
            "creator": "",
            "title": "测试activity",
            "description": "测试",
            "icon": "http://img3.imgtn.bdimg.com/it/u=1160786852,2482799599&fm=26&gp=0.jpg",
            "requiredAttendTimes": 20,
            "type": "",
            "status": null,
            "attendTimes": 0
        },
        {
            "id": 4,
            "creator": "测试",
            "title": "测试2",
            "description": "测试",
            "icon": "http://pic.58pic.com/58pic/13/77/08/09j58PICyV2_1024.jpg",
            "requiredAttendTimes": 1,
            "type": "",
            "status": "Compeleted",
            "attendTimes": 4
        }
    ],
    "badgeList": []
}
```

3. punch
> http://localhost:8080/attendActivity/oU_QP1Og4zKhImr9f7MLvm-bh1rM/4
```
{
	"id": 1,
	"title": "测试badge",
	"description": "测试",
	"icon": "http://img3.imgtn.bdimg.com/it/u=1160786852,2482799599&fm=26&gp=0.jpg",
	"upgradeRequiredTime": 0,
	"upgradeBadgeId": 0,
	"upgradeBadgeTitle": "",
	"completedRequiredActivities": 0,
	"userActivityList": [{
		"id": 1,
		"creator": "",
		"title": "测试activity",
		"description": "测试",
		"icon": "http://img3.imgtn.bdimg.com/it/u=1160786852,2482799599&fm=26&gp=0.jpg",
		"requiredAttendTimes": 20,
		"type": "",
		"status": null,
		"attendTimes": 0
	}, {
		"id": 4,
		"creator": "测试",
		"title": "测试2",
		"description": "测试",
		"icon": "http://pic.58pic.com/58pic/13/77/08/09j58PICyV2_1024.jpg",
		"requiredAttendTimes": 1,
		"type": "",
		"status": "Compeleted",
		"attendTimes": 4
	}],
	"badgeList": []
}
```