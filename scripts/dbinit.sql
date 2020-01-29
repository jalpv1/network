INSERT INTO public.nodes_info (node_id, type, name, description, identifier) VALUES (327, 'FEEDER', 'New feeder', 'Feeder that belong to Dnipro-tf', 'FEEDER327');
INSERT INTO public.nodes_info (node_id, type, name, description, identifier) VALUES (326, 'FEEDER', 'Long feeder', 'Feeder that belong to Dnipro-tf', 'FEEDER326');
INSERT INTO public.nodes_info (node_id, type, name, description, identifier) VALUES (324, 'network', 'Kyiv_sub', 'Kyiv substation', 'network324');
INSERT INTO public.nodes_info (node_id, type, name, description, identifier) VALUES (330, 'RESOURCE', 'Consumer', 'Load that belong to fdr-1', 'RESOURCE330');
INSERT INTO public.nodes_info (node_id, type, name, description, identifier) VALUES (328, 'RESOURCE', 'Big-consumer', 'Load that belong to fdr-1', 'RESOURCE328');
INSERT INTO public.nodes_info (node_id, type, name, description, identifier) VALUES (329, 'RESOURCE', 'SP-saturn', 'Solar panels, 10MWatt', 'RESOURCE329');
INSERT INTO public.nodes_info (node_id, type, name, description, identifier) VALUES (325, 'TRANSFORMER', 'Dnipro-tf', 'Dnipro transformer', 'TRANSFORMER325')
;

INSERT INTO public.node_params (id, node_id, param_name, param_value) VALUES (295, 324, 'lon', '50.45');
INSERT INTO public.node_params (id, node_id, param_name, param_value) VALUES (296, 324, 'lat', '30.52');
INSERT INTO public.node_params (id, node_id, param_name, param_value) VALUES (297, 328, 'consumes', '20');
INSERT INTO public.node_params (id, node_id, param_name, param_value) VALUES (298, 328, 'units', 'MWatt');
INSERT INTO public.node_params (id, node_id, param_name, param_value) VALUES (299, 330, 'consumes', '10');
INSERT INTO public.node_params (id, node_id, param_name, param_value) VALUES (300, 330, 'units', 'MWatt');


INSERT INTO public.hierarchy (id, node_id, parent_id, root_id) VALUES (311, 324, 0, 324);
INSERT INTO public.hierarchy (id, node_id, parent_id, root_id) VALUES (312, 325, 324, 324);
INSERT INTO public.hierarchy (id, node_id, parent_id, root_id) VALUES (313, 326, 325, 324);
INSERT INTO public.hierarchy (id, node_id, parent_id, root_id) VALUES (314, 327, 325, 324);
INSERT INTO public.hierarchy (id, node_id, parent_id, root_id) VALUES (315, 328, 326, 324);
INSERT INTO public.hierarchy (id, node_id, parent_id, root_id) VALUES (316, 329, 326, 324);
INSERT INTO public.hierarchy (id, node_id, parent_id, root_id) VALUES (317, 330, 327, 324);
