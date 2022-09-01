set foreign_key_checks = 0;

lock tables cidade write, cozinha write, estado write, forma_pagamento write, 
			grupo write, grupo_permissao write, permissao write, produto write,
			restaurante write, restaurante_forma_pagamento write, restaurante_usuario_responsavel write,
			usuario write, usuario_grupo write, pedido write, item_pedido write, foto_produto write,
			oauth2_registered_client write;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from restaurante_usuario_responsavel;
delete from usuario;
delete from usuario_grupo;
delete from pedido;
delete from item_pedido;
delete from foto_produto;
delete from oauth2_registered_client;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');
insert into cozinha (nome) values ('Argentina');
insert into cozinha (nome) values ('Brasileira');

insert into forma_pagamento (descricao, data_atualizacao) values ('Cartão de crédito', utc_timestamp);
insert into forma_pagamento (descricao, data_atualizacao) values ('Cartão de débito', utc_timestamp);
insert into forma_pagamento (descricao, data_atualizacao) values ('Dinheiro', utc_timestamp);

insert into estado (nome) values ('São Paulo');
insert into estado (nome) values ('Minas Gerais');
insert into estado (nome) values ('Rio de Janeiro');
insert into estado (nome) values ('Espírito Santo');
insert into estado (nome) values ('Bahia');
insert into estado (nome) values ('Ceará');
insert into estado (nome) values ('Pernambuco');
insert into estado (nome) values ('Alagoas');
insert into estado (nome) values ('Rio Grande do Norte');

insert into cidade (nome, estado_id) values ('Guarulhos', 1);
insert into cidade (nome, estado_id) values ('Campinas', 1);
insert into cidade (nome, estado_id) values ('Teófilo Otoni', 2);
insert into cidade (nome, estado_id) values ('Teixeira de Freitas', 5);

insert into grupo (nome) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

insert into permissao (nome, descricao) values ('EDITAR_COZINHAS', 'Permite editar cozinha');
insert into permissao (nome, descricao) values ('EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (nome, descricao) values ('EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (nome, descricao) values ('EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (nome, descricao) values ('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite consultar usuários');
insert into permissao (nome, descricao) values ('EDITAR_USUARIOS_GRUPOS_PERMISSOES', 'Permite criar ou editar usuários');
insert into permissao (nome, descricao) values ('EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (nome, descricao) values ('CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (nome, descricao) values ('GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (nome, descricao) values ('GERAR_RELATORIOS', 'Permite gerar relatórios');

# Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissao (grupo_id, permissao_id)
select 1, id from permissao;

# Adiciona permissoes no grupo do vendedor
# insert into grupo_permissao (grupo_id, permissao_id)
# select 2, id from permissao where nome like 'CONSULTAR_%';

insert into grupo_permissao (grupo_id, permissao_id) values (2, 10);

# Adiciona permissoes no grupo do auxiliar
insert into grupo_permissao (grupo_id, permissao_id)
select 3, id from permissao where nome like 'CONSULTAR_%';

# Adiciona permissoes no grupo cadastrador
insert into grupo_permissao (grupo_id, permissao_id)
select 4, id from permissao where nome like '%_RESTAURANTES' or nome like '%_PRODUTOS';

insert into usuario (nome, email, senha, data_cadastro) values ('Renato Ramos', 'tatoramos@gmail.com', '$2y$12$OAL4vW755qhhEgMnzdxaX.SUFpDDvUHovldrDNpCr0LXoVrB6AFym', utc_timestamp);
insert into usuario (nome, email, senha, data_cadastro) values ('Patricia Avelar', 'pravelar@hotmail.com', '$2y$12$OAL4vW755qhhEgMnzdxaX.SUFpDDvUHovldrDNpCr0LXoVrB6AFym', utc_timestamp);
insert into usuario (nome, email, senha, data_cadastro) values ('Eliana Soares', 'elianaliu@hotmail.com', '$2y$12$OAL4vW755qhhEgMnzdxaX.SUFpDDvUHovldrDNpCr0LXoVrB6AFym', utc_timestamp);
insert into usuario (nome, email, senha, data_cadastro) values ('Roseli Ramos', 'roseramos@hotmail.com', '$2y$12$OAL4vW755qhhEgMnzdxaX.SUFpDDvUHovldrDNpCr0LXoVrB6AFym', utc_timestamp);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2), (3, 3), (4, 4);

insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, false, 1, '38440-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values ('Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, false);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values ('Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, false);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values ('Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, false);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values ('Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, false);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values ('Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, false);

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

delete from restaurante_usuario_responsavel;

insert into usuario (nome, email, senha, data_cadastro) values ('Manoel Lima', 'manoel.loja@gmail.com', '$2y$12$OAL4vW755qhhEgMnzdxaX.SUFpDDvUHovldrDNpCr0LXoVrB6AFym', utc_timestamp);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 5), (3, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

delete from item_pedido;
delete from pedido; 

insert into pedido (codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values ('f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
        'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 2, 2, 110, 220, 'Menos picante, por favor');

insert into pedido (codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values ('d178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', utc_timestamp, 79, 0, 79);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 6, 1, 79, 79, 'Ao ponto');

insert into pedido (codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values ('b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'ENTREGUE', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 1, 110, 110, null);

insert into pedido (codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values ('5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'CRIADO', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (4, 3, 2, 87.2, 174.4, null);

insert into pedido (codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values ('8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'CRIADO', '2019-11-03 02:00:30', '2019-11-03 02:01:21', '2019-11-03 02:20:10', 87.2, 10, 97.2);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (5, 3, 1, 87.2, 87.2, null);

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('1', 'algafood-backend', '2022-08-16 19:04:12', '$2a$10$97f9cT/X/htp85ELK8.IhOBpCRHAmn0Z0cYOJVscCj6esvTIFYOrS', NULL, 'AlgaFood Back-End', 'client_secret_basic', 'client_credentials', '', 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('2', 'algafood-web', '2022-08-16 19:04:12', '$2a$10$ku07Df8C0xrgJ.lId5.Cie..VZH4AReQ0wNIKaqvcMlC3MrjT6IF2', NULL, 'AlgaFood Web', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('3', 'foodanalytics', '2022-08-16 19:04:12', '$2a$10$E5f93hZ5kq97tcZVVUEtru08Eg9KBkziAdyZegNT/cfgJItimzPwW', NULL, 'Food Analytics', 'client_secret_basic', 'authorization_code', 'http://www.foodanalytics.local:8082', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

unlock tables;