INSERT INTO public.functions(function_id, function_name, x_from, x_to, count) VALUES (1, 'CosFunction', 0.0, 10.0, 5);

INSERT INTO public.function_points(point_id, function_id, x_value, y_value)
values  (1, 1, 0.0, 1.0),
        (2, 1, 0.0, 2.0),
        (3, 1, 3.14, 3.3);