create table person (
    id BIGINT NOT NULL DEFAULT NEXTVAL(('"person_id_seq"' :: TEXT) :: REGCLASS),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    handling_count INTEGER NOT NULL,
    rating NUMERIC(8, 5)
);

ALTER TABLE person
    ADD CONSTRAINT pk_person_id
        PRIMARY KEY (id);

CREATE SEQUENCE person_id_seq INCREMENT 1 START 1;