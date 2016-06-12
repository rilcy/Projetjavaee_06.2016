
    alter table Event_Athlete 
        drop constraint FK_6aqx2up3qlojdfcvc29qw9sim

    alter table Event_Athlete 
        drop constraint FK_asexiga9sorm14vx1ln7aap2k

    alter table Event_Manager 
        drop constraint FK_pcu3y7hydr5g6f2xcjh0066c2

    alter table Event_Manager 
        drop constraint FK_47h8bq8h4oc4vrxadk2vgfuvx

    alter table Time 
        drop constraint FK_t27h2b5k8eifv26ucfs4bfjpu

    alter table Time 
        drop constraint FK_h49w0unvnhslmgop589byos66

    drop table Athlete if exists

    drop table Event if exists

    drop table Event_Athlete if exists

    drop table Event_Manager if exists

    drop table Manager if exists

    drop table Time if exists

    drop sequence hibernate_sequence

    create table Athlete (
        id bigint not null,
        mail varchar(255),
        phoneNumber varchar(255),
        firstname varchar(255),
        lastname varchar(255),
        city varchar(255),
        number integer not null,
        postalCode integer not null,
        street varchar(255),
        club varchar(255),
        sex varchar(255),
        primary key (id)
    )

    create table Event (
        id bigint not null,
        city varchar(255),
        number integer not null,
        postalCode integer not null,
        street varchar(255),
        mail varchar(255),
        phoneNumber varchar(255),
        eventName varchar(255),
        year integer not null,
        primary key (id)
    )

    create table Event_Athlete (
        events_id bigint not null,
        athletes_id bigint not null,
        primary key (events_id, athletes_id)
    )

    create table Event_Manager (
        events_id bigint not null,
        managers_id bigint not null,
        primary key (events_id, managers_id)
    )

    create table Manager (
        id bigint not null,
        mail varchar(255),
        phoneNumber varchar(255),
        firstname varchar(255),
        lastname varchar(255),
        function varchar(255),
        primary key (id)
    )

    create table Time (
        id bigint not null,
        time varchar(255),
        event_id bigint,
        athlete_fk bigint,
        primary key (id)
    )

    alter table Event_Athlete 
        add constraint FK_6aqx2up3qlojdfcvc29qw9sim 
        foreign key (athletes_id) 
        references Athlete

    alter table Event_Athlete 
        add constraint FK_asexiga9sorm14vx1ln7aap2k 
        foreign key (events_id) 
        references Event

    alter table Event_Manager 
        add constraint FK_pcu3y7hydr5g6f2xcjh0066c2 
        foreign key (managers_id) 
        references Manager

    alter table Event_Manager 
        add constraint FK_47h8bq8h4oc4vrxadk2vgfuvx 
        foreign key (events_id) 
        references Event

    alter table Time 
        add constraint FK_t27h2b5k8eifv26ucfs4bfjpu 
        foreign key (event_id) 
        references Event

    alter table Time 
        add constraint FK_h49w0unvnhslmgop589byos66 
        foreign key (athlete_fk) 
        references Athlete

    create sequence hibernate_sequence start with 1
