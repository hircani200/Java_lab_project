INSERT INTO public.functions(function_id, function_name, x_from, x_to, count) VALUES (1, 'CosFunction', 0.0, 10.0, 5);

INSERT INTO public.tasks(task_id, function_id, task_type, status, start_time, end_time)
values  (1, 1, 'Sum', 'Completed', '2024-11-02 10:00:00', '2024-11-02 10:00:15'),
        (2, 1, 'Integration', 'In progress', '2024-11-02 11:00:00', '2024-11-02 11:35:10'),
        (3, 1, 'MultiPlication', 'Pending', '2024-11-02 10:10:56', '2024-11-02 10:30:15');

INSERT INTO public.task_results(result_id, task_id, result)
values  (1, 3, 0.0),
        (2, 2, 3.14),
        (3, 1, 2.7);