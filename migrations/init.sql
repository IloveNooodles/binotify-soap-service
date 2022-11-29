drop table if exists Logging;
drop table if exists Subscription;

create table Logging(
                        id int(11) AUTO_INCREMENT PRIMARY KEY,
                        description varchar(256) NOT NULL,
                        IP varchar(16) NOT NULL,
                        endpoint varchar(256) NOT NULL,
                        requested_at timestamp NOT NULL DEFAULT NOW()
);

INSERT INTO Logging
(description, IP, endpoint, requested_at)
VALUES('See song', '135.20.19.1', '/Song', CURRENT_TIMESTAMP);
INSERT INTO binotify_soap.Logging
(description, IP, endpoint, requested_at)
VALUES('Subscription activating', '135.20.19.1', '/subscription', CURRENT_TIMESTAMP);


create table Subscription(
                             creator_id int(11) NOT NULL,
                             subscriber_id int(11) NOT NULL,
                             status enum('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
                             PRIMARY KEY(creator_id, subscriber_id)
);

INSERT INTO Subscription
(creator_id, subscriber_id, status)
VALUES(0, 4, 'PENDING');
INSERT INTO binotify_soap.Subscription
(creator_id, subscriber_id, status)
VALUES(0, 1, 'PENDING');
INSERT INTO binotify_soap.Subscription
(creator_id, subscriber_id, status)
VALUES(0, 2, 'PENDING');
INSERT INTO binotify_soap.Subscription
(creator_id, subscriber_id, status)
VALUES(0, 3, 'PENDING');
INSERT INTO binotify_soap.Subscription
(creator_id, subscriber_id, status)
VALUES(0, 5, 'ACCEPTED');
