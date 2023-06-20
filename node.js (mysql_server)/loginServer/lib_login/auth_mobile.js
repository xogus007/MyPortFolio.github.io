var express = require('express');
var router = express.Router();
var db = require('./db');

router.post('/login_process_mobile', function (request, response) {
  const id = request.body.id;
  const pw = request.body.pw;

  if (id && pw) {
    db.query(
      'SELECT * FROM usertable WHERE username = ? AND password = ?',
      [id, pw],
      function (error, results, fields) {
        if (error) {
          console.error('Database query error:', error);
          response.statusCode = 500;
          response.json({ result: false, error: 'An error occurred' });
          return;
        }

        if (results.length > 0) {
          response.statusCode = 200;
          request.session.is_logined = true;
          request.session.nickname = id;
          request.session.save(function (error) {

            if (error) {
              console.error('Session save error:', error);
              response.statusCode = 500;
              response.json({ result: false, error: 'An error occurred' });
            } else {
              response.json({ result: true });
            }
          });
        } else {
          response.statusCode = 403;
          response.json({ result: false, error: 'Invalid credentials' });
        }
      }
    );

  } else {
    response.statusCode = 204;
    response.json({ result: false, error: 'Missing credentials' });
  }
});

// 회원가입 프로세스
router.post('/register_process_mobile', function (request, response) {
  var id = request.body.id;
  var pw = request.body.pw;
  var pw2 = request.body.pw2;

  if (id && pw && pw2) {
    db.query('SELECT * FROM usertable WHERE username = ?', [id], function (error, results, fields) {
      if (error) {
        console.error('Database query error:', error);
        response.statusCode = 500;
        response.json({ result: false, error: 'An error occurred' });
        return;
      }

      if (results.length <= 0 && pw === pw2) {
        db.query('INSERT INTO usertable (username, password) VALUES(?,?)', [id, pw], function (error, data) {

          if (error) {
            console.error('Database query error:', error);
            response.statusCode = 500;
            response.json({ result: false, error: 'An error occurred' });
          } else {
            response.statusCode = 200;
            response.json({ result: true });
          }
        });

      } else if (pw !== pw2) {
        response.statusCode = 405;
        response.json({ result: false, error: 'Passwords do not match' });

      } else {
        response.statusCode = 409;
        response.json({ result: false, error: 'Username already exists' });
      }
    });

  } else {
    response.statusCode = 400;
    response.json({ result: false, error: 'Missing credentials' });
  }
  
});

module.exports = router;
