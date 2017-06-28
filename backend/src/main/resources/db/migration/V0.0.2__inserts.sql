INSERT INTO contato(id, datacriacao, dataatualizacao, nome, email, texto)
    VALUES (1, '2017-06-27', null, 'Joao Paulo', 'joaopaulo@cellenti.com.br', 'Quero mais informacoes por favor.');

INSERT INTO contato(id, datacriacao, dataatualizacao, nome, email, texto)
    VALUES (2, '2017-06-27', null, 'Gleice', 'gleice@cellenti.com.br', 'Quero mais informacoes por favor tambem!');


INSERT INTO hibernate_sequences( sequence_name, sequence_next_hi_value) VALUES ('Contato',3);
