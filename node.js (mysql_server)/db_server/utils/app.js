const express = require('express');
const mysql = require('mysql');
const config = require('./config');

const app = express();
const port = process.env.PORT || 7852;

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

// API 엔드포인트 정의
app.get('/plug_data2', (req, res) => {
  var using = parseInt(req.query.cpStat) || 0;
  var speed = parseInt(req.query.chargeTp) || 0;
  var charge = parseInt(req.query.cpTp) || 0;

  var query = "SELECT * FROM plug_data2";
  var where = "";
  if(using != 0) {
    where += `${where.length > 0? ' AND':''} cpStat = ${using}`;
  }
  if(speed != 0) {
    where +=  `${where.length > 0? ' AND':''} chargeTp = ${using}`;
  }
  if(charge != 0) {
    where += `${where.length > 0? ' AND':''} cpTp = ${using}`;
  }
  if(where.length > 0) {
    query += ` WHERE ${where}`;
  }

  //&chargeTp= &cpTp=

  console.log(query);

  // MySQL 데이터베이스에서 데이터 조회
  connection.query(query, [], (error, results) => {
    if (error) {+
      console.error('Failed to fetch data from MySQL:', error);
      res.status(500).json({ error: 'Failed to fetch data from MySQL' });
    } else {
      console.log('Fetched data from MySQL:', results);
      res.json(results); // 데이터베이스 결과를 JSON 형식으로 응답
    }
  });
});

// 서버 시작
app.listen(port, () => {
  console.log(`Server On: http://localhost:${port}`);
});

