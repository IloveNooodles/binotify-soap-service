drop table if exists Logging;
drop table if exists Subscription;

create table Logging(
                        id int(11) AUTO_INCREMENT PRIMARY KEY,
                        description varchar(256) NOT NULL,
                        IP varchar(16) NOT NULL,
                        endpoint varchar(256) NOT NULL,
                        requested_at timestamp NOT NULL DEFAULT NOW()
);

create table Subscription(
                             creator_id int(11) NOT NULL,
                             subscriber_id int(11) NOT NULL,
                             status enum('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
                             PRIMARY KEY(creator_id, subscriber_id)
);
