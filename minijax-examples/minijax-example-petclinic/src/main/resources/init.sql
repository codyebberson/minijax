
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db3367e66671a7bfb8602b47f', '110 W. Liberty St.', 'Madison', TIMESTAMP '2017-10-14 07:15:49.303', NULL, 'George Franklin', '6085551023', TIMESTAMP '2017-10-14 07:15:49.331');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db3456a95b5a0048b847bdb55', '638 Cardinal Ave.', 'Sun Prairie', TIMESTAMP '2017-10-14 07:15:49.317', NULL, 'Betty Davis', '6085551749', TIMESTAMP '2017-10-14 07:15:49.336');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db3467dc19dbdd901c4aee7ca', '2693 Commerce St.', 'McFarland', TIMESTAMP '2017-10-14 07:15:49.318', NULL, 'Eduardo Rodriquez', '6085558763', TIMESTAMP '2017-10-14 07:15:49.341');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db346fcfd8d094734b8d06f0f', '563 Friendly St.', 'Windsor', TIMESTAMP '2017-10-14 07:15:49.318', NULL, 'Harold Davis', '6085553198', TIMESTAMP '2017-10-14 07:15:49.343');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db34787eb0cfa97d88e6aee4d', '2387 S. Fair Way', 'Madison', TIMESTAMP '2017-10-14 07:15:49.319', NULL, 'Peter McTavish', '6085552765', TIMESTAMP '2017-10-14 07:15:49.345');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db3485b85ebb27eccdf97f052', '105 N. Lake St.', 'Monona', TIMESTAMP '2017-10-14 07:15:49.32', NULL, 'Jean Coleman', '6085552654', TIMESTAMP '2017-10-14 07:15:49.357');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db34927dff0537ed296e0951a', '1450 Oak Blvd.', 'Monona', TIMESTAMP '2017-10-14 07:15:49.321', NULL, 'Jeff Black', '6085555387', TIMESTAMP '2017-10-14 07:15:49.362');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db3491c2997f22c48a1350e63', '345 Maple St.', 'Madison', TIMESTAMP '2017-10-14 07:15:49.321', NULL, 'Maria Escobito', '6085557683', TIMESTAMP '2017-10-14 07:15:49.364');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db34ab068891b8d168a778bb0', '2749 Blackhawk Trail', 'Madison', TIMESTAMP '2017-10-14 07:15:49.322', NULL, 'David Schroeder', '6085559435', TIMESTAMP '2017-10-14 07:15:49.366');
MERGE INTO OWNER(ID, ADDRESS, CITY, CREATEDDATETIME, DELETEDDATETIME, NAME, TELEPHONE, UPDATEDDATETIME) VALUES (X'015f1b3db34a12342dce3503004309d5', '2335 Independence La.', 'Waunakee', TIMESTAMP '2017-10-14 07:15:49.322', NULL, 'Carlos Estaban', '6085555487', TIMESTAMP '2017-10-14 07:15:49.369');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db352cc69d88d22b846d39068', '2000-09-07', TIMESTAMP '2017-10-14 07:15:49.33', NULL, 'Leo', 'cat', TIMESTAMP '2017-10-14 07:15:49.33', X'015f1b3db3367e66671a7bfb8602b47f');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db3572d0f50c5f36efb379e6b', '2002-08-06', TIMESTAMP '2017-10-14 07:15:49.335', NULL, 'Basil', 'hamster', TIMESTAMP '2017-10-14 07:15:49.335', X'015f1b3db3456a95b5a0048b847bdb55');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db35ab8afa40fffe3f25535b5', '2001-04-17', TIMESTAMP '2017-10-14 07:15:49.338', NULL, 'Rosy', 'dog', TIMESTAMP '2017-10-14 07:15:49.338', X'015f1b3db3467dc19dbdd901c4aee7ca');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db35cd9ad3c6a79d5d5f0fdfb', '2000-03-07', TIMESTAMP '2017-10-14 07:15:49.34', NULL, 'Jewel', 'dog', TIMESTAMP '2017-10-14 07:15:49.34', X'015f1b3db3467dc19dbdd901c4aee7ca');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db35fccc3fdec8cbef87703f4', '2000-11-30', TIMESTAMP '2017-10-14 07:15:49.343', NULL, 'Iggy', 'lizard', TIMESTAMP '2017-10-14 07:15:49.343', X'015f1b3db346fcfd8d094734b8d06f0f');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db3607283dff778c4f6a916f7', '2000-01-20', TIMESTAMP '2017-10-14 07:15:49.344', NULL, 'George', 'snake', TIMESTAMP '2017-10-14 07:15:49.344', X'015f1b3db34787eb0cfa97d88e6aee4d');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db362b30cb3b80eb8eb1c5869', '1995-09-04', TIMESTAMP '2017-10-14 07:15:49.346', NULL, 'Samantha', 'cat', TIMESTAMP '2017-10-14 07:15:49.355', X'015f1b3db3485b85ebb27eccdf97f052');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db36cefe51ee4c6979e8554e4', '1995-09-04', TIMESTAMP '2017-10-14 07:15:49.356', NULL, 'Max', 'cat', TIMESTAMP '2017-10-14 07:15:49.36', X'015f1b3db3485b85ebb27eccdf97f052');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db3722e2af39c02fc1ee89301', '1999-08-06', TIMESTAMP '2017-10-14 07:15:49.362', NULL, 'Lucky', 'bird', TIMESTAMP '2017-10-14 07:15:49.362', X'015f1b3db34927dff0537ed296e0951a');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db3738bc33a29cea9196ae5de', '1997-02-24', TIMESTAMP '2017-10-14 07:15:49.363', NULL, 'Mulligan', 'dog', TIMESTAMP '2017-10-14 07:15:49.363', X'015f1b3db3491c2997f22c48a1350e63');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db3759081de7a05ef23764b4b', '2000-03-09', TIMESTAMP '2017-10-14 07:15:49.365', NULL, 'Freddy', 'bird', TIMESTAMP '2017-10-14 07:15:49.365', X'015f1b3db34ab068891b8d168a778bb0');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db3772733a1cf6f387b87cb8f', '2000-06-24', TIMESTAMP '2017-10-14 07:15:49.367', NULL, 'Lucky', 'dog', TIMESTAMP '2017-10-14 07:15:49.367', X'015f1b3db34a12342dce3503004309d5');
MERGE INTO PET(ID, BIRTHDATE, CREATEDDATETIME, DELETEDDATETIME, NAME, PETTYPE, UPDATEDDATETIME, OWNER_ID) VALUES (X'015f1b3db3784b2819a76dcaa737fa4a', '2002-06-08', TIMESTAMP '2017-10-14 07:15:49.368', NULL, 'Sly', 'cat', TIMESTAMP '2017-10-14 07:15:49.368', X'015f1b3db34a12342dce3503004309d5');
MERGE INTO VET(ID, CREATEDDATETIME, DELETEDDATETIME, NAME, SPECIALTY, UPDATEDDATETIME) VALUES (X'015f1b3db34b6403f4de9052233cec1d', TIMESTAMP '2017-10-14 07:15:49.323', NULL, 'James Carter', 'none', TIMESTAMP '2017-10-14 07:15:49.323');
MERGE INTO VET(ID, CREATEDDATETIME, DELETEDDATETIME, NAME, SPECIALTY, UPDATEDDATETIME) VALUES (X'015f1b3db34bcd703c4ca9a5baf29dc0', TIMESTAMP '2017-10-14 07:15:49.323', NULL, 'Helen Leary', 'radiology', TIMESTAMP '2017-10-14 07:15:49.323');
MERGE INTO VET(ID, CREATEDDATETIME, DELETEDDATETIME, NAME, SPECIALTY, UPDATEDDATETIME) VALUES (X'015f1b3db34c191c808b8cdeecf6a455', TIMESTAMP '2017-10-14 07:15:49.324', NULL, 'Linda Douglas', 'dentistry', TIMESTAMP '2017-10-14 07:15:49.324');
MERGE INTO VET(ID, CREATEDDATETIME, DELETEDDATETIME, NAME, SPECIALTY, UPDATEDDATETIME) VALUES (X'015f1b3db34cb48abbda7932d03fcc31', TIMESTAMP '2017-10-14 07:15:49.324', NULL, 'Rafael Ortega', 'surgery', TIMESTAMP '2017-10-14 07:15:49.324');
MERGE INTO VET(ID, CREATEDDATETIME, DELETEDDATETIME, NAME, SPECIALTY, UPDATEDDATETIME) VALUES (X'015f1b3db34daa8d4cd7fb5b1c1ca417', TIMESTAMP '2017-10-14 07:15:49.325', NULL, 'Henry Stevens', 'radiology', TIMESTAMP '2017-10-14 07:15:49.325');
MERGE INTO VET(ID, CREATEDDATETIME, DELETEDDATETIME, NAME, SPECIALTY, UPDATEDDATETIME) VALUES (X'015f1b3db34ea9cfc2ba9e934c5474f5', TIMESTAMP '2017-10-14 07:15:49.326', NULL, 'Sharon Jenkins', 'none', TIMESTAMP '2017-10-14 07:15:49.326');
MERGE INTO VISIT(ID, CREATEDDATETIME, DATE, DELETEDDATETIME, DESCRIPTION, UPDATEDDATETIME, PET_ID) VALUES (X'015f1b3db36972fc662621f8fc4fb2a4', TIMESTAMP '2017-10-14 07:15:49.353', TIMESTAMP '2010-03-03 20:00:00.0', NULL, 'Rabies shot', TIMESTAMP '2017-10-14 07:15:49.353', X'015f1b3db362b30cb3b80eb8eb1c5869');
MERGE INTO VISIT(ID, CREATEDDATETIME, DATE, DELETEDDATETIME, DESCRIPTION, UPDATEDDATETIME, PET_ID) VALUES (X'015f1b3db36a8b44837f91d79b833fe3', TIMESTAMP '2017-10-14 07:15:49.354', TIMESTAMP '2008-09-03 21:00:00.0', NULL, 'Spayed', TIMESTAMP '2017-10-14 07:15:49.354', X'015f1b3db362b30cb3b80eb8eb1c5869');
MERGE INTO VISIT(ID, CREATEDDATETIME, DATE, DELETEDDATETIME, DESCRIPTION, UPDATEDDATETIME, PET_ID) VALUES (X'015f1b3db36e15d370b7d3b284bd20f6', TIMESTAMP '2017-10-14 07:15:49.358', TIMESTAMP '2011-03-03 20:00:00.0', NULL, 'Rabies shot', TIMESTAMP '2017-10-14 07:15:49.358', X'015f1b3db36cefe51ee4c6979e8554e4');
MERGE INTO VISIT(ID, CREATEDDATETIME, DATE, DELETEDDATETIME, DESCRIPTION, UPDATEDDATETIME, PET_ID) VALUES (X'015f1b3db36f8a3479894d17fa3f48a4', TIMESTAMP '2017-10-14 07:15:49.359', TIMESTAMP '2009-06-03 21:00:00.0', NULL, 'Neutered', TIMESTAMP '2017-10-14 07:15:49.359', X'015f1b3db36cefe51ee4c6979e8554e4');