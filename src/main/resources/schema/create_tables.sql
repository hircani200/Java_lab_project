CREATE SCHEMA if not exists public;

create table public.functions(
    function_id SERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    x_from NUMERIC(100, 10) ,
    x_to NUMERIC(100, 10) ,
    count INTEGER
);

CREATE TABLE public.function_points (
    point_id SERIAL PRIMARY KEY,
    function_id INTEGER NOT NULL,
    x_value NUMERIC(100, 10) ,
    y_value NUMERIC(100, 10) ,
    CONSTRAINT fk_function
        FOREIGN KEY (function_id)
        REFERENCES public.functions(function_id)
        ON DELETE CASCADE
);