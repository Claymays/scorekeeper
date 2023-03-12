create table scorekeeper.native
(
    next_val bigint null
);

create table scorekeeper.user
(
    id        int          not null
        primary key,
    password  varchar(255) null,
    user_name varchar(255) null
);

create table scorekeeper.friend
(
    id           int          not null
        primary key,
    losses       float        null,
    name         varchar(255) null,
    note         varchar(255) null,
    wins         float        null,
    friend_of_id int          null,
    constraint FK9w7s02jpf9beb1lmqueqrhxnb
        foreign key (friend_of_id) references scorekeeper.user (id)
);

create table scorekeeper.game
(
    id       int          not null
        primary key,
    name     varchar(255) null,
    note     varchar(255) null,
    owner_id int          null,
    constraint FK78rluwi9sko0vaaxgunxeqcyc
        foreign key (owner_id) references scorekeeper.user (id)
);

create table scorekeeper.friend_games
(
    friend_id int not null,
    game_id   int not null,
    constraint FKcla0p2iq735ji99wwerlhkhg9
        foreign key (friend_id) references scorekeeper.friend (id),
    constraint FKrqsyv4opabkfvj336snvuevvc
        foreign key (game_id) references scorekeeper.game (id)
);

create table scorekeeper.user_friend
(
    friend_id int not null,
    user_id   int not null,
    constraint UK_yqo5tjhs5j9v500vx9dsciks
        unique (user_id),
    constraint FK2awsxv8v89cse0nwaadkd8ufx
        foreign key (friend_id) references scorekeeper.user (id),
    constraint FKkfrhebyxly083n2m0hb7jsw2s
        foreign key (user_id) references scorekeeper.friend (id)
);

create table scorekeeper.user_game
(
    game_id int not null,
    user_id int not null,
    constraint UK_555uy7obk35j9opflw2o2yo1u
        unique (user_id),
    constraint FKfahe5jy24aq6d0ue83ws1exg3
        foreign key (game_id) references scorekeeper.user (id),
    constraint FKj7fgfrfhm07prvc8hs9kmu79a
        foreign key (user_id) references scorekeeper.game (id)
);

