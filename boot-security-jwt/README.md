# boot-security-jwt

**基于spring boot 2.1.4.RELEASE**

spring boot + spring security + jwt实现前后端分离

### 用户登陆

请求地址`http://localhost:8080/login`,**注意，使用POST请求**

```console
{
    "data": {
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNTU4ODUyNTk0LCJleHAiOjE1NTg4NTI2MDF9
                .AGoIAlsL-z4PPth34NhJGFbTdLpyGgfRgDhg11IPjIuqYIgDT431nuxhLEXR119OjAiAk4610cv6Pw3KWP8_Vg",
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNTU4ODUyNTk0LCJleHAiOjE1NTg4NTI2ODF9
                .Kfi9Hq40CvtoJWOH5HehLty6dSv5apnGWK59irSkOH8X2I-9jhxx-Om2e8peloq-Ax46ICIjuqI-drBjBkZx7w",
        "expiredIn": 1558852601885
    },
    "code": 200,
    "msg": "success",
    "timeStamp": 1558852594688
}
```

**每次登陆，accessToken和refreshToken都会重新生成**

### 使用token请求接口地址

接口地址: `http://localhost:8080/user/info`,**注意在request添加header: `Authorization: Bearer ${token}`,将accessToken替换`${token}`**

```json
{
    "data": {
        "id": 1,
        "userName": "test",
        "password": "$2a$10$Fqf.dsfti1exVWdPJQb2X.mITGLs8gS2ANKMac4OIcaPbQhXUATtm",
        "enabled": true,
        "atDeleted": false,
        "atCreateTime": "2019-05-25T22:43:34.000+0000",
        "atModifyTime": "2019-05-26T09:17:58.000+0000"
    },
    "code": 200,
    "msg": "success",
    "timeStamp": 1558850057461
}
```

如果token失效:  

```json
{
    "data": "无效的token!!",
    "code": 500,
    "msg": "error",
    "timeStamp": 1558854181611
}
```




