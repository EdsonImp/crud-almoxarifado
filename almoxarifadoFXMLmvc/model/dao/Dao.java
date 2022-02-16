package almoxarifadoFXMLmvc.model.dao;

import java.util.List; 

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import almoxarifadoFXMLmvc.model.entidades.Produto;



public class Dao<E> {
	static private EntityManagerFactory emf;
	private Class<E> classe;
	private EntityManager em;
	
	//abaixo implementa o emf em um bloco estatico
	static {
	try {
		emf = Persistence.createEntityManagerFactory("almoxarifadoBD");
	}catch(Exception e) {		
	}

	}
		
	public Dao() {
		this(null);
	}
	
	//abaixo cria o "em" dentro do construtor, e ja pede como parametro a classe
	public Dao(Class<E> classe) {
		this.classe = classe;
		em = emf.createEntityManager();
}
	//abaixo metodos para acrir e fechar a conexao
	
		public Dao<E> abrirT(){
		em.getTransaction().begin();
		return this;
	}
	public Dao<E> fecharT(){
		em.getTransaction().commit();
		return this;
	}
	public Dao<E> incluir(E entidade ){
		em.persist(entidade);
		return this;
	}
	
	public Dao<E> incluirAtomico(E entidade){
		return this.abrirT().incluir(entidade).fecharT();
	
	}
	
	
	
	public E obterPorId( Object id) {
			return em.find(classe, id);
		
	}
	public List<Produto> obterPorNome(String nome){
		String nomeConsulta = nome;
		if(!nome.isEmpty()) { 
		em.getTransaction().begin();
		String jpql = ("select c from Produto c where c.nome like :pnome");
		Query query = em.createQuery(jpql);
		query.setParameter("pnome", "%" + nomeConsulta + "%");
		@SuppressWarnings("unchecked")
		List<Produto> produtos = query.getResultList();
		return produtos;}
		else {return null;}
	}
	
	public void removerProduto(int id) {
		int idARemover = id;
		Produto prodARemover = em.find(Produto.class, idARemover);
		if (prodARemover != null) {
		em.getTransaction().begin();
		em.remove(prodARemover);
		em.getTransaction().commit();
		em.close();}
		
	}
	
	public List<Produto> obterPorEspecie(String especie){
		String especieConsulta = especie;
		if(!especie.isEmpty()) { 
		em.getTransaction().begin();
		String jpql = ("select c from Produto c where c.especie like :pespecie");
		Query query = em.createQuery(jpql);
		query.setParameter("pespecie", "%" + especieConsulta + "%");
		@SuppressWarnings("unchecked")
		List<Produto> produtos = query.getResultList();
		return produtos;}
		else {return null;}
	}
	
	public List<Produto> obterPorLocal(String local){
		String localConsulta = local;
		if(!local.isEmpty()) { 
		em.getTransaction().begin();
		String jpql = ("select c from Produto c where c.local like :plocal");
		Query query = em.createQuery(jpql);
		query.setParameter("plocal", "%" + localConsulta + "%");
		@SuppressWarnings("unchecked")
		List<Produto> produtos = query.getResultList();
		return produtos;}
		else {return null;}
	}
	//public List<E> obterTodos(){
	//	return this.obterTodos(10,0);
	//}
	
	// nesse caso passa para o metodo a quantidade de resultados que vc quer
	// e de onde começa, tipo quero apenas 5, começando do terceito
	public List<E> obterTodos(){
		if (classe == null) {
			throw new UnsupportedOperationException("classe nula");
		}
		//cria o arquivo detalhando a consulta
		String jpql = "Select e from " + classe.getName() + " e";
		//cria a consulta
		TypedQuery<E> query = em.createQuery(jpql, classe); //cria a pesquisa
		//query.setMaxResults(qtd); //passa parametros po referencia
		//query.setFirstResult();
		return query.getResultList();
	}
	public void fechar() {
		em.close();
	}
}


