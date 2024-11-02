CREATE SCHEMA if not exists public;

create table public.functions(
    function_id SERIAL PRIMARY KEY,
    function_name VARCHAR(100) NOT NULL,
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

CREATE TABLE public.tasks (
    task_id SERIAL PRIMARY KEY,
    function_id INTEGER NOT NULL,
    task_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    start_time TIMESTAMP ,
    end_time TIMESTAMP,
    CONSTRAINT fk_task_function
        FOREIGN KEY (function_id)
        REFERENCES public.functions(function_id)
        ON DELETE CASCADE
);

CREATE TABLE public.task_results (
    result_id SERIAL PRIMARY KEY,
    task_id INTEGER NOT NULL,
    result NUMERIC(100, 10),
    CONSTRAINT fk_task
        FOREIGN KEY (task_id)
        REFERENCES public.tasks(task_id)
        ON DELETE CASCADE
);