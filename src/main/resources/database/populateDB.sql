INSERT INTO users
VALUES  (default, 'admin', '$2y$12$IWmpDwOU9N0i2CICEy/1suJbCjZVfRXmcYk0/qNr/nWIhKEt1taSK', 'tName1', 'tLName1', '1', 'tEmail1@edu.ru', '79998881122', CURRENT_TIMESTAMP, '2021-02-07', '111', 'ADMIN'),
        (default, 'user', '$2y$12$v4PcvGT6sElW2w4lIqjnDOgmL/z1/fyVPh55FTH86dodcpkH1s2Jq', 'tName2', 'tLName2', '2', 'tEmail2@edu.ru', '79998881123', CURRENT_TIMESTAMP, '2021-02-07', '222', 'USER'),
        (default, 'owner', '$2a$12$MCj.Ur2zrNWSW5I7i.xPGOFDB.WzBTqkz0ejMIWXI0pJ4nncZcLDO', 'tName2', 'tLName2', '2', 'tEmafreil2@edu.ru','7999877773', CURRENT_TIMESTAMP, '2021-02-07', '222', 'OWNER');

INSERT INTO genres
VALUES (default, 'Rock'),
       (default, 'Rap'),
       (default, 'Classical');

INSERT INTO albums
VALUES (default, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'firstAlbum', 1, 'uuid');

INSERT INTO artists
VALUES (default, 3, 'firstArtist', 'uuid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO playlists
VALUES (default, 1, NULL, 'default', 'desc', TRUE);
