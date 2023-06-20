var mysql = require('mysql');

var db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'login_db'
});
db.connect();

module.exports = db;