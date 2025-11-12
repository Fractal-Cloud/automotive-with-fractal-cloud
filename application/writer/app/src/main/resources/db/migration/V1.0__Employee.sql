CREATE TABLE IF NOT EXISTS employee
(
    id
    uuid,
    first_name
    varchar
(
    250
),
    last_name varchar
(
    250
),
    designation varchar
(
    250
),
    phone_number varchar
(
    250
),
    joined_on date,
    address varchar
(
    250
),
    date_of_birth date,
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY
(
    id
)
    );