const mysql = require('mysql');
const config = require('./config.js');

// 데이터베이스 연결 설정
const connection = mysql.createConnection(config.dbConfig);

// 데이터베이스 연결
connection.connect((error) => {
  if (error) {
    console.error('Failed to connect to MySQL:', error);
  } else {
    console.log('Connected to MySQL');
  }
});

// 연결된 데이터베이스 커넥션을 모듈로 exports
module.exports = connection;
