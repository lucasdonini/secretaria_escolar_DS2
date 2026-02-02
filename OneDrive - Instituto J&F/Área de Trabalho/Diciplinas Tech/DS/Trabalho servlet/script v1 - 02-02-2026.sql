

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE table aluno (
senha UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
nome varchar(90),
email varchar(90),
matricula varchar(90),
usuario varchar(90)
);

CREATE table professor (
senha UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
nome varchar(90),
disciplina varchar(90),
usuario varchar(90)
);

CREATE table admin (
senha UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
usuario varchar(90)
);

