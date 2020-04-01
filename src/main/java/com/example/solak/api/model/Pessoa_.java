package com.example.solak.api.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pessoa.class)
public abstract class Pessoa_ {

	public static volatile SingularAttribute<Pessoa, Long> codigo;
	public static volatile SingularAttribute<Pessoa, Endereco> endereco;
	public static volatile SingularAttribute<Pessoa, String> rg;
	public static volatile SingularAttribute<Pessoa, Boolean> statusAtivo;
	public static volatile SingularAttribute<Pessoa, String> cpf;
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile SingularAttribute<Pessoa, Date> dataCadastro;
	public static volatile SingularAttribute<Pessoa, String> email;

}

