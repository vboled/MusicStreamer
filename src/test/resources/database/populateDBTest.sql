INSERT INTO regions
VALUES (default, 'North America', 0.015, '+1'),
       (default, 'South America', 0.011, '+2'),
       (default, 'Australia and Oceania', 0.009, '+3'),
       (default, 'China', 0.02, '+4'),
       (default, 'India', 0.005, '+5'),
       (default, 'Russia', 0.007, '+6'),
       (default, 'United Kingdom', 0.012, '+7'),
       (default, 'Asia', 0.013, '+8'),
       (default, 'Africa', 0.006, '+9'),
       (default, 'Europe', 0.01, '+10'),
       (default, 'Other', 0.008, '+11');

INSERT INTO genres
VALUES (default, 'Rock'),
       (default, 'Rap'),
       (default, 'Classical');

INSERT INTO users
VALUES  (default, 'admin', '$2y$12$IWmpDwOU9N0i2CICEy/1suJbCjZVfRXmcYk0/qNr/nWIhKEt1taSK', 'tName1', 'tLName1', '1', 'tEmail1@edu.ru', '+79998881122', CURRENT_TIMESTAMP, '2021-02-07', '111', 'ADMIN'),
        (default, 'user', '$2y$12$v4PcvGT6sElW2w4lIqjnDOgmL/z1/fyVPh55FTH86dodcpkH1s2Jq', 'tName2', 'tLName2', '2', 'tEmail2@edu.ru', '+79998881123', CURRENT_TIMESTAMP, '2021-02-07', '222', 'USER'),
        (default, 'owner', '$2a$12$MCj.Ur2zrNWSW5I7i.xPGOFDB.WzBTqkz0ejMIWXI0pJ4nncZcLDO', 'tName2', 'tLName2', '2', 'tEmafreil2@edu.ru','+7999877773', CURRENT_TIMESTAMP, '2021-02-07', '222', 'OWNER');

INSERT INTO artists
VALUES
    (default, 3, 'artist', null, '2021-02-07', null);

INSERT INTO albums
VALUES
    (default, 3, 1, 0, null, '2021-02-07', null, 'album', 1, null, null);