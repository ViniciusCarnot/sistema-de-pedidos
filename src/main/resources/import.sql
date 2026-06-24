INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (2, 'PC Gamer Entrada', 3300.0, 'INATIVO');
INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (3, 'GTA San Andreas', 65.0, 'ATIVO');
INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (4, 'GTA Vice City', 25.0, 'ATIVO');
INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (5, 'Monitor 27pol QHD 165Hz', 850.0, 'ATIVO');
INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (6, 'Monitor 24pol FHD 165Hz', 600.0, 'ATIVO');
INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (7, 'Uncharted 4', 85.0, 'ATIVO');
INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (8, 'Impressora de Entrada', 128.99, 'ATIVO');
INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (9, 'PC GAMER Intermediário', 7500.0, 'ATIVO');
INSERT INTO tb_produto (id, nome, preco, status_produto) VALUES (10, 'Need For Speed Most Wanted [2005] PS2 ', 15.0, 'INATIVO');

INSERT INTO tb_categoria (id, nome) VALUES (3, 'PC');
INSERT INTO tb_categoria (id, nome) VALUES (4, 'Jogos');

INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (3, 2);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (3, 9);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (4, 3);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (4, 4);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (4, 7);

INSERT INTO tb_estado (id, nome) VALUES (1, 'Acre');
INSERT INTO tb_estado (id, nome) VALUES (2, 'Alagoas');
INSERT INTO tb_estado (id, nome) VALUES (3, 'Amapá');
INSERT INTO tb_estado (id, nome) VALUES (4, 'Amazonas');
INSERT INTO tb_estado (id, nome) VALUES (5, 'Bahia');
INSERT INTO tb_estado (id, nome) VALUES (6, 'Ceará');
INSERT INTO tb_estado (id, nome) VALUES (7, 'Distrito Federal');
INSERT INTO tb_estado (id, nome) VALUES (8, 'Espírito Santo');
INSERT INTO tb_estado (id, nome) VALUES (9, 'Goiás');
INSERT INTO tb_estado (id, nome) VALUES (10, 'Maranhão');
INSERT INTO tb_estado (id, nome) VALUES (11, 'Mato Grosso');
INSERT INTO tb_estado (id, nome) VALUES (12, 'Mato Grosso do Sul');
INSERT INTO tb_estado (id, nome) VALUES (13, 'Minas Gerais');
INSERT INTO tb_estado (id, nome) VALUES (14, 'Pará');
INSERT INTO tb_estado (id, nome) VALUES (15, 'Paraíba');
INSERT INTO tb_estado (id, nome) VALUES (16, 'Paraná');
INSERT INTO tb_estado (id, nome) VALUES (17, 'Pernambuco');
INSERT INTO tb_estado (id, nome) VALUES (18, 'Piauí');
INSERT INTO tb_estado (id, nome) VALUES (19, 'Rio de Janeiro');
INSERT INTO tb_estado (id, nome) VALUES (20, 'Rio Grande do Norte');
INSERT INTO tb_estado (id, nome) VALUES (21, 'Rio Grande do Sul');
INSERT INTO tb_estado (id, nome) VALUES (22, 'Rondônia');
INSERT INTO tb_estado (id, nome) VALUES (23, 'Roraima');
INSERT INTO tb_estado (id, nome) VALUES (24, 'Santa Catarina');
INSERT INTO tb_estado (id, nome) VALUES (25, 'São Paulo');
INSERT INTO tb_estado (id, nome) VALUES (26, 'Sergipe');
INSERT INTO tb_estado (id, nome) VALUES (27, 'Tocantins');

INSERT INTO tb_cidade (id, nome, estado_id) VALUES (1, 'Rio Branco', 1);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (2, 'Maceió', 2);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (3, 'Macapá', 3);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (4, 'Manaus', 4);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (5, 'Salvador', 5);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (6, 'Fortaleza', 6);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (7, 'Brasília', 7);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (8, 'Vitória', 8);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (9, 'Goiânia', 9);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (10, 'São Luís', 10);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (11, 'Cuiabá', 11);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (12, 'Campo Grande', 12);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (13, 'Belo Horizonte', 13);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (14, 'Belém', 14);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (15, 'João Pessoa', 15);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (16, 'Curitiba', 16);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (17, 'Recife', 17);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (18, 'Teresina', 18);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (19, 'Rio de Janeiro', 19);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (20, 'Natal', 20);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (21, 'Porto Alegre', 21);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (22, 'Porto Velho', 22);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (23, 'Boa Vista', 23);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (24, 'Florianópolis', 24);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (25, 'São Paulo', 25);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (26, 'Aracaju', 26);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (27, 'Palmas', 27);
INSERT INTO tb_cidade (id, nome, estado_id) VALUES (29, 'Campo Grande', 20);

INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (1, 'Jardim Oriente', 'Avenida Floriano', '54C', 6);
INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (2, 'Independência', 'Rua Boa Morte', '1459', 12);
INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (4, 'Nova Cidade', 'Rua das Isis', '77', 24);
INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (5, 'Vila Lobos', 'Avenida Jaraguá', '36', 12);
INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (6, 'Monte Leste', 'Furtado Mendes', '99A', 13);
INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (7, 'AAA', 'AAA', 'AAA', 1);
INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (8, 'BBB', 'BBB', 'BBB', 2);
INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (9, 'CCC', 'CCC', 'CCC', 3);
INSERT INTO tb_endereco (id, bairro, logradouro, numero, cidade_id) VALUES (10, 'DDD', 'DDD', 'DDD', 4);

