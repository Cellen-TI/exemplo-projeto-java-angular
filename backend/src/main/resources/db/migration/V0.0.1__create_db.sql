CREATE TABLE hibernate_sequences(
	sequence_name varchar(255) NOT NULL,
	sequence_next_hi_value bigint NOT NULL
);

CREATE TABLE contato(
  id bigint NOT NULL,
  dataatualizacao timestamp,
  datacriacao timestamp,
  texto varchar(2000) NOT NULL,
  nome varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  CONSTRAINT permissao_pkey PRIMARY KEY (id)  
);
