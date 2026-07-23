INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('PC Gamer Entrada', 3300.0, 'INDISPONIVEL', true);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('GTA San Andreas', 65.0, 'DISPONIVEL', true);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('GTA Vice City', 25.0, 'DISPONIVEL', false);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('Monitor 27pol QHD 165Hz', 850.0, 'DISPONIVEL', true);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('Monitor 24pol FHD 165Hz', 600.0, 'DISPONIVEL', false);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('Uncharted 4', 85.0, 'DISPONIVEL', true);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('Impressora de Entrada', 128.99, 'DISPONIVEL', true);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('PC GAMER Intermediário', 7500.0, 'DISPONIVEL', false);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('Need For Speed Most Wanted [2005] PS2', 15.0, 'INDISPONIVEL', true);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('Teclado Aula F75', 315.0, 'DISPONIVEL', true);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('Teclado Mancer', 90.0, 'DISPONIVEL', true);
INSERT INTO tb_produto (nome, preco, disponibilidade, visibilidade) VALUES ('Teclado Raze', 450.0, 'DISPONIVEL', true);

INSERT INTO tb_categoria (nome) VALUES ('PC');
INSERT INTO tb_categoria (nome) VALUES ('Jogos');
INSERT INTO tb_categoria (nome) VALUES ('Jogos Antigos');
INSERT INTO tb_categoria (nome) VALUES ('Mouses');

INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (1, 1);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (2, 2);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (3, 2);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (2, 3);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (3, 3);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (1, 4);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (1, 5);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (2, 6);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (1, 7);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (1, 8);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (2, 9);
INSERT INTO tb_categoria_produto (categoria_id, produto_id) VALUES (3, 9);

INSERT INTO tb_estado (nome) VALUES ('Acre');
INSERT INTO tb_estado (nome) VALUES ('Alagoas');
INSERT INTO tb_estado (nome) VALUES ('Amapá');
INSERT INTO tb_estado (nome) VALUES ('Amazonas');
INSERT INTO tb_estado (nome) VALUES ('Bahia');
INSERT INTO tb_estado (nome) VALUES ('Ceará');
INSERT INTO tb_estado (nome) VALUES ('Distrito Federal');
INSERT INTO tb_estado (nome) VALUES ('Espírito Santo');
INSERT INTO tb_estado (nome) VALUES ('Goiás');
INSERT INTO tb_estado (nome) VALUES ('Maranhão');
INSERT INTO tb_estado (nome) VALUES ('Mato Grosso');
INSERT INTO tb_estado (nome) VALUES ('Mato Grosso do Sul');
INSERT INTO tb_estado (nome) VALUES ('Minas Gerais');
INSERT INTO tb_estado (nome) VALUES ('Pará');
INSERT INTO tb_estado (nome) VALUES ('Paraíba');
INSERT INTO tb_estado (nome) VALUES ('Paraná');
INSERT INTO tb_estado (nome) VALUES ('Pernambuco');
INSERT INTO tb_estado (nome) VALUES ('Piauí');
INSERT INTO tb_estado (nome) VALUES ('Rio de Janeiro');
INSERT INTO tb_estado (nome) VALUES ('Rio Grande do Norte');
INSERT INTO tb_estado (nome) VALUES ('Rio Grande do Sul');
INSERT INTO tb_estado (nome) VALUES ('Rondônia');
INSERT INTO tb_estado (nome) VALUES ('Roraima');
INSERT INTO tb_estado (nome) VALUES ('Santa Catarina');
INSERT INTO tb_estado (nome) VALUES ('São Paulo');
INSERT INTO tb_estado (nome) VALUES ('Sergipe');
INSERT INTO tb_estado (nome) VALUES ('Tocantins');

INSERT INTO tb_cidade (nome, estado_id) VALUES ('Rio Branco', 1);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Maceió', 2);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Macapá', 3);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Manaus', 4);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Salvador', 5);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Fortaleza', 6);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Brasília', 7);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Vitória', 8);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Goiânia', 9);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('São Luís', 10);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Cuiabá', 11);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Campo Grande', 12);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Belo Horizonte', 13);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Belém', 14);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('João Pessoa', 15);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Curitiba', 16);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Recife', 17);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Teresina', 18);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Rio de Janeiro', 19);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Natal', 20);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Porto Alegre', 21);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Porto Velho', 22);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Boa Vista', 23);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Florianópolis', 24);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('São Paulo', 25);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Aracaju', 26);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Palmas', 27);
INSERT INTO tb_cidade (nome, estado_id) VALUES ('Campo Grande', 20);

INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('Jardim Oriente', 'Avenida Floriano', '54C', 1);
INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('Independência', 'Rua Boa Morte', '1459', 2);
INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('Nova Cidade', 'Rua das Isis', '77', 3);
INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('Vila Lobos', 'Avenida Jaraguá', '36', 4);
INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('Monte Leste', 'Furtado Mendes', '99A', 5);
INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('AAA', 'AAA', 'AAA', 1);
INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('BBB', 'BBB', 'BBB', 2);
INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('CCC', 'CCC', 'CCC', 3);
INSERT INTO tb_endereco (bairro, logradouro, numero, cidade_id) VALUES ('DDD', 'DDD', 'DDD', 4);