INSERT INTO tb_cliente (id, cpf_ou_cnpj, email, nome, role, senha, tipo) VALUES (6, '842.107.660-31', 'albertorodrigues@email.com', 'Alberto Rodrigues', 'NORMAL', '$2a$10$hVXWCGm/1zQgBiB6RoZRyOj.46zUeMdj5ggvPIS3hxnY45X3LFm6K', 'PESSOA_FISICA');
INSERT INTO tb_cliente (id, cpf_ou_cnpj, email, nome, role, senha, tipo) VALUES (7, '842.107.660-31', 'bernardosilva@email.com', 'Bernardo Silva', 'ADMIN', '$2a$10$Y19DyKkLcKmr5bpUmWu9c.FKXjNJYMDpMEh9V43/DN/XUvvwEM7ES', 'PESSOA_FISICA');
INSERT INTO tb_cliente (id, cpf_ou_cnpj, email, nome, role, senha, tipo) VALUES (8, '418.502.610-33', 'carlosalves@email.com', 'Carlos Alves', 'ADMIN', '$2a$10$5nPzWzXF6CZfgxNxTQOpeOe2oz7Zjp0MWxX9KteYhmFzo92kZEkZq', 'PESSOA_FISICA');
INSERT INTO tb_cliente (id, cpf_ou_cnpj, email, nome, role, senha, tipo) VALUES (9, '953.740.180-80', 'danielramos@email.com', 'Daniel Ramos', 'NORMAL', '$2a$10$zbiQe3elLSIvk9xJRPgUA.45R1hrnFpzWAydHkUxi78FtTAlg0zc6', 'PESSOA_JURIDICA');

INSERT INTO tb_telefone (id, numero, cliente_id) VALUES (5, '(99) 99999-9999', 6);
INSERT INTO tb_telefone (id, numero, cliente_id) VALUES (6, '(61) 98115-6673', 6);
INSERT INTO tb_telefone (id, numero, cliente_id) VALUES (9, '(11) 97233-1045', 7);
INSERT INTO tb_telefone (id, numero, cliente_id) VALUES (10, '(21) 98821-4560', 7);
INSERT INTO tb_telefone (id, numero, cliente_id) VALUES (11, '(99) 99999-9999', 8);
INSERT INTO tb_telefone (id, numero, cliente_id) VALUES (12, '(88) 88888-8888', 8);
INSERT INTO tb_telefone (id, numero, cliente_id) VALUES (13, '(77) 77777-7777', 9);

INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (6, 1);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (6, 6);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (7, 4);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (8, 4);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (8, 5);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (9, 9);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (9, 10);

INSERT INTO tb_pedido (id, instante_da_compra, cliente_id, endereco_de_entrega_id, status_pedido) VALUES (9, TIMESTAMP WITH TIME ZONE '2026-02-03T15:33:39Z', 8, 9, 'CANCELADO');
INSERT INTO tb_pedido (id, instante_da_compra, cliente_id, endereco_de_entrega_id, status_pedido) VALUES (10, TIMESTAMP WITH TIME ZONE '2026-02-03T15:43:39Z', 6, 6, 'ENTREGUE');
INSERT INTO tb_pedido (id, instante_da_compra, cliente_id, endereco_de_entrega_id, status_pedido) VALUES (11, TIMESTAMP WITH TIME ZONE '2026-02-12T23:39:21Z', 9, 10, 'AGUARDANDO_PAGAMENTO');

INSERT INTO tb_item_pedido (produto_id, pedido_id, quantidade, desconto_unitario, preco_unitario) VALUES (3, 9, 1, 0.00, 70.00);
INSERT INTO tb_item_pedido (produto_id, pedido_id, quantidade, desconto_unitario, preco_unitario) VALUES (4, 9, 2, 10.00, 45.00);
INSERT INTO tb_item_pedido (produto_id, pedido_id, quantidade, desconto_unitario, preco_unitario) VALUES (5, 10, 1, 50.00, 850.00);
INSERT INTO tb_item_pedido (produto_id, pedido_id, quantidade, desconto_unitario, preco_unitario) VALUES (6, 10, 1, 50.00, 600.00);
INSERT INTO tb_item_pedido (produto_id, pedido_id, quantidade, desconto_unitario, preco_unitario) VALUES (7, 11, 1, 10.00, 85.00);
INSERT INTO tb_item_pedido (produto_id, pedido_id, quantidade, desconto_unitario, preco_unitario) VALUES (8, 11, 1, 10.00, 128.99);

INSERT INTO tb_pagamento (pedido_id, tipo_pagamento, estado_pagamento) VALUES (9, 'BOLETO', 'PENDENTE');
INSERT INTO tb_pagamento (pedido_id, tipo_pagamento, estado_pagamento) VALUES (10, 'BOLETO', 'PENDENTE');
INSERT INTO tb_pagamento (pedido_id, tipo_pagamento, estado_pagamento) VALUES (11, 'BOLETO', 'PENDENTE');

INSERT INTO tb_boleto (pedido_id, data_vencimento) VALUES (9, TIMESTAMP WITH TIME ZONE '2026-05-12T13:00:00Z');
INSERT INTO tb_boleto (pedido_id, data_vencimento) VALUES (10, TIMESTAMP WITH TIME ZONE '2026-05-03T13:00:00Z');
INSERT INTO tb_boleto (pedido_id, data_vencimento) VALUES (11, TIMESTAMP WITH TIME ZONE '2026-05-12T13:00:00Z');






