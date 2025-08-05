# toolkit
A website with multiple subservices as java/spring-boot practice


# GET
- curl http://127.0.0.1:8080/api/v1/polls
- curl http://127.0.0.1:8080/api/v1/polls/1

# POST
- curl -X POST   http://localhost:8080/api/v1/polls   -H 'Content-Type: application/json'   -d '{
  "question": "What is your favorite programming language?",
  "choicesContent": ["Java", "Python", "JavaScript", "C#"]
  }'


# SQL:
INSERT INTO polls.polls (question, is_closed) VALUES ('What is your favorite food?', FALSE);

INSERT INTO polls.polls (question, is_closed) VALUES ('What is your favorite season?', FALSE);

INSERT INTO polls.polls (question, is_closed) VALUES ('What is your favorite movie genre?', TRUE);

INSERT INTO polls.choices (content, votes, poll_id) VALUES ('Pizza', DEFAULT, 1);

INSERT INTO polls.choices (content, votes, poll_id) VALUES ('Pasta', DEFAULT, 1);

INSERT INTO polls.choices (content, votes, poll_id) VALUES ('Sushi', DEFAULT, 1);

