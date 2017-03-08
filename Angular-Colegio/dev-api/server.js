var express = require("express");
var path = require("path");
var bodyParser = require("body-parser");

var app = express();
app.use(express.static(__dirname + "/public"));
app.use(bodyParser.json());

app.use(function(req, res, next) {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS');
    res.header('Access-Control-Allow-Headers', 'Content-Type, Authorization, Content-Length, X-Requested-With');
    next();
});

var server = app.listen(process.env.PORT || 8887, function() {
    var port = server.address().port;
    console.log("App now running on port", port);
});

app.get("/", function(req, res) {
    res.json({
        message: "Hello World!"
    });
});

app.get("/ping", function(req, res) {
    res.json({
        message: "Pong!"
    });
});

app.post("/login", function(req, res) {
    //console.log(req);
    console.log("POST");
    res.json({
        result: true
    });
});

app.post("/status", function(req, res) {
    res.json({
        "state": "OK",
        "messageItems": null,
        "empty": false,
        "responseObject": {
            "moduleId": "moduleId",
            "sei": "sei",
            "body": {
                "fid": "1",
                "listName": "Pagos a Proveedores",
                "currentAmount": "1337",
                "processedItems": "2",
                "totalItems": "3",
                "percentage": 100,
                "amount": "1250.25"
            },
            "txId": "txId"
        }
    });
});

app.post("/findCompany", function(req, res) {
    res.json({
        "state": "OK",
        "responseObject": {
            "moduleId": "mpay",
            "sei": "/api/prov/findCompany",
            "body": {
                "result": {
                    "items": [
                        {
                            "text": "Compania 1",
                            "value": "06-30707560289"
                        },
                        {
                            "text": "Compania 2",
                            "value": "06-30707560999"
                        }
                    ]
                }
            },
            "txId": "e09aae68-4e8e-4c84-b0a9-e53876ab5772"
        },
        "messageItems": null,
        "empty": false
    });
});


// Tarjeta-86
app.post("/pendingList", function(req, res) {
    res.json({
        "state": "OK",
        "responseObject": {
            "moduleId": "mpay",
            "sei": "pendingList",
            "body": [
                {
                    "dateDue": "2016-11-29",
                    "customerName": "Accenture",
                    "listId": "1",
                    "origin": null,
                    "paymentType": "check",
                    "amount": "0",
                    "state": "Draft",
                    "action": "liberar",
                    "alert": true // true|false
                },
                {
                    "dateDue": "2016-11-29",
                    "customerName": "Test",
                    "listId": "2",
                    "origin": null,
                    "paymentType": "check",
                    "amount": "0",
                    "state": "Create",
                    "action": "Sign",
                    "alert": false // true|false
                }
            ],
            "txId": "algo",
            "sessionId": null
        },
        "messageItems": null,
        "empty": false // true si no hay datos
    });
});

app.post("/historicalList", function(req, res) {
    res.json({
        "state": "OK",
        "responseObject": {
            "moduleId": "mpay",
            "sei": "pendingList",
            "body": [
                {
                    "dateDue": "2016-11-29",
                    "customerName": "Accenture",
                    "listId": "1",
                    "origin": null,
                    "paymentType": "check",
                    "amount": "0",
                    "state": "Draft",
                    "action": "liberar"
                },
                {
                    "dateDue": "2016-11-29",
                    "customerName": "Historic",
                    "listId": "2",
                    "origin": null,
                    "paymentType": "check",
                    "amount": "0",
                    "state": "Create",
                    "action": "Sign"
                }
            ],
            "txId": "algo",
            "sessionId": null
        },
        "messageItems": null,
        "empty": false // true si no hay datos
    });
});

app.post("/detallePago", function(req, res) {
    res.json({
        "state": "OK",
        "responseObject": {
            "moduleId": "moduleId",
            "sei": "sei",
            "body": {
                "id": "1",
                "emissionDate": "2016-12-01 15:00:13.481",
                "payer": "A",
                "creationChannel": "Prueba",
                "deliveryBranch": null,
                "checkPaymentAccount": "50",
                "icbcAccreditations": "25",
                "otherBanksAccreditations": "50",
                "itemsAmmount": "5",
                "total": "44",
                "historicalStateChanges": [{
                    "id": "1",
                    "name": "Juan",
                    "surname": "null",
                    "date": "2016-12-01 15:00:13.478",
                    "time": null,
                    "action": "Borrador"

                },
                {
                    "id": "2",
                    "name": "Test",
                    "surname": "surname",
                    "date": "2016-12-01 15:00:13.478",
                    "time": null,
                    "action": "Borrador"

                }]
            },
            "txId": null
        },
        "messageItems": null,
        "empty": false
    });
});

app.post("/pendingStates", function(req, res) {
    res.json({
        "state": "OK",
        "messageItems": null,
        "empty": false,
        "responseObject": {
            "moduleId": "moduleId",
            "sei": "sei",
            "body": [
                "Borrador",
                "Creada",
                "Firmada",
                "Aprobada",
                "Reabierta"
            ],
        }
    });
});

app.post("/historicalStates", function(req, res) {
    res.json({
        "state": "OK",
        "messageItems": null,
        "empty": false,
        "responseObject": {
            "moduleId": "moduleId",
            "sei": "sei",
            "body":       [
                "Liberada",
                "Procesada",
                "Con errores",
                "Denegada",
                "Vencido",
                "Rechazado"
            ],
        }
    });
});

app.post("/paymentTypes", function(req, res) {
    res.json({
        "state": "OK",
        "messageItems": null,
        "empty": false,
        "responseObject": {
            "moduleId": "moduleId",
            "sei": "sei",
            "body": [
                "Cheque al dia",
                "Cheque pago diferido",
                "Transferencia"
            ],
        }
    });
});

app.post("/customerNames", function(req, res) {
    res.json({
        "state": "OK",
        "messageItems": null,
        "empty": false,
        "responseObject": {
            "moduleId": "moduleId",
            "sei": "sei",
            "body":
            [{
                "customerName": "Accenture"
            },
            {
                "customerName": "ICBC"
            }]
        }
    });
});

app.post("/existsUserByUsername", function(req, res) {
    res.json({
        "state": "OK",
        "responseObject": {
            "body": {
                "existsUser": false
            },
            "txId": "txId",
            "sessionId": null
        },
        "messageItems": null,
        "empty": false
    });
});

app.post("/existsUserByCuil", function(req, res) {
    res.json({
        "state": "OK",
        "responseObject": {
            "body": {
                "existsUser": false
            },
            "txId": "txId",
            "sessionId": null
        },
        "messageItems": null,
        "empty": false
    });
});

app.post("/existsUserByTypeAndDocument", function(req, res) {
    res.json({
        "state": "OK",
        "responseObject": {
            "body": {
                "existsUser": true
            },
            "txId": "txId",
            "sessionId": null
        },
        "messageItems": null,
        "empty": false
    });
});