INSERT INTO tb_role (nome) VALUES ('ROLE_NORMAL');
INSERT INTO tb_role (nome) VALUES ('ROLE_ADMIN');

INSERT INTO tb_cliente (nome, email, senha, cpf_ou_cnpj, tipo, ativo) VALUES ('Alberto Rodrigues', 'alberto@email.com', '$2a$10$hVXWCGm/1zQgBiB6RoZRyOj.46zUeMdj5ggvPIS3hxnY45X3LFm6K', '842.107.660-31', 'PESSOA_FISICA', true);
INSERT INTO tb_cliente (nome, email, senha, cpf_ou_cnpj, tipo, ativo) VALUES ('Bernardo Silva', 'bernardo@email.com', '$2a$10$Y19DyKkLcKmr5bpUmWu9c.FKXjNJYMDpMEh9V43/DN/XUvvwEM7ES', '842.107.660-31', 'PESSOA_FISICA', true);
INSERT INTO tb_cliente (nome, email, senha, cpf_ou_cnpj, tipo, ativo) VALUES ('Carlos Alves', 'carlos@email.com', '$2a$10$5nPzWzXF6CZfgxNxTQOpeOe2oz7Zjp0MWxX9KteYhmFzo92kZEkZq', '418.502.610-33', 'PESSOA_FISICA', true);
INSERT INTO tb_cliente (nome, email, senha, cpf_ou_cnpj, tipo, ativo) VALUES ('Daniel Ramos', 'daniel@email.com', '$2a$10$zbiQe3elLSIvk9xJRPgUA.45R1hrnFpzWAydHkUxi78FtTAlg0zc6', '953.740.180-80', 'PESSOA_JURIDICA', false);

INSERT INTO tb_cliente_role (cliente_id, role_id) VALUES (1, 1);
INSERT INTO tb_cliente_role (cliente_id, role_id) VALUES (1, 2);
INSERT INTO tb_cliente_role (cliente_id, role_id) VALUES (2, 2);
INSERT INTO tb_cliente_role (cliente_id, role_id) VALUES (3, 1);
INSERT INTO tb_cliente_role (cliente_id, role_id) VALUES (4, 1);

INSERT INTO tb_telefone (numero, cliente_id) VALUES ('(11) 11111-1111', 1);
INSERT INTO tb_telefone (numero, cliente_id) VALUES ('(22) 22222-2222', 2);
INSERT INTO tb_telefone (numero, cliente_id) VALUES ('(33) 33333-3333', 3);
INSERT INTO tb_telefone (numero, cliente_id) VALUES ('(44) 44444-4444', 4);

INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (1, 1);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (1, 2);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (2, 2);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (3, 3);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (3, 4);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (4, 5);
INSERT INTO tb_cliente_endereco (cliente_id, endereco_id) VALUES (4, 6);

INSERT INTO tb_pedido (instante_da_compra, status_pedido, cliente_id, endereco_de_entrega_id) VALUES (TIMESTAMP WITH TIME ZONE '2026-02-03T15:33:39Z', 'AGUARDANDO_PAGAMENTO', 1, 2);
INSERT INTO tb_pedido (instante_da_compra, status_pedido, cliente_id, endereco_de_entrega_id) VALUES (TIMESTAMP WITH TIME ZONE '2026-02-03T15:43:39Z', 'ENTREGUE', 2, 2);
INSERT INTO tb_pedido (instante_da_compra, status_pedido, cliente_id, endereco_de_entrega_id) VALUES (TIMESTAMP WITH TIME ZONE '2026-02-12T23:39:21Z', 'AGUARDANDO_PAGAMENTO',3, 3);

INSERT INTO tb_item_pedido (produto_id, pedido_id, nome_produto, quantidade, preco_unitario) VALUES (2, 1, 'GTA San Andreas', 1, 65.00);
INSERT INTO tb_item_pedido (produto_id, pedido_id, nome_produto, quantidade, preco_unitario) VALUES (3, 1, 'GTA Vice City', 1, 25.00);
INSERT INTO tb_item_pedido (produto_id, pedido_id, nome_produto, quantidade, preco_unitario) VALUES (1, 2, 'PC Gamer Entrada', 1, 3300.00);
INSERT INTO tb_item_pedido (produto_id, pedido_id, nome_produto, quantidade, preco_unitario) VALUES (4, 3, 'Monitor 27pol QHD 165Hz',1, 850.00);

INSERT INTO tb_pagamento (pedido_id, tipo_pagamento, estado_pagamento) VALUES (1, 'BOLETO', 'PENDENTE');
INSERT INTO tb_pagamento (pedido_id, tipo_pagamento, estado_pagamento) VALUES (2, 'CARTAO_DE_CREDITO', 'PENDENTE');
INSERT INTO tb_pagamento (pedido_id, tipo_pagamento, estado_pagamento) VALUES (3, 'BOLETO', 'PENDENTE');

INSERT INTO tb_boleto (pedido_id, data_vencimento) VALUES (1, TIMESTAMP WITH TIME ZONE '2026-05-12T13:00:00Z');
INSERT INTO tb_boleto (pedido_id, data_vencimento) VALUES (3, TIMESTAMP WITH TIME ZONE '2026-05-03T13:00:00Z');

INSERT INTO tb_cartao_de_credito (pedido_id, quantidade_parcelas) VALUES (2, 10);

