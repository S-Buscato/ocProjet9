
CREATE SEQUENCE public.patient_id_seq;

CREATE TABLE public.patient (
id INTEGER NOT NULL DEFAULT nextval('public.patient_id_seq'),
firstname VARCHAR(100) NOT NULL,
lastname VARCHAR(100) NOT NULL,
dob TIMESTAMP NOT NULL,
address VARCHAR(100),
phone VARCHAR(100),
sex CHAR NOT NULL
);

ALTER SEQUENCE public.patient_id_seq OWNED BY public.patient.id;
/*TODO : voir controle unicité*/