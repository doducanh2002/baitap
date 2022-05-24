-- <! user profile service mysql database>
CREATE SCHEMA IF NOT EXISTS  LM_USER_PROFILE;
CREATE USER 'lm_user_profile'@'%' IDENTIFIED WITH mysql_native_password BY 'lm@user2022';
GRANT ALL PRIVILEGES ON LM_USER_PROFILE.* TO 'lm_user_profile'@'%';
FLUSH PRIVILEGES;

-- <! document service mysql database>
CREATE SCHEMA IF NOT EXISTS  LM_DOCUMENT;
CREATE USER 'lm_document'@'%' IDENTIFIED WITH mysql_native_password BY 'lm@document2022';
GRANT ALL PRIVILEGES ON LM_DOCUMENT.* TO 'lm_document'@'%';
FLUSH PRIVILEGES;

-- <! review service mysql database>
CREATE SCHEMA IF NOT EXISTS  LM_REVIEW;
CREATE USER 'lm_review'@'%' IDENTIFIED WITH mysql_native_password BY 'lm@review2022';
GRANT ALL PRIVILEGES ON LM_REVIEW.* TO 'lm_review'@'%';
FLUSH PRIVILEGES;
