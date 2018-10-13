/*
 * generated by Xtext 2.12.0
 */
package org.mito.gesiel.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import static org.junit.Assert.*
import org.junit.Test
import org.junit.Assert
import org.junit.runner.RunWith
import org.mito.gesiel.gesiel.Domainmodel
import org.mito.gesiel.gesiel.Entity

@RunWith(XtextRunner)
@InjectWith(GesielInjectorProvider)
class GesielParsingTest {
	@Inject
	ParseHelper<Domainmodel> parseHelper
	
	@Test 
    def void loadModel() {
        val result = parseHelper.parse('''
            @filename="teste"
            
            entity Telefone {
            	ddd: char(2)
            	numero: varchar(10)
            }
            
            entity Pessoa{
            	nome: text
            	cpf: char(11)
            	data_nascimento: date
            	many Telefone
            }
            
            entity Cliente extends Pessoa{
            	data_cadastro: timestamp  = "now()"
            	login: varchar(30)
            	senha: varchar(20)
            }
            
            
            entity Fabricante{
            	nome: text
            	cnpj: char(14) unique
            	data_cadastro: timestamp allowNull
            }
            
            entity Produto{
            	nome: text
            	preco: numeric
            	Fabricante
            }
            
            entity Compra{
            	data: timestamp
            	Pessoa
            	many Produto{
            		desconto: int allowNull
            	}
            }
        ''')
        
        Assert.assertNotNull(result)
		val errors = result.eResource.errors
		Assert.assertTrue('''Unexpected errors: «errors.join(", ")»''', errors.isEmpty)
    }
    	//--------------------SQL------------------//
    	/*
        drop database if exists teste;
		create database teste;
		\connect teste
		
		CREATE TABLE telefone (
			id_telefone SERIAL,
			ddd CHAR(2) NOT NULL,
			numero VARCHAR(10) NOT NULL,
			id_pessoa INTEGER NOT NULL,
			CONSTRAINT telefone_pk PRIMARY KEY (id_telefone)
		); 
		
		CREATE TABLE pessoa (
			id_pessoa SERIAL,
			nome TEXT NOT NULL,
			cpf CHAR(11) NOT NULL,
			data_nascimento DATE NOT NULL,
			CONSTRAINT pessoa_pk PRIMARY KEY (id_pessoa)
		); 
		
		CREATE TABLE cliente (
			id_cliente SERIAL,
			data_cadastro TIMESTAMP NOT NULL DEFAULT now(),
			login VARCHAR(30) NOT NULL,
			senha VARCHAR(20) NOT NULL,
			CONSTRAINT cliente_pk PRIMARY KEY (id_cliente)
		) INHERITS (pessoa); 
		
		CREATE TABLE fabricante (
			id_fabricante SERIAL,
			nome TEXT NOT NULL,
			cnpj CHAR(14) NOT NULL UNIQUE,
			data_cadastro TIMESTAMP,
			CONSTRAINT fabricante_pk PRIMARY KEY (id_fabricante)
		); 
		
		CREATE TABLE produto (
			id_produto SERIAL,
			nome TEXT NOT NULL,
			preco NUMERIC NOT NULL,
			id_fabricante INTEGER NOT NULL,
			CONSTRAINT produto_pk PRIMARY KEY (id_produto)
		); 
		
		CREATE TABLE compra (
			id_compra SERIAL,
			data TIMESTAMP NOT NULL,
			id_pessoa INTEGER NOT NULL,
			CONSTRAINT compra_pk PRIMARY KEY (id_compra)
		); 
		
		CREATE TABLE compra_produto (
			id_compra INTEGER,
			id_produto INTEGER,
			desconto INTEGER,
			CONSTRAINT compra_produto_pk PRIMARY KEY (id_compra,id_produto)
		); 
		
		ALTER TABLE compra ADD CONSTRAINT compra_fk0 FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa);
		
		ALTER TABLE compra_produto ADD CONSTRAINT compra_produto_fk0 FOREIGN KEY (id_compra) REFERENCES compra(id_compra);
		ALTER TABLE compra_produto ADD CONSTRAINT compra_produto_fk1 FOREIGN KEY (id_produto) REFERENCES produto(id_produto);
		
		ALTER TABLE telefone ADD CONSTRAINT telefone_fk0 FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa);
		
		ALTER TABLE produto ADD CONSTRAINT produto_fk0 FOREIGN KEY (id_fabricante) REFERENCES fabricante(id_fabricante);
		     
         */  
        
    
    
    @Test 
    def void teste2() {
        val result = parseHelper.parse('''
			entity Pessoa{
			    nome: text
			}
			entity Comentario{
			    comentario: text
			    data: timestamp = "now"
			    Pessoa
			}
			
			entity Post{
			    titulo: varchar(50)
			    many Comentario allowNull
			}
			entity Blog{
			    many Post
			}
        ''')
        
        Assert.assertNotNull(result)
		val errors = result.eResource.errors
		Assert.assertTrue('''Unexpected errors: «errors.join(", ")»''', errors.isEmpty)
    }
	
	@Test 
    def void parseDomainmodel() {
        val model = parseHelper.parse(
            "entity MyEntity {
                MyEntity
            }")
        val entity = model.elements.head as Entity
        assertSame(entity, entity.relations.head.type)
    }
}
