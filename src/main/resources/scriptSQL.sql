
CREATE SEQUENCE public.patient_id_seq;

CREATE TABLE public.patient (
id INTEGER NOT NULL DEFAULT nextval('public.patient_id_seq'),
dob TIMESTAMP NOT NULL,
address VARCHAR(100) NOT NULL,
phone VARCHAR(100) NOT NULL,
sex CHAR NOT NULL
);

ALTER SEQUENCE public.patient_id_seq OWNED BY public.patient.id;
