# JSON Eater

This project exposes a POST endpoint that takes any input JSON body and sends it back to you. Invalid JSON will cause a Bad Request Exception to be thrown.

You can send curl requests to my endpoint like tis:

```
curl --header "Content-Type: application/json" --request POST --data '{"hello":"world","jsoneater":23434}' https://jsoneater.herokuapp.com/eat
```

Or you can send requests using a friendly swagger UI found here: https://jsoneater.herokuapp.com/. Simply click the green POST request box, then click the "Try Now" button, then enter valid JSON, then click "Execute".
