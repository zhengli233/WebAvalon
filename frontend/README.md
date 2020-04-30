# webavalon

> houtiao project

``` bash
# 房主创建房间
request:
{"players": number}
response:
{"room": "string", "players": number, readyPlayers: number}

# 玩家进入房间
request:
{"room": "string"}
response:
{"room": "string", "players": number, readyPlayers: number}
```
